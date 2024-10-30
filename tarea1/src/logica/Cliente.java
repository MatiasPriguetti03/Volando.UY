package logica;

import logica.datatypes.DTUsuario;
import logica.enums.EnumDoc;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class Cliente extends Usuario {
    private String apellido;
	private LocalDate fechaNacimiento;
    private String nacionalidad;
    private EnumDoc tipoDoc;
    private String documento;
    private Map<Integer, ReservaVuelo> reservasDeVuelo;
    private Map<String, CompraPaquete> comprasDePaquetes;
    
    // Constructor new Cliente(nick, nombre, contsasenia, email, apellido, fechaNac, nacionalidad, tipoDoc, numDoc);
    public Cliente(String _nickname, String _nombre, String imagen, String _email, String _contrasenia, String _apellido,
    	LocalDate _fechaNacimiento, String _nacionalidad, EnumDoc _tipoDoc, String _documento) {
    	super(_email, _nickname, _nombre, imagen, _contrasenia);
    	this.apellido = _apellido;
    	this.fechaNacimiento = _fechaNacimiento;
    	this.nacionalidad = _nacionalidad;
    	this.tipoDoc = _tipoDoc;
    	this.documento = _documento;
    	
    	this.reservasDeVuelo = new HashMap<Integer, ReservaVuelo>();
    	this.comprasDePaquetes = new HashMap<String, CompraPaquete>();
    }
    
    // Getters
    public String getApellido() {
        return apellido;
    }
    
    public LocalDate getFechaNac() {
        return fechaNacimiento;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    public EnumDoc getTipoDoc() {
        return tipoDoc;
    }

    public String getDoc() {
        return documento;
    }
    
    public Map<Integer, ReservaVuelo> getReservasVuelo() {
    	return reservasDeVuelo;
    }
    
    public Map<String, CompraPaquete> getComprasDePaquetes() {
    	return comprasDePaquetes;
    }
    
    public DTUsuario getDTUsuario() {
		DTUsuario datosUsuario = new DTUsuario(getNickname(), getNombre(), getImagen(), getEmail(), getContrasenia());
		return datosUsuario;
	}
    
    // Setters
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public void setFechaNacimiento(java.time.LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setNacionalidad(String nacionalidad) {
    	this.nacionalidad = nacionalidad;
    }

    public void setTipoDoc(EnumDoc tipoDoc) {
    	this.tipoDoc = tipoDoc;
    }

    public void setDoc(String documento) {
    	this.documento = documento;
    }
    
    public void setReservasVuelo(Map<Integer, ReservaVuelo> reservasDeVuelo) {
    	this.reservasDeVuelo = reservasDeVuelo;
    }

    //
    
    public void agregarReserva(ReservaVuelo nuevaReserva) {
    	this.reservasDeVuelo.put(nuevaReserva.getId(), nuevaReserva);
    }
    
    public boolean comproPaquete(String nombrePaquete) {
    	return this.comprasDePaquetes.containsKey(nombrePaquete);
    }
    
    public void agregarCompraPaquete(CompraPaquete compra) {
    	this.comprasDePaquetes.put(compra.getPaqueteDeCompra().getNombre(), compra);
    }

    
    
}
