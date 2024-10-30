package logica.datatypes;

public class DTInfoPaquete {
	private String nombre;
	private String descripcion;
	
	public DTInfoPaquete(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
}
