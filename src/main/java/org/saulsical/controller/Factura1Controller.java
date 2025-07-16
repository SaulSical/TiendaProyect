
package org.saulsical.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.saulsical.system.Main;

/**
 * FXML Controller class
 *
 * @author denis
 */
public class Factura1Controller implements Initializable {
    @FXML private Button btnRegresar;
      private Main principal;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
        @FXML
    private void btnRegresarActionEvent(ActionEvent event) {
        // Aquí va la lógica de respuesta a los botones y menús
        if (event.getSource() == btnRegresar) {
                principal.getMenuPrincipalView();
            }
        }
    
}
