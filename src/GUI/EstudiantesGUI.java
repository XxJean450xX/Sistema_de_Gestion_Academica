package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File; // Necesario para JFileChooser en JPanelToPDF
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Kernel.Estudiante;
import Kernel.Materia;
import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

/**
 * La clase EstudiantesGUI representa la interfaz gráfica de usuario para el módulo de estudiantes.
 * Permite a los estudiantes interactuar con funcionalidades como preinscripción de materias,
 * consulta de notas, visualización de horario, entre otras.
 */
public class EstudiantesGUI extends JFrame {

	/**
	 * Identificador de versión para la serialización de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * El panel de contenido principal de la ventana.
	 */
	private JPanel contentPane;
	/**
	 * Coordenadas X e Y del mouse, utilizadas para el arrastre de la ventana.
	 */
	private int mouseX, mouseY;
	/**
	 * La instancia del estudiante actualmente logueado. Es estática para facilitar el acceso global.
	 */
	private static Estudiante estudianteActual;
	/**
	 * Botón para imprimir el horario del estudiante.
	 */
	private JButton btnImprimirHorario;

	/**
	 * Panel que representa la sección de inicio o bienvenida del estudiante.
	 */
	private final JPanel panelinicio = new JPanel();
	/**
	 * Modelo de lista para gestionar los estudiantes preinscritos (aunque no se usa directamente en este fragmento).
	 */
	private DefaultListModel<Estudiante> modeloEstudiantesPreinscritos = new DefaultListModel<>();

    /**
	 * Crea una nueva instancia de la interfaz gráfica para estudiantes.
	 * Configura la ventana principal y sus componentes iniciales.
	 *
	 * @param estudiante El objeto Estudiante que ha iniciado sesión.
	 * @param gestorU El gestor de usuarios para operaciones relacionadas con usuarios (guardar, etc.).
	 * @param gestorM El gestor de materias para acceder a la lista de materias disponibles.
	 * @param s El gestor de solicitudes para manejar las preinscripciones del estudiante.
	 */
	public EstudiantesGUI(Estudiante estudiante, GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {
		this.estudianteActual = estudiante; // Asignar el estudiante recibido
     
		// Configuración básica de la ventana
		setUndecorated(true); // Elimina los bordes de la ventana para un diseño personalizado
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
		setBounds(100, 100, 800, 500); // Establece la posición y tamaño inicial de la ventana
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Añade un borde vacío al panel de contenido
		setContentPane(contentPane); // Establece este panel como el contenedor principal de la ventana
		contentPane.setLayout(null); // Deshabilita el gestor de diseño para posicionamiento absoluto de componentes
		contentPane.repaint(); // Fuerza un repintado del panel de contenido

		// --- Panel Principal de Inicio ---
		JPanel panelInicio = new JPanel();
		panelInicio.setBounds(0, 82, 800, 418); // Posición y tamaño del panel de inicio
		contentPane.add(panelInicio); // Añade el panel de inicio al panel de contenido principal
		panelInicio.setLayout(null); // Deshabilita el gestor de diseño para el panel de inicio

		JPanel panelInstruccionesEstudiante = new JPanel();
		panelInstruccionesEstudiante.setLayout(null); // Layout nulo para posicionamiento manual
		panelInstruccionesEstudiante.setBackground(new Color(255, 255, 255)); // Fondo blanco
		panelInstruccionesEstudiante.setBounds(70, 22, 659, 364); // Posición y tamaño
		panelInicio.add(panelInstruccionesEstudiante); // Añade al panel de inicio

		// Mensaje de bienvenida
		JLabel lblBienvenidaEst = new JLabel("¡Bienvenido Estudiante!");
		lblBienvenidaEst.setFont(new Font("Leelawadee UI", Font.BOLD, 20)); // Fuente y tamaño
		lblBienvenidaEst.setBounds(50, 30, 400, 30); // Posición y tamaño del texto de bienvenida
		panelInstruccionesEstudiante.add(lblBienvenidaEst); // Añade al panel de instrucciones

		// Instrucciones generales para el estudiante
		JTextArea instruccionesEst = new JTextArea(
			    "Puedes realizar las siguientes funciones:\n\n" +
			    "• Preinscribirte a las materias que deseas cursar en el próximo semestre.\n" +
			    "• Consultar tus notas finales por materia, período y docente asignado.\n" +
			    "• Visualizar tu horario académico completo, incluyendo salones y profesores.\n" +
			    "• Revisar el estado de tus solicitudes académicas (si fueron aprobadas o rechazadas).\n" +
			    "• Acceder a la información de contacto de tus profesores.\n\n" +
			    "Recuerda mantener actualizados tus datos y cumplir con los plazos establecidos."
			);

		instruccionesEst.setFont(new Font("Leelawadee UI", Font.PLAIN, 16)); // Fuente y tamaño de las instrucciones
		instruccionesEst.setEditable(false); // Hace que el texto no sea editable
		instruccionesEst.setBackground(new Color(255, 255, 255)); // Fondo blanco
		instruccionesEst.setBounds(50, 80, 500, 200); // Posición y tamaño
		instruccionesEst.setLineWrap(true); // Permite que el texto se ajuste a la siguiente línea
		instruccionesEst.setWrapStyleWord(true); // Ajusta el texto por palabras completas
		panelInstruccionesEstudiante.add(instruccionesEst); // Añade al panel de instrucciones
		
		// Imagen de fondo para el panel de inicio
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-87, 0, 974, 461); // Posición y tamaño
		panelInicio.add(imgFondo); // Añade la imagen de fondo al panel de inicio
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen horizontalmente
		// Carga y escala la imagen desde los recursos del proyecto
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen = ico.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH); // Escala la imagen
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		imgFondo.setIcon(imagenEscalada); // Asigna la imagen escalada al JLabel

		// --- Panel de Cabecera (Header) ---
		JPanel Header = new JPanel();
		Header.setBounds(0, 0, 800, 39); // Posición y tamaño del header
		Header.setLayout(null); // Deshabilita el gestor de diseño
		Header.setBackground(Color.WHITE); // Fondo blanco
		contentPane.add(Header); // Añade el header al panel de contenido principal

		JPanel VolverPanel = new JPanel();
		VolverPanel.setLayout(null); // Deshabilita el gestor de diseño
		VolverPanel.setForeground(Color.BLACK); // Color de primer plano
		VolverPanel.setBackground(Color.WHITE); // Fondo blanco
		VolverPanel.setBounds(0, 0, 41, 39); // Posición y tamaño
		Header.add(VolverPanel); // Añade el panel "Volver" al header

