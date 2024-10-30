<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="logica.datatypes.DTPaquete"%>
<%@ page import="logica.datatypes.DTRutaVueloPaquete"%>
<%@ page import="logica.enums.EnumAsiento"%>


<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"></jsp:include>
	<title>Volando.uy - Comprar Paquete</title>
	
    <%    
	DTPaquete datosPaquete = (DTPaquete) request.getAttribute("datos_paquete");
    
    float costoConDescuento = datosPaquete.getCosto();
    
    String formattedCosto;
    if (costoConDescuento == Math.floor(costoConDescuento)) {
        formattedCosto = String.valueOf((int) costoConDescuento);
    } else {
        formattedCosto = String.format("%.2f", costoConDescuento);
    }
    
    float costoSinDescuento = datosPaquete.getCosto() * 100 / (100 - datosPaquete.getDescuento());
    
    String formattedCostoSD;
    if (costoSinDescuento == Math.floor(costoSinDescuento)) {
    	formattedCostoSD = String.valueOf((int) costoSinDescuento);
    } else {
    	formattedCostoSD = String.format("%.2f", costoSinDescuento);
    }
    
    %>
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
	<!-- header -->
	<jsp:include page="../templates/header.jsp" />
	
	<!-- Listados -->
	<jsp:include page="../templates/sub-header.jsp" />
	
	<main>

		<div class="container mt-4 mb-4">
			<div class="row">
				<!-- Menu Izquierda -->
				<jsp:include page="../templates/menu-izquierda.jsp" />
				
				<div class="col-lg-9">
                    <div class="card mb-4">
                        <div class="row g-0">
	                        <div class="card-body">
	                            <div class="d-flex align-items-center justify-content-between"
	                                style="padding-bottom: 1rem;">
	                                <h3 class="card-title fw-bold">Confirmación de Compra - <%=datosPaquete.getNombre()%>:</h3>
	                            </div>
	                            <ul class="list-group list-group-flush mb-3">
	                            	<li class="list-group-item" style="color: green; font-size: 1.2em;"><strong>¡Descuento aplicado: <%=datosPaquete.getDescuento()%>%!</strong></li>
	                            	<li class="list-group-item"><strong>Costo sin descuento:</strong> USD <%= formattedCostoSD %></li>
	                            	<li class="list-group-item"><strong>Costo con descuento:</strong> USD <%= formattedCosto %></li>
	                            </ul>
							</div>  
                        </div>
                    </div> 
				
				    <div class="window mb-4">
				        <h3>Rutas de Vuelo Incluidas</h3>
				        <div id="routeList">
				        	<%
				        	List<DTRutaVueloPaquete> rutasVuelo = datosPaquete.getRutasVuelo();
				        	for (DTRutaVueloPaquete datosRuta: rutasVuelo) {
				        		String origen = datosRuta.getOrigen();
				        		String destino = datosRuta.getDestino();
				        		String nombre = datosRuta.getNombre();
				        		String cantAsientos = String.valueOf(datosRuta.getCantidad());
				        		String tipoAsiento = EnumAsiento.TURISTA.equals(datosRuta.getTipoAsiento()) ? "Turista" : "Ejecutivo";
				        		%>
					            <div class="route-card">
					                <div class="route-info">
					                    <a href="detalle-ruta-vuelo?id=<%= datosRuta.getNombre() %>" class="text-decoration-none">
					                        <h3><%=origen%>-<%=destino%> (<%=nombre%>)</h3>
					                    </a>
                                        <span class="badge client-badge"><%=cantAsientos%> Asiento/s <%=tipoAsiento%></span>
					                </div>
					            </div>
								<%
				        	}
				        	%>
				        </div>
				    </div>
					<% 
					if (!(boolean) request.getAttribute("compro_paquete")) {
				   	%>		
						<div class="mt-4 text-center d-flex justify-content-center">
						    <form method="POST" action="/volandouy/compra-paquete" onsubmit="redirectToReservas(event)">
						        <input type="hidden" name="compraPaquete" value="<%= datosPaquete.getNombre() %>">
						        <button type="submit" class="btn btn-success btn-lg me-3">
						            <i class="bi bi-check-circle"></i> Confirmar Compra
						        </button>
						    </form>
						    <a href="/volandouy/detalle-paquete?detalle-de=<%=datosPaquete.getNombre()%>" class="btn btn-danger btn-lg">
						        <i class="bi bi-x-circle"></i> Cancelar
						    </a>
						</div>
				    <%
				    }
					else {
					%>
						<p>Ya has comprado este paquete</p>
					<%}%>
				</div>
			</div>
		</div>
	</main>
	<!-- footer -->
	
	<jsp:include page="../templates/footer.jsp"/>
	
</body>
</html>