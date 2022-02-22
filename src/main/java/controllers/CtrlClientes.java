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
import models.Clientes;
import querys.QuerysClientes;
import views.ClientePanel;
import views.FrmPrincipal;
import views.RegistroClientePanel;
import views.RegistroFacturasPanel;
import views.RegistroMascotasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlClientes implements MouseListener, DocumentListener{
    
    FrmPrincipal frm;
    ClientePanel panel;
    Clientes cliente;
    String titulos[] = {"Id", "Nombre", "Dirección", "Localidad", "Teléfono", "Email", "CP"};
    String info[][];
    boolean isSelected;
    boolean condicion;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    public CtrlClientes(FrmPrincipal frm, ClientePanel p, boolean condicion) {
        this.frm = frm;
        this.panel = p;
        this.condicion = condicion;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaClientes().addMouseListener(this);
        
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
    
    public void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                        info = obtieneFiltro();
                        panel.getTablaClientes().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }
    
    private Clientes getCliente() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaClientes().getValueAt(this.panel.getTablaClientes().getSelectedRow(), 0)));        
        this.cliente = QuerysClientes.consultaGeneral(id);
        return this.cliente;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            if (condicion) {
                if (isSelected) {
                    if (CtrlPrincipal.eleccion == 3) {
                        CtrlPrincipal.cliente = getCliente();
                        RegistroMascotasPanel mp = new RegistroMascotasPanel();
                        CtrlRegMascotas mas = new CtrlRegMascotas(frm, mp, true);
                    } else if(CtrlPrincipal.eleccion == 4) {
                        CtrlPrincipal.cliente = getCliente();
                        RegistroFacturasPanel fp = new RegistroFacturasPanel();
                        CtrlRegFacturas fac = new CtrlRegFacturas(frm, fp, true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
                }
            }else {
                RegistroClientePanel registro = new RegistroClientePanel();
                CtrlRegClientes rc = new CtrlRegClientes(this.frm, registro, this.cliente, false);
            }
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
           this.cliente = getCliente();
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
