package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTCiudad;
import logica.datatypes.DTPasajero;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTRutaVueloAerolinea;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTRutaVueloPaquete;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTVuelo;
import logica.datatypes.Pair;
import logica.enums.EnumAsiento;
import excepciones.YaRegistradoException;
import excepciones.EsConjuntoVacioException;
import excepciones.NoExisteException;
import excepciones.OperacionInvalidaException;

public class ControladorVuelo implements IVuelo{
	
	//combiné seleccionarRutaVuelo, ingresarDatosVuelo y confirmarAltaVuelo xq por el gui se haría todo junto
	//para AltaVuelo
	public void agregarVueloARuta(String imagen, String nombreRuta, String nombreVuelo, LocalDate fechaVuelo, 
			LocalTime duracion, int maxEje, int maxTur, LocalDate fechaAlta) throws YaRegistradoException {

		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		Map<String, RutaVuelo> rutas = ManV.getRutasVuelo();
		boolean existe = false;
		for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
			existe = existe || entry.getValue().existeVuelo(nombreVuelo);
		}
		
		if (!existe) {
			RutaVuelo rutaVuelo = ManV.obtenerRutaVuelo(nombreRuta);
			Vuelo vuelo = new Vuelo(imagen, nombreVuelo, rutaVuelo, fechaVuelo, fechaAlta, duracion, maxTur, maxEje);
			
			rutaVuelo.agregarVueloARuta(vuelo);
			ManV.agregarVuelo(vuelo);
		} else throw new YaRegistradoException("El nombre del vuelo ya esta en uso");
	}
	
	public void agregarVueloARuta(String nombreRuta, Vuelo vuelo) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		RutaVuelo rutavuelo = ManV.obtenerRutaVuelo(nombreRuta);
		rutavuelo.agregarVueloARuta(vuelo);
		vuelo.setRutaVuelo(rutavuelo);
		ManV.agregarVuelo(vuelo);
	}
	
	//combiné seleccionarAerolinea y listarRutasVuelosAerolinea xq se harían al mismo tiempo
	public Set<DTInfoRutaVuelo> listarRutasVuelosAerolinea(String aerolineaStr) throws EsConjuntoVacioException {
		ManejadorUsuario ManU = ManejadorUsuario.getInstancia();
		Aerolinea aerolinea = (Aerolinea) ManU.getUsuario(aerolineaStr);
		Map<String, RutaVuelo> rutas = aerolinea.getRutas();
		Set<DTInfoRutaVuelo> ret = new HashSet<DTInfoRutaVuelo>();
		if (rutas == null || rutas.isEmpty())
			return null;
		else {	
			for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
				ret.add(entry.getValue().getDTInfoRutaVuelo());
			}
		}
		return ret;
	}
	
	public Set<DTRutaVuelo> listarRutasVuelos() {
		ManejadorVuelo maneV = ManejadorVuelo.getInstancia();
		Map<String, RutaVuelo> rutas = maneV.getRutasVuelo();
		Set<DTRutaVuelo> retorno = new HashSet<DTRutaVuelo>();
		if (rutas == null || rutas.isEmpty())
			return null;
		else {
			for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
				if (entry != null)
				retorno.add(entry.getValue().getDTRutaVuelo());
			}
		}
		return retorno;
	}
	
	//combiné seleccionarRutaVuelo y listarInfoRutaVuelo
	//para ConsultaRutaVuelo y ReservaVuelo
	public DTRutaVuelo obtenerDTRutaVuelo(String rutaVuelo) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		RutaVuelo rutaV = ManV.obtenerRutaVuelo(rutaVuelo);
		
		if (rutaV == null) return null;
		
		return rutaV.getDTRutaVuelo();
	}
	
	//para ConsultaRutaVuelo y ReservaVuelo
	public String[] listarVuelos(String rutaVuelo) throws EsConjuntoVacioException {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		RutaVuelo rutaV = ManV.obtenerRutaVuelo(rutaVuelo);
		Map<String, Vuelo> vuelos = rutaV.getVuelos();
		
		String[] nombreVuelos = new String[vuelos.size()];
		
		if (vuelos == null || vuelos.isEmpty())
			throw new EsConjuntoVacioException("La ruta de vuelo " + rutaVuelo + " aun no contiene Vuelos");
		else {	
			Iterator<String> iterator = vuelos.keySet().iterator();
			int indice = 0;
			
			while (iterator.hasNext()) {
				String key = iterator.next();
				nombreVuelos[indice] = key;
				indice++;
			}
			
			Arrays.sort(nombreVuelos);
		}
		
		return nombreVuelos;
	}
	
	public List<DTVuelo> listarDTVuelos() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		List<DTVuelo> listaV = new ArrayList<>();
		Map<String, RutaVuelo> rutas = manV.getRutasVuelo(); 
		
		for (Map.Entry<String, RutaVuelo> ruta : rutas.entrySet()) {
			Map<String, Vuelo> vuelosRuta = ruta.getValue().getVuelos();
			
			for (Map.Entry<String, Vuelo> vuelos : vuelosRuta.entrySet()) {
				listaV.add(vuelos.getValue().getDTVuelo());
			}
		}			
		
		return listaV;
	}
	
	public List<DTVuelo> listarDTVuelosRC() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		List<DTVuelo> listaV = new ArrayList<>();
		Map<String, RutaVuelo> rutas = manV.getRutasVuelo(); 
		
		for (Map.Entry<String, RutaVuelo> ruta : rutas.entrySet()) {
			Map<String, Vuelo> vuelosRuta = ruta.getValue().getVuelos();
			if (ruta.getValue().getEstado() == "Confirmada") {
				for (Map.Entry<String, Vuelo> vuelos : vuelosRuta.entrySet()) {
					listaV.add(vuelos.getValue().getDTVuelo());
				}
			}
		}	
		
		Collections.sort(listaV, Comparator.comparing(DTVuelo::getName));
		
		return listaV;
	}
	
	public List<DTRutaVuelo> listarDTRutasVuelo() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		List<DTRutaVuelo> listaR = new ArrayList<>();
		Map<String, RutaVuelo> rutas = manV.getRutasVuelo(); 
		
		for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
			listaR.add(entry.getValue().getDTRutaVuelo());
		}	
		
		Collections.sort(listaR, Comparator.comparing(DTRutaVuelo::getName));
		
		return listaR;
	}
	
	public List<DTRutaVuelo> listarDTRutasVueloConfirmadas() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		List<DTRutaVuelo> listaR = new ArrayList<>();
		Map<String, RutaVuelo> rutas = manV.getRutasVuelo(); 
		
		for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
			if (entry.getValue().getEstado().equals("Confirmada"))
				listaR.add(entry.getValue().getDTRutaVuelo());
		}	
		
		Collections.sort(listaR, Comparator.comparing(DTRutaVuelo::getName));
		
		return listaR;
	}
	
	public List<DTRutaVuelo> listarDTRutasVueloConfirmadas(String categoria) {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		Map<String, RutaVuelo> rutas = manV.getRutasVuelo();
		List<DTRutaVuelo> listaR = new ArrayList<>();
		
		if (rutas == null || rutas.isEmpty())
			return null;
		else {
			for (Map.Entry<String, RutaVuelo> ruta : rutas.entrySet()) {
				if (ruta != null && ruta.getValue().getCategorias().containsKey(categoria) && ruta.getValue().getEstado().equals("Confirmada")) {						
					listaR.add(ruta.getValue().getDTRutaVuelo());						
				}
			}	
			
		Collections.sort(listaR, Comparator.comparing(DTRutaVuelo::getName));
			
		}
		return listaR;
	}
	
	/**
	 * Trae los DTRutaVuelo de una categoria determinada de la aerolinea 
	 * Si categorias es "", trae todas las rutas de la aerolinea 
	 **/
	public List<DTRutaVuelo> listarDTRutasVuelo(String aerolinea, String categoria) {
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		
		Map<String, RutaVuelo> rutas = ((Aerolinea) manU.getUsuario(aerolinea)).getRutas();
		List<DTRutaVuelo> listaR = new ArrayList<>();
		
		if (rutas == null || rutas.isEmpty())
			return null;
		else {
			for (Map.Entry<String, RutaVuelo> ruta : rutas.entrySet()) {
				if (ruta != null && categoria != "" && ruta.getValue().getCategorias().containsKey(categoria)) {						
					listaR.add(ruta.getValue().getDTRutaVuelo());						
				} else if (ruta != null)
					listaR.add(ruta.getValue().getDTRutaVuelo());				
			}	
		}
		
		Collections.sort(listaR, Comparator.comparing(DTRutaVuelo::getName));
		
		return listaR;
	}
	
	/**
	 * Trae los DTRutaVuelo de rutas CONFIRMADAS de una categoria determinada de la aerolinea 
	 * Si categorias es "", trae todas las rutas de la aerolinea CONFIRMADAS
	 **/
	public List<DTRutaVuelo> listarDTRutasVueloConfirmadas(String aerolinea, String categoria) {
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		
		Map<String, RutaVuelo> rutas = ((Aerolinea) manU.getUsuario(aerolinea)).getRutas();
		List<DTRutaVuelo> listaR = new ArrayList<>();
		
		if (rutas == null || rutas.isEmpty())
			return null;
		else {
			for (Map.Entry<String, RutaVuelo> ruta : rutas.entrySet()) {
				if (ruta != null && categoria != "" && ruta.getValue().getCategorias().containsKey(categoria) && ruta.getValue().getEstado().equals("Confirmada")) {						
					listaR.add(ruta.getValue().getDTRutaVuelo());						
				} else if (ruta != null && ruta.getValue().getEstado().equals("Confirmada"))
					listaR.add(ruta.getValue().getDTRutaVuelo());				
			}	
		}
		
		Collections.sort(listaR, Comparator.comparing(DTRutaVuelo::getName));
		
		return listaR;
	}
	
	public List<DTReservaVuelo> listarDTReservasUsuario(String nickUsuario) throws NoExisteException {
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		
		Usuario usr = manU.getUsuario(nickUsuario);
		if (usr == null)
			throw new NoExisteException("No existe el usuario al que se intento acceder");
		
		List<DTReservaVuelo> listaR = new ArrayList<>();
		Map<Integer, ReservaVuelo> reservasCliente;
		
		if (usr instanceof Cliente) {
			reservasCliente = ((Cliente) usr).getReservasVuelo();
			for (Map.Entry<Integer, ReservaVuelo> reservaC: reservasCliente.entrySet()) {
				listaR.add(reservaC.getValue().getDTReservaVuelo());
			}
		} else {
			Fabrica fabrica = Fabrica.getInstance();
			IUsuario conU = fabrica.getIUsuario();
			Map<String, Cliente> clientes = manU.getClientes();
			
			for (Map.Entry<String, Cliente> cliente : clientes.entrySet()) {
				reservasCliente = cliente.getValue().getReservasVuelo();
			
				for (Map.Entry<Integer, ReservaVuelo> reserva : reservasCliente.entrySet()) {
					String nombreRuta = reserva.getValue().getVuelo().getRutaDeVuelo().getNombre();
					if (nickUsuario == conU.obtenerNickAerolineaDeRutaVuelo(nombreRuta)) {
						listaR.add(reserva.getValue().getDTReservaVuelo());
					}
				}
			}
		}
		return listaR;
	}
	
	public DTVuelo obtenerDTVuelo(String vuelo) {
		ManejadorVuelo manVue = ManejadorVuelo.getInstancia();
		ManejadorUsuario manUsr = ManejadorUsuario.getInstancia();
		
		Vuelo Vuelo = manVue.obtenerVuelo(vuelo);
		
		if (Vuelo == null) return null;
		DTVuelo datosVuelo = Vuelo.getDTVuelo();
		
		Map<String, Cliente> clientes = manUsr.getClientes();
		
		// Recorremos los clientes y sus reservas de vuelo
	    clientes.forEach((String k, Cliente v) -> {
	        // Para cada cliente, recorremos sus reservas
	        v.getReservasVuelo().forEach((k1, v1) -> {
	            // Verificamos si la reserva es del vuelo que estamos buscando
	            if (v1.getVuelo().getNombre().equals(vuelo)) {
	                // Agregamos la reserva a los datos del vuelo
	                datosVuelo.addReserva(v1.getDTReservaVuelo());
	            }
	        });
	    });
			
		return datosVuelo;
	}
	
	public DTCiudad listarInfoCiudad(String ciudad, String pais) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		Ciudad ciudadElegida = ManV.obtenerCiudad(ciudad, pais);

		return ciudadElegida.getDTCiudad();
	}
	
	//combiné seleccionarAerolinea, ingresarDatosRutaVuelo y altaRutaVuelo
	//para AltaVuelo
	@Override
	public void ingresarDatosRutaVuelo(String Aerolinea, String nombre, String imagen, String desc, String descCorta, 
			LocalTime hora, LocalDate fechaAlta, float costoExtra, float costoEje, float costoTur, String ciudadOrigenStr, 
			String paisOrigen, String ciudadDestinoStr, String paisDestino, Map<String, Categoria> categorias) throws YaRegistradoException {
		
		ManejadorUsuario ManU = ManejadorUsuario.getInstancia();
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		
		Aerolinea aerolinea = (Aerolinea) ManU.getUsuario(Aerolinea);
		

		Ciudad ciudadOrigen = ManV.obtenerCiudad(ciudadOrigenStr, paisOrigen);
		Ciudad ciudadDestino = ManV.obtenerCiudad(ciudadDestinoStr, paisDestino);

		
		if (imagen == null) {			
			imagen = "RV.png";
		}
		
		boolean existe = ManV.existeRutaVuelo(nombre);
		
		if (!existe) {
			RutaVuelo rutaVuelo = new RutaVuelo(nombre, imagen, descCorta, desc, hora, fechaAlta, costoExtra, costoEje, costoTur, ciudadOrigen, ciudadDestino, categorias);
			rutaVuelo.setCategorias(categorias);
			
			ManV.agregarRutaVuelo(rutaVuelo);
			aerolinea.agregarRutaVuelo(rutaVuelo);
		} else throw new YaRegistradoException("El nombre de la ruta de vuelo ya esta en uso");
	}
	
	public void ingresarDatosVuelo(String imagen, String nombre, LocalDate fecha, LocalTime duracion, Integer maxAsientosTurista,
									Integer maxAsientosEjecutivo, LocalDate fechaAlta) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		
		if (imagen == null) {
			imagen = "VU.jpg";
		}
		
		Vuelo vuelo = new Vuelo(imagen, nombre, fechaAlta, fecha, duracion, maxAsientosTurista, maxAsientosEjecutivo);
		ManV.agregarVuelo(vuelo);
	}
	
	public void agregarRutaVueloAAerolinea(String aerolinea, RutaVuelo rutaVuelo) {
		ManejadorUsuario ManU = ManejadorUsuario.getInstancia();
		Aerolinea aero = (Aerolinea) ManU.getUsuario(aerolinea);
		aero.agregarRutaVuelo(rutaVuelo);
	}
	
	//combiné ingresarDatosCategoria y confirmarAltaCategoria
	//para AltaCategoria
	public void ingresarDatosCategoria(String categoria) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		boolean existe = ManV.existeCategoria(categoria);
		
		if (!existe) {
			Categoria cat = new Categoria(categoria);
			ManV.agregarCategoria(cat);
		}
	}
	
	//combiné ingresarDatosCiudad y confirmarAltaCiudad
	//para AltaCategoria
	@Override
	public void ingresarDatosCiudad(String nombre, String pais, String nombreAeropuerto, String desc, String sitioWeb, LocalDate fechaAlta) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		boolean existe = ManV.existeCiudad(nombre, pais);
		
		if (!existe) {
			Ciudad ciudad = new Ciudad(nombre, pais, nombreAeropuerto, desc, sitioWeb, fechaAlta);
			ManV.agregarCiudad(ciudad);
		}
	}
	
	public Float calcularCostoReserva(String nombreRuta, EnumAsiento tipoAsiento, Integer cantPasajes, Integer cantEquipajeExtra) {
		Float costoTotal;
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		RutaVuelo ruta = manV.getRutasVuelo().get(nombreRuta);
		
		Float costoAsiento = EnumAsiento.TURISTA.equals(tipoAsiento) ? ruta.getCostoBaseTurista() : ruta.getCostoBaseEjecutivo();
		
		costoTotal = (costoAsiento * cantPasajes) + (cantEquipajeExtra * ruta.getCostoExtra());
		
		return costoTotal;
	}
	
	
	// Reserva con paquete
	public Float calcularCostoReserva(String nombreRuta, EnumAsiento tipoAsiento, Integer cantPasajes, Integer cantEquipajeExtra, Integer descuentoPaquete) {
		Float costoTotal;
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		RutaVuelo ruta = manV.getRutasVuelo().get(nombreRuta);
		
		Float costoAsiento = EnumAsiento.TURISTA.equals(tipoAsiento) ? ruta.getCostoBaseTurista() : ruta.getCostoBaseEjecutivo();
		
		costoTotal = (costoAsiento * cantPasajes) * (1 - (descuentoPaquete/100.0f))+ (cantEquipajeExtra * ruta.getCostoExtra());
		
		return costoTotal;
	}
	
	//combiné seleccionarVuelo, seleccionarCliente y crearReserva
	//para reservaVuelo
	public void crearReserva(String nombreVuelo, String cliente, EnumAsiento tipoAsiento,
			List<DTPasajero> acomp, Integer equipajeExtra, LocalDate fecha) throws YaRegistradoException {

		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		Vuelo vuelo = manV.obtenerVuelo(nombreVuelo);
		
		Map<Integer, ReservaVuelo> reservasCliente = manU.getClientes().get(cliente).getReservasVuelo();
		if (reservasCliente != null && !reservasCliente.isEmpty()) {
			Iterator<Integer> iterator = reservasCliente.keySet().iterator();
			
			while (iterator.hasNext()) {
				int key = iterator.next();
				if ( nombreVuelo.equals(reservasCliente.get(key).getVuelo().getNombre()))
					throw new YaRegistradoException("El cliente " + "ya a hecho una reserva al vuelo " + nombreVuelo);
				}
		}

		Float costoTotal = calcularCostoReserva(vuelo.getRutaDeVuelo().getNombre(), tipoAsiento, acomp.size(), equipajeExtra);

		ReservaVuelo nuevaReserva = new ReservaVuelo(generarID(), vuelo, fecha, 
				tipoAsiento, acomp, equipajeExtra, costoTotal);
		
		manU.getClientes().get(cliente).agregarReserva(nuevaReserva);
		
	}

	// Crear reserva con paquete
	public void crearReserva(String nombreVuelo, String nickCliente, EnumAsiento tipoAsiento, 
			List<DTPasajero> acomp, Integer equipajeExtra, LocalDate fecha, String nombrePaquete) throws YaRegistradoException, NoExisteException, OperacionInvalidaException {

		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		Cliente cliente = (Cliente) manU.getUsuario(nickCliente);
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		
		if (!manV.existeVuelo(nombreVuelo)) {
			throw new NoExisteException("No existe el vuelo ingresado");
		}
		Vuelo vuelo = manV.obtenerVuelo(nombreVuelo);
		RutaVuelo ruta = vuelo.getRutaDeVuelo();
			
		Map<Integer, ReservaVuelo> reservasCliente = cliente.getReservasVuelo();
		if (reservasCliente != null && !reservasCliente.isEmpty()) {
			Iterator<Integer> iterator = reservasCliente.keySet().iterator();
			
			while (iterator.hasNext()) {
				int key = iterator.next();
				if ( nombreVuelo == reservasCliente.get(key).getVuelo().getNombre())
					throw new YaRegistradoException("El cliente " + "ya a hecho una reserva al vuelo " + nombreVuelo);
				}
		}
		
		Map<String, CompraPaquete> comprasPaqs = cliente.getComprasDePaquetes();
		CompraPaquete compraPaq = comprasPaqs.get(nombrePaquete);
		// Chequeo que el cliente haya comprado el paquete
		if (compraPaq == null) {
			throw new NoExisteException("No existe una compra del paquete " + nombrePaquete + " para el cliente " + cliente.getNickname());
		}
		// Chequeo que el paquete tenga usos disponibles para todos los pasajeros
		ControladorUsuario ctrlUsuario = new ControladorUsuario();
		Integer usosRest = ctrlUsuario.usosRestantesRutaEnPaqueteParaCliente(nickCliente, ruta.getNombre(), nombrePaquete);
		if (acomp.size() > usosRest) {
			throw new OperacionInvalidaException("No tiene rutas disponibles en el paquete para todos los pasajeros.");
		}
		// Chequeo que coincida el tipo de asiento
		PaqueteRutaVuelo rutaEnPaq = compraPaq.getPaqueteDeCompra().getPaqueteRutaVuelo(ruta.getNombre());
		if (!tipoAsiento.equals(rutaEnPaq.getTipoAsiento())) {
			throw new OperacionInvalidaException("El tipo de asiento debe coincidir con el de la ruta en el paquete.");
		}
		
		Float costoTotal = calcularCostoReserva(vuelo.getRutaDeVuelo().getNombre(), tipoAsiento, acomp.size(), equipajeExtra, compraPaq.getPaqueteDeCompra().getDescuento());
		
		ReservaVuelo nuevaReserva = new ReservaVuelo(generarID(), vuelo, fecha, 
				tipoAsiento, acomp, equipajeExtra, costoTotal, compraPaq);
		
		cliente.agregarReserva(nuevaReserva);
		
	}
	
	// La cantidad de reservas de vuelo existentes +1
	// no funciona si se pueden borrar vuelo/reservas de vuelo
	public int generarID() {
		int nuevoID = 1;
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		Map<String, Cliente> clientes = manU.getClientes();
		Iterator<String> it1 = clientes.keySet().iterator();
		
		while (it1.hasNext()) {
			String key = it1.next();
			Map<Integer, ReservaVuelo> reservasCliente = clientes.get(key).getReservasVuelo();
			nuevoID += reservasCliente.size();
		}
			
		return nuevoID;
	}
	
	public String[] listarCiudades(){
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		Map<Pair<String, String>, Ciudad> ciudades = ManV.getCiudades();
		
		String[] nombreCiudades = new String[ciudades.size()];
		int indice = 0;
		for (Map.Entry<Pair<String, String>, Ciudad> entry : ciudades.entrySet()) {
			nombreCiudades[indice] = entry.getValue().getNombre() + ", " +entry.getValue().getPais();
			indice++;
		}
		
		Arrays.sort(nombreCiudades);
		
		return nombreCiudades;
	}

	@Override
	public String obtenerCategoria(String categoria) {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		Categoria cat = ManV.obtenerCategoria(categoria);
		
		return cat.getNombre();
	}
	
	public String[] listarCategorias() {
		ManejadorVuelo ManV = ManejadorVuelo.getInstancia();
		Map<String, Categoria> categorias = ManV.getCategorias();
		
		String[] nombreCategorias = new String[categorias.size()];
		int indice = 0;
		for (Map.Entry<String, Categoria> entry : categorias.entrySet()) {
			nombreCategorias[indice] = entry.getValue().getNombre();
			indice++;
		}
		
		Arrays.sort(nombreCategorias);
		
		return nombreCategorias;
	}

	@Override
	public List<DTPaquete> obtenerPaquetesDeRutaVuelo(String rutaVuelo) {
		Fabrica fabrica = Fabrica.getInstance();
		IPaquete iPaquete = fabrica.getIPaquete();
		
		List<DTPaquete> paquetesRV = new ArrayList<>();
		
		List<DTPaquete> paquetes = iPaquete.listarDTPaquetes();

		for (DTPaquete paquete : paquetes) {
			List<DTRutaVueloPaquete> rutas = paquete.getRutasVuelo();
			for (DTRutaVueloPaquete DTRuta : rutas) {
				if (rutaVuelo.equals(DTRuta.getNombre())) {
					paquetesRV.add(paquete);
				}
			}
		}
		
		return paquetesRV;
	}
	
	public DTRutaVueloAerolinea obtenerDTRutaVueloAerolinea(String nombreRuta) {
		ManejadorVuelo manejadorVuelo = ManejadorVuelo.getInstancia();
		RutaVuelo ruta = manejadorVuelo.obtenerRutaVuelo(nombreRuta);
		if (ruta == null) return null;
		ControladorUsuario contUsr = new ControladorUsuario();
		String nickAerolinea = contUsr.obtenerNickAerolineaDeRutaVuelo(nombreRuta);
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
		Aerolinea aerolinea = (Aerolinea) manejadorUsuario.getUsuario(nickAerolinea);
		Map<String, Categoria> categorias = ruta.getCategorias();
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
		return new DTRutaVueloAerolinea(nombreRuta, ruta.getDescripcion(), ruta.getDescripcionCorta(), ruta.getOrigen().getNombre(), ruta.getDestino().getNombre(), cats, ruta.getImagen(), nickAerolinea, aerolinea.getNombre());
	}
	
	public void rechazarRutaVuelo(String ruta) {
		ManejadorVuelo manejadorVuelo = ManejadorVuelo.getInstancia();
		manejadorVuelo.obtenerRutaVuelo(ruta).rechazar();
	}
	
	public void aceptarRutaVuelo(String ruta) {
		ManejadorVuelo manejadorVuelo = ManejadorVuelo.getInstancia();
		manejadorVuelo.obtenerRutaVuelo(ruta).aceptar();
	}
	
	

	
}
