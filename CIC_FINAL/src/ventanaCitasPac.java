
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ventanaCitasPac extends javax.swing.JFrame {

    static String usuarioId;
    private static Statement stmt;
    public static Connection con;

    public ventanaCitasPac(String usuarioId) {//String usuarioId
        this.setTitle("Citas Paciente");
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        con = ConexionSQL.ConexionSQLServer();
        m = (DefaultTableModel) tblCita.getModel();
        lblFecha.setText(obtenerFecha());
        //actualizarCitasPasadas();
        llenarCitas();
        aplicarColoresEstatus();
        verificarCitasModificadas();
        // ... después de llenar la tabla
        

        DefaultTableModel model = (DefaultTableModel) tblCita.getModel();
        tblCita.setModel(model);

        // Aplicar color al encabezado
        JTableHeader header = tblCita.getTableHeader();
        header.setBackground(new java.awt.Color(224, 224, 224)); // verde
        header.setForeground(Color.BLACK);
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        header.setOpaque(false);

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
        lblAgregarCita = new javax.swing.JLabel();
        lblHorarioMedico = new javax.swing.JLabel();
        lblHistorialCita = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblPago = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCita = new javax.swing.JTable();
        cmbMes = new javax.swing.JComboBox<>();
        cmbDia = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        reloj11 = new Reloj1();
        txtBuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

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

        lblAgregarCita.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblAgregarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar-usuario.png"))); // NOI18N
        lblAgregarCita.setText("Agregar Cita");
        lblAgregarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAgregarCitaMouseClicked(evt);
            }
        });

        lblHorarioMedico.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblHorarioMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asistencia-medica.png"))); // NOI18N
        lblHorarioMedico.setText("Horario Medico");
        lblHorarioMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHorarioMedicoMouseClicked(evt);
            }
        });

        lblHistorialCita.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        lblHistorialCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Historial Citas.png"))); // NOI18N
        lblHistorialCita.setText("Historial Cita ");
        lblHistorialCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHistorialCitaMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe-medico.png"))); // NOI18N
        jLabel1.setText("Historial Clinico");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        lblPago.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dar-dinero.png"))); // NOI18N
        lblPago.setText("Pago ");
        lblPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPagoMouseClicked(evt);
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
                        .addGap(42, 42, 42)
                        .addComponent(lblSalir))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblInicio)
                                .addGap(0, 166, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblHorarioMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAgregarCita)
                                .addComponent(lblHistorialCita, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPago, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInicio)
                .addGap(27, 27, 27)
                .addComponent(lblPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(lblHorarioMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblAgregarCita)
                .addGap(36, 36, 36)
                .addComponent(lblHistorialCita, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(lblSalir)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        tblCita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Hora", "Medico", "Especialidad", "Estatus"
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

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes...", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cmbMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMesItemStateChanged(evt);
            }
        });

        cmbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia...", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cmbDia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDiaItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Citas programadas");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/citaM.png"))); // NOI18N
        jLabel3.setText("Citas");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFecha.setText("fecha actual");

        lblBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar (1).png"))); // NOI18N
        lblBuscar.setText("Buscar cita");

        btnLimpiar.setText("Refresh");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
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
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblFecha)
                                .addGap(89, 89, 89)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(219, 219, 219)
                                .addComponent(jLabel3)
                                .addGap(85, 85, 85))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(btnLimpiar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBuscar))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(123, 123, 123)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar)
                        .addGap(150, 150, 150)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
        buscarPorFecha();
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

    private void lblAgregarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAgregarCitaMouseClicked
        try {
            registroCitaPac rc = new registroCitaPac(this.usuarioId);
            rc.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ventanaCitasPac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblAgregarCitaMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        m.setRowCount(0);
        cmbMes.setSelectedIndex(0);
        cmbDia.setSelectedIndex(0);
       // actualizarCitasPasadas();  // Actualiza citas pasadas
        llenarCitas();              // Vuelve a llenar la tabla
        aplicarColoresEstatus();    // Reaplica los colores
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cmbDiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiaItemStateChanged
        buscarPorFecha();
    }//GEN-LAST:event_cmbDiaItemStateChanged
