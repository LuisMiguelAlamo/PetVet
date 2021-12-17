/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import views.FrmPrincipal;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMascotas {
    FrmPrincipal frm;
    

    public CtrlMascotas(FrmPrincipal frm, JPanel p) {
        this.frm = frm;
        
        p.setSize(630, 440);
        p.setLocation(0, 0);
        this.frm.getContentPanel().removeAll();
        this.frm.getContentPanel().add(p, BorderLayout.CENTER);
        this.frm.getContentPanel().revalidate();
        this.frm.getContentPanel().repaint();
    }
    
    
}
