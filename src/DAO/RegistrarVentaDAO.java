package DAO;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.CabeceraVenta;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.DetalleVenta;

public class RegistrarVentaDAO implements IVentaDAO {
public static int idCabeceraRegistrada;

    @Override
    public int guardarCabecera(CabeceraVenta cabecera) {
        int idGenerado = 0;
        String sql = "INSERT INTO tb_cabecera_venta (idCliente, valorPagar, fechaVenta, estado) VALUES (?, ?, ?, ?)";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, cabecera.getIdCliente());
            ps.setDouble(2, cabecera.getValorPagar());
            ps.setString(3, cabecera.getFechaVenta());
            ps.setInt(4, cabecera.getEstado());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        idCabeceraRegistrada = idGenerado;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar cabecera: " + e.getMessage());
        }

        return idGenerado;
    }

    @Override
    public boolean guardarDetalle(DetalleVenta detalle) {
        boolean guardado = false;
        String sql = "INSERT INTO tb_detalleventa (idCabeceraVenta, idProducto, cantidad, precioUnitario, subTotal, iva, totalPagar, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, detalle.getIdCabeceraVenta());
            ps.setInt(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setDouble(6, detalle.getIva());
            ps.setDouble(7, detalle.getTotalPagar());
            ps.setInt(8, detalle.getEstado());

            guardado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar detalle de venta: " + e.getMessage());
        }

        return guardado;
    }

    @Override
    public boolean actualizarCabecera(CabeceraVenta cabecera, int idCabeceraVenta) {
        boolean respuesta = false;
        String sql = "UPDATE tb_cabecera_venta SET idCliente = ?, valorPagar = ?, fechaVenta = ?, estado = ? WHERE idCabeceraVenta = ?";

        try (
            Connection cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, cabecera.getIdCliente());
            ps.setDouble(2, cabecera.getValorPagar());
            ps.setString(3, cabecera.getFechaVenta());
            ps.setInt(4, cabecera.getEstado());
            ps.setInt(5, idCabeceraVenta);

            respuesta = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar cabecera de venta: " + e.getMessage());
        }

        return respuesta;
    }
}