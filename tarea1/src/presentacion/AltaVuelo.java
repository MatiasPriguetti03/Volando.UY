package presentacion;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.ZoneId;

import logica.ControladorUsuario;
import logica.ControladorVuelo;
import logica.IUsuario;
import logica.IVuelo;
import logica.datatypes.DTInfoRutaVuelo;
import utils.PresentacionUtils;
import excepciones.YaRegistradoException;
import excepciones.EsConjuntoVacioException;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Font;


@SuppressWarnings("serial")
public class AltaVuelo extends JInternalFrame{
	private IVuelo ctrlVuelo;
	private IUsuario ctrlUsuario;
	private JDateChooser dateChooseFechaVuelo;
	private JDateChooser dateChooseFechaAlta;
	private JTextField textNombreVuelo;
	private JTextField textDuracionHora;
	private JTextField textDuracionMin;
	private JTextField textMaxAsientosTurista;
	private JTextField textMaxAsientosEjecutivo;
	private JComboBox<String> combAerolineas;
	private JComboBox<String> combRutasVuelo;
	
	public AltaVuelo() {
		
		setTitle("Alta De Vuelo");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 500, 423);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 174, 130, 121, 30, 0};
		gridBagLayout.rowHeights = new int[]{30, 28, 28, 14, 28, 28, 28, 28, 28, 23, 0, 0, 0, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblAerolineas = new JLabel("Aerolinea:");
		lblAerolineas.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblAerolineas = new GridBagConstraints();
		gbc_lblAerolineas.fill = GridBagConstraints.BOTH;
		gbc_lblAerolineas.insets = new Insets(0, 0, 5, 5);
		gbc_lblAerolineas.gridx = 1;
		gbc_lblAerolineas.gridy = 1;
		getContentPane().add(lblAerolineas, gbc_lblAerolineas);
		
		combAerolineas = new JComboBox<String>();
		GridBagConstraints gbc_combAerolineas = new GridBagConstraints();
		gbc_combAerolineas.gridwidth = 2;
		gbc_combAerolineas.fill = GridBagConstraints.HORIZONTAL;
		gbc_combAerolineas.insets = new Insets(0, 0, 5, 5);
		gbc_combAerolineas.gridx = 2;
		gbc_combAerolineas.gridy = 1;
		getContentPane().add(combAerolineas, gbc_combAerolineas);
		combAerolineas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (combAerolineas.getSelectedItem() != null && combAerolineas.getSelectedItem() != "")
					cargarCombBoxRutasVuelo();
				else
					combRutasVuelo.removeAllItems();
			}
		});
		
		JLabel lblRutasVuelo = new JLabel("Ruta de Vuelo:");
		lblRutasVuelo.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblRutasVuelo = new GridBagConstraints();
		gbc_lblRutasVuelo.fill = GridBagConstraints.BOTH;
		gbc_lblRutasVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblRutasVuelo.gridx = 1;
		gbc_lblRutasVuelo.gridy = 2;
		getContentPane().add(lblRutasVuelo, gbc_lblRutasVuelo);
		
		combRutasVuelo = new JComboBox<String>();
		GridBagConstraints gbc_combRutasVuelo = new GridBagConstraints();
		gbc_combRutasVuelo.gridwidth = 2;
		gbc_combRutasVuelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_combRutasVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_combRutasVuelo.gridx = 2;
		gbc_combRutasVuelo.gridy = 2;
		getContentPane().add(combRutasVuelo, gbc_combRutasVuelo);
		
		JLabel lblInfoVuelo = new JLabel("Informacion del Vuelo");
		lblInfoVuelo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInfoVuelo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblInfoVuelo = new GridBagConstraints();
		gbc_lblInfoVuelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInfoVuelo.gridwidth = 5;
		gbc_lblInfoVuelo.anchor = GridBagConstraints.NORTH;
		gbc_lblInfoVuelo.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfoVuelo.gridx = 0;
		gbc_lblInfoVuelo.gridy = 4;
		getContentPane().add(lblInfoVuelo, gbc_lblInfoVuelo);
		
		JLabel lblNombreVuelo = new JLabel("Nombre del Vuelo:");
		lblNombreVuelo.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNombreVuelo = new GridBagConstraints();
		gbc_lblNombreVuelo.fill = GridBagConstraints.BOTH;
		gbc_lblNombreVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreVuelo.gridx = 1;
		gbc_lblNombreVuelo.gridy = 5;
		getContentPane().add(lblNombreVuelo, gbc_lblNombreVuelo);
		
		textNombreVuelo = new JTextField();
		textNombreVuelo.setColumns(10);
		GridBagConstraints gbc_textNombreVuelo = new GridBagConstraints();
		gbc_textNombreVuelo.gridwidth = 2;
		gbc_textNombreVuelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombreVuelo.anchor = GridBagConstraints.SOUTH;
		gbc_textNombreVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_textNombreVuelo.gridx = 2;
		gbc_textNombreVuelo.gridy = 5;
		getContentPane().add(textNombreVuelo, gbc_textNombreVuelo);
		
		JLabel lblDuracionVuelo = new JLabel("Duracion del Vuelo (HH/MM):");
		lblDuracionVuelo.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDuracionVuelo = new GridBagConstraints();
		gbc_lblDuracionVuelo.fill = GridBagConstraints.BOTH;
		gbc_lblDuracionVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracionVuelo.gridx = 1;
		gbc_lblDuracionVuelo.gridy = 6;
		getContentPane().add(lblDuracionVuelo, gbc_lblDuracionVuelo);
		
		textDuracionHora = new JTextField();
		textDuracionHora.setColumns(10);
		GridBagConstraints gbc_textDuracionHora = new GridBagConstraints();
		gbc_textDuracionHora.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDuracionHora.anchor = GridBagConstraints.SOUTH;
		gbc_textDuracionHora.insets = new Insets(0, 0, 5, 5);
		gbc_textDuracionHora.gridx = 2;
		gbc_textDuracionHora.gridy = 6;
		getContentPane().add(textDuracionHora, gbc_textDuracionHora);
		
		textDuracionMin = new JTextField();
		textDuracionMin.setColumns(10);
		GridBagConstraints gbc_textDuracionMin = new GridBagConstraints();
		gbc_textDuracionMin.insets = new Insets(0, 0, 5, 5);
		gbc_textDuracionMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDuracionMin.gridx = 3;
		gbc_textDuracionMin.gridy = 6;
		getContentPane().add(textDuracionMin, gbc_textDuracionMin);
		
		JLabel lblFechaDelVuelo = new JLabel("Fecha del Vuelo:");
		lblFechaDelVuelo.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblFechaDelVuelo = new GridBagConstraints();
		gbc_lblFechaDelVuelo.fill = GridBagConstraints.BOTH;
		gbc_lblFechaDelVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDelVuelo.gridx = 1;
		gbc_lblFechaDelVuelo.gridy = 7;
		getContentPane().add(lblFechaDelVuelo, gbc_lblFechaDelVuelo);
		
		dateChooseFechaVuelo = new JDateChooser();
		GridBagConstraints gbc_dateChooseFechaVuelo = new GridBagConstraints();
		gbc_dateChooseFechaVuelo.gridwidth = 2;
		gbc_dateChooseFechaVuelo.fill = GridBagConstraints.BOTH;
		gbc_dateChooseFechaVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooseFechaVuelo.gridx = 2;
		gbc_dateChooseFechaVuelo.gridy = 7;
		getContentPane().add(dateChooseFechaVuelo, gbc_dateChooseFechaVuelo);
		
		JLabel lblFechaAlta = new JLabel("Fecha de Alta del Vuelo:");
		lblFechaAlta.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 1;
		gbc_lblFechaAlta.gridy = 8;
		getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
		
		dateChooseFechaAlta = new JDateChooser();
		GridBagConstraints gbc_dateChooseFechaAlta = new GridBagConstraints();
		gbc_dateChooseFechaAlta.gridwidth = 2;
		gbc_dateChooseFechaAlta.fill = GridBagConstraints.BOTH;
		gbc_dateChooseFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooseFechaAlta.gridx = 2;
		gbc_dateChooseFechaAlta.gridy = 8;
		getContentPane().add(dateChooseFechaAlta, gbc_dateChooseFechaAlta);
		
		JLabel lblMaxAsientosTurista = new JLabel("Cant. Asientos de tipo Turista:");
		lblMaxAsientosTurista.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblMaxAsientosTurista = new GridBagConstraints();
		gbc_lblMaxAsientosTurista.fill = GridBagConstraints.BOTH;
		gbc_lblMaxAsientosTurista.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxAsientosTurista.gridx = 1;
		gbc_lblMaxAsientosTurista.gridy = 9;
		getContentPane().add(lblMaxAsientosTurista, gbc_lblMaxAsientosTurista);
		
		textMaxAsientosTurista = new JTextField();
		textMaxAsientosTurista.setColumns(10);
		GridBagConstraints gbc_textMaxAsientosTurista = new GridBagConstraints();
		gbc_textMaxAsientosTurista.gridwidth = 2;
		gbc_textMaxAsientosTurista.fill = GridBagConstraints.HORIZONTAL;
		gbc_textMaxAsientosTurista.anchor = GridBagConstraints.SOUTH;
		gbc_textMaxAsientosTurista.insets = new Insets(0, 0, 5, 5);
		gbc_textMaxAsientosTurista.gridx = 2;
		gbc_textMaxAsientosTurista.gridy = 9;
		getContentPane().add(textMaxAsientosTurista, gbc_textMaxAsientosTurista);
		
		JLabel lblMaxAsientosEjecutivo = new JLabel("Cant. Asientos de tipo Ejecutivo:");
		lblMaxAsientosEjecutivo.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblMaxAsientosEjecutivo = new GridBagConstraints();
		gbc_lblMaxAsientosEjecutivo.fill = GridBagConstraints.BOTH;
		gbc_lblMaxAsientosEjecutivo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxAsientosEjecutivo.gridx = 1;
		gbc_lblMaxAsientosEjecutivo.gridy = 10;
		getContentPane().add(lblMaxAsientosEjecutivo, gbc_lblMaxAsientosEjecutivo);
		
		textMaxAsientosEjecutivo = new JTextField();
		textMaxAsientosEjecutivo.setColumns(10);
		GridBagConstraints gbc_textMaxAsientosEjecutivo = new GridBagConstraints();
		gbc_textMaxAsientosEjecutivo.gridwidth = 2;
		gbc_textMaxAsientosEjecutivo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textMaxAsientosEjecutivo.anchor = GridBagConstraints.SOUTH;
		gbc_textMaxAsientosEjecutivo.insets = new Insets(0, 0, 5, 5);
		gbc_textMaxAsientosEjecutivo.gridx = 2;
		gbc_textMaxAsientosEjecutivo.gridy = 10;
		getContentPane().add(textMaxAsientosEjecutivo, gbc_textMaxAsientosEjecutivo);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(validarCampos())
                	confirmarAltaDeVuelo();
            }
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.anchor = GridBagConstraints.EAST;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 12;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                closeFrame();
	            }
	    });
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 12;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				closeFrame();
			}
		});
	}
	
	// Método que permite cargar un nuevo modelo para el combo con la información
    // Se invoca el método antes de hacer visible el JInternalFrame
	public void cargarComboBoxAerolinea() {
		ctrlUsuario = new ControladorUsuario();
		try {
			PresentacionUtils.cargarComboBox(ctrlUsuario.listarAerolineas(), combAerolineas);
		} catch(EsConjuntoVacioException e) {
			combAerolineas.removeAllItems();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void cargarCombBoxRutasVuelo() {
		ctrlVuelo = new ControladorVuelo();
		String aerolinea = (String) combAerolineas.getSelectedItem();
		try {
			Set<DTInfoRutaVuelo> rutas = ctrlVuelo.listarRutasVuelosAerolinea(aerolinea);
			Iterator<DTInfoRutaVuelo> it = rutas.iterator();
			
			String[] nombreRutas = new String[rutas.size()];
			int i = 0;
			
			while(it.hasNext()) {
				DTInfoRutaVuelo datosRuta = it.next();
				nombreRutas[i] = datosRuta.getName();
				i++;
			}
			
			Arrays.sort(nombreRutas);

			PresentacionUtils.cargarComboBox(nombreRutas, combRutasVuelo);
		} catch(EsConjuntoVacioException e) {
			combRutasVuelo.removeAllItems();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	protected boolean validarCampos() {
		String nombre = this.textNombreVuelo.getText();
		String CantAsientosTurista = this.textMaxAsientosTurista.getText();
		String CantAsientosEjecutivo = this.textMaxAsientosEjecutivo.getText();
		
		if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe selecionar un Nombre", "Alta de Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
		
		
		if (!PresentacionUtils.validarSeleccionComboBox(this, combAerolineas, "Debe seleccionar una Aerolinea")) {
            return false;
        }
		
		if (!PresentacionUtils.validarSeleccionComboBox(this, combRutasVuelo, "Debe seleccionar una Ruta de Vuelo")) {
            return false;
        }
		
		if (dateChooseFechaVuelo.getDate() == null) {
            PresentacionUtils.mostrarError(this, "Debe seleccionar una fecha para el vuelo");
            return false;
        }
		
		if (dateChooseFechaAlta.getDate() == null) {
            PresentacionUtils.mostrarError(this, "Debe seleccionar una fecha para el alta");
            return false;
        }
		
		int asientosTurista;
		int asientosEjecutivo;
		int duracionHoras;
		int duracionMin;
		LocalTime horasVuelo;
		
		try {
			duracionHoras = Integer.parseInt(textDuracionHora.getText());
				if (duracionHoras < 0 || duracionHoras > 23)
					throw new NumberFormatException("Las horas deben ser del 00 al 23");
			duracionMin = Integer.parseInt(textDuracionMin.getText());
			if (duracionMin < 0 || duracionMin > 59)
				throw new NumberFormatException("Los minutos deben ser del 00 al 59"); 
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de Vuelo", JOptionPane.ERROR_MESSAGE);
            	return false;
		}
		
		try {
            asientosTurista = Integer.parseInt(CantAsientosTurista);
            asientosEjecutivo = Integer.parseInt(CantAsientosEjecutivo);
            if (asientosTurista < 0|| asientosEjecutivo < 0) 
            	throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad de asientos debe ser un numero positivo", 
            		"Alta de Vuelo", JOptionPane.ERROR_MESSAGE);
            	return false;
        }
		
		return true;
	}
	
	private void confirmarAltaDeVuelo() {
		String ruta = (String) combRutasVuelo.getSelectedItem();
    	String nombre = textNombreVuelo.getText();
    	LocalDate fechaVuelo = dateChooseFechaVuelo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate fechaAlta = dateChooseFechaAlta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	String strDuracion = textDuracionHora.getText() + ":" + textDuracionMin.getText();
    	LocalTime duracion = PresentacionUtils.parseTime(strDuracion);
    	int maxEje = Integer.parseInt(textMaxAsientosEjecutivo.getText());
    	int maxTur = Integer.parseInt(textMaxAsientosTurista.getText());
    	try {
    		ctrlVuelo.agregarVueloARuta("", ruta, nombre, fechaVuelo, duracion, maxEje, maxTur, fechaAlta);
    		JOptionPane.showMessageDialog(this, "El Vuelo se ha creado con éxito", "Alta de Vuelo",
                    JOptionPane.INFORMATION_MESSAGE);
    		limpiarCampos();
    	}
    	catch (YaRegistradoException e) {
    		PresentacionUtils.mostrarError(this, e.getMessage());
    	}
    }
	
	private void limpiarCampos() {
		PresentacionUtils.limpiarCamposTexto(textNombreVuelo,textDuracionHora, 
				textDuracionMin, textMaxAsientosTurista, textMaxAsientosEjecutivo);
		PresentacionUtils.limpiarComboBoxes(combAerolineas);
		combRutasVuelo.removeAllItems();
		if (combAerolineas.getSelectedItem() != "" && combAerolineas.getSelectedItem() != null)
			combAerolineas.setSelectedIndex(0);
		
        if (dateChooseFechaVuelo != null) {
        	dateChooseFechaVuelo.setDate(null);
        }
        if (dateChooseFechaAlta != null) {
        	dateChooseFechaAlta.setDate(null);
        }
	}
	
	public void prepararFrame() {
		cargarComboBoxAerolinea();
	}
	
	private void closeFrame() {
		limpiarCampos();
		setVisible(false);
	}
}