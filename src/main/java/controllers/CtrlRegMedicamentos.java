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
 * Esta clase se encarga de controlar el registro de Medicamentos
 */
public class CtrlRegMedicamentos implements MouseListener {

    Medicamentos medicamento;
    FrmPrincipal frm;
    RegistroMedicamentosPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlRegMedicamentos(FrmPrincipal frm, RegistroMedicamentosPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);

        //Comprueba si hay que cargar los datos en los campos
        if (opcion) {
            //Si aún no se han seleccionado los campos su valor es 0 por lo tanto se muestran vacíos
            if (CtrlPrincipal.medicamento.getPrecio() == 0) {
                this.registro.getTxtPrecio().setText("");
            }else{
                this.registro.getTxtPrecio().setText(String.valueOf(CtrlPrincipal.medicamento.getPrecio()));
            }
            this.registro.getTxtNombre().setText(CtrlPrincipal.medicamento.getNombre());
            this.registro.getTxtProveedor().setText(CtrlPrincipal.proveedor.getNombre());
        }
    }


    //Crea un nuevo medicamento cargando los datos de los campos de texto
    public Medicamentos setMedicamento() {
        String nombre;
        double precio;

        if (this.registro.getTxtNombre().getText().isEmpty()) {
            nombre = "";
        }else{
            nombre = this.registro.getTxtNombre().getText();
        }
        if (this.registro.getTxtPrecio().getText().isEmpty()) {
            precio = 0;
        } else {
            precio = Double.parseDouble(this.registro.getTxtPrecio().getText());
        }
        this.medicamento = new Medicamentos(0, nombre, precio, 0);
        return this.medicamento;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            //Comprobación de que los campos no están vacíos 
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtPrecio().getText().isEmpty()
                    || this.registro.getTxtProveedor().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                    //Se comprueba que sea un nuevo medicamento
                    if (CtrlPrincipal.isNew) {
                        CtrlPrincipal.medicamento = setMedicamento();
                        CtrlPrincipal.medicamento.setCodProveedor(CtrlPrincipal.proveedor.getId());
                        QuerysMedicamentos.crear(CtrlPrincipal.medicamento);
                    } else {//Si no es nuevo se cargan los datos que se han actualizado
                        this.medicamento = CtrlPrincipal.medicamento;
                        this.medicamento.setNombre(this.registro.getTxtNombre().getText());
                        this.medicamento.setPrecio(Double.parseDouble(this.registro.getTxtPrecio().getText()));
                        this.medicamento.setCodProveedor(CtrlPrincipal.proveedor.getId());
                        QuerysMedicamentos.actualizar(this.medicamento);
                    }

                    MedicamentosPanel cp = new MedicamentosPanel();
                    CtrlMedicamentos med = new CtrlMedicamentos(frm, cp, false);
            }
        }
        
        
        if (e.getSource().equals(this.registro.getBtnSeleccionar())) {

                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.medicamento = setMedicamento();

                    ProveedoresPanel pp = new ProveedoresPanel();
                    CtrlProveedores pro = new CtrlProveedores(frm, pp, true);
                } else {
                    CtrlPrincipal.medicamento.setNombre(setMedicamento().getNombre());
                    CtrlPrincipal.medicamento.setPrecio(setMedicamento().getPrecio());
                    ProveedoresPanel pp = new ProveedoresPanel();
                    CtrlProveedores pro = new CtrlProveedores(frm, pp, true);
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
