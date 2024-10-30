package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.NoExisteException;
import excepciones.YaRegistradoException;
import logica.Aerolinea;
import logica.Categoria;
import logica.Ciudad;
import logica.Fabrica;
import logica.IUsuario;
import logica.ManejadorUsuario;
import logica.ManejadorVuelo;
import logica.RutaVuelo;
import logica.datatypes.DTAerolinea;
import logica.datatypes.DTCliente;
import logica.datatypes.DTUsuario;
import logica.enums.EnumDoc;
import utils.PresentacionUtils;


public class ControladorUsuarioTest {

	private static IUsuario iUsuario;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		Fabrica fabrica = Fabrica.getInstance();
		iUsuario = fabrica.getIUsuario();
	}
	
	@AfterEach
	public void tearDownAfterClass() {
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        MU.borrarUsuarios();
	}
	
	@Test
	void testIngresarAerolinea() throws Exception {
		
		String nick = "Aerolinea1";
		String nombre = "Aerolinea";
		String email = "Aerolinea@gmail.com";
		String descripcion = "Aerolinea de prueba";
		String sitioWeb = "www.aerolinea.com";
		String contrasenia = "";
		
		try {
				iUsuario.ingresarAerolinea(nick, nombre,  "", email, contrasenia, descripcion, sitioWeb);
				DTAerolinea aerolinea = iUsuario.obtenerDatosAerolinea(nick);
				DTUsuario infoAerolinea = iUsuario.obtenerInfoUsuario(nick);

				assertEquals(aerolinea.getDescripcion(), descripcion);
				assertEquals(aerolinea.getSitioWeb(), sitioWeb);
				assertEquals(infoAerolinea.getNick(), nick);
				assertEquals(infoAerolinea.getNombre(), nombre);
				assertEquals(infoAerolinea.getEmail(), email);

        } catch (YaRegistradoException e) {
	        	fail(e.getMessage());
	        	e.printStackTrace();
		}
	}
	
	@Test
	void testIngresarCliente() throws Exception {
        String nick = "Cliente1";
        String nombre = "Cliente";
        String apellido = "Apellido";
        String email = "cliente@gmail.com";
        String nacionalidad = "Uruguay";
        String documento = "GZ1234567";
        EnumDoc tipoDoc = EnumDoc.CEDULA;
        LocalDate fechaNac = PresentacionUtils.parseDate("01/01/2000");
        String contrasenia = "";
        
        try {  
	        iUsuario.ingresarCliente(nick, nombre,  "", apellido, email, contrasenia, fechaNac, nacionalidad, tipoDoc, documento);
			DTUsuario infoCliente = iUsuario.obtenerInfoUsuario(nick);
			DTCliente datosCliente = iUsuario.obtenerDatosCliente(nick);
			
			assertEquals(infoCliente.getNick(), nick);
			assertEquals(infoCliente.getNombre(), nombre);
			assertEquals(infoCliente.getEmail(), email);
			assertEquals(datosCliente.getApellido(), apellido);
			assertEquals(datosCliente.getNacionalidad(), nacionalidad);
			assertEquals(datosCliente.getFechaNac(), fechaNac);
			assertEquals(datosCliente.getDoc(), documento);
			assertEquals(datosCliente.getTipoDoc(), tipoDoc);;
		
		} catch (YaRegistradoException e) {
	    	fail(e.getMessage());
	    	e.printStackTrace();
		}
	}
	
	@Test
	void testModificarAerolinea() throws Exception {
		String nick = "CarrascoTravel";
		String email = "Carrasco@gmail.com";
		
		String nombre = "Aeropuerto Carrasco";
		String sitioWeb = "www.CarrascoTravel.com";
		String descripcion = "Aeropuerto Carrasco de prueba";
		String contrasenia = "";
		
		String nombre2 = "CarrascoFly";
		String sitioWeb2 = "www.CarrascoTravel2.com";
		String descripcion2 = "Aeropuerto CarrascoFly de prueba 2";
		
		try {
			iUsuario.ingresarAerolinea(nick, nombre,  "", email, contrasenia, descripcion, sitioWeb);
			iUsuario.modificarAerolinea(nick, nombre2, email, contrasenia, descripcion2, sitioWeb2);
			DTAerolinea aerolinea = iUsuario.obtenerDatosAerolinea(nick);
			DTUsuario infoAerolinea = iUsuario.obtenerInfoUsuario(nick);
			
			assertEquals(aerolinea.getDescripcion(), descripcion2);
			assertEquals(aerolinea.getSitioWeb(), sitioWeb2);
			assertEquals(infoAerolinea.getNick(), nick);
			assertEquals(infoAerolinea.getNombre(), nombre2);
			assertEquals(infoAerolinea.getEmail(), email);

	    } catch (YaRegistradoException e) {
	        	fail(e.getMessage());
	        	e.printStackTrace();
		} catch (NoExisteException e) {
				fail(e.getMessage());
				e.printStackTrace();
		}
	}
	
    @Test
	void testModificarCliente() throws Exception {
        String nick = "1Cliente1";
        String nombre = "1Cliente";
        String apellido = "1Apellido";
        String email = "1cliente@gmail.com";
        String nacionalidad = "1Uruguay";
        String documento = "A9876543";
        EnumDoc tipoDoc = EnumDoc.CEDULA;
        LocalDate fechaNac = PresentacionUtils.parseDate("05/01/2000");
        String contrasenia = "";
        
        String nombre2 = "Cliente22";
        String apellido2 = "Apellido2";
        String nacionalidad2 = "Uruguay2";
        String documento2 = "AJ7684359";
        EnumDoc tipoDoc2 = EnumDoc.PASAPORTE;
        LocalDate fechaNac2 = PresentacionUtils.parseDate("26/12/2003");
        
        try {  
	        iUsuario.ingresarCliente(nick, nombre,  "", apellido, email, contrasenia, fechaNac, nacionalidad, tipoDoc, documento);
	        iUsuario.modificarCliente(nick, nombre2, apellido2, email, contrasenia, fechaNac2, nacionalidad2, tipoDoc2, documento2);
			DTUsuario infoCliente = iUsuario.obtenerInfoUsuario(nick);
			DTCliente datosCliente = iUsuario.obtenerDatosCliente(nick);
			
			assertEquals(infoCliente.getNick(), nick);
			assertEquals(infoCliente.getNombre(), nombre2);
			assertEquals(infoCliente.getEmail(), email);
			assertEquals(datosCliente.getApellido(), apellido2);
			assertEquals(datosCliente.getNacionalidad(), nacionalidad2);
			assertEquals(datosCliente.getFechaNac(), fechaNac2);
			assertEquals(datosCliente.getDoc(), documento2);
			assertEquals(datosCliente.getTipoDoc(), tipoDoc2);
		
		} catch (YaRegistradoException e) {
	    	fail(e.getMessage());
	    	e.printStackTrace();
		} catch (NoExisteException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
    
    @Test
    void listarClientesTest() throws Exception {
    	iUsuario.ingresarCliente("car", "carlos",  "", "lopez", "lopez@gmail.com", "", LocalDate.now(), "Uruguay", EnumDoc.CEDULA, "12345678");
    	iUsuario.ingresarCliente("pe", "pepe",  "", "lopez", "pepe@gmial.com", "", LocalDate.now(), "Uruguay", EnumDoc.CEDULA, "12349218");
    	iUsuario.ingresarCliente("ju", "juan",  "", "lopez", "asd@asd.com", "", LocalDate.now(), "Uruguay", EnumDoc.CEDULA, "12345678");
    	
    	String[] clientes = iUsuario.listarClientes();
    	
    	
    	// La lista esta ordenada alfabeticamente por nickname
    	assertEquals(clientes.length, 3);
    	assertEquals(clientes[0], "car");
    	assertEquals(clientes[1], "ju");
    	assertEquals(clientes[2], "pe");
    }
    
    @Test
    void listarAerolineasTest() throws Exception {
    	iUsuario.ingresarAerolinea("carra", "CarrascoTravel",  "", "qwe@gmail", "", "Aeropuertos Carrasco", "www.carrasco6.com");
    	iUsuario.ingresarAerolinea("petr", "PepeTravel",  "", "pepe@gmail", "", "Aeropuertos Pepe", "www.pepe.com");
    	iUsuario.ingresarAerolinea("jutr", "JuanTravel",  "", "juan@gmail", "", "Aeropuertos Juan", "www.juan.com");
    	
    	String[] aerolineas = iUsuario.listarAerolineas();
    	
    	
    	// La lista esta ordenada alfabeticamente por nickname
    	assertEquals(aerolineas.length, 3);
    	assertEquals(aerolineas[0], "carra");
    	assertEquals(aerolineas[1], "jutr");
    	assertEquals(aerolineas[2], "petr");
    }
    
    
    @Test
	void obtenerRutasAerolineaTest() throws Exception {
		iUsuario.ingresarAerolinea("Marco",  "", "MarcoTravel", "Marco@gmail", "", "Aeropuertos marco", "www.Marco2.com");
		
		ManejadorUsuario MU = ManejadorUsuario.getInstancia();
		Aerolinea aerolinea = (Aerolinea) MU.getUsuario("Marco");

		String[] rutas = iUsuario.obtenerRutasAerolinea("Marco");

		assertEquals(rutas.length, 0);
		
		LocalTime hora = PresentacionUtils.parseTime("15:00");
		
		Map<String, Categoria> categoriaRuta1 = new HashMap<>();
		Categoria cat = new Categoria("Primera");
		categoriaRuta1.put(cat.getNombre(), cat);
		
		Map<String, Categoria> categoriaRuta2 = new HashMap<>();
		Categoria cat2 = new Categoria("Segunda");
		categoriaRuta2.put(cat2.getNombre(), cat2);
		
		Map<String, Categoria> categoriaRuta3 = new HashMap<>();
		Categoria cat3 = new Categoria("Tercera");
		categoriaRuta3.put(cat3.getNombre(), cat3);
		
		RutaVuelo ruta = new RutaVuelo("Montevideo", "imagen7.jpg","ruta para uruguay", "descripcion corta", hora, LocalDate.now(), 50, 50, 50, new Ciudad("Santiago", "a", "b", "asd", "asd", LocalDate.now()), 
				new Ciudad("montevideo", "a", "b", "asd", "asd", LocalDate.now()), categoriaRuta1);
		RutaVuelo ruta2 = new RutaVuelo("China", "imagen8.jpg", "ruta para uruguay", "descripcion corta",hora, LocalDate.now(), 50, 50, 50, new Ciudad("Santiago", "a", "b", "asd", "asd", LocalDate.now()), 
				new Ciudad("montevideo", "a", "b", "asd", "asd", LocalDate.now()), categoriaRuta2);
		RutaVuelo ruta3 = new RutaVuelo("Peru", "imagen9.jpg", "ruta para uruguay", "descripcion corta", hora, LocalDate.now(),50, 50, 50, new Ciudad("Santiago", "a", "b", "asd", "asd", LocalDate.now()), 
				new Ciudad("montevideo", "a", "b", "asd", "asd", LocalDate.now()), categoriaRuta3);

		aerolinea.agregarRutaVuelo(ruta);
		aerolinea.agregarRutaVuelo(ruta2);
		aerolinea.agregarRutaVuelo(ruta3);
		
		rutas = iUsuario.obtenerRutasAerolinea("Marco");
		
		
		assertEquals(rutas.length, 3);
		assertEquals(rutas[0], "China");
		assertEquals(rutas[1], "Montevideo");
		assertEquals(rutas[2], "Peru");
		
	}
    
    @Test
    void testListarDTUsuarios() throws Exception {
        // Asegurarse de que no haya usuarios inicialmente
        ManejadorUsuario.getInstancia().borrarUsuarios();

        // listarDTUsuarios debería devolver una lista vacía cuando no haya usuarios
        List<DTUsuario> usuarios = iUsuario.listarDTUsuarios();
        assertTrue(usuarios.isEmpty(), "La lista de usuarios debería estar vacía cuando no hay usuarios.");

        // Agregar algunos usuarios: un cliente y una aerolínea
        iUsuario.ingresarCliente("cliente1", "Carlos", "", "Perez", "carlos@example.com", "", LocalDate.of(1980, 5, 15), "Uruguay", EnumDoc.CEDULA, "12345678");
        iUsuario.ingresarAerolinea("aerolinea1", "Aero1", "", "aero1@example.com", "", "Description1", "www.aero1.com");

        // listarDTUsuarios ahora debería devolver ambos usuarios
        usuarios = iUsuario.listarDTUsuarios();
        assertEquals(2, usuarios.size(), "La lista de usuarios debería contener 2 usuarios.");

        // Verificar que ambos usuarios están en la lista
        boolean foundCliente1 = false;
        boolean foundAerolinea1 = false;
        for (DTUsuario usuario : usuarios) {
            if (usuario.getNick().equals("cliente1")) {
                foundCliente1 = true;
            }
            if (usuario.getNick().equals("aerolinea1")) {
                foundAerolinea1 = true;
            }
        }
        assertTrue(foundCliente1, "La lista de usuarios debería contener 'cliente1'.");
        assertTrue(foundAerolinea1, "La lista de usuarios debería contener 'aerolinea1'.");
    }

    @Test
    void testListarDTUsuariosConTipoUsuario() throws Exception {
        // Asegurarse de que no haya usuarios inicialmente
        ManejadorUsuario.getInstancia().borrarUsuarios();

        // Agregar múltiples clientes y aerolíneas
        iUsuario.ingresarCliente("cliente1", "Carlos", "", "Perez", "carlos@example.com", "", LocalDate.of(1980, 5, 15), "Uruguay", EnumDoc.CEDULA, "12345678");
        iUsuario.ingresarCliente("cliente2", "Ana", "", "Gomez", "ana@example.com", "", LocalDate.of(1985, 7, 20), "Uruguay", EnumDoc.CEDULA, "87654321");
        iUsuario.ingresarAerolinea("aerolinea1", "Aero1", "", "aero1@example.com", "", "Description1", "www.aero1.com");
        iUsuario.ingresarAerolinea("aerolinea2", "Aero2", "", "aero2@example.com", "", "Description2", "www.aero2.com");

        // listarDTUsuarios("Cliente") debería devolver solo los clientes
        List<DTUsuario> clientes = iUsuario.listarDTUsuarios("Cliente");
        assertEquals(2, clientes.size(), "La lista de clientes debería contener 2 clientes.");
        for (DTUsuario cliente : clientes) {
            assertTrue(cliente.getNick().equals("cliente1") || cliente.getNick().equals("cliente2"), "La lista de clientes contiene un usuario inesperado.");
        }

        // listarDTUsuarios("Aerolinea") debería devolver solo las aerolíneas
        List<DTUsuario> aerolineas = iUsuario.listarDTUsuarios("Aerolinea");
        assertEquals(2, aerolineas.size(), "La lista de aerolíneas debería contener 2 aerolíneas.");
        for (DTUsuario aerolinea : aerolineas) {
            assertTrue(aerolinea.getNick().equals("aerolinea1") || aerolinea.getNick().equals("aerolinea2"), "La lista de aerolíneas contiene un usuario inesperado.");
        }

        // Prueba con "cliente" en minúsculas
        clientes = iUsuario.listarDTUsuarios("cliente");
        assertEquals(2, clientes.size(), "La lista de clientes debería contener 2 clientes cuando 'cliente' está en minúsculas.");

        // Prueba con un tipo de usuario inválido
        List<DTUsuario> invalidType = iUsuario.listarDTUsuarios("InvalidType");
        // Según la lógica del método, esto devolverá la lista de aerolíneas
        assertEquals(2, invalidType.size(), "La lista debería contener 2 aerolíneas cuando se proporciona un tipo inválido.");
    }

    @Test
    void testAutenticarUsuario() throws Exception {
        // Borrar los usuarios existentes para asegurar un entorno limpio de prueba
        ManejadorUsuario.getInstancia().borrarUsuarios();

        // Crear un usuario cliente con credenciales conocidas
        String nickCliente = "cliente1";
        String emailCliente = "carlos@example.com";
        String passwordCliente = "password123";
        iUsuario.ingresarCliente(nickCliente, "Carlos", "", "Perez", emailCliente, passwordCliente, LocalDate.of(1980, 5, 15), "Uruguay", EnumDoc.CEDULA, "12345678");

        // Crear un usuario aerolínea con credenciales conocidas
        String nickAerolinea = "aerolinea1";
        String emailAerolinea = "aero1@example.com";
        String passwordAerolinea = "aeroPass456";
        iUsuario.ingresarAerolinea(nickAerolinea, "Aero1", "", emailAerolinea, passwordAerolinea, "Description1", "www.aero1.com");

        // 1. Autenticación exitosa utilizando nickname y contraseña correcta
        DTUsuario usuarioAutenticado = iUsuario.autenticarUsuario(nickCliente, passwordCliente);
        assertNotNull(usuarioAutenticado, "La autenticación debería tener éxito con el nickname y la contraseña correctos.");
        assertEquals(nickCliente, usuarioAutenticado.getNick(), "El usuario autenticado debería tener el nickname correcto.");

        // 2. Autenticación exitosa utilizando email y contraseña correcta
        usuarioAutenticado = iUsuario.autenticarUsuario(emailAerolinea, passwordAerolinea);
        assertNotNull(usuarioAutenticado, "La autenticación debería tener éxito con el email y la contraseña correctos.");
        assertEquals(nickAerolinea, usuarioAutenticado.getNick(), "El usuario autenticado debería tener el nickname correcto.");

        // 3. Fallo de autenticación utilizando nickname y contraseña incorrecta
        try {
            iUsuario.autenticarUsuario(nickCliente, "wrongPassword");
            fail("La autenticación debería fallar con una contraseña incorrecta.");
        } catch (NoExisteException e) {
            assertEquals("Usuario o contraseña incorrecta", e.getMessage(), "El mensaje de la excepción debería indicar usuario o contraseña incorrectos.");
        }

        // 4. Fallo de autenticación utilizando email y contraseña incorrecta
        try {
            iUsuario.autenticarUsuario(emailAerolinea, "wrongPassword");
            fail("La autenticación debería fallar con una contraseña incorrecta.");
        } catch (NoExisteException e) {
            assertEquals("Usuario o contraseña incorrecta", e.getMessage(), "El mensaje de la excepción debería indicar usuario o contraseña incorrectos.");
        }

        // 5. Fallo de autenticación con un nickname inexistente
        try {
            iUsuario.autenticarUsuario("nonExistingUser", "anyPassword");
            fail("La autenticación debería fallar con un usuario inexistente.");
        } catch (NoExisteException e) {
            assertEquals("Usuario incorrecto", e.getMessage(), "El mensaje de la excepción debería indicar usuario incorrecto.");
        }

        // 6. Fallo de autenticación con un email inexistente
        try {
            iUsuario.autenticarUsuario("nonexistent@example.com", "anyPassword");
            fail("La autenticación debería fallar con un usuario inexistente.");
        } catch (NoExisteException e) {
            assertEquals("Usuario incorrecto", e.getMessage(), "El mensaje de la excepción debería indicar usuario incorrecto.");
        }
    }
    
    
}
    