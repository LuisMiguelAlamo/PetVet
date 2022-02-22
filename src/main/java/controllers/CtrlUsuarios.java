/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Acceso;
import querys.QuerysAcceso;
import querys.QuerysVeterinarios;
import views.UsuariosPanel;
import views.FrmPrincipal;
import views.RegistroUsuariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlUsuarios implements MouseListener {

    FrmPrincipal frm;
    UsuariosPanel panel;
    Acceso usuarios;
    String titulos[] = {"Id", "Rol", "Usuario", "Password"};
    String info[][];
    boolean isSelected;
    boolean condicion;
    int idMascota;

    public CtrlUsuarios(FrmPrincipal frm, UsuariosPanel u, boolean condicion) {
        this.frm = frm;
        this.panel = u;
        this.condicion = condicion;

        CtrlPrincipal.showContentPanel(frm, u);

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


    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaUsuarios().setModel(new DefaultTableModel(info, titulos));
    }

    private String[][] obtieneMatriz() {

        ArrayList<Acceso> miLista = QuerysAcceso.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getRol()+ "";
            informacion[x][2] = miLista.get(x).getUsuario()+ "";
            informacion[x][3] = miLista.get(x).getPassword()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Acceso> miLista = QuerysAcceso.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getRol()+ "";
            informacion[x][2] = miLista.get(x).getUsuario()+ "";
            informacion[x][3] = miLista.get(x).getPassword()+ "";
        }

        return informacion;
    }

    private Acceso getUsuario() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaUsuarios().getValueAt(this.panel.getTablaUsuarios().getSelectedRow(), 0)));
        usuarios = QuerysAcceso.consultaGeneral(id);
        return usuarios;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            RegistroUsuariosPanel registro = new RegistroUsuariosPanel();
//                CtrlRegUsuarios rc = new CtrlRegUsuarios(this.frm, registro, this.usuarios, true);            
        }

        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                RegistroUsuariosPanel registro = new RegistroUsuariosPanel();
//                CtrlRegUsuarios rc = new CtrlRegUsuarios(this.frm, registro, this.usuarios, true);
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un usuario");
            }
        }

        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaUsuarios().getValueAt(this.panel.getTablaUsuarios().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysVeterinarios.eliminar(id);
                this.actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un usuario");
            }
        }

        if (e.getSource().equals(this.panel.getTablaUsuarios())) {
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

}
