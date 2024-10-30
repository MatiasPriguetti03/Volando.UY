package presentacion;

import javax.swing.JInternalFrame;
import utils.PresentacionUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import excepciones.YaRegistradoException;
import excepciones.EsConjuntoVacioException;
import excepciones.NoExisteException;
import excepciones.OperacionInvalidaException;

import logica.Fabrica;
import logica.IUsuario;
import logica.IVuelo;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTInfoRutaVuelo;
import logica.datatypes.DTVuelo;
import logica.datatypes.DTPasajero;
import logica.enums.EnumAsiento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Font;

@SuppressWarnings("serial")
public class ReservarVuelo extends JInternalFrame {
	private JScrollPane scrollPaneAcomps;
	private JList<String> listaAcomps;
	private DefaultListModel<String> model;
	private JDateChooser dateChooseFechaAlta;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCantEquipajeExtra;
	private JTextField textCantPasajeros;
	private JTextField textDuracion;
	private JTextField textOrigen;
	private JTextField textDestino;
	private JTextField textFechaVuelo;
	private JComboBox<String> combAerolineas;
	private JComboBox<String> combVuelos;
	private JComboBox<String> combRutasVuelo;
	private JComboBox<String> combClientes;
	private JComboBox<String> combPaquete;
	private JComboBox<String> combTipoAsiento;
	private JButton btnBorrarAcomp;
	
