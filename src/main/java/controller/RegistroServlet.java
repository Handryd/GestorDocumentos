package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import service.AuthService;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

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

        request.getRequestDispatcher("/registro.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getParameter("nombre"));
        nuevo.setApellido(request.getParameter("apellido"));
        nuevo.setUsername(request.getParameter("usuario"));
        nuevo.setPassword(request.getParameter("password"));
        nuevo.setCorreo(request.getParameter("correo"));

        String error = authService.registrarPublico(nuevo);

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/registro.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute(
                "mensaje",
                "Registro exitoso. Ya puede iniciar sesión."
        );
        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }
}
