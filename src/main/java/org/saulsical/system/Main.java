package org.saulsical.system;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.saulsical.controller.ComprasController;
import org.saulsical.controller.Factura1Controller;
import org.saulsical.controller.LoginController;
import org.saulsical.controller.MenuPrincipalController;
import org.saulsical.controller.MenuPrincipalController;
import org.saulsical.controller.ProductosController;

/**
 * Clase principal de la aplicación
 */
public class Main extends Application {

    private static HostServices hostServices;
    private static final String URL = "/org/saulsical/view/";
    private Stage escenarioPrincipal;
    private Scene siguienteEscena;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        hostServices = getHostServices();
        this.escenarioPrincipal = stage;

        stage.setTitle("Tienda de Videojuegos");
        getLoginView(); // Mostrar Login como primera pantalla
        stage.show();
    }

    public Initializable cambiarEscena(String fxml, double ancho, double alto) throws Exception {
        Initializable interfazDeCambio = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivoFXML = Main.class.getResourceAsStream(URL + fxml);

        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Main.class.getResource(URL + fxml));

        siguienteEscena = new Scene(cargadorFXML.load(archivoFXML), ancho, alto);
        escenarioPrincipal.setScene(siguienteEscena);
        escenarioPrincipal.sizeToScene();

        interfazDeCambio = cargadorFXML.getController();
        return interfazDeCambio;
    }

    public static HostServices getAppHostServices() {
        return hostServices;
    }

    // Vista Login
    public void getLoginView() {
        try {
            LoginController control = (LoginController) cambiarEscena("LoginView.fxml", 600, 400);
            control.setPrincipal(this); // para cambiar de vista desde LoginController
        } catch (Exception e) {
            System.out.println("Error al cargar LoginView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Vista Menú Principal (después del login)
    public void getMenuPrincipalView() {
        try {
            MenuPrincipalController control = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1000, 900);
            control.setPrincipal(this);
        } catch (Exception ex) {
            System.out.println("Error al ir al Menú Principal: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void getComprasView() {
        try {
            ComprasController control = (ComprasController) cambiarEscena("ComprasView.fxml", 1000, 900);
            control.setPrincipal(this);
        } catch (Exception ex) {
            System.out.println("Error al ir a Compras: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void getFacturasView() {
        try {
            Factura1Controller control = (Factura1Controller) cambiarEscena("Factura1View.fxml", 1000, 900);
            control.setPrincipal(this);
        } catch (Exception ex) {
            System.out.println("Error al ir a Facturas: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void getProductosView() {
        try {
            ProductosController control = (ProductosController) cambiarEscena("ProductosView.fxml", 1000, 900);
            control.setPrincipal(this);
        } catch (Exception ex) {
            System.out.println("Error al ir a Productos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Puedes agregar otras vistas como getIngresoView(), getProductosView(), etc. si es necesario
}
