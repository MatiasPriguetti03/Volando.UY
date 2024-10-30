package logica.enums;

/**
 * Enumerado para tipos de Asiento
 * con valores posibles: TURISTA y EJECUTIVO
*/
public enum EnumAsiento {
	TURISTA, EJECUTIVO;
	
	@Override
    public String toString() {
        String result;
		switch (this) {
            case TURISTA:
                result = "Turista";
                break;
                
            case EJECUTIVO:
            	result = "Ejecutivo";
            	break;
            	
            default:
            	result = "";
            	break;
		}
		
		return result;
    }
}
