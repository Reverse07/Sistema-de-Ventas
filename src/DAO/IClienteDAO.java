
package DAO;

import java.util.List;
import modelo.Cliente;


public interface IClienteDAO {
    
    boolean guardar(Cliente cliente);
    boolean existeCliente(String cedula);
    boolean actualizar(Cliente cliente, int idCliente);
    boolean eliminar(String cedula);

    // Opcionales
    Cliente buscarPorCedula(String cedula);
    List<Cliente> listarTodos();
}
    
    

