
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;   
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ventanaAdmin extends javax.swing.JFrame {

    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private DefaultTableModel mMedicos;
    private DefaultTableModel mRecep;

    public ventanaAdmin(String usuarioId) {
    this.usuarioId = usuarioId;
    initComponents();

    // ================== MODELOS DE TABLAS ==================
    String[] colsMed = {
            "Id", "Nombre", "Apellido1", "Apellido2",
            "FechaContratacion", "FechaNacimiento",
            "Especialidad", "NumCedula", "NumTelefonico",
            "Domicilio", "Correo"
    };

    String[] colsRec = {
            "Id", "Nombre", "Apellido1", "Apellido2",
            "FechaContratacion", "Telefono", "Correo"
    };

    mMedicos = new DefaultTableModel(null, colsMed) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    mRecep = new DefaultTableModel(null, colsRec) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    // tblCita = MÉDICOS
    tblCita.setModel(mMedicos);
    // tblRecep = RECEPCIONISTAS
    tblRecep.setModel(mRecep);

    tblCita.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tblRecep.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tblCita.getColumnModel().getColumn(9).setPreferredWidth(200);
    tblCita.getColumnModel().getColumn(10).setPreferredWidth(180);
    tblCita.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

tblRecep.getColumnModel().getColumn(5).setPreferredWidth(180);
tblRecep.getColumnModel().getColumn(6).setPreferredWidth(200);
tblRecep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    con = ConexionSQL.ConexionSQLServer();
    llenarPersonal();

    setSize(1200, 800);
    this.setLocationRelativeTo(this);

    lblFecha.setText(obtenerFecha());
}
  
    private void buscarPersonal(String texto) {
    if (con == null) {
        JOptionPane.showMessageDialog(this, "Conexión nula.");
        return;
    }
    String like = "%" + texto.trim() + "%";

    // limpiamos siempre ambas
    mMedicos.setRowCount(0);
    mRecep.setRowCount(0);

    // ================= SOLO RECEPCIONISTAS =================
    if (cbRecep.isSelected()) {
        String sqlRec =
                "SELECT idRecepcionista, nombre, apellido1, apellido2, " +
                "       CONVERT(VARCHAR(10), fechaContratacion, 120) AS fechaContratacion, " +
                "       telefono, correo " +
                "FROM recepcionista " +
                "WHERE nombre LIKE ? OR apellido1 LIKE ? OR apellido2 LIKE ? OR correo LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(sqlRec)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mRecep.addRow(new Object[]{
                            rs.getInt("idRecepcionista"),
                            rs.getString("nombre"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2"),
                            rs.getString("fechaContratacion"),
                            rs.getString("telefono"),
                            rs.getString("correo")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error buscar recepcionistas:\n" + ex.getMessage());
        }
        return; // IMPORTANTE: salimos aquí
    }

    // ================= SOLO MÉDICOS =================
    String sqlMed =
            "SELECT idMedico, nombreMed, apellido1, apellido2, " +
            "       CONVERT(VARCHAR(10), fechaContra, 120) AS fechaContra, " +
            "       CONVERT(VARCHAR(10), fechaNac, 120) AS fechaNac, " +
            "       Especialidad, NumCedula, NumTelefonico, Domicilio, Correo " +
            "FROM medico " +
            "WHERE nombreMed LIKE ? OR apellido1 LIKE ? OR apellido2 LIKE ? OR Correo LIKE ?";

    try (PreparedStatement ps = con.prepareStatement(sqlMed)) {
        ps.setString(1, like);
        ps.setString(2, like);
        ps.setString(3, like);
        ps.setString(4, like);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mMedicos.addRow(new Object[]{
                        rs.getInt("idMedico"),
                        rs.getString("nombreMed"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("fechaContra"),
                        rs.getString("fechaNac"),
                        rs.getString("Especialidad"),
                        rs.getString("NumCedula"),
                        rs.getString("NumTelefonico"),
                        rs.getString("Domicilio"),
                        rs.getString("Correo")
                });
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error buscar médicos:\n" + ex.getMessage());
    }
}







    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblAgregarCita1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        reloj11 = new Reloj1();
        btnRefresh = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRecep = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        cbRecep = new javax.swing.JCheckBox();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        lblSalir.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblSalir.setText("Cerrar sesión");
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });

        lblAgregarCita1.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblAgregarCita1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar-usuario.png"))); // NOI18N
        lblAgregarCita1.setText("Agregar ");
        lblAgregarCita1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAgregarCita1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblAgregarCita1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblSalir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(155, 155, 155)
                .addComponent(lblAgregarCita1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
                .addComponent(lblSalir)
                .addGap(142, 142, 142))
        );

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tipo", "Nombre", "Apellido", "Apellido2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCitaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCita);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        jLabel2.setText("Registros");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar ");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Recepcionista");

        tblRecep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblRecep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tipo", "Nombre", "Apellido", "Apellido2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRecep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRecepMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblRecep);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Medicos ");

        cbRecep.setText("Recepcionista");
        cbRecep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRecepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnRefresh)
                                        .addGap(56, 56, 56)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel2))))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbRecep)
                                    .addComponent(lblFecha)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(jLabel4)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(498, 1470, Short.MAX_VALUE)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(57, 57, 57))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRefresh)
                                    .addComponent(cbRecep))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(285, 285, 285)))
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        llenarPersonal();
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String q = txtBuscar.getText().trim();
            if (q.isEmpty()) llenarPersonal();
            else buscarPersonal(q);
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked

        if (evt.getClickCount() < 1) return;
    int row = tblCita.getSelectedRow();
    if (row < 0) return;

    String id = mMedicos.getValueAt(row, 0).toString();

    String mensaje =
            "Nombre: " + mMedicos.getValueAt(row,1) + "\n" +
            "Apellido1: " + mMedicos.getValueAt(row,2) + "\n" +
            "Apellido2: " + mMedicos.getValueAt(row,3) + "\n" +
            "Fecha contratación: " + mMedicos.getValueAt(row,4) + "\n" +
            "Fecha nacimiento: " + mMedicos.getValueAt(row,5) + "\n" +
            "Especialidad: " + mMedicos.getValueAt(row,6) + "\n" +
            "Cédula: " + mMedicos.getValueAt(row,7) + "\n" +
            "Teléfono: " + mMedicos.getValueAt(row,8) + "\n" +
            "Domicilio: " + mMedicos.getValueAt(row,9) + "\n" +
            "Correo: " + mMedicos.getValueAt(row,10);

    Object[] opciones = {"Editar","Eliminar","Cancelar"};
    int op = JOptionPane.showOptionDialog(
            this, mensaje, "Médico " + id,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, opciones, opciones[0]
    );

    if (op == 0) { // EDITAR
        Object[] datos = new Object[11];
        for (int i = 0; i < 11; i++) {
            datos[i] = mMedicos.getValueAt(row, i);
        }
        EditarPersonaDialog dlg = new EditarPersonaDialog(this, "Medico", datos);
        dlg.setVisible(true);

        if (dlg.fueGuardado()) {
            actualizarMedico(id, dlg);
        }
    } else if (op == 1) { // ELIMINAR
    manejarEliminacionMedico(id);
    }


    }//GEN-LAST:event_tblCitaMouseClicked

    private void lblAgregarCita1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAgregarCita1MouseClicked
        registroAdmin ra = new registroAdmin();
        ra.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblAgregarCita1MouseClicked

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void tblRecepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRecepMouseClicked
        if (evt.getClickCount() < 1) return;
    int row = tblRecep.getSelectedRow();
    if (row < 0) return;

    String id = mRecep.getValueAt(row, 0).toString();

    String mensaje =
            "Nombre: " + mRecep.getValueAt(row,1) + "\n" +
            "Apellido1: " + mRecep.getValueAt(row,2) + "\n" +
            "Apellido2: " + mRecep.getValueAt(row,3) + "\n" +
            "Fecha contratación: " + mRecep.getValueAt(row,4) + "\n" +
            "Teléfono: " + mRecep.getValueAt(row,5) + "\n" +
            "Correo: " + mRecep.getValueAt(row,6);

    Object[] opciones = {"Editar","Eliminar","Cancelar"};
    int op = JOptionPane.showOptionDialog(
            this, mensaje, "Recepcionista " + id,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, opciones, opciones[0]
    );

    if (op == 0) { // EDITAR
        Object[] datos = new Object[7];
        for (int i = 0; i < 7; i++) {
            datos[i] = mRecep.getValueAt(row, i);
        }
        EditarPersonaDialog dlg = new EditarPersonaDialog(this, "Recepcionista", datos);
        dlg.setVisible(true);

        if (dlg.fueGuardado()) {
            actualizarRecepcionista(id, dlg);
        }
    } else if (op == 1) { // ELIMINAR
        eliminarRecepcionista(id);
    }
    }//GEN-LAST:event_tblRecepMouseClicked

    private void cbRecepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRecepActionPerformed
            String q = txtBuscar.getText().trim();
    if (q.isEmpty()) {
        // si no hay texto, recarga todo
        llenarPersonal();
    } else {
        // si hay texto, vuelve a buscar pero ahora con el nuevo estado del checkbox
        buscarPersonal(q);
    }

    }//GEN-LAST:event_cbRecepActionPerformed
    public static String obtenerIdMed(String correo) {
        con = ConexionSQL.ConexionSQLServer();
        Statement stmt2;
        ResultSet resultadoMed;
        String identificadorMed = "";

        try {
            stmt2 = con.createStatement();
            String idMed = "select med.idMedico \n"
                    + "from medico med\n"
                    + "inner join usuario u on (med.Correo = u.correo)\n"
                    + "where med.Correo = " + "'" + correo + "'";

            resultadoMed = stmt2.executeQuery(idMed);

            while (resultadoMed.next()) {
                identificadorMed = resultadoMed.getString("idMedico");
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return identificadorMed;
    }
   
    private String resolverId(String tipo, String nombre, String ape1, String ape2) throws SQLException {
    String sql;
    if ("Medico".equalsIgnoreCase(tipo)) {
        sql = "SELECT TOP 1 CAST(idMedico AS VARCHAR(50)) AS id " +
              "FROM medico WHERE nombreMed = ? AND apellido1 = ? AND apellido2 = ? ORDER BY idMedico";
    } else if ("Recepcionista".equalsIgnoreCase(tipo)) {
        sql = "SELECT TOP 1 CAST(idRecepcionista AS VARCHAR(50)) AS id " +
              "FROM recepcionista WHERE nombreRec = ? AND apellido1 = ? AND apellido2 = ? ORDER BY idRecepcionista";
    } else {
        return null;
    }

    try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nombre);
        ps.setString(2, ape1);
        ps.setString(3, ape2);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString("id") : null;
        }
    }
}

    private void editarPersonal(String tipo, String nombre, String ape1, String ape2) {
    try {
        String id = resolverId(tipo, nombre, ape1, ape2);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el registro para editar.");
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog(this, "Nombre:", nombre);
        if (nuevoNombre == null) return;
        String nuevoApe1   = JOptionPane.showInputDialog(this, "Apellido paterno:", ape1);
        if (nuevoApe1 == null) return;
        String nuevoApe2   = JOptionPane.showInputDialog(this, "Apellido materno:", ape2);
        if (nuevoApe2 == null) return;

        String sql;
        if ("Medico".equalsIgnoreCase(tipo)) {
            sql = "UPDATE medico SET nombreMed = ?, apellido1 = ?, apellido2 = ? WHERE idMedico = ?";
        } else {
            sql = "UPDATE recepcionista SET nombreRec = ?, apellido1 = ?, apellido2 = ? WHERE idRecepcionista = ?";
        }

        try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre.trim());
            ps.setString(2, nuevoApe1.trim());
            ps.setString(3, nuevoApe2.trim());
            ps.setString(4, id);
            int n = ps.executeUpdate();
            if (n > 0) JOptionPane.showMessageDialog(this, "Actualizado correctamente.");
        }
        llenarPersonal();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
    }
}
    private void manejarEliminacionMedico(String idMedico) {
    if (con == null) {
        JOptionPane.showMessageDialog(this, "Conexión nula.");
        return;
    }

    try {
        // 1) Contar citas "activas" del médico
        String sqlCount = "SELECT COUNT(*) FROM CITA WHERE idMedico = ?";
        int total = 0;

        try (PreparedStatement ps = con.prepareStatement(sqlCount)) {
            ps.setInt(1, Integer.parseInt(idMedico));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        }

        if (total == 0) {
            // 2) No tiene citas → mensaje + botones Eliminar / Cancelar
            Object[] opciones = { "Eliminar", "Cancelar" };
            int op = JOptionPane.showOptionDialog(
                    this,
                    "Este médico no tiene citas disponibles.\n\n¿Deseas eliminarlo?",
                    "Eliminar médico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (op == 0) {
                // El usuario confirmó
                eliminarMedicoSeguro(idMedico);
            }

        } else {
            // 3) SÍ tiene citas → abrir diálogo para reasignar
            ReasignarCitasDialog dlg = new ReasignarCitasDialog(this, con, Integer.parseInt(idMedico));
            dlg.setVisible(true);

            // Si el diálogo indica que ya reasignó todas las citas,
            // entonces ahora sí lo puedes eliminar
            if (dlg.fueReasignadoTodo()) {
                eliminarMedicoSeguro(idMedico);
            }
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error verificando citas del médico:\n" + ex.getMessage());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "idMedico no es numérico: " + idMedico);
    }
}

    private void eliminarPersonal(String tipo, String nombre, String ape1, String ape2) {
    try {
        String id = resolverId(tipo, nombre, ape1, ape2);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el registro para eliminar.");
            return;
        }

        int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return;

        String sql;
        if ("Medico".equalsIgnoreCase(tipo)) {
            sql = "DELETE FROM medico WHERE idMedico = ?";
        } else {
            sql = "DELETE FROM recepcionista WHERE idRecepcionista = ?";
        }

        try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            int n = ps.executeUpdate();
            if (n > 0) JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
        }
        llenarPersonal();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
    }
}


    private String obtenerFecha() {
        String fecha = "";
        LocalDateTime hoy = LocalDateTime.now();
        fecha = hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();
        return fecha;
    }
   private void editarPorTipo(String tipo, String id, int row) {
    try {
        // El correo se queda tal como está, NO se pide ni se cambia
        String corr = m.getValueAt(row,10) == null ? "" : m.getValueAt(row,10).toString();

        if ("Medico".equalsIgnoreCase(tipo)) {
            String nombre = pedir("Nombre",   m.getValueAt(row,2)); if (nombre == null) return;
            String ap1    = pedir("Apellido1",m.getValueAt(row,3)); if (ap1    == null) return;
            String ap2    = pedir("Apellido2",m.getValueAt(row,4)); if (ap2    == null) return;
            String fecha  = pedir("Fecha nacimiento (YYYY-MM-DD)", m.getValueAt(row,5)); if (fecha == null) return;
            String tel    = pedir("Teléfono", m.getValueAt(row,6)); if (tel    == null) return;
            String esp    = pedir("Especialidad", m.getValueAt(row,7)); if (esp == null) return;
            String ced    = pedir("Número de cédula", m.getValueAt(row,8)); if (ced == null) return;
            String dom    = pedir("Domicilio", m.getValueAt(row,9)); if (dom == null) return;

            String sql = "UPDATE medico SET nombreMed=?, apellido1=?, apellido2=?, fechaNac=?, " +
                         "NumTelefonico=?, Especialidad=?, NumCedula=?, Domicilio=?, Correo=? " +
                         "WHERE idMedico=?";
            try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,nombre.trim());
                ps.setString(2,ap1.trim());
                ps.setString(3,ap2.trim());
                ps.setDate  (4, java.sql.Date.valueOf(fecha));
                ps.setString(5,tel.trim());
                ps.setString(6,esp.trim());
                ps.setString(7,ced.trim());
                ps.setString(8,dom.trim());
                // correo se manda tal cual estaba
                ps.setString(9,corr.trim());
                ps.setString(10,(id));
                ps.executeUpdate();
            }

        } else { // Recepcionista
            String nombre = pedir("Nombre",   m.getValueAt(row,2)); if (nombre == null) return;
            String ap1    = pedir("Apellido1",m.getValueAt(row,3)); if (ap1    == null) return;
            String ap2    = pedir("Apellido2",m.getValueAt(row,4)); if (ap2    == null) return;
            String fecha  = pedir("Fecha contratación (YYYY-MM-DD)", m.getValueAt(row,5)); if (fecha == null) return;
            String tel    = pedir("Teléfono", m.getValueAt(row,6)); if (tel    == null) return;
            // aquí TAMPOCO pedimos correo, usamos el mismo

            String sql = "UPDATE recepcionista SET nombre=?, apellido1=?, apellido2=?, " +
                         "fechaContratacion=?, telefono=?, correo=? WHERE idRecepcionista=?";
            try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,nombre.trim());
                ps.setString(2,ap1.trim());
                ps.setString(3,ap2.trim());
                ps.setDate  (4, java.sql.Date.valueOf(fecha));
                ps.setString(5,tel.trim());
                // correo igual que antes
                ps.setString(6,corr.trim());
                ps.setString   (7,(id));
                ps.executeUpdate();
            }
        }

        JOptionPane.showMessageDialog(this, "Actualizado correctamente.");
        llenarPersonal();

    } catch (SQLException | IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
    }
}

