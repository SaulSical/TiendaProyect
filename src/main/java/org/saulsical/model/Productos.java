
package org.saulsical.model;

/**
 * 
 * 	idProducto int auto_increment, 
    nombreProducto varchar(250), 
    categoria enum('Videojuego', 'Consola', 'Accesorio'),
    plataforma varchar(100),
    precio decimal(6,2),
    descripcion varchar(250),
    stock int not null,
 *
 * @author Saul Sical
 */
public class Productos {
    
    private int IdPro,stock;
    private String nombreP,categoria,plataforma,descripcion;
    private Double precio;
    
    public Productos() {
    }

    public Productos(int IdPro, String nombreP, String categoria, String plataforma, Double precio, String descripcion,int stock) {
        this.IdPro = IdPro;
        this.nombreP = nombreP;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public int getIdPro() {
        return IdPro;
    }

    public void setIdPro(int IdPro) {
        this.IdPro = IdPro;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
