package querys;

import conexion.AbrirConexion;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Citas;

/**
 *
 * @author Luis Miguel
 */
public class QuerysCitas {

    static ResultSet rs;
    static Statement smnt;
    
    
    public static ArrayList<Citas> consultaGeneral() {
        Citas cita;
        ArrayList<Citas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM citas");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date fecha = rs.getDate("fecha");
                    String hora = rs.getString("hora");
                    int codVeterinario = rs.getInt("codigoVeterinario");
                    int codMascota = rs.getInt("codigoMascota");
                    cita = new Citas(id, fecha, hora,codVeterinario, codMascota);
                    lista.add(cita);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;

    }
    public static Citas consultaGeneral(int id) {
        Citas cita = null;
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM citas");
                while (rs.next()) {
                    Date fecha = rs.getDate("fecha");
                    String hora = rs.getString("hora");
                    int codVeterinario = rs.getInt("codigoVeterinario");
                    int codMascota = rs.getInt("codigoMascota");
                    cita = new Citas(id, fecha, hora,codVeterinario, codMascota);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return cita;

    }

    public static ArrayList<Citas> consultaFiltro(String campo) {
        Citas cita;
        ArrayList<Citas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM citas WHERE fecha LIKE '%" + campo + "%'"
                        + " OR hora LIKE '%" + campo + "%'"
                        + " OR codigoMedicamento LIKE '%" + campo + "%'"
                        + " OR codigoMascota LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date fecha = rs.getDate("fecha");
                    String hora = rs.getString("hora");
                    int codVeterinario = rs.getInt("codigoVeterinario");
                    int codMascota = rs.getInt("codigoMascota");
                    cita = new Citas(id, fecha, hora,codVeterinario, codMascota);
                    lista.add(cita);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    public static void crear(Citas cita) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO citas VALUES (null,'" 
                        + cita.getFecha()+ "','" 
                        + cita.getHora()+ "','" 
                        + cita.getCodVeterinario()+ "','" 
                        + cita.getCodMascota()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void actualizar(Citas cita) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  citas SET fecha = '" + cita.getFecha()
                        + "', hora = '" + cita.getHora()
                        + "', codigoVeterinario = '" + cita.getCodVeterinario()
                        + "', codigoMascota = '" + cita.getCodMascota()
                        + "' WHERE id = '" + cita.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM citas WHERE id = '" + id + "';");
                System.out.println(id);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
        }
    }
}

