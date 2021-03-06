package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Clientes;

/**
 *
 * @author Luis Miguel
 */
public class QuerysClientes {

    static ResultSet rs;
    static Statement smnt;
    
    //Método que devuelve una lista de todos los Clientes
    public static ArrayList<Clientes> consultaGeneral() {
        Clientes cliente;
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM clientes");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("cp");
                    cliente = new Clientes(id, nombre, direccion, localidad, telefono, email, cp);
                    lista.add(cliente);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    //Método que devuelve un Cliente por su id
    public static Clientes consultaGeneral(int id) {
        Clientes cliente = null;
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM clientes WHERE id = " + id );
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("cp");
                    cliente = new Clientes(id, nombre, direccion, localidad, telefono, email, cp);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return cliente;
    }

    //Método que devuelve una lista de los Clientes que contengan algún dato 
    //igual al pasado como argumento
    public static ArrayList<Clientes> consultaFiltro(String campo) {
        Clientes cliente;
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM clientes WHERE id LIKE '%" + campo + "%'"
                        + " OR nombre LIKE '%" + campo + "%'"
                        + " OR email LIKE '%" + campo + "%'"
                        + " OR telefono LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("cp");
                    cliente = new Clientes(id, nombre, direccion, localidad, telefono, email, cp);
                    lista.add(cliente);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos, filtro no funciona");
        }
        return lista;
    }

    //Método que crea un nuevo Cliente
    public static void crear(Clientes cliente) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                System.out.println(cliente.getNombre() + cliente.getDireccion()+
                cliente.getLocalidad());
                smnt.executeUpdate("INSERT INTO clientes VALUES (null,'" + cliente.getNombre() + "','" 
                        + cliente.getDireccion() + "','" 
                        + cliente.getLocalidad() + "','"
                        + cliente.getTelefono() + "','" 
                        + cliente.getEmail()+ "','" 
                        + cliente.getCp() + "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que actualiza el Cliente pasado como argumento
    public static void actualizar(Clientes cliente) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  clientes SET nombre = '" + cliente.getNombre()
                        + "', direccion = '" + cliente.getDireccion()
                        + "', localidad = '" + cliente.getLocalidad()
                        + "', telefono = '" + cliente.getTelefono()
                        + "', email = '" + cliente.getEmail()
                        + "', CP = '" + cliente.getCp()
                        + "' WHERE id = '" + cliente.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que elimina el Cliente con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM clientes WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
        }
    }
}

