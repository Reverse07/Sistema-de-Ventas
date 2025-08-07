package vista;

// ========== Librerías de Java Swing ==========
import javax.swing.*;
import java.awt.*;

// ========== Librerías para manejo de fechas y datos ==========
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// ========== Librerías para conexión a base de datos ==========
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


// ========== Proyecto interno ==========
import conexion.Conexion;
import Servicio.VentaFacade;
import com.formdev.flatlaf.FlatLightLaf;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import modelo.CabeceraVenta;
import modelo.DetalleVenta;
import vista.GradientButton;

public class InterFacturacion extends javax.swing.JInternalFrame {

   
    private JPanel panelPago;

    //Modelo de los datos
    private DefaultTableModel modeloDatosProducto;

    //Lista para el detalle de ventas de los productos
    ArrayList<DetalleVenta> listaProductos = new ArrayList<>();
    private DetalleVenta producto;
    private int idCliente = 0; //ID del cliente seleccionado

    private int idProducto = 0;
    private String nombre = "";
    private int cantidadProductoBBDD = 0;
    private double precioUnitario = 0.0;
    private int porcentajeIgv = 0;

    private int cantidad = 0;  //Cantidad de productos a comprar 
    private double subtotal = 0.0; // Cantidad * precio
    private int descuento = 0;
    private double igv = 0.0;
    private double totalPagar = 0.0;

    //Variable para calculos globales
    private double subtotalGeneral = 0.0;
    private double descuentoGeneral = 0.0;
    private double igvGeneral = 0.0;
    private double totalPagarGeneral = 0.0;

    //Fin de variables de calculos globales
    private int auxIdDetalle = 1; //ID del detalle de venta

    public InterFacturacion() {

        // Estilo FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 10);

        initComponents2();
        this.setSize(new Dimension(950, 650));
        this.setTitle("🛒 Facturación");
        this.setMaximizable(true);

        // Inicializar datos
        CargarComboClientes();
        CargarComboProductos();
        inicializarTablaProductos();

        // Valores por defecto
        txt_efectivo.setEnabled(false);
        jButton_calcular_cambio.setEnabled(false);
        txt_subtotal.setText("0.0");
        txt_igv.setText("0.0");
        txt_descuento.setText("0.0");
        txt_total_pagar.setText("0.0");
    }

