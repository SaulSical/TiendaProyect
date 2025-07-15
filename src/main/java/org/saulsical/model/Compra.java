/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.saulsical.model;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Saul Sical
 */
public class Compra {

    private int idCompra, idCliente,idEmpleado;

    private String estado, pago;
    private double total;
    private LocalDate fecha;

    public Compra(int idCompra, int idCliente, int idEmpleado) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
    }

    public Compra(int idCompra, int idCliente, int idEmpleado, LocalDate fecha, double total, String pago,String estado ) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.total = total;
        this.pago = pago;
        this.estado = estado;
        
        
        
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
}
