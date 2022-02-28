/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import models.Proveedores;
import querys.QuerysProveedores;
import views.ProveedoresPanel;
import views.FrmPrincipal;
import views.RegistroProveedoresPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegProveedores implements MouseListener {

    Proveedores proveedor;
    FrmPrincipal frm;
    RegistroProveedoresPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;

    public CtrlRegProveedores(FrmPrincipal frm, RegistroProveedoresPanel r, Proveedores p, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);

        if (opcion) {
            this.proveedor = p;
            this.registro.getTxtNombre().setText(p.getNombre());
            this.registro.getTxtDireccion().setText(p.getDireccion());
            this.registro.getTxtLocalidad().setText(p.getLocalidad());
            this.registro.getTxtTelefono().setText(p.getTelefono());
            this.registro.getTxtEmail().setText(p.getEmail());
            this.registro.getTxtCP().setText(String.valueOf(p.getCP()));
            this.registro.getjDateChooser1().setDate(p.getFecha());
        }

    }

    public Proveedores llenaProveedor() {
        int id = 0;
        String nombre = this.registro.getTxtNombre().getText();
        String direccion = this.registro.getTxtDireccion().getText();
        String localidad = this.registro.getTxtLocalidad().getText();
        String telefono = this.registro.getTxtTelefono().getText();
        String email = this.registro.getTxtEmail().getText();
        int CP = Integer.parseInt(this.registro.getTxtCP().getText());
        long time = this.registro.getjDateChooser1().getDate().getTime();
        Date fecha = new Date(time);
        this.proveedor = new Proveedores(id, nombre, direccion, localidad, telefono, email, CP, fecha);
        return proveedor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtDireccion().getText().isEmpty()
                    || this.registro.getTxtLocalidad().getText().isEmpty()
                    || this.registro.getTxtTelefono().getText().isEmpty()
                    || this.registro.getTxtEmail().getText().isEmpty()
                    || this.registro.getTxtCP().getText().isEmpty()
                    || this.registro.getjDateChooser1().getDate() == null) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                CtrlPrincipal.mEmail = CtrlPrincipal.pEmail.matcher(this.registro.getTxtEmail().getText());
                CtrlPrincipal.mTel = CtrlPrincipal.pTel.matcher(this.registro.getTxtTelefono().getText());
                CtrlPrincipal.mCP = CtrlPrincipal.pCP.matcher(this.registro.getTxtCP().getText());
                if (!CtrlPrincipal.mEmail.matches()) {
                    JOptionPane.showMessageDialog(null, "El email no es válido");
                } else if (!CtrlPrincipal.mTel.matches()) {
                    JOptionPane.showMessageDialog(null, "El teléfono no es válido");
                } else if (!CtrlPrincipal.mCP.matches()) {
                    JOptionPane.showMessageDialog(null, "El CP no es válido");
                } else {

                    if (opcion) {
                        this.proveedor.setNombre(this.registro.getTxtNombre().getText());
                        this.proveedor.setDireccion(this.registro.getTxtDireccion().getText());
                        this.proveedor.setLocalidad(this.registro.getTxtLocalidad().getText());
                        this.proveedor.setTelefono(this.registro.getTxtTelefono().getText());
                        this.proveedor.setEmail(this.registro.getTxtEmail().getText());
                        this.proveedor.setCP(Integer.parseInt(this.registro.getTxtCP().getText()));
                        long time = this.registro.getjDateChooser1().getDate().getTime();
                        this.proveedor.setFecha(new Date(time));
                        QuerysProveedores.actualizar(this.proveedor);
                    } else {
                        this.proveedor = llenaProveedor();
                        QuerysProveedores.crear(this.proveedor);
                    }

                    ProveedoresPanel pp = new ProveedoresPanel();
                    CtrlProveedores pro = new CtrlProveedores(frm, pp, false);
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
