package com.volandouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.volandouy.model.Datos;

import excepciones.EsConjuntoVacioException;
import excepciones.NoExisteException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logica.Fabrica;
import logica.IVuelo;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTVuelo;


/**
 * Servlet implementation class Home
 */
@WebServlet ("/detalle-vuelo")
public class DetalleVuelo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fabrica fabrica;
	private IVuelo iVuelo;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetalleVuelo() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoExisteException, EsConjuntoVacioException {

    	if (request.getParameter("vuelo") != null) {
    		fabrica = Fabrica.getInstance();
		} else {
			throw new NoExisteException("No existe el elemento solicitado");
		}
  

    	if (request.getParameter("vuelo") != null) {

			iVuelo = fabrica.getIVuelo();
			String vueloName = request.getParameter("vuelo"); // Se obtiene nombre del vuelo
			DTVuelo datos = iVuelo.obtenerDTVuelo(vueloName);
			request.setAttribute("datos", datos); // se pasa el DTVuelo
			
			// Se obtiene las reservas de un vuelo
			List<DTReservaVuelo> reservasVuelo = datos.getReservas();
			// se pasa el Set de DTReservaVuelo para autentificar si el cliente reservo el vuelo
			request.setAttribute("reservasVuelo", reservasVuelo);
			
			
			
			// se obtiene el usuario de sesion
			HttpSession session = request.getSession();
			DTUsuario user = (DTUsuario) session.getAttribute("usuario_sesion");
			String userType = (String) session.getAttribute("tipo_usuario_sesion");
			
			if (user != null && userType != null && userType.equals("aerolinea")) {
				Set<DTInfoRutaVuelo> rutas = iVuelo.listarRutasVuelosAerolinea(user.getNick());
				if (rutas != null) {
					List<DTRutaVuelo> rutasVuelo = new ArrayList<DTRutaVuelo>();
					rutas.forEach(r -> {
						DTRutaVuelo dtRuta = iVuelo.obtenerDTRutaVuelo(r.getName());
						rutasVuelo.add(dtRuta);
					});
					request.setAttribute("rutasVuelo", rutasVuelo);
				}
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vuelo/detalle-vuelo.jsp");
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
		} catch (ServletException | IOException | NoExisteException | EsConjuntoVacioException e) {
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
		} catch (ServletException | IOException | NoExisteException | EsConjuntoVacioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
