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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.saulsical.system.Main;

/**
 * FXML Controller class
 *
 * @author denis
 */
public class MenuPrincipalController implements Initializable {
    @FXML private Button btnEmpleados,btnCompras,btnFacturas;
    @FXML private MenuItem itemCerrar;
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
        if (event.getSource() == btnFacturas) {
                principal.getFacturasView();
            }else if(event.getSource()== btnEmpleados){
                principal.getProductosView();
            }else if (event.getSource()== btnCompras){
                principal.getComprasView();
            
            }else if (event.getSource()== itemCerrar){
                principal.getLoginView();
            }
        }
    }
    

