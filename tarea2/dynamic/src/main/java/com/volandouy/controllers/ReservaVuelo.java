package com.volandouy.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import excepciones.AccesoProhibidoException;
import excepciones.NoExisteException;
import excepciones.OperacionInvalidaException;
import excepciones.YaRegistradoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IVuelo;
import logica.IUsuario;
import logica.datatypes.DTVuelo;
import logica.enums.EnumAsiento;
import logica.datatypes.DTCliente;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTPasajero;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/reserva-vuelo")
public class ReservaVuelo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservaVuelo() {
        super();
        // TODO Auto-generated constructor stub
    }

    private boolean verificarAcceso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	boolean acceso = false;
    	try {
    		// Chequear si existe parametro
    		String nombre_vuelo = req.getParameter("vuelo");    		
    		if (nombre_vuelo == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return false;
    		}
   
    		// Chequear si es cliente el que accede
        	HttpSession session = req.getSession();
        	String tipoUsr = (String) session.getAttribute("tipo_usuario_sesion");
        	if (!tipoUsr.equals("cliente")) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        	}
        	acceso = true;
    	} catch (Exception ex) {
    		// Otro error
    		resp.sendError(HttpServletResponse.SC_FORBIDDEN);
    	}
    	return acceso;
	}
    
    private void cargarAtributosyEnviarRequest(HttpServletRequest req, HttpServletResponse resp, String nombreVuelo) throws ServletException, IOException {
		// Sesion
		HttpSession session = req.getSession();
		
    	Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		DTVuelo vuelo = iVuelo.obtenerDTVuelo(nombreVuelo);
		// Debo chequear aun que exista el vuelo
		if (vuelo == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {			
			req.setAttribute("datos_vuelo", vuelo);
			// Obtener ruta del vuelo
			DTRutaVuelo ruta = iVuelo.obtenerDTRutaVuelo(vuelo.getRutaVuelo().getName());
			if (ruta == null) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else {				
				req.setAttribute("datos_ruta_del_vuelo", ruta);
				IUsuario iUsuario = fabrica.getIUsuario();
				DTUsuario usrAerolinea = iUsuario.obtenerInfoUsuario(iUsuario.obtenerNickAerolineaDeRutaVuelo(ruta.getName()));
				if (usrAerolinea == null) {
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} else {
					req.setAttribute("datos_aerolinea_del_vuelo", usrAerolinea);
					// Obtengo cliente
					DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
					List<DTCompraPaquete> paquetes = iUsuario.obtenerComprasDePaquetes(usr.getNick());
					List<DTCompraPaquete> paquetesValidos = new ArrayList<DTCompraPaquete>(); 
					for (DTCompraPaquete compra : paquetes) {
						if (compra.getFechaVenc().isAfter(LocalDate.now()) && 
							compra.getUsosRestantes().containsKey(ruta.getName()) &&
							compra.getUsosRestantes().get(ruta.getName()) > 0) {
							paquetesValidos.add(compra);
						}
					}
					req.setAttribute("datos_paquetes", paquetesValidos);
					req.getRequestDispatcher("/WEB-INF/reserva/reserva-vuelo.jsp").forward(req, resp);
				}
			}
		}
    }
    
	private void processRequestGET(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Ya verifique que parametro existe en validar acceso
		String nombreVuelo = req.getParameter("vuelo");
		cargarAtributosyEnviarRequest(req, resp, nombreVuelo);
	}
	
	private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    	HttpSession session = req.getSession();
    	
    	Fabrica fabrica = Fabrica.getInstance();
    	IUsuario iUsuario = fabrica.getIUsuario();
    	IVuelo iVuelo = fabrica.getIVuelo();

    	DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
    	DTCliente cliente = iUsuario.obtenerDatosCliente(usr.getNick());

    	String nombreVuelo = req.getParameter("nombreVuelo");
    	
    	String tipoAsiento = req.getParameter("tipoAsiento");
    	EnumAsiento asiento = EnumAsiento.EJECUTIVO;
    	if (tipoAsiento.equals("turista")) {
    		asiento = EnumAsiento.TURISTA;
    	}
    	Integer cantEquipajeExtra = Integer.parseInt(req.getParameter("cantEquipajeExtra"));
    	Integer cantPasajeros = Integer.parseInt(req.getParameter("cantPasajeros"));
    	
    	List<DTPasajero> pasajeros = new ArrayList<DTPasajero>();
    	String nombreP = usr.getNombre();
    	String apellidoP = cliente.getApellido();
    	DTPasajero pasajero = new DTPasajero(nombreP, apellidoP);
    	pasajeros.add(pasajero);
    	for (int i = 1; i < cantPasajeros ; i++) {
    		nombreP = req.getParameter("nombre" + i);
    		apellidoP = req.getParameter("apellido" + i);
    		pasajero = new DTPasajero(nombreP, apellidoP);
    		pasajeros.add(pasajero);
    	}
    	
    	String paquete = req.getParameter("paqueteReserva");
    	
    	if (paquete.equals("")) {
    		try {
    		iVuelo.crearReserva(nombreVuelo, usr.getNick(), asiento, pasajeros, cantEquipajeExtra, LocalDate.now());
    		resp.sendRedirect("home");
    		} catch (YaRegistradoException ex) {
    			req.setAttribute("error", ex.getMessage());
    			cargarAtributosyEnviarRequest(req, resp, nombreVuelo);
    		}
    	} else {
    		try {
        		iVuelo.crearReserva(nombreVuelo, usr.getNick(), asiento, pasajeros, cantEquipajeExtra, LocalDate.now(), paquete);
        		resp.sendRedirect("home");
    		} catch (YaRegistradoException ex) {
    			req.setAttribute("error", ex.getMessage());
    			cargarAtributosyEnviarRequest(req, resp, nombreVuelo);
    		} catch (NoExisteException nex) {
    			req.setAttribute("error", nex.getMessage());
    			cargarAtributosyEnviarRequest(req, resp, nombreVuelo);
    		} catch (OperacionInvalidaException oex) {
    			req.setAttribute("error", oex.getMessage());
    			cargarAtributosyEnviarRequest(req, resp, nombreVuelo);
    		}
    	}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (verificarAcceso(request, response)) {			
			processRequestGET(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequestPOST(request, response);
	}

}