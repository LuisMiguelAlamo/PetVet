/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Roles;

/**
 *
 * @author Luis Miguel
 */
public class QuerysRol {
    
    static ResultSet rs;
    static Statement smnt;
    
    //Método que devuelve una lista de todos los Roles
     public static ArrayList<Roles> consultaGeneral() {
        Roles rol;
        ArrayList<Roles> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM roles");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    rol = new Roles(id, nombre);
                    lista.add(rol);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;

    }
     
     //Método que devuelve un Rol por su id
     public static Roles consultaGeneral(int id) {
        Roles rol = null;
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM roles WHERE id = " + id);
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    rol = new Roles(id, nombre);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return rol;

    }
}
