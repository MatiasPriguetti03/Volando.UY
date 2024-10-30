<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="es">

<head>
<jsp:include page="/WEB-INF/templates/head.jsp"></jsp:include>
<title>Volando.uy - Iniciar sesión</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
	<header class="bg-white shadow-sm">
		<nav class="navbar navbar-expand-lg navbar-light py-0">
			<div class="container">
				<a href="/volandouy/home"> <img style="height: 100%; width: 30%;"
					src="${pageContext.request.contextPath}/img/logo-no-bg.png" alt="Volando.uy"></img>
				</a>
			</div>
		</nav>
	</header>

	<main class="d-flex align-items-center justify-content-center mt-auto">
		<div class="col-4 d-flex align-items-center justify-content-center border window rounded-3">
			<form class="col-10 text-center my-5 mx-auto" method="POST" action="/volandouy/inicio-sesion">

				<img class="mb-4 img-fluid" src="${pageContext.request.contextPath}/img/logo-no-bg.png" alt="logo" width="400">
				<h1 class="h3 mb-3 fw-normal">Inicia sesión</h1>

				<div class="form-floating mb-3">
					<input type="text" class="form-control" id="loginInput" placeholder="nick or name@example.com" name="login" required>
					<label for="floatingInput">Nickname o mail</label>
					<div class="invalid-feedback">Ingrese nickname o mail.</div>
				</div>
				<div class="form-floating mb-3">
					<input type="password" class="form-control" id="passwordInput" placeholder="Password" name="password" required> <label
						for="floatingPassword">Contraseña</label>
					<div class="invalid-feedback">Ingrese una contraseña.</div>
				</div>
				<% 
					if (request.getAttribute("error") != null) {
				%>
					<div class="text-danger my-3"><%=((String) request.getAttribute("error")) %></div>
				<%
					}
				%>
				<div class="checkbox mb-3">
					<label> <input type="checkbox" name="remember-me-check" value="remember-me"> Recuerdame
					</label>
				</div>

				<button class="w-100 btn btn-lg btn-primary" type="submit">Ingresar</button>
				<div class="mb-2 mt-3">
					<a href="alta-usuario">No tenés usuario todavía? Registrate</a>
				</div>

			</form>
		</div>
	</main>

	<!-- 	Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp"></jsp:include>

</body>

</html>