/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.FrmPrincipal;
import views.InicioPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlInicio {
    FrmPrincipal frm;
    InicioPanel panel;

    //Constructor de la clase que recibe el JFrame principal y el panel que será mostrado
    public CtrlInicio(FrmPrincipal frm, InicioPanel panel) {
        this.frm = frm;
        this.panel = panel;        
        CtrlPrincipal.showContentPanel(frm, panel);      
    }
    
    
}
