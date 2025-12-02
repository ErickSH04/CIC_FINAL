
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;



public class registroPacienteCuestionario extends javax.swing.JFrame {
    private static Statement stmt;
    public static Connection con;
    private String correo;
    private String nss;
    private String queryusuario;
    private boolean  modoEdicion;
    
    
     // Constructor modificado para aceptar modo edición
    public registroPacienteCuestionario(String correo, String nss, String queryusuario, boolean modoEdicion) {
        initComponents();
        this.setLocationRelativeTo(this);
        this.nss = nss;
        this.correo = correo;
        this.queryusuario = queryusuario;
        this.modoEdicion = modoEdicion; // Asignar modo edición
        con = ConexionSQL.ConexionSQLServer();
        
        // Configurar interfaz según el modo
        configurarModoEdicion();
        
        // Si está en modo edición, cargar los datos existentes
        if (modoEdicion) {
            cargarDatosExistentes();
        }
    }
    
      private void configurarModoEdicion() {
        if (modoEdicion) {
            // En modo edición, ocultamos el botón Confirmar y mostramos Editar
            Aceptar.setVisible(false);
            btnEditar.setVisible(true);
            btnCancelar.setText("Volver"); // Cambiar texto del botón cancelar
        } else {
            // En modo registro normal, mostramos Confirmar y ocultamos Editar
            Aceptar.setVisible(true);
            btnEditar.setVisible(false);
            btnCancelar.setText("Cancelar");
        }
    }
      
