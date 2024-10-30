package com.volandouy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import excepciones.AccesoProhibidoException;
import excepciones.YaRegistradoException;
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
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;


/**
 * Servlet implementation class Home
 */
@WebServlet ("/alta-vuelo")
@MultipartConfig
public class AltaVuelo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaVuelo() {
        super();
        // TODO Auto-generated constructor stub
    }
  
    private boolean verificarAcceso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	boolean acceso = false;
    	try {
    		// Chequear si existe parametro
    		String nombre_ruta = req.getParameter("ruta_vuelo");
    	
    		// Chequear si ruta vuelo es de aerolinea
        	HttpSession session = req.getSession();
    		Fabrica fabrica = Fabrica.getInstance();
        	IUsuario iUsuario = fabrica.getIUsuario();
        	DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
        	if (usr == null) {
        		throw new AccesoProhibidoException();
        	}
        	// Este control incluye si existe la ruta de vuelo y si pertenece a la aerolinea
        	if (!iUsuario.obtenerNickAerolineaDeRutaVuelo(nombre_ruta).equals(usr.getNick())) {
        		throw new AccesoProhibidoException();
        	}
        	acceso = true;
    	}
    	catch (NullPointerException ex) {
    		// No existe parametro
    		resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 		
    	}
    	catch (AccesoProhibidoException ex) {
    		// No es cliente el que accede
    		resp.sendError(HttpServletResponse.SC_FORBIDDEN);
    	}
    	catch (Exception ex) {
    		// Otro error
    		resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	}
    	return acceso;
	}
    
	private void processRequestGET(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Ya verifique que parametro sea correcto
		String nombre_ruta = req.getParameter("ruta_vuelo");
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		DTRutaVuelo ruta = iVuelo.obtenerDTRutaVuelo(nombre_ruta);
		// Debo chequear aun que exista la ruta de vuelo
		if (ruta == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {			
			req.setAttribute("datos_ruta", ruta);
			req.getRequestDispatcher("/WEB-INF/vuelo/alta-vuelo.jsp").forward(req, resp);
		}
	}
	
	private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	Fabrica fabrica = Fabrica.getInstance();
    	IVuelo iVuelo = fabrica.getIVuelo();

    	String nombreRuta = req.getParameter("nombreRuta");
		String nombreVuelo = req.getParameter("nombreVuelo");
		LocalDate fechaVuelo = LocalDate.parse(req.getParameter("fechaVuelo"));
		Integer nHrs = Integer.parseInt(req.getParameter("duracionHoras"));
		String hrs = String.format("%02d", nHrs);
		Integer nMin = Integer.parseInt(req.getParameter("duracionMinutos"));
		String min = String.format("%02d", nMin);
		LocalTime duracion = LocalTime.parse(hrs + ":" + min, DateTimeFormatter.ofPattern("HH:mm"));
		Integer asientosTu = Integer.parseInt(req.getParameter("asientosTurista"));
		Integer asientosEj = Integer.parseInt(req.getParameter("asientosEjecutivo"));

		// manejo de la imagen
    	Part filePart = req.getPart("imagenVuelo"); // Obtener el archivo subido
    	String newFileName = null;
    	if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Nombre del archivo
            InputStream fileContent = filePart.getInputStream(); // Contenido del archivo
            
         // Agregar un timestamp al nombre del archivo
            newFileName = System.currentTimeMillis() + "_" + fileName;

            // Guardar el archivo
            String uploadPath = getServletContext().getRealPath("/img"); // Ruta relativa a la carpeta "uploads" dentro de la aplicación
            Path uploadDir = Paths.get(uploadPath); // Seleccionar carpeta de subidas
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Files.copy(fileContent, uploadDir.resolve(newFileName));
        }
    	if (newFileName == null) {
    		newFileName = "VU.jpg";
    	}

    	// No hago control sobre los datos porque esto se hace en el frontend
		try {
			iVuelo.agregarVueloARuta(newFileName, nombreRuta, nombreVuelo, fechaVuelo, duracion, asientosEj, asientosTu, LocalDate.now());
			// Si lo logra agrega DTUsuario a sesion y redirige a index
			//Redirect
			resp.sendRedirect("home");
		}
		//
		// Especificar excepciones
		catch (YaRegistradoException ex) {
			// Envio mensaje de error
			req.setAttribute("error", ex.getMessage());
			
			DTRutaVuelo ruta = iVuelo.obtenerDTRutaVuelo(nombreRuta);
			req.setAttribute("datos_ruta", ruta);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/vuelo/alta-vuelo.jsp");
			dispatcher.forward(req, resp);
		}
		catch (Exception ex) {
			// Si error inesperado retorno error interno
			req.setAttribute("error", "Error interno");
			
			DTRutaVuelo ruta = iVuelo.obtenerDTRutaVuelo(nombreRuta);
			req.setAttribute("datos_ruta", ruta);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/vuelo/alta-vuelo.jsp");
			dispatcher.forward(req, resp);
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
