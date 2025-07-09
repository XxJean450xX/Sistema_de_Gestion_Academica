package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DropMode;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import Kernel.Estudiante;
import Kernel.GestorLogin;
import Kernel.HashUtil;
import Kernel.Profesor;
import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

/**
 * La clase Login representa la ventana de inicio de sesión de la aplicación.
 * Muestra diferentes paneles de login según el rol seleccionado (Estudiante, Docente, Administrador).
 */
public class Login extends JFrame {

	private JPanel contentPane; // Panel principal de contenido de la ventana
	// Paneles específicos para cada tipo de login
	private JPanel LoginEstudiante, LoginDocente, LoginAdministrador;

	// Campos de texto para el código y la contraseña de cada tipo de usuario
	private JTextField txtCodigoEstudiante, txtCodigoDocente, txtCodigoAdmin;
	private JPasswordField pwdEstudiante, pwdDocente, pwdAdmin;

	private String rolEsperado; // Almacena el rol que se espera iniciar sesión

	// Variables para almacenar la posición del mouse y permitir mover la ventana
	int xMouse, yMouse;

	/**
	 * Constructor de la clase Login.
	 * Inicializa la interfaz gráfica y los listeners de eventos para la ventana de login.
	 *
	 * @param rol El rol de usuario para el que se mostrará el formulario de login (Estudiante, Docente, Administrador).
	 * @param gestorU Objeto GestorUsuarios para la gestión de usuarios.
	 * @param gestorM Objeto GestorMaterias para la gestión de materias.
	 * @param s Objeto GestionSolicitudes para la gestión de solicitudes.
	 */
	public Login(String rol, GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		this.rolEsperado = rol.toLowerCase(); // Convierte el rol a minúsculas para comparaciones

		setUndecorated(true); // Elimina los bordes, la barra de título y los botones de la ventana
		setResizable(false); // Impide que el usuario pueda redimensionar la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define la operación por defecto al cerrar la ventana
		setBounds(100, 100, 800, 500); // Establece la posición y el tamaño de la ventana
		contentPane = new JPanel(); // Crea el panel de contenido
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Establece un borde vacío al panel de contenido
		setContentPane(contentPane); // Asigna el panel de contenido a la ventana
		contentPane.setLayout(null); // Establece el layout a null para posicionar componentes manualmente

		// Panel superior para arrastrar la ventana
		JPanel Header = new JPanel();

		Header.addMouseListener(new MouseAdapter() {
			@Override
			// Metodo para guardar la información de la posición del mouse al presionar
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX(); // Guarda la coordenada X del mouse
				yMouse = e.getY(); // Guarda la coordenada Y del mouse
			}
		});
		Header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			// Metodo para establecer la ubicación de la ventana según el movimiento del mouse
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen(); // Obtiene la coordenada X de la pantalla
				int y = e.getYOnScreen(); // Obtiene la coordenada Y de la pantalla
				setLocation(x - xMouse, y - yMouse); // Establece la nueva ubicación de la ventana
			}
		});
		Header.setBackground(Color.WHITE); // Establece el color de fondo del header
		Header.setBounds(0, 0, 800, 39); // Establece la posición y el tamaño del header
		contentPane.add(Header); // Añade el header al panel de contenido
		Header.setLayout(null); // Establece el layout a null

		// Panel para el botón "Volver"
		JPanel VolverPanel = new JPanel();
		VolverPanel.setBackground(Color.WHITE); // Establece el color de fondo
		VolverPanel.setBounds(0, 0, 41, 39); // Establece la posición y el tamaño
		Header.add(VolverPanel); // Añade el panel al header
		VolverPanel.setLayout(null); // Establece el layout a null

		JLabel VolverTxt = new JLabel("<"); // Etiqueta para el texto del botón "Volver"
		VolverTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
		VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Establece la fuente
		VolverTxt.setBounds(0, 0, 41, 39); // Establece la posición y el tamaño
		VolverPanel.add(VolverTxt); // Añade la etiqueta al panel "Volver"

		// Listener para el panel "Volver"
		VolverPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose(); // Cierra la ventana actual
				new Inicio(gestorU, gestorM, s).setVisible(true); // Abre la ventana de Inicio
			}

			public void mouseEntered(MouseEvent e) {
				VolverTxt.setForeground(Color.WHITE); // Cambia el color del texto al pasar el mouse
				VolverPanel.setBackground(new Color(234, 175, 0)); // Cambia el color de fondo del panel
			}

			public void mouseExited(MouseEvent e) {
				VolverTxt.setForeground(Color.BLACK); // Restaura el color del texto al salir el mouse
				VolverPanel.setBackground(Color.WHITE); // Restaura el color de fondo del panel
			}
		});

		// Panel para el botón "Salir" (X)
		JPanel ExitPanel = new JPanel();
		ExitPanel.setBackground(Color.WHITE); // Establece el color de fondo
		ExitPanel.setBounds(759, 0, 41, 39); // Establece la posición y el tamaño
		Header.add(ExitPanel); // Añade el panel al header
		ExitPanel.setLayout(null); // Establece el layout a null

		JLabel ExitTxt = new JLabel("X"); // Etiqueta para el texto del botón "Salir"
		ExitTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
		ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Establece la fuente
		ExitTxt.setBounds(0, 0, 41, 39); // Establece la posición y el tamaño
		ExitPanel.add(ExitTxt); // Añade la etiqueta al panel "Salir"

		// Listener para el panel "Salir"
		ExitPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0); // Cierra la aplicación
			}

			public void mouseEntered(MouseEvent e) {
				ExitTxt.setForeground(Color.WHITE); // Cambia el color del texto al pasar el mouse
				ExitPanel.setBackground(new Color(128, 0, 0)); // Cambia el color de fondo del panel
			}

			public void mouseExited(MouseEvent e) {
				ExitTxt.setForeground(Color.BLACK); // Restaura el color del texto al salir el mouse
				ExitPanel.setBackground(Color.WHITE); // Restaura el color de fondo del panel
			}
		});

		// Llama a los métodos para crear los paneles de login para cada rol
		crearPanelEstudiante(gestorU, gestorM, s);
		crearPanelDocente(gestorU, gestorM, s);
		crearPanelAdministrador(gestorU, gestorM, s);

		// Oculta todos los paneles de login inicialmente
		LoginEstudiante.setVisible(false);
		LoginDocente.setVisible(false);
		LoginAdministrador.setVisible(false);

		// Muestra el panel de login correspondiente al rol esperado
		switch (rolEsperado) {
		case "estudiante":
			LoginEstudiante.setVisible(true);
			break;
		case "docente":
			LoginDocente.setVisible(true);
			break;
		case "administrador":
			LoginAdministrador.setVisible(true);
			break;
		default:
			// Si el rol es desconocido, muestra un mensaje y vuelve a la ventana de Inicio
			JOptionPane.showMessageDialog(null, "Rol desconocido: " + rolEsperado);
			dispose(); // Cierra la ventana actual
			new Inicio(gestorU, gestorM, s).setVisible(true); // Abre la ventana de Inicio
		}
	}

	/**
	 * Crea y configura el panel de inicio de sesión para el Administrador.
	 * Aunque el método se llama crearPanelEstudiante, el código corresponde al panel de Administrador.
	 *
	 * @param gestorU Objeto GestorUsuarios para la gestión de usuarios.
	 * @param gestorM Objeto GestorMaterias para la gestión de materias.
	 * @param s Objeto GestionSolicitudes para la gestión de solicitudes.
	 */
	private void crearPanelEstudiante(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		LoginAdministrador = new JPanel(); // Inicializa el panel de Administrador
		LoginAdministrador.setBackground(Color.WHITE); // Establece el color de fondo
		LoginAdministrador.setBounds(0, 39, 800, 461); // Establece la posición y el tamaño
		contentPane.add(LoginAdministrador); // Añade el panel al panel de contenido
		LoginAdministrador.setLayout(null); // Establece el layout a null

		// Etiqueta para el título del panel de Administrador
		JLabel lblTitulo_2 = new JLabel("INICIO SESIÓN - ADMIN");
		lblTitulo_2.setFont(new Font("Leelawadee", Font.BOLD, 18)); // Establece la fuente
		lblTitulo_2.setBounds(50, 93, 400, 30); // Establece la posición y el tamaño
		LoginAdministrador.add(lblTitulo_2); // Añade la etiqueta al panel

		// Etiqueta para el campo de código del Administrador
		JLabel lblCodigo_2 = new JLabel("Código:");
		lblCodigo_2.setBounds(50, 143, 100, 25); // Establece la posición y el tamaño
		LoginAdministrador.add(lblCodigo_2); // Añade la etiqueta al panel

		txtCodigoAdmin = new JTextField(); // Campo de texto para el código del Administrador
		txtCodigoAdmin.setBounds(50, 173, 300, 25); // Establece la posición y el tamaño
		LoginAdministrador.add(txtCodigoAdmin); // Añade el campo de texto al panel

		// Etiqueta para el campo de contraseña del Administrador
		JLabel lblPass_2 = new JLabel("Contraseña:");
		lblPass_2.setBounds(50, 213, 100, 25); // Establece la posición y el tamaño
		LoginAdministrador.add(lblPass_2); // Añade la etiqueta al panel

		pwdAdmin = new JPasswordField(); // Campo de contraseña para el Administrador
		pwdAdmin.setBounds(50, 243, 300, 25); // Establece la posición y el tamaño
		LoginAdministrador.add(pwdAdmin); // Añade el campo de contraseña al panel

		// Botón para iniciar sesión como Administrador
		JButton btnLogin_2 = new JButton("Iniciar Sesión");
		btnLogin_2.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnLogin_2.setOpaque(true); // Hace que se pinte el fondo con el color elegido
		btnLogin_2.setBorderPainted(false); // Quita el borde pintado
		btnLogin_2.setForeground(Color.WHITE); // Establece el color del texto del botón
		btnLogin_2.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Establece la fuente del botón
		btnLogin_2.setBorder(null); // Elimina el borde
		btnLogin_2.setBackground(new Color(128, 0, 0)); // Establece el color de fondo del botón
		btnLogin_2.setBounds(50, 293, 150, 30); // Establece la posición y el tamaño
		LoginAdministrador.add(btnLogin_2); // Añade el botón al panel
		
		// Panel con fondo semitransparente para los elementos de login del Administrador
		JPanel panel = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g); // Llama al método paintComponent de la superclase
		        // Pintar el fondo con opacidad (RGBA)
		        Graphics2D g2d = (Graphics2D) g.create(); // Crea un contexto gráfico 2D
		        g2d.setColor(new Color(255, 255, 255, 200)); // Blanco con opacidad (0-255)
		        g2d.fillRect(0, 0, getWidth(), getHeight()); // Rellena el rectángulo del panel
		        g2d.dispose(); // Libera los recursos del contexto gráfico
		    }
		};
		panel.setForeground(new Color(255, 255, 255)); // Establece el color del primer plano
		panel.setOpaque(false); // Hace el panel transparente (para que se vea el fondo pintado)
		panel.setBounds(30, 55, 345, 320); // Establece la posición y el tamaño del panel
		LoginAdministrador.add(panel); // Añade el panel al panel de Administrador
		
		// Etiqueta para la imagen de fondo del panel de Administrador
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-90, 0, 974, 461); // Establece la posición y el tamaño
		LoginAdministrador.add(imgFondo); // Añade la etiqueta de fondo al panel de Administrador
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
		
		// Cargar y escalar la imagen de fondo
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoBosa.png"));
		Image imagen = ico.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH); // Escala la imagen
		ImageIcon imagenEscalada = new ImageIcon(imagen); // Crea un nuevo ImageIcon con la imagen escalada
		// Asignar imagen al JLabel
		imgFondo.setIcon(imagenEscalada); // Establece la imagen en la etiqueta

		// ActionListener para el botón de iniciar sesión del Administrador
		btnLogin_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long codigo = Long.parseLong(txtCodigoAdmin.getText().trim()); // Obtiene el código del campo de texto
				String pass = new String(pwdAdmin.getPassword()); // Obtiene la contraseña del campo de contraseña
				GestorLogin gestorL = new GestorLogin(); // Crea una instancia del GestorLogin

				if (gestorL.validarLogin(codigo, pass, "Administrador")) { // Valida las credenciales
					JOptionPane.showMessageDialog(null, "Bienvenido Admin"); // Mensaje de bienvenida
					dispose(); // Cierra la ventana actual
					new AdminGUI(gestorU, gestorM, s).setVisible(true); // Abre la ventana de Administrador
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas"); // Mensaje de error
				}
			}
		});

		// Inicializa el panel de Docente (aunque el código está dentro de crearPanelEstudiante)
		LoginDocente = new JPanel();
		LoginDocente.setBackground(Color.WHITE);
		LoginDocente.setBounds(0, 39, 800, 461);
		contentPane.add(LoginDocente);
		LoginDocente.setLayout(null);

		// Escalar y cargar imagen de fondo para el panel de Docente
		ImageIcon icoDocente = new ImageIcon(Login.class.getResource("/Imagenes/FondoBosa.png"));
		Image imagenDocente = icoDocente.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);

		// Etiqueta para el título del panel de Docente
		JLabel lblTitulo_1 = new JLabel("INICIO SESIÓN - DOCENTE");
		lblTitulo_1.setFont(new Font("Leelawadee", Font.BOLD, 18));
		lblTitulo_1.setBounds(50, 93, 400, 30);
		LoginDocente.add(lblTitulo_1);

		// Etiqueta para el campo de código del Docente
		JLabel lblCodigo_1 = new JLabel("Código:");
		lblCodigo_1.setBounds(50, 143, 100, 25);
		LoginDocente.add(lblCodigo_1);

		txtCodigoDocente = new JTextField(); // Campo de texto para el código del Docente
		txtCodigoDocente.setBounds(50, 173, 300, 25);
		LoginDocente.add(txtCodigoDocente);

		// Etiqueta para el campo de contraseña del Docente
		JLabel lblPass_1 = new JLabel("Contraseña:");
		lblPass_1.setBounds(50, 213, 100, 25);
		LoginDocente.add(lblPass_1);

		pwdDocente = new JPasswordField(); // Campo de contraseña para el Docente
		pwdDocente.setBounds(50, 243, 300, 25);
		LoginDocente.add(pwdDocente);

		// Botón para iniciar sesión como Docente
		JButton btnLogin_1 = new JButton("Iniciar Sesión");
		btnLogin_1.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnLogin_1.setOpaque(true); // Hace que se pinte el fondo con el color elegido
		btnLogin_1.setBorderPainted(false); // Quita el borde pintado
		btnLogin_1.setForeground(Color.WHITE); // Establece el color del texto del botón
		btnLogin_1.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Establece la fuente del botón
		btnLogin_1.setBorder(null); // Elimina el borde
		btnLogin_1.setBackground(new Color(128, 0, 0)); // Establece el color de fondo del botón
		btnLogin_1.setBounds(50, 293, 150, 30); // Establece la posición y el tamaño
		LoginDocente.add(btnLogin_1); // Añade el botón al panel
		
		// Panel con fondo semitransparente para los elementos de login del Docente
		JPanel panelDocente = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g); // Llama al método paintComponent de la superclase
		        Graphics2D g2d = (Graphics2D) g.create(); // Crea un contexto gráfico 2D
		        g2d.setColor(new Color(255, 255, 255, 200)); // Blanco con opacidad
		        g2d.fillRect(0, 0, getWidth(), getHeight()); // Rellena el rectángulo del panel
		        g2d.dispose(); // Libera los recursos del contexto gráfico
		    }
		};
		
		panelDocente.setOpaque(false); // Hace el panel transparente
		panelDocente.setBounds(30, 55, 345, 320); // Establece la posición y el tamaño
		LoginDocente.add(panelDocente); // Añade el panel al panel de Docente
		
		// Etiqueta para la imagen de fondo del panel de Docente
		JLabel imgFondoDocente = new JLabel("");
		imgFondoDocente.setBounds(-90, 0, 974, 461); // Establece la posición y el tamaño
		imgFondoDocente.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
		LoginDocente.add(imgFondoDocente); // Añade la etiqueta de fondo al panel de Docente
		imgFondoDocente.setIcon(new ImageIcon(imagenDocente)); // Establece la imagen en la etiqueta

		// ActionListener para el botón de iniciar sesión del Docente
		btnLogin_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long codigo = Long.parseLong(txtCodigoDocente.getText().trim()); // Obtiene el código del campo de texto
				String pass = new String(pwdDocente.getPassword()); // Obtiene la contraseña del campo de contraseña
				GestorLogin gestor = new GestorLogin(); // Crea una instancia del GestorLogin
				if (gestor.validarLogin(codigo, pass, "Profesor")) { // Valida las credenciales
					JOptionPane.showMessageDialog(null, "Bienvenido Docente"); // Mensaje de bienvenida
					dispose(); // Cierra la ventana actual
					// Abre la ventana de ProfesorGUI, pasando el objeto Profesor correspondiente
					new ProfesorGUI((Profesor) gestorU.buscarUsuarioPorCodigo(codigo), gestorU, gestorM, s)
							.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas"); // Mensaje de error
				}
			}
		});

		// Inicializa el panel de Estudiante
		LoginEstudiante = new JPanel();
		LoginEstudiante.setBackground(Color.WHITE);
		LoginEstudiante.setBounds(0, 39, 800, 461);
		contentPane.add(LoginEstudiante);
		LoginEstudiante.setLayout(null);

		// Escalar y cargar imagen de fondo para el panel de Estudiante
		ImageIcon icoEst = new ImageIcon(Login.class.getResource("/Imagenes/FondoBosa.png"));
		Image imagenEst = icoEst.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);


		// Etiqueta para el título del panel de Estudiante
		JLabel lblTitulo = new JLabel("INICIO SESIÓN - ESTUDIANTE");
		lblTitulo.setFont(new Font("Leelawadee", Font.BOLD, 18));
		lblTitulo.setBounds(50, 92, 300, 30);
		LoginEstudiante.add(lblTitulo);

		// Etiqueta para el campo de código del Estudiante
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(50, 142, 100, 25);
		LoginEstudiante.add(lblCodigo);

		txtCodigoEstudiante = new JTextField(); // Campo de texto para el código del Estudiante
		txtCodigoEstudiante.setBounds(50, 172, 300, 25);
		LoginEstudiante.add(txtCodigoEstudiante);

		// Etiqueta para el campo de contraseña del Estudiante
		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(50, 212, 100, 25);
		LoginEstudiante.add(lblPass);

		pwdEstudiante = new JPasswordField(); // Campo de contraseña para el Estudiante
		pwdEstudiante.setBounds(50, 242, 300, 25);
		LoginEstudiante.add(pwdEstudiante);

		// Botón para iniciar sesión como Estudiante
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnLogin.setOpaque(true); // Hace que se pinte el fondo con el color elegido
		btnLogin.setBorderPainted(false); // Quita el borde pintado
		btnLogin.setForeground(Color.WHITE); // Establece el color del texto del botón
		btnLogin.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Establece la fuente del botón
		btnLogin.setBorder(null); // Elimina el borde
		btnLogin.setBackground(new Color(128, 0, 0)); // Establece el color de fondo del botón
		btnLogin.setBounds(50, 292, 150, 30); // Establece la posición y el tamaño
		LoginEstudiante.add(btnLogin); // Añade el botón al panel

		// Panel con fondo semitransparente para los elementos de login del Estudiante
		JPanel panelEstudiante = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g); // Llama al método paintComponent de la superclase
		        Graphics2D g2d = (Graphics2D) g.create(); // Crea un contexto gráfico 2D
		        g2d.setColor(new Color(255, 255, 255, 200)); // Blanco con opacidad
		        g2d.fillRect(0, 0, getWidth(), getHeight()); // Rellena el rectángulo del panel
		        g2d.dispose(); // Libera los recursos del contexto gráfico
		    }
		};
		panelEstudiante.setOpaque(false); // Hace el panel transparente
		panelEstudiante.setBounds(30, 55, 345, 320); // Establece la posición y el tamaño
		LoginEstudiante.add(panelEstudiante); // Añade el panel al panel de Estudiante

		// Etiqueta para la imagen de fondo del panel de Estudiante
		JLabel imgFondo_12 = new JLabel("");
		imgFondo_12.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
		imgFondo_12.setBounds(-90, 0, 974, 461); // Establece la posición y el tamaño
		LoginEstudiante.add(imgFondo_12); // Añade la etiqueta de fondo al panel de Estudiante
		imgFondo_12.setIcon(new ImageIcon(imagenEst)); // Establece la imagen en la etiqueta
		
		// Etiqueta imgFondo_1 (parece ser redundante o no se utiliza activamente)
		JLabel imgFondo_1 = new JLabel("");
		imgFondo_1.setHorizontalAlignment(SwingConstants.CENTER);
		imgFondo_1.setBounds(0, 0, 974, 461);
		contentPane.add(imgFondo_1);

		// ActionListener para el botón de iniciar sesión del Estudiante
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long codigo = Long.parseLong(txtCodigoEstudiante.getText().trim()); // Obtiene el código del campo de texto
				String pass = new String(pwdEstudiante.getPassword()); // Obtiene la contraseña del campo de contraseña
				GestorLogin gestorL = new GestorLogin(); // Crea una instancia del GestorLogin
				if (gestorL.validarLogin(codigo, pass, "Estudiante")) { // Valida las credenciales
					JOptionPane.showMessageDialog(null, "Bienvenido Estudiante"); // Mensaje de bienvenida
					dispose(); // Cierra la ventana actual
					// Abre la ventana de EstudiantesGUI, pasando el objeto Estudiante correspondiente
					new EstudiantesGUI((Estudiante) gestorU.buscarUsuarioPorCodigo(codigo), gestorU, gestorM, s)
							.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas"); // Mensaje de error
				}
			}
		});
	}

	/**
	 * Método para crear y configurar el panel de inicio de sesión para el Docente.
	 * (Este método está vacío en el código original, su funcionalidad se encuentra en crearPanelEstudiante).
	 *
	 * @param gestorU Objeto GestorUsuarios para la gestión de usuarios.
	 * @param gestorM Objeto GestorMaterias para la gestión de materias.
	 * @param s Objeto GestionSolicitudes para la gestión de solicitudes.
	 */
	private void crearPanelDocente(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		// La lógica para el panel de Docente se encuentra actualmente en crearPanelEstudiante
	}

	/**
	 * Método para crear y configurar el panel de inicio de sesión para el Administrador.
	 * Este método maneja la visibilidad de los paneles de login.
	 * (Este método también contiene lógica que se superpone con el constructor).
	 *
	 * @param gestorU Objeto GestorUsuarios para la gestión de usuarios.
	 * @param gestorM Objeto GestorMaterias para la gestión de materias.
	 * @param s Objeto GestionSolicitudes para la gestión de solicitudes.
	 */
	private void crearPanelAdministrador(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {

		// Controla la visibilidad de los paneles de login según el rol esperado
		if (rolEsperado.equalsIgnoreCase("Estudiante")) {
			LoginEstudiante.setVisible(true); // Muestra el panel de Estudiante
			LoginDocente.setVisible(false); // Oculta el panel de Docente
			LoginAdministrador.setVisible(false); // Oculta el panel de Administrador
			contentPane.setComponentZOrder(LoginEstudiante, 0); // Trae el panel de Estudiante al frente
		} else if (rolEsperado.equalsIgnoreCase("Docente")) {
			LoginEstudiante.setVisible(false); // Oculta el panel de Estudiante
			LoginDocente.setVisible(true); // Muestra el panel de Docente
			LoginAdministrador.setVisible(false); // Oculta el panel de Administrador
			contentPane.setComponentZOrder(LoginDocente, 0); // Trae el panel de Docente al frente
		} else if (rolEsperado.equalsIgnoreCase("Administrador")) {
			LoginEstudiante.setVisible(false); // Oculta el panel de Estudiante
			LoginDocente.setVisible(false); // Oculta el panel de Docente
			LoginAdministrador.setVisible(true); // Muestra el panel de Administrador
			contentPane.setComponentZOrder(LoginAdministrador, 0); // Trae el panel de Administrador al frente
		} else {
			// Muestra un mensaje si el rol es desconocido
			JOptionPane.showMessageDialog(null, "Rol desconocido: " + rolEsperado);
		}

		contentPane.revalidate(); // Revalida el layout del panel de contenido
		contentPane.repaint(); // Repinta el panel de contenido
	}
}