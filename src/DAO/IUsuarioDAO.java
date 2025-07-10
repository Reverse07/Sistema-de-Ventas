
package DAO;

import modelo.Usuario;


public interface IUsuarioDAO {
    boolean loginUser(Usuario usuario);
    boolean guardar(Usuario usuario);
    boolean existeUsuario(String username);
    boolean actualizar(Usuario usuario, int idUsuario);
    boolean eliminar(int idUsuario);
}