	public ReservarVuelo() {
		
		setTitle("Reserva de Vuelo");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 860, 510);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		getContentPane().setLayout(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 166, 114, 71, 108, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 22, 22, 22, 23, 35, 14, 31, 20, 30, 0, 20, 30, 30, 0, 0, 15, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblAerolinea = new JLabel("Seleccionar Aerolínea:");
		GridBagConstraints gbc_lblAerolinea = new GridBagConstraints();
		gbc_lblAerolinea.anchor = GridBagConstraints.EAST;
		gbc_lblAerolinea.insets = new Insets(0, 0, 5, 5);
		gbc_lblAerolinea.gridx = 1;
		gbc_lblAerolinea.gridy = 1;
		getContentPane().add(lblAerolinea, gbc_lblAerolinea);
		
		combAerolineas = new JComboBox<String>();
		combAerolineas.setSize(50, 22);
		combAerolineas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (combAerolineas.getSelectedItem() != "") {
					cargarCombBoxRutasVuelo();
				} else {
					combRutasVuelo.removeAllItems();
					combVuelos.removeAllItems();
				}
			}
		});
		GridBagConstraints gbc_combAerolineas = new GridBagConstraints();
		gbc_combAerolineas.anchor = GridBagConstraints.NORTH;
		gbc_combAerolineas.fill = GridBagConstraints.HORIZONTAL;
		gbc_combAerolineas.insets = new Insets(0, 0, 5, 5);
		gbc_combAerolineas.gridx = 2;
		gbc_combAerolineas.gridy = 1;
		getContentPane().add(combAerolineas, gbc_combAerolineas);
		
		JLabel lblRutasVuelo = new JLabel("Seleccione una Ruta de Vuelo:");
		GridBagConstraints gbc_lblRutasVuelo = new GridBagConstraints();
		gbc_lblRutasVuelo.anchor = GridBagConstraints.EAST;
		gbc_lblRutasVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblRutasVuelo.gridx = 1;
		gbc_lblRutasVuelo.gridy = 2;
		getContentPane().add(lblRutasVuelo, gbc_lblRutasVuelo);
		
		combRutasVuelo = new JComboBox<String>();
		combRutasVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (combAerolineas.getSelectedItem() != "" && combAerolineas.getSelectedItem() != null 
						&& combRutasVuelo.getSelectedItem() != "") {
					cargarCombBoxVuelos();
					if (combClientes.getSelectedItem() != "" && combClientes.getSelectedItem() != null) {
						cargarCombBoxPaquetes();
					}
				} 
				else 
					combVuelos.removeAllItems();
					combPaquete.removeAllItems();			
			}
		});
		GridBagConstraints gbc_combRutasVuelo = new GridBagConstraints();
		gbc_combRutasVuelo.anchor = GridBagConstraints.NORTH;
		gbc_combRutasVuelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_combRutasVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_combRutasVuelo.gridx = 2;
		gbc_combRutasVuelo.gridy = 2;
		getContentPane().add(combRutasVuelo, gbc_combRutasVuelo);
		
		JLabel lblCliente = new JLabel("Seleccionar Cliente:");
		GridBagConstraints gbc_lblCliente = new GridBagConstraints();
		gbc_lblCliente.anchor = GridBagConstraints.EAST;
		gbc_lblCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblCliente.gridx = 3;
		gbc_lblCliente.gridy = 2;
		getContentPane().add(lblCliente, gbc_lblCliente);
		
		combClientes = new JComboBox<String>();
		combClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (combClientes.getSelectedItem() != "" && combClientes.getSelectedItem() != null) {
					if (combRutasVuelo.getSelectedItem() != "" && combRutasVuelo.getSelectedItem() != null) {
						cargarCombBoxPaquetes();
					}
				} else {
					combPaquete.removeAllItems();
				}
			}
		});
		GridBagConstraints gbc_combClientes = new GridBagConstraints();
		gbc_combClientes.anchor = GridBagConstraints.NORTH;
		gbc_combClientes.fill = GridBagConstraints.HORIZONTAL;
		gbc_combClientes.insets = new Insets(0, 0, 5, 5);
		gbc_combClientes.gridx = 4;
		gbc_combClientes.gridy = 2;
		getContentPane().add(combClientes, gbc_combClientes);
		
		JLabel lblVuelos = new JLabel("Seleccione un Vuelo:");
		GridBagConstraints gbc_lblVuelos = new GridBagConstraints();
		gbc_lblVuelos.anchor = GridBagConstraints.EAST;
		gbc_lblVuelos.insets = new Insets(0, 0, 5, 5);
		gbc_lblVuelos.gridx = 1;
		gbc_lblVuelos.gridy = 3;
		getContentPane().add(lblVuelos, gbc_lblVuelos);
		
		combVuelos = new JComboBox<String>();
		combVuelos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (combRutasVuelo.getSelectedItem() != "" && combRutasVuelo.getSelectedItem() != null
						&& combVuelos.getSelectedItem() != "") {
					cargarDatosVuelo();
				} 	
			}
		});
		GridBagConstraints gbc_combVuelos = new GridBagConstraints();
		gbc_combVuelos.anchor = GridBagConstraints.NORTH;
		gbc_combVuelos.fill = GridBagConstraints.HORIZONTAL;
		gbc_combVuelos.insets = new Insets(0, 0, 5, 5);
		gbc_combVuelos.gridx = 2;
		gbc_combVuelos.gridy = 3;
		getContentPane().add(combVuelos, gbc_combVuelos);
		
		JLabel lblSeleccionarPaquete = new JLabel("Seleccionar Paquete:");
		GridBagConstraints gbc_lblSeleccionarPaquete = new GridBagConstraints();
		gbc_lblSeleccionarPaquete.anchor = GridBagConstraints.EAST;
		gbc_lblSeleccionarPaquete.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarPaquete.gridx = 3;
		gbc_lblSeleccionarPaquete.gridy = 3;
		getContentPane().add(lblSeleccionarPaquete, gbc_lblSeleccionarPaquete);
		
		combPaquete = new JComboBox<String>();
		GridBagConstraints gbc_combPaquete = new GridBagConstraints();
		gbc_combPaquete.insets = new Insets(0, 0, 5, 5);
		gbc_combPaquete.fill = GridBagConstraints.HORIZONTAL;
		gbc_combPaquete.gridx = 4;
		gbc_combPaquete.gridy = 3;
		getContentPane().add(combPaquete, gbc_combPaquete);
		
		JLabel lblopcional = new JLabel("(Opcional)");
		lblopcional.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gbc_lblopcional = new GridBagConstraints();
		gbc_lblopcional.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblopcional.insets = new Insets(0, 0, 5, 5);
		gbc_lblopcional.gridx = 3;
		gbc_lblopcional.gridy = 4;
		getContentPane().add(lblopcional, gbc_lblopcional);
		
		JLabel lblTipoAsiento = new JLabel("Seleccione el tipo de Asiento:");
		GridBagConstraints gbc_lblTipoAsiento = new GridBagConstraints();
		gbc_lblTipoAsiento.anchor = GridBagConstraints.EAST;
		gbc_lblTipoAsiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoAsiento.gridx = 1;
		gbc_lblTipoAsiento.gridy = 6;
		getContentPane().add(lblTipoAsiento, gbc_lblTipoAsiento);
		
		combTipoAsiento = new JComboBox<String>();
		GridBagConstraints gbc_combCantPasajeros = new GridBagConstraints();
		gbc_combCantPasajeros.insets = new Insets(0, 0, 5, 5);
		gbc_combCantPasajeros.fill = GridBagConstraints.HORIZONTAL;
		gbc_combCantPasajeros.gridx = 2;
		gbc_combCantPasajeros.gridy = 6;
		getContentPane().add(combTipoAsiento, gbc_combCantPasajeros);
		
		JLabel lblEquipajeExtra = new JLabel("Cantidad de equipaje extra: ");
		GridBagConstraints gbc_lblEquipajeExtra = new GridBagConstraints();
		gbc_lblEquipajeExtra.anchor = GridBagConstraints.EAST;
		gbc_lblEquipajeExtra.insets = new Insets(0, 0, 5, 5);
		gbc_lblEquipajeExtra.gridx = 3;
		gbc_lblEquipajeExtra.gridy = 6;
		getContentPane().add(lblEquipajeExtra, gbc_lblEquipajeExtra);
		
		textCantEquipajeExtra = new JTextField();
		GridBagConstraints gbc_textCantEquipajeExtra = new GridBagConstraints();
		gbc_textCantEquipajeExtra.insets = new Insets(0, 0, 5, 5);
		gbc_textCantEquipajeExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCantEquipajeExtra.gridx = 4;
		gbc_textCantEquipajeExtra.gridy = 6;
		getContentPane().add(textCantEquipajeExtra, gbc_textCantEquipajeExtra);
		textCantEquipajeExtra.setColumns(10);
		
		JLabel lblCantPasajes = new JLabel("Seleccione la cantidad de Pasajes:");
		GridBagConstraints gbc_lblCantPasajes = new GridBagConstraints();
		gbc_lblCantPasajes.anchor = GridBagConstraints.EAST;
		gbc_lblCantPasajes.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantPasajes.gridx = 1;
		gbc_lblCantPasajes.gridy = 7;
		getContentPane().add(lblCantPasajes, gbc_lblCantPasajes);
		
		textCantPasajeros = new JTextField();
		GridBagConstraints gbc_textCantPasajeros = new GridBagConstraints();
		gbc_textCantPasajeros.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCantPasajeros.insets = new Insets(0, 0, 5, 5);
		gbc_textCantPasajeros.gridx = 2;
		gbc_textCantPasajeros.gridy = 7;
		getContentPane().add(textCantPasajeros, gbc_textCantPasajeros);
		textCantPasajeros.setColumns(10);
				
		JLabel lblfechaAlta = new JLabel("Seleccionar la fecha de Alta:");
		GridBagConstraints gbc_lblfechaAlta = new GridBagConstraints();
		gbc_lblfechaAlta.anchor = GridBagConstraints.EAST;
		gbc_lblfechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblfechaAlta.gridx = 3;
		gbc_lblfechaAlta.gridy = 7;
		getContentPane().add(lblfechaAlta, gbc_lblfechaAlta);
		
		dateChooseFechaAlta = new JDateChooser();
		GridBagConstraints gbc_dateChooseFechaAlta = new GridBagConstraints();
		gbc_dateChooseFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooseFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooseFechaAlta.gridx = 4;
		gbc_dateChooseFechaAlta.gridy = 7;
		getContentPane().add(dateChooseFechaAlta, gbc_dateChooseFechaAlta);

		JLabel lblAcompaniantes = new JLabel("Acompañantes:");
		lblAcompaniantes.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblAcompaniantes = new GridBagConstraints();
		gbc_lblAcompaniantes.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcompaniantes.gridwidth = 4;
		gbc_lblAcompaniantes.gridx = 1;
		gbc_lblAcompaniantes.gridy = 9;
		getContentPane().add(lblAcompaniantes, gbc_lblAcompaniantes);
		
		btnBorrarAcomp = new JButton("Borrar");
		btnBorrarAcomp.setVisible(false);
		GridBagConstraints gbc_btnBorrarAcomp = new GridBagConstraints();
		gbc_btnBorrarAcomp.insets = new Insets(0, 0, 5, 5);
		gbc_btnBorrarAcomp.gridx = 3;
		gbc_btnBorrarAcomp.gridy = 11;
		getContentPane().add(btnBorrarAcomp, gbc_btnBorrarAcomp);
		btnBorrarAcomp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeElement(listaAcomps.getSelectedValue());
				listaAcomps.setModel(model);
				scrollPaneAcomps.revalidate();
				btnBorrarAcomp.setVisible(false);
			}
		});
		
		model = new DefaultListModel<>();
		listaAcomps = new JList<>();
		scrollPaneAcomps = new JScrollPane(listaAcomps);
		GridBagConstraints gbc_scrollPaneAcomps = new GridBagConstraints();
		gbc_scrollPaneAcomps.gridheight = 3;
		gbc_scrollPaneAcomps.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneAcomps.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneAcomps.gridx = 4;
		gbc_scrollPaneAcomps.gridy = 10;
		getContentPane().add(scrollPaneAcomps, gbc_scrollPaneAcomps);
		listaAcomps.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	if (!btnBorrarAcomp.isVisible() && listaAcomps.getSelectedValue() != null)
                		btnBorrarAcomp.setVisible(true);
                }
            }
        });
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 10;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		textNombre = new JTextField("");
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.gridx = 2;
		gbc_textNombre.gridy = 10;
		getContentPane().add(textNombre, gbc_textNombre);
		
		JButton btnAgregar = new JButton();
		btnAgregar.setText("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarAcomp();	
			}
	    });
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.NORTH;
		gbc_btnAgregar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregar.gridx = 3;
		gbc_btnAgregar.gridy = 10;
		getContentPane().add(btnAgregar, gbc_btnAgregar);
		
		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 1;
		gbc_lblApellido.gridy = 11;
		getContentPane().add(lblApellido, gbc_lblApellido);
		
		textApellido = new JTextField("");
		GridBagConstraints gbc_textApellido = new GridBagConstraints();
		gbc_textApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_textApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textApellido.gridx = 2;
		gbc_textApellido.gridy = 11;
		getContentPane().add(textApellido, gbc_textApellido);
		
		JLabel lblDatosVuelo = new JLabel("Datos del Vuelo:");
		lblDatosVuelo.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblDatosVuelo = new GridBagConstraints();
		gbc_lblDatosVuelo.gridwidth = 4;
		gbc_lblDatosVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatosVuelo.gridx = 1;
		gbc_lblDatosVuelo.gridy = 13;
		getContentPane().add(lblDatosVuelo, gbc_lblDatosVuelo);
		
		JLabel lblFechaVuelo = new JLabel("fecha del Vuelo:");
		GridBagConstraints gbc_lblFechaVuelo = new GridBagConstraints();
		gbc_lblFechaVuelo.anchor = GridBagConstraints.EAST;
		gbc_lblFechaVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaVuelo.gridx = 1;
		gbc_lblFechaVuelo.gridy = 14;
		getContentPane().add(lblFechaVuelo, gbc_lblFechaVuelo);
		
		textFechaVuelo = new JTextField("");
		textFechaVuelo.setEditable(false);
		GridBagConstraints gbc_textFechaVuelo = new GridBagConstraints();
		gbc_textFechaVuelo.anchor = GridBagConstraints.NORTH;
		gbc_textFechaVuelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFechaVuelo.insets = new Insets(0, 0, 5, 5);
		gbc_textFechaVuelo.gridx = 2;
		gbc_textFechaVuelo.gridy = 14;
		getContentPane().add(textFechaVuelo, gbc_textFechaVuelo);
		
		JLabel lblDuracion = new JLabel("horas de Vuelo:");
		GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
		gbc_lblDuracion.anchor = GridBagConstraints.EAST;
		gbc_lblDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracion.gridx = 3;
		gbc_lblDuracion.gridy = 14;
		getContentPane().add(lblDuracion, gbc_lblDuracion);
		
		textDuracion = new JTextField("");
		textDuracion.setEditable(false);
		GridBagConstraints gbc_textDuracion = new GridBagConstraints();
		gbc_textDuracion.anchor = GridBagConstraints.NORTH;
		gbc_textDuracion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_textDuracion.gridx = 4;
		gbc_textDuracion.gridy = 14;
		getContentPane().add(textDuracion, gbc_textDuracion);
		
		JLabel lblOrigen = new JLabel("Origen:");
		GridBagConstraints gbc_lblOrigen = new GridBagConstraints();
		gbc_lblOrigen.anchor = GridBagConstraints.EAST;
		gbc_lblOrigen.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrigen.gridx = 1;
		gbc_lblOrigen.gridy = 15;
		getContentPane().add(lblOrigen, gbc_lblOrigen);
		
		textOrigen = new JTextField("");
		textOrigen.setEditable(false);
		GridBagConstraints gbc_textOrigen = new GridBagConstraints();
		gbc_textOrigen.anchor = GridBagConstraints.NORTH;
		gbc_textOrigen.fill = GridBagConstraints.HORIZONTAL;
		gbc_textOrigen.insets = new Insets(0, 0, 5, 5);
		gbc_textOrigen.gridx = 2;
		gbc_textOrigen.gridy = 15;
		getContentPane().add(textOrigen, gbc_textOrigen);
		
		JLabel lblDestino = new JLabel("Destino:");
		GridBagConstraints gbc_lblDestino = new GridBagConstraints();
		gbc_lblDestino.anchor = GridBagConstraints.EAST;
		gbc_lblDestino.insets = new Insets(0, 0, 5, 5);
		gbc_lblDestino.gridx = 3;
		gbc_lblDestino.gridy = 15;
		getContentPane().add(lblDestino, gbc_lblDestino);
		
		textDestino = new JTextField("");
		textDestino.setEditable(false);
		GridBagConstraints gbc_textDestino = new GridBagConstraints();
		gbc_textDestino.anchor = GridBagConstraints.NORTH;
		gbc_textDestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDestino.insets = new Insets(0, 0, 5, 5);
		gbc_textDestino.gridx = 4;
		gbc_textDestino.gridy = 15;
		getContentPane().add(textDestino, gbc_textDestino);
		
		JButton btnAceptar = new JButton();
		btnAceptar.setText("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if (verificaCampos()) {
                	confirmarReserva();
                }
	        }
	    });
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 17;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton();
		btnCancelar.setText("Cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 17;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                limpiarCampos();            
                combRutasVuelo.removeAllItems();
                combVuelos.removeAllItems();
                setVisible(false);
	        }
	    });
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				closeFrame();
			}
		});
	}
	
	public void cargarComboBoxAerolinea() {
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		try {
			PresentacionUtils.cargarComboBox(iUsuario.listarAerolineas(), combAerolineas);
		}
		catch(EsConjuntoVacioException e) {
			combAerolineas.removeAllItems();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void cargarComboBoxCliente() {
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		try {
			PresentacionUtils.cargarComboBox(iUsuario.listarClientes(), combClientes);
		}
		catch(EsConjuntoVacioException e) {
			combAerolineas.removeAllItems();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void cargarCombBoxRutasVuelo() {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		String aerolinea = (String) combAerolineas.getSelectedItem();
		try {
			Set<DTInfoRutaVuelo> rutas = iVuelo.listarRutasVuelosAerolinea(aerolinea);
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
			combVuelos.removeAllItems();
		}
		catch(EsConjuntoVacioException e) {
			combRutasVuelo.removeAllItems();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void cargarCombBoxVuelos() {
		if(combRutasVuelo.getSelectedItem() != null && combRutasVuelo.getSelectedItem() != "") {
			Fabrica fabrica = Fabrica.getInstance();
			IVuelo iVuelo = fabrica.getIVuelo();
			try {
				String[] vuelos = iVuelo.listarVuelos((String) combRutasVuelo.getSelectedItem());
				PresentacionUtils.cargarComboBox(vuelos, combVuelos);
			}
			catch(EsConjuntoVacioException e) {
				combVuelos.removeAllItems();
				JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public void cargarCombBoxTipoAsiento() {
		String[] opciones = {"", "Turista", "Ejecutivo"};
		DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(opciones);
        combTipoAsiento.setModel(model);
	}
	
	public void cargarCombBoxPaquetes() {
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		if(combClientes.getSelectedItem() != null && combClientes.getSelectedItem() != "") {
			String nickCliente = (String) combClientes.getSelectedItem();
			if(combRutasVuelo.getSelectedItem() != null && combRutasVuelo.getSelectedItem() != "") {
				String nombreRuta = (String) combRutasVuelo.getSelectedItem();

				List<DTCompraPaquete> paquetes = iUsuario.obtenerComprasDePaquetes(nickCliente);
				List<String> paquetesValidos = new ArrayList<String>(); 
				for (DTCompraPaquete compra : paquetes) {
					if (compra.getFechaVenc().isAfter(LocalDate.now()) && 
						compra.getUsosRestantes().containsKey(nombreRuta) &&
						compra.getUsosRestantes().get(nombreRuta) > 0) {
						paquetesValidos.add(compra.getNombrePaquete());
					}
				}
				
				PresentacionUtils.cargarComboBox(paquetesValidos.toArray(new String[0]), combPaquete);
			}
		}
	}

	public void cargarDatosVuelo() {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		String nombreRuta = (String) combRutasVuelo.getSelectedItem();
		String vuelo = (String) combVuelos.getSelectedItem();
		DTVuelo infoVuelo = iVuelo.obtenerDTVuelo(vuelo);
		
		String origen = iVuelo.obtenerDTRutaVuelo(nombreRuta).getOrigen();
		String destino = iVuelo.obtenerDTRutaVuelo(nombreRuta).getDestino();
		

		textFechaVuelo.setText(infoVuelo.getFechaVuelo().toString());
		textDuracion.setText(PresentacionUtils.parseLocalTime(infoVuelo.getDuracion()));
		textOrigen.setText(origen);
		textDestino.setText(destino);
	}
	
	public boolean agregarAcomp() {
		String nombre = textNombre.getText();
		String apellido = textApellido.getText();
		
		if (nombre.isEmpty() || apellido.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Para agregar un acompañante se deben completar los campos Nombre y Apellido", "Reservar Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
		}
		
		String pasajero = nombre + " " + apellido;
		
		if (model.contains(pasajero)) {
			JOptionPane.showMessageDialog(this, "El acompañante seleccionado ya esta ingresado", "Reservar Vuelo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
		}
		model.addElement(pasajero);
		listaAcomps.setModel(model);
		scrollPaneAcomps.revalidate();
		
		PresentacionUtils.limpiarCamposTexto(textNombre, textApellido);
		return true;
	}
	
	public boolean verificaCampos() {
		if (!PresentacionUtils.validarSeleccionComboBox(this, combAerolineas, "Debe seleccionar una Aerolinea")) {
            return false;
        }
		
		if (!PresentacionUtils.validarSeleccionComboBox(this, combRutasVuelo, "Debe seleccionar una Ruta de Vuelo")) {
            return false;
		}
		
        if (!PresentacionUtils.validarSeleccionComboBox(this, combVuelos, "Debe seleccionar un Vuelo")) {
            return false;
        }
		
		if (!PresentacionUtils.validarSeleccionComboBox(this, combClientes, "Debe seleccionar un Cliente")) {
            return false;
        }
  
		if (!PresentacionUtils.validarSeleccionComboBox(this, combTipoAsiento, "Debe seleccionar un tipo de Asiento")) {
            return false;
        }
		
		if (dateChooseFechaAlta.getDate() == null) {
            PresentacionUtils.mostrarError(this, "Debe seleccionar una fecha de alta");
            return false;
        }
		
		try {
			if (Integer.parseInt(textCantEquipajeExtra.getText()) < 0) {
				PresentacionUtils.mostrarError(this, "La cantidad de Equipaje extra debe ser un numero mayor o igual a 0");
             	return false;
         	}
		}	
        catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "La cantidad de Equipaje extra debe ser un numero mayor o igual a 0");
        	return false;
        }
		 
		try {
			if (Integer.parseInt(textCantPasajeros.getText()) <= 0) {
				PresentacionUtils.mostrarError(this, "Debe haber por lo menos 1 pasaje");
				return false;
			}
		}
		catch (NumberFormatException e) {
			PresentacionUtils.mostrarError(this, "La cantidad de pasajes debe ser un numero");
			return false;
		}
		 
		if (Integer.parseInt(textCantPasajeros.getText()) != model.getSize() + 1) {
			PresentacionUtils.mostrarError(this, "La cantidad de pasajes no coincide con la cantidad de pasajeros");
			return false;
		}
		 	
		return true;
	}
	
	public void confirmarReserva() {
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo iVuelo = fabrica.getIVuelo();
		
		String vuelo = (String) combVuelos.getSelectedItem();
		String cliente = (String) combClientes.getSelectedItem();
		String paquete = (String) combPaquete.getSelectedItem();
		EnumAsiento tipoAsiento;
		if ((String) combTipoAsiento.getSelectedItem() == "Turista")
			tipoAsiento = EnumAsiento.TURISTA;
		else
			tipoAsiento = EnumAsiento.EJECUTIVO;
		
		List<DTPasajero> acomps = new ArrayList<DTPasajero>();
		String[] pasajeros = new String[model.getSize()];
		model.copyInto(pasajeros);
		for (String pasajero: pasajeros) {
			String[] acomp = pasajero.split(pasajero, 2);
			DTPasajero nuevoPasajero = new DTPasajero(acomp[0], acomp[1]);
			acomps.add(nuevoPasajero);
		}
		
		int cantEquipajeExtra = Integer.parseInt(textCantEquipajeExtra.getText());
		
		LocalDate fecha;
		//fecha = LocalDate.now();
		fecha = (dateChooseFechaAlta.getDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		if (paquete.equals("") || paquete == null) {
			try {
				iVuelo.crearReserva(vuelo, cliente, tipoAsiento, acomps, cantEquipajeExtra, fecha);
				JOptionPane.showMessageDialog(this, "La Reserva se ha creado con éxito", "Reserva de Vuelo",
						JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
			}
			catch (YaRegistradoException e) {
				PresentacionUtils.mostrarError(this, e.getMessage());
			}			
		} else {
			try {
				iVuelo.crearReserva(vuelo, cliente, tipoAsiento, acomps, cantEquipajeExtra, fecha, paquete);
				JOptionPane.showMessageDialog(this, "La Reserva se ha creado con éxito", "Reserva de Vuelo",
						JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
			}
			catch (YaRegistradoException e) {
				PresentacionUtils.mostrarError(this, e.getMessage());
			}
			catch (NoExisteException ne) {
				PresentacionUtils.mostrarError(this, ne.getMessage());
			}
			catch (OperacionInvalidaException oe) {
				PresentacionUtils.mostrarError(this, oe.getMessage());
			}
		}
		
	}
	
	public void limpiarCampos() {
		PresentacionUtils.limpiarCamposTexto(textNombre, textApellido, textFechaVuelo, textDuracion, 
				textOrigen, textDestino, textCantEquipajeExtra, textCantPasajeros);
		model.clear();
		listaAcomps.setModel(model);
		scrollPaneAcomps.revalidate();
		if (dateChooseFechaAlta != null) {
            dateChooseFechaAlta.setDate(null);
        }
		btnBorrarAcomp.setVisible(false);
		
		if (combAerolineas.getSelectedItem() != null && combAerolineas.getSelectedItem() != "")
			combAerolineas.setSelectedIndex(0);
		
		if (combClientes.getSelectedItem() != null && combClientes.getSelectedItem() != "")
			combClientes.setSelectedIndex(0);
		
		combTipoAsiento.setSelectedIndex(0);
	}
	
	public void prepararFrame() {
		cargarComboBoxAerolinea();
    	cargarComboBoxCliente();
		cargarCombBoxTipoAsiento();
	}
	
	private void closeFrame() {
		limpiarCampos();
		setVisible(false);
	}
}
