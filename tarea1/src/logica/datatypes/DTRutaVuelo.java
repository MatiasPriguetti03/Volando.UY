package logica.datatypes;

import logica.enums.EnumRutaEstado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class DTRutaVuelo {
	private String name;
	private String imagen;
	private String descripcion;
	private String descripcionCorta;
	private EnumRutaEstado estado;
	private LocalTime hora;
	private LocalDate fechaAlta;
	private float costoExtra;
	private float costoEjecutivo;
	private float costoTurista;
	private Set<DTInfoVuelo> infoVuelos;
	private List<DTVuelo> datosVuelos;
	private String origen;
	private String destino;
	private String[] categorias;
	
	public DTRutaVuelo(String name, String imagen, String descripcion, String descripcionCorta, EnumRutaEstado estado, LocalDate fechaAlta, LocalTime hora, float costoExtra,
			float costoEjecutivo, float costoTurista, Set<DTInfoVuelo> infoVuelos, List<DTVuelo> datosVuelos, String origen, String destino, String[] categorias) {
		this.name = name;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.descripcionCorta = descripcionCorta;
		this.estado = estado;
		this.hora = hora;
		this.fechaAlta = fechaAlta;
		this.costoExtra = costoExtra;
		this.costoEjecutivo = costoEjecutivo;
		this.costoTurista = costoTurista;
		this.infoVuelos = infoVuelos;
		this.datosVuelos = datosVuelos;
		this.origen = origen;
		this.destino = destino;
		this.categorias = categorias;
	}

	public String getName() {
		return name;
	}
	
	public String getImagen() {
		return imagen;
	}

	public String getDesc() {
		return descripcion;
	}
	
	public String getDescCorta() {
		return descripcionCorta;
	}

	public String getEstado() {
		return estado.toString();
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	public LocalTime getHora() {
		return hora;
	}

	public float getCostoExtra() {
		return costoExtra;
	}

	public float getCostoEjecutivo() {
		return costoEjecutivo;
	}

	public float getCostoTurista() {
		return costoTurista;
	}

	public Set<DTInfoVuelo> getDTInfoVuelos() {
		return infoVuelos;
	}
	
	public List<DTVuelo> getDTVuelos() {
		return datosVuelos;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public String[] getCategorias() {
		return categorias;
	}
}
