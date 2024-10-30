package logica;

import java.time.LocalDate;
import java.util.List;

import excepciones.NoExisteException;
import excepciones.YaExisteException;
import excepciones.YaRegistradoException;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTInfoPaquete;
import logica.enums.EnumAsiento;

public interface IPaquete {

	public abstract void crearPaquete(String nombre, String imagen, String descripcion, Integer validez, Integer descuento, LocalDate fechaActual) throws YaExisteException;

	public abstract List<DTInfoPaquete> listarPaquetes();
	
	public abstract List<DTPaquete> listarDTPaquetes();
	
	public abstract List<DTInfoPaquete> listarPaquetesNoComprados();
	
	public abstract List<DTInfoPaquete> listarPaquetesConRutas();
	
	public abstract DTPaquete obtenerDatosPaquete(String nombrePaq) throws NoExisteException;
	
	public abstract void agregarRutaVuelo(String nombrePaq, String nombreRV, EnumAsiento tipoAsiento, int cantidad) throws NoExisteException, YaRegistradoException;
	
	public abstract void comprarPaquete(String nickCient, LocalDate fechaComp, String nombrePaq) throws NoExisteException, YaRegistradoException;

//	public abstract void crearPaquete(String nombre, String descripcion, Integer validez, Integer descuento, LocalDate fechaActual);
//	
//	public DTInfoPaquete obtenerInfoPaquete(String nombre);

}
