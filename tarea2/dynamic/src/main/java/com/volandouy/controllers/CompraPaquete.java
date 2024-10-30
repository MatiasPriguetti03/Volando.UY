package com.volandouy.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.volandouy.model.Datos;

import excepciones.NoExisteException;
import excepciones.YaRegistradoException;
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
import logica.ManejadorUsuario;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTPaquete;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/compra-paquete")
public class CompraPaquete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUsuario iUsuario;
	private IPaquete iPaquete;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompraPaquete() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequestPOST(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	Fabrica fabrica = Fabrica.getInstance();
    	iUsuario = fabrica.getIUsuario();
    	iPaquete = fabrica.getIPaquete();
    	HttpSession session = request.getSession();
    	DTUsuario usr; 
    	
    	try {
    		usr = (DTUsuario) session.getAttribute("usuario_sesion");
    	}
    	catch (Exception e) {
    		usr = null; 
    	}
    	
    	if (usr != null && "cliente" == iUsuario.determinarTipoUsuario(usr.getNick()) ) {
    		String nombrePaquete = request.getParameter("compraPaquete");
    		DTPaquete paquete;
    		try {
    			paquete = iPaquete.obtenerDatosPaquete(nombrePaquete); 
    			request.setAttribute("datos_paquete", paquete);
    		}
    		catch (NoExisteException e){
    			paquete = null;
    		}
    		
    		try {
	    		if (paquete != null && iUsuario.clienteComproPaquete(usr.getNick(), paquete.getNombre()))
	    			request.setAttribute("compro_paquete", true);
	    		else {
	    			request.setAttribute("compro_paquete", false);
	    			
	    			LocalDate fechaDeCompra = LocalDate.now();
	    			try {
		    			iPaquete.comprarPaquete(usr.getNick(), fechaDeCompra, paquete.getNombre());
		    	        response.sendRedirect("home");
		    	        
	    			}
	    			catch (YaRegistradoException e) {
	    				// Muestro error	    		
		    		}
	    		}
    		}
    		catch (NoExisteException e){
    			// mostrar error, paquete o cliente no existe	
    		}
    	}
	}
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	Fabrica fabrica = Fabrica.getInstance();
    	iUsuario = fabrica.getIUsuario();
    	iPaquete = fabrica.getIPaquete();
    	HttpSession session = request.getSession();
    	DTUsuario usr; 
    	
    	try {
    		usr = (DTUsuario) session.getAttribute("usuario_sesion");
    	}
    	catch (Exception e) {
    		// Mostrar pagina de error
    		usr = null; 
    	}
    	
    	if (usr != null && "cliente" == iUsuario.determinarTipoUsuario(usr.getNick()) ) {
    		String nombrePaquete = request.getParameter("compraPaquete");
    		DTPaquete paquete;
    		
    		try {
    			paquete = iPaquete.obtenerDatosPaquete(nombrePaquete); 
    			request.setAttribute("datos_paquete", paquete);
    		}
    		catch (NoExisteException e){
    			paquete = null;
    		}
    		
    		try {
	    		if (paquete != null && iUsuario.clienteComproPaquete(usr.getNick(), paquete.getNombre()))
	    			request.setAttribute("compro_paquete", true);
	    		else 
	    			request.setAttribute("compro_paquete", false);
    		}
    		catch (NoExisteException e){
    			// Mostrar pagina de error
    		}
    		
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paquete/compra-paquete.jsp");
    		dispatcher.forward(request, response);
    	}
    	else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequestPOST(request, response);
	}

}
