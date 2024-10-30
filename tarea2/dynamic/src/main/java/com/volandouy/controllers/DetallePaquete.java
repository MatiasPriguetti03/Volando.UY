package com.volandouy.controllers;

import java.io.IOException;

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
import logica.datatypes.DTPaquete;
import logica.datatypes.DTUsuario;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/detalle-paquete")
public class DetallePaquete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fabrica fabrica;
	private IPaquete iPaquete;
	private IUsuario iUsuario;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetallePaquete() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoExisteException {
    	
    	fabrica = Fabrica.getInstance();
    	iPaquete = fabrica.getIPaquete();
    	iUsuario = fabrica.getIUsuario();
    	HttpSession session = request.getSession();
    	DTUsuario usr; 
    	
    	try {
    		usr = (DTUsuario) session.getAttribute("usuario_sesion");
    	}
    	catch (Exception e) {
    		usr = null; 
    	}
    	
    	if (usr != null && iUsuario.determinarTipoUsuario(usr.getNick()) == "cliente") { 
    		request.setAttribute("tipo-usuario", "cliente");
    	}
    	else {
    		request.setAttribute("tipo-usuario", "");
    	}
    		
    	String paquete = request.getParameter("detalle-de");
    	DTPaquete datos = iPaquete.obtenerDatosPaquete(paquete);
    	request.setAttribute("detalle-Paquete", datos);    	
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paquete/detalle-paquete.jsp");
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