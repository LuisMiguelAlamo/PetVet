/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Clientes;
import querys.QuerysClientes;
import views.ClientePanel;
import views.FrmPrincipal;
import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlClientes implements MouseListener{
    
    FrmPrincipal frm;
    ClientePanel panel;
    Clientes cliente;
    String titulos[] = {"Id", "Nombre", "Dirección", "Localidad", "Teléfono", "Email", "CP"};
    String info[][];
    boolean isSelected;

    public CtrlClientes(FrmPrincipal frm, ClientePanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaClientes().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaClientes().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        ArrayList<Clientes> miLista = QuerysClientes.consultaGeneral();

        String informacion[][] = new String[miLista.size()][7];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion()+ "";
            informacion[x][3] = miLista.get(x).getLocalidad()+ "";
            informacion[x][4] = miLista.get(x).getTelefono()+ "";
            informacion[x][5] = miLista.get(x).getEmail()+ "";
            informacion[x][6] = miLista.get(x).getCp()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Clientes> miLista = QuerysClientes.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][7];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion()+ "";
            informacion[x][3] = miLista.get(x).getLocalidad()+ "";
            informacion[x][4] = miLista.get(x).getTelefono()+ "";
            informacion[x][5] = miLista.get(x).getEmail()+ "";
            informacion[x][6] = miLista.get(x).getCp()+ "";
        }

        return informacion;
    }
    
    private Clientes llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 0)));
        String nombre = String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 1));
        String direccion = String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 2));
        String localidad = String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 3));
        String telefono = String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 4));
        String email = String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 5));
        int CP = Integer.parseInt(String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 6)));

        cliente = new Clientes(id, nombre, direccion, localidad, telefono, email, CP);
        return cliente;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            RegistroClientePanel registro = new RegistroClientePanel();                        
            CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.cliente, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                RegistroClientePanel registro = new RegistroClientePanel();
                CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.cliente, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysClientes.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaClientes())) {
            isSelected = true;
           this.cliente = llenaCampos();
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
