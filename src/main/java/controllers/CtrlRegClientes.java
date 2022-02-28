/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import models.Clientes;
import querys.QuerysClientes;
import views.ClientePanel;
import views.FrmPrincipal;
import views.RegistroClientePanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegClientes implements MouseListener {

    Clientes cliente;
    FrmPrincipal frm;
    RegistroClientePanel registro;
    boolean opcion;


    public CtrlRegClientes(FrmPrincipal frm, RegistroClientePanel r, Clientes c, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);

        if (opcion) {
            this.cliente = c;
            this.registro.getTxtNombre().setText(c.getNombre());
            this.registro.getTxtDireccion().setText(c.getDireccion());
            this.registro.getTxtLocalidad().setText(c.getLocalidad());
            this.registro.getTxtTelefono().setText(c.getTelefono());
            this.registro.getTxtEmail().setText(c.getEmail());
            this.registro.getTxtCP().setText(String.valueOf(c.getCp()));
        }

    }

    public Clientes llenaCliente() {
        int id = 0;
        String nombre = this.registro.getTxtNombre().getText();
        String direccion = this.registro.getTxtDireccion().getText();
        String localidad = this.registro.getTxtLocalidad().getText();
        String telefono = this.registro.getTxtTelefono().getText();
        String email = this.registro.getTxtEmail().getText();
        int CP = Integer.parseInt(this.registro.getTxtCP().getText());
        this.cliente = new Clientes(id, nombre, direccion, localidad, telefono, email, CP);
        return cliente;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtDireccion().getText().isEmpty()
                    || this.registro.getTxtLocalidad().getText().isEmpty()
                    || this.registro.getTxtTelefono().getText().isEmpty()
                    || this.registro.getTxtEmail().getText().isEmpty()
                    || this.registro.getTxtCP().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                CtrlPrincipal.mEmail = CtrlPrincipal.pEmail.matcher(this.registro.getTxtEmail().getText());
                CtrlPrincipal.mTel = CtrlPrincipal.pTel.matcher(this.registro.getTxtTelefono().getText());
                CtrlPrincipal.mCP = CtrlPrincipal.pCP.matcher(this.registro.getTxtCP().getText());
                if (!CtrlPrincipal.mEmail.matches()) {
                    JOptionPane.showMessageDialog(null, "El email no es válido");
                }
                else if (!CtrlPrincipal.mTel.matches()) {
                    JOptionPane.showMessageDialog(null, "El teléfono no es válido");
                }
                else if (!CtrlPrincipal.mCP.matches()) {
                    JOptionPane.showMessageDialog(null, "El CP no es válido");
                }else{
                    if (opcion) {
                        this.cliente.setNombre(this.registro.getTxtNombre().getText());
                        this.cliente.setDireccion(this.registro.getTxtDireccion().getText());
                        this.cliente.setLocalidad(this.registro.getTxtLocalidad().getText());
                        this.cliente.setTelefono(this.registro.getTxtTelefono().getText());
                        this.cliente.setEmail(this.registro.getTxtEmail().getText());
                        this.cliente.setCp(Integer.parseInt(this.registro.getTxtCP().getText()));
                        QuerysClientes.actualizar(this.cliente);
                    } else {
                        this.cliente = llenaCliente();
                        QuerysClientes.crear(this.cliente);
                    }

                    ClientePanel cp = new ClientePanel();
                    CtrlClientes cli = new CtrlClientes(frm, cp, false);
                }

            }
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
