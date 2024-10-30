package logica.datatypes;

public class DTUsuario {
	private String nick;
	private String nombre;
	private String imagen;
	private String email;
	private String contrasenia;

	public DTUsuario(String nick, String nombre, String imagen, String email, String contrasenia) {
		this.nick = nick;
		this.nombre = nombre;
		this.imagen = imagen;
		this.email = email;
		this.contrasenia = contrasenia;
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getImagen() {
		return this.imagen;
	}
	
	public String getEmail() {
		return this.email;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}
}


