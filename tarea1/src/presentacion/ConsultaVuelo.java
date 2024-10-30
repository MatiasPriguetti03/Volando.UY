package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

import excepciones.EsConjuntoVacioException;
import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import logica.ReservaVuelo;
import logica.RutaVuelo;
import logica.Vuelo;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTVuelo;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTInfoVuelo;
import utils.PresentacionUtils;

import javax.swing.DefaultComboBoxModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.Font;

@SuppressWarnings("serial")
public class ConsultaVuelo extends JInternalFrame {
	private JComboBox<String> comboAerolineas;
	private JComboBox<String> comboRutaVuelo;
	private JComboBox<String> comboVuelos;
	private JTextField textFieldNombre;
	private JTextField textFieldHora;
	private JTextField textFieldTurMax;
	private JTextField textFieldFechaAlta;
	private JTextField textFieldEjeMax;
	private JTextField textFieldFechaVuelo;
	private JTable tablaDeReservas;
	private JScrollPane listScrollPaneReservas;
	
	
	
	public ConsultaVuelo() {
		
		setTitle("Consulta de Vuelo");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
        setBounds(50, 50, 700, 470);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{59, 68, 92, 79, 102, 0};
		gridBagLayout.rowHeights = new int[]{20, 22, 33, 21, 20, 20, 22, 20, 34, 20, 15, 0, 0, 24};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 5;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		
		JLabel lblNewLabel = new JLabel("Seleccionar Aerolinea:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		comboAerolineas = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboAerolineas, gbc_comboBox);
		comboAerolineas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if (comboAerolineas.getSelectedItem() != null && !("".equals(comboAerolineas.getSelectedItem()))) {
					cargarRutas(comboAerolineas.getSelectedItem().toString());
					comboVuelos.removeAllItems();
				} else {
					PresentacionUtils.limpiarCamposTexto(textFieldNombre, textFieldHora, textFieldTurMax,
			                textFieldFechaAlta, textFieldEjeMax, textFieldFechaVuelo);
					
					limpiarComboVuelo();
					limpiarComboRutaVuelo();
					limpiarTablaReserva();
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Rutas de Vuelo:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 2;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		comboRutaVuelo = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 2;
		getContentPane().add(comboRutaVuelo, gbc_comboBox_1);
		comboRutaVuelo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (comboAerolineas.getSelectedItem() != "" && comboAerolineas.getSelectedItem() != null 
						&& comboRutaVuelo.getSelectedItem() != "") {
					cargarVuelos();
				} else {
					if(comboVuelos != null) comboVuelos.removeAllItems();
					PresentacionUtils.limpiarCamposTexto(textFieldNombre, textFieldHora, textFieldTurMax,
							textFieldFechaAlta, textFieldEjeMax, textFieldFechaVuelo);
					limpiarTablaReserva();
				}
			}
		});
		