    // ====== Inicialización UI ======
    private void initComponents2() {

        jPanel1 = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color color1 = new Color(245, 247, 250);
                Color color2 = new Color(230, 240, 250);
                g2.setPaint(new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        jPanel1.setOpaque(false);

        // === PANEL CLIENTE ===
        jComboBox_cliente = new JComboBox<>();
        txt_cliente_buscar = new JTextField();
        jButton_busca_cliente = createGradientButton("Buscar", "/img/search.png",
                new Color(25, 118, 210), new Color(21, 101, 192)); // Azul profesional

        JPanel panelCliente = createTitledPanel("Cliente");
        panelCliente.add(jComboBox_cliente);
        panelCliente.add(txt_cliente_buscar);
        panelCliente.add(jButton_busca_cliente);

        jComboBox_cliente.setPreferredSize(new Dimension(250, 30));
        txt_cliente_buscar.setPreferredSize(new Dimension(150, 30));
        jButton_busca_cliente.addActionListener(e -> jButton_busca_clienteActionPerformed(e));

        // === PANEL PRODUCTO ===
        jComboBox_producto = new JComboBox<>();
        txt_cantidad = new JTextField();
        jButton_añadir_producto = createGradientButton("Añadir", "/img/add.png",
                new Color(56, 142, 60), new Color(46, 125, 50)); // Verde profesional

        JPanel panelProducto = createTitledPanel("Producto");
        panelProducto.add(jComboBox_producto);
        panelProducto.add(txt_cantidad);
        panelProducto.add(jButton_añadir_producto);

        jComboBox_producto.setPreferredSize(new Dimension(250, 30));
        txt_cantidad.setPreferredSize(new Dimension(80, 30));
        jButton_añadir_producto.addActionListener(e -> jButton_añadir_productoActionPerformed(e));

        // === TABLA PRODUCTOS ===
        jTable_productos = new JTable();
        jTable_productos.setRowHeight(28);
        jTable_productos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1 = new JScrollPane(jTable_productos);

        JPanel panelTabla = createTitledPanel("Detalle Venta");
        panelTabla.setLayout(new BorderLayout());
        panelTabla.add(jScrollPane1, BorderLayout.CENTER);
        jTable_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_productosMouseClicked(evt);
            }
        });

        // === PANEL TOTALES ===
        txt_subtotal = crearCampoTotal();
        txt_descuento = crearCampoTotal();
        txt_igv = crearCampoTotal();
        txt_total_pagar = crearCampoTotal();

        JPanel panelTotales = createTitledPanel("Totales");
        panelTotales.setLayout(new GridLayout(4, 2, 10, 10));
        panelTotales.add(new JLabel("Subtotal:"));
        panelTotales.add(txt_subtotal);
        panelTotales.add(new JLabel("Descuento:"));
        panelTotales.add(txt_descuento);
        panelTotales.add(new JLabel("IGV:"));
        panelTotales.add(txt_igv);
        panelTotales.add(new JLabel("Total Pagar:"));
        panelTotales.add(txt_total_pagar);

        // === PANEL PAGO ===
        txt_efectivo = new JTextField();
        txt_cambio = new JTextField();
        jButton_calcular_cambio = createGradientButton("Cambio", "/img/money.png",
                new Color(251, 192, 45), new Color(249, 168, 37)); // Amarillo profesional
        jButton_RegistrarVenta = createGradientButton("Registrar", "/img/save.png",
                new Color(211, 47, 47), new Color(198, 40, 40)); // Rojo elegante

        panelPago = createTitledPanel("Pago");
        panelPago.setLayout(new FlowLayout());

        panelPago.add(new JLabel("Efectivo:"));
        panelPago.add(txt_efectivo);
        panelPago.add(new JLabel("Cambio:"));
        panelPago.add(txt_cambio);
        panelPago.add(jButton_calcular_cambio);
        panelPago.add(jButton_RegistrarVenta);

        txt_efectivo.setPreferredSize(new Dimension(100, 30));
        txt_cambio.setPreferredSize(new Dimension(100, 30));
        jButton_calcular_cambio.addActionListener(e -> jButton_calcular_cambioActionPerformed(e));
        jButton_RegistrarVenta.addActionListener(e -> jButton_RegistrarVentaActionPerformed(e));

        // === Layout Principal ===
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 10, 10));
        panelSuperior.setOpaque(false);
        panelSuperior.add(panelCliente);
        panelSuperior.add(panelProducto);

        JPanel panelDerecha = new JPanel(new BorderLayout(10, 10));
        panelDerecha.setOpaque(false);
        panelDerecha.add(panelTotales, BorderLayout.NORTH);
        panelDerecha.add(panelPago, BorderLayout.CENTER);

        jPanel1.add(panelSuperior, BorderLayout.NORTH);
        jPanel1.add(panelTabla, BorderLayout.CENTER);
        jPanel1.add(panelDerecha, BorderLayout.EAST);

        jLabel_wallpaper = new JLabel();
        jLabel_wallpaper.setLayout(new BorderLayout());
        jLabel_wallpaper.add(jPanel1, BorderLayout.CENTER);
        setContentPane(jLabel_wallpaper);
    }

    // ====== Métodos auxiliares ======
    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JTextField crearCampoTotal() {
        JTextField campo = new JTextField();
        campo.setHorizontalAlignment(JTextField.RIGHT);
        campo.setEditable(false);
        campo.setBackground(new Color(240, 240, 240));
        campo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return campo;
    }

    private GradientButton createGradientButton(String text, String iconPath, Color start, Color end) {
        URL iconURL = getClass().getResource(iconPath);
        ImageIcon icon = null;
        if (iconURL != null) {
            icon = new ImageIcon(new ImageIcon(iconURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
        GradientButton button = new GradientButton(text, icon, start, end);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    //Metodo para inicializar la tabla de los productos
    private void inicializarTablaProductos() {

        modeloDatosProducto = new DefaultTableModel();
        //añadir columnas

        modeloDatosProducto.addColumn("N");
        modeloDatosProducto.addColumn("Nombre");
        modeloDatosProducto.addColumn("Cantidad");
        modeloDatosProducto.addColumn("P. Unitario");
        modeloDatosProducto.addColumn("SubTotal");
        modeloDatosProducto.addColumn("Descuento");
        modeloDatosProducto.addColumn("IGV");
        modeloDatosProducto.addColumn("TotalPagar");
        modeloDatosProducto.addColumn("Accion");

        //Agregar datos de modelo tabla
        this.jTable_productos.setModel(modeloDatosProducto);

    }

    //Metodo para presentar informacion de la tabla
    private void listaTablaProducto() {

        this.modeloDatosProducto.setRowCount(listaProductos.size());

        for (int i = 0; i < listaProductos.size(); i++) {

            this.modeloDatosProducto.setValueAt(i + 1, i, 0);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getNombre(), i, 1);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getCantidad(), i, 2);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getPrecioUnitario(), i, 3);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getSubtotal(), i, 4);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getDescuento(), i, 5);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getIva(), i, 6);
            this.modeloDatosProducto.setValueAt(listaProductos.get(i).getTotalPagar(), i, 7);
            this.modeloDatosProducto.setValueAt("Eliminar", i, 8); //Aqui luego de poner el boton eliminar
        }

        //Añadir al JTable 
        jTable_productos.setModel(modeloDatosProducto);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_cliente = new javax.swing.JComboBox<>();
        jComboBox_producto = new javax.swing.JComboBox<>();
        txt_cliente_buscar = new javax.swing.JTextField();
        txt_cantidad = new javax.swing.JTextField();
        jButton_busca_cliente = new javax.swing.JButton();
        jButton_añadir_producto = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_productos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        txt_descuento = new javax.swing.JTextField();
        txt_igv = new javax.swing.JTextField();
        txt_total_pagar = new javax.swing.JTextField();
        txt_efectivo = new javax.swing.JTextField();
        txt_cambio = new javax.swing.JTextField();
        jButton_calcular_cambio = new javax.swing.JButton();
        jButton_RegistrarVenta = new javax.swing.JButton();
        jLabel_wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Facturacion");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Cliente:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Producto:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Cantidad:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 80, -1));

        jComboBox_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox_cliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione cliente:", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 170, 30));

        jComboBox_producto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox_producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione producto:", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 170, -1));

        txt_cliente_buscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(txt_cliente_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 160, 30));

        txt_cantidad.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        txt_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cantidadActionPerformed(evt);
            }
        });
        getContentPane().add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 60, 20));

        jButton_busca_cliente.setBackground(new java.awt.Color(51, 153, 0));
        jButton_busca_cliente.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton_busca_cliente.setForeground(new java.awt.Color(255, 255, 255));
        jButton_busca_cliente.setText("Buscar");
        jButton_busca_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_busca_clienteActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_busca_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 80, 30));

        jButton_añadir_producto.setBackground(new java.awt.Color(51, 153, 0));
        jButton_añadir_producto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_añadir_producto.setText("Añadir productos");
        jButton_añadir_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_añadir_productoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_añadir_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 150, 20));

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
        jTable_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_productos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 740, 190));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 760, 210));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("SubTotal:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Descuento:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("IGV:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Total a pagar:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Efectivo:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Cambio:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        txt_subtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_subtotal.setEnabled(false);
        jPanel2.add(txt_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 150, -1));

        txt_descuento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_descuento.setEnabled(false);
        jPanel2.add(txt_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, -1));

        txt_igv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_igv.setEnabled(false);
        jPanel2.add(txt_igv, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 150, -1));

        txt_total_pagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total_pagar.setEnabled(false);
        jPanel2.add(txt_total_pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 130, -1));

        txt_efectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(txt_efectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 130, -1));

        txt_cambio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_cambio.setEnabled(false);
        jPanel2.add(txt_cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 130, -1));

        jButton_calcular_cambio.setBackground(new java.awt.Color(51, 255, 255));
        jButton_calcular_cambio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_calcular_cambio.setForeground(new java.awt.Color(255, 0, 102));
        jButton_calcular_cambio.setText("Calcular Cambio");
        jButton_calcular_cambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_calcular_cambioActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_calcular_cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 130, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 380, 210));

        jButton_RegistrarVenta.setBackground(new java.awt.Color(51, 255, 255));
        jButton_RegistrarVenta.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton_RegistrarVenta.setForeground(new java.awt.Color(51, 153, 0));
        jButton_RegistrarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/result_impresora3-removebg-preview.png"))); // NOI18N
        jButton_RegistrarVenta.setText("Registrar Venta");
        jButton_RegistrarVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_RegistrarVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_RegistrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RegistrarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_RegistrarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 170, 100));
        getContentPane().add(jLabel_wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cantidadActionPerformed

    private void jButton_añadir_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_añadir_productoActionPerformed

        String combo = this.jComboBox_producto.getSelectedItem().toString();

        //Validar que seleccione un producto
        if (combo.equalsIgnoreCase("Seleccione producto:")) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");

        } else {

            //Validar que ingrese una cantidad
            if (!txt_cantidad.getText().isEmpty()) {

                //Validar que no ingrese caracteres no numericos
                boolean validacion = validar(txt_cantidad.getText());

                if (validacion == true) {

                    //Validar que la cantidad sea mayor a 0
                    if (Integer.parseInt(txt_cantidad.getText()) > 0) {

                        cantidad = Integer.parseInt(txt_cantidad.getText());
                        //Ejecutar metodo para obtener datos del producto

                        this.DatosDelProducto();

                        //Validar que en la cantidad de productos seleccionado no sea mayor al stock de la base de datos
                        if (cantidad <= cantidadProductoBBDD) {

                            subtotal = precioUnitario * cantidad;
                            totalPagar = subtotal + igv + descuento;

                            //Redondear decimales
                            subtotal = (double) Math.round(subtotal * 100) / 100;
                            igv = (double) Math.round(igv * 100) / 100;
                            descuento = (int) ((double) Math.round(descuento * 100) / 100);
                            totalPagar = (double) Math.round(totalPagar * 100) / 100;

                            //Se crea un nuevo producto
                            producto = new DetalleVenta(auxIdDetalle, 1, idProducto, cantidad,
                                    nombre, precioUnitario, subtotal,
                                    (int) Math.round(igv), // iva
                                    totalPagar,
                                    1, // estado
                                    (int) Math.round(descuento));

                            //Añadir a la lista
                            listaProductos.add(producto);
                            JOptionPane.showMessageDialog(null, "Producto agregado");
                            auxIdDetalle++;
                            txt_cantidad.setText(""); //Limpiar el campo

                            //Volver a cargar combo productos
                            this.CargarComboProductos();
                            this.CalcularTotalPagar();
                            txt_efectivo.setEnabled(true);
                            jButton_calcular_cambio.setEnabled(true);

                        } else {
                            JOptionPane.showMessageDialog(null, "La cantidad supera el stock");

                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "La cantidad tiene que ser mayor a 0");
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "En la cantidad no se admiten cantidades no numericos");

                }

            } else {

                JOptionPane.showMessageDialog(null, "Ingrese la cantidad de productos");

            }

        }

        //Llamar al metodo
        this.listaTablaProducto();

    }//GEN-LAST:event_jButton_añadir_productoActionPerformed

    private void jButton_busca_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_busca_clienteActionPerformed

        String clienteBuscar = txt_cliente_buscar.getText().trim();
        Connection cn = Conexion.getConnection();

        String sql = "select nombre, apellido from tb_cliente where cedula = '" + clienteBuscar + "'";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {

                jComboBox_cliente.setSelectedItem(rs.getString("nombre") + " " + rs.getString("apellido"));

            } else {

                jComboBox_cliente.setSelectedItem("Seleccione cliente:");
                JOptionPane.showMessageDialog(null, "Cedula de cliente no encontrada");
            }

            txt_cliente_buscar.setText("");
            cn.close();

        } catch (SQLException e) {

            System.out.println("Error al buscar el cliente : " + e);
        }


    }//GEN-LAST:event_jButton_busca_clienteActionPerformed

    private void jButton_calcular_cambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_calcular_cambioActionPerformed

        if (!txt_efectivo.getText().isEmpty()) {

            //Validamos que el usuario no ingrese otros caracteres no numericos
            boolean validar = validarDouble(txt_efectivo.getText());

            if (validar == true) {

                //Validar que el efectivo sea mayor a 0
                double efe = Double.parseDouble(txt_efectivo.getText());
                double top = Double.parseDouble(txt_total_pagar.getText());

                if (efe < top) {

                    JOptionPane.showMessageDialog(null, "El dinero en efectivo no es suficiente");

                } else {

                    double cambio = (efe - top);

                    double cambi = (double) Math.round(cambio * 100) / 100;
                    String camb = String.valueOf(cambi);
                    txt_cambio.setText(camb);

                }

            } else {
                JOptionPane.showMessageDialog(null, "No se admiten caracteres no numericos");

            }

        } else {

            JOptionPane.showMessageDialog(null, "Ingrese dinero en efectivo para calcular el cambio");

        }


    }//GEN-LAST:event_jButton_calcular_cambioActionPerformed

    int idArrayList = 0;

    private void jTable_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_productosMouseClicked

        int fila_point = jTable_productos.rowAtPoint(evt.getPoint());
        int columna_point = 0;

        if (fila_point > -1) {

            idArrayList = (int) modeloDatosProducto.getValueAt(fila_point, columna_point);
        }

        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el producto?", "Eliminar producto", JOptionPane.YES_NO_OPTION);
        //Opciones de confir dialog - (si = 0; no = 1; cancel = 2; close = -1)

        switch (opcion) {
            case JOptionPane.YES_OPTION:
                listaProductos.remove(idArrayList - 1);
                this.CalcularTotalPagar();
                this.listaTablaProducto();
                break;

            case JOptionPane.NO_OPTION:
                // No hacer nada
                break;

            default:
                // Cancelar o cerrar
                break;
        }
    }//GEN-LAST:event_jTable_productosMouseClicked

    private void jButton_RegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RegistrarVentaActionPerformed

        if (!jComboBox_cliente.getSelectedItem().equals("Seleccione cliente:")) {
        if (!listaProductos.isEmpty()) {

            this.obtenerIdCliente();

            CabeceraVenta cabeceraVenta = new CabeceraVenta();
            cabeceraVenta.setIdCliente(idCliente);
            cabeceraVenta.setFechaVenta(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            cabeceraVenta.setValorPagar(Double.parseDouble(txt_total_pagar.getText()));
            cabeceraVenta.setEstado(1);

            VentaFacade ventaFacade = new VentaFacade();
            ventaFacade.procesarVenta(cabeceraVenta, listaProductos);

            JOptionPane.showMessageDialog(null, "Venta registrada correctamente.");

            // Reset de UI
            txt_subtotal.setText("0.0");
            txt_igv.setText("0.0");
            txt_descuento.setText("0.0");
            txt_total_pagar.setText("0.0");
            txt_efectivo.setText("0.0");
            txt_cambio.setText("0.0");
            listaProductos.clear();
            listaTablaProducto();
            CargarComboClientes();

        } else {
            JOptionPane.showMessageDialog(null, "Debe agregar productos a la venta.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.");
    }
    }//GEN-LAST:event_jButton_RegistrarVentaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_RegistrarVenta;
    private javax.swing.JButton jButton_añadir_producto;
    private javax.swing.JButton jButton_busca_cliente;
    private javax.swing.JButton jButton_calcular_cambio;
    private javax.swing.JComboBox<String> jComboBox_cliente;
    private javax.swing.JComboBox<String> jComboBox_producto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable_productos;
    private javax.swing.JTextField txt_cambio;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_cliente_buscar;
    private javax.swing.JTextField txt_descuento;
    public static javax.swing.JTextField txt_efectivo;
    private javax.swing.JTextField txt_igv;
    private javax.swing.JTextField txt_subtotal;
    public static javax.swing.JTextField txt_total_pagar;
    // End of variables declaration//GEN-END:variables

    //Metodo para cargar clientes en el JcomboBox
    private void CargarComboClientes() {

        Connection cn = Conexion.getConnection();
        String sql = "select*from tb_cliente";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            jComboBox_cliente.removeAllItems();
            jComboBox_cliente.addItem("Seleccione cliente:");

            while (rs.next()) {
                jComboBox_cliente.addItem(rs.getString("Nombre") + " " + rs.getString("Apellido"));

            }

            cn.close();
        } catch (SQLException e) {

            System.out.println("Error al cargar clientes : " + e);

        }

    }

    //Metodo para cargar los productos en JComboBox
    private void CargarComboProductos() {

        Connection cn = Conexion.getConnection();
        String sql = "select*from tb_producto";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            jComboBox_producto.removeAllItems();
            jComboBox_producto.addItem("Seleccione producto:");

            while (rs.next()) {
                jComboBox_producto.addItem(rs.getString("nombre"));

            }

            cn.close();
        } catch (SQLException e) {

            System.out.println("Error al cargar productos : " + e);

        }

    }

    private boolean validar(String valor) {

        try {

            int num = Integer.parseInt(valor);
            return true;

        } catch (NumberFormatException e) {

            return false;

        }

    }

    //Metodo para validar que el usuario no ingrese caracteres no numericos 
    private boolean validarDouble(String valor) {

        try {

            double num = Double.parseDouble(valor);
            return true;

        } catch (NumberFormatException e) {
            return false;

        }
    }

    //Metodo para mostrar los datos del producto
    private void DatosDelProducto() {

        try {

            String sql = "select * from tb_producto where nombre ='" + this.jComboBox_producto.getSelectedItem() + "'";
            Connection cn = Conexion.getConnection();
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                idProducto = rs.getInt("idProducto");
                nombre = rs.getString("nombre");
                cantidadProductoBBDD = rs.getInt("cantidad");
                precioUnitario = rs.getDouble("precio");
                porcentajeIgv = rs.getInt("porcentajeIva");
                this.CalcularIgv(precioUnitario, porcentajeIgv); //Calcula  y retorna el IGV

            }

        } catch (SQLException e) {

            System.out.println("Error al obtener datos del producto, " + e);

        }
    }

    //Metodo para calcular IGV
    private double CalcularIgv(double precio, int porcentajeIgv) {

        int p_igv = porcentajeIgv;

        switch (porcentajeIgv) {

            case 0:
                igv = 0.0;
                break;

            case 18:

                igv = (precio * cantidad) * 0.18;
                break;

            default:
                break;
        }

        return igv;

    }

    //Metodo para calcular el total a pagar de todos los productos agregados
    private void CalcularTotalPagar() {

        subtotalGeneral = 0;
        descuentoGeneral = 0;
        igvGeneral = 0;
        totalPagarGeneral = 0;

        for (DetalleVenta elemento : listaProductos) {

            subtotalGeneral += elemento.getSubtotal();
            descuentoGeneral += elemento.getDescuento();
            igvGeneral += elemento.getIva();
            totalPagarGeneral += elemento.getTotalPagar();

        }

        //Redondear decimales
        subtotalGeneral = (double) Math.round(subtotalGeneral * 100) / 100;
        descuentoGeneral = (double) Math.round(descuentoGeneral * 100) / 100;
        igvGeneral = (double) Math.round(igvGeneral * 100) / 100;
        totalPagarGeneral = (double) Math.round(totalPagarGeneral * 100) / 100;

        //Enviar datos a la vista
        txt_subtotal.setText(String.valueOf(subtotalGeneral));
        txt_descuento.setText(String.valueOf(descuentoGeneral));
        txt_igv.setText(String.valueOf(igvGeneral));
        txt_total_pagar.setText(String.valueOf(totalPagarGeneral));
    }

    //Metodo para obtener id del cliente
    public void obtenerIdCliente() {
        String clienteSeleccionado = jComboBox_cliente.getSelectedItem().toString();
        String sql = "SELECT idCliente FROM tb_cliente WHERE CONCAT(nombre, ' ', apellido) = ?";

        try (Connection cn = Conexion.getConnection(); PreparedStatement ps = (PreparedStatement) cn.prepareStatement(sql)) {

            ps.setString(1, clienteSeleccionado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idCliente = rs.getInt("idCliente");
                System.out.println("ID Cliente obtenido correctamente: " + idCliente);
            } else {
                idCliente = 0;
                System.out.println("Cliente no encontrado en la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el id del cliente: " + e.getMessage());
        }
    }

    private JButton createIconButton(String text, String iconPath, Color color) {
        // Cargar icono
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        // Crear botón
        JButton button = new JButton(text, resizedIcon);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setIconTextGap(10);
        button.setPreferredSize(new Dimension(160, 40));

        // Hover (opcional, efecto moderno)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

}
