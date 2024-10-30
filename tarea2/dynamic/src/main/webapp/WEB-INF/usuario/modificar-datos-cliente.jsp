<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="logica.datatypes.DTUsuario" %>
<%@ page import="logica.datatypes.DTCliente" %>
<%@ page import="logica.datatypes.DTReservaVuelo" %>
<%@ page import="logica.datatypes.DTCompraPaquete" %>
<%@ page import="logica.enums.EnumDoc" %>
<%@ page import="logica.enums.EnumAsiento" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <%
        // Obtener el usuario del request
        DTUsuario user = (DTUsuario) request.getAttribute("datos");
        DTCliente cliente = (DTCliente) request.getAttribute("datos+");

        String nombre = user.getNombre();
        String apellido = cliente.getApellido();
        String[] nombreSinApellido = nombre.split(" "+ apellido);
        nombre = nombreSinApellido[0];

        
        String imagen = user.getImagen();
        
        String nick = user.getNick();
        String email = user.getEmail();
        String nacionalidad = cliente.getNacionalidad();
        
        LocalDate fechaNac = cliente.getFechaNac();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String tipoDoc = cliente.getTipoDoc().toString();
        String doc = cliente.getDoc();
    %>

    <jsp:include page="/WEB-INF/templates/head.jsp" />
    <title>Volando.uy | <%= nick %></title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
    <div class="container-fluid p-0">
        <!-- Header -->
        <jsp:include page="/WEB-INF/templates/header.jsp" />

        <main>
            <!-- Listados -->
            <jsp:include page="../templates/sub-header.jsp" />

            <div class="container mt-4 mb-4">
                <div class="row">
                    <!-- Botones y Categorias-->
                    <jsp:include page="../templates/menu-izquierda.jsp" />

                    <div class="col-lg-9">
                        <div class="card mb-4">
                            <div class="row g-0">
                                <div class="col-md-8">
                                    <div class="card-body">
                                    <form method="POST" action="modificar-datos-usuario" enctype="multipart/form-data" id="modificar-datos-usuarioForm">
                                    
                                        <p class="card-text"><b>Nickname</b>: <%= nick %></p>
                                        <p class="card-text"><b>Email</b>: <%= email %></p>
                                        
                                        <div class="mb-3">
											<label for="name" class="form-label">Nombre</label>
											<div class="has-validation">
												<input type="text" class="form-control" name="nombre" id="name" value="<%=nombre %>">
											</div>
										</div>
										
										<div class="mb-3">
											<label for="surname" class="form-label">Apellido</label>
											<div class="has-validation">
												<input type="text" class="form-control required-cliente" name="apellido" id="surname" value="<%=apellido %>">
											</div>
										</div>
                                        
                                        <div class="mb-3">
											<label for="birthdate" class="form-label">Fecha de nacimiento</label>
											<div class="has-validation">
												<input type="date" class="form-control required-cliente" name="nacimiento" id="birthdate" value="<%=fechaNac %>">
											</div>
										</div>
                                        
                                        <div class="mb-3">
											<label for="nationality" class="form-label">Nacionalidad</label>
											<div class="has-validation">
												<input type="text" class="form-control required-cliente" name="nacionalidad" id="nationality" value="<%=nacionalidad %>">
											</div>
										</div>
                                        
                                        <div class="mb-3">
											<label for="username" class="form-label">Documento</label>
											<div class="input-group has-validation">
												<select class="form-select bg-light.bg-gradient col-1 required-cliente" name="tipoDoc" id="typeId">
													<option value="mantener">Mantener</option>
													<option value="ci">CI</option>
													<option value="passport">Pasaporte</option>
												</select> 
												<input type="text" class="form-control required-cliente" name="documento" id="idNumber" value="<%=doc %>">
											</div>
										</div>
                                        
                                        <div>
                                        	<label for="imgFile" class="form-label">Foto de perfil <span class="text-muted">(Opcional)</span></label> <input
											class="form-control" type="file" name="imagen" id="imgFile">
                                        </div>
                                        
                                        <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Cambiar Datos</button>
                                        <p class="card-text">los campos vacíos no serán cambiados</p>
                                    </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <!-- Footer -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>
