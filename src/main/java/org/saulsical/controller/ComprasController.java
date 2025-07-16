
package org.saulsical.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.SpinnerNumberModel;
import org.saulsical.conexion.Conexion;
import org.saulsical.model.Cliente;
import org.saulsical.model.Compra;
import org.saulsical.model.Empleado;
import org.saulsical.system.Main;

/**
 * FXML Controller class
 *
 * @author denis
 */
public class ComprasController implements Initializable {

    private ObservableList <Compra>listaCompra;
    @FXML private Button btnRegresar, btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnBuscar,btnReporte;
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn colId, colFecha, colEstadoCompra, colCliente, colFormaPago, colTotal, colEmpleado;
    @FXML private TextField txtId;
    @FXML private DatePicker dpFecha;
    @FXML private RadioButton rbPendienteCompra,rbCancelada,rbCompletada,rbEfectivo,rbTarjeta;
    @FXML private ComboBox<Cliente> cbxCliente;
    @FXML private ComboBox<Empleado> cbxEmpleado;
    @FXML private Spinner<Double> spiTotal;
    private Main principal;
    private Compra compra;

    
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
        cargarTablaCompra();
        cargarComprasFormulario();
        cargarClienteComboBox();
        cargarEmpleadoComboBox();
        actualizarEstadoFormulario(estadoActual);
        tablaCompras.setOnMouseClicked(eventHandler -> cargarComprasFormulario());
        rbCompletada.setSelected(true);
        rbEfectivo.setSelected(true);
        txtId.setDisable(true);
    }
    
    public void configuarColumnas(){
        colId.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("idCompra"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Compra, String>("idCliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Compra, String>("idEmpleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Compra, DatePicker>("fecha"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compra, Double>("total"));
        colFormaPago.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("pago"));
        colEstadoCompra.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("estado"));
        
    }
    private void cargarTablaCompra(){
        listaCompra = FXCollections.observableList(listarCompra());
        tablaCompras.setItems(listaCompra);
        tablaCompras.getSelectionModel().selectFirst();
    }
    
    
        private void cargarComprasFormulario() {

            Compra compraSeleccionada = tablaCompras.getSelectionModel().getSelectedItem();

            if (compraSeleccionada != null) {

                txtId.setText(String.valueOf(compraSeleccionada.getIdCompra()));
                dpFecha.setValue(compraSeleccionada.getFecha()); 
                
                // ComboBox de Cliente
                for (Cliente cliente : cbxCliente.getItems()) {
                    if (cliente.getIdCliente()== compraSeleccionada.getIdCliente()) {
                        cbxCliente.setValue(cliente);
                        break;
                    }
                }

                // ComboBox de Empleado
                for (Empleado empleado : cbxEmpleado.getItems()) {
                    if (empleado.getIdEmpleado()== compraSeleccionada.getIdEmpleado()) {
                        cbxEmpleado.setValue(empleado);
                        break;
                    }
                }

                // RadioButton para estado de compra
                String estado = compraSeleccionada.getEstado(); // valores: Pendiente, Cancelado, Completado
                if (estado.equalsIgnoreCase("Pendiente")) {
                    rbPendienteCompra.setSelected(true);
                } else if (estado.equalsIgnoreCase("Cancelado")) {
                    rbCancelada.setSelected(true);
                } else if (estado.equalsIgnoreCase("Completado")) {
                    rbCompletada.setSelected(true);
                }

                // RadioButton para método de pago
                String metodo = compraSeleccionada.getPago(); // valores: Efectivo, Tarjeta
                if (metodo.equalsIgnoreCase("Efectivo")) {
                    rbEfectivo.setSelected(true);
                } else if (metodo.equalsIgnoreCase("Tarjeta")) {
                    rbTarjeta.setSelected(true);
                }

                // Spinner para total
                SpinnerValueFactory.DoubleSpinnerValueFactory total = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 2000.00, compraSeleccionada.getTotal(), 0.50);
                spiTotal.setValueFactory(total);
            }
        }

    
    
    private void cargarClienteComboBox(){
        ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();
        
        try{    
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = enunciado.executeQuery();
            while(resultado.next()){
                listaCliente.add(new Cliente(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5),
                        resultado.getString(6)));
            }
            
        }catch(SQLException e){
            System.out.println("Error al listar Clientes");
            e.printStackTrace();
        }
        
        cbxCliente.setItems(listaCliente);
    }
    
     private void cargarEmpleadoComboBox(){
        ObservableList<Empleado> listaEmpleado = FXCollections.observableArrayList();
        
        try{    
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = enunciado.executeQuery();
            while(resultado.next()){
                listaEmpleado.add(new Empleado(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4), 
                        resultado.getString(5), 
                        resultado.getString(6),
                        resultado.getDouble(7))) ;
            }
            
        }catch(SQLException e){
            System.out.println("Error al listar Empleado");
            e.printStackTrace();
        }
        
        cbxEmpleado.setItems(listaEmpleado);
    }
    
    
    
    
        private ArrayList<Compra> listarCompra(){
            ArrayList<Compra> compra = new ArrayList<>();

             try{    

                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarVentas()}");
                ResultSet resultado =enunciado.executeQuery();
                while (resultado.next()){
                    compra.add(new Compra(
                            resultado.getInt("idVenta"),
                            resultado.getInt("idCliente"),
                            resultado.getInt("idEmpleado"),
                            resultado.getDate("fechaVenta").toLocalDate(),
                            resultado.getDouble("total"),
                            resultado.getString("metodoPago"),
                            resultado.getString("estadoPago")));
                }

            }catch(SQLException e){
                System.out.println("Error al listar Compras");
                e.printStackTrace();
            }

            return compra;
        }

        private Compra cargarModeloCompra() {
    int codigoCompra = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());

    Cliente clienteSeleccionado = cbxCliente.getValue();
    if (clienteSeleccionado == null) {
        mostrarMensajeError("Debe seleccionar un cliente.");
        return null;
    }

    Empleado empleadoSeleccionado = cbxEmpleado.getValue();
    if (empleadoSeleccionado == null) {
        mostrarMensajeError("Debe seleccionar un empleado.");
        return null;
    }

    String estado;
    if (rbPendienteCompra.isSelected()) {
        estado = "Pendiente";
    } else if (rbCancelada.isSelected()) {
        estado = "Canelado";
    } else {
        estado = "Completado";
    }

    String metodo;
    if (rbEfectivo.isSelected()) {
        metodo = "Efectivo";
    } else {
        metodo = "Tarjeta";
    }

    double total = spiTotal.getValue();

    return new Compra(
        codigoCompra,
        clienteSeleccionado.getIdCliente(),
        empleadoSeleccionado.getIdEmpleado(),
        dpFecha.getValue(),
        total,
        estado,
        metodo
    );
}


    
    private void insertarNuevaCompra(){
        
         compra = cargarModeloCompra();
        
        try{
        
        CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarVenta(?,?,?,?,?,?)}");
        enunciado.setInt(1, compra.getIdCliente());
        enunciado.setInt(2, compra.getIdEmpleado());
        enunciado.setDate(3, Date.valueOf(compra.getFecha()));
        enunciado.setDouble(4, compra.getTotal());
        enunciado.setString(5, compra.getPago());
        enunciado.setString(6, compra.getEstado());
        
        int registrosAgregados = enunciado.executeUpdate();
            if (registrosAgregados > 0) {
                System.out.println("Compra agregada correctamente");
                cargarTablaCompra();
                tablaCompras.getSelectionModel().selectFirst();
            }
        }catch(SQLException e){
            System.out.println("Error al agregar Compra");
            e.printStackTrace();
        }
    }
    private void actualizarCompra(){
        compra = cargarModeloCompra();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarVenta(?,?,?,?,?,?,?)}");
            enunciado.setInt(1, compra.getIdCompra());
            enunciado.setInt(2, compra.getIdCliente());
            enunciado.setInt(3, compra.getIdEmpleado());
            enunciado.setDate(4, Date.valueOf(compra.getFecha()));
            enunciado.setDouble(5, compra.getTotal());
            enunciado.setString(6, compra.getPago());
            enunciado.setString(7, compra.getEstado());
            enunciado.execute();
            cargarTablaCompra();
            tablaCompras.getSelectionModel().selectFirst();


        } catch (SQLException e) {
            System.out.println("Error al editar Compra");
            e.printStackTrace();
        }
    }
    private void eliminarCompra(){
     compra = tablaCompras.getSelectionModel().getSelectedItem();
        try {
             CallableStatement enunciado = Conexion.getInstancia().getConexion()
                     .prepareCall("{call sp_eliminarVenta(?)}");
             
             enunciado.setInt(1, compra.getIdCompra());
             enunciado.execute();
             cargarTablaCompra();
             tablaCompras.getSelectionModel().selectFirst();

            
        } catch (Exception e) {
            System.out.println("Error al eliminar Compras");
            e.printStackTrace();
        }

    }
    
    private void limpiarFormulario(){
        txtId.clear();
        dpFecha.setValue(null);
        rbCancelada.setSelected(false);
        rbCompletada.setSelected(false);
        rbEfectivo.setSelected(false);
        rbPendienteCompra.setSelected(false);
        rbTarjeta.setSelected(false);
        cbxCliente.getSelectionModel().clearSelection();
        cbxEmpleado.getSelectionModel().clearSelection();
        
         // Restablecer spinner a 0.00
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory =
        new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 2000.00, 0.00, 0.50);
        spiTotal.setValueFactory(valueFactory);
        
    }
    
    
    private void actualizarEstadoFormulario(EstadoFormulario estado){
        estadoActual = estado;
        
        boolean activo= (estado == EstadoFormulario.AGREGAR || estado == EstadoFormulario.EDITAR);
        
        txtId.setDisable(!activo);
        rbCancelada.setSelected(!activo);
        rbCompletada.setSelected(!activo);
        rbEfectivo.setSelected(!activo);
        rbPendienteCompra.setSelected(!activo);
        rbTarjeta.setSelected(!activo);
        dpFecha.setDisable(!activo);
        cbxCliente.setDisable(!activo);
        cbxEmpleado.setDisable(!activo);
        spiTotal.setDisable(!activo);
        
        tablaCompras.setDisable(activo);
        
        btnNuevo.setText(activo ? "Guardar":"Nuevo");
        btnEliminar.setText(activo ? "Cancelar":"Eliminar");
        btnEditar.setDisable(activo);
    }
    
    @FXML private void agregarGuardar(){
        switch (estadoActual) {
            case NINGUNO:
                limpiarFormulario();
                System.out.println("Voy a preparar el formulario");
                actualizarEstadoFormulario(EstadoFormulario.AGREGAR);
                
                
                break;
            case AGREGAR:
                insertarNuevaCompra();
                System.out.println("guardando nuevo Cita");
                actualizarEstadoFormulario(EstadoFormulario.NINGUNO);
                break;
            case EDITAR:
                actualizarCompra();
                System.out.println("Guardando edicion Cita");
                actualizarEstadoFormulario(EstadoFormulario.NINGUNO);
                
                break;
           
          };
        }
    
    @FXML private void editarCompra(){
        actualizarEstadoFormulario(EstadoFormulario.EDITAR);
    }
    
    
    @FXML private void EliminarCancelar(){
        if (estadoActual == EstadoFormulario.NINGUNO) {
           eliminarCompra();
            System.out.println("Terminando de Eliminar Compra");
        }else {
            actualizarEstadoFormulario(EstadoFormulario.NINGUNO);
            tablaCompras.getSelectionModel().selectFirst();

        }
    }

    
    
    @FXML
    private void btnSiguienteAccion(){
        //en donde estamos (index),
        int indice = tablaCompras.getSelectionModel().getSelectedIndex();
        if (indice < listaCompra.size()-1) {
            tablaCompras.getSelectionModel().select(indice+1);
            cargarComprasFormulario();
        }
    }
    
    
    @FXML
    private void btnAnteriorAccion(){
        int indice = tablaCompras.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaCompras.getSelectionModel().select(indice-1);
            cargarComprasFormulario();
        }
    }
    
    public void clickManejadorEventos(ActionEvent evento){
        if (evento.getSource() == btnRegresar) {
            System.out.println(" cerrando, adios. ");
            principal.getMenuPrincipalView();
        } 
    }
    
    private void mostrarMensajeError(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Error");
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}

    
}
