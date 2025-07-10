package vista;

import DAO.UsuarioDAO;
import Factory.DAOFactory;
import Servicio.UsuarioService;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Usuario;

public class InterUsuario extends javax.swing.JInternalFrame {

    private final UsuarioService usuarioService = new UsuarioService(DAOFactory.crearUsuarioDAO());

    public InterUsuario() {

        initComponents2();
        this.setSize(600, 420);
        this.setTitle("Registrar Usuario");
        this.setClosable(true);
        this.setIconifiable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents2();
    }

    private void initComponents2() {
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 245, 255), 0, h, new Color(200, 220, 240));
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;

        // Inicializar campos
        txt_nombre = new JTextField(20);
        txt_apellido = new JTextField(20);
        txt_usuario = new JTextField(20);
        txt_password = new JPasswordField(20);
        txt_password_visible = new JTextField(20);
        txt_telefono = new JTextField(20);
        jCheckBox_ver_clave = new JCheckBox("Ver contraseña");

        int fila = 0;
        agregarFila(mainPanel, gbc, fila++, "Nombre:", txt_nombre, "/img/user.png");
        agregarFila(mainPanel, gbc, fila++, "Apellido:", txt_apellido, "/img/user-tag.png");
        agregarFila(mainPanel, gbc, fila++, "Usuario:", txt_usuario, "/img/user-check.png");
        agregarFila(mainPanel, gbc, fila++, "Teléfono:", txt_telefono, "/img/phone.png");

        // Contraseña: panel con CardLayout
        JPanel passwordPanel = new JPanel(new CardLayout());
        passwordPanel.setOpaque(false);
        passwordPanel.add(txt_password, "oculto");
        passwordPanel.add(txt_password_visible, "visible");
        txt_password_visible.setVisible(false);

        jCheckBox_ver_clave.setOpaque(false);
        jCheckBox_ver_clave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CardLayout cl = (CardLayout) passwordPanel.getLayout();
                if (jCheckBox_ver_clave.isSelected()) {
                    txt_password_visible.setText(new String(txt_password.getPassword()));
                    txt_password.setVisible(false);
                    txt_password_visible.setVisible(true);
                    cl.show(passwordPanel, "visible");
                } else {
                    txt_password.setText(txt_password_visible.getText().trim());
                    txt_password.setVisible(true);
                    txt_password_visible.setVisible(false);
                    cl.show(passwordPanel, "oculto");
                }
            }
        });

        agregarFilaPersonalizada(mainPanel, gbc, fila++, "Contraseña:", passwordPanel, "/img/key.jpg");

        // CheckBox "ver contraseña"
        gbc.gridx = 1;
        gbc.gridy = fila++;
        gbc.gridwidth = 1;
        mainPanel.add(jCheckBox_ver_clave, gbc);

        // Botón Guardar personalizado
        ImageIcon iconGuardar = new ImageIcon(getClass().getResource("/img/GuardarFondo.png"));
        Image scaledImage = iconGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        iconGuardar = new ImageIcon(scaledImage);

        jButton_Guardar = new GradientButton("Guardar Usuario", iconGuardar);
        jButton_Guardar.setPreferredSize(new Dimension(200, 40));
        jButton_Guardar.addActionListener(this::jButton_GuardarActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(jButton_Guardar);

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        // Contenedor general
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.decode("#F5F5F5"));
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        this.add(wrapperPanel, BorderLayout.CENTER);
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String labelText, JTextField textField, String rutaIcono) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        URL iconURL = getClass().getResource(rutaIcono);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } else {
            System.err.println("⚠ No se encontró el ícono: " + rutaIcono);
        }

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panel.add(textField, gbc);
    }

    private void agregarFilaPersonalizada(JPanel panel, GridBagConstraints gbc, int fila, String labelText, JPanel campoPanel, String rutaIcono) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        URL iconURL = getClass().getResource(rutaIcono);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } else {
            System.err.println("⚠ No se encontró el ícono: " + rutaIcono);
        }

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        campoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panel.add(campoPanel, gbc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_apellido = new javax.swing.JTextField();
        txt_usuario = new javax.swing.JTextField();
        jButton_Guardar = new javax.swing.JButton();
        txt_telefono = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        jCheckBox_ver_clave = new javax.swing.JCheckBox();
        txt_password_visible = new javax.swing.JTextField();
        jWallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nuevo Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nombre:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 50, 90, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Apellido:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 90, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Usuario:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 90, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Telefono:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 90, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Password:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 90, -1));

        txt_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 170, -1));

        txt_apellido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_apellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 170, -1));

        txt_usuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 170, -1));

        jButton_Guardar.setBackground(new java.awt.Color(0, 204, 204));
        jButton_Guardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_Guardar.setText("Guardar");
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 90, 30));

        txt_telefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 170, -1));

        txt_password.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        getContentPane().add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 170, -1));

        jCheckBox_ver_clave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox_ver_claveMouseClicked(evt);
            }
        });
        getContentPane().add(jCheckBox_ver_clave, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, -1, 20));

        txt_password_visible.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_password_visible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_password_visibleActionPerformed(evt);
            }
        });
        getContentPane().add(txt_password_visible, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 170, -1));

        jWallpaper.setBackground(new java.awt.Color(255, 255, 255));
        jWallpaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo3.jpg"))); // NOI18N
        getContentPane().add(jWallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed

        if (txt_nombre.getText().isEmpty() || txt_apellido.getText().isEmpty() || txt_usuario.getText().isEmpty()
                || obtenerPassword().isEmpty() || txt_telefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Completa todos los campos");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(txt_nombre.getText().trim());
        usuario.setApellido(txt_apellido.getText().trim());
        usuario.setUsuario(txt_usuario.getText().trim());
        usuario.setPassword(obtenerPassword());
        usuario.setTelefono(txt_telefono.getText().trim());
        usuario.setEstado(1);

        boolean registrado = usuarioService.registrarUsuario(usuario);

        if (registrado) {
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "El usuario ya está registrado o hubo un error");
        }

        this.limpiar();

    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void txt_password_visibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_password_visibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_password_visibleActionPerformed

    private void jCheckBox_ver_claveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox_ver_claveMouseClicked

        if (jCheckBox_ver_clave.isSelected()) {
            String pass = new String(txt_password.getPassword()).trim();
            txt_password_visible.setText(pass);
            txt_password.setVisible(false);
            txt_password_visible.setVisible(true);
        } else {
            String pass = txt_password_visible.getText().trim();
            txt_password.setText(pass);
            txt_password.setVisible(true);
            txt_password_visible.setVisible(false);
        }
    }

    private String obtenerPassword() {
        return txt_password.isVisible()
                ? String.valueOf(txt_password.getPassword()).trim()
                : txt_password_visible.getText().trim();

    }//GEN-LAST:event_jCheckBox_ver_claveMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JCheckBox jCheckBox_ver_clave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jWallpaper;
    private javax.swing.JTextField txt_apellido;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_password_visible;
    private javax.swing.JTextField txt_telefono;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables

    //Metodo para Limpíar campos
    private void limpiar() {

        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_usuario.setText("");
        txt_telefono.setText("");
        txt_password.setText("");
        txt_password_visible.setText("");

    }
}
