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
public class Mascotas {
    int id;
    String nombre;
    String especie;
    String color;
    String sexo;
    String enfermedades;
    String anotaciones;
    String vacunas;
    int chip;
    int codCliente;

    public Mascotas(int id, String nombre, String especie, String color, String sexo, String enfermedades, String anotaciones, String vacunas, int chip, int codCliente) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.color = color;
        this.sexo = sexo;
        this.enfermedades = enfermedades;
        this.anotaciones = anotaciones;
        this.vacunas = vacunas;
        this.chip = chip;
        this.codCliente = codCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(String anotaciones) {
        this.anotaciones = anotaciones;
    }

    public String getVacunas() {
        return vacunas;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }

    public int getChip() {
        return chip;
    }

    public void setChip(int chip) {
        this.chip = chip;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }
    
    
}
