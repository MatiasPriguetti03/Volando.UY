<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="logica.datatypes.DTUsuario" %>
<%@ page import="logica.datatypes.DTCliente" %>
<%@ page import="logica.datatypes.DTReservaVuelo" %>
<%@ page import="logica.datatypes.DTCompraPaquete" %>
<%@ page import="logica.enums.EnumDoc" %>
<%@ page import="logica.enums.EnumAsiento" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <%
        // Obtener el usuario de la sesión
        DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");

        // Obtener el usuario del request
        DTUsuario user = (DTUsuario) request.getAttribute("datos");
        DTCliente cliente = (DTCliente) request.getAttribute("datos+");
        List<DTCompraPaquete> paquetes = (List<DTCompraPaquete>) request.getAttribute("compras");
        List<DTReservaVuelo> reservas = (List<DTReservaVuelo>) request.getAttribute("reservas");
        Map<String, String> nombresVuelos = (Map<String, String>) request.getAttribute("vuelos");

        String nick = user.getNick();
        String nombre = user.getNombre();
        String apellido = cliente.getApellido();
        String imagen = user.getImagen();
        String email = user.getEmail();
        String nacionalidad = cliente.getNacionalidad();
        
        LocalDate fechaNac = cliente.getFechaNac();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String tipoDoc = cliente.getTipoDoc().toString();
        String doc = cliente.getDoc();

        Boolean perfilPropio = false;

        if (loggedUser != null) {
            String loggedUserNick = loggedUser.getNick();
            perfilPropio = loggedUserNick.equals(nick);
        }
    %>

    <jsp:include page="/WEB-INF/templates/head.jsp" />
    <title>Volando.uy | <%= nick %></title>
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

                    <div class="col-lg-9">
                        <div class="card mb-4">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img src="img/<%= imagen %>" class="img-fluid rounded-start" alt="Rio de Janeiro">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <div class="d-flex align-items-center justify-content-between" style="padding-bottom: 1rem;">
                                            <h3 class="card-title fw-bold"><%= nombre + " " + apellido %></h3>
                                            <%
                                                if (perfilPropio) {
                                            %>
                                            <a href="/volandouy/modificar-datos-usuario"  class="btn btn-outline-primary">
                                                <i class="bi bi-pencil-square"></i> Modificar perfil
                                            </a>
                                            <%
                                                }
                                            %>
                                        </div>
                                        <p class="card-text"><b>Nickname</b>: <%= nick %></p>
                                        <p class="card-text"><b>Email</b>: <%= email %></p>
                                        <p class="card-text"><b>Fecha de Nacimiento</b>: <%= fechaNac.format(formatter) %></p>
                                        <p class="card-text"><b>Nacionalidad</b>: <%= nacionalidad %></p>
                                        <p class="card-text"><b>Tipo de Documento</b>: <%= tipoDoc %></p>
                                        <p class="card-text"><b>Numero de Documento</b>: <%= doc %></p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%
                            if (perfilPropio && reservas!= null && reservas.size() > 0) {
                        %>
                        <div class="window mb-4">
                            <h3>Mis Reservas</h3>
                            <%
                                for (DTReservaVuelo reserva : reservas) {
                            %>
                            <div class="flight-card mb-3">
                                <div class="flight-header">
                                    <div class="flight-details" style="flex-direction: column; align-items:start;">
                                        <span class="flight-date"><%= reserva.getFecha().format(formatter) %></span>
                                        <span class="flight-route" style="margin-bottom: 0.5rem;">
                                            <a href="detalle-reserva?id=<%= reserva.getId() %>" class="text-decoration-none" style="color: #3498db;">
                                                <%= nombresVuelos.get(reserva.getNameVuelo()) + " (" + reserva.getNameVuelo() + ")"%>
                                            </a>
                                        </span>
                                        <div class="flight-info">
                                            <span class="badge baggage-badge">
                                                <%= reserva.getEquipajeExtra() %> Equipaje extra
                                            </span>
                                            <span class="badge ticket-badge">
                                                <%= reserva.getPasajes() %> Pasajes
                                            </span>
                                            <span class="badge class-badge">
                                                <%= reserva.getTipoAsiento().toString() %>
                                            </span>
                                        </div>
                                    </div>
                                    <%
                                        float costo = reserva.getCosto();
                                        String formattedCosto;
                                        if (costo == Math.floor(costo)) {
                                            formattedCosto = String.valueOf((int) costo);
                                        } else {
                                            formattedCosto = String.format("%.2f", costo);
                                        }
                                    %>
                                    <div class="d-flex" style="flex-direction:column; text-align: right;">
                                        <span class="flight-cost">USD <%= formattedCosto %></span>
                                        <span class=""><small><%= reserva.getTipoReserva() %></small></span>
                                    </div>
                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                        <%
                            }

                            if (perfilPropio && paquetes != null && paquetes.size() > 0) {
                        %>
                        <div class="window mb-4">
                            <h3>Mis Paquetes</h3>
                            <%
                                for (DTCompraPaquete paquete : paquetes) {
                            %>
                            <div class="flight-card mb-3">
	                            <div class="flight-header">
	                                <div class="flight-details" style="flex-direction: column; align-items:start;">
	                                    <span class="flight-date"><%= paquete.getFechaCompra().format(formatter) %></span>
	                                    <span class="flight-route" style="margin-bottom: 0.5rem;"><a href="detalle-paquete?detalle-de=<%= paquete.getNombrePaquete() %>" class="text-decoration-none" style="color: #3498db;">
	                                    	<%= paquete.getNombrePaquete() %></a></span>
	                                    <div class="flight-info">
	                                        <span class="badge expiration-badge">Vence:
	                                            <%= paquete.getFechaVenc().format(formatter) %>
	                                        </span>
	                                    </div>
	                                </div>
									<%
                                        float costo = paquete.getCosto();
                                        String formattedCosto;
                                        if (costo == Math.floor(costo)) {
                                            formattedCosto = String.valueOf((int) costo);
                                        } else {
                                            formattedCosto = String.format("%.2f", costo);
                                        }
                                    %>
	                                <div class="d-flex" style="flex-direction:column; text-align: right;">
	                                    <span class="flight-cost">USD <%= formattedCosto %></span>
	                                </div>
	                            </div>
                             </div>
                            <%
                                }
                            %>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <!-- Footer -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>
