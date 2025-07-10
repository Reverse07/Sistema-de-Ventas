package vista;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import conexion.Conexion;
import DAO.CategoriaDAO;
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
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.PreparedStatement;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import modelo.Categoria;

public class InterGestionarCategoria extends javax.swing.JInternalFrame {

    private int idCategoria;

    public InterGestionarCategoria() {
         FlatLightLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 10);

        initComponents2();

        this.setSize(new Dimension(600, 400));
        this.setTitle("📁 Gestionar Categoría");

        cargarTablaCategorias();

        // Fondo moderno
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
        jLabel_Wallpaper.setIcon(icono);
        this.repaint();

        // ActionListeners botones
        jButton_Actualizar.addActionListener(this::jButton_ActualizarActionPerformed);
        jButton_Eliminar.addActionListener(this::jButton_EliminarActionPerformed);
    }

    private void initComponents2() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1 = new JPanel(new BorderLayout(10, 10));
        jPanel1.setBackground(new Color(245, 245, 245));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tabla
        jTable_Categorias = new JTable();
        jTable_Categorias.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Descripción"}
        ));
        jTable_Categorias.setRowHeight(28);
        jTable_Categorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1 = new JScrollPane(jTable_Categorias);

        // Panel inferior para entrada
        jPanel2 = new JPanel(new BorderLayout(5, 5));
        jPanel2.setBackground(Color.WHITE);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txt_descripcion = new JTextField();
        txt_descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jPanel2.add(lblDescripcion, BorderLayout.NORTH);
        jPanel2.add(txt_descripcion, BorderLayout.CENTER);

        // Panel botones con FlowLayout a la derecha
        jPanel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        jPanel4.setBackground(Color.WHITE);

        jButton_Actualizar = createIconButton("Actualizar", "src/img/actualizarIcono.png", new Color(33, 150, 243));
        jButton_Eliminar = createIconButton("Eliminar", "src/img/borrarIcono.png", new Color(244, 67, 54));

        jPanel4.add(jButton_Actualizar);
        jPanel4.add(jButton_Eliminar);

        // Agregar componentes a panel principal
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.add(jPanel4, BorderLayout.NORTH);

        jLabel_Wallpaper = new JLabel();
        jLabel_Wallpaper.setLayout(new BorderLayout());
        jLabel_Wallpaper.add(jPanel1, BorderLayout.CENTER);

        setContentPane(jLabel_Wallpaper);
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
        button.setPreferredSize(new Dimension(140, 40));
        return button;
    }
    
 

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Categorias = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton_Actualizar = new javax.swing.JButton();
        jButton_Eliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_descripcion = new javax.swing.JTextField();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Administrar Categorias");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_Categorias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_Categorias);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 330, 230));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 350, 250));

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

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 130, 80));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Descripcion : ");

        txt_descripcion.setBackground(new java.awt.Color(255, 255, 255));
        txt_descripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_descripcion.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_descripcion)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 95, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 210, 80));

        jLabel_Wallpaper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo3.jpg"))); // NOI18N
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 590, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed
        
        if(!txt_descripcion.getText().isEmpty()){
            Categoria categoria = new Categoria();
            CategoriaDAO controlCategoria = new CategoriaDAO();
            
            categoria.setDescripcion(txt_descripcion.getText().trim());
            if(controlCategoria.actualizar(categoria, idCategoria))
            JOptionPane.showMessageDialog(null,"Categoria actualizada");
            
            txt_descripcion.setText("");
            this.cargarTablaCategorias();
            
                    } else {
            
            JOptionPane.showMessageDialog(null,"Error al actualizar Categoria");

            }   
    }//GEN-LAST:event_jButton_ActualizarActionPerformed

    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarActionPerformed
        
        if(!txt_descripcion.getText().isEmpty()){
            Categoria categoria = new Categoria();
            CategoriaDAO controlCategoria = new CategoriaDAO();
            
            categoria.setDescripcion(txt_descripcion.getText().trim());
            if(!controlCategoria.eliminar(idCategoria)){
                JOptionPane.showMessageDialog(null,"Categoria eliminada");
            txt_descripcion.setText("");
            this.cargarTablaCategorias();
            
                
            } else {
                
                 JOptionPane.showMessageDialog(null,"Error al eliminar una categoria");
            }
            
            
                    } else {
            
            JOptionPane.showMessageDialog(null,"Seleccione una Categoria");

            }   
    }//GEN-LAST:event_jButton_EliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable_Categorias;
    private javax.swing.JTextField txt_descripcion;
    // End of variables declaration//GEN-END:variables

    //Metodo para mostrar todas las categorias
   private void cargarTablaCategorias() {

    Connection cn = Conexion.getConnection();
    DefaultTableModel model = new DefaultTableModel();
    String sql = "SELECT * FROM tb_Categoria";
    Statement st;

    try {
        st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        jTable_Categorias.setModel(model);
        jScrollPane1.setViewportView(jTable_Categorias);

        model.addColumn("idCategoria");
        model.addColumn("descripcion");
        model.addColumn("estado");

        // Llenar la tabla con los datos
        while (rs.next()) {
            Object fila[] = new Object[3];
            for (int i = 0; i < 3; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            model.addRow(fila);
        }

        cn.close();
    } catch (SQLException e) {
        System.out.println("Error al llenar la tabla de categorias: " + e);
    }

    // Captura el evento de clic en la tabla
    jTable_Categorias.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int fila_point = jTable_Categorias.rowAtPoint(e.getPoint());
            int columna_point = 0; // Cambié la columna de ID (0) a descripcion (1)

            if (fila_point > -1) {
                idCategoria = (int) model.getValueAt(fila_point, columna_point); // idCategoria en columna 0
                System.out.println("ID Categoria Seleccionada: " + idCategoria); // Mensaje de depuración
                // Obtener la descripción de la fila seleccionada (columna 1)
                EnviarDatosCategoriaSeleccionada(idCategoria);
            }
        }
    });
}

private void EnviarDatosCategoriaSeleccionada(int idCategoria) {
    try {
        System.out.println("Consultando categoría con id: " + idCategoria); // Mensaje de depuración
        Connection cn = Conexion.getConnection();
        PreparedStatement pst = cn.prepareStatement("SELECT * FROM tb_Categoria WHERE idCategoria = ?");
        pst.setInt(1, idCategoria); // Usamos setInt para el parámetro de idCategoria
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            txt_descripcion.setText(rs.getString("descripcion"));
            System.out.println("Descripción: " + rs.getString("descripcion")); // Mensaje de depuración
        } else {
            System.out.println("No se encontró la categoría con id: " + idCategoria); // Mensaje de depuración
        }

        cn.close();
    } catch (SQLException e) {
        System.out.println("Error al seleccionar la categoria: " + e);
    }
}

    
}

