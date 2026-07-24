package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import service.AuthService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("usuario");
        String password = request.getParameter("password");

        Usuario usuarioLogueado = authService.login(username, password);

        if (usuarioLogueado != null) {
            String rol = usuarioLogueado.getRol();

            if (rol == null || rol.isBlank()) {
                request.setAttribute("error", "Rol no válido.");
                request.getRequestDispatcher("/login.jsp")
                        .forward(request, response);
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuarioLogueado);
            session.setAttribute("nombreUsuario", usuarioLogueado.getNombreCompleto());
            session.setAttribute("rol", rol);

            response.sendRedirect(
                    request.getContextPath() + "/dashboard.jsp"
            );
            return;
        }

        request.setAttribute("error", "Usuario o contraseña incorrectos.");
        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }
}
