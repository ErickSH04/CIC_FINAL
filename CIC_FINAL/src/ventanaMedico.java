
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;



public class ventanaMedico extends javax.swing.JFrame {
    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private final Color iconBgNormal = new Color(136,212,234);
    private final Color iconBgHover  = new Color( 94,176,206);
    private JPopupMenu menuUser;
    private static medico medico;
    //private final Color separatorColor = new Color(94, 176, 206);

   
    private void showWelcomeDialog(String nombre) {
         // 1) Recuperar la foto desde BLOB
    ImageIcon avatarIcon = null;
    
    //  Fallback si no hay foto en BD
    if (avatarIcon == null) {
        ImageIcon raw = new ImageIcon(
            getClass().getResource("/imagenes/asistencia-medica.png")
        );
        Image scaled = raw.getImage()
            .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        avatarIcon = new ImageIcon(scaled);
    }

    // 2) Crear el diálogo sin bordes
    JDialog dlg = new JDialog(this, false);
    dlg.setUndecorated(true);
    dlg.getRootPane().setBorder(
        BorderFactory.createLineBorder(iconBgHover, 2, true)
    );

    // 3) Panel de contenido
    JPanel content = new JPanel();
    content.setBackground(Color.WHITE);
    content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

    // 4) Avatar
    JLabel avatar = new JLabel(avatarIcon);
    avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

    // 5) Texto de bienvenida
    JLabel text = new JLabel("Bienvenido, Dr." + nombre);
    text.setFont(text.getFont().deriveFont(Font.BOLD, 18f));
    text.setAlignmentX(Component.CENTER_ALIGNMENT);
    text.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    // 6) Montar contenido y mostrar diálogo
    content.add(avatar);
    content.add(text);
    dlg.getContentPane().add(content);
    dlg.pack();
    dlg.setLocationRelativeTo(this);
    dlg.setVisible(true);

    // 7) Timer que cierra el diálogo tras 3 segundos
    new javax.swing.Timer(3000, e -> dlg.dispose()) {{
        setRepeats(false);
        start();
      }};
    }

