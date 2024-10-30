package presentacion;

import logica.Fabrica;
import logica.datatypes.DTAerolinea;
import logica.datatypes.DTCliente;
import logica.datatypes.DTUsuario;
import logica.IUsuario;
import logica.enums.EnumDoc;
import utils.PresentacionUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.CompoundBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.LocalDate;
import java.time.ZoneId;

import com.toedter.calendar.JDateChooser;

import excepciones.EsConjuntoVacioException;

@SuppressWarnings("serial")
public class ModificarUsuario extends JInternalFrame {
	private JPanel mainPanel;
	private JPanel formPanel;
	private JPanel buttonPanel;
	private JScrollPane listScrollPane;

	private JList<String> listaDeUsuarios;
	private JButton modificarButton;
	private JButton cancelarButton;
	private JTextField nicknameTF;
	private JTextField emailTF;
	private JTextField nombreTF;
	private JTextField apellidoTF;
	private JDateChooser fechaNacDC;
	private JTextField nacionalidadTF;
	private JComboBox<String> comboTipoDoc;
	private JTextField documentoTF;
	private JTextField sitioWebTF;
	private JTextArea descTA;
	private JScrollPane descScrollPane;
	private JPasswordField contraseniaTF;

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
	private JLabel lblContrasea;

	private DTUsuario datosUsuarioSeleccion;
	private DTAerolinea datosAerolineaSeleccion;
	private DTCliente datosClienteSeleccion;

	public ModificarUsuario() {

		setTitle("Modificar Datos de Usuario");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 400);

		mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(mainPanel);

