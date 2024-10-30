package presentacion;

import logica.Fabrica;
import logica.IPaquete;
import logica.IUsuario;
import logica.datatypes.DTInfoPaquete;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import utils.ColoredElementsListCellRenderer;
import utils.ColoredRowsTableCellRenderer;
import utils.PresentacionUtils;

/**
 * JInternalFrame que permite registrar una nueva compra de paquete en el sistema.
 * 
 * @author FB
 *
 */
@SuppressWarnings("serial")
public class ComprarPaquete extends JInternalFrame {

    private IPaquete controlPaq;
    private IUsuario controlUsr;
    
    // Los componentes gráficos se agregan como atributos de la clase
    // para facilitar su acceso desde diferentes métodos de la misma.

    private JPanel btnPanel;
    private JButton btnComprar;
    private JButton btnCancelar;
    private JPanel mainPanel;
    private JScrollPane listScrollPane;
    private JList<String> listaDePaquetes;
    private JLabel lblPaquetesEditables;
    private JPanel rightPanel;
    private JScrollPane listClientsScrollPane;
    private JPanel listClientsPanel;
    private JList<String> listaClientes;
    private JLabel lblRutasDeVuelo;
    private JPanel panel_8;
    private JPanel panel_9;
    private JPanel panel;
    private JLabel lblIndicaQue;
    private JPanel topPanel;
    private JLabel lblSeleccioneUnPaquete;
    private JPanel fechaCompraPanel;
    private JLabel lblFechaDeCompra;
    private JDateChooser fieldFechaCompra;
    private JPanel panel_1;
    private JPanel panel_2;

