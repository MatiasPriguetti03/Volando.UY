package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.NoExisteException;
import excepciones.YaExisteException;
import excepciones.YaRegistradoException;
import logica.Categoria;
import logica.Ciudad;
import logica.Fabrica;
import logica.IPaquete;
import logica.IUsuario;
import logica.IVuelo;
import logica.ManejadorPaquete;
import logica.ManejadorUsuario;
import logica.ManejadorVuelo;
import logica.datatypes.Pair;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTInfoPaquete;
import logica.enums.EnumAsiento;
import logica.enums.EnumDoc;
import utils.PresentacionUtils;

public class ControladorPaqueteTest {
    private static IUsuario iUsuario;
    private static IPaquete iPaquete;
    private static IVuelo iVuelo;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    @BeforeAll
    public static void setUpBeforeClass() {
        Fabrica fabrica = Fabrica.getInstance();
        iUsuario = fabrica.getIUsuario();
        iPaquete = fabrica.getIPaquete();
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
    void testCrearPaquete() {
        String nombre = "Paquete";
        String descripcion = "Paquete de prueba";
        Integer validez = 10;
        Integer descuento = 20;
        String fecha = "31/12/2021";

        try {
            iPaquete.crearPaquete(nombre, "", descripcion, validez, descuento, LocalDate.parse(fecha, DATE_FORMATTER));
            DTPaquete dtPaquete = iPaquete.obtenerDatosPaquete(nombre);
            assertEquals(dtPaquete.getNombre(), nombre);
            assertEquals(dtPaquete.getDescripcion(), descripcion);
            assertEquals(dtPaquete.getPeriodoValidez(), validez);
            assertEquals(dtPaquete.getDescuento(), descuento);
            assertEquals(dtPaquete.getCosto(), 0);
            assertEquals(dtPaquete.getFechaAlta(), LocalDate.parse(fecha, DATE_FORMATTER));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    void testCrearPaqueteRepetido() {
        String nombre = "Paquete";
        String descripcion = "Paquete de prueba";
        Integer validez = 10;
        Integer descuento = 20;
        String fecha = "31/12/2021";

        try {
            iPaquete.crearPaquete(nombre, "", descripcion, validez, descuento, LocalDate.parse(fecha, DATE_FORMATTER));
            assertThrows(YaExisteException.class, () -> {
                iPaquete.crearPaquete(nombre, "", descripcion, validez, descuento, LocalDate.parse(fecha, DATE_FORMATTER));
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    void testObtenerPaqueteInexistente() {
        assertThrows(NoExisteException.class, () -> {
            iPaquete.obtenerDatosPaquete("PaqueteInexistente");
        });
    }
    
    @Test
    void testListarPaquetesVacio() {
        List<DTInfoPaquete> paquetes = iPaquete.listarPaquetes();
        assertTrue(paquetes.isEmpty());
    }

    @Test
    void testListarPaquetes() {
        String nombre1 = "Paquete1";
        String nombre2 = "Paquete2";
        String descripcion = "Paquete de prueba";
        Integer validez = 10;
        Integer descuento = 20;
        LocalDate fecha = LocalDate.parse("31/12/2021", DATE_FORMATTER);

        try {
            iPaquete.crearPaquete(nombre1, "", descripcion, validez, descuento, fecha);
            iPaquete.crearPaquete(nombre2, "", descripcion, validez, descuento, fecha);
            
            List<DTInfoPaquete> paquetes = iPaquete.listarPaquetes();
            assertEquals(2, paquetes.size());
            assertTrue(paquetes.stream().anyMatch(p -> p.getNombre().equals(nombre1)));
            assertTrue(paquetes.stream().anyMatch(p -> p.getNombre().equals(nombre2)));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testComprarPaqueteInexistente() {
        try {
            iUsuario.ingresarCliente("usuario1", "nombre", "", "apellido", "email@test.com", "", 
                LocalDate.now(), "nacionalidad", EnumDoc.PASAPORTE, "123456");
                
            assertThrows(NoExisteException.class, () -> {
                iPaquete.comprarPaquete("usuario1", LocalDate.now(), "PaqueteInexistente");
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testComprarPaqueteUsuarioInexistente() {
        try {
            iPaquete.crearPaquete("Paquete1", "", "descripcion", 10, 20, LocalDate.now());
            
            assertThrows(NoExisteException.class, () -> {
                iPaquete.comprarPaquete("usuarioInexistente", LocalDate.now(), "Paquete1");
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testComprarPaqueteYaComprado() {
        try {
            // Crear usuario y paquete
            iUsuario.ingresarCliente("usuario1", "nombre", "", "apellido", "email@test.com", "", 
                LocalDate.now(), "nacionalidad", EnumDoc.PASAPORTE, "123456");
            iPaquete.crearPaquete("Paquete1", "", "descripcion", 10, 20, LocalDate.now());
            
            // Primera compra
            iPaquete.comprarPaquete("usuario1", LocalDate.now(), "Paquete1");
            
            // Intento de segunda compra
            assertThrows(YaRegistradoException.class, () -> {
                iPaquete.comprarPaquete("usuario1", LocalDate.now(), "Paquete1");
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
	
	@Test
	void testAgregarRutaVueloAPaquete() {
		// Datos Aerolinea
		String aerolinea = "CanAirlines";
		String nombreAerolinea = "Canadian Airline";
		String emailAerolinea = "CanAirlines@email.com";
		String descripcionAerolinea = "Need to travel? Come fly with us";
		String sitioWeb = "";
		
		// Datos Ciudades
		String nombreCiudadOrigen = "Lima";
		String paisOrigen = "Peru";
		String nombreAeropuertoOrigen = "Alperu";
		String descOrigen = "Vista los Andes :)";
		String sitioWebOrigen = "www.Alperu.com.pe";
		LocalDate FechaAltaOrigen = LocalDate.parse("14/05/2024", DATE_FORMATTER);
		
		String nombreCiudadDestino = "Ottawa";
		String paisDestino = "Canada";
		String nombreAeropuertoDestino = "Canadian Airlines";
		String descDestino = "The best option to travel around the world";
		String sitioWebDestino = "www.CanAirlines.com.ca";
		LocalDate FechaAltaDestino = LocalDate.parse("01/03/2024", DATE_FORMATTER);
		
		// Datos Ruta de Vuelo
		String nombreRuta = "UKG437";
		String imagen = "imagen2.jpg";
		String descripcionRuta = "Un viaje que siempre quisieras hacer, con un elmozo destino llamado: no se";
		String descripcionCorta = "Un viaje que siempre quisiste hacer";
		LocalTime hora = PresentacionUtils.parseTime("06:00");
		float costoExtra = 120;
		float costoEje = 4500;
		float costoTur = 2300;
		Map<String, Categoria> categorias = new HashMap<String, Categoria>();
		LocalDate fechaAltaRuta = LocalDate.parse("02/06/2024", DATE_FORMATTER);
		
		// Datos Paquete
		String nombre = "Paquete";
		String descripcion = "Paquete de prueba";
		Integer validez = 10;
		Integer descuento = 20;
		String fecha = "31/12/2021";
		
		// Datos Ruta de Vuelo - Paquete
		Integer cantidad = 2;
		EnumAsiento asiento = EnumAsiento.EJECUTIVO;
		Float costoEsperado = costoEje*cantidad*(100-descuento)/100;
		String contraseniaAerolinea = "";
		
		try {
			// Creo aerolinea
			iUsuario.ingresarAerolinea(aerolinea, nombreAerolinea,  "", emailAerolinea, contraseniaAerolinea, descripcionAerolinea, sitioWeb);
			// Creo 2 ciudades
			iVuelo.ingresarDatosCiudad(nombreCiudadOrigen, paisOrigen, nombreAeropuertoOrigen, descOrigen, sitioWebOrigen, FechaAltaOrigen);
			iVuelo.ingresarDatosCiudad(nombreCiudadDestino, paisDestino, nombreAeropuertoDestino, descDestino, sitioWebDestino, FechaAltaDestino);
			// Obtengo las ciudades
			ManejadorVuelo MV = ManejadorVuelo.getInstancia();
			Pair<String, String> ciudadOrigenPair = new Pair<String, String>(nombreCiudadOrigen, paisOrigen);
			Pair<String, String> ciudadDestinoPair = new Pair<String, String>(nombreCiudadDestino, paisDestino);
			Ciudad ciudadOrigen = MV.getCiudades().get(ciudadOrigenPair);
			Ciudad ciudadDestino =MV.getCiudades().get(ciudadDestinoPair);
			// Creo categorias
			categorias.put("Invernal", new Categoria("Invernal"));
			categorias.put("Turistico", new Categoria("Turistico"));
			// Creo ruta de	vuelo
			iVuelo.ingresarDatosRutaVuelo(aerolinea, nombreRuta, imagen, descripcionRuta, descripcionCorta, hora, fechaAltaRuta, costoExtra, costoEje,
					costoTur, ciudadOrigen.getNombre(), ciudadOrigen.getPais(), ciudadDestino.getNombre(), ciudadDestino.getPais(), categorias);
			// Creo paquete
			iPaquete.crearPaquete(nombre,  "", descripcion, validez, descuento, LocalDate.parse(fecha, DATE_FORMATTER));
			// Chequeo que se creo paquete
			List<DTInfoPaquete> listaPaquetes = new ArrayList<DTInfoPaquete>();
			listaPaquetes = iPaquete.listarPaquetes();
			assertEquals(1, listaPaquetes.size());
			assertEquals(nombre, listaPaquetes.get(0).getNombre());
			// Chequeo que no hay paquetes con rutas de vuelos
			List<DTInfoPaquete> listaPaquetesConRutas = new ArrayList<DTInfoPaquete>();
			listaPaquetesConRutas = iPaquete.listarPaquetesConRutas();
			assertEquals(0, listaPaquetesConRutas.size());
			// Agrego ruta de vuelo
			iPaquete.agregarRutaVuelo(nombre, nombreRuta, asiento, cantidad);
			DTPaquete dtPaquete = iPaquete.obtenerDatosPaquete(nombre);
			assertEquals(costoEsperado, dtPaquete.getCosto());
			// Chequeo que ahora si aparece el paquete con ruta de vuelo
			listaPaquetesConRutas = iPaquete.listarPaquetesConRutas();
			assertEquals(1, listaPaquetesConRutas.size());
			assertEquals(nombre, listaPaquetesConRutas.get(0).getNombre());
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	void testComprarPaquete() {
		// Datos Cliente
		String nicknameCliente = "Genki09";
		String nombreCliente = "Pedro";
		String apellido = "Gutierrez";
		String emailCliente = "genki09@email.com";
		String nacionalidad = "Peruano";
		String documento = "40934249";
		LocalDate fechaNac = PresentacionUtils.parseDate("28/09/2000");
		EnumDoc tipoDoc = EnumDoc.PASAPORTE;
		String contraseniaCliente = "";
		
		// Datos Aerolinea
		String aerolinea = "CanAirlines";
		String nombreAerolinea = "Canadian Airline";
		String emailAerolinea = "CanAirlines@email.com";
		String descripcionAerolinea = "Need to travel? Come fly with us";
		String sitioWeb = "";
		String contraseniaAerolinea = "";
		
		// Datos Ciudades
		String nombreCiudadOrigen = "Lima";
		String paisOrigen = "Peru";
		String nombreAeropuertoOrigen = "Alperu";
		String descOrigen = "Vista los Andes :)";
		String sitioWebOrigen = "www.Alperu.com.pe";
		LocalDate FechaAltaOrigen = LocalDate.parse("14/05/2024", DATE_FORMATTER);
		
		String nombreCiudadDestino = "Ottawa";
		String paisDestino = "Canada";
		String nombreAeropuertoDestino = "Canadian Airlines";
		String descDestino = "The best option to travel around the world";
		String sitioWebDestino = "www.CanAirlines.com.ca";
		LocalDate FechaAltaDestino = LocalDate.parse("01/03/2024", DATE_FORMATTER);
		
		// Datos Ruta de Vuelo
		String nombreRuta = "UKG437";
		String imagen = "imagen3.jpg";
		String descripcionRuta = "Un viaje que siempre quisieras hacer, con un elmozo destino llamado: no se";
		String descripcionCorta = "Un viaje que siempre quisiste hacer";
		LocalTime hora = PresentacionUtils.parseTime("06:00");
		float costoExtra = 120;
		float costoEje = 4500;
		float costoTur = 2300;
		Map<String, Categoria> categorias = new HashMap<String, Categoria>();
		LocalDate fechaAltaRuta = LocalDate.parse("02/06/2024", DATE_FORMATTER);
		
		// Datos Paquete
		String nombre = "Paquete";
		String descripcion = "Paquete de prueba";
		Integer validez = 10;
		Integer descuento = 20;
		String fecha = "31/12/2021";
		
		// Datos Ruta de Vuelo - Paquete
		Integer cantidad = 2;
		EnumAsiento asiento = EnumAsiento.EJECUTIVO;
		
		// Datos Compra Paquete
		LocalDate fechaCompra = LocalDate.parse("02/09/2024", DATE_FORMATTER);
		
		try {
			// Creo Cliente
			iUsuario.ingresarCliente(nicknameCliente, nombreCliente,  "", apellido, emailCliente, contraseniaCliente, fechaNac, nacionalidad, tipoDoc, documento);
			// Creo aerolinea
			iUsuario.ingresarAerolinea(aerolinea, nombreAerolinea,  "", emailAerolinea, contraseniaCliente, descripcionAerolinea, sitioWeb);
			// Creo 2 ciudades
			iVuelo.ingresarDatosCiudad(nombreCiudadOrigen, paisOrigen, nombreAeropuertoOrigen, descOrigen, sitioWebOrigen, FechaAltaOrigen);
			iVuelo.ingresarDatosCiudad(nombreCiudadDestino, paisDestino, nombreAeropuertoDestino, descDestino, sitioWebDestino, FechaAltaDestino);
			// Obtengo las ciudades
			ManejadorVuelo MV = ManejadorVuelo.getInstancia();
			Pair<String, String> ciudadOrigenPair = new Pair<String, String>(nombreCiudadOrigen, paisOrigen);
			Pair<String, String> ciudadDestinoPair = new Pair<String, String>(nombreCiudadDestino, paisDestino);
			Ciudad ciudadOrigen = MV.getCiudades().get(ciudadOrigenPair);
			Ciudad ciudadDestino =MV.getCiudades().get(ciudadDestinoPair);
			// Creo categorias
			categorias.put("Invernal", new Categoria("Invernal"));
			categorias.put("Turistico", new Categoria("Turistico"));
			// Creo ruta de	vuelo
			iVuelo.ingresarDatosRutaVuelo(aerolinea, nombreRuta, imagen, descripcionRuta, descripcionCorta, hora, fechaAltaRuta, costoExtra, costoEje,
					costoTur, ciudadOrigen.getNombre(), ciudadOrigen.getPais(), ciudadDestino.getNombre(), ciudadDestino.getPais(), categorias);
			// Creo paquete
			iPaquete.crearPaquete(nombre,  "", descripcion, validez, descuento, LocalDate.parse(fecha, DATE_FORMATTER));
			// Chequeo que se creo paquete
			List<DTInfoPaquete> listaPaquetes = new ArrayList<DTInfoPaquete>();
			listaPaquetes = iPaquete.listarPaquetes();
			assertEquals(1, listaPaquetes.size());
			assertEquals(nombre, listaPaquetes.get(0).getNombre());
			// Chequeo que no hay paquetes con rutas de vuelos
			List<DTInfoPaquete> listaPaquetesNoComprados = new ArrayList<DTInfoPaquete>();
			listaPaquetesNoComprados = iPaquete.listarPaquetesNoComprados();
			assertEquals(1, listaPaquetesNoComprados.size());
			// Agrego ruta de vuelo
			iPaquete.agregarRutaVuelo(nombre, nombreRuta, asiento, cantidad);
			// Comprar Paquete
			iPaquete.comprarPaquete(nicknameCliente, fechaCompra, nombre);
			// Chequeo la compra
			listaPaquetesNoComprados = iPaquete.listarPaquetesNoComprados();
			assertEquals(0, listaPaquetesNoComprados.size());
			assertEquals(true, iUsuario.clienteComproPaquete(nicknameCliente, nombre));
			// Chequeo desde cliente
			List<DTCompraPaquete> comprasDePaquetes = iUsuario.obtenerComprasDePaquetes(nicknameCliente);
			assertEquals(1, comprasDePaquetes.size());

		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
    @Test
    void testListarDTPaquetesVacio() {
        List<DTPaquete> paquetes = iPaquete.listarDTPaquetes();
        assertTrue(paquetes.isEmpty());
    }

    @Test
    void testListarDTPaquetes() {
        String nombre1 = "PaqueteCompleto1";
        String nombre2 = "PaqueteCompleto2";
        String descripcion = "Descripción del paquete";
        Integer validez = 30;
        Integer descuento = 15;
        LocalDate fecha = LocalDate.parse("31/12/2021", DATE_FORMATTER);

        try {
            iPaquete.crearPaquete(nombre1, "", descripcion, validez, descuento, fecha);
            iPaquete.crearPaquete(nombre2, "", descripcion, validez, descuento, fecha);

            List<DTPaquete> paquetes = iPaquete.listarDTPaquetes();
            
            assertEquals(2, paquetes.size());
            
            // Verificamos que los datos de ambos paquetes estén correctos
            DTPaquete paq1 = paquetes.stream()
                .filter(p -> p.getNombre().equals(nombre1))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No se encontró el paquete " + nombre1));
                
            assertEquals(descripcion, paq1.getDescripcion());
            assertEquals(validez, paq1.getPeriodoValidez());
            assertEquals(descuento, paq1.getDescuento());
            assertEquals(fecha, paq1.getFechaAlta());
            assertEquals(0, paq1.getCosto());

            DTPaquete paq2 = paquetes.stream()
                .filter(p -> p.getNombre().equals(nombre2))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No se encontró el paquete " + nombre2));
                
            assertEquals(descripcion, paq2.getDescripcion());
            assertEquals(validez, paq2.getPeriodoValidez());
            assertEquals(descuento, paq2.getDescuento());
            assertEquals(fecha, paq2.getFechaAlta());
            assertEquals(0, paq2.getCosto());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testListarDTPaquetesConRutasVuelo() {
        String nombrePaquete = "PaqueteConRuta";
        String descripcion = "Paquete con ruta de vuelo";
        Integer validez = 30;
        Integer descuento = 15;
        LocalDate fecha = LocalDate.parse("31/12/2021", DATE_FORMATTER);

        // Datos para la ruta de vuelo
        String aerolinea = "TestAir";
        String nombreAerolinea = "Test Airlines";
        String emailAerolinea = "test@airlines.com";
        String nombreRuta = "TEST123";
        
        try {
            // Crear aerolinea
            iUsuario.ingresarAerolinea(aerolinea, nombreAerolinea, "", emailAerolinea, "", "descripcion", "");
            
            // Crear ciudades
            iVuelo.ingresarDatosCiudad("CiudadOrigen", "PaisOrigen", "AeropuertoOrigen", 
                "DescripcionOrigen", "", LocalDate.now());
            iVuelo.ingresarDatosCiudad("CiudadDestino", "PaisDestino", "AeropuertoDestino", 
                "DescripcionDestino", "", LocalDate.now());

            // Crear ruta de vuelo
            Map<String, Categoria> categorias = new HashMap<>();
            categorias.put("Test", new Categoria("Test"));
            
            iVuelo.ingresarDatosRutaVuelo(aerolinea, nombreRuta, "", "descripcion", "desc corta",
                LocalTime.now(), LocalDate.now(), 100f, 200f, 150f, 
                "CiudadOrigen", "PaisOrigen", "CiudadDestino", "PaisDestino", categorias);

            // Crear paquete y agregarle la ruta
            iPaquete.crearPaquete(nombrePaquete, "", descripcion, validez, descuento, fecha);
            iPaquete.agregarRutaVuelo(nombrePaquete, nombreRuta, EnumAsiento.TURISTA, 2);

            // Verificar que el paquete aparece en el listado con el costo actualizado
            List<DTPaquete> paquetes = iPaquete.listarDTPaquetes();
            assertEquals(1, paquetes.size());
            
            DTPaquete paqueteConRuta = paquetes.get(0);
            assertEquals(nombrePaquete, paqueteConRuta.getNombre());
            assertTrue(paqueteConRuta.getCosto() > 0, "El costo del paquete debería ser mayor a 0 al tener una ruta de vuelo");
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
