
package Servicio;

import DAO.IClienteDAO;
import java.util.List;
import modelo.Cliente;

public class ClienteService {
    
     private final IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    // Registrar nuevo cliente (validando existencia por cédula)
    public boolean registrarCliente(Cliente cliente) {
        if (clienteDAO.existeCliente(cliente.getCedula())) {
            throw new IllegalArgumentException("El cliente ya está registrado.");
        }
        return clienteDAO.guardar(cliente);
    }

    // Actualizar datos de cliente
    public boolean actualizarCliente(Cliente cliente, int idCliente) {
        return clienteDAO.actualizar(cliente, idCliente);
    }

    // Eliminar cliente por cédula
    public boolean eliminarCliente(String cedula) {
        return clienteDAO.eliminar(cedula);
    }

    // Buscar cliente por cédula
    public Cliente buscarPorCedula(String cedula) {
        return clienteDAO.buscarPorCedula(cedula);
    }

    // Listar todos los clientes
    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }
}

