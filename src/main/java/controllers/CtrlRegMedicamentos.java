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
import querys.QuerysMedicamentos;
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

    public CtrlRegMedicamentos(FrmPrincipal frm, RegistroMedicamentosPanel r, Medicamentos m, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);

        if (opcion) {
            this.medicamento = m;
            this.registro.getTxtNombre().setText(m.getNombre());
            this.registro.getTxtPrecio().setText(String.valueOf(m.getPrecio()));
            this.registro.getTxtProveedor().setText(String.valueOf(m.getCodProveedor()));
        }
    }

    public CtrlRegMedicamentos(FrmPrincipal frm, RegistroMedicamentosPanel registro, boolean opcion, int idProveedor) {
        this.frm = frm;
        this.registro = registro;
        this.opcion = opcion;
        
        CtrlPrincipal.showContentPanel(frm, registro);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);
        this.registro.getTxtProveedor().setText(String.valueOf(idProveedor));
    }
    
    

    public Medicamentos llenaMedicamentos() {
        int id = 0;
        String nombre = this.registro.getTxtNombre().getText();
        double precio = Double.parseDouble(this.registro.getTxtPrecio().getText());
        int proveedor = Integer.parseInt(this.registro.getTxtProveedor().getText());
        
        this.medicamento = new Medicamentos(id, nombre, precio, proveedor);
        return medicamento;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtPrecio().getText().isEmpty()
                    || this.registro.getTxtProveedor().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {

                if (opcion) {
                    this.medicamento.setNombre(this.registro.getTxtNombre().getText());
                    this.medicamento.setPrecio(Double.parseDouble(this.registro.getTxtPrecio().getText()));
                    this.medicamento.setCodProveedor(Integer.parseInt(this.registro.getTxtProveedor().getText()));
                    QuerysMedicamentos.actualizar(this.medicamento);
                } else {
                    this.medicamento = llenaMedicamentos();
                    QuerysMedicamentos.crear(this.medicamento);
                }

                MedicamentosPanel cp = new MedicamentosPanel();
                CtrlMedicamentos med = new CtrlMedicamentos(frm, cp);
            }
        }
        if (e.getSource().equals(this.registro.getBtnSeleccionar())) {
            ProveedoresPanel pp = new ProveedoresPanel();
            CtrlProveedores pro = new CtrlProveedores(frm, pp, true);
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
