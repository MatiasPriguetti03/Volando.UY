<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

<head>
<jsp:include page="/WEB-INF/templates/head.jsp"></jsp:include>
<title>Volando.uy | Registrarse</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
	<header class="bg-white shadow-sm">
		<nav class="navbar navbar-expand-lg navbar-light py-0">
			<div class="container">
				<a href="home"> <img style="height: 100%; width: 30%;" src="img/logo-no-bg.png" alt="Volando.uy"></img>
				</a>
			</div>
		</nav>
	</header>

	<main class="d-flex align-items-center justify-content-center mt-auto">
		<div class="col-6 my-5 d-flex flex-column border window rounded-3">
			<div class="d-flex align-items-center justify-content-center mt-4">
				<div class="col-10 text-center mx-auto">
					<h1 class="h3 mb-5 fw-normal">Ingresa tus datos</h1>
					<form method="POST" action="alta-usuario" enctype="multipart/form-data" id="alta-usuarioForm">
						<div class="btn-group col-8 mb-2" role="group" aria-label="Basic radio toggle button group">
							<input type="radio" class="btn-check" name="tipoUsrRadio" id="cliRadio" value="cliente" autocomplete="off" checked>
							<label class="btn btn-outline-primary" for="cliRadio">Cliente</label> 
							<input type="radio" class="btn-check" name="tipoUsrRadio" id="aerRadio" value="aerolinea" autocomplete="off"> 
							<label class="btn btn-outline-primary" for="aerRadio">Aerolinea</label>
						</div>

						<div class="d-flex mt-auto text-start">
							<div class="col-6 flex-column d-flex align-items-center">
								<div class="mt-2 px-3 w-100">
									<label for="nickname" class="form-label">Nickname</label>
									<div class="input-group has-validation">
										<span class="input-group-text">@</span> <input type="text" class="form-control" name="nick" id="nickname" required>
										<div class="invalid-feedback">El nickname es requerido.</div>
									</div>
								</div>
								<div class="mt-2 px-3 w-100">
									<label for="password" class="form-label">Contraseña</label>
									<div class="has-validation">
										<input type="password" class="form-control" name="contrasenia" id="password" required>
										<div class="invalid-feedback">La contraseña es requerida.</div>
									</div>
								</div>
								<div class="mt-2 px-3 w-100">
									<label for="imgFile" class="form-label">Foto de perfil <span class="text-muted">(Opcional)</span></label> <input
										class="form-control" type="file" name="imagen" id="imgFile">
								</div>
							</div>
							<div class="col-6 flex-column d-flex mt-auto">
								<div class="mt-2 px-3 w-100">
									<label for="email" class="form-label">E-mail</label>
									<div class="has-validation">
										<input type="text" class="form-control" name="mail" id="email" required>
										<div class="invalid-feedback">El e-mail es requerido.</div>
									</div>
								</div>
								<div class="mt-2 px-3 w-100">
									<label for="rptpassword" class="form-label">Repetir Contraseña</label>
									<div class="has-validation">
										<input type="password" class="form-control" id="rptpassword" required>
										<div class="invalid-feedback">Debe repetir la contraseña.</div>
									</div>
								</div>
								<div class="mt-2 px-3 w-100">
									<label for="name" class="form-label">Nombre</label>
									<div class="has-validation">
										<input type="text" class="form-control" name="nombre" id="name" required>
										<div class="invalid-feedback">El nombre es requerido.</div>
									</div>
								</div>
							</div>
						</div>

						<div class="d-flex mt-auto mb-3 text-start alta-usuario-cliente d-none" id="div-cliente">
							<div class="col-6 flex-column d-flex align-items-center">
								<div class="mt-2 px-3 w-100">
									<label for="nationality" class="form-label">Nacionalidad</label>
									<div class="has-validation">
										<input type="text" class="form-control required-cliente" name="nacionalidad" id="nationality" required>
										<div class="invalid-feedback">La nacionalidad es requerida para clientes.</div>
									</div>
								</div>
								<div class="mt-2 px-3 w-100">
									<label for="username" class="form-label">Documento</label>
									<div class="input-group has-validation">
										<select class="form-select bg-light.bg-gradient col-1 required-cliente" name="tipoDoc" id="typeId" required>
											<option value="ci">CI</option>
											<option value="passport">Pasaporte</option>
										</select> <input type="text" class="form-control required-cliente" name="documento" id="idNumber" required>
										<div class="invalid-feedback">El documento es requerido.</div>
									</div>
								</div>
							</div>
							<div class="col-6 flex-column d-flex mt-auto">
								<div class="mt-2 px-3 w-100">
									<label for="surname" class="form-label">Apellido</label>
									<div class="has-validation">
										<input type="text" class="form-control required-cliente" name="apellido" id="surname" required>
										<div class="invalid-feedback">El apellido es requerido para clientes.</div>
									</div>
								</div>
								<div class="mt-2 px-3 w-100">
									<label for="birthdate" class="form-label">Fecha de nacimiento</label>
									<div class="has-validation">
										<input type="date" class="form-control required-cliente" name="nacimiento" id="birthdate" required>
										<div class="invalid-feedback">La fecha de nacimiento es requerida para clientes.</div>
									</div>
								</div>
							</div>
						</div>

						<div class="d-flex mt-auto mb-3 text-start d-none" id="div-aerolinea">
							<div class="col-6 flex-column d-flex align-items-end mt-auto">
								<div class="mt-2 px-3 w-100">
									<label for="website" class="form-label">Sitio web</label> <input type="text" class="form-control" name="sitio" id="website">
								</div>
							</div>
							<div class="col-6 flex-column d-flex mt-auto">
								<div class="mt-2 px-3 w-100">
									<label for="description" class="form-label">Descripción</label>
									<div class="has-validation">
										<textarea class="form-control required-aerolinea" name="descripcion" id="description" required></textarea>
										<div class="invalid-feedback">La descripción es requerida para clientes.</div>
									</div>
								</div>
							</div>
						</div>
						<%
						if (request.getAttribute("error") != null) {
						%>
						<div class="text-danger my-4"><%=((String) request.getAttribute("error"))%></div>
						<%
						}
						%>
						
						<div class="text-danger my-4 d-none" id="error-password">Las contraseñas deben coincidir</div>
						
						<button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Registrarse</button>

					</form>
				</div>
			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/templates/footer.jsp"></jsp:include>

</body>

</html>