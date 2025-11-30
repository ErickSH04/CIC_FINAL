import javax.swing.*;
import java.awt.*;

public class EditarPersonaDialog extends JDialog {

    private JTextField txtNombre, txtApe1, txtApe2, txtTel, txtEsp, txtCed, txtDom, txtCorreo;
    private boolean guardado = false;

    public EditarPersonaDialog(Frame owner, String tipo, Object[] datosFila) {
        super(owner, "Editar " + tipo, true);

        setLayout(new GridLayout(0, 2, 10, 10));

        // ================= NOMBRE =================
        add(new JLabel("Nombre:"));
        txtNombre = new JTextField(datosFila[1].toString());
        add(txtNombre);

        // ================= APELLIDO 1 =================
        add(new JLabel("Apellido 1:"));
        txtApe1 = new JTextField(datosFila[2].toString());
        add(txtApe1);

        // ================= APELLIDO 2 =================
        add(new JLabel("Apellido 2:"));
        txtApe2 = new JTextField(datosFila[3].toString());
        add(txtApe2);

        if ("Medico".equalsIgnoreCase(tipo)) {
            // =============== FECHA CONTRATACIÓN (solo mostrar, negritas) ===============
            add(new JLabel("Fecha contratación:"));
            JLabel lblFechaContra = new JLabel(datosFila[4] == null ? "" : datosFila[4].toString());
            lblFechaContra.setFont(lblFechaContra.getFont().deriveFont(Font.BOLD));
            add(lblFechaContra);

            // =============== FECHA NACIMIENTO (solo mostrar, negritas) ===============
            add(new JLabel("Fecha nacimiento:"));
            JLabel lblFechaNac = new JLabel(datosFila[5] == null ? "" : datosFila[5].toString());
            lblFechaNac.setFont(lblFechaNac.getFont().deriveFont(Font.BOLD));
            add(lblFechaNac);

            // =============== TELÉFONO =================
            add(new JLabel("Teléfono:"));
            txtTel = new JTextField(datosFila[8].toString()); // NumTelefonico
            add(txtTel);

            // =============== ESPECIALIDAD =================
            add(new JLabel("Especialidad:"));
            txtEsp = new JTextField(datosFila[6].toString());
            add(txtEsp);

            // =============== CÉDULA =================
            add(new JLabel("Cédula:"));
            txtCed = new JTextField(datosFila[7].toString());
            add(txtCed);

            // =============== DOMICILIO =================
            add(new JLabel("Domicilio:"));
            txtDom = new JTextField(datosFila[9].toString());
            add(txtDom);

            // =============== CORREO (solo lectura, negritas) ===============
            add(new JLabel("Correo:"));
            txtCorreo = new JTextField(datosFila[10].toString());
            txtCorreo.setEditable(false);
            txtCorreo.setFocusable(false);
            txtCorreo.setFont(txtCorreo.getFont().deriveFont(Font.BOLD));
            add(txtCorreo);

        } else { // ================== RECEPCIONISTA ==================

            // =============== FECHA CONTRATACIÓN (solo mostrar, negritas) ===============
            add(new JLabel("Fecha contratación:"));
            JLabel lblFechaContrat = new JLabel(datosFila[4] == null ? "" : datosFila[4].toString());
            lblFechaContrat.setFont(lblFechaContrat.getFont().deriveFont(Font.BOLD));
            add(lblFechaContrat);

            // =============== TELÉFONO =================
            add(new JLabel("Teléfono:"));
            txtTel = new JTextField(datosFila[5].toString());
            add(txtTel);

            // Estos no aplican para recepcionista, pero los inicializo vacíos
            txtEsp = new JTextField("");
            txtCed = new JTextField("");
            txtDom = new JTextField("");

            // =============== CORREO (solo lectura, negritas) ===============
            add(new JLabel("Correo:"));
            txtCorreo = new JTextField(datosFila[6].toString());
            txtCorreo.setEditable(false);
            txtCorreo.setFocusable(false);
            txtCorreo.setFont(txtCorreo.getFont().deriveFont(Font.BOLD));
            add(txtCorreo);
        }

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            guardado = true;
            setVisible(false);
        });

        btnCancelar.addActionListener(e -> {
            guardado = false;
            setVisible(false);
        });

        add(btnGuardar);
        add(btnCancelar);

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean fueGuardado() { return guardado; }

    public String getNombre() { return txtNombre.getText().trim(); }
    public String getApe1()   { return txtApe1.getText().trim(); }
    public String getApe2()   { return txtApe2.getText().trim(); }
    public String getTel()    { return txtTel.getText().trim(); }
    public String getEsp()    { return txtEsp.getText().trim(); }
    public String getCed()    { return txtCed.getText().trim(); }
    public String getDom()    { return txtDom.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
}
