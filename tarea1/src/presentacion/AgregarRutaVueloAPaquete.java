package presentacion;

import logica.Fabrica;
import logica.IPaquete;
import logica.IUsuario;
import logica.datatypes.DTAerolinea;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTRutaVueloPaquete;
import logica.datatypes.DTInfoPaquete;
import logica.datatypes.DTRutaVuelo;
import logica.enums.EnumAsiento;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import excepciones.EsConjuntoVacioException;
import excepciones.NoExisteException;
import excepciones.YaRegistradoException;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import utils.LineWrapCellRenderer;
import utils.ColoredRowsTableCellRenderer;
import utils.PresentacionUtils;

/**
 * JInternalFrame que permite registrar un nuevo paquete al sistema.
 * 
 * @author FB
 *
 */
@SuppressWarnings("serial")
public class AgregarRutaVueloAPaquete extends JInternalFrame {

    private IPaquete controlPaq;
    private IUsuario controlUsr;
    
    // Los componentes gráficos se agregan como atributos de la clase
    // para facilitar su acceso desde diferentes métodos de la misma.

    private JPanel btnPanel;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JPanel mainPanel;
    private JScrollPane listScrollPane;
    private JPanel selectAerolineaPanel;
    private JList<String> listaDePaquetes;
    private JLabel lblPaquetesEditables;
    private JLabel lblSeleccioneUnaAerolinea;
    private JComboBox<String> aerolineaComboBox;
    private JPanel rightPanel;
    private JScrollPane tableRVScrollPane;
    private JTable tablaRutasVuelo;
    private JPanel tablePanel;
    private JLabel lblRutasDeVuelo;
    private JPanel otherOptionsPanel;
    private JLabel lblTipoDeAsiento;
    private JTextField cantidadTextField;
    private JLabel lblCantidad;
    private JComboBox<String> tipoAsientoComboBox;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_8;
    private JPanel panel_9;
    private JPanel panel;
    private JLabel lblIndicaQue;

