package logica;

/**
 * 
 * holiwis
 */
public class Fabrica {
	private static Fabrica instancia;

    private Fabrica() {
    }

    public static Fabrica getInstance() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    public IUsuario getIUsuario() {
        return new ControladorUsuario();
    }

    public IPaquete getIPaquete() {
        return new ControladorPaquete();
    }

    public IVuelo getIVuelo() {
        return new ControladorVuelo();
    }
}