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
import models.Veterinarios;
import querys.QuerysVeterinarios;
import views.VeterinariosPanel;
import views.FrmPrincipal;
import views.RegistroCitasPanel;
import views.RegistroConsultasPanel;
import views.RegistroUsuariosPanel;
import views.RegistroVeterinariosPanel;
//import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlVeterinarios implements MouseListener, DocumentListener {

    FrmPrincipal frm;
    VeterinariosPanel panel;
    Veterinarios veterinario;
    String titulos[] = {"Id", "Nombre", "Direccion", "Teléfono"};
    String info[][];
    boolean isSelected;
    boolean condicion;
    int idMascota;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlVeterinarios(FrmPrincipal frm, VeterinariosPanel p, boolean condicion) {
        this.frm = frm;
        this.panel = p;
        this.condicion = condicion;

        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, p);

        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaVeterinarios().addMouseListener(this);
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
        this.panel.getTablaVeterinarios().setModel(new DefaultTableModel(info, titulos));
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
    private String[][] obtieneMatriz() {

        ArrayList<Veterinarios> miLista = QuerysVeterinarios.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion() + "";
            informacion[x][3] = miLista.get(x).getTelefono() + "";
        }
        return informacion;
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
    private String[][] obtieneFiltro() {

        ArrayList<Veterinarios> miLista = QuerysVeterinarios.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion() + "";
            informacion[x][3] = miLista.get(x).getTelefono() + "";
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
                        panel.getTablaVeterinarios().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }

    //Método que devuelve el veterinario de la fila seleccionada en la tabla
    private Veterinarios getVeterinario() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 0)));
        veterinario = QuerysVeterinarios.consultaGeneral(id);
        return veterinario;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            if (condicion) {
                if (isSelected) {
                    CtrlPrincipal.veterinario = getVeterinario();
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
                            
                        case 5:
                            RegistroUsuariosPanel regUs = new RegistroUsuariosPanel();
                            CtrlRegUsuarios usu = new CtrlRegUsuarios(frm, regUs, true);
                            break;
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un veterinario");
                }
            } else {
                RegistroVeterinariosPanel registro = new RegistroVeterinariosPanel();
                CtrlRegVeterinarios rc = new CtrlRegVeterinarios(this.frm, registro, this.veterinario, false);
            }
        }

        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                RegistroVeterinariosPanel registro = new RegistroVeterinariosPanel();
                CtrlRegVeterinarios rc = new CtrlRegVeterinarios(this.frm, registro, this.veterinario, true);
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }

        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysVeterinarios.eliminar(id);
                this.actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }

        if (e.getSource().equals(this.panel.getTablaVeterinarios())) {
            isSelected = true;
            this.veterinario = getVeterinario();
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
