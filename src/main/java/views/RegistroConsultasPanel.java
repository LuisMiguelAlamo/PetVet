/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.toedter.calendar.JDateChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Luis Miguel
 */
public class RegistroConsultasPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClientePanel
     */
    public RegistroConsultasPanel() {
        initComponents();
    }

    public JDateChooser getTxtFecha() {
        return txtFecha;
    }
        

    public JTextField getTxtHora() {
        return txtHora;
    }

    public JTextField getTxtMascota() {
        return txtMascota;
    }

    public JTextField getTxtVeterinario() {
        return txtVeterinario;
    }

    public JTextField getTxtMedicamento() {
        return txtMedicamento;
    }

    public void setTxtMedicamento(JTextField txtMedicamento) {
        this.txtMedicamento = txtMedicamento;
    }
        

    public JTextArea getAreaDiagnóstico() {
        return areaDiagnóstico;
    }

    public void setAreaDiagnóstico(JTextArea areaDiagnóstico) {
        this.areaDiagnóstico = areaDiagnóstico;
    }

    public JTextArea getAreaTratamiento() {
        return areaTratamiento;
    }

    public void setAreaTratamiento(JTextArea areaTratamiento) {
        this.areaTratamiento = areaTratamiento;
    }
    
    

    public JPanel getBtnMascota() {
        return btnMascota;
    }

    public JPanel getBtnVeterinario() {
        return btnVeterinario;
    }    

    public JPanel getBtnMedicamento() {
        return btnMedicamento;
    }
    
    
    
    public JPanel getBtnGuardar() {
        return btnGuardar;
    }

    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        labelVeterinario = new javax.swing.JLabel();
        txtVeterinario = new javax.swing.JTextField();
        labelMascota = new javax.swing.JLabel();
        txtMascota = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnMascota = new javax.swing.JPanel();
        mascotaLabel = new javax.swing.JLabel();
        btnVeterinario = new javax.swing.JPanel();
        veterinarioLabel = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        txtMedicamento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnMedicamento = new javax.swing.JPanel();
        medicamentoLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDiagnóstico = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaTratamiento = new javax.swing.JTextArea();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(51, 204, 0));
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Guardar");

        javax.swing.GroupLayout btnGuardarLayout = new javax.swing.GroupLayout(btnGuardar);
        btnGuardar.setLayout(btnGuardarLayout);
        btnGuardarLayout.setHorizontalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        btnGuardarLayout.setVerticalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        labelFecha.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        labelFecha.setText("Fecha");

        labelVeterinario.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        labelVeterinario.setText("Veterinario");

        txtVeterinario.setEditable(false);

        labelMascota.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        labelMascota.setText("Mascota");

        txtMascota.setEditable(false);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnMascota.setBackground(new java.awt.Color(51, 204, 0));
        btnMascota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        mascotaLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mascotaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mascotaLabel.setText("Mascota");

        javax.swing.GroupLayout btnMascotaLayout = new javax.swing.GroupLayout(btnMascota);
        btnMascota.setLayout(btnMascotaLayout);
        btnMascotaLayout.setHorizontalGroup(
            btnMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mascotaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        btnMascotaLayout.setVerticalGroup(
            btnMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mascotaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        btnVeterinario.setBackground(new java.awt.Color(51, 204, 0));
        btnVeterinario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        veterinarioLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        veterinarioLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        veterinarioLabel.setText("Veterinario");

        javax.swing.GroupLayout btnVeterinarioLayout = new javax.swing.GroupLayout(btnVeterinario);
        btnVeterinario.setLayout(btnVeterinarioLayout);
        btnVeterinarioLayout.setHorizontalGroup(
            btnVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(veterinarioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        btnVeterinarioLayout.setVerticalGroup(
            btnVeterinarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(veterinarioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel1.setText("Hora");

        txtMedicamento.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel2.setText("Medicamento");

        btnMedicamento.setBackground(new java.awt.Color(51, 204, 0));
        btnMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        medicamentoLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        medicamentoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        medicamentoLabel.setText("Medicamento");

        javax.swing.GroupLayout btnMedicamentoLayout = new javax.swing.GroupLayout(btnMedicamento);
        btnMedicamento.setLayout(btnMedicamentoLayout);
        btnMedicamentoLayout.setHorizontalGroup(
            btnMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(medicamentoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnMedicamentoLayout.setVerticalGroup(
            btnMedicamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(medicamentoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel3.setText("Diagnóstico");

        areaDiagnóstico.setColumns(20);
        areaDiagnóstico.setRows(5);
        jScrollPane1.setViewportView(areaDiagnóstico);

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel5.setText("Tratamiento");

        areaTratamiento.setColumns(20);
        areaTratamiento.setRows(5);
        jScrollPane2.setViewportView(areaTratamiento);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(682, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelVeterinario, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtVeterinario, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelMascota, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtMascota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addComponent(txtMedicamento, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelFecha)
                                .addGap(83, 83, 83))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtHora, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(201, 201, 201))
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2))
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(labelFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelVeterinario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(labelMascota)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDiagnóstico;
    private javax.swing.JTextArea areaTratamiento;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JPanel btnMascota;
    private javax.swing.JPanel btnMedicamento;
    private javax.swing.JPanel btnVeterinario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelMascota;
    private javax.swing.JLabel labelVeterinario;
    private javax.swing.JLabel mascotaLabel;
    private javax.swing.JLabel medicamentoLabel;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtMascota;
    private javax.swing.JTextField txtMedicamento;
    private javax.swing.JTextField txtVeterinario;
    private javax.swing.JLabel veterinarioLabel;
    // End of variables declaration//GEN-END:variables
}
