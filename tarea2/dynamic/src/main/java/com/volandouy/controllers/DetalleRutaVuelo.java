package com.volandouy.controllers;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Vector;

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
import logica.ManejadorUsuario;

import logica.datatypes.DTAerolinea;
import logica.datatypes.DTCliente;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTVuelo;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/detalle-ruta-vuelo")
public class DetalleRutaVuelo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fabrica fabrica;
	private IVuelo iVuelo;
	private IUsuario iUsuario;
	private IPaquete iPaquete;
	private ManejadorUsuario manU;
	private String tipoUsuario;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetalleRutaVuelo() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoExisteException {
        

        fabrica = Fabrica.getInstance();
        
        iUsuario = fabrica.getIUsuario();
        iVuelo = fabrica.getIVuelo();
        
        String nombreRuta = request.getParameter("id");

        DTRutaVuelo ruta = iVuelo.obtenerDTRutaVuelo(nombreRuta);
    	request.setAttribute("ruta", ruta);
    	
    	
	    if (request.getParameter("id") != null && ruta != null) {
	        
	        List<DTPaquete> paquetes = iVuelo.obtenerPaquetesDeRutaVuelo(nombreRuta);
	    	request.setAttribute("paquetes", paquetes);
	        
	        String nickAerolinea = iUsuario.obtenerNickAerolineaDeRutaVuelo(nombreRuta);
	    	request.setAttribute("nick-aerolinea", nickAerolinea);    

	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ruta-vuelo/detalle-ruta-vuelo.jsp");
	    	dispatcher.forward(request, response);
	    } else {
	    	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    }
    	
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
		} catch (ServletException | IOException | NoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (ServletException | IOException | NoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
