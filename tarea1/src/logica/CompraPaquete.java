package logica;

import java.time.LocalDate;

public class CompraPaquete {
	private LocalDate fechaCompra;
	private Float costo;
	private LocalDate fechaVenc;
	private Paquete paquete;
	
	public CompraPaquete(LocalDate fechaComp, Paquete paq) {
		fechaCompra = fechaComp;
		paquete = paq;
		costo = paquete.getCosto();
		fechaVenc = fechaCompra.plusDays(paquete.getPeriodoValidez());
		// usosRestantes = new HashMap<String, Integer>();
		// // Rellenar usos restantes
		// Map<String, PaqueteRutaVuelo> rutasEnPaquete = paquete.getPaquetesRutaVuelo();
		// for (Map.Entry<String, PaqueteRutaVuelo> entry : rutasEnPaquete.entrySet()) {
		// 	String nombreRuta = entry.getKey();
		// 	PaqueteRutaVuelo prv = entry.getValue();
		// 	usosRestantes.put(nombreRuta, prv.getCantidad());
		// }
	}
	
	public Paquete getPaqueteDeCompra() {
		return paquete;
	}
	
	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public LocalDate getFechaVenc() {
		return fechaVenc;
	}

	public Float getCosto() {
		return costo;
	}
	
	// public void usarRutaVuelo(String nombreRuta) {
	// 	Integer usos = usosRestantes.get(nombreRuta);
	// 	if (usos == 0) {
			
	// 	} else {
	// 	usosRestantes.replace(nombreRuta, usos - 1);	
	// 	}
	// }
}
