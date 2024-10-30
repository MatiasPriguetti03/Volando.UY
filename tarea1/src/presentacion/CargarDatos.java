package presentacion;


import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import utils.PresentacionUtils;

/**
 * JInternalFrame que permite registrar un nuevo paquete al sistema.
 * 
 * @author FB
 *
 */
@SuppressWarnings("serial")
public class CargarDatos extends JInternalFrame {
    
    // Los componentes gráficos se agregan como atributos de la clase
    // para facilitar su acceso desde diferentes métodos de la misma.
    private JButton btnCargarUsuarios;
    private JButton btnCargarCategorias;
    private JButton btnCargarCiudades;
    private JButton btnCargarRutasDe;
    private JButton btnCargarVuelos;
    private JButton btnCargarPaquetes;
    private JButton btnCargarComprasPaquetes;
    private JButton btnCargarReservasVuelos;
    private JButton btnCargarTodosLos;
    private JPanel panel_2;

    /**
     * Create the frame.
     */
    public CargarDatos() {
    	
    	JInternalFrame CD = this;
    	test.Datos datos = test.Datos.getInstancia();
    	
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Cargar Datos de Prueba");
        setBounds(100, 100, 350, 400);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{70, 0, 70, 0};
        gridBagLayout.rowHeights = new int[]{40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        
        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        panel.setPreferredSize(new Dimension(40,35));
        getContentPane().add(panel, gbc_panel);
        
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setVisible(false);
            }
        });
        
        btnCargarUsuarios = new JButton("Cargar Usuarios");
        GridBagConstraints gbc_btnCargarUsuarios = new GridBagConstraints();
        gbc_btnCargarUsuarios.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarUsuarios.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarUsuarios.gridx = 1;
        gbc_btnCargarUsuarios.gridy = 1;
        getContentPane().add(btnCargarUsuarios, gbc_btnCargarUsuarios);
        btnCargarUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosUsuarios();
            		btnCargarUsuarios.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarCategorias = new JButton("Cargar Categorias");
        GridBagConstraints gbc_btnCargar = new GridBagConstraints();
        gbc_btnCargar.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargar.gridx = 1;
        gbc_btnCargar.gridy = 2;
        getContentPane().add(btnCargarCategorias, gbc_btnCargar);
        btnCargarCategorias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosCategorias();
            		btnCargarCategorias.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarCiudades = new JButton("Cargar Ciudades");
        GridBagConstraints gbc_btnCargarCiudades = new GridBagConstraints();
        gbc_btnCargarCiudades.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarCiudades.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarCiudades.gridx = 1;
        gbc_btnCargarCiudades.gridy = 3;
        getContentPane().add(btnCargarCiudades, gbc_btnCargarCiudades);
        btnCargarCiudades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosCiudades();
            		btnCargarCiudades.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarRutasDe = new JButton("Cargar Rutas de Vuelo");
        GridBagConstraints gbc_btnCargarRutasDe = new GridBagConstraints();
        gbc_btnCargarRutasDe.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarRutasDe.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarRutasDe.gridx = 1;
        gbc_btnCargarRutasDe.gridy = 4;
        getContentPane().add(btnCargarRutasDe, gbc_btnCargarRutasDe);
        btnCargarRutasDe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosRutasVuelo();
            		btnCargarRutasDe.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarVuelos = new JButton("Cargar Vuelos");
        GridBagConstraints gbc_btnCargarVuelos = new GridBagConstraints();
        gbc_btnCargarVuelos.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarVuelos.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarVuelos.gridx = 1;
        gbc_btnCargarVuelos.gridy = 5;
        getContentPane().add(btnCargarVuelos, gbc_btnCargarVuelos);
        btnCargarVuelos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosVuelos();
            		btnCargarVuelos.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarPaquetes = new JButton("Cargar Paquetes");
        GridBagConstraints gbc_btnCargarPaquetes = new GridBagConstraints();
        gbc_btnCargarPaquetes.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarPaquetes.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarPaquetes.gridx = 1;
        gbc_btnCargarPaquetes.gridy = 6;
        getContentPane().add(btnCargarPaquetes, gbc_btnCargarPaquetes);
        btnCargarPaquetes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosPaquetes();
            		btnCargarPaquetes.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarComprasPaquetes = new JButton("Cargar Compras Paquetes");
        GridBagConstraints gbc_btnCargarComprasPaquetes = new GridBagConstraints();
        gbc_btnCargarComprasPaquetes.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarComprasPaquetes.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarComprasPaquetes.gridx = 1;
        gbc_btnCargarComprasPaquetes.gridy = 7;
        getContentPane().add(btnCargarComprasPaquetes, gbc_btnCargarComprasPaquetes);
        btnCargarComprasPaquetes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosComprasPaquetes();
            		btnCargarComprasPaquetes.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        btnCargarReservasVuelos = new JButton("Cargar Reservas Vuelos");
        GridBagConstraints gbc_btnCargarReservasVuelos = new GridBagConstraints();
        gbc_btnCargarReservasVuelos.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarReservasVuelos.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarReservasVuelos.gridx = 1;
        gbc_btnCargarReservasVuelos.gridy = 8;
        getContentPane().add(btnCargarReservasVuelos, gbc_btnCargarReservasVuelos);
        btnCargarReservasVuelos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		test.Datos.cargarDatosReservasVuelos();
            		btnCargarReservasVuelos.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.insets = new Insets(0, 0, 5, 5);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 9;
        getContentPane().add(panel_2, gbc_panel_2);
        
        btnCargarTodosLos = new JButton("Cargar todos los Datos");
        GridBagConstraints gbc_btnCargarTodosLos = new GridBagConstraints();
        gbc_btnCargarTodosLos.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCargarTodosLos.insets = new Insets(0, 0, 5, 5);
        gbc_btnCargarTodosLos.gridx = 1;
        gbc_btnCargarTodosLos.gridy = 10;
        getContentPane().add(btnCargarTodosLos, gbc_btnCargarTodosLos);
        btnCargarTodosLos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		Vector<JButton> btns = new Vector<JButton>();
            		btns.add(btnCargarUsuarios);
            		btns.add(btnCargarCategorias);
            		btns.add(btnCargarCiudades);
            		btns.add(btnCargarRutasDe);
            		btns.add(btnCargarVuelos);
            		btns.add(btnCargarPaquetes);
            		btns.add(btnCargarComprasPaquetes);
            		btns.add(btnCargarReservasVuelos);
            		for (JButton jButton : btns) {
						if(jButton.isEnabled()) {
							jButton.doClick();
						}
					}
            		btnCargarTodosLos.setEnabled(false);
            	} catch (Exception ex) {
        		PresentacionUtils.mostrarError(CD, ex.getMessage());
            	}
            }
        });
        
        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 2;
        gbc_panel_1.gridy = 11;
        panel_1.setPreferredSize(new Dimension(40,35));
        getContentPane().add(panel_1, gbc_panel_1);
    }
}
