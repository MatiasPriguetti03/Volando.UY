package logica.datatypes;

import java.time.LocalDate;
import java.util.List;

import logica.enums.EnumAsiento;

public class DTReservaVuelo {
	private Integer idReserva;
	private int cantEquipajeExtra;
	private int cantPasajes;
	private float costo;
	private String nameVuelo;
	private String nickUser;
	private String nameUser;
	private String tipoReserva;
	private LocalDate fecha;
	private EnumAsiento tipoDeAsiento;
	private List<DTPasajero> acompaniantes;
	private DTRutaVuelo rutaVuelo;
	private DTVuelo vuelo;
	
	public DTReservaVuelo(Integer idReserva, String nameVuelo, String nickUser, String nameUser, LocalDate fechaDeReserva,
			int cantEquipaExtra, int cantPasajes, float costo, String tipoReserva, EnumAsiento tipoAsiento, 
			List<DTPasajero> acompaniantes, DTRutaVuelo rutaVuelo, DTVuelo vuelo) {
		this.idReserva = idReserva;
		this.cantEquipajeExtra = cantEquipaExtra;
		this.cantPasajes = cantPasajes;
		this.costo = costo;
		this.nickUser = nickUser;
		this.nameUser = nameUser;
		this.nameVuelo = nameVuelo;
		this.tipoReserva = tipoReserva;
		this.fecha = fechaDeReserva;
		this.tipoDeAsiento = tipoAsiento;
		this.acompaniantes = acompaniantes;
		this.rutaVuelo = rutaVuelo;
		this.vuelo = vuelo;
		
	}

	public Integer getId() {
		return idReserva;
	}

	public int getEquipajeExtra() {
		return cantEquipajeExtra;
	}
	
	public int getPasajes() {
		return cantPasajes;
	}
	
	public float getCosto() {
		return costo;
	}
	
	public String getNameVuelo() {
		return nameVuelo;
	}
	
	public String getNickUser() {
		return nickUser;
	}
	
	public String getNameUser() {
		return nameUser;
	}
	
	public String getTipoReserva() {
		return tipoReserva;
	}

	public LocalDate getFecha() {
		return fecha;
	}
	
	public EnumAsiento getTipoAsiento() {
		return tipoDeAsiento;
	}

	public List<DTPasajero> getAcompaniantes() {
		return acompaniantes;
	}

	public DTRutaVuelo getRutaVuelo() {
		return rutaVuelo;
	}

	public DTVuelo getVuelo() {
		return vuelo;
	}
}
