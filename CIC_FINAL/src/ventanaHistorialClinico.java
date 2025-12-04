import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ventanaHistorialClinico extends javax.swing.JFrame {

    static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private String nssPaciente;


    public ventanaHistorialClinico(String usuarioId) {//String usuarioId
        this.setTitle("Historial de Clinico");
        this.usuarioId = usuarioId;
        this.nssPaciente = obtenerNSS(usuarioId);
        con = ConexionSQL.ConexionSQLServer(); 
        initComponents();
        this.setLocationRelativeTo(this);
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        llenarHistorialClinico();
       // DefaultTableModel model = (DefaultTableModel) tblCita.getModel();
        //tblCita.setModel(model);
        // Refrescar la tabla
        tblCita.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        reloj11 = new Reloj1();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        lblSalir.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblSalir.setText("Salir");
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });

        lblInicio.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pagina-de-inicio.png"))); // NOI18N
        lblInicio.setText("Inicio");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInicioMouseClicked(evt);
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
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInicio)
                    .addComponent(lblSalir))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addGap(203, 203, 203)
                .addComponent(lblInicio)
                .addGap(147, 147, 147)
                .addComponent(lblSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 816));

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "N° Expediente", "Fecha_Atencion", "Nombre_Medico", "Especialidad", "Motivo_Cita"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 375, 772, 240));

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes...", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cmbMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMesItemStateChanged(evt);
            }
        });
        cmbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });
        jPanel1.add(cmbMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 230, 99, -1));

        cmbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia...", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cmbDia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDiaItemStateChanged(evt);
            }
        });
        jPanel1.add(cmbDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, 79, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/documentacionPaciente.png"))); // NOI18N
        jLabel3.setText("Historial Clinico");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(566, 6, -1, -1));

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");
        jPanel1.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(934, 152, -1, 34));

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar cita");
        jPanel1.add(lblBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 131, 145, 35));

        btnLimpiar.setText("Refresh");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 230, -1, -1));
        jPanel1.add(reloj11, new org.netbeans.lib.awtextra.AbsoluteConstraints(911, 72, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(528, 138, 209, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/calendario.png"))); // NOI18N
        jLabel1.setText("Seleccionar Fecha:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(469, 376, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/calendario.png"))); // NOI18N
        jLabel2.setText("Seleccionar Fecha:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, -1, -1));

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED) {
        buscarPorFecha();
    }
    }//GEN-LAST:event_cmbMesItemStateChanged

    public static String obtenerNSS(String correo) {
        con = ConexionSQL.ConexionSQLServer();
        Statement stmt2;
        ResultSet resultadoMed;
        String identificadorPac = "";

        try {
            stmt2 = con.createStatement();
            String idPac = "select pac.numeroSeguro \n"
                    + "from paciente pac\n"
                    + "where pac.Correo = '" + correo + "'";
            System.out.println(idPac);

            resultadoMed = stmt2.executeQuery(idPac);

            while (resultadoMed.next()) {
                identificadorPac = resultadoMed.getString("numeroSeguro");
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return identificadorPac;
    }

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        VentanaPaciente vp = new VentanaPaciente(usuarioId);
        vp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        m.setRowCount(0);
        cmbMes.setSelectedIndex(0);
        cmbDia.setSelectedIndex(0);
       llenarHistorialClinico();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
     if (evt.getStateChange() == ItemEvent.SELECTED) {
     buscarPorFecha();
    }
    }//GEN-LAST:event_cmbDiaItemStateChanged

    
    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        //char c = (char) evt.getKeyCode();
        //if (c == evt.VK_ENTER) {
        //    buscarMedico(txtBuscar.getText());
        //    if (txtBuscar.getText().equals("")) {
        //        llenarCitas();
        //    }
       // }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
   String texto = txtBuscar.getText().trim(); 
        if (texto.isEmpty()) {
       llenarHistorialClinico();
    } else {
        buscarMedico(texto); // Si hay texto, busca coincidencias
    }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
    int fila = tblCita.getSelectedRow();

    if (fila == -1) return;

    // obtenemos el idExp de la fila seleccionada
    String idExp = tblCita.getValueAt(fila, 0).toString();

    consultarHistorialClinico c = new consultarHistorialClinico(idExp);
    c.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_tblCitaMouseClicked

    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMesActionPerformed

 /*private void buscarPorFecha() {
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);

    try {
        String nss = obtenerNSS(usuarioId);

        int nDia = cmbDia.getSelectedIndex();
        int nMes = cmbMes.getSelectedIndex();

        String query =
            "SELECT HC.idExp, HC.fecha_atencion, " +
            "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
            "       M.Especialidad, HC.motivo_atencion " +
            "FROM expediente_clinico HC " +
            "JOIN MEDICO M ON HC.id_medico = M.idMedico " +
            "WHERE HC.nss = ? ";

        if (nMes > 0) query += "AND MONTH(HC.fecha_atencion) = " + nMes + " ";
        if (nDia > 0) query += "AND DAY(HC.fecha_atencion) = " + nDia + " ";

        query += "ORDER BY HC.fecha_atencion DESC";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, nss);

        ResultSet rs = ps.executeQuery();

        Object fila[] = new Object[5];

        while (rs.next()) {
            fila[0] = rs.getObject("idExp");
            fila[1] = rs.getObject("fecha_atencion");
            fila[2] = rs.getObject("nombreMedico");
            fila[3] = rs.getObject("Especialidad");
            fila[4] = rs.getObject("motivo_atencion");
            m.addRow(fila);
        }

    } catch (SQLException ex) {
        System.out.println("Error buscarPorFecha: " + ex.getMessage());
    }
}*/
private void buscarPorFecha() {
    limpiarTabla();
    String nss = obtenerNSS(usuarioId);

    int mes = cmbMes.getSelectedIndex();
    int dia = cmbDia.getSelectedIndex();

    // Si ambos combo están en la posición 0 (sin seleccionar) → mostrar todo
    if (mes == 0 && dia == 0) {
        llenarHistorialClinico();
        return;
    }

    if (nss == null || nss.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No se pudo obtener el NSS del paciente.");
        return;
    }

    try {
        boolean encontradoEnPrimera = false;
        boolean encontradoEnSegunda = false;
        int totalResultados = 0;
        Object fila[] = new Object[5];

        // -------------------------
        //   PRIMERA TABLA
        // -------------------------
        String sql1 =
            "SELECT HC.idExp, HC.fecha_atencion, " +
            "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
            "       M.Especialidad, HC.motivo_atencion " +
            "FROM expediente_clinico HC " +
            "JOIN MEDICO M ON HC.id_medico = M.idMedico " +
            "WHERE HC.nss = ? " +
            "  AND MONTH(HC.fecha_atencion) = ? " +
            "  AND DAY(HC.fecha_atencion) = ? " +
            "ORDER BY HC.fecha_atencion DESC";

        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.setString(1, nss);
        ps1.setInt(2, mes);
        ps1.setInt(3, dia);

        ResultSet rs1 = ps1.executeQuery();

        while (rs1.next()) {
            encontradoEnPrimera = true;
            totalResultados++;

            fila[0] = rs1.getObject("idExp");
            fila[1] = rs1.getObject("fecha_atencion");
            fila[2] = rs1.getObject("nombreMedico");
            fila[3] = rs1.getObject("Especialidad");
            fila[4] = rs1.getObject("motivo_atencion");
            m.addRow(fila);
        }
        rs1.close();
        ps1.close();

        // -------------------------
        //   SEGUNDA TABLA
        // -------------------------
        if (!encontradoEnPrimera) {
            String sql2 =
                "SELECT EE.idExp, EE.fecha_atencion, " +
                "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
                "       M.Especialidad, EE.motivo_atencion " +
                "FROM expedientes_eliminados EE " +
                "JOIN MEDICO M ON EE.id_medico = M.idMedico " +
                "WHERE EE.nss = ? " +
                "  AND MONTH(EE.fecha_atencion) = ? " +
                "  AND DAY(EE.fecha_atencion) = ? " +
                "ORDER BY EE.fecha_atencion DESC";

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, nss);
            ps2.setInt(2, mes);
            ps2.setInt(3, dia);

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                encontradoEnSegunda = true;
                totalResultados++;

                fila[0] = rs2.getObject("idExp");
                fila[1] = rs2.getObject("fecha_atencion");
                fila[2] = rs2.getObject("nombreMedico");
                fila[3] = rs2.getObject("Especialidad");
                fila[4] = rs2.getObject("motivo_atencion");
                m.addRow(fila);
            }
            rs2.close();
            ps2.close();
        }

        // -------------------------
        // MENSAJE SI NO HAY DATOS
        // -------------------------
        if (totalResultados == 0) {
            JOptionPane.showMessageDialog(
                null,
                "No hay registros para la fecha seleccionada.",
                "Sin coincidencias",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

    } catch (SQLException e) {
        System.out.println("Error al filtrar por fecha: " + e.getMessage());
        e.printStackTrace();
    }
}


public void buscarMedico(String textoBusqueda) {
    limpiarTabla();
    String nss = obtenerNSS(usuarioId);
    
     if (textoBusqueda.trim().isEmpty()) {
        llenarHistorialClinico(); 
        return;
    }
     
    if (nss == null || nss.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No se pudo obtener el NSS del paciente.");
        return;
    }
    
    try {
        boolean encontradoEnPrimera = false;
        boolean encontradoEnSegunda = false;
        int totalResultados = 0;
        
        // Si el texto de búsqueda está vacío, mostrar todos los registros
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            llenarHistorialClinico(); // Llama al método original que muestra todo
            return;
        }
        
        String likePattern = textoBusqueda + "%";
        Object fila[] = new Object[5];
        
        // PRIMER INTENTO → expediente_clinico
        String sql1 = 
            "SELECT HC.idExp, HC.fecha_atencion, " +
            "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
            "       M.Especialidad, HC.motivo_atencion " +
            "FROM expediente_clinico HC " +
            "JOIN MEDICO M ON HC.id_medico = M.idMedico " +
            "WHERE HC.nss = ? " +
            "  AND (M.nombreMed LIKE ? OR M.apellido1 LIKE ? OR M.apellido2 LIKE ?) " +
            "ORDER BY HC.fecha_atencion DESC";

        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.setString(1, nss);
        ps1.setString(2, likePattern);
        ps1.setString(3, likePattern);
        ps1.setString(4, likePattern);

        ResultSet rs1 = ps1.executeQuery();

        while (rs1.next()) {
            encontradoEnPrimera = true;
            totalResultados++;
            fila[0] = rs1.getObject("idExp");
            fila[1] = rs1.getObject("fecha_atencion");
            fila[2] = rs1.getObject("nombreMedico");
            fila[3] = rs1.getObject("Especialidad");
            fila[4] = rs1.getObject("motivo_atencion");
            m.addRow(fila);
        }
        rs1.close();
        ps1.close();

        // SEGUNDO INTENTO → expedientes_eliminados (solo si no se encontró en la primera)
        if (!encontradoEnPrimera) {
            String sql2 = 
                "SELECT EE.idExp, EE.fecha_atencion, " +
                "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
                "       M.Especialidad, EE.motivo_atencion AS motivo_atencion " +
                "FROM expedientes_eliminados EE " +
                "JOIN MEDICO M ON EE.id_medico = M.idMedico " +
                "WHERE EE.nss = ? " +
                "  AND (M.nombreMed LIKE ? OR M.apellido1 LIKE ? OR M.apellido2 LIKE ?) " +
                "ORDER BY EE.fecha_atencion DESC";

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, nss);
            ps2.setString(2, likePattern);
            ps2.setString(3, likePattern);
            ps2.setString(4, likePattern);

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                encontradoEnSegunda = true;
                totalResultados++;
                fila[0] = rs2.getObject("idExp");
                fila[1] = rs2.getObject("fecha_atencion");
                fila[2] = rs2.getObject("nombreMedico");
                fila[3] = rs2.getObject("Especialidad");
                fila[4] = rs2.getObject("motivo_atencion");
                m.addRow(fila);
            }
            rs2.close();
            ps2.close();
        }

        // Mostrar mensajes solo cuando sea necesario (no con cada tecla)
        if (totalResultados == 0 && !textoBusqueda.trim().isEmpty()) {
            // Solo mostrar si la búsqueda tenía texto
            JOptionPane.showMessageDialog(null, 
                "No se encontró ningún médico con el nombre: " + textoBusqueda,
                "Búsqueda sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Opcional: mostrar en la barra de estado en lugar de JOptionPane
        if (encontradoEnSegunda && !encontradoEnPrimera) {
            // Mostrar en la interfaz, no con popup
            System.out.println("Mostrando resultados del historial eliminado");
        } else {System.out.println("Resultados encontrados: " + totalResultados);
        }

    } catch (SQLException e) {
        System.out.println("Error en buscarMedico: " + e.getMessage());
        e.printStackTrace();
        //lblEstado.setText("Error en la búsqueda");
    }
}
 
 private void limpiarTabla() {
    DefaultTableModel modelo = (DefaultTableModel) tblCita.getModel();
    modelo.setRowCount(0);
}

  public void llenarHistorialClinico() {
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);

    try {
        String nss = obtenerNSS(usuarioId); // toma el NSS del paciente logueado

        String query =
            "SELECT HC.idExp, HC.fecha_atencion, " +
            "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
            "       M.Especialidad, HC.motivo_atencion " +
            "FROM expediente_clinico HC " +
            "JOIN MEDICO M ON HC.id_medico = M.idMedico " +
            "WHERE HC.nss = ? " +
            "ORDER BY HC.fecha_atencion DESC";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, nss);

        ResultSet rs = ps.executeQuery();
        Object fila[] = new Object[5];

        while (rs.next()) {
            fila[0] = rs.getObject("idExp");
            fila[1] = rs.getObject("fecha_atencion");
            fila[2] = rs.getObject("nombreMedico");
            fila[3] = rs.getObject("Especialidad");
            fila[4] = rs.getObject("motivo_atencion");
            m.addRow(fila);
        }
        
         // SI HAY DATOS → no continuar
        if (m.getRowCount() > 0) {
            return;
        }

        // SEGUNDO INTENTO → expediente_eliminados
        String query2 =
    "SELECT EE.idExp, EE.fecha_atencion, " +
    "       M.nombreMed + ' ' + M.apellido1 + ' ' + M.apellido2 AS nombreMedico, " +
    "       M.Especialidad, EE.motivo_atencion AS motivo_atencion " +
    "FROM expedientes_eliminados EE " +
    "JOIN MEDICO M ON EE.id_medico = M.idMedico " +
    "WHERE EE.nss = ? " +
    "ORDER BY EE.fecha_atencion DESC";


        PreparedStatement ps2 = con.prepareStatement(query2);
        ps2.setString(1, nss);

        ResultSet rs2 = ps2.executeQuery();

        while (rs2.next()) {
            fila[0] = rs2.getObject("idExp");
            fila[1] = rs2.getObject("fecha_atencion");
            fila[2] = rs2.getObject("nombreMedico");
            fila[3] = rs2.getObject("Especialidad");
            fila[4] = rs2.getObject("motivo_atencion");
            m.addRow(fila);
        }

        // SI TAMPOCO HAY DATOS
        if (m.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "El paciente no tiene historial clínico.");
        } else {
            JOptionPane.showMessageDialog(this, "Mostrando historial ELIMINADO.");
        }
        

    } catch (SQLException e) {
        System.out.println("Error al llenar historial clínico: " + e.getMessage());
    }
}
    private String obtenerFecha() {
        String fecha = "";
        LocalDateTime hoy = LocalDateTime.now();
        fecha = hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();

        return fecha;
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
                //ventanaHistorialClinico v = new ventanaHistorialClinico("");
                //v.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}