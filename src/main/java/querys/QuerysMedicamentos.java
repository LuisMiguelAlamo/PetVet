package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Medicamentos;

/**
 *
 * @author Luis Miguel
 */
public class QuerysMedicamentos {

    static ResultSet rs;
    static Statement smnt;

    //Método que devuelve una lista de todos los Medicamentos
    public static ArrayList<Medicamentos> consultaGeneral() {
        Medicamentos medicamento;
        ArrayList<Medicamentos> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM medicamentos");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int codProveedor = rs.getInt("codigoProveedor");
                    medicamento = new Medicamentos(id, nombre, precio, codProveedor);
                    
                    lista.add(medicamento);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;

    }
    
    //Método que devuelve un Medicamento por su id
    public static Medicamentos consultaGeneral(int id) {
        Medicamentos medicamento = null;
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM medicamentos  WHERE id = "+ id);
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int codProveedor = rs.getInt("codigoProveedor");
                    medicamento = new Medicamentos(id, nombre, precio, codProveedor);                    
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return medicamento;

    }

    //Método que devuelve una lista de los Medicamentos que contengan algún dato 
    //igual al pasado como argumento
    public static ArrayList<Medicamentos> consultaFiltro(String campo) {
        Medicamentos medicamento;
        ArrayList<Medicamentos> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM medicamentos WHERE nombre LIKE '%" + campo + "%'"
                        + " OR precio LIKE '%" + campo + "%'"
                        + " OR codigoProveedor LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int codProveedor = rs.getInt("codigoProveedor");
                    medicamento = new Medicamentos(id, nombre, precio, codProveedor);
                    
                    lista.add(medicamento);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    //Método que crea un nuevo Medicamento
    public static void crear(Medicamentos medicamento) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO medicamentos VALUES (null,'"
                        + medicamento.getNombre()+ "','"
                        + medicamento.getPrecio()+ "','"
                        + medicamento.getCodProveedor()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que actualiza el Medicamento pasado como argumento
    public static void actualizar(Medicamentos medicamento) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  medicamentos SET nombre = '" + medicamento.getNombre()
                        + "', precio = '" + medicamento.getPrecio()
                        + "', codigoProveedor = '" + medicamento.getCodProveedor()
                        + "' WHERE id = '" + medicamento.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que elimina el Medicamento con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM medicamentos WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
        }
    }
}
