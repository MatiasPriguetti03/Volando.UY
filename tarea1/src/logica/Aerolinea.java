package logica;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import logica.datatypes.DTAerolinea;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTUsuario;

public class Aerolinea extends Usuario {
    private String descripcion;
    private String sitioWeb;
    private Map<String, RutaVuelo> rutasVuelo;
    
 // Constructor
    public Aerolinea(String nickname, String nombre , String imagen, String email, String contrasenia, String descripcion, String sitioWeb) {
        super(email, nickname, nombre, imagen, contrasenia);
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.rutasVuelo = new java.util.HashMap<>();
    }
    
    // Getters
    public String getDescripcion() {
    	return descripcion;
    }
    
    public String getSitioWeb() {
    	return sitioWeb;
    }
    
    public DTUsuario getDTUsuario() {
		DTUsuario datosUsuario = new DTUsuario(getNickname(), getNombre(), getImagen(), getEmail(), getContrasenia());
		return datosUsuario;
	}
    
    // Setters
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    
	public Map<String, RutaVuelo> getRutas() {
        return this.rutasVuelo;
	}
    
    //
    
    public DTAerolinea obtenerDatosAerolinea() {
        List<DTRutaVuelo> rutas = new ArrayList<>();
        for (Map.Entry<String, RutaVuelo> ruta : rutasVuelo.entrySet()) {
            RutaVuelo val = ruta.getValue();
            rutas.add(val.getDTRutaVuelo());
        }
        DTAerolinea dtDatosAerolinea = new DTAerolinea(descripcion, sitioWeb,
                rutas);
        
        return dtDatosAerolinea;
    }
    
	public void agregarRutaVuelo(RutaVuelo rutaVuelo) {
		this.rutasVuelo.put(rutaVuelo.getNombre(), rutaVuelo);

	}


}
