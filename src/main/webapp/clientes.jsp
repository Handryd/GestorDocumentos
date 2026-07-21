<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Cliente"%>

<%
    List<Cliente> clientes =
            (List<Cliente>) request.getAttribute("clientes");

    Cliente clienteEditar =
            (Cliente) request.getAttribute("clienteEditar");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Gestión de Clientes</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-4">

    <div class="card shadow">

        <div class="card-header bg-primary text-white">

            <h2 class="mb-0">
                Gestión de Clientes
            </h2>

        </div>

        <div class="card-body">

            <!-- FORMULARIO -->

            <h4 class="mb-3">

                <%= (clienteEditar != null)
                        ? "Editar Cliente"
                        : "Registrar Cliente" %>

            </h4>

            <form action="clientes"
                  method="post">

                <input type="hidden"
                       name="accion"
                       value="<%= (clienteEditar != null)
                                ? "actualizar"
                                : "registrar" %>">

                <% if(clienteEditar != null){ %>

                    <input type="hidden"
                           name="id"
                           value="<%= clienteEditar.getId() %>">

                <% } %>

                <div class="row">

                    <!-- CÓDIGO EXPEDIENTE -->

                    <div class="col-md-3 mb-3">

                        <label class="form-label">

                            Código Expediente

                        </label>

                        <input type="text"
                               name="codigoExpediente"
                               class="form-control"
                               placeholder="EXP-001"
                               required
                               value="<%= clienteEditar != null
                                        ? clienteEditar.getCodigoExpediente()
                                        : "" %>">

                    </div>

                    <!-- EXPEDIENTE PADRE -->

                    <div class="col-md-3 mb-3">

                        <label class="form-label">

                            Expediente Padre

                        </label>

                        <input type="text"
                               name="expedientePadre"
                               class="form-control"
                               placeholder="EXP-001"
                               value="<%= clienteEditar != null
                                        && clienteEditar.getExpedientePadre() != null
                                        ? clienteEditar.getExpedientePadre()
                                        : "" %>">

                        <small class="text-muted">

                            Déjelo vacío si es expediente principal.

                        </small>

                    </div>

                    <!-- NOMBRE -->

                    <div class="col-md-3 mb-3">

                        <label class="form-label">

                            Nombre

                        </label>

                        <input type="text"
                               name="nombre"
                               class="form-control"
                               required
                               value="<%= clienteEditar != null
                                        ? clienteEditar.getNombre()
                                        : "" %>">

                    </div>

                    <!-- TELÉFONO -->

                    <div class="col-md-3 mb-3">

                        <label class="form-label">

                            Teléfono

                        </label>

                        <input type="text"
                               name="telefono"
                               class="form-control"
                               value="<%= clienteEditar != null
                                        ? clienteEditar.getTelefono()
                                        : "" %>">

                    </div>

                </div>

                <!-- CORREO -->

                <div class="mb-3">

                    <label class="form-label">

                        Correo Electrónico

                    </label>

                    <input type="email"
                           name="correo"
                           class="form-control"
                           value="<%= clienteEditar != null
                                    ? clienteEditar.getCorreo()
                                    : "" %>">

                </div>

                <button type="submit"
                        class="btn btn-success">

                    <%= (clienteEditar != null)
                            ? "Actualizar"
                            : "Guardar" %>

                </button>

                <a href="clientes"
                   class="btn btn-secondary">

                    Limpiar

                </a>

            </form>

            <hr>

            <!-- TABLA -->

            <h4>
                Lista de Clientes
            </h4>

            <table class="table table-striped table-hover">

                <thead class="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Código Expediente</th>
                    <th>Expediente Padre</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Tipo</th>
                    <th>Acciones</th>

                </tr>

                </thead>

                <tbody>

                <% if(clientes != null){

                    for(Cliente cliente : clientes){
                %>

                <tr>

                    <td>
                        <%= cliente.getId() %>
                    </td>

                    <td>

                        <span class="badge bg-primary">

                            <%= cliente.getCodigoExpediente() %>

                        </span>

                    </td>

                    <td>

                        <%= cliente.getDescripcionPadre() %>

                    </td>

                    <td>

                        <%= cliente.getNombre() %>

                    </td>

                    <td>

                        <%= cliente.getCorreo() %>

                    </td>

                    <td>

                        <%= cliente.getTelefono() %>

                    </td>

                    <td>

                        <% if(cliente.esExpedientePrincipal()){ %>

                            <span class="badge bg-success">

                                Principal

                            </span>

                        <% } else { %>

                            <span class="badge bg-warning text-dark">

                                Relacionado

                            </span>

                        <% } %>

                    </td>

                    <td>

                        <a href="clientes?accion=editar&id=<%= cliente.getId() %>"
                           class="btn btn-warning btn-sm">

                            Editar

                        </a>

                        <a href="clientes?accion=eliminar&id=<%= cliente.getId() %>"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('¿Desea eliminar este cliente?');">

                            Eliminar

                        </a>

                    </td>

                </tr>

                <%      }
                   }
                %>

                </tbody>

            </table>

        </div>

    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>