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
import logica.IUsuario;
import logica.datatypes.DTUsuario;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/inicio-sesion")
public class InicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUsuario iUsuario;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InicioSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	Fabrica fabrica = Fabrica.getInstance();
    	iUsuario = fabrica.getIUsuario();

    	HttpSession session = request.getSession();

		String login = request.getParameter("login");
		String password = request.getParameter("password");


		if (login != null && password != null) {
			// Autentica el usuario
			try {
				DTUsuario usr = iUsuario.autenticarUsuario(login, password);
				// Si lo logra agrega DTUsuario a sesion y redirige a index
				session.setAttribute("usuario_sesion", usr);
				String tipoUsr = iUsuario.determinarTipoUsuario(usr.getNick());
				session.setAttribute("tipo_usuario_sesion", tipoUsr);
				//Redirect
				response.sendRedirect("home");
			}
			catch (NoExisteException ex) {
				// Sino vuelvo a inicio sesion
				request.setAttribute("error", ex.getMessage());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inicio-sesion/inicio-sesion.jsp");
				dispatcher.forward(request, response);
			}
		}
		else {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inicio-sesion/inicio-sesion.jsp");
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
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
