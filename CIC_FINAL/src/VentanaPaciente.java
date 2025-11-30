
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


public class VentanaPaciente extends javax.swing.JFrame {

    private static String usuarioId;
    private static Statement stmt;
    public static Connection con;
    private final Color iconBgNormal = new Color(136,212,234);
    private final Color iconBgHover  = new Color( 94,176,206);
    private JPopupMenu menuUser;    
       
    private void showWelcomeDialog(String nombre) {
    // 1) Recuperar la foto desde BLOB
    /*ImageIcon avatarIcon = null;
    String sql = "SELECT foto_blobP FROM PACIENTE WHERE correo = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, usuarioId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("foto_blobP");
                if (imgBytes != null && imgBytes.length > 0) {
                    ImageIcon raw = new ImageIcon(imgBytes);
                    Image scaled = raw.getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    avatarIcon = new ImageIcon(scaled);
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    //  Fallback si no hay foto en BD
    if (avatarIcon == null) {
        ImageIcon raw = new ImageIcon(
            getClass().getResource("/imagenes/paciente.png")
        );
        Image scaled = raw.getImage()
            .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        avatarIcon = new ImageIcon(scaled);
    }*/

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
    //JLabel avatar = new JLabel(avatarIcon);
    //avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

    // 5) Texto de bienvenida
    JLabel text = new JLabel("Bienvenido, Paciente." + nombre);
    text.setFont(text.getFont().deriveFont(Font.BOLD, 18f));
    text.setAlignmentX(Component.CENTER_ALIGNMENT);
    text.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    // 6) Montar contenido y mostrar diálogo
    //content.add(avatar);
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

    public VentanaPaciente() {
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
    }

    public VentanaPaciente(String usuarioId) {//String usuarioId
        this.usuarioId = usuarioId;
        initComponents();
        this.setLocationRelativeTo(this);
        String nombre = obtenerNombrePac();
        lblBienvenida.setText(nombre);
        showWelcomeDialog(nombre);
        
        jPanelCItas.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        // --- separadores horizontales bajo cada sección ---
        jPanelRecetas.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        jPanelInformacion.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        jPanelMedicos.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        jPanelCerrarSesion.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, iconBgHover));
        
