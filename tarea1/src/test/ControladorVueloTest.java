package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.NoExisteException;
import excepciones.OperacionInvalidaException;
import excepciones.YaExisteException;
import excepciones.YaRegistradoException;
import excepciones.EsConjuntoVacioException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.PresentacionUtils;

import logica.Fabrica;
import logica.IUsuario;
import logica.IPaquete;
import logica.IVuelo;
import logica.ManejadorPaquete;
import logica.ManejadorUsuario;
import logica.ManejadorVuelo;
import logica.datatypes.Pair;
import logica.Categoria;
import logica.Ciudad;
import logica.RutaVuelo;
import logica.Vuelo;
import logica.datatypes.DTCiudad;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTVuelo;
import logica.datatypes.DTPasajero;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVueloAerolinea;
import logica.enums.*;


public class ControladorVueloTest {
	
	private static IVuelo iVuelo;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		Fabrica fabrica = Fabrica.getInstance();
		iVuelo = fabrica.getIVuelo();
	}
	
	@AfterEach
	public void tearDownAfterClass() {
		ManejadorUsuario MU = ManejadorUsuario.getInstancia();
		MU.borrarUsuarios();
        ManejadorPaquete MP = ManejadorPaquete.getInstancia();
        MP.limpiarPaquetes();
        ManejadorVuelo MV = ManejadorVuelo.getInstancia();
        MV.limpiarColecciones();
	}
	
	@Test
	void ingresarDatosCiudadTest() throws YaRegistradoException {
	    String nombre = "Montevideo";
        String pais = "Uruguay";
        String nombreAeropuerto = "Aeropuerto1";
        String desc = "Descripcion";
        String sitioWeb = "www.aeropuerto.com";
        LocalDate fechaAlta = PresentacionUtils.parseDate("15/06/2021");
        
        iVuelo.ingresarDatosCiudad(nombre, pais, nombreAeropuerto, desc, sitioWeb, fechaAlta);
		DTCiudad ciudad = iVuelo.listarInfoCiudad(nombre,pais);
		
		assertEquals(ciudad.getNombre(), nombre);
		assertEquals(ciudad.getDescripcion(), desc);
		assertEquals(ciudad.getFechaDeAlta(), fechaAlta);
		assertEquals(ciudad.getAeropuerto(), nombreAeropuerto);
		assertEquals(ciudad.getPais(), pais);
		assertEquals(ciudad.getSitioWeb(), sitioWeb);
		
		String[] ciudades = iVuelo.listarCiudades();
		assertEquals(1, ciudades.length);
		assertEquals(nombre + ", "+ pais, ciudades[0]);
	}
	
	@Test
	void ingresarDatosCategoriaTest() throws YaRegistradoException {
		String categoria = "Categoria1";
		iVuelo.ingresarDatosCategoria(categoria);
		String cat = iVuelo.obtenerCategoria(categoria);
		
		assertEquals(cat, categoria);
		
		String[] categorias = iVuelo.listarCategorias();
		assertEquals(1, categorias.length);
		assertEquals(cat, categorias[0]);
	}
	
	@Test
	void ingresarRutaVueloyPruebaSetters() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario ctrlUsuario = fabrica.getIUsuario();
		
		// Datos Ciudades
		String nombreCiudadOrigen = "Lima";
		String paisOrigen = "Peru";
		String nombreAeropuertoOrigen = "Alperu";
		String descOrigen = "Vista los Andes :)";
		String sitioWebOrigen = "www.Alperu.com.pe";
		LocalDate FechaAltaOrigen = PresentacionUtils.parseDate("14/05/2024");
		
		String nombreCiudadDestino = "Ottawa";
		String paisDestino = "Canada";
		String nombreAeropuertoDestino = "Canadian Airlines";
		String descDestino = "The best option to travel around the world";
		String sitioWebDestino = "www.CanAirlines.com.ca";
		LocalDate FechaAltaDestino = PresentacionUtils.parseDate("01/03/2024");
		
		iVuelo.ingresarDatosCiudad(nombreCiudadOrigen, paisOrigen, nombreAeropuertoOrigen, descOrigen, sitioWebOrigen, FechaAltaOrigen);
		iVuelo.ingresarDatosCiudad(nombreCiudadDestino, paisDestino, nombreAeropuertoDestino, descDestino, sitioWebDestino, FechaAltaDestino);
		
		Pair<String, String> ciudadOrigenPair = new Pair<String, String>(nombreCiudadOrigen, paisOrigen);
		Pair<String, String> ciudadDestinoPair = new Pair<String, String>(nombreCiudadDestino, paisDestino);
				
		Ciudad ciudadOrigen = manV.getCiudades().get(ciudadOrigenPair);
		Ciudad ciudadDestino = manV.getCiudades().get(ciudadDestinoPair);
		
		//Datos Ruta
		String nombreRuta = "UKG4371";
		String imagen = "imagen5.jpg";
		String descripcionRuta = "Un viaje que siempre quisieras hacer, con un elmozo destino llamado: no se";
		String descripcionCorta = "Un viaje que siempre quisieras hacer";
		LocalTime hora = PresentacionUtils.parseTime("06:00");
		float costoExtra = 120;
		float costoEje = 4500;
		float costoTur = 2300;
		LocalDate fechaAltaRuta = PresentacionUtils.parseDate("02/06/2024");
		Map<String, Categoria> categorias = new HashMap<String, Categoria>();
		categorias.put("Invernal", new Categoria("Invernal"));
		categorias.put("Turistico", new Categoria("Turistico"));
		
		RutaVuelo ruta = new RutaVuelo(nombreRuta, imagen, descripcionRuta, descripcionCorta, hora, fechaAltaRuta, 
										costoExtra, costoEje, costoTur, ciudadOrigen,ciudadDestino, categorias);
		
		assertEquals(ruta.getNombre(), nombreRuta);
		assertEquals(ruta.getDescripcion(), descripcionRuta);
		assertEquals(ruta.getHora(), hora);
		assertEquals(ruta.getCostoExtra(), costoExtra);
		assertEquals(ruta.getCostoBaseEjecutivo(), costoEje);
		assertEquals(ruta.getCostoBaseTurista(), costoTur);
		assertEquals(ruta.getFechaAlta(), fechaAltaRuta);
		assertEquals(ruta.getOrigen(), ciudadOrigen);
		assertEquals(ruta.getDestino(), ciudadDestino);
	}
	
	@Test
	void ingresarRutaVueloYVerAerolineaTest() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario ctrlUsuario = fabrica.getIUsuario();
		
		// Datos Aerolinea
		String aerolinea = "CanAirlines1";
		String nombreAerolinea = "Canadian Airline1";
		String emailAerolinea = "CanAirlines@email.com1";
		String descripcion = "Need to travel? Come fly with us";
		String sitioWeb = "";
		String contraseniaAerolinea = "";	
		
		try {
			ctrlUsuario.ingresarAerolinea(aerolinea, nombreAerolinea,  "", emailAerolinea, contraseniaAerolinea, descripcion, sitioWeb);
		}
		catch(Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		// Datos Ciudades
		String nombreCiudadOrigen = "Lima";
		String paisOrigen = "Peru";
		String nombreAeropuertoOrigen = "Alperu";
		String descOrigen = "Vista los Andes :)";
		String sitioWebOrigen = "www.Alperu.com.pe";
		LocalDate FechaAltaOrigen = PresentacionUtils.parseDate("14/05/2024");
		
		String nombreCiudadDestino = "Ottawa";
		String paisDestino = "Canada";
		String nombreAeropuertoDestino = "Canadian Airlines";
		String descDestino = "The best option to travel around the world";
		String sitioWebDestino = "www.CanAirlines.com.ca";
		LocalDate FechaAltaDestino = PresentacionUtils.parseDate("01/03/2024");
		
		iVuelo.ingresarDatosCiudad(nombreCiudadOrigen, paisOrigen, nombreAeropuertoOrigen, descOrigen, sitioWebOrigen, FechaAltaOrigen);
		iVuelo.ingresarDatosCiudad(nombreCiudadDestino, paisDestino, nombreAeropuertoDestino, descDestino, sitioWebDestino, FechaAltaDestino);
		
		Pair<String, String> ciudadOrigenPair = new Pair<String, String>(nombreCiudadOrigen, paisOrigen);
		Pair<String, String> ciudadDestinoPair = new Pair<String, String>(nombreCiudadDestino, paisDestino);
				
		Ciudad ciudadOrigen = manV.getCiudades().get(ciudadOrigenPair);
		Ciudad ciudadDestino = manV.getCiudades().get(ciudadDestinoPair);
		
		//Datos Ruta
		String nombreRuta = "UKG4371";
		String imagen = "imagen4.jpg";
		String descripcionCorta = "Un viaje que siempre quisieras hacer";
		String descripcionRuta = "Un viaje que siempre quisieras hacer, con un elmozo destino llamado: no se";
		LocalTime hora = PresentacionUtils.parseTime("06:00");
		float costoExtra = 120;
		float costoEje = 4500;
		float costoTur = 2300;
		LocalDate fechaAltaRuta = PresentacionUtils.parseDate("02/06/2024");
		Map<String, Categoria> categorias = new HashMap<String, Categoria>();
		categorias.put("Invernal", new Categoria("Invernal"));
		categorias.put("Turistico", new Categoria("Turistico"));
		
		RutaVuelo ruta = new RutaVuelo(nombreRuta, imagen, descripcionRuta, descripcionCorta, hora, fechaAltaRuta, 
										costoExtra, costoEje, costoTur, ciudadOrigen,ciudadDestino, categorias);
		manV.agregarRutaVuelo(ruta);
		iVuelo.agregarRutaVueloAAerolinea(aerolinea, ruta);
		try {
			String nickAerolineaRV = ctrlUsuario.obtenerNickAerolineaDeRutaVuelo(nombreRuta);
			assertEquals(aerolinea, nickAerolineaRV);

		//Datos iniciales Vuelo
		String nombre = "IDK404!";
		LocalTime duracion = PresentacionUtils.parseTime("05:00");
		int cantTur = 130;
		int cantEjec = 24;
		LocalDate fechaVuelo = PresentacionUtils.parseDate("17/08/2024");
		LocalDate fechaAlta = PresentacionUtils.parseDate("09/09/2024");
		
		iVuelo.agregarVueloARuta("", nombreRuta, nombre, fechaVuelo, duracion, cantTur, cantEjec, fechaAlta);
		
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	void agregarVueloARutaConVueloTest() {
	    // Configuración de instancias necesarias
	    ManejadorVuelo manV = ManejadorVuelo.getInstancia();
	    Fabrica fabrica = Fabrica.getInstance();
	    IVuelo iVuelo = fabrica.getIVuelo();
	    IUsuario ctrlUsuario = fabrica.getIUsuario();

	    // Crear ciudades de origen y destino
	    String ciudadOrigen = "Santiago";
	    String paisOrigen = "Chile";
	    String ciudadDestino = "Lima";
	    String paisDestino = "Perú";
	    LocalDate fechaAltaCiudad = LocalDate.now();
	    iVuelo.ingresarDatosCiudad(ciudadOrigen, paisOrigen, "Aeropuerto SCL", "Descripción del aeropuerto", "www.aeropuertoscl.cl", fechaAltaCiudad);
	    iVuelo.ingresarDatosCiudad(ciudadDestino, paisDestino, "Aeropuerto LIM", "Descripción del aeropuerto", "www.aeropuertolim.pe", fechaAltaCiudad);

	    // Crear una categoría
	    String categoria = "Regional";
	    iVuelo.ingresarDatosCategoria(categoria);
	    Map<String, Categoria> categorias = new HashMap<>();
	    categorias.put(categoria, new Categoria(categoria));

	    // Registrar una aerolínea necesaria para crear la ruta
	    String aerolineaNick = "LAN";
	    String nombreAerolinea = "LAN Airlines";
	    String emailAerolinea = "info@lan.com";
	    String descripcionAerolinea = "Descripción de LAN";
	    String sitioWebAerolinea = "www.lan.com";
	    String contraseniaAerolinea = "password";
	    try {
	        ctrlUsuario.ingresarAerolinea(aerolineaNick, nombreAerolinea, "", emailAerolinea, contraseniaAerolinea, descripcionAerolinea, sitioWebAerolinea);
	    } catch (Exception e) {
	        fail("Fallo al registrar aerolínea: " + e.getMessage());
	    }

	    // Ingresar la ruta de vuelo
	    String nombreRuta = "RutaSCL-LIM";
	    String imagenRuta = "rutaSCL-LIM.jpg";
	    String descripcionRuta = "Ruta de Santiago a Lima";
	    String descripcionCortaRuta = "SCL - LIM";
	    LocalTime horaRuta = LocalTime.of(8, 0);
	    float costoExtra = 30;
	    float costoEje = 400;
	    float costoTur = 200;
	    LocalDate fechaAltaRuta = LocalDate.now();

	    try {
	    iVuelo.ingresarDatosRutaVuelo(aerolineaNick, nombreRuta, imagenRuta, descripcionRuta, descripcionCortaRuta, horaRuta, fechaAltaRuta,
	            costoExtra, costoEje, costoTur, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
	    } catch(YaRegistradoException e) {
	    	//
	    }
	    
	    // Crear un objeto Vuelo
	    String imagenVuelo = "vueloSCL-LIM.jpg";
	    String nombreVuelo = "VueloSCL-LIM-001";
	    LocalDate fechaVuelo = LocalDate.now().plusDays(5);
	    LocalTime duracionVuelo = LocalTime.of(3, 30);
	    int maxEje = 15;
	    int maxTur = 100;
	    LocalDate fechaAltaVuelo = LocalDate.now();

	    iVuelo.ingresarDatosVuelo(imagenVuelo, nombreVuelo, fechaVuelo, duracionVuelo, maxTur, maxEje, fechaAltaVuelo);
	    Vuelo vuelo = manV.obtenerVuelo(nombreVuelo);
	    assertNotNull(vuelo, "El vuelo no debería ser nula");

	    // Llamar al método agregarVueloARuta a través de la interfaz IVuelo
	    iVuelo.agregarVueloARuta(nombreRuta, vuelo);

	    // Verificar que el vuelo fue agregado correctamente a la ruta
	    DTRutaVuelo dtRutaVuelo = iVuelo.obtenerDTRutaVuelo(nombreRuta);
	    assertNotNull(dtRutaVuelo, "La ruta de vuelo no debería ser nula");
	    
	    // Verificar que el vuelo aparece en la lista de vuelos de la ruta
	    assertTrue(dtRutaVuelo.getDTVuelos().stream()
	        .anyMatch(dtVuelo -> dtVuelo.getName().equals(nombreVuelo)),
	        "El vuelo debería estar en la lista de vuelos de la ruta");

	    // Verificar que el vuelo tiene asignada la ruta de vuelo correcta
	    assertEquals(nombreRuta, vuelo.getRutaDeVuelo().getNombre(),
	        "La ruta asignada al vuelo debería ser " + nombreRuta);

	    // Verificar que el vuelo fue agregado al ManejadorVuelo
	    Vuelo vueloObtenido = manV.obtenerVuelo(nombreVuelo);
	    assertNotNull(vueloObtenido, "El vuelo debería existir en el ManejadorVuelo");
	    assertEquals(nombreVuelo, vueloObtenido.getNombre(), "El nombre del vuelo debería ser " + nombreVuelo);
	}
	
	@Test
	void listarRutasVuelosTest() {
	    // Configuración de instancias necesarias
	    ManejadorVuelo manV = ManejadorVuelo.getInstancia();
	    Fabrica fabrica = Fabrica.getInstance();
	    IVuelo iVuelo = fabrica.getIVuelo();
	    IUsuario ctrlUsuario = fabrica.getIUsuario();

	    // Probar listarRutasVuelos sin rutas
	    Set<DTRutaVuelo> todasLasRutasVacio = null;
	    try {
	    	todasLasRutasVacio = iVuelo.listarRutasVuelos();
	    	
	    } catch (EsConjuntoVacioException e) {
	    	//
	    }
	    assertNull(todasLasRutasVacio, "La debe ser nula");
	    
	    // Crear ciudades de origen y destino
	    String ciudadOrigen = "Madrid";
	    String paisOrigen = "España";
	    String ciudadDestino = "Barcelona";
	    String paisDestino = "España";
	    LocalDate fechaAltaCiudad = LocalDate.now();
	    iVuelo.ingresarDatosCiudad(ciudadOrigen, paisOrigen, "Aeropuerto de Madrid", "Descripción del aeropuerto", "www.aeropuertomadrid.es", fechaAltaCiudad);
	    iVuelo.ingresarDatosCiudad(ciudadDestino, paisDestino, "Aeropuerto de Barcelona", "Descripción del aeropuerto", "www.aeropuertobarcelona.es", fechaAltaCiudad);

	    // Crear una categoría
	    String categoria = "Nacional";
	    iVuelo.ingresarDatosCategoria(categoria);
	    Map<String, Categoria> categorias = new HashMap<>();
	    categorias.put(categoria, new Categoria(categoria));

	    // Registrar aerolíneas
	    String aerolinea1Nick = "Iberia";
	    String nombreAerolinea1 = "Iberia Airlines";
	    String emailAerolinea1 = "info@iberia.es";
	    String descripcionAerolinea1 = "Descripción de Iberia";
	    String sitioWebAerolinea1 = "www.iberia.es";
	    String contraseniaAerolinea1 = "password1";

	    String aerolinea2Nick = "Vueling";
	    String nombreAerolinea2 = "Vueling Airlines";
	    String emailAerolinea2 = "info@vueling.com";
	    String descripcionAerolinea2 = "Descripción de Vueling";
	    String sitioWebAerolinea2 = "www.vueling.com";
	    String contraseniaAerolinea2 = "password2";

	    try {
	        ctrlUsuario.ingresarAerolinea(aerolinea1Nick, nombreAerolinea1, "", emailAerolinea1, contraseniaAerolinea1, descripcionAerolinea1, sitioWebAerolinea1);
	        ctrlUsuario.ingresarAerolinea(aerolinea2Nick, nombreAerolinea2, "", emailAerolinea2, contraseniaAerolinea2, descripcionAerolinea2, sitioWebAerolinea2);
	    } catch (Exception e) {
	        fail("Fallo al registrar aerolíneas: " + e.getMessage());
	    }

	    // Ingresar rutas de vuelo para cada aerolínea
	    String nombreRuta1 = "RutaMadridBarcelona";
	    String imagenRuta1 = "rutaMadridBarcelona.jpg";
	    String descripcionRuta1 = "Ruta de Madrid a Barcelona";
	    String descripcionCortaRuta1 = "Madrid - Barcelona";
	    LocalTime horaRuta1 = LocalTime.of(9, 0);
	    float costoExtra1 = 20;
	    float costoEje1 = 200;
	    float costoTur1 = 100;
	    LocalDate fechaAltaRuta1 = LocalDate.now();

	    String nombreRuta2 = "RutaBarcelonaMadrid";
	    String imagenRuta2 = "rutaBarcelonaMadrid.jpg";
	    String descripcionRuta2 = "Ruta de Barcelona a Madrid";
	    String descripcionCortaRuta2 = "Barcelona - Madrid";
	    LocalTime horaRuta2 = LocalTime.of(15, 0);
	    float costoExtra2 = 25;
	    float costoEje2 = 220;
	    float costoTur2 = 110;
	    LocalDate fechaAltaRuta2 = LocalDate.now();

	    try {
	    	iVuelo.ingresarDatosRutaVuelo(aerolinea1Nick, nombreRuta1, imagenRuta1, descripcionRuta1, descripcionCortaRuta1, horaRuta1, fechaAltaRuta1,
	    			costoExtra1, costoEje1, costoTur1, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
	    	
	    	iVuelo.ingresarDatosRutaVuelo(aerolinea2Nick, nombreRuta2, imagenRuta2, descripcionRuta2, descripcionCortaRuta2, horaRuta2, fechaAltaRuta2,
	    			costoExtra2, costoEje2, costoTur2, ciudadDestino, paisDestino, ciudadOrigen, paisOrigen, categorias);
	    	
	    } catch(YaRegistradoException e) {
	    	//
	    }

	    // Probar listarRutasVuelosAerolinea para aerolinea1
	    try {
	        Set<DTInfoRutaVuelo> rutasAerolinea1 = iVuelo.listarRutasVuelosAerolinea(aerolinea1Nick);
	        assertNotNull(rutasAerolinea1, "La lista de rutas no debe ser nula");
	        assertEquals(1, rutasAerolinea1.size(), "La aerolínea 1 debe tener 1 ruta");

	        DTInfoRutaVuelo rutaInfo = rutasAerolinea1.iterator().next();
	        assertEquals(nombreRuta1, rutaInfo.getName(), "El nombre de la ruta debe ser " + nombreRuta1);
	    } catch (EsConjuntoVacioException e) {
	        fail("La lista de rutas no debería estar vacía: " + e.getMessage());
	    }

	    // Probar listarRutasVuelosAerolinea para aerolinea2
	    try {
	        Set<DTInfoRutaVuelo> rutasAerolinea2 = iVuelo.listarRutasVuelosAerolinea(aerolinea2Nick);
	        assertNotNull(rutasAerolinea2, "La lista de rutas no debe ser nula");
	        assertEquals(1, rutasAerolinea2.size(), "La aerolínea 2 debe tener 1 ruta");

	        DTInfoRutaVuelo rutaInfo = rutasAerolinea2.iterator().next();
	        assertEquals(nombreRuta2, rutaInfo.getName(), "El nombre de la ruta debe ser " + nombreRuta2);
	    } catch (EsConjuntoVacioException e) {
	        fail("La lista de rutas no debería estar vacía: " + e.getMessage());
	    }

	    // Probar listarRutasVuelos 
	    Set<DTRutaVuelo> todasLasRutas = null;
	    try {
	    	todasLasRutas = iVuelo.listarRutasVuelos();
	    	
	    } catch (EsConjuntoVacioException e) {
	    	//
	    }
	    assertNotNull(todasLasRutas, "La lista de todas las rutas no debe ser nula");
	    assertEquals(2, todasLasRutas.size(), "Debe haber 2 rutas en total");

	    // Verificar que ambas rutas están en el conjunto
	    List<String> nombresRutas = todasLasRutas.stream().map(DTRutaVuelo::getName).collect(Collectors.toList());
	    assertTrue(nombresRutas.contains(nombreRuta1), "Debe contener la ruta " + nombreRuta1);
	    assertTrue(nombresRutas.contains(nombreRuta2), "Debe contener la ruta " + nombreRuta2);
	}
	
	@Test
	void listarDTReservasUsuarioYObtenerDTVueloTest() {
	    // Configuración de instancias necesarias
	    ManejadorVuelo manV = ManejadorVuelo.getInstancia();
	    ManejadorUsuario manU = ManejadorUsuario.getInstancia();
	    Fabrica fabrica = Fabrica.getInstance();
	    IVuelo iVuelo = fabrica.getIVuelo();
	    IUsuario ctrlUsuario = fabrica.getIUsuario();

	    // Crear ciudades de origen y destino
	    String ciudadOrigen = "Londres";
	    String paisOrigen = "Reino Unido";
	    String ciudadDestino = "Nueva York";
	    String paisDestino = "Estados Unidos";
	    LocalDate fechaAltaCiudad = LocalDate.now();
	    iVuelo.ingresarDatosCiudad(ciudadOrigen, paisOrigen, "Aeropuerto Heathrow", "Aeropuerto en Londres", "www.heathrow.com", fechaAltaCiudad);
	    iVuelo.ingresarDatosCiudad(ciudadDestino, paisDestino, "Aeropuerto JFK", "Aeropuerto en Nueva York", "www.jfkairport.com", fechaAltaCiudad);

	    // Crear una categoría
	    String categoria = "Internacional";
	    iVuelo.ingresarDatosCategoria(categoria);
	    Map<String, Categoria> categorias = new HashMap<>();
	    categorias.put(categoria, new Categoria(categoria));

	    // Registrar una aerolínea
	    String aerolineaNick = "BritishAirways";
	    String nombreAerolinea = "British Airways";
	    String emailAerolinea = "info@britishairways.com";
	    String descripcionAerolinea = "Descripción de British Airways";
	    String sitioWebAerolinea = "www.britishairways.com";
	    String contraseniaAerolinea = "password";
	    try {
	        ctrlUsuario.ingresarAerolinea(aerolineaNick, nombreAerolinea, "", emailAerolinea, contraseniaAerolinea, descripcionAerolinea, sitioWebAerolinea);
	    } catch (Exception e) {
	        fail("Fallo al registrar aerolínea: " + e.getMessage());
	    }

	    // Ingresar la ruta de vuelo
	    String nombreRuta = "RutaLondresNY";
	    String imagenRuta = "rutaLondresNY.jpg";
	    String descripcionRuta = "Ruta de Londres a Nueva York";
	    String descripcionCortaRuta = "Londres - Nueva York";
	    LocalTime horaRuta = LocalTime.of(10, 0);
	    float costoExtra = 60;
	    float costoEje = 600;
	    float costoTur = 300;
	    LocalDate fechaAltaRuta = LocalDate.now();

	    try {
	    	iVuelo.ingresarDatosRutaVuelo(aerolineaNick, nombreRuta, imagenRuta, descripcionRuta, descripcionCortaRuta, horaRuta, fechaAltaRuta,
	    			costoExtra, costoEje, costoTur, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
	    	
	    } catch(YaRegistradoException e) {
	    	//
	    }

	    // Crear y agregar un vuelo a la ruta
	    String imagenVuelo = "vueloLondresNY.jpg";
	    String nombreVuelo = "VueloLN-001";
	    LocalDate fechaVuelo = LocalDate.now().plusDays(7);
	    LocalTime duracionVuelo = LocalTime.of(8, 0);
	    int maxEje = 30;
	    int maxTur = 150;
	    LocalDate fechaAltaVuelo = LocalDate.now();

	    try {
	        iVuelo.agregarVueloARuta(imagenVuelo, nombreRuta, nombreVuelo, fechaVuelo, duracionVuelo, maxEje, maxTur, fechaAltaVuelo);
	    } catch (YaRegistradoException e) {
	        fail("Fallo al agregar vuelo a la ruta: " + e.getMessage());
	    }

	    // Registrar un cliente
	    String nickCliente = "john_doe";
	    String nombreCliente = "John";
	    String apellidoCliente = "Doe";
	    String imagenCliente = "img1.png";
	    String emailCliente = "john.doe@example.com";
	    String contraseniaCliente = "password123";
	    LocalDate fechaNacimientoCliente = LocalDate.of(1985, 5, 20);
	    String nacionalidadCliente = "Uruguayo";
	    String documentoCliente = "50885301";
	    try {
	        ctrlUsuario.ingresarCliente(nickCliente, nombreCliente, imagenCliente, apellidoCliente, emailCliente, contraseniaCliente, fechaNacimientoCliente, 
	                nacionalidadCliente, EnumDoc.PASAPORTE, documentoCliente);
	    } catch (Exception e) {
	        fail("Fallo al registrar cliente: " + e.getMessage());
	    }

	    // Crear una reserva para el cliente
	    List<DTPasajero> pasajeros = new ArrayList<>();
	    DTPasajero pasajero = new DTPasajero(nombreCliente, apellidoCliente);
	    pasajeros.add(pasajero);

	    int equipajeExtra = 2;
	    String tipoReserva = "Ida";
	    EnumAsiento tipoAsiento = EnumAsiento.EJECUTIVO;
	    LocalDate fechaReserva = LocalDate.now();

	    try {
	        iVuelo.crearReserva(nombreVuelo, nickCliente, tipoAsiento, pasajeros, equipajeExtra, fechaReserva);
	    } catch (YaRegistradoException e) {
	        fail("Fallo al crear reserva: " + e.getMessage());
	    }

	    // Probar listarDTReservasUsuario para un cliente
	    try {
	        List<DTReservaVuelo> reservasCliente = iVuelo.listarDTReservasUsuario(nickCliente);
	        assertNotNull(reservasCliente, "La lista de reservas no debe ser nula");
	        assertEquals(1, reservasCliente.size(), "El cliente debe tener 1 reserva");

	        DTReservaVuelo reserva = reservasCliente.get(0);
	        assertEquals(nombreVuelo, reserva.getVuelo().getName(), "El nombre del vuelo en la reserva debe ser " + nombreVuelo);
	        assertEquals(nickCliente, reserva.getNickUser(), "El nick del cliente en la reserva debe ser " + nickCliente);
	    } catch (NoExisteException e) {
	        fail("No debería lanzarse NoExisteException: " + e.getMessage());
	    }

	    // Probar listarDTReservasUsuario para una aerolínea
	    try {
	        List<DTReservaVuelo> reservasAerolinea = iVuelo.listarDTReservasUsuario(aerolineaNick);
	        assertNotNull(reservasAerolinea, "La lista de reservas no debe ser nula");
	        assertEquals(1, reservasAerolinea.size(), "La aerolínea debe tener 1 reserva asociada");

	        DTReservaVuelo reserva = reservasAerolinea.get(0);
	        assertEquals(nombreVuelo, reserva.getVuelo().getName(), "El nombre del vuelo en la reserva debe ser " + nombreVuelo);
	        assertEquals(nickCliente, reserva.getNickUser(), "El nick del cliente en la reserva debe ser " + nickCliente);
	    } catch (NoExisteException e) {
	        fail("No debería lanzarse NoExisteException: " + e.getMessage());
	    }

	    // Probar listarDTReservasUsuario para un usuario inexistente
	    String nickInexistente = "usuario_inexistente";
	    try {
	        iVuelo.listarDTReservasUsuario(nickInexistente);
	        fail("Se esperaba NoExisteException al listar reservas de un usuario inexistente");
	    } catch (NoExisteException e) {
	        // Excepción esperada
	    }

	    // Probar obtenerDTVuelo
	    DTVuelo dtVuelo = iVuelo.obtenerDTVuelo(nombreVuelo);
	    assertNotNull(dtVuelo, "El DTVuelo no debe ser nulo");
	    assertEquals(nombreVuelo, dtVuelo.getName(), "El nombre del vuelo debe ser " + nombreVuelo);
	    assertEquals(nombreRuta, dtVuelo.getRutaVuelo().getName(), "El nombre de la ruta debe ser " + nombreRuta);

	    // Verificar que el DTVuelo contiene la reserva asociada
	    List<DTReservaVuelo> reservasVuelo = dtVuelo.getReservas();
	    assertNotNull(reservasVuelo, "La lista de reservas del vuelo no debe ser nula");
	    assertEquals(1, reservasVuelo.size(), "El vuelo debe tener 1 reserva asociada");

	    DTReservaVuelo reservaVuelo = reservasVuelo.iterator().next();
	    assertEquals(nickCliente, reservaVuelo.getNickUser(), "El nick del cliente en la reserva debe ser " + nickCliente);

	    // Probar obtenerDTVuelo para un vuelo inexistente
	    String nombreVueloInexistente = "VueloInexistente";
	    DTVuelo dtVueloInexistente = iVuelo.obtenerDTVuelo(nombreVueloInexistente);
	    assertNull(dtVueloInexistente, "El DTVuelo debe ser nulo para un vuelo inexistente");
	}
	
	@Test
	void listarVuelosYListarDTVuelosTest() {
	    // Configuración de instancias necesarias
	    ManejadorVuelo manV = ManejadorVuelo.getInstancia();
	    Fabrica fabrica = Fabrica.getInstance();
	    IVuelo iVuelo = fabrica.getIVuelo();
	    IUsuario ctrlUsuario = fabrica.getIUsuario();
	    IPaquete iPaquete = fabrica.getIPaquete(); // Se agrega para manejar paquetes

	    // Crear ciudades de origen y destino
	    String ciudadOrigen = "Berlín";
	    String paisOrigen = "Alemania";
	    String ciudadDestino = "París";
	    String paisDestino = "Francia";
	    LocalDate fechaAltaCiudad = LocalDate.now();
	    iVuelo.ingresarDatosCiudad(ciudadOrigen, paisOrigen, "Aeropuerto de Berlín", "Descripción del aeropuerto", "www.aeropuertoberlin.de", fechaAltaCiudad);
	    iVuelo.ingresarDatosCiudad(ciudadDestino, paisDestino, "Aeropuerto de París", "Descripción del aeropuerto", "www.aeropuertoparis.fr", fechaAltaCiudad);

	    // Crear una categoría
	    String categoria = "Europeo";
	    iVuelo.ingresarDatosCategoria(categoria);
	    Map<String, Categoria> categorias = new HashMap<>();
	    categorias.put(categoria, new Categoria(categoria));

	    // Registrar una aerolínea
	    String aerolineaNick = "Lufthansa";
	    String nombreAerolinea = "Lufthansa Airlines";
	    String emailAerolinea = "info@lufthansa.de";
	    String descripcionAerolinea = "Descripción de Lufthansa";
	    String sitioWebAerolinea = "www.lufthansa.com";
	    String contraseniaAerolinea = "password";
	    try {
	        ctrlUsuario.ingresarAerolinea(aerolineaNick, nombreAerolinea, "", emailAerolinea, contraseniaAerolinea, descripcionAerolinea, sitioWebAerolinea);
	    } catch (Exception e) {
	        fail("Fallo al registrar aerolínea: " + e.getMessage());
	    }

	    // Ingresar la ruta de vuelo
	    String nombreRuta = "RutaBerlinParis";
	    String imagenRuta = "rutaBerlinParis.jpg";
	    String descripcionRuta = "Ruta de Berlín a París";
	    String descripcionCortaRuta = "Berlín - París";
	    LocalTime horaRuta = LocalTime.of(9, 0);
	    float costoExtra = 25;
	    float costoEje = 500;
	    float costoTur = 250;
	    LocalDate fechaAltaRuta = LocalDate.now();

	    try {
	    	iVuelo.ingresarDatosRutaVuelo(aerolineaNick, nombreRuta, imagenRuta, descripcionRuta, descripcionCortaRuta, horaRuta, fechaAltaRuta,
	    			costoExtra, costoEje, costoTur, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
	    	
	    } catch(YaRegistradoException e) {
	    	//
	    }

	    // Crear y agregar vuelos a la ruta
	    String imagenVuelo1 = "vueloBerlinParis1.jpg";
	    String nombreVuelo1 = "VueloBP-001";
	    LocalDate fechaVuelo1 = LocalDate.now().plusDays(1);
	    LocalTime duracionVuelo1 = LocalTime.of(1, 30);
	    int maxEje1 = 20;
	    int maxTur1 = 100;
	    LocalDate fechaAltaVuelo1 = LocalDate.now();

	    String imagenVuelo2 = "vueloBerlinParis2.jpg";
	    String nombreVuelo2 = "VueloBP-002";
	    LocalDate fechaVuelo2 = LocalDate.now().plusDays(2);
	    LocalTime duracionVuelo2 = LocalTime.of(1, 35);
	    int maxEje2 = 18;
	    int maxTur2 = 90;
	    LocalDate fechaAltaVuelo2 = LocalDate.now();

	    try {
	        iVuelo.agregarVueloARuta(imagenVuelo1, nombreRuta, nombreVuelo1, fechaVuelo1, duracionVuelo1, maxEje1, maxTur1, fechaAltaVuelo1);
	        iVuelo.agregarVueloARuta(imagenVuelo2, nombreRuta, nombreVuelo2, fechaVuelo2, duracionVuelo2, maxEje2, maxTur2, fechaAltaVuelo2);
	    } catch (YaRegistradoException e) {
	        fail("Fallo al agregar vuelo a la ruta: " + e.getMessage());
	    }

	    // Probar listarVuelos(String rutaVuelo)
	    try {
	        String[] vuelos = iVuelo.listarVuelos(nombreRuta);
	        assertNotNull(vuelos, "La lista de vuelos no debe ser nula");
	        assertEquals(2, vuelos.length, "Debe haber 2 vuelos en la ruta");
	        Arrays.sort(vuelos); // Aseguramos que están ordenados

	        // Nombres de vuelos esperados ordenados
	        String[] expectedVuelos = {nombreVuelo1, nombreVuelo2};
	        Arrays.sort(expectedVuelos);
	        assertArrayEquals(expectedVuelos, vuelos, "Los nombres de los vuelos deben coincidir y estar ordenados");
	    } catch (EsConjuntoVacioException e) {
	        fail("No se esperaba EsConjuntoVacioException: " + e.getMessage());
	    }

	    // Probar listarVuelos en una ruta sin vuelos
	    String nombreRutaSinVuelos = "RutaSinVuelos";
	    
	    try {
	    	iVuelo.ingresarDatosRutaVuelo(aerolineaNick, nombreRutaSinVuelos, "imagen.jpg", "Descripción", "Desc corta", horaRuta, fechaAltaRuta,
	    			costoExtra, costoEje, costoTur, ciudadDestino, paisDestino, ciudadOrigen, paisOrigen, categorias);
	    	
	    } catch(YaRegistradoException e) {
	    	//
	    }

	    try {
	        iVuelo.listarVuelos(nombreRutaSinVuelos);
	        fail("Se esperaba EsConjuntoVacioException al listar vuelos de una ruta sin vuelos");
	    } catch (EsConjuntoVacioException e) {
	        // Excepción esperada
	    }

	    // Probar listarDTVuelos()
	    List<DTVuelo> dtVuelos = iVuelo.listarDTVuelos();
	    assertNotNull(dtVuelos, "La lista de DTVuelos no debe ser nula");
	    assertEquals(2, dtVuelos.size(), "Debe haber 2 DTVuelos en total");

	    List<String> nombresVuelos = dtVuelos.stream().map(DTVuelo::getName).collect(Collectors.toList());
	    assertTrue(nombresVuelos.contains(nombreVuelo1), "Debe contener el vuelo " + nombreVuelo1);
	    assertTrue(nombresVuelos.contains(nombreVuelo2), "Debe contener el vuelo " + nombreVuelo2);

	    // Probar listarDTRutasVuelo()
	    List<DTRutaVuelo> dtRutasVuelo = iVuelo.listarDTRutasVuelo();
	    assertNotNull(dtRutasVuelo, "La lista de DTRutaVuelo no debe ser nula");
	    assertEquals(2, dtRutasVuelo.size(), "Debe haber 2 DTRutaVuelo en total");

	    List<String> nombresRutas = dtRutasVuelo.stream().map(DTRutaVuelo::getName).collect(Collectors.toList());
	    assertTrue(nombresRutas.contains(nombreRuta), "Debe contener la ruta " + nombreRuta);
	    assertTrue(nombresRutas.contains(nombreRutaSinVuelos), "Debe contener la ruta " + nombreRutaSinVuelos);

	    // Probar listarDTRutasVueloConfirmadas(String categoria)
	    List<DTRutaVuelo> dtRutasCategoria = iVuelo.listarDTRutasVueloConfirmadas(categoria);
	    assertNotNull(dtRutasCategoria, "La lista de DTRutasVuelo para la categoría no debe ser nula");
	    assertEquals(0, dtRutasCategoria.size(), "Debe haber 2 DTRutaVuelo en la categoría");

	    // Probar listarDTRutasVueloConfirmadas(String categoria) con una categoría inexistente
	    String categoriaInexistente = "CategoriaInexistente";
	    List<DTRutaVuelo> dtRutasCategoriaInexistente = iVuelo.listarDTRutasVueloConfirmadas(categoriaInexistente);
	    assertTrue(dtRutasCategoriaInexistente.isEmpty(), "La lista debe ser nula para una categoría inexistente");

	    // **Inicio de las pruebas para obtenerPaquetesDeRutaVuelo y obtenerDTRutaVueloAerolinea**

	    // Crear paquetes y asociar la ruta de vuelo al paquete
	    String nombrePaquete1 = "PaqueteEuropa";
	    String descripcionPaquete1 = "Paquete turístico por Europa";
	    int descuento1 = 10;
	    int validez1 = 50;
	    LocalDate fechaInicio1 = LocalDate.now();
	    String imagenPaquete1 = "imagenPaquete1.jpg";

	    String nombrePaquete2 = "PaqueteGastronómico";
	    String descripcionPaquete2 = "Paquete gastronómico europeo";
	    int descuento2 = 15;
	    int validez2 = 75;
	    LocalDate fechaInicio2 = LocalDate.now();
	    String imagenPaquete2 = "imagenPaquete2.jpg";

	    try {
	        iPaquete.crearPaquete(nombrePaquete1, imagenPaquete1, descripcionPaquete1, descuento1, validez1, fechaInicio1);
	        iPaquete.crearPaquete(nombrePaquete2, imagenPaquete2, descripcionPaquete2, descuento2, validez1, fechaInicio2);
	    } catch (YaExisteException e) {
	        fail("Fallo al crear paquetes: " + e.getMessage());
	    }

	    // Asociar la ruta de vuelo al primer paquete
	    try {
	        iPaquete.agregarRutaVuelo(nombrePaquete1, nombreRuta, EnumAsiento.EJECUTIVO, 3);
	    } catch (Exception e) {
	        fail("Fallo al agregar ruta de vuelo al paquete: " + e.getMessage());
	    }

	    // Probar obtenerPaquetesDeRutaVuelo
	    List<DTPaquete> paquetesDeRuta = iVuelo.obtenerPaquetesDeRutaVuelo(nombreRuta);
	    assertNotNull(paquetesDeRuta, "La lista de paquetes no debe ser nula");
	    assertEquals(1, paquetesDeRuta.size(), "Debe haber 1 paquete asociado a la ruta de vuelo");

	    DTPaquete paqueteObtenido = paquetesDeRuta.get(0);
	    assertEquals(nombrePaquete1, paqueteObtenido.getNombre(), "El nombre del paquete debe ser " + nombrePaquete1);

	    // Probar obtenerDTRutaVueloAerolinea
	    DTRutaVueloAerolinea dtRutaVueloAerolinea = iVuelo.obtenerDTRutaVueloAerolinea(nombreRuta);
	    assertNotNull(dtRutaVueloAerolinea, "El DTRutaVueloAerolinea no debe ser nulo");
	    assertEquals(nombreRuta, dtRutaVueloAerolinea.getName(), "El nombre de la ruta debe ser " + nombreRuta);
	    assertEquals(aerolineaNick, dtRutaVueloAerolinea.getNickAerolinea(), "El nick de la aerolínea debe ser " + aerolineaNick);
	    assertEquals(nombreAerolinea, dtRutaVueloAerolinea.getNombreAerolinea(), "El nombre de la aerolínea debe ser " + nombreAerolinea);

	    // Probar obtenerDTRutaVueloAerolinea para una ruta inexistente
	    String nombreRutaInexistente = "RutaInexistente";
	    DTRutaVueloAerolinea dtRutaVueloAerolineaInexistente = iVuelo.obtenerDTRutaVueloAerolinea(nombreRutaInexistente);
	    assertNull(dtRutaVueloAerolineaInexistente, "El DTRutaVueloAerolinea debe ser nulo para una ruta inexistente");

	    // **Fin de las pruebas adicionales**
	}
	
	@Test
	void ListarDTRutasVueloTest() {
		ManejadorVuelo manV = ManejadorVuelo.getInstancia();
		ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario ctrlUsuario = fabrica.getIUsuario();
		
		// Datos Aerolinea
		String aerolinea = "CanAirlines1";
		String nombreAerolinea = "Canadian Airline1";
		String emailAerolinea = "CanAirlines@email.com1";
		String descripcion = "Need to travel? Come fly with us";
		String sitioWeb = "";
		String contraseniaAerolinea = "";	
		
		try {
			ctrlUsuario.ingresarAerolinea(aerolinea, nombreAerolinea,  "", emailAerolinea, contraseniaAerolinea, descripcion, sitioWeb);
		}
		catch(Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		// Datos Ciudades
		String nombreCiudadOrigen = "Lima";
		String paisOrigen = "Peru";
		String nombreAeropuertoOrigen = "Alperu";
		String descOrigen = "Vista los Andes :)";
		String sitioWebOrigen = "www.Alperu.com.pe";
		LocalDate FechaAltaOrigen = PresentacionUtils.parseDate("14/05/2024");
		
		String nombreCiudadDestino = "Ottawa";
		String paisDestino = "Canada";
		String nombreAeropuertoDestino = "Canadian Airlines";
		String descDestino = "The best option to travel around the world";
		String sitioWebDestino = "www.CanAirlines.com.ca";
		LocalDate FechaAltaDestino = PresentacionUtils.parseDate("01/03/2024");
		
		iVuelo.ingresarDatosCiudad(nombreCiudadOrigen, paisOrigen, nombreAeropuertoOrigen, descOrigen, sitioWebOrigen, FechaAltaOrigen);
		iVuelo.ingresarDatosCiudad(nombreCiudadDestino, paisDestino, nombreAeropuertoDestino, descDestino, sitioWebDestino, FechaAltaDestino);
		
		Pair<String, String> ciudadOrigenPair = new Pair<String, String>(nombreCiudadOrigen, paisOrigen);
		Pair<String, String> ciudadDestinoPair = new Pair<String, String>(nombreCiudadDestino, paisDestino);
				
		Ciudad ciudadOrigen = manV.getCiudades().get(ciudadOrigenPair);
		Ciudad ciudadDestino = manV.getCiudades().get(ciudadDestinoPair);
		
		//Datos Ruta 1
		String nombreRuta = "UKG4371";
		String imagen = "imagen4.jpg";
		String descripcionCorta = "Un viaje que siempre quisieras hacer";
		String descripcionRuta = "Un viaje que siempre quisieras hacer, con un elmozo destino llamado: no se";
		LocalTime hora = PresentacionUtils.parseTime("06:00");
		float costoExtra = 120;
		float costoEje = 4500;
		float costoTur = 2300;
		LocalDate fechaAltaRuta = PresentacionUtils.parseDate("02/06/2024");
		Map<String, Categoria> categorias = new HashMap<String, Categoria>();
		categorias.put("Invernal", new Categoria("Invernal"));
		categorias.put("Turistico", new Categoria("Turistico"));
		
		RutaVuelo ruta = new RutaVuelo(nombreRuta, imagen, descripcionRuta, descripcionCorta, hora, fechaAltaRuta, 
										costoExtra, costoEje, costoTur, ciudadOrigen,ciudadDestino, categorias);
		manV.agregarRutaVuelo(ruta);
		iVuelo.agregarRutaVueloAAerolinea(aerolinea, ruta);
		try {
			String nickAerolineaRV = ctrlUsuario.obtenerNickAerolineaDeRutaVuelo(nombreRuta);
			assertEquals(aerolinea, nickAerolineaRV);

		//Datos iniciales Vuelo
		String nombre = "IDK404!";
		LocalTime duracion = PresentacionUtils.parseTime("05:00");
		int cantTur = 130;
		int cantEjec = 24;
		LocalDate fechaVuelo = PresentacionUtils.parseDate("17/08/2024");
		LocalDate fechaAlta = PresentacionUtils.parseDate("09/09/2024");
		
		iVuelo.agregarVueloARuta("", nombreRuta, nombre, fechaVuelo, duracion, cantTur, cantEjec, fechaAlta);
		} catch(Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		//Datos Ruta 2
		String nombreRuta2 = "UKG4300";
		String imagen2 = "imagen5.jpg";
		String descripcionCorta2 = "Un viaje que siempre quisieras hacer de vuelta";
		String descripcionRuta2 = "Un viaje que siempre quisieras hacer, con un elmozo destino llamado: no se jeje";
		LocalTime hora2 = PresentacionUtils.parseTime("06:00");
		float costoExtra2 = 100;
		float costoEje2 = 4500;
		float costoTur2 = 2600;
		LocalDate fechaAltaRuta2 = PresentacionUtils.parseDate("02/06/2024");
		Map<String, Categoria> categorias2 = new HashMap<String, Categoria>();
		categorias.put("Invernal", new Categoria("Invernal"));
		categorias.put("Corto", new Categoria("Corto"));
		
		RutaVuelo ruta2 = new RutaVuelo(nombreRuta2, imagen2, descripcionRuta2, descripcionCorta2, hora2, fechaAltaRuta2, 
										costoExtra2, costoEje2, costoTur2, ciudadDestino, ciudadOrigen, categorias2);
		manV.agregarRutaVuelo(ruta2);
		iVuelo.agregarRutaVueloAAerolinea(aerolinea, ruta2);
		try {
			String nickAerolineaRV = ctrlUsuario.obtenerNickAerolineaDeRutaVuelo(nombreRuta2);
			assertEquals(aerolinea, nickAerolineaRV);

		//Datos iniciales Vuelo 2
		String nombre2 = "IDK403!";
		LocalTime duracion2 = PresentacionUtils.parseTime("05:00");
		int cantTur2 = 130;
		int cantEjec2 = 24;
		LocalDate fechaVuelo2 = PresentacionUtils.parseDate("17/08/2024");
		LocalDate fechaAlta2 = PresentacionUtils.parseDate("09/09/2024");
		
		iVuelo.agregarVueloARuta("", nombreRuta2, nombre2, fechaVuelo2, duracion2, cantTur2, cantEjec2, fechaAlta2);
		} catch(Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		//Listar DTRutasVuelo
		List<DTRutaVuelo> dtRutasInvernal = iVuelo.listarDTRutasVuelo(aerolinea, "Invernal");
		assertEquals(2, dtRutasInvernal.size());
		List<DTRutaVuelo> dtRutasInvernalConf = iVuelo.listarDTRutasVueloConfirmadas(aerolinea, "Invernal");
		assertEquals(0, dtRutasInvernalConf.size());
		List<DTRutaVuelo> dtRutasNada = iVuelo.listarDTRutasVuelo(aerolinea, "Nada");
		assertEquals(0, dtRutasNada.size());
		List<DTRutaVuelo> dtRutasNadaConf = iVuelo.listarDTRutasVueloConfirmadas(aerolinea, "Nada");
		assertEquals(0, dtRutasNadaConf.size());
		
		
	}
	
	@Test
	void reservarVueloConPaqueteTest() {
	    // Configuración de instancias necesarias
	    Fabrica fabrica = Fabrica.getInstance();
	    IVuelo iVuelo = fabrica.getIVuelo();
	    IUsuario ctrlUsuario = fabrica.getIUsuario();
	    IPaquete iPaquete = fabrica.getIPaquete();

	    // Crear ciudades de origen y destino
	    String ciudadOrigen = "Londres";
	    String paisOrigen = "Reino Unido";
	    String ciudadDestino = "Nueva York";
	    String paisDestino = "Estados Unidos";
	    LocalDate fechaAltaCiudad = LocalDate.now();
	    iVuelo.ingresarDatosCiudad(ciudadOrigen, paisOrigen, "Aeropuerto Heathrow", "Aeropuerto en Londres", "www.heathrow.com", fechaAltaCiudad);
	    iVuelo.ingresarDatosCiudad(ciudadDestino, paisDestino, "Aeropuerto JFK", "Aeropuerto en Nueva York", "www.jfkairport.com", fechaAltaCiudad);

	    // Crear una categoría
	    String categoria = "Internacional";
	    iVuelo.ingresarDatosCategoria(categoria);
	    Map<String, Categoria> categorias = new HashMap<>();
	    categorias.put(categoria, new Categoria(categoria));

	    // Registrar una aerolínea
	    String aerolineaNick = "BritishAirways";
	    String nombreAerolinea = "British Airways";
	    String emailAerolinea = "info@britishairways.com";
	    String descripcionAerolinea = "Descripción de British Airways";
	    String sitioWebAerolinea = "www.britishairways.com";
	    String contraseniaAerolinea = "password";
	    try {
	        ctrlUsuario.ingresarAerolinea(aerolineaNick, nombreAerolinea, "", emailAerolinea, contraseniaAerolinea, descripcionAerolinea, sitioWebAerolinea);
	    } catch (Exception e) {
	        fail("Fallo al registrar aerolínea: " + e.getMessage());
	    }

	    // Ingresar la ruta de vuelo
	    String nombreRuta = "RutaLondresNY";
	    String imagenRuta = "rutaLondresNY.jpg";
	    String descripcionRuta = "Ruta de Londres a Nueva York";
	    String descripcionCortaRuta = "Londres - Nueva York";
	    LocalTime horaRuta = LocalTime.of(10, 0);
	    float costoExtra = 60;
	    float costoEje = 600;
	    float costoTur = 300;
	    LocalDate fechaAltaRuta = LocalDate.now();

	    try {
	    	iVuelo.ingresarDatosRutaVuelo(aerolineaNick, nombreRuta, imagenRuta, descripcionRuta, descripcionCortaRuta, horaRuta, fechaAltaRuta,
	    			costoExtra, costoEje, costoTur, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
	    	
	    } catch(YaRegistradoException e) {
	    	//
	    }
	    iVuelo.aceptarRutaVuelo(nombreRuta);
	    

	    // Crear y agregar un vuelo a la ruta
	    String imagenVuelo = "vueloLondresNY.jpg";
	    String nombreVuelo = "VueloLN-001";
	    LocalDate fechaVuelo = LocalDate.now().plusDays(7);
	    LocalTime duracionVuelo = LocalTime.of(8, 0);
	    int maxEje = 30;
	    int maxTur = 150;
	    LocalDate fechaAltaVuelo = LocalDate.now();

	    try {
	        iVuelo.agregarVueloARuta(imagenVuelo, nombreRuta, nombreVuelo, fechaVuelo, duracionVuelo, maxEje, maxTur, fechaAltaVuelo);
	    } catch (YaRegistradoException e) {
	        fail("Fallo al agregar vuelo a la ruta: " + e.getMessage());
	    }
	    
	    List<DTVuelo> dtVuelos = iVuelo.listarDTVuelosRC();
		assertEquals(1, dtVuelos.size());

	    // Registrar un cliente
	    String nickCliente = "john_doe";
	    String nombreCliente = "John";
	    String apellidoCliente = "Doe";
	    String imagenCliente = "img1.png";
	    String emailCliente = "john.doe@example.com";
	    String contraseniaCliente = "password123";
	    LocalDate fechaNacimientoCliente = LocalDate.of(1985, 5, 20);
	    String nacionalidadCliente = "Uruguayo";
	    String documentoCliente = "50885301";
	    try {
	        ctrlUsuario.ingresarCliente(nickCliente, nombreCliente, imagenCliente, apellidoCliente, emailCliente, contraseniaCliente, fechaNacimientoCliente, 
	                nacionalidadCliente, EnumDoc.PASAPORTE, documentoCliente);
	    } catch (Exception e) {
	        fail("Fallo al registrar cliente: " + e.getMessage());
	    }
	    
	    // Crear Paquete
        String nombreP = "Paquete";
        String descripcionP = "Paquete de prueba";
        String imagenP = "imagenPaquete.jpg";
        Integer validezP = 10;
        Integer descuentoP = 20;
        try {
            iPaquete.crearPaquete(nombreP, imagenP, descripcionP, validezP, descuentoP, LocalDate.now());
        } catch (Exception e) {
            fail(e.getMessage());
        }
          
        // Agregar rutas de vuelo a paquete
        EnumAsiento tipoAsientoRuta = EnumAsiento.EJECUTIVO;
        Integer cantRuta = 3;
        try {
        	iPaquete.agregarRutaVuelo(nombreP, nombreRuta, tipoAsientoRuta, cantRuta);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // Comprar paquete
        try {
        	iPaquete.comprarPaquete(nickCliente, LocalDate.now(), nombreP);
        } catch (Exception e) {
            fail(e.getMessage());
        }

	    // Crear una reserva para el cliente
	    List<DTPasajero> pasajeros = new ArrayList<>();
	    DTPasajero pasajero = new DTPasajero(nombreCliente, apellidoCliente);
	    DTPasajero acomp = new DTPasajero("Juan", "Acompania");
	    pasajeros.add(pasajero);
	    pasajeros.add(acomp);

	    int equipajeExtra = 2;
	    EnumAsiento tipoAsiento = EnumAsiento.EJECUTIVO;
	    LocalDate fechaReserva = LocalDate.now();

	    try {
	        iVuelo.crearReserva(nombreVuelo, nickCliente, tipoAsiento, pasajeros, equipajeExtra, fechaReserva, nombreP);
	    } catch (YaRegistradoException e) {
	        fail("Fallo al crear reserva: " + e.getMessage());
	    } catch (NoExisteException e) {
	        fail("Fallo al crear reserva: " + e.getMessage());
	    } catch (OperacionInvalidaException e) {
	        fail("Fallo al crear reserva: " + e.getMessage());
	    }
	    
	    List<DTPasajero> pasajeros2 = new ArrayList<>();
	    DTPasajero pasajero2 = new DTPasajero(nombreCliente, apellidoCliente);
	    pasajeros2.add(pasajero2);
	    
	    // Reserva dos veces a un mismo vuelo
	    assertThrows(YaRegistradoException.class, () -> {
	    	iVuelo.crearReserva(nombreVuelo, nickCliente, tipoAsiento, pasajeros, equipajeExtra, fechaReserva, nombreP);
	    });
	    
	    String nombreVuelo2 = "VueloLN-0012";
	    // Reserva de un vuelo con un paquete que no compro el usuario
	    assertThrows(NoExisteException.class, () -> {
	    	iVuelo.crearReserva(nombreVuelo2, nickCliente, tipoAsiento, pasajeros, equipajeExtra, fechaReserva, nombreP);
	    });
	    
	    // Reserva de un vuelo que no existe
	    assertThrows(NoExisteException.class, () -> {
	    	iVuelo.crearReserva(nombreVuelo2, nickCliente, tipoAsiento, pasajeros, equipajeExtra, fechaReserva, nombreP);
	    });

	    // Crear y agregar un vuelo a la ruta
	    String imagenVuelo2 = "vueloLondresNY2.jpg";
	    LocalDate fechaVuelo2 = LocalDate.now().plusDays(8);
	    LocalTime duracionVuelo2 = LocalTime.of(8, 0);
	    int maxEje2 = 10;
	    int maxTur2 = 130;
	    LocalDate fechaAltaVuelo2 = LocalDate.now();

	    try {
	        iVuelo.agregarVueloARuta(imagenVuelo2, nombreRuta, nombreVuelo2, fechaVuelo2, duracionVuelo2, maxEje2, maxTur2, fechaAltaVuelo2);
	    } catch (YaRegistradoException e) {
	        fail("Fallo al agregar vuelo a la ruta: " + e.getMessage());
	    }
	    
	    dtVuelos = iVuelo.listarDTVuelosRC();
	    assertEquals(2, dtVuelos.size());

	    // Reserva de un con tipo de asiento incorrecto
	    EnumAsiento tipoAsientoErr = EnumAsiento.TURISTA;
	    assertThrows(OperacionInvalidaException.class, () -> {
	    	iVuelo.crearReserva(nombreVuelo2, nickCliente, tipoAsientoErr, pasajeros, equipajeExtra, fechaReserva, nombreP);
	    });
	    
	    DTPasajero acomp2 = new DTPasajero(nombreCliente, apellidoCliente);
	    pasajeros2.add(acomp2);
	    // Reserva de un con mas pasajes de los disponibles
	    EnumAsiento Err = EnumAsiento.TURISTA;
	    assertThrows(OperacionInvalidaException.class, () -> {
	    	iVuelo.crearReserva(nombreVuelo2, nickCliente, tipoAsientoErr, pasajeros, equipajeExtra, fechaReserva, nombreP);
	    });
	    
	}
}

