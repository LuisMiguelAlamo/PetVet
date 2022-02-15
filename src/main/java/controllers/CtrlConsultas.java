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
import models.Consultas;
import models.Mascotas;
import models.Medicamentos;
import models.Veterinarios;
import querys.QuerysConsultas;
import querys.QuerysMascotas;
import querys.QuerysMedicamentos;
import querys.QuerysVeterinarios;
import views.ConsultasPanel;
import views.FrmPrincipal;
import views.RegistroConsultasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlConsultas implements MouseListener{
    
    FrmPrincipal frm;
    ConsultasPanel panel;
    Consultas consulta;
    String titulos[] = {"Id", "Fecha", "Hora","Diagnostico", "Tratamiento", "Medicamento", "Veterinario", "Mascota"};
    String info[][];
    boolean isSelected;
    ArrayList<Consultas> miLista;
    ArrayList<Mascotas> listaMascotas;
    ArrayList<Veterinarios> listaVet;
    ArrayList<Medicamentos> listaMed;

    public CtrlConsultas(FrmPrincipal frm, ConsultasPanel p) {
        this.frm = frm;
        this.panel = p;
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaConsultas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaConsultas().setModel(new DefaultTableModel(info, titulos));
    }
    
    private String[][] obtieneMatriz() {

        miLista = QuerysConsultas.consultaGeneral();
        listaMascotas = QuerysMascotas.consultaGeneral();
        listaVet = QuerysVeterinarios.consultaGeneral();
        listaMed = QuerysMedicamentos.consultaGeneral();

        String informacion[][] = new String[miLista.size()][8];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getHora()+ "";
            informacion[x][3] = miLista.get(x).getDiagnostico()+ "";
            informacion[x][4] = miLista.get(x).getTratamiento()+ "";
            
            int cv = miLista.get(x).getCodVeterinario();
            int cm = miLista.get(x).getCodMascota();
            int md = miLista.get(x).getCodMedicamento();
            String vet = leeVet(cv);
            String masc = leeMascota(cm);
            String med = leeMed(md);
            
            informacion[x][5] = med;
            informacion[x][6] = vet;
            informacion[x][7] = masc;
        }

        return informacion;
    }

    private String[][] obtieneFiltro() {

        miLista = QuerysConsultas.consultaFiltro(this.panel.getCampoBuscar().getText());
        listaMascotas = QuerysMascotas.consultaGeneral();
        listaVet = QuerysVeterinarios.consultaGeneral();
        listaMed = QuerysMedicamentos.consultaGeneral();

        String informacion[][] = new String[miLista.size()][8];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getHora()+ "";
            informacion[x][3] = miLista.get(x).getDiagnostico()+ "";
            informacion[x][4] = miLista.get(x).getTratamiento()+ "";
            
            int cv = miLista.get(x).getCodVeterinario();
            int cm = miLista.get(x).getCodMascota();
            int md = miLista.get(x).getCodMedicamento();
            String vet = leeVet(cv);
            String masc = leeMascota(cm);
            String med = leeMed(md);
            
            informacion[x][5] = med;
            informacion[x][6] = vet;
            informacion[x][7] = masc;
        }

        return informacion;
    }
    
    
    //Método que devuelve el nombre de la mascota para no tener que llenar la tabla
    //con el número del id 
    private String leeMascota(int cm){
        String nombre = "";
        for (Mascotas m : listaMascotas) {
            if (m.getId() == cm) {
                nombre = m.getNombre();
            }
        }
        return nombre;
    }
    
    /*
    Método que devuelve el nombre del veterinario para no tener que llenar la 
    tabla con el número del id
    */
    private String leeVet(int cv){
        String nombre = "";
        for (Veterinarios v : listaVet) {
            if (v.getId() == cv) {
                nombre = v.getNombre();
            }
        }
        return nombre;
    }
    
    /*
    Método que devuelve el nombre del veterinario para no tener que llenar la 
    tabla con el número del id
    */
    private String leeMed(int cv){
        String nombre = "";
        for (Medicamentos m : listaMed) {
            if (m.getId() == cv) {
                nombre = m.getNombre();
            }
        }
        return nombre;
    }
    
    //Método que devuelve la consulta de la fila seleccionada en la tabla
    private Consultas getConsulta() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 0)));
        consulta = QuerysConsultas.consultaGeneral(id);
        return consulta;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.panel.getBtnBuscar())) {
            
        }
        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            //Si es una nueva consulta se cargan a null las variables globales
            CtrlPrincipal.isNew = true;
            CtrlPrincipal.consulta= null;
            CtrlPrincipal.mascota = null;
            CtrlPrincipal.veterinario = null;
            CtrlPrincipal.medicamento = null;
            
            RegistroConsultasPanel registro = new RegistroConsultasPanel();                        
            CtrlRegConsultas rc = new CtrlRegConsultas(frm, registro, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.consulta = getConsulta();
                CtrlPrincipal.mascota = QuerysMascotas.consultaGeneral(this.consulta.getCodMascota());
                CtrlPrincipal.veterinario = QuerysVeterinarios.consultaGeneral(this.consulta.getCodVeterinario());
                CtrlPrincipal.medicamento = QuerysMedicamentos.consultaGeneral(this.consulta.getCodMedicamento());
                
                RegistroConsultasPanel registro = new RegistroConsultasPanel();
                CtrlRegConsultas rc = new CtrlRegConsultas(frm, registro, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysConsultas.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un cliente");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaConsultas())) {
            isSelected = true;
           this.consulta = getConsulta();
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
