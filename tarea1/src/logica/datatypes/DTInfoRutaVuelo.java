package logica.datatypes;

public class DTInfoRutaVuelo {
	private String name;
	private String desc;
	private String origen;
	private String destino;
	
	public DTInfoRutaVuelo(String name, String desc, String origen, String destino) {
		this.name = name;
		this.desc = desc;
		this.origen = origen;
		this.destino = destino;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}
}
