
package Servicio;

import DAO.IProductoDAO;
import java.util.List;
import modelo.Producto;


public class ProductoService {
    
  
    private final IProductoDAO productoDAO;

    public ProductoService(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    // Registrar un producto
    public boolean registrarProducto(Producto producto) {
        if (productoDAO.existeProducto(producto.getNombre())) {
            throw new IllegalArgumentException("El producto ya existe.");
        }
        return productoDAO.guardar(producto);
    }

    // Actualizar un producto
    public boolean actualizarProducto(Producto producto, int idProducto) {
        return productoDAO.actualizar(producto, idProducto);
    }

    // Eliminar un producto
    public boolean eliminarProducto(int idProducto) {
        return productoDAO.eliminar(idProducto);
    }

    // Actualizar solo el stock de un producto
    public boolean actualizarStock(int idProducto, int nuevaCantidad) {
        Producto producto = new Producto();
        producto.setCantidad(nuevaCantidad);
        return productoDAO.actualizarStock(producto, idProducto);
    }

    // Buscar producto por su ID
    public Producto buscarProductoPorId(int idProducto) {
        return productoDAO.buscarPorId(idProducto);
    }

    // Listar todos los productos
    public List<Producto> listarTodosLosProductos() {
        return productoDAO.listarTodos();
    }

    // Verificar existencia de producto por nombre (útil para validación en vista)
    public boolean existeProducto(String nombreProducto) {
        return productoDAO.existeProducto(nombreProducto);
    }
}