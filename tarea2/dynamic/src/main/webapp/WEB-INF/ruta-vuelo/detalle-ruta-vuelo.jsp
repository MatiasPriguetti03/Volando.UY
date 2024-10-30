<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="logica.datatypes.DTUsuario" %>
<%@ page import="logica.datatypes.DTCliente" %>
<%@ page import="logica.datatypes.DTRutaVuelo" %>
<%@ page import="logica.datatypes.DTCompraPaquete" %>
<%@ page import="logica.datatypes.DTInfoVuelo" %>
<%@ page import="logica.datatypes.DTPaquete" %>
<%@ page import="logica.enums.EnumDoc" %>
<%@ page import="logica.enums.EnumAsiento" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <%
	    // Obtener el usuario de la sesión
	    DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");

    	// Obtener el DTRutaVuelo
        DTRutaVuelo ruta = (DTRutaVuelo) request.getAttribute("ruta");
    	
        List<DTPaquete> paquetes = (List<DTPaquete>) request.getAttribute("paquetes");
    	
    	String nickAerolinea = (String) request.getAttribute("nick-aerolinea");
    	String nombre = ruta.getName();
    	String desc = ruta.getDesc();
    	String imagen = ruta.getImagen();
    	String origen = ruta.getOrigen();
    	String destino = ruta.getDestino();
    	LocalTime hora = ruta.getHora();
    	
    	float costoTurista = ruta.getCostoTurista();
    	float costoEjecutivo = ruta.getCostoEjecutivo();
    	float costoExtra = ruta.getCostoExtra();
    	String formattedCostoTurista;
    	String formattedCostoEjecutivo;
    	String formattedCostoExtra;
    	
    	LocalDate fechaAlta = ruta.getFechaAlta();
    	String[] categorias = ruta.getCategorias();
    	String categoriasStr = String.join(", ", categorias);
		Set<DTInfoVuelo> vuelos = ruta.getDTInfoVuelos();
		
		Boolean rutaPropia = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	
        if (costoTurista == Math.floor(costoTurista)) {
            formattedCostoTurista = String.valueOf((int) costoTurista);
        } else {
            formattedCostoTurista = String.format("%.2f", costoTurista);
        }
        
        if (costoEjecutivo == Math.floor(costoEjecutivo)) {
        	formattedCostoEjecutivo = String.valueOf((int) costoEjecutivo);
        } else {
        	formattedCostoEjecutivo = String.format("%.2f", costoEjecutivo);
        }
        
        if (costoExtra == Math.floor(costoExtra)) {
        	formattedCostoExtra = String.valueOf((int) costoExtra);
        } else {
        	formattedCostoExtra = String.format("%.2f", costoExtra);
        }
    	

		String tipoUsuario = (String) session.getAttribute("tipo_usuario_sesion");

        if (loggedUser != null) {
            String loggedUserNick = loggedUser.getNick();
            rutaPropia = nickAerolinea.equals(loggedUserNick) && tipoUsuario.equals("aerolinea");
        }
        
        
    %>

    <jsp:include page="/WEB-INF/templates/head.jsp" />
    <title>Volando.uy | <%= nombre %></title>
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

					<div class="col-9">
                        <div class="card mb-4">
                            <div class="row g-0">
                            <%if (imagen != null){ %>
                                <div class="col-4 div-img-cover">
                                    <img src="img/<%= imagen %>" class="rounded-start"
                                        alt="detail-img">
                                </div>
                            <%
                            }
                            %>
                                <div class="col-8">
                                    <div class="card-body">
                                        <h3 class="card-title fw-bold"><%= origen + " - " + destino + " (" + nombre + ")"%></h3>
                                        <p class="card-text"><%= desc %></p>
                                        <p class="card-text"><b>Origen:</b> <%= origen %></p>
                                        <p class="card-text"><b>Destino:</b> <%= destino %></p>
                                        <p class="card-text"><b>Hora:</b> <%= hora %></p>
                                        <p class="card-text"><b>Costo Turista:</b> USD <%= formattedCostoTurista %></p>
                                        <p class="card-text"><b>Costo Ejecutivo:</b> USD <%= formattedCostoEjecutivo %></p>
                                        <p class="card-text"><b>Costo Equipaje Extra:</b> USD <%= formattedCostoExtra %></p>
                                        <p class="card-text"><b>Categorias:</b> <%= categoriasStr %></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
	                    <%
	                    	if ((vuelos.size() > 0 || rutaPropia) && ruta.getEstado() == "Confirmada") {
	                    %>
	                	<div class="window mb-4">
	                        <h3>Vuelos Asociados</h3>
                            <%
                                for (DTInfoVuelo vuelo : vuelos) {
                            %>
	                        <div class="route-card">
	                            <div class="route-info">
	                                <span class="flight-date"><%= vuelo.getFecha().format(formatter) %></span>
	                                <a href="detalle-vuelo?vuelo=<%= vuelo.getName() %>" class="text-decoration-none">
	                                    <h3><%= vuelo.getName() %></h3>
	                                    <p><%= vuelo.getOrigen() + " - " + vuelo.getDestino() %></p>
	                                </a>
	                            </div>
	                        </div>
		                    <%
                                }
		                    	if (rutaPropia && ruta.getEstado() == "Confirmada") {
		                    %>
	                        <a href="alta-vuelo?ruta_vuelo=<%=nombre %>" class="btn btn-primary">Agregar Vuelo</a>
		                    <%
		                    	}
		                    %>
	                    </div>
	                    <%
	                    	}
	                    	if (paquetes.size() > 0) {
	                    %>
                        <div class="window mb-4">
	                        <h3>Paquetes Asociados</h3>
                            <%
                                for (DTPaquete paquete : paquetes) {
                            %>
	                        <a href="detalle-paquete?detalle-de=<%= paquete.getNombre() %>" class="text-decoration-none">
							    <div class="route-card" style="height: 110px; align-items: center;">
							        <div class="route-info">
							            <div class="header-container" style="display: flex; align-items: center;">
							                <h3 style="margin-right: 10px;"><%= paquete.getNombre() %></h3>
							                <span class="badge client-badge" style="background-color: #28a745;"><%= paquete.getDescuento() %>% OFF</span>
							            </div>
							            <p class="text-muted"><%= paquete.getDescripcion() %></p>
							        </div>
							    </div>
							</a>
	                    <%
	                    		}
	                    	}
	                    %>	
	                    </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <!-- Footer -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>
