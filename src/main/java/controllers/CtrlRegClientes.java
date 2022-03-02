/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado, el cliente y la condición
    public CtrlRegClientes(FrmPrincipal frm, RegistroClientePanel r, Clientes c, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);
        //Si la opcion es verdadera se llenan los campos 
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
    
    //Método que devuelve un nuevo cliente 
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
            //Se comprueba que los campos no estén vacíos
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtDireccion().getText().isEmpty()
                    || this.registro.getTxtLocalidad().getText().isEmpty()
                    || this.registro.getTxtTelefono().getText().isEmpty()
                    || this.registro.getTxtEmail().getText().isEmpty()
                    || this.registro.getTxtCP().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                //Se comprueba la validez de los datos introducidos mediante las regex
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
                    //Si la opcion es verdadera se acturaliza el cliente seleccionado
                    if (opcion) {
                        this.cliente.setNombre(this.registro.getTxtNombre().getText());
                        this.cliente.setDireccion(this.registro.getTxtDireccion().getText());
                        this.cliente.setLocalidad(this.registro.getTxtLocalidad().getText());
                        this.cliente.setTelefono(this.registro.getTxtTelefono().getText());
                        this.cliente.setEmail(this.registro.getTxtEmail().getText());
                        this.cliente.setCp(Integer.parseInt(this.registro.getTxtCP().getText()));
                        QuerysClientes.actualizar(this.cliente);
                    } else {//Si es false se crea uno nuevo
                        this.cliente = llenaCliente();
                        QuerysClientes.crear(this.cliente);
                    }
                    //Se vuelve a mostrar el Panel de la tabla con los nuevos datos actualizados
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
