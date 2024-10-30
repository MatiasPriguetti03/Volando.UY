package com.volandouy.controllers;

import java.io.IOException;

import com.volandouy.model.Datos;

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

import logica.datatypes.DTPaquete;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/detalle")
public class Detalle extends HttpServlet {
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
    public Detalle() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoExisteException {

    	if (request.getParameter("id") != null) {
    		fabrica = Fabrica.getInstance();
		} else {
			throw new NoExisteException("No existe el elemento solicitado");
		}
  
		if (request.getParameter("detalle") != null && request.getParameter("detalle").equals("ruta_vuelo")) {
			
			iVuelo = fabrica.getIVuelo();
			String ruta = request.getParameter("id");
			DTRutaVuelo datos = iVuelo.obtenerDTRutaVuelo(ruta);
			request.setAttribute("datos", datos);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ruta-vuelo/detalle-ruta-vuelo.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("detalle") != null && request.getParameter("detalle").equals("paquete")) {
			
			iPaquete = fabrica.getIPaquete();
			String paq = request.getParameter("id");
			DTPaquete datos = iPaquete.obtenerDatosPaquete(paq);
			request.setAttribute("datos", datos);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/paquete/detalle-paquete.jsp");
			dispatcher.forward(request, response);
			
		} else if (request.getParameter("detalle") != null && request.getParameter("detalle").equals("usuario")) {
			
			iUsuario = fabrica.getIUsuario();
			String usr = request.getParameter("id");
			DTUsuario datos = iUsuario.obtenerInfoUsuario(usr);
			request.setAttribute("datos", datos);

			tipoUsuario = iUsuario.determinarTipoUsuario(usr);
			request.setAttribute("tipo", tipoUsuario);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/usuario/detalle-usuario.jsp");
			dispatcher.forward(request, response);

		} else if (request.getParameter("detalle") != null && request.getParameter("detalle").equals("vuelo")) {

			iVuelo = fabrica.getIVuelo();
			String ruta = request.getParameter("id");
			DTRutaVuelo datos = iVuelo.obtenerDTRutaVuelo(ruta);
			request.setAttribute("datos", datos);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vuelo/detalle-vuelo.jsp");
			dispatcher.forward(request, response);
			
		} else if (request.getParameter("detalle") != null && request.getParameter("detalle").equals("reserva")) {

			iUsuario = fabrica.getIUsuario();
			String usr = request.getParameter("id");
			DTUsuario datos = iUsuario.obtenerInfoUsuario(usr);
			request.setAttribute("datos", datos);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reserva/detalle-reserva.jsp");
			dispatcher.forward(request, response);
			
		} else {
			throw new NoExisteException("El detalle solicitado no existe");
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
