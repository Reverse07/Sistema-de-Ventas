package DAO;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.Producto;

public class ClienteDAO implements IClienteDAO {

  // GUARDAR CLIENTE
    public boolean guardar(Cliente objeto) {
        boolean respuesta = false;
        String sql = "INSERT INTO tb_cliente (nombre, apellido, cedula, direccion, telefono, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getCedula());
            consulta.setString(4, objeto.getDireccion());
            consulta.setString(5, objeto.getTelefono());
            consulta.setInt(6, objeto.getEstado());

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar cliente: " + e.getMessage());
        }

        return respuesta;
    }

    // VERIFICAR SI EXISTE EL CLIENTE
    public boolean existeCliente(String cedula) {
        boolean respuesta = false;
        String sql = "SELECT 1 FROM tb_cliente WHERE cedula = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, cedula.trim());

            try (ResultSet rs = consulta.executeQuery()) {
                respuesta = rs.next();
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar cliente: " + e.getMessage());
        }

        return respuesta;
    }

    // ACTUALIZAR CLIENTE
    public boolean actualizar(Cliente objeto, int idCliente) {
        boolean respuesta = false;
        String sql = "UPDATE tb_cliente SET nombre=?, apellido=?, cedula=?, direccion=?, telefono=?, estado=? WHERE idCliente=?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getCedula());
            consulta.setString(4, objeto.getDireccion());
            consulta.setString(5, objeto.getTelefono());
            consulta.setInt(6, objeto.getEstado());
            consulta.setInt(7, idCliente);

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar cliente: " + e.getMessage());
        }

        return respuesta;
    }

    // ELIMINAR CLIENTE
    public boolean eliminar(String cedula) {
        boolean respuesta = false;
        String sql = "DELETE FROM tb_cliente WHERE cedula = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, cedula != null ? cedula.trim() : "");

            respuesta = consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar cliente: " + e.getMessage());
        }

        return respuesta;
    }

    @Override
    public Cliente buscarPorCedula(String cedula) {
      Cliente cliente = null;
        String sql = "SELECT * FROM tb_cliente WHERE cedula = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement consulta = cn.prepareStatement(sql)
        ) {
            consulta.setString(1, cedula.trim());
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setCedula(rs.getString("cedula"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setEstado(rs.getInt("estado"));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar cliente: " + e.getMessage());
        }

        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() {
    List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_cliente";

        try (
            Connection cn = Conexion.getConnection();
            Statement consulta = cn.createStatement();
            ResultSet rs = consulta.executeQuery(sql)
        ) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEstado(rs.getInt("estado"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }
    }
