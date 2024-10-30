package excepciones;
/**
 * Excepción utilizada para indicar que ya existe un paquete con ese nombre en el sistema.
 * 
 * @author TProg2017
 *
 */
@SuppressWarnings("serial")
public class YaExisteException extends Exception {

    public YaExisteException(String string) {
        super(string);
    }
}

