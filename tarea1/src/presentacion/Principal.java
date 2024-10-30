package presentacion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


import com.formdev.flatlaf.FlatLightLaf;

public class Principal {
	public static JFrame mainFrame;

	public static JDesktopPane desktopPane;

	public static AltaUsuario creUsrInternalFrame;
	public static ConsultaUsuario conUsrInternalFrame;
	public static ModificarUsuario modUsrInternalFrame;
	public static AltaVuelo creVueInternalFrame;
	public static ConsultaVuelo conVueInternalFrame;
	public static ReservarVuelo resVueInternalFrame;
	public static CrearPaquete crePaqInternalFrame;
	public static ConsultaPaquete conPaqInternalFrame;
	public static AgregarRutaVueloAPaquete agrRVaPaqInternalFrame;
	public static AltaCiudad creCiuInternalFrame;
	public static AltaRutaVuelo altRutInternalFrame;
	public static ConsultaRutaVuelo conRutInternalFrame;
	public static CargarDatos carDatosInternalFrame;
	public static ComprarPaquete comPaqInternalFrame;
	public static AceptarRechazarRutaVuelo aceRechRutVueInternalFrame;

	int width = 1680;
	int height = 1050;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static JFrame getMainFrame() {
    	return mainFrame;
    }

	public Principal() {
		FlatLightLaf.setup();
		
		initialize();
        
        // Se crean los tres InternalFrame y se incluyen al Frame principal ocultos.
        // De esta forma, no es necesario crear y destruir objetos lo que enlentece la ejecución.
        // Cada InternalFrame usa un layout diferente, simplemente para mostrar distintas opciones.
        creUsrInternalFrame = new AltaUsuario();
        creUsrInternalFrame.setVisible(false);
        
        conUsrInternalFrame = new ConsultaUsuario();
        conUsrInternalFrame.setVisible(false);
        
        modUsrInternalFrame = new ModificarUsuario();
        modUsrInternalFrame.setVisible(false);
        
        creVueInternalFrame = new AltaVuelo();
        creVueInternalFrame.setVisible(false);
        
        conVueInternalFrame = new ConsultaVuelo();
        conVueInternalFrame.setVisible(false);
        
        resVueInternalFrame = new ReservarVuelo();
        resVueInternalFrame.setVisible(false);
        
        creCiuInternalFrame = new AltaCiudad();
        creCiuInternalFrame.setVisible(false);
        
        altRutInternalFrame = new AltaRutaVuelo();
        altRutInternalFrame.setVisible(false);
        
        conRutInternalFrame = new ConsultaRutaVuelo();
        conRutInternalFrame.setVisible(false);
        
        crePaqInternalFrame = new CrearPaquete();
        crePaqInternalFrame.setVisible(false);
        
        conPaqInternalFrame = new ConsultaPaquete();
        conPaqInternalFrame.setVisible(false);
        
        agrRVaPaqInternalFrame = new AgregarRutaVueloAPaquete();
        agrRVaPaqInternalFrame.setVisible(false);
        
        aceRechRutVueInternalFrame = new AceptarRechazarRutaVuelo();
        aceRechRutVueInternalFrame.setVisible(false);
        
        comPaqInternalFrame = new ComprarPaquete();
        comPaqInternalFrame.setVisible(false);
        
        carDatosInternalFrame = new CargarDatos();
        carDatosInternalFrame.setVisible(false);
        
        desktopPane.add(creUsrInternalFrame);
        desktopPane.add(conUsrInternalFrame);
        desktopPane.add(modUsrInternalFrame);
        desktopPane.add(creVueInternalFrame);
        desktopPane.add(conVueInternalFrame);
        desktopPane.add(resVueInternalFrame);
        desktopPane.add(creCiuInternalFrame);
        desktopPane.add(altRutInternalFrame);
        desktopPane.add(conRutInternalFrame);
        desktopPane.add(crePaqInternalFrame);
        desktopPane.add(conPaqInternalFrame);
        desktopPane.add(agrRVaPaqInternalFrame);
        desktopPane.add(carDatosInternalFrame);
        desktopPane.add(comPaqInternalFrame);
        desktopPane.add(aceRechRutVueInternalFrame);
        //Por alguna razon el ultimo que se agrega ocupa todo el mainFrame, agregamos JInternalFrame vacio para evitar este bug
        desktopPane.add(new JInternalFrame());
        
        }
	
	
	private void initialize() {
		mainFrame = new JFrame();
		
		mainFrame.setBounds(100, 100, 1680, 1050);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		desktopPane = new JDesktopPane();
		
		mainFrame.setContentPane(desktopPane);
		
		// Se crea una barra de menú (JMenuBar) con dos menú (JMenu) desplegables.
        // Cada menú contiene diferentes opciones (JMenuItem), los cuales tienen un 
        // evento asociado que permite realizar una acción una vez se seleccionan. 
		
		JMenuBar menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
        ///////
  		//Menu Usuarios
  		///////
        JMenu menuUsuarios = new JMenu("Usuarios");
        menuBar.add(menuUsuarios);        
        
        JMenuItem menuItemRegistrar = new JMenuItem("Crear Usuario");
        menuItemRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	//
            	 creUsrInternalFrame.prepararFrame();
                 creUsrInternalFrame.setVisible(true);
                 centrarFrame(creUsrInternalFrame);
            }
        });
        menuUsuarios.add(menuItemRegistrar);
        
        JMenuItem menuItemConsultaUsuario = new JMenuItem("Consulta de Usuario");
        menuItemConsultaUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para consultar un usuario
            	conUsrInternalFrame.prepararFrame();
                conUsrInternalFrame.setVisible(true);
                centrarFrame(conUsrInternalFrame);
            }
        });
        menuUsuarios.add(menuItemConsultaUsuario);
        
        JMenuItem menuItemModificarUsuario = new JMenuItem("Modificar Datos de Usuario");
        menuItemModificarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para modificar un usuario
            	modUsrInternalFrame.prepararFrame();
            	modUsrInternalFrame.setVisible(true);
                centrarFrame(modUsrInternalFrame);
            }
        });
        menuUsuarios.add(menuItemModificarUsuario);
        
        ///////
    	//Menu Rutas de Vuelo
    	///////
        JMenu menuRutas = new JMenu("Rutas de Vuelo");
        menuBar.add(menuRutas); 
        JMenuItem menuItemAltaRuta = new JMenuItem("Ingresar nueva Ruta de Vuelo");
        menuItemAltaRuta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para dar de alta ruta vuelo
            	altRutInternalFrame.prepararFrame();
            	altRutInternalFrame.setVisible(true);
                centrarFrame(altRutInternalFrame);
            }
        }); 
        menuRutas.add(menuItemAltaRuta);
        JMenuItem menuItemConsultaRuta = new JMenuItem("Consultar una Ruta de Vuelo");
        menuItemConsultaRuta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para dar de alta ruta vuelo
            	conRutInternalFrame.prepararFrame();
            	conRutInternalFrame.setVisible(true);
                centrarFrame(conRutInternalFrame);
            }
        }); 
        menuRutas.add(menuItemConsultaRuta);
        
        JMenuItem menuItemAceptarRechazarRutaVuelo = new JMenuItem("Aceptar/Rechazar Ruta de Vuelo");
		menuItemAceptarRechazarRutaVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para Aceptar/Rechazar Ruta de Vuelo
				//
				aceRechRutVueInternalFrame.prepararFrame();
				aceRechRutVueInternalFrame.setVisible(true);
                centrarFrame(aceRechRutVueInternalFrame);
			}
		});
		menuRutas.add(menuItemAceptarRechazarRutaVuelo);
        
		JMenuItem menuItemCrearCiudad = new JMenuItem("Crear Ciudad");
		menuItemCrearCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para crear una ciudad
				//
				creCiuInternalFrame.prepararFrame();
				creCiuInternalFrame.setVisible(true);
                centrarFrame(creCiuInternalFrame);
			}
		});
		menuRutas.add(menuItemCrearCiudad);
		
        
        ///////
  		//Menu Vuelos
  		///////
        JMenu menuVuelos = new JMenu("Vuelos");
        menuBar.add(menuVuelos);        
        JMenuItem menuItemIngresarVuelo = new JMenuItem("Ingresar nuevo Vuelo");
        menuItemIngresarVuelo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para dar de alta un vuelo
            	creVueInternalFrame.prepararFrame();
            	creVueInternalFrame.setVisible(true);
                centrarFrame(creVueInternalFrame);
            }
        }); 
        menuVuelos.add(menuItemIngresarVuelo);
        
        JMenuItem menuItemConsultarVuelo = new JMenuItem("Consultar Vuelo");
		menuItemConsultarVuelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para consultar un vuelo
			 	conVueInternalFrame.prepararFrame();;
			 	conVueInternalFrame.setVisible(true);
                centrarFrame(conVueInternalFrame);
			}
		});
		menuVuelos.add(menuItemConsultarVuelo);
		  
        JMenuItem menuItemReservarVuelo = new JMenuItem("Reservar Vuelo");
        menuItemReservarVuelo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para dar de alta un vuelo
            	resVueInternalFrame.prepararFrame();
            	resVueInternalFrame.setVisible(true);
                centrarFrame(resVueInternalFrame);
            }
        }); 
        menuVuelos.add(menuItemReservarVuelo);
		
        
		///////
		//Menu Paquetes
		///////
        JMenu menuPaquetes = new JMenu("Paquetes");
        menuBar.add(menuPaquetes);
        
        JMenuItem menuItemCrearPaquete = new JMenuItem("Crear Paquete");
        menuItemCrearPaquete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	//
            	crePaqInternalFrame.prepararFrame();
                crePaqInternalFrame.setVisible(true);
                centrarFrame(crePaqInternalFrame);
            }
        });
        menuPaquetes.add(menuItemCrearPaquete);
        
        JMenuItem menuItemConsultaPaquete = new JMenuItem("Consultar Paquete");
        menuItemConsultaPaquete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	conPaqInternalFrame.prepararFrame();
            	conPaqInternalFrame.setVisible(true);
                centrarFrame(conPaqInternalFrame);
            }
        });
        menuPaquetes.add(menuItemConsultaPaquete);
        
        JMenuItem menuItemAgregarRVaPaquete = new JMenuItem("Agregar Ruta de Vuelo a Paquete");
        menuItemAgregarRVaPaquete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	agrRVaPaqInternalFrame.prepararFrame();
            	agrRVaPaqInternalFrame.setVisible(true);
                centrarFrame(agrRVaPaqInternalFrame);
            }
        });
        menuPaquetes.add(menuItemAgregarRVaPaquete);
        
        JMenuItem menuItemComprarPaquete = new JMenuItem("Comprar Paquete");
        menuItemComprarPaquete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	comPaqInternalFrame.prepararFrame();
            	comPaqInternalFrame.setVisible(true);
                centrarFrame(comPaqInternalFrame);
            }
        });
        menuPaquetes.add(menuItemComprarPaquete);

        ///////
        //Menu Sistema
        ///////
        JMenu menuSistema = new JMenu("Sistema");
        menuBar.add(menuSistema);
        
        JMenuItem menuItemCargarDatos = new JMenuItem("Cargar Datos de Prueba");
        menuItemCargarDatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	carDatosInternalFrame.setVisible(true);
                centrarFrame(carDatosInternalFrame);
            }
        });
        menuSistema.add(menuItemCargarDatos);
        
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		// Salgo de la aplicación
        		mainFrame.setVisible(false);
        		mainFrame.dispose();
        	}
        });
        menuSistema.add(menuItemSalir);
	}
	
    private void centrarFrame(JInternalFrame frame) {
        if (desktopPane != null) {
            int desktopWidth = desktopPane.getWidth();
            int desktopHeight = desktopPane.getHeight();
            int frameWidth = frame.getWidth();
            int frameHeight = frame.getHeight();
            int x = (desktopWidth - frameWidth) / 2;
            int y = (desktopHeight - frameHeight) / 2;
            frame.setLocation(x, y);
        }
    }
}



