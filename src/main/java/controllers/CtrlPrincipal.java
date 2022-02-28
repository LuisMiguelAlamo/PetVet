 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import models.Acceso;
import models.Citas;
import models.Clientes;
import models.Consultas;
import models.Facturas;
import models.Mascotas;
import models.Medicamentos;
import models.Proveedores;
import models.Veterinarios;
import views.CitasPanel;
import views.ClientePanel;
import views.ConsultasPanel;
import views.FacturasPanel;
import views.FrmPrincipal;
import views.InicioPanel;
import views.MascotasPanel;
import views.MedicamentosPanel;
import views.ProveedoresPanel;
import views.UsuariosPanel;
import views.VeterinariosPanel;

/**
 *
 * @author Luis Miguel
 */
public class CtrlPrincipal implements MouseListener, MouseMotionListener{
    FrmPrincipal frm;
    int xMouse, yMouse;
    boolean condicion;
    //Variable para la fecha del programa
    LocalDateTime dateTime = LocalDateTime.now();
    String fecha = "Hoy es:  "+ dateTime.getDayOfMonth() + " / "+ dateTime.getMonthValue()+ " / "+ dateTime.getYear();
    //Variables para la validación de las expresiones regulares
    public static Pattern pEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    public static Pattern pTel = Pattern.compile("[0-9]{9}");
    public static Pattern pCP = Pattern.compile("0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3}");
    public static Pattern pPrecio = Pattern.compile("[0-9]+[.]?[0-9]{1,2}");
    public static Pattern pIGIC = Pattern.compile("[0-9]+[.]?[0-9]{1,2}");
    public static Matcher mEmail;
    public static Matcher mTel;
    public static Matcher mCP;
    public static Matcher mPrecio;
    public static Matcher mIGIC;
    //Variables para acumular los datos de los modelos
    
    public static Acceso usuario;
    public static Clientes cliente;
    public static Proveedores proveedor;
    public static Veterinarios veterinario;
    public static Medicamentos medicamento;
    public static Mascotas mascota;
    public static Citas cita;
    public static Consultas consulta;
    public static Facturas factura;
    public static boolean isNew;
    public static int eleccion; //citas = 1, consultas = 2, mascotas = 3, facturas = 4, usuario = 5

