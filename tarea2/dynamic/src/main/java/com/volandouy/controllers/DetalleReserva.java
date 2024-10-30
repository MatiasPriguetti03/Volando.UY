package com.volandouy.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import logica.datatypes.DTAerolinea;
import logica.datatypes.DTCliente;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVueloAerolinea;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTVuelo;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/detalle-reserva")
public class DetalleReserva extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fabrica fabrica;
	private IVuelo iVuelo;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetalleReserva() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoExisteException {

        HttpSession session = request.getSession();
        DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");

        if (loggedUser == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return; // Make sure to return after sending an error
        }

        if (request.getParameter("id") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return; // Same here
        }

        fabrica = Fabrica.getInstance();
        iVuelo = fabrica.getIVuelo();

        Integer idReserva = null;
        try {
            idReserva = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return; // Ensure you return after sending an error
        }

        String idUsuario = loggedUser.getNick();
        
        // Initialize the list correctly
        List<DTReservaVuelo> reservasUsuario = new ArrayList<>(); // Use ArrayList or appropriate implementation
        try {
            reservasUsuario = iVuelo.listarDTReservasUsuario(idUsuario); // Use the list returned by the method
        } catch (NoExisteException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return; // Ensure you return after sending an error
        }
        
        
        DTReservaVuelo reservaSeleccionada = null;
        String nombreAerolinea = "";
        // Look for the reservation by id
        for (DTReservaVuelo reserva : reservasUsuario) {
            if (reserva.getId() == idReserva) { // Use equals for object comparison
            	reservaSeleccionada = reserva;
            	nombreAerolinea = iVuelo.obtenerDTRutaVueloAerolinea(reserva.getRutaVuelo().getName()).getNombreAerolinea();
                break;
            }
        }

        if (reservaSeleccionada == null) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN);        	
        } else {
            request.setAttribute("reserva", reservaSeleccionada);
            request.setAttribute("nombre-aerolinea", nombreAerolinea);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reserva//detalle-reserva.jsp");
        	dispatcher.forward(request, response);        	
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
