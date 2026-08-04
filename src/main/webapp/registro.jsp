<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro | Gestor de Documentos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f4f6f9;
        }

        .registro-container {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px 0;
        }

        .registro-card {
            width: 100%;
            max-width: 520px;
            padding: 30px;
            border-radius: 15px;
            background: white;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        .logo {
            font-size: 2rem;
            font-weight: bold;
            text-align: center;
            margin-bottom: 10px;
        }

        .subtitle {
            text-align: center;
            color: gray;
            margin-bottom: 25px;
        }
    </style>
</head>
<body>

<div class="container registro-container">

    <div class="registro-card">

        <div class="logo">Gestor de Documentos</div>

        <div class="subtitle">
            Cree su cuenta. Se registrará con rol <strong>Usuario</strong>.
        </div>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <div class="alert alert-danger"><%= error %></div>
        <%
            }
        %>

        <form action="<%= request.getContextPath() %>/registro" method="post">

            <div class="mb-3">
                <label class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Apellido</label>
                <input type="text" name="apellido" class="form-control">
            </div>

            <div class="mb-3">
                <label class="form-label">Usuario de acceso</label>
                <input type="text" name="usuario" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Correo</label>
                <input type="email" name="correo" class="form-control">
            </div>

            <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary">
                    Registrarse
                </button>
            </div>

        </form>

        <hr>

        <div class="text-center">
            <a href="login.jsp">¿Ya tiene cuenta? Iniciar sesión</a>
        </div>

    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
