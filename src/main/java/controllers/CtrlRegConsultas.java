/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import models.Consultas;
import models.Mascotas;
import models.Medicamentos;
import models.Veterinarios;
import querys.QuerysConsultas;
import views.CitasPanel;
import views.ConsultasPanel;
import views.FrmPrincipal;
import views.MascotasPanel;
import views.MedicamentosPanel;
import views.RegistroConsultasPanel;
import views.VeterinariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegConsultas implements MouseListener {

    Consultas consulta;
    FrmPrincipal frm;
    RegistroConsultasPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;
    int idMascota;
    int idVet;
    long time;

    public CtrlRegConsultas(FrmPrincipal frm, RegistroConsultasPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnMascota().addMouseListener(this);
        this.registro.getBtnVeterinario().addMouseListener(this);
        this.registro.getBtnMedicamento().addMouseListener(this);

        //Si la opcion es verdadera se llenan los campos 
        if (opcion) {
            this.registro.getTxtFecha().setDate(CtrlPrincipal.consulta.getFecha());
            this.registro.getTxtHora().setText(CtrlPrincipal.consulta.getHora());
            this.registro.getAreaDiagnóstico().setText(CtrlPrincipal.consulta.getDiagnostico());
            this.registro.getAreaTratamiento().setText(CtrlPrincipal.consulta.getTratamiento());
            this.registro.getTxtMascota().setText(String.valueOf(CtrlPrincipal.mascota.getNombre()));
            this.registro.getTxtVeterinario().setText(String.valueOf(CtrlPrincipal.veterinario.getNombre()));
            this.registro.getTxtMedicamento().setText(String.valueOf(CtrlPrincipal.medicamento.getNombre()));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            //Se comprueba que los campos no estén vacíos antes de guardar la nueva información
            if (this.registro.getTxtFecha().getDate() == null
                    || this.registro.getTxtHora().getText().isEmpty()
                    || this.registro.getTxtMascota().getText().isEmpty()
                    || this.registro.getTxtVeterinario().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                //Se comprueba que sea una nueva consulta
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.consulta.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.consulta.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    CtrlPrincipal.consulta.setCodMedicamento(CtrlPrincipal.medicamento.getId());
                    QuerysConsultas.crear(CtrlPrincipal.consulta);
                } else {
                    //Si no es nueva se cargan los datos que se han actualizado
                    this.consulta = CtrlPrincipal.consulta;
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    this.consulta.setFecha(new Date(time));
                    this.consulta.setHora(this.registro.getTxtHora().getText());
                    this.consulta.setDiagnostico(this.registro.getAreaDiagnóstico().getText());
                    this.consulta.setTratamiento(this.registro.getAreaTratamiento().getText());
                    this.consulta.setCodMascota(CtrlPrincipal.mascota.getId());
                    this.consulta.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    this.consulta.setCodMedicamento(CtrlPrincipal.medicamento.getId());
                    QuerysConsultas.actualizar(this.consulta);
                }

                //Abrimos en panel de citas
                ConsultasPanel cp = new ConsultasPanel();
                CtrlConsultas con = new CtrlConsultas(frm, cp);
            }
        }
        if (e.getSource().equals(this.registro.getBtnMascota())) {
            //Se comprueba que los campos no estén vacíos
            if (this.registro.getTxtFecha().getDate() == null
                    || this.registro.getTxtHora().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar la mascota");

            } else {
                //Se comprueba que sea una nueva consulta
                CtrlPrincipal.eleccion = 2;
                if (CtrlPrincipal.isNew) {
                    //Se crea un objeto consulta para almacenar la fecha y la hora
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    CtrlPrincipal.consulta = new Consultas(idVet, new Date(time),
                            this.registro.getTxtHora().getText(),
                            this.registro.getAreaDiagnóstico().getText(),
                            this.registro.getAreaTratamiento().getText(), 0, 0, 0);
                    
                    //Compruebo si el veterinario es null, entonces creo uno vacío no perder los datos
                    //cuando seleccione la mascota
                    if (CtrlPrincipal.veterinario == null) {
                        CtrlPrincipal.veterinario = new Veterinarios(0, "", "", "");
                    }
                    if (CtrlPrincipal.medicamento == null) {
                        CtrlPrincipal.medicamento = new Medicamentos(0, "", 0, 0);
                    }

                    //Se abre el panel de mascotas para seleccionar una 
                    MascotasPanel mp = new MascotasPanel();
                    CtrlMascotas mas = new CtrlMascotas(frm, mp, true);
                } else {
                    //Se carga el objeto consulta con los datos de los campos y los almacenados en mascotas y veterinarios
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    CtrlPrincipal.consulta.setFecha(new Date(time));
                    CtrlPrincipal.consulta.setHora(this.registro.getTxtHora().getText());
                    CtrlPrincipal.consulta.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.consulta.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    CtrlPrincipal.consulta.setCodMedicamento(CtrlPrincipal.medicamento.getId());

                    //Se abre el panel de mascotas para seleccionar una 
                    MascotasPanel mp = new MascotasPanel();
                    CtrlMascotas mas = new CtrlMascotas(frm, mp, true);  //se carga a true para que muestre solo la opción de seleccionar
                }
            }
        }
        if (e.getSource().equals(this.registro.getBtnVeterinario())) {
            //Se comprueba que los campos no estén vacíos
            if (this.registro.getTxtFecha().getDate() == null
                    || this.registro.getTxtHora().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar el veterinario");

            } else {
                //Se comprueba que sea una nueva consulta
                CtrlPrincipal.isVet = true;
                CtrlPrincipal.eleccion = 2;
                if (CtrlPrincipal.isNew) {
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    CtrlPrincipal.consulta = new Consultas(idVet, new Date(time),
                            this.registro.getTxtHora().getText(),
                            this.registro.getAreaDiagnóstico().getText(),
                            this.registro.getAreaTratamiento().getText(), 0, 0, 0);
                    
                    /*
                    Compruebo si la mascota es null, entonces creo uno vacío 
                    para no perder lo datos cuando cargue el veterinario
                    */
                    if (CtrlPrincipal.mascota == null) {
                        CtrlPrincipal.mascota = new Mascotas(0, "", "", "", "", "", "", "", 0, 0);
                    }
                    if (CtrlPrincipal.medicamento == null) {
                        CtrlPrincipal.medicamento = new Medicamentos(0, "", 0, 0);
                    }

                    //Se abre el panel de veterinarios para seleccionar uno
                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true);
                } else {
                    //Se carga el objeto consulta con los datos de los campos y los almacenados en mascotas y veterinarios
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    CtrlPrincipal.consulta.setFecha(new Date(time));
                    CtrlPrincipal.consulta.setHora(this.registro.getTxtHora().getText());
                    CtrlPrincipal.consulta.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.consulta.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    CtrlPrincipal.consulta.setCodMedicamento(CtrlPrincipal.medicamento.getId());

                    //Se abre el panel de veterinarios para seleccionar uno 
                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true); //se carga a true para que muestre solo la opción de seleccionar
                }
            }
        }
        
        if (e.getSource().equals(this.registro.getBtnMedicamento())) {
            //Se comprueba que los campos no estén vacíos
            if (this.registro.getTxtFecha().getDate() == null
                    || this.registro.getTxtHora().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe llenar los campos antes de seleccionar el veterinario");

            } else {
                //Se comprueba que sea una nueva consulta
                CtrlPrincipal.isVet = true;
                if (CtrlPrincipal.isNew) {
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    CtrlPrincipal.consulta = new Consultas(idVet, new Date(time),
                            this.registro.getTxtHora().getText(),
                            this.registro.getAreaDiagnóstico().getText(),
                            this.registro.getAreaTratamiento().getText(), 0, 0, 0);
                    
                    /*
                    Compruebo si la mascota es null, entonces creo uno vacío 
                    para no perder lo datos cuando cargue el veterinario
                    */
                    if (CtrlPrincipal.mascota == null) {
                        CtrlPrincipal.mascota = new Mascotas(0, "", "", "", "", "", "", "", 0, 0);
                    }
                    if (CtrlPrincipal.veterinario == null) {
                        CtrlPrincipal.veterinario = new Veterinarios(0, "", "", "");
                    }

                    //Se abre el panel de veterinarios para seleccionar uno
                    MedicamentosPanel mp = new MedicamentosPanel();
                    CtrlMedicamentos med = new CtrlMedicamentos(frm, mp, true);
                } else {
                    //Se carga el objeto consulta con los datos de los campos y los almacenados en mascotas y veterinarios
                    this.time = this.registro.getTxtFecha().getDate().getTime();
                    CtrlPrincipal.consulta.setFecha(new Date(time));
                    CtrlPrincipal.consulta.setHora(this.registro.getTxtHora().getText());
                    CtrlPrincipal.consulta.setCodMascota(CtrlPrincipal.mascota.getId());
                    CtrlPrincipal.consulta.setCodVeterinario(CtrlPrincipal.veterinario.getId());
                    CtrlPrincipal.consulta.setCodMedicamento(CtrlPrincipal.medicamento.getId());

                    //Se abre el panel de veterinarios para seleccionar uno 
                    MedicamentosPanel mp = new MedicamentosPanel();
                    CtrlMedicamentos med = new CtrlMedicamentos(frm, mp, true); //se carga a true para que muestre solo la opción de seleccionar
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
