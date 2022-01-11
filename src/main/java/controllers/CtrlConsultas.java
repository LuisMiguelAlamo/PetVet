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
import models.Consultas;
import querys.QuerysClientes;
import querys.QuerysConsultas;
import views.ConsultasPanel;
import views.FrmPrincipal;
//import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlConsultas implements MouseListener{
    
    FrmPrincipal frm;
    ConsultasPanel panel;
    Consultas consulta;
    String titulos[] = {"Id", "Fecha", "Diagnostico", "Tratamiento", "Medicamento", "Veterinario", "Mascota"};
    String info[][];
    boolean isSelected;
    ArrayList<Consultas> miLista;

    public CtrlConsultas(FrmPrincipal frm, ConsultasPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaConsultas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaConsultas().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        miLista = QuerysConsultas.consultaGeneral();

        String informacion[][] = new String[miLista.size()][7];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getDiagnostico()+ "";
            informacion[x][3] = miLista.get(x).getTratamiento()+ "";
            informacion[x][4] = miLista.get(x).getCodMedicamento()+ "";
            informacion[x][5] = miLista.get(x).getCodVeterinario()+ "";
            informacion[x][6] = miLista.get(x).getCodMascota()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        miLista = QuerysConsultas.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][7];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getDiagnostico()+ "";
            informacion[x][3] = miLista.get(x).getTratamiento()+ "";
            informacion[x][4] = miLista.get(x).getCodMedicamento()+ "";
            informacion[x][5] = miLista.get(x).getCodVeterinario()+ "";
            informacion[x][6] = miLista.get(x).getCodMascota()+ "";
        }

        return informacion;
    }
    
    private Consultas llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 0)));
        Timestamp fecha = Timestamp.valueOf(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 1)));
        String diagnostico = String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 2));
        String tratamiento = String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 3));
        int codMedicamento = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 4)));
        int codVeterinario = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 5)));
        int codMascota = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 6)));

        consulta = new Consultas(id, fecha, diagnostico, tratamiento, codMedicamento, codVeterinario, codMascota);
        return consulta;
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
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysClientes.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaConsultas())) {
            isSelected = true;
           this.consulta = llenaCampos();
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
