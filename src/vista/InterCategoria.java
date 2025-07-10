package vista;

import com.formdev.flatlaf.FlatLightLaf;
import DAO.CategoriaDAO;
import Factory.DAOFactory;
import Servicio.CategoriaService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import modelo.Categoria;

public class InterCategoria extends javax.swing.JInternalFrame {

    private final CategoriaService categoriaService;

    public InterCategoria() {

        this.categoriaService = new CategoriaService(DAOFactory.crearCategoriaDAO());

        FlatLightLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 10);

        initComponents2();

        setTitle("📁 Nueva Categoría");
        setSize(new Dimension(500, 320));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(false);
    }

    private void initComponents2() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Título
        JLabel titleLabel = new JLabel("Formulario de nueva categoría:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 33, 33));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        // Label con ícono
        JPanel labelDescPanel = createLabelWithIcon("Descripción:", "src/img/descripcion.png");
        formPanel.add(labelDescPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        jText_Descripcion = new JTextField();
        jText_Descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(jText_Descripcion, gbc);

        // Botón Guardar usando GradientButton
        ImageIcon icon = new ImageIcon("src/img/GuardarFondo.png");
        Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);

        jButton1 = new GradientButton("Guardar", scaledIcon);
        jButton1.setPreferredSize(new Dimension(160, 45));
        jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Opcional: cambia el cursor o efecto hover, si GradientButton no lo maneja
                jButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Aquí puedes revertir cualquier cambio de hover si quieres
            }
        });

        jButton1.addActionListener(this::jButton1ActionPerformed);

        // Panel botón centrado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(jButton1);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        jLabel_Wallpaper = new JLabel();
        jLabel_Wallpaper.setLayout(new BorderLayout());
        jLabel_Wallpaper.setBackground(new Color(45, 65, 95));
        jLabel_Wallpaper.setOpaque(true);
        jLabel_Wallpaper.add(mainPanel, BorderLayout.CENTER);

        setContentPane(jLabel_Wallpaper);
    }

    private JPanel createLabelWithIcon(String text, String iconPath) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaled));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        textLabel.setForeground(new Color(33, 33, 33));

        panel.add(iconLabel);
        panel.add(textLabel);

        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jText_Descripcion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Nueva Categoria");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Descripcion Categoria:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jText_Descripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(jText_Descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 170, -1));

        jButton1.setBackground(new java.awt.Color(0, 204, 204));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 90, 30));

        jLabel_Wallpaper.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Wallpaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo3.jpg"))); // NOI18N
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 170));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String descripcion = jText_Descripcion.getText().trim();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setDescripcion(descripcion);
        categoria.setEstado(1);

        try {
            boolean guardado = categoriaService.registrarCategoria(categoria);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "✅ Registro guardado correctamente.");
                jText_Descripcion.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo guardar la categoría.");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "⚠ Advertencia", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠ Error inesperado: " + ex.getMessage());
        }


    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JTextField jText_Descripcion;
    // End of variables declaration//GEN-END:variables
}
