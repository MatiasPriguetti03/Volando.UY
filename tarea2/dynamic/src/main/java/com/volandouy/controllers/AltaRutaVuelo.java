package com.volandouy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import excepciones.NoExisteException;
import excepciones.YaRegistradoException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logica.Categoria;
import logica.Fabrica;

import logica.datatypes.DTPaquete;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;
import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import logica.ManejadorUsuario;
import logica.ManejadorVuelo;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTPaquete;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/alta-ruta-vuelo")
@MultipartConfig
public class AltaRutaVuelo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUsuario iUsuario;
	private ManejadorUsuario manU;
	private IVuelo iVuelo;
	private ManejadorVuelo manV;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaRutaVuelo() {
        super();
        // TODO Auto-generated constructor stub
    }

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	Fabrica fabrica = Fabrica.getInstance();
    	manU = ManejadorUsuario.getInstancia();
    	iUsuario = fabrica.getIUsuario();
    	iVuelo = fabrica.getIVuelo();
    	
    	HttpSession session = request.getSession();
    	DTUsuario usr;
    	
    	try {
    		usr = (DTUsuario) session.getAttribute("usuario_sesion");
    	}
    	catch (Exception e) {
    		usr = null; 
    	}
    	
    	if (usr != null && "aerolinea" == iUsuario.determinarTipoUsuario(usr.getNick()) ) {
    		request.setAttribute("ciudades", iVuelo.listarCiudades());
    		
    		request.getRequestDispatcher("/WEB-INF/ruta-vuelo/alta-ruta-vuelo.jsp").
    		forward(request, response);
    	}
    	else { 		
    		response.sendRedirect("home");
    	}
	}
	
	private void processRequestPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fabrica fabrica = Fabrica.getInstance();
    	manU = ManejadorUsuario.getInstancia();
    	iUsuario = fabrica.getIUsuario();
    	manV = ManejadorVuelo.getInstancia();
    	iVuelo = fabrica.getIVuelo();
    	HttpSession session = request.getSession();
    	DTUsuario usr;
    	
    	
    	try {
    		usr = (DTUsuario) session.getAttribute("usuario_sesion");
    		request.setAttribute("ciudades", iVuelo.listarCiudades());
    	}
    	catch (Exception e) {
    		usr = null; 
    	}
    	
    	String name = request.getParameter("nombreRuta");
		String descripcionCorta = request.getParameter("descripcionCorta");
		String descripcion = request.getParameter("descripcion");
		String strHora = request.getParameter("hora");
		LocalTime hora = LocalTime.parse(strHora);
    	LocalDate fechaAlta = LocalDate.now();
		float costoTur = Float.parseFloat(request.getParameter("costoTurista"));
		float costoEje = Float.parseFloat(request.getParameter("costoEjecutivo"));
		float costoEquipajeExtra = Float.parseFloat(request.getParameter("costoEquipajeExtra"));
		
		String[] arrOrigen  = ((String) request.getParameter("ciudadOrigen")).split(", ", 2);
		String ciudadOrigen = arrOrigen[0];
		String paisOrigen = arrOrigen[1];
		
		String[] arrDestino  = ((String) request.getParameter("ciudadDestino")).split(", ", 2);
		String ciudadDestino = arrDestino[0];
		String paisDestino = arrDestino[1];
    	
    	Part filePart = request.getPart("imagenRuta"); // Obtener el archivo subido
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
    	
    	Map<String, Categoria> categorias = new HashMap<String, Categoria>();
	    String[] strCategorias = request.getParameterValues("categorias[]");
	    if (strCategorias != null) {
	    	for(String cat: strCategorias) {
	    		Categoria newCat = new Categoria(cat);
	    		categorias.put(cat, newCat);
	    	}
    	}
	    
    	// No hago control sobre los datos porque esto se hace en el frontend
    	try {
    		iVuelo.ingresarDatosRutaVuelo(usr.getNick(), name, newFileName, descripcion, 
        			descripcionCorta, hora, fechaAlta, costoEquipajeExtra, costoEje, 
        			costoTur, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
    		
    		//Redirect
    		response.sendRedirect("home");
    	}
    	//
    	// Especificar excepciones
    	catch (YaRegistradoException ex) {
    		// Envio mensaje de error
    		request.setAttribute("error", ex.getMessage());
    		
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ruta-vuelo/alta-ruta-vuelo.jsp");
    		dispatcher.forward(request, response);
    	}
    	catch (Exception ex) {
    		request.setAttribute("error", "Error interno");
    		
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ruta-vuelo/alta-ruta-vuelo.jsp");
    		dispatcher.forward(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequestPOST(request, response);
	}
}


