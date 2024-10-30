<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="logica.Fabrica"%>
<%@ page import="logica.IVuelo"%>
<%@ page import="logica.datatypes.DTVuelo"%>
<%@ page import="logica.datatypes.DTInfoRutaVuelo"%>
<%@ page import="logica.datatypes.DTRutaVuelo"%>
<%@ page import="logica.datatypes.DTUsuario"%>
<%@ page import="logica.datatypes.DTPaquete"%>
<%@ page import="logica.datatypes.DTReservaVuelo"%>
<%@ page import="logica.enums.EnumAsiento"%>
<%@ page import="utils.PresentacionUtils"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.format.DateTimeFormatter" %>


<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/head.jsp"></jsp:include>
<title>Volando.uy - Lista de <%=request.getAttribute("listado-de")%></title>
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
					<%
					String listadoDe = (String) request.getAttribute("listado-de");
					%>
					<h2 class="mb-4"><%=listadoDe%></h2>

					<!-- Lista de Items -->
					<%
					String imagen = "";
					String nombre = "";
					String descripcion = "";
					String etiqueta = "";
					String hrefDetalle = "";

					List<String> etiquetas = new ArrayList<>();
					List<DTVuelo> listaV = new ArrayList<>();
					List<DTRutaVuelo> listaR = new ArrayList<>();
					List<DTUsuario> listaU = new ArrayList<>();
					List<DTPaquete> listaP = new ArrayList<>();
					List<DTReservaVuelo> listaRV = new ArrayList<>();

					boolean esVacio = true;

					int cantItems = 0;

					switch (listadoDe) {
					case "Vuelos":
						listaV = (List<DTVuelo>) request.getAttribute("listaVuelos");
						if (listaV != null && listaV.size() != 0) {
							cantItems = listaV.size();
							esVacio = false;
						}
						break;

					case "Rutas de Vuelo":
						listaR = (List<DTRutaVuelo>) request.getAttribute("listaRutas");
						if (listaR != null && listaR.size() != 0) {
							cantItems = listaR.size();
							esVacio = false;
						}
						break;

					case "Usuarios":
						listaU = (List<DTUsuario>) request.getAttribute("listaUsuarios");
						if (listaU != null && listaU.size() != 0) {
							cantItems = listaU.size();
							esVacio = false;
						}
						break;

					case "Paquetes":
						listaP = (List<DTPaquete>) request.getAttribute("listaPaquetes");
						if (listaP != null && listaP.size() != 0) {
							cantItems = listaP.size();
							esVacio = false;
						}
						break;
					}
					if (!listadoDe.equals("Reservas") && !esVacio) {

						for (int i = 0; i < cantItems; i++) {
							switch (listadoDe) {
							case "Vuelos":
								DTInfoRutaVuelo rutaVuelo = listaV.get(i).getRutaVuelo();
								imagen = listaV.get(i).getImagen();
								nombre = rutaVuelo.getOrigen() + " - " + rutaVuelo.getDestino() + " (" + listaV.get(i).getName() + ")";
								etiqueta = listaV.get(i).getFechaVuelo().toString();
								hrefDetalle = "/volandouy/detalle-vuelo?vuelo=" + listaV.get(i).getName();
								break;

							case "Rutas de Vuelo":
								imagen = listaR.get(i).getImagen();
								nombre = listaR.get(i).getOrigen() + " - " + listaR.get(i).getDestino() + " (" + listaR.get(i).getName()
										+ ")";
								descripcion = listaR.get(i).getDescCorta();
								for (String categoria: listaR.get(i).getCategorias())
									etiquetas.add(categoria);
								
								hrefDetalle = "/volandouy/detalle-ruta-vuelo?id=" + listaR.get(i).getName();
								break;

							case "Usuarios":
								imagen = listaU.get(i).getImagen();
								nombre = listaU.get(i).getNombre() + " - (" + listaU.get(i).getNick() + ")";
								//etiqueta = (determinarTipoUsuario(listaU.get(i).getNick()) == "cliente") ? "Cliente" : "Aerolinea";
								hrefDetalle = "/volandouy/perfil?id=" + listaU.get(i).getNick();
								break;

							case "Paquetes":
								imagen = listaP.get(i).getImagen();
								nombre = listaP.get(i).getNombre();
								descripcion = listaP.get(i).getDescripcion();
								etiqueta = listaP.get(i).getDescuento().toString() + "% OFF";
								String refNombre = nombre.replace(" ", "%20");
								hrefDetalle = "/volandouy/detalle-paquete?detalle-de=" + refNombre;
								break;
							}
					%>
					<div class="card mb-4">
						<div class="row g-0">
							<div class="col-md-4">
								<a href=<%=hrefDetalle%> class="text-decoration-none" style="color: black"> <img
									src="img/<%=imagen%>" class="rounded-start index-imagen" alt="imagen"> 
										<%if (listadoDe.equals("Paquetes")) {%>
									<div class="descuento-etiqueta position-absolute"><%=etiqueta%></div> <%}%>
								</a>
							</div>
							<div class="col-md-8">
								<div class="card-body">
									<h5 class="card-title">
										<a href=<%=hrefDetalle%> class="text-decoration-none" style="color: black"><%=nombre%> </a>
									</h5>
									<%if (listadoDe.equals("Rutas de Vuelo") || listadoDe.equals("Paquetes")) {%>
									<!-- Descripcion -->
									<p class="card-text"><%=descripcion%></p>
									<%
										if (listadoDe.equals("Rutas de Vuelo")) {
											for (String etiquetaCategoria: etiquetas) {
											%>
												<span class="badge class-badge"><%=etiquetaCategoria%></span>
											<%}
											etiquetas.clear();
										}
									} else {
									%>
									<!-- Etiqueta -->
									<span class="badge client-badge"><%=etiqueta%></span>
									<%}%>
									<a href=<%=hrefDetalle%> class="btn btn-link p-0">Saber más</a>
								</div>
							</div>
						</div>
					</div>
					<%
					}
					} else if (listadoDe.equals("Reservas")) {
					%>
					<div class="window mb-4">
						<%
						Fabrica fabrica = Fabrica.getInstance();
						IVuelo iVuelo = fabrica.getIVuelo();

						listaRV = (List<DTReservaVuelo>) request.getAttribute("listaReservas");
						
						if (listaRV != null && listaRV.size() != 0) {
							cantItems = listaRV.size();						

							for (int i = 0; i < cantItems; i++) {
								DTVuelo vuelo = iVuelo.obtenerDTVuelo(listaRV.get(i).getNameVuelo());
								DTInfoRutaVuelo rVuelo = vuelo.getRutaVuelo();
	

							    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								descripcion = listaRV.get(i).getFecha().format(formatter);
								nombre =  rVuelo.getOrigen() + " - " + rVuelo.getDestino() + " (" + listaRV.get(i).getNameVuelo() + ")";
	
								etiqueta = listaRV.get(i).getEquipajeExtra() + " Equipaje Extra";
								String etiqueta2 = listaRV.get(i).getPasajes() + " Pasaje/s";
								String etiqueta3 = (listaRV.get(i).getTipoAsiento() == EnumAsiento.TURISTA) ? "Turista" : "Ejecutivo";
	

                                float costo = listaRV.get(i).getCosto();
                                String formattedCosto;
                                if (costo == Math.floor(costo)) {
                                    formattedCosto = String.valueOf((int) costo);
                                } else {
                                    formattedCosto = String.format("%.2f", costo);
                                }
								
								String costoStr = "US$ " + formattedCosto;
								String tipoReserva = listaRV.get(i).getTipoReserva();
	
								DTUsuario datosUsr = (DTUsuario) session.getAttribute("usuario_sesion");
															
								hrefDetalle = "/volandouy/detalle-reserva?id=" + listaRV.get(i).getId();
							%>
							<div class="flight-card" style="margin-bottom: 20px;">
								<div class="flight-header">
									<div class="flight-details" style="flex-direction: column; align-items: start;">
										<span class="flight-date"><%=descripcion%></span> <span class="flight-route" style="margin-bottom: 0.5rem;">
											<a href=<%=hrefDetalle%> class="text-decoration-none" style="color: #3498db;"> <%=nombre%></a>
										</span>
										<div class="flight-info">
											<span class="badge baggage-badge"><%=etiqueta%></span> <span class="badge ticket-badge"><%=etiqueta2%></span>
											<span class="badge class-badge"><%=etiqueta3%></span>
										</div>
									</div>
									<div class="d-flex" style="flex-direction: column; text-align: right;">
										<span class="flight-cost"><%= costoStr %></span> <span class=""><small><%=tipoReserva%></small></span>
									</div>
								</div>
							</div>
							<%
							}
						} else {	
						%>
						<h4>No tienes Reservas ingesadas</h4>
						</div>
						<% 
						}
					} else {
					%>
					<h4>
						Aun no hay
						<%=listadoDe%>
						ingresados
					</h4>
					<%
					}
					%>
				</div>
			</div>
		</div>
	</main>

	<!-- footer -->
	<jsp:include page="../templates/footer.jsp" />

</body>
</html>