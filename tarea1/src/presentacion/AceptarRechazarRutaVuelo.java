package presentacion;

import java.util.Arrays;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import excepciones.EsConjuntoVacioException;

import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import utils.PresentacionUtils;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AceptarRechazarRutaVuelo extends JInternalFrame {
	private JComboBox<String> comboAerolineas;
	private JComboBox<String> comboRutas;
//	private AceptarRechazarRutaVuelo aceptarRechazarRutaVuelo;
	
	
	public AceptarRechazarRutaVuelo() {
		
		setTitle("Aceptar/Rechazar Ruta de Vuelo");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(0, 100, 600, 260);
        
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 106, 168, 79, 88, 0};
		gridBagLayout.rowHeights = new int[]{10, 22, 33, 21, 20, 20, 23, 20, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		addInternalFrameListener(new InternalFrameAdapter() {
		    @Override
		    public void internalFrameClosing(InternalFrameEvent event) {
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
				} else {
					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
					comboRutas.setModel(model);
					
				}
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
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[]{""});
		comboRutas.setModel(model);

		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.gridwidth = 4;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 1;
		gbc_separator_1.gridy = 4;
		getContentPane().add(separator_1, gbc_separator_1);
		
		JButton btnNewButton = new JButton("Confirmar Ruta");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 5;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(validarCampos()) {
					
                    String ruta = comboRutas.getSelectedItem().toString();
					aceptarRuta(ruta);
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Rechazar Ruta");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 5;
		getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(validarCampos()) {
					
                    String ruta = comboRutas.getSelectedItem().toString();
	                rechazarRuta(ruta);
	                    
				}
			}
		});
		
	}
	
	
	public void cargarAerolineas() {
		Fabrica fabrica = Fabrica.getInstance();
        IUsuario iUsuario = fabrica.getIUsuario();
        
        try {
	        String[] aerolineas = iUsuario.listarAerolineas();
	        String[] nombresAerolineas = new String[aerolineas.length + 1];
	        nombresAerolineas[0] = "";
	        
	        
	        int contador = 1;
	        for (String aerolinea : aerolineas) {
	        	nombresAerolineas[contador] = aerolinea;
	        	contador++;
	        }
	
			Arrays.sort(nombresAerolineas);
			
			DefaultComboBoxModel<String> model;
	        model = new DefaultComboBoxModel<String>(nombresAerolineas);
	        comboAerolineas.setModel(model);
	        
        } catch(EsConjuntoVacioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
        }
	}

	private void cargarRutas(String aerolinea) {
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		String[] rutas = iUsuario.obtenerRutasIngresadasAerolinea(aerolinea);
		String[] nombresRutas = new String[rutas.length + 1];
		nombresRutas[0] = "";
		
		int contador = 1;
		for (String ruta : rutas) {
			nombresRutas[contador] = ruta;
			contador++;
		}
		
		Arrays.sort(nombresRutas);
		
		
		DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(nombresRutas);
        comboRutas.setModel(model);
		
	}
	
	
	private void limpiarCampos() {
		//PresentacionUtils.limpiarComboBoxes(comboAerolineas,comboRutas,comboBoxVuelos, comboBoxCategorias);
		PresentacionUtils.limpiarComboBoxes(comboAerolineas);
		comboRutas.removeAll();
		comboRutas.setSelectedItem("");
		
	}
	
	
	public void rechazarRuta(String ruta) {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();

		iVuelo.rechazarRutaVuelo(ruta);
		PresentacionUtils.mostrarExito(this, "Ruta de Vuelo Rechazada con éxito");
    	limpiarCampos();
    	cargarAerolineas();
		
	}
	
	
	public void aceptarRuta(String ruta) {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();

		iVuelo.aceptarRutaVuelo(ruta);
		PresentacionUtils.mostrarExito(this, "Ruta de Vuelo Aceptada con éxito");
		limpiarCampos();
		cargarAerolineas();	
	}
	
	private boolean validarCampos() {
		
		String aerolina = (String) comboAerolineas.getSelectedItem();
        if (aerolina == null || aerolina.isEmpty()) {
            PresentacionUtils.mostrarError(this, "Debe seleccionar una Aerolinea");
            return false;
        }
        
        String ruta = (String) comboRutas.getSelectedItem();
		if (ruta == null || ruta.isEmpty()) {
			PresentacionUtils.mostrarError(this, "Debe seleccionar una Ruta");
			return false;
		}

		return true;
	}
	
	public void prepararFrame() {
//		aceptarRechazarRutaVuelo = Principal.aceRechRutVueInternalFrame;
		comboAerolineas.setSelectedItem("");
		cargarAerolineas();
	}
}
