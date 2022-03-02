package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Veterinarios;

/**
 *
 * @author Luis Miguel
 */
public class QuerysVeterinarios {

    static ResultSet rs;
    static Statement smnt;
    
    //Método que devuelve una lista de todos los Veterinarios
    public static ArrayList<Veterinarios> consultaGeneral() {
        Veterinarios vet;
        ArrayList<Veterinarios> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM veterinarios");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    vet = new Veterinarios(id, nombre, direccion, telefono);
                    lista.add(vet);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    //Método que devuelve un Veterinario por su id
    public static Veterinarios consultaGeneral(int id) {
        Veterinarios vet = null;
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM veterinarios WHERE id = " + id);
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    vet = new Veterinarios(id, nombre, direccion, telefono);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return vet;
    }

    //Método que devuelve una lista de los Veterinarios que contengan algún dato 
    //igual al pasado como argumento
    public static ArrayList<Veterinarios> consultaFiltro(String campo) {
        Veterinarios vet;
        ArrayList<Veterinarios> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM veterinarios WHERE nombre LIKE '%" + campo + "%'"
                        + " OR direccion LIKE '%" + campo + "%'"
                        + " OR telefono LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    vet = new Veterinarios(id, nombre, direccion, telefono);
                    lista.add(vet);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    //Método que crea un nuevo Veterinario
    public static void crear(Veterinarios vet) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO veterinarios VALUES (null,'" + vet.getNombre() + "','" 
                        + vet.getDireccion() + "','" 
                        + vet.getTelefono() + "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear nuevo Veterinario");
        }
    }

    //Método que actualiza el Veterinario pasado como argumento
    public static void actualizar(Veterinarios vet) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  veterinarios SET nombre = '" + vet.getNombre()
                        + "', direccion = '" + vet.getDireccion()
                        + "', telefono = '" + vet.getTelefono()
                        + "' WHERE id = '" + vet.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al editar el veterinario");
        }
    }

    //Método que elimina el Medicamento con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM veterinarios WHERE id = '" + id + "';");
                System.out.println("Ejecutó método eliminar");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el veterinario");
        }
    }
}