private String pedir(String label, Object valorActual) {
    return JOptionPane.showInputDialog(this, label + ":", valorActual == null ? "" : valorActual.toString());
}
private void eliminarPorTipo(String tipo, String id) {
    int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
    if (conf != JOptionPane.YES_OPTION) return;

    String sql = "Medico".equalsIgnoreCase(tipo)
            ? "DELETE FROM medico WHERE idMedico=?"
            : "DELETE FROM recepcionista WHERE idRecepcionista=?";
    try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1,(id));
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
        llenarPersonal();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
    }
}

    

    private void buscarPaciente(String nombre) {
        String busqueda = nombre;
        String query;
        con = ConexionSQL.ConexionSQLServer();
        ResultSet rs;
        m.setRowCount(0);

        query = "select cit.fecha, cit.hora, pac.nombrePac, pac.apellido1, pac.apellido2, pac.numeroSeguro, cit.estatus\n"
                + "from cita cit\n"
                + "inner join paciente pac on (pac.numeroSeguro = cit.numeroSeguro)\n"
                + "WHERE pac.nombrePac LIKE '%" + busqueda + "%'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            Object R[] = new Object[7];

            while (rs.next()) {
                R[0] = rs.getObject("fecha");
                R[1] = rs.getObject("hora");
                R[2] = rs.getObject("nombrePac");
                R[3] = rs.getObject("apellido1");
                R[4] = rs.getObject("apellido2");
                R[5] = rs.getObject("numeroSeguro");
                R[6] = rs.getObject("estatus");
                m.addRow(R);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void llenarPersonal() {
    if (con == null) {
        JOptionPane.showMessageDialog(this, "Conexión nula.");
        return;
    }

    mMedicos.setRowCount(0);
    mRecep.setRowCount(0);

    // ===== MÉDICOS =====
    String sqlMed =
            "SELECT idMedico, nombreMed, apellido1, apellido2, " +
            "       CONVERT(VARCHAR(10), fechaContra, 120) AS fechaContra, " +
            "       CONVERT(VARCHAR(10), fechaNac, 120) AS fechaNac, " +
            "       Especialidad, NumCedula, NumTelefonico, Domicilio, Correo " +
            "FROM medico";

    try (Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sqlMed)) {
        while (rs.next()) {
            mMedicos.addRow(new Object[]{
                    rs.getInt("idMedico"),
                    rs.getString("nombreMed"),
                    rs.getString("apellido1"),
                    rs.getString("apellido2"),
                    rs.getString("fechaContra"),
                    rs.getString("fechaNac"),
                    rs.getString("Especialidad"),
                    rs.getString("NumCedula"),
                    rs.getString("NumTelefonico"),
                    rs.getString("Domicilio"),
                    rs.getString("Correo")
            });
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error llenar médicos:\n" + ex.getMessage());
        ex.printStackTrace();
    }

    // ===== RECEPCIONISTAS =====
    String sqlRec =
            "SELECT idRecepcionista, nombre, apellido1, apellido2, " +
            "       CONVERT(VARCHAR(10), fechaContratacion, 120) AS fechaContratacion, " +
            "       telefono, correo " +
            "FROM recepcionista";

    try (Statement st2 = con.createStatement();
         ResultSet rs2 = st2.executeQuery(sqlRec)) {
        while (rs2.next()) {
            mRecep.addRow(new Object[]{
                    rs2.getInt("idRecepcionista"),
                    rs2.getString("nombre"),
                    rs2.getString("apellido1"),
                    rs2.getString("apellido2"),
                    rs2.getString("fechaContratacion"),
                    rs2.getString("telefono"),
                    rs2.getString("correo")
            });
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error llenar recepcionistas:\n" + ex.getMessage());
        ex.printStackTrace();
    }
}
private void actualizarMedico(String id, EditarPersonaDialog d) {
    try {
        String sql =
                "UPDATE medico SET nombreMed=?, apellido1=?, apellido2=?, " +
                "NumTelefonico=?, Especialidad=?, NumCedula=?, Domicilio=?, Correo=? " +
                "WHERE idMedico=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApe1());
            ps.setString(3, d.getApe2());
            ps.setString(4, d.getTel());
            ps.setString(5, d.getEsp());
            ps.setString(6, d.getCed());
            ps.setString(7, d.getDom());
            ps.setString(8, d.getCorreo()); // correo no se edita en dialog, solo se reusa
            ps.setString(9, id);

            ps.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Médico actualizado.");
        llenarPersonal();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar médico:\n" + ex.getMessage());
    }
}

private void actualizarRecepcionista(String id, EditarPersonaDialog d) {
    try {
        String sql =
                "UPDATE recepcionista SET nombre=?, apellido1=?, apellido2=?, " +
                "telefono=?, correo=? WHERE idRecepcionista=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApe1());
            ps.setString(3, d.getApe2());
            ps.setString(4, d.getTel());
            ps.setString(5, d.getCorreo());
            ps.setString(6, id);
            ps.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Recepcionista actualizado.");
        llenarPersonal();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar recepcionista:\n" + ex.getMessage());
    }
}

private void eliminarMedicoSeguro(String id) {
    try {
        con.setAutoCommit(false);

        String[] sqls = {
                "DELETE FROM CITA WHERE idMedico = ?",
                "DELETE FROM expediente_clinico WHERE id_medico = ?",
                "DELETE FROM CitasCompletadas WHERE idmedico = ?",
                "DELETE FROM medico WHERE idMedico = ?"
        };

        for (String sql : sqls) {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        }

        con.commit();
        JOptionPane.showMessageDialog(this, "Médico eliminado completamente.");
        llenarPersonal();

    } catch (SQLException ex) {
        try { con.rollback(); } catch (Exception ignored) {}
        JOptionPane.showMessageDialog(this, "Error eliminando médico:\n" + ex.getMessage());
    }
}

private void eliminarRecepcionista(String id) {
    int conf = JOptionPane.showConfirmDialog(
            this,
            "¿Eliminar este recepcionista?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
    );
    if (conf != JOptionPane.YES_OPTION) return;

    String sql = "DELETE FROM recepcionista WHERE idRecepcionista=?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, id);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Recepcionista eliminado.");
        llenarPersonal();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar recepcionista: " + ex.getMessage());
    }
}





    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ventanaAdmin vcm = new ventanaAdmin(usuarioId);
                vcm.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JCheckBox cbRecep;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAgregarCita1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblCita;
    private javax.swing.JTable tblRecep;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
