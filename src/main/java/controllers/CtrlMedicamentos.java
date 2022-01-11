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
import models.Medicamentos;
import querys.QuerysMedicamentos;
import views.MedicamentosPanel;
import views.FrmPrincipal;
//import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMedicamentos implements MouseListener{
    
    FrmPrincipal frm;
    MedicamentosPanel panel;
    Medicamentos medicamento;
    String titulos[] = {"Id", "Nombre", "Precio", "CodProveedor"};
    String info[][];
    boolean isSelected;

    public CtrlMedicamentos(FrmPrincipal frm, MedicamentosPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaMedicamentos().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaMedicamentos().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        ArrayList<Medicamentos> miLista = QuerysMedicamentos.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getPrecio()+ "";
            informacion[x][3] = miLista.get(x).getCodProveedor()+ "";
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        ArrayList<Medicamentos> miLista = QuerysMedicamentos.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getPrecio()+ "";
            informacion[x][3] = miLista.get(x).getCodProveedor()+ "";
        }

        return informacion;
    }
    
    private Medicamentos llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 0)));
        String nombre = String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 1));
        double precio = Double.parseDouble(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 2)));
        int codProveedor = Integer.parseInt(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 3)));

        medicamento = new Medicamentos(id, nombre, precio, codProveedor);
        return medicamento;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
//            RegistroClientePanel registro = new RegistroClientePanel();                        
//            CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.medicamento, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
//                RegistroClientePanel registro = new RegistroClientePanel();
//                CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.medicamento, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysMedicamentos.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaMedicamentos())) {
           isSelected = true;
           this.medicamento = llenaCampos();
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
