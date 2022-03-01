/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author Luis Miguel
 */
public class Facturas {
    int id;
    Date fecha;
    double total;
    double IGIC;
    double totalConIGIC;
    int codCliente;

    public Facturas(int id, Date fecha,double total, double IGIC, double totalConIGIC, int codCliente) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.IGIC = IGIC;
        this.totalConIGIC = totalConIGIC;
        this.codCliente = codCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }        

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getIGIC() {
        return IGIC;
    }

    public void setIGIC(double IGIC) {
        this.IGIC = IGIC;
    }

    public double getTotalConIGIC() {
        return totalConIGIC;
    }

    public void setTotalConIGIC(double totalConIGIC) {
        this.totalConIGIC = totalConIGIC;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }
    
    
}
