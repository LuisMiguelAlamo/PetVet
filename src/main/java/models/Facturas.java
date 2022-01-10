/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Luis Miguel
 */
public class Facturas {
    int id;
    double total;
    double IGIC;
    double totalConIGIC;
    int codConsulta;

    public Facturas(int id, double total, double IGIC, double totalConIGIC, int codConsulta) {
        this.id = id;
        this.total = total;
        this.IGIC = IGIC;
        this.totalConIGIC = totalConIGIC;
        this.codConsulta = codConsulta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCodConsulta() {
        return codConsulta;
    }

    public void setCodConsulta(int codConsulta) {
        this.codConsulta = codConsulta;
    }
    
    
}
