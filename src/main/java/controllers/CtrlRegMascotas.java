/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import models.Clientes;
import models.Mascotas;
import querys.QuerysClientes;
import querys.QuerysMascotas;
import views.ClientePanel;
import views.FrmPrincipal;
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

    public CtrlRegMascotas(FrmPrincipal frm, RegistroMascotasPanel r, Mascotas m, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);

        if (opcion) {
            this.mascota = m;
            this.registro.getTxtNombre().setText(m.getNombre());
            this.registro.getTxtEspecie().setText(m.getEspecie());
            this.registro.getTxtColor().setText(m.getColor());
            this.registro.getTxtEnfermedades().setText(m.getEnfermedades());
            this.registro.getTxtAnotaciones().setText(m.getAnotaciones());
            this.registro.getTxtVacunas().setText(m.getVacunas());
        }

    }

    public Mascotas llenaMascota() {
        int id = 0;
        String nombre = this.registro.getTxtNombre().getText();
        String especie = this.registro.getTxtEspecie().getText();
        String color = this.registro.getTxtColor().getText();
        String enfermedades = this.registro.getTxtEnfermedades().getText();
        String anotaciones = this.registro.getTxtAnotaciones().getText();
        String vacunas = this.registro.getTxtVacunas().getText();

        this.mascota = new Mascotas(id, nombre, especie, color, "hembra", enfermedades, anotaciones, vacunas, false, id);
        return mascota;
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

                if (opcion) {
                    this.mascota.setNombre(this.registro.getTxtNombre().getText());
                    this.mascota.setEspecie(this.registro.getTxtEspecie().getText());
                    this.mascota.setColor(this.registro.getTxtColor().getText());
                    this.mascota.setEnfermedades(this.registro.getTxtEnfermedades().getText());
                    this.mascota.setAnotaciones(this.registro.getTxtAnotaciones().getText());
                    this.mascota.setVacunas(this.registro.getTxtVacunas().getText());
                    QuerysMascotas.actualizar(this.mascota);
                } else {
                    this.mascota = llenaMascota();
                    QuerysMascotas.crear(this.mascota);
                }

                ClientePanel cp = new ClientePanel();
                CtrlClientes cli = new CtrlClientes(frm, cp);
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
