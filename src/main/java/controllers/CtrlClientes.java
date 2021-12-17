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
import javax.swing.table.DefaultTableModel;
import models.Clientes;
import querys.QuerysClientes;
import views.ClientePanel;
import views.FrmPrincipal;

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

    public CtrlClientes(FrmPrincipal frm, ClientePanel p) {
        this.frm = frm;
        p.setSize(630, 440);
        p.setLocation(0, 0);
        this.panel = p;
        this.frm.getContentPanel().removeAll();
        this.frm.getContentPanel().add(p, BorderLayout.CENTER);
        this.frm.getContentPanel().revalidate();
        this.frm.getContentPanel().repaint();
        
        
        info = obtieneMatriz();
        panel.getTablaClientes().setModel(new DefaultTableModel(info, titulos));
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
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
