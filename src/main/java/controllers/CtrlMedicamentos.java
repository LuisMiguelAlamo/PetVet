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
import models.Proveedores;
import querys.QuerysMedicamentos;
import querys.QuerysProveedores;
import views.MedicamentosPanel;
import views.FrmPrincipal;
import views.RegistroMedicamentosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMedicamentos implements MouseListener{
    
    FrmPrincipal frm;
    MedicamentosPanel panel;
    Medicamentos medicamento;
    ArrayList<Medicamentos> miLista;
    ArrayList<Proveedores> proList;
    String titulos[] = {"Id", "Nombre", "Precio", "Proveedor"};
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

        miLista = QuerysMedicamentos.consultaGeneral();
        proList = QuerysProveedores.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getPrecio()+ "";
            int cp = miLista.get(x).getCodProveedor();
            String proveedor = leerProveedor(cp);
            informacion[x][3] = proveedor;
        }

        return informacion;
    }
    
    private String leerProveedor(int cp) {
        String nombre = "";
        for (Proveedores p : proList) {
            if (p.getId() == cp) {
                nombre = p.getNombre();
            }
        }
        return nombre;
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
        medicamento = QuerysMedicamentos.consultaGeneral(id);
        return medicamento;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            RegistroMedicamentosPanel registro = new RegistroMedicamentosPanel();                        
            CtrlRegMedicamentos rc = new CtrlRegMedicamentos(frm, registro, this.medicamento, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                RegistroMedicamentosPanel registro = new RegistroMedicamentosPanel();
                CtrlRegMedicamentos rc = new CtrlRegMedicamentos(this.frm, registro, this.medicamento, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un medicamento");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 0)));
                QuerysMedicamentos.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un medicamento");
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
