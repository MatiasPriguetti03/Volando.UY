package logica;

import logica.datatypes.DTPaquete;
import logica.datatypes.DTRutaVueloPaquete;
import logica.datatypes.DTInfoPaquete;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paquete {
	
	private String nombre;
	private String imagen;
	private String descripcion;
	private int periodoValidez;
	private int descuento;
	private LocalDate fechaAlta;
//	private EnumAsiento tipoAsiento;
	private float costo;
	private Map<String, PaqueteRutaVuelo> paquetesRutaVuelo;

	public Paquete(String nombre, String imagen, String descripcion, int periodoDeValidez, int descuento, LocalDate fechaDeAlta) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.periodoValidez = periodoDeValidez;
		this.descuento = descuento;
		this.fechaAlta = fechaDeAlta;
//		this.tipoAsiento = tipoDeAsiento;
		this.paquetesRutaVuelo = new HashMap<String, PaqueteRutaVuelo>();
	}

	public void crearLinkPaqueteRutaVuelo(PaqueteRutaVuelo newPaqueteRutaVuelo) {
        this.paquetesRutaVuelo.put(newPaqueteRutaVuelo.getNombreRutaVuelo(), newPaqueteRutaVuelo);
    }
	
	public boolean existePaqueteRutaVuelo(String nombreRV) {
		return paquetesRutaVuelo.containsKey(nombreRV);
	}
	
	public Map<String, PaqueteRutaVuelo> getPaquetesRutaVuelo(){
		return paquetesRutaVuelo;
	}
	
	public PaqueteRutaVuelo getPaqueteRutaVuelo(String nombreRV){
		return paquetesRutaVuelo.get(nombreRV);
	}
	
	public DTPaquete getDTPaquete() {
		List<DTRutaVueloPaquete> rutaVuelos = new ArrayList<>();
		if (paquetesRutaVuelo != null) {
			for (Map.Entry<String, PaqueteRutaVuelo> entry : paquetesRutaVuelo.entrySet()) {
				PaqueteRutaVuelo paqueteRuta = entry.getValue();
				rutaVuelos.add(paqueteRuta.getDTRutaVueloPaquete());
			}
		}
        return new DTPaquete(nombre, imagen, descripcion, periodoValidez, descuento, fechaAlta, costo, rutaVuelos);
    }
	
	public DTInfoPaquete getDTInfoPaquete() {
        return new DTInfoPaquete(nombre, descripcion);
    }
	
	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getPeriodoValidez() {
		return periodoValidez;
	}

	public int getDescuento() {
		return descuento;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float newCosto) {
		this.costo = newCosto;
	}

	@Override
	public String toString() {
		return "Paquete [nombre=" + nombre + ", descripcion=" + descripcion + ", periodoValidez=" + periodoValidez + ", descuento="
				+ descuento + ", fecha de alta="+ fechaAlta + ", costo=" + costo + "]";
	}
}
