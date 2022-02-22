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
import models.Clientes;
import models.Facturas;
import querys.QuerysClientes;
import querys.QuerysFacturas;
import views.FacturasPanel;
import views.FrmPrincipal;
import views.RegistroFacturasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlFacturas implements MouseListener{
    
    FrmPrincipal frm;
    FacturasPanel panel;
    Facturas factura;
    String titulos[] = {"No. Factura", "Total", "IGIC", "total con IGIC", "Cliente"};
    String info[][];
    boolean isSelected;
    ArrayList<Facturas> miLista;
    ArrayList<Clientes> cliList;

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

        miLista = QuerysFacturas.consultaGeneral();
        cliList = QuerysClientes.consultaGeneral();

        String informacion[][] = new String[miLista.size()][5];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getTotal()+ "";
            informacion[x][2] = miLista.get(x).getIGIC()+ "";
            informacion[x][3] = miLista.get(x).getTotalConIGIC()+ "";
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][4] = cli;
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        miLista = QuerysFacturas.consultaFiltro(this.panel.getCampoBuscar().getText());
        cliList = QuerysClientes.consultaGeneral();
        
        String informacion[][] = new String[miLista.size()][5];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getTotal()+ "";
            informacion[x][2] = miLista.get(x).getIGIC()+ "";
            informacion[x][3] = miLista.get(x).getTotalConIGIC()+ "";
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][4] = cli;
        }

        return informacion;
    }
    
    private String leerClientes(int cc) {
        String nombre = "";
        for (Clientes c : cliList) {
            if (c.getId() == cc) {
                nombre = c.getNombre();
            }
        }
        return nombre;
    }
    
    private Facturas setFactura() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 0)));
        factura = QuerysFacturas.consultaGeneral(id);
        return factura;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            CtrlPrincipal.isNew = true;
            RegistroFacturasPanel registro = new RegistroFacturasPanel();                        
            CtrlRegFacturas rc = new CtrlRegFacturas(frm, registro, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.factura = setFactura();
                RegistroFacturasPanel registro = new RegistroFacturasPanel();  
                CtrlRegFacturas rc = new CtrlRegFacturas(frm, registro, true);
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
           this.factura = setFactura();
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
