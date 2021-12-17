/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import views.ClientePanel;
import views.ConsultasPanel;
import views.FrmPrincipal;
import views.MascotasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlPrincipal implements MouseListener{
    FrmPrincipal frm;

    public CtrlPrincipal() {
        frm = new FrmPrincipal();
        this.frm.getBtnClientes().addMouseListener(this);
        this.frm.getBtnMascotas().addMouseListener(this);
        this.frm.getBtnConsultas().addMouseListener(this);
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
