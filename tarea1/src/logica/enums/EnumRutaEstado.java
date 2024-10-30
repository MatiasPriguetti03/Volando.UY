package logica.enums;

/**
 * Enumerado para estados de Ruta de Vuelo
 * sus posibles valores son {INGRESADA, CONFIRMADA, RECHAZADA}
*/
public enum EnumRutaEstado {
	INGRESADA, CONFIRMADA, RECHAZADA;

    @Override
    public String toString() {
    	String result;
        switch (this) {
            case INGRESADA:
            	result = "Ingresada";
            	break;
            	
            case CONFIRMADA:
            	result = "Confirmada";
            	break;
            
            case RECHAZADA:
            	result =  "Rechazada";
            	break;
            
            default:
            	result = super.toString();
            	break;
        }
        
        return result;
    }
}
