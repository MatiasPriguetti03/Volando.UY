package presentacion;

import java.util.Arrays;
import java.util.Set;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import excepciones.EsConjuntoVacioException;

import javax.swing.BorderFactory;
import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import logica.datatypes.DTRutaVuelo;
import logica.datatypes.DTInfoVuelo;
import utils.PresentacionUtils;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ConsultaRutaVuelo extends JInternalFrame {
	private JComboBox<String> comboAerolineas;
	private JComboBox<String> comboRutas;
	private JTextField textFieldNombre;
	private JTextField textFieldCostoExtra;
	private JTextField textFieldHora;
	private JTextField textFieldCostoEje;
	private JTextField textFieldCostoTur;
	private JTextField textFieldOrigen;
	private JTextField textFieldDestino;
	private JTextArea textAreaDescripcion;
	private JTextArea textAreaDescripcionCorta;
	private JComboBox<String> comboBoxCategorias;
	private JTextField textFieldFechaAlta;
	private JComboBox<String> comboBoxVuelos;
	private ConsultaVuelo conVue;
	
	
	public ConsultaRutaVuelo() {
		
		setTitle("Consulta de Ruta de Vuelo");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(0, 100, 800, 420);
        
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 106, 168, 79, 211, 0};
		gridBagLayout.rowHeights = new int[]{10, 22, 33, 21, 20, 20, 77, 20, 23, 20, 0, 0, 10};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		addInternalFrameListener(new InternalFrameAdapter() {
		    @Override
		    public void internalFrameClosing(InternalFrameEvent e) {
		        limpiarCampos();
		        setVisible(false);
		    }
		});
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		getContentPane().add(panel_2, gbc_panel_2);
		
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
				if(comboAerolineas.getSelectedItem() != null && !("".equals(comboAerolineas.getSelectedItem()))) {
					cargarRutas(comboAerolineas.getSelectedItem().toString());
				}else {
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
					comboRutas.setModel(model);
					
				}
				limpiarCampos();
			}
		});
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridwidth = 4;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		getContentPane().add(separator, gbc_separator);
		
		JLabel lblNewLabel_1 = new JLabel("Rutas de Vuelo:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		comboRutas = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 3;
		getContentPane().add(comboRutas, gbc_comboBox_1);
		comboRutas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(comboRutas.getSelectedItem() != null && !("".equals(comboRutas.getSelectedItem()))) {
					cargarInfoRuta(comboRutas.getSelectedItem().toString());
				}else {
					limpiarCampos();
				}
			}
		});
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.gridwidth = 4;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 1;
		gbc_separator_1.gridy = 4;
		getContentPane().add(separator_1, gbc_separator_1);
		
		JLabel lblNewLabel_12 = new JLabel("Información de la Ruta Seleccionada");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.gridwidth = 6;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_12.gridx = 0;
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
		
		JLabel lblNewLabel_3 = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 6;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setRows(3);
		textAreaDescripcion.setLineWrap(true);
        textAreaDescripcion.setWrapStyleWord(true);
        textAreaDescripcion.setBorder(BorderFactory.createLineBorder(new Color(194, 194, 194), 1));
        textAreaDescripcion.setEditable(false);
		textAreaDescripcion.setEditable(false);
		GridBagConstraints gbc_textAreaDescripcion = new GridBagConstraints();
		gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
		//gbc_textAreaDescripcion.insets = new Insets(5, 5, 5, 5);
		gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion.gridx = 4;
		gbc_textAreaDescripcion.gridy = 6;
		getContentPane().add(textAreaDescripcion, gbc_textAreaDescripcion);
		
		JLabel lblNewLabel_4 = new JLabel("Costo Equipaje Extra:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 7;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textFieldCostoExtra = new JTextField();
		textFieldCostoExtra.setEditable(false);
		GridBagConstraints gbc_textFieldCostoExtra = new GridBagConstraints();
		gbc_textFieldCostoExtra.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCostoExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCostoExtra.gridx = 2;
		gbc_textFieldCostoExtra.gridy = 7;
		getContentPane().add(textFieldCostoExtra, gbc_textFieldCostoExtra);
		textFieldCostoExtra.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Descripcion Corta:");
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.gridx = 3;
		gbc_lblNewLabel_3_1.gridy = 7;
		getContentPane().add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		textAreaDescripcionCorta = new JTextArea();
		textAreaDescripcionCorta.setWrapStyleWord(true); 
		textAreaDescripcionCorta.setRows(3);
		textAreaDescripcionCorta.setLineWrap(true);
		textAreaDescripcionCorta.setEditable(false);
		textAreaDescripcionCorta.setBorder(BorderFactory.createLineBorder(new Color(194, 194, 194), 1));
		GridBagConstraints gbc_textAreaDescripcion_1 = new GridBagConstraints();
		gbc_textAreaDescripcion_1.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaDescripcion_1.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion_1.gridx = 4;
		gbc_textAreaDescripcion_1.gridy = 7;
		getContentPane().add(textAreaDescripcionCorta, gbc_textAreaDescripcion_1);
		
		JLabel lblNewLabel_6 = new JLabel("Costo Ejecutivo:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 8;
		getContentPane().add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		textFieldCostoEje = new JTextField();
		textFieldCostoEje.setEditable(false);
		GridBagConstraints gbc_textFieldCostoEje = new GridBagConstraints();
		gbc_textFieldCostoEje.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCostoEje.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCostoEje.gridx = 2;
		gbc_textFieldCostoEje.gridy = 8;
		getContentPane().add(textFieldCostoEje, gbc_textFieldCostoEje);
		textFieldCostoEje.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Hora:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 8;
		getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		textFieldHora = new JTextField();
		textFieldHora.setEditable(false);
		GridBagConstraints gbc_textFieldHora = new GridBagConstraints();
		gbc_textFieldHora.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHora.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHora.gridx = 4;
		gbc_textFieldHora.gridy = 8;
		getContentPane().add(textFieldHora, gbc_textFieldHora);
		textFieldHora.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Ciudad Origen:");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 9;
		getContentPane().add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		textFieldOrigen = new JTextField();
		textFieldOrigen.setEditable(false);
		GridBagConstraints gbc_textFieldOrigen = new GridBagConstraints();
		gbc_textFieldOrigen.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldOrigen.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldOrigen.gridx = 2;
		gbc_textFieldOrigen.gridy = 9;
		getContentPane().add(textFieldOrigen, gbc_textFieldOrigen);
		textFieldOrigen.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Costo Turista:");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 3;
		gbc_lblNewLabel_7.gridy = 9;
		getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		textFieldCostoTur = new JTextField();
		textFieldCostoTur.setEditable(false);
		GridBagConstraints gbc_textFieldCostoTur = new GridBagConstraints();
		gbc_textFieldCostoTur.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCostoTur.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCostoTur.gridx = 4;
		gbc_textFieldCostoTur.gridy = 9;
		getContentPane().add(textFieldCostoTur, gbc_textFieldCostoTur);
		textFieldCostoTur.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Fecha Alta:");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 1;
		gbc_lblNewLabel_10.gridy = 10;
		getContentPane().add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		textFieldFechaAlta = new JTextField();
		textFieldFechaAlta.setEditable(false);
		GridBagConstraints gbc_textFieldFechaAlta = new GridBagConstraints();
		gbc_textFieldFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFechaAlta.gridx = 2;
		gbc_textFieldFechaAlta.gridy = 10;
		getContentPane().add(textFieldFechaAlta, gbc_textFieldFechaAlta);
		textFieldFechaAlta.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Ciudad Destino:");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 3;
		gbc_lblNewLabel_9.gridy = 10;
		getContentPane().add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		textFieldDestino = new JTextField();
		textFieldDestino.setEditable(false);
		GridBagConstraints gbc_textFieldDestino = new GridBagConstraints();
		gbc_textFieldDestino.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDestino.gridx = 4;
		gbc_textFieldDestino.gridy = 10;
		getContentPane().add(textFieldDestino, gbc_textFieldDestino);
		textFieldDestino.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("Vuelos:");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 1;
		gbc_lblNewLabel_13.gridy = 11;
		getContentPane().add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		comboBoxVuelos = new JComboBox<String>();
		GridBagConstraints gbc_comboBox1 = new GridBagConstraints();
		gbc_comboBox1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox1.gridx = 2;
		gbc_comboBox1.gridy = 11;
		getContentPane().add(comboBoxVuelos, gbc_comboBox1);
		comboBoxVuelos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(comboBoxVuelos.getSelectedItem() != null && !("".equals(comboBoxVuelos.getSelectedItem()))) {
					//cargarInfoRuta(comboBoxVuelos.getSelectedItem().toString());
					conVue.prepararFrame();
					conVue.listarInfoVueloRutaVuelo(comboAerolineas.getSelectedItem().toString(), comboRutas.getSelectedItem().toString(), comboBoxVuelos.getSelectedItem().toString());
					conVue.setVisible(true);
				}
			}
		});
		
		JLabel lblNewLabel_11 = new JLabel("Categorias:");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_11.gridx = 3;
		gbc_lblNewLabel_11.gridy = 11;
		getContentPane().add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		comboBoxCategorias = new JComboBox<String>();
		GridBagConstraints gbc_comboBoxCategorias = new GridBagConstraints();
		gbc_comboBoxCategorias.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxCategorias.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategorias.gridx = 4;
		gbc_comboBoxCategorias.gridy = 11;
		getContentPane().add(comboBoxCategorias, gbc_comboBoxCategorias);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 5;
		gbc_panel_1.gridy = 12;
		getContentPane().add(panel_1, gbc_panel_1);
	}
	
	private void cargarInfoRuta(String ruta) {
		Fabrica fabrica = Fabrica.getInstance();
        IVuelo iVuelo = fabrica.getIVuelo();
        DTRutaVuelo infoRuta = iVuelo.obtenerDTRutaVuelo(ruta);
        
        textFieldNombre.setText(infoRuta.getName());
        textAreaDescripcion.setText(infoRuta.getDesc());
        textAreaDescripcionCorta.setText(infoRuta.getDescCorta());
        textFieldCostoExtra.setText(Float.toString(infoRuta.getCostoExtra()));
        textFieldHora.setText(PresentacionUtils.parseLocalTime(infoRuta.getHora()));
        textFieldCostoEje.setText(Float.toString(infoRuta.getCostoEjecutivo()));
        textFieldCostoTur.setText(Float.toString(infoRuta.getCostoTurista()));
        textFieldOrigen.setText(infoRuta.getOrigen());
        textFieldDestino.setText(infoRuta.getDestino());
        textFieldFechaAlta.setText(PresentacionUtils.parseLocalDate(infoRuta.getFechaAlta()));
        
        DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(infoRuta.getCategorias());
        comboBoxCategorias.setModel(model);
        
        cargarVuelos();
	}
	
	public void cargarAerolineas() {
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
        comboRutas.setModel(model);
	}
	
	private void cargarVuelos(){
		if (comboRutas.getSelectedItem() == null || "".equals(comboRutas.getSelectedItem().toString())) {
            return;
        }
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		DTRutaVuelo RV  = iVuelo.obtenerDTRutaVuelo(comboRutas.getSelectedItem().toString());
		Set<DTInfoVuelo> vuelos = RV.getDTInfoVuelos();
		
        comboBoxVuelos.removeAllItems();
        comboBoxVuelos.addItem("");

		for (DTInfoVuelo vuelo : vuelos) {
			if (vuelo == null) {
				return;
			}
			comboBoxVuelos.addItem(vuelo.getName());
		}
	}
	
	private void limpiarCampos() {
		PresentacionUtils.limpiarCamposTexto(textFieldNombre, textAreaDescripcionCorta,textAreaDescripcion, textFieldHora, textFieldCostoExtra, textFieldCostoEje, textFieldCostoTur, textFieldOrigen, textFieldDestino, textFieldFechaAlta);
		//PresentacionUtils.limpiarComboBoxes(comboAerolineas,comboRutas,comboBoxVuelos, comboBoxCategorias);
		PresentacionUtils.limpiarComboBoxes(comboAerolineas,comboBoxVuelos, comboBoxCategorias);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		comboBoxCategorias.setModel(model);
		//comboRutas.removeAllItems();
		comboBoxVuelos.removeAllItems();
	}
	
	
	public void listarInfoRutaVueloPaquete(String aerolinea, String ruta) {
		cargarAerolineas();
		comboAerolineas.setSelectedItem(aerolinea);
		cargarRutas(aerolinea);
		comboRutas.setSelectedItem(ruta);
		cargarVuelos();
		this.setVisible(true);
	}
	
	public void prepararFrame() {
		conVue = Principal.conVueInternalFrame;
		comboAerolineas.setSelectedItem("");
		cargarAerolineas();
	}
}
