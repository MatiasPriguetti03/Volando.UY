package com.volandouy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
import logica.enums.EnumDoc;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/modificar-datos-usuario")
@MultipartConfig
public class ModificarDatosUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Fabrica fabrica;
	private IUsuario iUsuario;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarDatosUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequestGET(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoExisteException {
        
        HttpSession session = request.getSession();
        DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");
        
        if (loggedUser == null) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN);     
        } else {
		    fabrica = Fabrica.getInstance();
		    iUsuario = fabrica.getIUsuario();
		    
		    String nick = loggedUser.getNick();
		    String tipoUsuario = iUsuario.determinarTipoUsuario(nick);
		    request.setAttribute("datos", loggedUser);
		    
		    if (tipoUsuario.equals("aerolinea")) {
		        processAerolineaRequest(request, response, nick);
		    } else if (tipoUsuario.equals("cliente")) {
		        processClienteRequest(request, response, nick);
		    }
        }
    }

    private void processAerolineaRequest(HttpServletRequest request, HttpServletResponse response, String nickAerolinea)
            throws ServletException, IOException, NoExisteException {

        DTAerolinea datosAerolinea = iUsuario.obtenerDatosAerolinea(nickAerolinea);
        request.setAttribute("datos+", datosAerolinea);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/usuario/modificar-datos-aerolinea.jsp");
        dispatcher.forward(request, response);
    }

    private void processClienteRequest(HttpServletRequest request, HttpServletResponse response, String nickCliente)
            throws ServletException, IOException, NoExisteException {

        DTCliente datosCliente = iUsuario.obtenerDatosCliente(nickCliente);
        request.setAttribute("datos+", datosCliente);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/usuario/modificar-datos-cliente.jsp");
        dispatcher.forward(request, response);
    }
    
    private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
        DTUsuario loggedUser = (DTUsuario) session.getAttribute("usuario_sesion");
        
        String nick = loggedUser.getNick();
        
        String tipoUsuario = (String) session.getAttribute("tipo_usuario_sesion");

		Fabrica fabrica = Fabrica.getInstance();
    	IUsuario iUsuario = fabrica.getIUsuario();
    	
		// No hago control sobre los datos porque esto se hace en el frontend
		try {
			String nombre = req.getParameter("nombre");
			String nickname = loggedUser.getNick();
			String email = loggedUser.getEmail();
			String contrasenia = loggedUser.getContrasenia();
			
			// Imagen
			Part filePart = req.getPart("imagen"); // Obtener el archivo subido
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Nombre del archivo
				InputStream fileContent = filePart.getInputStream(); // Contenido del archivo
				// Agregar un timestamp al nombre del archivo
				String newFileName = System.currentTimeMillis() + "_" + fileName;
				// Guardar el archivo
				String uploadPath = getServletContext().getRealPath("/img"); // Ruta relativa a la carpeta "uploads" dentro de la aplicación
				Path uploadDir = Paths.get(uploadPath); // Seleccionar carpeta de subidas
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}
				Files.copy(fileContent, uploadDir.resolve(newFileName));
				iUsuario.updateImagenUsuario(nickname, newFileName);
			}
			
			if (tipoUsuario.equals("cliente")) {
				DTCliente datosCliente = iUsuario.obtenerDatosCliente(nick);
				
				if (nombre.equals("")) {
					String apellido = datosCliente.getApellido();
			        String[] nombreSinApellido = loggedUser.getNombre().split(" "+ apellido);
			        nombre = nombreSinApellido[0]; 
				}
				
				String nacionalidad = req.getParameter("nacionalidad");
				if (nacionalidad.equals(""))
					nacionalidad = datosCliente.getNacionalidad();
				
				String tipoDocStr = req.getParameter("tipoDoc");
				EnumDoc tipoDoc;
				if (tipoDocStr.equals("mantener"))
					tipoDoc = datosCliente.getTipoDoc();
				else if (tipoDocStr.equals("ci"))
					tipoDoc = EnumDoc.CEDULA;
				else 
					tipoDoc = EnumDoc.PASAPORTE;
				
				String documento = req.getParameter("documento");
				if (documento.equals(""))
					documento = datosCliente.getDoc();
				
				String apellido = req.getParameter("apellido");
				if (apellido.equals(""))
					apellido = datosCliente.getApellido();
				
				LocalDate fechaNac = LocalDate.parse(req.getParameter("nacimiento"));
				
				iUsuario.modificarCliente(nickname, nombre, apellido, email, contrasenia, fechaNac, nacionalidad, tipoDoc, documento);
				
			} else if (tipoUsuario.equals("aerolinea")) {
				DTAerolinea datosAerolinea = iUsuario.obtenerDatosAerolinea(nick);
				
				if (nombre.equals(""))
			        nombre = loggedUser.getNombre();
				
				String sitio = req.getParameter("sitio");
				
				String descripcion = req.getParameter("descripcion");
				if(descripcion.equals(""))
					descripcion = datosAerolinea.getDescripcion();

				iUsuario.modificarAerolinea(nickname, nombre, email, contrasenia, descripcion, sitio);
				
			} else {
				throw new Exception();
			}
			//Actualizamos el atributo usuario sesion
			DTUsuario usr = iUsuario.obtenerInfoUsuario(nickname);
			session.setAttribute("usuario_sesion", usr);

			resp.sendRedirect("home");
		}
		catch (Exception ex) {
			RequestDispatcher dispatcher = req.getRequestDispatcher("home");
			dispatcher.forward(req, resp);
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
			processRequestGET(request, response);
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
			processRequestPOST(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
