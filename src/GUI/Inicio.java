package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

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
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	//Guardar informacion de la posicion de mouse para mover la ventana
	int xMouse, yMouse;


	/**
	 * Create the frame.
	 */
	public Inicio(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {		
		setLocationByPlatform(true);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel Header = new JPanel();
		Header.setOpaque(false);
		Header.setBackground(new Color(0, 0, 0, 0));
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
		Header.setBounds(0, 0, 800, 47);
		Header.setLayout(null);
		Header.setBackground(Color.WHITE);
		contentPane.add(Header);
		JPanel panelinicio = new JPanel();
		panelinicio.setBounds(0, 0, 800, 500);
		contentPane.add(panelinicio);
		panelinicio.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ACCEDE COMO:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		lblNewLabel.setBounds(320, 259, 190, 14);
		panelinicio.add(lblNewLabel);
		
		JComboBox<String> comboBox = new JComboBox<>(new String[] {
			    "Estudiante", "Docente", "Administrador"
			});
			comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			comboBox.setBackground(Color.WHITE);
			comboBox.setForeground(new Color(50, 50, 50));
			comboBox.setBounds(320, 299, 190, 30);

			// Quitar el borde original y poner uno más suave
			comboBox.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
			    BorderFactory.createEmptyBorder(5, 10, 5, 10)
			));

			// Elimina el focus border azul al hacer clic
			comboBox.setFocusable(false);

			// Suaviza los bordes del comboBox (solo estético)
			comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
			    @Override
			    protected JButton createArrowButton() {
			        JButton button = new JButton("▼");
			        button.setBorder(BorderFactory.createEmptyBorder());
			        button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
			        button.setContentAreaFilled(false);
			        button.setForeground(new Color(100, 100, 100));
			        return button;
			    }
			});

			panelinicio.add(comboBox);
			
		
		JButton btnAcceder = new JButton("Estudiante");
		btnAcceder.setOpaque(true);
		btnAcceder.setForeground(Color.WHITE);
		btnAcceder.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnAcceder.setFocusPainted(false);
		btnAcceder.setBorderPainted(false);
		btnAcceder.setBorder(null);
		btnAcceder.setBackground(new Color(128, 0, 0));
		btnAcceder.setBounds(305, 347, 222, 59);
		panelinicio.add(btnAcceder);
		
		JLabel imgInicio_1 = new JLabel("");
		imgInicio_1.setHorizontalAlignment(SwingConstants.CENTER);
		imgInicio_1.setBounds(318, 55, 190, 206);
		panelinicio.add(imgInicio_1);
		
		// Cargar y escalar la imagen
		ImageIcon ico2 = new ImageIcon(Login.class.getResource("/Imagenes/Escudo_UD.svg.png"));
		Image imagen2 = ico2.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada2 = new ImageIcon(imagen2);
		
		// Asignar imagen al JLabel
		imgInicio_1.setIcon(imagenEscalada2);
		
		JPanel panel = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        // Pintar el fondo con opacidad (RGBA)
		        Graphics2D g2d = (Graphics2D) g.create();
		        g2d.setColor(new Color(253 ,255, 255, 140)); // negro con opacidad (0-255)
		        g2d.fillRect(0, 0, getWidth(), getHeight());
		        g2d.dispose();
		    }
		};
		panel.setBackground(new Color(253, 240, 147));
		panel.setForeground(new Color(255, 255, 255));
		panel.setOpaque(false);
		panel.setBounds(252, 44, 325, 413);
		panelinicio.add(panel);
		
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-89, 0, 974, 521);
		panelinicio.add(imgFondo);
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Cargar y escalar la imagen
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen = ico.getImage().getScaledInstance(825, 500, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		// Asignar imagen al JLabel
		imgFondo.setIcon(imagenEscalada);
		
		
		JPanel ExitPanel = new JPanel();
		ExitPanel.setOpaque(false);
		ExitPanel.setBackground(new Color(0, 0, 0, 0));
		ExitPanel.setBounds(753, 0, 47, 47);
		Header.add(ExitPanel);
		ExitPanel.setLayout(null);
		
		JLabel ExitTxt = new JLabel("X");
		ExitTxt.setBounds(0, 0, 47, 47);
		ExitPanel.add(ExitTxt);
		ExitPanel.addMouseListener(new MouseAdapter() {
			@Override
			//Cerrar la ventana
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			//Cambiar el color del fondo del label
			public void mouseEntered(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 23));
				ExitTxt.setForeground(Color.white);
				ExitPanel.setOpaque(true);

				ExitPanel.setBackground(new Color(128, 0, 0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22));
				ExitTxt.setForeground(Color.black);
				ExitPanel.setOpaque(false);
				ExitPanel.setBackground(new Color(0, 0, 0, 0));
			}
		});
		
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String seleccion = (String) comboBox.getSelectedItem();

		        switch (seleccion) {
		            case "Estudiante":
		            	dispose();
		            	new Login("Estudiante",gestorU, gestorM, s).setVisible(true);
		                break;
		            case "Docente":
		            	dispose();
		            	new Login("Docente",gestorU, gestorM, s).setVisible(true);
		                break;
		            case "Administrador":
		            	dispose();
		            	new Login("Administrador",gestorU, gestorM, s).setVisible(true);
		                break;
		            default:
		                JOptionPane.showMessageDialog(null, "Seleccione un rol válido");
		        }
		    }
		});
		ExitTxt.setHorizontalAlignment(SwingConstants.CENTER);
		ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		
		// Cargar y escalar la imagen
		ImageIcon ico1 = new ImageIcon(Login.class.getResource("/Imagenes/output-onlinepngtools.png"));
		Image imagen1 = ico1.getImage().getScaledInstance(50, 52, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada1 = new ImageIcon(imagen1);
		
		
		
		
		
	}
}
