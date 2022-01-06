/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JPanel;
import views.ClientePanel;
import views.ConsultasPanel;
import views.FrmVeterinario;
import views.MascotasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlUserVet implements MouseListener, MouseMotionListener{
    FrmVeterinario frm;
    int xMouse, yMouse;
    Date date; 
    SimpleDateFormat fecha = new SimpleDateFormat("dddd/mmmm/yyyy");

    public CtrlUserVet() {
        frm = new FrmVeterinario();
        this.frm.getBtnClientes().addMouseListener(this);
        this.frm.getBtnMascotas().addMouseListener(this);
        this.frm.getBtnConsultas().addMouseListener(this);
        this.frm.getHeaderPanel().addMouseMotionListener(this);
        this.frm.getExitPanel().addMouseListener(this);
        this.frm.getExitLabel().addMouseListener(this);
        
        this.frm.getLabelFecha().setText(String.valueOf(fecha));
        frm.setVisible(true);
    }
    
    
    public static void showContentPanel(JPanel p){
                       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.frm.getBtnPrincipal())) {
            ClientePanel cp = new ClientePanel();
            showContentPanel(cp);
        }
        if (e.getSource().equals(this.frm.getBtnClientes())) {
            ClientePanel cp = new ClientePanel();
            CtrlClientes cli = new CtrlClientes(frm, cp);
        }
        if (e.getSource().equals(this.frm.getBtnMascotas())) {
            MascotasPanel mp = new MascotasPanel();
            CtrlMascotas mascotas = new CtrlMascotas(frm, mp);
        }
        if (e.getSource().equals(this.frm.getBtnConsultas())) {
            ConsultasPanel cop = new ConsultasPanel();
            showContentPanel(cop);
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
