package DAO;

import java.sql.Statement;
import conexion.Conexion;
import modelo.Categoria;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements ICategoriaDAO {

      // GUARDAR
    public boolean guardar(Categoria objeto) {
        boolean respuesta = false;
        String sql = "INSERT INTO tb_categoria (descripcion, estado) VALUES (?, ?)";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, objeto.getDescripcion());
            consulta.setInt(2, objeto.getEstado());

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar categoría: " + e.getMessage());
        }

        return respuesta;
    }

    // VERIFICAR SI EXISTE
    public boolean existeCategoria(String categoria) {
        boolean respuesta = false;
        String sql = "SELECT descripcion FROM tb_categoria WHERE descripcion = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, categoria.trim());

            try (ResultSet rs = consulta.executeQuery()) {
                respuesta = rs.next();
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al verificar categoría: " + e.getMessage());
        }

        return respuesta;
    }

    // ACTUALIZAR
    public boolean actualizar(Categoria objeto, int idCategoria) {
        boolean respuesta = false;
        String sql = "UPDATE tb_categoria SET descripcion = ?, estado = ? WHERE idCategoria = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, objeto.getDescripcion());
            consulta.setInt(2, objeto.getEstado());
            consulta.setInt(3, idCategoria);

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar categoría: " + e.getMessage());
        }

        return respuesta;
    }

    // ELIMINAR
    public boolean eliminar(int idCategoria) {
        boolean respuesta = false;
        String sql = "DELETE FROM tb_categoria WHERE idCategoria = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setInt(1, idCategoria);
            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar categoría: " + e.getMessage());
        }

        return respuesta;
    }

    @Override
    public Categoria buscarPorId(int idCategoria) {
        Categoria categoria = null;
        String sql = "SELECT * FROM tb_categoria WHERE idCategoria = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setInt(1, idCategoria);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("idCategoria"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setEstado(rs.getInt("estado"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar categoría: " + e.getMessage());
        }

        return categoria;
    }

    @Override
    public List<Categoria> listarTodos() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_categoria";

        try (
            Connection cn = Conexion.getConnection();
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setIdCategoria(rs.getInt("idCategoria"));
                cat.setDescripcion(rs.getString("descripcion"));
                cat.setEstado(rs.getInt("estado"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar categorías: " + e.getMessage());
        }

        return lista;
    }
        
        } 
