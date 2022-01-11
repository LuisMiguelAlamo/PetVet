/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Proveedores;
import querys.QuerysProveedores;
import views.ProveedoresPanel;
import views.FrmPrincipal;
//import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlProveedores implements MouseListener{
    
    FrmPrincipal frm;
    ProveedoresPanel panel;
    Proveedores proveedor;
    String titulos[] = {"Id", "Nombre", "Dirección", "Localidad", "Teléfono", "Email", "CP", "Antiguedad"};
    String info[][];
    boolean isSelected;

    public CtrlProveedores(FrmPrincipal frm, ProveedoresPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaProveedores().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaProveedores().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        ArrayList<Proveedores> miLista = QuerysProveedores.consultaGeneral();

        String informacion[][] = new String[miLista.size()][8];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion()+ "";
            informacion[x][3] = miLista.get(x).getLocalidad()+ "";
            informacion[x][4] = miLista.get(x).getTelefono()+ "";
            informacion[x][5] = miLista.get(x).getEmail()+ "";
            informacion[x][6] = miLista.get(x).getCP()+ "";
            informacion[x][7] = miLista.get(x).getFecha()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Proveedores> miLista = QuerysProveedores.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][8];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getDireccion()+ "";
            informacion[x][3] = miLista.get(x).getLocalidad()+ "";
            informacion[x][4] = miLista.get(x).getTelefono()+ "";
            informacion[x][5] = miLista.get(x).getEmail()+ "";
            informacion[x][6] = miLista.get(x).getCP()+ "";
            informacion[x][7] = miLista.get(x).getFecha()+ "";
        }

        return informacion;
    }
    
    private Proveedores llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 0)));
        String nombre = String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 1));
        String direccion = String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 2));
        String localidad = String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 3));
        String telefono = String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 4));
        String email = String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 5));
        int CP = Integer.parseInt(String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 6)));
        Date fecha = Date.valueOf(String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 6)));

        proveedor = new Proveedores(id, nombre, direccion, localidad, telefono, email, CP, fecha);
        return proveedor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
//            RegistroClientePanel registro = new RegistroClientePanel();                        
//            CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.proveedor, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
//                RegistroClientePanel registro = new RegistroClientePanel();
//                CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.proveedor, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysProveedores.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaProveedores())) {
            isSelected = true;
           this.proveedor = llenaCampos();
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
