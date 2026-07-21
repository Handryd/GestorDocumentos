<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Documento" %>

<%
    List<Documento> documentos =
            (List<Documento>) request.getAttribute("documentos");

    int expedienteId =
            (request.getAttribute("expedienteId") != null)
                    ? (int) request.getAttribute("expedienteId")
                    : Integer.parseInt(request.getParameter("expedienteId"));
%>

<!DOCTYPE html>
<html>
<head>
    <title>Documentos del Expediente</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-4">

    <h2 class="mb-3">
        📁 Documentos del Expediente ID: <%= expedienteId %>
    </h2>

    <!-- ================= BOTÓN GENERAR ================= -->
    <div class="mb-3">

        <a href="documentos?accion=generar&expedienteId=<%= expedienteId %>"
           class="btn btn-primary">
            🔄 Generar Documentos
        </a>

        <a href="expedientes.jsp"
           class="btn btn-secondary">
            ⬅ Volver a Expedientes
        </a>

    </div>

    <!-- ================= TABLA ================= -->
    <div class="card">

        <div class="card-body table-responsive">

            <table class="table table-bordered table-striped">

                <thead class="table-dark">

                <tr>
                    <th>ID</th>
                    <th>Documento</th>
                    <th>Estado</th>
                    <th>Archivo</th>
                    <th>Acciones</th>
                </tr>

                </thead>

                <tbody>

                <%
                    if (documentos != null) {

                        for (Documento d : documentos) {
                %>

                <tr>

                    <td><%= d.getId() %></td>

                    <td><%= d.getNombreDocumento() %></td>

                    <td>
                        <span class="badge <%= d.estaEntregado() ? "bg-success" : "bg-warning text-dark" %>">
                            <%= d.getEstado() %>
                        </span>
                    </td>

                    <td>
                        <%= (d.getArchivo() != null) ? d.getArchivo() : "Sin archivo" %>
                    </td>

                    <td>

                        <!-- MARCAR ENTREGADO -->
                        <a href="documentos?accion=entregado&id=<%= d.getId() %>&expedienteId=<%= expedienteId %>"
                           class="btn btn-success btn-sm">
                            ✔ Entregado
                        </a>

                        <!-- MARCAR PENDIENTE -->
                        <a href="documentos?accion=pendiente&id=<%= d.getId() %>&expedienteId=<%= expedienteId %>"
                           class="btn btn-warning btn-sm">
                            ⏳ Pendiente
                        </a>

                    </td>

                </tr>

                <!-- FORM SUBIR ARCHIVO -->
                <tr>

                    <td colspan="5">

                        <form action="documentos" method="post" enctype="multipart/form-data">

                            <input type="hidden" name="accion" value="Subir">
                            <input type="hidden" name="id" value="<%= d.getId() %>">
                            <input type="hidden" name="expedienteId" value="<%= expedienteId %>">

                            <input type="file" name="archivoPDF" accept="application/pdf" class="form-control">

                            <button class="btn btn-primary btn-sm mt-2">
                                📤 Subir Archivo
                            </button>

                        </form>

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