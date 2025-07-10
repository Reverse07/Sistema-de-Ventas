
package DAO;

import modelo.CabeceraVenta;
import modelo.DetalleVenta;


public interface IVentaDAO {
    
     int guardarCabecera(CabeceraVenta cabecera);
    boolean guardarDetalle(DetalleVenta detalle);
    boolean actualizarCabecera(CabeceraVenta cabecera, int idCabeceraVenta);
    
}
