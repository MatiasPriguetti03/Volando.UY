package presentacion;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.toedter.calendar.JDateChooser;

import utils.PresentacionUtils;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import logica.Fabrica;
import logica.IVuelo;
import logica.ManejadorVuelo;
import javax.swing.JTextArea;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AltaCiudad extends JInternalFrame{
	private JTextField NombreTF;
	private JTextField PaisTF;
	private JTextField AeropuertoTF;
	private JTextArea DescripcionTF;
	private JTextField SitioWebTF;
	private JDateChooser FechaAltaDate;
	private JScrollPane scrollPane;
	
	
	public AltaCiudad() {
		
		setTitle("Crear Ciudad");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0,0, 650, 260);
				
		getContentPane().setLayout(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 46, 0, 0, 78, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 46, 0, 60, 0, 0, 0, 31, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_2_1 = new JPanel();
		GridBagConstraints gbc_panel_2_1 = new GridBagConstraints();
		gbc_panel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2_1.fill = GridBagConstraints.BOTH;
		gbc_panel_2_1.gridx = 0;
		gbc_panel_2_1.gridy = 0;
		getContentPane().add(panel_2_1, gbc_panel_2_1);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		NombreTF = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		getContentPane().add(NombreTF, gbc_textField);
		NombreTF.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("País:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 1;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		PaisTF = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 4;
		gbc_textField_1.gridy = 1;
		getContentPane().add(PaisTF, gbc_textField_1);
		PaisTF.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Aeropuerto:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		AeropuertoTF = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 3;
		getContentPane().add(AeropuertoTF, gbc_textField_2);
		AeropuertoTF.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Descripción:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 3;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		// Crear el JTextArea
		DescripcionTF = new JTextArea();
		DescripcionTF.setLineWrap(true);
		DescripcionTF.setWrapStyleWord(true);

		// Envolver JTextArea en un JScrollPane
		JScrollPane scrollPane = new JScrollPane(DescripcionTF);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 4;
		gbc_scrollPane.gridy = 3;

		// Añadir JScrollPane (que contiene JTextArea) al layout
		getContentPane().add(scrollPane, gbc_scrollPane);


		

		
		
		JLabel lblNewLabel_4 = new JLabel("Sitio Web:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 5;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		SitioWebTF = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 5;
		getContentPane().add(SitioWebTF, gbc_textField_3);
		SitioWebTF.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Fecha de Alta:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 5;
		getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		FechaAltaDate = new JDateChooser();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 4;
		gbc_textField_5.gridy = 5;
		getContentPane().add(FechaAltaDate, gbc_textField_5);
		
		JButton btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 7;
		getContentPane().add(btnAceptar, gbc_btnNewButton);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 5;
		gbc_btnNewButton_1.gridy = 7;
		getContentPane().add(btnCancelar, gbc_btnNewButton_1);
		btnAceptar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (validarCampos()) {
		            crearCiudad();
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

	private void crearCiudad() {
		String name = NombreTF.getText();
		String country = PaisTF.getText();
		LocalDate date = FechaAltaDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String desc = DescripcionTF.getText();
		String web = SitioWebTF.getText();
		String airport = AeropuertoTF.getText();
		
		Fabrica fabrica = Fabrica.getInstance();
		IVuelo conVuelo = fabrica.getIVuelo();
		
		try {
			conVuelo.ingresarDatosCiudad(name, country, airport, desc, web, date);
            PresentacionUtils.mostrarExito(this, "Ciudad ingresada con éxito");
            limpiarCampos();
        } catch (Exception ex) {
            PresentacionUtils.mostrarError(this, "Error al ingresar ciudad: " + ex.getMessage());
        }
	}
	
	private boolean validarCampos() {
		String name = NombreTF.getText();
		String country = PaisTF.getText();
//		LocalDate date = FechaAltaDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		Fabrica fabrica = Fabrica.getInstance();
		ManejadorVuelo MV  = ManejadorVuelo.getInstancia();
		
		if (!PresentacionUtils.validarCamposRequeridos(this, "Todos los campos son obligatorios", 
				NombreTF, PaisTF, AeropuertoTF, DescripcionTF, SitioWebTF, FechaAltaDate)) {
            return false;
        }
		
		if (MV.existeCiudad(name, country)) {
        	PresentacionUtils.mostrarError(this, "Ya existe una ciudad con ese nombre y pais");
        	return false;
        }
		
		
		return true;
	}
	
	private void limpiarCampos() {
		PresentacionUtils.limpiarCamposTexto(NombreTF, PaisTF, AeropuertoTF, DescripcionTF, SitioWebTF);
		if (FechaAltaDate != null) {
			FechaAltaDate.setDate(null);
        }
	}
	
	public void prepararFrame() {
		
	}
	
}
