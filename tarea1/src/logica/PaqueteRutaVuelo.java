package logica;

import logica.datatypes.DTRutaVueloPaquete;
import logica.enums.EnumAsiento;

public class PaqueteRutaVuelo {
	private int cantidad;
	private EnumAsiento tipoAsiento;
	private RutaVuelo rutaVuelo;

	public PaqueteRutaVuelo(int cantidad, EnumAsiento tipoDeAsiento) {
		this.cantidad = cantidad;
		this.tipoAsiento = tipoDeAsiento;
	}
	
	public void crearLinkPaqueteRutaVuelo(RutaVuelo newRutaVuelo) {
        this.rutaVuelo = newRutaVuelo;
    }
	
	public DTRutaVueloPaquete getDTRutaVueloPaquete() {
		String origen = rutaVuelo.getOrigen().getNombre();
		String destino = rutaVuelo.getDestino().getNombre();
		DTRutaVueloPaquete dtRutaPaquete = new DTRutaVueloPaquete(rutaVuelo.getNombre(), 
				cantidad, tipoAsiento, origen, destino);
		
		return dtRutaPaquete;		
    }
	
	public int getCantidad() {
		return cantidad;
	}
	
//	public void setCantidad(int cant) {
//		cantidad = cant;
//	}
	
	public EnumAsiento getTipoAsiento() {
		return tipoAsiento;
	}
	
	public String getNombreRutaVuelo() {
		return rutaVuelo.getNombre();
	}
	
	public float getCostoTurista() {
		return rutaVuelo.getCostoBaseTurista();
	}
	
	public float getCostoEjecutivo() {
		return rutaVuelo.getCostoBaseEjecutivo();
	}
}
