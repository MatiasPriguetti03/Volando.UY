package logica.datatypes;

import java.util.List;

public class DTAerolinea {
	private String descripcion;
	private String sitioWeb;
	// Version base
	//private Vector<DTRutaVuelosAerolinea> rutasVuelo;
	// Version pro
	private List<DTRutaVuelo> rutasVuelo;
	
	public DTAerolinea(String descripcion, String sitioWeb, List<DTRutaVuelo> rutas) { // Vector<DTRutaVuelosAerolinea> rutas
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
		this.rutasVuelo = rutas;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public String getSitioWeb() {
		return this.sitioWeb;
	}
	
	// public Vector<DTRutaVuelosAerolinea> getRutasVuelo() {
	public List<DTRutaVuelo> getRutasVuelo() {
		return this.rutasVuelo;
	}
}