/*void actualizarCitasPasadas() {
    try (Statement stmtUpdate = con.createStatement()) {
        String sql = """
            UPDATE cita
            SET estatus = 'Cancelado'
            WHERE 
                (
                    fecha < CAST(SWITCHOFFSET(SYSDATETIMEOFFSET(), '-06:00') AS date)
                    OR (
                        fecha = CAST(SWITCHOFFSET(SYSDATETIMEOFFSET(), '-06:00') AS date)
                        AND hora < CAST(SWITCHOFFSET(SYSDATETIMEOFFSET(), '-06:00') AS time)
                    )
                )
                AND estatus IN ('Activa', 'Modificado');
            """;

        int filasAfectadas = stmtUpdate.executeUpdate(sql);

        if (filasAfectadas > 0) {
            System.out.println("✅ Se actualizaron " + filasAfectadas + " citas pasadas a 'Cancelado'.");
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Se actualizaron " + filasAfectadas + " citas pasadas a 'Cancelado'.",
                    "Actualización completada",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("ℹ No se encontraron citas pasadas para actualizar.");
            javax.swing.JOptionPane.showMessageDialog(null,
                    "No se encontraron citas pasadas para actualizar.",
                    "Sin cambios",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        System.out.println("⚠ Error al actualizar citas pasadas: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(null,
                "Error al actualizar citas pasadas:\n" + e.getMessage(),
                "Error SQL",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}*/
    /*void actualizarCitasPasadas() {
    try {
        // Obtener número de seguro del paciente logueado
        String obtenerPacienteSQL = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
        PreparedStatement psPaciente = con.prepareStatement(obtenerPacienteSQL);
        psPaciente.setString(1, this.usuarioId);
        ResultSet rsPaciente = psPaciente.executeQuery();
        
        String numeroSeguro = "";
        if (rsPaciente.next()) {
            numeroSeguro = rsPaciente.getString("numeroSeguro");
        } else {
            return;
        }

        // Actualizar solo las citas del paciente logueado
        String sql = """
            UPDATE cita
            SET estatus = 'Cancelado'
            WHERE numeroSeguro = ?
            AND (
                fecha < CAST(SWITCHOFFSET(SYSDATETIMEOFFSET(), '-06:00') AS date)
                OR (
                    fecha = CAST(SWITCHOFFSET(SYSDATETIMEOFFSET(), '-06:00') AS date)
                    AND hora < CAST(SWITCHOFFSET(SYSDATETIMEOFFSET(), '-06:00') AS time)
                )
            )
            AND estatus IN ('Activa', 'Modificado')
        """;

        PreparedStatement psUpdate = con.prepareStatement(sql);
        psUpdate.setString(1, numeroSeguro);
        
        int filasAfectadas = psUpdate.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("✅ Se actualizaron " + filasAfectadas + " citas pasadas del paciente.");
        }

    } catch (SQLException e) {
        System.out.println("⚠ Error al actualizar citas pasadas: " + e.getMessage());
    }
}*/

    private void tblCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitaMouseClicked
  con = ConexionSQL.ConexionSQLServer();
    int renglon1 = tblCita.getSelectedRow();
     if (renglon1 == -1) return; // No hay fila seleccionada
      String estatus = tblCita.getValueAt(renglon1, 4).toString(); // Columna 4 = Estatus

    if (estatus.equalsIgnoreCase("Cancelado")) {
        JOptionPane.showMessageDialog(null, "Ya cancelaste la cita.");
        return; // No permite modificar ni cancelar
    }
     
     int respuesta = 0;
   try {
    respuesta = Integer.parseInt(JOptionPane.showInputDialog("¿Qué operación desea realizar?\n"
            + "1.- Modificar cita\n"
            + "2.- Cancelar cita\n"
            + "3.- Cancelar operación"));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Entrada inválida. Intenta de nuevo.");
        return;
    }
    System.out.println("Respuesta: " + respuesta);
    Object arreglo[] = new Object[4];
    int renglon = tblCita.getSelectedRow();
    String fecha = "", hora = "", medico = "", especialidad = "";

    for (int i = 0; i < arreglo.length; i++) {
        arreglo[i] = tblCita.getValueAt(renglon, i);
    }

    fecha = arreglo[0].toString();
    hora = arreglo[1].toString();
    medico = arreglo[2].toString();
    especialidad = arreglo[3].toString();

   

    // Si el usuario selecciona "Modificar cita"
    if (respuesta == 1) {
        //Object arreglo[] = new Object[4];
        //int renglon = tblCita.getSelectedRow();
        //String fecha = "", hora = "", medico = "", especialidad = "";

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = tblCita.getValueAt(renglon, i);
        }

        fecha = arreglo[0].toString();
        hora = arreglo[1].toString();
        medico = arreglo[2].toString();
        especialidad = arreglo[3].toString();

        // Buscar idCita según fecha, hora y nombre del médico
        String buscarIdCita = "SELECT cit.idCita " +
                              "FROM cita cit " +
                              "INNER JOIN medico med ON med.idMedico = cit.idMedico " +
                              "WHERE cit.hora = '" + hora + "' " +
                              "AND cit.fecha = '" + fecha + "' " +
                              "AND med.nombreMed = '" + medico + "';";

        System.out.println(buscarIdCita);
        String idC = "";
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(buscarIdCita);
            while (rs.next()) {
                idC = rs.getString("idCita");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventanaCitasMed.class.getName()).log(Level.SEVERE, null, ex);
        }

        modificarFecha mf = new modificarFecha(idC);//this
        mf.setVisible(true);
    }

    // Si el usuario selecciona "Cancelar cita"
    if (respuesta == 2) {
       // Object arreglo[] = new Object[4];
        //int renglon = tblCita.getSelectedRow();
        //String fecha = "", hora = "", medico = "", especialidad = "";

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = tblCita.getValueAt(renglon, i);
        }

        fecha = arreglo[0].toString();
        hora = arreglo[1].toString();
        medico = arreglo[2].toString();
        especialidad = arreglo[3].toString();

        // Buscar idCita según fecha, hora y médico
        String buscarIdCita = "SELECT cit.idCita " +
                              "FROM cita cit " +
                              "INNER JOIN medico med ON med.idMedico = cit.idMedico " +
                              "WHERE cit.hora = '" + hora + "' " +
                              "AND cit.fecha = '" + fecha + "' " +
                              "AND med.nombreMed = '" + medico + "';";

        System.out.println(buscarIdCita);
        String idC = "";
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(buscarIdCita);
            while (rs.next()) {
                idC = rs.getString("idCita");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventanaCitasMed.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Cancelar cita
        String Cancelar = "UPDATE cita SET estatus = 'Cancelado' WHERE idCita = '" + idC + "';";

        try {
            stmt.executeUpdate(Cancelar);
            JOptionPane.showMessageDialog(null, "Cita cancelada con éxito.");
           //actualizarCitasPasadas();
           llenarCitas();
           aplicarColoresEstatus();
           tblCita.repaint(); //  
        } catch (SQLException ex) {
            Logger.getLogger(ventanaCitasMed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      if (respuesta == 3) {
        JOptionPane.showMessageDialog(null, "Operación cancelada.");
        return; // Detiene el método y cierra el menú de opciones
      }
    }//GEN-LAST:event_tblCitaMouseClicked
    
    
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
        llenarCitas(); // Si no hay texto, muestra todo
    } else {
        buscarMedico(texto); // Si hay texto, busca coincidencias
    }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void lblHorarioMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHorarioMedicoMouseClicked
    ventanaConsultarMedicoHorario vcmh = new ventanaConsultarMedicoHorario(this.usuarioId);
    vcmh.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_lblHorarioMedicoMouseClicked

    private void lblHistorialCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistorialCitaMouseClicked
    ventanaHistorialCita a = new ventanaHistorialCita(usuarioId);
    a.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_lblHistorialCitaMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
    ventanaHistorialClinico hm = new ventanaHistorialClinico(usuarioId);
    hm.setVisible(true);
    this.dispose();    }//GEN-LAST:event_jLabel1MouseClicked

    private void lblPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPagoMouseClicked
        ventanaPagoCitaFinal hm = new ventanaPagoCitaFinal(usuarioId);
        hm.setVisible(true);
    }//GEN-LAST:event_lblPagoMouseClicked

 /*private void buscarPorFecha() {
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);
    Statement stmt2;
    ResultSet resultadoMed;
    String nombreMed = "";

    try {
        stmt2 = con.createStatement();
        String queryMed = "SELECT med.nombreMed " +
                          "FROM medico med " +
                          "INNER JOIN usuario u ON (med.Correo = u.correo) " +
                          "WHERE med.Correo = '" + this.usuarioId + "'";

        resultadoMed = stmt2.executeQuery(queryMed);

        while (resultadoMed.next()) {
            nombreMed = resultadoMed.getString("nombreMed");
        }

        int nDia = cmbDia.getSelectedIndex();
        int nMes = cmbMes.getSelectedIndex();

        stmt = con.createStatement();
        String query = "SELECT cit.fecha, cit.hora, med.nombreMed, med.Especialidad ,cit.estatus\n" +
                       "FROM cita cit " +
                       "INNER JOIN medico med ON med.idMedico = cit.idMedico " +
                       "WHERE med.nombreMed LIKE '" + nombreMed + "%' ";

        // FILTRO POR MES Y DÍA (como en tu versión original)
        if (nMes > 0 && nDia > 0) {
            query += "AND MONTH(cit.fecha) = " + nMes + " AND DAY(cit.fecha) = " + nDia + " ";
        } else if (nMes > 0) {
            query += "AND MONTH(cit.fecha) = " + nMes + " ";
        } else if (nDia > 0) {
            query += "AND DAY(cit.fecha) = " + nDia + " ";
        }

        query += "ORDER BY cit.fecha;";

        ResultSet result = stmt.executeQuery(query);
        Object R[] = new Object[5];
        while (result.next()) {
            R[0] = result.getObject("fecha");
            R[1] = result.getObject("hora");
            R[2] = result.getObject("nombreMed");
            R[3] = result.getObject("Especialidad");
            R[4] = result.getObject("estatus");
            m.addRow(R);
        }

    } catch (SQLException ex) {
        Logger.getLogger(ventanaCitasMed.class.getName()).log(Level.SEVERE, null, ex);
    }
}*/
    private void buscarPorFecha() {
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);

    // Si no hay filtro, recarga todas las citas programadas
    if (cmbMes.getSelectedIndex() == 0 && cmbDia.getSelectedIndex() == 0) {
        llenarCitas();
        return;
    }

    try {
        String obtenerPacienteSQL = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
        PreparedStatement psPaciente = con.prepareStatement(obtenerPacienteSQL);
        psPaciente.setString(1, this.usuarioId);
        ResultSet rsPaciente = psPaciente.executeQuery();
        
        String numeroSeguro = "";
        if (rsPaciente.next()) {
            numeroSeguro = rsPaciente.getString("numeroSeguro");
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el paciente.");
            return;
        }

        StringBuilder query = new StringBuilder("""
            SELECT cit.fecha, cit.hora, med.nombreMed, med.Especialidad, cit.estatus
            FROM cita cit 
            INNER JOIN medico med ON med.idMedico = cit.idMedico
            WHERE cit.numeroSeguro = ?
              AND cit.estatus <> 'Cancelado'
        """);

        int nDia = cmbDia.getSelectedIndex();
        int nMes = cmbMes.getSelectedIndex();

        if (nMes > 0) {
            query.append(" AND MONTH(cit.fecha) = ").append(nMes);
        }
        if (nDia > 0) {
            query.append(" AND DAY(cit.fecha) = ").append(nDia);
        }

        query.append(" ORDER BY cit.fecha DESC");

        PreparedStatement ps = con.prepareStatement(query.toString());
        ps.setString(1, numeroSeguro);
        
        ResultSet result = ps.executeQuery();
        Object R[] = new Object[5];
        
        while (result.next()) {
            R[0] = result.getObject("fecha");
            R[1] = result.getObject("hora");
            R[2] = result.getObject("nombreMed");
            R[3] = result.getObject("Especialidad");
            R[4] = result.getObject("estatus");
            m.addRow(R);
        }

        if (m.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron citas para la fecha seleccionada.",
                "Sin resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            verificarCitasModificadas();
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al filtrar por fecha: " + ex.getMessage());
        ex.printStackTrace();
    }
}


 /*private void buscarMedico(String nombre) {
    String busqueda = nombre;
    String query;
    con = ConexionSQL.ConexionSQLServer();
    ResultSet rs;
    m.setRowCount(0);

    query = "SELECT cit.fecha, cit.hora, med.nombreMed, med.Especialidad,cit.estatus\n" 
            + "FROM cita cit\n" 
            + "INNER JOIN medico med ON (med.idMedico = cit.idMedico)\n" 
            + "WHERE med.nombreMed LIKE '%" + busqueda + "%'";
    try {
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        Object R[] = new Object[5];

        while (rs.next()) {
            R[0] = rs.getObject("fecha");
            R[1] = rs.getObject("hora");
            R[2] = rs.getObject("nombreMed");
            R[3] = rs.getObject("Especialidad");
            R[4] = rs.getObject("estatus");
            m.addRow(R);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}*/
  private void buscarMedico(String nombre) {
    String busqueda = nombre.trim();
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);

    try {
        // Obtener número de seguro del paciente logueado
        String obtenerPacienteSQL = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
        PreparedStatement psPaciente = con.prepareStatement(obtenerPacienteSQL);
        psPaciente.setString(1, this.usuarioId);
        ResultSet rsPaciente = psPaciente.executeQuery();
        
        String numeroSeguro = "";
        if (rsPaciente.next()) {
            numeroSeguro = rsPaciente.getString("numeroSeguro");
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el paciente.");
            return;
        }

        // Consulta segura con PreparedStatement
        String query = """
            SELECT cit.fecha, cit.hora, med.nombreMed, med.Especialidad, cit.estatus
            FROM cita cit 
            INNER JOIN medico med ON med.idMedico = cit.idMedico
            WHERE cit.numeroSeguro = ? 
            AND med.nombreMed LIKE ?
            ORDER BY cit.fecha DESC
        """;
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, numeroSeguro);
        ps.setString(2, "%" + busqueda + "%");
        
        ResultSet rs = ps.executeQuery();
        Object R[] = new Object[5];

        while (rs.next()) {
            R[0] = rs.getObject("fecha");
            R[1] = rs.getObject("hora");
            R[2] = rs.getObject("nombreMed");
            R[3] = rs.getObject("Especialidad");
            R[4] = rs.getObject("estatus");
            m.addRow(R);
        }
        
        // Si no hay resultados
        if (m.getRowCount() == 0 && !busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron citas con el médico: " + busqueda,
                "Búsqueda sin resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error en búsqueda: " + ex.getMessage());
        ex.printStackTrace();
    }
}
  
  
    private String obtenerFecha() {
        String fecha = "";
        LocalDateTime hoy = LocalDateTime.now();
        fecha = hoy.getMonthValue() + "/" + hoy.getDayOfMonth() + "/" + hoy.getYear();

        return fecha;
    }

    /*public void llenarCitas() {
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);
    Statement stmt2;
    ResultSet resultadoMed;
    String nombre = "";

    try {
        stmt2 = con.createStatement();
        String idMed = "select med.nombreMed "
                + "from medico med "
                + "inner join usuario u on (med.Correo = u.correo) "
                + "where med.Correo = '" + this.usuarioId + "'";

        resultadoMed = stmt2.executeQuery(idMed);

        while (resultadoMed.next()) {
            nombre = resultadoMed.getString("nombreMed");
        }

        stmt = con.createStatement();
        String query = "SELECT cit.fecha, cit.hora, med.nombreMed, med.Especialidad,cit.estatus\n"
                + "FROM cita cit "
                + "INNER JOIN medico med ON med.idMedico = cit.idMedico "
                + "WHERE med.nombreMed LIKE '" + nombre + "%' "
                + "ORDER BY cit.fecha;";

        ResultSet result = stmt.executeQuery(query);

        Object R[] = new Object[5];
        while (result.next()) {
            R[0] = result.getObject("fecha");
            R[1] = result.getObject("hora");
            R[2] = result.getObject("nombreMed");
            R[3] = result.getObject("Especialidad");
            R[4] = result.getObject("estatus");
            m.addRow(R);
        }
    } catch (SQLException ex) {
        java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
}*/
  public void llenarCitas() {
    con = ConexionSQL.ConexionSQLServer();
    m.setRowCount(0);

    try {
        // Obtener número de seguro del paciente logueado
        String obtenerPacienteSQL = "SELECT numeroSeguro FROM PACIENTE WHERE Correo = ?";
        PreparedStatement psPaciente = con.prepareStatement(obtenerPacienteSQL);
        psPaciente.setString(1, this.usuarioId);
        ResultSet rsPaciente = psPaciente.executeQuery();

        String numeroSeguro = "";
        if (rsPaciente.next()) {
            numeroSeguro = rsPaciente.getString("numeroSeguro");
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el paciente.");
            return;
        }

        // Solo citas activas o modificadas (pendientes)
        String query = """
    SELECT 
                cit.fecha,
                cit.hora,
                med.nombreMed,
                med.Especialidad,
                cit.estatus
            FROM cita cit
            INNER JOIN medico med ON med.idMedico = cit.idMedico
            WHERE cit.numeroSeguro = ?
              AND cit.estatus IN ('Activa', 'Modificado')
            ORDER BY cit.fecha ASC, cit.hora ASC;
        """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, numeroSeguro);
        ResultSet result = ps.executeQuery();

        Object fila[] = new Object[6];
        while (result.next()) {
            //fila[0] = result.getObject("idCita");
            fila[0] = result.getObject("fecha");
            fila[1] = result.getObject("hora");
            fila[2] = result.getObject("nombreMed");
            fila[3] = result.getObject("Especialidad");
            fila[4] = result.getObject("estatus");
            m.addRow(fila);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Error al cargar citas activas: " + ex.getMessage());
    }
}


