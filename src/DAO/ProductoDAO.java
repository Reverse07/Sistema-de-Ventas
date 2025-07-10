package DAO;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Categoria;
import modelo.Producto;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

   // GUARDAR PRODUCTO
    public boolean guardar(Producto objeto) {
        boolean respuesta = false;
        String sql = "INSERT INTO tb_producto VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setInt(1, 0); // Autoincrement
            consulta.setString(2, objeto.getNombre());
            consulta.setInt(3, objeto.getCantidad());
            consulta.setDouble(4, objeto.getPrecio());
            consulta.setString(5, objeto.getDescripcion());
            consulta.setInt(6, objeto.getPorcentajeIva());
            consulta.setInt(7, objeto.getIdCategoria());
            consulta.setInt(8, objeto.getEstado());

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar producto: " + e.getMessage());
        }

        return respuesta;
    }

    // VERIFICAR EXISTENCIA DEL PRODUCTO
    public boolean existeProducto(String nombre) {
        boolean respuesta = false;
        String sql = "SELECT nombre FROM tb_producto WHERE nombre = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, nombre.trim());

            try (ResultSet rs = consulta.executeQuery()) {
                respuesta = rs.next();
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al verificar producto: " + e.getMessage());
        }

        return respuesta;
    }

    // ACTUALIZAR PRODUCTO
    public boolean actualizar(Producto objeto, int idProducto) {
        boolean respuesta = false;
        String sql = "UPDATE tb_producto SET nombre=?, cantidad=?, precio=?, descripcion=?, porcentajeIva=?, idCategoria=?, estado=? WHERE idProducto=?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, objeto.getNombre());
            consulta.setInt(2, objeto.getCantidad());
            consulta.setDouble(3, objeto.getPrecio());
            consulta.setString(4, objeto.getDescripcion());
            consulta.setInt(5, objeto.getPorcentajeIva());
            consulta.setInt(6, objeto.getIdCategoria());
            consulta.setInt(7, objeto.getEstado());
            consulta.setInt(8, idProducto);

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar producto: " + e.getMessage());
        }

        return respuesta;
    }

    // ELIMINAR PRODUCTO
    public boolean eliminar(int idProducto) {
        boolean respuesta = false;
        String sql = "DELETE FROM tb_producto WHERE idProducto = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setInt(1, idProducto);

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar producto: " + e.getMessage());
        }

        return respuesta;
    }

    // ACTUALIZAR STOCK
    public boolean actualizarStock(Producto producto, int idProducto) {
        boolean respuesta = false;
        String sql = "UPDATE tb_producto SET cantidad = ? WHERE idProducto = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setInt(1, producto.getCantidad());
            consulta.setInt(2, idProducto);

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar stock: " + e.getMessage());
        }

        return respuesta;
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        Producto producto = null;
    String sql = "SELECT * FROM tb_producto WHERE idProducto = ?";

    try (
        Connection cn = Conexion.getConnection();
        PreparedStatement consulta = cn.prepareStatement(sql)
    ) {
        consulta.setInt(1, idProducto);
        try (ResultSet rs = consulta.executeQuery()) {
            if (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPorcentajeIva(rs.getInt("porcentajeIva"));
                producto.setIdCategoria(rs.getInt("idCategoria"));
                producto.setEstado(rs.getInt("estado"));
            }
        }
    } catch (SQLException e) {
        System.out.println("❌ Error al buscar producto por ID: " + e.getMessage());
    }

    return producto;
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
    String sql = "SELECT * FROM tb_producto";

    try (
        Connection cn = Conexion.getConnection();
        PreparedStatement consulta = cn.prepareStatement(sql);
        ResultSet rs = consulta.executeQuery()
    ) {
        while (rs.next()) {
            Producto producto = new Producto();
            producto.setIdProducto(rs.getInt("idProducto"));
            producto.setNombre(rs.getString("nombre"));
            producto.setCantidad(rs.getInt("cantidad"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPorcentajeIva(rs.getInt("porcentajeIva"));
            producto.setIdCategoria(rs.getInt("idCategoria"));
            producto.setEstado(rs.getInt("estado"));
            lista.add(producto);
        }
    } catch (SQLException e) {
        System.out.println("❌ Error al listar productos: " + e.getMessage());
    }

    return lista;
    }
    
    @Override
public boolean reducirStock(int idProducto, int cantidadReducir) {
    boolean respuesta = false;
    String sql = "UPDATE tb_producto SET cantidad = cantidad - ? WHERE idProducto = ? AND cantidad >= ?";

    try (
        Connection cn = Conexion.getConnection();
        PreparedStatement consulta = cn.prepareStatement(sql)
    ) {
        consulta.setInt(1, cantidadReducir);
        consulta.setInt(2, idProducto);
        consulta.setInt(3, cantidadReducir); // Garantiza no bajar de 0

        int filasAfectadas = consulta.executeUpdate();
        respuesta = filasAfectadas > 0;

    } catch (SQLException e) {
        System.out.println("❌ Error al reducir stock: " + e.getMessage());
    }

    return respuesta;
}
}