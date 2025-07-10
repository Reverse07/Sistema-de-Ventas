
package Servicio;

import DAO.IProductoDAO;
import DAO.IVentaDAO;
import DAO.VentaPDF;
import Factory.DAOFactory;
import java.util.List;
import modelo.CabeceraVenta;
import modelo.DetalleVenta;


public class VentaFacade {
    
     private final IVentaDAO ventaDAO;
    private final IProductoDAO productoDAO;
    private final VentaPDF ventaPDF;

    public VentaFacade() {
        this.ventaDAO = DAOFactory.crearVentaDAO();      // Correcto, retorna IVentaDAO
        this.productoDAO = DAOFactory.crearProductoDAO(); // Correcto, retorna IProductoDAO
        this.ventaPDF = new VentaPDF();
    }

    /**
     * Procesa una venta completa: registra cabecera, detalles, actualiza stock y genera factura PDF.
     *
     * @param cabeceraVenta datos de cabecera de la venta
     * @param detalles lista de detalles de la venta
     */
    public void procesarVenta(CabeceraVenta cabeceraVenta, List<DetalleVenta> detalles) {
        // 1️⃣ Registrar cabecera de venta
        int idVentaGenerada = ventaDAO.guardarCabecera(cabeceraVenta);

        // 2️⃣ Registrar detalles de venta y actualizar stock
        for (DetalleVenta detalle : detalles) {
            detalle.setIdCabeceraVenta(idVentaGenerada);
            ventaDAO.guardarDetalle(detalle);

            productoDAO.reducirStock(detalle.getIdProducto(), detalle.getCantidad());
        }

        // 3️⃣ Generar factura PDF
        ventaPDF.DatosCliente(cabeceraVenta.getIdCliente());
        ventaPDF.generarFacturaPDF();
    }
}
