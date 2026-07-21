package controller;

import model.Cliente;
import service.ClienteService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private ClienteService clienteService;

    @Override
    public void init() {
        clienteService = new ClienteService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            case "editar":
                editarCliente(request, response);
                break;

            case "eliminar":
                eliminarCliente(request, response);
                break;

            default:
                listarClientes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("actualizar".equals(accion)) {

            actualizarCliente(request, response);

        } else {

            registrarCliente(request, response);
        }
    }

    // ==========================
    // LISTAR
    // ==========================
    private void listarClientes(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        List<Cliente> clientes =
                clienteService.listarClientes();

        request.setAttribute("clientes", clientes);

        request.getRequestDispatcher(
                "/clientes.jsp")
                .forward(request, response);
    }

    // ==========================
    // REGISTRAR
    // ==========================
    private void registrarCliente(HttpServletRequest request,
                                  HttpServletResponse response)
            throws IOException {

        Cliente cliente = new Cliente();

        cliente.setCodigoExpediente(
                request.getParameter("codigoExpediente"));

        cliente.setExpedientePadre(
                request.getParameter("expedientePadre"));

        cliente.setNombre(
                request.getParameter("nombre"));

        cliente.setCorreo(
                request.getParameter("correo"));

        cliente.setTelefono(
                request.getParameter("telefono"));

        clienteService.registrarCliente(cliente);

        response.sendRedirect("clientes");
    }

    // ==========================
    // EDITAR
    // ==========================
    private void editarCliente(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id"));

        Cliente cliente =
                clienteService.buscarPorId(id);

        List<Cliente> clientes =
                clienteService.listarClientes();

        request.setAttribute(
                "clienteEditar",
                cliente);

        request.setAttribute(
                "clientes",
                clientes);

        request.getRequestDispatcher(
                "/clientes.jsp")
                .forward(request, response);
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    private void actualizarCliente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Cliente cliente = new Cliente();

        cliente.setId(
                Integer.parseInt(
                        request.getParameter("id")));

        cliente.setCodigoExpediente(
                request.getParameter("codigoExpediente"));

        cliente.setExpedientePadre(
                request.getParameter("expedientePadre"));

        cliente.setNombre(
                request.getParameter("nombre"));

        cliente.setCorreo(
                request.getParameter("correo"));

        cliente.setTelefono(
                request.getParameter("telefono"));

        clienteService.actualizarCliente(cliente);

        response.sendRedirect("clientes");
    }

    // ==========================
    // ELIMINAR
    // ==========================
    private void eliminarCliente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id"));

        clienteService.eliminarCliente(id);

        response.sendRedirect("clientes");
    }
}