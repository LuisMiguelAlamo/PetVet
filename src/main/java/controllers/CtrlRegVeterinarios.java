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
import models.Veterinarios;
import querys.QuerysVeterinarios;
import views.VeterinariosPanel;
import views.FrmPrincipal;
import views.RegistroVeterinariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegVeterinarios implements MouseListener {

    Veterinarios veterinario;
    FrmPrincipal frm;
    RegistroVeterinariosPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlRegVeterinarios(FrmPrincipal frm, RegistroVeterinariosPanel r, Veterinarios v, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getTxtNombre().addMouseListener(this);
        this.registro.getBtnGuardar().addMouseListener(this);
        //Comprueba si hay que cargar los datos en los campos
        if (opcion) {
            this.veterinario = v;
            this.registro.getTxtNombre().setText(v.getNombre());
            this.registro.getTxtDireccion().setText(v.getDireccion());
            this.registro.getTxtTelefono().setText(v.getTelefono());
        }

    }

    //Crea un nuevo veterinario cargando los datos de los campos de texto
    public Veterinarios llenaVeterinario() {
        int id = 0;
        String nombre = this.registro.getTxtNombre().getText();
        String direccion = this.registro.getTxtDireccion().getText();
        String telefono = this.registro.getTxtTelefono().getText();
        this.veterinario = new Veterinarios(id, nombre, direccion, telefono);
        return veterinario;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Se comprueba que los campos no estén vacíos antes de guardar la nueva información
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtNombre().getText().isEmpty()
                    || this.registro.getTxtDireccion().getText().isEmpty()
                    || this.registro.getTxtTelefono().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                //Se comprueba la validez de los datos introducidos mediante las regex
                CtrlPrincipal.mTel = CtrlPrincipal.pTel.matcher(this.registro.getTxtTelefono().getText());
                if (!CtrlPrincipal.mTel.matches()) {
                    JOptionPane.showMessageDialog(null, "El teléfono no es válido");
                } else {
                    if (opcion) {
                        this.veterinario.setNombre(this.registro.getTxtNombre().getText());
                        this.veterinario.setDireccion(this.registro.getTxtDireccion().getText());
                        this.veterinario.setTelefono(this.registro.getTxtTelefono().getText());
                        QuerysVeterinarios.actualizar(this.veterinario);
                    } else {
                        this.veterinario = llenaVeterinario();
                        QuerysVeterinarios.crear(this.veterinario);
                    }
                    //Se carga el panel de Veterinarios nuevamente
                    VeterinariosPanel vp = new VeterinariosPanel();
                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, false);
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
