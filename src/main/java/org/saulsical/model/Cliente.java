/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.saulsical.model;

/**
 *
 * @author Saul Sical
 * 
 * idCliente int auto_increment, 
    nombreCliente varchar(64), 
    apellidoCliente varchar(64),
    nitCliente varchar(20), 
    correoCliente varchar(64) unique,
    contrasenaCliente varchar(64),
    constraint pk_cliente primary key (idCliente)
 */
public class Cliente {
    
    private int idCliente;
    private String nombreC,apellidoC,nitC,correoC,contraseñaC;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombreC, String apellidoC, String nitC, String correoC, String contraseñaC) {
        this.idCliente = idCliente;
        this.nombreC = nombreC;
        this.apellidoC = apellidoC;
        this.nitC = nitC;
        this.correoC = correoC;
        this.contraseñaC = contraseñaC;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getApellidoC() {
        return apellidoC;
    }

    public void setApellidoC(String apellidoC) {
        this.apellidoC = apellidoC;
    }

    public String getNitC() {
        return nitC;
    }

    public void setNitC(String nitC) {
        this.nitC = nitC;
    }

    public String getCorreoC() {
        return correoC;
    }

    public void setCorreoC(String correoC) {
        this.correoC = correoC;
    }

    public String getContraseñaC() {
        return contraseñaC;
    }

    public void setContraseñaC(String contraseñaC) {
        this.contraseñaC = contraseñaC;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nombreC=" + nombreC + ", apellidoC=" + apellidoC + ", correoC=" + correoC + '}';
    }
    
    
    
    
}
