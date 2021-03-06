package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Consultas;

/**
 *
 * @author Luis Miguel
 */
public class QuerysConsultas {

    static ResultSet rs;
    static Statement smnt;
    
    //Método que devuelve una lista de todas las Consultas
    public static ArrayList<Consultas> consultaGeneral() {
        Consultas consulta;
        ArrayList<Consultas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM consultas");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date fecha = rs.getDate("fecha");
                    String hora = rs.getString("hora");
                    String diagnostico = rs.getString("diagnostico");
                    String tratamiento = rs.getString("tratamiento");
                    int codMedicamento = rs.getInt("codigoMedicamento");
                    int codVeterinario = rs.getInt("codigoVeterinario");
                    int codMascota = rs.getInt("codigoMascota");
                    consulta = new Consultas(id, fecha, hora,diagnostico, tratamiento, codMedicamento, codVeterinario, codMascota);
                    lista.add(consulta);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    //Método que devuelve una Consulta por su id
    public static Consultas consultaGeneral(int id) {
        Consultas consulta = null;
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM consultas WHERE id =" + id);
                while (rs.next()) {
                    Date fecha = rs.getDate("fecha");
                    String hora = rs.getString("hora");
                    String diagnostico = rs.getString("diagnostico");
                    String tratamiento = rs.getString("tratamiento");
                    int codMedicamento = rs.getInt("codigoMedicamento");
                    int codVeterinario = rs.getInt("codigoVeterinario");
                    int codMascota = rs.getInt("codigoMascota");
                    consulta = new Consultas(id, fecha, hora,diagnostico, tratamiento, codMedicamento, codVeterinario, codMascota);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return consulta;
    }

    //Método que devuelve una lista de las Consultas que contengan algún dato 
    //igual al pasado como argumento
    public static ArrayList<Consultas> consultaFiltro(String campo) {
        Consultas consulta;
        ArrayList<Consultas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM consultas WHERE fecha LIKE '%" + campo + "%'"
                        + " OR hora LIKE '%" + campo + "%'"
                        + " OR tratamiento LIKE '%" + campo + "%'"
                        + " OR diagnostico LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date fecha = rs.getDate("fecha");
                    String hora = rs.getString("hora");
                    String diagnostico = rs.getString("diagnostico");
                    String tratamiento = rs.getString("tratamiento");
                    int codMedicamento = rs.getInt("codigoMedicamento");
                    int codVeterinario = rs.getInt("codigoVeterinario");
                    int codMascota = rs.getInt("codigoMascota");
                    consulta = new Consultas(id, fecha, hora,diagnostico, tratamiento, codMedicamento, codVeterinario, codMascota);
                    lista.add(consulta);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    //Método que crea una nueva Consulta
    public static void crear(Consultas consulta) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO consultas VALUES (null,'" + consulta.getFecha()+ "','" 
                        + consulta.getHora()+ "','" 
                        + consulta.getDiagnostico()+ "','" 
                        + consulta.getTratamiento()+ "','"
                        + consulta.getCodMedicamento()+ "','" 
                        + consulta.getCodVeterinario()+ "','" 
                        + consulta.getCodMascota()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que actualiza la Consulta pasada como argumento
    public static void actualizar(Consultas consulta) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  consultas SET fecha = '" + consulta.getFecha()
                        + "', hora = '" + consulta.getHora()
                        + "', diagnostico = '" + consulta.getDiagnostico()
                        + "', tratamiento = '" + consulta.getTratamiento()
                        + "', codigoMedicamento = '" + consulta.getCodMedicamento()
                        + "', codigoVeterinario = '" + consulta.getCodVeterinario()
                        + "', codigoMascota = '" + consulta.getCodMascota()
                        + "' WHERE id = '" + consulta.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que elimina la Consulta con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM consultas WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
        }
    }
}

