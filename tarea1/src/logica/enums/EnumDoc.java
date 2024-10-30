package logica.enums;

/** 
 * Enumerado para tipo de Documento
 * puede ser CEDULA o PASAPORTE
 */
public enum EnumDoc {
	CEDULA, PASAPORTE;
	
    @Override
    public String toString() {
        String result;
    	switch (this) {
            case CEDULA:
                result = "Cédula";
                break;
                
            case PASAPORTE:
                result = "Pasaporte";
                break;
                
            default:
                result = super.toString();
                break;
        }
    	
    	return result;
    }
}
