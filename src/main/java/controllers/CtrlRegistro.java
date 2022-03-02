
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import models.Acceso;
import querys.QuerysAcceso;
import views.DlgRegistro;
import views.Login;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegistro implements MouseListener{
    
    Login frm;
    DlgRegistro dlg;
    Acceso usuario;
    Pattern pEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    Matcher mEmail;

    public CtrlRegistro(Login frm) {
        dlg = new DlgRegistro(frm);        
        dlg.getBtnGuardar().addMouseListener(this);
        dlg.getxPanel().addMouseListener(this);
        dlg.setVisible(true);
    }
    
    private Acceso setUsuario() {
        int rol;
        if (this.dlg.getComboEleccion().getSelectedItem().toString().equals("Administrador")) {
            rol = 2;
        }else{
            rol = 1;
        }
        String email = this.dlg.getTxtEmail().getText();
        String valorPass = new String(this.dlg.getTxtPassword().getPassword());
        
        this.usuario = new Acceso(0, rol, email, valorPass);
        return this.usuario;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.dlg.getBtnGuardar())) {
            if (this.dlg.getTxtEmail().getText().isEmpty() || this.dlg.getTxtPassword().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");
            }else{
                mEmail = pEmail.matcher(this.dlg.getTxtEmail().getText());
                if (!mEmail.matches()) {
                    JOptionPane.showMessageDialog(null, "El email no es válido");
                } else {
                    QuerysAcceso.crear(setUsuario());
                    this.dlg.dispose();
                }
            }
            
        }
        if (e.getSource().equals(this.dlg.getxPanel())) {
            this.dlg.dispose();
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
