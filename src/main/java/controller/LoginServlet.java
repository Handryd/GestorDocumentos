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
                request.getParameter("usuario");

        String password =
                request.getParameter("password");
        

        System.out.println("Intentando login:");
        System.out.println("Usuario: " + username);


        Usuario usuarioLogueado =
                authService.login(
                        username,
                        password
                );

        if (usuarioLogueado != null) {

            HttpSession session =
                    request.getSession(true);

            session.setAttribute(
                    "usuario",
                    usuarioLogueado
            );

            session.setAttribute(
                    "nombreUsuario",
                    usuarioLogueado.getNombreCompleto()
            );

            session.setAttribute(
                    "rol",
                    usuarioLogueado.getRol()
            );

            // Redirección según rol
            switch (usuarioLogueado.getRol().toUpperCase()) {

                case "ADMINISTRADOR":
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/dashboard.jsp"
                    );
                    break;

                case "CLIENTE":
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/dashboardCliente.jsp"
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