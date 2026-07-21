<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Expediente" %>

<%
    List<Expediente> expedientes =
            (List<Expediente>) request.getAttribute("expedientes");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Expedientes</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-4">

    <h2 class="mb-4">📁 Gestión de Expedientes</h2>

    <!-- ================= BOTÓN CREAR ================= -->
    <div class="mb-3">

        <a href="crearExpediente.jsp"
           class="btn btn-primary">
            ➕ Nuevo Expediente
        </a>

        <a href="dashboard.jsp"
           class="btn btn-secondary">
            📊 Dashboard
        </a>

    </div>

    <!-- ================= TABLA ================= -->
    <div class="card">

        <div class="card-body table-responsive">

            <table class="table table-bordered table-striped">

                <thead class="table-dark">

                <tr>
                    <th>ID</th>
                    <th>Código</th>
                    <th>Cliente</th>
                    <th>Estado</th>
                    <th>Progreso</th>
                    <th>Fecha</th>
                    <th>Acciones</th>
                </tr>

                </thead>

                <tbody>

                <%
                    if (expedientes != null) {

                        for (Expediente e : expedientes) {
                %>

                <tr>

                    <td><%= e.getId() %></td>

                    <td><strong><%= e.getCodigo() %></strong></td>

                    <td><%= e.getNombreCliente() %></td>

                    <td>
                        <span class="badge
                            <%= e.getEstado().equals("COMPLETADO") ? "bg-success"
                             : e.getEstado().equals("EN PROCESO") ? "bg-warning text-dark"
                             : "bg-secondary" %>">

                            <%= e.getEstado() %>

                        </span>
                    </td>

                    <!-- PROGRESO VISUAL -->
                    <td style="width: 200px;">

                        <div class="progress">

                            <div class="progress-bar"
                                 role="progressbar"
                                 style="width: <%= e.getProgreso() %>%;">
                                <%= String.format("%.0f", e.getProgreso()) %>%
                            </div>

                        </div>

                    </td>

                    <td><%= e.getFechaCreacion() %></td>

                    <td>

                        <!-- VER DOCUMENTOS -->
                        <a href="documentos?expedienteId=<%= e.getId() %>"
                           class="btn btn-info btn-sm">
                            📄 Documentos
                        </a>

                        <!-- GENERAR DOCUMENTOS -->
                        <a href="documentos?accion=generar&expedienteId=<%= e.getId() %>"
                           class="btn btn-primary btn-sm">
                            🔄 Generar
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