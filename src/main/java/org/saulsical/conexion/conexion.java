
package org.saulsical.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author edy14
 */
public class Conexion {
    private static Conexion instancia;
    private Connection conexion;
    private String url = "jdbc:mysql://127.0.0.1:3306/DBTiendaVideojuegos?userSSL=false";
    private String user = "quintov";
    private String password = "admin";
    private String driver = "com.mysql.jdbc.Driver";

    private Conexion(){
        conectar();
    }
   
    public void conectar(){
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, password);
            
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(" error al conectar:" + e.getMessage());
        }
   
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
            
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion != null || conexion.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            System.out.println(" error al reconectar " + e.getMessage());
        }
        return conexion;
    }
}
