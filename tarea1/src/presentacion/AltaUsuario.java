package presentacion;

import logica.Fabrica;
import logica.IUsuario;
import logica.enums.EnumDoc;
import utils.PresentacionUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class AltaUsuario extends JInternalFrame {
	private JPanel mainPanel;
	private JPanel formPanel;
	private JPanel botonesPanel;

	private JTextField nicknameTF;
	private JPasswordField contraseniaTF;
	private JPasswordField rptContraseniaTF;
	private JTextField nombreTF;
	private JTextField emailTF;

	private JTextArea descTA;
	private JTextField sitioWebTF;

	private JTextField apellidoTF;
	private JDateChooser fechaNacDC;
	private JTextField nacionalidadTF;
	private JTextField documentoTF;
	private JComboBox<String> comboTipoUsuario;
	private JComboBox<String> comboTipoDoc;
	private JPanel panelC;
	private JPanel panelL;
	private JPanel panelR;

	public AltaUsuario() {

		setTitle("Crear Usuario");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 520, 520);

		mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(mainPanel);

		panelC = new JPanel(new BorderLayout(10, 10));
		panelC.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(panelC, BorderLayout.CENTER);

		GridBagLayout gbl_formPanel = new GridBagLayout();
		gbl_formPanel.rowWeights = new double[] { 0.0 };
		gbl_formPanel.rowHeights = new int[] { 0 };
		gbl_formPanel.columnWidths = new int[] { 127, 0 };
		formPanel = new JPanel(gbl_formPanel);
		panelC.add(formPanel, BorderLayout.NORTH);

		panelL = new JPanel();
		mainPanel.add(panelL, BorderLayout.WEST);

		panelR = new JPanel();
		mainPanel.add(panelR, BorderLayout.EAST);

		botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		mainPanel.add(botonesPanel, BorderLayout.SOUTH);

		inicializarComponentes();

		JButton btnAceptar = new JButton("Aceptar");
		botonesPanel.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		botonesPanel.add(btnCancelar);

		comboTipoUsuario.addActionListener(e -> actualizarFormulario());
		btnCancelar.addActionListener(e -> {
			limpiarCampos();
			setVisible(false);
		});
		btnAceptar.addActionListener(e -> {
			if (validarCampos()) {
				crearUsuario();
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

	private void inicializarComponentes() {
		comboTipoUsuario = new JComboBox<>();
		comboTipoUsuario.addItem("");
		comboTipoUsuario.addItem("Aerolinea");
		comboTipoUsuario.addItem("Cliente");
		añadirComponente(new JLabel("Tipo de Usuario:"), comboTipoUsuario, 0);

		nicknameTF = new JTextField();
		añadirComponente(new JLabel("Nickname:"), nicknameTF, 1);

		contraseniaTF = new JPasswordField();
		añadirComponente(new JLabel("Contraseña:"), contraseniaTF, 2);

		rptContraseniaTF = new JPasswordField();
		añadirComponente(new JLabel("Repetir contraseña:"), rptContraseniaTF, 3);

		nombreTF = new JTextField();
		añadirComponente(new JLabel("Nombre:"), nombreTF, 4);

		emailTF = new JTextField();
		añadirComponente(new JLabel("Email:"), emailTF, 5);
	}

	private void actualizarFormulario() {
		String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
		formPanel.removeAll();

		añadirComponente(new JLabel("Tipo de Usuario:"), comboTipoUsuario, 0);
		añadirComponente(new JLabel("Nickname:"), nicknameTF, 1);
		añadirComponente(new JLabel("Contraseña:"), contraseniaTF, 2);
		añadirComponente(new JLabel("Repetir contraseña:"), rptContraseniaTF, 3);
		añadirComponente(new JLabel("Nombre:"), nombreTF, 4);
		añadirComponente(new JLabel("Email:"), emailTF, 5);

		if ("Cliente".equals(tipoUsuario)) {
			agregarCamposCliente();
		} else if ("Aerolinea".equals(tipoUsuario)) {
			agregarCamposAerolinea();
		}

		formPanel.revalidate();
		formPanel.repaint();
	}

	private void añadirComponente(JLabel label, JComponent component, int gridy) {
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.gridx = 0;
		gbcLabel.gridy = gridy;
		gbcLabel.anchor = GridBagConstraints.EAST;
		gbcLabel.insets = new Insets(5, 5, 5, 5);
		formPanel.add(label, gbcLabel);

		GridBagConstraints gbcComponent = new GridBagConstraints();
		gbcComponent.gridx = 1;
		gbcComponent.gridy = gridy;
		gbcComponent.fill = GridBagConstraints.HORIZONTAL;
		gbcComponent.weightx = 1.0;
		gbcComponent.insets = new Insets(5, 5, 5, 5);
		formPanel.add(component, gbcComponent);
	}

	private void agregarCamposCliente() {
		apellidoTF = new JTextField();
		añadirComponente(new JLabel("Apellido:"), apellidoTF, 6);

		fechaNacDC = new JDateChooser();
		añadirComponente(new JLabel("Fecha de Nacimiento:"), fechaNacDC, 7);

		nacionalidadTF = new JTextField();
		añadirComponente(new JLabel("Nacionalidad:"), nacionalidadTF, 8);

		comboTipoDoc = new JComboBox<>();
		comboTipoDoc.addItem("");
		comboTipoDoc.addItem("CI");
		comboTipoDoc.addItem("Pasaporte");
		añadirComponente(new JLabel("Tipo de Documento:"), comboTipoDoc, 9);

		documentoTF = new JTextField();
		añadirComponente(new JLabel("Documento:"), documentoTF, 10);
	}

	private void agregarCamposAerolinea() {
		descTA = new JTextArea();
		descTA.setRows(3);
		descTA.setLineWrap(true);
		descTA.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(descTA);
		añadirComponente(new JLabel("Descripción:"), scrollPane, 6);

		sitioWebTF = new JTextField();
		añadirComponente(new JLabel("Sitio Web:"), sitioWebTF, 7);
	}

	private boolean validarCampos() {
		String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
		if (tipoUsuario == null || tipoUsuario.isEmpty()) {
			PresentacionUtils.mostrarError(this, "Debe seleccionar un tipo de usuario");
			return false;
		}

		if ("Aerolinea".equals(tipoUsuario)) {
			if(!PresentacionUtils.validarCamposRequeridos(this,
					"Los campos nickname, nombre, contraseña, repetir contraseña, email y descripción son obligatorios para Aerolinea",
					nicknameTF, nombreTF, emailTF, descTA)) {
				return false;
			}
		} else if ("Cliente".equals(tipoUsuario)) {
			if (!PresentacionUtils.validarCamposRequeridos(this, "Todos los campos son obligatorios para Cliente",
					nicknameTF, nombreTF, emailTF, apellidoTF, nacionalidadTF, documentoTF)) {
				return false;
			}
			if (!PresentacionUtils.validarSeleccionComboBox(this, comboTipoDoc,
					"Debe seleccionar un tipo de documento")) {
				return false;
			}
			if (fechaNacDC.getDate() == null) {
				PresentacionUtils.mostrarError(this, "Debe seleccionar una fecha de nacimiento");
				return false;
			}
		}
		if (!Arrays.equals(contraseniaTF.getPassword(), rptContraseniaTF.getPassword())) {
			PresentacionUtils.mostrarError(this, "Las contraseñas no coinciden");
			return false;
		}
		
		return true;
	}

	private void crearUsuario() {
		String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();

		String nick = nicknameTF.getText();
		String nombre = nombreTF.getText();
		String email = emailTF.getText();
		char[] contrasenia = contraseniaTF.getPassword();

		try {
			if ("Aerolinea".equals(tipoUsuario)) {
				String descripcion = descTA.getText();
				String sitioWeb = sitioWebTF.getText();
				iUsuario.ingresarAerolinea(nick, nombre, "", email, new String(contrasenia), descripcion, sitioWeb);
			} else if ("Cliente".equals(tipoUsuario)) {
				String apellido = apellidoTF.getText();
				LocalDate fechaNac = fechaNacDC.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String nacionalidad = nacionalidadTF.getText();
				String tipoDocText = (String) comboTipoDoc.getSelectedItem();
				EnumDoc tipoDoc = "CI".equals(tipoDocText) ? EnumDoc.CEDULA : EnumDoc.PASAPORTE;
				String documento = documentoTF.getText();
				iUsuario.ingresarCliente(nick, nombre, "", apellido, email, new String(contrasenia), fechaNac, nacionalidad,
						tipoDoc, documento);
			}
			PresentacionUtils.mostrarExito(this, "Usuario ingresado con éxito");
			limpiarCampos();
		} catch (Exception e) {
			PresentacionUtils.mostrarError(this, e.getMessage());
		}
	}

	public void prepararFrame() {
		// inicializarComponentes();

	}

	private void limpiarCampos() {
		PresentacionUtils.limpiarCamposTexto(nicknameTF, contraseniaTF, rptContraseniaTF, nombreTF, emailTF);
		comboTipoUsuario.setSelectedIndex(0);
		if (fechaNacDC != null) {
			fechaNacDC.setDate(null);
		}
		actualizarFormulario();
	}
}