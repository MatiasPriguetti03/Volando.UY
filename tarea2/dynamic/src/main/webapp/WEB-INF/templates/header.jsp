<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="logica.datatypes.DTUsuario"%>

<header class="bg-primary text-white bg-white">
	<div class="container">
		<div class="row align-items-center">
			<div class="col-md-3">
				<a href="/volandouy/home"> <img class="img-logo" src="${pageContext.request.contextPath}/img/logo-no-bg.png"
					alt="Volando.uy"></img>
				</a>
			</div>
			<div class="col-md-7">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Usuario, Ruta, vuelo, Paquete">
					<button class="btn btn-primary" type="button">Buscar</button>
				</div>
			</div>
			
			<div class="col-md-2 text-end">

				<%
				DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
				if (usr == null) {
				%>
				<!-- Usuario sin loguear -->

				<div class="d-flex align-items-center" style="justify-content: space-between">
					<a href="/volandouy/alta-usuario" class="btn btn-outline-primary">Registrarse</a> <a
						href="/volandouy/inicio-sesion" class="btn btn-primary" style="white-space: nowrap; margin-left: 0.5rem;">Iniciar
						Sesión</a>
<!-- 					Cargar Datos -->
					<!-- <a href="/volandouy/cargar-datos" class="btn btn-outline-secondary text-decoration-none btn-sm mx-2">Cargar Datos</a> -->
						
				</div>
				<%
				} else {
					String nick = usr.getNick();
				%>
				<!-- Usuario logueado -->

				<div class="dropdown">
					<div class="d-flex align-items-center" style="justify-content: end" id="userDropdown" data-bs-toggle="dropdown"
						aria-expanded="false">
						<div class="user-avatar me-2">
							<img src="img/<%= usr.getImagen() %>" alt="ejstar" class="rounded-circle" style="border: 1px solid #d3d3d3;" width="40" height="40">
						</div>
						<span class="me-2 texto-colapsado" style="color: black; user-select: none;"><%=usr.getNombre()%></span> <i
							class="bi bi-chevron-down" style="color: black;"></i>
					</div>
					<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
						<li><a class="dropdown-item" href="/volandouy/perfil?id=<%= nick %>">Mi Perfil</a></li>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="/volandouy/home?cerrar-sesion=true">Cerrar Sesión</a></li>
					</ul>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>
</header>