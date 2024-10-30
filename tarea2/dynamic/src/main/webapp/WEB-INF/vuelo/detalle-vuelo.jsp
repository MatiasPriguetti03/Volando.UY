<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="logica.datatypes.DTVuelo"%>
<%@ page import="logica.datatypes.DTUsuario"%>
<%@ page import="logica.datatypes.DTInfoRutaVuelo"%>
<%@ page import="logica.datatypes.DTRutaVuelo"%>
<%@ page import="logica.datatypes.DTReservaVuelo"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="es">

<head>
	<jsp:include page="../templates/head.jsp"/>
    <title>Volando.uy - detalle vuelo</title>
</head>

<body class="d-flex flex-column min-vh-100 bg-light">
    <main class="container-fluid p-0 mb-4">
        <jsp:include page="../templates/header.jsp"/>

        <jsp:include page="../templates/sub-header.jsp" />
                 
        
        <div class="container mt-4 mb-4">
            <div class="row">
				<jsp:include page="../templates/menu-izquierda.jsp" />
			
			<%
			DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
			String tipoUsr = (String) session.getAttribute("tipo_usuario_sesion");
			
			DTVuelo vuelo = (DTVuelo) request.getAttribute("datos");
			List<DTReservaVuelo> reservasVuelo =	(List<DTReservaVuelo>) request.getAttribute("reservasVuelo");
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");    
			
			DTInfoRutaVuelo infoRutaVuelo = vuelo.getRutaVuelo();
			%>
				<div class="col-lg-9">
	               <div class="card mb-4">
	                   <div class="row g-0">
	                   	   <%
	                   	   if (vuelo.getImagen() != null) {
	                   	   %>
	                       <div class="col-md-4">
	                           <img src="img/<%=vuelo.getImagen()%>" class="img-fluid rounded-start" >
	                       </div>
	                       <%
	                   	   }
	                       %>
	                       <div class="col-md-8">
	                           <div class="card-body">
	                               <h3 class="card-title h5 fw-bold" style="padding-bottom: 1rem;">
	                                   <%=vuelo.getName()%></h3>
	                               <p class="card-text"><b>Ruta de Vuelo:</b> <%= infoRutaVuelo.getOrigen() %> - <%= infoRutaVuelo.getDestino() %></p>
	                               <p class="card-text"><b>Fecha: </b><%=vuelo.getFechaVuelo().format(formatter)%></p>
	                               <p class="card-text"><b>Duración: </b><%=vuelo.getDuracion()%></p>
	                               <p class="card-text"><b>Capacidad Turista: </b> <%=vuelo.getAsientoxMaxTurista()%></p>
	                               <p class="card-text"><b>Capacidad Ejecutivo:</b> <%=vuelo.getAsientosMaxEjecutivo()%></p>
	                           </div>
	                       </div>
	                      </div>
	                  </div>
	              
	              
            	<%
             	if (tipoUsr != null && tipoUsr == "aerolinea") {
             		
             		List<DTRutaVuelo> rutasVuelo = (List<DTRutaVuelo>) request.getAttribute("rutasVuelo");
             		
             		boolean isOwner = false;
             		if (rutasVuelo != null){
	             		for (DTRutaVuelo dtRuta : rutasVuelo){
	             			if (dtRuta.getName().equals(vuelo.getRutaVuelo().getName())){
	             				isOwner = true;
	             				break;
	             			}
	             		}
             		}
             		
             		if (isOwner){
             				
             	%>	
             	
             	<div class="window mb-4">
                        <h3>Reservas</h3>
                         <%
                         for (DTReservaVuelo resVue : reservasVuelo){
                       	
                       		if (resVue != null){
                       			
                       		
                       	%>

                        <a href="#" style="text-decoration: none; color: inherit;">
                            <div class="reservation-card mb-4">
                                <div class="reservation-header" style="background-color: #f9f9f9;">
                                    <div class="user-info">
                                        <p><%=resVue.getNickUser()%></p>
                                    </div>
                                    <div class="reservation-date-cost" style="display: block; line-height: 1;">
                                        <p style="color: #666; margin-bottom: 5px; text-align: right;"><%=resVue.getFecha()%></p>
                                        <p class="flight-cost" style="margin-bottom: 0;">USD<%=resVue.getCosto()%></p>
                                    </div>
                                </div>
                                <div class="pt-3 pb-4 px-4">
                                    <div class="reservation-badges">
                                        <span class="badge baggage-badge"><%=resVue.getEquipajeExtra()%> Equipajes Extra</span>
                                        <span class="badge ticket-badge"><%=resVue.getPasajes()%> Pasajes</span>
                                        <span class="badge class-badge"><%=resVue.getTipoAsiento()%></span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>


	           	<%
                       		}
                       	}
             		}
	              } else if (tipoUsr != null && tipoUsr.equals("cliente")){
	            	  boolean tieneReserva = false;
	            	  for(DTReservaVuelo resVue : reservasVuelo){
	            		  if (resVue != null && resVue.getNameUser() == usr.getNick()){
	            			  tieneReserva = true;
	            			  break;
	            		  }
	            	  
	            	  
	            	  
	            	  if (tieneReserva) {
	            %>
		            <div class="flight-container">
	                        <h3>Mis Reservas de Vuelo</h3>
	                        <a href="#" class="text-decoration-none">
	                            <div class="flight-card">
	                                <div class="flight-header">
	                                    <div class="flight-details" style="flex-direction: column; align-items: start;">
	                                        <span class="flight-date"><%= resVue.getFecha()%></span>
	                                        <span class="flight-route" style="margin-bottom: 0.5rem;">
	                                            <%= resVue.getNameVuelo() %>
	                                        </span>
	                                        <div class="flight-info">
	                                            <span class="badge baggage-badge">
	                                                <%= resVue.getEquipajeExtra() %> Equipaje extra
	                                            </span>
	                                            <span class="badge ticket-badge">
	                                                <%=resVue.getPasajes()%> Pasajes
	                                            </span>
	                                            <span class="badge class-badge">
	                                                <%= resVue.getTipoAsiento() %>
	                                            </span>
	                                        </div>
	                                    </div>
	                                    <div class="d-flex" style="flex-direction: column; text-align: right;">
	                                        <span class="flight-cost">USD<%= resVue.getCosto() %></span>
	                                        <span class=""><small><%= resVue.getTipoReserva() %></small></span>
	                                    </div>
	                                </div>
	                            </div>
	                        </a>    
	                    </div>
	            
	            <%
	            	  	
	            	  }
	              }
	            %>

	            
	            <div class="text-center mt-4">
                     <a href="reserva-vuelo?vuelo=<%=vuelo.getName() %>" class="btn btn-primary btn-lg w-20">
                         <i class="bi bi-cart-check me-2"></i>Reservar Vuelo
                     </a>
                </div>

	            <%
	              }
	            %>
	            </div>
			</div>
		</div>
	</main>


    <jsp:include page="../templates/footer.jsp"/>

</body>

</html>