void aplicarColoresEstatus() {
    tblCita.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String estatus = table.getValueAt(row, 4).toString().trim().toLowerCase();

            // Colores según estatus
            switch (estatus) {
                case "activa":
                    setBackground(new java.awt.Color(0, 128, 0));   // Verde
                    setForeground(java.awt.Color.WHITE);
                    break;
                case "cancelado":
                    setBackground(new java.awt.Color(204, 0, 0));  // Rojo
                    setForeground(java.awt.Color.WHITE);
                    break;
                case "modificado":
                    setBackground(new java.awt.Color(0, 102, 204)); // Azul
                    setForeground(java.awt.Color.WHITE);
                    // SOLO cambiar el texto en la columna de Estatus
                    if (column == 4) {
                        setText("Activa (Modificado)");
                    }
                    break;
                default:
                    setBackground(java.awt.Color.WHITE);
                    setForeground(java.awt.Color.BLACK);
                    break;
            }

            if (isSelected) {
                setBackground(getBackground().darker());
            }

            return this;
        }
    });
}


    private void verificarCitasModificadas() {
    if (mensajeModificacionMostrado) return;

    int filas = tblCita.getRowCount();
    for (int i = 0; i < filas; i++) {
        Object v = tblCita.getValueAt(i, 4);
        if (v != null && "modificado".equalsIgnoreCase(v.toString().trim())) {
            mensajeModificacionMostrado = true;

            JOptionPane.showMessageDialog(
                    this,
                    "Una o más de tus citas fueron reasignadas.\n" +
                    "Tu médico anterior ya no está disponible y se te asignó otro médico.\n" +
                    "Revisa la fecha, la hora y el nombre del nuevo médico.",
                    "Cita modificada",
                    JOptionPane.INFORMATION_MESSAGE
            );
            break;
        }
    }
}

