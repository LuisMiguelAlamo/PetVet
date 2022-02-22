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
import models.Citas;
import models.Mascotas;
import models.Veterinarios;
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
    long time;

    public CtrlRegCitas(FrmPrincipal frm, RegistroCitasPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnMascota().addMouseListener(this);
        this.registro.getBtnVeterinario().addMouseListener(this);

        //Si la opcion es verdadera se llenan los campos 
        if (opcion) {
            this.registro.getTxtFecha().setDate(CtrlPrincipal.cita.getFecha());
            this.registro.getTxtHora().setText(CtrlPrincipal.cita.getHora());
            this.registro.getTxtMascota().setText(String.valueOf(CtrlPrincipal.mascota.getNombre()));
            this.registro.getTxtVeterinario().setText(String.valueOf(CtrlPrincipal.veterinario.getNombre()));
        }
    }
    
    private Citas setCita(){
        long tiempo;
        Date fecha = null;
        if (this.registro.getTxtFecha().getDate() == null) {
            tiempo = 0;
            this.registro.getTxtFecha().setDate(null);
        }else{
            tiempo = this.registro.getTxtFecha().getDate().getTime();
            fecha = new Date(tiempo);
        }
        String hora = this.registro.getTxtHora().getText();
        this.cita = new Citas(0, fecha, hora, idVet, 0);
        return this.cita;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            //Se comprueba que los campos no estén vacíos antes de guardar la nueva información
            if (this.registro.getTxtFecha() == null
                    || this.registro.getTxtHora().getText().isEmpty()
                    || this.registro.getTxtMascota().getText().isEmpty()
                    || this.registro.getTxtVeterinario().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                //Se comprueba que sea una nueva cita
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    QuerysCitas.crear(CtrlPrincipal.cita);
                } else {
                    //Si no es nueva se cargan los datos que se han actualizado
                    this.cita = CtrlPrincipal.cita;
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    this.cita.setFecha(new Date(time));
                    this.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    this.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    QuerysCitas.actualizar(this.cita);
                }

                //Abrimos en panel de citas
                CitasPanel mp = new CitasPanel();
                CtrlCitas cit = new CtrlCitas(frm, mp);
            }
        }
        if (e.getSource().equals(this.registro.getBtnMascota())) {
                //Se comprueba que sea una nueva cita
                CtrlPrincipal.eleccion = 1;
                if (CtrlPrincipal.isNew) {
                    //Se crea un objeto cita para almacenar la fecha y la hora
                    CtrlPrincipal.cita = setCita();
                    
                    //Compruebo si el veterinario es null, entonces creo uno vacío no perder los datos
                    //cuando seleccione la mascota
                    if (CtrlPrincipal.veterinario == null) {
                        CtrlPrincipal.veterinario = new Veterinarios(0, "", "", "");
                    }

                    //Se abre el panel de mascotas para seleccionar una 
                    MascotasPanel mp = new MascotasPanel();
                    CtrlMascotas mas = new CtrlMascotas(frm, mp, true);
                } else {
                    //Se carga el objeto cita con los datos de los campos y los almacenados en mascotas y veterinarios
                    CtrlPrincipal.cita.setFecha(setCita().getFecha());
                    CtrlPrincipal.cita.setHora(setCita().getHora());
                    CtrlPrincipal.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());

                    //Se abre el panel de mascotas para seleccionar una 
                    MascotasPanel mp = new MascotasPanel();
                    CtrlMascotas mas = new CtrlMascotas(frm, mp, true);  //se carga a true para que muestre solo la opción de seleccionar
                }
            
        }
        if (e.getSource().equals(this.registro.getBtnVeterinario())) {

                //Se comprueba que sea una nueva cita
                CtrlPrincipal.eleccion = 1;
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.cita = setCita();
                    
                    /*
                    Compruebo si la mascota es null, entonces creo uno vacío 
                    para no perder lo datos cuando cargue el veterinario
                    */
                    if (CtrlPrincipal.mascota == null) {
                        CtrlPrincipal.mascota = new Mascotas(0, "", "", "", "", "", "", "", 0, 0);
                    }

                    //Se abre el panel de veterinarios para seleccionar uno
                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true);
                } else {
                    //Se carga el objeto cita con los datos de los campos y los almacenados en mascotas y veterinarios
                    CtrlPrincipal.cita.setFecha(setCita().getFecha());
                    CtrlPrincipal.cita.setHora(setCita().getHora());
                    CtrlPrincipal.cita.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.cita.setCodVeterinario(CtrlPrincipal.veterinario.getId());

                    //Se abre el panel de veterinarios para seleccionar uno 
                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true); //se carga a true para que muestre solo la opción de seleccionar
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
