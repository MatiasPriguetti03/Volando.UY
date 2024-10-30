package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.PresentacionUtils;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import excepciones.EsConjuntoVacioException;
import logica.Categoria;
import logica.Ciudad;
import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import logica.ManejadorVuelo;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AltaRutaVuelo extends JInternalFrame{
	private JTextField textFieldNombre;
	private JTextField textFieldCostoExtra;
	private JTextField textFieldCostoTur;
	private JTextField textFieldCostoEje;
	private JTextField textFieldHora;
	private JTextArea textAreaDescripcion;
	private JTextArea textAreaDescripcionCorta;
    private JDateChooser cFechaAlta;
	private JComboBox<String> comboAerolineas;
	private JComboBox<String> comboBoxOrigen;
	private JComboBox<String> comboBoxDestino;
	private JComboBox<String> comboBoxCategorias;
	private JButton btnBorrarCategoria;
	private DefaultListModel<String> model;
	private JList<String> listadoCategorias;
	private Map<String, Categoria> categorias;
	private String ciudadOrigen;
	private String paisOrigen;
	private String ciudadDestino;
	private String paisDestino;
	private JTextField textFieldMinutos;
	
	public AltaRutaVuelo() {
		
		setTitle("Crear Ruta de Vuelo");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
        setBounds(10, 10, 750, 400);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 79, 102, 79, 51, 51, 0};
		gridBagLayout.rowHeights = new int[]{0, 22, 0, 2, 0, 21, 20, 20, 22, 20, 0, 23, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		categorias = new HashMap<String, Categoria>();
		model = new DefaultListModel<>();
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);

		
		JLabel lblseleccionarAerolineas = new JLabel("Seleccionar Aerolinea:");
		GridBagConstraints gbc_lblseleccionarAerolineas = new GridBagConstraints();
		gbc_lblseleccionarAerolineas.anchor = GridBagConstraints.EAST;
		gbc_lblseleccionarAerolineas.insets = new Insets(0, 0, 5, 5);
		gbc_lblseleccionarAerolineas.gridwidth = 2;
		gbc_lblseleccionarAerolineas.gridx = 1;
		gbc_lblseleccionarAerolineas.gridy = 1;
		getContentPane().add(lblseleccionarAerolineas, gbc_lblseleccionarAerolineas);
		
		comboAerolineas = new JComboBox<String>();
		GridBagConstraints gbc_comboAerolineas = new GridBagConstraints();
		gbc_comboAerolineas.anchor = GridBagConstraints.NORTH;
		gbc_comboAerolineas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAerolineas.insets = new Insets(0, 0, 5, 5);
		gbc_comboAerolineas.gridwidth = 2;
		gbc_comboAerolineas.gridx = 3;
		gbc_comboAerolineas.gridy = 1;
		getContentPane().add(comboAerolineas, gbc_comboAerolineas);
		
		JPanel panel_2_1 = new JPanel();
		GridBagConstraints gbc_panel_2_1 = new GridBagConstraints();
		gbc_panel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2_1.fill = GridBagConstraints.BOTH;
		gbc_panel_2_1.gridx = 3;
		gbc_panel_2_1.gridy = 2;
		getContentPane().add(panel_2_1, gbc_panel_2_1);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.NORTH;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridwidth = 5;
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 3;
		getContentPane().add(separator, gbc_separator);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 4;
		getContentPane().add(panel_2, gbc_panel_2);
		
		JLabel lblDescripcionCorta = new JLabel("Descripcion Corta:");
		GridBagConstraints gbc_lblDescripcionCorta = new GridBagConstraints();
		gbc_lblDescripcionCorta.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcionCorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionCorta.gridx = 3;
		gbc_lblDescripcionCorta.gridy = 4;
		getContentPane().add(lblDescripcionCorta, gbc_lblDescripcionCorta);
		
		textAreaDescripcionCorta = new JTextArea();
		textAreaDescripcionCorta.setWrapStyleWord(true);
		textAreaDescripcionCorta.setLineWrap(true);
		GridBagConstraints gbc_textAreaDescripcion_1 = new GridBagConstraints();
		gbc_textAreaDescripcion_1.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaDescripcion_1.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion_1.gridx = 4;
		gbc_textAreaDescripcion_1.gridy = 4;
		getContentPane().add(textAreaDescripcionCorta, gbc_textAreaDescripcion_1);
		textAreaDescripcionCorta.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 5;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 5;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lbldescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lbldescripcion = new GridBagConstraints();
		gbc_lbldescripcion.anchor = GridBagConstraints.EAST;
		gbc_lbldescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lbldescripcion.gridx = 3;
		gbc_lbldescripcion.gridy = 5;
		getContentPane().add(lbldescripcion, gbc_lbldescripcion);
		
		textAreaDescripcion = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setWrapStyleWord(true);
		gbc_textArea.gridwidth = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 4;
		gbc_textArea.gridy = 5;
		
		JScrollPane scrollPane = new JScrollPane(textAreaDescripcion);
		getContentPane().add(scrollPane, gbc_textArea);
		
		JLabel lblcostoExtra = new JLabel("Costo Equipaje Extra:");
		GridBagConstraints gbc_lblcostoExtra = new GridBagConstraints();
		gbc_lblcostoExtra.anchor = GridBagConstraints.EAST;
		gbc_lblcostoExtra.insets = new Insets(0, 0, 5, 5);
		gbc_lblcostoExtra.gridx = 1;
		gbc_lblcostoExtra.gridy = 6;
		getContentPane().add(lblcostoExtra, gbc_lblcostoExtra);
		
		textFieldCostoExtra = new JTextField();
		GridBagConstraints gbc_textFieldCostoExtra = new GridBagConstraints();
		gbc_textFieldCostoExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCostoExtra.anchor = GridBagConstraints.NORTH;
		gbc_textFieldCostoExtra.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCostoExtra.gridx = 2;
		gbc_textFieldCostoExtra.gridy = 6;
		getContentPane().add(textFieldCostoExtra, gbc_textFieldCostoExtra);
		textFieldCostoExtra.setColumns(10);
		
		JLabel lblhora = new JLabel("Hora (HH/MM):");
		GridBagConstraints gbc_lblhora = new GridBagConstraints();
		gbc_lblhora.anchor = GridBagConstraints.EAST;
		gbc_lblhora.insets = new Insets(0, 0, 5, 5);
		gbc_lblhora.gridx = 3;
		gbc_lblhora.gridy = 6;
		getContentPane().add(lblhora, gbc_lblhora);
		
		textFieldHora = new JTextField();
		GridBagConstraints gbc_textFieldHora = new GridBagConstraints();
		gbc_textFieldHora.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHora.anchor = GridBagConstraints.NORTH;
		gbc_textFieldHora.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHora.gridx = 4;
		gbc_textFieldHora.gridy = 6;
		getContentPane().add(textFieldHora, gbc_textFieldHora);
		textFieldHora.setColumns(10);
		
		textFieldMinutos = new JTextField();
		GridBagConstraints gbc_textFieldMinutos = new GridBagConstraints();
		gbc_textFieldMinutos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMinutos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldMinutos.gridx = 5;
		gbc_textFieldMinutos.gridy = 6;
		getContentPane().add(textFieldMinutos, gbc_textFieldMinutos);
		textFieldMinutos.setColumns(10);
		
		JLabel lblcostoTur = new JLabel("Costo Turista:");
		GridBagConstraints gbc_lblcostoTur = new GridBagConstraints();
		gbc_lblcostoTur.anchor = GridBagConstraints.EAST;
		gbc_lblcostoTur.insets = new Insets(0, 0, 5, 5);
		gbc_lblcostoTur.gridx = 1;
		gbc_lblcostoTur.gridy = 7;
		getContentPane().add(lblcostoTur, gbc_lblcostoTur);
		
		textFieldCostoTur = new JTextField();
		GridBagConstraints gbc_textFieldCostoTur = new GridBagConstraints();
		gbc_textFieldCostoTur.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCostoTur.anchor = GridBagConstraints.NORTH;
		gbc_textFieldCostoTur.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCostoTur.gridx = 2;
		gbc_textFieldCostoTur.gridy = 7;
		getContentPane().add(textFieldCostoTur, gbc_textFieldCostoTur);
		textFieldCostoTur.setColumns(10);
		
		JLabel lblorigen = new JLabel("Ciudad Origen:");
		GridBagConstraints gbc_lblorigen = new GridBagConstraints();
		gbc_lblorigen.anchor = GridBagConstraints.EAST;
		gbc_lblorigen.insets = new Insets(0, 0, 5, 5);
		gbc_lblorigen.gridx = 3;
		gbc_lblorigen.gridy = 7;
		getContentPane().add(lblorigen, gbc_lblorigen);
		
		comboBoxOrigen = new JComboBox<String>();
		GridBagConstraints gbc_comboBoxOrigen = new GridBagConstraints();
		gbc_comboBoxOrigen.gridwidth = 2;
		gbc_comboBoxOrigen.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxOrigen.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxOrigen.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxOrigen.gridx = 4;
		gbc_comboBoxOrigen.gridy = 7;
		getContentPane().add(comboBoxOrigen, gbc_comboBoxOrigen);
		comboBoxOrigen.addActionListener(e -> setOrigen());
		
		JLabel lblcostoEje = new JLabel("Costo Ejecutivo:");
		GridBagConstraints gbc_lblcostoEje = new GridBagConstraints();
		gbc_lblcostoEje.anchor = GridBagConstraints.EAST;
		gbc_lblcostoEje.insets = new Insets(0, 0, 5, 5);
		gbc_lblcostoEje.gridx = 1;
		gbc_lblcostoEje.gridy = 8;
		getContentPane().add(lblcostoEje, gbc_lblcostoEje);
		
		textFieldCostoEje = new JTextField();
		GridBagConstraints gbc_textFieldCostoEje = new GridBagConstraints();
		gbc_textFieldCostoEje.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCostoEje.anchor = GridBagConstraints.NORTH;
		gbc_textFieldCostoEje.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCostoEje.gridx = 2;
		gbc_textFieldCostoEje.gridy = 8;
		getContentPane().add(textFieldCostoEje, gbc_textFieldCostoEje);
		textFieldCostoEje.setColumns(10);
		//comboBoxOrigen.addActionListener(new java.awt.event.ActionListener() {
			//public void actionPerformed(java.awt.event.ActionEvent evt) {
				//if(comboBoxOrigen.getSelectedItem() != null && !("".equals(comboBoxOrigen.getSelectedItem()))) {
					//setOrigen(comboBoxOrigen.getSelectedItem().toString());
				//}
			//}
		//});
		
		JLabel lbldestino = new JLabel("Ciudad Destino:");
		GridBagConstraints gbc_lbldestino = new GridBagConstraints();
		gbc_lbldestino.anchor = GridBagConstraints.EAST;
		gbc_lbldestino.insets = new Insets(0, 0, 5, 5);
		gbc_lbldestino.gridx = 3;
		gbc_lbldestino.gridy = 8;
		getContentPane().add(lbldestino, gbc_lbldestino);
		
		comboBoxDestino = new JComboBox<String>();
		GridBagConstraints gbc_comboBoxDestino = new GridBagConstraints();
		gbc_comboBoxDestino.gridwidth = 2;
		gbc_comboBoxDestino.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxDestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDestino.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDestino.gridx = 4;
		gbc_comboBoxDestino.gridy = 8;
		getContentPane().add(comboBoxDestino, gbc_comboBoxDestino);
		comboBoxDestino.addActionListener(e -> setDestino());
		//comboBoxDestino.addActionListener(new java.awt.event.ActionListener() {
			//public void actionPerformed(java.awt.event.ActionEvent evt) {
				//if(comboBoxDestino.getSelectedItem() != null && !("".equals(comboBoxDestino.getSelectedItem()))) {
					//setDestino(comboBoxDestino.getSelectedItem().toString());
				//}
			//}
		//});
		
		JLabel lblfechaAlta = new JLabel("Fecha Alta:");
		GridBagConstraints gbc_lblfechaAlta = new GridBagConstraints();
		gbc_lblfechaAlta.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblfechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblfechaAlta.gridx = 1;
		gbc_lblfechaAlta.gridy = 9;
		getContentPane().add(lblfechaAlta, gbc_lblfechaAlta);
		
		cFechaAlta = new JDateChooser();
		GridBagConstraints gbc_textFieldFechaAlta = new GridBagConstraints();
		gbc_textFieldFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFechaAlta.anchor = GridBagConstraints.NORTH;
		gbc_textFieldFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaAlta.gridx = 2;
		gbc_textFieldFechaAlta.gridy = 9;
		getContentPane().add(cFechaAlta, gbc_textFieldFechaAlta);
		
		JLabel lblcategorias = new JLabel("Categorias:");
		GridBagConstraints gbc_lblcategorias = new GridBagConstraints();
		gbc_lblcategorias.anchor = GridBagConstraints.EAST;
		gbc_lblcategorias.insets = new Insets(0, 0, 5, 5);
		gbc_lblcategorias.gridx = 3;
		gbc_lblcategorias.gridy = 9;
		getContentPane().add(lblcategorias, gbc_lblcategorias);
		
		comboBoxCategorias = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 9;
		getContentPane().add(comboBoxCategorias, gbc_comboBox);
		comboBoxCategorias.addActionListener(e -> agregarCategoria());
		
		btnBorrarCategoria = new JButton("Deseleccionar");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 10;
		getContentPane().add(btnBorrarCategoria, gbc_btnNewButton);
		btnBorrarCategoria.setVisible(false);
		btnBorrarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeElement(listadoCategorias.getSelectedValue());
				categorias.remove(listadoCategorias.getSelectedValue());
				listadoCategorias.setModel(model);
				btnBorrarCategoria.setVisible(false);
			}
		});

		listadoCategorias = new JList<String>();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 5;
		gbc_list.gridy = 9;
		getContentPane().add(listadoCategorias, gbc_list);
		listadoCategorias.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	if (!btnBorrarCategoria.isVisible() && listadoCategorias.getSelectedValue() != null)
                		btnBorrarCategoria.setVisible(true);
                }
            }
        });
		listadoCategorias.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 6;
		gbc_panel_3.gridy = 10;
		getContentPane().add(panel_3, gbc_panel_3);
		
		JButton btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.anchor = GridBagConstraints.SOUTH;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridwidth = 2;
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 11;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelar.gridwidth = 2;
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 11;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                setVisible(false);
	            }
	    });
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 6;
		gbc_panel_1.gridy = 12;
		getContentPane().add(panel_1, gbc_panel_1);
		btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    crearRutaVuelo();
                }
            }
        });
		
		addInternalFrameListener(new InternalFrameAdapter() {
		    @Override
		    public void internalFrameClosing(InternalFrameEvent e) {
		        limpiarCampos();
		        setVisible(false);
		    }
		});
	}
	
	public void rellenarListaDeAerolineas() {
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
        } catch(EsConjuntoVacioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	public void rellenarListaDeCiudades() {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		
		String[] ciudades = iVuelo.listarCiudades();
        String[] nombresCiudades = new String[ciudades.length + 1];
        nombresCiudades[0] = "";
        
        int i = 1;
		for(String ciudad : ciudades) {
			nombresCiudades[i] = ciudad;
        	i++;
		}
		
		Arrays.sort(nombresCiudades);
		
		DefaultComboBoxModel<String> model1;
		DefaultComboBoxModel<String> model2;
        model1 = new DefaultComboBoxModel<String>(nombresCiudades);
        model2 = new DefaultComboBoxModel<String>(nombresCiudades);
        comboBoxOrigen.setModel(model1);
        comboBoxDestino.setModel(model2);
	}
	
	public void rellenarListaDeCategorias() {
        Fabrica fabrica = Fabrica.getInstance();
        IVuelo iVuelo = fabrica.getIVuelo();
        
        String[] categorias = iVuelo.listarCategorias();
        String[] nombresCategorias = new String[categorias.length + 1];
        nombresCategorias[0] = "";
        
        int i = 1;
        for (String categoria : categorias) {
        	nombresCategorias[i] = categoria;
        	i++;
        }

		Arrays.sort(nombresCategorias);
		
		DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(nombresCategorias);
        comboBoxCategorias.setModel(model);
    }
	
	private void agregarCategoria() {
		Fabrica fabrica = Fabrica.getInstance();
		ManejadorVuelo MV = ManejadorVuelo.getInstancia();
		
		if(comboBoxCategorias.getSelectedItem() != null && !("".equals(comboBoxCategorias.getSelectedItem()))) {
			String categoria = (String) comboBoxCategorias.getSelectedItem();
			categorias.put(categoria, MV.obtenerCategoria(categoria));
			if(!model.contains(categoria)) {
				model.addElement(categoria);
				listadoCategorias.setModel(model);
			}
		}
	}
	
	private void setOrigen() {
		Fabrica fabrica = Fabrica.getInstance();
		ManejadorVuelo manejadorVuelo = ManejadorVuelo.getInstancia();
		
		if(comboBoxOrigen.getSelectedItem() != null && !("".equals(comboBoxOrigen.getSelectedItem()))) {
			String[] arrOrigen  = ((String) comboBoxOrigen.getSelectedItem()).split(", ", 2);
			ciudadOrigen = arrOrigen[0];
			paisOrigen = arrOrigen[1];
		}
	}
	
	private void setDestino() {
		Fabrica fabrica = Fabrica.getInstance();
		ManejadorVuelo manejadorVuelo = ManejadorVuelo.getInstancia();
		
		if(comboBoxDestino.getSelectedItem() != null && !("".equals(comboBoxDestino.getSelectedItem()))) {
			String[] arrDestino  = ((String) comboBoxDestino.getSelectedItem()).split(", ", 2);
			ciudadDestino = arrDestino[0];
			paisDestino = arrDestino[1];
		}
	}
	
	private void limpiarCampos() {
		if (cFechaAlta != null) {
            cFechaAlta.setDate(null);
        }
		PresentacionUtils.limpiarCamposTexto(textFieldNombre, textAreaDescripcionCorta,textAreaDescripcion, textFieldCostoExtra, textFieldCostoTur, textFieldCostoEje, textFieldHora, textFieldMinutos);
		PresentacionUtils.limpiarComboBoxes(comboAerolineas, comboBoxOrigen, comboBoxDestino, comboBoxCategorias);
		categorias = new HashMap<String, Categoria>();
		model = new DefaultListModel<>();
		listadoCategorias.setModel(model);
		btnBorrarCategoria.setVisible(false);
	}
	
	private boolean validarCampos() {
		Fabrica fabrica = Fabrica.getInstance();
		ManejadorVuelo manejadorVuelo = ManejadorVuelo.getInstancia();
		
		String aerolina = (String) comboAerolineas.getSelectedItem();
        if (aerolina == null || aerolina.isEmpty()) {
            PresentacionUtils.mostrarError(this, "Debe seleccionar una Aerolinea");
            return false;
        }
        
        if (!PresentacionUtils.validarCamposRequeridos(this, "Todos los campos son obligatorios", 
        		textFieldNombre, textAreaDescripcion, textFieldCostoExtra, textFieldCostoTur, textFieldCostoEje, textFieldHora)) {
            return false;
        }
        
        if (!PresentacionUtils.validarSeleccionComboBox(this, comboBoxOrigen, "Debe seleccionar Ciudad de Origen")) {
            return false;
        }
        
        if (!PresentacionUtils.validarSeleccionComboBox(this, comboBoxDestino, "Debe seleccionar Ciudad de Destino")) {
            return false;
        }
        
        if (cFechaAlta.getDate() == null) {
            PresentacionUtils.mostrarError(this, "Debe seleccionar una fecha de Alta");
            return false;
        }
        
        try {
        	if (Integer.parseInt(textFieldHora.getText()) < 0 || Integer.parseInt(textFieldHora.getText()) > 23) {
            	PresentacionUtils.mostrarError(this, "La hora ingresada debe ser positiva y menor a 24");
            	return false;
        	}
        	
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "La hora ingresada contiene caracteres no válidos.\nPor favor, ingrese solo números");
        	return false;
        }
        
        try {
        	if (textFieldMinutos != null && (Integer.parseInt(textFieldMinutos.getText()) < 0 || Integer.parseInt(textFieldMinutos.getText()) > 59)) {
            	PresentacionUtils.mostrarError(this, "Los minutos ingresados debe ser positivos y menores a 60");
            	return false;
        	}
        	
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "Los minutos ingresados contienen caracteres no válidos.\nPor favor, ingrese solo números");
        	return false;
        }
        
        try {
        	if (Float.parseFloat(textFieldCostoExtra.getText()) < 0) {
            	PresentacionUtils.mostrarError(this, "El costo ingresado contiene caracteres no válidos.\nPor favor, ingrese solo números");
            	return false;
        	}
        	
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "El costo ingresado contiene caracteres no válidos.\nPor favor, ingrese solo números");
        	return false;
        }
        
        try {
        	if (Float.parseFloat(textFieldCostoEje.getText()) < 0) {
            	PresentacionUtils.mostrarError(this, "El costo ingresado contiene caracteres no válidos.\nPor favor, ingrese solo números");
            	return false;
        	}
        	
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "El costo ingresado contiene caracteres no válidos.\nPor favor, ingrese solo números");
        	return false;
        }
        
        try {
        	if (Float.parseFloat(textFieldCostoTur.getText()) < 0) {
            	PresentacionUtils.mostrarError(this, "El costo ingresado contiene caracteres no válidos.\nPor favor, ingrese solo números");
            	return false;
        	}
        	
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "El costo ingresado contiene caracteres no válidos.\nPor favor, ingrese solo números");
        	return false;
        }
        
        if (manejadorVuelo.existeRutaVuelo(textFieldNombre.getText())) {
            PresentacionUtils.mostrarError(this, "Ya existe una Ruta deVuelo con ese nombre. \nPor favor, ingrese otro nombre");
            return false;
        }
        
        if (comboBoxOrigen.getSelectedItem() == comboBoxDestino.getSelectedItem() 
        		&& comboBoxOrigen != null && comboBoxOrigen.getSelectedItem() != "") {
            PresentacionUtils.mostrarError(this, "El Origen de la Ruta de Vuelo no puede ser el mismo que el Destino");
            return false;
        }
        
		return true;
	}
	
	private void crearRutaVuelo() {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		
		String aerolinea = (String) comboAerolineas.getSelectedItem();
				
		String nombre = textFieldNombre.getText();
		String descripcion = textAreaDescripcion.getText();
		String descripcionCorta = textAreaDescripcionCorta.getText();
		String strHora = textFieldHora.getText() + ":" + textFieldMinutos.getText();
		LocalTime hora = PresentacionUtils.parseTime(strHora);
		float costoExtra = Float.parseFloat(textFieldCostoExtra.getText());
		float costoEje = Float.parseFloat(textFieldCostoEje.getText());
		float costoTur = Float.parseFloat(textFieldCostoTur.getText());
		
		LocalDate fechaAlta = cFechaAlta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		
		try {
			iVuelo.ingresarDatosRutaVuelo(aerolinea, nombre, null, descripcion, descripcionCorta, hora, fechaAlta, costoExtra, costoEje, costoTur, ciudadOrigen, paisOrigen, ciudadDestino, paisDestino, categorias);
			PresentacionUtils.mostrarExito(this, "Ruta de Vuelo ingresada con éxito");
        	limpiarCampos();
        	rellenarListaDeAerolineas();
        	rellenarListaDeCiudades();
        	rellenarListaDeCategorias();
        	
		} catch (Exception e) {
        	PresentacionUtils.mostrarError(this, e.getMessage());
        }
	}
	
	public void prepararFrame() {
		rellenarListaDeAerolineas();
    	rellenarListaDeCiudades();
    	rellenarListaDeCategorias();
	}
}
