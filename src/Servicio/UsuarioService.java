
package Servicio;

import DAO.IUsuarioDAO;
import modelo.Usuario;


public class UsuarioService {
    
     private final IUsuarioDAO usuarioDAO;

    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuarioDAO.existeUsuario(usuario.getUsuario())) {
            return false; // Usuario ya existe
        }
        return usuarioDAO.guardar(usuario);
    }

    public boolean existeUsuario(String username) {
        return usuarioDAO.existeUsuario(username);
    }

    public boolean actualizarUsuario(Usuario usuario, int idUsuario) {
        return usuarioDAO.actualizar(usuario, idUsuario);
    }

    public boolean eliminarUsuario(int idUsuario) {
        return usuarioDAO.eliminar(idUsuario);
    }

    public boolean login(Usuario usuario) {
        return usuarioDAO.loginUser(usuario);
    }
    
}