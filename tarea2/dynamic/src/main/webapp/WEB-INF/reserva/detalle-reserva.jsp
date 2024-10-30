<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="logica.datatypes.DTVuelo"%>
<%@ page import="logica.datatypes.DTReservaVuelo"%>
<%@ page import="logica.datatypes.DTUsuario"%>
<%@ page import="logica.datatypes.DTPasajero"%>
<%@ page import="logica.datatypes.DTRutaVuelo"%>
<%@ page import="logica.datatypes.DTVuelo"%>
<%@ page import="logica.enums.EnumAsiento"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">

<head>
	<%
    DTReservaVuelo reserva = (DTReservaVuelo) request.getAttribute("reserva");
    String nombreAerolinea = (String) request.getAttribute("nombre-aerolinea");
    DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");    
    
    String tipoReserva = reserva.getTipoReserva();
    String tipoAsiento = reserva.getTipoAsiento().toString();
    Integer cantPasajes = reserva.getPasajes();
    Integer cantEquipaje = reserva.getEquipajeExtra();
    LocalDate fecha = reserva.getFecha();
    
    float costo = reserva.getCosto();
    String formattedCosto;
    if (costo == Math.floor(costo)) {
        formattedCosto = String.valueOf((int) costo);
    } else {
        formattedCosto = String.format("%.2f", costo);
    }
    
    List<DTPasajero> acompaniantes = reserva.getAcompaniantes();
    
    DTRutaVuelo rutaVuelo = reserva.getRutaVuelo();
    DTVuelo vuelo = reserva.getVuelo();
	%>
	<jsp:include page="../templates/head.jsp"/>
    <title>Volando.uy | Detalle de Reseva</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
    <div class="container-fluid p-0">
		<!-- Header -->
		<jsp:include page="/WEB-INF/templates/header.jsp" />
        <jsp:include page="../templates/sub-header.jsp"/>

		<main>
			<div class="container mt-4 mb-4">
		        <div class="row">
		        	<jsp:include page="../templates/menu-izquierda.jsp"/>
		
		   			<div class="col-lg-9">
		                    <div class="card mb-4">
		                        <div class="row g-0">
			                        <div class="card-body">
			                            <div class="d-flex align-items-center justify-content-between"
			                                style="padding-bottom: 1rem;">
			                                <h3 class="card-title fw-bold">Datos reserva:</h3>
			                            </div>
			                            <ul class="list-group list-group-flush mb-3">
			                            	<li class="list-group-item"><strong>Tipo de Reserva: </strong> <%= tipoReserva %></li>
			                            	<li class="list-group-item"><strong>Tipo de Asiento: </strong> <%= tipoAsiento %></li>
			                        		<li class="list-group-item"><strong>Cantidad de Pasajeros: </strong> <%= cantPasajes %></li>
			                        		<li class="list-group-item"><strong>Cantidad de Equipaje Extra: </strong> <%= cantEquipaje %></li>
			                        		<li class="list-group-item"><strong>Fecha de Reserva: </strong> <%= fecha.format(formatter) %></li>
			                        		<li class="list-group-item"><strong>Costo:</strong> USD <%= formattedCosto %></li>
			                            </ul>
									</div>  
		                        </div>
		                    </div> 
		                    	
			              	<div class="window mb-4">
			              		<h3>Ruta del Vuelo:</h3>
			                   <div class="route-card">
			                       <div class="route-info">
			                           <span class="flight-date"><%= nombreAerolinea %></span>
			                           <a href="detalle-ruta-vuelo?id=<%= rutaVuelo.getName() %>" class="text-decoration-none">
			                               <h3><%= rutaVuelo.getOrigen() + " - " + rutaVuelo.getDestino() + " (" + rutaVuelo.getName() + ")"%></h3>
			                           </a>
			                           <%
			                           for (String categoria : rutaVuelo.getCategorias()) {
			                           %>
			                           <span class="badge client-badge"><%= categoria %></span>
			                           <%
			                           }
			                           %>
			                       </div>
			                   </div>
			            	</div>
			            	
			              	<div class="window mb-4">
		                        <h3>Vuelo:</h3>
		                           
		                        <div class="route-card">
		                            <div class="route-info">
		                                <span class="flight-date"><%= vuelo.getFechaAlta().format(formatter) %></span>
		                                <a href="detalle-vuelo?vuelo=<%= vuelo.getName() %>" class="text-decoration-none">
		                                    <h3><%= vuelo.getName() %></h3>
		                                    <p><%= rutaVuelo.getOrigen() + " - " + rutaVuelo.getDestino() %></p>
		                                </a>
		                            </div>
		                        </div>
			                    
		                    </div>
		                    
		                    <div class="window mb-4" style="padding-bottom: 2.5rem !important;">
		                        <div>
		                            <h3>Pasajeros</h3>
		
		                            <div class="passengers-grid">
		                                <%
		                                //
		                                boolean esPrincipal = true;
		                                for (DTPasajero acompaniante : acompaniantes) {
		                                	String nombre = acompaniante.getNombre() + " " + acompaniante.getApellido();
		                                	if (esPrincipal) {
		                                %>
		                                <div class="passenger-card">
		                                    <div class="passenger-info">
		                                        <div class="passenger-avatar">👤</div>
		                                        <div class="passenger-details">
		                                            <div class="passenger-name"><%= nombre %></div>
		                                            <span class="passenger-type primary">Principal</span>
		                                        </div>
		                                    </div>
		                                </div>
		                                <%		esPrincipal = false;
		                                	} else { %>
		                                <div class="passenger-card">
		                                    <div class="passenger-info">
		                                        <div class="passenger-avatar">👤</div>
		                                        <div class="passenger-details">
		                                            <div class="passenger-name"><%= nombre %></div>
		                                            <span class="passenger-type secondary">Acompañante</span>
		                                        </div>
		                                    </div>
		                                </div>
		                                <%
		                                	}
		                                }
		                                %>
		                            </div>
		                        </div>
		                    </div>
			        	</div>
		            </div>
		        </div>
	    </main>
	</div>
	    
	<!-- 	Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>