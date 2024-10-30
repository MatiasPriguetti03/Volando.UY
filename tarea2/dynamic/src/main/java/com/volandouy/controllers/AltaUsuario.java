package com.volandouy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

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
import logica.datatypes.DTUsuario;
import logica.enums.EnumDoc;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;


/**
 * Servlet implementation class Home
 */
@WebServlet ("/alta-usuario")
@MultipartConfig
public class AltaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }
  
    private boolean verificarAcceso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	boolean acceso = false;
    	try {
    		// Chequear si ya hay usuario logueado
        	HttpSession session = req.getSession();
        	DTUsuario usr = (DTUsuario) session.getAttribute("usuario_sesion");
        	if (usr == null) throw new NullPointerException();
        	resp.sendRedirect("home");    		
    	}
    	catch (NullPointerException ex) {
    		// No hay usuario logueado
    		acceso = true;
    	}
    	catch (Exception ex) {
    		// Otra excepcion, vuelvo al home
    		resp.sendRedirect("home");
    	}
    	return acceso;
	}
    
	private void processRequestGET(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/usuario/alta-usuario.jsp").forward(req, resp);
	}
	
	private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	HttpSession session = req.getSession();

		Fabrica fabrica = Fabrica.getInstance();
    	IUsuario iUsuario = fabrica.getIUsuario();
    	
		// No hago control sobre los datos porque esto se hace en el frontend
		try {
			String tipoUsr = req.getParameter("tipoUsrRadio");
			
			String nickname = req.getParameter("nick");
			String contrasenia = req.getParameter("contrasenia");
			String email = req.getParameter("mail");
			String nombre = req.getParameter("nombre");
			// Imagen
			Part filePart = req.getPart("imagen"); // Obtener el archivo subido
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
			
			if (tipoUsr.equals("cliente")) {
				String nacionalidad = req.getParameter("nacionalidad");
				String tipoDocStr = req.getParameter("tipoDoc");
				EnumDoc tipoDoc;
				if (tipoDocStr.equals("ci"))
					tipoDoc = EnumDoc.CEDULA;
				else
					tipoDoc = EnumDoc.PASAPORTE;
				String documento = req.getParameter("documento");
				String apellido = req.getParameter("apellido");
				LocalDate fechaNac = LocalDate.parse(req.getParameter("nacimiento"));
				
				iUsuario.ingresarCliente(nickname, nombre, newFileName, apellido, email, contrasenia, fechaNac, nacionalidad, tipoDoc, documento);
			} else if (tipoUsr.equals("aerolinea")) {
				String sitio = req.getParameter("sitio");
				String descripcion = req.getParameter("descripcion");

				iUsuario.ingresarAerolinea(nickname, nombre, newFileName, email, contrasenia, descripcion, sitio);
			} else {
				throw new Exception();
			}
			//Redirect
			DTUsuario usr = iUsuario.obtenerInfoUsuario(nickname);
			session.setAttribute("usuario_sesion", usr);
			session.setAttribute("tipo_usuario_sesion", tipoUsr);

			resp.sendRedirect("home");
		}
		// Especificar excepciones
		catch (YaRegistradoException ex) {
			// Envio mensaje de error
			req.setAttribute("error", ex.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/usuario/alta-usuario.jsp");
			dispatcher.forward(req, resp);
		}
		catch (Exception ex) {
			// Sino redirigo a pagina de error?
			req.setAttribute("error", "Error interno");

			RequestDispatcher dispatcher = req.getRequestDispatcher("home");
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
