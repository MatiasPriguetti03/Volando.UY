package logica.datatypes;

import java.time.LocalDate;
import java.util.List;

public class DTPaquete {
	private String nombre;
	private String imagen;
	private String descripcion;
	private int periodoValidez;
	private int descuento;
	private LocalDate fechaAlta;
	private float costo;
	private List<DTRutaVueloPaquete> rutaVuelos;
	
	public DTPaquete(String nombre, String imagen, String descripcion, int periodoValidez, int descuento, LocalDate fechaAlta, float costo,
		List<DTRutaVueloPaquete> rutaVuelos) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.periodoValidez = periodoValidez;
		this.descuento = descuento;
		this.fechaAlta = fechaAlta;
		this.costo = costo;
		this.rutaVuelos = rutaVuelos;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getImagen() {
		return imagen;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public Integer getPeriodoValidez() {
		return periodoValidez;
	}

	public Integer getDescuento() {
		return descuento;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public Float getCosto() {
		return costo;
	}
	
	public List<DTRutaVueloPaquete> getRutasVuelo() {
		return rutaVuelos;
	}

	public String toString() {
        return "Nombre: " + nombre + " Descripcion: " + descripcion + " Periodo de validez: " + periodoValidez + " Descuento: " + descuento + " Costo: " + costo;
    }
	

}

