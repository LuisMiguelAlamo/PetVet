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
import models.Medicamentos;
import models.Proveedores;
import querys.QuerysMedicamentos;
import querys.QuerysProveedores;
import views.MedicamentosPanel;
import views.FrmPrincipal;
import views.ProveedoresPanel;
import views.RegistroMedicamentosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegMedicamentos implements MouseListener {

    Medicamentos medicamento;
    FrmPrincipal frm;
    RegistroMedicamentosPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;

    public CtrlRegMedicamentos(FrmPrincipal frm, RegistroMedicamentosPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);

        if (opcion) {
            this.registro.getTxtNombre().setText(CtrlPrincipal.medicamento.getNombre());
            this.registro.getTxtPrecio().setText(String.valueOf(CtrlPrincipal.medicamento.getPrecio()));
            this.registro.getTxtProveedor().setText(CtrlPrincipal.proveedor.getNombre());
        }
    }


    public void setMedicamento() {
        String nombre = this.registro.getTxtNombre().getText();
        double precio = Double.parseDouble(this.registro.getTxtPrecio().getText());

        this.medicamento = new Medicamentos(0, nombre, precio, 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtPrecio().getText().isEmpty()
                    || this.registro.getTxtProveedor().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.medicamento.setCodProveedor(CtrlPrincipal.proveedor.getId());
                    QuerysMedicamentos.crear(CtrlPrincipal.medicamento);
                } else {                    
                    this.medicamento = CtrlPrincipal.medicamento;
                    this.medicamento.setNombre(this.registro.getTxtNombre().getText());
                    this.medicamento.setPrecio(Double.parseDouble(this.registro.getTxtPrecio().getText()));
                    this.medicamento.setCodProveedor(CtrlPrincipal.proveedor.getId());
                    QuerysMedicamentos.actualizar(this.medicamento);
                }

                MedicamentosPanel cp = new MedicamentosPanel();
                CtrlMedicamentos med = new CtrlMedicamentos(frm, cp);
            }
        }
        if (e.getSource().equals(this.registro.getBtnSeleccionar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    && this.registro.getTxtPrecio().getText().isEmpty()
                    && this.registro.getTxtProveedor().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar el dueño");

            }else {                
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.medicamento = new Medicamentos(0, this.registro.getTxtNombre().getText(), Double.parseDouble(this.registro.getTxtPrecio().getText()), 0);
                    
                    ProveedoresPanel pp = new ProveedoresPanel();
                    CtrlProveedores pro = new CtrlProveedores(frm, pp, true);
                }else {
                    CtrlPrincipal.medicamento.setNombre(this.registro.getTxtNombre().getText());
                    CtrlPrincipal.medicamento.setPrecio(Double.parseDouble(this.registro.getTxtPrecio().getText()));
                    ProveedoresPanel pp = new ProveedoresPanel();
                    CtrlProveedores pro = new CtrlProveedores(frm, pp, true);
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
