<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <%@page errorPage="/WEB-INF/500.jsp"%> -->
<%@ page import="logica.datatypes.DTVuelo"%>
<%@ page import="logica.datatypes.DTRutaVuelo"%>
<%@ page import="logica.datatypes.DTUsuario"%>
<%@ page import="logica.datatypes.DTCompraPaquete"%>
<%@ page import="logica.enums.EnumAsiento"%>
<%@ page import="java.util.List"%>


<!DOCTYPE html>
<html lang="es">

<head>
	<jsp:include page="../templates/head.jsp"/>
    <title>Volando.uy</title>
  	<%
		DTVuelo datosVuelo = (DTVuelo) request.getAttribute("datos_vuelo");
       	DTRutaVuelo datosRuta = (DTRutaVuelo) request.getAttribute("datos_ruta_del_vuelo");
       	DTUsuario datosAerolinea = (DTUsuario) request.getAttribute("datos_aerolinea_del_vuelo");
       	List<DTCompraPaquete> datosPaquetes = (List<DTCompraPaquete>) request.getAttribute("datos_paquetes");
	%>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
    <div class="container-fluid p-0">
        <header class="bg-primary text-white bg-white">
            <jsp:include page="../templates/header.jsp"/>
            <jsp:include page="../templates/sub-header.jsp"/>
    	</header>

    <main>

	<div class="container mt-4 mb-4">
        <div class="row">
        	<jsp:include page="../templates/menu-izquierda.jsp"/>

    <div class="col-lg-9">
                    <div class="card mb-4">
                        <div class="row g-0">
                        <%
                        if (datosRuta.getImagen() != null) { 
                        %>
                        	<div class="col-md-4 div-img-cover">
                                <img src="img/<%=datosVuelo.getImagen() %>" alt="Vuelo" class="rounded-start">
                            </div>
                        <%
                        }
                        %>
                            <div class="col-md-8">
                            	<div class="card-body">
		                        	<h3 class="card-title h5 fw-bold" style="padding-bottom: 1rem;">
		                        		<%=datosVuelo.getName() %></h3>
		                            <p class="card-text"><b>Ciudad origen:</b> <%=datosVuelo.getRutaVuelo().getOrigen()  %></p>
		                            <p class="card-text"><b>Ciudad destino:</b> <%=datosVuelo.getRutaVuelo().getDestino() %></p>
		                            <p class="card-text"><b>Fecha de vuelo:</b> <%=datosVuelo.getFechaVuelo() %></p>
		                            <p class="card-text"><b>Duracion:</b> <%=datosVuelo.getDuracion() %> horas</p>
		                            <p class="card-text"><b>Capacidad turista:</b> <%=datosVuelo.getAsientoxMaxTurista() %></p>
		                            <p class="card-text"><b>Capacidad ejecutivo:</b> <%=datosVuelo.getAsientosMaxEjecutivo() %></p>
                            	</div>
                            </div>
                        </div>
                    </div>
                   	<div class="window mb-4">
                    	<h3>Ruta del Vuelo:</h3>
                        <div class="route-card">
                            <div class="route-info">
                                <a href="perfil?id=<%=datosAerolinea.getNick() %>" class="flight-date text-decoration-none" style="color: #3498DB; font-weight: 600"><%= datosAerolinea.getNombre() %></a>
                                <a href="detalle-ruta-vuelo?id=<%=datosRuta.getName() %>" class="text-decoration-none">
                                    <h3><%=datosVuelo.getRutaVuelo().getOrigen()  %> - <%=datosVuelo.getRutaVuelo().getDestino() %> (<%=datosVuelo.getRutaVuelo().getName() %>)</h3>
                                </a>
                                <%
									for (String categoria : datosRuta.getCategorias()){
								%>								
								<span class="badge client-badge"><%=categoria %></span>
								<%	
									}
								%>
								<div class="flight-date mt-2">
									Asiento Turista: USD <span id="cost-tur"><%=datosRuta.getCostoTurista() %></span> - Asiento Ejecutivo: USD <span
										id="cost-eje"><%=datosRuta.getCostoEjecutivo() %></span> -
									Equipaje Extra: USD <span id="cost-ext"><%=datosRuta.getCostoExtra() %></span>
								</div>
                            </div>
                        </div>
                    </div>
                    
                    <%
                    if (datosPaquetes != null && datosPaquetes.size() > 0) {
                    %>
                    <div class="window mb-4">
						<h3>Paquetes disponibles:</h3>
						<%
						for (DTCompraPaquete paquete : datosPaquetes) {
						%>
						<div class="route-card d-flex">
							<div class="route-info col-10">
								<a href="detalle-paquete?detalle-de=<%=paquete.getNombrePaquete() %>" class="text-decoration-none">
									<h3 class="mb-2"><%=paquete.getNombrePaquete() %></h3>
									<span class="badge client-badge" style="background-color: #28a745;">
									<span id="<%=paquete.getNombrePaquete() %>-discount"><%=paquete.getDescuentoPaquete() %></span>% OFF</span>
									<span class="badge expiration-badge"> Vence: <%=paquete.getFechaVenc() %> </span>
								</a>
								<p class="flight-date"><%=paquete.getDTRutasDeCompra().get(datosRuta.getName()).getTipoAsiento().toString() %> </p>
							</div>
							<div class="col-2 mx-auto mt-auto text-center">
								<span class="badge uses-badge"><%=paquete.getUsosRestantes().get(datosRuta.getName()) %></span>
								<p class="flight-date">usos restantes</p>
							</div>
						</div>
						<%
						}
						%>
					</div>
					<%
                    }
					%>
					<div class="window mb-4">
						<form method="POST" action="reserva-vuelo">
							<div class="mb-3 d-none">
								<label for="nombre-vuelo" class="form-label">Vuelo:</label> <input
									type="text" class="form-control" id="flight-name" name="nombreVuelo"
									value="<%= datosVuelo.getName()%>" required>
							</div>
							<div class="mb-3">
								<h3 style="padding-bottom: 1rem">Datos de la Reserva:</h3>
								<label for="asiento-type" class="form-label">Tipo de Asiento:</label> <select
									class="form-select" id="asiento-type" name="tipoAsiento" required>
									<option value="turista">Turista</option>
									<option value="ejecutivo">Ejecutivo</option>
								</select>
							</div>
							<div class="mb-3">
								<label for="cant-equipaje" class="form-label">Cantidad de Equipaje Extra:</label> <input
									type="number" class="form-control" id="cant-equipaje" name="cantEquipajeExtra"
									min="0" value="0">
							</div>
							<div class="mb-3">
								<label for="cant-pasajeros" class="form-label">Cantidad de Pasajes:</label> <input
									type="number" class="form-control" id="cant-pasajeros" name="cantPasajeros" min="1"
									value="1">
							</div>
							<div class="mb-3" id="pasajeros-container">
