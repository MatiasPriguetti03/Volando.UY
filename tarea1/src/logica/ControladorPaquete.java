package logica;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import excepciones.NoExisteException;
import excepciones.YaExisteException;
import excepciones.YaRegistradoException;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTInfoPaquete;
import logica.enums.EnumAsiento;

public class ControladorPaquete implements IPaquete{

	public void crearPaquete(String nombre, String imagen, String descripcion, Integer validez, Integer descuento, LocalDate fechaActual) throws YaExisteException {
		ManejadorPaquete manejador = ManejadorPaquete.getInstancia();
		if (manejador.existePaquete(nombre)) {
			throw new YaExisteException("Ya existe un paquete con el nombre ingresado.");
		}
		Paquete paquete = new Paquete(nombre, imagen, descripcion, validez, descuento, fechaActual);
		manejador.agregarPaquete(paquete);
	}
	
	public List<DTInfoPaquete> listarPaquetes(){
		ManejadorPaquete manejador = ManejadorPaquete.getInstancia();
		List<DTInfoPaquete> listaDTPaq = new ArrayList<>();
		Map<String, Paquete> paquetes =manejador.getPaquetes();
		for (Map.Entry<String, Paquete> entry : paquetes.entrySet()) {
			Paquete paq = entry.getValue();
			listaDTPaq.add(paq.getDTInfoPaquete());
		}
		
		return listaDTPaq;
	}
	
	public List<DTInfoPaquete> listarPaquetesNoComprados(){
		List<DTInfoPaquete> todosLosPaquetes = listarPaquetes();
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstancia();
		Map<String, Cliente> mapClientes = manejadorU.getClientes();

		List<DTInfoPaquete> paquetesComprados = new ArrayList<DTInfoPaquete>();
		for (DTInfoPaquete dtPaquete : todosLosPaquetes) {
			for (Map.Entry<String, Cliente> entryCliente : mapClientes.entrySet()) {
				Cliente cliente = entryCliente.getValue();
				if (cliente.comproPaquete(dtPaquete.getNombre())) {
					paquetesComprados.add(dtPaquete);
					break;
				}
			}		
		}
		todosLosPaquetes.removeAll(paquetesComprados);
		return todosLosPaquetes;
	}
	
	public List<DTInfoPaquete> listarPaquetesConRutas(){
		ManejadorPaquete manejador = ManejadorPaquete.getInstancia();
		List<DTInfoPaquete> listaDTPaq = new ArrayList<>();
		Map<String, Paquete> paquetes =manejador.getPaquetes();
		for (Map.Entry<String, Paquete> entry : paquetes.entrySet()) {
			Paquete paq = entry.getValue();
			if (!paq.getPaquetesRutaVuelo().isEmpty()) {				
				listaDTPaq.add(paq.getDTInfoPaquete());
			}
		}
		return listaDTPaq;
	}
	
	public List<DTPaquete> listarDTPaquetes() {
		ManejadorPaquete manP = ManejadorPaquete.getInstancia();
		
		List<DTPaquete> listaP = new ArrayList<>();
		Map<String, Paquete> paquetes = manP.getPaquetes(); 
		
		for (Map.Entry<String, Paquete> paquete : paquetes.entrySet()) {
			listaP.add(paquete.getValue().getDTPaquete());
		}	    		
		 
		Collections.sort(listaP, Comparator.comparing(DTPaquete::getNombre));
		
		return listaP;
	}
	
	public DTPaquete obtenerDatosPaquete(String nombrePaq) throws NoExisteException {
		ManejadorPaquete manejador = ManejadorPaquete.getInstancia();
		if (!manejador.existePaquete(nombrePaq)) {
			throw new NoExisteException("No existe un paquete con el nombre ingresado.");
		}
		Paquete paquete = manejador.obtenerPaquete(nombrePaq);
		return paquete.getDTPaquete();
	}
	
	public void agregarRutaVuelo(String nombrePaq, String nombreRV, EnumAsiento tipoAsiento, int cantidad) throws NoExisteException, YaRegistradoException {
		ManejadorPaquete manejadorP = ManejadorPaquete.getInstancia();
		if (!manejadorP.existePaquete(nombrePaq)) {
			throw new NoExisteException("No existe un paquete con el nombre: " + nombrePaq);
		}
		Paquete paquete = manejadorP.obtenerPaquete(nombrePaq);
		
		// Verifica que no haya sido comprado
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstancia();
		Map<String, Cliente> mapClientes = manejadorU.getClientes();
		for (Map.Entry<String, Cliente> entryCliente : mapClientes.entrySet()) {
			Cliente cliente = entryCliente.getValue();
			if (cliente.comproPaquete(nombrePaq)) {
				throw new YaRegistradoException("El paquete: " + nombrePaq + " ya fue comprado");
			}
		}
		
		ManejadorVuelo manejadorV = ManejadorVuelo.getInstancia();
		if (!manejadorV.existeRutaVuelo(nombreRV)) {
			throw new NoExisteException("No existe una ruta de vuelo con el nombre: " + nombreRV);
		}
		RutaVuelo rutavuelo = manejadorV.obtenerRutaVuelo(nombreRV);
		if (!paquete.existePaqueteRutaVuelo(nombreRV)) {
			PaqueteRutaVuelo paqRV = new PaqueteRutaVuelo(cantidad, tipoAsiento);
			paqRV.crearLinkPaqueteRutaVuelo(rutavuelo);
			paquete.crearLinkPaqueteRutaVuelo(paqRV);
		} else {
			// Si ya esta la ruta de vuelo en el paquete da error siempre
			throw new YaRegistradoException("Ya existe la Ruta de Vuelo en el Paquete");
//			}
		}
		
		Float costo = paquete.getCosto();
		if (tipoAsiento == EnumAsiento.TURISTA) {
			costo += rutavuelo.getCostoBaseTurista()*cantidad*(100-paquete.getDescuento())/100;
		} else {
			costo += rutavuelo.getCostoBaseEjecutivo()*cantidad*(100-paquete.getDescuento())/100;
		}
		paquete.setCosto(costo);
	}
	
	public void comprarPaquete(String nickCient, LocalDate fechaComp, String nombrePaq) throws NoExisteException, YaRegistradoException{
		ManejadorPaquete manejadorP = ManejadorPaquete.getInstancia();
		if (!manejadorP.existePaquete(nombrePaq)) {
			throw new NoExisteException("No existe un paquete con el nombre: " + nombrePaq);
		}
		Paquete paquete = manejadorP.obtenerPaquete(nombrePaq);
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstancia();
		if (!manejadorU.existeUsuarioNick(nickCient)) {
			throw new NoExisteException("No existe un cliente con el nickname: " + nickCient);
		}
		Cliente cliente = (Cliente) manejadorU.getUsuario(nickCient);
		if (cliente.comproPaquete(nombrePaq)) {
			throw new YaRegistradoException("El cliente ya compro el paquete seleccionado");
		}
		CompraPaquete compra = new CompraPaquete(fechaComp, paquete);
		cliente.agregarCompraPaquete(compra);
	}
//	
//	public DTInfoPaquete obtenerInfoPaquete(String nombre) {
//		return null;
//	}
}
