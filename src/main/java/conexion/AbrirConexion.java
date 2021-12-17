/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis Miguel Alamo Hernández
 */
public class AbrirConexion {
    
    private static Connection cone = null;
    
     public static Connection getCone() {
        return cone;
    }

    public AbrirConexion() {
    }
    
    public static boolean abrirConect() throws SQLException {
        if (cone != null && cone.isValid(2000)) {
            return true;
        }
        try {
            String url = "jdbc:mysql://localhost:3306/test" + "?useServerPrepStmts=true";
            cone = DriverManager.getConnection(url, "root", "");
            System.out.println("Conectado a test");
            return true;

        } catch (SQLException ex) {
            if (cone != null) {
                cone.close();
                cone = null;
            }

            System.out.println("Se ha producido un error");
            return false;
        }
    }
}
