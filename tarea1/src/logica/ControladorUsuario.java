package logica;

import logica.enums.EnumDoc;
import excepciones.NoExisteException;
import excepciones.YaRegistradoException;
import excepciones.EsConjuntoVacioException;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalDate;

import logica.datatypes.DTCliente;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTAerolinea;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTRutaVueloPaquete;


public class ControladorUsuario implements IUsuario {
    @Override
    public void ingresarAerolinea(String nick, String nombre, String imagen, String email, String contrasenia, String descripcion, String sitioWeb)
            throws YaRegistradoException {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        boolean existeNickUsuario = manejadorUsuario.existeUsuarioNick(nick);
        boolean existeEmailUsuario = manejadorUsuario.existeUsuarioEmail(email);

        if (existeNickUsuario) {
            throw new YaRegistradoException("Ya existe un usuario con ese nickname");
        }
        if (existeEmailUsuario) {
            throw new YaRegistradoException("Ya existe un usuario con ese email");
        }   
        Aerolinea aerolinea = new Aerolinea(nick, nombre, imagen, email, contrasenia, descripcion, sitioWeb);
        manejadorUsuario.agregarUsuario(aerolinea);
    }

    @Override
    public void ingresarCliente(String nick, String nombre, String imagen, String apellido, String email, String contrasenia, LocalDate fechaNac,
            String nacionalidad, EnumDoc tipoDoc, String documento) throws YaRegistradoException {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
        
        boolean existeNickUsuario = manejadorUsuario.existeUsuarioNick(nick);
        boolean existeEmailUsuario = manejadorUsuario.existeUsuarioEmail(email);

        if (existeNickUsuario) {
            throw new YaRegistradoException("Ya existe un usuario con ese nickname");
        }
        if (existeEmailUsuario) {
            throw new YaRegistradoException("Ya existe un usuario con ese email");
        }
        
        Cliente cliente = new Cliente(nick, nombre, imagen, email, contrasenia, apellido, fechaNac, nacionalidad, tipoDoc, documento);
        manejadorUsuario.agregarUsuario(cliente);
    }

    @Override
    public String[] listarClientes() {
        ManejadorUsuario manUsu = ManejadorUsuario.getInstancia();
        Map<String, Cliente> clientes = manUsu.getClientes();
        Iterator<String> iterator = clientes.keySet().iterator();

        String[] listaClientes = new String[clientes.size()];
        
        int indice = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            listaClientes[indice] = key;
            indice++;
        }
  
        Arrays.sort(listaClientes);
        