       // Método para cargar datos existentes desde la base de datos
    private void cargarDatosExistentes() {
        String query = "SELECT * FROM preguntas_preliminares WHERE nss = '" + nss + "'";
        
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                // Cargar los datos en los campos
                txtNumEmergencia.setText(rs.getString("Contacto_Emergencia"));
                jcbEnfermedadCronica.setSelectedItem(rs.getString("enfermedad_cronica"));
                jcbConsumoTabaco.setSelectedItem(rs.getString("Tabaco"));
                jcbConsumoAlcohol.setSelectedItem(rs.getString("Alcohol"));
                txtEnfermedadFamiliar.setText(rs.getString("Enfermedad_Familiar"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Aceptar1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Aceptar = new javax.swing.JButton();
        txtContactoEmergencia = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        txtEnfermedadFamiliar = new javax.swing.JTextField();
        txtNumEmergencia = new javax.swing.JTextField();
        jcbConsumoTabaco = new javax.swing.JComboBox<>();
        jcbConsumoAlcohol = new javax.swing.JComboBox<>();
        jcbEnfermedadCronica = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();

        Aceptar1.setText("Confirmar");
        Aceptar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aceptar1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(136, 212, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 2, 24)); // NOI18N
        jLabel1.setText("Información Adicional");

        jLabel6.setFont(new java.awt.Font("Roboto", 2, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel6.setText("Centro de Informacion y Consulta");

        jLabel5.setText("Número de contacto de emergencia:");

        jLabel7.setText("¿Padece de una enfermedad crónica?");

        jLabel8.setText("¿Consume tabaco?");

        jLabel9.setText("¿Consume alcohol?");

        jLabel10.setText("¿Algún familiar padece una enfermedad crónica?");

        Aceptar.setText("Confirmar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtNumEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumEmergenciaActionPerformed(evt);
            }
        });

        jcbConsumoTabaco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sí", "No", "Ocasional" }));

        jcbConsumoAlcohol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sí", "No", "Ocasional" }));

        jcbEnfermedadCronica.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sí", "No" }));

        jLabel2.setText("De ser así, ¿cual?");

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNumEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbEnfermedadCronica, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(txtEnfermedadFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnCancelar)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(282, 282, 282)
                                .addComponent(txtContactoEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jcbConsumoTabaco, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbConsumoAlcohol, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Aceptar)
                                    .addComponent(btnEditar))))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtContactoEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jcbEnfermedadCronica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jcbConsumoTabaco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jcbConsumoAlcohol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEnfermedadFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(Aceptar))
                .addGap(35, 35, 35))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 360, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
         registrarPaciente(this.correo);
        ventanaPagos pagos = new ventanaPagos(this.correo, this.correo, this.nss); // Usar 3 parámetros
    pagos.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_AceptarActionPerformed

    private void Aceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aceptar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Aceptar1ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (modoEdicion) {
            this.dispose();
            // Volver a ventanaPagos
        ventanaPagos pagos = new ventanaPagos(this.correo, this.correo, this.nss);
        pagos.setVisible(true);
        } else {
        
        String query = "delete from usuario \n"
                +"where(correo = '" +correo + "');";
   
        System.out.println(query);
        
        try {
             stmt = con.createStatement();
             stmt.executeUpdate(query);
             showMessageDialog(null,"Registro cancelado con exito");
        } catch (SQLException ex) {
            showMessageDialog(null, ex.getMessage());
        }
        this.dispose();
        loginHospitalNuevo login = new loginHospitalNuevo();
        login.setVisible(true);
        }
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNumEmergenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumEmergenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumEmergenciaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
 actualizarDatosPaciente();
    }//GEN-LAST:event_btnEditarActionPerformed
 // Método para actualizar los datos del paciente
    private void actualizarDatosPaciente() {
        String contacto, enfermedad, tabaco, alcohol, enfermedadfam;
        
        contacto = txtNumEmergencia.getText().toString();
        enfermedad = jcbEnfermedadCronica.getSelectedItem().toString();
        tabaco = jcbConsumoTabaco.getSelectedItem().toString();
        alcohol = jcbConsumoAlcohol.getSelectedItem().toString();
        enfermedadfam = txtEnfermedadFamiliar.getText();
        
        // Query de actualización en lugar de inserción
        String query = "UPDATE preguntas_preliminares SET " +
                      "Contacto_Emergencia = '" + contacto + "', " +
                      "enfermedad_cronica = '" + enfermedad + "', " +
                      "Tabaco = '" + tabaco + "', " +
                      "Alcohol = '" + alcohol + "', " +
                      "Enfermedad_Familiar = '" + enfermedadfam + "' " +
                      "WHERE nss = '" + nss + "'";
        
        System.out.println(query);
        
        try {
            stmt = con.createStatement();
            int filasActualizadas = stmt.executeUpdate(query);
            
            if (filasActualizadas > 0) {
                showMessageDialog(null, "Datos actualizados con éxito");
                // Redirigir a la ventana de pagos o donde corresponda
                 ventanaPagos pagos = new ventanaPagos(this.correo, this.correo, this.nss);
                pagos.setVisible(true);
                this.dispose();
            } else {
                showMessageDialog(null, "No se encontraron datos para actualizar");
            }
        } catch (SQLException ex) {
            showMessageDialog(null, "Error al actualizar datos: " + ex.getMessage());
        }
    }
    
 
    private void registrarPaciente(String correo) {
        String contacto, enfermedad, tabaco, alcohol, enfermedadfam;
        con = ConexionSQL.ConexionSQLServer();
        
        try{
             stmt = con.createStatement();
             stmt.executeUpdate(this.queryusuario);
            // showMessageDialog(null,"Paciente agregado con exito");
        } catch (SQLException ex) {
            showMessageDialog(null,"Error al guardar información.");
        }
        
        
        contacto = txtNumEmergencia.getText().toString();
        enfermedad = jcbEnfermedadCronica.getSelectedItem().toString();
        tabaco = jcbConsumoTabaco.getSelectedItem().toString();
        alcohol = jcbConsumoAlcohol.getSelectedItem().toString();
        enfermedadfam = txtEnfermedadFamiliar.getText();
        
        String query = "INSERT INTO preguntas_preliminares (nss, Contacto_Emergencia, enfermedad_cronica, Tabaco, Alcohol, Enfermedad_Familiar) VALUES "
             + "('"+nss+"', '" + contacto + "', '" + enfermedad + "', '" + tabaco + "', '" + alcohol + "', '" + enfermedadfam +"');";
   
        System.out.println(query);
        
        try {
             stmt = con.createStatement();
             stmt.executeUpdate(query);
             showMessageDialog(null,"✓ Información adicional guardada\n✓ Proceda al pago de la consulta");
        } catch (SQLException ex) {
            showMessageDialog(null,"Error al insertar paciente por "+(ex)+"");
        }
        
        this.dispose();
        
    }
  
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JButton Aceptar1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> jcbConsumoAlcohol;
    private javax.swing.JComboBox<String> jcbConsumoTabaco;
    private javax.swing.JComboBox<String> jcbEnfermedadCronica;
    private javax.swing.JTextField txtContactoEmergencia;
    private javax.swing.JTextField txtEnfermedadFamiliar;
    private javax.swing.JTextField txtNumEmergencia;
    // End of variables declaration//GEN-END:variables
}
