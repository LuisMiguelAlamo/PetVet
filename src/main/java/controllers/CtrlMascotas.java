/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Clientes;
import models.Mascotas;
import querys.QuerysClientes;
import querys.QuerysMascotas;
import views.FrmPrincipal;
import views.MascotasPanel;
import views.RegistroMascotasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMascotas implements MouseListener {

    FrmPrincipal frm;
    MascotasPanel panel;
    Mascotas mascota;
    String titulos[] = {"Id", "Nombre", "Especie", "Color", "Sexo", "Enfermedades", "Anotaciones", "Vacunas", "Chip", "Dueño"};
    String info[][];
    boolean isSelected;
    ArrayList<Mascotas> miLista;
    ArrayList<Clientes> cliList;

    public CtrlMascotas(FrmPrincipal frm, MascotasPanel p) {
        this.frm = frm;
        this.panel = p;
        
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnBuscar().addMouseListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaMascotas().addMouseListener(this);

        isSelected = false;
        actualizarTabla();
    }

    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaMascotas().setModel(new DefaultTableModel(info, titulos));
    }

    private String[][] obtieneMatriz() {

         miLista = QuerysMascotas.consultaGeneral();
         cliList = QuerysClientes.consultaGeneral();

        String informacion[][] = new String[miLista.size()][10];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getEspecie() + "";
            informacion[x][3] = miLista.get(x).getColor() + "";
            informacion[x][4] = miLista.get(x).getSexo() + "";
            informacion[x][5] = miLista.get(x).getEnfermedades() + "";
            informacion[x][6] = miLista.get(x).getAnotaciones() + "";
            informacion[x][7] = miLista.get(x).getVacunas() + "";
            informacion[x][8] = miLista.get(x).isChip() + "";
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][9] = cli;
        }
        return informacion;
    }

    private String leerClientes(int cc) {
        String nombre = "";
        for (Clientes c : cliList) {
            if (c.getId() == cc) {
                nombre = c.getNombre();
            }
        }
        return nombre;
    }

    private String[][] obtieneFiltro() {

        miLista = QuerysMascotas.consultaFiltro(this.panel.getCampoBuscar().getText());

        String informacion[][] = new String[miLista.size()][8];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getNombre() + "";
            informacion[x][1] = miLista.get(x).getEspecie() + "";
            informacion[x][2] = miLista.get(x).getColor() + "";
            informacion[x][3] = miLista.get(x).getSexo() + "";
            informacion[x][4] = miLista.get(x).getEnfermedades() + "";
            informacion[x][5] = miLista.get(x).getAnotaciones() + "";
            informacion[x][6] = miLista.get(x).getVacunas() + "";
            informacion[x][7] = miLista.get(x).isChip() + "";
            informacion[x][8] = miLista.get(x).getCodCliente() + "";
        }

        return informacion;
    }

    private Mascotas llenaCampos() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 0)));
        String nombre = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 1));
        String especie = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 2));
        String color = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 3));
        String sexo = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 4));
        String enfermedades = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 5));
        String anotaciones = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 6));
        String vacunas = String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 7));
        boolean chip = Boolean.parseBoolean(String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 8)));
        int codigoCliente = QuerysMascotas.leerCliente(id);
        

        mascota = new Mascotas(id, nombre, especie, color, sexo, enfermedades, anotaciones, vacunas, chip, codigoCliente);

        return mascota;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {

        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            RegistroMascotasPanel registro = new RegistroMascotasPanel();
            CtrlRegMascotas rc = new CtrlRegMascotas(frm, registro, this.mascota, false);
        }

        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                RegistroMascotasPanel registro = new RegistroMascotasPanel();
                CtrlRegMascotas rc = new CtrlRegMascotas(frm, registro, this.mascota, true);
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una mascota");
            }
        }

        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaMascotas().getValueAt(this.panel.getTablaMascotas().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysMascotas.eliminar(id);
                this.actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una mascota");
            }
        }

        if (e.getSource().equals(this.panel.getTablaMascotas())) {
            isSelected = true;
            this.mascota = llenaCampos();
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
