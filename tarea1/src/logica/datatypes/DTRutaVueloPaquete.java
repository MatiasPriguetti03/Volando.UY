package logica.datatypes;

import logica.enums.EnumAsiento;

public class DTRutaVueloPaquete {
	private String nombre;
	private int cantidad;
	private EnumAsiento tipoAsiento;
	private String origen;
	private String destino;
	
	public DTRutaVueloPaquete(String nombre, int cantidad, EnumAsiento tipoAsiento, String origen, String destino) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.tipoAsiento = tipoAsiento;
		this.origen = origen;
		this.destino = destino;
	}
	
	public String getNombre() {
		return this.nombre;
	}	
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public EnumAsiento getTipoAsiento() {
		return this.tipoAsiento;
	}
	
	public String getOrigen() {
		return this.origen;
	}
	
	public String getDestino() {
		return this.destino;
	}
	
	public void setNombre(String _nombre) {
		this.nombre = _nombre;
	}
	
	public void setCantidad(int _cantidad) {
		this.cantidad = _cantidad;
	}
	
	public void setTipoDeAsiento(EnumAsiento _tipoAsiento) {
		this.tipoAsiento = _tipoAsiento;
	}
}
