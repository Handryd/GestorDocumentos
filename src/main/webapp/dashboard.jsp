<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Usuario"%>

<%
    if(session.getAttribute("usuario") == null){
        response.sendRedirect("login.jsp");
        return;
    }

    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | Gestor de Documentos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>

        body{
            background-color:#f5f7fa;
        }

        .sidebar{
            min-height:100vh;
            background:#0d6efd;
            color:white;
        }

        .sidebar a{
            color:white;
            text-decoration:none;
            display:block;
            padding:12px;
            border-radius:8px;
        }

        .sidebar a:hover{
            background:rgba(255,255,255,0.15);
        }

        .card-dashboard{
            transition:0.3s;
        }

        .card-dashboard:hover{
            transform:translateY(-5px);
        }

    </style>

</head>
<body>

<div class="container-fluid">

    <div class="row">

        <!-- Menú lateral -->

        <div class="col-md-2 sidebar p-3">

            <h4 class="text-center mb-4">
                📁 Gestor Docs
            </h4>

            <hr>

            <a href="dashboard.jsp">
                🏠 Inicio
            </a>

            <a href="clientes">
                👤 Clientes
            </a>

            <a href="#">
                📂 Expedientes
            </a>

            <a href="#">
                📄 Documentos
            </a>

            <a href="#">
                📊 Reportes
            </a>

            <hr>

            <a href="logout">
                🚪 Cerrar Sesión
            </a>

        </div>

        <!-- Contenido -->

        <div class="col-md-10 p-4">

            <div class="d-flex justify-content-between">

                <h2>
                    Bienvenido,
                    <%= usuario.getNombre() %>
                </h2>

                <span class="badge bg-primary fs-6">
                    <%= usuario.getRol() %>
                </span>

            </div>

            <hr>

            <!-- Tarjetas -->

            <div class="row g-4">

                <div class="col-md-3">

                    <div class="card shadow card-dashboard">

                        <div class="card-body text-center">

                            <h1>👤</h1>

                            <h5>Clientes</h5>

                            <p>
                                Gestión de clientes registrados.
                            </p>

                            <a href="clientes"
                               class="btn btn-primary">

                                Entrar

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-3">

                    <div class="card shadow card-dashboard">

                        <div class="card-body text-center">

                            <h1>📂</h1>

                            <h5>Expedientes</h5>

                            <p>
                                Administración de expedientes.
                            </p>

                            <a href="#"
                               class="btn btn-primary">

                                Entrar

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-3">

                    <div class="card shadow card-dashboard">

                        <div class="card-body text-center">

                            <h1>📄</h1>

                            <h5>Documentos</h5>

                            <p>
                                Gestión documental.
                            </p>

                            <a href="#"
                               class="btn btn-primary">

                                Entrar

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-3">

                    <div class="card shadow card-dashboard">

                        <div class="card-body text-center">

                            <h1>📊</h1>

                            <h5>Reportes</h5>

                            <p>
                                Estadísticas y avances.
                            </p>

                            <a href="#"
                               class="btn btn-primary">

                                Entrar

                            </a>

                        </div>

                    </div>

                </div>

            </div>

            <hr class="mt-5">

            <!-- Información -->

            <div class="card shadow">

                <div class="card-header bg-primary text-white">

                    Información del Usuario

                </div>

                <div class="card-body">

                    <table class="table">

                        <tr>
                            <th>Nombre</th>
                            <td><%= usuario.getNombre() %></td>
                        </tr>

                        <tr>
                            <th>Usuario</th>
                            <td><%= usuario.getUsername() %></td>
                        </tr>

                        <tr>
                            <th>Rol</th>
                            <td><%= usuario.getRol() %></td>
                        </tr>

                        <tr>
                            <th>Estado</th>
                            <td>
                                <%= usuario.isActivo() ? "Activo" : "Inactivo" %>
                            </td>
                        </tr>

                    </table>

                </div>

            </div>

        </div>

    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>