    //Constructor de la clase
    public CtrlPrincipal(boolean condicion) {
        frm = new FrmPrincipal();
        this.frm.getBtnPrincipal().addMouseListener(this);
        this.frm.getBtnClientes().addMouseListener(this);
        this.frm.getBtnMascotas().addMouseListener(this);
        this.frm.getBtnConsultas().addMouseListener(this);
        this.frm.getBtnCitas().addMouseListener(this);
        this.frm.getBtnFacturas().addMouseListener(this);
        this.frm.getBtnMedicamentos().addMouseListener(this);
        this.frm.getBtnVeterinarios().addMouseListener(this);
        this.frm.getBtnProveedores().addMouseListener(this);
        this.frm.getBtnUsuarios().addMouseListener(this);
        this.frm.getHeaderPanel().addMouseMotionListener(this);
        this.frm.getExitPanel().addMouseListener(this);
        this.frm.getExitLabel().addMouseListener(this);
        
        this.condicion = condicion;
        if (condicion) {
            cargaAdmin();
        }else{
            this.frm.getBtnMedicamentos().setVisible(false);
            this.frm.getBtnVeterinarios().setVisible(false);
            this.frm.getBtnProveedores().setVisible(false);
            this.frm.getBtnUsuarios().setVisible(false);           
        }
        
        InicioPanel ip = new InicioPanel();
        CtrlInicio ci = new CtrlInicio(frm, ip);
        
        this.frm.getLabelFecha().setText(String.valueOf(fecha));
        frm.setVisible(true);
    }
    
    
    public static void showContentPanel(FrmPrincipal frm,JPanel p){
        p.setSize(790, 480);
        p.setLocation(0, 0);
        frm.getContentPanel().removeAll();
        frm.getContentPanel().add(p, BorderLayout.CENTER);
        frm.getContentPanel().revalidate();
        frm.getContentPanel().repaint();
    }
    
    
    private  void cargaAdmin(){
        this.frm.getTopPanel().setBackground(new Color(0, 102, 102));
        this.frm.getSidePanel().setBackground(new Color(0, 51, 51));
        this.frm.getBtnPrincipal().setBackground(new Color(0, 51, 51));
        this.frm.getBtnClientes().setBackground(new Color(0, 51, 51));
        this.frm.getBtnMascotas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnConsultas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnCitas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnFacturas().setBackground(new Color(0, 51, 51));
        this.frm.getBtnMedicamentos().setBackground(new Color(0, 51, 51));
        this.frm.getBtnMedicamentos().setBackground(new Color(0, 51, 51));
        this.frm.getBtnVeterinarios().setBackground(new Color(0, 51, 51));
        this.frm.getBtnProveedores().setBackground(new Color(0, 51, 51));
        this.frm.getBtnUsuarios().setBackground(new Color(0, 51, 51));
        
        this.frm.getBtnMedicamentos().setVisible(true);
        this.frm.getBtnProveedores().setVisible(true);
        this.frm.getBtnUsuarios().setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.frm.getBtnPrincipal())) {
            InicioPanel ip = new InicioPanel();
            CtrlInicio ci = new CtrlInicio(frm, ip);
        }
        if (e.getSource().equals(this.frm.getBtnClientes())) {
            ClientePanel cp = new ClientePanel();
            CtrlClientes cli = new CtrlClientes(frm, cp, false);
        }
        if (e.getSource().equals(this.frm.getBtnMascotas())) {
            MascotasPanel masPan = new MascotasPanel();
            CtrlMascotas mascotas = new CtrlMascotas(frm, masPan, false);
        }
        if (e.getSource().equals(this.frm.getBtnConsultas())) {
            ConsultasPanel consPan = new ConsultasPanel();
            CtrlConsultas consultas = new CtrlConsultas(frm, consPan, false);
        }
        if (e.getSource().equals(this.frm.getBtnCitas())) {
            CitasPanel cpa = new CitasPanel();
            CtrlCitas citas = new CtrlCitas(frm, cpa);
        }
        if (e.getSource().equals(this.frm.getBtnFacturas())) {
            FacturasPanel facPan = new FacturasPanel();
            CtrlFacturas facturas = new CtrlFacturas(frm, facPan);
        }
        if (e.getSource().equals(this.frm.getBtnMedicamentos())) {
            MedicamentosPanel medPan = new MedicamentosPanel();
            CtrlMedicamentos medicamentos = new CtrlMedicamentos(frm, medPan, false);
        }
        if (e.getSource().equals(this.frm.getBtnVeterinarios())) {
            VeterinariosPanel vetPan = new VeterinariosPanel();
            CtrlVeterinarios veterinarios = new CtrlVeterinarios(frm, vetPan, false);
        }
        if (e.getSource().equals(this.frm.getBtnProveedores())) {
            ProveedoresPanel proPan = new ProveedoresPanel();
            CtrlProveedores proveedores = new CtrlProveedores(frm, proPan, false);
        }
        if (e.getSource().equals(this.frm.getBtnUsuarios())) {
            UsuariosPanel usuPan = new UsuariosPanel();
            CtrlUsuarios usuarios = new CtrlUsuarios(frm, usuPan, false);
        }
        if (e.getSource().equals(this.frm.getExitLabel())) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(this.frm.getHeaderPanel())) {
            this.xMouse = e.getX();
            this.yMouse = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(this.frm.getExitLabel())) {
            this.frm.getExitPanel().setBackground(Color.red);
            this.frm.getExitLabel().setForeground(Color.WHITE);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(this.frm.getExitLabel())) {
            this.frm.getExitPanel().setBackground(Color.WHITE);
            this.frm.getExitLabel().setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(this.frm.getHeaderPanel())) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            this.frm.setLocation(x - xMouse, y - yMouse);
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    
}
