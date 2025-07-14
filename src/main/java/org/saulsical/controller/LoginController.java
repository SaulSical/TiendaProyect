package org.saulsical.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.saulsical.conexion.Conexion;
import org.saulsical.system.Main;

public class LoginController implements Initializable {

    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private Button btnIngresar;
    
    private Main principal;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIngresar.setOnAction(e -> login());
    }

    private void login() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, llene todos los campos.");
            return;
        }

        try {
            Connection conn = Conexion.getInstancia().getConexion();
            String sql = "SELECT * FROM Clientes WHERE correoCliente = ? AND contrasenaCliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mostrarAlerta("Éxito", "Bienvenido " + rs.getString("nombreCliente") + " " + rs.getString("apellidoCliente"));
                principal.getComprasView();
                // Aquí podrías redirigir a una vista principal
            } else {
                mostrarAlerta("Error", "Correo o contraseña incorrectos.");
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
