package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/dashboard.jsp",
        "/usuarios",
        "/usuarios/*",
        "/clientes",
        "/clientes/*",
        "/expedientes",
        "/expedientes/*",
        "/documentos",
        "/documentos/*",
        "/requisitos",
        "/requisitos/*"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean autenticado = session != null
                && session.getAttribute("usuario") != null;

        if (autenticado) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect(
                httpRequest.getContextPath() + "/login.jsp"
        );
    }
}
