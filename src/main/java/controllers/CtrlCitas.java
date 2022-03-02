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
import models.Citas;
import models.Mascotas;
import models.Veterinarios;
import querys.QuerysCitas;
import querys.QuerysMascotas;
import querys.QuerysVeterinarios;
import views.CitasPanel;
import views.FrmPrincipal;
import views.RegistroCitasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlCitas implements MouseListener, DocumentListener{
    
    FrmPrincipal frm;
    CitasPanel panel;
    Citas cita;
    String titulos[] = {"Id", "Fecha", "Hora","Veterinario", "Mascota"};
    String info[][];
    ArrayList<Citas> miLista;
    ArrayList<Mascotas> listaMascotas;
    ArrayList<Veterinarios> listaVet;
    boolean isSelected;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    //Constructor de la clase que recibe el JFrame principal y el panel que será mostrado
    public CtrlCitas(FrmPrincipal frm, CitasPanel p) {
        this.frm = frm;
        this.panel = p;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaCitas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    //Método que actualiza la información de la tabla
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaCitas().setModel(new DefaultTableModel(info, titulos));
    }
    
    
    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
    private String[][] obtieneMatriz() {

        miLista = QuerysCitas.consultaGeneral();
        listaMascotas = QuerysMascotas.consultaGeneral();
        listaVet = QuerysVeterinarios.consultaGeneral();

        String informacion[][] = new String[miLista.size()][5];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getHora()+ "";
            int cv = miLista.get(x).getCodVeterinario();
            int cm = miLista.get(x).getCodMascota();
            String vet = leeVet(cv);
            String masc = leeMascota(cm);
            informacion[x][3] = vet;
            informacion[x][4] = masc;
        }

        return informacion;
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
    private String[][] obtieneFiltro() {

        miLista = QuerysCitas.consultaFiltro(this.panel.getCampoBuscar().getText());
        listaMascotas = QuerysMascotas.consultaGeneral();
        listaVet = QuerysVeterinarios.consultaGeneral();

        String informacion[][] = new String[miLista.size()][5];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getHora()+ "";
            int cv = miLista.get(x).getCodVeterinario();
            int cm = miLista.get(x).getCodMascota();
            String vet = leeVet(cv);
            String masc = leeMascota(cm);
            informacion[x][3] = vet;
            informacion[x][4] = masc;
        }

        return informacion;
    }
    
    //Método que actualiza la tabla cada 3 ms filtrando los datos que son mostrados
    public void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                    info = obtieneFiltro();
                    panel.getTablaCitas().setModel(new DefaultTableModel(info, titulos));
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
    
    //Método que devuelve la cita de la fila seleccionada en la tabla
    private Citas getCita() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaCitas().getValueAt(this.panel.getTablaCitas().getSelectedRow(), 0)));
        cita = QuerysCitas.consultaGeneral(id);
        return cita;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            //Si es una nueva cita se cargan a null las variables globales
            CtrlPrincipal.isNew = true;
            CtrlPrincipal.cita = null;
            CtrlPrincipal.mascota = null;
            CtrlPrincipal.veterinario = null;
            
            //Se abre el panel de registro de citas a false para crear una nueva
            RegistroCitasPanel registro = new RegistroCitasPanel();                        
            CtrlRegCitas rc = new CtrlRegCitas(frm, registro, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                //Si es una actualización de datos se carga la cita seleccionada
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.cita = getCita();
                System.out.println(CtrlPrincipal.cita.getHora());
                CtrlPrincipal.mascota = QuerysMascotas.consultaGeneral(CtrlPrincipal.cita.getCodMascota());
                CtrlPrincipal.veterinario = QuerysVeterinarios.consultaGeneral(CtrlPrincipal.cita.getCodVeterinario());
                
                //Se abre el panel de registro de citas a true para que cargue los datos 
                RegistroCitasPanel registro = new RegistroCitasPanel();                        
                CtrlRegCitas rc = new CtrlRegCitas(frm, registro, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado una cita");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            //Se comprueba que una fila de la tabla esté seleccionada
            if (isSelected) {
                //Se llama a la clase de consultas para eliminar la cita con el id seleccionado
                QuerysCitas.eliminar(this.cita.getId());
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado una cita");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaCitas())) {
            //Si está seleccionada una fila se carga la cita correspondiente
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
