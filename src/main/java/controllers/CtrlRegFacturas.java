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
import models.Facturas;
import querys.QuerysFacturas;
import views.ClientePanel;
import views.FrmPrincipal;
import views.FacturasPanel;
import views.RegistroFacturasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlRegFacturas implements MouseListener {

    Facturas factura;
    FrmPrincipal frm;
    RegistroFacturasPanel registro;
    boolean opcion;
    Pattern p = Pattern.compile("[0-9]{5}");
    Matcher m;
    int chip;

    public CtrlRegFacturas(FrmPrincipal frm, RegistroFacturasPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;

        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);


        if (opcion) {
            this.registro.getTxtTotal().setText(String.valueOf(CtrlPrincipal.factura.getTotal()));
            this.registro.getTxtIGIC().setText(String.valueOf(CtrlPrincipal.factura.getIGIC()));
            this.registro.getTxtTotalIGIC().setText(String.valueOf(CtrlPrincipal.factura.getTotalConIGIC()));


            this.registro.getTxtConsulta().setText(CtrlPrincipal.cliente.getNombre());
        }
    }

    public Facturas setFactura() {
            double total;
            double IGIC;
            double totalIGIC;
            if (this.registro.getTxtTotal().getText().isEmpty()) {
                total = 0;
            } else {
                total = Double.parseDouble(this.registro.getTxtTotal().getText());
            }
            if (this.registro.getTxtIGIC().getText().isEmpty()) {
                IGIC = 0;
            } else {
                IGIC = Double.parseDouble(this.registro.getTxtIGIC().getText());
            }
            if (this.registro.getTxtTotalIGIC().getText().isEmpty()) {
                totalIGIC = 0;
            } else {
                totalIGIC = (Double.parseDouble(this.registro.getTxtIGIC().getText())*0.07)+ total;
            }

            this.factura = new Facturas(0, total, IGIC, totalIGIC, 0);
     
        return this.factura;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            if (this.registro.getTxtTotal().getText().isEmpty()
                    || this.registro.getTxtIGIC().getText().isEmpty()
                    || this.registro.getTxtTotalIGIC().getText().isEmpty()
                    || this.registro.getTxtConsulta().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {

                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.factura = setFactura();
                    CtrlPrincipal.factura.setCodCliente(CtrlPrincipal.cliente.getId());
                    QuerysFacturas.crear(CtrlPrincipal.factura);
                } else {
                    CtrlPrincipal.factura.setTotal(setFactura().getTotal());
                    CtrlPrincipal.factura.setIGIC(setFactura().getIGIC());
                    CtrlPrincipal.factura.setIGIC(setFactura().getTotalConIGIC());
                    CtrlPrincipal.mascota.setCodCliente(CtrlPrincipal.cliente.getId());
                    QuerysFacturas.actualizar(CtrlPrincipal.factura);
                }

                FacturasPanel fp = new FacturasPanel();
                CtrlFacturas fac = new CtrlFacturas(frm, fp);
            }
        }
        if (e.getSource().equals(this.registro.getBtnSeleccionar())) {
            
                CtrlPrincipal.eleccion = 4;
                if (CtrlPrincipal.isNew) {
                    CtrlPrincipal.factura = setFactura();
                    ClientePanel cp = new ClientePanel();
                    CtrlClientes cli = new CtrlClientes(frm, cp, true);
                } else {
                    CtrlPrincipal.factura.setTotal(setFactura().getTotal());
                    CtrlPrincipal.factura.setIGIC(setFactura().getIGIC());
                    CtrlPrincipal.factura.setTotalConIGIC(setFactura().getTotalConIGIC());
                    
                    ClientePanel cp = new ClientePanel();
                    CtrlClientes cli  = new CtrlClientes(frm, cp, true);
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
