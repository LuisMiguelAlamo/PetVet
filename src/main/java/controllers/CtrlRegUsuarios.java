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
import models.Acceso;
import querys.QuerysAcceso;
import views.FrmPrincipal;
import views.InicioPanel;
import views.RegistroUsuariosPanel;
import views.UsuariosPanel;
import views.VeterinariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegUsuarios implements MouseListener {

    Acceso usuario;
    FrmPrincipal frm;
    RegistroUsuariosPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;
    int idMascota;
    int idVet;
    long time;
    int rolUsuario;
    String txtRol = "";

    public CtrlRegUsuarios(FrmPrincipal frm, RegistroUsuariosPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;
        
        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        
        //Si es administrador puede editar el ComboBox 
        if (!CtrlPrincipal.isAdmin) {
            this.registro.getComboRol().setEnabled(false);
        }else{
            this.registro.getComboRol().setEnabled(true);
        }

        //Si la opcion es verdadera se llenan los campos 
        if (opcion) {
            this.rolUsuario = CtrlPrincipal.usuario.getRol();
            if (rolUsuario == 1) {
                this.txtRol = "Veterinario";
            }else{
                this.txtRol = "Administrador";
            }
            this.registro.getComboRol().setSelectedItem(this.txtRol);
            this.registro.getTxtEmail().setText(CtrlPrincipal.usuario.getEmail());
            this.registro.getTxtPassword().setText(CtrlPrincipal.usuario.getPassword());
        }
    }
    
    private Acceso setUsuario() {
        int rol;
        if (this.registro.getComboRol().getSelectedItem().toString().equals("Administrador")) {
            rol = 2;
        }else{
            rol = 1;
        }
        String email = this.registro.getTxtEmail().getText();
        String password = this.registro.getTxtPassword().getText();
        
        this.usuario = new Acceso(0, rol, email, password);
        return this.usuario;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            //Se comprueba que los campos no estén vacíos antes de guardar la nueva información
            if (this.registro.getTxtEmail().getText().isEmpty()
                    || this.registro.getTxtPassword().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                CtrlPrincipal.mEmail = CtrlPrincipal.pEmail.matcher(this.registro.getTxtEmail().getText());
                this.txtRol = this.registro.getComboRol().getSelectedItem().toString();
                //Se comprueba que el email sea válido mediante una expresión regular
                if (!CtrlPrincipal.mEmail.matches()) {
                    JOptionPane.showMessageDialog(null, "El email no es válido");
                } else {

                    //Se comprueba que sea una nueva usuario
                    if (CtrlPrincipal.isNew) {
                        CtrlPrincipal.usuario = setUsuario();
                        QuerysAcceso.crear(CtrlPrincipal.usuario);
                    } else {
                        //Si no es nueva se cargan los datos que se han actualizado
                        //Se verifica si es Administrador para saber si permite manejar el combobox
                        if (!CtrlPrincipal.isAdmin) {                            
                            CtrlPrincipal.usuario.setEmail(setUsuario().getEmail());
                            CtrlPrincipal.usuario.setPassword(setUsuario().getPassword());
                        }else {
                            CtrlPrincipal.usuario.setRol(setUsuario().getRol());
                            CtrlPrincipal.usuario.setEmail(setUsuario().getEmail());
                            CtrlPrincipal.usuario.setPassword(setUsuario().getPassword());
                        }
                        QuerysAcceso.actualizar(CtrlPrincipal.usuario);
                    }
                    //Si es admin nos manda al Panel de Usuarios, sino al panel de inicio
                    if (CtrlPrincipal.isAdmin) {
                        //Abrimos en panel de citas
                        UsuariosPanel up = new UsuariosPanel();
                        CtrlUsuarios usu = new CtrlUsuarios(frm, up, false);
                    }else {
                        InicioPanel ip = new InicioPanel();
                        CtrlInicio ci = new CtrlInicio(frm, ip);
                    }
                }
            }
        }

//        if (e.getSource().equals(this.registro.getBtnVeterinario())) {
//
//                //Se comprueba que sea una nueva usuario
//                CtrlPrincipal.eleccion = 5;
//                if (CtrlPrincipal.isNew) {
//                    CtrlPrincipal.usuario = setUsuario();
//                    
//
//                    //Se abre el panel de veterinarios para seleccionar uno
//                    VeterinariosPanel vp = new VeterinariosPanel();
//                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true);
//                } else {
//                    //Se carga el objeto usuario con los datos de los campos y los almacenados en mascotas y veterinarios
//                    CtrlPrincipal.usuario.setEmail(setUsuario().getEmail());
//                    CtrlPrincipal.usuario.setPassword(setUsuario().getPassword());
//
//                    //Se abre el panel de veterinarios para seleccionar uno 
//                    VeterinariosPanel vp = new VeterinariosPanel();
//                    CtrlVeterinarios vet = new CtrlVeterinarios(frm, vp, true); //se carga a true para que muestre solo la opción de seleccionar
//                }
//            
//        }
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
