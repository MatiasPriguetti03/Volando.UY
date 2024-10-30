package test;

import logica.Categoria;
import logica.Fabrica;
import logica.IPaquete;
import logica.IUsuario;
import logica.IVuelo;
import logica.ManejadorVuelo;
import logica.datatypes.DTPasajero;
import logica.enums.EnumAsiento;
import logica.enums.EnumDoc;
import utils.PresentacionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Datos {
	
	private static Datos instancia;
	private static Fabrica fabrica;
	private static IUsuario iUsuario;
	private static IVuelo iVuelo;
	private static ManejadorVuelo mVuelo;
	private static IPaquete iPaquete;
	
	public Datos() {
		fabrica = Fabrica.getInstance();
		iUsuario = fabrica.getIUsuario();
		iVuelo = fabrica.getIVuelo();
		mVuelo = ManejadorVuelo.getInstancia();
		iPaquete = fabrica.getIPaquete();
	}
	
	public static Datos getInstancia() {
        if (instancia == null) {
            instancia = new Datos();
        }
        return instancia;
    }
	
	public static void cargarTodo() throws Exception {
		try {
	        cargarDatosUsuarios();
	        cargarDatosCategorias();
	        cargarDatosCiudades();
	        cargarDatosRutasVuelo();
	        cargarDatosVuelos();
	        cargarDatosPaquetes();
	        cargarDatosComprasPaquetes();
	        cargarDatosReservasVuelos();
		} catch (Exception e){
			throw e;
		}
    }

	// SE AGREGARON LAS CONTRASENIAS
	// FALTAN IMAGENES
	public static void cargarDatosUsuarios() throws Exception {
		try {
			// Ingresar aerolíneas
			/* 1 */ iUsuario.ingresarAerolinea("aerolineas", "Aerolíneas Argentinas",  "US01.png",
					"servicioalcliente@aerolineas.com.uy", "zaq1xsw2",
					"Aerolínea nacional de Argentina que ofrece vuelos directos entre múltiples destinos.",
					"https://www.aerolineas.com.ar");
			/* 2 */ iUsuario.ingresarAerolinea("aireuropa", "Air Europa",  "US02.png", "reservas@aireuropa.com.uy", "cde3vfr4",
					"Aerolínea española que ofrece vuelos varios destinos en Europa y América.",
					"https://www.aireuropa.com");
			/* 5 */ iUsuario.ingresarAerolinea("copaair", "Copa Airlines",  "US05.png", "contacto@copaair.com.uy", "2wsx3edc",
					"Aerolínea panameña con conexiones a varios destinos en América y el Caribe",
					"https://www.copaair.com");
			/* 9 */ iUsuario.ingresarAerolinea("iberia", "Iberia",  "US09.png", "atencionclientes@iberia.com.uy", "qwer1234",
					"Aerolínea española que te conecta con Europa y otros destinos internacionales.",
					"https://www.iberia.com");
			/* 12 */ iUsuario.ingresarAerolinea("latam", "LATAM Airlines",  "US12.png", "info@latam.com.uy", "mki8nju7",
					"Ofrecemos vuelos nacionales e internacionales", "https://www.latam.com");
			/* 17 */ iUsuario.ingresarAerolinea("zfly", "ZuluFly",  "US17.png", "info@zfly.com", "r45tgvcf",
					"Viajes exlcusivos entre los destinos más solicitados", "http://www.zfly.com");

			// Ingresar clientes
			iUsuario.ingresarCliente("anarod87", "Ana",  "US03.jpg", "Rodríguez", "arodriguez87@netuy.com", "bgt5nhy6",
					PresentacionUtils.parseDate("18/02/1987"), "Uruguaya", EnumDoc.PASAPORTE, "B2345678");
			iUsuario.ingresarCliente("claire93d", "Claire",  "US04.jpeg", "Rinaldi", "claire.db@frmail.fr", "mju76yhn",
					PresentacionUtils.parseDate("22/08/1993"), "Italiana", EnumDoc.PASAPORTE, "20VF756483");
			iUsuario.ingresarCliente("csexto", "Cristian",  "US06.jpeg", "Sexto", "csexto@adinet.com.uy", "4rfvbgt5",
					PresentacionUtils.parseDate("26/03/1987"), "Uruguaya", EnumDoc.CEDULA, "45871265");
			iUsuario.ingresarCliente("ejstar", "Emily",  "US07.jpeg", "Johnson", "emily.j@hotmail.com", "lkjoiu987",
					PresentacionUtils.parseDate("24/06/1985"), "Estadounidense", EnumDoc.PASAPORTE, "485719842");
			iUsuario.ingresarCliente("hernacar", "Carlos",  "US08.jpg", "Hernández", "carlosh89@mxmail.com", "poi098lkj",
					PresentacionUtils.parseDate("15/09/1988"), "Mexicano", EnumDoc.PASAPORTE, "GZ1234567");
			iUsuario.ingresarCliente("jackwil", "Jack",  "US10.jpeg", "Oliveira", "jack.w.90@mail.br", "asdfzxcv",
					PresentacionUtils.parseDate("10/12/1990"), "Brasilera", EnumDoc.PASAPORTE, "N98123456");
			iUsuario.ingresarCliente("juanitop", "Juan",  "US11.jpeg", "Pérez", "juanito.uy@correo.com", "cde34rfv",
					PresentacionUtils.parseDate("12/03/1990"), "Uruguaya", EnumDoc.CEDULA, "39142842");
			iUsuario.ingresarCliente("liamth", "Liam",  "US13.jpeg", "Thompson", "liam.t.ca@mailbox.com", "bhu7vgy7",
					PresentacionUtils.parseDate("30/11/1992"), "Canadiense", EnumDoc.PASAPORTE, "AJ7684359");
			iUsuario.ingresarCliente("marsil14", "Martín",  "US13.jpeg", "Silva", "m.silva94@webmail.uy", "vgy6cft5",
					PresentacionUtils.parseDate("14/01/1994"), "Uruguaya", EnumDoc.PASAPORTE, "C3456789");
			iUsuario.ingresarCliente("martig", "Marta",  "US15.jpeg", "García", "marta.garcia95@webmail.es", "cft5xdr4",
					PresentacionUtils.parseDate("05/07/1995"), "Española", EnumDoc.PASAPORTE, "X1245786L");
			iUsuario.ingresarCliente("sofi89", "Sofía",  "US16.jpeg", "López", "sofia.lopez@correouruguay.com", "xde2zsw1",
					PresentacionUtils.parseDate("25/04/1989"), "Uruguaya", EnumDoc.PASAPORTE, "A9876543");
		} catch (Exception e) {
			throw e;
		}
	}

	public static void cargarDatosCiudades() throws Exception {
		try {
			// Ciudades
			iVuelo.ingresarDatosCiudad("Montevideo", "Uruguay", "Carrasco",
					"Capital uruguaya, conocida por su Rambla, arquitectura colonial y vibrante vida cultural.",
					"https://montevideo.gub.uy", PresentacionUtils.parseDate("01/04/2024"));
			iVuelo.ingresarDatosCiudad("Múnich", "Alemania", "Aeropuerto de Múnich",
					"Ciudad alemana con rica historia, arquitectura barroca y vibrante vida cultural",
					"https://www.munich.travel/es", PresentacionUtils.parseDate("23/06/2024"));
			iVuelo.ingresarDatosCiudad("Ciudad de Panamá", "Panamá", "Tocumen",
					"Moderno centro urbano con rascacielos, el Canal de Panamá y vibrante vida cultural.",
					"https://www.atp.gob.pa/", PresentacionUtils.parseDate("25/06/2024"));
			iVuelo.ingresarDatosCiudad("Buenos Aires", "Argentina", "Aeroparque Jorge Newbery",
					"Vibrante capital argentina, conocida por su arquitectura, tango y vida cultural.",
					"https://turismo.buenosaires.gob.ar/es", PresentacionUtils.parseDate("05/07/2024"));
			iVuelo.ingresarDatosCiudad("Barcelona", "España", "Josep Tarradellas Barcelona–El Prat",
					"Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.",
					"https://ajuntament.barcelona.cat", PresentacionUtils.parseDate("05/07/2024"));
			iVuelo.ingresarDatosCiudad("Santiago de Chile", "Chile", "Arturo Merino Benı́tez",
					"Capital chilena con moderna arquitectura, cerros y rica vida cultural",
					"https://disfrutasantiago.cl", PresentacionUtils.parseDate("06/07/2024"));
			iVuelo.ingresarDatosCiudad("Punta del Este", "Uruguay", "Laguna del Sauce",
					"Famoso balneario uruguayo, con playas, vida nocturna y lujosos resorts.",
					"https://www.maldonado.gub.uy", PresentacionUtils.parseDate("15/07/2024"));
			iVuelo.ingresarDatosCiudad("Madrid", "España", "Adolfo Suárez Madrid-Barajas",
					"Vibrante capital española con rica historia, cultura y arquitectura impresionante.",
					"https://www.turismomadrid.es/es/", PresentacionUtils.parseDate("12/08/2024"));
			iVuelo.ingresarDatosCiudad("Nueva York", "Estado Unidos", "ohn F. Kennedy",
					"Ciudad icónica con rascacielos, cultura diversa y atracciones famosas", "https://www.nyc.gov/",
					PresentacionUtils.parseDate("25/08/2024"));
			iVuelo.ingresarDatosCiudad("Rı́o de Janeiro", "Brasil", "Galeão Antonio Carlos Jobim",
					"Ciudad costera de Brasil, famosa por sus playas y la estatua del Cristo Redentor.",
					"https://riotur.rio/es/bienvenido/", PresentacionUtils.parseDate("01/07/2024"));
			iVuelo.ingresarDatosCiudad("Sevilla", "España", "Sevilla-San Pablo",
					"Sevilla, es un destino turı́stico fascinante que ofrece una rica mezcla de historia, cultura y belleza.",
					"https://visitasevilla.es/", PresentacionUtils.parseDate("29/02/2024"));
		} catch (Exception e) {
			throw e;
		}
	}

	public static void cargarDatosCategorias() throws Exception {
		try {
			// Cartegorias
			iVuelo.ingresarDatosCategoria("Nacionales"); // 1
			iVuelo.ingresarDatosCategoria("Internacionales"); // 2
			iVuelo.ingresarDatosCategoria("Europa"); // 3
			iVuelo.ingresarDatosCategoria("America "); // 4
			iVuelo.ingresarDatosCategoria("Exclusivos"); // 5
			iVuelo.ingresarDatosCategoria("Temporada"); // 6
			iVuelo.ingresarDatosCategoria("Cortos"); // 7

		} catch (Exception e) {
			throw e;
		}
	}

	public static void cargarDatosRutasVuelo() throws Exception {
		try {
			// Rutas de vuelo
			// ingresarDatosRutaVuelo(String Aerolinea, String nombre, String imagen, String desc, String descCorta, EnumRutaEstado estado, LocalTime hora, LocalDate fechaAlta, float costoExtra, float costoEje, float costoTur, Ciudad origen, Ciudad destino, Map<String, Categoria> categorias)
			Map<String, Categoria> categorias1 = new HashMap<>();
			categorias1.put("Internacionales", new Categoria("Internacionales"));
			categorias1.put("Europa", new Categoria("Europa"));
			iVuelo.ingresarDatosRutaVuelo("aireuropa", "UX46", "RV01.jpeg", "Tiempo de vuelo: aprox. 12 horas con comidas incluidas", "Vuelo a Europa", 
				PresentacionUtils.parseTime("12:20"), PresentacionUtils.parseDate("19/08/2024"), 
				50, 950, 450, "Montevideo", "Uruguay", "Madrid", "España", categorias1);

			Map<String, Categoria> categorias2 = new HashMap<>();
			categorias2.put("Internacionales", new Categoria("Internacionales"));
			categorias2.put("Europa", new Categoria("Europa"));
			categorias2.put("Temporada", new Categoria("Temporada"));
			iVuelo.ingresarDatosRutaVuelo("iberia", "IB6012", "RV02.jpeg",
					"Tiempo de vuelo: cerca de 12 horas. Incluye comidas, bebidas y entretenimiento en vuelo", "Vuelo de larga duración",  
				PresentacionUtils.parseTime("13:00"), PresentacionUtils.parseDate("22/08/2024"), 
				60, 1800, 350, "Montevideo", "Uruguay", "Madrid", "España", categorias2);

			Map<String, Categoria> categorias3 = new HashMap<>();
			categorias3.put("America", new Categoria("America"));
			categorias3.put("Temporada", new Categoria("Temporada"));
			iVuelo.ingresarDatosRutaVuelo("aerolineas", "AR1380", "RV03.jpg",  
					"Tiempo de vuelo 1 hora, directo y sin escalas", "Vuelo corto", 
				PresentacionUtils.parseTime("07:55"), PresentacionUtils.parseDate("09/08/2024"), 
				30, 340, 120, "Buenos Aires", "Argentina", "Montevideo", "Uruguay", categorias3);

			Map<String, Categoria> categorias4 = new HashMap<>();
			categorias4.put("Cortos", new Categoria("Cortos"));
			categorias4.put("America", new Categoria("America"));
			iVuelo.ingresarDatosRutaVuelo("aerolineas", "AR1381", "RV04.jpg", 
					"Tiempo estimado de vuelo 55 minutos", "Vuelo corto", 
				PresentacionUtils.parseTime("09:35"), PresentacionUtils.parseDate("09/08/2024"), 
				30, 400, 160, "Montevideo", "Uruguay", "Buenos Aires", "Argentina", categorias4);

			Map<String, Categoria> categorias5 = new HashMap<>();
			categorias5.put("Exclusivos", new Categoria("Exclusivos"));
			iVuelo.ingresarDatosRutaVuelo("zfly", "ZL1501", "RV05.jpg", 
					"Vuelo exclusivo en aviones pequeños, tiempo de vuelo 20 minutos", "Vuelo exclusivo", 
				PresentacionUtils.parseTime("12:00"), PresentacionUtils.parseDate("11/08/2024"), 
				10, 200, 60, "Montevideo", "Uruguay", "Punta del Este", "Uruguay", categorias5);

			Map<String, Categoria> categorias6 = new HashMap<>();
			categorias6.put("Temporada", new Categoria("Temporada"));
			categorias6.put("Europa", new Categoria("Europa"));
			iVuelo.ingresarDatosRutaVuelo("iberia", "IB6011", "RV06.jpg", 
					"Ruta directa con tiempo aproximado de vuelo de 11 horas", "Vuelo directo a Europa", 
				PresentacionUtils.parseTime("00:10"), PresentacionUtils.parseDate("28/08/2024"), 
				60, 1000, 400, "Madrid", "España", "Montevideo", "Uruguay", categorias6);

			Map<String, Categoria> categorias7 = new HashMap<>();
			categorias7.put("Internacionales", new Categoria("Internacionales"));
			categorias7.put("Europa", new Categoria("Europa"));
			iVuelo.ingresarDatosRutaVuelo("aireuropa", "UX45", "RV07.jpg", 
					"Ideal para quienes buscan una experiencia de vuelo sin complicaciones, con todos los servicios necesarios para un trayecto largo.", 
					"Vuelo largo", PresentacionUtils.parseTime("23:55"), PresentacionUtils.parseDate("25/08/2024"), 
				50, 950, 450, "Madrid", "España", "Montevideo", "Uruguay", categorias7);

			Map<String, Categoria> categorias8 = new HashMap<>();
			categorias8.put("America", new Categoria("America"));
			categorias8.put("Temporada", new Categoria("Temporada"));
			iVuelo.ingresarDatosRutaVuelo("latam", "LA406", "RV08.jpg",  
					"Tiempo de vuelo: alrededor de 2 horas y 30 minutos.", "Vuelo mediano",
				PresentacionUtils.parseTime("12:31"), PresentacionUtils.parseDate("30/07/2024"), 
				50, 500, 100, "Santiago de Chile", "Chile", "Montevideo", "Uruguay", categorias8);

			Map<String, Categoria> categorias9 = new HashMap<String, Categoria>();
			categorias9.put("Nacionales", new Categoria("Nacionales"));
			categorias9.put("Cortos", new Categoria("Cortos"));
			iVuelo.ingresarDatosRutaVuelo("iberia", "IB34", "RV09.jpeg",
					"Tiempo de vuelo: aproximadamente 1 hora y 20 minutos. Ofrece vuelos directos con servicio de bebidas y snacks a bordo. "
					+ "Ideal para un viaje rápido y eficiente entre las dos principales ciudades españolas.", "Vuelo corto",
					PresentacionUtils.parseTime("07:05"), PresentacionUtils.parseDate("16/08/2024"),20, 400, 170, "Madrid", "España", "Barcelona", "España", categorias9);

			Map<String, Categoria> categorias10 = new HashMap<String, Categoria>();
			categorias10.put("Europa", new Categoria("Europa"));
			categorias10.put("Cortos", new Categoria("Cortos"));
			iVuelo.ingresarDatosRutaVuelo("aireuropa", "UX1515", "RV10.jpg", 
					"Tiempo de vuelo: aproximadamente 2 horas y 30 minutos. Ofrece vuelos directos con servicio de bebidas y snacks a bordo, "
					+ "además de entretenimiento para hacer tu viaje más placentero. Ideal para un traslado rápido y cómodo entre las dos ciudades europeas","Vuelo corto",
					PresentacionUtils.parseTime("07:10"), PresentacionUtils.parseDate("22/08/2024"), 60, 250, 50, "Madrid", "España", "Múnich", "Alemania", categorias10);

			Map<String, Categoria> categorias11 = new HashMap<String, Categoria>();
			categorias11.put("America", new Categoria("America"));
			iVuelo.ingresarDatosRutaVuelo("copaair", "CM284", "RV11.jpg", 
					"Tiempo de vuelo: aproximadamente 7 horas. Copa Airlines ofrece comidas, bebidas y entretenimiento a bordo para un viaje cómodo y agradable. "
					+ "Ideal para quienes buscan una conexión eficiente entre Sudamérica y Centroamérica", "Vuelo largo",
					PresentacionUtils.parseTime("00:30"), PresentacionUtils.parseDate("20/07/2024"), 60, 1800, 350, "Montevideo", "Uruguay", "Ciudad de Panamá", "Panamá", categorias11);

			Map<String, Categoria> categorias12 = new HashMap<String, Categoria>();
			categorias12.put("America", new Categoria("America"));
			categorias12.put("Internacionales", new Categoria("Internacionales"));
			iVuelo.ingresarDatosRutaVuelo("copaair", "CM804", "RV12.jpg", 
					"Copa Airlines ofrece comidas, bebidas y entretenimiento a bordo, asegurando una experiencia de viaje cómoda y agradable entre Centroamérica "
					+ "y la ciudad de Nueva York.", "Vuelo largo",
					PresentacionUtils.parseTime("18:33"),  PresentacionUtils.parseDate("27/08/2024"), 15, 1000, 400, "Ciudad de Panamá", "Panamá", "Nueva York", "Estado Unidos", categorias12);

			Map<String, Categoria> categorias13 = new HashMap<String, Categoria>();
			categorias13.put("Internacionales", new Categoria("Internacionales"));
			categorias13.put("Temporada", new Categoria("Temporada"));
			categorias13.put("America", new Categoria("America"));
			iVuelo.ingresarDatosRutaVuelo("latam", "LA533", "RV13.jpg", 
					"LATAM ofrece comidas, bebidas y entretenimiento a bordo para un viaje cómodo y placentero entre Estados Unidos y Chile.","Vuelo largo",
					PresentacionUtils.parseTime("20:05"), PresentacionUtils.parseDate("28/08/2024"), 40, 1600, 600, "Nueva York", "Estado Unidos", "Santiago de Chile", "Chile", categorias13);

			Map<String, Categoria> categorias14 = new HashMap<String, Categoria>();
			categorias14.put("Internacionales", new Categoria("Internacionales"));
			categorias14.put("America", new Categoria("America"));
			iVuelo.ingresarDatosRutaVuelo("copaair", "CM283", null, 
					"Elvuelo tiene una duración aproximada de 7 horas. Ofrece comidas, bebidas y entretenimiento a bordo para una experiencia de viaje cómoda y agradable", "Vuelo largo",
					PresentacionUtils.parseTime("20:10"), PresentacionUtils.parseDate("14/07/2024"), 60, 1700, 500, "Ciudad de Panamá", "Panamá", "Montevideo", "Uruguay", categorias14);

			Map<String, Categoria> categorias15 = new HashMap<String, Categoria>();
			categorias15.put("Temporada", new Categoria("Temporada"));
			iVuelo.ingresarDatosRutaVuelo("latam", "LA407", "RV15.jpg",
					"El vuelo incluye bebidas y snacks a bordo, con una duración aproximada de 2 horas y 30 minutos, ofreciendo una experiencia cómoda entre las dos ciudades sudamericanas", "Vuelo largo",
					PresentacionUtils.parseTime("19:40"), PresentacionUtils.parseDate("02/08/2024"), 50, 500, 150, "Montevideo", "Uruguay", "Santiago de Chile", "Chile", categorias15);

			Map<String, Categoria> categorias16 = new HashMap<String, Categoria>();
			categorias16.put("Internacionales", new Categoria("Internacionales"));
			categorias16.put("America", new Categoria("America"));
			categorias16.put("Cortos", new Categoria("Cortos"));
			iVuelo.ingresarDatosRutaVuelo("zfly", "ZL1502", "RV16.jpeg", 
					"Tiempo estimado de vuelo 2 horas y 30 minutos, vuelo directo.", "Vuelo corto",
					PresentacionUtils.parseTime("12:50"), PresentacionUtils.parseDate("28/07/2024"), 30, 190, 75, "Montevideo", "Uruguay", "Rı́o de Janeiro", "Brasil", categorias16);

			Map<String, Categoria> categorias17 = new HashMap<String, Categoria>();
			categorias17.put("Nacionales", new Categoria("Nacionales"));
			categorias17.put("Europa", new Categoria("Europa"));
			categorias17.put("Cortos", new Categoria("Cortos"));
			iVuelo.ingresarDatosRutaVuelo("iberia", "IB3009", "RV17.jpeg", "Tiempo estimado de vuelo es de 1 hora y 40 minutos.", "Vuelo corto" ,
					PresentacionUtils.parseTime("14:55"), PresentacionUtils.parseDate("20/03/2024"), 20, 250, 140, "Barcelona", "España", "Sevilla", "España", categorias17);

			// Agregamos las rutasVuelo correspondientes a cada Aerolinea

			iVuelo.agregarRutaVueloAAerolinea("aireuropa", mVuelo.obtenerRutaVuelo("UX46"));
			iVuelo.agregarRutaVueloAAerolinea("iberia", mVuelo.obtenerRutaVuelo("IB6012"));
			iVuelo.agregarRutaVueloAAerolinea("aerolineas", mVuelo.obtenerRutaVuelo("AR1380"));
			iVuelo.agregarRutaVueloAAerolinea("aerolineas", mVuelo.obtenerRutaVuelo("AR1381"));
			iVuelo.agregarRutaVueloAAerolinea("zfly", mVuelo.obtenerRutaVuelo("ZL1501"));
			iVuelo.agregarRutaVueloAAerolinea("iberia", mVuelo.obtenerRutaVuelo("IB6011"));
			iVuelo.agregarRutaVueloAAerolinea("aireuropa", mVuelo.obtenerRutaVuelo("UX45"));
			iVuelo.agregarRutaVueloAAerolinea("latam", mVuelo.obtenerRutaVuelo("LA406"));
			iVuelo.agregarRutaVueloAAerolinea("iberia", mVuelo.obtenerRutaVuelo("IB34"));
			iVuelo.agregarRutaVueloAAerolinea("aireuropa", mVuelo.obtenerRutaVuelo("UX1515"));
			iVuelo.agregarRutaVueloAAerolinea("copaair", mVuelo.obtenerRutaVuelo("CM284"));
			iVuelo.agregarRutaVueloAAerolinea("copaair", mVuelo.obtenerRutaVuelo("CM804"));
			iVuelo.agregarRutaVueloAAerolinea("latam", mVuelo.obtenerRutaVuelo("LA533"));
			iVuelo.agregarRutaVueloAAerolinea("copaair", mVuelo.obtenerRutaVuelo("CM283"));
			iVuelo.agregarRutaVueloAAerolinea("latam", mVuelo.obtenerRutaVuelo("LA407"));
			iVuelo.agregarRutaVueloAAerolinea("zfly", mVuelo.obtenerRutaVuelo("ZL1502"));
			iVuelo.agregarRutaVueloAAerolinea("iberia", mVuelo.obtenerRutaVuelo("IB3009"));

		} catch (Exception e) {
			throw e;
		}
	}

	public static void cargarDatosVuelos() throws Exception {
		try {
			// Vuelos
			iVuelo.ingresarDatosVuelo("VU01.jpg", "IB6012272", PresentacionUtils.parseDate("29/09/2024"), 
					PresentacionUtils.parseTime("11:31"), 269, 19, PresentacionUtils.parseDate("24/08/2024"));
			iVuelo.ingresarDatosVuelo("VU02.jpg", "IB6012377", PresentacionUtils.parseDate("03/11/2024"), 
					PresentacionUtils.parseTime("11:29"), 269, 19, PresentacionUtils.parseDate("29/08/2024"));
			iVuelo.ingresarDatosVuelo("VU03.jpg", "IB60124102", PresentacionUtils.parseDate("01/10/2024"), 
					PresentacionUtils.parseTime("11:46"), 269, 19, PresentacionUtils.parseDate("29/08/2024"));
			iVuelo.ingresarDatosVuelo("VU04.jpg", "IB60125114", PresentacionUtils.parseDate("30/09/2024"), 
					PresentacionUtils.parseTime("11:57"), 269, 19, PresentacionUtils.parseDate("28/08/2024"));
			iVuelo.ingresarDatosVuelo("VU05.jpg", "IB6011651", PresentacionUtils.parseDate("29/09/2024"), 
					PresentacionUtils.parseTime("11:56"), 200, 34, PresentacionUtils.parseDate("28/08/2024"));
			iVuelo.ingresarDatosVuelo("VU06.jpg", "IB6011769", PresentacionUtils.parseDate("30/09/2024"), 
					PresentacionUtils.parseTime("12:04"), 200, 34, PresentacionUtils.parseDate("29/08/2024"));
			iVuelo.ingresarDatosVuelo("VU07.jpg", "UX45810", PresentacionUtils.parseDate("30/08/2024"), 
					PresentacionUtils.parseTime("12:01"), 150, 8, PresentacionUtils.parseDate("29/08/2024"));
			iVuelo.ingresarDatosVuelo("VU08.jpg", "AR1380939", PresentacionUtils.parseDate("19/09/2024"), 
					PresentacionUtils.parseTime("00:26"), 153, 16, PresentacionUtils.parseDate("26/08/2024"));
			iVuelo.ingresarDatosVuelo("VU09.jpg", "AR13801059", PresentacionUtils.parseDate("20/09/2024"), 
					PresentacionUtils.parseTime("00:30"), 162, 8, PresentacionUtils.parseDate("27/08/2024"));
			iVuelo.ingresarDatosVuelo("VU10.jpg", "AR138111124", PresentacionUtils.parseDate("24/09/2024"), 
					PresentacionUtils.parseTime("00:47"), 248, 16, PresentacionUtils.parseDate("28/08/2024"));
			iVuelo.ingresarDatosVuelo("VU11.jpg", "AR1381124", PresentacionUtils.parseDate("30/08/2024"), 
					PresentacionUtils.parseTime("00:28"), 162, 8, PresentacionUtils.parseDate("14/08/2024"));
			iVuelo.ingresarDatosVuelo(null, "ZL15011350", PresentacionUtils.parseDate("01/10/2024"), 
					PresentacionUtils.parseTime("00:15"), 2, 2, PresentacionUtils.parseDate("27/08/2024"));
			iVuelo.ingresarDatosVuelo(null, "ZL15011419", PresentacionUtils.parseDate("30/09/2024"),
					PresentacionUtils.parseTime("00:24"), 2, 2, PresentacionUtils.parseDate("26/08/2024"));
			iVuelo.ingresarDatosVuelo(null, "ZL15011527", PresentacionUtils.parseDate("16/09/2024"), 
					PresentacionUtils.parseTime("00:25"), 0, 4, PresentacionUtils.parseDate("16/08/2024"));
			iVuelo.ingresarDatosVuelo("VU15.jpg", "CM2841635", PresentacionUtils.parseDate("28/09/2024"), 
					PresentacionUtils.parseTime("06:55"), 159, 20, PresentacionUtils.parseDate("21/08/2024"));
			iVuelo.ingresarDatosVuelo("VU16.jpg", "CM8041764", PresentacionUtils.parseDate("27/09/2024"), 
					PresentacionUtils.parseTime("04:37"), 160, 16, PresentacionUtils.parseDate("27/08/2024"));
			iVuelo.ingresarDatosVuelo("VU17.jpg", "IB3418130", PresentacionUtils.parseDate("30/09/2024"), 
					PresentacionUtils.parseTime("00:54"), 171, 16, PresentacionUtils.parseDate("29/08/2024"));
			iVuelo.ingresarDatosVuelo("VU18.jpg", "CM2831967", PresentacionUtils.parseDate("25/09/2024"), 
					PresentacionUtils.parseTime("06:27"), 146, 20, PresentacionUtils.parseDate("03/08/2024"));
			iVuelo.ingresarDatosVuelo("VU19.jpg", "CM2842032", PresentacionUtils.parseDate("27/09/2024"), 
					PresentacionUtils.parseTime("06:47"), 147, 20, PresentacionUtils.parseDate("16/08/2024"));
			iVuelo.ingresarDatosVuelo("VU20.jpg", "CM284218", PresentacionUtils.parseDate("29/09/2024"), 
					PresentacionUtils.parseTime("07:03"), 159, 20, PresentacionUtils.parseDate("22/07/2024"));
			iVuelo.ingresarDatosVuelo("VU21.jpg", "IB3009689", PresentacionUtils.parseDate("16/10/2024"), 
					PresentacionUtils.parseTime("01:40"), 120, 45, PresentacionUtils.parseDate("02/09/2024"));

			// Agregamos los vuelos correspondientes a cada rutaVuelo
			iVuelo.agregarVueloARuta("IB6012", mVuelo.obtenerVuelo("IB6012272"));
			iVuelo.agregarVueloARuta("IB6012", mVuelo.obtenerVuelo("IB6012377"));
			iVuelo.agregarVueloARuta("IB6012", mVuelo.obtenerVuelo("IB60124102"));
			iVuelo.agregarVueloARuta("IB6012", mVuelo.obtenerVuelo("IB60125114"));
			iVuelo.agregarVueloARuta("IB6011", mVuelo.obtenerVuelo("IB6011651"));
			iVuelo.agregarVueloARuta("IB6011", mVuelo.obtenerVuelo("IB6011769"));
			iVuelo.agregarVueloARuta("UX46", mVuelo.obtenerVuelo("UX45810"));
			iVuelo.agregarVueloARuta("AR1380", mVuelo.obtenerVuelo("AR1380939"));
			iVuelo.agregarVueloARuta("AR1380", mVuelo.obtenerVuelo("AR13801059"));
			iVuelo.agregarVueloARuta("AR1381", mVuelo.obtenerVuelo("AR138111124"));
			iVuelo.agregarVueloARuta("AR1381", mVuelo.obtenerVuelo("AR1381124"));
			iVuelo.agregarVueloARuta("ZL1501", mVuelo.obtenerVuelo("ZL15011350"));
			iVuelo.agregarVueloARuta("ZL1501", mVuelo.obtenerVuelo("ZL15011419"));
			iVuelo.agregarVueloARuta("ZL1501", mVuelo.obtenerVuelo("ZL15011527"));
			iVuelo.agregarVueloARuta("CM284", mVuelo.obtenerVuelo("CM2841635"));
			iVuelo.agregarVueloARuta("CM804", mVuelo.obtenerVuelo("CM8041764"));
			iVuelo.agregarVueloARuta("IB34", mVuelo.obtenerVuelo("IB3418130"));
			iVuelo.agregarVueloARuta("CM283", mVuelo.obtenerVuelo("CM2831967"));
			iVuelo.agregarVueloARuta("CM284", mVuelo.obtenerVuelo("CM2842032"));
			iVuelo.agregarVueloARuta("CM284", mVuelo.obtenerVuelo("CM284218"));
			iVuelo.agregarVueloARuta("IB3009", mVuelo.obtenerVuelo("IB3009689"));

		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void cargarDatosPaquetes() throws Exception {
		try {
			// Paquetes
			
			// PQ01	Madrid ida y vuelta	Descubre Madrid...	120	50	23/08/2024	1400	PQ01.jpeg				
			iPaquete.crearPaquete("Madrid ida y vuelta",  "PQ01.jpeg",
					"Descubre Madrid con este paquete perfecto para una escapada completa. Disfruta de vuelos directos ida y vuelta, con cómodos horarios que te permiten aprovechar al máximo tu tiempo en la vibrante capital española.",
					120, 50, PresentacionUtils.parseDate("23/08/2024"));
			// PQ02	Cruzar el Charco	Escápate a Buenos Aires...	150	30	25/08/2024	1008	PQ02.jpeg					
			iPaquete.crearPaquete("Cruzar el Charco",  "PQ02.jpeg",
					"Escápate a Buenos Aires  y sumérgete en la vibrante vida de la capital argentina. Este paquete incluye vuelos directos.",
					150, 30, PresentacionUtils.parseDate("25/08/2024"));
			// PQ03	Recorrer España	Descubre lo mejor de España en un solo viaje.	30	10	29/08/2024	594	PQ03.jpeg					
			iPaquete.crearPaquete("Recorrer España",  "PQ03.jpeg", 
					"Descubre lo mejor de España en un solo viaje.",
					 30, 10, PresentacionUtils.parseDate("29/08/2024"));
			// PQ04	Descubre la Ciudad de Panamá		180	50	21/09/2024	2050	PQ04.jpg		
			iPaquete.crearPaquete("Descubre la Ciudad de Panamá",  "PQ04.jpg", 
					"La Ciudad de Panamá es una vibrante metrópoli que combina historia, cultura y modernidad. Conocida como el \"Corazón de las Américas\" esta ciudad es famosa por su impresionante Canal, una obra maestra de la ingeniería que conecta el océano Atlántico y el Pacífico.",
					 180, 50,PresentacionUtils.parseDate("21/09/2024"));
			// PQ05	Destinos Inolvidables: NY - Madrid	Nueva York, la ciudad que...	90	40	25/09/2024	2040	PQ05.jpeg
			iPaquete.crearPaquete("Destinos Inolvidables: NY - Madrid",  "PQ05.jpeg", 
					"Nueva York, la ciudad que nunca duerme te espera con sus icónicas calles, rascacielos impresionantes y una cultura vibrante. Sumérgete a su vez\t en la rica historia y el arte de la capital española, Madrid. Pasea por el majestuoso Parque del Retiro, explora el Museo del Prado y disfruta de la auténtica gastronomía local en sus tapas y mercados.",
					 90, 40,PresentacionUtils.parseDate("25/09/2024"));

			// PR01	PQ01	US09	RV02	1	Ejecutivo
			iPaquete.agregarRutaVuelo("Madrid ida y vuelta", "IB6012", EnumAsiento.EJECUTIVO, 1);
			// PR02	PQ01	US09	RV06	1	Ejecutivo
			iPaquete.agregarRutaVuelo("Madrid ida y vuelta", "IB6011", EnumAsiento.EJECUTIVO, 1);
			
			// PR03	PQ02	US01	RV03	2	Turista
			iPaquete.agregarRutaVuelo("Cruzar el Charco", "AR1380", EnumAsiento.TURISTA, 2);
			// PR04	PQ02	US01	RV04	3	Ejecutivo
			iPaquete.agregarRutaVuelo("Cruzar el Charco", "AR1381", EnumAsiento.EJECUTIVO, 3);
			
			// PR05	PQ03	US09	RV09	1	Turista
			iPaquete.agregarRutaVuelo("Recorrer España", "IB34", EnumAsiento.TURISTA, 1);
			// PR06	PQ03	US09	RV02	1	Turista
			iPaquete.agregarRutaVuelo("Recorrer España", "IB6012", EnumAsiento.TURISTA, 1);
			// PR07	PQ03	US09	RV17	1	Turista
			iPaquete.agregarRutaVuelo("Recorrer España", "IB3009", EnumAsiento.TURISTA, 1);
			
			// PR08	PQ04	US05	RV11	2	Turista
			iPaquete.agregarRutaVuelo("Descubre la Ciudad de Panamá", "CM284", EnumAsiento.TURISTA, 2);
			// PR09	PQ04	US05	RV14	2	Ejecutivo
			iPaquete.agregarRutaVuelo("Descubre la Ciudad de Panamá", "CM283", EnumAsiento.EJECUTIVO, 2);

			// PR10	PQ05	US09	RV02	4	Tursita
			iPaquete.agregarRutaVuelo("Destinos Inolvidables: NY - Madrid", "IB6012", EnumAsiento.TURISTA, 4);
			// PR11	PQ05	US05	RV12	2	Ejecutivo
			iPaquete.agregarRutaVuelo("Destinos Inolvidables: NY - Madrid", "CM804", EnumAsiento.EJECUTIVO, 2); 

		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void cargarDatosComprasPaquetes() throws Exception {
		try {
			// Compras paquetes
//			CO01	PQ02	US16	26/08/2024	23/01/2025	1008
			iPaquete.comprarPaquete("sofi89", PresentacionUtils.parseDate("26/08/2024"), "Cruzar el Charco");
//			CO02	PQ01	US16	26/08/2024	24/12/2024	1400
			iPaquete.comprarPaquete("sofi89", PresentacionUtils.parseDate("26/08/2024"), "Madrid ida y vuelta");
//			CO03	PQ01	US15	27/08/2024	25/12/2024	1400
			iPaquete.comprarPaquete("martig", PresentacionUtils.parseDate("27/08/2024"), "Madrid ida y vuelta");
//			CO04	PQ02	US07	26/08/2024	23/01/2025	1008
			iPaquete.comprarPaquete("ejstar", PresentacionUtils.parseDate("26/08/2024"), "Cruzar el Charco");
//			CO05	PQ04	US11	01/09/2024	28/02/2025	2050
			iPaquete.comprarPaquete("juanitop", PresentacionUtils.parseDate("01/09/2024"), "Descubre la Ciudad de Panamá");
//			CO06	PQ04	US08	14/10/2024	12/04/2025	2050
			iPaquete.comprarPaquete("hernacar", PresentacionUtils.parseDate("14/10/2024"), "Descubre la Ciudad de Panamá");

		} catch (Exception e) {
			throw e;
		}
	}

	public static void cargarDatosReservasVuelos() throws Exception {
		try {
			// Reservas vuelos

//			PA01	RE01	Carlos	Hernández
			List<DTPasajero> pasajeros1 = new ArrayList<DTPasajero>();
//			RE01		US01	RV04	VU11	US07	Ejecutivo	3	3	29/08/2024	450	PQ02
			pasajeros1.add(new DTPasajero("Carlos", "Hernández"));
			iVuelo.crearReserva("IB6012272", "hernacar", EnumAsiento.TURISTA, pasajeros1, 2,
					PresentacionUtils.parseDate("28/08/2024"));
			
//			PA02	RE02	Jack	Oliveira
//			PA02	RE02	Jill	Perk
			List<DTPasajero> pasajeros2 = new ArrayList<DTPasajero>();
			pasajeros2.add(new DTPasajero("Jack ", "Oliveira"));
			pasajeros2.add(new DTPasajero("Jill ", "Perk"));
//			RE02	US09	RV02	VU01	US10	Ejecutivo	2	4	29/08/2024	3840	General
			iVuelo.crearReserva("IB6012272", "jackwil", EnumAsiento.EJECUTIVO, pasajeros2, 4,
					PresentacionUtils.parseDate("29/08/2024"));

//			PA04	RE03	Cristian	Sexto
//			PA05	RE03	Marta	Lopez
			List<DTPasajero> pasajeros3 = new ArrayList<DTPasajero>();
			pasajeros3.add(new DTPasajero("Cristian", "Sexto"));
			pasajeros3.add(new DTPasajero("Marta", "Lopez"));
//			RE03	US17	RV05	VU12	US06	Turista	2	0	30/08/2024	120	General		
			iVuelo.crearReserva("ZL15011350", "csexto", EnumAsiento.TURISTA, pasajeros3, 0,
					PresentacionUtils.parseDate("30/08/2024"));

//			PA06	RE04	Ana	Rodríguez
//			PA07	RE04	Lucas	Morales
			List<DTPasajero> pasajeros4 = new ArrayList<DTPasajero>();
			pasajeros4.add(new DTPasajero("Ana", "Rodrı́guez"));
			pasajeros4.add(new DTPasajero("Lucas", "Morales"));
//			RE04	US17	RV05	VU12	US03	Ejecutivo	2	2	30/08/2024	420	General
			iVuelo.crearReserva("ZL15011350", "anarod87", EnumAsiento.EJECUTIVO, pasajeros4, 2,
					PresentacionUtils.parseDate("30/08/2024"));

//			PA08	RE05	Juan	Pérez
//			PA09	RE05	Franco	Gonzalez
			List<DTPasajero> pasajeros5 = new ArrayList<DTPasajero>();
			pasajeros5.add(new DTPasajero("Juan", "Pérez"));
			pasajeros5.add(new DTPasajero("Franco", "Gonzalez"));
//			RE05	US17	RV05	VU13	US11	Turista	2	1	27/08/2024	130	General	
			iVuelo.crearReserva("ZL15011419", "juanitop", EnumAsiento.TURISTA, pasajeros5, 1,
					PresentacionUtils.parseDate("27/08/2024"));


//			PA10	RE06	Emily	Johnson
//			PA11	RE06	Jack	Jhonson
//			PA12	RE06	Liberty	Trent
//			PA13	RE06	Marc	Ruffalo
//			PA14	RE06	Jessica	Landon
//			PA15	RE06	Robert	Shank
//			PA16	RE06	Frank	Trent
//			PA17	RE06	Lucy	Felton
			List<DTPasajero> pasajeros6 = new ArrayList<DTPasajero>();
			pasajeros6.add(new DTPasajero("Emily", "Johnson"));
			pasajeros6.add(new DTPasajero("Jack", "Jhonson"));
			pasajeros6.add(new DTPasajero("Liberty", "Trent"));
			pasajeros6.add(new DTPasajero("Marc", "Ruffalo"));
			pasajeros6.add(new DTPasajero("Jessica", "Landon"));
			pasajeros6.add(new DTPasajero("Robert", "Shank"));
			pasajeros6.add(new DTPasajero("Frank", "Trent"));
			pasajeros6.add(new DTPasajero("Lucy", "Felton"));
//			RE06	US01	RV03	VU09	US07	Ejecutivo	8	5	28/08/2024	2870	General
			iVuelo.crearReserva("AR13801059", "ejstar", EnumAsiento.EJECUTIVO, pasajeros6, 5,
					PresentacionUtils.parseDate("28/08/2024"));

//			PA18	RE07	Emily	Johnson
//			PA19	RE07	Jack	Jhonson
//			PA20	RE07	Trent	Jhonson
			List<DTPasajero> pasajeros7 = new ArrayList<DTPasajero>();
			pasajeros7.add(new DTPasajero("Emily", "Johnson"));
			pasajeros7.add(new DTPasajero("Jack", "Jhonson"));
			pasajeros7.add(new DTPasajero("Trent", "Jhonson"));
//			RE07	US01	RV04	VU11	US07	Ejecutivo	3	3	29/08/2024	450	PQ02
			iVuelo.crearReserva("AR1381124", "ejstar", EnumAsiento.EJECUTIVO, pasajeros7, 3,
					PresentacionUtils.parseDate("29/08/2024"), "Cruzar el Charco");

//			PA21	RE08	Emily	Johnson
			List<DTPasajero> pasajeros8 = new ArrayList<DTPasajero>();
			pasajeros8.add(new DTPasajero("Emily", "Johnson"));
//			RE08	US01	RV04	VU10	US07	Ejecutivo	1	1	30/08/2024	430	General
			iVuelo.crearReserva("AR138111124", "ejstar", EnumAsiento.EJECUTIVO, pasajeros8, 1,
					PresentacionUtils.parseDate("29/08/2024"));

//			PA22	RE09	Juan	Pérez
			List<DTPasajero> pasajeros9 = new ArrayList<DTPasajero>();
			pasajeros9.add(new DTPasajero("Juan", "Perez"));
//			RE09	US05	RV11	VU15	US11	Turista	1	1	30/09/2024	235	PQ04
			iVuelo.crearReserva("CM2841635", "juanitop", EnumAsiento.TURISTA, pasajeros9, 1,
					PresentacionUtils.parseDate("30/08/2024"), "Descubre la Ciudad de Panamá");
			
//			PA23	RE10	Carlos	Hernández
//			PA24	RE10	Marta	Lima
			List<DTPasajero> pasajeros10 = new ArrayList<DTPasajero>();
			pasajeros10.add(new DTPasajero("Carlos", "Hernández"));
			pasajeros10.add(new DTPasajero("Marta", "Lima"));
//			RE10	US05	RV11	VU19	US08	Turista	2	1	01/10/2024	410	PQ04
			iVuelo.crearReserva("CM2842032", "hernacar", EnumAsiento.TURISTA, pasajeros10, 1,
					PresentacionUtils.parseDate("01/10/2024"), "Descubre la Ciudad de Panamá");	
		
//			PA25	RE11	Carlos	Hernández
//			PA26	RE11	Marta	Lima
			List<DTPasajero> pasajeros11 = new ArrayList<DTPasajero>();
			pasajeros11.add(new DTPasajero("Carlos", "Hernández"));
			pasajeros11.add(new DTPasajero("Marta", "Lima"));
//			RE11	US05	RV14	VU18	US08	Ejecutivo	2	2	01/10/2024	1820	PQ04
			iVuelo.crearReserva("CM2831967", "hernacar", EnumAsiento.EJECUTIVO, pasajeros11, 2,
					PresentacionUtils.parseDate("01/10/2024"), "Descubre la Ciudad de Panamá");


		} catch (Exception e) {
			throw e;
		}
	}
}
