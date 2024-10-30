package logica;

import logica.datatypes.DTUsuario;

public abstract class Usuario {
    private String email;
    private String nickname;
    private String nombre;
    private String imagen;
    private String contrasenia;

    // Constructor
    protected Usuario(String _email, String _nickname, String _nombre, String _imagen, String _contrasenia) {
    	this.email = _email;
    	this.nickname = _nickname;
    	this.nombre = _nombre;
    	this.contrasenia = _contrasenia;
    	this.imagen = _imagen;
    }
    
    // Getters
    public String getEmail(){
        return email;
     }

    public String getNickname(){
        return nickname;
    }

    public String getNombre() {
    	return nombre;
    }
    
    public String getImagen() {
    	return imagen;
    }
    
    public String getContrasenia() {
    	return contrasenia;
    }
    
    // Setters
    public void setEmail(String email) {
    	this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
    
    public void setImagen(String imagen) {
    	this.imagen = imagen;
    }

	abstract public DTUsuario getDTUsuario();
}