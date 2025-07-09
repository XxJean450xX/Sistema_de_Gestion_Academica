package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Kernel.Estudiante;
import Kernel.Materia;
import Kernel.Profesor;
import Kernel.Usuario;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;
import Persistencia.GestionSolicitudes;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.JScrollBar;

public class EstudiantesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int mouseX, mouseY;
	private static Estudiante estudianteActual;
	


	private final JPanel panelinicio = new JPanel();
	private DefaultListModel<Estudiante> modeloEstudiantesPreinscritos = new DefaultListModel<>();

    
    



	/**
	 * Create the frame.
	 */
	public EstudiantesGUI(Estudiante estudiante, GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		this.estudianteActual = estudiante; // Asignar el estudiante recibido
     

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.repaint();
		
		// panel principal
		
		JPanel panelInicio = new JPanel();
		panelInicio.setBounds(0, 82, 800, 418);
		contentPane.add(panelInicio);
		panelInicio.setLayout(null);
		
		
		

		JPanel panelInstruccionesEstudiante = new JPanel();
		panelInstruccionesEstudiante.setLayout(null);
		panelInstruccionesEstudiante.setBackground(new Color(255, 255, 255));
		panelInstruccionesEstudiante.setBounds(70, 22, 659, 364);
		panelInicio.add(panelInstruccionesEstudiante);


		// Mensaje de bienvenida
		JLabel lblBienvenidaEst = new JLabel("¡Bienvenido Estudiante!");
		lblBienvenidaEst.setFont(new Font("Leelawadee UI", Font.BOLD, 20));
		lblBienvenidaEst.setBounds(50, 30, 400, 30);
		panelInstruccionesEstudiante.add(lblBienvenidaEst);

		// Instrucciones
		JTextArea instruccionesEst = new JTextArea(
			    "Puedes realizar las siguientes funciones:\n\n" +
			    "• Preinscribirte a las materias que deseas cursar en el próximo semestre.\n" +
			    "• Consultar tus notas finales por materia, período y docente asignado.\n" +
			    "• Visualizar tu horario académico completo, incluyendo salones y profesores.\n" +
			    "• Revisar el estado de tus solicitudes académicas (si fueron aprobadas o rechazadas).\n" +
			    "• Acceder a la información de contacto de tus profesores.\n\n" +
			    "Recuerda mantener actualizados tus datos y cumplir con los plazos establecidos."
			);

		instruccionesEst.setFont(new Font("Leelawadee UI", Font.PLAIN, 16));
		instruccionesEst.setEditable(false);
		instruccionesEst.setBackground(new Color(255, 255, 255));
		instruccionesEst.setBounds(50, 80, 500, 200);
		instruccionesEst.setLineWrap(true);
		instruccionesEst.setWrapStyleWord(true);
		panelInstruccionesEstudiante.add(instruccionesEst);
		
		
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-87, 0, 974, 461);
		panelInicio.add(imgFondo);
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER);
		// Asignar imagen al JLabel
		// Cargar y escalar la imagen
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen = ico.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		imgFondo.setIcon(imagenEscalada);
		
		
		//Panel Header
		
		
		JPanel Header = new JPanel();
		Header.setBounds(0, 0, 800, 39);
		Header.setLayout(null);
		Header.setBackground(Color.WHITE);
		contentPane.add(Header);
		
		JPanel VolverPanel = new JPanel();
		VolverPanel.setLayout(null);
		VolverPanel.setForeground(Color.BLACK);
		VolverPanel.setBackground(Color.WHITE);
		VolverPanel.setBounds(0, 0, 41, 39);
		Header.add(VolverPanel);
		
		Header.addMouseListener(new MouseAdapter() {
			@Override
			//Metodo para guardar informacion al presionar en el borde
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		Header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			//Metodo para establecer la locacion de la ventana segun el mouse
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		
		// panel Navbar
		
		JPanel NavBar = new JPanel();
		NavBar.setBounds(0, 39, 800, 44);
		NavBar.setLayout(null);
		NavBar.setBackground(new Color(128, 0, 0));
		contentPane.add(NavBar);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setText(estudianteActual.getNombre().toUpperCase()+" "+estudianteActual.getApellido().toUpperCase()); // ← Nombre dinámico del estudiante
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel.setBounds(80, 1, 191, 40);
		NavBar.add(lblNewLabel);
		
		JButton btnInscripciones = new JButton("INSCRIPCIONES");
		btnInscripciones.setBounds(459, 1, 89, 43);
		btnInscripciones.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnInscripciones.setForeground(Color.WHITE);
		btnInscripciones.setBackground(new Color(128, 0, 0));
		btnInscripciones.setOpaque(true);
		btnInscripciones.setFocusPainted(false);
		btnInscripciones.setContentAreaFilled(false);
		btnInscripciones.setBorderPainted(false);
		btnInscripciones.setBorder(null);
		NavBar.add(btnInscripciones);
		
		JButton btnNotas = new JButton("NOTAS");
		btnNotas.setBounds(560, 0, 111, 43);
		btnNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnNotas.setForeground(Color.WHITE);
		btnNotas.setBackground(new Color(128, 0, 0));
		btnNotas.setOpaque(true);
		btnNotas.setFocusPainted(false);
		btnNotas.setContentAreaFilled(false);
		btnNotas.setBorderPainted(false);
		btnNotas.setBorder(null);
		NavBar.add(btnNotas);
		
		JButton btnHorario = new JButton("HORARIO");
		btnHorario.setBounds(680, 0, 103, 43);
		btnHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnHorario.setForeground(Color.WHITE);
		btnHorario.setBackground(new Color(128, 0, 0));
		btnHorario.setOpaque(true);
		btnHorario.setFocusPainted(false);
		btnHorario.setContentAreaFilled(false);
		btnHorario.setBorderPainted(false);
		btnHorario.setBorder(null);
		NavBar.add(btnHorario);
		
		//Panel para salida
		
		JPanel ExitPanel = new JPanel();
		ExitPanel.setLayout(null);
		ExitPanel.setBackground(Color.WHITE);
		ExitPanel.setBounds(760, 0, 41, 39);
		Header.add(ExitPanel);
		
		JLabel ExitTxt = new JLabel("X");
		ExitTxt.setBounds(0, 0, 41, 39);
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
				ExitPanel.setBackground(new Color(128, 0, 0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22));
				ExitTxt.setForeground(Color.black);
				ExitPanel.setBackground(Color.white);
			}
		});
		ExitTxt.setHorizontalAlignment(SwingConstants.CENTER);
		ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		
		//Panel para volver
		
		JLabel VolverTxt = new JLabel("<");
		VolverTxt.setHorizontalAlignment(SwingConstants.CENTER);
		VolverTxt.setForeground(new Color(0, 0, 0));
		VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		VolverTxt.setBounds(0, 0, 41, 39);
		VolverPanel.add(VolverTxt);
		
		
		//BOTONES 
		

		
		JLabel Estudiante = new JLabel("ESTUDIANTE");
		Estudiante.setHorizontalAlignment(SwingConstants.CENTER);
		Estudiante.setBounds(317, 0, 150, 39);
		Header.add(Estudiante);
		Estudiante.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		Estudiante.setForeground(Color.BLACK);
		Estudiante.setBackground(new Color(255, 255, 255));
		Estudiante.setOpaque(true);
		Estudiante.setBorder(null);
		VolverPanel.addMouseListener(new MouseAdapter() {
			@Override
			//Volver a la anterior ventana
			public void mouseClicked(MouseEvent e) {
				dispose();
				Inicio frameInicio = new Inicio(gestorU, gestorM, s);
				frameInicio.setVisible(true);
			}
			@Override
			//Cambiar el color del fondo del label
			public void mouseEntered(MouseEvent e) {
				VolverTxt.setFont(new Font("Verdana", Font.BOLD, 23));
				VolverTxt.setForeground(Color.white);
				VolverPanel.setBackground(new Color(234, 175, 0));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22));
				VolverTxt.setForeground(Color.black);
				VolverPanel.setBackground(Color.white);
			}
		});
		
		
		// Panel Inscripciones
		JPanel panelInscripciones = new JPanel();
		panelInscripciones.setBackground(Color.WHITE);
		panelInscripciones.setSize(655, 363);
		panelInscripciones.setLocation(71, 106);
		panelInscripciones.setLayout(null);
		contentPane.add(panelInscripciones);

		// Título
		JLabel lblTitulo = new JLabel("PREINSCRIPCIÓN DE MATERIAS");
		lblTitulo.setFont(new Font("Leelawadee UI", Font.BOLD, 16));
		lblTitulo.setBounds(197, 20, 244, 30);
		panelInscripciones.add(lblTitulo);

		// Panel para las materias (dentro de un scroll)
		JPanel panelMateriasLista = new JPanel();
		panelMateriasLista.setLayout(new BoxLayout(panelMateriasLista, BoxLayout.Y_AXIS));
		panelMateriasLista.setBackground(Color.WHITE);

		// Scroll
		JScrollPane scrollMaterias = new JScrollPane(panelMateriasLista);
		scrollMaterias.setBounds(100, 60, 450, 240);
		panelInscripciones.add(scrollMaterias);
		
		
		// Contador de créditos
		JLabel lblCreditos = new JLabel("Créditos preinscritos: 0 / 18");
		lblCreditos.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		lblCreditos.setBounds(250, 300, 300, 20);
		panelInscripciones.add(lblCreditos);

		// Límite de créditos
		final int LIMITE_CREDITOS = 18;
		final int[] creditosActuales = {0};
		
		List<Materia> materiasPreinscritas = s.obtenerSolicitudes(estudianteActual.getCodigo());
		Map<Materia, JPanel> mapaFilas = new HashMap<>();
		Map<Materia, JCheckBox> mapaMateriasCheckbox = new LinkedHashMap<>();

		
		for (Materia m : gestorM.getMaterias()) {
		    JPanel fila = new JPanel(new BorderLayout());
		    fila.setBackground(Color.WHITE);
		    fila.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		    JLabel lblMateria = new JLabel(m.getNombre() + " (" + m.getCreditos() + " créditos)");
		    lblMateria.setFont(new Font("Tahoma", Font.PLAIN, 13));

		    JCheckBox check = new JCheckBox();
		    check.setBackground(Color.WHITE);

		    if (materiasPreinscritas.contains(m)) {
		        check.setSelected(true);
		        check.setEnabled(false);
		        creditosActuales[0] += m.getCreditos();
		    }



		    // Listener para controlar créditos
		    check.addActionListener(e -> {
		        if (check.isSelected()) {
		            if (creditosActuales[0] + m.getCreditos() > LIMITE_CREDITOS) {
		                check.setSelected(false);
		                JOptionPane.showMessageDialog(panelInscripciones,
		                        "Ya alcanzaste el límite de 18 créditos por semestre.",
		                        "Límite de créditos",
		                        JOptionPane.WARNING_MESSAGE);
		            } else {
		                creditosActuales[0] += m.getCreditos();
		            }
		        } else {
		            creditosActuales[0] -= m.getCreditos();
		        }

		        // Actualizar etiqueta
		        lblCreditos.setText("Créditos preinscritos: " + creditosActuales[0] + " / " + LIMITE_CREDITOS);

		        // Bloquear los checkboxes si se llegó al límite
		        for (Map.Entry<Materia, JCheckBox> entry : mapaMateriasCheckbox.entrySet()) {
		            JCheckBox cb = entry.getValue();
		            if (!cb.isSelected()) {
		                cb.setEnabled(creditosActuales[0] < LIMITE_CREDITOS);
		            }
		        }
		    });

		    fila.add(lblMateria, BorderLayout.WEST);
		    fila.add(check, BorderLayout.EAST);

		    panelMateriasLista.add(fila);
		    mapaMateriasCheckbox.put(m, check);
		    mapaFilas.put(m, fila);
		}
		panelMateriasLista.revalidate();
		panelMateriasLista.repaint();

		// Mostrar créditos iniciales
		lblCreditos.setText("Créditos preinscritos: " + creditosActuales[0] + " / " + LIMITE_CREDITOS);

		



		// Botón enviar
		JButton btnEnviarSolicitud = new JButton("Enviar Solicitud");
		btnEnviarSolicitud.setBounds(217, 318, 224, 35);
		btnEnviarSolicitud.setBackground(new Color(255, 255, 255));
		btnEnviarSolicitud.setForeground(new Color(192, 192, 192));
		btnEnviarSolicitud.setFont(new Font("Leelawadee UI", Font.BOLD, 12));
		panelInscripciones.add(btnEnviarSolicitud);
		
		// --- DESHABILITAR BOTÓN SI YA TIENE SOLICITUD ---
		if (estudianteActual.getPreinscripciones() != null &&
			    !estudianteActual.getPreinscripciones().isEmpty()) {
			    
			    btnEnviarSolicitud.setEnabled(false);
			    btnEnviarSolicitud.setText("Solicitud Enviada");
			    btnEnviarSolicitud.setForeground(Color.GRAY);

			    for (JCheckBox check : mapaMateriasCheckbox.values()) {
			        check.setEnabled(false);
			    }
			}

		
		// --- LISTENER DEL BOTÓN ---
		btnEnviarSolicitud.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        List<Materia> seleccionadas = new ArrayList<>();

		        for (Map.Entry<Materia, JCheckBox> entry : mapaMateriasCheckbox.entrySet()) {
		            if (entry.getValue().isSelected()) {
		                seleccionadas.add(entry.getKey());
		            }
		        }

		        if (seleccionadas.isEmpty()) {
		            JOptionPane.showMessageDialog(panelInscripciones, 
		                "Selecciona al menos una materia.", 
		                "Error", 
		                JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        for (Materia m : seleccionadas) {
		            estudianteActual.preinscribirMateria(m);
		        }

		        // Guardar en archivo
		        gestorU.guardarUsuarios();

		        s.guardarSolicitud(estudianteActual);
		       

		        // Cambiar UI
		        btnEnviarSolicitud.setEnabled(false);
		        btnEnviarSolicitud.setText("Solicitud Enviada");
		        btnEnviarSolicitud.setForeground(Color.GRAY);

		        for (JCheckBox check : mapaMateriasCheckbox.values()) {
		            check.setEnabled(false);
		        }

		        JOptionPane.showMessageDialog(panelInscripciones, 
		            "Solicitud enviada con éxito.", 
		            "Éxito", 
		            JOptionPane.INFORMATION_MESSAGE);
		    }
		});

		panelInscripciones.setVisible(false);
		
		//Panel Horario
		
				
		JPanel panelHorario = new JPanel();
		panelHorario.setBackground(new Color(255, 255, 255));
		panelHorario.setSize(655, 363);
		panelHorario.setLocation(71, 106);
		panelHorario.setLayout(null);
		contentPane.add(panelHorario);
		
		// Botón Imprimir Horario
		JButton btnImprimirHorario = new JButton("Imprimir horario");
		btnImprimirHorario.setOpaque(true);
		btnImprimirHorario.setForeground(Color.WHITE);
		btnImprimirHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnImprimirHorario.setFocusPainted(false);
		btnImprimirHorario.setBorderPainted(false);
		btnImprimirHorario.setBorder(null);
		btnImprimirHorario.setBackground(new Color(128, 0, 0));
		btnImprimirHorario.setBounds(263, 318, 109, 35);
				
								// LIMPIAR panel
			panelHorario.removeAll();

				// Crear título
				JLabel lblVerHorario = new JLabel("HORARIO");
				lblVerHorario.setBounds(163, 10, 296, 40);
				lblVerHorario.setHorizontalAlignment(SwingConstants.CENTER);
				lblVerHorario.setForeground(Color.BLACK);
				lblVerHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 28));
				panelHorario.add(lblVerHorario);
				
								    
		
							
			// Agregar botón imprimir
			panelHorario.add(btnImprimirHorario);
			
			panelHorario.setVisible(false); // ocultarlo al inicio
		

		
		// Panel de notas del estudiante
			
		JPanel panelNotas = new JPanel();
		panelNotas.setBackground(Color.WHITE);
		panelNotas.setBounds(71, 106, 655, 363);
		panelNotas.setLayout(null);
		contentPane.add(panelNotas);

		// Título
		JLabel lblVerificarNotas = new JLabel("NOTAS");
		lblVerificarNotas.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerificarNotas.setForeground(Color.BLACK);
		lblVerificarNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 28));
		lblVerificarNotas.setBounds(180, 10, 296, 40);
		panelNotas.add(lblVerificarNotas);

		// Botón imprimir notas
		JButton btnVer = new JButton("Imprimir notas");
		btnVer.setOpaque(true);
		btnVer.setForeground(Color.WHITE);
		btnVer.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnVer.setFocusPainted(false);
		btnVer.setBorderPainted(false);
		btnVer.setBorder(null);
		btnVer.setBackground(new Color(128, 0, 0));
		btnVer.setBounds(265, 310, 125, 35);
		panelNotas.add(btnVer);

		// Modelo de la tabla
		DefaultTableModel modeloTablaNotas = new DefaultTableModel(
		    new Object[]{"Materia", "Nota 1", "Nota 2", "Nota 3", "Promedio"}, 0
		) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // No editable
		    }
		};

		// Tabla
		JTable tablaNotas = new JTable(modeloTablaNotas);
		tablaNotas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaNotas.setRowHeight(30);
		tablaNotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tablaNotas.getTableHeader().setReorderingAllowed(false);
		
		// 🔒 3. Bloquear redimensión manual
		tablaNotas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaNotas.getTableHeader().setResizingAllowed(false);
		int[] anchos = {182, 104, 104, 104, 104};
		for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
		    tablaNotas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}


		// Centrado del contenido
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
		    tablaNotas.getColumnModel().getColumn(i).setCellRenderer(centro);
		}

		// Scroll de tabla
		JScrollPane scroll = new JScrollPane(tablaNotas);
		scroll.setBounds(20, 60, 600, 230);
		panelNotas.add(scroll);

		// Ocultar inicialmente
		panelNotas.setVisible(false);
		
		//ACCIONES DE LOS BOTONES

		btnInscripciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelNotas.setVisible(false);
				panelInscripciones.setVisible(true);
				panelHorario.setVisible(false);
				panelInicio.setVisible(false);
				contentPane.setComponentZOrder(panelInscripciones, 1); // al frente
			    contentPane.revalidate();
			    contentPane.repaint();
			}
		});
		btnNotas.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        panelNotas.setVisible(true);
		        panelInscripciones.setVisible(false);
		        panelHorario.setVisible(false);
		        panelInicio.setVisible(false);
		        contentPane.setComponentZOrder(panelNotas, 1);
		        contentPane.revalidate();
		        contentPane.repaint();

		     // Actualiza la lista de materias desde el gestor para asegurar que esté sincronizada
		        List<Materia> materiasCargadas = gestorM.getMaterias();

		        // Limpiar la tabla
		        modeloTablaNotas.setRowCount(0);

		        // Recorrer materias cargadas para este estudiante
		        for (Materia materia : materiasCargadas) {
		            if (materia.getEstudiantes().contains(estudianteActual)) {
		                List<Double> notas = materia.getNotasEstudiante(estudianteActual);
		                double n1 = 0.0, n2 = 0.0, n3 = 0.0, promedio = 0.0;

		                if (notas != null && notas.size() == 3) {
		                    n1 = notas.get(0);
		                    n2 = notas.get(1);
		                    n3 = notas.get(2);
		                    promedio = Math.round(materia.calcularPromedio(estudianteActual) * 100.0) / 100.0;
		                }

		                modeloTablaNotas.addRow(new Object[]{
		                    materia.getNombre(),
		                    String.format("%.2f", n1),
		                    String.format("%.2f", n2),
		                    String.format("%.2f", n3),
		                    String.format("%.2f", promedio)
		                });
		            }
		        }
		    }
		});

		
		btnHorario.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        panelNotas.setVisible(false);
		        panelInscripciones.setVisible(false);
		        panelHorario.setVisible(true);
		        panelInicio.setVisible(false);
		        contentPane.setComponentZOrder(panelHorario, 1);
		        contentPane.revalidate();
		        contentPane.repaint();

		        panelHorario.removeAll();
		        panelHorario.setLayout(null);

		        JLabel lblVerHorario = new JLabel("HORARIO");
		        lblVerHorario.setBounds(163, 10, 296, 40);
		        lblVerHorario.setHorizontalAlignment(SwingConstants.CENTER);
		        lblVerHorario.setForeground(Color.BLACK);
		        lblVerHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 28));
		        panelHorario.add(lblVerHorario);

		        DefaultTableModel modelo = new DefaultTableModel() {
		            @Override
		            public boolean isCellEditable(int row, int column) {
		                return false;
		            }
		        };

		        modelo.setColumnIdentifiers(new Object[]{
		            "Cod.", "Nombre", "Créditos", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"
		        });

		        // Cargar materias desde el gestor, no desde listas temporales
		        List<Materia> materiasCargadas = gestorM.getMaterias();

		        for (Materia materia : materiasCargadas) {
		            if (materia.getEstudiantes().contains(estudianteActual)) {
		                String[] fila = new String[9];
		                fila[0] = String.valueOf(materia.getCodigo());
		                fila[1] = materia.getNombre();
		                fila[2] = String.valueOf(materia.getCreditos());

		                for (int i = 3; i < 9; i++) fila[i] = "";

		                for (String dia : materia.getDias()) {
		                    int col = switch (dia.toLowerCase()) {
		                        case "lun" -> 3;
		                        case "mar" -> 4;
		                        case "mie" -> 5;
		                        case "jue" -> 6;
		                        case "vie" -> 7;
		                        case "sab" -> 8;
		                        default -> -1;
		                    };
		                    if (col != -1) {
		                        fila[col] = materia.getHoraInicio() + " - " + materia.getHoraFin();
		                    }
		                }

		                modelo.addRow(fila);
		            }
		        }

		        JTable tabla = new JTable(modelo);
		        tabla.setRowHeight(40);
		        tabla.getTableHeader().setReorderingAllowed(false);
		        tabla.setGridColor(new Color(200, 200, 200));
		        tabla.setShowHorizontalLines(true);
		        tabla.setShowVerticalLines(false);
		        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		        tabla.setFillsViewportHeight(true);
		        tabla.setBackground(Color.WHITE);
		        tabla.setSelectionBackground(new Color(220, 220, 255));

		        // Centrar contenido
		        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		        centro.setHorizontalAlignment(SwingConstants.CENTER);
		        for (int i = 0; i < tabla.getColumnCount(); i++) {
		            tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
		        }

		        // Minimizar columnas de días vacías
		        for (int col = 3; col <= 8; col++) {
		            boolean vacía = true;
		            for (int fila = 0; fila < modelo.getRowCount(); fila++) {
		                if (modelo.getValueAt(fila, col) != null &&
		                    !modelo.getValueAt(fila, col).toString().isBlank()) {
		                    vacía = false;
		                    break;
		                }
		            }
		            if (vacía) {
		                tabla.getColumnModel().getColumn(col).setMinWidth(30);
		                tabla.getColumnModel().getColumn(col).setMaxWidth(30);
		                tabla.getColumnModel().getColumn(col).setPreferredWidth(30);
		            }
		        }
		        

		        JScrollPane scroll = new JScrollPane(tabla);
		        scroll.setBounds(20, 60, 600, 250);
		        panelHorario.add(scroll);
		        
		        

		        // Botón imprimir (si ya lo tienes creado arriba)
		        btnImprimirHorario.setBounds(263, 318, 109, 35);
		        panelHorario.add(btnImprimirHorario);
		     

		        panelHorario.revalidate();
		        panelHorario.repaint();
		        
		    }
		});
		
		btnInscripciones.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btnInscripciones.setBackground(new Color(80, 0, 0));
		        btnInscripciones.repaint();
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnInscripciones.setBackground(new Color(128, 0, 0));
		    	btnInscripciones.repaint();
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		    	btnInscripciones.setBackground(new Color(100, 0, 0)); // un color distinto para el clic
		    	btnInscripciones.repaint();
		    }
		});
		
		btnNotas.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btnNotas.setBackground(new Color(80, 0, 0));
		        btnNotas.repaint();
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnNotas.setBackground(new Color(128, 0, 0));
		    	btnNotas.repaint();
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		    	btnNotas.setBackground(new Color(100, 0, 0)); // un color distinto para el clic
		    	btnNotas.repaint();
		    }
		});
		
		btnHorario.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btnHorario.setBackground(new Color(80, 0, 0));
		        btnHorario.repaint();
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnHorario.setBackground(new Color(128, 0, 0));
		    	btnHorario.repaint();
		    }

		    @Override
		    public void mousePressed(MouseEvent e) {
		    	btnHorario.setBackground(new Color(100, 0, 0)); // un color distinto para el clic
		    	btnHorario.repaint();
		    }
		});
		
		JPanel Escudo = new JPanel();
		Escudo.setForeground(new Color(0, 0, 0));
		Escudo.setLayout(null);
		Escudo.setBackground(new Color(128, 0, 0));
		Escudo.setBounds(10, 0, 53, 43);
		NavBar.add(Escudo);

		JLabel EscudoTxt = new JLabel("");
		EscudoTxt.setHorizontalAlignment(SwingConstants.CENTER);
		EscudoTxt.setBounds(0, 0, 53, 43);
		Escudo.add(EscudoTxt);
		Escudo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Mostrar panel de inicio como hace el botón
				panelInicio.setVisible(true);
				panelInscripciones.setVisible(false);
				panelNotas.setVisible(false);
				panelHorario.setVisible(false);

				contentPane.setComponentZOrder(panelinicio, 0);
				contentPane.revalidate();
				contentPane.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Escudo.setBackground(new Color(80, 0, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Escudo.setBackground(new Color(128, 0, 0));
			}
		});

		
		ImageIcon ico1 = new ImageIcon(Login.class.getResource("/Imagenes/output-onlinepngtools.png"));
		Image imagen1 = ico1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada1 = new ImageIcon(imagen1);
		
		// Asignar imagen al JLabel
				EscudoTxt.setIcon(imagenEscalada1);
				
				
		JLabel imgFondo3 = new JLabel("");
		imgFondo3.setBounds(-87, 83, 974, 461);
		contentPane.add(imgFondo3);
		imgFondo3.setHorizontalAlignment(SwingConstants.CENTER);
		// Cargar y escalar la imagen
		ImageIcon ico3 = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen3 = ico3.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada3 = new ImageIcon(imagen3);
		// Asignar imagen al JLabel
		imgFondo3.setIcon(imagenEscalada3);
				


		



	

	}
}