package logica;

import logica.datatypes.DTAerolinea;
import logica.datatypes.DTCliente;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTReservaVuelo;
import logica.enums.EnumDoc;

import java.time.LocalDate;
import java.util.List;

import excepciones.EsConjuntoVacioException;
import excepciones.NoExisteException;
import excepciones.YaRegistradoException;

public interface IUsuario {

	public abstract void ingresarAerolinea(String nick, String nombre, String imagen, String email, String contrasenia, String descripcion, String sitioWeb) throws YaRegistradoException;

    public abstract void ingresarCliente(String nick, String nombre, String imagen, String apellido, String email, String contrasenia, LocalDate fechaNac, String nacionalidad, EnumDoc tipoDoc, String documento) throws YaRegistradoException;
    
    public abstract String[] listarAerolineas() throws EsConjuntoVacioException;
    
    public abstract String[] listarClientes() throws EsConjuntoVacioException;
    
    public abstract List<DTUsuario> listarDTUsuarios();
    
    public abstract List<DTUsuario> listarDTUsuarios(String tipoUsuario);
    
    public abstract void modificarAerolinea(String nick, String nombre, String email, String contrasenia, String descripcion, String sitioWeb) throws NoExisteException;
    
    public abstract void modificarCliente(String nick, String nombre, String apellido, String email, String contrasenia, LocalDate fechaNac, String nacionalidad, EnumDoc tipoDoc, String documento) throws NoExisteException;

    public abstract DTUsuario obtenerInfoUsuario(String nick);
    
    public abstract DTCliente obtenerDatosCliente(String nick);
    
    public abstract DTAerolinea obtenerDatosAerolinea(String nick);
    
    public abstract String[] obtenerRutasAerolinea(String nick);
    
    public abstract String[] obtenerRutasIngresadasAerolinea(String nick);

	public abstract DTReservaVuelo listarInfoReservaVuelo(String idReserva);
	
	public abstract List<DTReservaVuelo> obtenerReservasVueloCliente(String nickCliente);
	
	public abstract String obtenerNickAerolineaDeRutaVuelo(String nombreRV);
	
	public abstract boolean clienteComproPaquete(String nickCliente, String nombrePaquete) throws NoExisteException;

    public abstract DTUsuario autenticarUsuario(String login, String contrasenia) throws NoExisteException;

	public abstract List<DTCompraPaquete> obtenerComprasDePaquetes(String nickCliente);
	
	public abstract String determinarTipoUsuario(String nick);
	
	public abstract void updateImagenUsuario(String nick, String imagen);
}
