
package Servicio;

import DAO.ICategoriaDAO;
import java.util.List;
import java.util.stream.Collectors;
import modelo.Categoria;


public class CategoriaService {
    
  private final ICategoriaDAO categoriaDAO;

    public CategoriaService(ICategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    // Registrar nueva categoría si no existe
    public boolean registrarCategoria(Categoria categoria) {
        if (categoriaDAO.existeCategoria(categoria.getDescripcion())) {
            throw new IllegalArgumentException("La categoría ya existe.");
        }
        return categoriaDAO.guardar(categoria);
    }

    // Actualizar categoría existente
    public boolean actualizarCategoria(Categoria categoria, int idCategoria) {
        return categoriaDAO.actualizar(categoria, idCategoria);
    }

    // Eliminar categoría por ID
    public boolean eliminarCategoria(int idCategoria) {
        return categoriaDAO.eliminar(idCategoria);
    }

    // Buscar una categoría por ID
    public Categoria buscarPorId(int idCategoria) {
        return categoriaDAO.buscarPorId(idCategoria);
    }

    // Listar todas las categorías
    public List<Categoria> listarTodas() {
        return categoriaDAO.listarTodos();
    }

    // Listar solo nombres de categorías activas
    public List<String> listarNombresCategoriasActivas() {
        return categoriaDAO.listarTodos().stream()
                .filter(c -> c.getEstado() == 1)
                .map(Categoria::getDescripcion)
                .collect(Collectors.toList());
    }

    // Obtener ID a partir de la descripción
    public int obtenerIdCategoria(String descripcion) {
        return categoriaDAO.listarTodos().stream()
                .filter(c -> c.getDescripcion().equalsIgnoreCase(descripcion))
                .map(Categoria::getIdCategoria)
                .findFirst()
                .orElse(-1); // Retorna -1 si no encuentra
    }

    // Validar existencia
    public boolean existeCategoria(String descripcion) {
        return categoriaDAO.existeCategoria(descripcion);
    }
}