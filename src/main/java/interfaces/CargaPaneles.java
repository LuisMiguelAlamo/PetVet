/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import views.FrmVeterinario;

/**
 *
 * @author Luis Miguel
 */
public interface CargaPaneles {
    
     private void cargarPanel( FrmVeterinario frm, JPanel p){
        p.setSize(630, 440);
        p.setLocation(0, 0);
        frm.getContentPanel().removeAll();
        frm.getContentPanel().add(p, BorderLayout.CENTER);
        frm.getContentPanel().revalidate();
        frm.getContentPanel().repaint();
    }
}
