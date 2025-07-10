package DAO;

import java.sql.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.Statement;
import modelo.Usuario;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import modelo.Cliente;

public class UsuarioDAO implements IUsuarioDAO {

  public boolean loginUser(Usuario usuario) {
        String sql = "SELECT * FROM tb_usuarios WHERE usuario = ? AND password = ?";
        boolean respuesta = false;

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                respuesta = rs.next();
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al iniciar sesión: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión");
        }

        return respuesta;
    }

    public boolean guardar(Usuario usuario) {
        String sql = "INSERT INTO tb_usuarios (nombre, apellido, usuario, password, telefono, estado) VALUES (?, ?, ?, ?, ?, ?)";
        boolean respuesta = false;

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getUsuario());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getTelefono());
            ps.setInt(6, usuario.getEstado());

            respuesta = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar usuario: " + e.getMessage());
        }

        return respuesta;
    }

    public boolean existeUsuario(String username) {
        String sql = "SELECT 1 FROM tb_usuarios WHERE usuario = ?";
        boolean existe = false;

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, username.trim());

            try (ResultSet rs = ps.executeQuery()) {
                existe = rs.next();
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al verificar existencia de usuario: " + e.getMessage());
        }

        return existe;
    }

    public boolean actualizar(Usuario usuario, int idUsuario) {
        String sql = "UPDATE tb_usuarios SET nombre=?, apellido=?, usuario=?, password=?, telefono=?, estado=? WHERE idUsuario=?";
        boolean respuesta = false;

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getUsuario());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getTelefono());
            ps.setInt(6, usuario.getEstado());
            ps.setInt(7, idUsuario);

            respuesta = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar usuario: " + e.getMessage());
        }

        return respuesta;
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM tb_usuarios WHERE idUsuario = ?";
        boolean respuesta = false;

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, idUsuario);
            respuesta = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar usuario: " + e.getMessage());
        }

        return respuesta;
    }
}
 
