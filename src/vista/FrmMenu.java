package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import DAO.Reportes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class FrmMenu extends javax.swing.JFrame {

    public static JDesktopPane jDesktopPane_menu;

    public FrmMenu() {
        initComponents();
        configurarVentana();

    }

    private void configurarVentana() {
        this.setSize(new Dimension(1230, 780));
        this.setSize(new Dimension(1230, 780));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setTitle("SISTEMA DE VENTAS");

        // Establecer layout BorderLayout para que el JDesktopPane ocupe todo el espacio
        this.setLayout(new BorderLayout());

        jDesktopPane_menu = new JDesktopPaneConFondo();

        this.add(jDesktopPane_menu, BorderLayout.CENTER);
    }

    @Override
    public Image getIconImage() {
     Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/ventas.png"));
        return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem_nuevoUsuario = new javax.swing.JMenuItem();
        jMenuItem_GestionarUsuario = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem_NuevoProducto = new javax.swing.JMenuItem();
        jMenuItem_GestionarProductos = new javax.swing.JMenuItem();
        jMenuItem_ActualizarStock = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem_NuevoCliente = new javax.swing.JMenuItem();
        jMenuItem_GestionarClientes = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem_NuevaCategoria = new javax.swing.JMenuItem();
        jMenuItem_GestionarCategoria = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem_NuevaVenta = new javax.swing.JMenuItem();
        jMenuItem_GestionarVentas = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem_ReportesClientes = new javax.swing.JMenuItem();
        jMenuItem_ReportesCategorias = new javax.swing.JMenuItem();
        jMenuItem_ReporteProductos = new javax.swing.JMenuItem();
        jMenuItem_ReporteVentas = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem_VerHistorial = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem_CerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N
        jMenu1.setText("Usuario");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_nuevoUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_nuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo-cliente.png"))); // NOI18N
        jMenuItem_nuevoUsuario.setText("Nuevo Usuario");
        jMenuItem_nuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_nuevoUsuarioActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_nuevoUsuario);

        jMenuItem_GestionarUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_GestionarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/configuraciones.png"))); // NOI18N
        jMenuItem_GestionarUsuario.setText("Gestionar Usuario");
        jMenuItem_GestionarUsuario.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_GestionarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarUsuarioActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_GestionarUsuario);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        jMenu2.setText("Producto");
        jMenu2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(200, 50));

        jMenuItem_NuevoProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_NuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo-producto.png"))); // NOI18N
        jMenuItem_NuevoProducto.setText("Nuevo Producto");
        jMenuItem_NuevoProducto.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_NuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_NuevoProductoActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_NuevoProducto);

        jMenuItem_GestionarProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_GestionarProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        jMenuItem_GestionarProductos.setText("Gestionar Productos");
        jMenuItem_GestionarProductos.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_GestionarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarProductosActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_GestionarProductos);

        jMenuItem_ActualizarStock.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_ActualizarStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo.png"))); // NOI18N
        jMenuItem_ActualizarStock.setText("Actualizar Stock");
        jMenuItem_ActualizarStock.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_ActualizarStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ActualizarStockActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_ActualizarStock);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cliente.png"))); // NOI18N
        jMenu3.setText("Cliente");
        jMenu3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_NuevoCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_NuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo-cliente.png"))); // NOI18N
        jMenuItem_NuevoCliente.setText("Nuevo Cliente");
        jMenuItem_NuevoCliente.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_NuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_NuevoClienteActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem_NuevoCliente);

        jMenuItem_GestionarClientes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_GestionarClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cliente.png"))); // NOI18N
        jMenuItem_GestionarClientes.setText("Gestionar Clientes");
        jMenuItem_GestionarClientes.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_GestionarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarClientesActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem_GestionarClientes);

        jMenuBar1.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/categorias.png"))); // NOI18N
        jMenu4.setText("Categoria");
        jMenu4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(180, 30));

        jMenuItem_NuevaCategoria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_NuevaCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo.png"))); // NOI18N
        jMenuItem_NuevaCategoria.setText("Nueva Categoria");
        jMenuItem_NuevaCategoria.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_NuevaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_NuevaCategoriaActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem_NuevaCategoria);

        jMenuItem_GestionarCategoria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_GestionarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/categorias.png"))); // NOI18N
        jMenuItem_GestionarCategoria.setText("Gestionar Categoria");
        jMenuItem_GestionarCategoria.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_GestionarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarCategoriaActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem_GestionarCategoria);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/carrito.png"))); // NOI18N
        jMenu5.setText("Facturar");
        jMenu5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_NuevaVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_NuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/anadir.png"))); // NOI18N
        jMenuItem_NuevaVenta.setText("Nueva Venta");
        jMenuItem_NuevaVenta.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_NuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_NuevaVentaActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem_NuevaVenta);

        jMenuItem_GestionarVentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_GestionarVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/configuraciones.png"))); // NOI18N
        jMenuItem_GestionarVentas.setText("Gestionar Ventas");
        jMenuItem_GestionarVentas.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_GestionarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarVentasActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem_GestionarVentas);

        jMenuBar1.add(jMenu5);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reportes.png"))); // NOI18N
        jMenu6.setText("Reportes");
        jMenu6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu6.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_ReportesClientes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_ReportesClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reporte1.png"))); // NOI18N
        jMenuItem_ReportesClientes.setText("Reportes Clientes");
        jMenuItem_ReportesClientes.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_ReportesClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ReportesClientesActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_ReportesClientes);

        jMenuItem_ReportesCategorias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_ReportesCategorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reporte1.png"))); // NOI18N
        jMenuItem_ReportesCategorias.setText("Reportes Categorias");
        jMenuItem_ReportesCategorias.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_ReportesCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ReportesCategoriasActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_ReportesCategorias);

        jMenuItem_ReporteProductos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_ReporteProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reporte1.png"))); // NOI18N
        jMenuItem_ReporteProductos.setText("Reportes Producto");
        jMenuItem_ReporteProductos.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_ReporteProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ReporteProductosActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_ReporteProductos);

        jMenuItem_ReporteVentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_ReporteVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reporte1.png"))); // NOI18N
        jMenuItem_ReporteVentas.setText("Reportes Ventas");
        jMenuItem_ReporteVentas.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_ReporteVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ReporteVentasActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_ReporteVentas);

        jMenuBar1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/historial1.png"))); // NOI18N
        jMenu7.setText("Historial");
        jMenu7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu7.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_VerHistorial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_VerHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/historial1.png"))); // NOI18N
        jMenuItem_VerHistorial.setText("Ver Historial");
        jMenuItem_VerHistorial.setPreferredSize(new java.awt.Dimension(150, 30));
        jMenuItem_VerHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_VerHistorialActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem_VerHistorial);

        jMenuBar1.add(jMenu7);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar-sesion.png"))); // NOI18N
        jMenu8.setText("Cerrar Sesion");
        jMenu8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu8.setPreferredSize(new java.awt.Dimension(200, 50));

        jMenuItem_CerrarSesion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMenuItem_CerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar-sesion.png"))); // NOI18N
        jMenuItem_CerrarSesion.setText("Cerrar Sesion");
        jMenuItem_CerrarSesion.setPreferredSize(new java.awt.Dimension(150, 30));
        jMenuItem_CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_CerrarSesionActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem_CerrarSesion);

        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_nuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_nuevoUsuarioActionPerformed
        InterUsuario interusuario = new InterUsuario();
        jDesktopPane_menu.add(interusuario);
        interusuario.setVisible(true);

    }//GEN-LAST:event_jMenuItem_nuevoUsuarioActionPerformed

    private void jMenuItem_CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_CerrarSesionActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem_CerrarSesionActionPerformed

    private void jMenuItem_GestionarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarCategoriaActionPerformed

        InterGestionarCategoria interGestionarCategoria = new InterGestionarCategoria();
        jDesktopPane_menu.add(interGestionarCategoria);
        interGestionarCategoria.setVisible(true);


    }//GEN-LAST:event_jMenuItem_GestionarCategoriaActionPerformed

    private void jMenuItem_GestionarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarProductosActionPerformed

        InterGestionarProducto interGestionarProducto = new InterGestionarProducto();
        jDesktopPane_menu.add(interGestionarProducto);
        interGestionarProducto.setVisible(true);


    }//GEN-LAST:event_jMenuItem_GestionarProductosActionPerformed

    private void jMenuItem_GestionarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarClientesActionPerformed

        InterGestionarCliente interGestionarCliente = new InterGestionarCliente();
        jDesktopPane_menu.add(interGestionarCliente);
        interGestionarCliente.setVisible(true);


    }//GEN-LAST:event_jMenuItem_GestionarClientesActionPerformed

    private void jMenuItem_NuevaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_NuevaCategoriaActionPerformed
        InterCategoria interCategoria = new InterCategoria();
        jDesktopPane_menu.add(interCategoria);
        interCategoria.setVisible(true);


    }//GEN-LAST:event_jMenuItem_NuevaCategoriaActionPerformed

    private void jMenuItem_NuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_NuevoProductoActionPerformed

        InterProducto interProducto = new InterProducto();
        jDesktopPane_menu.add(interProducto);
        interProducto.setVisible(true);


    }//GEN-LAST:event_jMenuItem_NuevoProductoActionPerformed

    private void jMenuItem_NuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_NuevoClienteActionPerformed

        InterCliente interCliente = new InterCliente();
        jDesktopPane_menu.add(interCliente);
        interCliente.setVisible(true);

    }//GEN-LAST:event_jMenuItem_NuevoClienteActionPerformed

    private void jMenuItem_ActualizarStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ActualizarStockActionPerformed

        InterActualizarStock interactualizarstock = new InterActualizarStock();
        jDesktopPane_menu.add(interactualizarstock);
        interactualizarstock.setVisible(true);

    }//GEN-LAST:event_jMenuItem_ActualizarStockActionPerformed

    private void jMenuItem_GestionarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarUsuarioActionPerformed

        InterGestionarUsuario intergestionarusuario = new InterGestionarUsuario();
        jDesktopPane_menu.add(intergestionarusuario);
        intergestionarusuario.setVisible(true);


    }//GEN-LAST:event_jMenuItem_GestionarUsuarioActionPerformed

    private void jMenuItem_NuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_NuevaVentaActionPerformed
        InterFacturacion interfacturacion = new InterFacturacion();
        jDesktopPane_menu.add(interfacturacion);
        interfacturacion.setVisible(true);
    }//GEN-LAST:event_jMenuItem_NuevaVentaActionPerformed

    private void jMenuItem_GestionarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarVentasActionPerformed
        InterGestionarVentas interventas = new InterGestionarVentas();
        jDesktopPane_menu.add(interventas);
        interventas.setVisible(true);
    }//GEN-LAST:event_jMenuItem_GestionarVentasActionPerformed

    private void jMenuItem_ReportesClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ReportesClientesActionPerformed
        Reportes reportes = new Reportes();
        reportes.reporteClientes();
    }//GEN-LAST:event_jMenuItem_ReportesClientesActionPerformed

    private void jMenuItem_ReportesCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ReportesCategoriasActionPerformed
        Reportes reportes = new Reportes();
        reportes.reporteCategorias();
    }//GEN-LAST:event_jMenuItem_ReportesCategoriasActionPerformed

    private void jMenuItem_ReporteProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ReporteProductosActionPerformed
        Reportes reportes = new Reportes();
        reportes.reporteProductos();
    }//GEN-LAST:event_jMenuItem_ReporteProductosActionPerformed

    private void jMenuItem_ReporteVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ReporteVentasActionPerformed
        Reportes reportes = new Reportes();
        reportes.reporteVentas();
    }//GEN-LAST:event_jMenuItem_ReporteVentasActionPerformed

    private void jMenuItem_VerHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_VerHistorialActionPerformed
        InterGraficas intergraficas = new InterGraficas();
        jDesktopPane_menu.add(intergraficas);
        intergraficas.setVisible(true);
    }//GEN-LAST:event_jMenuItem_VerHistorialActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_ActualizarStock;
    private javax.swing.JMenuItem jMenuItem_CerrarSesion;
    private javax.swing.JMenuItem jMenuItem_GestionarCategoria;
    private javax.swing.JMenuItem jMenuItem_GestionarClientes;
    private javax.swing.JMenuItem jMenuItem_GestionarProductos;
    private javax.swing.JMenuItem jMenuItem_GestionarUsuario;
    private javax.swing.JMenuItem jMenuItem_GestionarVentas;
    private javax.swing.JMenuItem jMenuItem_NuevaCategoria;
    private javax.swing.JMenuItem jMenuItem_NuevaVenta;
    private javax.swing.JMenuItem jMenuItem_NuevoCliente;
    private javax.swing.JMenuItem jMenuItem_NuevoProducto;
    private javax.swing.JMenuItem jMenuItem_ReporteProductos;
    private javax.swing.JMenuItem jMenuItem_ReporteVentas;
    private javax.swing.JMenuItem jMenuItem_ReportesCategorias;
    private javax.swing.JMenuItem jMenuItem_ReportesClientes;
    private javax.swing.JMenuItem jMenuItem_VerHistorial;
    private javax.swing.JMenuItem jMenuItem_nuevoUsuario;
    // End of variables declaration//GEN-END:variables
}
