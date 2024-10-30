package com.volandouy.controllers;

import com.volandouy.model.Datos;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CargaDatos
 */
@WebServlet("/cargar-datos")
public class CargarDatos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método init() para inicializar los datos
     */
    @Override
    public void init() throws ServletException {
        super.init(); // Llamar al método init de HttpServlet
        try {
            Datos.getInstancia();
			Datos.cargarDatosUsuarios();
			Datos.cargarDatosCiudades();
			Datos.cargarDatosCategorias();
			Datos.cargarDatosRutasVuelo();
			Datos.cargarDatosVuelos();
			Datos.cargarDatosPaquetes();
			Datos.cargarDatosComprasPaquetes();
			Datos.cargarDatosReservasVuelos();
            System.out.println("Se cargaron los datos correctamente.");
        } catch (Exception e) {
            // Probablemente porque los datos ya fueron cargados
            System.err.println("Error al intentar cargar los datos:");
            e.printStackTrace();
        }
    }

    /**
     * Procesa las solicitudes HTTP GET y POST.
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("home");
    }

    /**
     * Maneja las solicitudes GET.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes POST.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
