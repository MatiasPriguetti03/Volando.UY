package presentacion;

import logica.*;
import logica.datatypes.DTPaquete;
import logica.datatypes.DTRutaVueloPaquete;
import logica.datatypes.DTInfoPaquete;
import utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;


/**
 * JInternalFrame que permite consultar todos los paquetes registrados en el sistema.
 * 
 * @author FB
 *
 */
@SuppressWarnings("serial")
public class ConsultaPaquete extends JInternalFrame {
	
	private IPaquete controlPaq;
	private IUsuario controlUsr;
	
	private JDesktopPane deskPane;
	private ConsultaRutaVuelo consRV;
	
	private JPanel mainPanel;
	private JScrollPane listScrollPane;
    private JList<String> listaDePaquetes;
    private JPanel btnPanel;
    private JButton cancelarButton;
    private JLabel lblNombre;
    private JLabel lblDescripci;
    private JLabel lblValidez;
    private JLabel lblDescuento;
    private JLabel lblFechaAlta;
    private JTextField textFieldNombre;
    private JTextArea textAreaDescripcion;
    private JTextField textFieldValidez;
    private JTextField textFieldDescuento;
    private JPanel rightPanel;
    private JScrollPane tableRVScrollPane;
    private JLabel lblRutasDeVuelo;
    private JLabel lblPaquetes;
    private JTable tablaRutasVuelo;
    private JPanel infoPanel;
    private JPanel panel_aux;
    private JPanel panel_aux1;
    private JPanel tablePanel;
    private JTextField fieldFechaAlta;
    private JLabel lblCosto;
    private JTextField textFieldCosto;