		// Listener para arrastrar la ventana sin bordes
		Header.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Guarda las coordenadas del mouse al presionar, para calcular el arrastre de la ventana.
			 * @param e El evento de mouse.
			 */
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		Header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			/**
			 * Mueve la ventana al arrastrar el mouse, calculando la nueva posición.
			 * @param e El evento de arrastre del mouse.
			 */
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY); // Establece la nueva ubicación de la ventana
			}
		});

		// --- Barra de Navegación (NavBar) ---
		JPanel NavBar = new JPanel();
		NavBar.setBounds(0, 39, 800, 44); // Posición y tamaño de la barra de navegación
		NavBar.setLayout(null); // Deshabilita el gestor de diseño
		NavBar.setBackground(new Color(128, 0, 0)); // Fondo rojo oscuro
		contentPane.add(NavBar); // Añade la barra de navegación al panel de contenido principal
		
		// Etiqueta para mostrar el nombre del estudiante logueado
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setText(estudianteActual.getNombre().toUpperCase()+" "+estudianteActual.getApellido().toUpperCase()); // Nombre dinámico del estudiante
		lblNewLabel.setForeground(Color.WHITE); // Color de texto blanco
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 14)); // Fuente y tamaño
		lblNewLabel.setBounds(80, 1, 191, 40); // Posición y tamaño
		NavBar.add(lblNewLabel); // Añade al NavBar
		
		// Botón de navegación para la sección de Inscripciones
		JButton btnInscripciones = new JButton("INSCRIPCIONES");
		btnInscripciones.setBounds(459, 1, 89, 43); // Posición y tamaño
		btnInscripciones.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Fuente y tamaño
		btnInscripciones.setForeground(Color.WHITE); // Color de texto blanco
		btnInscripciones.setBackground(new Color(128, 0, 0)); // Color de fondo (aunque contentAreaFilled es false)
		btnInscripciones.setOpaque(true); // Hace que el fondo sea visible
		btnInscripciones.setFocusPainted(false); // No pinta el foco alrededor del texto
		btnInscripciones.setContentAreaFilled(false); // No rellena el área de contenido del botón
		btnInscripciones.setBorderPainted(false); // No pinta el borde
		btnInscripciones.setBorder(null); // Elimina cualquier borde existente
		NavBar.add(btnInscripciones); // Añade al NavBar
		
		// Botón de navegación para la sección de Notas
		JButton btnNotas = new JButton("NOTAS");
		btnNotas.setBounds(560, 0, 111, 43); // Posición y tamaño
		btnNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Fuente y tamaño
		btnNotas.setForeground(Color.WHITE); // Color de texto blanco
		btnNotas.setBackground(new Color(128, 0, 0)); // Color de fondo
		btnNotas.setOpaque(true);
		btnNotas.setFocusPainted(false);
		btnNotas.setContentAreaFilled(false);
		btnNotas.setBorderPainted(false);
		btnNotas.setBorder(null);
		NavBar.add(btnNotas); // Añade al NavBar
		
		// Botón de navegación para la sección de Horario
		JButton btnHorario = new JButton("HORARIO");
		btnHorario.setBounds(680, 0, 103, 43); // Posición y tamaño
		btnHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Fuente y tamaño
		btnHorario.setForeground(Color.WHITE); // Color de texto blanco
		btnHorario.setBackground(new Color(128, 0, 0)); // Color de fondo
		btnHorario.setOpaque(true);
		btnHorario.setFocusPainted(false);
		btnHorario.setContentAreaFilled(false);
		btnHorario.setBorderPainted(false);
		btnHorario.setBorder(null);
		NavBar.add(btnHorario); // Añade al NavBar
		
		// --- Panel de Salida (Botón 'X') ---
		JPanel ExitPanel = new JPanel();
		ExitPanel.setLayout(null); // Deshabilita el gestor de diseño
		ExitPanel.setBackground(Color.WHITE); // Fondo blanco
		ExitPanel.setBounds(760, 0, 41, 39); // Posición y tamaño
		Header.add(ExitPanel); // Añade al header
		
		JLabel ExitTxt = new JLabel("X");
		ExitTxt.setBounds(0, 0, 41, 39); // Posición y tamaño
		ExitPanel.add(ExitTxt); // Añade la etiqueta 'X' al panel de salida
		ExitPanel.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Cierra la aplicación al hacer clic en el botón 'X'.
			 * @param e El evento de mouse.
			 */
			public void mouseClicked(MouseEvent e) {
				System.exit(0); // Termina la ejecución de la JVM
			}
			@Override
			/**
			 * Cambia el estilo del botón 'X' al entrar el mouse.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 23)); // Aumenta el tamaño de la fuente
				ExitTxt.setForeground(Color.white); // Cambia el color del texto a blanco
				ExitPanel.setBackground(new Color(128, 0, 0)); // Cambia el color de fondo a rojo oscuro
			}
			@Override
			/**
			 * Restaura el estilo del botón 'X' al salir el mouse.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Restaura el tamaño de la fuente
				ExitTxt.setForeground(Color.black); // Restaura el color del texto a negro
				ExitPanel.setBackground(Color.white); // Restaura el color de fondo a blanco
			}
		});
		ExitTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto 'X'
		ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Fuente y tamaño inicial

		// --- Panel para Volver a la Ventana Anterior ---
		JLabel VolverTxt = new JLabel("<");
		VolverTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto '<'
		VolverTxt.setForeground(new Color(0, 0, 0)); // Color de texto negro
		VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Fuente y tamaño
		VolverTxt.setBounds(0, 0, 41, 39); // Posición y tamaño
		VolverPanel.add(VolverTxt); // Añade la etiqueta '<' al panel "Volver"

		// Etiqueta de título "ESTUDIANTE" en el header
		JLabel Estudiante = new JLabel("ESTUDIANTE");
		Estudiante.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
		Estudiante.setBounds(317, 0, 150, 39); // Posición y tamaño
		Header.add(Estudiante); // Añade al header
		Estudiante.setFont(new Font("Leelawadee UI", Font.BOLD, 14)); // Fuente y tamaño
		Estudiante.setForeground(Color.BLACK); // Color de texto negro
		Estudiante.setBackground(new Color(255, 255, 255)); // Fondo blanco
		Estudiante.setOpaque(true); // Hace que el fondo sea visible
		Estudiante.setBorder(null); // Sin borde

		// Listener para el panel "Volver"
		VolverPanel.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Regresa a la ventana de inicio al hacer clic en el botón 'Volver'.
			 * @param e El evento de mouse.
			 */
			public void mouseClicked(MouseEvent e) {
				dispose(); // Cierra la ventana actual de EstudiantesGUI
				Inicio frameInicio = new Inicio(gestorU, gestorM, s); // Crea una nueva instancia de la ventana de Inicio
				frameInicio.setVisible(true); // Hace visible la ventana de Inicio
			}
			@Override
			/**
			 * Cambia el estilo del botón 'Volver' al entrar el mouse.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				VolverTxt.setFont(new Font("Verdana", Font.BOLD, 23)); // Aumenta el tamaño de la fuente
				VolverTxt.setForeground(Color.white); // Cambia el color del texto a blanco
				VolverPanel.setBackground(new Color(234, 175, 0)); // Cambia el color de fondo a naranja
			}
			@Override
			/**
			 * Restaura el estilo del botón 'Volver' al salir el mouse.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Restaura el tamaño de la fuente
				VolverTxt.setForeground(Color.black); // Restaura el color del texto a negro
				VolverPanel.setBackground(Color.white); // Restaura el color de fondo a blanco
			}
		});
		
		// --- Panel de Inscripciones ---
		JPanel panelInscripciones = new JPanel();
		panelInscripciones.setBackground(Color.WHITE); // Fondo blanco
		panelInscripciones.setSize(655, 363); // Tamaño del panel
		panelInscripciones.setLocation(71, 106); // Posición dentro de contentPane
		panelInscripciones.setLayout(null); // Deshabilita el gestor de diseño
		contentPane.add(panelInscripciones); // Añade al panel de contenido principal

		// Título del panel de preinscripción
		JLabel lblTitulo = new JLabel("PREINSCRIPCIÓN DE MATERIAS");
		lblTitulo.setFont(new Font("Leelawadee UI", Font.BOLD, 16)); // Fuente y tamaño
		lblTitulo.setBounds(197, 20, 244, 30); // Posición y tamaño
		panelInscripciones.add(lblTitulo); // Añade al panel de inscripciones

		// Panel para contener la lista de materias (dentro de un scroll)
		JPanel panelMateriasLista = new JPanel();
		panelMateriasLista.setLayout(new BoxLayout(panelMateriasLista, BoxLayout.Y_AXIS)); // Layout vertical
		panelMateriasLista.setBackground(Color.WHITE); // Fondo blanco

		// ScrollPane para la lista de materias
		JScrollPane scrollMaterias = new JScrollPane(panelMateriasLista);
		scrollMaterias.setBounds(100, 60, 450, 240); // Posición y tamaño
		panelInscripciones.add(scrollMaterias); // Añade al panel de inscripciones
		
		// Etiqueta para mostrar el contador de créditos preinscritos
		JLabel lblCreditos = new JLabel("Créditos preinscritos: 0 / 18");
		lblCreditos.setFont(new Font("Leelawadee UI", Font.BOLD, 13)); // Fuente y tamaño
		lblCreditos.setBounds(250, 300, 300, 20); // Posición y tamaño
		panelInscripciones.add(lblCreditos); // Añade al panel de inscripciones

		// Constante para el límite de créditos por semestre
		final int LIMITE_CREDITOS = 18;
		// Array para mantener el recuento actual de créditos (necesario para ser final en un lambda)
		final int[] creditosActuales = {0};
		
		// Obtiene las materias que el estudiante ya tiene preinscritas
		List<Materia> materiasPreinscritas = s.obtenerSolicitudes(estudianteActual.getCodigo());
		// Mapas para asociar materias con sus paneles de fila y checkboxes
		Map<Materia, JPanel> mapaFilas = new HashMap<>();
		Map<Materia, JCheckBox> mapaMateriasCheckbox = new LinkedHashMap<>();

		// Itera sobre todas las materias disponibles para crear la lista de selección
		for (Materia m : gestorM.getMaterias()) {
		    JPanel fila = new JPanel(new BorderLayout()); // Panel para cada fila de materia
		    fila.setBackground(Color.WHITE); // Fondo blanco
		    fila.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Borde vacío para espaciado

		    JLabel lblMateria = new JLabel(m.getNombre() + " (" + m.getCreditos() + " créditos)"); // Etiqueta de la materia
		    lblMateria.setFont(new Font("Tahoma", Font.PLAIN, 13)); // Fuente y tamaño

		    JCheckBox check = new JCheckBox(); // Checkbox para seleccionar la materia
		    check.setBackground(Color.WHITE); // Fondo blanco del checkbox

		    // Si la materia ya está preinscrita por el estudiante
		    if (materiasPreinscritas.contains(m)) {
		        check.setSelected(true); // Marca el checkbox
		        check.setEnabled(false); // Deshabilita el checkbox para que no se pueda cambiar
		        creditosActuales[0] += m.getCreditos(); // Suma los créditos al total
		    }

		    // Listener para controlar los créditos seleccionados al marcar/desmarcar un checkbox
		    check.addActionListener(e -> {
		        if (check.isSelected()) {
		            // Si al seleccionar excede el límite de créditos
		            if (creditosActuales[0] + m.getCreditos() > LIMITE_CREDITOS) {
		                check.setSelected(false); // Desmarca el checkbox
		                JOptionPane.showMessageDialog(panelInscripciones,
		                        "Ya alcanzaste el límite de 18 créditos por semestre.",
		                        "Límite de créditos",
		                        JOptionPane.WARNING_MESSAGE); // Muestra un mensaje de advertencia
		            } else {
		                creditosActuales[0] += m.getCreditos(); // Suma los créditos
		            }
		        } else {
		            creditosActuales[0] -= m.getCreditos(); // Resta los créditos al desmarcar
		        }

		        // Actualiza la etiqueta de créditos preinscritos
		        lblCreditos.setText("Créditos preinscritos: " + creditosActuales[0] + " / " + LIMITE_CREDITOS);

		        // Bloquea o desbloquea los checkboxes no seleccionados si se alcanzó/liberó el límite de créditos
		        for (Map.Entry<Materia, JCheckBox> entry : mapaMateriasCheckbox.entrySet()) {
		            JCheckBox cb = entry.getValue();
		            if (!cb.isSelected()) { // Solo afecta a los checkboxes que no están marcados
		                cb.setEnabled(creditosActuales[0] < LIMITE_CREDITOS); // Habilita si hay créditos disponibles, deshabilita si no
		            }
		        }
		    });

		    fila.add(lblMateria, BorderLayout.WEST); // Añade la etiqueta a la izquierda de la fila
		    fila.add(check, BorderLayout.EAST); // Añade el checkbox a la derecha de la fila

		    panelMateriasLista.add(fila); // Añade la fila al panel de la lista de materias
		    mapaMateriasCheckbox.put(m, check); // Guarda el checkbox asociado a la materia
		    mapaFilas.put(m, fila); // Guarda el panel de fila asociado a la materia
		}
		panelMateriasLista.revalidate(); // Revalida el layout del panel de materias
		panelMateriasLista.repaint(); // Repinta el panel de materias

		// Muestra los créditos iniciales al cargar la interfaz
		lblCreditos.setText("Créditos preinscritos: " + creditosActuales[0] + " / " + LIMITE_CREDITOS);

		// Botón para enviar la solicitud de preinscripción
		JButton btnEnviarSolicitud = new JButton("Enviar Solicitud");
		btnEnviarSolicitud.setBounds(217, 318, 224, 35); // Posición y tamaño
		btnEnviarSolicitud.setBackground(new Color(255, 255, 255)); // Fondo blanco
		btnEnviarSolicitud.setForeground(new Color(192, 192, 192)); // Color de texto gris claro
		btnEnviarSolicitud.setFont(new Font("Leelawadee UI", Font.BOLD, 12)); // Fuente y tamaño
		panelInscripciones.add(btnEnviarSolicitud); // Añade al panel de inscripciones
		
		// --- DESHABILITAR BOTÓN SI YA TIENE SOLICITUD ENVIADA ---
		// Comprueba si el estudiante ya tiene preinscripciones activas
		if (estudianteActual.getPreinscripciones() != null &&
			    !estudianteActual.getPreinscripciones().isEmpty()) {
			    
			    btnEnviarSolicitud.setEnabled(false); // Deshabilita el botón de enviar
			    btnEnviarSolicitud.setText("Solicitud Enviada"); // Cambia el texto del botón
			    btnEnviarSolicitud.setForeground(Color.GRAY); // Cambia el color del texto a gris

			    // Deshabilita todos los checkboxes de materias
			    for (JCheckBox check : mapaMateriasCheckbox.values()) {
			        check.setEnabled(false);
			    }
			}

		// --- LISTENER DEL BOTÓN ENVIAR SOLICITUD ---
		btnEnviarSolicitud.addActionListener(new ActionListener() {
		    @Override
			/**
			 * Maneja el evento de clic del botón "Enviar Solicitud".
			 * Recopila las materias seleccionadas, las asigna al estudiante y guarda la solicitud.
			 * @param e El evento de acción.
			 */
		    public void actionPerformed(ActionEvent e) {
		        List<Materia> seleccionadas = new ArrayList<>();

		        // Recopila las materias que han sido seleccionadas por el usuario
		        for (Map.Entry<Materia, JCheckBox> entry : mapaMateriasCheckbox.entrySet()) {
		            if (entry.getValue().isSelected()) {
		                seleccionadas.add(entry.getKey());
		            }
		        }

		        // Valida que al menos una materia haya sido seleccionada
		        if (seleccionadas.isEmpty()) {
		            JOptionPane.showMessageDialog(panelInscripciones, 
		                "Selecciona al menos una materia.", 
		                "Error", 
		                JOptionPane.WARNING_MESSAGE); // Muestra un mensaje de advertencia
		            return; // Sale del método
		        }

		        // Preinscribe cada materia seleccionada al estudiante
		        for (Materia m : seleccionadas) {
		            estudianteActual.preinscribirMateria(m);
		        }

		        // Guarda la información de los usuarios (incluyendo las preinscripciones del estudiante)
		        gestorU.guardarUsuarios();

		        // Guarda la solicitud de preinscripción del estudiante
		        s.guardarSolicitud(estudianteActual);
		       
		        // --- Cambiar el estado de la interfaz después de enviar la solicitud ---
		        btnEnviarSolicitud.setEnabled(false); // Deshabilita el botón de enviar
		        btnEnviarSolicitud.setText("Solicitud Enviada"); // Cambia el texto
		        btnEnviarSolicitud.setForeground(Color.GRAY); // Cambia el color del texto

		        // Deshabilita todos los checkboxes para evitar más selecciones
		        for (JCheckBox check : mapaMateriasCheckbox.values()) {
		            check.setEnabled(false);
		        }

		        JOptionPane.showMessageDialog(panelInscripciones, 
		            "Solicitud enviada con éxito.", 
		            "Éxito", 
		            JOptionPane.INFORMATION_MESSAGE); // Muestra un mensaje de éxito
		    }
		});

		panelInscripciones.setVisible(false); // Inicialmente, el panel de inscrip
		
		// --- Panel Horario ---
				JPanel panelHorario = new JPanel();
				panelHorario.setBackground(new Color(255, 255, 255)); // Fondo blanco
				panelHorario.setSize(655, 363); // Tamaño del panel
				panelHorario.setLocation(71, 106); // Posición dentro de contentPane
				panelHorario.setLayout(null); // Deshabilita el gestor de diseño
				contentPane.add(panelHorario); // Añade al panel de contenido principal
				
				// Botón Imprimir Horario (Configuración inicial, su Listener principal está más abajo)
				btnImprimirHorario = new JButton("Imprimir horario");
				// Este ActionListener es problemático, ya que define un segundo ActionListener y configura el botón de nuevo.
				// Debería ser solo la configuración del botón y luego su ActionListener.
				btnImprimirHorario.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// El siguiente código se repite y debería estar fuera de este ActionListener,
						// configurando el botón una sola vez.
				        btnImprimirHorario.setBounds(263, 318, 109, 35);
				        panelHorario.add(btnImprimirHorario);
				     
				        // ******* AÑADE ESTE BLOQUE DE CÓDIGO AQUÍ *******
				        // Agrega el ActionListener para el botón Imprimir/PDF
				        btnImprimirHorario.addActionListener(new ActionListener() {
				            @Override
							/**
							 * Maneja la acción del botón para imprimir o guardar el horario como PDF.
							 * Presenta al usuario un cuadro de diálogo con opciones de impresión.
							 * @param e El evento de acción.
							 */
				            public void actionPerformed(ActionEvent e) {
				                // Opciones para el usuario en el cuadro de diálogo
				                String[] options = {"Imprimir", "Guardar como PDF"};
				                int choice = JOptionPane.showOptionDialog(
				                    EstudiantesGUI.this, // Componente padre para el diálogo
				                    "¿Qué desea hacer con el horario?", // Mensaje a mostrar
				                    "Opciones de Horario", // Título del diálogo
				                    JOptionPane.YES_NO_OPTION, // Tipo de opciones (presenta dos botones)
				                    JOptionPane.QUESTION_MESSAGE, // Icono de pregunta
				                    null, // No se usa un icono personalizado
				                    options, // Los botones "Imprimir" y "Guardar como PDF"
				                    options[0] // Opción por defecto seleccionada ("Imprimir")
				                );

				                if (choice == 0) { // El usuario eligió "Imprimir"
				                    JPanelPrinter printer = new JPanelPrinter(panelHorario); // Crea una instancia del impresor de panel
				                    printer.printPanel(); // Llama al método para iniciar la impresión
				                } else if (choice == 1) { // El usuario eligió "Guardar como PDF"
				                    JFileChooser fileChooser = new JFileChooser(); // Crea un selector de archivos
				                    fileChooser.setDialogTitle("Guardar Horario como PDF"); // Título del diálogo de guardar
				                    // Sugiere un nombre de archivo único basado en la fecha/hora actual para evitar sobrescribir
				                    fileChooser.setSelectedFile(new File("Horario_" + System.currentTimeMillis() + ".pdf")); 
				                    
				                    int userSelection = fileChooser.showSaveDialog(EstudiantesGUI.this); // Muestra el diálogo de guardar archivo
				                    
				                    if (userSelection == JFileChooser.APPROVE_OPTION) { // Si el usuario hizo clic en "Guardar"
				                        File fileToSave = fileChooser.getSelectedFile(); // Obtiene el archivo seleccionado por el usuario
				                        String filePath = fileToSave.getAbsolutePath(); // Obtiene la ruta absoluta del archivo
				                        if (!filePath.toLowerCase().endsWith(".pdf")) {
				                            filePath += ".pdf"; // Asegura que el archivo tenga la extensión .pdf
				                        }
				                        JPanelToPDF pdfGenerator = new JPanelToPDF(); // Crea una instancia del generador de PDF
				                        pdfGenerator.createPdfFromPanel(panelHorario, filePath); // Genera el PDF del panel en la ruta especificada
				                    }
				                }
				            }
				        });
				        // ******* FIN DEL BLOQUE DE CÓDIGO AÑADIDO *******
						// Estas llamadas también se repiten y deberían estar fuera de este ActionListener.
				        panelHorario.revalidate();
				        panelHorario.repaint();
				        
				    } // Cierre del ActionListener de btnHorario (este es redundante o mal ubicado)
				});
				// Configuración visual del botón Imprimir Horario (repetida, debería ser única)
				btnImprimirHorario.setOpaque(true);
				btnImprimirHorario.setForeground(Color.WHITE);
				btnImprimirHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
				btnImprimirHorario.setFocusPainted(false);
				btnImprimirHorario.setBorderPainted(false);
				btnImprimirHorario.setBorder(null);
				btnImprimirHorario.setBackground(new Color(128, 0, 0)); // Color de fondo rojo oscuro
				btnImprimirHorario.setBounds(263, 318, 109, 35); // Posición y tamaño
						
				// LIMPIAR panel (esto limpia el panel antes de añadir componentes, lo cual es útil si se recarga el horario)
				panelHorario.removeAll();

				// Crear título del horario
				JLabel lblVerHorario = new JLabel("HORARIO");
				lblVerHorario.setBounds(163, 10, 296, 40); // Posición y tamaño
				lblVerHorario.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
				lblVerHorario.setForeground(Color.BLACK); // Color de texto negro
				lblVerHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 28)); // Fuente y tamaño
				panelHorario.add(lblVerHorario); // Añade al panel de horario
				    
				// Agregar botón imprimir al panel de horario
				panelHorario.add(btnImprimirHorario);
				
				panelHorario.setVisible(false); // Oculta el panel de horario al inicio
				
				// --- Panel de Notas del Estudiante ---
				JPanel panelNotas = new JPanel();
				panelNotas.setBackground(Color.WHITE); // Fondo blanco
				panelNotas.setBounds(71, 106, 655, 363); // Posición y tamaño
				panelNotas.setLayout(null); // Deshabilita el gestor de diseño
				contentPane.add(panelNotas); // Añade al panel de contenido principal

				// Título del panel de notas
				JLabel lblVerificarNotas = new JLabel("NOTAS");
				lblVerificarNotas.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
				lblVerificarNotas.setForeground(Color.BLACK); // Color de texto negro
				lblVerificarNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 28)); // Fuente y tamaño
				lblVerificarNotas.setBounds(180, 10, 296, 40); // Posición y tamaño
				panelNotas.add(lblVerificarNotas);

				// Modelo de la tabla de notas
				DefaultTableModel modeloTablaNotas = new DefaultTableModel(
				    new Object[]{"Materia", "Nota 1", "Nota 2", "Nota 3", "Promedio"}, 0 // Columnas y 0 filas iniciales
				) {
				    @Override
					/**
					 * Sobrescribe el método para que las celdas de la tabla no sean editables.
					 * @param row La fila de la celda.
					 * @param column La columna de la celda.
					 * @return Siempre false, indicando que la celda no es editable.
					 */
				    public boolean isCellEditable(int row, int column) {
				        return false; // No editable
				    }
				};

				// Tabla para mostrar las notas
				JTable tablaNotas = new JTable(modeloTablaNotas); // Crea la tabla con el modelo definido
				tablaNotas.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente de las celdas
				tablaNotas.setRowHeight(30); // Altura de las filas
				tablaNotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13)); // Fuente del encabezado
				tablaNotas.getTableHeader().setReorderingAllowed(false); // Evita que se puedan reordenar las columnas
				
				// 🔒 3. Bloquear redimensión manual de columnas y ajustar anchos
				tablaNotas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Deshabilita el auto-redimensionamiento de columnas
				tablaNotas.getTableHeader().setResizingAllowed(false); // Evita que se puedan redimensionar las columnas manualmente
				int[] anchos = {182, 104, 104, 104, 104}; // Anchos preferidos para cada columna
				for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
				    tablaNotas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]); // Aplica los anchos
				}

				// Centrado del contenido de las celdas
				DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
				centro.setHorizontalAlignment(SwingConstants.CENTER); // Alinea el texto al centro
				for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
				    tablaNotas.getColumnModel().getColumn(i).setCellRenderer(centro); // Aplica el renderizador centrado a todas las columnas
				}

				// ScrollPane para la tabla de notas
				JScrollPane scroll = new JScrollPane(tablaNotas);
				scroll.setBounds(20, 60, 600, 230); // Posición y tamaño
				panelNotas.add(scroll); // Añade el scrollpane con la tabla al panel de notas

				// Ocultar inicialmente el panel de notas
				panelNotas.setVisible(false);
				
				// --- ACCIONES DE LOS BOTONES DE NAVEGACIÓN ---

				/**
				 * Listener para el botón "INSCRIPCIONES" en la barra de navegación.
				 * Muestra el panel de inscripciones y oculta los demás.
				 */
				btnInscripciones.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						panelNotas.setVisible(false);
						panelInscripciones.setVisible(true);
						panelHorario.setVisible(false);
						panelInicio.setVisible(false); // Asegura que el panel de inicio también se oculte
						contentPane.setComponentZOrder(panelInscripciones, 1); // Coloca el panel de inscripciones al frente
					    contentPane.revalidate(); // Revalida el layout
					    contentPane.repaint(); // Repinta el contenedor
					}
				});

				/**
				 * Listener para el botón "NOTAS" en la barra de navegación.
				 * Muestra el panel de notas, oculta los demás y carga las notas del estudiante.
				 */
				btnNotas.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        panelNotas.setVisible(true); // Hace visible el panel de notas
				        panelInscripciones.setVisible(false);
				        panelHorario.setVisible(false);
				        panelInicio.setVisible(false);
				        contentPane.setComponentZOrder(panelNotas, 1); // Coloca el panel de notas al frente
				        contentPane.revalidate();
				        contentPane.repaint();

				        // Actualiza la lista de materias desde el gestor para asegurar que esté sincronizada
				        List<Materia> materiasCargadas = gestorM.getMaterias();

				        // Limpiar la tabla de notas antes de cargar nuevos datos
				        modeloTablaNotas.setRowCount(0);

				        // Recorre las materias cargadas para encontrar las que el estudiante está cursando
				        for (Materia materia : materiasCargadas) {
				            if (materia.getEstudiantes().contains(estudianteActual)) { // Verifica si el estudiante está en esta materia
				                List<Double> notas = materia.getNotasEstudiante(estudianteActual); // Obtiene las notas del estudiante para esta materia
				                double n1 = 0.0, n2 = 0.0, n3 = 0.0, promedio = 0.0;

				                // Si hay notas y son 3 (se asume 3 notas por materia)
				                if (notas != null && notas.size() == 3) {
				                    n1 = notas.get(0);
				                    n2 = notas.get(1);
				                    n3 = notas.get(2);
				                    // Calcula el promedio y lo redondea a dos decimales
				                    promedio = Math.round(materia.calcularPromedio(estudianteActual) * 100.0) / 100.0;
				                }

				                // Añade una nueva fila a la tabla con la información de la materia y sus notas
				                modeloTablaNotas.addRow(new Object[]{
				                    materia.getNombre(),
				                    String.format("%.2f", n1), // Formatea las notas a dos decimales
				                    String.format("%.2f", n2),
				                    String.format("%.2f", n3),
				                    String.format("%.2f", promedio)
				                });
				            }
				        }
				    }
				});

				/**
				 * Listener para el botón "HORARIO" en la barra de navegación.
				 * Muestra el panel de horario, oculta los demás y genera dinámicamente la tabla de horario.
				 */
				btnHorario.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        panelNotas.setVisible(false); // Oculta otros paneles
				        panelInscripciones.setVisible(false);
				        panelHorario.setVisible(true); // Hace visible el panel de horario
				        panelInicio.setVisible(false);
				        contentPane.setComponentZOrder(panelHorario, 1); // Coloca el panel de horario al frente
				        contentPane.revalidate();
				        contentPane.repaint();

				        panelHorario.removeAll(); // Limpia el panel de horario para recargar su contenido
				        panelHorario.setLayout(null); // Asegura layout nulo para posicionamiento manual

				        JLabel lblVerHorario = new JLabel("HORARIO"); // Título del panel de horario
				        lblVerHorario.setBounds(163, 10, 296, 40);
				        lblVerHorario.setHorizontalAlignment(SwingConstants.CENTER);
				        lblVerHorario.setForeground(Color.BLACK);
				        lblVerHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 28));
				        panelHorario.add(lblVerHorario);

				        // Modelo de la tabla para el horario (no editable)
				        DefaultTableModel modelo = new DefaultTableModel() {
				            @Override
							/**
							 * Sobrescribe el método para que las celdas de la tabla de horario no sean editables.
							 * @param row La fila de la celda.
							 * @param column La columna de la celda.
							 * @return Siempre false, indicando que la celda no es editable.
							 */
				            public boolean isCellEditable(int row, int column) {
				                return false;
				            }
				        };

				        // Define los identificadores de columna para la tabla de horario
				        modelo.setColumnIdentifiers(new Object[]{
				            "Cod.", "Nombre", "Créditos", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"
				        });

				        // Carga las materias desde el gestor (debe ser la fuente de verdad)
				        List<Materia> materiasCargadas = gestorM.getMaterias();

				        // Itera sobre las materias para construir las filas del horario
				        for (Materia materia : materiasCargadas) {
				            if (materia.getEstudiantes().contains(estudianteActual)) { // Si el estudiante está inscrito en esta materia
				                String[] fila = new String[9]; // Array para los datos de la fila de la tabla
				                fila[0] = String.valueOf(materia.getCodigo()); // Código de la materia
				                fila[1] = materia.getNombre(); // Nombre de la materia
				                fila[2] = String.valueOf(materia.getCreditos()); // Créditos de la materia

				                for (int i = 3; i < 9; i++) fila[i] = ""; // Inicializa las celdas de días vacías

				                // Asigna los horarios a los días correspondientes
				                for (String dia : materia.getDias()) {
				                    int col = switch (dia.toLowerCase()) { // Determina la columna según el día
				                        case "lun" -> 3;
				                        case "mar" -> 4;
				                        case "mie" -> 5;
				                        case "jue" -> 6;
				                        case "vie" -> 7;
				                        case "sab" -> 8;
				                        default -> -1; // Valor por defecto si el día no coincide
				                    };
				                    if (col != -1) {
				                        fila[col] = materia.getHoraInicio() + " - " + materia.getHoraFin(); // Asigna el rango horario
				                    }
				                }

				                modelo.addRow(fila); // Añade la fila completa al modelo de la tabla
				            }
				        }

				        JTable tabla = new JTable(modelo); // Crea la tabla con el modelo poblado
				        tabla.setRowHeight(40); // Altura de las filas
				        tabla.getTableHeader().setReorderingAllowed(false); // Evita reordenar columnas
				        tabla.setGridColor(new Color(200, 200, 200)); // Color de las líneas de la cuadrícula
				        tabla.setShowHorizontalLines(true); // Muestra líneas horizontales
				        tabla.setShowVerticalLines(false); // Oculta líneas verticales
				        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente de las celdas
				        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13)); // Fuente del encabezado
				        tabla.setFillsViewportHeight(true); // Hace que la tabla ocupe toda la altura del viewport
				        tabla.setBackground(Color.WHITE); // Fondo blanco
				        tabla.setSelectionBackground(new Color(220, 220, 255)); // Color de fondo al seleccionar celda

				        // Centrar contenido de las celdas de la tabla de horario
				        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
				        centro.setHorizontalAlignment(SwingConstants.CENTER);
				        for (int i = 0; i < tabla.getColumnCount(); i++) {
				            tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
				        }

				        // Minimizar columnas de días si están vacías (para ahorrar espacio)
				        for (int col = 3; col <= 8; col++) { // Recorre las columnas de los días
				            boolean vacía = true;
				            for (int fila = 0; fila < modelo.getRowCount(); fila++) {
				                if (modelo.getValueAt(fila, col) != null &&
				                    !modelo.getValueAt(fila, col).toString().isBlank()) {
				                    vacía = false; // Si encuentra contenido, la columna no está vacía
				                    break;
				                }
				            }
				            if (vacía) { // Si la columna está vacía, se minimiza su ancho
				                tabla.getColumnModel().getColumn(col).setMinWidth(30);
				                tabla.getColumnModel().getColumn(col).setMaxWidth(30);
				                tabla.getColumnModel().getColumn(col).setPreferredWidth(30);
				            }
				        }
				        
				        JScrollPane scrollHorario = new JScrollPane(tabla); // Crea un scrollpane para la tabla de horario
				        scrollHorario.setBounds(20, 60, 600, 250); // Posición y tamaño
				        panelHorario.add(scrollHorario); // Añade al panel de horario
				        
				        // Agrega el botón imprimir al panel (asegurando su correcta ubicación después de la tabla)
				        btnImprimirHorario.setBounds(263, 318, 109, 35);
				        panelHorario.add(btnImprimirHorario);
				     
				        panelHorario.revalidate(); // Revalida el layout del panel de horario
				        panelHorario.repaint(); // Repinta el panel de horario
				        
				    }
				});
				
				// --- Listeners de Eventos de Mouse para los botones de la barra de navegación ---
				// Cambian el color de fondo de los botones al pasar el mouse por encima o al presionarlos.

				btnInscripciones.addMouseListener(new MouseAdapter() {
				    @Override
					/**
					 * Cambia el color de fondo del botón "INSCRIPCIONES" al entrar el mouse.
					 * @param e El evento de mouse.
					 */
				    public void mouseEntered(MouseEvent e) {
				        btnInscripciones.setBackground(new Color(80, 0, 0)); // Color más oscuro
				        btnInscripciones.repaint();
				    }

				    @Override
					/**
					 * Restaura el color de fondo del botón "INSCRIPCIONES" al salir el mouse.
					 * @param e El evento de mouse.
					 */
				    public void mouseExited(MouseEvent e) {
				    	btnInscripciones.setBackground(new Color(128, 0, 0)); // Color original
				    	btnInscripciones.repaint();
				    }

				    @Override
					/**
					 * Cambia el color de fondo del botón "INSCRIPCIONES" al ser presionado.
					 * @param e El evento de mouse.
					 */
				    public void mousePressed(MouseEvent e) {
				    	btnInscripciones.setBackground(new Color(100, 0, 0)); // Un color distinto para el clic
				    	btnInscripciones.repaint();
				    }
				});
				
				btnNotas.addMouseListener(new MouseAdapter() {
				    @Override
					/**
					 * Cambia el color de fondo del botón "NOTAS" al entrar el mouse.
					 * @param e El evento de mouse.
					 */
				    public void mouseEntered(MouseEvent e) {
				        btnNotas.setBackground(new Color(80, 0, 0));
				        btnNotas.repaint();
				    }

				    @Override
					/**
					 * Restaura el color de fondo del botón "NOTAS" al salir el mouse.
					 * @param e El evento de mouse.
					 */
				    public void mouseExited(MouseEvent e) {
				    	btnNotas.setBackground(new Color(128, 0, 0));
				    	btnNotas.repaint();
				    }

				    @Override
					/**
					 * Cambia el color de fondo del botón "NOTAS" al ser presionado.
					 * @param e El evento de mouse.
					 */
				    public void mousePressed(MouseEvent e) {
				    	btnNotas.setBackground(new Color(100, 0, 0)); // un color distinto para el clic
				    	btnNotas.repaint();
				    }
				});
				
				btnHorario.addMouseListener(new MouseAdapter() {
				    @Override
					/**
					 * Cambia el color de fondo del botón "HORARIO" al entrar el mouse.
					 * @param e El evento de mouse.
					 */
				    public void mouseEntered(MouseEvent e) {
				        btnHorario.setBackground(new Color(80, 0, 0));
				        btnHorario.repaint();
				    }

				    @Override
					/**
					 * Restaura el color de fondo del botón "HORARIO" al salir el mouse.
					 * @param e El evento de mouse.
					 */
				    public void mouseExited(MouseEvent e) {
				    	btnHorario.setBackground(new Color(128, 0, 0));
				    	btnHorario.repaint();
				    }

				    @Override
					/**
					 * Cambia el color de fondo del botón "HORARIO" al ser presionado.
					 * @param e El evento de mouse.
					 */
				    public void mousePressed(MouseEvent e) {
				    	btnHorario.setBackground(new Color(100, 0, 0)); // un color distinto para el clic
				    	btnHorario.repaint();
				    }
				});
				
				// --- Panel del Escudo (en la NavBar) ---
				JPanel Escudo = new JPanel();
				Escudo.setForeground(new Color(0, 0, 0)); // Color de primer plano
				Escudo.setLayout(null); // Deshabilita el gestor de diseño
				Escudo.setBackground(new Color(128, 0, 0)); // Fondo rojo oscuro
				Escudo.setBounds(10, 0, 53, 43); // Posición y tamaño
				NavBar.add(Escudo); // Añade al NavBar

				JLabel EscudoTxt = new JLabel(""); // Etiqueta para el icono del escudo
				EscudoTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
				EscudoTxt.setBounds(0, 0, 53, 43); // Posición y tamaño
				Escudo.add(EscudoTxt); // Añade al panel del escudo

				// Listener para el panel del escudo (funciona como botón para volver al inicio)
				Escudo.addMouseListener(new MouseAdapter() {
					@Override
					/**
					 * Muestra el panel de inicio y oculta los demás paneles al hacer clic en el escudo.
					 * @param e El evento de mouse.
					 */
					public void mouseClicked(MouseEvent e) {
						// Mostrar panel de inicio como hace el botón
						panelInicio.setVisible(true);
						panelInscripciones.setVisible(false);
						panelNotas.setVisible(false);
						panelHorario.setVisible(false);

						contentPane.setComponentZOrder(panelInicio, 0); // Coloca el panel de inicio al frente
						contentPane.revalidate();
						contentPane.repaint();
					}
					
					@Override
					/**
					 * Cambia el color de fondo del panel del escudo al entrar el mouse.
					 * @param e El evento de mouse.
					 */
					public void mouseEntered(MouseEvent e) {
						Escudo.setBackground(new Color(80, 0, 0)); // Color más oscuro
					}

					@Override
					/**
					 * Restaura el color de fondo del panel del escudo al salir el mouse.
					 * @param e El evento de mouse.
					 */
					public void mouseExited(MouseEvent e) {
						Escudo.setBackground(new Color(128, 0, 0)); // Color original
					}
				});

				// Carga y escala la imagen del escudo para el JLabel EscudoTxt
				ImageIcon ico1 = new ImageIcon(Login.class.getResource("/Imagenes/output-onlinepngtools.png"));
				Image imagen1 = ico1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				ImageIcon imagenEscalada1 = new ImageIcon(imagen1);
				
				// Asignar imagen al JLabel del escudo
				EscudoTxt.setIcon(imagenEscalada1);
						
				// --- Imagen de Fondo General (repetida, posible duplicidad) ---
				// Esta parte parece ser una duplicación del `imgFondo` del `panelInicio`
				// y podría estar causando un renderizado incorrecto o innecesario.
				// Revisa si es realmente necesaria o si `imgFondo` ya cumple su propósito.
				JLabel imgFondo3 = new JLabel("");
				imgFondo3.setBounds(-87, 83, 974, 461); // Posición y tamaño
				contentPane.add(imgFondo3); // Añade al panel de contenido principal
				imgFondo3.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
				// Cargar y escalar la imagen
				ImageIcon ico3 = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
				Image imagen3 = ico3.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);
				ImageIcon imagenEscalada3 = new ImageIcon(imagen3);
				// Asignar imagen al JLabel
				imgFondo3.setIcon(imagenEscalada3);
						
			} // Cierre del constructor de EstudiantesGUI
			
		    /**
		     * Clase interna privada para manejar la impresión de un JPanel.
		     * Implementa la interfaz {@link java.awt.print.Printable}.
		     */
		    private class JPanelPrinter implements Printable {
		        private JPanel panelToPrint;

				/**
				 * Constructor para JPanelPrinter.
				 * @param panel El JPanel que se desea imprimir.
				 */
		        public JPanelPrinter(JPanel panel) {
		            this.panelToPrint = panel;
		        }

		        @Override
				/**
				 * Método de la interfaz Printable que define cómo se dibuja el contenido para imprimir.
				 * @param graphics El contexto gráfico para dibujar.
				 * @param pageFormat El formato de la página a imprimir.
				 * @param pageIndex El índice de la página actual (solo soporta la primera página).
				 * @return PAGE_EXISTS si la página existe y se dibuja, NO_SUCH_PAGE si no hay más páginas.
				 * @throws PrinterException Si ocurre un error durante la impresión.
				 */
		        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		            if (pageIndex > 0) {
		                return NO_SUCH_PAGE; // Solo imprime la primera página
		            }

		            Graphics2D g2d = (Graphics2D) graphics;
		            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY()); // Traslada el origen a la esquina imprimible

		            // Escala el panel para que quepa en el área imprimible de la página
		            double scaleX = pageFormat.getImageableWidth() / panelToPrint.getWidth();
		            double scaleY = pageFormat.getImageableHeight() / panelToPrint.getHeight();
		            // Usa el factor de escala más pequeño para asegurar que todo el panel quepa sin cortar
		            double scale = Math.min(scaleX, scaleY); 
		            g2d.scale(scale, scale); // Aplica la escala

		            panelToPrint.printAll(g2d); // Dibuja todos los componentes del panel en el contexto gráfico
		            return PAGE_EXISTS; // Indica que la página fue dibujada
		        }

				/**
				 * Inicia el proceso de impresión del panel.
				 * Muestra un diálogo de impresión para que el usuario configure las opciones.
				 */
		        public void printPanel() {
		            PrinterJob job = PrinterJob.getPrinterJob(); // Obtiene una instancia de PrinterJob
		            job.setPrintable(this); // Asigna esta clase como el objeto imprimible

		            if (job.printDialog()) { // Muestra el diálogo de impresión y espera la confirmación del usuario
		                try {
		                    job.print(); // Si el usuario confirma, inicia la impresión
		                } catch (PrinterException ex) {
		                    JOptionPane.showMessageDialog(null, "Error al imprimir: " + ex.getMessage(), "Error de Impresión", JOptionPane.ERROR_MESSAGE);
		                    System.err.println("Error al imprimir: " + ex.getMessage());
		                }
		            }
		        }
		    }

		    /**
		     * Clase interna privada para generar un archivo PDF a partir de un JPanel.
		     * Utiliza la librería Apache PDFBox.
		     */
		    private class JPanelToPDF {
				/**
				 * Genera un documento PDF a partir de un JPanel.
				 * Captura el panel como una imagen y la incrusta en una nueva página PDF.
				 * @param panel El JPanel que se desea convertir a PDF.
				 * @param filePath La ruta completa del archivo donde se guardará el PDF.
				 */
		        public void createPdfFromPanel(JPanel panel, String filePath) {
		            PDDocument document = new PDDocument(); // Crea un nuevo documento PDF
		            try {
		                // Captura el contenido del JPanel como una imagen BufferedImage
		                BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		                Graphics2D g2 = image.createGraphics(); // Obtiene un contexto gráfico para dibujar en la imagen
		                panel.printAll(g2); // Dibuja todos los componentes del panel en la imagen
		                g2.dispose(); // Libera los recursos del contexto gráfico

		                PDPage page = new PDPage(); // Crea una nueva página PDF
		                document.addPage(page); // Añade la página al documento

		                // Convierte la imagen BufferedImage en un objeto PDImageXObject para incrustar en el PDF
		                PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);

		                // Inicia un nuevo flujo de contenido para la página
		                PDPageContentStream contentStream = new PDPageContentStream(document, page);
		                // Dibuja la imagen en la página, ajustándola al tamaño completo de la página
		                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

		                contentStream.close(); // Cierra el flujo de contenido
		                document.save(filePath); // Guarda el documento PDF en la ruta especificada
		                JOptionPane.showMessageDialog(null, "PDF generado exitosamente en: " + filePath, "PDF Creado", JOptionPane.INFORMATION_MESSAGE);

		            } catch (IOException e) {
		                JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error al Generar PDF", JOptionPane.ERROR_MESSAGE);
		                System.err.println("Error al generar el PDF: " + e.getMessage());
		            } finally {
		                try {
		                    if (document != null) {
		                        document.close(); // Asegúrate de cerrar el documento PDF para liberar recursos
		                    }
		                } catch (IOException e) {
		                    System.err.println("Error al cerrar el documento PDF: " + e.getMessage());
		                }
		            }
		        }
		    }
		} // <--- Este es el último corchete de cierre de la clase EstudiantesGUI