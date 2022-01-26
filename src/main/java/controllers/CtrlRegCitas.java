/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import models.Citas;
import querys.QuerysCitas;
import views.CitasPanel;
import views.FrmPrincipal;
import views.MascotasPanel;
import views.RegistroCitasPanel;
import views.VeterinariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegCitas implements MouseListener {

    Citas cita;
    FrmPrincipal frm;
    RegistroCitasPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;
    int idMascota;
    int idVet;

    public CtrlRegCitas(FrmPrincipal frm, RegistroCitasPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnMascota().addMouseListener(this);
        this.registro.getBtnVeterinario().addMouseListener(this);

        if (opcion) {
            this.registro.getTxtFecha().setText(String.valueOf(CtrlPrincipal.cita.getFecha()));
            this.registro.getTxtMascota().setText(String.valueOf(CtrlPrincipal.mascota.getNombre()));
            this.registro.getTxtVeterinario().setText(String.valueOf(CtrlPrincipal.veterinario.getNombre()));
        }
    }

    public Citas setCitas() {
        Timestamp fecha = Timestamp.valueOf(this.registro.getTxtFecha().getText());
        this.cita = new Citas(0, fecha, 0, 0);
        return cita;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtFecha().getText().isEmpty()
                    || this.registro.getTxtMascota().getText().isEmpty()
                    || this.registro.getTxtVeterinario().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    QuerysCitas.crear(CtrlPrincipal.cita);
                } else {
                    this.cita = CtrlPrincipal.cita;
                    this.cita.setFecha(Timestamp.valueOf(this.registro.getTxtFecha().getText()));
                    this.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    this.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    QuerysCitas.actualizar(this.cita);
                }

                CitasPanel mp = new CitasPanel();
                CtrlCitas cit = new CtrlCitas(frm, mp);
            }
        }
        if (e.getSource().equals(this.registro.getBtnMascota())) {
            if (this.registro.getTxtFecha().getText().isEmpty()
                    && this.registro.getTxtMascota().getText().isEmpty()
                    && this.registro.getTxtVeterinario().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar el dueño");

            } else {
                CtrlPrincipal.isMascota = true;
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.cita = new Citas(0, Timestamp.valueOf(this.registro.getTxtFecha().getText()), 0, 0);

                    MascotasPanel mp = new MascotasPanel();
                    CtrlMascotas mas = new CtrlMascotas(frm, mp, true);
                } else {
                    CtrlPrincipal.cita.setFecha(Timestamp.valueOf(this.registro.getTxtFecha().getText()));
                    CtrlPrincipal.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    MascotasPanel mp = new MascotasPanel();
                    CtrlMascotas mas = new CtrlMascotas(frm, mp, true);
                }
            }
        }
        if (e.getSource().equals(this.registro.getBtnVeterinario())) {
            if (this.registro.getTxtFecha().getText().isEmpty()
                    && this.registro.getTxtMascota().getText().isEmpty()
                    && this.registro.getTxtVeterinario().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar el dueño");

            } else {
                CtrlPrincipal.isVet = true;
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.cita = new Citas(0, Timestamp.valueOf(this.registro.getTxtFecha().getText()), 0, 0);

                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true);
                } else {
                    CtrlPrincipal.cita.setFecha(Timestamp.valueOf(this.registro.getTxtFecha().getText()));
                    CtrlPrincipal.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true);
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