    public ConsultaPaquete() {
    			
    	Fabrica fabrica = Fabrica.getInstance();
        controlPaq = fabrica.getIPaquete();
        controlUsr = fabrica.getIUsuario();
    	
        setTitle("Consulta de Paquete de Rutas de Vuelo");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(300, 0, 700, 550);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        listaDePaquetes = new JList<>();
        listScrollPane = new JScrollPane(listaDePaquetes);
        listScrollPane.setPreferredSize(new Dimension(200, 300));
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        
        lblPaquetes = new JLabel("  Paquetes");
        lblPaquetes.setPreferredSize(new Dimension(10, 30));
        lblPaquetes.setFont(UIManager.getFont("TableHeader.font"));
        listScrollPane.setColumnHeaderView(lblPaquetes);

        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        cancelarButton = new JButton("Cancelar");
//        consultarButton = new JButton("Consultar");
//        btnPanel.add(consultarButton);
        btnPanel.add(cancelarButton);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        infoPanel = new JPanel();
        GridBagLayout gbl_panelC = new GridBagLayout();
        gbl_panelC.columnWidths = new int[]{40, 145, 114, 21, 0};
        gbl_panelC.rowHeights = new int[]{0, 19, 80, 0, 0, 0, 0, 0, 0};
        gbl_panelC.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panelC.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        infoPanel.setLayout(gbl_panelC);
        
        panel_aux = new JPanel();
        GridBagConstraints gbc_panel_aux = new GridBagConstraints();
        gbc_panel_aux.insets = new Insets(0, 0, 5, 5);
        gbc_panel_aux.fill = GridBagConstraints.BOTH;
        gbc_panel_aux.gridx = 0;
        gbc_panel_aux.gridy = 0;
        infoPanel.add(panel_aux, gbc_panel_aux);
        
        lblNombre = new JLabel("Nombre");
        GridBagConstraints gbc_lblNombreDelPaquete = new GridBagConstraints();
        gbc_lblNombreDelPaquete.anchor = GridBagConstraints.EAST;
        gbc_lblNombreDelPaquete.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombreDelPaquete.gridx = 1;
        gbc_lblNombreDelPaquete.gridy = 1;
        infoPanel.add(lblNombre, gbc_lblNombreDelPaquete);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setEditable(false);
        GridBagConstraints gbc_textFieldNombrePaquete = new GridBagConstraints();
        gbc_textFieldNombrePaquete.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNombrePaquete.anchor = GridBagConstraints.NORTH;
        gbc_textFieldNombrePaquete.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombrePaquete.gridx = 2;
        gbc_textFieldNombrePaquete.gridy = 1;
        infoPanel.add(textFieldNombre, gbc_textFieldNombrePaquete);
        textFieldNombre.setColumns(10);
        
        lblDescripci = new JLabel("Descripción");
        GridBagConstraints gbc_lblDescripci = new GridBagConstraints();
        gbc_lblDescripci.anchor = GridBagConstraints.EAST;
        gbc_lblDescripci.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripci.gridx = 1;
        gbc_lblDescripci.gridy = 2;
        infoPanel.add(lblDescripci, gbc_lblDescripci);
        
        textAreaDescripcion = new JTextArea();
        textAreaDescripcion.setEditable(false);
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
     	gbc_scrollPane.gridy = 2;
     	
     	infoPanel.add(scrollPane, gbc_scrollPane);
        
        lblValidez = new JLabel("Validez en días");
        GridBagConstraints gbc_lblValidez = new GridBagConstraints();
        gbc_lblValidez.anchor = GridBagConstraints.EAST;
        gbc_lblValidez.insets = new Insets(0, 0, 5, 5);
        gbc_lblValidez.gridx = 1;
        gbc_lblValidez.gridy = 3;
        infoPanel.add(lblValidez, gbc_lblValidez);
        
        textFieldValidez = new JTextField();
        textFieldValidez.setEditable(false);
        GridBagConstraints gbc_textFieldValidez = new GridBagConstraints();
        gbc_textFieldValidez.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldValidez.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldValidez.gridx = 2;
        gbc_textFieldValidez.gridy = 3;
        infoPanel.add(textFieldValidez, gbc_textFieldValidez);
        textFieldValidez.setColumns(10);
        
        lblDescuento = new JLabel("Descuento %");
        GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
        gbc_lblDescuento.anchor = GridBagConstraints.EAST;
        gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescuento.gridx = 1;
        gbc_lblDescuento.gridy = 4;
        infoPanel.add(lblDescuento, gbc_lblDescuento);
        
        textFieldDescuento = new JTextField();
        textFieldDescuento.setEditable(false);
        GridBagConstraints gbc_textFieldDescuento = new GridBagConstraints();
        gbc_textFieldDescuento.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldDescuento.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldDescuento.gridx = 2;
        gbc_textFieldDescuento.gridy = 4;
        infoPanel.add(textFieldDescuento, gbc_textFieldDescuento);
        textFieldDescuento.setColumns(10);
        
        lblCosto = new JLabel("Costo");
        GridBagConstraints gbc_lblCosto = new GridBagConstraints();
        gbc_lblCosto.anchor = GridBagConstraints.EAST;
        gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
        gbc_lblCosto.gridx = 1;
        gbc_lblCosto.gridy = 5;
        infoPanel.add(lblCosto, gbc_lblCosto);
        
        textFieldCosto = new JTextField();
        textFieldCosto.setEditable(false);
        GridBagConstraints gbc_textFieldCosto = new GridBagConstraints();
        gbc_textFieldCosto.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldCosto.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldCosto.gridx = 2;
        gbc_textFieldCosto.gridy = 5;
        infoPanel.add(textFieldCosto, gbc_textFieldCosto);
        textFieldCosto.setColumns(10);
        
        lblFechaAlta = new JLabel("Fecha de Alta");
        GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
        gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
        gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
        gbc_lblFechaAlta.gridx = 1;
        gbc_lblFechaAlta.gridy = 6;
        infoPanel.add(lblFechaAlta, gbc_lblFechaAlta);

        rightPanel.add(infoPanel, BorderLayout.NORTH);
        
        fieldFechaAlta = new JTextField();
        fieldFechaAlta.setEditable(false);
        GridBagConstraints gbc_fieldFechaAlta = new GridBagConstraints();
        gbc_fieldFechaAlta.insets = new Insets(0, 0, 5, 5);
        gbc_fieldFechaAlta.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldFechaAlta.gridx = 2;
        gbc_fieldFechaAlta.gridy = 6;
        infoPanel.add(fieldFechaAlta, gbc_fieldFechaAlta);
        fieldFechaAlta.setColumns(10);
        
        panel_aux1 = new JPanel();
        GridBagConstraints gbc_panel_aux1 = new GridBagConstraints();
        gbc_panel_aux1.fill = GridBagConstraints.BOTH;
        gbc_panel_aux1.gridx = 3;
        gbc_panel_aux1.gridy = 7;
        infoPanel.add(panel_aux1, gbc_panel_aux1);
             
        tablePanel = new JPanel();
        rightPanel.add(tablePanel, BorderLayout.CENTER);
        
        String[] columnas = {"Nombre", "Cantidad", "Tipo de Asiento"};
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
                        
        lblRutasDeVuelo = new JLabel("  Rutas de vuelo incluidas");
        tablePanel.add(lblRutasDeVuelo, BorderLayout.NORTH);
        lblRutasDeVuelo.setBackground(UIManager.getColor("Button.background"));
        lblRutasDeVuelo.setPreferredSize(new Dimension(10, 30));
        lblRutasDeVuelo.setFont(UIManager.getFont("TableHeader.font"));
        
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                limpiarFormulario();
                setVisible(false);
            }
        });

        listaDePaquetes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarInfoDePaquetes();
                }
            }
        });
        
        tablaRutasVuelo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
            	mostrarInfoRutaVuelo();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                setVisible(false);
            }
        });
    }

    public void rellenarListaDePaquetes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        
        List<DTInfoPaquete> paquetes = controlPaq.listarPaquetes();
        for (DTInfoPaquete paquete : paquetes) {
            model.addElement(paquete.getNombre());
        }
        listaDePaquetes.setModel(model);
    }

    private void mostrarInfoDePaquetes() {
    	String paqueteSeleccionado = listaDePaquetes.getSelectedValue();
        if (paqueteSeleccionado != null) {
        	try {
        		DTPaquete paquete = controlPaq.obtenerDatosPaquete(paqueteSeleccionado);
        		textFieldNombre.setText(paquete.getNombre());
        		textAreaDescripcion.setText(paquete.getDescripcion());
        		textFieldValidez.setText(paquete.getPeriodoValidez().toString());
        		textFieldDescuento.setText(paquete.getDescuento().toString());
        		textFieldCosto.setText(paquete.getCosto().toString());
        		fieldFechaAlta.setText(paquete.getFechaAlta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        		DefaultTableModel model = (DefaultTableModel) tablaRutasVuelo.getModel();
        		// Limpiar filas existentes
	      		while (model.getRowCount() > 0) {
	      			model.removeRow(0);
	      		}
	      		for (DTRutaVueloPaquete rutaVuelo : paquete.getRutasVuelo()) {
	      			model.addRow(new Object[]{rutaVuelo.getNombre(),rutaVuelo.getCantidad(), rutaVuelo.getTipoAsiento()});
	      		}
	      		
        	} catch (Exception  e) {
        		PresentacionUtils.mostrarError(this, e.getMessage());
        	}
        } 
    }
    
    private void mostrarInfoRutaVuelo() {
    	if (tablaRutasVuelo.getSelectedRow() != -1) {
            int selectedRow = tablaRutasVuelo.getSelectedRow();
            String nombreRutaVuelo = (String) tablaRutasVuelo.getValueAt(selectedRow, 0);
            try {
	    		String aerolinea = controlUsr.obtenerNickAerolineaDeRutaVuelo(nombreRutaVuelo);
	    		consRV.prepararFrame();
	    		consRV.listarInfoRutaVueloPaquete(aerolinea, nombreRutaVuelo);
	    	} catch (Exception e) {
	    		PresentacionUtils.mostrarError(this, e.getMessage());
	    	}
        }
    }
    
    private void limpiarFormulario() {
    	PresentacionUtils.limpiarCamposTexto(textFieldNombre, textAreaDescripcion, textFieldValidez, textFieldDescuento, textFieldCosto, fieldFechaAlta);
    	PresentacionUtils.limpiarTablasyListas(tablaRutasVuelo);
    }
    
	public void consultaPaquete(String paquete) {
		rellenarListaDePaquetes();
		listaDePaquetes.setSelectedValue(paquete, true);
	}
	
	public void prepararFrame() {
		rellenarListaDePaquetes();
		consRV = Principal.conRutInternalFrame;
    	}
}
