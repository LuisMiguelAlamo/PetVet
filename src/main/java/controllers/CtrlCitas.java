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
import models.Clientes;
import querys.QuerysCitas;
import querys.QuerysClientes;
import views.CitasPanel;
import views.ClientePanel;
import views.FrmPrincipal;
import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlCitas implements MouseListener{
    
    FrmPrincipal frm;
    CitasPanel panel;
    Citas cita;
    String titulos[] = {"Id", "Fecha", "CodVeterinario", "CodMascota"};
    String info[][];
    boolean isSelected;

    public CtrlCitas(FrmPrincipal frm, CitasPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaCitas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaCitas().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        ArrayList<Citas> miLista = QuerysCitas.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getCodVeterinario()+ "";
            informacion[x][3] = miLista.get(x).getCodMascota()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Citas> miLista = QuerysCitas.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][7];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getCodVeterinario()+ "";
            informacion[x][3] = miLista.get(x).getCodMascota()+ "";
        }

        return informacion;
    }
    
    private Citas llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));
        Timestamp fecha = Timestamp.valueOf(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 1)));
        int codVeterinario = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));
        int codMascota = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));

        cita = new Citas(id, fecha, codVeterinario, codMascota);
        return cita;
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
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysClientes.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaCitas())) {
            isSelected = true;
           this.cita = llenaCampos();
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
