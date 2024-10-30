<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="logica.datatypes.DTUsuario" %>
<%@ page import="logica.datatypes.DTAerolinea" %>
<%@ page import="logica.datatypes.DTRutaVuelo" %>
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
        DTAerolinea aerolinea = (DTAerolinea) request.getAttribute("datos+");
        List<DTRutaVuelo> rutas = (List<DTRutaVuelo>) request.getAttribute("rutas");

        String nick = user.getNick();
        String nombre = user.getNombre();
        String imagen = user.getImagen();
        
        String email = user.getEmail();
        String descripcion = aerolinea.getDescripcion();
        String sitioWeb = aerolinea.getSitioWeb();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
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
                                            <h3 class="card-title fw-bold"><%= nombre %></h3>
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
                                        <p class="card-text"><b>Descripción</b>: <%= descripcion %></p>
                                        <p class="card-text"><b>Web</b>: <%= sitioWeb %></p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%
                            if (rutas.size() > 0) {
                        %>
                        <div class="window mb-4">
                            <h3>Rutas de Vuelo</h3>
                            <%
                                for (DTRutaVuelo ruta : rutas) {
                                	if (!perfilPropio) {
                                		if (ruta.getEstado() == "Confirmada") {
                           				 %>
				                            <a href="detalle-ruta-vuelo?id=<%= ruta.getName() %>" class="text-decoration-none">
				                                <div class="route-card">
				                                    <div class="route-info">
				                                        <span class="flight-date"><%= ruta.getFechaAlta().format(formatter) %></span>
				                                        <h3><%= ruta.getOrigen() + " - " + ruta.getDestino() + " (" + ruta.getName() + ")" %></h3>
				                                        <%
				                                            for (String categoria : ruta.getCategorias()) {
				                                        %>
				                                        <span class="badge client-badge"><%= categoria %></span>
				                                        <%
				                                            }
				                                        %>
				                                    </div>
				                                </div>
				                            </a>
                            			<%		
                            			}
                                	} else {
                                	%>
                                        <a href="detalle-ruta-vuelo?id=<%= ruta.getName() %>" class="text-decoration-none">
                                            <div class="route-card">
                                                <div class="route-info">
                                                    <span class="flight-date"><%= ruta.getFechaAlta().format(formatter) %></span>
                                                    <h3><%= ruta.getOrigen() + " - " + ruta.getDestino() + " (" + ruta.getName() + ")" %></h3>
                                                    <%
                                                        for (String categoria : ruta.getCategorias()) {
                                                    %>
                                                    <span class="badge client-badge"><%= categoria %></span>
                                                    <%
                                                        }
                                                    %>
                                                </div>
                                                <span class="status-badge" style="background-color: #198754; max-height: 40px;"><%= ruta.getEstado() %></span>
                                            </div>
                                        </a>
                                        <%
                                	}
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
