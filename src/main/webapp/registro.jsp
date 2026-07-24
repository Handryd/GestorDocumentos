<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Registro | Gestor de Documentos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">


    <style>

        body{
            background-color:#f4f6f9;
        }


        .registro-container{

            min-height:100vh;
            display:flex;
            justify-content:center;
            align-items:center;

        }


        .registro-card{

            width:100%;
            max-width:500px;
            padding:30px;
            border-radius:15px;
            background:white;
            box-shadow:0 0 15px rgba(0,0,0,0.1);

        }


        .titulo{

            text-align:center;
            font-size:2rem;
            font-weight:bold;
            margin-bottom:10px;

        }


        .subtitulo{

            text-align:center;
            color:gray;
            margin-bottom:25px;

        }

    </style>

</head>


<body>


<div class="container registro-container">


<div class="registro-card">


<div class="titulo">

    Crear cuenta

</div>


<div class="subtitulo">

    Regístrate como cliente

</div>



<%

String error = (String) request.getAttribute("error");

if(error != null){

%>

<div class="alert alert-danger">

    <%= error %>

</div>


<%

}

%>



<form action="registro" method="post">


<div class="mb-3">

<label class="form-label">
Nombre
</label>

<input
type="text"
name="nombre"
class="form-control"
required>

</div>



<div class="mb-3">

<label class="form-label">
Apellido
</label>

<input
type="text"
name="apellido"
class="form-control"
required>

</div>



<div class="mb-3">

<label class="form-label">
Usuario
</label>

<input
type="text"
name="username"
class="form-control"
required>

</div>



<div class="mb-3">

<label class="form-label">
Correo
</label>

<input
type="email"
name="correo"
class="form-control">

</div>



<div class="mb-3">

<label class="form-label">
Contraseña
</label>

<input
type="password"
name="password"
class="form-control"
required>

</div>



<div class="d-grid">

<button
type="submit"
class="btn btn-primary">

Registrarse

</button>


</div>


</form>



<hr>


<div class="text-center">

<a href="login">

Volver al login

</a>

</div>


</div>


</div>


</body>

</html>