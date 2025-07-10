package vista;

import com.formdev.flatlaf.FlatIntelliJLaf;
import conexion.Conexion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JTextField;
import static vista.FrmMenu.jDesktopPane_menu;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class InterGraficasVentas extends javax.swing.JInternalFrame {


    List<Integer> listaCantidad = new ArrayList<>();
    List<Double> listaIngresos = new ArrayList<>();
    List<String> listaFechas = new ArrayList<>();

    int cantidadResultados = 0;
    String[] vector_fechaVenta;
    int[] vector_estatus_cantidad;
    double[] vector_ingresos;

    public static String fecha_inicio = "";
    public static String fecha_fin = "";

    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    private JPanel panelGrafico, panelHeader, panelFooter, panelKPIs;
    private JLabel lblTitulo, lblRangoFechas, lblTotalVentas, lblIngresosTotales, lblTicketPromedio;
    private JButton btnExportar;

    public InterGraficasVentas() {
        FlatIntelliJLaf.setup();
        UIManager.put("Component.arc", 15);

        this.setSize(new Dimension(950, 650));
        this.setTitle("📊 Dashboard de Ventas Reverse");
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setResizable(true);

        if (!validarFechas()) {
            JOptionPane.showMessageDialog(this, "⚠️ Rango de fechas inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cantidadResultados = MetodoContador();
        if (cantidadResultados > 0) {
            vector_fechaVenta = new String[cantidadResultados];
            vector_estatus_cantidad = new int[cantidadResultados];
            vector_ingresos = new double[cantidadResultados];
            MetodoAlmacenaDatos();
        }

        // Header
        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(25, 118, 210));
        panelHeader.setBorder(new EmptyBorder(20, 20, 10, 20));

        lblTitulo = new JLabel("📊 Dashboard de Ventas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        lblRangoFechas = new JLabel("📅 Rango: " + fecha_inicio + " ➔ " + fecha_fin, SwingConstants.CENTER);
        lblRangoFechas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRangoFechas.setForeground(Color.WHITE);

        panelHeader.add(lblTitulo, BorderLayout.CENTER);
        panelHeader.add(lblRangoFechas, BorderLayout.SOUTH);

        // KPIs
        panelKPIs = new JPanel(new GridLayout(1, 3, 15, 0));
        panelKPIs.setBackground(Color.WHITE);
        panelKPIs.setBorder(new EmptyBorder(10, 20, 10, 20));

        int sumaVentas = listaCantidad.stream().mapToInt(Integer::intValue).sum();
        double ingresosTotales = listaIngresos.stream().mapToDouble(Double::doubleValue).sum();
        double ticketPromedio = sumaVentas > 0 ? ingresosTotales / sumaVentas : 0;

        lblTotalVentas = crearKpiLabel("🧾 Total Ventas", String.valueOf(sumaVentas));
        lblIngresosTotales = crearKpiLabel("💵 Ingresos Totales", String.format("S/ %.2f", ingresosTotales));
        lblTicketPromedio = crearKpiLabel("🎫 Ticket Promedio", String.format("S/ %.2f", ticketPromedio));

        panelKPIs.add(lblTotalVentas);
        panelKPIs.add(lblIngresosTotales);
        panelKPIs.add(lblTicketPromedio);

        // Gráfico
        panelGrafico = new JPanel() {
            private double animProgress = 0;
            private Timer timer;

            {
                timer = new Timer(15, e -> {
                    animProgress += 0.02;
                    if (animProgress >= 1) {
                        animProgress = 1;
                        timer.stop();
                    }
                    repaint();
                });
                timer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarGrafico((Graphics2D) g, animProgress);
            }
        };
        panelGrafico.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelGrafico);
        scrollPane.setBorder(null);

        // Footer
        panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelFooter.setBackground(Color.WHITE);

        btnExportar = new JButton("💾 Exportar Gráfico PNG");
        btnExportar.setFocusPainted(false);
        btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnExportar.addActionListener(e -> exportarGraficoComoPNG());

        panelFooter.add(btnExportar);

        // Layout
        setLayout(new BorderLayout());
        add(panelHeader, BorderLayout.NORTH);
        add(panelKPIs, BorderLayout.BEFORE_FIRST_LINE);
        add(scrollPane, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);
    }

    private void exportarGraficoComoPNG() {
        BufferedImage image = new BufferedImage(panelGrafico.getWidth(), panelGrafico.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        panelGrafico.paint(g2);
        g2.dispose();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar gráfico como PNG");
        chooser.setSelectedFile(new File("grafico_ventas.png"));
        int opcion = chooser.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(image, "png", chooser.getSelectedFile());
                JOptionPane.showMessageDialog(this, "✅ Gráfico exportado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al exportar: " + ex.getMessage());
            }
        }
    }

    private JLabel crearKpiLabel(String titulo, String valor) {
        JLabel label = new JLabel("<html><center>" + titulo + "<br><span style='font-size:18px;font-weight:bold;'>" + valor + "</span></center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setOpaque(true);
        label.setBackground(new Color(245, 245, 245));
        label.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return label;
    }

    private boolean validarFechas() {
        try {
            if (fecha_inicio == null || fecha_inicio.isEmpty() || fecha_fin == null || fecha_fin.isEmpty()) {
                return false;
            }
            formatoFecha.parse(fecha_inicio);
            formatoFecha.parse(fecha_fin);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public int MetodoContador() {
        int contador = 0;
        try (Connection cn = Conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(
                     "SELECT fechaVenta, COUNT(*) AS Ventas FROM tb_cabecera_Venta WHERE fechaVenta BETWEEN ? AND ? GROUP BY fechaVenta")) {
            pst.setString(1, fecha_inicio);
            pst.setString(2, fecha_fin);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    contador++;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en MetodoContador: " + e.getMessage());
        }
        return contador;
    }

    private void MetodoAlmacenaDatos() {
        try (Connection cn = Conexion.getConnection();
             PreparedStatement pst = cn.prepareStatement(
                     "SELECT fechaVenta, COUNT(*) AS Ventas, SUM(valorPagar) AS Ingresos FROM tb_cabecera_Venta WHERE fechaVenta BETWEEN ? AND ? GROUP BY fechaVenta")) {
            pst.setString(1, fecha_inicio);
            pst.setString(2, fecha_fin);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    listaFechas.add(rs.getString("fechaVenta"));
                    listaCantidad.add(rs.getInt("Ventas"));
                    listaIngresos.add(rs.getDouble("Ingresos"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en MetodoAlmacenaDatos: " + e.getMessage());
        }
    }

    private void dibujarGrafico(Graphics2D g2, double progress) {
        if (listaCantidad.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("⚠️ No hay datos para mostrar en este rango de fechas.", 50, 50);
            return;
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = panelGrafico.getWidth();
        int height = panelGrafico.getHeight();
        int margen = 70;
        int yBase = height - margen;
        int xInicio = margen;

        int mayorVenta = listaCantidad.stream().max(Integer::compareTo).orElse(0);
        int espacio = 20;
        int totalBarras = listaCantidad.size();
        int anchoBarra = Math.max(30, (width - 2 * margen - espacio * (totalBarras - 1)) / totalBarras);

        g2.setColor(new Color(230, 230, 230));
        for (int i = 0; i <= 5; i++) {
            int y = yBase - i * (yBase - margen) / 5;
            g2.drawLine(xInicio, y, width - margen, y);
            g2.setColor(new Color(100, 100, 100));
            g2.drawString(String.valueOf(mayorVenta * i / 5), xInicio - 50, y + 5);
            g2.setColor(new Color(230, 230, 230));
        }

        Color[] colores = {
                new Color(25, 118, 210),
                new Color(0, 172, 193),
                new Color(0, 200, 83),
                new Color(255, 143, 0),
                new Color(156, 39, 176),
                new Color(244, 67, 54)
        };

        int x = xInicio;
        for (int i = 0; i < totalBarras; i++) {
            int valor = listaCantidad.get(i);
            int alto = (int) (valor * (yBase - margen - 10) / (double) mayorVenta * progress);

            GradientPaint grad = new GradientPaint(x, yBase - alto, colores[i % colores.length], x, yBase, colores[i % colores.length].darker());
            g2.setPaint(grad);
            g2.fillRoundRect(x, yBase - alto, anchoBarra, alto, 15, 15);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.drawString(String.valueOf(valor), x + 5, yBase - alto - 5);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2.drawString(listaFechas.get(i), x, yBase + 15);

            x += anchoBarra + espacio;
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione fechas para Graficar ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    //Metodo para Limpíar campos
}