        crearMenuUser();
        jLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        // Si el menú ya está visible, no lo volvemos a mostrar
        if (!menuUser.isVisible()) {
            menuUser.show(jLabel2, 0, jLabel2.getHeight());
          }
       }
     });

       // Lista de los panels que queremos que reaccionen al hover
       JPanel[] botones = { jPanelCItas, jPanelRecetas, jPanelInformacion, jPanelMedicos, jPanelCerrarSesion};

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
            if (btn == jPanelCItas)      lblCitasMouseClicked(null);
            else if (btn == jPanelRecetas) lblRecetasMouseClicked(null);
            else if (btn == jPanelInformacion) lblInfoMouseClicked(null);
            else if (btn == jPanelMedicos) lblMedicosMouseClicked(e);
            else if (btn == jPanelCerrarSesion) lblCerrarSesionMouseClicked(e);
            }
         });
      }
        
   
    }
    
    public String obtenerNombrePac() {
        con = ConexionSQL.ConexionSQLServer();
        Statement stmt2;
        ResultSet resultadoMed;
        String nombreMed = "";

        try {
            stmt2 = con.createStatement();
            String query = "select nombrePac from PACIENTE \n"
                    + "where Correo = '" + usuarioId + "';";
            System.out.println(query);
            resultadoMed = stmt2.executeQuery(query);

            while (resultadoMed.next()) {
                nombreMed = resultadoMed.getString("nombrePac");
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ventanaCitasMed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return nombreMed;
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        reloj11 = new Reloj1();
        jPanelCItas = new javax.swing.JPanel();
        lblCitas = new javax.swing.JLabel();
        jPanelRecetas = new javax.swing.JPanel();
        lblRecetas = new javax.swing.JLabel();
        jPanelInformacion = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        jPanelMedicos = new javax.swing.JPanel();
        lblMedicos = new javax.swing.JLabel();
        jPanelCerrarSesion = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(850, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo .png"))); // NOI18N

        reloj11.setBackground(new java.awt.Color(136, 212, 234));
        reloj11.setFont(new java.awt.Font("Roboto", 3, 18)); // NOI18N

        jPanelCItas.setBackground(new java.awt.Color(136, 212, 234));

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

        javax.swing.GroupLayout jPanelCItasLayout = new javax.swing.GroupLayout(jPanelCItas);
        jPanelCItas.setLayout(jPanelCItasLayout);
        jPanelCItasLayout.setHorizontalGroup(
            jPanelCItasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCItasLayout.createSequentialGroup()
                .addComponent(lblCitas)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelCItasLayout.setVerticalGroup(
            jPanelCItasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRecetasLayout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addComponent(lblRecetas))
        );

        jPanelInformacion.setBackground(new java.awt.Color(136, 212, 234));

        lblInfo.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/documentacionPaciente.png"))); // NOI18N
        lblInfo.setText("Información");
        lblInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInfoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelInformacionLayout = new javax.swing.GroupLayout(jPanelInformacion);
        jPanelInformacion.setLayout(jPanelInformacionLayout);
        jPanelInformacionLayout.setHorizontalGroup(
            jPanelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInformacionLayout.createSequentialGroup()
                .addComponent(lblInfo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelInformacionLayout.setVerticalGroup(
            jPanelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInformacionLayout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(lblInfo))
        );

        jPanelMedicos.setBackground(new java.awt.Color(136, 212, 234));

        lblMedicos.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblMedicos.setForeground(new java.awt.Color(255, 255, 255));
        lblMedicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/estetoscopio.png"))); // NOI18N
        lblMedicos.setText("Medicos");
        lblMedicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMedicosMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelMedicosLayout = new javax.swing.GroupLayout(jPanelMedicos);
        jPanelMedicos.setLayout(jPanelMedicosLayout);
        jPanelMedicosLayout.setHorizontalGroup(
            jPanelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMedicosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMedicos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMedicosLayout.setVerticalGroup(
            jPanelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMedicosLayout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(lblMedicos))
        );

        jPanelCerrarSesion.setBackground(new java.awt.Color(136, 212, 234));

        lblCerrarSesion.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        lblCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesionM.png"))); // NOI18N
        lblCerrarSesion.setText("Cerrar sesión");
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
                .addContainerGap()
                .addComponent(lblCerrarSesion)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanelCerrarSesionLayout.setVerticalGroup(
            jPanelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCerrarSesionLayout.createSequentialGroup()
                .addComponent(lblCerrarSesion)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelRecetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelCItas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reloj11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCItas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        lblBienvenida.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblBienvenida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBienvenida.setText("jLabel1");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paciente.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 619, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBienvenida, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBienvenida)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCitasMouseClicked
        ventanaCitasPac vc = new ventanaCitasPac(this.usuarioId);
        vc.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblCitasMouseClicked

    private void lblInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInfoMouseClicked
        // TODO add your handling code here:
        VentanaInfoPac Pac = new VentanaInfoPac(usuarioId);
        Pac.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInfoMouseClicked

    private void lblRecetasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRecetasMouseClicked
        // TODO add your handling code here:
        VentanaRecePac rp = new VentanaRecePac(usuarioId);
        rp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblRecetasMouseClicked

    private void lblMedicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMedicosMouseClicked
        // TODO add your handling code here:
        VentanaInfoMed InfMe = new VentanaInfoMed(usuarioId);
        InfMe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblMedicosMouseClicked

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        // TODO add your handling code here:
        loginHospitalNuevo hl = new loginHospitalNuevo();
        hl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    
      
    private void crearMenuUser(){
        menuUser = new JPopupMenu();
        
        JMenuItem miPerfil = new JMenuItem("Ver perfil");
        miPerfil.addActionListener(e -> {
            //aqui se abre la ventana del perfil medico
            new VentanaPerfilPaciente(usuarioId).setVisible(true);
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
    
    
    private void cargarFotoUser() throws SQLException{
        String sql = "SELECT foto_blobP FROM PACIENTE WHERE Correo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, usuarioId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("foto_blobP");
                if (imgBytes != null && imgBytes.length > 0) {
                    ImageIcon raw = new ImageIcon(imgBytes);
                    Image scaled = raw.getImage()
                        .getScaledInstance(
                            jLabel2.getWidth(), 
                            jLabel2.getHeight(), 
                            Image.SCALE_SMOOTH
                        );
                    jLabel2.setIcon(new ImageIcon(scaled));
                    return;
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    // si no hay foto en BD
    jLabel2.setIcon(
      new ImageIcon(getClass().getResource("/imagenes/Paciente.png"))
       );
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
                new VentanaPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCItas;
    private javax.swing.JPanel jPanelCerrarSesion;
    private javax.swing.JPanel jPanelInformacion;
    private javax.swing.JPanel jPanelMedicos;
    private javax.swing.JPanel jPanelRecetas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblCitas;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblMedicos;
    private javax.swing.JLabel lblRecetas;
    private Reloj1 reloj11;
    // End of variables declaration//GEN-END:variables
}
