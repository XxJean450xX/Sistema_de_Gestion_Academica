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

public class Login extends JFrame {

	private JPanel contentPane;
	private JPanel LoginEstudiante, LoginDocente, LoginAdministrador;

	private JTextField txtCodigoEstudiante, txtCodigoDocente, txtCodigoAdmin;
	private JPasswordField pwdEstudiante, pwdDocente, pwdAdmin;

	private String rolEsperado;

	int xMouse, yMouse;

	public Login(String rol, GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		this.rolEsperado = rol.toLowerCase();

		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel Header = new JPanel();

		Header.addMouseListener(new MouseAdapter() {
			@Override
			// Metodo para guardar informacion al presionar en el borde
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
		Header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			// Metodo para establecer la locacion de la ventana segun el mouse
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - xMouse, y - yMouse);
			}
		});
		Header.setBackground(Color.WHITE);
		Header.setBounds(0, 0, 800, 39);
		contentPane.add(Header);
		Header.setLayout(null);

		JPanel VolverPanel = new JPanel();
		VolverPanel.setBackground(Color.WHITE);
		VolverPanel.setBounds(0, 0, 41, 39);
		Header.add(VolverPanel);
		VolverPanel.setLayout(null);

		JLabel VolverTxt = new JLabel("<");
		VolverTxt.setHorizontalAlignment(SwingConstants.CENTER);
		VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		VolverTxt.setBounds(0, 0, 41, 39);
		VolverPanel.add(VolverTxt);

		VolverPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Inicio(gestorU, gestorM, s).setVisible(true);
			}

			public void mouseEntered(MouseEvent e) {
				VolverTxt.setForeground(Color.WHITE);
				VolverPanel.setBackground(new Color(234, 175, 0));
			}

			public void mouseExited(MouseEvent e) {
				VolverTxt.setForeground(Color.BLACK);
				VolverPanel.setBackground(Color.WHITE);
			}
		});

		JPanel ExitPanel = new JPanel();
		ExitPanel.setBackground(Color.WHITE);
		ExitPanel.setBounds(759, 0, 41, 39);
		Header.add(ExitPanel);
		ExitPanel.setLayout(null);

		JLabel ExitTxt = new JLabel("X");
		ExitTxt.setHorizontalAlignment(SwingConstants.CENTER);
		ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		ExitTxt.setBounds(0, 0, 41, 39);
		ExitPanel.add(ExitTxt);

		ExitPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {
				ExitTxt.setForeground(Color.WHITE);
				ExitPanel.setBackground(new Color(128, 0, 0));
			}

			public void mouseExited(MouseEvent e) {
				ExitTxt.setForeground(Color.BLACK);
				ExitPanel.setBackground(Color.WHITE);
			}
		});

		crearPanelEstudiante(gestorU, gestorM, s);
		crearPanelDocente(gestorU, gestorM, s);
		crearPanelAdministrador(gestorU, gestorM, s);

		LoginEstudiante.setVisible(false);
		LoginDocente.setVisible(false);
		LoginAdministrador.setVisible(false);

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
			JOptionPane.showMessageDialog(null, "Rol desconocido: " + rolEsperado);
			dispose();
			new Inicio(gestorU, gestorM, s).setVisible(true);
		}
	}

	private void crearPanelEstudiante(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		LoginAdministrador = new JPanel();
		LoginAdministrador.setBackground(Color.WHITE);
		LoginAdministrador.setBounds(0, 39, 800, 461);
		contentPane.add(LoginAdministrador);
		LoginAdministrador.setLayout(null);

		JLabel lblTitulo_2 = new JLabel("INICIO SESIÓN - ADMIN");
		lblTitulo_2.setFont(new Font("Leelawadee", Font.BOLD, 18));
		lblTitulo_2.setBounds(50, 93, 400, 30);
		LoginAdministrador.add(lblTitulo_2);

		JLabel lblCodigo_2 = new JLabel("Código:");
		lblCodigo_2.setBounds(50, 143, 100, 25);
		LoginAdministrador.add(lblCodigo_2);

		txtCodigoAdmin = new JTextField();
		txtCodigoAdmin.setBounds(50, 173, 300, 25);
		LoginAdministrador.add(txtCodigoAdmin);

		JLabel lblPass_2 = new JLabel("Contraseña:");
		lblPass_2.setBounds(50, 213, 100, 25);
		LoginAdministrador.add(lblPass_2);

		pwdAdmin = new JPasswordField();
		pwdAdmin.setBounds(50, 243, 300, 25);
		LoginAdministrador.add(pwdAdmin);

		JButton btnLogin_2 = new JButton("Iniciar Sesión");
		btnLogin_2.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnLogin_2.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnLogin_2.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnLogin_2.setForeground(Color.WHITE);
		btnLogin_2.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnLogin_2.setBorder(null);
		btnLogin_2.setBackground(new Color(128, 0, 0));
		btnLogin_2.setBounds(50, 293, 150, 30);
		LoginAdministrador.add(btnLogin_2);
		
		JPanel panel = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        // Pintar el fondo con opacidad (RGBA)
		        Graphics2D g2d = (Graphics2D) g.create();
		        g2d.setColor(new Color(255, 255, 255, 200)); // negro con opacidad (0-255)
		        g2d.fillRect(0, 0, getWidth(), getHeight());
		        g2d.dispose();
		    }
		};
		panel.setForeground(new Color(255, 255, 255));
		panel.setOpaque(false);
		panel.setBounds(30, 55, 345, 320);
		LoginAdministrador.add(panel);
		
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-90, 0, 974, 461);
		LoginAdministrador.add(imgFondo);
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Cargar y escalar la imagen
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoBosa.png"));
		Image imagen = ico.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		// Asignar imagen al JLabel
		imgFondo.setIcon(imagenEscalada);

		btnLogin_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long codigo = Long.parseLong(txtCodigoAdmin.getText().trim());
				String pass = new String(pwdAdmin.getPassword());
				GestorLogin gestorL = new GestorLogin();

				if (gestorL.validarLogin(codigo, pass, "Administrador")) {
					JOptionPane.showMessageDialog(null, "Bienvenido Admin");
					dispose();
					new AdminGUI(gestorU, gestorM, s).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
				}
			}
		});
		LoginDocente = new JPanel();
		LoginDocente.setBackground(Color.WHITE);
		LoginDocente.setBounds(0, 39, 800, 461);
		contentPane.add(LoginDocente);
		LoginDocente.setLayout(null);

		// Escalar y cargar imagen
		ImageIcon icoDocente = new ImageIcon(Login.class.getResource("/Imagenes/FondoBosa.png"));
		Image imagenDocente = icoDocente.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);

		JLabel lblTitulo_1 = new JLabel("INICIO SESIÓN - DOCENTE");
		lblTitulo_1.setFont(new Font("Leelawadee", Font.BOLD, 18));
		lblTitulo_1.setBounds(50, 93, 400, 30);
		LoginDocente.add(lblTitulo_1);

		JLabel lblCodigo_1 = new JLabel("Código:");
		lblCodigo_1.setBounds(50, 143, 100, 25);
		LoginDocente.add(lblCodigo_1);

		txtCodigoDocente = new JTextField();
		txtCodigoDocente.setBounds(50, 173, 300, 25);
		LoginDocente.add(txtCodigoDocente);

		JLabel lblPass_1 = new JLabel("Contraseña:");
		lblPass_1.setBounds(50, 213, 100, 25);
		LoginDocente.add(lblPass_1);

		pwdDocente = new JPasswordField();
		pwdDocente.setBounds(50, 243, 300, 25);
		LoginDocente.add(pwdDocente);

		JButton btnLogin_1 = new JButton("Iniciar Sesión");
		btnLogin_1.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnLogin_1.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnLogin_1.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnLogin_1.setForeground(Color.WHITE);
		btnLogin_1.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnLogin_1.setBorder(null);
		btnLogin_1.setBackground(new Color(128, 0, 0));
		btnLogin_1.setBounds(50, 293, 150, 30);
		LoginDocente.add(btnLogin_1);
		
		JPanel panelDocente = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g.create();
		        g2d.setColor(new Color(255, 255, 255, 200)); // Blanco con opacidad
		        g2d.fillRect(0, 0, getWidth(), getHeight());
		        g2d.dispose();
		    }
		};
		
				panelDocente.setOpaque(false);
				panelDocente.setBounds(30, 55, 345, 320);
				LoginDocente.add(panelDocente);
		
		JLabel imgFondoDocente = new JLabel("");
		imgFondoDocente.setBounds(-90, 0, 974, 461);
		imgFondoDocente.setHorizontalAlignment(SwingConstants.CENTER);
		LoginDocente.add(imgFondoDocente);
		imgFondoDocente.setIcon(new ImageIcon(imagenDocente));

		btnLogin_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long codigo = Long.parseLong(txtCodigoDocente.getText().trim());
				String pass = new String(pwdDocente.getPassword());
				GestorLogin gestor = new GestorLogin();
				if (gestor.validarLogin(codigo, pass, "Profesor")) {
					JOptionPane.showMessageDialog(null, "Bienvenido Docente");
					dispose();
					new ProfesorGUI((Profesor) gestorU.buscarUsuarioPorCodigo(codigo), gestorU, gestorM, s)
							.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
				}
			}
		});
		LoginEstudiante = new JPanel();
		LoginEstudiante.setBackground(Color.WHITE);
		LoginEstudiante.setBounds(0, 39, 800, 461);
		contentPane.add(LoginEstudiante);
		LoginEstudiante.setLayout(null);

		ImageIcon icoEst = new ImageIcon(Login.class.getResource("/Imagenes/FondoBosa.png"));
		Image imagenEst = icoEst.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);


		JLabel lblTitulo = new JLabel("INICIO SESIÓN - ESTUDIANTE");
		lblTitulo.setFont(new Font("Leelawadee", Font.BOLD, 18));
		lblTitulo.setBounds(50, 92, 300, 30);
		LoginEstudiante.add(lblTitulo);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(50, 142, 100, 25);
		LoginEstudiante.add(lblCodigo);

		txtCodigoEstudiante = new JTextField();
		txtCodigoEstudiante.setBounds(50, 172, 300, 25);
		LoginEstudiante.add(txtCodigoEstudiante);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(50, 212, 100, 25);
		LoginEstudiante.add(lblPass);

		pwdEstudiante = new JPasswordField();
		pwdEstudiante.setBounds(50, 242, 300, 25);
		LoginEstudiante.add(pwdEstudiante);

		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnLogin.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnLogin.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnLogin.setBorder(null);
		btnLogin.setBackground(new Color(128, 0, 0));
		btnLogin.setBounds(50, 292, 150, 30);
		LoginEstudiante.add(btnLogin);
		JPanel panelEstudiante = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g.create();
		        g2d.setColor(new Color(255, 255, 255, 200));
		        g2d.fillRect(0, 0, getWidth(), getHeight());
		        g2d.dispose();
		    }
		};
		panelEstudiante.setOpaque(false);
		panelEstudiante.setBounds(30, 55, 345, 320);
		LoginEstudiante.add(panelEstudiante);

		JLabel imgFondo_12 = new JLabel("");
		imgFondo_12.setHorizontalAlignment(SwingConstants.CENTER);
		imgFondo_12.setBounds(-90, 0, 974, 461);
		LoginEstudiante.add(imgFondo_12);
		imgFondo_12.setIcon(new ImageIcon(imagenEst));
		
		JLabel imgFondo_1 = new JLabel("");
		imgFondo_1.setHorizontalAlignment(SwingConstants.CENTER);
		imgFondo_1.setBounds(0, 0, 974, 461);
		contentPane.add(imgFondo_1);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long codigo = Long.parseLong(txtCodigoEstudiante.getText().trim());
				String pass = new String(pwdEstudiante.getPassword());
				GestorLogin gestorL = new GestorLogin();
				if (gestorL.validarLogin(codigo, pass, "Estudiante")) {
					JOptionPane.showMessageDialog(null, "Bienvenido Estudiante");
					dispose();
					new EstudiantesGUI((Estudiante) gestorU.buscarUsuarioPorCodigo(codigo), gestorU, gestorM, s)
							.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
				}
			}
		});
	}

	private void crearPanelDocente(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
	}

	private void crearPanelAdministrador(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {

		if (rolEsperado.equalsIgnoreCase("Estudiante")) {
			LoginEstudiante.setVisible(true);
			LoginDocente.setVisible(false);
			LoginAdministrador.setVisible(false);
			contentPane.setComponentZOrder(LoginEstudiante, 0); // al frente
		} else if (rolEsperado.equalsIgnoreCase("Docente")) {
			LoginEstudiante.setVisible(false);
			LoginDocente.setVisible(true);
			LoginAdministrador.setVisible(false);
			contentPane.setComponentZOrder(LoginDocente, 0); // al frente
		} else if (rolEsperado.equalsIgnoreCase("Administrador")) {
			LoginEstudiante.setVisible(false);
			LoginDocente.setVisible(false);
			LoginAdministrador.setVisible(true);
			contentPane.setComponentZOrder(LoginAdministrador, 0); // al frente
		} else {
			JOptionPane.showMessageDialog(null, "Rol desconocido: " + rolEsperado);

		}

		contentPane.revalidate();
		contentPane.repaint();

	}
}