		JLabel lblNewLabel_1_1 = new JLabel("Vuelos:");
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 2;
		gbc_lblNewLabel_1_1.gridy = 3;
		getContentPane().add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		comboVuelos = new JComboBox<String>();
		GridBagConstraints gbc_comboVuelos = new GridBagConstraints();
		gbc_comboVuelos.gridwidth = 2;
		gbc_comboVuelos.insets = new Insets(0, 0, 5, 5);
		gbc_comboVuelos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboVuelos.gridx = 3;
		gbc_comboVuelos.gridy = 3;
		getContentPane().add(comboVuelos, gbc_comboVuelos);
		comboVuelos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (comboVuelos.getSelectedItem() != "" && comboVuelos.getSelectedItem() != null) {
					cargarInfoVuelo(comboVuelos.getSelectedItem().toString());
				} else {
					PresentacionUtils.limpiarCamposTexto(textFieldNombre, textFieldHora, textFieldTurMax,
							textFieldFechaAlta, textFieldEjeMax, textFieldFechaVuelo);
					limpiarTablaReserva();
			        
				}
			}
		});
		
		JLabel lblNewLabel_12 = new JLabel("Información del Vuelo Seleccionado");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.gridwidth = 2;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12.gridx = 2;
		gbc_lblNewLabel_12.gridy = 5;
		getContentPane().add(lblNewLabel_12, gbc_lblNewLabel_12);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 6;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 6;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Duracion:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 6;
		getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		textFieldHora = new JTextField();
		textFieldHora.setEditable(false);
		GridBagConstraints gbc_textFieldHora = new GridBagConstraints();
		gbc_textFieldHora.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHora.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHora.gridx = 4;
		gbc_textFieldHora.gridy = 6;
		getContentPane().add(textFieldHora, gbc_textFieldHora);
		textFieldHora.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Fecha de Alta:");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 7;
		getContentPane().add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		textFieldFechaAlta = new JTextField();
		textFieldFechaAlta.setEditable(false);
		GridBagConstraints gbc_textFieldFechaAlta = new GridBagConstraints();
		gbc_textFieldFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFechaAlta.gridx = 2;
		gbc_textFieldFechaAlta.gridy = 7;
		getContentPane().add(textFieldFechaAlta, gbc_textFieldFechaAlta);
		textFieldFechaAlta.setColumns(10);
		
		
		JLabel lblNewLabel_7 = new JLabel("Turistas Maximos:");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 3;
		gbc_lblNewLabel_7.gridy = 7;
		getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		textFieldTurMax = new JTextField();
		textFieldTurMax.setEditable(false);
		GridBagConstraints gbc_textFieldTurMax = new GridBagConstraints();
		gbc_textFieldTurMax.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTurMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTurMax.gridx = 4;
		gbc_textFieldTurMax.gridy = 7;
		getContentPane().add(textFieldTurMax, gbc_textFieldTurMax);
		textFieldTurMax.setColumns(10);
		
		JLabel lblFechaVuelo = new JLabel("Fecha Vuelo");
		GridBagConstraints gbc_lblFechaVuelo = new GridBagConstraints();
		gbc_lblFechaVuelo.anchor = GridBagConstraints.EAST;
		gbc_lblFechaVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaVuelo.gridx = 1;
		gbc_lblFechaVuelo.gridy = 8;
		getContentPane().add(lblFechaVuelo, gbc_lblFechaVuelo);
		
		textFieldFechaVuelo = new JTextField();
		textFieldFechaVuelo.setEditable(false);
		GridBagConstraints gbc_textFieldFechaVuelo = new GridBagConstraints();
		gbc_textFieldFechaVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaVuelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFechaVuelo.gridx = 2;
		gbc_textFieldFechaVuelo.gridy = 8;
		getContentPane().add(textFieldFechaVuelo, gbc_textFieldFechaVuelo);
		textFieldFechaVuelo.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Ejecutivos Maximos:");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 3;
		gbc_lblNewLabel_9.gridy = 8;
		getContentPane().add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		textFieldEjeMax = new JTextField();
		textFieldEjeMax.setEditable(false);
		GridBagConstraints gbc_textFieldEjeMax = new GridBagConstraints();
		gbc_textFieldEjeMax.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEjeMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEjeMax.gridx = 4;
		gbc_textFieldEjeMax.gridy = 8;
		getContentPane().add(textFieldEjeMax, gbc_textFieldEjeMax);
		textFieldEjeMax.setColumns(10);
		
		JLabel lblNewLabel_12_1 = new JLabel("Información de las Reservas");
		lblNewLabel_12_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel_12_1 = new GridBagConstraints();
		gbc_lblNewLabel_12_1.gridwidth = 2;
		gbc_lblNewLabel_12_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12_1.gridx = 2;
		gbc_lblNewLabel_12_1.gridy = 9;
		getContentPane().add(lblNewLabel_12_1, gbc_lblNewLabel_12_1);
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 3;
		gbc_list.gridwidth = 4;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 10;
		
		String[] reservasColumnNames = {"ID Reserva", "Nombre del Cliente", "Fecha de Alta"};
		DefaultTableModel vuelosTableModel = new DefaultTableModel(reservasColumnNames, 0);
		tablaDeReservas = new JTable(vuelosTableModel);
		tablaDeReservas.setPreferredScrollableViewportSize(new Dimension(tablaDeReservas.getPreferredSize().width, 300));
		tablaDeReservas.setDefaultEditor(Object.class, null);
		
        listScrollPaneReservas = new JScrollPane(tablaDeReservas);
        listScrollPaneReservas.setPreferredSize(new Dimension(200, 300));
        listScrollPaneReservas.setViewportView(tablaDeReservas);
        getContentPane().add(listScrollPaneReservas, gbc_list);
        
     // Añadir el evento de clausura
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
	}
	
	private void cargarInfoVuelo(String vuelo) {
		Fabrica fabrica = Fabrica.getInstance();
        IVuelo iVuelo = fabrica.getIVuelo();
        DTVuelo infoVuelo = iVuelo.obtenerDTVuelo(vuelo);
        
        textFieldNombre.setText(infoVuelo.getName());
        textFieldHora.setText(PresentacionUtils.parseLocalTime(infoVuelo.getDuracion()));
        textFieldTurMax.setText(Integer.toString(infoVuelo.getAsientoxMaxTurista()));
        textFieldEjeMax.setText(Integer.toString(infoVuelo.getAsientosMaxEjecutivo()));
        textFieldFechaAlta.setText(PresentacionUtils.parseLocalDate(infoVuelo.getFechaAlta()));
        textFieldFechaVuelo.setText(PresentacionUtils.parseLocalDate(infoVuelo.getFechaVuelo()));
       
        DefaultTableModel modelo = (DefaultTableModel) tablaDeReservas.getModel();
        modelo.setRowCount(0);  // Esto elimina todas las filas
        
        
		List<DTReservaVuelo> reservas = infoVuelo.getReservas();
		for (DTReservaVuelo reserva : reservas) {
			if (reserva == null) {
				return;
			}
			cargarDatosReserva(reserva);
		}

	}
	
	public void cargarAerolineas() {
		limpiarCampos();
		
		Fabrica fabrica = Fabrica.getInstance();
        IUsuario iUsuario = fabrica.getIUsuario();

        try {
	        String[] aerolineas = iUsuario.listarAerolineas();
	        String[] nombresAerolineas = new String[aerolineas.length + 1];
	        nombresAerolineas[0] = "";
	        
	        int i = 1;
	        for (String aerolinea : aerolineas) {
	        	nombresAerolineas[i] = aerolinea;
	        	i++;
	        }
	
			Arrays.sort(nombresAerolineas);
			
			DefaultComboBoxModel<String> model;
	        model = new DefaultComboBoxModel<String>(nombresAerolineas);
	        comboAerolineas.setModel(model);
        }
        catch(EsConjuntoVacioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void cargarRutas(String aerolinea) {
		DefaultTableModel modelo = (DefaultTableModel) tablaDeReservas.getModel();
        modelo.setRowCount(0);
        PresentacionUtils.limpiarCamposTexto(textFieldNombre, textFieldHora, textFieldTurMax,
                textFieldFechaAlta, textFieldEjeMax, textFieldFechaVuelo);
        PresentacionUtils.limpiarComboBoxes(comboRutaVuelo);
        limpiarComboVuelo();
        
		
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		String[] rutas = iUsuario.obtenerRutasAerolinea(aerolinea);
		String[] nombresRutas = new String[rutas.length + 1];
		nombresRutas[0] = "";
		
		int i = 1;
		for (String ruta : rutas) {
			nombresRutas[i] = ruta;
			i++;
		}
		
		Arrays.sort(nombresRutas);
		
		DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(nombresRutas);
        comboRutaVuelo.setModel(model);
	}
	
	private void cargarVuelos(){
		if (comboRutaVuelo.getSelectedItem() == null || "".equals(comboRutaVuelo.getSelectedItem().toString())) {
            return;
        }
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		DTRutaVuelo RV  = iVuelo.obtenerDTRutaVuelo(comboRutaVuelo.getSelectedItem().toString());

		Set<DTInfoVuelo> vuelos = RV.getDTInfoVuelos();
		
		comboVuelos.removeAllItems();
        comboVuelos.addItem("");

		for (DTInfoVuelo vuelo : vuelos) {
			if (vuelo == null) {
				return;
			}
			if (vuelo.getName() == null) {
                return;
			}
			comboVuelos.addItem(vuelo.getName());
		}
	}
	
	private void limpiarCampos() {
		PresentacionUtils.limpiarCamposTexto(textFieldNombre, textFieldHora, textFieldTurMax,
                textFieldFechaAlta, textFieldEjeMax, textFieldFechaVuelo);
		PresentacionUtils.limpiarComboBoxes(comboAerolineas, comboRutaVuelo, comboVuelos);

		DefaultTableModel modelo = (DefaultTableModel) tablaDeReservas.getModel();
        modelo.setRowCount(0);

		limpiarTablaReserva();
		limpiarComboVuelo();
		limpiarComboRutaVuelo();
	}
	
	
	public void listarInfoVueloRutaVuelo(String aerolinea, String ruta, String vuelo) {
		cargarAerolineas();
		comboAerolineas.setSelectedItem(aerolinea);
		cargarRutas(aerolinea);
		comboRutaVuelo.setSelectedItem(ruta);
		cargarInfoVuelo(vuelo);
		comboVuelos.setSelectedItem(vuelo);
	}
	

	public void cargarDatosVuelo(DTReservaVuelo reserva) {
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		IVuelo iVuelo = fabrica.getIVuelo();
		
		String nombreVuelo = reserva.getNameVuelo();
		DTVuelo infoVuelo = iVuelo.obtenerDTVuelo(nombreVuelo);
		
		DTInfoRutaVuelo rutaVuelo = infoVuelo.getRutaVuelo();
		String aerolinea = iUsuario.obtenerNickAerolineaDeRutaVuelo(rutaVuelo.getName());

		cargarAerolineas();
		comboAerolineas.setSelectedItem(aerolinea);
		cargarRutas(aerolinea);
		comboRutaVuelo.setSelectedItem(rutaVuelo.getName());
		comboVuelos.setSelectedItem(infoVuelo.getName());
		
		
		textFieldNombre.setText(nombreVuelo);
		textFieldHora.setText(PresentacionUtils.parseLocalTime(infoVuelo.getDuracion()));
		textFieldTurMax.setText(Integer.toString(infoVuelo.getAsientoxMaxTurista()));
		textFieldEjeMax.setText(Integer.toString(infoVuelo.getAsientosMaxEjecutivo()));
		textFieldFechaAlta.setText(PresentacionUtils.parseLocalDate(infoVuelo.getFechaAlta()));
		textFieldFechaVuelo.setText(PresentacionUtils.parseLocalDate(infoVuelo.getFechaVuelo()));
		
		//cargarDatosReserva(reserva.getDTReservaVuelo());
		
	}
	
	private void cargarDatosReserva(DTReservaVuelo reserva) {
		DefaultTableModel model = (DefaultTableModel) tablaDeReservas.getModel();
        model.addRow(new Object[] {reserva.getId(), reserva.getNickUser(), PresentacionUtils.parseLocalDate(reserva.getFecha())});
	}
	
	private void limpiarTablaReserva() {
		DefaultTableModel modelo = (DefaultTableModel) tablaDeReservas.getModel();
        modelo.setRowCount(0);
	}
	
	private void limpiarComboVuelo() {
		comboVuelos.removeAllItems();
		comboVuelos.addItem("");
	}
	
	private void limpiarComboRutaVuelo() {
		comboRutaVuelo.removeAllItems();
		comboRutaVuelo.addItem("");
	}
	
	public void prepararFrame() {
		cargarAerolineas();
	}
	
}
