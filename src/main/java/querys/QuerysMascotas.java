package querys;

import conexion.AbrirConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Clientes;
import models.Mascotas;

/**
 *
 * @author Luis Miguel
 */
public class QuerysMascotas {

    static ResultSet rs;
    static Statement smnt;
    
    //Método que devuelve una lista de todas las Mascotas
    public static ArrayList<Mascotas> consultaGeneral() {
        Mascotas mascota;
        ArrayList<Mascotas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM mascotas JOIN clientes ON "
                        + "mascotas.codigoCliente = clientes.id");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String especie = rs.getString("especie");
                    String color = rs.getString("color");
                    String sexo = rs.getString("sexo");
                    String enfermedades = rs.getString("enfermedades");
                    String anotaciones = rs.getString("anotaciones");
                    String vacunas = rs.getString("vacunas");
                    int chip = rs.getInt("chip");
                    int codCliente = rs.getInt("codigoCliente");
                    mascota = new Mascotas(id, nombre, especie, color, sexo, enfermedades, anotaciones, vacunas, chip, codCliente);
                    lista.add(mascota);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;

    }
    
    //Método que devuelve una Mascota por su id
    public static Mascotas consultaGeneral(int id) {
        Mascotas mascota = null;
        
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM mascotas WHERE  id = "+ id);
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String especie = rs.getString("especie");
                    String color = rs.getString("color");
                    String sexo = rs.getString("sexo");
                    String enfermedades = rs.getString("enfermedades");
                    String anotaciones = rs.getString("anotaciones");
                    String vacunas = rs.getString("vacunas");
                    int chip = rs.getInt("chip");
                    int codCliente = rs.getInt("codigoCliente");
                    mascota = new Mascotas(id, nombre, especie, color, sexo, enfermedades, anotaciones, vacunas, chip, codCliente);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return mascota;
    }
    
    //Método que devuelve un cliente con el id pasado como argumento
    public static Clientes leerCliente(int id) {
        Clientes cliente = null;        
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM clientes WHERE  id = "+ id);
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String localidad = rs.getString("localidad");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");
                    int cp = rs.getInt("CP");
                    cliente = new Clientes(id, nombre, direccion, localidad, telefono, email, cp);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return cliente;

    }
    
    
    //Método que devuelve una lista de las Mascotas que contengan algún dato 
    //igual al pasado como argumento
    public static ArrayList<Mascotas> consultaFiltro(String campo) {
        Mascotas mascota;
        ArrayList<Mascotas> lista = new ArrayList<>();
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                rs = smnt.executeQuery("SELECT * FROM mascotas WHERE id LIKE '%" + campo + "%'"
                        + " OR nombre LIKE '%" + campo + "%'"
                        + " OR especie LIKE '%" + campo + "%'"
                        + " OR color LIKE '%" + campo + "%'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String especie = rs.getString("especie");
                    String color = rs.getString("color");
                    String sexo = rs.getString("sexo");
                    String enfermedades = rs.getString("enfermedades");
                    String anotaciones = rs.getString("anotaciones");
                    String vacunas = rs.getString("vacunas");
                    int chip = rs.getInt("chip");
                    int codCliente = rs.getInt("codigoCliente");
                    mascota = new Mascotas(id, nombre, especie, color, sexo, enfermedades, anotaciones, vacunas, chip, codCliente);
                    lista.add(mascota);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de acceso a la base de datos");
        }
        return lista;
    }

    //Método que crea una nueva Mascota
    public static void crear(Mascotas mascota) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();

                smnt.executeUpdate("INSERT INTO mascotas VALUES (null,'" + mascota.getNombre() + "','" 
                        + mascota.getEspecie()+ "','" 
                        + mascota.getColor()+ "','" 
                        + mascota.getSexo()+ "','"
                        + mascota.getEnfermedades()+ "','" 
                        + mascota.getAnotaciones()+ "','"
                        + mascota.getVacunas()+ "','"
                        + mascota.getChip()+ "','"
                        + mascota.getCodCliente()+ "');");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear la mascota");
        }
    }

    //Método que actualiza la Mascota pasada como argumento
    public static void actualizar(Mascotas mascota) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("UPDATE  mascotas SET nombre = '" + mascota.getNombre()
                        + "', especie = '" + mascota.getEspecie()
                        + "', color = '" + mascota.getColor()
                        + "', sexo  = '" + mascota.getSexo()
                        + "', enfermedades = '" + mascota.getEnfermedades()
                        + "', anotaciones = '" + mascota.getAnotaciones()
                        + "', vacunas = '" + mascota.getVacunas()
                        + "', chip = '" + mascota.getChip()
                        + "', codigoCliente = '" + mascota.getCodCliente()
                        + "' WHERE id = '" + mascota.getId() + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la consulta");
        }
    }

    //Método que elimina la Mascota con el id pasado como argumento
    public static void eliminar(int id) {
        try {
            if (AbrirConexion.abrirConect()) {
                smnt = AbrirConexion.getCone().createStatement();
                smnt.executeUpdate("DELETE FROM mascotas WHERE id = '" + id + "';");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar las mascota");
        }
    }
}

