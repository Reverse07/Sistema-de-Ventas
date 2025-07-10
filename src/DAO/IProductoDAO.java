
package DAO;

import java.util.List;
import modelo.Producto;


public interface IProductoDAO {
    
   boolean guardar(Producto objeto);
    boolean existeProducto(String nombre);
    boolean actualizar(Producto objeto, int idProducto);
    boolean eliminar(int idProducto);
    boolean actualizarStock(Producto producto, int idProducto);
    Producto buscarPorId(int idProducto);
    List<Producto> listarTodos();
    boolean reducirStock(int idProducto, int cantidadReducir); 
    
}
