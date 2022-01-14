package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    double total = rs.getDouble("total");
                    double IGIC = rs.getDouble("IGIC");
                    double totalConIGIC = rs.getDouble("totalConIGIC");
                    int codConsulta = rs.getInt("codigoConsulta");
                    factura = new Facturas(id, total, IGIC, totalConIGIC, codConsulta);
                    
                    lista.add(factura);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }
    
    
    public static Facturas consultaGeneral(int id) {
        Facturas factura = null;
        try {
            if (AbrirConexion.abrirConect()) {
                System.out.println("metodo consultaGeneral");
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM facturas");
                while (rs.next()) {
                    double total = rs.getDouble("total");
                    double IGIC = rs.getDouble("IGIC");
                    double totalConIGIC = rs.getDouble("totalConIGIC");
                    int codConsulta = rs.getInt("codigoConsulta");
                    factura = new Facturas(id, total, IGIC, totalConIGIC, codConsulta);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return factura;
    }

    public static ArrayList<Facturas> consultaFiltro(String campo) {
        Facturas factura;
        ArrayList<Facturas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM facturas WHERE total LIKE '%" + campo + "%'"
                        + " OR codigoConsulta LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    double total = rs.getDouble("total");
                    double IGIC = rs.getDouble("IGIC");
                    double totalConIGIC = rs.getDouble("totalConIGIC");
                    int codConsulta = rs.getInt("codigoConsulta");
                    factura = new Facturas(id, total, IGIC, totalConIGIC, codConsulta);
                    
                    lista.add(factura);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    public static void crear(Facturas factura) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO facturas VALUES (null,'"
                        + factura.getTotal()+ "','"
                        + factura.getIGIC()+ "','"
                        + factura.getTotalConIGIC()+ "','"
                        + factura.getCodConsulta()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void actualizar(Facturas factura) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  facturas SET total = '" + factura.getTotal()
                        + "', IGIC = '" + factura.getIGIC()
                        + "', totalConIGIC = '" + factura.getTotalConIGIC()
                        + "' WHERE id = '" + factura.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM facturas WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
        }
    }
}
