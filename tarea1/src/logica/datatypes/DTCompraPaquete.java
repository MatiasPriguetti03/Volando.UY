package logica.datatypes;

import java.time.LocalDate;
import java.util.Map;

public class DTCompraPaquete {
	private String nickCliente;
	private String nombrePaquete;
	private Integer descuentoPaquete;
	private Float costo;
	private LocalDate fechaCompra;
	private LocalDate fechaVenc;
	private Map<String, DTRutaVueloPaquete> rutasDeCompra;
	private Map<String, Integer> usosRestantes;
	
	public DTCompraPaquete(String nickCliente, String nombrePaquete, Integer descuentoPaquete, LocalDate fechaCompra, Float costo, LocalDate fechaVenc,
			Map<String, DTRutaVueloPaquete> rutas, Map<String, Integer> usosRestantes) {
		this.nickCliente = nickCliente;
		this.nombrePaquete = nombrePaquete;
		this.descuentoPaquete = descuentoPaquete;
		this.fechaCompra = fechaCompra;
		this.costo = costo;
		this.fechaVenc = fechaVenc;
		this.rutasDeCompra = rutas;
		this.usosRestantes = usosRestantes;
	}
	
	public String getNickCliente() {
		return nickCliente;
	}

	public String getNombrePaquete() {
		return nombrePaquete;
	}
	
	public Integer getDescuentoPaquete() {
		return descuentoPaquete;
	}
	
	public Float getCosto() {
		return costo;
	}
	
	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public LocalDate getFechaVenc() {
		return fechaVenc;
	}
	
	public Map<String, DTRutaVueloPaquete> getDTRutasDeCompra(){
		return rutasDeCompra;
	}
	
	public Map<String, Integer> getUsosRestantes() {
		return usosRestantes;
	}
}