        return listaClientes;
    }

    @Override
    public String[] listarAerolineas() throws EsConjuntoVacioException {
        ManejadorUsuario manUsu = ManejadorUsuario.getInstancia();
        Map<String, Aerolinea> aerolineas = manUsu.getAerolineas();
        Iterator<String> iterator = aerolineas.keySet().iterator();

        String[] listaAerolineas = new String[aerolineas.size()];
        
        if (aerolineas == null || aerolineas.isEmpty())
			throw new EsConjuntoVacioException("Aun no existe ninguna Aerolinea en el sistema");
        else {
	        int indice = 0;
	
	        while (iterator.hasNext()) {
	            String key = iterator.next();
	            listaAerolineas[indice] = key;
	            indice++;
	        }
	       
	        Arrays.sort(listaAerolineas);
        }
        
        return listaAerolineas;
    }
    
    public List<DTUsuario> listarDTUsuarios() {
    	ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		
    	List<DTUsuario> listaU = new ArrayList<>();
		Map<String, Usuario> usuarios = manU.getUsuarios(); 
		
		for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
			listaU.add(entry.getValue().getDTUsuario());
		}
		
		Collections.sort(listaU, Comparator.comparing(DTUsuario::getNombre));
		
		return listaU;
    }
    
    public List<DTUsuario> listarDTUsuarios(String tipoUsuario) {
    	ManejadorUsuario manU = ManejadorUsuario.getInstancia();
		
    	List<DTUsuario> listaCliente = new ArrayList<>();
    	List<DTUsuario> listaAerolinea = new ArrayList<>();
		Map<String, Usuario> usuarios = manU.getUsuarios(); 
		
		for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {
			if (usuario.getValue() instanceof Cliente) {
				listaCliente.add(usuario.getValue().getDTUsuario());
				Collections.sort(listaCliente, Comparator.comparing(DTUsuario::getNombre));
			}
			else {
				listaAerolinea.add(usuario.getValue().getDTUsuario());
				Collections.sort(listaAerolinea, Comparator.comparing(DTUsuario::getNombre));
			}
		}
		
		if (tipoUsuario == "Cliente" || tipoUsuario == "cliente")
			return listaCliente;
		else
			return listaAerolinea;
    }

    public void modificarAerolinea(String nick, String nombre, String email, String contrasenia, String descripcion, String sitioWeb)
            throws NoExisteException {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        boolean existeNickUsuario = manejadorUsuario.existeUsuarioNick(nick);
        boolean existeEmailUsuario = manejadorUsuario.existeUsuarioEmail(email);

        if (!existeNickUsuario) {
            throw new NoExisteException("No existe un usuario con ese nickname");
        }
        if (!existeEmailUsuario) {
            throw new NoExisteException("No existe un usuario con ese email");
        }

        Usuario usr = manejadorUsuario.getUsuario(nick);
        Aerolinea aerolinea = (Aerolinea) usr;

        aerolinea.setNombre(nombre);
        aerolinea.setContrasenia(contrasenia);
        aerolinea.setDescripcion(descripcion);
        aerolinea.setSitioWeb(sitioWeb);
    }

    @Override
    public void modificarCliente(String nick, String nombre, String apellido, String email, String contrasenia,
    		LocalDate fechaNac, String nacionalidad, EnumDoc tipoDoc, String documento) throws NoExisteException {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        boolean existeNickUsuario = manejadorUsuario.existeUsuarioNick(nick);
        boolean existeEmailUsuario = manejadorUsuario.existeUsuarioEmail(email);
        
        if (!existeNickUsuario) {
            throw new NoExisteException("No existe un usuario con ese nickname");
        }
        if (!existeEmailUsuario) {
            throw new NoExisteException("No existe un usuario con ese email");
        }
        
        Usuario usr = manejadorUsuario.getUsuario(nick);
        Cliente cliente = (Cliente) usr;

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setContrasenia(contrasenia);
//        cliente.setEmail(email);
        cliente.setFechaNacimiento(fechaNac);
        cliente.setNacionalidad(nacionalidad);
        cliente.setTipoDoc(tipoDoc);
        cliente.setDoc(documento);
    }

    public DTUsuario obtenerInfoUsuario(String nick) {
        Fabrica fabrica = Fabrica.getInstance();
        IUsuario iUsuario = fabrica.getIUsuario();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

    	Usuario usr = manejadorUsuario.getUsuario(nick);
    	
    	String tipoUsuario = iUsuario.determinarTipoUsuario(usr.getNickname());    	
    	String nombre = usr.getNombre();

    	// Revisar
    	if (tipoUsuario == "cliente") {
    		Cliente cliente = (Cliente) usr;
    		nombre = nombre;
    	}
    
    	DTUsuario dtInfoUsuario = new DTUsuario(usr.getNickname(), nombre, usr.getImagen(), usr.getEmail(), usr.getContrasenia());
    	return dtInfoUsuario;        	
    }

    public DTCliente obtenerDatosCliente(String nick) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

    	Usuario usr = manejadorUsuario.getUsuario(nick);
    	
    	Cliente cliente = (Cliente) usr;
    	DTCliente dtDatosCliente = new DTCliente(cliente.getApellido(), cliente.getNacionalidad(),
    			cliente.getFechaNac(), cliente.getTipoDoc(), cliente.getDoc());
    	return dtDatosCliente;        	        	
    }

    public DTAerolinea obtenerDatosAerolinea(String nick) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

    	Usuario usr = manejadorUsuario.getUsuario(nick);
    	Aerolinea aerolinea = (Aerolinea) usr;
    	
    	return aerolinea.obtenerDatosAerolinea();
    }

    public String[] obtenerRutasAerolinea(String nick) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        Usuario usr = manejadorUsuario.getUsuario(nick);
        Aerolinea aerolinea = (Aerolinea) usr;
        Map<String, RutaVuelo> rutas = aerolinea.getRutas();

        String[] listaRutas = new String[rutas.size()];
        int indice = 0;

        for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
            listaRutas[indice] = entry.getKey();
            indice++;
        }

        Arrays.sort(listaRutas);

        return listaRutas;
    }
    
    public String[] obtenerRutasIngresadasAerolinea(String nick) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
        
        Usuario usr = manejadorUsuario.getUsuario(nick);
        Aerolinea aerolinea = (Aerolinea) usr;
        Map<String, RutaVuelo> rutas = aerolinea.getRutas();
        
        List<String> listaRutas = new ArrayList<>();
        
        for (Map.Entry<String, RutaVuelo> entry : rutas.entrySet()) {
            if (entry.getValue().getEstado().toLowerCase().equals("ingresada")) {
                listaRutas.add(entry.getKey());
            }
        }

        String[] rutasArray = listaRutas.toArray(new String[0]);
        Arrays.sort(rutasArray);

        return rutasArray;
    }

    public DTReservaVuelo listarInfoReservaVuelo(String idReserva) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        ReservaVuelo reserva = manejadorUsuario.getReservaVuelo(idReserva);
        
		if (reserva == null) {
			return null;
		}

        return reserva.getDTReservaVuelo();
    }
    
    public String obtenerNickAerolineaDeRutaVuelo(String nombreRV) {
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
        
        Map<String, Aerolinea> aerolineas = manejadorUsuario.getAerolineas();
        for (Map.Entry<String, Aerolinea> entryAerolinea : aerolineas.entrySet()) {
        	String nickAeorolinea = entryAerolinea.getKey();
            Aerolinea aerolinea = entryAerolinea.getValue();
            Map<String, RutaVuelo> mapRutas = aerolinea.getRutas();
            if (mapRutas.containsKey(nombreRV)) {
            	return nickAeorolinea;       
            }
        }
        return "";
    }
    
    public boolean clienteComproPaquete(String nickCliente, String nombrePaquete) throws NoExisteException {
    	ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
		if (!manejadorUsuario.existeUsuarioNick(nickCliente)) {     			
			new NoExisteException("No existe un usuario con ese nickname");
		}
		Cliente cliente = (Cliente) manejadorUsuario.getUsuario(nickCliente);
		return cliente.comproPaquete(nombrePaquete);    			
    }

    public List<DTReservaVuelo> obtenerReservasVueloCliente(String nickCliente) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        Usuario usr = manejadorUsuario.getUsuario(nickCliente);
        Cliente cliente = (Cliente) usr;

        List<DTReservaVuelo> reservasVector = new ArrayList<>();
        
        Map<Integer, ReservaVuelo> reservasMap = cliente.getReservasVuelo();
        for (Map.Entry<Integer, ReservaVuelo> entry : reservasMap.entrySet()) {
			ReservaVuelo reserva = entry.getValue();
			reservasVector.add(reserva.getDTReservaVuelo());
		}
        
        return reservasVector;
    }
    
	public String determinarTipoUsuario(String nick) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

		Usuario usr = manejadorUsuario.getUsuario(nick);
		if (usr instanceof Cliente) {
			return "cliente";
		} else if (usr instanceof Aerolinea) {
			return "aerolinea";
		}
		return null;
	}

        
    public List<DTCompraPaquete> obtenerComprasDePaquetes(String nickCliente) {
        Fabrica fabrica = Fabrica.getInstance();
        ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();

        Usuario usr = manejadorUsuario.getUsuario(nickCliente);
        // 
        //        if (usr == null) throw new Exception() ?
        Cliente cliente = (Cliente) usr;
        
        List<DTCompraPaquete> vector = new ArrayList<>();
        
        Map<String, CompraPaquete> mapCompraPaquete = cliente.getComprasDePaquetes();
        for (Map.Entry<String, CompraPaquete> compras : mapCompraPaquete.entrySet()) {
			CompraPaquete compraPaquete = compras.getValue();
            Paquete paquete = compraPaquete.getPaqueteDeCompra();
            LocalDate fechaCompra = compraPaquete.getFechaCompra();
            Float costo = compraPaquete.getCosto();
            LocalDate fechaVenc = compraPaquete.getFechaVenc();
            Map<String, DTRutaVueloPaquete> dtRutasEnPaquete = new HashMap<String, DTRutaVueloPaquete>();
            Map<String, Integer> usosRestantes = new HashMap<String, Integer>();
            
            Map<String, PaqueteRutaVuelo> rutasEnPaquete = paquete.getPaquetesRutaVuelo();
            for (Map.Entry<String, PaqueteRutaVuelo> ruta : rutasEnPaquete.entrySet()) {
				String nombreRuta = ruta.getKey();
				PaqueteRutaVuelo paqRuta = ruta.getValue();
				dtRutasEnPaquete.put(nombreRuta, paqRuta.getDTRutaVueloPaquete());
				usosRestantes.put(nombreRuta, usosRestantesRutaEnPaqueteParaCliente(nickCliente, nombreRuta, paquete.getNombre()));
			}
			vector.add(new DTCompraPaquete(nickCliente, paquete.getNombre(), paquete.getDescuento(), fechaCompra, costo, fechaVenc, dtRutasEnPaquete, usosRestantes));
		}
        
        return vector;
    }
    
    public Integer usosRestantesRutaEnPaqueteParaCliente(String nickCliente, String nombreRuta, String nombrePaquete) {
    	ManejadorUsuario manU = ManejadorUsuario.getInstancia();
    	ManejadorPaquete manP = ManejadorPaquete.getInstancia();
    	Cliente cliente = (Cliente) manU.getUsuario(nickCliente);
    	Paquete paquete = manP.obtenerPaquete(nombrePaquete);
    	PaqueteRutaVuelo paqueteRuta = paquete.getPaqueteRutaVuelo(nombreRuta);
    	// Seteo los usos igual a la cantidad de rutas en ese paquete
    	Integer usosRest = paqueteRuta.getCantidad();
    	// Por cada pasajero en reserva que se haya hecho con compra paquete resto usos
    	Map<Integer, ReservaVuelo> reservas = cliente.getReservasVuelo();
    	for (Map.Entry<Integer, ReservaVuelo> entry : reservas.entrySet()) {
			ReservaVuelo reserva = entry.getValue();
			// Me fijo en la compra de paquete de la reserva (si existe)
			CompraPaquete compraPaqueteEnReserva = reserva.getCompraPaquete();
			if (compraPaqueteEnReserva != null) {
				// Me fijo si el paquete de la compra es el mismo del que quiero saber los usos restantes
				if (compraPaqueteEnReserva.getPaqueteDeCompra().getNombre().equals(nombrePaquete)) {
					// Me fijo si la reserva contiene la ruta que quiero saber usos restantes
					RutaVuelo rutaEnReserva = reserva.getVuelo().getRutaDeVuelo();
					if (rutaEnReserva.getNombre().equals(nombreRuta)) {
						// Me fijo la cantidad de pasajeros y resto ese numero a los usos restantes
						// Si se permitio la reserva con compra entonces tipo de asiento es el mismo que
						// el de la compra paquete, asi que no controlo eso
						
						// OJO: Chequear si Acompaniantes incluye al propio cliente
						// En principio supongo que si
						usosRest -= reserva.getAcompaniantes().size();
					}
					
				}
			}
		}
		return usosRest;
    	
    }


	public DTUsuario autenticarUsuario(String login, String contrasenia) throws NoExisteException{
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
		// Busco por nick
		if (manejadorUsuario.existeUsuarioNick(login)) {
			Usuario usr = manejadorUsuario.getUsuario(login);
			if (usr.getContrasenia().equals(contrasenia)) {
				return obtenerInfoUsuario(usr.getNickname());
			} else {
				throw new NoExisteException("Usuario o contraseña incorrecta");
			}
		// Sino busco por mail 
		}  else if (manejadorUsuario.existeUsuarioEmail(login)) {
			Usuario usr = manejadorUsuario.getUsuarioPorMail(login);
			if (usr.getContrasenia().equals(contrasenia)) {
				return obtenerInfoUsuario(usr.getNickname());
			} else {
				throw new NoExisteException("Usuario o contraseña incorrecta");
			}
		} else throw new NoExisteException("Usuario incorrecto");
	}
	
	public void updateImagenUsuario(String nick, String imagen) {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstancia();
		
		Usuario usr = manejadorUsuario.getUsuario(nick);
		usr.setImagen(imagen);
	}
}
