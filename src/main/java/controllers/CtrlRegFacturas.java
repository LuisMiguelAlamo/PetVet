/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
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
public class CtrlRegFacturas implements MouseListener, KeyListener {

    Facturas factura;
    FrmPrincipal frm;
    RegistroFacturasPanel registro;
    boolean opcion;
    int chip;
    long time;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlRegFacturas(FrmPrincipal frm, RegistroFacturasPanel r, boolean opcion) {
        this.frm = frm;
        this.registro = r;
        this.opcion = opcion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, r);

        this.registro.getBtnGuardar().addMouseListener(this);
        this.registro.getBtnSeleccionar().addMouseListener(this);
        this.registro.getTxtTotal().addKeyListener(this);
        this.registro.getTxtIGIC().addKeyListener(this);
        //Si la opcion es verdadera se llenan los campos
        if (opcion) {
            this.registro.getTxtFecha().setDate(CtrlPrincipal.factura.getFecha());
            //Si aún no se han seleccionado los campos su valor es 0 por lo tanto se muestran vacíos
            if (CtrlPrincipal.factura.getTotal() == 0 || CtrlPrincipal.factura.getIGIC() == 0) {
                this.registro.getTxtTotal().setText("");
                this.registro.getTxtIGIC().setText("");
                this.registro.getTxtTotalIGIC().setText("");
            }else{
                this.registro.getTxtTotal().setText(String.valueOf(CtrlPrincipal.factura.getTotal()));
                this.registro.getTxtIGIC().setText(String.valueOf(CtrlPrincipal.factura.getIGIC()));
                this.registro.getTxtTotalIGIC().setText(String.valueOf(CtrlPrincipal.factura.getTotalConIGIC()));                
            }
                        
            
            this.registro.getTxtCliente().setText(CtrlPrincipal.cliente.getNombre());
        }
    }
    
    //Método que calcula el total con IGIC 
    public Double devuelveTotalIGIC(Double total, Double IGIC){        
        return (total*IGIC)/100 + total;
    }
    
    //Método que calcula el IGIC
    public Double calculaIGIC(Double total, Double IGIC){        
        return (total*IGIC)/100;
    }

    //Método que crea una nueva factura cargando los datos de los campos de texto
    public Facturas setFactura() {
        long tiempo;
        Date fecha = null;
        double total;
        double IGIC;
        double totalIGIC;
        if (this.registro.getTxtFecha().getDate() == null) {
            tiempo = 0;
            this.registro.getTxtFecha().setDate(null);
        } else {
            tiempo = this.registro.getTxtFecha().getDate().getTime();
            fecha = new Date(tiempo);
        }
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
        totalIGIC = devuelveTotalIGIC(total, IGIC);

        this.factura = new Facturas(0, fecha, total, IGIC, totalIGIC, 0);

        return this.factura;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.registro.getBtnGuardar())) {
            //Se comprueba que los demás campos no estén vacíos antes de guardar la nueva información
            if (this.registro.getTxtFecha().getDate() == null
                    || this.registro.getTxtTotal().getText().isEmpty()
                    || this.registro.getTxtIGIC().getText().isEmpty()
                    || this.registro.getTxtTotalIGIC().getText().isEmpty()
                    || this.registro.getTxtCliente().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");

            } else {
                CtrlPrincipal.mTotal = CtrlPrincipal.pDinero.matcher(this.registro.getTxtTotal().getText());
                CtrlPrincipal.mIGIC = CtrlPrincipal.pDinero.matcher(this.registro.getTxtIGIC().getText());
                if (!CtrlPrincipal.mTotal.matches()) {
                    JOptionPane.showMessageDialog(null, "El total no es válido");
                }else if (!CtrlPrincipal.mIGIC.matches()) {
                    JOptionPane.showMessageDialog(null, "El IGIC no es válido");
                } else {

                    //Se comprueba que sea una nueva factura
                    if (CtrlPrincipal.isNew) {
                        CtrlPrincipal.factura = setFactura();
                        CtrlPrincipal.factura.setCodCliente(CtrlPrincipal.cliente.getId());
                        QuerysFacturas.crear(CtrlPrincipal.factura);
                    } else {//Si no es nueva se cargan los datos que se han actualizado
                        this.time = this.registro.getTxtFecha().getDate().getTime();
                        CtrlPrincipal.factura.setFecha(new Date(time));
                        CtrlPrincipal.factura.setTotal(Double.parseDouble(this.registro.getTxtTotal().getText()));
                        CtrlPrincipal.factura.setIGIC(Double.parseDouble(this.registro.getTxtIGIC().getText()));
                        CtrlPrincipal.factura.setTotalConIGIC(Double.parseDouble(this.registro.getTxtTotalIGIC().getText()));
                        CtrlPrincipal.factura.setCodCliente(CtrlPrincipal.cliente.getId());
                        QuerysFacturas.actualizar(CtrlPrincipal.factura);
                    }
                    //Abrimos en panel de facturas
                    FacturasPanel fp = new FacturasPanel();
                    CtrlFacturas fac = new CtrlFacturas(frm, fp);
                }
            }
        }
        
        
        if (e.getSource().equals(this.registro.getBtnSeleccionar())) {
            CtrlPrincipal.mTotal = CtrlPrincipal.pDinero.matcher(this.registro.getTxtTotal().getText());
            CtrlPrincipal.mIGIC = CtrlPrincipal.pDinero.matcher(this.registro.getTxtIGIC().getText());
            if (!CtrlPrincipal.mTotal.matches()) {
                JOptionPane.showMessageDialog(null, "El total no es válido");
            }
            if (!CtrlPrincipal.mIGIC.matches()) {
                JOptionPane.showMessageDialog(null, "El IGIC no es válido");
            } else {
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
                    CtrlClientes cli = new CtrlClientes(frm, cp, true);
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Se llena el campo de Total con IGIC al escribir en los dos anteriores
        try {
            double base = Double.parseDouble(this.registro.getTxtTotal().getText());
            double impuesto = Double.parseDouble(this.registro.getTxtIGIC().getText());
            this.registro.getTxtTotalIGIC().setText(String.valueOf(devuelveTotalIGIC(base, impuesto)));
        } catch (Exception ex) {
            this.registro.getTxtTotalIGIC().setText(String.valueOf(0));
        }
    }

}
