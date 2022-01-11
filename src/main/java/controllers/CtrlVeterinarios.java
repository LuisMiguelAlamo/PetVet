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
import models.Veterinarios;
import querys.QuerysVeterinarios;
import views.VeterinariosPanel;
import views.FrmPrincipal;
//import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlVeterinarios implements MouseListener{
    
    FrmPrincipal frm;
    VeterinariosPanel panel;
    Veterinarios veterinario;
    String titulos[] = {"Id", "Nombre", "Direccion", "Teléfono"};
    String info[][];
    boolean isSelected;

    public CtrlVeterinarios(FrmPrincipal frm, VeterinariosPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaVeterinarios().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaVeterinarios().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        ArrayList<Veterinarios> miLista = QuerysVeterinarios.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion()+ "";
            informacion[x][3] = miLista.get(x).getTelefono()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Veterinarios> miLista = QuerysVeterinarios.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion()+ "";
            informacion[x][3] = miLista.get(x).getTelefono()+ "";
        }

        return informacion;
    }
    
    private Veterinarios llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 0)));
        String nombre = String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 1));
        String direccion = String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 2));
        String telefono = String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 3));
        

        veterinario = new Veterinarios(id, nombre, direccion, telefono);
        return veterinario;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
//            RegistroClientePanel registro = new RegistroClientePanel();                        
//            CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.veterinario, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
//                RegistroClientePanel registro = new RegistroClientePanel();
//                CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.veterinario, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaVeterinarios().getValueAt(this.panel.getTablaVeterinarios().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysVeterinarios.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaVeterinarios())) {
           isSelected = true;
           this.veterinario = llenaCampos();
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
