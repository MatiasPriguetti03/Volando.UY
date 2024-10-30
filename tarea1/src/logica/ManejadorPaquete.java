package logica;

import java.util.HashMap;
import java.util.Map;

public class ManejadorPaquete {
	private static ManejadorPaquete instancia = null;
    private Map<String, Paquete> paquetes;

    private ManejadorPaquete() {
        paquetes = new HashMap<>();
    }

    public static ManejadorPaquete getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorPaquete();
        }
        return instancia;
    }

    public void agregarPaquete(Paquete paquete) {
        paquetes.put(paquete.getNombre(), paquete);
    }

    public Paquete obtenerPaquete(String nombre) {
        return paquetes.get(nombre);
    }

    public boolean existePaquete(String nombre) {
        return paquetes.containsKey(nombre);
    }

    public void eliminarPaquete(String paquete) {
        paquetes.remove(paquete);
    }
    
    public void limpiarPaquetes() {
        paquetes.clear();
    }

    public Map<String, Paquete> getPaquetes() {
        return new HashMap<>(paquetes);
    }
}
