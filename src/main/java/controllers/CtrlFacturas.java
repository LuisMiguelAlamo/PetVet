/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Citas;
import models.Facturas;
import querys.QuerysCitas;
import querys.QuerysClientes;
import querys.QuerysFacturas;
import views.CitasPanel;
import views.FacturasPanel;
import views.FrmPrincipal;
//import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlFacturas implements MouseListener{
    
    FrmPrincipal frm;
    FacturasPanel panel;
    Facturas factura;
    String titulos[] = {"Id", "Total", "IGIC", "total con IGIC", "codConsulta"};
    String info[][];
    boolean isSelected;

    public CtrlFacturas(FrmPrincipal frm, FacturasPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaFacturas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaFacturas().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        ArrayList<Facturas> miLista = QuerysFacturas.consultaGeneral();

        String informacion[][] = new String[miLista.size()][5];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getTotal()+ "";
            informacion[x][2] = miLista.get(x).getIGIC()+ "";
            informacion[x][3] = miLista.get(x).getTotalConIGIC()+ "";
            informacion[x][4] = miLista.get(x).getCodConsulta()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Facturas> miLista = QuerysFacturas.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][5];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getTotal()+ "";
            informacion[x][2] = miLista.get(x).getIGIC()+ "";
            informacion[x][3] = miLista.get(x).getTotalConIGIC()+ "";
            informacion[x][4] = miLista.get(x).getCodConsulta()+ "";
        }

        return informacion;
    }
    
    private Facturas llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 0)));
        double total = Double.parseDouble(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 1)));
        double IGIC = Double.parseDouble(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 1)));
        double totalConIGIC = Double.parseDouble(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 1)));
        int codMascota = Integer.parseInt(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 0)));

        factura = new Facturas(id, total, IGIC, totalConIGIC, codMascota);
        return factura;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
//            RegistroClientePanel registro = new RegistroClientePanel();                        
//            CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.cliente, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
//                RegistroClientePanel registro = new RegistroClientePanel();
//                CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.cliente, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 0)));
                QuerysFacturas.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaFacturas())) {
            isSelected = true;
           this.factura = llenaCampos();
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
