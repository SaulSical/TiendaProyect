/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.saulsical.model;

/**
 *
 * @author Saul Sical
 * 
 * 
 * 
 * 
 */
public class Empleado {
    
    private int idEmpleado;
    private String nombreE,ApellidoE,puestoE,telefonoE,correoE;
    private double sueldo;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String nombreE, String ApellidoE, String puestoE, String telefonoE, String correoE, double sueldo) {
        this.idEmpleado = idEmpleado;
        this.nombreE = nombreE;
        this.ApellidoE = ApellidoE;
        this.puestoE = puestoE;
        this.telefonoE = telefonoE;
        this.correoE = correoE;
        this.sueldo = sueldo;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreE() {
        return nombreE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

    public String getApellidoE() {
        return ApellidoE;
    }

    public void setApellidoE(String ApellidoE) {
        this.ApellidoE = ApellidoE;
    }

    public String getPuestoE() {
        return puestoE;
    }

    public void setPuestoE(String puestoE) {
        this.puestoE = puestoE;
    }

    public String getTelefonoE() {
        return telefonoE;
    }

    public void setTelefonoE(String telefonoE) {
        this.telefonoE = telefonoE;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Empleado{" + "nombreE=" + nombreE + ", ApellidoE=" + ApellidoE + ", puestoE=" + puestoE + ", telefonoE=" + telefonoE + ", correoE=" + correoE + '}';
    }
    
    
    
    
}
