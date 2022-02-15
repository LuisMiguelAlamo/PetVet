package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JOptionPane;
import models.Proveedores;

/**
 *
 * @author Luis Miguel
 */
public class QuerysProveedores {

    static ResultSet rs;
    static Statement smnt;
    
    
    public static ArrayList<Proveedores> consultaGeneral() {
        Proveedores proveedor;
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM proveedores");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("CP");
                    Date alta = rs.getDate("alta");
                    proveedor = new Proveedores(id, nombre, direccion, localidad, telefono, email, cp, alta);
                    lista.add(proveedor);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;

    }
    
    public static Proveedores consultaGeneral(int id) {
        Proveedores proveedor = null;
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM proveedores WHERE id = "+ id);
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("CP");
                    Date alta = rs.getDate("alta");
                    proveedor = new Proveedores(id, nombre, direccion, localidad, telefono, email, cp, alta);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return proveedor;

    }

    public static ArrayList<Proveedores> consultaFiltro(String campo) {
        Proveedores proveedor;
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM proveedores WHERE codCliente LIKE '%" + campo + "%'"
                        + " OR nombre LIKE '%" + campo + "%'"
                        + " OR telefono LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("CP");
                    Date alta = rs.getDate("alta");
                    proveedor = new Proveedores(id, nombre, direccion, localidad, telefono, email, cp, alta);
                    lista.add(proveedor);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    public static void crear(Proveedores proveedor) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                System.out.println(proveedor.getNombre() + proveedor.getDireccion()+
                proveedor.getLocalidad());
                smnt.executeUpdate("INSERT INTO proveedores VALUES (null,'" + proveedor.getNombre() + "','" 
                        + proveedor.getDireccion() + "','" 
                        + proveedor.getLocalidad() + "','"
                        + proveedor.getTelefono() + "','" 
                        + proveedor.getEmail()+ "','" 
                        + proveedor.getCP()+ "','" 
                        + proveedor.getFecha()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void actualizar(Proveedores proveedor) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  proveedores SET nombre = '" + proveedor.getNombre()
                        + "', direccion = '" + proveedor.getDireccion()
                        + "', localidad = '" + proveedor.getLocalidad()
                        + "', telefono = '" + proveedor.getTelefono()
                        + "', email = '" + proveedor.getEmail()
                        + "', CP = '" + proveedor.getCP()
                        + "', alta = '" + proveedor.getFecha()
                        + "' WHERE id = '" + proveedor.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM proveedores WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
        }
    }
}

