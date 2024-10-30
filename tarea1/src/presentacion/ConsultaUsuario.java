package presentacion;

import logica.CompraPaquete;
import logica.Fabrica;
import logica.IUsuario;
import logica.ReservaVuelo;
import logica.datatypes.DTCliente;
import logica.datatypes.DTCompraPaquete;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTReservaVuelo;
import logica.datatypes.DTAerolinea;
import logica.datatypes.DTRutaVuelo;

import utils.PresentacionUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;

import excepciones.EsConjuntoVacioException;

import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ConsultaUsuario extends JInternalFrame {
	private JPanel mainPanel;
	private JPanel formPanel;
	private JScrollPane listScrollPane;
	
	private ConsultaRutaVuelo consRV;
	private ConsultaPaquete consP;
	private ConsultaVuelo consV;

	private JList<String> listaDeUsuarios;
	private JTextField nicknameTF;
	private JTextField emailTF;
	private JTextField nombreTF;
	private JTextField apellidoTF;
	private JTextField fechaNacTF;
	private JTextField nacionalidadTF;
	private JTextField tipoDocTF;
	private JTextField documentoTF;
	private JTextField sitioWebTF;
	private JTextArea descTA;
	private JTable vuelosTable;
	private JTable paquetesTable;
	private JTable rutasVuelosTable;
	private JScrollPane paquetesScrollPane;
	private JScrollPane vuelosScrollPane;
	private JScrollPane rutasVuelosScrollPane;
	
	
	private Map<String, DTReservaVuelo> reservaMap = new HashMap<>();

	private JLabel nicknameLbl;
	private JLabel emailLbl;
	private JLabel nombreLbl;
	private JLabel apellidoLbl;
	private JLabel fechaNacLbl;
	private JLabel nacionalidadLbl;
	private JLabel tipoDocLbl;
	private JLabel documentoLbl;
	private JLabel sitioWebLbl;
	private JLabel descLbl;
	private JLabel rutasLbl;
	private JLabel vuelosLbl;
	private JLabel paquetesLbl;
	
	private DTUsuario datosUsuarioSeleccion;
	private DTAerolinea datosAerolineaSeleccion;
	private DTCliente datosClienteSeleccion;


	public ConsultaUsuario() {
		
		setTitle("Consulta de Usuario");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 770, 520);

		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 200, 379, 0 };
		gbl_mainPanel.rowHeights = new int[] { 350, 0 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		listaDeUsuarios = new JList<>();
		JScrollPane listScrollPane_1 = new JScrollPane(listaDeUsuarios);
		listScrollPane_1.setPreferredSize(new Dimension(200, 300));
		GridBagConstraints gbc_listScrollPane_1 = new GridBagConstraints();
		gbc_listScrollPane_1.anchor = GridBagConstraints.WEST;
		gbc_listScrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_listScrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_listScrollPane_1.gridx = 0;
		gbc_listScrollPane_1.gridy = 0;
		mainPanel.add(listScrollPane_1, gbc_listScrollPane_1);

		listaDeUsuarios.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					actualizarFormulario();
				}
			}
		});

		formPanel = new JPanel();
		GridBagConstraints gbc_formPanel = new GridBagConstraints();
		gbc_formPanel.fill = GridBagConstraints.BOTH;
		gbc_formPanel.gridx = 1;
		gbc_formPanel.gridy = 0;
		mainPanel.add(formPanel, gbc_formPanel);
		GridBagLayout gbl_formPanel = new GridBagLayout();
		gbl_formPanel.columnWidths = new int[] { 177, 258, 5, 93, 5 };
		gbl_formPanel.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 60, 60, 30 };
		gbl_formPanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0 };
		gbl_formPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0 };
		formPanel.setLayout(gbl_formPanel);

		nicknameLbl = new JLabel("Nickname");
		GridBagConstraints gbc_nicknameLbl = new GridBagConstraints();
		gbc_nicknameLbl.anchor = GridBagConstraints.EAST;
		gbc_nicknameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameLbl.gridx = 0;
		gbc_nicknameLbl.gridy = 0;
		formPanel.add(nicknameLbl, gbc_nicknameLbl);

		nicknameTF = new JTextField();
		GridBagConstraints gbc_nicknameTF = new GridBagConstraints();
		gbc_nicknameTF.gridwidth = 2;
		gbc_nicknameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameTF.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameTF.gridx = 1;
		gbc_nicknameTF.gridy = 0;
		formPanel.add(nicknameTF, gbc_nicknameTF);
		nicknameTF.setColumns(10);
		nicknameTF.setEditable(false);

		emailLbl = new JLabel("Email");
		GridBagConstraints gbc_emailLbl = new GridBagConstraints();
		gbc_emailLbl.anchor = GridBagConstraints.EAST;
		gbc_emailLbl.insets = new Insets(0, 0, 5, 5);
		gbc_emailLbl.gridx = 0;
		gbc_emailLbl.gridy = 1;
		formPanel.add(emailLbl, gbc_emailLbl);

		emailTF = new JTextField();
		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.gridwidth = 2;
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.insets = new Insets(0, 0, 5, 5);
		gbc_emailTF.gridx = 1;
		gbc_emailTF.gridy = 1;
		formPanel.add(emailTF, gbc_emailTF);
		emailTF.setColumns(10);
		emailTF.setEditable(false);

		nombreLbl = new JLabel("Nombre");
		GridBagConstraints gbc_nombreLbl = new GridBagConstraints();
		gbc_nombreLbl.anchor = GridBagConstraints.EAST;
		gbc_nombreLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nombreLbl.gridx = 0;
		gbc_nombreLbl.gridy = 2;
		formPanel.add(nombreLbl, gbc_nombreLbl);

		nombreTF = new JTextField();
		GridBagConstraints gbc_nombreTF = new GridBagConstraints();
		gbc_nombreTF.gridwidth = 2;
		gbc_nombreTF.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTF.gridx = 1;
		gbc_nombreTF.gridy = 2;
		formPanel.add(nombreTF, gbc_nombreTF);
		nombreTF.setColumns(10);
		nombreTF.setEditable(false);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				closeFrame();
			} 
		});
	}

	public void rellenarListaDeUsuarios() {
		DefaultListModel<String> model = new DefaultListModel<>();

		Fabrica fabrica = Fabrica.getInstance();
		IUsuario interfazUsuario = fabrica.getIUsuario();

		try {
			String[] listaClientes = interfazUsuario.listarClientes();
			String[] listaAerolineas = interfazUsuario.listarAerolineas();
	
			for (String aerolinea : listaAerolineas) {
				model.addElement(aerolinea);
			}
			for (String cliente : listaClientes) {
				model.addElement(cliente);
			}
	
			listaDeUsuarios.setModel(model);
		}
		catch(EsConjuntoVacioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void actualizarFormulario() {
		String usuarioSeleccionado = listaDeUsuarios.getSelectedValue();
		if (usuarioSeleccionado != null) {
			Fabrica fabrica = Fabrica.getInstance();
			IUsuario interfazUsuario = fabrica.getIUsuario();

			DTUsuario infoUsuario = interfazUsuario.obtenerInfoUsuario(usuarioSeleccionado);

			datosUsuarioSeleccion = infoUsuario;
			
			nicknameTF.setText(infoUsuario.getNick());
			nombreTF.setText(infoUsuario.getNombre());
			emailTF.setText(infoUsuario.getEmail());
			
			String tipoUsuario = interfazUsuario.determinarTipoUsuario(usuarioSeleccionado);

			if (tipoUsuario == "cliente") {
				DTCliente datosCliente = interfazUsuario.obtenerDatosCliente(usuarioSeleccionado);
				
				datosClienteSeleccion = datosCliente;

				limpiarPanel();
				agregarCamposCliente();

				apellidoTF.setText(datosCliente.getApellido());
				fechaNacTF.setText(datosCliente.getFechaNac().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				nacionalidadTF.setText(datosCliente.getNacionalidad());
				tipoDocTF.setText(datosCliente.getTipoDoc().toString());
				documentoTF.setText(datosCliente.getDoc());

				formPanel.revalidate();
				formPanel.repaint();

			} else if (tipoUsuario == "aerolinea") {
				DTAerolinea datosAerolinea = interfazUsuario.obtenerDatosAerolinea(usuarioSeleccionado);
				
				datosAerolineaSeleccion = datosAerolinea;

				limpiarPanel();
				agregarCamposAerolinea();

				sitioWebTF.setText(datosAerolinea.getSitioWeb());
				descTA.setText(datosAerolinea.getDescripcion());

				formPanel.revalidate();
				formPanel.repaint();
			}
		}
	}

	private void limpiarPanel() {
		if (apellidoTF != null) {
			formPanel.remove(apellidoLbl);
			formPanel.remove(apellidoTF);
			formPanel.remove(fechaNacLbl);
			formPanel.remove(fechaNacTF);
			formPanel.remove(nacionalidadLbl);
			formPanel.remove(nacionalidadTF);
			formPanel.remove(tipoDocLbl);
			formPanel.remove(tipoDocTF);
			formPanel.remove(documentoLbl);
			formPanel.remove(documentoTF);
			formPanel.remove(vuelosLbl);
			formPanel.remove(vuelosScrollPane);
			formPanel.remove(vuelosTable);
			formPanel.remove(paquetesLbl);
			formPanel.remove(paquetesScrollPane);
			formPanel.remove(paquetesTable);
		}
		if (sitioWebTF != null) {
			formPanel.remove(sitioWebLbl);
			formPanel.remove(sitioWebTF);
			formPanel.remove(descLbl);
			formPanel.remove(descTA);
			formPanel.remove(rutasLbl);
			formPanel.remove(rutasVuelosScrollPane);
			formPanel.remove(rutasVuelosTable);
		}
	}

	private void agregarCamposCliente() {
		apellidoLbl = new JLabel("Apellido");
		GridBagConstraints gbc_apellidoLabel = new GridBagConstraints();
		gbc_apellidoLabel.anchor = GridBagConstraints.EAST;
		gbc_apellidoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoLabel.gridx = 0;
		gbc_apellidoLabel.gridy = 3;
		formPanel.add(apellidoLbl, gbc_apellidoLabel);

		apellidoTF = new JTextField();
		GridBagConstraints gbc_apellidoTF = new GridBagConstraints();
		gbc_apellidoTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoTF.insets = new Insets(0, 0, 5, 0);
		gbc_apellidoTF.gridx = 1;
		gbc_apellidoTF.gridy = 3;
		formPanel.add(apellidoTF, gbc_apellidoTF);
		apellidoTF.setColumns(10);
		apellidoTF.setEditable(false);

		fechaNacLbl = new JLabel("Fecha de Nacimiento");
		GridBagConstraints gbc_fechaNacLbl = new GridBagConstraints();
		gbc_fechaNacLbl.anchor = GridBagConstraints.EAST;
		gbc_fechaNacLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fechaNacLbl.gridx = 0;
		gbc_fechaNacLbl.gridy = 4;
		formPanel.add(fechaNacLbl, gbc_fechaNacLbl);

		fechaNacTF = new JTextField();
		GridBagConstraints gbc_fechaNacTF = new GridBagConstraints();
		gbc_fechaNacTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaNacTF.insets = new Insets(0, 0, 5, 0);
		gbc_fechaNacTF.gridx = 1;
		gbc_fechaNacTF.gridy = 4;
		formPanel.add(fechaNacTF, gbc_fechaNacTF);
		fechaNacTF.setColumns(10);
		fechaNacTF.setEditable(false);

		nacionalidadLbl = new JLabel("Nacionalidad");
		GridBagConstraints gbc_nacionalidadLbl = new GridBagConstraints();
		gbc_nacionalidadLbl.anchor = GridBagConstraints.EAST;
		gbc_nacionalidadLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nacionalidadLbl.gridx = 0;
		gbc_nacionalidadLbl.gridy = 5;
		formPanel.add(nacionalidadLbl, gbc_nacionalidadLbl);

		nacionalidadTF = new JTextField();
		GridBagConstraints gbc_nacionalidadTF = new GridBagConstraints();
		gbc_nacionalidadTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nacionalidadTF.insets = new Insets(0, 0, 5, 0);
		gbc_nacionalidadTF.gridx = 1;
		gbc_nacionalidadTF.gridy = 5;
		formPanel.add(nacionalidadTF, gbc_nacionalidadTF);
		nacionalidadTF.setColumns(10);
		nacionalidadTF.setEditable(false);

		tipoDocLbl = new JLabel("Tipo de Documento");
		GridBagConstraints gbc_tipoDocLbl = new GridBagConstraints();
		gbc_tipoDocLbl.anchor = GridBagConstraints.EAST;
		gbc_tipoDocLbl.insets = new Insets(0, 0, 5, 5);
		gbc_tipoDocLbl.gridx = 0;
		gbc_tipoDocLbl.gridy = 6;
		formPanel.add(tipoDocLbl, gbc_tipoDocLbl);

		tipoDocTF = new JTextField();
		GridBagConstraints gbc_tipoDocTF = new GridBagConstraints();
		gbc_tipoDocTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipoDocTF.insets = new Insets(0, 0, 5, 0);
		gbc_tipoDocTF.gridx = 1;
		gbc_tipoDocTF.gridy = 6;
		formPanel.add(tipoDocTF, gbc_tipoDocTF);
		tipoDocTF.setColumns(10);
		tipoDocTF.setEditable(false);

		documentoLbl = new JLabel("Documento");
		GridBagConstraints gbc_documentoLbl = new GridBagConstraints();
		gbc_documentoLbl.anchor = GridBagConstraints.EAST;
		gbc_documentoLbl.insets = new Insets(0, 0, 5, 5);
		gbc_documentoLbl.gridx = 0;
		gbc_documentoLbl.gridy = 7;
		formPanel.add(documentoLbl, gbc_documentoLbl);

		documentoTF = new JTextField();
		GridBagConstraints gbc_documentoTF = new GridBagConstraints();
		gbc_documentoTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_documentoTF.insets = new Insets(0, 0, 5, 0);
		gbc_documentoTF.gridx = 1;
		gbc_documentoTF.gridy = 7;
		formPanel.add(documentoTF, gbc_documentoTF);
		documentoTF.setColumns(10);
		documentoTF.setEditable(false);
		
		// Vuelos
		vuelosLbl = new JLabel("Vuelos");
		GridBagConstraints gbc_vuelosLbl = new GridBagConstraints();
		gbc_vuelosLbl.anchor = GridBagConstraints.EAST;
		gbc_vuelosLbl.insets = new Insets(0, 0, 5, 5);
		gbc_vuelosLbl.gridx = 0;
		gbc_vuelosLbl.gridy = 8;
		formPanel.add(vuelosLbl, gbc_vuelosLbl);
		
		vuelosScrollPane = new JScrollPane();
		vuelosScrollPane.setPreferredSize(new Dimension(vuelosScrollPane.getPreferredSize().width, 300));
		GridBagConstraints gbc_vuelosScrollPane = new GridBagConstraints();
		gbc_vuelosScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_vuelosScrollPane.fill = GridBagConstraints.BOTH;
		gbc_vuelosScrollPane.gridx = 1;
		gbc_vuelosScrollPane.gridy = 8;
		formPanel.add(vuelosScrollPane, gbc_vuelosScrollPane);
		
	    String[] vuelosColumnNames = {"Nombre del Vuelo", "Fecha de Reserva"};
        DefaultTableModel vuelosTableModel = new DefaultTableModel(vuelosColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
	    vuelosTable = new JTable(vuelosTableModel);
	    vuelosTable.setPreferredScrollableViewportSize(new Dimension(vuelosTable.getPreferredSize().width, 300));
	    vuelosScrollPane.setViewportView(vuelosTable);
		
		String nickCliente = datosUsuarioSeleccion.getNick();
		
		
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario interfazUsuario = fabrica.getIUsuario();
		
		List<DTReservaVuelo> reservas = interfazUsuario.obtenerReservasVueloCliente(nickCliente);

		for (DTReservaVuelo reserva : reservas) {
            String fecha = reserva.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String nombreVuelo = reserva.getNameVuelo();
            vuelosTableModel.addRow(new Object[]{nombreVuelo, fecha});
            reservaMap.put(nombreVuelo, reserva);
	    }
		
		vuelosTable.getSelectionModel().addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting() && vuelosTable.getSelectedRow() != -1) {
		        int selectedRow = vuelosTable.getSelectedRow();
		        
		        String nombreVuelo = (String) vuelosTable.getValueAt(selectedRow, 0);
		
		        DTReservaVuelo reservaSeleccionada = reservaMap.get(nombreVuelo);
		        
		        consV.prepararFrame();
		        consV.cargarDatosVuelo(reservaSeleccionada);
		        
		        consV.setVisible(true);
		    }
		});
		
		// Paquetes
		paquetesLbl = new JLabel("Paquetes");
		GridBagConstraints gbc_paquetesLbl = new GridBagConstraints();
		gbc_paquetesLbl.anchor = GridBagConstraints.EAST;
		gbc_paquetesLbl.insets = new Insets(0, 0, 5, 5);
		gbc_paquetesLbl.gridx = 0;
		gbc_paquetesLbl.gridy = 9;
		formPanel.add(paquetesLbl, gbc_paquetesLbl);

		paquetesScrollPane = new JScrollPane();
        GridBagConstraints gbc_paquetesScrollPane = new GridBagConstraints();
        gbc_paquetesScrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_paquetesScrollPane.fill = GridBagConstraints.BOTH;
        gbc_paquetesScrollPane.gridx = 1;
        gbc_paquetesScrollPane.gridy = 9;
        formPanel.add(paquetesScrollPane, gbc_paquetesScrollPane);

        String[] paquetesColumnNames = {"Nombre del Paquete", "Fecha de Compra"};
        DefaultTableModel paquetesTableModel = new DefaultTableModel(paquetesColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paquetesTable = new JTable(paquetesTableModel);
        paquetesTable.setPreferredScrollableViewportSize(new Dimension(paquetesTable.getPreferredSize().width, 150));
        paquetesScrollPane.setViewportView(paquetesTable);
        
        List<DTCompraPaquete> comprasDePaquetes = interfazUsuario.obtenerComprasDePaquetes(nickCliente);
        
        for (DTCompraPaquete compra : comprasDePaquetes) {
            String nombre = compra.getNombrePaquete();
            LocalDate fechaCompra = compra.getFechaCompra();
            
            String fechaCompraStr = fechaCompra.toString();
            
            paquetesTableModel.addRow(new Object[]{nombre, fechaCompraStr});
        }
        
        paquetesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && paquetesTable.getSelectedRow() != -1) {
                int selectedRow = paquetesTable.getSelectedRow();
                
                String nombrePaquete = (String) paquetesTable.getValueAt(selectedRow, 0);
                
                consP.prepararFrame();
                consP.setVisible(true);
                
                consP.consultaPaquete(nombrePaquete);
            }
        });
}

	private void agregarCamposAerolinea() {
		sitioWebLbl = new JLabel("Sitio Web");
		GridBagConstraints gbc_sitioWebLbl = new GridBagConstraints();
		gbc_sitioWebLbl.anchor = GridBagConstraints.EAST;
		gbc_sitioWebLbl.insets = new Insets(0, 0, 5, 5);
		gbc_sitioWebLbl.gridx = 0;
		gbc_sitioWebLbl.gridy = 3;
		formPanel.add(sitioWebLbl, gbc_sitioWebLbl);

		sitioWebTF = new JTextField();
		GridBagConstraints gbc_sitioWebTF = new GridBagConstraints();
		gbc_sitioWebTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_sitioWebTF.insets = new Insets(0, 0, 5, 0);
		gbc_sitioWebTF.gridx = 1;
		gbc_sitioWebTF.gridy = 3;
		formPanel.add(sitioWebTF, gbc_sitioWebTF);
		sitioWebTF.setColumns(10);
		sitioWebTF.setEditable(false);

		descLbl = new JLabel("Descripción");
		GridBagConstraints gbc_descLbl = new GridBagConstraints();
		gbc_descLbl.anchor = GridBagConstraints.EAST;
		gbc_descLbl.insets = new Insets(0, 0, 5, 5);
		gbc_descLbl.gridx = 0;
		gbc_descLbl.gridy = 4;
		formPanel.add(descLbl, gbc_descLbl);

		descTA = new JTextArea();
		GridBagConstraints gbc_descTA = new GridBagConstraints();
		gbc_descTA.fill = GridBagConstraints.HORIZONTAL;
		gbc_descTA.insets = new Insets(0, 0, 5, 0);
		gbc_descTA.gridx = 1;
		gbc_descTA.gridy = 4;
		formPanel.add(descTA, gbc_descTA);
		descTA.setColumns(10);
		descTA.setRows(3);
		descTA.setLineWrap(true);
		descTA.setWrapStyleWord(true);
		descTA.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		Border compoundBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.decode("#C2C2C2")),
				new EmptyBorder(2, 5, 2, 5));
		descTA.setBorder(compoundBorder);
		descTA.setEditable(false);

		rutasLbl = new JLabel("Ruta de Vuelo");
		GridBagConstraints gbc_rutasLbl = new GridBagConstraints();
		gbc_rutasLbl.anchor = GridBagConstraints.EAST;
		gbc_rutasLbl.insets = new Insets(0, 0, 5, 5);
		gbc_rutasLbl.gridx = 0;
		gbc_rutasLbl.gridy = 5;
		formPanel.add(rutasLbl, gbc_rutasLbl);

		rutasVuelosScrollPane = new JScrollPane();
		rutasVuelosScrollPane.setMinimumSize(new Dimension(100, 150));
        GridBagConstraints gbc_rutasVuelosScrollPane = new GridBagConstraints();
        gbc_rutasVuelosScrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_rutasVuelosScrollPane.fill = GridBagConstraints.BOTH;
        gbc_rutasVuelosScrollPane.gridx = 1;
        gbc_rutasVuelosScrollPane.gridy = 5;
        formPanel.add(rutasVuelosScrollPane, gbc_rutasVuelosScrollPane);

        String[] rutasVuelosColumnNames = {"Nombre de la Ruta", "Descripción"};
        DefaultTableModel rutasVuelosTableModel = new DefaultTableModel(rutasVuelosColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rutasVuelosTable = new JTable(rutasVuelosTableModel);
        rutasVuelosTable.setPreferredScrollableViewportSize(new Dimension(rutasVuelosTable.getPreferredSize().width, 150));
        rutasVuelosScrollPane.setViewportView(rutasVuelosTable);

		
        for (DTRutaVuelo ruta : datosAerolineaSeleccion.getRutasVuelo()) {
            String nombre = ruta.getName();
            String desc = ruta.getDesc();
            
            
            rutasVuelosTableModel.addRow(new Object[]{nombre, desc});
        }
        
        rutasVuelosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && rutasVuelosTable.getSelectedRow() != -1) {
            	Fabrica fabrica = Fabrica.getInstance();
                IUsuario interfazUsuario = fabrica.getIUsuario();

                int selectedRow = rutasVuelosTable.getSelectedRow();

                String nombreRuta = (String) rutasVuelosTable.getValueAt(selectedRow, 0);

                String aerolinea = interfazUsuario.obtenerNickAerolineaDeRutaVuelo(nombreRuta);
                consRV.prepararFrame();
	    		consRV.listarInfoRutaVueloPaquete(aerolinea, nombreRuta);
                consRV.setVisible(true);
                
                //consRV.listarInfoRutaVueloPaquete(aerolinea, nombreRuta);
            }
        });
	}
    
//    public void pasarJInternalFrameConsultaRutaVuelo(ConsultaRutaVuelo crv) {
//    	consRV = crv;
//    }
//    
//    public void pasarJInternalFrameConsultaPaquete(ConsultaPaquete cp) {
//    	consP = cp;
//    }
//
//    public void pasarJInternalFrameConsultaVuelo(ConsultaVuelo cv) {
//    	consV = cv;
//    }
	
	public void prepararFrame() {
		rellenarListaDeUsuarios();
		consRV = Principal.conRutInternalFrame;
    	consV = Principal.conVueInternalFrame;
    	consP = Principal.conPaqInternalFrame;
	}
	

	private void closeFrame() {
		nicknameTF.setText(null);
		nombreTF.setText(null);
		emailTF.setText(null);
		limpiarPanel();
		setVisible(false);
	}
}