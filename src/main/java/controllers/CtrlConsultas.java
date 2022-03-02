/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
public class CtrlConsultas implements MouseListener, DocumentListener{
    
    FrmPrincipal frm;
    ConsultasPanel panel;
    Consultas consulta;
    String titulos[] = {"Id", "Fecha", "Hora","Diagnostico", "Tratamiento", "Medicamento", "Veterinario", "Mascota"};
    String info[][];
    boolean isSelected;
    boolean opcion;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;
    ArrayList<Consultas> miLista;
    ArrayList<Mascotas> listaMascotas;
    ArrayList<Veterinarios> listaVet;
    ArrayList<Medicamentos> listaMed;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlConsultas(FrmPrincipal frm, ConsultasPanel p, boolean opcion) {
        this.frm = frm;
        this.panel = p;
        this.opcion = opcion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaConsultas().addMouseListener(this);
        //Si se cumple la condición se muestra solo el botón de seleccionar
        if (opcion) {
            this.panel.getBtnEditar().setVisible(false);
            this.panel.getBtnEliminar().setVisible(false);
            this.panel.getNuevoLabel().setText("Seleccionar");
        }        
        isSelected = false;
        actualizarTabla();
    }
    
    //Método que actualiza la información de la tabla
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaConsultas().setModel(new DefaultTableModel(info, titulos));
    }
    
    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
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

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
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
    
    //Método que actualiza la tabla cada 3 msm filtrando los datos que son mostrados
    public void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                        info = obtieneFiltro();
                        panel.getTablaConsultas().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
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

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            //Si es una nueva consulta se cargan a null las variables globales
            CtrlPrincipal.isNew = true;
            CtrlPrincipal.consulta= null;
            CtrlPrincipal.mascota = null;
            CtrlPrincipal.veterinario = null;
            CtrlPrincipal.medicamento = null;
            
            if (opcion) {
                if (isSelected) {
                    
                }else{
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una consulta");
                }
            }else {
                RegistroConsultasPanel registro = new RegistroConsultasPanel();
                CtrlRegConsultas rc = new CtrlRegConsultas(frm, registro, false);
            }
            
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
                JOptionPane.showMessageDialog(null, "No ha seleccionado una consulta");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaConsultas().getValueAt(this.panel.getTablaConsultas().getSelectedRow(), 0)));
                System.out.println(id);
                QuerysConsultas.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado una consulta");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaConsultas())) {
            //Si está seleccionada una fila se carga el cliente correspondiente
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

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.activarTimer();//Se llama al método que actualiza la tabla cada 3 ms
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.activarTimer();//Se llama al método que actualiza la tabla cada 3 ms
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.activarTimer();//Se llama al método que actualiza la tabla cada 3 ms
    }
    
    
    
    
}