    public ventanaMedico(String usuarioId) {//String usuarioId
        this.usuarioId = usuarioId;
        initComponents();
        con = ConexionSQL.ConexionSQLServer();
        this.setLocationRelativeTo(this);
        jPanelLogo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        // --- separadores horizontales bajo cada sección ---
        jPanelCitas.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        jPanelRecetas.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        jPanelPacientes.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        jPanelDocumentacionP.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));

      
        crearMenuUser();

        
        jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        // Si el menú ya está visible, no lo volvemos a mostrar
        if (!menuUser.isVisible()) {
            menuUser.show(jLabel1, 0, jLabel1.getHeight());
          }
       }
     });

       // Lista de los panels que queremos que reaccionen al hover
       JPanel[] botones = { jPanelCitas, jPanelRecetas, jPanelPacientes, jPanelDocumentacionP};

        for (JPanel btn : botones) {
        // asegúrate de que pinten su fondo
        btn.setOpaque(true);
        btn.setBackground(iconBgNormal);
        // cursor de mano para indicar “clicable”
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // listener que cambia el fondo al pasar/salir el ratón
        btn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            btn.setBackground(iconBgHover);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            btn.setBackground(iconBgNormal);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            // opcional: disparar tu lógica de click existente
            if (btn == jPanelCitas)      lblCitasMouseClicked(null);
            else if (btn == jPanelRecetas) lblRecetasMouseClicked(null);
            else if (btn == jPanelPacientes) lblPacientesMouseClicked(null);
            else if (btn == jPanelDocumentacionP) lblDocumentacionMouseClicked(e);
            }
         });
      }
        
        this.setLocationRelativeTo(this);
        this.medico = new medico();
        this.medico = rellenarDatosMed(this.usuarioId);
        String nombre = medico.getNombreMed();
        lblBienvenida.setText("Dr. " + nombre);
        showWelcomeDialog(nombre);
        medico.mostrarMed(this.medico);
   }   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanelCentroControl = new javax.swing.JPanel();
        jPanelCitas = new javax.swing.JPanel();
        lblCitas = new javax.swing.JLabel();
        jPanelRecetas = new javax.swing.JPanel();
        lblRecetas = new javax.swing.JLabel();
        jPanelPacientes = new javax.swing.JPanel();
        lblPacientes = new javax.swing.JLabel();
        jPanelCerrarSesion = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        jPanelDocumentacionP = new javax.swing.JPanel();
        lblExpedientes = new javax.swing.JLabel();
        jPanelLogo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        reloj11 = new Reloj1();
        lblBienvenida = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        jPanelCentroControl.setBackground(new java.awt.Color(136, 212, 234));

        jPanelCitas.setBackground(new java.awt.Color(136, 212, 234));

        lblCitas.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblCitas.setForeground(new java.awt.Color(255, 255, 255));
        lblCitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/citaM.png"))); // NOI18N
        lblCitas.setText("Citas");
        lblCitas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCitasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelCitasLayout = new javax.swing.GroupLayout(jPanelCitas);
        jPanelCitas.setLayout(jPanelCitasLayout);
        jPanelCitasLayout.setHorizontalGroup(
            jPanelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCitasLayout.createSequentialGroup()
                .addComponent(lblCitas)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelCitasLayout.setVerticalGroup(
            jPanelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCitas)
        );

        jPanelRecetas.setBackground(new java.awt.Color(136, 212, 234));

        lblRecetas.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblRecetas.setForeground(new java.awt.Color(255, 255, 255));
        lblRecetas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/receta(1).png"))); // NOI18N
        lblRecetas.setText("Recetas");
        lblRecetas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblRecetas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRecetasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelRecetasLayout = new javax.swing.GroupLayout(jPanelRecetas);
        jPanelRecetas.setLayout(jPanelRecetasLayout);
        jPanelRecetasLayout.setHorizontalGroup(
            jPanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecetasLayout.createSequentialGroup()
                .addComponent(lblRecetas)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelRecetasLayout.setVerticalGroup(
            jPanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecetasLayout.createSequentialGroup()
                .addComponent(lblRecetas)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanelPacientes.setBackground(new java.awt.Color(136, 212, 234));

        lblPacientes.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblPacientes.setForeground(new java.awt.Color(255, 255, 255));
        lblPacientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paciente.png"))); // NOI18N
        lblPacientes.setText("Pacientes");
        lblPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPacientesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelPacientesLayout = new javax.swing.GroupLayout(jPanelPacientes);
        jPanelPacientes.setLayout(jPanelPacientesLayout);
        jPanelPacientesLayout.setHorizontalGroup(
            jPanelPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPacientesLayout.createSequentialGroup()
                .addComponent(lblPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelPacientesLayout.setVerticalGroup(
            jPanelPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPacientes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelCerrarSesion.setBackground(new java.awt.Color(136, 212, 234));

        lblCerrarSesion.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblCerrarSesion.setText(" Cerrar sesión");
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelCerrarSesionLayout = new javax.swing.GroupLayout(jPanelCerrarSesion);
        jPanelCerrarSesion.setLayout(jPanelCerrarSesionLayout);
        jPanelCerrarSesionLayout.setHorizontalGroup(
            jPanelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCerrarSesionLayout.createSequentialGroup()
                .addComponent(lblCerrarSesion)
                .addGap(0, 82, Short.MAX_VALUE))
        );
        jPanelCerrarSesionLayout.setVerticalGroup(
            jPanelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCerrarSesionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCerrarSesion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelDocumentacionP.setBackground(new java.awt.Color(136, 212, 234));

        lblExpedientes.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblExpedientes.setForeground(new java.awt.Color(255, 255, 255));
        lblExpedientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/documentacionPaciente.png"))); // NOI18N
        lblExpedientes.setText("Expedientes");
        lblExpedientes.setMaximumSize(new java.awt.Dimension(557, 512));
        lblExpedientes.setMinimumSize(new java.awt.Dimension(557, 512));
        lblExpedientes.setPreferredSize(new java.awt.Dimension(557, 512));
        lblExpedientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExpedientesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelDocumentacionPLayout = new javax.swing.GroupLayout(jPanelDocumentacionP);
        jPanelDocumentacionP.setLayout(jPanelDocumentacionPLayout);
        jPanelDocumentacionPLayout.setHorizontalGroup(
            jPanelDocumentacionPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDocumentacionPLayout.createSequentialGroup()
                .addComponent(lblExpedientes, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelDocumentacionPLayout.setVerticalGroup(
            jPanelDocumentacionPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExpedientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanelCentroControlLayout = new javax.swing.GroupLayout(jPanelCentroControl);
        jPanelCentroControl.setLayout(jPanelCentroControlLayout);
        jPanelCentroControlLayout.setHorizontalGroup(
            jPanelCentroControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelCentroControlLayout.createSequentialGroup()
                .addGroup(jPanelCentroControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCitas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelRecetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanelDocumentacionP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelCentroControlLayout.setVerticalGroup(
            jPanelCentroControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCentroControlLayout.createSequentialGroup()
                .addComponent(jPanelCitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jPanelRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDocumentacionP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(jPanelCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        jPanelLogo.setBackground(new java.awt.Color(136, 212, 234));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        reloj11.setBackground(new java.awt.Color(136, 212, 234));
        reloj11.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N

        javax.swing.GroupLayout jPanelLogoLayout = new javax.swing.GroupLayout(jPanelLogo);
        jPanelLogo.setLayout(jPanelLogoLayout);
        jPanelLogoLayout.setHorizontalGroup(
            jPanelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogoLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLogoLayout.setVerticalGroup(
            jPanelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogoLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCentroControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCentroControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        lblBienvenida.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblBienvenida.setText("jLabel1");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/asistencia-medica.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 679, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblBienvenida)
                        .addGap(42, 42, 42))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBienvenida)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    private void lblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPacientesMouseClicked
        ventanaPacientesMed vpm = new ventanaPacientesMed(usuarioId);
        vpm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblPacientesMouseClicked

    private void lblCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCitasMouseClicked
        ventanaCitasMed vc = new ventanaCitasMed(this.medico);
        vc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblCitasMouseClicked

    private void lblRecetasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRecetasMouseClicked
         seleccionarReceta cr = new seleccionarReceta(ventanaMedico.getMedico());
         cr.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_lblRecetasMouseClicked

    private void lblExpedientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExpedientesMouseClicked
        act_eli_Expediente aee = new act_eli_Expediente(this.medico);
        this.setVisible(false);
        aee.setVisible(true);
    }//GEN-LAST:event_lblExpedientesMouseClicked
    
    private void lblDocumentacionMouseClicked(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static medico getMedico(){
        return medico;
    }
    
    public void setMedico(medico medico){
        this.medico=medico;
    }
    
    public medico rellenarDatosMed(String correo){
        con = ConexionSQL.ConexionSQLServer();
        Statement stmt2;
        ResultSet resultadoMed;
        medico medico = new medico();
        int i = 0;
        
        try {
            stmt2 = con.createStatement();
            String query = "select * from MEDICO \n"
                    + "where Correo = '"+correo+"';";
            System.out.println(query);
            resultadoMed = stmt2.executeQuery(query);
            
            while(resultadoMed.next()){
                medico.setIdMedico(resultadoMed.getString("idMedico"));
                medico.setNombreMed(resultadoMed.getString("nombreMed"));
                medico.setApellido1(resultadoMed.getString("apellido1"));
                medico.setApellido2(resultadoMed.getString("apellido2"));
                medico.setFechaNac(resultadoMed.getString("fechaNac"));
                medico.setEspecialidad(resultadoMed.getString("Especialidad"));
                medico.setNumCedula(resultadoMed.getString("NumCedula"));
                medico.setNumTelefonico(resultadoMed.getString("NumTelefonico"));
                medico.setDomicilio(resultadoMed.getString("Domicilio"));
                medico.setCorreo(correo);
            } 
           
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return medico;
    }
    
    
    private void crearMenuUser(){
        menuUser = new JPopupMenu();
        
        JMenuItem miPerfil = new JMenuItem("Ver perfil");
        miPerfil.addActionListener(e -> {
            //aqui se abre la ventana del perfil medico
            new VentanaPerfilMedico(usuarioId).setVisible(true);
            this.dispose();
         }
        );
        menuUser.add(miPerfil);
        
        JMenuItem miCambiarPassword = new JMenuItem("Cambiar Contraseña");
        miCambiarPassword.addActionListener(e -> {
            //
          }
        );
        menuUser.add(miCambiarPassword);
        
        menuUser.addSeparator();
        
        JMenuItem miCerrar = new JMenuItem("Cerrar sesión");
        miCerrar.addActionListener(e -> {
        new loginHospitalNuevo().setVisible(true);
        dispose();
        });
        menuUser.add(miCerrar);
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
                ventanaMedico vm = new ventanaMedico(usuarioId);
                        vm.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelCentroControl;
    private javax.swing.JPanel jPanelCerrarSesion;
    private javax.swing.JPanel jPanelCitas;
    private javax.swing.JPanel jPanelDocumentacionP;
    private javax.swing.JPanel jPanelLogo;
    private javax.swing.JPanel jPanelPacientes;
    private javax.swing.JPanel jPanelRecetas;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblCitas;
    private javax.swing.JLabel lblExpedientes;
    private javax.swing.JLabel lblPacientes;
    private javax.swing.JLabel lblRecetas;
    private Reloj1 reloj11;
    // End of variables declaration//GEN-END:variables
}
