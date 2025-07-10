package vista;


import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import conexion.Conexion;
import DAO.ClienteDAO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Cliente;

public class InterGestionarCliente extends javax.swing.JInternalFrame {

    private int idCliente;

    public InterGestionarCliente() {
       try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            System.out.println("No se pudo cargar FlatLaf");
        }

        initComponents2();

        this.setSize(new Dimension(900, 500));
        this.setTitle("Gestionar Clientes");
        this.setClosable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);

        cargarTablaClientes();

        this.setVisible(true);
    }

    private void initComponents2() {
        // PANEL PRINCIPAL
        jPanel1 = new JPanel(new BorderLayout(10, 10));
        jPanel1.setBackground(new Color(245, 245, 245));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // TABLA CLIENTES
        jTable_clientes = new JTable();
        jTable_clientes.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Apellido", "Cédula", "Dirección", "Teléfono"}
        ));
        jTable_clientes.setRowHeight(28);
        jTable_clientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jScrollPane1 = new JScrollPane(jTable_clientes);

        // PANEL INPUTS (GridLayout 3 filas x 4 columnas)
        jPanel2 = new JPanel(new GridLayout(3, 4, 15, 10));
        jPanel2.setBackground(Color.WHITE);

        // Labels y campos (usar variables declaradas)
        jLabel1 = new JLabel("Nombre:");
        txt_nombre = new JTextField();

        jLabel2 = new JLabel("Apellido:");
        txt_apellido = new JTextField();

        jLabel3 = new JLabel("Cédula:");
        txt_cedula = new JTextField();

        jLabel4 = new JLabel("Dirección:");
        txt_direccion = new JTextField();

        jLabel5 = new JLabel("Teléfono:");
        txt_telefono = new JTextField();

        // Ajustar fuentes etiquetas para uniformidad
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        jLabel1.setFont(labelFont);
        jLabel2.setFont(labelFont);
        jLabel3.setFont(labelFont);
        jLabel4.setFont(labelFont);
        jLabel5.setFont(labelFont);

        // Agregamos en orden a jPanel2
        jPanel2.add(jLabel1);
        jPanel2.add(txt_nombre);

        jPanel2.add(jLabel2);
        jPanel2.add(txt_apellido);

        jPanel2.add(jLabel3);
        jPanel2.add(txt_cedula);

        jPanel2.add(jLabel4);
        jPanel2.add(txt_direccion);

        jPanel2.add(jLabel5);
        jPanel2.add(txt_telefono);

        // Para completar la grilla 3x4, agregamos dos espacios vacíos
        jPanel2.add(new JLabel());
        jPanel2.add(new JLabel());

        // PANEL BOTONES
        jPanel5 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        jPanel5.setBackground(Color.WHITE);

        jButton_Actualizar = createIconButton("Actualizar", "src/img/actualizarIcono.png", new Color(33, 150, 243));
        jButton_Eliminar = createIconButton("Eliminar", "src/img/borrarIcono.png", new Color(244, 67, 54));

        jPanel5.add(jButton_Actualizar);
        jPanel5.add(jButton_Eliminar);

        // Agregar componentes a jPanel1
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.add(jPanel5, BorderLayout.EAST);

        // Fondo con JLabel
        jLabel_wallpaper = new JLabel();
        jLabel_wallpaper.setLayout(new BorderLayout());

        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH));
        jLabel_wallpaper.setIcon(icono);

        jLabel_wallpaper.add(jPanel1, BorderLayout.CENTER);

        this.setContentPane(jLabel_wallpaper);
        
        // Agregar ActionListeners a los botones
        jButton_Actualizar.addActionListener(this::jButton_ActualizarActionPerformed);
        jButton_Eliminar.addActionListener(this::jButton_EliminarActionPerformed);
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
        jTable_clientes = new javax.swing.JTable();
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
        txt_cedula = new javax.swing.JTextField();
        txt_telefono = new javax.swing.JTextField();
        txt_direccion = new javax.swing.JTextField();
        txt_apellido = new javax.swing.JTextField();
        jLabel_wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Administrar Clientes ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_clientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_clientes);

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
        jLabel2.setText(" Telefono: ");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        txt_nombre.setBackground(new java.awt.Color(255, 255, 255));
        txt_nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_nombre.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 186, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Apellido: ");
        jLabel3.setToolTipText("");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Cedula: ");
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
        jLabel6.setText("Direccion : ");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));

        txt_cedula.setBackground(new java.awt.Color(255, 255, 255));
        txt_cedula.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_cedula.setForeground(new java.awt.Color(0, 0, 0));
        txt_cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cedulaActionPerformed(evt);
            }
        });
        jPanel5.add(txt_cedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 220, -1));

        txt_telefono.setBackground(new java.awt.Color(255, 255, 255));
        txt_telefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_telefono.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 210, -1));

        txt_direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_direccionActionPerformed(evt);
            }
        });
        jPanel5.add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 190, -1));
        jPanel5.add(txt_apellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 190, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 870, 100));
        getContentPane().add(jLabel_wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed

        
       if(txt_nombre.getText().isEmpty()  && txt_apellido.getText().isEmpty() 
    && txt_cedula.getText().isEmpty() && txt_direccion.getText().isEmpty() && txt_telefono.getText().isEmpty()) {

        
            
        JOptionPane.showMessageDialog(null,"Complete todos los campos");
        
        txt_nombre.setBackground(Color.red);
        txt_apellido.setBackground(Color.red);
        txt_cedula.setBackground(Color.red);
        txt_direccion.setBackground(Color.red);
        txt_telefono.setBackground(Color.red);
        
        
        } else { 
        Cliente cliente = new Cliente ();
        ClienteDAO controladorCliente = new ClienteDAO ();
        
        
        cliente.setNombre(txt_nombre.getText().trim());
        cliente.setApellido(txt_apellido.getText().trim());
        cliente.setCedula(txt_cedula.getText().trim());
        cliente.setDireccion(txt_direccion.getText().trim());
        cliente.setTelefono(txt_telefono.getText().trim());
        
        if(controladorCliente.actualizar(cliente, idCliente)) {
            JOptionPane.showMessageDialog(null,"Cliente correctamente actualizado");
            this.cargarTablaClientes();
            this.limpiar();

        } else {
          JOptionPane.showMessageDialog(null,"Error al actualizar"); 
            
        }
           } 
    }//GEN-LAST:event_jButton_ActualizarActionPerformed

    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarActionPerformed

        ClienteDAO controladorCliente = new ClienteDAO ();
        
        if(idCliente==0){
            
            JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            
        } else {
            
            String cedula = txt_cedula.getText().trim();
            
            if(controladorCliente.eliminar(cedula)){
                JOptionPane.showMessageDialog(null, "Cliente Eliminado");
                this.cargarTablaClientes();
                this.limpiar();
            } else {
                
                JOptionPane.showMessageDialog(null, "Error al eliminar Cliente");
                this.limpiar();
            }
            
            
            
        }
        
        
    }//GEN-LAST:event_jButton_EliminarActionPerformed

    private void txt_direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_direccionActionPerformed

    }//GEN-LAST:event_txt_direccionActionPerformed

    private void txt_cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cedulaActionPerformed
    
    }//GEN-LAST:event_txt_cedulaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable_clientes;
    private javax.swing.JTextField txt_apellido;
    private javax.swing.JTextField txt_cedula;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables

    //Metodo para limpiar 
    private void limpiar() {

        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_cedula.setText("");
        txt_telefono.setText("");
        txt_cedula.setText("");

    }

    //Metodo para mostrar todas los clientes
    private void cargarTablaClientes() {

        Connection cn = Conexion.getConnection();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "Select * from tb_cliente";
        Statement st;

        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jTable_clientes.setModel(model);
            jScrollPane1.setViewportView(jTable_clientes);

            model.addColumn("Nº");//ID
            model.addColumn("nombre");
            model.addColumn("apellido");
            model.addColumn("cedula");
            model.addColumn("direccion");
            model.addColumn("telefono");
            model.addColumn("estado");

            // Llenar la tabla con los datos
            while (rs.next()) {

                Object fila[] = new Object[7];
                for (int i = 0; i < 7; i++) {

                    fila[i] = rs.getObject(i + 1);

                }
                model.addRow(fila);
            }

            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de clientes: " + e);
        }

        // Captura el evento de clic en la tabla
        jTable_clientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_clientes.rowAtPoint(e.getPoint());
                int columna_point = 0; // Cambié la columna de ID (0) a descripcion (1)
//
                if (fila_point > -1) {
                    idCliente = (int) model.getValueAt(fila_point, columna_point); // idCategoria en columna 0

                    // Obtener la descripción de la fila seleccionada (columna 1)
                    EnviarDatosClienteSeleccionado(idCliente); //MetodoF
                }
            }
        });
    }
    
    //Metodos que envia datos seleccionados

    private void EnviarDatosClienteSeleccionado(int idCliente) {
        try {

            Connection cn = Conexion.getConnection();
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tb_cliente WHERE idCliente ='" + idCliente + "'");

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_nombre.setText(rs.getString("nombre"));
                txt_apellido.setText(rs.getString("apellido"));
                txt_cedula.setText(rs.getString("cedula"));
                txt_direccion.setText(rs.getString("direccion"));
                txt_telefono.setText(rs.getString("telefono"));
                
               
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al seleccionar cliente: " + e);
        }

    }

}
