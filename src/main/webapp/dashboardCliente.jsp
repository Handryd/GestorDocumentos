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

<title>Panel Cliente | Gestor de Documentos</title>


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">


<style>

body{
    background:#f5f7fa;
}


.sidebar{

    min-height:100vh;
    background:#198754;
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

    transition:.3s;

}


.card-dashboard:hover{

    transform:translateY(-5px);

}


</style>


</head>


<body>


<div class="container-fluid">


<div class="row">



<!-- MENU CLIENTE -->


<div class="col-md-2 sidebar p-3">


<h4 class="text-center mb-4">

📄 Gestor Docs

</h4>


<hr>



<a href="dashboardCliente.jsp">

🏠 Inicio

</a>



<a href="#">

📂 Mi Expediente

</a>



<a href="#">

📄 Mis Documentos

</a>



<a href="#">

👤 Mi Perfil

</a>



<hr>



<a href="logout">

🚪 Cerrar Sesión

</a>


</div>




<!-- CONTENIDO -->


<div class="col-md-10 p-4">


<div class="d-flex justify-content-between">


<h2>

Bienvenido,

<%= usuario.getNombre() %>

</h2>


<span class="badge bg-success fs-6">

CLIENTE

</span>


</div>



<hr>




<div class="row g-4">



<div class="col-md-4">


<div class="card shadow card-dashboard">


<div class="card-body text-center">


<h1>

📂

</h1>


<h5>

Mi Expediente

</h5>


<p>

Consulta el estado de tu expediente.

</p>


<a href="#" class="btn btn-success">

Consultar

</a>


</div>


</div>


</div>




<div class="col-md-4">


<div class="card shadow card-dashboard">


<div class="card-body text-center">


<h1>

📄

</h1>


<h5>

Mis Documentos

</h5>


<p>

Consulta tus documentos registrados.

</p>


<a href="#" class="btn btn-success">

Ver documentos

</a>


</div>


</div>


</div>




<div class="col-md-4">


<div class="card shadow card-dashboard">


<div class="card-body text-center">


<h1>

👤

</h1>


<h5>

Mi Perfil

</h5>


<p>

Consulta tu información personal.

</p>


<a href="#" class="btn btn-success">

Ver perfil

</a>


</div>


</div>


</div>



</div>




<hr class="mt-5">



<div class="card shadow">


<div class="card-header bg-success text-white">

Información del Usuario

</div>



<div class="card-body">


<table class="table">


<tr>

<th>
Nombre
</th>

<td>
<%= usuario.getNombre() %>
</td>

</tr>



<tr>

<th>
Usuario
</th>

<td>
<%= usuario.getUsername() %>
</td>

</tr>



<tr>

<th>
Correo
</th>

<td>
<%= usuario.getCorreo() %>
</td>

</tr>



<tr>

<th>
Rol
</th>

<td>
CLIENTE
</td>

</tr>



</table>


</div>


</div>


</div>


</div>


</div>



</body>

</html>