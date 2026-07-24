package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import service.UsuarioService;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdministrador(request, response)) {
            return;
        }

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        request.setAttribute("usuarios", usuarios);

        request.getRequestDispatcher("/usuarios.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdministrador(request, response)) {
            return;
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getParameter("nombre"));
        nuevo.setApellido(request.getParameter("apellido"));
        nuevo.setUsername(request.getParameter("username"));
        nuevo.setPassword(request.getParameter("password"));
        nuevo.setCorreo(request.getParameter("correo"));
        nuevo.setRol(request.getParameter("rol"));

        String error = usuarioService.registrarUsuario(nuevo);

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("usuarios", usuarioService.listarUsuarios());
            request.getRequestDispatcher("/usuarios.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("mensaje", "Usuario registrado correctamente.");
        request.setAttribute("usuarios", usuarioService.listarUsuarios());
        request.getRequestDispatcher("/usuarios.jsp")
                .forward(request, response);
    }

    private boolean esAdministrador(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }

        Usuario actual = (Usuario) session.getAttribute("usuario");

        if (!actual.esAdministrador()) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return false;
        }

        return true;
    }
}
