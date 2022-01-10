/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Luis Miguel
 */
public class Citas {
    int id;
    Timestamp fecha;
    int codVeterinario;
    int codMascota;

    public Citas(int id, Timestamp fecha, int codVeterinario, int codMascota) {
        this.id = id;
        this.fecha = fecha;
        this.codVeterinario = codVeterinario;
        this.codMascota = codMascota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getCodVeterinario() {
        return codVeterinario;
    }

    public void setCodVeterinario(int codVeterinario) {
        this.codVeterinario = codVeterinario;
    }

    public int getCodMascota() {
        return codMascota;
    }

    public void setCodMascota(int codMascota) {
        this.codMascota = codMascota;
    }
    
    
}
