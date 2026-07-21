package controller;

import model.RequisitoDocumento;
import service.RequisitoDocumentoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/requisitos")
public class RequisitoDocumentoServlet extends HttpServlet {

    private RequisitoDocumentoService service;

    @Override
    public void init() throws ServletException {
        service = new RequisitoDocumentoService();
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
            listar(request, response);
            return;
        }

        switch (accion) {

            case "editar":
                editar(request, response);
                break;

            case "eliminar":
                eliminar(request, response);
                break;

            case "estado":
                cambiarEstado(request, response);
                break;

            default:
                listar(request, response);
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

        switch (accion) {

            case "insertar":
                insertar(request, response);
                break;

            case "actualizar":
                actualizar(request, response);
                break;

            default:
                response.sendRedirect("requisitos");
                break;
        }
    }

    // ==========================
    // LISTAR
    // ==========================
    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<RequisitoDocumento> lista =
                service.obtenerTodos();

        request.setAttribute(
                "requisitos",
                lista
        );

        request.getRequestDispatcher(
                "/requisitos.jsp"
        ).forward(request, response);
    }

    // ==========================
    // INSERTAR
    // ==========================
    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        RequisitoDocumento r =
                new RequisitoDocumento();

        r.setNombre(
                request.getParameter("nombre")
        );

        r.setDescripcion(
                request.getParameter("descripcion")
        );

        r.setActivo(true);

        service.crearRequisito(r);

        response.sendRedirect("requisitos");
    }

    // ==========================
    // EDITAR (CARGAR DATOS)
    // ==========================
    private void editar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        RequisitoDocumento r =
                service.buscarPorId(id);

        request.setAttribute(
                "requisitoEditar",
                r
        );

        request.setAttribute(
                "requisitos",
                service.obtenerTodos()
        );

        request.getRequestDispatcher(
                "/requisitos.jsp"
        ).forward(request, response);
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        RequisitoDocumento r =
                new RequisitoDocumento();

        r.setId(
                Integer.parseInt(
                        request.getParameter("id")
                )
        );

        r.setNombre(
                request.getParameter("nombre")
        );

        r.setDescripcion(
                request.getParameter("descripcion")
        );

        r.setActivo(
                Boolean.parseBoolean(
                        request.getParameter("activo")
                )
        );

        service.actualizar(r);

        response.sendRedirect("requisitos");
    }

    // ==========================
    // ELIMINAR
    // ==========================
    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        service.eliminar(id);

        response.sendRedirect("requisitos");
    }

    // ==========================
    // CAMBIAR ESTADO
    // ==========================
    private void cambiarEstado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        boolean estado =
                Boolean.parseBoolean(
                        request.getParameter("activo")
                );

        service.cambiarEstado(id, estado);

        response.sendRedirect("requisitos");
    }
}