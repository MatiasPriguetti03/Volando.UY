package logica;

import java.util.HashMap;
import java.util.Map;

import logica.datatypes.Pair;

public class ManejadorVuelo {
	private static ManejadorVuelo instancia = null;
    private Map<String, RutaVuelo> rutasVuelo;
    private Map<Pair<String, String>, Ciudad> ciudades;
    private Map<String, Categoria> categorias;
    private Map<String, Vuelo> vuelos;
    
    private ManejadorVuelo() {
        rutasVuelo = new HashMap<>();
        ciudades = new HashMap<>();
        categorias = new HashMap<>();
        vuelos = new HashMap<>();
    }
    
    public static ManejadorVuelo getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorVuelo();
        }
        return instancia;
    }
    
    public void agregarRutaVuelo(RutaVuelo rutaVuelo) {
    	rutasVuelo.put(rutaVuelo.getNombre(), rutaVuelo);
    }
    
    public void eliminarRutaVuelo(String nombre) {
    	rutasVuelo.remove(nombre);
    }
    
    public boolean existeRutaVuelo(String nombre) {
    	return rutasVuelo.containsKey(nombre);
    }
    
    public RutaVuelo obtenerRutaVuelo(String nombre) {
    	return rutasVuelo.get(nombre);
    }
    
    public Map<String, RutaVuelo> getRutasVuelo(){
    	return this.rutasVuelo;
    }
    
    public void agregarCiudad(Ciudad ciudad) {
    	ciudades.put(new Pair<String, String>(ciudad.getNombre(), ciudad.getPais()), ciudad);
    }
    
    public void eliminarCiudad(String nombre, String pais) {
    	ciudades.remove(new Pair<String, String>(nombre, pais));
    }
    
    public boolean existeCiudad(String nombre, String pais) {
    	return ciudades.containsKey(new Pair<String, String>(nombre, pais));
    }
    
    public Ciudad obtenerCiudad(String nombre, String pais) {
    	return ciudades.get(new Pair<String, String>(nombre, pais));
    }
    
	public void agregarVuelo(Vuelo vuelo) {
		vuelos.put(vuelo.getNombre(), vuelo);
	}
	
	public boolean existeVuelo(String nombre) {
    	return vuelos.containsKey(nombre);
    }
	
	public Vuelo obtenerVuelo(String nombre) {
		return vuelos.get(nombre);
	}
    
    public Map<Pair<String, String>, Ciudad> getCiudades(){
    	return this.ciudades;
    }
    
    public void agregarCategoria(Categoria categoria) {
    	categorias.put(categoria.getNombre(), categoria);
    }
    
    public void eliminarCategoria(String nombre) {
    	categorias.remove(nombre);
    }
    
    public boolean existeCategoria(String nombre) {
    	return categorias.containsKey(nombre);
    }
    
    public Categoria obtenerCategoria(String nombre) {
    	return categorias.get(nombre);
    }
    
    public Map<String, Categoria> getCategorias(){
    	return this.categorias;
    }
    
    public void limpiarColecciones() {
    	rutasVuelo.clear();
        ciudades.clear();
        categorias.clear();
        vuelos.clear();
    }
}
