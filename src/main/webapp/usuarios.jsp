<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Usuario"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Usuario admin = (Usuario) session.getAttribute("usuario");
    if (!admin.esAdministrador()) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    String error = (String) request.getAttribute("error");
    String mensaje = (String) request.getAttribute("mensaje");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios | Gestor de Documentos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-4 mb-5">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Gestión de Usuarios</h2>
        <a href="dashboard.jsp" class="btn btn-outline-secondary">Volver al inicio</a>
    </div>

    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <% if (mensaje != null) { %>
    <div class="alert alert-success"><%= mensaje %></div>
    <% } %>

    <div class="card shadow mb-4">
        <div class="card-header bg-primary text-white">
            Registrar nuevo usuario
        </div>
        <div class="card-body">
            <form action="usuarios" method="post">
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>

                    <div class="col-md-3 mb-3">
                        <label class="form-label">Apellido</label>
                        <input type="text" name="apellido" class="form-control">
                    </div>

                    <div class="col-md-3 mb-3">
                        <label class="form-label">Usuario de acceso</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>

                    <div class="col-md-3 mb-3">
                        <label class="form-label">Contraseña</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>

                    <div class="col-md-4 mb-3">
                        <label class="form-label">Correo</label>
                        <input type="email" name="correo" class="form-control">
                    </div>

                    <div class="col-md-4 mb-3">
                        <label class="form-label">Rol</label>
                        <select name="rol" class="form-select" required>
                            <option value="">Seleccione...</option>
                            <option value="USUARIO">Usuario</option>
                            <option value="CLIENTE">Cliente</option>
                            <option value="ADMINISTRADOR">Administrador</option>
                        </select>
                    </div>

                    <div class="col-md-4 mb-3 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">
                            Agregar usuario
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card shadow">
        <div class="card-header bg-dark text-white">
            Usuarios registrados
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped mb-0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Usuario</th>
                            <th>Correo</th>
                            <th>Rol</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (usuarios != null && !usuarios.isEmpty()) { %>
                            <% for (Usuario u : usuarios) { %>
                            <tr>
                                <td><%= u.getId() %></td>
                                <td><%= u.getNombreCompleto() %></td>
                                <td><%= u.getUsername() %></td>
                                <td><%= u.getCorreo() != null ? u.getCorreo() : "-" %></td>
                                <td><%= u.getRol() %></td>
                                <td><%= u.isActivo() ? "Activo" : "Inactivo" %></td>
                            </tr>
                            <% } %>
                        <% } else { %>
                            <tr>
                                <td colspan="6" class="text-center text-muted">
                                    No hay usuarios registrados.
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
