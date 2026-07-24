<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestor de Documentos</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body{
            background-color:#f8f9fa;
        }

        .hero{
            padding:80px 20px;
            text-align:center;
        }

        .card-modulo{
            transition:0.3s;
        }

        .card-modulo:hover{
            transform:translateY(-5px);
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">
                Gestor de Documentos
            </a>
        </div>
    </nav>

    <!-- Encabezado -->
    <div class="hero">
        <div class="container">

            <h1 class="display-4">
                Sistema de Gestión de Expedientes
            </h1>

            <p class="lead">
                Administración de clientes, expedientes y documentos.
            </p>

            <a href="login.jsp" class="btn btn-primary btn-lg">
                Iniciar Sesión
            </a>

        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white text-center p-3">
        <p class="mb-0">
            Gestor de Documentos © 2026
        </p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>