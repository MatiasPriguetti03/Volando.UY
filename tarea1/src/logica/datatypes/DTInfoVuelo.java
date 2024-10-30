package logica.datatypes;

import java.time.LocalDate;

public class DTInfoVuelo {
	private String name;
	private LocalDate fecha;
	private String origen;
	private String destino;
	
	public DTInfoVuelo(String name, String origen, String destino, LocalDate fecha) {
		this.name = name;
		this.fecha = fecha;
		this.origen = origen;
		this.destino = destino;
	}

	public String getName() {
		return name;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getFecha() {
		return fecha;
	}
}
