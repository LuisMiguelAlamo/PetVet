/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.Login;

/**
 *
 * @author Luis Miguel
 */
public class CtrlLogin implements MouseListener{
    Login log;

    public CtrlLogin() {
        this.log = new Login();
        this.log.getBtnAceptar().addMouseListener(this);
        this.log.getBtnCancelar().addMouseListener(this);
        this.log.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.log.getBtnAceptar())) {
            CtrlPrincipal ctrlPri = new CtrlPrincipal();
        }
        if (e.getSource().equals(this.log.getBtnCancelar())) {
            System.exit(0);
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