<!-- 						<div class="col-md-6 mt-1"> -->
<!-- 							<label for="acomp-nombre" class="form-label">Nombre:</label> <input type="text" -->
<%-- 							class="form-control" id="nombre${i}" name="nombre${i}" required> --%>
<!-- 						</div> -->
<!-- 						<div class="col-md-6 mt-1"> -->
<!-- 							<label for="acomp-apellido" class="form-label">Apellido:</label> <input type="text" -->
<%-- 							class="form-control" id="apellido${i}" name="apellido${i}" required> --%>
<!-- 						</div> -->
							</div>
							<div class="mb-3 d-flex">
								<div class="col-8">
									<label for="acomps" class="form-label">Usar paquete:</label> <select
										class="form-select" id="paquete" name="paqueteReserva">
										<option value=""></option>
										<%
										for (DTCompraPaquete paquete : datosPaquetes) {
										%>
										<option value="<%=paquete.getNombrePaquete() %>"><%=paquete.getNombrePaquete() %></option>
										<%
										}
										%>
									</select>
								</div>
								<div class="col-4 text-center mt-auto">
									Costo Total <br>
									<b>USD <span id="cost"><%=datosRuta.getCostoTurista() %></span></b>
								</div>
							</div>
							<% 					
								if (request.getAttribute("error") != null) {
							%>
								<div class="text-danger my-3"><%=((String) request.getAttribute("error")) %></div>
							<%
								}
							%>
							<div class="text-center mt-5">
								<button type="submit" class="btn btn-success btn-lg w-20" id="submit-reserva">
									<i class="bi bi-check-circle"></i>Confirmar Reserva
								</button>
								<a href="/volandouy/detalle-vuelo?vuelo=<%=datosVuelo.getName()%>" class="btn btn-danger btn-lg">
						        	<i class="bi bi-x-circle"></i> Cancelar
						    	</a>
							</div>
						</form>
					</div>
                </div>
            </div>
        </div>
    </main>

    <footer class="bg-dark py-4 mt-auto">
        <jsp:include page="../templates/footer.jsp"/>
    </footer>

</body>

</html>
