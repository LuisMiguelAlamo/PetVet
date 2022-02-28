/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import models.Medicamentos;
import models.Proveedores;
import querys.QuerysProveedores;
import views.ProveedoresPanel;
import views.FrmPrincipal;
import views.RegistroMedicamentosPanel;
import views.RegistroProveedoresPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlProveedores implements MouseListener, DocumentListener{
    
    FrmPrincipal frm;
    ProveedoresPanel panel;
    Proveedores proveedor;
    Medicamentos medicamento;
    String titulos[] = {"Id", "Nombre", "Dirección", "Localidad", "Teléfono", "Email", "CP", "Antiguedad"};
    String info[][];
    boolean isSelected;
    boolean opcion;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    public CtrlProveedores(FrmPrincipal frm, ProveedoresPanel p, boolean condicion) {
        this.frm = frm;
        this.panel = p;
        this.opcion = condicion;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaProveedores().addMouseListener(this);
        
        if (condicion) {
            this.panel.getBtnEditar().setVisible(false);
            this.panel.getBtnEliminar().setVisible(false);
            this.panel.getNuevoLabel().setText("Seleccionar");
        }
        
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
    
    public void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                        info = obtieneFiltro();
                        panel.getTablaProveedores().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }
    
    public Proveedores getProveedor() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaProveedores().getValueAt(this.panel.getTablaProveedores().getSelectedRow(), 0)));
        proveedor = QuerysProveedores.consultaGeneral(id);
        return proveedor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            if (opcion) {
                if (isSelected) {
                    CtrlPrincipal.proveedor = getProveedor();
                    RegistroMedicamentosPanel rm = new RegistroMedicamentosPanel();
                    CtrlRegMedicamentos med = new CtrlRegMedicamentos(frm, rm, true);
                }else{
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un proveedor");
                }
            }else{
                RegistroProveedoresPanel pp = new RegistroProveedoresPanel();
                CtrlRegProveedores pro = new CtrlRegProveedores(frm, pp, proveedor, false);
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                RegistroProveedoresPanel pp = new RegistroProveedoresPanel();
                CtrlRegProveedores pro = new CtrlRegProveedores(frm, pp, proveedor, true);
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
           this.proveedor = getProveedor();
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

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.activarTimer();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.activarTimer();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.activarTimer();
    }
    
    
    
    
}
