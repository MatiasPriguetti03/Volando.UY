<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="/WEB-INF/templates/head.jsp" />
<%@ page import="logica.datatypes.DTRutaVuelo"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<title>Volando.uy | Home</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
	<div class="container-fluid p-0">
		<!-- Header -->
		<jsp:include page="/WEB-INF/templates/header.jsp" />

		<main>
			<!-- Banner Carousel -->
			<div id="bannerCarousel" class="carousel slide" data-bs-ride="carousel">
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="img/banner-1.jpg" class="d-block w-100 index-banner" alt="Oferta Especial">
						<div class="carousel-caption">
							<p class="small">Ya lo soñaste...</p>
							<h1>Tu viaje comienza aquí­.</h1>
						</div>
					</div>
					<div class="carousel-item active">
					 <img src="img/banner-2.jpg" class="d-block w-100 index-banner" alt="Oferta Especial">
						<div class="carousel-caption">
							<p class="small">No esperes más</p>
								<h1>Miles de destinos a un click de distancia</h1>
						</div>
					</div>
					<!-- <div class="carousel-item active">
                        <a href="./listado-paquete.html">
                            <img src="img/PQ2.jpeg" class="d-block w-100 index-banner" alt="Oferta Especial">
                        </a>
                        <div class="carousel-caption">
                            <p>Paquete</p>
                            <a href="./listado-paquete.html" class="text-decoration-none" style="color: white;">
                                <h2></h2>
                            </a>
                        </div>
                    </div> -->
				</div>
				<button class="carousel-control-prev" type="button" data-bs-target="#bannerCarousel" data-bs-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span class="visually-hidden">Previous</span>
				</button>
				<button class="carousel-control-next" type="button" data-bs-target="#bannerCarousel" data-bs-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span> <span class="visually-hidden">Next</span>
				</button>
			</div>

			<!-- Listados -->
			<jsp:include page="../templates/sub-header.jsp" />

			<div class="container mt-4 mb-4">
				<div class="row">
					<!-- Botones y Categorias-->
					<jsp:include page="../templates/menu-izquierda.jsp" />

					<div class="col-md-9">
					
					<%
					Set<DTRutaVuelo> datosRuta = (Set<DTRutaVuelo>) request.getAttribute("rutas");
					Map<String, String> nombreAerolineas = (Map<String, String>) request.getAttribute("nombreAerolineas");
					int count = 0; // Contador para limitar las iteraciones
					
					if (datosRuta != null){
						for (DTRutaVuelo ruta : datosRuta){
							if (count >= 5){
								break;
							}
					%>
							<!-- Rutas de vuelo con nuevo estilo de tarjetas -->
							<div class="card mb-4">
								<div class="row g-0">
									<div class="col-md-4">
										<a href="/volandouy/detalle-ruta-vuelo?id=<%= ruta.getName() %>" class="text-decoration-none" style="color: black"> <img
											src="img/<%= ruta.getImagen() %>" class="rounded-start index-imagen">
										</a>
									</div>
									<div class="col-md-8">
										<div class="card-body">
											<a href="/volandouy/detalle-ruta-vuelo?id=<%= ruta.getName() %>" class="text-decoration-none" style="color: black">
												<h5 class="card-title"><%= ruta.getOrigen() %> - <%= ruta.getDestino() %> por <%= nombreAerolineas.get(ruta.getName()) %> (<%= ruta.getName() %>)</h5>
											</a>
											<p class="card-text"><%= ruta.getDescCorta() %></p>
											<a href="/volandouy/detalle-ruta-vuelo?id=<%= ruta.getName() %>" class="btn btn-link p-0">Saber más</a>
										</div>
									</div>
								</div>
							</div>
					<%
						count++;
						}
					} else {
					%>
						<div class="card m-auto">
							<div class="row g-0" style="">
								<h5 class="card-title p-4" style="text-align: center;">No existen Rutas de Vuelos ingresadas</h5>
							</div>
						</div>
					<%	
					}
					%>
						
					</div>
				</div>
			</div>
		</main>
	</div>

<!-- 	Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>
</html>