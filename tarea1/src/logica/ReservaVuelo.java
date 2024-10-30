package logica;

import logica.enums.EnumAsiento;

import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import logica.datatypes.DTPasajero;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTVuelo;

//import logica.IUsuario;
//import logica.Usuario;
//import logica.Fabrica;

public class ReservaVuelo {
	private int idReserva;
	private Vuelo vuelo;
	private LocalDate fechaDeReserva;
	private EnumAsiento tipoDeAsiento;
	private int cantEquipajeExtra;
	private List<DTPasajero> acompaniantes;
	private float costoTotal;
	private CompraPaquete compraPaquete;

	// Constructor
	public ReservaVuelo(int _id, Vuelo _vuelo, LocalDate _fechaDeReserva, EnumAsiento _tipoDeAsiento,
			List<DTPasajero> _acompaniantes, int _cantEquipajeExtra, float _costoTotal) {
		this.idReserva = _id;
		this.vuelo = _vuelo;
		this.fechaDeReserva = _fechaDeReserva;
		this.tipoDeAsiento = _tipoDeAsiento;
		this.cantEquipajeExtra = _cantEquipajeExtra;
		this.acompaniantes = _acompaniantes;
		this.costoTotal = _costoTotal;
		this.compraPaquete = null;
	}
	
	// Reserva con compra de paquete
	public ReservaVuelo(int _id, Vuelo _vuelo, LocalDate _fechaDeReserva, EnumAsiento _tipoDeAsiento,
			List<DTPasajero> _acompaniantes, int _cantEquipajeExtra, float _costoTotal,
			CompraPaquete _compraPaquete) {
		this.idReserva = _id;
		this.vuelo = _vuelo;
		this.fechaDeReserva = _fechaDeReserva;
		this.tipoDeAsiento = _tipoDeAsiento;
		this.cantEquipajeExtra = _cantEquipajeExtra;
		this.acompaniantes = _acompaniantes;
		this.costoTotal = _costoTotal;
		this.compraPaquete = _compraPaquete;
	}

	// Getters
	public int getId() {
		return this.idReserva;
	}

	public Vuelo getVuelo() {
		return this.vuelo;
	}

	public java.time.LocalDate getFechaDeReserva() {
		return this.fechaDeReserva;
	}

	public EnumAsiento getTipoDeAsiento() {
		return this.tipoDeAsiento;
	}

	public int getCantEquipajeExtra() {
		return this.cantEquipajeExtra;
	}

	public List<DTPasajero> getAcompaniantes() {
		return this.acompaniantes;
	}

	public float getCostoTotal() {
		return this.costoTotal;
	}
	
	public CompraPaquete getCompraPaquete() {
		return this.compraPaquete;
	}
	
	public DTReservaVuelo getDTReservaVuelo() {
		String nickUser = "";
		String nameUser = "";
		ManejadorUsuario ManU = ManejadorUsuario.getInstancia();
		Map<String, Cliente> clientes = ManU.getClientes();
		for (Map.Entry<String, Cliente> c : clientes.entrySet()) {
			Cliente cliente = c.getValue();
			if (cliente.getReservasVuelo().containsKey(idReserva)) {
				nickUser = cliente.getNickname();
				nameUser = cliente.getNombre() + " " + cliente.getApellido();
				break;
			}
		}
		int cantPasajes = acompaniantes.size();
		
		String tipoReserva = "General";
		if (compraPaquete != null) {
			tipoReserva = compraPaquete.getPaqueteDeCompra().getNombre();
		}
		
		DTRutaVuelo rutaVueloData = vuelo.getRutaDeVuelo().getDTRutaVuelo();
		DTVuelo vueloData = vuelo.getDTVuelo();
		
		// Creo una copia de DTPasajeros de la clase para mandarla al DTReserva
		List<DTPasajero> acomps = new ArrayList<DTPasajero>();
		for (DTPasajero acompaniante : acompaniantes) {
			DTPasajero acomp = new DTPasajero(acompaniante.getNombre(), acompaniante.getApellido());
			acomps.add(acomp);
		}
				
		return new DTReservaVuelo(idReserva, vuelo.getNombre(), nickUser, nameUser, fechaDeReserva, 
				cantEquipajeExtra, cantPasajes, costoTotal, tipoReserva, tipoDeAsiento, 
				acomps, rutaVueloData, vueloData);
	}
}
