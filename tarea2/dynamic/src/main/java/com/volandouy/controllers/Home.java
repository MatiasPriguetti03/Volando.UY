package com.volandouy.controllers;

import java.io.IOException;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import excepciones.EsConjuntoVacioException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTRutaVuelo;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fabrica fabrica;
	private IVuelo iVuelo;
	private IUsuario iUsuario;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, EsConjuntoVacioException {
    	// Obtiene las rutas de vuelo para listarlas
    	fabrica = fabrica.getInstance();
    	iVuelo = fabrica.getIVuelo();
    	iUsuario = fabrica.getIUsuario();
    	
    	Set<DTRutaVuelo> rutas = iVuelo.listarRutasVuelos();
    	Map<String, String> nombreAerolineas = new HashMap<String, String>();
    	String[] categorias = iVuelo.listarCategorias();
    	
    	
    	if (rutas != null) {    		
    		
			for (DTRutaVuelo ruta : rutas) {
				nombreAerolineas.put(ruta.getName(), iUsuario.obtenerNickAerolineaDeRutaVuelo(ruta.getName()));
			}
	    	
			req.setAttribute("nombreAerolineas", nombreAerolineas);
	    	req.setAttribute("rutas", rutas);
    	}
		// Inicializa la sesión si no estaba creada
		HttpSession session = req.getSession();
		session.setAttribute("categorias", categorias);
		
		if (req.getParameter("cerrar-sesion") != null && req.getParameter("cerrar-sesion").equals("true")) {
			session.removeAttribute("usuario_sesion");
			session.removeAttribute("tipo_usuario_sesion");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/home/index.jsp");
			dispatcher.forward(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/home/index.jsp").forward(req, resp);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | EsConjuntoVacioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | EsConjuntoVacioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
