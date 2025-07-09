package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import Kernel.Estudiante;
import Kernel.Materia;
import Kernel.Profesor;
import Kernel.Usuario;
import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

/**
 * La clase `ProfesorGUI` representa la interfaz gráfica de usuario para los profesores
 * en el sistema. Permite a los profesores gestionar notas de estudiantes y consultar
 * su horario de clases.
 */
public class ProfesorGUI extends JFrame {
	private JPanel contentPane; // Panel principal de contenido de la ventana
	private DefaultListModel<String> modeloLista = new DefaultListModel<>(); // Modelo para una lista (no utilizada directamente en el código proporcionado)
	private List<Usuario> estudiantes = new ArrayList<>(); // Lista de estudiantes (no utilizada directamente en el código proporcionado)
	private List<Usuario> profesores = new ArrayList<>(); // Lista de profesores (no utilizada directamente en el código proporcionado)

	// Variables para almacenar la posición del mouse y permitir mover la ventana
	int xMouse, yMouse;

	/**
	 * Constructor para crear la ventana de la interfaz de profesor.
	 * Inicializa y configura todos los componentes de la interfaz de usuario,
	 * incluyendo la barra de título personalizada, la barra de navegación y los
	 * paneles para la gestión de notas y la visualización del horario.
	 *
	 * @param profesor El objeto Profesor que ha iniciado sesión, cuyos datos se mostrarán.
	 * @param gestorU Objeto GestorUsuarios para la gestión de usuarios (necesario para buscar estudiantes).
	 * @param gestorM Objeto GestorMaterias para la gestión de materias y sus datos.
	 * @param s Objeto GestionSolicitudes (no utilizado directamente en el código proporcionado).
	 */
	public ProfesorGUI(Profesor profesor, GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {

		// Configuración básica de la ventana JFrame
		setLocationByPlatform(true); // Permite al SO decidir la ubicación inicial de la ventana
		setUndecorated(true); // Elimina los bordes, la barra de título y los botones nativos de la ventana
		setResizable(false); // Impide que el usuario pueda redimensionar la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define la operación por defecto al cerrar la ventana
		setBounds(100, 100, 800, 500); // Establece la posición y el tamaño de la ventana
		contentPane = new JPanel(); // Crea el panel principal de contenido
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Establece un borde vacío al panel de contenido
		setContentPane(contentPane); // Asigna el panel de contenido a la ventana
		contentPane.setLayout(null); // Establece el layout a null para posicionar componentes manualmente

		// Panel de la cabecera (Header) para arrastrar la ventana y mostrar controles de la ventana
		JPanel Header = new JPanel();

		// Listener para el evento de presionar el mouse en el header (para arrastrar la ventana)
		Header.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Método para guardar la información de la posición del mouse al presionar.
			 * @param e El evento de mouse.
			 */
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX(); // Guarda la coordenada X del mouse
				yMouse = e.getY(); // Guarda la coordenada Y del mouse
			}
		});
		// Listener para el evento de arrastrar el mouse en el header (para mover la ventana)
		Header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			/**
			 * Método para establecer la ubicación de la ventana según el movimiento del mouse.
			 * @param e El evento de movimiento del mouse.
			 */
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen(); // Obtiene la coordenada X de la pantalla
				int y = e.getYOnScreen(); // Obtiene la coordenada Y de la pantalla
				setLocation(x - xMouse, y - yMouse); // Establece la nueva ubicación de la ventana
			}
		});

		// Configuración visual del Header
		Header.setBackground(new Color(255, 255, 255)); // Color de fondo blanco
		Header.setBounds(0, 0, 800, 39); // Posición y tamaño
		contentPane.add(Header); // Añade el header al panel de contenido
		Header.setLayout(null); // Establece el layout a null

		// Panel para el botón "Volver"
		JPanel VolverPanel = new JPanel();
		VolverPanel.setForeground(new Color(0, 0, 0)); // Color de primer plano
		VolverPanel.setLayout(null); // Layout nulo
		VolverPanel.setBackground(Color.WHITE); // Color de fondo
		VolverPanel.setBounds(0, 0, 41, 39); // Posición y tamaño
		Header.add(VolverPanel); // Añade el panel al header

		JLabel VolverTxt = new JLabel("<"); // Etiqueta para el texto del botón "Volver"
		VolverTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
		VolverTxt.setForeground(new Color(0, 0, 0)); // Color del texto
		VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Fuente del texto
		VolverTxt.setBounds(0, 0, 41, 39); // Posición y tamaño
		VolverPanel.add(VolverTxt); // Añade la etiqueta al panel "Volver"
		// Listener para el panel "Volver"
		VolverPanel.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Maneja el evento de clic del mouse para volver a la ventana de inicio.
			 * @param e El evento de mouse.
			 */
			public void mouseClicked(MouseEvent e) {
				dispose(); // Cierra la ventana actual
				Inicio frameInicio = new Inicio(gestorU, gestorM, s); // Crea una nueva instancia de la ventana de Inicio
				frameInicio.setVisible(true); // Hace visible la ventana de Inicio
			}

			@Override
			/**
			 * Maneja el evento de entrada del mouse para cambiar el estilo del texto y el fondo.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				VolverTxt.setFont(new Font("Verdana", Font.BOLD, 23)); // Cambia el tamaño de la fuente
				VolverTxt.setForeground(Color.white); // Cambia el color del texto a blanco
				VolverPanel.setBackground(new Color(234, 175, 0)); // Cambia el color de fondo a naranja
			}

			@Override
			/**
			 * Maneja el evento de salida del mouse para restaurar el estilo del texto y el fondo.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Restaura el tamaño de la fuente
				VolverTxt.setForeground(Color.black); // Restaura el color del texto a negro
				VolverPanel.setBackground(Color.white); // Restaura el color de fondo a blanco
			}
		});

		// Panel para el botón de salir (Exit)
		JPanel ExitPanel = new JPanel();
		ExitPanel.setBackground(new Color(255, 255, 255)); // Color de fondo
		ExitPanel.setBounds(759, 0, 41, 39); // Posición y tamaño
		Header.add(ExitPanel); // Añade el panel al header
		ExitPanel.setLayout(null); // Layout nulo

		JLabel ExitTxt = new JLabel("X"); // Etiqueta para el texto del botón "X"
		ExitTxt.setBounds(0, 0, 41, 39); // Posición y tamaño
		ExitPanel.add(ExitTxt); // Añade la etiqueta al panel "Exit"
		// Listener para el panel "Exit"
		ExitPanel.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Maneja el evento de clic del mouse para cerrar la aplicación.
			 * @param e El evento de mouse.
			 */
			public void mouseClicked(MouseEvent e) {
				System.exit(0); // Cierra la aplicación
			}

			@Override
			/**
			 * Maneja el evento de entrada del mouse para cambiar el estilo del texto y el fondo.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 23)); // Cambia el tamaño de la fuente
				ExitTxt.setForeground(Color.white); // Cambia el color del texto a blanco
				ExitPanel.setBackground(new Color(128, 0, 0)); // Cambia el color de fondo a rojo oscuro
			}

			@Override
			/**
			 * Maneja el evento de salida del mouse para restaurar el estilo del texto y el fondo.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Restaura el tamaño de la fuente
				ExitTxt.setForeground(Color.black); // Restaura el color del texto a negro
				ExitPanel.setBackground(Color.white); // Restaura el color de fondo a blanco
			}
		});
		ExitTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
		ExitTxt.setFont(new Font("Verdana", Font.BOLD, 22)); // Fuente del texto

		JLabel Docente = new JLabel("DOCENTE"); // Etiqueta para el título del rol "DOCENTE" en el header
		Docente.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
		Docente.setBounds(325, 0, 150, 39); // Posición y tamaño
		Header.add(Docente); // Añade la etiqueta al header
		Docente.setFont(new Font("Leelawadee UI", Font.BOLD, 14)); // Fuente
		Docente.setForeground(Color.BLACK); // Color del texto
		Docente.setBackground(new Color(255, 255, 255)); // Color de fondo por defecto
		Docente.setOpaque(true); // Hace que el fondo sea visible
		Docente.setBorder(null); // Sin borde

		// Barra de navegación (NavBar)
		JPanel NavBar = new JPanel();
		NavBar.setBackground(new Color(128, 0, 0)); // Color de fondo rojo oscuro
		NavBar.setBounds(0, 39, 800, 44); // Posición y tamaño
		contentPane.add(NavBar); // Añade la barra de navegación al panel de contenido
		NavBar.setLayout(null); // Layout nulo

		JLabel lblNewLabel = new JLabel(
				profesor.getNombre().toUpperCase() + " " + profesor.getApellido().toUpperCase()); // Etiqueta con el nombre del profesor
		lblNewLabel.setForeground(Color.WHITE); // Color del texto blanco
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 14)); // Fuente
		lblNewLabel.setBounds(80, 1, 191, 40); // Posición y tamaño
		NavBar.add(lblNewLabel); // Añade la etiqueta a la barra de navegación

		// ---------------- PANEL INICIO ----------------
		JPanel panelinicio = new JPanel();
		panelinicio.setBounds(0, 83, 800, 417); // Posición y tamaño
		contentPane.add(panelinicio); // Añade el panel de inicio al panel de contenido
		panelinicio.setLayout(null); // Layout nulo

		// Cargar y escalar la imagen de fondo para el panel de inicio
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen = ico.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH); // Escala la imagen
		ImageIcon imagenEscalada = new ImageIcon(imagen); // Crea un nuevo ImageIcon con la imagen escalada

		// Panel interno para las instrucciones del docente
		JPanel panelInstruccionesDocente = new JPanel();
		panelInstruccionesDocente.setLayout(null); // Layout nulo
		panelInstruccionesDocente.setBackground(Color.WHITE); // Color de fondo blanco
		panelInstruccionesDocente.setBounds(70, 22, 656, 364); // Posición y tamaño
		panelinicio.add(panelInstruccionesDocente); // Añade el panel de instrucciones al panel de inicio

		// Mensaje de bienvenida para el docente
		JLabel lblBienvenidaDoc = new JLabel("¡Bienvenido Docente!"); // Etiqueta de bienvenida
		lblBienvenidaDoc.setFont(new Font("Leelawadee UI", Font.BOLD, 20)); // Fuente
		lblBienvenidaDoc.setBounds(50, 30, 400, 30); // Posición y tamaño
		panelInstruccionesDocente.add(lblBienvenidaDoc); // Añade la etiqueta al panel de instrucciones

		// Área de texto con instrucciones para el docente
		JTextArea instruccionesDoc = new JTextArea("Puedes realizar las siguientes funciones:\n\n"
				+ "• Ingresar y modificar las notas finales de los estudiantes asignados.\n"
				+ "• Consultar tu horario semanal detallado por curso, grupo y aula.\n"
				+ "• Ver la lista de estudiantes inscritos en cada materia.\n"
				+ "• Gestionar observaciones o notas adicionales asociadas a un estudiante.\n\n"
				+ "Asegúrate de ingresar las calificaciones dentro de los plazos establecidos para evitar inconvenientes.");

		instruccionesDoc.setFont(new Font("Leelawadee UI", Font.PLAIN, 16)); // Fuente
		instruccionesDoc.setEditable(false); // No editable
		instruccionesDoc.setBackground(Color.WHITE); // Color de fondo blanco
		instruccionesDoc.setBounds(50, 80, 500, 200); // Posición y tamaño
		instruccionesDoc.setLineWrap(true); // Habilita el ajuste de línea
		instruccionesDoc.setWrapStyleWord(true); // Ajusta la línea por palabra
		panelInstruccionesDocente.add(instruccionesDoc); // Añade el área de texto al panel de instrucciones

		// Etiqueta para la imagen de fondo del panel de inicio
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-90, 0, 974, 461); // Posición y tamaño
		panelinicio.add(imgFondo); // Añade la etiqueta de fondo al panel de inicio
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
		imgFondo.setIcon(imagenEscalada); // Asigna la imagen escalada

		// ---------------- PANEL NOTAS ----------------
		JPanel panelNotas = new JPanel();
		panelNotas.setLayout(null); // Layout nulo
		panelNotas.setBounds(71, 106, 655, 363); // Posición y tamaño
		panelNotas.setVisible(false); // Mantenlo oculto por defecto
		contentPane.add(panelNotas); // Añade el panel de notas al panel de contenido

		JLabel lblSeleccionaMateria = new JLabel("Selecciona una materia:"); // Etiqueta para seleccionar materia
		lblSeleccionaMateria.setBounds(20, 10, 200, 25); // Posición y tamaño
		lblSeleccionaMateria.setFont(new Font("Leelawadee UI", Font.PLAIN, 14)); // Fuente
		panelNotas.add(lblSeleccionaMateria); // Añade la etiqueta al panel de notas

		JComboBox<Materia> comboMaterias = new JComboBox<>(); // ComboBox para seleccionar materias
		comboMaterias.setBounds(200, 10, 300, 25); // Posición y tamaño
		panelNotas.add(comboMaterias); // Añade el ComboBox al panel de notas

		// Crear modelo de tabla personalizado para controlar la edición de celdas
		DefaultTableModel modeloNotas = new DefaultTableModel() {
			@Override
			/**
			 * Determina si una celda específica es editable.
			 * @param row El índice de la fila.
			 * @param column El índice de la columna.
			 * @return true si la celda es editable, false en caso contrario.
			 */
			public boolean isCellEditable(int row, int column) {
				// Las columnas 0 (Código), 1 (Nombre) y 5 (Promedio) NO son editables
				return column != 0 && column != 1 && column != 5;
			}
		};
		// Añadir columnas al modelo de la tabla de notas
		modeloNotas.addColumn("Código");
		modeloNotas.addColumn("Nombre");
		modeloNotas.addColumn("Nota 1 (35%)");
		modeloNotas.addColumn("Nota 2 (35%)");
		modeloNotas.addColumn("Nota 3 (30%)");
		modeloNotas.addColumn("Promedio");

		// Crear tabla con el modelo de notas
		JTable tablaNotas = new JTable(modeloNotas);

		// Estética de la tabla de notas
		tablaNotas.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente
		tablaNotas.setRowHeight(30); // Altura de las filas
		tablaNotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13)); // Fuente del encabezado
		tablaNotas.getTableHeader().setReorderingAllowed(false); // Impide la reordenación de columnas

		JScrollPane scrollNotas = new JScrollPane(tablaNotas); // ScrollPane para la tabla de notas
		scrollNotas.setBounds(20, 50, 610, 230); // Posición y tamaño
		panelNotas.add(scrollNotas); // Añade el ScrollPane al panel de notas

		// Centrar contenido de las celdas de la tabla de notas
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER); // Alineación central
		for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
			tablaNotas.getColumnModel().getColumn(i).setCellRenderer(centro); // Aplica el renderizador a cada columna
		}

		// Desactivar redimensión lateral de las columnas de la tabla de notas
		for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
			tablaNotas.getColumnModel().getColumn(i).setResizable(false);
		}

		// --- Configurar el CellEditor para las columnas de notas (2, 3, 4) ---
		// Este editor personalizado se encargará de la validación
		TableCellEditor notaCellEditor = new DefaultCellEditor(new JTextField()) {
			// Patrón de expresión regular para permitir números con hasta dos decimales, entre 0.0 y 5.0
			private static final Pattern NUMERIC_PATTERN = Pattern
					.compile("^(?:[0-4](?:\\.[0-9]{1,2})?|5(?:\\.0{1,2})?)$");

			@Override
			/**
			 * Detiene la edición de la celda, validando la entrada del usuario.
			 * @return true si la edición se puede detener (entrada válida), false en caso contrario.
			 */
			public boolean stopCellEditing() {
				String value = ((JTextField) getComponent()).getText().trim(); // Obtiene el texto del editor
				try {
					// Validar si la entrada no está vacía
					if (value.isEmpty()) {
						JOptionPane.showMessageDialog(null, "La nota no puede estar vacía.", "Error de Validación",
								JOptionPane.ERROR_MESSAGE);
						return false; // No permite detener la edición
					}

					// Validar el formato con la expresión regular
					if (!NUMERIC_PATTERN.matcher(value).matches()) {
						JOptionPane.showMessageDialog(null,
								"Formato de nota inválido. Use números entre 0.0 y 5.0 con un máximo de dos decimales (ej. 3.5, 4.25, 5.0).",
								"Error de Validación", JOptionPane.ERROR_MESSAGE);
						return false;
					}

					// Convertir a double para la validación de rango (aunque el patrón ya lo cubre)
					double nota = Double.parseDouble(value);
					if (nota < 0.0 || nota > 5.0) { // Doble chequeo por seguridad
						JOptionPane.showMessageDialog(null, "La nota debe estar entre 0.0 y 5.0.",
								"Error de Validación", JOptionPane.ERROR_MESSAGE);
						return false; // No permite detener la edición
					}

				} catch (NumberFormatException e) {
					// Este catch debería ser raramente alcanzado si el patrón es correcto,
					// pero es un respaldo para entradas completamente no numéricas.
					JOptionPane.showMessageDialog(null, "Por favor, ingresa solo números válidos para las notas.",
							"Error de Validación", JOptionPane.ERROR_MESSAGE);
					return false; // No permite detener la edición
				}
				return super.stopCellEditing(); // Si la validación es exitosa, permite detener la edición
			}

			@Override
			/**
			 * Configura el componente del editor para la celda.
			 * @param table La tabla a la que pertenece la celda.
			 * @param value El valor de la celda.
			 * @param isSelected Si la celda está seleccionada.
			 * @param row El índice de la fila.
			 * @param column El índice de la columna.
			 * @return El componente del editor.
			 */
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				// Al iniciar la edición, seleccionamos todo el texto para facilitar la
				// sobreescritura
				JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				editor.selectAll(); // Selecciona todo el texto en el editor
				return editor;
			}
		};

		// Asignar el editor personalizado a las columnas de notas
		tablaNotas.getColumnModel().getColumn(2).setCellEditor(notaCellEditor); // Nota 1 (35%)
		tablaNotas.getColumnModel().getColumn(3).setCellEditor(notaCellEditor); // Nota 2 (35%)
		tablaNotas.getColumnModel().getColumn(4).setCellEditor(notaCellEditor); // Nota 3 (30%)
		// --- Fin de la configuración del CellEditor ---

		JButton btnGuardarNotas = new JButton("Guardar Notas"); // Botón para guardar notas
		btnGuardarNotas.setBounds(480, 300, 150, 30); // Posición y tamaño
		btnGuardarNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 13)); // Fuente
		panelNotas.add(btnGuardarNotas); // Añade el botón al panel de notas

		// Cargar materias que imparte el profesor en el ComboBox
		for (Materia m : gestorM.getMaterias()) {
			if (m.getProfesor() != null && m.getProfesor().equals(profesor)) {
				comboMaterias.addItem(m); // Añade la materia al ComboBox
			}
		}

		// ActionListener para el ComboBox de materias: al seleccionar una materia, llenar la tabla
		comboMaterias.addActionListener(e -> {
			Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem(); // Obtiene la materia seleccionada
			modeloNotas.setRowCount(0); // Limpia la tabla
			if (materiaSeleccionada != null) {
				for (Estudiante est : materiaSeleccionada.getEstudiantes()) { // Itera sobre los estudiantes de la materia
					List<Double> notas = materiaSeleccionada.getNotasEstudiante(est); // Obtiene las notas del estudiante
					double prom = materiaSeleccionada.calcularPromedio(est); // Calcula el promedio
					// Añade la fila con los datos del estudiante y sus notas a la tabla
					modeloNotas.addRow(new Object[] { est.getCodigo(), est.getNombre(), notas.get(0), notas.get(1),
							notas.get(2), String.format("%.2f", prom) });
				}
			}
		});

		// ActionListener para el botón "Guardar Notas"
		btnGuardarNotas.addActionListener(e -> {
			Materia materia = (Materia) comboMaterias.getSelectedItem(); // Obtiene la materia seleccionada
			if (materia == null) // Si no hay materia seleccionada, salir
				return;

			Materia materiaReal = gestorM.buscarMateriaPorCodigo(materia.getCodigo()); // Busca la materia real por su código
			if (materiaReal == null) {
				JOptionPane.showMessageDialog(panelNotas, "No se encontró la materia para guardar las notas.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Asegurarse de que cualquier edición en curso se detenga antes de guardar.
			// Esto es crucial para que el CellEditor realice su validación final.
			if (tablaNotas.isEditing()) {
				// Si stopCellEditing() retorna false, significa que la edición no se pudo
				// detener debido a una validación fallida, y no debemos proceder con el guardado.
				if (!tablaNotas.getCellEditor().stopCellEditing()) {
					return;
				}
			}

			try {
				for (int i = 0; i < modeloNotas.getRowCount(); i++) { // Itera sobre cada fila de la tabla
					long codigoEst = (long) modeloNotas.getValueAt(i, 0); // Obtiene el código del estudiante
					Estudiante est = (Estudiante) gestorU.buscarUsuarioPorCodigo(codigoEst); // Busca el objeto Estudiante

					// Obtener los valores de las notas de la tabla. Ya han sido validados por el CellEditor,
					// pero es bueno hacer un último chequeo de null/vacío.
					String sN1 = modeloNotas.getValueAt(i, 2) != null ? modeloNotas.getValueAt(i, 2).toString().trim()
							: "";
					String sN2 = modeloNotas.getValueAt(i, 3) != null ? modeloNotas.getValueAt(i, 3).toString().trim()
							: "";
					String sN3 = modeloNotas.getValueAt(i, 4) != null ? modeloNotas.getValueAt(i, 4).toString().trim()
							: "";

					// Si alguna nota está vacía, muestra un mensaje de error y detiene el guardado
					if (sN1.isEmpty() || sN2.isEmpty() || sN3.isEmpty()) {
						JOptionPane.showMessageDialog(panelNotas,
								"Por favor completa todas las notas del estudiante: " + est.getNombre(), "Error",
								JOptionPane.ERROR_MESSAGE);
						return; // Detiene el guardado
					}

					// Convierte las notas de String a Double
					double n1 = Double.parseDouble(sN1);
					double n2 = Double.parseDouble(sN2);
					double n3 = Double.parseDouble(sN3);

					// No es estrictamente necesario re-validar el rango aquí si el CellEditor
					// funciona bien, pero puede ser un respaldo.
					if (n1 < 0.0 || n1 > 5.0 || n2 < 0.0 || n2 > 5.0 || n3 < 0.0 || n3 > 5.0) {
						JOptionPane.showMessageDialog(panelNotas,
								"Las notas deben estar entre 0.0 y 5.0 para el estudiante: " + est.getNombre(),
								"Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Agrega las notas a la materia real
					materiaReal.agregarNotas(materiaReal, est, n1, n2, n3);

					// Calcular promedio y actualizar columna en la tabla
					double promedio = materiaReal.calcularPromedio(est);
					modeloNotas.setValueAt(String.format("%.2f", promedio), i, 5); // columna 5 = promedio
				}

				gestorM.guardarMaterias(); // Guarda las materias actualizadas
				JOptionPane.showMessageDialog(panelNotas, "Notas guardadas exitosamente."); // Mensaje de éxito

			} catch (NumberFormatException ex) {
				// Este catch es un respaldo si algo falla en la conversión a número
				// (aunque el CellEditor ya lo previene en gran medida)
				JOptionPane.showMessageDialog(panelNotas,
						"Se encontró una nota con formato inválido. Verifica que todas las notas sean números válidos.",
						"Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace(); // Para depuración
			} catch (Exception ex) {
				// Catch genérico para cualquier otro error inesperado durante el proceso de
				// guardado
				JOptionPane.showMessageDialog(panelNotas,
						"Ocurrió un error inesperado al guardar las notas: " + ex.getMessage(), "Error General",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace(); // Para depuración
			}
		});

		// ---------------- PANEL HORARIO (GRUPOS) ----------------
		JPanel panelGrupos = new JPanel();
		panelGrupos.setLayout(null); // Layout nulo
		panelGrupos.setBounds(71, 106, 655, 363); // Posición y tamaño
		panelGrupos.setVisible(false); // Oculto por defecto
		contentPane.add(panelGrupos); // Añade el panel de grupos al panel de contenido

		// Modelo de tabla para el horario (inicialmente vacío, se llenará al hacer clic en el botón HORARIO)
		DefaultTableModel modeloGrupo = new DefaultTableModel() {
			/**
			 * Determina si una celda específica es editable.
			 * @param row El índice de la fila.
			 * @param column El índice de la columna.
			 * @return Siempre false, ya que las celdas del horario no son editables.
			 */
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Establece los identificadores de columna para el modelo de horario
		modeloGrupo.setColumnIdentifiers(
				new Object[] { "Cod.", "Nombre", "Créditos", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" });

		// Tabla para mostrar el horario del profesor
		JTable tablaGrupo = new JTable(modeloGrupo);
		tablaGrupo.setRowHeight(30); // Altura de las filas
		JScrollPane scrollGrupo = new JScrollPane(tablaGrupo); // ScrollPane para la tabla del horario
		scrollGrupo.setBounds(20, 20, 610, 300); // Posición y tamaño
		panelGrupos.add(scrollGrupo); // Añade el ScrollPane al panel de grupos

		// Llenar el modelo de tabla de grupos inicialmente (esta parte se duplica en el ActionListener del botón HORARIO)
		for (Materia m : gestorM.getMaterias()) {
			if (m.getProfesor() != null && m.getProfesor().equals(profesor)) {
				String[] fila = new String[9];
				fila[0] = String.valueOf(m.getCodigo());
				fila[1] = m.getNombre();
				fila[2] = String.valueOf(m.getCreditos());
				for (int i = 3; i < 9; i++)
					fila[i] = ""; // Inicializa las celdas de días vacías

				// Asigna el horario a los días correspondientes
				for (String dia : m.getDias()) {
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
						fila[col] = m.getHoraInicio() + " - " + m.getHoraFin();
					}
				}
				modeloGrupo.addRow(fila); // Añade la fila al modelo del horario
			}
		}

		// Botón "NOTAS" en la barra de navegación
		JButton btnNotas = new JButton("NOTAS");
		btnNotas.addActionListener(new ActionListener() {
			/**
			 * Maneja el evento de clic del botón "NOTAS". Oculta los otros paneles y muestra el panel de notas.
			 * @param e El evento de acción.
			 */
			public void actionPerformed(ActionEvent e) {
				panelinicio.setVisible(false); // Oculta el panel de inicio
				panelGrupos.setVisible(false); // Oculta el panel de grupos
				panelNotas.setVisible(true); // Muestra el panel de notas
				panelNotas.revalidate(); // Revalida el layout del panel de notas
				panelNotas.repaint(); // Repinta el panel de notas
			}
		});

		// Listeners para efectos visuales del botón "NOTAS" al pasar el mouse
		btnNotas.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Maneja el evento de entrada del mouse para cambiar el color de fondo del botón.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				// Cambio de color al entrar al botón
				btnNotas.setBackground(new Color(80, 0, 0)); // Rojo oscuro más intenso
			}

			@Override
			/**
			 * Maneja el evento de salida del mouse para restaurar el color de fondo del botón.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				// Cambio de color al salir del botón
				btnNotas.setBackground(new Color(128, 0, 0)); // Rojo oscuro original
			}

			@Override
			/**
			 * Maneja el evento de presionar el mouse para cambiar el color de fondo del botón.
			 * @param e El evento de mouse.
			 */
			public void mousePressed(MouseEvent e) {
				btnNotas.setBackground(new Color(80, 0, 0)); // Rojo oscuro más intenso
			}
		});
		// Configuración visual del botón "NOTAS"
		btnNotas.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnNotas.setContentAreaFilled(false); // Quita el área rellena (para que se vea el color de fondo personalizado)
		btnNotas.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnNotas.setBorderPainted(false); // Quita el borde pintado
		btnNotas.setForeground(Color.WHITE); // Color del texto blanco
		btnNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Fuente
		btnNotas.setBorder(null); // Sin borde
		btnNotas.setBackground(new Color(128, 0, 0)); // Color de fondo rojo oscuro
		btnNotas.setBounds(560, 0, 111, 43); // Posición y tamaño
		NavBar.add(btnNotas); // Añade el botón a la barra de navegación

		// Botón "HORARIO" en la barra de navegación
		JButton btnGrupo = new JButton("HORARIO");
		btnGrupo.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnGrupo.setContentAreaFilled(false); // Quita el área rellena
		btnGrupo.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnGrupo.setBorderPainted(false); // Quita el borde pintado
		btnGrupo.setForeground(Color.WHITE); // Color del texto blanco
		btnGrupo.setFont(new Font("Leelawadee UI", Font.BOLD, 11)); // Fuente
		btnGrupo.setBorder(null); // Sin borde
		btnGrupo.setBackground(new Color(128, 0, 0)); // Color de fondo rojo oscuro
		btnGrupo.setBounds(680, 0, 103, 43); // Posición y tamaño
		NavBar.add(btnGrupo); // Añade el botón a la barra de navegación

		// EVENTOS del botón "HORARIO"
		btnGrupo.addActionListener(new ActionListener() {
			/**
			 * Maneja el evento de clic del botón "HORARIO". Oculta los otros paneles,
			 * muestra el panel de grupos y lo actualiza con el horario del profesor.
			 * @param e El evento de acción.
			 */
			public void actionPerformed(ActionEvent e) {
				panelNotas.setVisible(false); // Oculta el panel de notas
				panelGrupos.setVisible(true); // Muestra el panel de grupos (horario)
				contentPane.setComponentZOrder(panelGrupos, 0); // Trae el panel de grupos al frente
				contentPane.revalidate(); // Revalida el layout del panel de contenido
				contentPane.repaint(); // Repinta el panel de contenido

				// Limpiar el panel anterior antes de añadir nuevos componentes para el horario
				panelGrupos.removeAll();
				panelGrupos.setLayout(null);

				// Título del horario del profesor
				JLabel lblHorarioProfe = new JLabel("HORARIO DEL PROFESOR");
				lblHorarioProfe.setBounds(163, 10, 400, 40); // Posición y tamaño
				lblHorarioProfe.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto
				lblHorarioProfe.setForeground(Color.BLACK); // Color del texto
				lblHorarioProfe.setFont(new Font("Leelawadee UI", Font.BOLD, 28)); // Fuente
				panelGrupos.add(lblHorarioProfe); // Añade el título al panel de grupos

				// Modelo de tabla para el horario (se crea de nuevo para asegurar limpieza)
				DefaultTableModel modelo = new DefaultTableModel() {
					@Override
					/**
					 * Determina si una celda específica es editable.
					 * @param row El índice de la fila.
					 * @param column El índice de la columna.
					 * @return Siempre false, ya que las celdas del horario no son editables.
					 */
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				// Establece los identificadores de columna para el modelo de horario
				modelo.setColumnIdentifiers(
						new Object[] { "Cod.", "Materia", "Créditos", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" });

				// Llenar tabla con las materias dictadas por el profesor
				for (Materia materia : gestorM.getMaterias()) {
					if (materia.getProfesor() != null && materia.getProfesor().equals(profesor)) {
						String[] fila = new String[9];
						fila[0] = String.valueOf(materia.getCodigo());
						fila[1] = materia.getNombre();
						fila[2] = String.valueOf(materia.getCreditos());

						// Inicializar celdas vacías para los días
						for (int i = 3; i < 9; i++)
							fila[i] = "";

						// Asignar horarios a los días correspondientes
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

						modelo.addRow(fila); // Añade la fila al modelo de la tabla
					}
				}

				JTable tabla = new JTable(modelo); // Crea la tabla con el modelo actualizado
				tabla.setRowHeight(40); // Altura de las filas
				tabla.getTableHeader().setReorderingAllowed(false); // Impide la reordenación de columnas
				tabla.setGridColor(new Color(200, 200, 200)); // Color de la cuadrícula
				tabla.setShowHorizontalLines(true); // Muestra líneas horizontales
				tabla.setShowVerticalLines(false); // Oculta líneas verticales
				tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Fuente de la tabla
				tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13)); // Fuente del encabezado
				tabla.setFillsViewportHeight(true); // La tabla usa toda la altura disponible en el viewport
				tabla.setBackground(Color.WHITE); // Color de fondo
				tabla.setSelectionBackground(new Color(220, 220, 255)); // Color de selección

				// Centrado del contenido de las celdas de la tabla de horario
				DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
				centro.setHorizontalAlignment(SwingConstants.CENTER); // Alineación central
				for (int i = 0; i < tabla.getColumnCount(); i++) {
					tabla.getColumnModel().getColumn(i).setCellRenderer(centro); // Aplica el renderizador a cada columna
				}

				// Minimizar columnas de días vacías en el horario
				for (int col = 3; col <= 8; col++) {
					boolean vacía = true;
					for (int fila = 0; fila < modelo.getRowCount(); fila++) {
						if (modelo.getValueAt(fila, col) != null
								&& !modelo.getValueAt(fila, col).toString().isBlank()) {
							vacía = false;
							break;
						}
					}
					if (vacía) {
						tabla.getColumnModel().getColumn(col).setMinWidth(30); // Ancho mínimo
						tabla.getColumnModel().getColumn(col).setMaxWidth(30); // Ancho máximo
						tabla.getColumnModel().getColumn(col).setPreferredWidth(30); // Ancho preferido
					}
				}

				JScrollPane scroll = new JScrollPane(tabla); // ScrollPane para la tabla de horario
				scroll.setBounds(20, 60, 600, 250); // Posición y tamaño
				panelGrupos.add(scroll); // Añade el ScrollPane al panel de grupos

				panelGrupos.revalidate(); // Revalida el layout del panel de grupos
				panelGrupos.repaint(); // Repinta el panel de grupos
			}
		});

		// Listeners para efectos visuales del botón "HORARIO" al pasar el mouse
		btnGrupo.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Maneja el evento de entrada del mouse para cambiar el color de fondo del botón.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				btnGrupo.setBackground(new Color(80, 0, 0)); // Rojo oscuro más intenso
				btnGrupo.repaint(); // Repinta el botón
			}

			@Override
			/**
			 * Maneja el evento de salida del mouse para restaurar el color de fondo del botón.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				btnGrupo.setBackground(new Color(128, 0, 0)); // Rojo oscuro original
				btnGrupo.repaint(); // Repinta el botón
			}

			@Override
			/**
			 * Maneja el evento de presionar el mouse para cambiar el color de fondo del botón a un color distinto.
			 * @param e El evento de mouse.
			 */
			public void mousePressed(MouseEvent e) {
				btnGrupo.setBackground(new Color(100, 0, 0)); // Un color distinto para el clic
				btnGrupo.repaint(); // Repinta el botón
			}
		});

		// Panel para el escudo (icono de la universidad) en la barra de navegación
		JPanel Escudo = new JPanel();
		Escudo.setForeground(new Color(0, 0, 0)); // Color de primer plano
		Escudo.setLayout(null); // Layout nulo
		Escudo.setBackground(new Color(128, 0, 0)); // Color de fondo rojo oscuro
		Escudo.setBounds(10, 0, 52, 43); // Posición y tamaño
		NavBar.add(Escudo); // Añade el panel del escudo a la barra de navegación

		JLabel EscudoTxt = new JLabel(""); // Etiqueta para la imagen del escudo
		EscudoTxt.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
		EscudoTxt.setBounds(0, 0, 52, 43); // Posición y tamaño
		Escudo.add(EscudoTxt); // Añade la etiqueta a el panel del escudo
		// Listeners para el panel del escudo
		Escudo.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Maneja el evento de clic del mouse para mostrar el panel de inicio.
			 * @param e El evento de mouse.
			 */
			public void mouseClicked(MouseEvent e) {
				// Mostrar panel de inicio como hace el botón
				panelinicio.setVisible(true); // Muestra el panel de inicio
				panelNotas.setVisible(false); // Oculta el panel de notas
				panelGrupos.setVisible(false); // Oculta el panel de grupos

				contentPane.setComponentZOrder(panelinicio, 0); // Trae el panel de inicio al frente
				contentPane.revalidate(); // Revalida el layout del panel de contenido
				contentPane.repaint(); // Repinta el panel de contenido
			}

			@Override
			/**
			 * Maneja el evento de entrada del mouse para cambiar el color de fondo del panel.
			 * @param e El evento de mouse.
			 */
			public void mouseEntered(MouseEvent e) {
				Escudo.setBackground(new Color(80, 0, 0)); // Rojo oscuro más intenso
			}

			@Override
			/**
			 * Maneja el evento de salida del mouse para restaurar el color de fondo del panel.
			 * @param e El evento de mouse.
			 */
			public void mouseExited(MouseEvent e) {
				Escudo.setBackground(new Color(128, 0, 0)); // Rojo oscuro original
			}
		});

		// Carga y escala la imagen del escudo
		ImageIcon ico1 = new ImageIcon(Login.class.getResource("/Imagenes/output-onlinepngtools.png"));
		Image imagen1 = ico1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Escala la imagen
		ImageIcon imagenEscalada1 = new ImageIcon(imagen1); // Crea un nuevo ImageIcon con la imagen escalada

		// Asigna la imagen escalada al JLabel del escudo
		EscudoTxt.setIcon(imagenEscalada1);

		// Etiqueta para la imagen de fondo principal (posiblemente redundante o para un uso general)
		JLabel imgFondo3 = new JLabel("");
		imgFondo3.setBounds(-87, 83, 974, 461); // Posición y tamaño
		contentPane.add(imgFondo3); // Añade la etiqueta al panel de contenido
		imgFondo3.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen
		// Cargar y escalar la imagen de fondo principal
		ImageIcon ico3 = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen3 = ico3.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH); // Escala la imagen
		ImageIcon imagenEscalada3 = new ImageIcon(imagen3); // Crea un nuevo ImageIcon con la imagen escalada
		// Asignar imagen al JLabel
		imgFondo3.setIcon(imagenEscalada3); // Asigna la imagen escalada
	}
}