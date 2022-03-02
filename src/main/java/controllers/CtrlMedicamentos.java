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
import models.Medicamentos;
import models.Proveedores;
import querys.QuerysMedicamentos;
import querys.QuerysProveedores;
import views.MedicamentosPanel;
import views.FrmPrincipal;
import views.RegistroConsultasPanel;
import views.RegistroMedicamentosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlMedicamentos implements MouseListener, DocumentListener{
    
    FrmPrincipal frm;
    MedicamentosPanel panel;
    Medicamentos medicamento;
    ArrayList<Medicamentos> miLista;
    ArrayList<Proveedores> proList;
    String titulos[] = {"Id", "Nombre", "Precio", "Proveedor"};
    String info[][];
    boolean isSelected;
    boolean condicion;
    private static final int TIEMPO_BUSCAR = 300;
    private Timer timer_buscar;

    //Constructor de la clase que recibe el JFrame principal, el panel que será mostrado y la condición
    public CtrlMedicamentos(FrmPrincipal frm, MedicamentosPanel p, boolean condicion) {
        this.frm = frm;
        this.panel = p;
        this.condicion = condicion;
        //Cargamos el panel
        CtrlPrincipal.showContentPanel(frm, p);
        
        this.panel.getCampoBuscar().getDocument().addDocumentListener(this);
        this.panel.getBtnNuevo().addMouseListener(this);
        this.panel.getBtnEditar().addMouseListener(this);
        this.panel.getBtnEliminar().addMouseListener(this);
        this.panel.getTablaMedicamentos().addMouseListener(this);
        //Si se cumple la condición se muestra solo el botón de seleccionar
        if (condicion) {
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
        this.panel.getTablaMedicamentos().setModel(new DefaultTableModel(info, titulos));
    }
    
    //Método que devuelve una matríz de String con los datos de cada fila y columna de la tabla
    private String[][] obtieneMatriz() {

        miLista = QuerysMedicamentos.consultaGeneral();
        proList = QuerysProveedores.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getPrecio()+ "";
            int cp = miLista.get(x).getCodProveedor();
            String proveedor = leerProveedor(cp);
            informacion[x][3] = proveedor;
        }
        return informacion;
    }
    
    //Método que devuelve el nombre del proveedor para no tener que llenar la tabla
    //con el número del id 
    private String leerProveedor(int cp) {
        String nombre = "";
        for (Proveedores p : proList) {
            if (p.getId() == cp) {
                nombre = p.getNombre();
            }
        }
        return nombre;
    }

    //Método que devuelve una matríz de String con los datos de cada fila y columna 
    //mostrando solamente aquellos resultados que coinciden con la búsqueda
    private String[][] obtieneFiltro() {

        ArrayList<Medicamentos> miLista = QuerysMedicamentos.consultaFiltro(this.panel.getCampoBuscar().getText());
        proList = QuerysProveedores.consultaGeneral();

        String informacion[][] = new String[miLista.size()][4];

        for (int x = 0; x < informacion.length; x++) {
            informacion[x][0] = miLista.get(x).getId() + "";
            informacion[x][1] = miLista.get(x).getNombre() + "";
            informacion[x][2] = miLista.get(x).getPrecio()+ "";
            int cp = miLista.get(x).getCodProveedor();
            String proveedor = leerProveedor(cp);
            informacion[x][3] = proveedor;
        }
        return informacion;
    }
    
    //Método que actualiza la tabla cada 3 segundos filtrando los datos que son mostrados
    public void activarTimer() {
        if ((timer_buscar != null) && timer_buscar.isRunning()) {
            timer_buscar.restart();
        } else {
            timer_buscar = new Timer(TIEMPO_BUSCAR, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer_buscar = null;
                        info = obtieneFiltro();
                        panel.getTablaMedicamentos().setModel(new DefaultTableModel(info, titulos));
                }
            });
            timer_buscar.setRepeats(false);
            timer_buscar.start();
        }
    }
    
    //Método que devuelve el medicamento de la fila seleccionada en la tabla
    private Medicamentos getMedicamento() {
        int id = Integer.parseInt(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 0)));        
        medicamento = QuerysMedicamentos.consultaGeneral(id);
        return medicamento;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(this.panel.getBtnNuevo())) {
            //Si se cumple la condición se activa el botón Seleccionar
            if (condicion) {
                if (isSelected) {
                    CtrlPrincipal.medicamento = getMedicamento();
                    RegistroConsultasPanel rc = new RegistroConsultasPanel();
                    CtrlRegConsultas con = new CtrlRegConsultas(frm, rc, true);
                }else{
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una medicamento");
                }                
            }else {
                //Como no se cumple simplemente abre su panel de registro
                CtrlPrincipal.isNew = true;
                CtrlPrincipal.medicamento = null;
                RegistroMedicamentosPanel registro = new RegistroMedicamentosPanel();
                CtrlRegMedicamentos rc = new CtrlRegMedicamentos(this.frm, registro, false);
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEditar())) {
            if (isSelected) {
                CtrlPrincipal.isNew = false;
                CtrlPrincipal.medicamento = getMedicamento();
                this.medicamento = getMedicamento();
                CtrlPrincipal.proveedor = QuerysProveedores.consultaGeneral(this.medicamento.getCodProveedor());
                RegistroMedicamentosPanel registro = new RegistroMedicamentosPanel();
                CtrlRegMedicamentos rm = new CtrlRegMedicamentos(this.frm, registro, true);
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un medicamento");
            }
        }
        
        if (e.getSource().equals(this.panel.getBtnEliminar())) {
            if (isSelected) {
                int id = Integer.parseInt(String.valueOf(this.panel.getTablaMedicamentos().getValueAt(this.panel.getTablaMedicamentos().getSelectedRow(), 0)));
                QuerysMedicamentos.eliminar(id);
                this.actualizarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado un medicamento");
            }
        }
        
        if (e.getSource().equals(this.panel.getTablaMedicamentos())) {
            //Si está seleccionada una fila se carga el medicamento correspondiente
           isSelected = true;
           this.medicamento = getMedicamento();
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
