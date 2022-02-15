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
import models.Mascotas;
import querys.QuerysMascotas;
import views.ClientePanel;
import views.FrmPrincipal;
import views.MascotasPanel;
import views.RegistroMascotasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegMascotas implements MouseListener {

    Mascotas mascota;
    FrmPrincipal frm;
    RegistroMascotasPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;
    int chip;

    public CtrlRegMascotas(FrmPrincipal frm, RegistroMascotasPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);
        this.registro.getjRadioButton1().addMouseListener(this);
        this.registro.getjComboBox1().addMouseListener(this);

        if (opcion) {
            this.registro.getTxtNombre().setText(CtrlPrincipal.mascota.getNombre());
            this.registro.getTxtEspecie().setText(CtrlPrincipal.mascota.getEspecie());
            this.registro.getjComboBox1().setSelectedItem(CtrlPrincipal.mascota.getSexo());
            this.registro.getTxtColor().setText(CtrlPrincipal.mascota.getColor());
            this.registro.getTxtEnfermedades().setText(CtrlPrincipal.mascota.getEnfermedades());
            this.registro.getTxtAnotaciones().setText(CtrlPrincipal.mascota.getAnotaciones());
            this.registro.getTxtVacunas().setText(CtrlPrincipal.mascota.getVacunas());
            if (CtrlPrincipal.mascota.getChip() == 1) {
                this.registro.getjRadioButton1().setSelected(true);
            } else {
                this.registro.getjRadioButton1().setSelected(false);
            }
            this.registro.getTxtCliente().setText(CtrlPrincipal.cliente.getNombre());
        }
    }

    public Mascotas setMascota() {
        String nombre = this.registro.getTxtNombre().getText();
        String especie = this.registro.getTxtEspecie().getText();
        String sexo = this.registro.getjComboBox1().getSelectedItem().toString();
        String color = this.registro.getTxtColor().getText();
        String enfermedades = this.registro.getTxtEnfermedades().getText();
        String anotaciones = this.registro.getTxtAnotaciones().getText();
        String vacunas = this.registro.getTxtVacunas().getText();
        if (registro.getjRadioButton1().isSelected()) {
            chip = 1;
        } else {
            chip = 0;
        }
        
        this.mascota = new Mascotas(0, nombre, especie, color, sexo, enfermedades, anotaciones, vacunas, chip, 0);

        return this.mascota;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtEspecie().getText().isEmpty()
                    || this.registro.getTxtColor().getText().isEmpty()
                    || this.registro.getTxtEnfermedades().getText().isEmpty()
                    || this.registro.getTxtAnotaciones().getText().isEmpty()
                    || this.registro.getTxtVacunas().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {

                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.mascota = setMascota();
                    CtrlPrincipal.mascota.setCodCliente(CtrlPrincipal.cliente.getId());
                    QuerysMascotas.crear(CtrlPrincipal.mascota);
                } else {
                    this.mascota = CtrlPrincipal.mascota;
                    this.mascota.setNombre(this.registro.getTxtNombre().getText());
                    this.mascota.setEspecie(this.registro.getTxtEspecie().getText());
                    this.mascota.setSexo(this.registro.getjComboBox1().getSelectedItem().toString());
                    this.mascota.setColor(this.registro.getTxtColor().getText());
                    this.mascota.setEnfermedades(this.registro.getTxtEnfermedades().getText());
                    this.mascota.setAnotaciones(this.registro.getTxtAnotaciones().getText());
                    this.mascota.setVacunas(this.registro.getTxtVacunas().getText());
                    if (registro.getjRadioButton1().isSelected()) {
                        chip = 1;
                    } else {
                        chip = 0;
                    }
                    this.mascota.setChip(chip);
                    this.mascota.setCodCliente(CtrlPrincipal.cliente.getId());
                    QuerysMascotas.actualizar(this.mascota);
                }

                MascotasPanel mp = new MascotasPanel();
                CtrlMascotas mas = new CtrlMascotas(frm, mp, false);
            }
        }
        if (e.getSource().equals(this.registro.getBtnSeleccionar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar el cliente");
            } else {
                CtrlPrincipal.eleccion = 3;
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.mascota = setMascota();
                    ClientePanel cp = new ClientePanel();
                    CtrlClientes cli = new CtrlClientes(frm, cp, true);
                } else {
                    CtrlPrincipal.mascota.setNombre(this.registro.getTxtNombre().getText());
                    CtrlPrincipal.mascota.setEspecie(this.registro.getTxtEspecie().getText());
                    CtrlPrincipal.mascota.setSexo(this.registro.getjComboBox1().getSelectedItem().toString());
                    CtrlPrincipal.mascota.setColor(this.registro.getTxtColor().getText());
                    CtrlPrincipal.mascota.setEnfermedades(this.registro.getTxtEnfermedades().getText());
                    CtrlPrincipal.mascota.setAnotaciones(this.registro.getTxtAnotaciones().getText());
                    CtrlPrincipal.mascota.setVacunas(this.registro.getTxtVacunas().getText());
                    if (registro.getjRadioButton1().isSelected()) {
                        chip = 1;
                    } else {
                        chip = 0;
                    }
                    CtrlPrincipal.mascota.setChip(chip);
                    
                    ClientePanel cp = new ClientePanel();
                    CtrlClientes cli = new CtrlClientes(frm, cp, true);
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
