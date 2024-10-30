<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="logica.datatypes.DTRutaVuelo"%>
<%@ page import="logica.datatypes.DTUsuario"%>
<!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="/WEB-INF/templates/head.jsp" />
<title>Volando.uy - Crear Vuelo</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
	<div class="container-fluid p-0">
		<!-- Header -->
		<jsp:include page="/WEB-INF/templates/header.jsp" />

		<!-- Sub-header -->
		<jsp:include page="../templates/sub-header.jsp" />

		<div class="container mt-4 mb-4">
			<div class="row">
				<!-- Botones y Categorias-->
				<jsp:include page="../templates/menu-izquierda.jsp" />
				
				<%
					DTRutaVuelo datosRuta = (DTRutaVuelo) request.getAttribute("datos_ruta");
					DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
				
				%>

				<div class="col-md-9">
					<div class="window mb-4">
						<h4>Ruta del Vuelo:</h4>
						<div class="route-card">
							<div class="route-info">
								<span class="flight-date"><%=usr.getNombre() %></span> <a href="detalle-ruta-vuelo?id=<%=datosRuta.getName() %>"
									class="text-decoration-none">
									<h3><%=datosRuta.getOrigen() %> - <%=datosRuta.getDestino() %> (<%=datosRuta.getName() %>)</h3>
								</a> 
								<%
									for (String categoria : datosRuta.getCategorias()){
								%>								
								<span class="badge client-badge"><%=categoria %></span>
								<%	
									}
								%>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-body">
							<h2 class="card-title">Alta de Vuelo</h2>
							<form method="POST" action="alta-vuelo" enctype="multipart/form-data">
								<div class="mb-3 d-none">
									<label for="flightName" class="form-label">Ruta Vuelo</label> <input type="text" class="form-control"
										id="flightName" name="nombreRuta" value="<%=datosRuta.getName()%>" readonly="readonly">
								</div>
								<div class="mb-3">
									<label for="flightName" class="form-label">Nombre del Vuelo</label> <input type="text" class="form-control"
										id="flightName" name="nombreVuelo" required spellcheck="">
									<div class="form-text">Debe ser único en la plataforma</div>
									<% 					
										if (request.getAttribute("error") != null) {
									%>
										<div class="text-danger my-3"><%=((String) request.getAttribute("error")) %></div>
									<%
										}
									%>
								</div>
								<div class="mb-3">
									<label for="flightDate" class="form-label">Fecha del Vuelo</label> <input type="date" class="form-control"
										id="flightDate" name="fechaVuelo" required>
								</div>
								<div class="row mb-3">
									<div class="col">
										<label for="duration" class="form-label">Duración</label>
										<div class="input-group">
										    <input type="number" class="form-control" id="hours" name="duracionHoras" min="0" max="99" required>
											<span class="input-group-text">hrs</span>
										    
										    <input type="number" class="form-control" id="minutes" name="duracionMinutos" min="0" max="59" required>
											<span class="input-group-text">min</span>
										</div>
									</div>
									<div class="col">
										<label for="touristNumber" class="form-label">Cantidad de Asientos Turista</label> <input type="number"
											min="0" class="form-control" id="touristNumber" name="asientosTurista" required>
									</div>
									<div class="col">
										<label for="executiveNumber" class="form-label">Cantidad de Asientos Ejecutivo</label> <input type="number"
											min="0" class="form-control" id="executiveNumber" name="asientosEjecutivo" required>
									</div>
								</div>
								<div class="mb-3">
									<label for="routeImage" class="form-label">Imagen (opcional)</label> <input type="file" class="form-control"
										id="flightImage" name="imagenVuelo">
								</div>
								<button type="submit" class="btn btn-primary" style="float: right;">Crear Vuelo</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>

</html>