public void modificarCitaAdministrador(int idCita, int nuevoIdMedico, String nuevaFecha, String nuevaHora) {
    Connection con = ConexionSQL.ConexionSQLServer();

    try {
        String updateSQL = """
            UPDATE cita
            SET 
                idMedico = ?, 
                fecha = ?, 
                hora = ?, 
                estatus = 'Modificada'
            WHERE idCita = ?
        """;

        PreparedStatement ps = con.prepareStatement(updateSQL);
        ps.setInt(1, nuevoIdMedico);
        ps.setString(2, nuevaFecha);
        ps.setString(3, nuevaHora);
        ps.setInt(4, idCita);

        int resultado = ps.executeUpdate();

        if (resultado > 0) {
            JOptionPane.showMessageDialog(null, "✔ La cita fue modificada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se encontró la cita para modificar.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "⚠ Error al modificar la cita: " + e.getMessage());
        e.printStackTrace();
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
                ventanaCitasPac vcp = new ventanaCitasPac(usuarioId);
                vcp.setVisible(true);
            }
        });
    }
    private DefaultTableModel m;
    private boolean mensajeModificacionMostrado = false;

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
    private javax.swing.JLabel lblAgregarCita;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHistorialCita;
    private javax.swing.JLabel lblHorarioMedico;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private Reloj1 reloj11;
    private javax.swing.JTable tblCita;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
