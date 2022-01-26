/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Citas;
import models.Mascotas;
import models.Veterinarios;
import querys.QuerysCitas;
import querys.QuerysClientes;
import querys.QuerysMascotas;
import querys.QuerysVeterinarios;
import views.CitasPanel;
import views.FrmPrincipal;
import views.RegistroCitasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlCitas implements MouseListener{
    
    FrmPrincipal frm;
    CitasPanel panel;
    Citas cita;
    String titulos[] = {"Id", "Fecha", "Veterinario", "Mascota"};
    String info[][];
    ArrayList<Citas> miLista;
    ArrayList<Mascotas> listaMascotas;
    ArrayList<Veterinarios> listaVet;
    boolean isSelected;

    public CtrlCitas(FrmPrincipal frm, CitasPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaCitas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaCitas().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        miLista = QuerysCitas.consultaGeneral();
        listaMascotas = QuerysMascotas.consultaGeneral();
        listaVet = QuerysVeterinarios.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            int cv = miLista.get(x).getCodVeterinario();
            int cm = miLista.get(x).getCodMascota();
            String vet = leeVet(cv);
            String masc = leeMascota(cm);
            informacion[x][2] = vet;
            informacion[x][3] = masc;
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        miLista = QuerysCitas.consultaFiltro(this.panel.getCampoBuscar().getText());
        listaMascotas = QuerysMascotas.consultaGeneral();
        listaVet = QuerysVeterinarios.consultaGeneral();

        String informacion[][] = new String[miLista.size()][7];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            int cv = miLista.get(x).getCodVeterinario();
            int cm = miLista.get(x).getCodMascota();
            String vet = leeVet(cv);
            String masc = leeMascota(cm);
            informacion[x][2] = vet;
            informacion[x][3] = masc;
        }

        return informacion;
    }
    
    private String leeMascota(int cm){
        String nombre = "";
        for (Mascotas m : listaMascotas) {
            if (m.getId() == cm) {
                nombre = m.getNombre();
            }
        }
        return nombre;
    }
    private String leeVet(int cv){
        String nombre = "";
        for (Veterinarios v : listaVet) {
            if (v.getId() == cv) {
                nombre = v.getNombre();
            }
        }
        return nombre;
    }
    
    private Citas getCita() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));
        cita = QuerysCitas.consultaGeneral(id);
        return cita;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            CtrlPrincipal.isNew = true;
            CtrlPrincipal.cita = null;
            RegistroCitasPanel registro = new RegistroCitasPanel();                        
            CtrlRegCitas rc = new CtrlRegCitas(frm, registro, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.cita = getCita();
                this.cita = getCita();
                CtrlPrincipal.mascota = QuerysMascotas.consultaGeneral(this.cita.getCodMascota());
                CtrlPrincipal.veterinario = QuerysVeterinarios.consultaGeneral(this.cita.getCodVeterinario());
                RegistroCitasPanel registro = new RegistroCitasPanel();                        
                CtrlRegCitas rc = new CtrlRegCitas(frm, registro, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysClientes.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaCitas())) {
            isSelected = true;
            this.cita = getCita();
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
