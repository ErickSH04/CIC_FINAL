
import java.awt.Color;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class loginHospitalNuevo extends javax.swing.JFrame {

    public static Connection con;
    int xMouse, yMouse;

    public loginHospitalNuevo() {
        initComponents();
        this.setLocationRelativeTo(this);
        //conectar
        con = ConexionSQL.ConexionSQLServer();
        //VerificadorCitasProximas hilo = new VerificadorCitasProximas(con);
        //hilo.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        back = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        exitBtn = new javax.swing.JPanel();
        exitText = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userTxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();
        passTxt = new javax.swing.JPasswordField();
        loginBtnTxt1 = new javax.swing.JLabel();
        crearCuentaTxt = new javax.swing.JLabel();
        lblCrear = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        back.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel3.setText("CONTRASEÑA");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        exitBtn.setBackground(new java.awt.Color(255, 255, 255));

        exitText.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        exitText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitText.setText("X");
        exitText.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitTextMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitTextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitTextMouseExited(evt);
            }
        });

        javax.swing.GroupLayout exitBtnLayout = new javax.swing.GroupLayout(exitBtn);
        exitBtn.setLayout(exitBtnLayout);
        exitBtnLayout.setHorizontalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exitBtnLayout.createSequentialGroup()
                .addComponent(exitText, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        exitBtnLayout.setVerticalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 836, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setText("INICIAR SESIÓN");

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel2.setText("USUARIO");

        userTxt.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        userTxt.setForeground(new java.awt.Color(204, 204, 204));
        userTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        userTxt.setText("Ingrese su nombre de Usuario");
        userTxt.setBorder(null);
        userTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userTxtMousePressed(evt);
            }
        });
        userTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTxtActionPerformed(evt);
            }
        });
        userTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userTxtKeyPressed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favicon.png"))); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel4.setText("Centro de Información y Consulta");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(229, 229, 229))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        loginBtn.setBackground(new java.awt.Color(136, 212, 234));

        loginBtnTxt.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        loginBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt.setText("ENTRAR");
        loginBtnTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtnTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginBtnTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtnTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtnTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout loginBtnLayout = new javax.swing.GroupLayout(loginBtn);
        loginBtn.setLayout(loginBtnLayout);
        loginBtnLayout.setHorizontalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginBtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(loginBtnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        loginBtnLayout.setVerticalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginBtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(loginBtnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        passTxt.setForeground(new java.awt.Color(204, 204, 204));
        passTxt.setText("******");
        passTxt.setBorder(null);
        passTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                passTxtMousePressed(evt);
            }
        });
        passTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passTxtKeyPressed(evt);
            }
        });

        loginBtnTxt1.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        loginBtnTxt1.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt1.setText("ENTRAR");
        loginBtnTxt1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtnTxt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtnTxt1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtnTxt1MouseExited(evt);
            }
        });

        crearCuentaTxt.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        crearCuentaTxt.setText("¿No tienes una Cuenta?");
        crearCuentaTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblCrear.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        lblCrear.setForeground(new java.awt.Color(255, 51, 51));
        lblCrear.setText("Crea una");
        lblCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCrearMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(loginBtnTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(crearCuentaTxt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblCrear))
                                    .addComponent(passTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(passTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crearCuentaTxt)
                    .addComponent(lblCrear))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(loginBtnTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        back.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 600));

        jPanel2.setBackground(new java.awt.Color(136, 212, 234));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/city.png"))); // NOI18N

        jLabel8.setText("jLabel8");

        jLabel9.setText("jLabel9");

        jLabel10.setText("jLabel10");

        jLabel11.setText("jLabel11");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iStock-471629610-Converted(2).png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(786, 786, 786)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(310, 310, 310)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(479, 479, 479))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        back.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 300, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnTxt1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_loginBtnTxt1MouseExited

    private void loginBtnTxt1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_loginBtnTxt1MouseEntered

    private void passTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passTxtMousePressed
        if (String.valueOf(passTxt.getPassword()).equals("******")) {
            passTxt.setText("");
            passTxt.setForeground(Color.black);
        }
        if (userTxt.getText().isEmpty()) {
            userTxt.setText("Ingrese su nombre de Usuario");
            userTxt.setForeground(Color.gray);
        }
    }//GEN-LAST:event_passTxtMousePressed

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        loginBtn.setBackground(new Color(136, 212, 234));
    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        loginBtn.setBackground(new Color(141, 219, 242));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void userTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userTxtActionPerformed

    private void userTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTxtMousePressed
        if (userTxt.getText().equals("Ingrese su nombre de Usuario")) {
            userTxt.setText("");
            userTxt.setForeground(Color.black);
        }

        if (String.valueOf(passTxt.getPassword()).isEmpty()) {
            passTxt.setText("******");
            passTxt.setForeground(Color.gray);
        }
    }//GEN-LAST:event_userTxtMousePressed

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void exitTextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTextMouseExited
        exitBtn.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_exitTextMouseExited

    private void exitTextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTextMouseEntered
        exitBtn.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_exitTextMouseEntered

    private void exitTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitTextMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitTextMouseClicked

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked

        //Obtener los datos ingresados en los campos de texto
        String correo = userTxt.getText();
        String contraseña = String.valueOf(passTxt.getPassword());

        //Validar si los campos estan vacios
        if (correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa tu correo y contraseña");
            return;
        }

        //Obtener la conexion utilizando el metodo que ya tenemos para conectar 
        Connection con = ConexionSQL.ConexionSQLServer();

        //Validar que la conexion no sea nula
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }

        //Consulta sql para validar las credenciales
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
            return;
        }

        //Consulta SQL para validar las credenciales
        String query = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
        try {
            // Preparar la consulta
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Procesar el resultado
            if (rs.next()) {

                String usuarioId = rs.getString("correo");
                String rol = rs.getString("rol");

                // Abrir la ventana correspondiente según el rol
                if ("Medico".equals(rol)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido, médico");
                    //new VentanaMedico(usuarioId).setVisible(true);
                    ventanaMedico vm = new ventanaMedico(usuarioId);
                    vm.setVisible(true);
                    this.setVisible(false);
                } else if ("Paciente".equals(rol)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido, paciente");
                    VentanaPaciente vp = new VentanaPaciente(usuarioId);
                    //ventanaConsultarMedicoHorario vp = new ventanaConsultarMedicoHorario(usuarioId);
                    vp.setVisible(true);
                    this.setVisible(false);
                } else if ("Recepcion".equals(rol)){
                    JOptionPane.showMessageDialog(this, "Bienvenido, Recepcionista");
                    VentanaRecepcion vr = new VentanaRecepcion();
                    vr.setVisible(true);
                    this.setVisible(false);
                }else if ("Admin".equals(rol)){
                    JOptionPane.showMessageDialog(this, "Bienvenido, Administrador");
                    ventanaAdmin ra = new ventanaAdmin(usuarioId);
                    ra.setVisible(true);
                    this.setVisible(false);
                }
                
                else {
                    JOptionPane.showMessageDialog(this, "Rol no identificado.");
                }
                // Cerrar la ventana actual (login)
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }

            // Cerrar la conexión
            //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginHospitalNuevo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_loginBtnTxtMouseClicked

    private void passTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passTxtKeyPressed
        char c = (char) evt.getKeyCode();
        if (c == evt.VK_ENTER) {
            String correo = userTxt.getText();
            String contraseña = String.valueOf(passTxt.getPassword());

            //Validar si los campos estan vacios
            //Obtener los datos ingresados en los campos de texto
        

        //Validar si los campos estan vacios
        if (correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa tu correo y contraseña");
            return;
        }

        //Obtener la conexion utilizando el metodo que ya tenemos para conectar 
        Connection con = ConexionSQL.ConexionSQLServer();

        //Validar que la conexion no sea nula
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }

        //Consulta sql para validar las credenciales
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
            return;
        }

        //Consulta SQL para validar las credenciales
        String query = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
        try {
            // Preparar la consulta
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Procesar el resultado
            if (rs.next()) {

                String usuarioId = rs.getString("correo");
                String rol = rs.getString("rol");

                // Abrir la ventana correspondiente según el rol
                if ("Medico".equals(rol)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido, médico");
                    //new VentanaMedico(usuarioId).setVisible(true);
                    ventanaMedico vm = new ventanaMedico(usuarioId);
                    vm.setVisible(true);
                    this.setVisible(false);
                } else if ("Paciente".equals(rol)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido, paciente");
                    VentanaPaciente vp = new VentanaPaciente(usuarioId);
                    vp.setVisible(true);
                    this.setVisible(false);
                } else if ("Recepcion".equals(rol)){
                    JOptionPane.showMessageDialog(this, "Bienvenido, Recepcionista");
                    VentanaRecepcion vr = new VentanaRecepcion();
                    vr.setVisible(true);
                    this.setVisible(false);
                }else if ("Admin".equals(rol)){
                    JOptionPane.showMessageDialog(this, "Bienvenido, Administrador");
                    ventanaAdmin ra = new ventanaAdmin(usuarioId);
                    ra.setVisible(true);
                    this.setVisible(false);
                }
                
                else {
                    JOptionPane.showMessageDialog(this, "Rol no identificado.");
                }
                // Cerrar la ventana actual (login)
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }

            // Cerrar la conexión
            //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginHospitalNuevo.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_passTxtKeyPressed

    private void userTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userTxtKeyPressed
        char c = (char) evt.getKeyCode();
        if (c == evt.VK_TAB) {
            passTxt.setText(" ");

            String correo = userTxt.getText();
            String contraseña = String.valueOf(passTxt.getPassword());

            //Validar si los campos estan vacios
            if (correo.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa tu correo y contraseña");
                return;
            }

            //Obtener la conexion utilizando el metodo que ya tenemos para conectar 
            Connection con = ConexionSQL.ConexionSQLServer();

            //Validar que la conexion no sea nula
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
            }

            //Consulta sql para validar las credenciales
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
                return;
            }

            //Consulta SQL para validar las credenciales
            String query = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
            //String query = "SELECT nombreUsuario, rol, correo FROM Usuario WHERE correo = ? AND contraseña = ?";

            try {
                // Preparar la consulta
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, correo);
                stmt.setString(2, contraseña);

                // Ejecutar la consulta
                ResultSet rs = stmt.executeQuery();

                // Procesar el resultado
                if (rs.next()) {

                    String usuarioId = rs.getString("correo");
                    String rol = rs.getString("rol");

                    // Abrir la ventana correspondiente según el rol
                    if ("Medico".equals(rol)) {
                        JOptionPane.showMessageDialog(this, "Bienvenido, médico");
                        ventanaMedico vm = new ventanaMedico(usuarioId);
                        vm.setVisible(true);
                        this.dispose();
                       // this.setVisible(false);
                    }
                    if ("Paciente".equals(rol)) {
                        JOptionPane.showMessageDialog(this, "Bienvenido, paciente");
                        VentanaPaciente vp = new VentanaPaciente();
                        vp.setVisible(true);
                        this.setVisible(false);
                    } else if("Recepcion".equals(rol)){
                         JOptionPane.showMessageDialog(this, "Bienvenido/a, recepcionista");
                         VentanaRecepcion vr = new VentanaRecepcion();
                         vr.setVisible(true);
                         this.setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Rol no identificado.");
                    }
                    // Cerrar la ventana actual (login)
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
                }

                // Cerrar la conexión
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(loginHospitalNuevo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_userTxtKeyPressed

    private void lblCrearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearMouseClicked
        registro res = new registro();
        res.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblCrearMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginHospitalNuevo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel back;
    private javax.swing.JLabel crearCuentaTxt;
    private javax.swing.JPanel exitBtn;
    private javax.swing.JLabel exitText;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblCrear;
    private javax.swing.JPanel loginBtn;
    private javax.swing.JLabel loginBtnTxt;
    private javax.swing.JLabel loginBtnTxt1;
    private javax.swing.JPasswordField passTxt;
    private javax.swing.JTextField userTxt;
    // End of variables declaration//GEN-END:variables
}
