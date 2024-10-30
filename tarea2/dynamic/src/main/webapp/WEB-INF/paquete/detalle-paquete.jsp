<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="logica.datatypes.DTPaquete" %>
<%@ page import="logica.datatypes.DTRutaVueloPaquete" %>
<%@ page import="logica.enums.EnumAsiento" %>
<%@ page import="java.util.List" %>
    
<!DOCTYPE html>
<html lang="es">

<head>
<jsp:include page="/WEB-INF/templates/head.jsp" />
<title>Volando.uy | Detalle Paquete</title>
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

					<div class="col-md-9">		
						<% 
						DTPaquete datosPaquete = (DTPaquete) request.getAttribute("detalle-Paquete");
						String imagen = datosPaquete.getImagen();
						String nombre = datosPaquete.getNombre();
						String refNombre = nombre.replace(" ", "%20");
						String descripcion = datosPaquete.getDescripcion();
						String descuento = datosPaquete.getDescuento().toString();
						float costo = datosPaquete.getCosto();
						String formattedCosto;
						
				        if (costo == Math.floor(costo)) {
				            formattedCosto = String.valueOf((int) costo);
				        } else {
				        	formattedCosto = String.format("%.2f", costo);
				        }
				        
						String validez = datosPaquete.getPeriodoValidez().toString();
						%>								
						<div class="card mb-4">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img src=img/<%=imagen%> class="img-fluid rounded-start"
                                        alt="">
    
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h3 class="card-title fw-bold"><%=nombre%></h3>
                                        <p class="card-text"><%=descripcion%></p>
                                        <p class="card-text"><b>Descuento:</b> <%=descuento%>% OFF</p>
                                        <p class="card-text"><b>Costo:</b> USD <%=formattedCosto%></p>
                                        <p class="card-text"><b>Validez:</b> <%=validez%> días</p>
    
                                    </div>
                                </div>
                            </div>
                        </div>
    
                        <div class="window mb-4">
                            <h3>Rutas de Vuelo Incluidas</h3>
                            <div id="routeList">
                            <% 
                            List<DTRutaVueloPaquete> rutasPaquete = datosPaquete.getRutasVuelo();
                            for (DTRutaVueloPaquete rutaDatos: rutasPaquete) {
                            	String nombreRuta = rutaDatos.getOrigen() + " - " + rutaDatos.getDestino() 
                            						+ " (" + rutaDatos.getNombre() + ")";
                            	Integer cantAsientosI = rutaDatos.getCantidad();
                            	String cantAsientos = cantAsientosI.toString();
                            	String tipoAsiento = rutaDatos.getTipoAsiento() == EnumAsiento.TURISTA? "Turista" : "Ejecutivo";
                            %>
                                <a href="/volandouy/detalle-ruta-vuelo?id=<%=rutaDatos.getNombre()%>" class="text-decoration-none">
                                    <div class="route-card">
                                        <div class="route-info">
                                            <h3><%=nombreRuta%></h3>
                                            <span class="badge client-badge"><%=cantAsientos%> Asiento/s <%=tipoAsiento%></span>
                                        </div>
                                    </div>
                                </a>
                            <%}%>
                            </div>
                        </div>
						<%
						if (request.getAttribute("tipo-usuario") == "cliente") {
						%>
                        <div class="text-center mt-4 mb-4">
                            <a href="compra-paquete?compraPaquete=<%=refNombre%>" class="btn btn-primary btn-lg w-20">
                                <i class="bi bi-cart-check me-2"></i>Comprar Paquete
                            </a>
                        </div>
                        <%}%>
					</div>
				</div>
			</div>
		</main>
	</div>

<!-- 	Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>

</html>