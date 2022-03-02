package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Facturas;

/**
 *
 * @author Luis Miguel
 */
public class QuerysFacturas {

    static ResultSet rs;
    static Statement smnt;

    //Método que devuelve una lista de todas las Facturas
    public static ArrayList<Facturas> consultaGeneral() {
        Facturas factura;
        ArrayList<Facturas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM facturas");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date fecha = rs.getDate("fecha");
                    double total = rs.getDouble("total");
                    double IGIC = rs.getDouble("IGIC");
                    double totalConIGIC = rs.getDouble("totalConIGIC");
                    int codCliente = rs.getInt("codigoCliente");
                    factura = new Facturas(id, fecha,total, IGIC, totalConIGIC, codCliente);
                    
                    lista.add(factura);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    //Método que devuelve una Factura por su id
    public static Facturas consultaGeneral(int id) {
        Facturas factura = null;
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM facturas WHERE id = " + id);
                while (rs.next()) {
                    Date fecha = rs.getDate("fecha");
                    double total = rs.getDouble("total");
                    double IGIC = rs.getDouble("IGIC");
                    double totalConIGIC = rs.getDouble("totalConIGIC");
                    int codCliente = rs.getInt("codigoCliente");
                    factura = new Facturas(id, fecha,total, IGIC, totalConIGIC, codCliente);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener la facturas");
        }
        return factura;
    }

    //Método que devuelve una lista de las Facturas que contengan algún dato 
    //igual al pasado como argumento
    public static ArrayList<Facturas> consultaFiltro(String campo) {
        Facturas factura;
        ArrayList<Facturas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM facturas WHERE total LIKE '%" + campo + "%'"
                        + " OR fecha LIKE '%" + campo + "%'"
                        + " OR IGIC LIKE '%" + campo + "%'"
                        + " OR totalConIGIC LIKE '%" + campo + "%'"
                        + " OR codigoCliente LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date fecha = rs.getDate("fecha");
                    double total = rs.getDouble("total");
                    double IGIC = rs.getDouble("IGIC");
                    double totalConIGIC = rs.getDouble("totalConIGIC");
                    int codCliente = rs.getInt("codigoCliente");
                    factura = new Facturas(id, fecha,total, IGIC, totalConIGIC, codCliente);
                    
                    lista.add(factura);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al filtrar la factura");
        }
        return lista;
    }

    //Método que crea una nueva Factura
    public static void crear(Facturas factura) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                System.out.println(factura.getFecha());
                smnt.executeUpdate("INSERT INTO facturas VALUES (null,'"
                        + factura.getFecha()+ "','"
                        + factura.getTotal()+ "','"
                        + factura.getIGIC()+ "','"
                        + factura.getTotalConIGIC()+ "','"
                        + factura.getCodCliente()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear la factura");
        }
    }

    //Método que actualiza la Factura pasada como argumento
    public static void actualizar(Facturas factura) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  facturas SET fecha = '" + factura.getFecha()
                        + "', total = '" + factura.getTotal()
                        + "', IGIC = '" + factura.getIGIC()
                        + "', totalConIGIC = '" + factura.getTotalConIGIC()
                        + "', codigoCliente = '" + factura.getCodCliente()
                        + "' WHERE id = '" + factura.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la factura");
        }
    }

    //Método que elimina la Factura con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM facturas WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la factura");
        }
    }
}
