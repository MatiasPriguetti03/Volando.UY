package logica.datatypes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DTVuelo {
	private String imagen;
	private String name;
	private LocalDate fechaVuelo;
	private LocalTime duracion;
	private int asientosMaxEjecutivo;
	private int asientosMaxTurista;
	private LocalDate fechaAlta;
	private DTInfoRutaVuelo rutaVuelo;
	private List<DTReservaVuelo> reservas;
	
	public DTVuelo(String imagen, String name, LocalDate fecha, LocalTime duracion, int maxEje, int maxTur, LocalDate fechaAlta, DTInfoRutaVuelo rutaVuelo, List<DTReservaVuelo> reservas) {
		this.name = name;
		this.fechaVuelo = fecha;
		this.duracion = duracion;
		this.asientosMaxEjecutivo = maxEje;
		this.asientosMaxTurista = maxTur;
		this.fechaAlta = fechaAlta;
		this.rutaVuelo = rutaVuelo;
		this.reservas = reservas;
		this.imagen = imagen;
	}
	
	public DTVuelo(String imagen, String name, LocalDate fecha, LocalTime duracion, int maxEje, int maxTur, LocalDate fechaAlta, DTInfoRutaVuelo rutaVuelo) {
		this.name = name;
		this.fechaVuelo = fecha;
		this.duracion = duracion;
		this.asientosMaxEjecutivo = maxEje;
		this.asientosMaxTurista = maxTur;
		this.fechaAlta = fechaAlta;
		this.rutaVuelo = rutaVuelo;
		this.reservas = new ArrayList<>();
		this.imagen = imagen;
	}

	public String getImagen() {
		return imagen;
	}
	
	public String getName() {
		return name;
	}

	public LocalDate getFechaVuelo() {
		return fechaVuelo;
	}

	public LocalTime getDuracion() {
		return duracion;
	}

	public int getAsientosMaxEjecutivo() {
		return asientosMaxEjecutivo;
	}

	public int getAsientoxMaxTurista() {
		return asientosMaxTurista;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	public void addReserva(DTReservaVuelo reserva) {
		reservas.add(reserva);
	}

	public DTInfoRutaVuelo getRutaVuelo() {
		return rutaVuelo;
	}
	
	public List<DTReservaVuelo> getReservas() {
		return reservas;
	}
}