    /**
     * Create the frame.
     */
    public AgregarRutaVueloAPaquete() {

    	Fabrica fabrica = Fabrica.getInstance();
        controlPaq = fabrica.getIPaquete();
        controlUsr = fabrica.getIUsuario();

        setTitle("Agregar Ruta de Vuelo a Paquete");
        setResizable(true);
        setClosable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(0,0,880,480);
        
        getContentPane().setLayout(new BorderLayout(0, 0));
        btnPanel = new JPanel();
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        GridBagLayout gbl_btnPanel = new GridBagLayout();
        gbl_btnPanel.columnWidths = new int[]{0, 94, 124, 0};
        gbl_btnPanel.rowHeights = new int[]{40, 0};
        gbl_btnPanel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_btnPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        btnPanel.setLayout(gbl_btnPanel);
        
        btnAceptar = new JButton("Aceptar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 0;
        btnPanel.add(btnAceptar, gbc_btnAceptar);
        
        btnCancelar = new JButton("Cancelar");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.gridx = 2;
        gbc_btnCancelar.gridy = 0;
        btnPanel.add(btnCancelar, gbc_btnCancelar);
        
        mainPanel = new JPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(0, 0));
        
        listaDePaquetes = new JList<>();
        listScrollPane = new JScrollPane(listaDePaquetes);
        listScrollPane.setPreferredSize(new Dimension(300, 300));
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        
        lblPaquetesEditables = new JLabel("  Paquetes editables");
        lblPaquetesEditables.setBackground(UIManager.getColor("Button.background"));
        lblPaquetesEditables.setPreferredSize(new Dimension(10, 30));
        lblPaquetesEditables.setFont(UIManager.getFont("TableHeader.font"));
        listScrollPane.setColumnHeaderView(lblPaquetesEditables);
        
        rightPanel = new JPanel();
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        rightPanel.setLayout(new BorderLayout(0, 0));
        
        selectAerolineaPanel = new JPanel();
        rightPanel.add(selectAerolineaPanel, BorderLayout.NORTH);
        GridBagLayout gbl_selectAerolineaPanel = new GridBagLayout();
        gbl_selectAerolineaPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
        gbl_selectAerolineaPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_selectAerolineaPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_selectAerolineaPanel.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        selectAerolineaPanel.setLayout(gbl_selectAerolineaPanel);
        
        panel_6 = new JPanel();
        GridBagConstraints gbc_panel_6 = new GridBagConstraints();
        gbc_panel_6.insets = new Insets(0, 0, 5, 5);
        gbc_panel_6.fill = GridBagConstraints.BOTH;
        gbc_panel_6.gridx = 0;
        gbc_panel_6.gridy = 1;
        selectAerolineaPanel.add(panel_6, gbc_panel_6);
        
        lblSeleccioneUnaAerolinea = new JLabel("Seleccionar una aerolinea");
        GridBagConstraints gbc_lblSeleccioneUnaAerolinea = new GridBagConstraints();
        gbc_lblSeleccioneUnaAerolinea.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeleccioneUnaAerolinea.gridx = 1;
        gbc_lblSeleccioneUnaAerolinea.gridy = 3;
        selectAerolineaPanel.add(lblSeleccioneUnaAerolinea, gbc_lblSeleccioneUnaAerolinea);
        
        aerolineaComboBox = new JComboBox<String>();
        aerolineaComboBox.setEnabled(false);
        GridBagConstraints gbc_aerolineaComboBox = new GridBagConstraints();
        gbc_aerolineaComboBox.insets = new Insets(0, 0, 5, 5);
        gbc_aerolineaComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_aerolineaComboBox.gridx = 3;
        gbc_aerolineaComboBox.gridy = 3;
        selectAerolineaPanel.add(aerolineaComboBox, gbc_aerolineaComboBox);
        
        panel_4 = new JPanel();
        GridBagConstraints gbc_panel_4 = new GridBagConstraints();
        gbc_panel_4.insets = new Insets(0, 0, 5, 0);
        gbc_panel_4.fill = GridBagConstraints.BOTH;
        gbc_panel_4.gridx = 4;
        gbc_panel_4.gridy = 4;
        selectAerolineaPanel.add(panel_4, gbc_panel_4);
//        tablePanel = new JPanel();
//        rightPanel.add(tablePanel, BorderLayout.CENTER);
//        tablePanel.setLayout(new BorderLayout(0, 0));
        
        tablePanel = new JPanel();
        rightPanel.add(tablePanel, BorderLayout.CENTER);
        
        String[] columnas = {"Nombre", "Origen", "Destino"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnas) {
        	@Override
        	public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        };
        
        tablaRutasVuelo = new JTable(model);
        tablaRutasVuelo.setDefaultEditor(Object.class, null);
        tablaRutasVuelo.setDefaultRenderer(Object.class, new LineWrapCellRenderer());
        tablePanel.setLayout(new BorderLayout(0, 0));
        tableRVScrollPane = new JScrollPane(tablaRutasVuelo);
        tablePanel.add(tableRVScrollPane, BorderLayout.CENTER);

        TableColumn columnaNombre = tablaRutasVuelo.getColumnModel().getColumn(0);
        columnaNombre.setPreferredWidth(100);
        columnaNombre.setMaxWidth(100);
        columnaNombre.setMinWidth(120);
                        
        lblRutasDeVuelo = new JLabel("  Rutas de vuelo incluidas");
        tablePanel.add(lblRutasDeVuelo, BorderLayout.NORTH);
        lblRutasDeVuelo.setBackground(UIManager.getColor("Button.background"));
        lblRutasDeVuelo.setPreferredSize(new Dimension(10, 30));
        lblRutasDeVuelo.setFont(UIManager.getFont("TableHeader.font"));
        
        panel = new JPanel();
        tablePanel.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        lblIndicaQue = new JLabel("color indica que Ruta de Vuelo ya pertenece a Paquete");
        lblIndicaQue.setVerticalAlignment(SwingConstants.BOTTOM);
        lblIndicaQue.setForeground(Color.DARK_GRAY);
        lblIndicaQue.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIndicaQue.setFont(new Font("Dialog", Font.PLAIN, 10));
        panel.add(lblIndicaQue);
        
        otherOptionsPanel = new JPanel();
        rightPanel.add(otherOptionsPanel, BorderLayout.SOUTH);
        GridBagLayout gbl_otherOptionsPanel = new GridBagLayout();
        gbl_otherOptionsPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
        gbl_otherOptionsPanel.rowHeights = new int[]{18, 0, 0, 0, 0};
        gbl_otherOptionsPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_otherOptionsPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        otherOptionsPanel.setLayout(gbl_otherOptionsPanel);
        
        panel_7 = new JPanel();
        GridBagConstraints gbc_panel_7 = new GridBagConstraints();
        gbc_panel_7.insets = new Insets(0, 0, 5, 5);
        gbc_panel_7.fill = GridBagConstraints.BOTH;
        gbc_panel_7.gridx = 0;
        gbc_panel_7.gridy = 0;
        otherOptionsPanel.add(panel_7, gbc_panel_7);
        
        lblCantidad = new JLabel("Cantidad a incluir");
        GridBagConstraints gbc_lblCantidad = new GridBagConstraints();
        gbc_lblCantidad.anchor = GridBagConstraints.WEST;
        gbc_lblCantidad.insets = new Insets(0, 0, 5, 5);
        gbc_lblCantidad.gridx = 1;
        gbc_lblCantidad.gridy = 1;
        otherOptionsPanel.add(lblCantidad, gbc_lblCantidad);
        
        cantidadTextField = new JTextField();
        cantidadTextField.setEnabled(false);
        GridBagConstraints gbc_cantidadTextField = new GridBagConstraints();
        gbc_cantidadTextField.insets = new Insets(0, 0, 5, 5);
        gbc_cantidadTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_cantidadTextField.gridx = 3;
        gbc_cantidadTextField.gridy = 1;
        otherOptionsPanel.add(cantidadTextField, gbc_cantidadTextField);
        cantidadTextField.setColumns(10);
        
        lblTipoDeAsiento = new JLabel("Tipo de Asiento");
        GridBagConstraints gbc_lblTipoDeAsiento = new GridBagConstraints();
        gbc_lblTipoDeAsiento.anchor = GridBagConstraints.WEST;
        gbc_lblTipoDeAsiento.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipoDeAsiento.gridx = 1;
        gbc_lblTipoDeAsiento.gridy = 2;
        otherOptionsPanel.add(lblTipoDeAsiento, gbc_lblTipoDeAsiento);
        
        tipoAsientoComboBox = new JComboBox<String>();
        tipoAsientoComboBox.setEnabled(false);
        GridBagConstraints gbc_tipoAsientoComboBox = new GridBagConstraints();
        gbc_tipoAsientoComboBox.insets = new Insets(0, 0, 5, 5);
        gbc_tipoAsientoComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipoAsientoComboBox.gridx = 3;
        gbc_tipoAsientoComboBox.gridy = 2;
        otherOptionsPanel.add(tipoAsientoComboBox, gbc_tipoAsientoComboBox);
        
        panel_5 = new JPanel();
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.fill = GridBagConstraints.BOTH;
        gbc_panel_5.gridx = 4;
        gbc_panel_5.gridy = 3;
        otherOptionsPanel.add(panel_5, gbc_panel_5);
        
        panel_9 = new JPanel();
        rightPanel.add(panel_9, BorderLayout.EAST);
        
        panel_8 = new JPanel();
        rightPanel.add(panel_8, BorderLayout.WEST);
        
        //Eventos
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                limpiarFormulario();
                setVisible(false);
            }
        });
        
        listaDePaquetes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                	limpiarFormulario();
                    aerolineaComboBox.setEnabled(true);
                    rellenarComboBoxAerolineas();
                }
            }
        });
        
        aerolineaComboBox.addActionListener( new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                rellenarListaDeRutasVuelo();
            }
        });
        
        tablaRutasVuelo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
            	cantidadTextField.setEnabled(true);
            	rellenarComboBoxTipoAsiento();
            	tipoAsientoComboBox.setEnabled(true);
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                limpiarFormulario();
                setVisible(false);
            }
        });
        
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	cmdAgregarRutaDeVuelo();
            }
        });

    }
    
    private void cmdAgregarRutaDeVuelo() {
        if (checkFormulario()) {
        	String paquete = (String) listaDePaquetes.getSelectedValue();
        	int selectedRow = tablaRutasVuelo.getSelectedRow();
        	String nombreRutaVuelo = (String) tablaRutasVuelo.getValueAt(selectedRow, 0);
        	String cantidad = cantidadTextField.getText();
        	EnumAsiento tipoAsiento;
        	if ((String) tipoAsientoComboBox.getSelectedItem() == "Turista")
        		tipoAsiento = EnumAsiento.TURISTA;
        	else
        		tipoAsiento = EnumAsiento.EJECUTIVO;
            try {
                controlPaq.agregarRutaVuelo(paquete, nombreRutaVuelo, tipoAsiento, Integer.parseInt(cantidad));;
                // Muestro éxito de la operación
                PresentacionUtils.mostrarExito(this, "La Ruta de Vuelo fue agregada al Paquete");
                // Limpio el internal frame antes de cerrar la ventana
                limpiarFormulario();
                setVisible(false);
                
            } catch (NoExisteException e) {
            	PresentacionUtils.mostrarError(this, e.getMessage());
            	
            } catch (YaRegistradoException e) {
            	PresentacionUtils.mostrarError(this, e.getMessage());
            }
        }
    }

    // Permite validar la información introducida en los campos
    private boolean checkFormulario() {
        if (!PresentacionUtils.validarCamposRequeridos(this, "No pueden haber campos vacíos", 
        		listaDePaquetes, tablaRutasVuelo, cantidadTextField)) {
            return false;
        }
        if (!PresentacionUtils.validarSeleccionComboBox(this, aerolineaComboBox, "No pueden haber campos sin seleccionar")) {
            return false;
        }
        if (!PresentacionUtils.validarSeleccionComboBox(this, tipoAsientoComboBox, "No pueden haber campos sin seleccionar")) {
            return false;
        }
        //Control dias entero
        String cantidad = cantidadTextField.getText();
        try {
            Integer cant = Integer.parseInt(cantidad);
            if (cant <= 0) {
            	throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
        	PresentacionUtils.mostrarError(this, "La cantidad debe ser un entero mayor a cero");
            return false;
        }
        return true;
    }
    
    public void rellenarListaDePaquetes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        // Implementar Listar Paquetes sin compras 
        /////
        List<DTInfoPaquete> paquetes = controlPaq.listarPaquetesNoComprados();
        for (DTInfoPaquete paquete : paquetes) {
        	String element = paquete.getNombre();
            model.addElement(element);
        }
        listaDePaquetes.setModel(model);
    }
    
    public void rellenarComboBoxAerolineas() {
    	try {
	        String[] aerolineas = controlUsr.listarAerolineas();
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
	        aerolineaComboBox.setModel(model);
    	} catch(EsConjuntoVacioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Atencion", JOptionPane.WARNING_MESSAGE);
    	}
    }
    
	public void rellenarComboBoxTipoAsiento() {
		String[] opciones = {"", "Turista", "Ejecutivo"};
		DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(opciones);
        tipoAsientoComboBox.setModel(model);
	}
    
    public void rellenarListaDeRutasVuelo() {
    	// Primero limpio todo
    	PresentacionUtils.limpiarTablasyListas(tablaRutasVuelo);
    	
    	DefaultTableModel model = (DefaultTableModel) tablaRutasVuelo.getModel();
        String aerolineaSeleccionada = (String) aerolineaComboBox.getSelectedItem();
        if (!(aerolineaSeleccionada == null || aerolineaSeleccionada == "")) {
	        try {
	        	String paqueteSeleccionado = (String)listaDePaquetes.getSelectedValue();
	        	DTPaquete paquete = controlPaq.obtenerDatosPaquete(paqueteSeleccionado);
	        	
		        DTAerolinea aerolinea = controlUsr.obtenerDatosAerolinea(aerolineaSeleccionada);
		        Vector<String> nombresRutasPaquete = new Vector<String>();
		        for (DTRutaVueloPaquete rv : paquete.getRutasVuelo()) {
		        	nombresRutasPaquete.add(rv.getNombre());
		        }
		        Integer contador = 0;
		        Vector<Integer> filasRutasCompradas = new Vector<Integer>();
		        for (DTRutaVuelo rutaVuelo : aerolinea.getRutasVuelo()) {
		        	model.addRow(new Object[]{rutaVuelo.getName(),rutaVuelo.getOrigen(), rutaVuelo.getDestino()});
		        	if(nombresRutasPaquete.contains(rutaVuelo.getName())) {
		        		filasRutasCompradas.add(contador);
		        	}
		        	contador ++;
		        }
		        // Renderizador para cambiar el color de las filas de rutas ya incluidas en el paquete
		        tablaRutasVuelo.setDefaultRenderer(Object.class, new ColoredRowsTableCellRenderer(filasRutasCompradas, Color.getHSBColor(46, 49, 98)));
		        
	        } catch (Exception  e) {
	    		PresentacionUtils.mostrarError(this, e.getMessage());
	    	}
        }
    }

    // Permite borrar el contenido de un formulario antes de cerrarlo.
    private void limpiarFormulario() {
    	PresentacionUtils.limpiarComboBoxes(aerolineaComboBox, tipoAsientoComboBox);
    	PresentacionUtils.limpiarTablasyListas(tablaRutasVuelo);
    	PresentacionUtils.limpiarCamposTexto(cantidadTextField);
    	aerolineaComboBox.setEnabled(false);
    	tipoAsientoComboBox.setEnabled(false);
    	cantidadTextField.setEnabled(false);
    }
    
	public void prepararFrame() {
    	rellenarListaDePaquetes();
	}
}
