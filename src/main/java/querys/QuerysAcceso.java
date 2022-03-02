package querys;

import conexion.AbrirConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Acceso;

/**
 *
 * @author Luis Miguel
 */
public class QuerysAcceso {

    static ResultSet rs;
    static Statement smnt;
    static PreparedStatement ps;

    //Método que devuelve una lista de todos los Usuarios
    public static ArrayList<Acceso> consultaGeneral() {
        Acceso acceso;
        ArrayList<Acceso> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM acceso");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int rol = rs.getInt("rol");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    acceso = new Acceso(id, rol, email, password);
                    lista.add(acceso);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta general de acceso");
        }
        return lista;
    }
    
    //Método que devuelve un Usuario por su id
    public static Acceso consultaGeneral(int id) {
        Acceso acceso = null;
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM acceso WHERE id = " + id);
                while (rs.next()) {
                    int rol = rs.getInt("rol");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    acceso = new Acceso(id, rol, email, password);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta general de acceso");
        }
        return acceso;
    }
    
    //Método que devuelve un boolean evaluando que el email pasado como argumento se 
    //encuentre o no en la tabla acceso
    public static Acceso isLogged(String email) {
        Acceso acceso = null;
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM acceso WHERE email = '"+ email +"'");
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int rol = rs.getInt("rol");
                    String pass = rs.getString("password");
                    
                    acceso = new Acceso(id, rol, email, pass);
                }
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta isLogged");
        }
        return acceso;

    }

    //Método que devuelve una lista de los Usuarios que contengan un email 
    //como el pasado como argumento
    public static ArrayList<Acceso> consultaFiltro(String campo) {
        Acceso acceso;
        ArrayList<Acceso> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM acceso WHERE email LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int rol = rs.getInt("rol");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    acceso = new Acceso(id, rol,email, password);
                    lista.add(acceso);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    //Método que crea un nuevo Usuario
    public static void crear(Acceso acceso) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO acceso VALUES (null,'" + acceso.getRol()+ "','" 
                        + acceso.getEmail()+ "','" 
                        + acceso.getPassword()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que actualiza el usuario pasado como argumento
    public static void actualizar(Acceso acceso) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  acceso SET rol = '" + acceso.getRol()
                        + "', email = '" + acceso.getEmail()
                        + "', password = '" + acceso.getPassword()
                        + "' WHERE id = '" + acceso.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que elimina el usuario con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM acceso WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el acceso");
        }
    }

    
}


