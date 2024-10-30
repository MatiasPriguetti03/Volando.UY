package logica;

import java.time.LocalDate;
import java.time.LocalTime;

import logica.datatypes.DTVuelo;
import logica.datatypes.DTInfoVuelo;

public class Vuelo {
	private String imagen;
	private String nombre;
	private RutaVuelo rutaVuelo;
	private LocalDate fechaAlta;
	private LocalDate fechaVuelo;
	private LocalTime duracion;
	private int maxAsientosTurista;
	private int maxAsientosEjecutivo;
	
	// Constructor CON Ruta de Vuelo
	public Vuelo(String _imagen, String _nombre, RutaVuelo _rutaVuelo, LocalDate _fechaAlta, LocalDate _fechaVuelo,
				LocalTime _duracion, int _maxTurista, int _maxEjecutivo) {
		this.imagen = _imagen;
		this.nombre = _nombre;
		this.rutaVuelo = _rutaVuelo;
		this.fechaAlta = _fechaAlta;
		this.fechaVuelo = _fechaVuelo;
		this.duracion = _duracion;
		this.maxAsientosTurista = _maxTurista;
		this.maxAsientosEjecutivo = _maxEjecutivo;
	}
	
	// Constructor SIN Ruta de Vuelo
	public Vuelo(String _imagen, String _nombre, LocalDate _fechaAlta, LocalDate _fechaVuelo,
			LocalTime _duracion, int _maxTurista, int _maxEjecutivo) {
	this.imagen = _imagen;
	this.nombre = _nombre;
	this.fechaAlta = _fechaAlta;
	this.fechaVuelo = _fechaVuelo;
	this.duracion = _duracion;
	this.maxAsientosTurista = _maxTurista;
	this.maxAsientosEjecutivo = _maxEjecutivo;
}
	
	// Getters
	public String getNombre() {
		return this.nombre;
	}
	
	public LocalDate getFechaAlta() {
		return this.fechaAlta;
	}
	
	public LocalDate getFechaVuelo() {
		return this.fechaVuelo;
	}
	
	public LocalTime getDuracion() {
		return this.duracion;
	}
	
	public int getMaxAsientosTurista() {
		return this.maxAsientosTurista;
	}
	
	public int getMaxAsientosEjecutivo() {
		return this.maxAsientosEjecutivo;
	}
	
	public RutaVuelo getRutaDeVuelo() {
		return this.rutaVuelo;
	}

	public DTVuelo getDTVuelo() {
		return new DTVuelo(this.imagen, this.nombre, this.fechaVuelo, this.duracion, this.maxAsientosEjecutivo, this.maxAsientosTurista, this.fechaAlta, this.rutaVuelo.getDTInfoRutaVuelo());
	}

	public DTInfoVuelo getRutaVuelo() {
		return new DTInfoVuelo(nombre, rutaVuelo.getOrigen().getNombre(),  rutaVuelo.getDestino().getNombre(), this.fechaAlta);
	}
	
	// Setter
	public void setRutaVuelo(RutaVuelo ruta) {
		this.rutaVuelo = ruta;
	}
}