    /**
     * Create the frame.
     */
    public ComprarPaquete() {

    	Fabrica fabrica = Fabrica.getInstance();
        controlPaq = fabrica.getIPaquete();
        controlUsr = fabrica.getIUsuario();

        setTitle("Comprar Paquete");
        setResizable(true);
        setClosable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(0,0,620,480);
        
        getContentPane().setLayout(new BorderLayout(0, 0));
        btnPanel = new JPanel();
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        GridBagLayout gbl_btnPanel = new GridBagLayout();
        gbl_btnPanel.columnWidths = new int[]{0, 94, 124, 0};
        gbl_btnPanel.rowHeights = new int[]{40, 0};
        gbl_btnPanel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_btnPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        btnPanel.setLayout(gbl_btnPanel);
        
        btnComprar = new JButton("Comprar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 0;
        btnPanel.add(btnComprar, gbc_btnAceptar);
        
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
        
        lblPaquetesEditables = new JLabel("  Paquetes con Rutas de Vuelo");
        lblPaquetesEditables.setBackground(UIManager.getColor("Button.background"));
        lblPaquetesEditables.setPreferredSize(new Dimension(10, 30));
        lblPaquetesEditables.setFont(UIManager.getFont("TableHeader.font"));
        listScrollPane.setColumnHeaderView(lblPaquetesEditables);
        
        rightPanel = new JPanel();
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        rightPanel.setLayout(new BorderLayout(0, 0));
        listClientsPanel = new JPanel();
        rightPanel.add(listClientsPanel, BorderLayout.CENTER);
        listClientsPanel.setLayout(new BorderLayout(0, 0));
        
        listaClientes = new JList<>();
        listClientsScrollPane = new JScrollPane(listaClientes);
        listClientsScrollPane.setPreferredSize(new Dimension(100, 150));
        listClientsPanel.add(listClientsScrollPane);
        
        lblRutasDeVuelo = new JLabel("  Clientes");
        lblRutasDeVuelo.setBackground(UIManager.getColor("Button.background"));
        lblRutasDeVuelo.setPreferredSize(new Dimension(10, 30));
        lblRutasDeVuelo.setFont(UIManager.getFont("TableHeader.font"));
        listClientsScrollPane.setColumnHeaderView(lblRutasDeVuelo);
        
        panel = new JPanel();
        listClientsPanel.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        lblIndicaQue = new JLabel("color indica que Cliente ya compro Paquete");
        lblIndicaQue.setVerticalAlignment(SwingConstants.BOTTOM);
        lblIndicaQue.setForeground(Color.DARK_GRAY);
        lblIndicaQue.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIndicaQue.setFont(new Font("Dialog", Font.PLAIN, 10));
        panel.add(lblIndicaQue);
        
        panel_9 = new JPanel();
        rightPanel.add(panel_9, BorderLayout.EAST);
        
        panel_8 = new JPanel();
        rightPanel.add(panel_8, BorderLayout.WEST);
        
        fechaCompraPanel = new JPanel();
        rightPanel.add(fechaCompraPanel, BorderLayout.SOUTH);
        GridBagLayout gbl_fechaCompraPanel = new GridBagLayout();
        gbl_fechaCompraPanel.columnWidths = new int[]{0, 122, 0, 0, 0};
        gbl_fechaCompraPanel.rowHeights = new int[]{0, 15, 0, 0};
        gbl_fechaCompraPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_fechaCompraPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        fechaCompraPanel.setLayout(gbl_fechaCompraPanel);
        
        panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        fechaCompraPanel.add(panel_1, gbc_panel_1);
        
        lblFechaDeCompra = new JLabel("Fecha de Compra");
        GridBagConstraints gbc_lblFechaDeCompra = new GridBagConstraints();
        gbc_lblFechaDeCompra.insets = new Insets(0, 0, 5, 5);
        gbc_lblFechaDeCompra.anchor = GridBagConstraints.EAST;
        gbc_lblFechaDeCompra.gridx = 1;
        gbc_lblFechaDeCompra.gridy = 1;
        fechaCompraPanel.add(lblFechaDeCompra, gbc_lblFechaDeCompra);
        
        fieldFechaCompra = new JDateChooser();
        GridBagConstraints gbc_fieldFechaCompra = new GridBagConstraints();
        gbc_fieldFechaCompra.insets = new Insets(0, 0, 5, 5);
        gbc_fieldFechaCompra.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldFechaCompra.gridx = 2;
        gbc_fieldFechaCompra.gridy = 1;
        //fieldFechaCompra.setDateFormatString("dd/MM/yyyy");
        fechaCompraPanel.add(fieldFechaCompra, gbc_fieldFechaCompra);
        
        panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 3;
        gbc_panel_2.gridy = 2;
        fechaCompraPanel.add(panel_2, gbc_panel_2);
        
        topPanel = new JPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        lblSeleccioneUnPaquete = new JLabel("Seleccione un Paquete y el Cliente que lo va a comprar");
        topPanel.add(lblSeleccioneUnPaquete);
        
        //Eventos
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
                	rellenarListaDeClientes();
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                setVisible(false);
            }
        });
        
        btnComprar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	cmdComprarPaquete();
            	
            }
        });

    }
    
    private void cmdComprarPaquete() {
  
    	if (checkFormulario()) {
    		String paquete = (String)listaDePaquetes.getSelectedValue();
    		String opcionCliente = (String)listaClientes.getSelectedValue();
    		Date fechaCompra = fieldFechaCompra.getDate();
    		//opcionCliente = opcionCliente.split(":")[0];
    		String cliente = opcionCliente.replace("*", "").trim();
            try {
                controlPaq.comprarPaquete(cliente, fechaCompra.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), paquete);
                // Muestro éxito de la operación
                PresentacionUtils.mostrarExito(this, "Se registro la compra con exito");
                limpiarFormulario();
                setVisible(false);
                
            } catch (Exception e) {
            	PresentacionUtils.mostrarError(this, e.getMessage());
            }
        }
    }

    // Permite validar la información introducida en los campos
    private boolean checkFormulario() {
        if (!PresentacionUtils.validarCamposRequeridos(this, "Debe seleccionar Paquete y Cliente", 
        		listaDePaquetes, listaClientes)) {
            return false;
        }
        if (!PresentacionUtils.validarCamposRequeridos(this, "Debe ingresar una fecha en formato: dd/MM/yyyy", 
        		fieldFechaCompra)) {
            return false;
        }
        return true;
    }
    
    public void rellenarListaDePaquetes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<DTInfoPaquete> paquetes = controlPaq.listarPaquetesConRutas();
        for (DTInfoPaquete paquete : paquetes) {
        	String element = paquete.getNombre();
            model.addElement(element);
        }
        listaDePaquetes.setModel(model);
    }
    
    public void rellenarListaDeClientes() {
    	try {
	    	String paquete = (String)listaDePaquetes.getSelectedValue();
	        DefaultListModel<String> model = new DefaultListModel<>();
	        String[] clientes = controlUsr.listarClientes();
	        Vector<String> clientesCompraron = new Vector<String>();
	        for (String cliente : clientes) {
	        	if (controlUsr.clienteComproPaquete(cliente, paquete)) {
	        		clientesCompraron.add(cliente);
	        	}
	            model.addElement(cliente);
	        }
	        listaClientes.setModel(model);
	        listaClientes.setCellRenderer(new ColoredElementsListCellRenderer(clientesCompraron, Color.getHSBColor(46, 49, 98)));
    	} catch (Exception e) {
    		PresentacionUtils.mostrarError(this, e.getMessage());
    	}
    }

    // Permite borrar el contenido de un formulario antes de cerrarlo.
    private void limpiarFormulario() {
    	listaDePaquetes.clearSelection();
    	PresentacionUtils.limpiarTablasyListas(listaClientes);
    	PresentacionUtils.limpiarCamposTexto(fieldFechaCompra);
    }
    
	public void prepararFrame() {
    	rellenarListaDePaquetes();
	}
}
