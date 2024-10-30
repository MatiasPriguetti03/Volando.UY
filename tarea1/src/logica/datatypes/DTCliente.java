package logica.datatypes;

import java.time.LocalDate;

import logica.enums.EnumDoc;

public class DTCliente {
	private String apellido;
	private String nacionalidad;
	private LocalDate fechaNac;
	private EnumDoc tipoDoc;
	private String documento;
	
	public DTCliente(String apellido, String nacionalidad, LocalDate fechaNac, EnumDoc tipoDoc, String documento) {
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.fechaNac = fechaNac;
		this.tipoDoc = tipoDoc;
		this.documento = documento;
	}
	
	public String getApellido() {
		return this.apellido;
	}
	
	public String getNacionalidad() {
		return this.nacionalidad;
	}
	
	public LocalDate getFechaNac() {
		return this.fechaNac;
	}
	
	public EnumDoc getTipoDoc() {
		return this.tipoDoc;
	}
	
	public String getDoc() {
		return this.documento;
	}
}
