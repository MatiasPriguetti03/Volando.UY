package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logica.enums.EnumRutaEstado;

import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTVuelo;
import logica.datatypes.DTInfoVuelo;

public class RutaVuelo {
	private String nombre;
	private String imagen;
	private String descripcion;
	private String descripcionCorta;
	private EnumRutaEstado estado;
	private LocalTime hora;
	private LocalDate fechaAlta;
	private float costoExtra;
	private float costoBaseEjecutivo;
	private float costoBaseTurista;
	private Map<String, Vuelo> vuelos;
	private Ciudad origen;
	private Ciudad destino;
	private Map<String, Categoria> categorias;
	private Set<PaqueteRutaVuelo> paquetes;
	
	public DTInfoRutaVuelo getDTInfoRutaVuelo() {
		DTInfoRutaVuelo info = new DTInfoRutaVuelo(nombre, descripcion, origen.getNombre(), destino.getNombre());
		return info;
	}
	
	public DTVuelo getInfoVuelo(String nombre) {
		Vuelo vuelo = vuelos.get(nombre);
		return vuelo.getDTVuelo();
	}
	
	public DTRutaVuelo getDTRutaVuelo() {
		Vuelo vuelo;
		Set<DTInfoVuelo> setDTInfoVuelos = new HashSet<DTInfoVuelo>();
		List<DTVuelo> datosVuelos = new ArrayList<>();
		for (Map.Entry<String, Vuelo> entry : vuelos.entrySet()) {
			vuelo = entry.getValue();
			setDTInfoVuelos.add(vuelo.getRutaVuelo());
			datosVuelos.add(vuelo.getDTVuelo());
		}

		String[] cats = new String[categorias.size()];
		if (categorias.size() != 0) {
			Categoria categoria;
			int indice = 0;
			for (Map.Entry<String, Categoria> entry : categorias.entrySet()) {
				categoria = entry.getValue();
				cats[indice] = categoria.getNombre();
				indice++;
			}
			Arrays.sort(cats);
		}
		
		return new DTRutaVuelo(nombre, imagen, descripcion, descripcionCorta, estado, 
				fechaAlta, hora, costoExtra, costoBaseEjecutivo, costoBaseTurista, 
				setDTInfoVuelos, datosVuelos, origen.getNombre(), destino.getNombre(), cats);
	}
	
	public void agregarVueloARuta(Vuelo Vuelo) {
		vuelos.put(Vuelo.getNombre(), Vuelo);
	}
	
	public boolean existeVuelo(String nombre) {
		return vuelos.containsKey(nombre);
	}

	public RutaVuelo(String nombre, String imagen, String descripcion, String descripcionCorta, LocalTime hora, LocalDate fechaAlta, 
						float costoExtra, float costoEjecutivo, float costoTurista, Ciudad origen, Ciudad destino, Map<String, Categoria> categorias) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.descripcionCorta = descripcionCorta;
		this.estado = EnumRutaEstado.INGRESADA;
		this.hora = hora;
		this.fechaAlta = fechaAlta;
		this.costoExtra = costoExtra;
		this.costoBaseEjecutivo = costoEjecutivo;
		this.costoBaseTurista = costoTurista;
		this.vuelos = new java.util.HashMap<>();
		this.origen = origen;
		this.destino = destino;
		this.categorias = categorias;
		this.paquetes = new java.util.HashSet<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
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

	public float getCostoBaseEjecutivo() {
		return costoBaseEjecutivo;
	}

	public float getCostoBaseTurista() {
		return costoBaseTurista;
	}

	public Map<String, Vuelo> getVuelos() {
		return vuelos;
	}
	
	public Vuelo obtenerVuelo(String vuelo) {
		return vuelos.get(vuelo);
	}

	public Ciudad getOrigen() {
		return origen;
	}

	public Ciudad getDestino() {
		return destino;
	}

	public Map<String, Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Map<String, Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<PaqueteRutaVuelo> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(Set<PaqueteRutaVuelo> paquetes) {
		this.paquetes = paquetes;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}
	
	public String getImagen() {
		return this.imagen;
	}
	
	public String getEstado() {
		return this.estado.toString();
	}
	
	public void rechazar() {
		this.estado = EnumRutaEstado.RECHAZADA;
	}
	
	public void aceptar() {
		this.estado = EnumRutaEstado.CONFIRMADA;
	}

}
