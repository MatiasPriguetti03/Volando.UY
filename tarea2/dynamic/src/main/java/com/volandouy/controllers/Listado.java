package com.volandouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import excepciones.NoExisteException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IPaquete;
import logica.IUsuario;
import logica.IVuelo;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTVuelo;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/listado")
public class Listado extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUsuario iUsuario;
	private IVuelo iVuelo;
	private IPaquete iPaquete;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Listado() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoExisteException {
    	
    	if (request.getParameter("listar") != null) {
    		
    		HttpSession session = request.getSession();
    		DTUsuario usr;
    		
    		try {
        		usr = (DTUsuario) session.getAttribute("usuario_sesion");
        	}
        	catch (Exception e) {
        		usr = null; 
        	}
    		
    		request.setAttribute("listado-de", request.getParameter("listar"));
    		Fabrica fabrica = Fabrica.getInstance();
    		iVuelo = fabrica.getIVuelo();
    		iUsuario = fabrica.getIUsuario(); 
    		iPaquete = fabrica.getIPaquete();
    		
    		switch (request.getParameter("listar")) {
		    	case "Vuelos":		    		 
		    		request.setAttribute("listaVuelos", iVuelo.listarDTVuelosRC());
		    		request.setAttribute("listaRutas", iVuelo.listarDTRutasVueloConfirmadas());
		    		request.setAttribute("listaUsuarios", iUsuario.listarDTUsuarios("Aerolinea"));
		    		break;
		    	
		    	case "Rutas de Vuelo":	
		    		String categoria = request.getParameter("categoria");	
		    		String rutasPropias = request.getParameter("propias");
		    		
		    		if (rutasPropias != null && rutasPropias.equals("true")) {
		    			request.setAttribute("listaRutas", iVuelo.listarDTRutasVuelo(usr.getNick(), ""));
		    		} else if (request.getParameter("categoria") != null) {
		    			request.setAttribute("listaRutas", iVuelo.listarDTRutasVueloConfirmadas(categoria));
		    		}
		    		else {
		    			request.setAttribute("listaRutas", iVuelo.listarDTRutasVueloConfirmadas());
		    		}
		    		
		    		request.setAttribute("listaUsuarios", iUsuario.listarDTUsuarios("Aerolinea"));
		    		request.setAttribute("Categorias", iVuelo.listarCategorias());
		    		break;
		    	
		    	case "Usuarios":
		  
		    		if (request.getParameter("tipo-usuario") == null || request.getParameter("tipo-usuario") == "")
		    			request.setAttribute("listaUsuarios", iUsuario.listarDTUsuarios());
		    		else if (request.getParameter("tipo-usuario").equals("Cliente")) 
		    			request.setAttribute("listaUsuarios", iUsuario.listarDTUsuarios("Cliente"));
		    		else if (request.getParameter("tipo-usuario").equals("Aerolinea"))
		    			request.setAttribute("listaUsuarios", iUsuario.listarDTUsuarios("Aerolinea"));
		    		break;
		    	
		    	case "Paquetes":	
		    		request.setAttribute("listaPaquetes", iPaquete.listarDTPaquetes());
		    		break;
		    		
		    	case "Reservas":
		    		if (usr == null) {
		                response.sendError(HttpServletResponse.SC_FORBIDDEN);
		                return;
		    			
		    		} else {		    			
		    			request.setAttribute("listaReservas", iVuelo.listarDTReservasUsuario(usr.getNick()));
		    			request.setAttribute("listaUsuarios", iUsuario.listarDTUsuarios("Aerolinea"));
		    			request.setAttribute("listaRutas", iVuelo.listarDTRutasVueloConfirmadas());
		    			request.setAttribute("listaVuelos", iVuelo.listarDTVuelosRC());
		    			break;
		    			
		    		}
		    }
    	}
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listado/listado.jsp");
		dispatcher.forward(request, response);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		}
		catch (NoExisteException e) {
			// mostrar error
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		}
		catch (NoExisteException e) {
			// mostrar error
		}
	}
}
