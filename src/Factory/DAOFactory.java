
package Factory;

import DAO.CategoriaDAO;
import DAO.ClienteDAO;
import DAO.ICategoriaDAO;
import DAO.IClienteDAO;
import DAO.IProductoDAO;
import DAO.IUsuarioDAO;
import DAO.IVentaDAO;
import DAO.ProductoDAO;
import DAO.RegistrarVentaDAO;
import DAO.UsuarioDAO;


public class DAOFactory {
    
    
    public static IUsuarioDAO crearUsuarioDAO() {
        return new UsuarioDAO();
    }

    public static IProductoDAO crearProductoDAO() {
        return new ProductoDAO();
    }
    
    public static ICategoriaDAO crearCategoriaDAO() {
        return new CategoriaDAO();
    }
    
    public static IClienteDAO crearClienteDAO() {
        return new ClienteDAO();
    }
    
   public static IVentaDAO crearVentaDAO() {
    return new RegistrarVentaDAO();
}

}
