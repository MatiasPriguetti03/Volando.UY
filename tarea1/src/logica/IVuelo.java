package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.List;
import java.util.Set;

import excepciones.YaRegistradoException;
import excepciones.EsConjuntoVacioException;
import excepciones.NoExisteException;
import excepciones.OperacionInvalidaException;
import logica.datatypes.DTCiudad;
import logica.datatypes.DTPasajero;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTRutaVueloAerolinea;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTVuelo;
import logica.enums.EnumAsiento;

public interface IVuelo {

	Set<DTInfoRutaVuelo> listarRutasVuelosAerolinea(String aerolineaStr) throws EsConjuntoVacioException;
	
	Set<DTRutaVuelo> listarRutasVuelos() throws EsConjuntoVacioException;
	
	void agregarVueloARuta(String nombreRuta, Vuelo vuelo);

	DTRutaVuelo obtenerDTRutaVuelo(String rutaVuelo);

	String[] listarVuelos(String rutaVuelo) throws EsConjuntoVacioException;
	
	List<DTVuelo> listarDTVuelos();
	
	List<DTVuelo> listarDTVuelosRC();

	List<DTRutaVuelo> listarDTRutasVuelo();
	
	List<DTRutaVuelo> listarDTRutasVueloConfirmadas();
	
	List<DTRutaVuelo> listarDTRutasVueloConfirmadas(String categoria);
	
	List<DTRutaVuelo> listarDTRutasVuelo(String aerolinea, String categoria);
	
	List<DTRutaVuelo> listarDTRutasVueloConfirmadas(String aerolinea, String categoria);
	
	List<DTReservaVuelo> listarDTReservasUsuario(String nickUsuario) throws NoExisteException;
		
	DTVuelo obtenerDTVuelo(String vuelo);

	void ingresarDatosRutaVuelo(String Aerolinea, String nombre, String imagen, String desc, String descCorta, 
			LocalTime hora, LocalDate fechaAlta, float costoExtra, float costoEje, float costoTur, String ciudadOrigen, 
			String paisOrigen, String ciudadDestino, String paisDestino, Map<String, Categoria> categorias) throws YaRegistradoException;

	void ingresarDatosCategoria(String categoria);

	void ingresarDatosCiudad(String nombre, String pais, String nombreAeropuerto, String desc, String sitioWeb,
			LocalDate fechaAlta);
	
	void crearReserva(String vuelo, String cliente, EnumAsiento tipoAsiento,
			List<DTPasajero> acomp, Integer equipajeExtra, LocalDate fecha) throws YaRegistradoException;

	void crearReserva(String vuelo, String cliente, EnumAsiento tipoAsiento,
			List<DTPasajero> acomp, Integer equipajeExtra, LocalDate fecha, String nombrePaquete) throws YaRegistradoException, NoExisteException, OperacionInvalidaException;
	
//	float calcularCosto(String ruta, EnumAsiento tipoAsiento, int cantPasajes, int cantEquipajeExtra);
	
	void ingresarDatosVuelo(String imagen, String nombre, LocalDate fecha, LocalTime duracion, Integer maxAsientosTurista,
				Integer maxAsientosEjecutivo, LocalDate fechaAlta);

	DTCiudad listarInfoCiudad(String ciudad, String pais);

	String[] listarCiudades();

	//Que hace?
	String obtenerCategoria(String categoria);

	String[] listarCategorias();

	void agregarRutaVueloAAerolinea(String aerolinea, RutaVuelo rutaVuelo);

	public void agregarVueloARuta(String imagen, String nombreRuta, String nombreVuelo, LocalDate fechaVuelo, LocalTime duracion, 
			int maxEje, int maxTur, LocalDate fechaAlta) throws YaRegistradoException;

	public List<DTPaquete> obtenerPaquetesDeRutaVuelo(String rutaVuelo);

	public DTRutaVueloAerolinea obtenerDTRutaVueloAerolinea(String name);

	void rechazarRutaVuelo(String ruta);

	void aceptarRutaVuelo(String ruta);
}
