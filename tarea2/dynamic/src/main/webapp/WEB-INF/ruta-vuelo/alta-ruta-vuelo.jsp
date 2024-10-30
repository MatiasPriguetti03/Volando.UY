<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- %@page errorPage="/WEB-INF/500.jsp"%> -->

<!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="../templates/head.jsp" />
<title>Volando.uy</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
	<div class="container-fluid p-0">
		<header class="bg-primary text-white bg-white">
			<jsp:include page="../templates/header.jsp" />
			<jsp:include page="../templates/sub-header.jsp" />
		</header>

		<main>

			<div class="container mt-4 mb-4">
				<div class="row">
					<jsp:include page="../templates/menu-izquierda.jsp" />

					<div class="col-md-9">
						<div class="card">
							<div class="card-body">
								<h2 class="card-title">Alta de Ruta de Vuelo</h2>
								<form method="POST" action="/volandouy/alta-ruta-vuelo" enctype="multipart/form-data">
									<div class="mb-3">
										<label for="routeName" class="form-label">Nombre de la Ruta</label> <input type="text" class="form-control"
											id="routeName" name="nombreRuta" required>
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
										<label for="shortDescription" class="form-label">Descripción Corta</label> <input type="text"
											class="form-control" id="shortDescription" name="descripcionCorta" required>
									</div>
									<div class="mb-3">
										<label for="description" class="form-label">Descripción</label>
										<textarea class="form-control" id="description" name="descripcion" rows="3" required></textarea>
									</div>
									<div class="row mb-3">
										<div class="col">
											<label for="time" class="form-label">Hora</label> <input type="time" class="form-control" id="time"
												name="hora" required>
										</div>
										<div class="col">
											<label for="touristCost" class="form-label">Costo Turista</label> <input type="number" min="0"
												class="form-control" id="touristCost" name="costoTurista" required>
										</div>
										<div class="col">
											<label for="executiveCost" class="form-label">Costo Ejecutivo</label> <input type="number" min="0"
												class="form-control" id="executiveCost" name="costoEjecutivo" required>
										</div>
									</div>
									<div class="mb-3">
										<label for="extraLuggageCost" class="form-label">Costo Unidad Equipaje Extra</label> <input type="number"
											min="0" class="form-control" id="extraLuggageCost" name="costoEquipajeExtra" required>
									</div>
									<div class="row mb-3">
										<div class="col">
											<label for="originCity" class="form-label">Ciudad Origen</label> <select class="form-control"
												id="originCity" name="ciudadOrigen" required>
												<%
												String[] ciudadesO = (String[]) request.getAttribute("ciudades");
												if (ciudadesO != null)
													for (String ciudad: ciudadesO) {
													%>
													<option><%=ciudad%></option>
													<%}%>
											</select>
										</div>
										<div class="col">
											<label for="destinationCity" class="form-label">Ciudad Destino</label> <select
												class="form-control" id="destinationCity" name="ciudadDestino" required>
												<%
												String[] ciudadesD = (String[]) request.getAttribute("ciudades");
												if (ciudadesD != null)
													for (String ciudad: ciudadesD) {
													%>
													<option><%=ciudad%></option>
													<%}%>
											</select>
										</div>
									</div>
									<div class="mb-3">
										<label for="categories" class="form-label">Categorías</label>

										<%
										String[] categorias = (String[]) session.getAttribute("categorias");
										if (categorias == null) {
										%>
										<h6>Aún no hay Categorías ingesadas</h6>
										<%
										} else {
											int cantCat = categorias.length;
											if (cantCat == 0) {
										%>
										<h6 class="list-group-item">Aún no hay Categorías ingesadas</h6>
										<%
											} else {
										%>

										<select multiple class="form-select" id="categories" name="categorias[]">
											<%
												for (int i = 0; i < cantCat; i++) {
											%>
											<option><%=categorias[i]%></option>
											<%
												}
											}
										}
											%>
										</select>
										<div class="form-text">Mantener apretado ctrl para seleccionar varias categorias</div>
									</div>
									<div class="mb-3">
										<label for="routeImage" class="form-label">Imagen (opcional)</label> <input type="file" class="form-control"
											id="routeImage" name="imagenRuta">
									</div>
									<button type="submit" class="btn btn-primary" style="float: right;">Crear Ruta de Vuelo</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>

	<footer class="bg-dark py-4 mt-auto">
		<jsp:include page="../templates/footer.jsp" />
	</footer>

</body>

</html>
