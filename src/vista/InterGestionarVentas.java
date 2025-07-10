package vista;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import conexion.Conexion;
import DAO.RegistrarVentaDAO;
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
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import modelo.CabeceraVenta;

public class InterGestionarVentas extends javax.swing.JInternalFrame {

    private int idCliente = 0;
    private int idVenta;

    public InterGestionarVentas() {
        // Estilo FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 10);

        initComponents2();

        this.setSize(new Dimension(900, 500));
        this.setTitle("🧾 Gestionar Ventas");

        cargarTablaVentas();
        CargarComboClientes();

        // Fondo elegante
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH));
        jLabel_wallpaper.setIcon(icono);
        this.repaint();
    }

    private void initComponents2() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1 = new JPanel(new BorderLayout(10, 10));
        jPanel1.setBackground(new Color(245, 245, 245));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tabla
        jTable_ventas = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Cliente", "Fecha", "Total", "Estado"}
        ));
        jTable_ventas.setRowHeight(28);
        jTable_ventas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1 = new JScrollPane(jTable_ventas);

        // Panel de formulario
        jPanel2 = new JPanel(new GridLayout(2, 4, 10, 10));
        jPanel2.setBackground(Color.WHITE);

        jComboBox_cliente1 = new JComboBox<>(new String[]{"Seleccione cliente"});
        jComboBox_estado = new JComboBox<>(new String[]{"Seleccione estado", "Pagada", "Pendiente"});
        txt_fecha = new JTextField();
        txt_total_pagar2 = new JTextField();

        jPanel2.add(createLabeledField("Cliente:", jComboBox_cliente1));
        jPanel2.add(createLabeledField("Fecha:", txt_fecha));
        jPanel2.add(createLabeledField("Total a Pagar:", txt_total_pagar2));
        jPanel2.add(createLabeledField("Estado:", jComboBox_estado));

        // Panel de botones (jPanel5)
        jPanel5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jPanel5.setBackground(Color.WHITE);

        jButton_Actualizar = createIconButton("Actualizar", "src/img/actualizarIcono.png", new Color(33, 150, 243));
        jButton_Actualizar.addActionListener(this::jButton_ActualizarActionPerformed);
        jPanel5.add(jButton_Actualizar);

        // Añadir componentes a jPanel1
        jPanel1.add(jPanel5, BorderLayout.NORTH);
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);

        // Fondo
        jLabel_wallpaper = new JLabel();
        jLabel_wallpaper.setLayout(new BorderLayout());
        jLabel_wallpaper.add(jPanel1, BorderLayout.CENTER);

        setContentPane(jLabel_wallpaper);
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
        jTable_ventas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton_Actualizar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_total_pagar2 = new javax.swing.JTextField();
        jComboBox_estado = new javax.swing.JComboBox<>();
        jComboBox_cliente1 = new javax.swing.JComboBox<>();
        jLabel_wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Administrar Ventas ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_ventas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_ventas);

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
        jLabel2.setText(" Estado: ");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        txt_fecha.setEditable(false);
        txt_fecha.setBackground(new java.awt.Color(255, 255, 255));
        txt_fecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_fecha.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 170, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Fecha: ");
        jLabel3.setToolTipText("");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Cliente: ");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText(" Total Pagar: ");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txt_total_pagar2.setEditable(false);
        txt_total_pagar2.setBackground(new java.awt.Color(255, 255, 255));
        txt_total_pagar2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_total_pagar2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_total_pagar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 170, -1));

        jComboBox_estado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        jPanel5.add(jComboBox_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 210, 20));

        jComboBox_cliente1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox_cliente1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione cliente:", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(jComboBox_cliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 210, 20));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 870, 100));
        getContentPane().add(jLabel_wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed

        CabeceraVenta cabeceraventa = new CabeceraVenta();
        RegistrarVentaDAO registrarventa = new RegistrarVentaDAO();

        String cliente = jComboBox_cliente1.getSelectedItem().toString().trim();
        String estado = jComboBox_estado.getSelectedItem().toString().trim();
        String fecha = txt_fecha.getText().trim();
        String totalTexto = txt_total_pagar2.getText().trim();

        // Validar cliente seleccionado
        if (cliente.equalsIgnoreCase("Seleccione cliente")) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente válido.");
            return;
        }

        // Validar estado seleccionado
        if (estado.equalsIgnoreCase("Seleccione estado")) {
            JOptionPane.showMessageDialog(null, "Seleccione un estado válido.");
            return;
        }

        // Validar fecha no vacía
        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese una fecha válida.");
            return;
        }

        // Validar total a pagar
        double total = 0.0;
        try {
            total = Double.parseDouble(totalTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Total inválido. Ingrese un número.");
            return;
        }

        // Obtener idCliente
        try {
            Connection cn = Conexion.getConnection();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT idCliente FROM tb_cliente WHERE CONCAT(nombre, ' ', apellido) = ?"
            );
            pst.setString(1, cliente);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idCliente = rs.getInt("idCliente");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado en la base de datos.");
                cn.close();
                return;
            }

            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID del cliente.");
            System.out.println("Error en cargar el id cliente: " + e);
            return;
        }

        // Configurar objeto venta
        cabeceraventa.setIdCliente(idCliente);
        cabeceraventa.setEstado(estado.equalsIgnoreCase("Pagada") ? 1 : 0);
        cabeceraventa.setFechaVenta(fecha);
        cabeceraventa.setValorPagar(total);

        // Actualizar
        if (registrarventa.actualizarCabecera(cabeceraventa, idVenta)) {
            JOptionPane.showMessageDialog(null, "Registro Actualizado");
            this.CargarComboClientes();
            this.cargarTablaVentas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
        }
    }//GEN-LAST:event_jButton_ActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JComboBox<String> jComboBox_cliente1;
    private javax.swing.JComboBox<String> jComboBox_estado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable_ventas;
    private javax.swing.JTextField txt_fecha;
    private javax.swing.JTextField txt_total_pagar2;
    // End of variables declaration//GEN-END:variables

    //Metodo para limpiar 
    private void limpiar() {

        this.txt_fecha.setText("");
        this.txt_total_pagar2.setText("");
        this.jComboBox_cliente1.setSelectedItem("Seleccione cliente:");
        this.jComboBox_estado.setSelectedItem("Pagado");
        idCliente = 0;

    }

    //Metodo para mostrar todas los clientes
    private void cargarTablaVentas() {
        Connection cn = Conexion.getConnection();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "SELECT cv.idCabeceraVenta AS id, "
                + "CONCAT(c.nombre, ' ', c.apellido) AS cliente, "
                + "cv.valorPagar AS total, "
                + "cv.fechaVenta, "
                + "cv.estado "
                + "FROM tb_cabecera_venta AS cv "
                + "INNER JOIN tb_cliente AS c ON cv.idCliente = c.idCliente";

        Statement st;

        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jTable_ventas.setModel(model);
            jScrollPane1.setViewportView(jTable_ventas);

            // Agregar las columnas al modelo
            model.addColumn("Nº");           // 0
            model.addColumn("Cliente");      // 1
            model.addColumn("Total Pagar");  // 2
            model.addColumn("Fecha Venta");  // 3
            model.addColumn("Estado");       // 4

            // Llenar la tabla
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("cliente");
                fila[2] = rs.getDouble("total");
                fila[3] = rs.getString("fechaVenta");

                // Convertir estado de 1/0 a texto
                int estado = rs.getInt("estado");
                fila[4] = (estado == 1) ? "Pagado" : "Pendiente";

                model.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de ventas: " + e);
        }

        // Añadir evento click a la tabla
        jTable_ventas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_ventas.rowAtPoint(e.getPoint());
                int columna_point = 0;
                if (fila_point > -1) {
                    idVenta = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosVentasSeleccionado(idVenta);
                }
            }
        });
    }

    //Metodos que envia datos seleccionados
    private void EnviarDatosVentasSeleccionado(int idVenta) {
        try {
            Connection cn = Conexion.getConnection();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT cv.fechaVenta, cv.valorPagar, cv.estado, c.idCliente, CONCAT(c.nombre,' ', c.apellido) AS cliente "
                    + "FROM tb_cabecera_Venta AS cv "
                    + "INNER JOIN tb_cliente AS c ON cv.idCliente = c.idCliente "
                    + "WHERE cv.idCabeceraVenta = ?"
            );
            pst.setInt(1, idVenta);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_fecha.setText(rs.getString("fechaVenta"));
                txt_total_pagar2.setText(rs.getString("valorPagar"));

                // Estado: 1 = Activo, 0 = Inactivo
                String estado = rs.getString("estado");
                if (estado.equals("1")) {
                    jComboBox_estado.setSelectedItem("Pagado");
                } else {
                    jComboBox_estado.setSelectedItem("Pendiente");
                }

                // Cliente
                String nombreCliente = rs.getString("cliente");
                jComboBox_cliente1.setSelectedItem(nombreCliente);

                // Guardar idCliente
                idCliente = rs.getInt("idCliente");
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar la venta: " + e);
        }
    }

    //Metodo para cargar clientes en el JcomboBox
    private void CargarComboClientes() {

        Connection cn = Conexion.getConnection();
        String sql = "select*from tb_cliente";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            jComboBox_cliente1.removeAllItems();
            jComboBox_cliente1.addItem("Seleccione cliente:");

            while (rs.next()) {
                jComboBox_cliente1.addItem(rs.getString("Nombre") + " " + rs.getString("Apellido"));

            }

            cn.close();
        } catch (SQLException e) {

            System.out.println("Error al cargar clientes : " + e);

        }

    }

}
