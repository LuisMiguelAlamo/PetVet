/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDateTime;
import javax.swing.JPanel;
import views.CitasPanel;
import views.ClientePanel;
import views.ConsultasPanel;
import views.FacturasPanel;
import views.FrmPrincipal;
import views.MascotasPanel;
import views.MedicamentosPanel;
import views.ProveedoresPanel;
import views.VeterinariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlPrincipal implements MouseListener, MouseMotionListener{
    FrmPrincipal frm;
    int xMouse, yMouse;
    boolean condicion;
    LocalDateTime dateTime = LocalDateTime.now();
    String fecha = "Hoy es "+dateTime.getDayOfWeek()+" "+ dateTime.getDayOfMonth() + " de "+ dateTime.getMonth() + " del "+ dateTime.getYear();

    public CtrlPrincipal(boolean condicion) {
        frm = new FrmPrincipal();
        this.frm.getBtnClientes().addMouseListener(this);
        this.frm.getBtnMascotas().addMouseListener(this);
        this.frm.getBtnConsultas().addMouseListener(this);
        this.frm.getBtnCitas().addMouseListener(this);
        this.frm.getBtnFacturas().addMouseListener(this);
        this.frm.getBtnMedicamentos().addMouseListener(this);
        this.frm.getBtnVeterinarios().addMouseListener(this);
        this.frm.getBtnProveedores().addMouseListener(this);
        this.frm.getBtnUsuarios().addMouseListener(this);
        this.frm.getHeaderPanel().addMouseMotionListener(this);
        this.frm.getExitPanel().addMouseListener(this);
        this.frm.getExitLabel().addMouseListener(this);
        
        this.condicion = condicion;
        if (condicion) {
            cargaAdmin();
        }else{
            this.frm.getBtnMedicamentos().setVisible(false);
            this.frm.getBtnVeterinarios().setVisible(false);
            this.frm.getBtnProveedores().setVisible(false);
            this.frm.getBtnUsuarios().setVisible(false);           
        }
        
        
        this.frm.getLabelFecha().setText(String.valueOf(fecha));
        frm.setVisible(true);
    }
    
    
    public static void showContentPanel(FrmPrincipal frm,JPanel p){
        p.setSize(790, 480);
        p.setLocation(0, 0);
        frm.getContentPanel().removeAll();
        frm.getContentPanel().add(p, BorderLayout.CENTER);
        frm.getContentPanel().revalidate();
        frm.getContentPanel().repaint();
    }
    
    
    private  void cargaAdmin(){
        this.frm.getTopPanel().setBackground(new Color(0, 102, 102));
        this.frm.getSidePanel().setBackground(new Color(0, 51, 51));
        this.frm.getBtnPrincipal().setBackground(new Color(0, 51, 51));
        this.frm.getBtnClientes().setBackground(new Color(0, 51, 51));
        this.frm.getBtnMascotas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnConsultas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnCitas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnFacturas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnMedicamentos().setBackground(new Color(0, 51, 51));
        this.frm.getBtnMedicamentos().setBackground(new Color(0, 51, 51));
        this.frm.getBtnVeterinarios().setBackground(new Color(0, 51, 51));
        this.frm.getBtnProveedores().setBackground(new Color(0, 51, 51));
        this.frm.getBtnUsuarios().setBackground(new Color(0, 51, 51));
        
        this.frm.getBtnMedicamentos().setVisible(true);
        this.frm.getBtnProveedores().setVisible(true);
        this.frm.getBtnUsuarios().setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.frm.getBtnPrincipal())) {
            
        }
        if (e.getSource().equals(this.frm.getBtnClientes())) {
            ClientePanel cp = new ClientePanel();
            CtrlClientes cli = new CtrlClientes(frm, cp, false);
        }
        if (e.getSource().equals(this.frm.getBtnMascotas())) {
            MascotasPanel mp = new MascotasPanel();
            CtrlMascotas mascotas = new CtrlMascotas(frm, mp);
        }
        if (e.getSource().equals(this.frm.getBtnConsultas())) {
            ConsultasPanel consPan = new ConsultasPanel();
            CtrlConsultas consultas = new CtrlConsultas(frm, consPan);
        }
        if (e.getSource().equals(this.frm.getBtnCitas())) {
            CitasPanel cpa = new CitasPanel();
            CtrlCitas citas = new CtrlCitas(frm, cpa);
        }
        if (e.getSource().equals(this.frm.getBtnFacturas())) {
            FacturasPanel facPan = new FacturasPanel();
            CtrlFacturas facturas = new CtrlFacturas(frm, facPan);
        }
        if (e.getSource().equals(this.frm.getBtnMedicamentos())) {
            MedicamentosPanel medPan = new MedicamentosPanel();
            CtrlMedicamentos medicamentos = new CtrlMedicamentos(frm, medPan);
        }
        if (e.getSource().equals(this.frm.getBtnVeterinarios())) {
            VeterinariosPanel vetPan = new VeterinariosPanel();
            CtrlVeterinarios veterinarios = new CtrlVeterinarios(frm, vetPan);
        }
        if (e.getSource().equals(this.frm.getBtnProveedores())) {
            ProveedoresPanel proPan = new ProveedoresPanel();
            CtrlProveedores proveedores = new CtrlProveedores(frm, proPan, false);
        }
        if (e.getSource().equals(this.frm.getExitLabel())) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(this.frm.getHeaderPanel())) {
            this.xMouse = e.getX();
            this.yMouse = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(this.frm.getExitLabel())) {
            this.frm.getExitPanel().setBackground(Color.red);
            this.frm.getExitLabel().setForeground(Color.WHITE);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(this.frm.getExitLabel())) {
            this.frm.getExitPanel().setBackground(Color.WHITE);
            this.frm.getExitLabel().setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(this.frm.getHeaderPanel())) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            this.frm.setLocation(x - xMouse, y - yMouse);
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    
}
