
package DAO;

import java.util.List;
import modelo.Categoria;


public interface ICategoriaDAO {
    
    boolean guardar(Categoria categoria);
    boolean existeCategoria(String nombre);
    boolean actualizar(Categoria categoria, int idCategoria);
    boolean eliminar(int idCategoria);

    // Opcionales para completar DAO:
    Categoria buscarPorId(int idCategoria);
    List<Categoria> listarTodos();
}
    

