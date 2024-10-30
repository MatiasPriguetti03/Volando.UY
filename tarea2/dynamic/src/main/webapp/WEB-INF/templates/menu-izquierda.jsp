<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="col-md-3">
<%
String tipoUsr = (String) session.getAttribute("tipo_usuario_sesion");
if (tipoUsr != null) {
%>
	<!-- Botones de acción -->
	<div class="d-grid gap-3 mb-4">
		<%
		if (tipoUsr.equals("cliente")) {
		%>
		<!-- Cliente -->

		<a href="/volandouy/listado?listar=Vuelos" class="btn btn-success btn-lg text-decoration-none" style="color: white;"> <i
			class="bi bi-calendar-check me-2"></i>Realizar Reserva
<!-- 		<a href="/volandouy/listado?listar=Usuarios&tipo-usuario=aerolinea" class="btn btn-success btn-lg text-decoration-none" style="color: white;">  -->
<!-- 			<i class="bi bi-calendar-check me-2"></i>Realizar Reserva -->
		</a> 
		<a href="/volandouy/listado?listar=Paquetes" class="btn btn-orange btn-lg text-decoration-none" style="color: white;"> <i
			class="bi bi-star me-2"></i>Comprar Paquete
		</a>
		<%
		} else {
		%>
		<!-- Aerolinea -->
		<!-- Cambiar a Listado -->
		<a href="/volandouy/listado?listar=Rutas de Vuelo&propias=true" class="btn btn-primary btn-lg text-decoration-none" style="color: white;"> <i
			class="bi bi-airplane me-2"></i>Crear Vuelo
		</a> 
		<a href="/volandouy/alta-ruta-vuelo" class="btn btn-danger btn-lg text-decoration-none" style="color: white;"> <i
			class="bi bi-arrow-down-up me-2"></i>Crear Ruta de Vuelo
		</a>
		<%
		}
		%>
		<!-- General -->
		<a href="/volandouy/listado?listar=Reservas" class="btn btn-purple btn-lg text-decoration-none" style="color: white;"> <i
			class="bi bi-search me-2"></i>Consultar Reserva
		</a>
	</div>
	<%
	}
	%>
	<!-- Categorias -->
	<div class="card">
		<div class="card-header">
			<b>Categorías</b>
		</div>
		<ul class="list-group list-group-flush">
			<%
			String[] categorias = (String[]) session.getAttribute("categorias");
			if (categorias == null || categorias.length == 0) {
			%>
			<li class="list-group-item">Aún no hay Categorías ingesadas</li>
			<%
			} else {			
				int cantCat = categorias.length;

				for (int i = 0; i < cantCat; i++) {
					%>
					<li class="list-group-item">
					<a href="/volandouy/listado?listar=Rutas de Vuelo&categoria=<%=categorias[i]%>" 
						class="text-decoration-none text-muted enlace-hover"><%=categorias[i]%></a>
					</li>
					<%
				}
			}
			%>
		</ul>
	</div>
</div>