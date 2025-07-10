package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import static vista.FrmMenu.jDesktopPane_menu;

public class InterGraficas extends javax.swing.JInternalFrame {

    public static String fecha_inicio = "", fecha_fin = "";

    public InterGraficas() {
        initComponents();
        this.setSize(new Dimension(450, 300));
        this.setTitle("Historial de Ventas");

        FlatDarkLaf.setup();
        UIManager.put("Component.arc", 20);
        SwingUtilities.updateComponentTreeUI(this);

        // Fondo con gradiente
        this.setContentPane(new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(18, 18, 18),
                        0, getHeight(), new Color(40, 80, 120));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        });

        // Inicializar componentes
        jLabel1 = new JLabel("Seleccione fechas para graficar");
        jLabel2 = new JLabel("Fecha Inicio:");
        jLabel3 = new JLabel("Fecha Fin:");

        // 👇 Aquí sí aplica bien el estilo
        estilizarJDateChooser(jDateChooser_fecha_inicio1);
        estilizarJDateChooser(jDateChooser_fecha_fin);

        // Estilos de etiquetas
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setForeground(Color.LIGHT_GRAY);

        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setForeground(Color.LIGHT_GRAY);

        // Estilo para los DateChooser
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 136), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        JTextField editor1 = (JTextField) jDateChooser_fecha_inicio1.getDateEditor().getUiComponent();
        JTextField editor2 = (JTextField) jDateChooser_fecha_fin.getDateEditor().getUiComponent();

        editor1.setBackground(new Color(30, 30, 30));
        editor1.setForeground(Color.WHITE);
        editor1.setCaretColor(Color.WHITE); // Cursor blanco
        editor1.setBorder(roundedBorder);

        editor2.setBackground(new Color(30, 30, 30));
        editor2.setForeground(Color.WHITE);
        editor2.setCaretColor(Color.WHITE);
        editor2.setBorder(roundedBorder);

        // Botón personalizado con fondo verde degradado
        jButton_Guardar = new JButton("Generar gráfico") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();

                // Dibuja fondo degradado redondeado
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 150, 136),
                        0, height, new Color(0, 180, 170));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, width, height, 30, 30);
                g2.dispose();

                // Ahora pinta el texto, íconos, etc.
                super.paintComponent(g);
            }

            @Override
            public boolean isContentAreaFilled() {
                return false; // No permite que el sistema pinte el fondo clásico
            }
        };
        jButton_Guardar.setOpaque(false);
        jButton_Guardar.setFocusPainted(false);
        jButton_Guardar.setBorderPainted(false);
        jButton_Guardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_Guardar.setForeground(Color.WHITE);
        jButton_Guardar.setFocusPainted(false);
        jButton_Guardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_Guardar.setBorder(new RoundedBorder(15, new Color(0, 150, 136)));
        jButton_Guardar.setMargin(new Insets(5, 20, 5, 20));  // ⬅️ Espacio interno ajustado

        jButton_Guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        // Layout principal
        Container content = this.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(jLabel1);
        content.add(Box.createVerticalStrut(15));

        JPanel fechasPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fechasPanel.setOpaque(false);
        fechasPanel.setBorder(new EmptyBorder(0, 40, 0, 40));
        fechasPanel.add(jLabel2);
        fechasPanel.add(jDateChooser_fecha_inicio1);
        fechasPanel.add(jLabel3);
        fechasPanel.add(jDateChooser_fecha_fin);

        content.add(fechasPanel);
        content.add(Box.createVerticalStrut(20));

        JPanel botonPanel = new JPanel();
        botonPanel.setOpaque(false);
        botonPanel.add(jButton_Guardar);
        content.add(botonPanel);
    }

    private void estilizarJDateChooser(JDateChooser dateChooser) {
        JTextField editor = (JTextField) dateChooser.getDateEditor().getUiComponent();
        editor.setBackground(new Color(30, 30, 30));
        editor.setForeground(Color.WHITE);
        editor.setCaretColor(Color.WHITE);
        editor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 136), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        editor.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // 👇 Estiliza el calendario desplegable (popup)
        JComponent pop = dateChooser.getCalendarButton();
        pop.setBackground(new Color(0, 150, 136));
        pop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pop.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton_Guardar = new javax.swing.JButton();
        jDateChooser_fecha_fin = new com.toedter.calendar.JDateChooser();
        jDateChooser_fecha_inicio1 = new com.toedter.calendar.JDateChooser();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Seleccione fechas para graficar");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha Inicio:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 100, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha Fin:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 100, -1));

        jButton_Guardar.setBackground(new java.awt.Color(0, 204, 204));
        jButton_Guardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/historial1.png"))); // NOI18N
        jButton_Guardar.setText("Graficar Ventas");
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 230, 50));

        jDateChooser_fecha_fin.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser_fecha_fin.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser_fecha_fin.setDateFormatString("yyyy-MM-dd");
        jDateChooser_fecha_fin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(jDateChooser_fecha_fin, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 140, -1));

        jDateChooser_fecha_inicio1.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser_fecha_inicio1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser_fecha_inicio1.setDateFormatString("yyyy-MM-dd");
        jDateChooser_fecha_inicio1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(jDateChooser_fecha_inicio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 140, -1));

        jLabel_Wallpaper.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Wallpaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo3.jpg"))); // NOI18N
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        Date fechaInicioDate = jDateChooser_fecha_inicio1.getDate();
        Date fechaFinDate = jDateChooser_fecha_fin.getDate();

        if (fechaInicioDate == null || fechaFinDate == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas para continuar.", "Fechas requeridas", JOptionPane.WARNING_MESSAGE);
            return;
        }

// Formato internacional (ej. "2025-06-10")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        fecha_inicio = sdf.format(fechaInicioDate);
        fecha_fin = sdf.format(fechaFinDate);
        // Asignar las fechas a la clase de las gráficas
        InterGraficasVentas.fecha_inicio = fecha_inicio;
        InterGraficasVentas.fecha_fin = fecha_fin;

        InterGraficasVentas intergraficasventas = new InterGraficasVentas();
        jDesktopPane_menu.add(intergraficasventas);
        intergraficasventas.setVisible(true);


    }//GEN-LAST:event_jButton_GuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Guardar;
    private com.toedter.calendar.JDateChooser jDateChooser_fecha_fin;
    private com.toedter.calendar.JDateChooser jDateChooser_fecha_inicio1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_Wallpaper;
    // End of variables declaration//GEN-END:variables

    //Metodo para Limpíar campos
}
