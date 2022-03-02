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
import models.Clientes;
import models.Facturas;
import querys.QuerysClientes;
import querys.QuerysFacturas;
import views.FacturasPanel;
import views.FrmPrincipal;
import views.RegistroFacturasPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlFacturas implements MouseListener, DocumentListener {
    
    FrmPrincipal frm;
    FacturasPanel panel;
    Facturas factura;
    String titulos[] = {"No. Factura", "Fecha","Total", "% IGIC", "total con IGIC", "Cliente"};
    String info[][];
    boolean isSelected;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;
    ArrayList<Facturas> miLista;
    ArrayList<Clientes> cliList;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado
    public CtrlFacturas(FrmPrincipal frm, FacturasPanel p) {
        this.frm = frm;
        this.panel = p;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaFacturas().addMouseListener(this);
        
        isSelected = false;
        actualizarTabla();
    }
    
    //Método que actualiza la información de la tabla
    private void actualizarTabla() {
        info = obtieneMatriz();
        this.panel.getTablaFacturas().setModel(new DefaultTableModel(info, titulos));
    }
    
    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
    private String[][] obtieneMatriz() {

        miLista = QuerysFacturas.consultaGeneral();
        cliList = QuerysClientes.consultaGeneral();

        String informacion[][] = new String[miLista.size()][6];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha() + "";
            informacion[x][2] = miLista.get(x).getTotal()+ "";
            informacion[x][3] = miLista.get(x).getIGIC()+ "";
            informacion[x][4] = miLista.get(x).getTotalConIGIC()+ "";
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][5] = cli;
        }
        return informacion;
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
    private String[][] obtieneFiltro() {

        miLista = QuerysFacturas.consultaFiltro(this.panel.getCampoBuscar().getText());
        cliList = QuerysClientes.consultaGeneral();
        
        String informacion[][] = new String[miLista.size()][6];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getFecha()+ "";
            informacion[x][2] = miLista.get(x).getTotal()+ "";
            informacion[x][3] = miLista.get(x).getIGIC()+ "";
            informacion[x][4] = miLista.get(x).getTotalConIGIC()+ "";
            int cc = miLista.get(x).getCodCliente();
            String cli = leerClientes(cc);

            informacion[x][5] = cli;
        }
        return informacion;
    }
    
    //Método que devuelve el nombre del cliente para no tener que llenar la tabla
    //con el número del id 
    private String leerClientes(int cc) {
        String nombre = "";
        for (Clientes c : cliList) {
            if (c.getId() == cc) {
                nombre = c.getNombre();
            }
        }
        return nombre;
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
                        panel.getTablaFacturas().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }
    
    //Método que devuelve la factura de la fila seleccionada en la tabla
    private Facturas setFactura() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 0)));
        factura = QuerysFacturas.consultaGeneral(id);
        return factura;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            //Si es una nueva consulta se cargan a null las variables globales
            CtrlPrincipal.isNew = true;
            CtrlPrincipal.cliente = null;
            RegistroFacturasPanel registro = new RegistroFacturasPanel();                        
            CtrlRegFacturas rc = new CtrlRegFacturas(frm, registro, false);
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.factura = setFactura();
                CtrlPrincipal.cliente = QuerysClientes.consultaGeneral(CtrlPrincipal.factura.getCodCliente());
                RegistroFacturasPanel registro = new RegistroFacturasPanel();  
                CtrlRegFacturas rc = new CtrlRegFacturas(frm, registro, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado una factura");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaFacturas().getValueAt(this.panel.getTablaFacturas().getSelectedRow(), 0)));
                QuerysFacturas.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado una factura");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaFacturas())) {
            //Si está seleccionada una fila se carga la factura correspondiente
            isSelected = true;
           this.factura = setFactura();
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
