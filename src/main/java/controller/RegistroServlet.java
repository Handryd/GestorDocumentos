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


    // ==========================
    // MOSTRAR REGISTRO
    // ==========================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        request.getRequestDispatcher(
                "/registro.jsp"
        ).forward(request, response);

    }



    // ==========================
    // CREAR USUARIO
    // ==========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        Usuario usuario = new Usuario();


        usuario.setNombre(
                request.getParameter("nombre")
        );


        usuario.setApellido(
                request.getParameter("apellido")
        );


        usuario.setUsername(
                request.getParameter("username")
        );


        usuario.setCorreo(
                request.getParameter("correo")
        );


        usuario.setPassword(
                request.getParameter("password")
        );


        boolean registrado =
                authService.registrarUsuario(usuario);



        if(registrado){


            request.setAttribute(
                    "mensaje",
                    "Registro exitoso. Ahora puedes iniciar sesión."
            );


            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);


        }else{


            request.setAttribute(
                    "error",
                    "No fue posible registrar el usuario."
            );


            request.getRequestDispatcher(
                    "/registro.jsp"
            ).forward(request, response);

        }

    }

}