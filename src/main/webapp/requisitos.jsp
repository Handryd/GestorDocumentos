<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.RequisitoDocumento" %>

<%
    List<RequisitoDocumento> requisitos =
            (List<RequisitoDocumento>) request.getAttribute("requisitos");

    RequisitoDocumento editar =
            (RequisitoDocumento) request.getAttribute("requisitoEditar");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Requisitos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-4">

    <h2 class="mb-4">📄 Gestión de Requisitos de Documentos</h2>

    <!-- ================= FORMULARIO ================= -->
    <div class="card mb-4">
        <div class="card-header">
            <strong><%= (editar == null) ? "Agregar Requisito" : "Editar Requisito" %></strong>
        </div>

        <div class="card-body">

            <form action="requisitos" method="post">

                <% if (editar != null) { %>
                    <input type="hidden" name="id" value="<%= editar.getId() %>">
                    <input type="hidden" name="accion" value="actualizar">
                <% } else { %>
                    <input type="hidden" name="accion" value="insertar">
                <% } %>

                <div class="mb-3">
                    <label>Nombre</label>
                    <input type="text"
                           name="nombre"
                           class="form-control"
                           value="<%= (editar != null) ? editar.getNombre() : "" %>"
                           required>
                </div>

                <div class="mb-3">
                    <label>Descripción</label>
                    <textarea name="descripcion"
                              class="form-control"><%= (editar != null) ? editar.getDescripcion() : "" %></textarea>
                </div>

                <% if (editar != null) { %>
                <div class="form-check mb-3">
                    <input type="checkbox"
                           class="form-check-input"
                           name="activo"
                           value="true"
                           <%= editar.isActivo() ? "checked" : "" %>>
                    <label class="form-check-label">Activo</label>
                </div>
                <% } %>

                <button type="submit" class="btn btn-primary">
                    Guardar
                </button>

                <a href="requisitos" class="btn btn-secondary">
                    Limpiar
                </a>

            </form>

        </div>
    </div>

    <!-- ================= TABLA ================= -->
    <div class="card">

        <div class="card-header">
            <strong>Lista de Requisitos</strong>
        </div>

        <div class="card-body table-responsive">

            <table class="table table-bordered table-striped">

                <thead class="table-dark">

                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>

                </thead>

                <tbody>

                <%
                    if (requisitos != null) {

                        for (RequisitoDocumento r : requisitos) {
                %>

                <tr>

                    <td><%= r.getId() %></td>

                    <td><%= r.getNombre() %></td>

                    <td><%= r.getDescripcion() %></td>

                    <td>
                        <span class="badge <%= r.isActivo() ? "bg-success" : "bg-danger" %>">
                            <%= r.isActivo() ? "Activo" : "Inactivo" %>
                        </span>
                    </td>

                    <td>

                        <a href="requisitos?accion=editar&id=<%= r.getId() %>"
                           class="btn btn-warning btn-sm">
                            Editar
                        </a>

                        <a href="requisitos?accion=eliminar&id=<%= r.getId() %>"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('¿Eliminar este requisito?')">
                            Eliminar
                        </a>

                        <a href="requisitos?accion=estado&id=<%= r.getId() %>&activo=<%= !r.isActivo() %>"
                           class="btn btn-info btn-sm">
                            Cambiar Estado
                        </a>

                    </td>

                </tr>

                <%
                        }
                    }
                %>

                </tbody>

            </table>

        </div>

    </div>

</div>

</body>
</html>