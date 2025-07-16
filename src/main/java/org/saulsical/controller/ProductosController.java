
package org.saulsical.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.saulsical.conexion.Conexion;
import org.saulsical.model.Productos;
import org.saulsical.system.Main;

/**
 * FXML Controller class
 *
 * @author denis
 */
public class ProductosController implements Initializable {
     private ObservableList <Productos>listaProducto;
    @FXML private Button btnRegresar, btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    @FXML private TableView<Productos> tablaProductos;
    @FXML private TableColumn colId, colNombre, colCategoria, colPlataforma, colPrecio, colDescricion, colStock;
    @FXML private TextField txtId,txtNombre,txtPlataforma,txtDescripcion;
    @FXML private RadioButton rbVideoJuego,rbConsola,rbAccesorio;
    @FXML private Spinner<Double> spiPrecio;
    @FXML private Spinner<Integer> spiStock;

    private Main principal;
    private Productos productos;

    
    private enum EstadoFormulario{
        AGREGAR,EDITAR,ELIMINAR,NINGUNO
    }
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    EstadoFormulario estadoActual = EstadoFormulario.NINGUNO;
    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configuarColumnas();
        cargarTablaProductos();
        cargarProductosFormulario();
        actualizarEstadoFormulario(estadoActual);
        tablaProductos.setOnMouseClicked(eventHandler -> cargarProductosFormulario ());
        rbVideoJuego.setSelected(true);
        txtId.setDisable(true);
    }
    
    public void configuarColumnas(){
        colId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("IdPro"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombreP"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("categoria"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<Productos, String>("plataforma"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precio"));
        colDescricion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
        colStock.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("stock"));
        
    }
    private void cargarTablaProductos(){
        listaProducto = FXCollections.observableList(listarProductos());
        tablaProductos.setItems(listaProducto);
        tablaProductos.getSelectionModel().selectFirst();
    }
    
    
        private void cargarProductosFormulario() {

            Productos compraSeleccionada = tablaProductos.getSelectionModel().getSelectedItem();

            if (compraSeleccionada != null) {

                txtId.setText(String.valueOf(compraSeleccionada.getIdPro()));
                txtNombre.setText(compraSeleccionada.getNombreP());
                txtPlataforma.setText(compraSeleccionada.getPlataforma());
                txtDescripcion.setText(compraSeleccionada.getDescripcion());
                
                // RadioButton para estado de compra
                String estado = compraSeleccionada.getCategoria(); // valores: Pendiente, Cancelado, Completado
                if (estado.equalsIgnoreCase("Accesorio")) {
                    rbAccesorio.setSelected(true);
                } else if (estado.equalsIgnoreCase("Consola")) {
                    rbConsola.setSelected(true);
                } else if (estado.equalsIgnoreCase("Videojuego")) {
                    rbVideoJuego.setSelected(true);
                }


                // Spinner para total
                SpinnerValueFactory.DoubleSpinnerValueFactory total = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 2000.00, compraSeleccionada.getPrecio(), 0.50);
                spiPrecio.setValueFactory(total);
                
                SpinnerValueFactory.IntegerSpinnerValueFactory stockFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, compraSeleccionada.getStock(), 1);
                spiStock.setValueFactory(stockFactory);

            }
        }

    
    
    
        private ArrayList<Productos> listarProductos(){
            ArrayList<Productos> productos = new ArrayList<>();

             try{    

                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarProductos()}");
                ResultSet resultado =enunciado.executeQuery();
                while (resultado.next()){
                    productos.add(new Productos(
                            resultado.getInt("idProducto"),
                            resultado.getString("nombreProducto"),
                            resultado.getString("categoria"),
                            resultado.getString("plataforma"),
                            resultado.getDouble("precio"),
                            resultado.getString("descripcion"),
                            resultado.getInt("stock")));
                    
                }

            }catch(SQLException e){
                System.out.println("Error al listar Productos");
                e.printStackTrace();
            }

            return productos;
        }
        
        

    private Productos cargarModeloProducto() {
    int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
    String nombre = txtNombre.getText();
    String plataforma = txtPlataforma.getText();
    String descripcion = txtDescripcion.getText();
    String categoria;

    // Determinar la categoría seleccionada
    if (rbAccesorio.isSelected()) {
        categoria = "Accesorio";
    } else if (rbConsola.isSelected()) {
        categoria = "Consola";
    } else {
        categoria = "Videojuego";
    }

    // Obtener precio del spinner
    double precio = spiPrecio.getValue();
    int stock = spiStock.getValue();

    // Por defecto establecer stock en 0 (o puede ser otro valor si lo tienes en el formulario)

    return new Productos(id, nombre, categoria, plataforma, precio, descripcion, stock);
}



    
   private void insertarNuevoProducto() {
    productos = cargarModeloProducto();

    try {
        CallableStatement cs = Conexion.getInstancia().getConexion()
            .prepareCall("{call sp_agregarProducto(?, ?, ?, ?, ?, ?)}");

        cs.setString(1, productos.getNombreP());
        cs.setString(2, productos.getCategoria());
        cs.setString(3, productos.getPlataforma());
        cs.setDouble(4, productos.getPrecio());
        cs.setString(5, productos.getDescripcion());
        cs.setInt(6, productos.getStock()); // podrías usar un Spinner de stock

        int resultado = cs.executeUpdate();
        if (resultado > 0) {
            System.out.println("Producto agregado exitosamente.");
            cargarTablaProductos();
            tablaProductos.getSelectionModel().selectFirst();
        }
    } catch (SQLException e) {
        System.out.println("Error al insertar producto");
        e.printStackTrace();
    }
}
   

   private void actualizarProducto() {
    productos = cargarModeloProducto();

    try {
        CallableStatement cs = Conexion.getInstancia().getConexion()
            .prepareCall("{call sp_actualizarProducto(?, ?, ?, ?, ?, ?, ?)}");

        cs.setInt(1, productos.getIdPro());
        cs.setString(2, productos.getNombreP());
        cs.setString(3, productos.getCategoria());
        cs.setString(4, productos.getPlataforma());
        cs.setDouble(5, productos.getPrecio());
        cs.setString(6, productos.getDescripcion());
        cs.setInt(7, productos.getStock());

        cs.execute();
        cargarTablaProductos();
        tablaProductos.getSelectionModel().selectFirst();
        System.out.println("Producto actualizado correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al actualizar producto");
        e.printStackTrace();
    }
}

    
    private void eliminarProducto() {
    productos = tablaProductos.getSelectionModel().getSelectedItem();

    if (productos != null) {
        try {
            CallableStatement cs = Conexion.getInstancia().getConexion()
                .prepareCall("{call sp_eliminarProducto(?)}");

            cs.setInt(1, productos.getIdPro());
            cs.execute();

            cargarTablaProductos();
            tablaProductos.getSelectionModel().selectFirst();
            System.out.println("Producto eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto");
            e.printStackTrace();
        }
    } else {
        mostrarMensajeError("Debes seleccionar un producto para eliminar.");
    }
}

    
    
    private void limpiarFormulario() {
    txtId.clear();
    txtNombre.clear();
    txtPlataforma.clear();
    txtDescripcion.clear();
    rbVideoJuego.setSelected(true);

    SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory =
        new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 2000.00, 0.00, 0.50);
    spiPrecio.setValueFactory(valueFactory);
    
    SpinnerValueFactory.IntegerSpinnerValueFactory stockFactory =
    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1);
    spiStock.setValueFactory(stockFactory);

}

    
    private void actualizarEstadoFormulario(EstadoFormulario estado) {
    estadoActual = estado;

    boolean activo = (estado == EstadoFormulario.AGREGAR || estado == EstadoFormulario.EDITAR);

    txtNombre.setDisable(!activo);
    txtPlataforma.setDisable(!activo);
    txtDescripcion.setDisable(!activo);
    spiStock.setDisable(!activo);
    spiPrecio.setDisable(!activo);
    
    rbVideoJuego.setDisable(!activo);
    rbConsola.setDisable(!activo);
    rbAccesorio.setDisable(!activo);

    tablaProductos.setDisable(activo);

    btnNuevo.setText(activo ? "Guardar" : "Nuevo");
    btnEliminar.setText(activo ? "Cancelar" : "Eliminar");
    btnEditar.setDisable(activo);
}

    
    @FXML private void agregarGuardar() {
    switch (estadoActual) {
        case NINGUNO:
            limpiarFormulario();
            actualizarEstadoFormulario(EstadoFormulario.AGREGAR);
            break;

        case AGREGAR:
            insertarNuevoProducto();
            actualizarEstadoFormulario(EstadoFormulario.NINGUNO);
            break;

        case EDITAR:
            actualizarProducto();
            actualizarEstadoFormulario(EstadoFormulario.NINGUNO);
            break;
    }
}

    
    @FXML private void editarProducto() {
    actualizarEstadoFormulario(EstadoFormulario.EDITAR);
}

    
    
    @FXML private void eliminarCancelar() {
    if (estadoActual == EstadoFormulario.NINGUNO) {
        eliminarProducto();
    } else {
        actualizarEstadoFormulario(EstadoFormulario.NINGUNO);
        tablaProductos.getSelectionModel().selectFirst();
    }
}


    
    
    @FXML
    private void btnSiguienteAccion(){
        //en donde estamos (index),
        int indice = tablaProductos.getSelectionModel().getSelectedIndex();
        if (indice < listaProducto.size()-1) {
            tablaProductos.getSelectionModel().select(indice+1);
            cargarProductosFormulario();
        }
    }
    
    
    @FXML
    private void btnAnteriorAccion(){
        int indice = tablaProductos.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaProductos.getSelectionModel().select(indice-1);
            cargarProductosFormulario();
        }
    }
    
     private void mostrarMensajeError(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Error");
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}
    
        @FXML
    private void btnRegresarActionEvent(ActionEvent event) {
        // Aquí va la lógica de respuesta a los botones y menús
        if (event.getSource() == btnRegresar) {
                principal.getMenuPrincipalView();
            }
        }
    
}
