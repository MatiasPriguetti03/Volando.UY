package presentacion;

import logica.Fabrica;
import logica.IPaquete;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import java.awt.*;
import java.awt.event.*;
import java.time.ZoneId;
import java.util.Date;

import utils.PresentacionUtils;

/**
 * JInternalFrame que permite registrar un nuevo paquete al sistema.
 * 
 * @author FB
 *
 */
@SuppressWarnings("serial")
public class CrearPaquete extends JInternalFrame {

    private IPaquete controlPaq;
    
    // Los componentes gráficos se agregan como atributos de la clase
    // para facilitar su acceso desde diferentes métodos de la misma.
    private JPanel panelN;
    private JPanel panelC;
    private JPanel panelS;
    private JLabel label;
    private JLabel lblIngreseLosDatos ;
    private JLabel lblNombreDelPaquete;
    private JLabel lblDescripci;
    private JLabel lblValidez;
    private JLabel lblDescuento;
    private JLabel lblFechaAlta;
    private JTextField textFieldNombrePaquete;
    private JTextArea textAreaDescripcion;
    private JTextField textFieldValidez;
    private JTextField textFieldDescuento;
    private JDateChooser fieldFechaAlta;
    private JButton btnAceptar;
    private JButton btnCancelar;

    /**
     * Create the frame.
     */
    public CrearPaquete() {
    	
    	Fabrica fabrica = Fabrica.getInstance();
        controlPaq = fabrica.getIPaquete();

        // Propiedades del JInternalFrame como dimensión, posición dentro del frame,
        // etc.
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Crear Paquete de Rutas de Vuelo");
        setBounds(0, 0, 550, 300);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        panelN = new JPanel();
        getContentPane().add(panelN, BorderLayout.NORTH);
        GridBagLayout gbl_panelN = new GridBagLayout();
        gbl_panelN.columnWidths = new int[]{30, 271, 0};
        gbl_panelN.rowHeights = new int[]{35, 0};
        gbl_panelN.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panelN.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelN.setLayout(gbl_panelN);
        
        label = new JLabel("");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.insets = new Insets(0, 0, 0, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        panelN.add(label, gbc_label);
        
        lblIngreseLosDatos = new JLabel("Ingrese los datos del nuevo paquete");
        lblIngreseLosDatos.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblIngreseLosDatos = new GridBagConstraints();
        gbc_lblIngreseLosDatos.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseLosDatos.gridx = 1;
        gbc_lblIngreseLosDatos.gridy = 0;
        panelN.add(lblIngreseLosDatos, gbc_lblIngreseLosDatos);
        
        panelC = new JPanel();
        getContentPane().add(panelC, BorderLayout.CENTER);
        GridBagLayout gbl_panelC = new GridBagLayout();
        gbl_panelC.columnWidths = new int[]{40, 145, 114, 21, 0};
        gbl_panelC.rowHeights = new int[]{19, 60, 0, 0, 0, 0};
        gbl_panelC.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panelC.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelC.setLayout(gbl_panelC);
        
        lblNombreDelPaquete = new JLabel("Nombre del paquete");
        GridBagConstraints gbc_lblNombreDelPaquete = new GridBagConstraints();
        gbc_lblNombreDelPaquete.anchor = GridBagConstraints.EAST;
        gbc_lblNombreDelPaquete.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombreDelPaquete.gridx = 1;
        gbc_lblNombreDelPaquete.gridy = 0;
        panelC.add(lblNombreDelPaquete, gbc_lblNombreDelPaquete);
        
        textFieldNombrePaquete = new JTextField();
        GridBagConstraints gbc_textFieldNombrePaquete = new GridBagConstraints();
        gbc_textFieldNombrePaquete.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNombrePaquete.anchor = GridBagConstraints.NORTH;
        gbc_textFieldNombrePaquete.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombrePaquete.gridx = 2;
        gbc_textFieldNombrePaquete.gridy = 0;
        panelC.add(textFieldNombrePaquete, gbc_textFieldNombrePaquete);
        textFieldNombrePaquete.setColumns(10);
        
        lblDescripci = new JLabel("Descripción");
        GridBagConstraints gbc_lblDescripci = new GridBagConstraints();
        gbc_lblDescripci.anchor = GridBagConstraints.EAST;
        gbc_lblDescripci.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripci.gridx = 1;
        gbc_lblDescripci.gridy = 1;
        panelC.add(lblDescripci, gbc_lblDescripci);
        
        textAreaDescripcion = new JTextArea();
        GridBagConstraints gbc_textAreaDescripcion = new GridBagConstraints();
        gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
        gbc_textAreaDescripcion.gridx = 2;
        gbc_textAreaDescripcion.gridy = 1;

        textAreaDescripcion.setColumns(10);
        textAreaDescripcion.setLineWrap(true);
        textAreaDescripcion.setWrapStyleWord(true);
        
     // Envolver JTextArea en un JScrollPane
     	JScrollPane scrollPane = new JScrollPane(textAreaDescripcion);
     	GridBagConstraints gbc_scrollPane = new GridBagConstraints();
     	gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
     	gbc_scrollPane.fill = GridBagConstraints.BOTH;
     	gbc_scrollPane.gridx = 2;
     	gbc_scrollPane.gridy = 1;
     	
     	panelC.add(scrollPane, gbc_scrollPane);
        
        lblValidez = new JLabel("Validez en días");
        GridBagConstraints gbc_lblValidez = new GridBagConstraints();
        gbc_lblValidez.anchor = GridBagConstraints.EAST;
        gbc_lblValidez.insets = new Insets(0, 0, 5, 5);
        gbc_lblValidez.gridx = 1;
        gbc_lblValidez.gridy = 2;
        panelC.add(lblValidez, gbc_lblValidez);
        
        textFieldValidez = new JTextField();
        GridBagConstraints gbc_textFieldValidez = new GridBagConstraints();
        gbc_textFieldValidez.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldValidez.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldValidez.gridx = 2;
        gbc_textFieldValidez.gridy = 2;
        panelC.add(textFieldValidez, gbc_textFieldValidez);
        textFieldValidez.setColumns(10);
        
        lblDescuento = new JLabel("Descuento %");
        GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
        gbc_lblDescuento.anchor = GridBagConstraints.EAST;
        gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescuento.gridx = 1;
        gbc_lblDescuento.gridy = 3;
        panelC.add(lblDescuento, gbc_lblDescuento);
        
        textFieldDescuento = new JTextField();
        GridBagConstraints gbc_textFieldDescuento = new GridBagConstraints();
        gbc_textFieldDescuento.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldDescuento.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldDescuento.gridx = 2;
        gbc_textFieldDescuento.gridy = 3;
        panelC.add(textFieldDescuento, gbc_textFieldDescuento);
        textFieldDescuento.setColumns(10);
        
        lblFechaAlta = new JLabel("Fecha de Alta");
        GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
        gbc_lblFechaAlta.insets = new Insets(0, 0, 0, 5);
        gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
        gbc_lblFechaAlta.gridx = 1;
        gbc_lblFechaAlta.gridy = 4;
        panelC.add(lblFechaAlta, gbc_lblFechaAlta);
        
        
        fieldFechaAlta = new JDateChooser();
        GridBagConstraints gbc_fieldFechaAlta = new GridBagConstraints();
        gbc_fieldFechaAlta.insets = new Insets(0, 0, 0, 5);
        gbc_fieldFechaAlta.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldFechaAlta.gridx = 2;
        gbc_fieldFechaAlta.gridy = 4;
        //fieldFechaAlta.setDateFormatString("dd/MM/yyyy");
        panelC.add(fieldFechaAlta, gbc_fieldFechaAlta);
        
        panelS = new JPanel();
        getContentPane().add(panelS, BorderLayout.SOUTH);
        GridBagLayout gbl_panelS = new GridBagLayout();
        gbl_panelS.columnWidths = new int[]{0, 94, 124, 0};
        gbl_panelS.rowHeights = new int[]{40, 0};
        gbl_panelS.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panelS.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelS.setLayout(gbl_panelS);
        
        btnAceptar = new JButton("Aceptar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 0;
        panelS.add(btnAceptar, gbc_btnAceptar);
        //Evento
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                limpiarCampos();
                setVisible(false);
            }
        });
        
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdCrearPaqueteActionPerformed(arg0);
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.gridx = 2;
        gbc_btnCancelar.gridy = 0;
        panelS.add(btnCancelar, gbc_btnCancelar);
        //Evento
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                setVisible(false);
            }
        });

    }
    
    protected void cmdCrearPaqueteActionPerformed(ActionEvent arg0) {

        // Obtengo datos de los controles Swing
    	String nombre = textFieldNombrePaquete.getText();
    	String descripcion = textAreaDescripcion.getText();
    	String validez = textFieldValidez.getText();
    	String descuento = textFieldDescuento.getText();
    	Date fechaAlta = fieldFechaAlta.getDate();
    	//PresentacionUtils.mostrarExito(this, fechaAlta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        if (checkFormulario()) {
            try {
                controlPaq.crearPaquete(nombre,  "", descripcion, Integer.parseInt(validez), Integer.parseInt(descuento), fechaAlta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                // Muestro éxito de la operación
                PresentacionUtils.mostrarExito(this, "El Paquete se ha creado con éxito");
                setVisible(false);
            
            } catch (Exception e) {
            	PresentacionUtils.mostrarError(this, e.getMessage());
            }

            // Limpio el internal frame antes de cerrar la ventana
            limpiarCampos();
        }
    }

    // Permite validar la información introducida en los campos
    private boolean checkFormulario() {
        if (!PresentacionUtils.validarCamposRequeridos(this, "No pueden haber campos vacíos", 
        		textFieldNombrePaquete, textAreaDescripcion, textFieldValidez, textFieldDescuento)) {
            return false;
        }
        //Control fecha
        if (!PresentacionUtils.validarCamposRequeridos(this, "Debe ingresar una fecha en formato: dd/MM/yyyy", 
        		fieldFechaAlta)) {
            return false;
        }
        //Control dias entero
        String validez = textFieldValidez.getText();
        try {
            Integer val = Integer.parseInt(validez);
            if (val < 0 ) {
            	throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "La validez debe ser un numero positivo de días");
            return false;
        }
        //Control descuento entre 0 y 100
        String descuento = textFieldDescuento.getText();
        try {
            Integer descu = Integer.parseInt(descuento);
            if (descu < 0 || descu > 100) {
            	throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "El descuento debe ser un numero entre 0 y 100");
            return false;
        }
        
        return true;
    }

    // Permite borrar el contenido de un formulario antes de cerrarlo.
    private void limpiarCampos() {
    	PresentacionUtils.limpiarCamposTexto(textFieldNombrePaquete, textAreaDescripcion, textFieldValidez,
    			textFieldDescuento, fieldFechaAlta);
    }
    
	public void prepararFrame() {
		
	}
}
