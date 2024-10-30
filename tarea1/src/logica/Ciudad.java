package logica;

import java.time.LocalDate;

import logica.datatypes.DTCiudad;

public class Ciudad {
	private String nombre;
	private String pais;
	private String aeropuerto;
	private String descripcion;
	private LocalDate fechaDeAlta;
	private String sitioWeb;
	
	// Constructor
	public Ciudad(String _nombre, String _pais, String _aeropuerto
			, String _descripcion, String _sitioWeb, LocalDate _fechaDeAlta) {
		this.nombre = _nombre;
		this.pais = _pais;
		this.aeropuerto = _aeropuerto;
		this.fechaDeAlta = _fechaDeAlta;
		this.sitioWeb = _sitioWeb;
		this.descripcion = _descripcion;
	}
	
	// Getters
	public String getNombre() {
		return this.nombre;
	}
	
	public String getPais() {
		return this.pais;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public DTCiudad getDTCiudad() {
		return new DTCiudad(this.nombre, this.pais, this.aeropuerto, this.descripcion, this.sitioWeb, this.fechaDeAlta);
	}
}
