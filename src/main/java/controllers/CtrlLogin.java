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
import querys.QuerysAcceso;
import views.Login;

/**
 *
 * @author Luis Miguel
 */
public class CtrlLogin implements MouseListener, MouseMotionListener {

    Login log;
    int xMouse, yMouse;
    Acceso acceso;
    ArrayList<Roles> rol;
    boolean admin;
    boolean isCorrect;

    //Constructor de la clase que activa todos los listener a los componentes
    public CtrlLogin() {
        this.log = new Login();
        this.log.getBtnAceptar().addMouseListener(this);
        this.log.getBtnCancelar().addMouseListener(this);
        this.log.getTxtUsuario().addMouseListener(this);
        this.log.getTxtPassword().addMouseListener(this);
        this.log.getHeaderPanel().addMouseMotionListener(this);
        this.log.getExitPanel().addMouseListener(this);
        this.log.getBtnX().addMouseListener(this);
        this.log.getRegistroLabel().addMouseListener(this);
        this.log.setVisible(true);
        admin = false;
    }
    
    //Método que comprueba que el password sea válido. Devuelve un boolean 
    private static boolean comprobarPassword(Acceso acceso, String password) {
        if (acceso.getPassword().equals(password)) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.log.getBtnAceptar())) {            
            
            if (this.log.getTxtUsuario().getText().isEmpty() 
                    || this.log.getTxtPassword().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");
            }else{
                String usuario = this.log.getTxtUsuario().getText();
                String password = this.log.getTxtPassword().getText();
                
                this.acceso = QuerysAcceso.isLogged(usuario);
                
                if (acceso == null) {
                    JOptionPane.showMessageDialog(null, "Usuario no existe");
                }else{
                    if (comprobarPassword(acceso, password)) {
                        CtrlPrincipal.usuario = this.acceso;
                        if (acceso.getRol() == 1) {
                            CtrlPrincipal pri = new CtrlPrincipal(false);
                            this.log.dispose();
                        }else{
                            CtrlPrincipal pri = new CtrlPrincipal(true);
                            this.log.dispose();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
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
        if (e.getSource().equals(this.log.getRegistroLabel())) {
            CtrlRegistro ctrlReg = new CtrlRegistro(this.log);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
        //Se cambia el color de fondo del botón X al tener el foco
        if (e.getSource().equals(this.log.getBtnX())) {
            this.log.getExitPanel().setBackground(Color.RED);
            this.log.getBtnX().setForeground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Se cambia el color de fondo del botón X al perder el foco
        if (e.getSource().equals(this.log.getBtnX())) {
            this.log.getExitPanel().setBackground(Color.WHITE);
            this.log.getBtnX().setForeground(Color.black);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Se toma la posición del cursor al arrastrar la ventana para posicionarla
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
