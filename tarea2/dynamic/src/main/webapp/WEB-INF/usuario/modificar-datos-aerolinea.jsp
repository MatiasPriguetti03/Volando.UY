<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="logica.datatypes.DTUsuario" %>
<%@ page import="logica.datatypes.DTAerolinea" %>
<%@ page import="logica.datatypes.DTRutaVuelo" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <%
        // Obtener el usuario de la sesión
        DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");

        // Obtener el usuario del request
        DTUsuario user = (DTUsuario) request.getAttribute("datos");
        DTAerolinea aerolinea = (DTAerolinea) request.getAttribute("datos+");

        String nick = user.getNick();
        String nombre = user.getNombre();
        String imagen = user.getImagen();
        
        String email = user.getEmail();
        String descripcion = aerolinea.getDescripcion();
        String sitioWeb = aerolinea.getSitioWeb();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        Boolean perfilPropio = false;

        if (loggedUser != null) {
            String loggedUserNick = loggedUser.getNick();
            perfilPropio = loggedUserNick.equals(nick);
        }
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
                                        
                                        <div class="mt-2 px-3 w-100">
											<label for="description" class="form-label">Descripción</label>
											<div class="has-validation">
												<textarea class="form-control required-aerolinea" name="descripcion" id="description" value="<%=descripcion %>"></textarea>
											</div>
										</div>
                                        
                                        <div class="cmb-3">
											<label for="website" class="form-label">Sitio web</label>
											<div class="mt-2 px-3 w-100">
												<input type="text" class="form-control" name="sitio" id="website" value="<%=sitioWeb %>">
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
