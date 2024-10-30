package logica.datatypes;


// Generamos este Datatype porque a pesar de ser muy parecido a DTInfoRutaVuelo, sustituir Info por este implicaria que no 
// se pueda generar el Datatype desde dentro de la clase RutaVuelo (ya que esta no contiene a aerolinea y entonces deberia
// recurrir al controlador lo que no es deseado)
public class DTRutaVueloAerolinea {
	private String name;
	private String desc;
	private String descCorta;
	private String origen;
	private String destino;
	private String[] categorias;
	private String imagen;
	private String nickAerolinea;
	private String nombreAerolinea;
	
	public DTRutaVueloAerolinea(String name, String desc, String descCorta, String origen, String destino, String[] categorias,  String imagen, String nickAerolinea, String nombreAerolinea) {
		this.name = name;
		this.desc = desc;
		this.descCorta = descCorta;
		this.origen = origen;
		this.destino = destino;
		this.categorias = categorias;
		this.imagen = imagen;
		this.nickAerolinea = nickAerolinea;
		this.nombreAerolinea = nombreAerolinea;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getDescCorta() {
		return descCorta;
	}
	
	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}
	
	public String getNickAerolinea() {
		return nickAerolinea;
	}
	
	public String getNombreAerolinea() {
		return nombreAerolinea;
	}
	
	public String getImagen() {
		return imagen;
	}
	
	public String[] getCategorias() {
		return categorias;
	}
}
