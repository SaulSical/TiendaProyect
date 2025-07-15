/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.saulsical.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.saulsical.system.Main;

/**
 * FXML Controller class
 *
 * @author denis
 */
public class MenuPrincipalController implements Initializable {

    private Main principal;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void clickManejadorEventos(ActionEvent event) {
        // Aquí va la lógica de respuesta a los botones y menús
        System.out.println("Evento detectado: " + event.getSource());
    }
    
}
