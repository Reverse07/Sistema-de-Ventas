package vista;

import DAO.ProductoDAO;
import com.formdev.flatlaf.FlatLightLaf;
import conexion.Conexion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import modelo.Producto;

public class InterActualizarStock extends javax.swing.JInternalFrame {

    //Variables
    int idProducto = 0;
    int cantidad = 0;

    public InterActualizarStock() {
          FlatLightLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 10);

        initComponents2();

        setTitle("🔄 Actualizar Stock");
        setSize(new Dimension(500, 320));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        this.CargarComboProductos();
        jComboBox_producto.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (!jComboBox_producto.getSelectedItem().equals("Seleccione producto")) {
                mostrarStock();
            } else {
                txt_cantidad_actual.setText("");
            }
        }
    }
});
        
     }  

    private void initComponents2() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.setBorder(new EmptyBorder(1, 10, 5, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        jComboBox_producto = new JComboBox<>(new String[]{"Seleccione producto"});
        txt_cantidad_actual = new JTextField();
        txt_cantidad_actual.setEditable(false);
        txt_cantidad_nueva = new JTextField();

        formPanel.add(createFieldBlockConIcono("Producto:", "src/img/iconoProducto(1).png", jComboBox_producto), gbc);
        gbc.gridy++;
        formPanel.add(createFieldBlockConIcono("Cantidad Actual:", "src/img/cantidadActual(1).png", txt_cantidad_actual), gbc);
        gbc.gridy++;
        formPanel.add(createFieldBlockConIcono("Cantidad Nueva:", "src/img/nuevo.png", txt_cantidad_nueva), gbc);
 
        jButton1= createIconButton("Actualizar", "src/img/actualizarIcono.png", new Color(33, 150, 243));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(jButton1);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        jLabel_Wallpaper = new JLabel();
        jLabel_Wallpaper.setLayout(new BorderLayout());
        jLabel_Wallpaper.add(mainPanel, BorderLayout.NORTH);

        setContentPane(jLabel_Wallpaper);
    }

    private JPanel createFieldBlockConIcono(String labelText, String iconPath, JComponent field) {
        JPanel container = new JPanel(new BorderLayout(5, 5));
        container.setOpaque(false);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        labelPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        textLabel.setForeground(new Color(33, 33, 33));

        labelPanel.add(iconLabel);
        labelPanel.add(textLabel);

        container.add(labelPanel, BorderLayout.NORTH);
        container.add(field, BorderLayout.CENTER);

        return container;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_cantidad_actual = new javax.swing.JTextField();
        txt_cantidad_nueva = new javax.swing.JTextField();
        jComboBox_producto = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Actualizar Stock de Productos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Producto:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 70, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Stock Actual:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 100, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Stock Nuevo:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        txt_cantidad_actual.setEditable(false);
        txt_cantidad_actual.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_cantidad_actual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cantidad_actualActionPerformed(evt);
            }
        });
        getContentPane().add(txt_cantidad_actual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 210, -1));

        txt_cantidad_nueva.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        getContentPane().add(txt_cantidad_nueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 210, -1));

        jComboBox_producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione producto:", "Item 2", "Item 3", "Item 4" }));
        jComboBox_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_productoActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 210, -1));

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 200, -1));

        jLabel_Wallpaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo3.jpg"))); // NOI18N
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cantidad_actualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cantidad_actualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cantidad_actualActionPerformed

    private void jComboBox_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_productoActionPerformed
        this.mostrarStock();
    }//GEN-LAST:event_jComboBox_productoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        //Validamos seleccion de producto
        if (!jComboBox_producto.getSelectedItem().equals("Seleccione producto:")) {
            //Validamos campos Vacios
            if (!txt_cantidad_nueva.getText().isEmpty()) {
                //Validamos que el usuario no ingrese otros caracteres no numericos

                boolean validacion = validar(txt_cantidad_nueva.getText().trim());

                if (validacion == true) {
                    //Validamos que la cantidad sea mayor a 0

                    if (Integer.parseInt(txt_cantidad_nueva.getText()) > 0) {

                        Producto producto = new Producto();
                        ProductoDAO controladorProducto = new ProductoDAO();

                        int stockActual = Integer.parseInt(txt_cantidad_actual.getText().trim());
                        int stockNuevo = Integer.parseInt(txt_cantidad_nueva.getText().trim());

                        stockNuevo = stockActual + stockNuevo;

                        producto.setCantidad(stockNuevo);

                        if (controladorProducto.actualizarStock(producto, idProducto)) {
                            JOptionPane.showMessageDialog(null, "Stock actualizado");
                            jComboBox_producto.setSelectedItem("Seleccione producto:");
                            txt_cantidad_actual.setText("");
                            txt_cantidad_nueva.setText("");
                            this.CargarComboProductos();
                        } else {

                            JOptionPane.showMessageDialog(null, "Error al actualizar");

                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "La cantidad no puede ser cero ni negativa ");
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "En la cantidad no se admiten caracteres no numericos");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingrese una nueva cantidad para sumar el stock del producto");

            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione producto");

        }


    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox_producto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JTextField txt_cantidad_actual;
    private javax.swing.JTextField txt_cantidad_nueva;
    // End of variables declaration//GEN-END:variables

    //Metodo para cargar los productos en el jComboBox
    private void CargarComboProductos() {

        Connection cn = Conexion.getConnection();
        String sql = "select * from tb_producto";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jComboBox_producto.removeAllItems();
            jComboBox_producto.addItem("Seleccione producto:");

            while (rs.next()) {

                jComboBox_producto.addItem(rs.getString("nombre"));

            }

        } catch (SQLException e) {

            System.out.println("Error al cargar los productos : " + e);

        }

    }

    //Metodo para seleccionar el stock del producto seleccionado
    private void mostrarStock() {

        try {

            Connection cn = Conexion.getConnection();
            String sql = "select * from tb_producto where nombre = '" + this.jComboBox_producto.getSelectedItem() + "'";
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {

                idProducto = rs.getInt("idProducto");
                cantidad = rs.getInt("cantidad");
                txt_cantidad_actual.setText(String.valueOf(cantidad));
            } else {
                txt_cantidad_actual.setText("");

            }

        } catch (SQLException e) {

            System.out.println("Error al mostrar el stock actual : " + e);

        }

    }

    //Metodo de validacion de caracteres no numericos
    private boolean validar(String valor) {

        int num;

        try {

            num = Integer.parseInt(valor);
            return true;

        } catch (NumberFormatException e) {
            return false;

        }

    }

}
