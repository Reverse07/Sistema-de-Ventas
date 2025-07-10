package vista;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import Factory.DAOFactory;
import Servicio.CategoriaService;
import Servicio.ProductoService;
import conexion.Conexion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Producto;

public class InterProducto extends javax.swing.JInternalFrame {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public InterProducto() {
        this.setSize(new Dimension(540, 400));
        this.setTitle("Nuevo Producto");
        this.setClosable(true);
        this.setIconifiable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.productoService = new ProductoService(DAOFactory.crearProductoDAO());
        this.categoriaService = new CategoriaService(DAOFactory.crearCategoriaDAO());

        initComponents2();
        cargarComboCategorias();
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

                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 240, 255), 0, h, new Color(180, 200, 230));
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;

        txt_nombre = new JTextField(20);
        txt_precio = new JTextField(20);
        txt_cantidad = new JTextField(20);
        txt_descripcion = new JTextField(20);
        jComboBox_categoria = new JComboBox<>();
        jComboBox_IGV = new JComboBox<>(new String[]{"Seleccione IGV:", "NO IGV", "18%"});

        int fila = 0;
        agregarFila(mainPanel, gbc, fila++, "Nombre:", txt_nombre, "/img/cliente.png");
        agregarFila(mainPanel, gbc, fila++, "Precio:", txt_precio, "/img/solesIcono.png");
        agregarFila(mainPanel, gbc, fila++, "Cantidad:", txt_cantidad, "/img/stockIcono.png");
        agregarFila(mainPanel, gbc, fila++, "Descripción:", txt_descripcion, "/img/descripcion.png");
        agregarFila(mainPanel, gbc, fila++, "Categoría:", jComboBox_categoria, "/img/categorias.png");
        agregarFila(mainPanel, gbc, fila++, "IGV:", jComboBox_IGV, "/img/IGVIcono.png");

        // Botón Guardar personalizado
        ImageIcon iconGuardar = new ImageIcon(getClass().getResource("/img/GuardarFondo.png"));
        Image scaledImage = iconGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        iconGuardar = new ImageIcon(scaledImage);

        jButton_Guardar = new GradientButton("Guardar Producto", iconGuardar);
        jButton_Guardar.setPreferredSize(new Dimension(260, 40));
        jButton_Guardar.addActionListener(this::jButton_GuardarActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(jButton_Guardar);

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(buttonPanel, gbc);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.decode("#F5F5F5"));
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        this.add(wrapperPanel, BorderLayout.CENTER);
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String labelText, JComponent campo, String rutaIcono) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        URL iconURL = getClass().getResource(rutaIcono);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } else {
            System.err.println("⚠ Icono no encontrado: " + rutaIcono);
        }

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        panel.add(campo, gbc);
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
        jLabel7 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_cantidad = new javax.swing.JTextField();
        txt_precio = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        jComboBox_IGV = new javax.swing.JComboBox<>();
        jComboBox_categoria = new javax.swing.JComboBox<>();
        jButton_Guardar = new javax.swing.JButton();
        jComboBox_categoria1 = new javax.swing.JComboBox<>();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nuevo Producto");
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
        jLabel3.setText("Cantidad:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 90, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Precio:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 90, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Descripcion:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 90, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("IGV:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 90, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Categorias:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 90, -1));

        txt_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 170, -1));

        txt_cantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 170, -1));

        txt_precio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 170, -1));

        txt_descripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 170, -1));

        jComboBox_IGV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_IGV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione IGV:", "NO IGV", "18%" }));
        getContentPane().add(jComboBox_IGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 170, -1));

        jComboBox_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Categoria:", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 170, -1));

        jButton_Guardar.setBackground(new java.awt.Color(0, 204, 204));
        jButton_Guardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_Guardar.setText("Guardar");
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 90, 30));

        jComboBox_categoria1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Categoria:", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox_categoria1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 170, -1));

        jLabel_Wallpaper.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Wallpaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo3.jpg"))); // NOI18N
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed

        String nombre = txt_nombre.getText().trim();
        String cantidadStr = txt_cantidad.getText().trim();
        String precioStr = txt_precio.getText().trim();
        String descripcion = txt_descripcion.getText().trim();
        String igv = jComboBox_IGV.getSelectedItem().toString().trim();
        String categoria = jComboBox_categoria.getSelectedItem().toString().trim();

        // Validaciones
        if (nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            txt_nombre.setBackground(Color.red);
            txt_cantidad.setBackground(Color.red);
            txt_precio.setBackground(Color.red);
            return;
        }

        if (igv.equalsIgnoreCase("Seleccione IGV:")) {
            JOptionPane.showMessageDialog(null, "Seleccione IGV.");
            return;
        }

        if (categoria.equalsIgnoreCase("Seleccione categoría")) {
            JOptionPane.showMessageDialog(null, "Seleccione categoría.");
            return;
        }

        if (productoService.existeProducto(nombre)) {
            JOptionPane.showMessageDialog(null, "El producto ya existe en la base de datos.");
            return;
        }

        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setCantidad(Integer.parseInt(cantidadStr));

            // Validar y convertir precio
            precioStr = precioStr.replace(",", ".");
            double precio = Double.parseDouble(precioStr);
            producto.setPrecio(precio);

            producto.setDescripcion(descripcion);
            producto.setPorcentajeIva(igv.equalsIgnoreCase("18%") ? 18 : 0);
            producto.setIdCategoria(categoriaService.obtenerIdCategoria(categoria));
            producto.setEstado(1);

            if (productoService.registrarProducto(producto)) {
                JOptionPane.showMessageDialog(null, "Registro guardado correctamente.");
                txt_nombre.setBackground(Color.green);
                txt_cantidad.setBackground(Color.green);
                txt_precio.setBackground(Color.green);
                txt_descripcion.setBackground(Color.green);

                limpiar();
                cargarComboCategorias();
                jComboBox_IGV.setSelectedItem("Seleccione IGV:");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Formato incorrecto en cantidad o precio.");
        }
    }

    private void limpiar() {
        txt_nombre.setText("");
        txt_cantidad.setText("");
        txt_precio.setText("");
        txt_descripcion.setText("");
    }

    private void cargarComboCategorias() {
        jComboBox_categoria.removeAllItems();
        jComboBox_categoria.addItem("Seleccione categoría");

        List<String> nombres = categoriaService.listarNombresCategoriasActivas();
        for (String nombre : nombres) {
            jComboBox_categoria.addItem(nombre);
        }

    }//GEN-LAST:event_jButton_GuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JComboBox<String> jComboBox_IGV;
    private javax.swing.JComboBox<String> jComboBox_categoria;
    private javax.swing.JComboBox<String> jComboBox_categoria1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_precio;
    // End of variables declaration//GEN-END:variables

}
