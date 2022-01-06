/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import views.FrmVeterinario;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMascotas {
    FrmVeterinario frm;    

    public CtrlMascotas(FrmVeterinario frm, JPanel p) {
        this.frm = frm;        
        cargarPanel(frm, p);
    }
    
    private void cargarPanel( FrmVeterinario frm, JPanel p){
        p.setSize(790, 480);
        p.setLocation(0, 0);
        frm.getContentPanel().removeAll();
        frm.getContentPanel().add(p, BorderLayout.CENTER);
        frm.getContentPanel().revalidate();
        frm.getContentPanel().repaint();
    }
    
    
}
