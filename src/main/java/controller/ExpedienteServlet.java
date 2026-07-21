package controller;

import model.Cliente;
import model.Expediente;
import service.ClienteService;
import service.ExpedienteService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/expedientes")
public class ExpedienteServlet extends HttpServlet {

    private ExpedienteService expedienteService;
    private ClienteService clienteService;

    @Override
    public void init() throws ServletException {

        expedienteService = new ExpedienteService();
        clienteService = new ClienteService();
    }

    // ==========================
    // GET
    // ==========================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            listarExpedientes(request, response);
            return;
        }

        switch (accion) {

            case "editar":
                editarExpediente(request, response);
                break;

            case "eliminar":
                eliminarExpediente(request, response);
                break;

            default:
                listarExpedientes(request, response);
                break;
        }
    }

    // ==========================
    // POST
    // ==========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {

            response.sendRedirect("expedientes");
            return;
        }

        switch (accion) {

            case "registrar":
                registrarExpediente(request, response);
                break;

            case "actualizar":
                actualizarExpediente(request, response);
                break;

            default:
                response.sendRedirect("expedientes");
                break;
        }
    }

    // ==========================
    // LISTAR
    // ==========================
    private void listarExpedientes(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Expediente> expedientes =
                expedienteService.listarExpedientes();

        List<Cliente> clientes =
                clienteService.listarClientes();

        request.setAttribute(
                "expedientes",
                expedientes
        );

        request.setAttribute(
                "clientes",
                clientes
        );

        request.getRequestDispatcher(
                "/expedientes.jsp"
        ).forward(request, response);
    }

    // ==========================
    // REGISTRAR
    // ==========================
    private void registrarExpediente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Expediente expediente =
                    new Expediente();

            expediente.setClienteId(
                    Integer.parseInt(
                            request.getParameter(
                                    "clienteId"
                            )
                    )
            );

            expediente.setCodigoExpediente(
                    request.getParameter(
                            "codigoExpediente"
                    )
            );

            expediente.setNombreDocumento(
                    request.getParameter(
                            "nombreDocumento"
                    )
            );

            expediente.setEstado(
                    request.getParameter(
                            "estado"
                    )
            );

            expediente.setObservaciones(
                    request.getParameter(
                            "observaciones"
                    )
            );

            expediente.setFechaRegistro(
                    LocalDate.now()
            );

            expedienteService
                    .registrarExpediente(
                            expediente
                    );

        } catch (Exception e) {

            e.printStackTrace();
        }

        response.sendRedirect("expedientes");
    }

    // ==========================
    // EDITAR
    // ==========================
    private void editarExpediente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );

            Expediente expediente =
                    expedienteService
                            .buscarPorId(id);

            List<Cliente> clientes =
                    clienteService
                            .listarClientes();

            List<Expediente> expedientes =
                    expedienteService
                            .listarExpedientes();

            request.setAttribute(
                    "expedienteEditar",
                    expediente
            );

            request.setAttribute(
                    "clientes",
                    clientes
            );

            request.setAttribute(
                    "expedientes",
                    expedientes
            );

            request.getRequestDispatcher(
                    "/expedientes.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "expedientes"
            );
        }
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    private void actualizarExpediente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Expediente expediente =
                    new Expediente();

            expediente.setId(
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    )
            );

            expediente.setClienteId(
                    Integer.parseInt(
                            request.getParameter(
                                    "clienteId"
                            )
                    )
            );

            expediente.setCodigoExpediente(
                    request.getParameter(
                            "codigoExpediente"
                    )
            );

            expediente.setNombreDocumento(
                    request.getParameter(
                            "nombreDocumento"
                    )
            );

            expediente.setEstado(
                    request.getParameter(
                            "estado"
                    )
            );

            expediente.setObservaciones(
                    request.getParameter(
                            "observaciones"
                    )
            );

            expedienteService
                    .actualizarExpediente(
                            expediente
                    );

        } catch (Exception e) {

            e.printStackTrace();
        }

        response.sendRedirect("expedientes");
    }

    // ==========================
    // ELIMINAR
    // ==========================
    private void eliminarExpediente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );

            expedienteService
                    .eliminarExpediente(id);

        } catch (Exception e) {

            e.printStackTrace();
        }

        response.sendRedirect("expedientes");
    }
}