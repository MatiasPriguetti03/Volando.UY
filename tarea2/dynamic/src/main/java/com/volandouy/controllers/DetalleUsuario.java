package com.volandouy.controllers;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTVuelo;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/perfil")
public class DetalleUsuario extends HttpServlet {
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
    public DetalleUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoExisteException {

        String nick = request.getParameter("id");
        if (nick == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        fabrica = Fabrica.getInstance();
        iUsuario = fabrica.getIUsuario();
        String tipoUsuarioConsultado = iUsuario.determinarTipoUsuario(nick);

        if (tipoUsuarioConsultado == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HttpSession session = request.getSession();
        DTUsuario datos = iUsuario.obtenerInfoUsuario(nick);
        request.setAttribute("datos", datos);

      
        if (tipoUsuarioConsultado.equals("aerolinea")) {
            processAerolineaRequest(request, response, nick);
        } else if (tipoUsuarioConsultado.equals("cliente")) {
            processClienteRequest(request, response, nick);
        }
    }

    private void processAerolineaRequest(HttpServletRequest request, HttpServletResponse response, String nickAerolinea)
            throws ServletException, IOException, NoExisteException {

        DTAerolinea datosAerolinea = iUsuario.obtenerDatosAerolinea(nickAerolinea);
        request.setAttribute("datos+", datosAerolinea);

        List<DTRutaVuelo> rutas = datosAerolinea.getRutasVuelo();
        request.setAttribute("rutas", rutas);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/usuario/detalle-aerolinea.jsp");
        dispatcher.forward(request, response);
    }

    private void processClienteRequest(HttpServletRequest request, HttpServletResponse response, String nickCliente)
            throws ServletException, IOException, NoExisteException {

        iVuelo = fabrica.getIVuelo();

        DTCliente datosCliente = iUsuario.obtenerDatosCliente(nickCliente);
        request.setAttribute("datos+", datosCliente);

        List<DTCompraPaquete> compras = iUsuario.obtenerComprasDePaquetes(nickCliente);
        request.setAttribute("compras", compras);

        List<DTReservaVuelo> reservas = iUsuario.obtenerReservasVueloCliente(nickCliente);
        request.setAttribute("reservas", reservas);

        Map<String, String> nombresVuelos = getNombresVuelos(reservas);
        request.setAttribute("vuelos", nombresVuelos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/usuario/detalle-cliente.jsp");
        dispatcher.forward(request, response);
    }

    private Map<String, String> getNombresVuelos(List<DTReservaVuelo> reservas) throws NoExisteException {
        Map<String, String> nombresVuelos = new HashMap<>();
        for (DTReservaVuelo reserva : reservas) {
            String idVuelo = reserva.getNameVuelo();
            DTVuelo vuelo = iVuelo.obtenerDTVuelo(idVuelo);
            DTInfoRutaVuelo infoRV = vuelo.getRutaVuelo();
            String nombreVuelo = infoRV.getOrigen() + " - " + infoRV.getDestino();
            nombresVuelos.put(idVuelo, nombreVuelo);
        }
        return nombresVuelos;
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
