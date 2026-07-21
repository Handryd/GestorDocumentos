package controller;

import model.Documento;
import service.DocumentoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/documentos")
public class DocumentoServlet extends HttpServlet {

    private DocumentoService service;

    @Override
    public void init() throws ServletException {
        service = new DocumentoService();
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

            case "generar":
                generar(request, response);
                break;

            case "entregado":
                marcarEntregado(request, response);
                break;

            case "pendiente":
                marcarPendiente(request, response);
                break;

            default:
                listar(request, response);
                break;
        }
    }

    // ==========================
    // LISTAR DOCUMENTOS
    // ==========================
    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int expedienteId =
                Integer.parseInt(
                        request.getParameter("expedienteId")
                );

        List<Documento> lista =
                service.obtenerPorExpediente(expedienteId);

        request.setAttribute(
                "documentos",
                lista
        );

        request.setAttribute(
                "expedienteId",
                expedienteId
        );

        request.getRequestDispatcher(
                "/documentos.jsp"
        ).forward(request, response);
    }

    // ==========================
    // GENERAR DOCUMENTOS
    // ==========================
    private void generar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int expedienteId =
                Integer.parseInt(
                        request.getParameter("expedienteId")
                );

        service.generarDocumentosParaExpediente(expedienteId);

        response.sendRedirect(
                "documentos?expedienteId=" + expedienteId
        );
    }

    // ==========================
    // MARCAR ENTREGADO
    // ==========================
    private void marcarEntregado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        int expedienteId =
                Integer.parseInt(
                        request.getParameter("expedienteId")
                );

        service.marcarEntregado(id);

        response.sendRedirect(
                "documentos?expedienteId=" + expedienteId
        );
    }

    // ==========================
    // MARCAR PENDIENTE
    // ==========================
    private void marcarPendiente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        int expedienteId =
                Integer.parseInt(
                        request.getParameter("expedienteId")
                );

        service.marcarPendiente(id);

        response.sendRedirect(
                "documentos?expedienteId=" + expedienteId
        );
    }

    // ==========================
    // POST (SUBIR ARCHIVO / OBSERVACIONES)
    // ==========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        int expedienteId =
                Integer.parseInt(
                        request.getParameter("expedienteId")
                );

        String archivo =
                request.getParameter("archivo");

        service.subirArchivo(id, archivo);

        response.sendRedirect(
                "documentos?expedienteId=" + expedienteId
        );
    }
}