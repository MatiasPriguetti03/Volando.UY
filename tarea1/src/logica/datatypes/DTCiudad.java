package logica.datatypes;

import java.time.LocalDate;

public class DTCiudad {
	private String nombre;
	private String pais;
	private String aeropuerto;
	private String descripcion;
	private String sitioWeb;
	private LocalDate fechaDeAlta;

	public DTCiudad(String nombre, String pais, String aeropuerto, String descripcion, String sitioWeb, LocalDate fechaAlta) {
		this.nombre = nombre;
		this.pais = pais;
		this.aeropuerto = aeropuerto;
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
		this.fechaDeAlta = fechaAlta;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPais() {
		return pais;
	}

	public String getAeropuerto() {
		return aeropuerto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}
	
	public LocalDate getFechaDeAlta() {
		return fechaDeAlta;
	}
}
