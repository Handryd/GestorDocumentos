package service;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    // ==========================
    // REGISTRAR CLIENTE
    // ==========================
    public boolean registrarCliente(Cliente cliente) {

        if (!validarCliente(cliente)) {
            return false;
        }

        // Verificar que el código no exista
        Cliente existente =
                clienteDAO.buscarPorCodigoExpediente(
                        cliente.getCodigoExpediente());

        if (existente != null) {
            System.out.println(
                    "Ya existe un cliente con el código: "
                    + cliente.getCodigoExpediente());
            return false;
        }

        return clienteDAO.registrarCliente(cliente);
    }

    // ==========================
    // ACTUALIZAR CLIENTE
    // ==========================
    public boolean actualizarCliente(Cliente cliente) {

        if (!validarCliente(cliente)) {
            return false;
        }

        Cliente existente =
                clienteDAO.buscarPorCodigoExpediente(
                        cliente.getCodigoExpediente());

        // Si existe otro cliente con el mismo código
        if (existente != null &&
            existente.getId() != cliente.getId()) {

            System.out.println(
                    "Ya existe otro cliente con el código: "
                    + cliente.getCodigoExpediente());

            return false;
        }

        return clienteDAO.actualizarCliente(cliente);
    }

    // ==========================
    // ELIMINAR CLIENTE
    // ==========================
    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminarCliente(id);
    }

    // ==========================
    // LISTAR CLIENTES
    // ==========================
    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Cliente buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    // ==========================
    // BUSCAR POR CÓDIGO
    // ==========================
    public Cliente buscarPorCodigoExpediente(
            String codigoExpediente) {

        return clienteDAO.buscarPorCodigoExpediente(
                codigoExpediente);
    }

    // ==========================
    // VALIDACIONES
    // ==========================
    private boolean validarCliente(Cliente cliente) {

        if (cliente == null) {
            return false;
        }

        // Código expediente obligatorio
        if (cliente.getCodigoExpediente() == null ||
            cliente.getCodigoExpediente().trim().isEmpty()) {

            System.out.println(
                    "El código de expediente es obligatorio.");
            return false;
        }

        // Nombre obligatorio
        if (cliente.getNombre() == null ||
            cliente.getNombre().trim().isEmpty()) {

            System.out.println(
                    "El nombre es obligatorio.");
            return false;
        }

        // Correo opcional, pero si existe debe tener formato básico
        if (cliente.getCorreo() != null &&
            !cliente.getCorreo().trim().isEmpty()) {

            if (!cliente.getCorreo().contains("@")) {

                System.out.println(
                        "Correo electrónico inválido.");
                return false;
            }
        }

        return true;
    }

    // ==========================
    // VERIFICAR SI EXISTE CÓDIGO
    // ==========================
    public boolean existeCodigoExpediente(
            String codigoExpediente) {

        return clienteDAO.buscarPorCodigoExpediente(
                codigoExpediente) != null;
    }
}