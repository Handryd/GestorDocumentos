package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

private boolean requiereAdministrador(String ruta){

    return ruta.equals("/clientes")
        || ruta.equals("/reportes");

}

@Override
public void doFilter(
ServletRequest request,
ServletResponse response,
FilterChain chain)
throws IOException, ServletException {


HttpServletRequest req =
(HttpServletRequest) request;


HttpServletResponse res =
(HttpServletResponse) response;


String ruta =
req.getServletPath();


HttpSession session =
req.getSession(false);



/*
Permitir recursos públicos
*/

if(
ruta.contains("/login") ||
ruta.contains("/registro") ||
ruta.contains("/css") ||
ruta.contains("/js")
){

chain.doFilter(request,response);
return;

}



/*
Validar sesión
*/

if(session == null ||
session.getAttribute("usuario")==null){

res.sendRedirect(
req.getContextPath()+"/login.jsp"
);

return;

}



/*
Obtener rol
*/

String rol =
(String) session.getAttribute("rol");


System.out.println(
"Usuario: " + session.getAttribute("usuario")
+ " | Rol: " + rol
);

/*
Reglas de administrador
*/

if(requiereAdministrador(ruta)){


    if(!"ADMINISTRADOR".equals(rol)){


        res.sendError(
            HttpServletResponse.SC_FORBIDDEN,
            "Acceso denegado"
        );


        return;

    }

}



chain.doFilter(request,response);


}


}