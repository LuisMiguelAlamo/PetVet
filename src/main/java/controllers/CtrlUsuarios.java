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
import models.Acceso;
import querys.QuerysAcceso;
import views.UsuariosPanel;
import views.FrmPrincipal;
import views.RegistroUsuariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlUsuarios implements MouseListener, DocumentListener {

    FrmPrincipal frm;
    UsuariosPanel panel;
    Acceso usuarios;
    String titulos[] = {"Id", "Rol", "Email", "Password"};
    String info[][];
    boolean isSelected;
    boolean condicion;
    int idMascota;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlUsuarios(FrmPrincipal frm, UsuariosPanel u, boolean condicion) {
        this.frm = frm;
        this.panel = u;
        this.condicion = condicion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, u);

        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaUsuarios().addMouseListener(this);

        if (condicion) {
            this.panel.getBtnEditar().setVisible(false);
            this.panel.getBtnEliminar().setVisible(false);
        }

        isSelected = false;
        actualizarTabla();
    }


    //Método que actualiza la información de la tabla
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaUsuarios().setModel(new DefaultTableModel(info, titulos));
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
    private String[][] obtieneMatriz() {

        ArrayList<Acceso> miLista = QuerysAcceso.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            String rolTxt = "";
            int rolNum;
            informacion[x][0] = miLista.get(x).getId() + "";
            rolNum = miLista.get(x).getRol();
            if (rolNum == 1) {
                rolTxt = "Veterinario";
            }else{
                rolTxt = "Admin";
            }
            informacion[x][1] = rolTxt;
            informacion[x][2] = miLista.get(x).getEmail()+ "";
            informacion[x][3] = miLista.get(x).getPassword()+ "";
        }
        return informacion;
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
    private String[][] obtieneFiltro() {

        ArrayList<Acceso> miLista = QuerysAcceso.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            String rolTxt = "";
            int rolNum;
            informacion[x][0] = miLista.get(x).getId() + "";
            rolNum = miLista.get(x).getRol();
            if (rolNum == 1) {
                rolTxt = "Veterinario";
            }else{
                rolTxt = "Admin";
            }
            informacion[x][1] = rolTxt;
            informacion[x][2] = miLista.get(x).getEmail()+ "";
            informacion[x][3] = miLista.get(x).getPassword()+ "";
        }
        return informacion;
    }
    
    //Método que actualiza la tabla cada 3 ms filtrando los datos que son mostrados
    private void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                        info = obtieneFiltro();
                        panel.getTablaUsuarios().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }

    //Método que devuelve el usuario de la fila seleccionada en la tabla
    private Acceso getUsuario() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaUsuarios().getValueAt(this.panel.getTablaUsuarios().getSelectedRow(), 0)));
        usuarios = QuerysAcceso.consultaGeneral(id);
        return usuarios;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            CtrlPrincipal.isNew = true;
            CtrlPrincipal.rol = null;
            RegistroUsuariosPanel registro = new RegistroUsuariosPanel();
            CtrlRegUsuarios rc = new CtrlRegUsuarios(this.frm, registro, false);
        }

        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.usuario = getUsuario();
                
                RegistroUsuariosPanel registro = new RegistroUsuariosPanel();
                CtrlRegUsuarios rc = new CtrlRegUsuarios(this.frm, registro, true);
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un usuario");
            }
        }

        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaUsuarios().getValueAt(this.panel.getTablaUsuarios().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysAcceso.eliminar(id);
                this.actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un usuario");
            }
        }

        if (e.getSource().equals(this.panel.getTablaUsuarios())) {
            //Si está seleccionada una fila se carga el usuario correspondiente
            isSelected = true;
            this.usuarios = getUsuario();
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
