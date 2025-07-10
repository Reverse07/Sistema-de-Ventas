package vista;

import DAO.ProductoDAO;
import Observador.Observer;
import Observador.Subject;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.sql.Connection;
import conexion.Conexion;
import DAO.CategoriaDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import modelo.Categoria;
import modelo.Producto;

public class InterGestionarProducto extends javax.swing.JInternalFrame implements Observer {

    private int idProducto;
    int obtenerIdCategoriaCombo = 0;

    public InterGestionarProducto() {
        FlatLightLaf.setup(); // Usa FlatDarkLaf si prefieres oscuro
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 15);
        UIManager.put("ProgressBar.arc", 15);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Table.showHorizontalLines", false);
        UIManager.put("Table.showVerticalLines", false);

        initComponents2();
        this.setSize(new Dimension(1000, 600));
        this.setTitle("📦 Gestionar Productos");

        cargarTablaProductos();
        cargarComboCategorias();
        aplicarBordesRedondeados();

        // Fondo moderno
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH));
        jLabel_wallpaper.setIcon(icono);
        this.repaint();

        // Agregar ActionListeners a los botones
        jButton_Actualizar.addActionListener(this::jButton_ActualizarActionPerformed);
        jButton_Eliminar.addActionListener(this::jButton_EliminarActionPerformed);
        Subject.agregarObservador(this);

    }

    private void initComponents2() {

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1 = new JPanel(new BorderLayout());
        jPanel1.setBackground(new Color(245, 245, 245));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabla
        jTable_productos = new JTable();
        jTable_productos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{
            "N°", "Nombre", "Cantidad", "Precio", "Descripción", "IGV", "Categoría", "Estado"
        }));
        jTable_productos.setRowHeight(28);
        jTable_productos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1 = new JScrollPane(jTable_productos);

        // Panel inferior de entrada
        jPanel2 = new JPanel(new GridLayout(4, 4, 10, 10));
        jPanel2.setBackground(Color.WHITE);

        txt_nombre = new JTextField();
        txt_precio = new JTextField();
        txt_cantidad = new JTextField();
        txt_descripcion = new JTextField();

        jComboBox_IGV = new JComboBox<>(new String[]{"Seleccione IGV:", "0%", "18%"});
        jComboBox_categoria = new JComboBox<>(new String[]{"Seleccione categoría"});

        jPanel2.add(createLabeledField("Nombre:", txt_nombre));
        jPanel2.add(createLabeledField("Precio:", txt_precio));
        jPanel2.add(createLabeledField("IGV:", jComboBox_IGV));
        jPanel2.add(createLabeledField("Cantidad:", txt_cantidad));
        jPanel2.add(createLabeledField("Descripción:", txt_descripcion));
        jPanel2.add(createLabeledField("Categoría:", jComboBox_categoria));

        // Panel de botones
        jPanel5 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // sin espacios extra
        jPanel5.setOpaque(false); // fondo transparente
        jPanel5.setBorder(BorderFactory.createEmptyBorder()); // sin margen
        jButton_Actualizar = createIconButton("Actualizar", "src/img/actualizarIcono.png", new Color(33, 150, 243));
        jButton_Eliminar = createIconButton("Eliminar", "src/img/borrarIcono.png", new Color(244, 67, 54));
        jPanel5.setBackground(Color.WHITE);
        jPanel5.add(jButton_Actualizar);
        jPanel5.add(jButton_Eliminar);

        // Agregar a panel principal
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.add(jPanel5, BorderLayout.EAST);

        jLabel_wallpaper = new JLabel();
        jLabel_wallpaper.setLayout(new BorderLayout());
        jLabel_wallpaper.add(jPanel1, BorderLayout.CENTER);

        this.setContentPane(jLabel_wallpaper);
    }

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JButton createIconButton(String text, String iconPath, Color color) {
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(text, resizedIcon);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setIconTextGap(10);
        button.setPreferredSize(new Dimension(160, 40));

        return button;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_productos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton_Actualizar = new javax.swing.JButton();
        jButton_Eliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        txt_precio = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        jComboBox_IGV = new javax.swing.JComboBox<>();
        jComboBox_categoria = new javax.swing.JComboBox<>();
        jLabel_wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Administrar Producto");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable_productos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 710, 250));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 730, 270));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_Actualizar.setBackground(new java.awt.Color(102, 255, 255));
        jButton_Actualizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_Actualizar.setForeground(new java.awt.Color(0, 0, 0));
        jButton_Actualizar.setText("Actualizar");
        jButton_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_Actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, -1));

        jButton_Eliminar.setBackground(new java.awt.Color(102, 255, 255));
        jButton_Eliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_Eliminar.setForeground(new java.awt.Color(0, 0, 0));
        jButton_Eliminar.setText("Eliminar");
        jButton_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 130, 270));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Descripcion : ");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        txt_nombre.setBackground(new java.awt.Color(255, 255, 255));
        txt_nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_nombre.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 186, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Cantidad : ");
        jLabel3.setToolTipText("");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Precio : ");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nombre : ");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("IGV : ");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Categoria : ");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, -1, -1));

        txt_cantidad.setBackground(new java.awt.Color(255, 255, 255));
        txt_cantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_cantidad.setForeground(new java.awt.Color(0, 0, 0));
        txt_cantidad.setEnabled(false);
        jPanel5.add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 186, -1));

        txt_precio.setBackground(new java.awt.Color(255, 255, 255));
        txt_precio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_precio.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 220, -1));

        txt_descripcion.setBackground(new java.awt.Color(255, 255, 255));
        txt_descripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_descripcion.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 190, -1));

        jComboBox_IGV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_IGV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione IGV:", "NO IGV", "18%" }));
        jPanel5.add(jComboBox_IGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 210, -1));

        jComboBox_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Categoria:", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(jComboBox_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 190, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 870, 100));
        getContentPane().add(jLabel_wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed

        Producto producto = new Producto();
        ProductoDAO controlProducto = new ProductoDAO();
        String igv = jComboBox_IGV.getSelectedItem().toString().trim();
        String categoria = jComboBox_categoria.getSelectedItem().toString().trim();

        if (txt_nombre.getText().isEmpty() || txt_cantidad.getText().isEmpty() || txt_precio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos ");
            return;
        }

        if (igv.equalsIgnoreCase("Seleccione IGV:")) {
            JOptionPane.showMessageDialog(null, "Seleccione IGV.");
            return;
        }

        if (categoria.equalsIgnoreCase("Seleccione categoria")) {
            JOptionPane.showMessageDialog(null, "Seleccione categoria");
            return;
        }

        try {
            producto.setNombre(txt_nombre.getText().trim());
            producto.setCantidad(Integer.parseInt(txt_cantidad.getText().trim()));

            String precioTXT = txt_precio.getText().trim();
            if (precioTXT.contains(",")) {
                precioTXT = precioTXT.replace(",", ".");
            }
            producto.setPrecio(Double.parseDouble(precioTXT));

            producto.setDescripcion(txt_descripcion.getText().trim());
            producto.setPorcentajeIva(igv.equalsIgnoreCase("18%") ? 18 : 0);

            this.idCategoria();
            producto.setIdCategoria(obtenerIdCategoriaCombo);
            producto.setEstado(1);
            producto.setIdProducto(idProducto);

            boolean actualizado = controlProducto.actualizar(producto, idProducto);
            if (actualizado) {
                JOptionPane.showMessageDialog(null, "✅ Registro Actualizado");

                // 👀 Notifica si hay stock bajo
                if (producto.getCantidad() <= 5) {
                    Subject.notificar("⚠️ Alerta: Stock bajo del producto '" + producto.getNombre() + "' (Cantidad: " + producto.getCantidad() + ")");

                }

                this.cargarComboCategorias();
                this.cargarTablaProductos();
                this.jComboBox_IGV.setSelectedItem("Seleccione IGV:");
                this.limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Error al actualizar. Puede que el nombre del producto ya exista.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Error en el formato de los datos: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton_ActualizarActionPerformed


    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarActionPerformed

        ProductoDAO controlProducto = new ProductoDAO();

        if (idProducto == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");

        } else {

            if (!controlProducto.eliminar(idProducto)) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                this.cargarTablaProductos();
                this.cargarComboCategorias();
                this.limpiar();

            } else {

                JOptionPane.showMessageDialog(null, "Error al eliminar");

            }

        }
    }//GEN-LAST:event_jButton_EliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JComboBox<String> jComboBox_IGV;
    private javax.swing.JComboBox<String> jComboBox_categoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable_productos;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_precio;
    // End of variables declaration//GEN-END:variables

    //Metodo para limpiar 
    private void limpiar() {

        txt_nombre.setText("");
        txt_cantidad.setText("");
        txt_precio.setText("");
        txt_descripcion.setText("");
        jComboBox_IGV.setSelectedItem("Seleccione IGV:");
        jComboBox_categoria.setSelectedItem("Seleccione categoria:");
    }

//Metodo para cargar Categorias en el JComboBox
    private void cargarComboCategorias() {

        Connection cn = Conexion.getConnection();
        String sql = "select*from tb_Categoria";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jComboBox_categoria.removeAllItems();
            jComboBox_categoria.addItem("Seleccione categoria");

            while (rs.next()) {

                jComboBox_categoria.addItem(rs.getString("descripcion"));
            }
            cn.close();

        } catch (SQLException e) {

            System.out.println("Error en la carga de la categoria : " + e);

        }

    }

    String descripcionCategoria = "";
    double precio = 0.0;
    int porcentajeigv = 0;
    double igv = 0;

    //Metodo para mostrar todas los productos
    private void cargarTablaProductos() {
        Connection cn = Conexion.getConnection();
        DefaultTableModel model = new DefaultTableModel();
        List<String> alertasStockBajo = new ArrayList<>(); // ⬅️ Lista de alertas

        String sql = "SELECT \n"
                + "    p.idProducto, \n"
                + "    p.nombre, \n"
                + "    p.cantidad, \n"
                + "    p.precio, \n"
                + "    p.descripcion, \n"
                + "    p.porcentajeIva, \n"
                + "    c.descripcion, \n"
                + "    p.estado \n"
                + "FROM \n"
                + "    tb_producto p \n"
                + "INNER JOIN \n"
                + "    tb_Categoria c ON p.idCategoria = c.idCategoria;";
        Statement st;

        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jTable_productos.setModel(model);
            jScrollPane1.setViewportView(jTable_productos);

            model.addColumn("Nº");//ID
            model.addColumn("nombre");
            model.addColumn("cantidad");
            model.addColumn("precio");
            model.addColumn("descripcion");
            model.addColumn("igv");
            model.addColumn("categoria");
            model.addColumn("estado");

            while (rs.next()) {
                precio = rs.getDouble("precio");
                porcentajeigv = rs.getInt("porcentajeIva");

                int cantidad = rs.getInt("cantidad");
                String nombreProducto = rs.getString("nombre");

                Object fila[] = new Object[8];
                for (int i = 0; i < 8; i++) {
                    if (i == 5) {
                        igv = this.calcularIGV(precio, porcentajeigv);
                        fila[i] = porcentajeigv + "%";
                    } else {
                        fila[i] = rs.getObject(i + 1);
                    }
                }

                model.addRow(fila);

                // ⚠️ Guardar alerta si hay stock bajo
                if (cantidad <= 5) {
                    alertasStockBajo.add("⚠️ Stock bajo: '" + nombreProducto + "' (Cantidad: " + cantidad + ")");
                }
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de productos: " + e);
        }

        // ✅ Mostrar alertas luego de que ya se cargó visualmente la tabla
        if (!alertasStockBajo.isEmpty()) {
            Timer timer = new Timer(2000, e -> {
                String mensaje = String.join("\n", alertasStockBajo);
                JOptionPane.showMessageDialog(this, mensaje, "Alerta de Stock Bajo", JOptionPane.WARNING_MESSAGE);
            });
            timer.setRepeats(false); // solo se ejecuta una vez
            timer.start();
        }

        // Captura el evento de clic en la tabla
        jTable_productos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_productos.rowAtPoint(e.getPoint());
                int columna_point = 0;
                if (fila_point > -1) {
                    idProducto = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosProductoSeleccionado(idProducto);
                }
            }
        });
    }

    //Metodo para calcular IGV
    private double calcularIGV(double precio, int porcentajeIGV) {
        double igvCalculado = 0.0;
        if (porcentajeIGV > 0) {
            igvCalculado = precio * porcentajeIGV / 100.0;
        }
        return Math.round(igvCalculado * 100.0) / 100.0;
    }

    private void EnviarDatosProductoSeleccionado(int idProducto) {
        try {

            Connection cn = Conexion.getConnection();
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tb_producto WHERE idProducto ='" + idProducto + "'");

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_nombre.setText(rs.getString("nombre"));
                txt_cantidad.setText(rs.getString("cantidad"));
                txt_precio.setText(rs.getString("precio"));
                txt_descripcion.setText(rs.getString("descripcion"));
                int igv = rs.getInt("porcentajeIva");

                switch (igv) {

                    case 0:

                        jComboBox_IGV.setSelectedItem("No IGV");
                        break;

                    case 18:

                        jComboBox_IGV.setSelectedItem("18%");
                        break;

                    default:

                        jComboBox_IGV.setSelectedItem(0);
                        break;

                }

                int idCate = rs.getInt("idCategoria");
                jComboBox_categoria.setSelectedItem(relacionarCategorias(idCate));

            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al seleccionar producto: " + e);
        }

    }
    //Metodo para relacionar Categorias

    private String relacionarCategorias(int idCategoria) {

        String sql = "select descripcion  from tb_Categoria where idCategoria = '" + idCategoria + "'";
        Statement st;

        try {

            Connection cn = Conexion.getConnection();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                descripcionCategoria = rs.getString("descripcion");
            }
            cn.close();

        } catch (SQLException e) {

            System.out.println("Error al obtener el id de la categoria : " + e);

        }

        return descripcionCategoria;

    }

    //Metodo para obtener categoria
    private int idCategoria() {

        String sql = "select * from tb_Categoria where descripcion = '" + this.jComboBox_categoria.getSelectedItem() + "'";
        Statement st;

        try {

            Connection cn = Conexion.getConnection();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                obtenerIdCategoriaCombo = rs.getInt("idCategoria");

            }

        } catch (SQLException e) {

        }

        return obtenerIdCategoriaCombo;

    }

    private void aplicarBordesRedondeados() {
        Color bordeColor = new Color(150, 150, 150); // Color gris claro
        int radio = 12;

        Border borde = new RoundedBorder(radio, bordeColor);

        // Campos de texto
        txt_nombre.setBorder(borde);
        txt_precio.setBorder(borde);
        txt_cantidad.setBorder(borde);
        txt_descripcion.setBorder(borde);

        // Botones
        jButton_Actualizar.setBorder(borde);
        jButton_Eliminar.setBorder(borde);
    }

    @Override
    public void actualizar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Alerta de Stock", JOptionPane.WARNING_MESSAGE);
    }

}
