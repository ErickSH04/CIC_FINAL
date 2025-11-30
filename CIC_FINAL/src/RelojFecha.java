
import javax.swing.JFrame;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class RelojFecha extends JFrame implements Runnable{
    private JLabel lblReloj;
    private volatile boolean running = true;
    
    public RelojFecha(){
        super("Reloj y Fecha");
        lblReloj = new JLabel();
        lblReloj.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblReloj.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblReloj);
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void run() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (running) {
            String texto = LocalDateTime.now().format(fmt);
            SwingUtilities.invokeLater(() -> lblReloj.setText(texto));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
    
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            RelojFecha ventana = new RelojFecha();
            ventana.setVisible(true);
            new Thread(ventana).start();
        });
    }
    
}
