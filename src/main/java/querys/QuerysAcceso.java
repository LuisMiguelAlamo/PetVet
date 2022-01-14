package querys;

import conexion.AbrirConexion;
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
                    String usuario = rs.getString("usuario");
                    String password = rs.getString("password");
                    int codigoVeterinario = rs.getInt("codigoVeterinario");
                    acceso = new Acceso(id, rol, usuario, password, codigoVeterinario);
                    lista.add(acceso);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta general de acceso");
        }
        return lista;

    }
    public static Acceso consultaGeneral(int id) {
        Acceso acceso = null;
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM acceso");
                while (rs.next()) {
                    int rol = rs.getInt("rol");
                    String usuario = rs.getString("usuario");
                    String password = rs.getString("password");
                    int codigoVeterinario = rs.getInt("codigoVeterinario");
                    acceso = new Acceso(id, rol, usuario, password, codigoVeterinario);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta general de acceso");
        }
        return acceso;

    }

    public static ArrayList<Acceso> consultaFiltro(String campo) {
        Acceso acceso;
        ArrayList<Acceso> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM acceso WHERE usuario LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int rol = rs.getInt("rol");
                    String usuario = rs.getString("usuario");
                    String password = rs.getString("password");
                    int codigoVeterinario = rs.getInt("codigoVeterinario");
                    acceso = new Acceso(id, rol,usuario, password, codigoVeterinario);
                    lista.add(acceso);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    
    public static void crear(Acceso acceso) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO acceso VALUES (null,'" + acceso.getRol()+ "','" 
                        + acceso.getUsuario()+ "','" 
                        + acceso.getPassword()+ "','"
                        + acceso.getCodigoVeterinario()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void actualizar(Acceso acceso) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  acceso SET rol = '" + acceso.getRol()
                        + "', usuario = '" + acceso.getUsuario()
                        + "', password = '" + acceso.getPassword()
                        + "', codigoVeterinario = '" + acceso.getCodigoVeterinario()
                        + "' WHERE id = '" + acceso.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

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