		listaDeUsuarios = new JList<>();
		JScrollPane listScrollPane = new JScrollPane(listaDeUsuarios);
		listScrollPane.setPreferredSize(new Dimension(200, 300));
		mainPanel.add(listScrollPane, BorderLayout.WEST);

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		modificarButton = new JButton("Guardar");
		cancelarButton = new JButton("Cancelar");
		buttonPanel.add(modificarButton);
		buttonPanel.add(cancelarButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		formPanel = new JPanel();
		mainPanel.add(formPanel, BorderLayout.CENTER);
		GridBagLayout gbl_formPanel = new GridBagLayout();
		gbl_formPanel.columnWidths = new int[] { 177, 215 };
		gbl_formPanel.rowHeights = new int[] { 20, 0, 20, 20, 20, 20, 20, 20, 0, 0, 0 };
		gbl_formPanel.columnWeights = new double[] { 0.0, 1.0 };
		gbl_formPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_nicknameTF.anchor = GridBagConstraints.NORTH;
		gbc_nicknameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameTF.insets = new Insets(0, 0, 5, 0);
		gbc_nicknameTF.gridx = 1;
		gbc_nicknameTF.gridy = 0;
		formPanel.add(nicknameTF, gbc_nicknameTF);
		nicknameTF.setColumns(10);
		nicknameTF.setEditable(false);

		lblContrasea = new JLabel("Contraseña");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.gridx = 0;
		gbc_lblContrasea.gridy = 1;
		formPanel.add(lblContrasea, gbc_lblContrasea);

		contraseniaTF = new JPasswordField();
		contraseniaTF.setEditable(false);
		contraseniaTF.setColumns(10);
		GridBagConstraints gbc_contraseniaTF = new GridBagConstraints();
		gbc_contraseniaTF.insets = new Insets(0, 0, 5, 0);
		gbc_contraseniaTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_contraseniaTF.gridx = 1;
		gbc_contraseniaTF.gridy = 1;
		formPanel.add(contraseniaTF, gbc_contraseniaTF);

		emailLbl = new JLabel("Email");
		GridBagConstraints gbc_emailLbl = new GridBagConstraints();
		gbc_emailLbl.anchor = GridBagConstraints.EAST;
		gbc_emailLbl.insets = new Insets(0, 0, 5, 5);
		gbc_emailLbl.gridx = 0;
		gbc_emailLbl.gridy = 2;
		formPanel.add(emailLbl, gbc_emailLbl);

		emailTF = new JTextField();
		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.anchor = GridBagConstraints.NORTH;
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.insets = new Insets(0, 0, 5, 0);
		gbc_emailTF.gridx = 1;
		gbc_emailTF.gridy = 2;
		formPanel.add(emailTF, gbc_emailTF);
		emailTF.setColumns(10);
		emailTF.setEditable(false);

		nombreLbl = new JLabel("Nombre");
		GridBagConstraints gbc_nombreLbl = new GridBagConstraints();
		gbc_nombreLbl.anchor = GridBagConstraints.EAST;
		gbc_nombreLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nombreLbl.gridx = 0;
		gbc_nombreLbl.gridy = 3;
		formPanel.add(nombreLbl, gbc_nombreLbl);

		nombreTF = new JTextField();
		GridBagConstraints gbc_nombreTF = new GridBagConstraints();
		gbc_nombreTF.insets = new Insets(0, 0, 5, 0);
		gbc_nombreTF.anchor = GridBagConstraints.NORTH;
		gbc_nombreTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTF.gridx = 1;
		gbc_nombreTF.gridy = 3;
		formPanel.add(nombreTF, gbc_nombreTF);
		nombreTF.setColumns(10);
		nombreTF.setEditable(false);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				closeFrame();
			}
		});

		listaDeUsuarios.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					actualizarFormulario();
				}
			}
		});

		modificarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modificarDatosUsuario();
				} catch (Exception ex) {
					PresentacionUtils.mostrarError(ModificarUsuario.this, "Error modificando el usuario.");
				}
			}
		});

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		} catch (EsConjuntoVacioException e) {
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
			contraseniaTF.setText(infoUsuario.getContrasenia());
			nombreTF.setText(infoUsuario.getNombre());
			emailTF.setText(infoUsuario.getEmail());

			String tipoUsuario = interfazUsuario.determinarTipoUsuario(usuarioSeleccionado);

			if (tipoUsuario == "cliente") {
				DTCliente datosCliente = interfazUsuario.obtenerDatosCliente(usuarioSeleccionado);

				datosClienteSeleccion = datosCliente;

				limpiarPanel();
				agregarCamposCliente();

				int tipoDocIndex = (datosCliente.getTipoDoc() == EnumDoc.CEDULA) ? 1 : 2;

				apellidoTF.setText(datosCliente.getApellido());
				fechaNacDC.setDate(java.util.Date
						.from(datosCliente.getFechaNac().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				nacionalidadTF.setText(datosCliente.getNacionalidad());
				comboTipoDoc.setSelectedIndex(tipoDocIndex);
				documentoTF.setText(datosCliente.getDoc());

				formPanel.revalidate();
				formPanel.repaint();

			} else if (tipoUsuario == "aerolinea") {
				DTAerolinea datosAerolinea = interfazUsuario.obtenerDatosAerolinea(usuarioSeleccionado);

				datosAerolineaSeleccion = datosAerolinea;

				limpiarPanel();
				agregarCamposAerolinea();

				descTA.setColumns(10);
				descTA.setRows(3);
				descTA.setLineWrap(true);
				descTA.setWrapStyleWord(true);

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
			formPanel.remove(fechaNacDC);
			formPanel.remove(nacionalidadLbl);
			formPanel.remove(nacionalidadTF);
			formPanel.remove(tipoDocLbl);
			formPanel.remove(comboTipoDoc);
			formPanel.remove(documentoLbl);
			formPanel.remove(documentoTF);
		}
		if (sitioWebTF != null) {
			formPanel.remove(sitioWebLbl);
			formPanel.remove(sitioWebTF);
			formPanel.remove(descLbl);
			formPanel.remove(descScrollPane);
		}
	}

	private void agregarCamposCliente() {
		contraseniaTF.setEditable(true);
		nombreTF.setEditable(true);

		Integer gridyInicio = 4;

		apellidoLbl = new JLabel("Apellido");
		GridBagConstraints gbc_apellidoLabel = new GridBagConstraints();
		gbc_apellidoLabel.anchor = GridBagConstraints.EAST;
		gbc_apellidoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoLabel.gridx = 0;
		gbc_apellidoLabel.gridy = gridyInicio;
		formPanel.add(apellidoLbl, gbc_apellidoLabel);

		apellidoTF = new JTextField();
		GridBagConstraints gbc_apellidoTF = new GridBagConstraints();
		gbc_apellidoTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoTF.insets = new Insets(0, 0, 5, 0);
		gbc_apellidoTF.gridx = 1;
		gbc_apellidoTF.gridy = gridyInicio;
		formPanel.add(apellidoTF, gbc_apellidoTF);
		apellidoTF.setColumns(10);

		fechaNacLbl = new JLabel("Fecha de Nacimiento");
		GridBagConstraints gbc_fechaNacLbl = new GridBagConstraints();
		gbc_fechaNacLbl.anchor = GridBagConstraints.EAST;
		gbc_fechaNacLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fechaNacLbl.gridx = 0;
		gbc_fechaNacLbl.gridy = gridyInicio + 1;
		formPanel.add(fechaNacLbl, gbc_fechaNacLbl);

		fechaNacDC = new JDateChooser();
		GridBagConstraints gbc_fechaNacDC = new GridBagConstraints();
		gbc_fechaNacDC.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaNacDC.insets = new Insets(0, 0, 5, 0);
		gbc_fechaNacDC.gridx = 1;
		gbc_fechaNacDC.gridy = gridyInicio + 1;
		formPanel.add(fechaNacDC, gbc_fechaNacDC);

		nacionalidadLbl = new JLabel("Nacionalidad");
		GridBagConstraints gbc_nacionalidadLbl = new GridBagConstraints();
		gbc_nacionalidadLbl.anchor = GridBagConstraints.EAST;
		gbc_nacionalidadLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nacionalidadLbl.gridx = 0;
		gbc_nacionalidadLbl.gridy = gridyInicio + 2;
		formPanel.add(nacionalidadLbl, gbc_nacionalidadLbl);

		nacionalidadTF = new JTextField();
		GridBagConstraints gbc_nacionalidadTF = new GridBagConstraints();
		gbc_nacionalidadTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nacionalidadTF.insets = new Insets(0, 0, 5, 0);
		gbc_nacionalidadTF.gridx = 1;
		gbc_nacionalidadTF.gridy = gridyInicio + 2;
		formPanel.add(nacionalidadTF, gbc_nacionalidadTF);
		nacionalidadTF.setColumns(10);

		tipoDocLbl = new JLabel("Tipo de Documento");
		GridBagConstraints gbc_tipoDocLbl = new GridBagConstraints();
		gbc_tipoDocLbl.anchor = GridBagConstraints.EAST;
		gbc_tipoDocLbl.insets = new Insets(0, 0, 5, 5);
		gbc_tipoDocLbl.gridx = 0;
		gbc_tipoDocLbl.gridy = gridyInicio + 3;
		formPanel.add(tipoDocLbl, gbc_tipoDocLbl);

		comboTipoDoc = new JComboBox();
		GridBagConstraints gbc_comboTipoDoc = new GridBagConstraints();
		gbc_comboTipoDoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboTipoDoc.insets = new Insets(0, 0, 5, 0);
		gbc_comboTipoDoc.gridx = 1;
		gbc_comboTipoDoc.gridy = gridyInicio + 3;
		formPanel.add(comboTipoDoc, gbc_comboTipoDoc);
		comboTipoDoc.addItem("");
		comboTipoDoc.addItem("CI");
		comboTipoDoc.addItem("Pasaporte");

		documentoLbl = new JLabel("Tipo de Documento");
		GridBagConstraints gbc_documentoLbl = new GridBagConstraints();
		gbc_documentoLbl.anchor = GridBagConstraints.EAST;
		gbc_documentoLbl.insets = new Insets(0, 0, 5, 5);
		gbc_documentoLbl.gridx = 0;
		gbc_documentoLbl.gridy = gridyInicio + 4;
		formPanel.add(documentoLbl, gbc_documentoLbl);

		documentoTF = new JTextField();
		GridBagConstraints gbc_documentoTF = new GridBagConstraints();
		gbc_documentoTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_documentoTF.insets = new Insets(0, 0, 5, 0);
		gbc_documentoTF.gridx = 1;
		gbc_documentoTF.gridy = gridyInicio + 4;
		formPanel.add(documentoTF, gbc_documentoTF);
		documentoTF.setColumns(10);

	}

	private void agregarCamposAerolinea() {
		contraseniaTF.setEditable(true);
		nombreTF.setEditable(true);

		Integer gridyInicio = 4;

		sitioWebLbl = new JLabel("Sitio Web");
		GridBagConstraints gbc_sitioWebLbl = new GridBagConstraints();
		gbc_sitioWebLbl.anchor = GridBagConstraints.EAST;
		gbc_sitioWebLbl.insets = new Insets(0, 0, 5, 5);
		gbc_sitioWebLbl.gridx = 0;
		gbc_sitioWebLbl.gridy = gridyInicio;
		formPanel.add(sitioWebLbl, gbc_sitioWebLbl);

		sitioWebTF = new JTextField();
		GridBagConstraints gbc_sitioWebTF = new GridBagConstraints();
		gbc_sitioWebTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_sitioWebTF.insets = new Insets(0, 0, 5, 0);
		gbc_sitioWebTF.gridx = 1;
		gbc_sitioWebTF.gridy = gridyInicio;
		formPanel.add(sitioWebTF, gbc_sitioWebTF);
		sitioWebTF.setColumns(10);

		descLbl = new JLabel("Descripción");
		GridBagConstraints gbc_descLbl = new GridBagConstraints();
		gbc_descLbl.anchor = GridBagConstraints.EAST;
		gbc_descLbl.insets = new Insets(0, 0, 5, 5);
		gbc_descLbl.gridx = 0;
		gbc_descLbl.gridy = gridyInicio + 1;
		formPanel.add(descLbl, gbc_descLbl);

		descTA = new JTextArea();
		descTA.setColumns(10);
		descTA.setLineWrap(true);
		descTA.setWrapStyleWord(true);

		// Envolver JTextArea en un JScrollPane
		descScrollPane = new JScrollPane(descTA);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = gridyInicio + 1;

		// Añadir JScrollPane (que contiene JTextArea) al layout
		formPanel.add(descScrollPane, gbc_scrollPane);
	}

	private void modificarDatosUsuario() throws Exception {
		String nick = nicknameTF.getText();
		char[] contrasenia = contraseniaTF.getPassword();
		String email = emailTF.getText();
		String nombre = nombreTF.getText();

		Fabrica fabrica = Fabrica.getInstance();
		IUsuario interfazUsuario = fabrica.getIUsuario();

		String tipoUsuario = interfazUsuario.determinarTipoUsuario(nick);

		if (validarCampos(tipoUsuario)) {
			if (tipoUsuario == "cliente") {
				LocalDate fechaNac = fechaNacDC.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				String documento = documentoTF.getText();
				EnumDoc tipoDoc = "CI".equals(comboTipoDoc.getSelectedItem()) ? EnumDoc.CEDULA : EnumDoc.PASAPORTE;

				interfazUsuario.modificarCliente(nick, nombre, apellidoTF.getText(), email, new String(contrasenia),
						fechaNac, nacionalidadTF.getText(), tipoDoc, documento);
			} else if (tipoUsuario == "aerolinea") {
				interfazUsuario.modificarAerolinea(nick, nombre, email, new String(contrasenia), descTA.getText(),
						sitioWebTF.getText());
			}
			PresentacionUtils.mostrarExito(this, "Usuario modificado con éxito");
		}
	}

	private boolean validarCampos(String tipoUsuario) {
		if (tipoUsuario == "cliente") {
			if (fechaNacDC.getDate() == null) {
				PresentacionUtils.mostrarError(this, "Debe seleccionar una fecha de nacimiento");
				return false;
			}
			return PresentacionUtils.validarSeleccionComboBox(this, comboTipoDoc,
					"Debe seleccionar un tipo de documento")
					&& PresentacionUtils.validarCamposRequeridos(this, "Todos los campos son obligatorios para Cliente",
							nombreTF, apellidoTF, nacionalidadTF, documentoTF);
		} else if (tipoUsuario == "aerolinea") {
			return PresentacionUtils.validarCamposRequeridos(this,
					"Los campos nombre y descripción son obligatorios para Aerolinea", nombreTF, descTA);
		}
		return true;
	}

	public void prepararFrame() {
		rellenarListaDeUsuarios();
	}

	private void closeFrame() {
		nicknameTF.setText(null);
		nombreTF.setText(null);
		nombreTF.setEditable(false);
		contraseniaTF.setText(null);
		contraseniaTF.setEditable(false);
		emailTF.setText(null);
		limpiarPanel();
		setVisible(false);
	}
}
