/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import models.Clientes;
import models.Mascotas;
import querys.QuerysClientes;
import querys.QuerysMascotas;
import views.FrmPrincipal;
import views.MascotasPanel;
import views.RegistroCitasPanel;
import views.RegistroConsultasPanel;
import views.RegistroMascotasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMascotas implements MouseListener, DocumentListener {

    FrmPrincipal frm;
    MascotasPanel panel;
    Mascotas mascota;
    String titulos[] = {"Id", "Nombre", "Especie", "Color", "Sexo", "Enfermedades", "Anotaciones", "Vacunas", "Chip", "Dueño"};
    String info[][];
    boolean isSelected;
    boolean condicion;
    ArrayList<Mascotas> miLista;
    ArrayList<Clientes> cliList;
    int idVeterninario;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlMascotas(FrmPrincipal frm, MascotasPanel p, boolean condicion) {
        this.frm = frm;
        this.panel = p;
        this.condicion = condicion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaMascotas().addMouseListener(this);
        
        //Si se cumple la condición se muestra solo el botón de seleccionar
        if (condicion) {
            this.panel.getBtnEditar().setVisible(false);
            this.panel.getBtnEliminar().setVisible(false);
            this.panel.getNuevoLabel().setText("Seleccionar");
        }
        
        isSelected = false;
        actualizarTabla();
    }
    
    //Método que actualiza la información de la tabla
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaMascotas().setModel(new DefaultTableModel(info, titulos));
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
    private String[][] obtieneMatriz() {

         miLista = QuerysMascotas.consultaGeneral();
         cliList = QuerysClientes.consultaGeneral();

        String informacion[][] = new String[miLista.size()][10];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getEspecie() + "";
            informacion[x][3] = miLista.get(x).getColor() + "";
            informacion[x][4] = miLista.get(x).getSexo() + "";
            informacion[x][5] = miLista.get(x).getEnfermedades() + "";
            informacion[x][6] = miLista.get(x).getAnotaciones() + "";
            informacion[x][7] = miLista.get(x).getVacunas() + "";
            int chip = miLista.get(x).getChip();
            String siNo = "";
            if (chip == 1) {
                siNo = "Sí";
            }else{
                siNo = "No";
            }
            informacion[x][8] = siNo;
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][9] = cli;
        }
        return informacion;
    }

    //Método que devuelve el nombre del cliente para no tener que llenar la tabla
    //con el número del id 
    private String leerClientes(int cc) {
        String nombre = "";
        for (Clientes c : cliList) {
            if (c.getId() == cc) {
                nombre = c.getNombre();
            }
        }
        return nombre;
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
    private String[][] obtieneFiltro() {

        miLista = QuerysMascotas.consultaFiltro(this.panel.getCampoBuscar().getText());
        cliList = QuerysClientes.consultaGeneral();

        String informacion[][] = new String[miLista.size()][10];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getEspecie() + "";
            informacion[x][3] = miLista.get(x).getColor() + "";
            informacion[x][4] = miLista.get(x).getSexo() + "";
            informacion[x][5] = miLista.get(x).getEnfermedades() + "";
            informacion[x][6] = miLista.get(x).getAnotaciones() + "";
            informacion[x][7] = miLista.get(x).getVacunas() + "";
            int chip = miLista.get(x).getChip();
            String siNo = "";
            if (chip == 1) {
                siNo = "Sí";
            }else{
                siNo = "No";
            }
            informacion[x][8] = siNo;
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][9] = cli;
        }

        return informacion;
    }
    
    //Método que actualiza la tabla cada 3 ms filtrando los datos que son mostrados
    public void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                        info = obtieneFiltro();
                        panel.getTablaMascotas().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }

    //Método que devuelve la mascota de la fila seleccionada en la tabla
    private Mascotas getMascota() {        
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 0)));
        mascota = QuerysMascotas.consultaGeneral(id);
        return mascota;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            //Si se cumple la condición se activa el botón Seleccionar
            if (condicion) {
                if (isSelected) {
                    CtrlPrincipal.mascota = getMascota();
                    //Se comprueba que Panel mostrar en dependencia de la variable global de elección
                    switch (CtrlPrincipal.eleccion) {
                        case 1:
                            RegistroCitasPanel rc = new RegistroCitasPanel();
                            CtrlRegCitas cit = new CtrlRegCitas(frm, rc, true);
                            break;

                        case 2:
                            RegistroConsultasPanel rcon = new RegistroConsultasPanel();
                            CtrlRegConsultas con = new CtrlRegConsultas(frm, rcon, true);
                            break;
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una mascota");
                }
            } else {//Como no se cumple simplemente abre su panel de registro
                CtrlPrincipal.isNew = true;
                RegistroMascotasPanel mp = new RegistroMascotasPanel();
                CtrlRegMascotas mas = new CtrlRegMascotas(frm, mp, false);
            }
        }

        if (e.getSource().equals(this.panel.getBtnEditar())) {
            //Se comprueba que una fila de la tabla esté seleccionada
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.mascota = getMascota();
                this.mascota = getMascota();
                CtrlPrincipal.cliente = QuerysClientes.consultaGeneral(this.mascota.getCodCliente());
                RegistroMascotasPanel registro = new RegistroMascotasPanel();
                CtrlRegMascotas rc = new CtrlRegMascotas(frm, registro, true);
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una mascota");
            }
        }

        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 0)));
                QuerysMascotas.eliminar(id);
                this.actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una mascota");
            }
        }

        if (e.getSource().equals(this.panel.getTablaMascotas())) {
            //Si está seleccionada una fila se carga la mascota correspondiente
            isSelected = true;
            this.mascota = getMascota();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.activarTimer();//Se llama al método que actualiza la tabla cada 3 ms
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.activarTimer();//Se llama al método que actualiza la tabla cada 3 ms
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.activarTimer();//Se llama al método que actualiza la tabla cada 3 ms
    }

}
