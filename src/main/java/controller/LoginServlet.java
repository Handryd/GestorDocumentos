package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Usuario;
import service.AuthService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthService();
    }

    // ==========================
    // MOSTRAR LOGIN
    // ==========================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/login.jsp"
        ).forward(request, response);
    }

    // ==========================
    // INICIAR SESIÓN
    // ==========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        Usuario usuario =
                authService.login(
                        username,
                        password
                );

        if (usuario != null) {

            HttpSession session =
                    request.getSession(true);

            session.setAttribute(
                    "usuario",
                    usuario
            );

            session.setAttribute(
                    "nombreUsuario",
                    usuario.getNombreCompleto()
            );

            session.setAttribute(
                    "rol",
                    usuario.getRol()
            );

            // Redirección según rol
            switch (usuario.getRol().toUpperCase()) {

                case "ADMINISTRADOR":
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/dashboard.jsp"
                    );
                    break;

                case "ASESOR":
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/dashboard.jsp"
                    );
                    break;

                case "CLIENTE":
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/dashboard.jsp"
                    );
                    break;

                default:
                    session.invalidate();

                    request.setAttribute(
                            "error",
                            "Rol no válido."
                    );

                    request.getRequestDispatcher(
                            "/login.jsp"
                    ).forward(request, response);
                    break;
            }

        } else {

            request.setAttribute(
                    "error",
                    "Usuario o contraseña incorrectos."
            );

            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);
        }
    }
}