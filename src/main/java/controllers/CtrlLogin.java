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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Acceso;
import models.Roles;
import querys.QuerysRol;
import querys.QuerysAcceso;
import views.Login;

/**
 *
 * @author Luis Miguel
 */
public class CtrlLogin implements MouseListener, MouseMotionListener {

    Login log;
    int xMouse, yMouse;
    ArrayList<Acceso> acceso;
    ArrayList<Roles> rol;
    boolean admin;

    public CtrlLogin() {
        this.log = new Login();
        this.log.getBtnAceptar().addMouseListener(this);
        this.log.getBtnCancelar().addMouseListener(this);
        this.log.getTxtUsuario().addMouseListener(this);
        this.log.getTxtUsuario().setEnabled (false);
        this.log.getTxtPassword().setEnabled(false);
        this.log.getTxtPassword().addMouseListener(this);
        this.log.getHeaderPanel().addMouseMotionListener(this);
        this.log.getExitPanel().addMouseListener(this);
        this.log.getBtnX().addMouseListener(this);
        this.log.setVisible(true);
        admin = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.log.getBtnAceptar())) {            
            
            if (this.log.getTxtUsuario().getText().isEmpty() 
                    || this.log.getTxtPassword().getText().isEmpty()
                    || this.log.getTxtUsuario().getText().equals("Ingrese su nombre de usuario")
                    || this.log.getTxtPassword().equals("*****")) {
                JOptionPane.showMessageDialog(null, "Debe introducir un usuario y contraseña");
            }else{
                String usuario = this.log.getTxtUsuario().getText();
                String password = this.log.getTxtPassword().getText();
                this.acceso = QuerysAcceso.consultaGeneral();
                this.rol = QuerysRol.consultaGeneral();
                
                for (Acceso a : acceso) {
                    if (a.getUsuario().equals(usuario) && a.getPassword().equals(password)) {
                        if (a.getRol() == 1) {
                            CtrlPrincipal ctrlPri = new CtrlPrincipal(false);
                            this.log.dispose();
                        } else {
                            CtrlPrincipal ctrlPri = new CtrlPrincipal(true);
                            this.log.dispose();
                        }

                    }
                }          
            }


        }
        if (e.getSource().equals(this.log.getBtnCancelar())) {
            System.exit(0);
        }
        if (e.getSource().equals(this.log.getBtnX())) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(this.log.getTxtUsuario())) {
            this.log.getTxtUsuario().setEnabled(true);
            this.log.getTxtPassword().setEnabled(true);
            if (this.log.getTxtUsuario().getText().equals("Ingrese su nombre de usuario")) {
                this.log.getTxtUsuario().setText("");
                this.log.getTxtUsuario().setForeground(Color.black);
            }
            if (String.valueOf(this.log.getTxtPassword().getText()).isEmpty()) {
                this.log.getTxtPassword().setText("*****");
                this.log.getTxtPassword().setForeground(Color.gray);
            }
        }
        if (e.getSource().equals(this.log.getTxtPassword())) {
            if (String.valueOf(this.log.getTxtPassword().getText()).equals("*****")) {
                this.log.getTxtPassword().setText("");
                this.log.getTxtPassword().setForeground(Color.black);
            }
            if (this.log.getTxtUsuario().getText().isEmpty()) {
                this.log.getTxtUsuario().setText("Ingrese su nombre de usuario");
                this.log.getTxtUsuario().setForeground(Color.gray);
            }
        }

        if (e.getSource().equals(this.log.getHeaderPanel())) {
            this.xMouse = e.getX();
            this.yMouse = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(this.log.getBtnX())) {
            this.log.getExitPanel().setBackground(Color.RED);
            this.log.getBtnX().setForeground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(this.log.getBtnX())) {
            this.log.getExitPanel().setBackground(Color.WHITE);
            this.log.getBtnX().setForeground(Color.black);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(this.log.getHeaderPanel())) {
            int xPos = e.getXOnScreen();
            int yPos = e.getYOnScreen();
            this.log.setLocation(xPos - xMouse, yPos - yMouse);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
