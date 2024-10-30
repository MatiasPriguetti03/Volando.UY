package logica.datatypes;

public class DTPasajero {

	private String nombre;
	private String apellido;
	
	// Constructor
	public DTPasajero(String _nombre, String _apellido) {
		this.nombre = _nombre;
		this.apellido = _apellido;
	}
	
	//Getters
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}
	
	// Setters
	public void setNombre(String _nombre) {
		this.nombre = _nombre;
	}
	
	public void setApellido(String _apellido) {
		this.nombre = _apellido;
	}
}
