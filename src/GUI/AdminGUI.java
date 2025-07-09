package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Kernel.Administrador;
import Kernel.Estudiante;
import Kernel.HashUtil;
import Kernel.Materia;
import Kernel.Profesor;
import Kernel.Usuario;
import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;


/**
 * La clase `AdminGUI` representa la interfaz gráfica de usuario para el administrador del sistema.
 * Permite al administrador realizar funciones como asignar materias a docentes y gestionar las
 * preinscripciones de los estudiantes.
 */
public class AdminGUI extends JFrame {
	private JPanel contentPane;

	// Guardar informacion de la posicion de mouse para mover la ventana
	int xMouse, yMouse;
	private JTextField IdDocenteField;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private DefaultListModel<Estudiante> modeloEstudiantesPreinscritos;
	private DefaultListModel<String> modeloLista = new DefaultListModel<>();
	private JList<Estudiante> listaEstudiantesPreinscritos; // También a nivel de clase
	private DefaultTableModel modeloMateriasConCheck;
	private JTable tablaMateriasConCheck;
	private Estudiante estudianteActual;
	private JScrollPane scrollPane;

	/**
	 * Crea la ventana principal de la interfaz del administrador.
	 *
	 * @param gestorU Objeto {@link GestorUsuarios} para la gestión de usuarios.
	 * @param gestorM Objeto {@link GestorMaterias} para la gestión de materias.
	 * @param s Objeto {@link GestionSolicitudes} para la gestión de solicitudes de preinscripción.
	 */
	public AdminGUI(GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {

		modeloEstudiantesPreinscritos = new DefaultListModel<>(); // Inicializa el modelo
		Administrador admin = new Administrador();
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

		JPanel panelinicio = new JPanel();
		panelinicio.setBounds(0, 82, 800, 418);
		contentPane.add(panelinicio);
		panelinicio.setLayout(null);
		
		JPanel panelInstruccionesAdmin = new JPanel();
		panelInstruccionesAdmin.setLayout(null);
		panelInstruccionesAdmin.setBackground(Color.WHITE);
		panelInstruccionesAdmin.setBounds(70, 22, 656, 364);
		panelinicio.add(panelInstruccionesAdmin);
		
		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-90, 0, 974, 521);
		panelinicio.add(imgFondo);
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Cargar y escalar la imagen
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen = ico.getImage().getScaledInstance(825, 500, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);
		// Asignar imagen al JLabel
		imgFondo.setIcon(imagenEscalada);


		// Mensaje de bienvenida
		JLabel lblBienvenidaAdmin = new JLabel("¡Bienvenido Administrador!");
		lblBienvenidaAdmin.setFont(new Font("Leelawadee UI", Font.BOLD, 20));
		lblBienvenidaAdmin.setBounds(50, 30, 500, 30);
		panelInstruccionesAdmin.add(lblBienvenidaAdmin);

		// Instrucciones
		JTextArea instruccionesAdmin = new JTextArea(
			    "Puedes realizar las siguientes funciones:\n\n" +
			    "• Crear nuevas cuentas de usuario para estudiantes y docentes, asignando sus roles.\n" +
			    "• Aprobar o rechazar las preinscripciones de los estudiantes según disponibilidad.\n" +
			    "• Asignar materias a los docentes, definiendo grupo, horario y salón.\n" +
			    "• Gestionar la oferta académica semestral (número de grupos, cupos, horarios).\n" +
			    "• Supervisar el estado de solicitudes pendientes y reportes administrativos.\n\n" +
			    "Recuerda verificar que los datos ingresados sean correctos antes de confirmar cualquier acción."
		);

		instruccionesAdmin.setFont(new Font("Leelawadee UI", Font.PLAIN, 16));
		instruccionesAdmin.setEditable(false);
		instruccionesAdmin.setBackground(Color.WHITE);
		instruccionesAdmin.setBounds(50, 80, 550, 200);
		instruccionesAdmin.setLineWrap(true);
		instruccionesAdmin.setWrapStyleWord(true);
		panelInstruccionesAdmin.add(instruccionesAdmin);


		// -------------------------------------------------------------------
		JPanel AsignarMateriaPanel = new JPanel();
		AsignarMateriaPanel.setLayout(null);
		AsignarMateriaPanel.setBackground(Color.WHITE);
		AsignarMateriaPanel.setBounds(56, 105, 668, 373);
		contentPane.add(AsignarMateriaPanel);

		JButton btnAsignarMateriaDocente = new JButton("ASIGNAR MATERIA");
		btnAsignarMateriaDocente.setOpaque(true);
		btnAsignarMateriaDocente.setForeground(Color.WHITE);
		btnAsignarMateriaDocente.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnAsignarMateriaDocente.setFocusPainted(false);
		btnAsignarMateriaDocente.setBorderPainted(false);
		btnAsignarMateriaDocente.setBorder(null);
		btnAsignarMateriaDocente.setBackground(new Color(128, 0, 0));
		btnAsignarMateriaDocente.setBounds(263, 266, 138, 43);
		AsignarMateriaPanel.add(btnAsignarMateriaDocente);

		JLabel lblAsignarMateriaA = new JLabel("ASIGNAR MATERIA A DOCENTES");
		lblAsignarMateriaA.setBounds(186, 51, 296, 40);
		AsignarMateriaPanel.add(lblAsignarMateriaA);
		lblAsignarMateriaA.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsignarMateriaA.setForeground(Color.BLACK);
		lblAsignarMateriaA.setFont(new Font("Leelawadee UI", Font.BOLD, 14));

		JLabel IdDocente = new JLabel("ID del Docente");
		IdDocente.setOpaque(true);
		IdDocente.setHorizontalAlignment(SwingConstants.LEFT);
		IdDocente.setForeground(Color.BLACK);
		IdDocente.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		IdDocente.setBorder(null);
		IdDocente.setBackground(Color.WHITE);
		IdDocente.setBounds(232, 99, 82, 23);
		AsignarMateriaPanel.add(IdDocente);

		IdDocenteField = new JTextField();
		IdDocenteField.setBounds(232, 123, 82, 22);
		AsignarMateriaPanel.add(IdDocenteField);
		IdDocenteField.setForeground(new Color(29, 33, 35));
		IdDocenteField.setColumns(10);
		IdDocenteField.setBorder(null);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(232, 147, 82, 14);
		AsignarMateriaPanel.add(separator_1_1);
		separator_1_1.setForeground(new Color(29, 33, 35));

		JLabel lblIngreseNombreDel = new JLabel("Nombre del Docente");
		lblIngreseNombreDel.setOpaque(true);
		lblIngreseNombreDel.setHorizontalAlignment(SwingConstants.LEFT);
		lblIngreseNombreDel.setForeground(Color.BLACK);
		lblIngreseNombreDel.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		lblIngreseNombreDel.setBorder(null);
		lblIngreseNombreDel.setBackground(Color.WHITE);
		lblIngreseNombreDel.setBounds(347, 99, 113, 23);
		AsignarMateriaPanel.add(lblIngreseNombreDel);

		textField = new JTextField();
		textField.setForeground(new Color(29, 33, 35));
		textField.setColumns(10);
		textField.setBorder(null);
		textField.setBounds(347, 123, 113, 22);
		AsignarMateriaPanel.add(textField);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setForeground(new Color(29, 33, 35));
		separator_1_1_1.setBounds(347, 147, 113, 14);
		AsignarMateriaPanel.add(separator_1_1_1);

		JLabel lblEscojaLaMateria = new JLabel("Materia a asignar");
		lblEscojaLaMateria.setOpaque(true);
		lblEscojaLaMateria.setHorizontalAlignment(SwingConstants.LEFT);
		lblEscojaLaMateria.setForeground(Color.BLACK);
		lblEscojaLaMateria.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		lblEscojaLaMateria.setBorder(null);
		lblEscojaLaMateria.setBackground(Color.WHITE);
		lblEscojaLaMateria.setBounds(48, 183, 199, 23);
		AsignarMateriaPanel.add(lblEscojaLaMateria);

		JSeparator separator_1_1_2 = new JSeparator();
		separator_1_1_2.setForeground(new Color(29, 33, 35));
		separator_1_1_2.setBounds(48, 231, 199, 14);
		AsignarMateriaPanel.add(separator_1_1_2);

		String[] horarios = { "06:00 - 08:00", "08:00 - 10:00", "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00",
				"16:00 - 18:00", "18:00 - 20:00" };

		JComboBox<String> comboHorario = new JComboBox<>();
		comboHorario.setBounds(271, 207, 150, 22);
		AsignarMateriaPanel.add(comboHorario);

		String[] dias1Credito = { "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" };

		String[] dias2Creditos = { "Lun-Mie", "Mar-Jue", "Lun-Vie", "Mie-Vie" };

		String[] dias3Creditos = { "Lun-Mie-Vie", "Mar-Jue-Sab", "Lun-Mie-Jue", "Mar-Jue-Vie" };

		JComboBox<String> comboDias = new JComboBox<>();
		comboDias.setBounds(447, 207, 190, 22);
		AsignarMateriaPanel.add(comboDias);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setBorder(null);
		comboBox.setBounds(48, 207, 199, 22);
		comboBox.removeAllItems(); // Limpiar items existentes
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Materia materiaSeleccionada = (Materia) comboBox.getSelectedItem();
				comboDias.removeAllItems(); // Limpiar los días anteriores

				if (materiaSeleccionada != null) {
					int creditos = materiaSeleccionada.getCreditos();

					String[] diasValidos;
					if (creditos == 1) {
						diasValidos = dias1Credito;
					} else if (creditos == 2) {
						diasValidos = dias2Creditos;
					} else {
						diasValidos = dias3Creditos;
					}

					for (String d : diasValidos) {
						comboDias.addItem(d);
					}
				}
				comboHorario.removeAllItems(); // Limpia si ya tenía algo
				for (String horario : horarios) {
					comboHorario.addItem(horario);
				}
			}
		});

		AsignarMateriaPanel.add(comboBox);

		JSeparator separator_1_1_2_1 = new JSeparator();
		separator_1_1_2_1.setForeground(new Color(29, 33, 35));
		separator_1_1_2_1.setBounds(270, 231, 151, 14);
		AsignarMateriaPanel.add(separator_1_1_2_1);

		JSeparator separator_1_1_2_2 = new JSeparator();
		separator_1_1_2_2.setForeground(new Color(29, 33, 35));
		separator_1_1_2_2.setBounds(448, 231, 189, 14);
		AsignarMateriaPanel.add(separator_1_1_2_2);

		JLabel lblHorario = new JLabel("Horario");
		lblHorario.setOpaque(true);
		lblHorario.setHorizontalAlignment(SwingConstants.LEFT);
		lblHorario.setForeground(Color.BLACK);
		lblHorario.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		lblHorario.setBorder(null);
		lblHorario.setBackground(Color.WHITE);
		lblHorario.setBounds(271, 183, 104, 23);
		AsignarMateriaPanel.add(lblHorario);

		JLabel lblDias = new JLabel("Dias");
		lblDias.setOpaque(true);
		lblDias.setHorizontalAlignment(SwingConstants.LEFT);
		lblDias.setForeground(Color.BLACK);
		lblDias.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		lblDias.setBorder(null);
		lblDias.setBackground(Color.WHITE);
		lblDias.setBounds(446, 183, 199, 23);
		AsignarMateriaPanel.add(lblDias);

		btnAsignarMateriaDocente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String idDocenteText = IdDocenteField.getText().trim();
				String nombreDocenteText = textField.getText().trim();

				// Validación: campo ID docente vacío
				if (idDocenteText.isEmpty()) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel, "Por favor, ingrese el ID del docente.",
							"Error de entrada", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Validación: ID docente numérico y positivo
				int idDocente;
				try {
					idDocente = Integer.parseInt(idDocenteText);
					if (idDocente <= 0) {
						JOptionPane.showMessageDialog(AsignarMateriaPanel,
								"El ID del docente debe ser un número positivo.", "ID inválido",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel, "El ID del docente debe ser un número válido.",
							"Error de formato", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Validación: materia seleccionada
				Materia materiaSeleccionada = (Materia) comboBox.getSelectedItem();
				if (materiaSeleccionada == null) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel,
							"Por favor, seleccione una materia para asignar.", "Error de selección",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Buscar usuario por ID
				Usuario usuarioEncontrado = gestorU.buscarUsuarioPorCodigo(idDocente);
				if (usuarioEncontrado == null) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel,
							"No se encontró un usuario con el ID " + idDocente + ".", "Profesor no encontrado",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Validación: usuario es un profesor
				if (!(usuarioEncontrado instanceof Profesor)) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel,
							"El usuario con ID " + idDocente + " no es un profesor.", "Tipo de usuario incorrecto",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Profesor profesor = (Profesor) usuarioEncontrado;

				// Validar que el nombre del docente coincida estrictamente
				if (!profesor.getNombre().equalsIgnoreCase(nombreDocenteText)) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel,
							"El nombre del docente no coincide con el ID proporcionado.", "Verificación fallida",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Obtener horario y días seleccionados
				String horarioSeleccionado = (String) comboHorario.getSelectedItem();
				String diasSeleccionados = (String) comboDias.getSelectedItem();

				// Validación: horario y días seleccionados
				if (horarioSeleccionado == null || diasSeleccionados == null) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel, "Seleccione un horario y días válidos.",
							"Faltan datos", JOptionPane.WARNING_MESSAGE);
					return;
				}

				// Validar cantidad de días según créditos
				int creditosMateria = materiaSeleccionada.getCreditos();
				List<String> diasList = Arrays.asList(diasSeleccionados.split("-"));
				for (int i = 0; i < diasList.size(); i++) {
					diasList.set(i, diasList.get(i).trim());
				}

				if (diasList.size() != creditosMateria) {
					JOptionPane.showMessageDialog(AsignarMateriaPanel,
							"La cantidad de días seleccionados (" + diasList.size()
									+ ") no coincide con los créditos de la materia (" + creditosMateria + ").\n"
									+ "Por favor, selecciona exactamente " + creditosMateria + " día(s).",
							"Error en cantidad de días", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String[] partesHorario = horarioSeleccionado.split(" - ");
				String horaInicioNueva = partesHorario[0];
				String horaFinNueva = partesHorario[1];

				// Convertir las horas a LocalTime para una comparación robusta
				LocalTime inicioNueva = LocalTime.parse(horaInicioNueva);
				LocalTime finNueva = LocalTime.parse(horaFinNueva);

				// --- INICIO DE LA LÓGICA DE CONFLICTO DE HORARIO MEJORADA ---
				for (Materia materiaExistente : profesor.getMaterias()) {
					List<String> diasExistentes = materiaExistente.getDias();
					String inicioExistenteStr = materiaExistente.getHoraInicio();
					String finExistenteStr = materiaExistente.getHoraFin();

					// Solo si la materia existente tiene horario definido (debería tenerlo si está
					// asignada)
					if (inicioExistenteStr != null && finExistenteStr != null && diasExistentes != null
							&& !diasExistentes.isEmpty()) {
						LocalTime inicioExistente = LocalTime.parse(inicioExistenteStr);
						LocalTime finExistente = LocalTime.parse(finExistenteStr);

						// 1. Verificar si hay al menos un día en común
						boolean hayDiaEnComun = false;
						for (String diaNuevo : diasList) {
							if (diasExistentes.contains(diaNuevo)) {
								hayDiaEnComun = true;
								break;
							}
						}

						// Si hay un día en común, entonces verifica la superposición de horas
						if (hayDiaEnComun) {
							// 2. Verificar superposición de intervalos de tiempo
							// Condición para superposición: (InicioNuevo < FinExistente) AND (FinNuevo >
							// InicioExistente)
							if (inicioNueva.isBefore(finExistente) && finNueva.isAfter(inicioExistente)) {
								JOptionPane.showMessageDialog(AsignarMateriaPanel,
										"Conflicto de horario: El profesor '" + profesor.getNombre()
												+ "' ya tiene asignada la materia '" + materiaExistente.getNombre()
												+ "' que se cruza con el horario de la nueva materia '"
												+ materiaSeleccionada.getNombre() + "'.\n\nDetalles del conflicto:\n"
												+ "Materia existente: " + materiaExistente.getNombre() + " ("
												+ inicioExistenteStr + " - " + finExistenteStr + " los días "
												+ String.join(", ", diasExistentes) + ")\n" + "Materia a asignar: "
												+ materiaSeleccionada.getNombre() + " (" + horaInicioNueva + " - "
												+ horaFinNueva + " los días " + String.join(", ", diasList) + ")",
										"Conflicto de Horario Detectado", JOptionPane.ERROR_MESSAGE);
								return; // Detiene la asignación si hay conflicto
							}
						}
					}
				}
				// --- FIN DE LA LÓGICA DE CONFLICTO DE HORARIO MEJORADA ---

				// Actualizar los datos de la materia con las horas y días asignados.
				// Es importante hacer esto *después* de todas las validaciones exitosas.
				materiaSeleccionada.setHoraInicio(horaInicioNueva);
				materiaSeleccionada.setHoraFin(horaFinNueva);
				materiaSeleccionada.setDias(diasList);
				materiaSeleccionada.setProfesor(profesor); // Asegúrate de asignar el profesor a la materia

				// 2. Asignar la materia usando el Administrador
				admin.asignarMateriaAProfesor(profesor, materiaSeleccionada);
				gestorU.guardarUsuarios(); // Guardar cambios en los profesores
				gestorM.guardarMaterias(); // Guardar cambios en las materias (si Materia.setProfesor() las modifica)

				JOptionPane.showMessageDialog(AsignarMateriaPanel,
						"Materia '" + materiaSeleccionada.getNombre() + "' asignada a '" + profesor.getNombre() + " "
								+ profesor.getApellido() + "' correctamente.",
						"Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);

				// Limpiar campos después de una asignación exitosa
				IdDocenteField.setText("");
				textField.setText("");
				comboBox.setSelectedIndex(-1); // Deseleccionar la materia
				comboHorario.setSelectedIndex(-1); // Deseleccionar horario
				comboDias.setSelectedIndex(-1); // Deseleccionar días
			}
		});
		AsignarMateriaPanel.setVisible(false);
		AsignarMateriaPanel.setVisible(false);

		JPanel PreinscripcionesPanel = new JPanel();
		PreinscripcionesPanel.setBackground(Color.WHITE);
		PreinscripcionesPanel.setBounds(56, 105, 668, 373);
		contentPane.add(PreinscripcionesPanel);
		PreinscripcionesPanel.setLayout(null);

		JPanel panelGestinPreinscripcion = new JPanel();
		panelGestinPreinscripcion.setBounds(0, 0, 668, 373);
		PreinscripcionesPanel.add(panelGestinPreinscripcion);
		panelGestinPreinscripcion.setLayout(null);

		JLabel lblNewLabel_9 = new JLabel("ESTUDIANTES");
		lblNewLabel_9.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel_9.setBounds(117, 38, 97, 13);
		panelGestinPreinscripcion.add(lblNewLabel_9);

		JLabel lblNewLabel_9_1 = new JLabel("MATERIAS A INSCRIBIR");
		lblNewLabel_9_1.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel_9_1.setBounds(397, 38, 163, 13);
		panelGestinPreinscripcion.add(lblNewLabel_9_1);

		JPanel VolverPanel_2 = new JPanel();
		VolverPanel_2.setLayout(null);
		VolverPanel_2.setForeground(Color.BLACK);
		VolverPanel_2.setBackground(Color.WHITE);
		VolverPanel_2.setBounds(0, 0, 41, 39);
		panelGestinPreinscripcion.add(VolverPanel_2);
		JPanel panelReportarProblema = new JPanel();
		panelReportarProblema.setBounds(0, 0, 668, 373);
		PreinscripcionesPanel.add(panelReportarProblema);
		panelReportarProblema.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("REPORTAR ERROR DEL SISTEMA");
		lblNewLabel_6.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel_6.setBounds(224, 27, 223, 13);
		panelReportarProblema.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Asunto:");
		lblNewLabel_7.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		lblNewLabel_7.setBounds(172, 80, 65, 13);
		panelReportarProblema.add(lblNewLabel_7);

		textField_1 = new JTextField();
		textField_1.setBounds(255, 78, 212, 19);
		panelReportarProblema.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Descripción:");
		lblNewLabel_8.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		lblNewLabel_8.setBounds(172, 125, 106, 13);
		panelReportarProblema.add(lblNewLabel_8);

		textField_2 = new JTextField();
		textField_2.setBounds(172, 151, 295, 116);
		panelReportarProblema.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setFont(new Font("Leelawadee UI", Font.PLAIN, 12));
		btnNewButton_1.setBounds(172, 277, 85, 21);
		panelReportarProblema.add(btnNewButton_1);

		JPanel panelInstrucciones = new JPanel();
		panelInstrucciones.setBackground(Color.WHITE);
		panelInstrucciones.setBounds(0, 0, 668, 373);
		PreinscripcionesPanel.add(panelInstrucciones);
		panelInstrucciones.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("BIENVENIDO AL MODULO DE GESTION");
		lblNewLabel_1.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel_1.setBounds(40, 24, 346, 18);
		panelInstrucciones.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("DE PREINSCRIPCIONES");
		lblNewLabel_1_1.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(40, 41, 346, 18);
		panelInstrucciones.add(lblNewLabel_1_1);

		JLabel lblNewLabel_2 = new JLabel(
				"<html>Desde el menú lateral derecho puede seleccionar la opción<br>'Gestionar preinscripciones' que le permite consultar, aceptar o cancelar Pre-inscripciones.</html>");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Leelawadee UI", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(40, 69, 415, 46);
		panelInstrucciones.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Importante!!!");
		lblNewLabel_3.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		lblNewLabel_3.setBounds(40, 145, 215, 13);
		panelInstrucciones.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("<html>"
				+ "<b>Apreciado administrador, por favor tenga en cuenta las siguientes directrices:</b><br><br>"
				+ "1. El proceso de gestión de preinscripciones se debe realizar únicamente durante las fechas estipuladas en el Calendario Académico.<br><br>"
				+ "2. En caso de tener problemas con el aplicativo, puede enviar un mensaje a la Oficina Asesora de Sistemas a través del menú lateral derecho en la opción 'Reportar problema'.<br><br>"
				+ "3. Para realizar su gestión de preinscripciones, se recomienda hacerlo utilizando el navegador MOZILLA FIREFOX."
				+ "</html>");
		lblNewLabel_4.setFont(new Font("Leelawadee UI", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(40, 171, 442, 181);
		panelInstrucciones.add(lblNewLabel_4);

		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(192, 192, 192));
		panelMenu.setBounds(493, 24, 152, 148);
		panelInstrucciones.add(panelMenu);
		panelMenu.setLayout(null);

		JButton btnNewButton = new JButton("<html>" + "Gestion de <br> preinscripciones" + "</html>");
		btnNewButton.setFont(new Font("Leelawadee UI", Font.PLAIN, 10));
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setBounds(20, 47, 116, 35);
		panelMenu.add(btnNewButton);

		JButton btnReportarProblema = new JButton("<html>" + "Reportar <br> problema" + "</html>");
		btnReportarProblema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInstrucciones.setVisible(false);
				panelGestinPreinscripcion.setVisible(false);
				panelReportarProblema.setVisible(true);
			}
		});

		btnReportarProblema.setFont(new Font("Leelawadee UI", Font.PLAIN, 10));
		btnReportarProblema.setBackground(Color.LIGHT_GRAY);
		btnReportarProblema.setBounds(20, 92, 116, 35);
		panelMenu.add(btnReportarProblema);

		JLabel lblNewLabel_5 = new JLabel("Menu");
		lblNewLabel_5.setFont(new Font("Leelawadee UI", Font.BOLD, 12));
		lblNewLabel_5.setBounds(20, 10, 57, 16);
		panelMenu.add(lblNewLabel_5);

		btnReportarProblema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInstrucciones.setVisible(false);
				panelGestinPreinscripcion.setVisible(false);
				panelReportarProblema.setVisible(true);
			}
		});

		PreinscripcionesPanel.setVisible(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 61, 241, 267);
		panelGestinPreinscripcion.add(scrollPane); // Añadir el scrollPane al panel de gestión

		listaEstudiantesPreinscritos = new JList<>(modeloEstudiantesPreinscritos); // Crea la JList
		scrollPane.setViewportView(listaEstudiantesPreinscritos); // Asocia la JList al JScrollPane

		// --- INICIALIZACIÓN Y CONFIGURACIÓN DE LA JTable DE MATERIAS ---
		// --- INICIALIZACIÓN Y CONFIGURACIÓN DE LA JTable DE MATERIAS ---
		JPanel PanelMaterias = new JPanel();
		PanelMaterias.setBackground(new Color(192, 192, 192));
		PanelMaterias.setBounds(349, 61, 255, 267);
		panelGestinPreinscripcion.add(PanelMaterias); // Añadir al panel de gestión
		PanelMaterias.setLayout(null);

		String[] columnas = { "Seleccionar", "Materia" };
		modeloMateriasConCheck = new DefaultTableModel(null, columnas) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		};
		tablaMateriasConCheck = new JTable(modeloMateriasConCheck); // Crea la JTable
		tablaMateriasConCheck.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaMateriasConCheck.getColumnModel().getColumn(1).setPreferredWidth(185);

		JScrollPane scrollMaterias = new JScrollPane(tablaMateriasConCheck);
		scrollMaterias.setBounds(10, 10, 235, 210);
		PanelMaterias.add(scrollMaterias); // Añadir el scrollPane de la tabla al PanelMaterias

		// --- CARGA DIRECTA DE ESTUDIANTES CON SOLICITUDES ---
		modeloEstudiantesPreinscritos.clear();

		for (Usuario u : gestorU.getUsuarios()) {
			if (u instanceof Estudiante estudiante) {
				List<Materia> materiasSolicitadas = s.obtenerSolicitudes(estudiante.getCodigo());

				if (materiasSolicitadas != null && !materiasSolicitadas.isEmpty()) {
					modeloEstudiantesPreinscritos.addElement(estudiante);
				}
			}
		}

		listaEstudiantesPreinscritos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Estudiante estudianteSeleccionado = listaEstudiantesPreinscritos.getSelectedValue();
					modeloMateriasConCheck.setRowCount(0); // Limpia tabla

					if (estudianteSeleccionado != null) {
						List<Materia> materiasSolicitadas = s.obtenerSolicitudes(estudianteSeleccionado.getCodigo());

						for (Materia m : materiasSolicitadas) {						
							modeloMateriasConCheck.addRow(new Object[] { false, m.getNombre() });

						}

						
					}
				}
			}
		});

		JButton btnInscribir = new JButton("Inscribir");
		btnInscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Estudiante estudianteSeleccionado = listaEstudiantesPreinscritos.getSelectedValue();
				if (estudianteSeleccionado == null) {
					JOptionPane.showMessageDialog(null, "Selecciona un estudiante primero.");
					return;
				}
				if (!s.tienePreinscripcion(estudianteSeleccionado)) {
					JOptionPane.showMessageDialog(null, "Este estudiante no tiene preinscripción activa.");
					return;
				}

				List<Materia> materiasSeleccionadasParaInscribir = new ArrayList<>();
				List<String> materiasSinProfesorOHorario = new ArrayList<>(); // Nueva lista para errores

				for (int i = 0; i < modeloMateriasConCheck.getRowCount(); i++) {
					Boolean check = (Boolean) modeloMateriasConCheck.getValueAt(i, 0);
					String nombreMateria = (String) modeloMateriasConCheck.getValueAt(i, 1);
					if (check) {
						Materia materia = gestorM.buscarMateriaPorNombre(nombreMateria);

						// --- NUEVA LÓGICA DE VALIDACIÓN ---
						// Verifica si la materia existe y si tiene profesor y horario completo.
						if (materia == null || materia.getProfesor() == null || materia.getHoraInicio() == null
								|| materia.getHoraFin() == null || materia.getDias() == null
								|| materia.getDias().isEmpty()) {

							materiasSinProfesorOHorario.add(nombreMateria); // Agrega el nombre de la materia a la lista
																			// de errores
						} else {
							materiasSeleccionadasParaInscribir.add(materia); // Si todo está bien, la añade para
																				// inscribir
						}
					}
				}

				// --- VERIFICACIÓN DE MATERIAS CON ERRORES ---
				if (!materiasSinProfesorOHorario.isEmpty()) {
					StringBuilder mensajeError = new StringBuilder();
					mensajeError.append(
							"Las siguientes materias seleccionadas no se pueden inscribir porque les falta profesor o información de horario:\n\n");
					for (String nombreMateria : materiasSinProfesorOHorario) {
						mensajeError.append("- ").append(nombreMateria).append("\n");
					}
					mensajeError.append(
							"\nPor favor, asigna un profesor y horario completo a estas materias antes de intentar inscribirlas.");
					JOptionPane.showMessageDialog(null, mensajeError.toString(), "Materias Incompletas Detectadas",
							JOptionPane.WARNING_MESSAGE);
					return; // Detiene la inscripción si hay materias con errores.
				}

				if (materiasSeleccionadasParaInscribir.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"No se seleccionaron materias válidas para inscribir (quizás todas tenían errores).");
					return;
				}

				// --- VERIFICACIÓN DE CONFLICTOS DE HORARIO (SOLO CON LAS MATERIAS VÁLIDAS) ---
				for (Materia nueva : materiasSeleccionadasParaInscribir) {
					if (gestorM.hayConflictoHorario(nueva, estudianteSeleccionado.getMaterias())) {
						JOptionPane.showMessageDialog(null,
								"Conflicto de horario con la materia: " + nueva.getNombre()
										+ ". Revise el horario del estudiante.",
								"Conflicto de Horario", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// Si pasa todas las validaciones, se aprueban las materias
				admin.aprobarMateriasSeleccionadas(estudianteSeleccionado, materiasSeleccionadasParaInscribir); // Usa
																												// la
																												// lista
																												// filtrada
				gestorU.guardarUsuarios();
				gestorM.guardarMaterias();
				s.eliminarSolicitud(estudianteSeleccionado.getCodigo()); // Elimina la solicitud original

				// --- Recarga la lista de estudiantes preinscritos ---
				// (Aquí llamarías a tu método auxiliar cargarEstudiantesPreinscritos(gestorU,
				// s); si lo tienes,
				// sino, la lógica que ya tenías para recargar el modelo).
				// Es recomendable usar el método auxiliar para mantener tu código limpio.
				// Por ahora, mantengo tu lógica para que veas el cambio:
				modeloEstudiantesPreinscritos.clear();
				for (Long codigo : s.getCodigosEstudiantesConSolicitudes()) {
					Usuario u = gestorU.buscarUsuarioPorCodigo(codigo);
					if (u instanceof Estudiante e1) {
						modeloEstudiantesPreinscritos.addElement(e1);
					}
				}
				// Eliminamos esta línea, ya que cargarEstudiantesPreinscritos o la lógica de
				// arriba ya refresca la lista completa
				// modeloEstudiantesPreinscritos.removeElement(estudianteSeleccionado);

				modeloMateriasConCheck.setRowCount(0); // Limpia la tabla de materias

				JOptionPane.showMessageDialog(null, "Materias inscritas correctamente.");
			}
		});

		PanelMaterias.add(btnInscribir); // Asegúrate de añadir el botón a tu panel
		btnInscribir.setBounds(450, 330, 100, 30); // Ajusta la posición según tu diseñ

		// -------------------------------------------------------

		JPanel CrearDocentePanel = new JPanel();
		CrearDocentePanel.setBackground(new Color(255, 255, 255));
		CrearDocentePanel.setBounds(56, 105, 668, 373);
		contentPane.add(CrearDocentePanel);
		CrearDocentePanel.setLayout(null);

		JLabel lblCreandoUsuario = new JLabel("CREACION DE CUENTA DOCENTE");
		lblCreandoUsuario.setBounds(22, 43, 296, 40);
		lblCreandoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreandoUsuario.setForeground(new Color(0, 0, 0));
		lblCreandoUsuario.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		CrearDocentePanel.add(lblCreandoUsuario);

		JLabel tstName = new JLabel("Nombre del Docente");
		tstName.setBounds(22, 156, 123, 23);
		tstName.setHorizontalAlignment(SwingConstants.LEFT);
		tstName.setOpaque(true);
		tstName.setForeground(new Color(0, 0, 0));
		tstName.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		tstName.setBorder(null);
		tstName.setBackground(new Color(255, 255, 255));
		CrearDocentePanel.add(tstName);

		JTextField NombreField = new JTextField();
		NombreField.setBounds(22, 185, 125, 22);
		NombreField.setForeground(new Color(29, 33, 35));
		NombreField.setColumns(10);
		NombreField.setBorder(null);
		CrearDocentePanel.add(NombreField);

		JSeparator separator = new JSeparator();
		separator.setBounds(22, 208, 125, 14);
		separator.setForeground(new Color(29, 33, 35));
		CrearDocentePanel.add(separator);

		JLabel txtApellido = new JLabel("Apellido del Docente");
		txtApellido.setBounds(193, 156, 123, 23);
		txtApellido.setOpaque(true);
		txtApellido.setHorizontalAlignment(SwingConstants.LEFT);
		txtApellido.setForeground(Color.BLACK);
		txtApellido.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtApellido.setBorder(null);
		txtApellido.setBackground(Color.WHITE);
		CrearDocentePanel.add(txtApellido);

		JTextField ApellidoField = new JTextField();
		ApellidoField.setBounds(193, 185, 125, 22);
		ApellidoField.setForeground(new Color(29, 33, 35));
		ApellidoField.setColumns(10);
		ApellidoField.setBorder(null);
		CrearDocentePanel.add(ApellidoField);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(193, 208, 125, 14);
		separator_1.setForeground(new Color(29, 33, 35));
		CrearDocentePanel.add(separator_1);

		JLabel txtCorreo = new JLabel("Correo");
		txtCorreo.setBounds(22, 218, 123, 23);
		txtCorreo.setOpaque(true);
		txtCorreo.setHorizontalAlignment(SwingConstants.LEFT);
		txtCorreo.setForeground(Color.BLACK);
		txtCorreo.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtCorreo.setBorder(null);
		txtCorreo.setBackground(Color.WHITE);
		CrearDocentePanel.add(txtCorreo);

		JTextField CorreoField = new JTextField();
		CorreoField.setBounds(22, 247, 125, 22);
		CorreoField.setForeground(new Color(29, 33, 35));
		CorreoField.setColumns(10);
		CorreoField.setBorder(null);
		CrearDocentePanel.add(CorreoField);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(22, 270, 125, 14);
		separator_2.setForeground(new Color(29, 33, 35));
		CrearDocentePanel.add(separator_2);

		JLabel txtCodigo = new JLabel("Codigo");
		txtCodigo.setOpaque(true);
		txtCodigo.setHorizontalAlignment(SwingConstants.LEFT);
		txtCodigo.setForeground(Color.BLACK);
		txtCodigo.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtCodigo.setBorder(null);
		txtCodigo.setBackground(Color.WHITE);
		txtCodigo.setBounds(22, 94, 123, 23);
		CrearDocentePanel.add(txtCodigo);

		JTextField CodigoField = new JTextField();
		CodigoField.setForeground(new Color(29, 33, 35));
		CodigoField.setColumns(10);
		CodigoField.setBorder(null);
		CodigoField.setBounds(22, 123, 296, 22);
		CrearDocentePanel.add(CodigoField);

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setForeground(new Color(29, 33, 35));
		separator_2_1.setBounds(22, 146, 296, 14);
		CrearDocentePanel.add(separator_2_1);

		JLabel txtContraseña = new JLabel("Contraseña");
		txtContraseña.setOpaque(true);
		txtContraseña.setHorizontalAlignment(SwingConstants.LEFT);
		txtContraseña.setForeground(Color.BLACK);
		txtContraseña.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtContraseña.setBorder(null);
		txtContraseña.setBackground(Color.WHITE);
		txtContraseña.setBounds(193, 218, 123, 23);
		CrearDocentePanel.add(txtContraseña);

		JTextField ContraseñaField = new JTextField();
		ContraseñaField.setForeground(new Color(29, 33, 35));
		ContraseñaField.setColumns(10);
		ContraseñaField.setBorder(null);
		ContraseñaField.setBounds(193, 247, 125, 22);
		CrearDocentePanel.add(ContraseñaField);

		JSeparator separator_2_2 = new JSeparator();
		separator_2_2.setForeground(new Color(29, 33, 35));
		separator_2_2.setBounds(193, 270, 125, 14);
		CrearDocentePanel.add(separator_2_2);

		JButton btnCrearDocente_2 = new JButton("AÑADIR");
		btnCrearDocente_2.setOpaque(true);
		btnCrearDocente_2.setForeground(Color.WHITE);
		btnCrearDocente_2.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnCrearDocente_2.setFocusPainted(false);
		btnCrearDocente_2.setBorderPainted(false);
		btnCrearDocente_2.setBorder(null);
		btnCrearDocente_2.setBackground(new Color(128, 0, 0));
		btnCrearDocente_2.setBounds(22, 309, 125, 43);
		CrearDocentePanel.add(btnCrearDocente_2);

		JList listEstudiantes = new JList();
		listEstudiantes.setFont(new Font("Leelawadee", Font.BOLD, 12));
		listEstudiantes.setBorder(new LineBorder(new Color(130, 135, 144)));
		listEstudiantes.setBounds(356, 53, 296, 230);
		listEstudiantes.setModel(modeloLista);

		CrearDocentePanel.add(listEstudiantes);

		JButton btnAddDocente = new JButton("GUARDAR DOCENTES");
		btnAddDocente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gestorU.guardarUsuarios();
					modeloLista.clear(); // Borra todos los elementos visuales del JList
					JOptionPane.showMessageDialog(null, "Estudiantes guardados correctamente.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Estudiantes guardados correctamente.", "ERROR",
							JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		btnAddDocente.setOpaque(true);
		btnAddDocente.setForeground(Color.WHITE);
		btnAddDocente.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnAddDocente.setFocusPainted(false);
		btnAddDocente.setBorderPainted(false);
		btnAddDocente.setBorder(null);
		btnAddDocente.setBackground(new Color(128, 0, 0));
		btnAddDocente.setBounds(435, 308, 162, 43);
		CrearDocentePanel.add(btnAddDocente);

		btnCrearDocente_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Inicializacion de los campos necesarios para la creacion de un nuevo docente
				long cod = 0;
				String nombre = "";
				String apellido = "";
				String correo = "";
				String password = "";
				try {
					// Verificacion del codigo ingresado

					String codText = CodigoField.getText().trim();
					if (codText.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo código no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					// se le asigna el valor parseado a la variable 'cod' de tipo int
					try {
						cod = Long.parseLong(codText);
						if (cod <= 0) {
							JOptionPane.showMessageDialog(null, "El código debe ser un número positivo.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Código inválido. Debe ser un número entero.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Validar posibles errores para el ingreso del nombre
					nombre = NombreField.getText().trim();
					if (nombre.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) { // Solo letras y espacios
						JOptionPane.showMessageDialog(null, "El Nombre solo puede contener letras y espacios.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Validación de Apellido
					apellido = ApellidoField.getText().trim();
					if (apellido.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Apellido no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) { // Solo letras y espacios
						JOptionPane.showMessageDialog(null, "El Apellido solo puede contener letras y espacios.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Validación de Correo
					correo = CorreoField.getText().trim();
					if (correo.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Correo no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Validación de formato de correo básico
					if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
						JOptionPane.showMessageDialog(null, "Formato de Correo inválido. Ej: usuario@dominio.com",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Validación de Contraseña
					password = ContraseñaField.getText().trim();
					if (password.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Contraseña no puede estar vacío.",
								"Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Verificación de Usuarios repetidos buscando por Código
					boolean codigoRepetido = false;

					if (gestorU.buscarUsuarioPorCodigo(cod) != null) {
						codigoRepetido = true;
					}

					if (codigoRepetido) {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con el mismo código.", "Error",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					// Después de todas las validaciones se procede a crear y agregar el nuevo
					// estudiante a la lista global manejada por gestor

					// Crear y agregar el nuevo estudiante
					Profesor nuevo = admin.crearProfesor(cod, nombre, apellido, correo, HashUtil.sha256(password));
					gestorU.agregarUsuario(nuevo);

					// Mostrar en la JList
					modeloLista.addElement(nombre + " " + apellido + " (" + cod + ")");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Código inválido. Debe ser un número.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al crear estudiante: " + ex.getMessage());
				}
			}
		});
		CrearDocentePanel.setVisible(false);
		// PANEL DE ESTUDIANTE
		// -------------------------------------------------------------------
		JPanel CrearEstudiantePanel = new JPanel();
		CrearEstudiantePanel.setBackground(new Color(255, 255, 255));
		CrearEstudiantePanel.setBounds(56, 105, 668, 373);
		contentPane.add(CrearEstudiantePanel);
		CrearEstudiantePanel.setLayout(null);

		JLabel lblCreandoUsuario1 = new JLabel("CREACION DE CUENTA ESTUDIANTIL");
		lblCreandoUsuario1.setBounds(22, 43, 296, 40);
		lblCreandoUsuario1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreandoUsuario1.setForeground(new Color(0, 0, 0));
		lblCreandoUsuario1.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		CrearEstudiantePanel.add(lblCreandoUsuario1);

		JLabel tstName1 = new JLabel("Nombre del Estudiante");
		tstName1.setBounds(22, 156, 123, 23);
		tstName1.setHorizontalAlignment(SwingConstants.LEFT);
		tstName1.setOpaque(true);
		tstName1.setForeground(new Color(0, 0, 0));
		tstName1.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		tstName1.setBorder(null);
		tstName1.setBackground(new Color(255, 255, 255));
		CrearEstudiantePanel.add(tstName1);

		JTextField NombreField1_1 = new JTextField();
		NombreField1_1.setBounds(22, 185, 125, 22);
		NombreField1_1.setForeground(new Color(29, 33, 35));
		NombreField1_1.setColumns(10);
		NombreField1_1.setBorder(null);
		CrearEstudiantePanel.add(NombreField1_1);

		JSeparator separator1 = new JSeparator();
		separator1.setBounds(22, 208, 125, 14);
		separator1.setForeground(new Color(29, 33, 35));
		CrearEstudiantePanel.add(separator1);

		JLabel txtApellido1 = new JLabel("Apellido del Estudiante");
		txtApellido1.setBounds(193, 156, 123, 23);
		txtApellido1.setOpaque(true);
		txtApellido1.setHorizontalAlignment(SwingConstants.LEFT);
		txtApellido1.setForeground(Color.BLACK);
		txtApellido1.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtApellido1.setBorder(null);
		txtApellido1.setBackground(Color.WHITE);
		CrearEstudiantePanel.add(txtApellido1);

		JTextField ApellidoField1_1 = new JTextField();
		ApellidoField1_1.setBounds(193, 185, 125, 22);
		ApellidoField1_1.setForeground(new Color(29, 33, 35));
		ApellidoField1_1.setColumns(10);
		ApellidoField1_1.setBorder(null);
		CrearEstudiantePanel.add(ApellidoField1_1);

		JSeparator separator_11 = new JSeparator();
		separator_11.setBounds(193, 208, 125, 14);
		separator_11.setForeground(new Color(29, 33, 35));
		CrearEstudiantePanel.add(separator_11);

		JLabel txtCorreo1 = new JLabel("Correo");
		txtCorreo1.setBounds(22, 218, 123, 23);
		txtCorreo1.setOpaque(true);
		txtCorreo1.setHorizontalAlignment(SwingConstants.LEFT);
		txtCorreo1.setForeground(Color.BLACK);
		txtCorreo1.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtCorreo1.setBorder(null);
		txtCorreo1.setBackground(Color.WHITE);
		CrearEstudiantePanel.add(txtCorreo1);

		JTextField CorreoField1_1 = new JTextField();
		CorreoField1_1.setBounds(22, 247, 125, 22);
		CorreoField1_1.setForeground(new Color(29, 33, 35));
		CorreoField1_1.setColumns(10);
		CorreoField1_1.setBorder(null);
		CrearEstudiantePanel.add(CorreoField1_1);

		JSeparator separator_21 = new JSeparator();
		separator_21.setBounds(22, 270, 125, 14);
		separator_21.setForeground(new Color(29, 33, 35));
		CrearEstudiantePanel.add(separator_21);

		JLabel txtCodigo1 = new JLabel("Codigo");
		txtCodigo1.setOpaque(true);
		txtCodigo1.setHorizontalAlignment(SwingConstants.LEFT);
		txtCodigo1.setForeground(Color.BLACK);
		txtCodigo1.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtCodigo1.setBorder(null);
		txtCodigo1.setBackground(Color.WHITE);
		txtCodigo1.setBounds(22, 94, 123, 23);
		CrearEstudiantePanel.add(txtCodigo1);

		JTextField CodigoField1_1 = new JTextField();
		CodigoField1_1.setForeground(new Color(29, 33, 35));
		CodigoField1_1.setColumns(10);
		CodigoField1_1.setBorder(null);
		CodigoField1_1.setBounds(22, 123, 296, 22);
		CrearEstudiantePanel.add(CodigoField1_1);

		JSeparator separator_2_11 = new JSeparator();
		separator_2_11.setForeground(new Color(29, 33, 35));
		separator_2_11.setBounds(22, 146, 296, 14);
		CrearEstudiantePanel.add(separator_2_11);

		JLabel txtContraseña1 = new JLabel("Contraseña");
		txtContraseña1.setOpaque(true);
		txtContraseña1.setHorizontalAlignment(SwingConstants.LEFT);
		txtContraseña1.setForeground(Color.BLACK);
		txtContraseña1.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		txtContraseña1.setBorder(null);
		txtContraseña1.setBackground(Color.WHITE);
		txtContraseña1.setBounds(193, 218, 123, 23);
		CrearEstudiantePanel.add(txtContraseña1);

		JTextField ContraseñaField1_1 = new JTextField();
		ContraseñaField1_1.setForeground(new Color(29, 33, 35));
		ContraseñaField1_1.setColumns(10);
		ContraseñaField1_1.setBorder(null);
		ContraseñaField1_1.setBounds(193, 247, 125, 22);
		CrearEstudiantePanel.add(ContraseñaField1_1);

		JSeparator separator_2_21 = new JSeparator();
		separator_2_21.setForeground(new Color(29, 33, 35));
		separator_2_21.setBounds(193, 270, 125, 14);
		CrearEstudiantePanel.add(separator_2_21);

		JButton btnCrearEstudiante_21 = new JButton("AÑADIR");

		btnCrearEstudiante_21.setOpaque(true);
		btnCrearEstudiante_21.setForeground(Color.WHITE);
		btnCrearEstudiante_21.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnCrearEstudiante_21.setFocusPainted(false);
		btnCrearEstudiante_21.setBorderPainted(false);
		btnCrearEstudiante_21.setBorder(null);
		btnCrearEstudiante_21.setBackground(new Color(128, 0, 0));
		btnCrearEstudiante_21.setBounds(22, 309, 125, 43);
		CrearEstudiantePanel.add(btnCrearEstudiante_21);

		JList listEstudiantes1 = new JList();
		listEstudiantes1.setFont(new Font("Leelawadee", Font.BOLD, 12));
		listEstudiantes1.setBorder(new LineBorder(new Color(130, 135, 144)));
		listEstudiantes1.setBounds(363, 50, 296, 223);
		listEstudiantes1.setModel(modeloLista);

		CrearEstudiantePanel.add(listEstudiantes1);

		JButton btnCrearEstudiante_2_11 = new JButton("GUARDAR ESTUDIANTES");
		btnCrearEstudiante_2_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gestorU.guardarUsuarios();
					modeloLista.clear(); // Borra todos los elementos visuales del JList
					JOptionPane.showMessageDialog(null, "Estudiantes guardados correctamente.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Estudiantes guardados correctamente.", "ERROR",
							JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		btnCrearEstudiante_2_11.setOpaque(true);
		btnCrearEstudiante_2_11.setForeground(Color.WHITE);
		btnCrearEstudiante_2_11.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnCrearEstudiante_2_11.setFocusPainted(false);
		btnCrearEstudiante_2_11.setBorderPainted(false);
		btnCrearEstudiante_2_11.setBorder(null);
		btnCrearEstudiante_2_11.setBackground(new Color(128, 0, 0));
		btnCrearEstudiante_2_11.setBounds(433, 309, 162, 43);
		CrearEstudiantePanel.add(btnCrearEstudiante_2_11);

		btnCrearEstudiante_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Inicializacion de los campos necesarios para la creacion de un nuevo docente
				long cod = 0;
				String nombre = "";
				String apellido = "";
				String correo = "";
				String password = "";
				try {
					// Verificacion del codigo ingresado

					String codText = CodigoField1_1.getText().trim();
					if (codText.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo código no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					// se le asigna el valor parseado a la variable 'cod' de tipo int
					try {
						cod = Long.parseLong(codText);
						if (cod <= 0) {
							JOptionPane.showMessageDialog(null, "El código debe ser un número positivo.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Código inválido. Debe ser un número entero.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Validar posibles errores para el ingreso del nombre
					nombre = NombreField1_1.getText().trim();
					if (nombre.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) { // Solo letras y espacios
						JOptionPane.showMessageDialog(null, "El Nombre solo puede contener letras y espacios.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Validación de Apellido
					apellido = ApellidoField1_1.getText().trim();
					if (apellido.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Apellido no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$")) { // Solo letras y espacios
						JOptionPane.showMessageDialog(null, "El Apellido solo puede contener letras y espacios.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Validación de Correo
					correo = CorreoField1_1.getText().trim();
					if (correo.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Correo no puede estar vacío.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Validación de formato de correo básico
					if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
						JOptionPane.showMessageDialog(null, "Formato de Correo inválido. Ej: usuario@dominio.com",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Validación de Contraseña
					password = ContraseñaField1_1.getText().trim();
					if (password.isEmpty()) { // JOPtionPane que muestra si el campo esta vacio
						JOptionPane.showMessageDialog(null, "El campo Contraseña no puede estar vacío.",
								"Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Verificación de Usuarios repetidos buscando por Código
					boolean codigoRepetido = false;

					if (gestorU.buscarUsuarioPorCodigo(cod) != null) {
						codigoRepetido = true;
					}

					if (codigoRepetido) {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con el mismo código.", "Error",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					// Después de todas las validaciones se procede a crear y agregar el nuevo
					// estudiante a la lista global manejada por gestor

					// Crear y agregar el nuevo estudiante
					Estudiante nuevo = admin.crearEstudiante(cod, nombre, apellido, correo, HashUtil.sha256(password));
					gestorU.agregarUsuario(nuevo);

					// Mostrar en la JList
					modeloLista.addElement(nombre + " " + apellido + " (" + cod + ")");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Código inválido. Debe ser un número.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al crear estudiante: " + ex.getMessage());
				}
			}
		});

		CrearEstudiantePanel.setVisible(false);

		// ---------------------------------------------
		Header.setBackground(new Color(255, 255, 255));
		Header.setBounds(0, 0, 800, 39);
		contentPane.add(Header);
		Header.setLayout(null);

		JPanel VolverPanel = new JPanel();
		VolverPanel.setForeground(new Color(0, 0, 0));
		VolverPanel.setLayout(null);
		VolverPanel.setBackground(Color.WHITE);
		VolverPanel.setBounds(0, 0, 41, 39);
		Header.add(VolverPanel);

		JLabel VolverTxt = new JLabel("<");
		VolverTxt.setHorizontalAlignment(SwingConstants.CENTER);
		VolverTxt.setForeground(new Color(0, 0, 0));
		VolverTxt.setFont(new Font("Verdana", Font.BOLD, 22));
		VolverTxt.setBounds(0, 0, 41, 39);
		VolverPanel.add(VolverTxt);
		VolverPanel.addMouseListener(new MouseAdapter() {
			@Override
			// Volver a la anterior ventana
			public void mouseClicked(MouseEvent e) {
				dispose();
				Inicio frameInicio = new Inicio(gestorU, gestorM, s);
				frameInicio.setVisible(true);
			}

			@Override
			// Cambiar el color del fondo del label
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

		JPanel ExitPanel = new JPanel();
		ExitPanel.setBackground(new Color(255, 255, 255));
		ExitPanel.setBounds(759, 0, 41, 39);
		Header.add(ExitPanel);
		ExitPanel.setLayout(null);

		JLabel ExitTxt = new JLabel("X");
		ExitTxt.setBounds(0, 0, 41, 39);
		ExitPanel.add(ExitTxt);
		ExitPanel.addMouseListener(new MouseAdapter() {
			@Override
			// Cerrar la ventana
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			// Cambiar el color del fondo del label
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

		JLabel Administrador = new JLabel("ADMINISTRADOR");
		Administrador.setHorizontalAlignment(SwingConstants.CENTER);
		Administrador.setBounds(312, 0, 150, 39);
		Header.add(Administrador);
		Administrador.setOpaque(true);
		Administrador.setForeground(Color.BLACK);
		Administrador.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		Administrador.setBorder(null);
		Administrador.setBackground(new Color(255, 255, 255));

		JPanel NavBar = new JPanel();
		NavBar.setBackground(new Color(128, 0, 0));
		NavBar.setBounds(0, 39, 800, 44);
		contentPane.add(NavBar);
		NavBar.setLayout(null);

		JButton btnCrearEstudiante = new JButton("CREAR ESTUDIANTE");
		btnCrearEstudiante.addMouseListener(new MouseAdapter() {
			@Override

			public void mouseEntered(MouseEvent e) {
				// Cambio de color al entrar al btn
				btnCrearEstudiante.setBackground(new Color(80, 0, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Cambio de color al salir al btn
				btnCrearEstudiante.setBackground(new Color(128, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btnCrearEstudiante.setBackground(new Color(80, 0, 0));
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
				panelinicio.setVisible(true);
				CrearEstudiantePanel.setVisible(false);
				CrearDocentePanel.setVisible(false);
				PreinscripcionesPanel.setVisible(false);
				AsignarMateriaPanel.setVisible(false);

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

		btnCrearEstudiante.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnCrearEstudiante.setContentAreaFilled(false); // Quita el área rellena
		btnCrearEstudiante.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnCrearEstudiante.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnCrearEstudiante.setForeground(Color.WHITE);
		btnCrearEstudiante.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnCrearEstudiante.setBorder(null);
		btnCrearEstudiante.setBackground(new Color(128, 0, 0));
		btnCrearEstudiante.setBounds(335, 0, 111, 43);
		NavBar.add(btnCrearEstudiante);

		JButton btnCrearDocente = new JButton("CREAR DOCENTE");

		btnCrearDocente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCrearDocente.setBackground(new Color(80, 0, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCrearDocente.setBackground(new Color(128, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btnCrearDocente.setBackground(new Color(80, 0, 0));
			}
		});
		btnCrearDocente.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnCrearDocente.setContentAreaFilled(false); // Quita el área rellena
		btnCrearDocente.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnCrearDocente.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnCrearDocente.setForeground(Color.WHITE);
		btnCrearDocente.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnCrearDocente.setBorder(null);
		btnCrearDocente.setBackground(new Color(128, 0, 0));
		btnCrearDocente.setBounds(459, 0, 89, 43);
		NavBar.add(btnCrearDocente);

		JButton btnPreinscripciones = new JButton("PREINSCRIPCIONES");
		listaEstudiantesPreinscritos.setModel(modeloEstudiantesPreinscritos);

		btnPreinscripciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPreinscripciones.setBackground(new Color(80, 0, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnPreinscripciones.setBackground(new Color(128, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btnPreinscripciones.setBackground(new Color(80, 0, 0));
			}
		});
		btnPreinscripciones.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnPreinscripciones.setContentAreaFilled(false); // Quita el área rellena
		btnPreinscripciones.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnPreinscripciones.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnPreinscripciones.setForeground(Color.WHITE);
		btnPreinscripciones.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnPreinscripciones.setBorder(null);
		btnPreinscripciones.setBackground(new Color(128, 0, 0));
		btnPreinscripciones.setBounds(560, 0, 111, 43);
		NavBar.add(btnPreinscripciones);

		JButton btnAsignarMateria = new JButton("ASIGNAR MATERIA");

		btnAsignarMateria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAsignarMateria.setBackground(new Color(80, 0, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAsignarMateria.setBackground(new Color(128, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btnAsignarMateria.setBackground(new Color(80, 0, 0));
			}
		});
		btnAsignarMateria.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnAsignarMateria.setContentAreaFilled(false); // Quita el área rellena
		btnAsignarMateria.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnAsignarMateria.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnAsignarMateria.setBounds(680, 0, 103, 43);
		NavBar.add(btnAsignarMateria);
		btnAsignarMateria.setForeground(Color.WHITE);
		btnAsignarMateria.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnAsignarMateria.setBorder(null);
		btnAsignarMateria.setBackground(new Color(128, 0, 0));
		// CARGAR MATERIAS
		List<Materia> materiasDisponibles = gestorM.cargarMaterias();
		for (Materia materia : gestorM.getMaterias()) {
			comboBox.addItem(materia); // Solo agregas el nombre (String)
		}

		JLabel lblCreandoUsuario_2 = new JLabel("CREACION DE CUENTA ESTUDIANTIL");
		lblCreandoUsuario_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreandoUsuario_2.setForeground(Color.BLACK);
		lblCreandoUsuario_2.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblCreandoUsuario_2.setBounds(0, 0, 296, 40);
		contentPane.add(lblCreandoUsuario_2);

		// Cargar y escalar la imagen
		ImageIcon ico11 = new ImageIcon(AdminGUI.class.getResource("/Imagenes/Escudo_UD.svg.png"));
		Image imagen11 = ico11.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada11 = new ImageIcon(imagen11);

		// METODOS DE ACTIONLISTENIGN
		btnCrearEstudiante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloLista.clear(); // Borra todos los elementos visuales del JList
				CrearEstudiantePanel.setVisible(true);
				CrearDocentePanel.setVisible(false);
				PreinscripcionesPanel.setVisible(false);
				AsignarMateriaPanel.setVisible(false);
				panelinicio.setVisible(false);
				contentPane.setComponentZOrder(CrearEstudiantePanel, 1); // al frente
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		btnCrearDocente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloLista.clear(); // Borra todos los elementos visuales del JList
				CrearEstudiantePanel.setVisible(false);
				CrearDocentePanel.setVisible(true);
				PreinscripcionesPanel.setVisible(false);
				AsignarMateriaPanel.setVisible(false);
				panelinicio.setVisible(false);
				contentPane.setComponentZOrder(CrearDocentePanel, 1); // al frente
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		btnPreinscripciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				modeloLista.clear(); // Borra todos los elementos visuales del JList
				CrearEstudiantePanel.setVisible(false);
				CrearDocentePanel.setVisible(false);
				PreinscripcionesPanel.setVisible(true);
				AsignarMateriaPanel.setVisible(false);
				panelinicio.setVisible(false);
				// Mostrar el panel de instrucciones y ocultar los demás subpaneles
				panelInstrucciones.setVisible(true);
				panelGestinPreinscripcion.setVisible(false);
				panelReportarProblema.setVisible(false);

				contentPane.setComponentZOrder(PreinscripcionesPanel, 1); // al frente
				contentPane.revalidate();
				contentPane.repaint();

				// ✅ Desactivar botón si ya tiene preinscripción
				boolean tienePreinscripcion = s.tienePreinscripcion(estudianteActual);
				btnInscribir.setEnabled(!tienePreinscripcion);

				// ACTUALIZAR JList de estudiantes con solicitudes
				modeloEstudiantesPreinscritos.clear();

				for (Usuario u : gestorU.getUsuarios()) {
					if (u instanceof Estudiante estudiante) {
						List<Materia> materiasSolicitadas = s.obtenerSolicitudes(estudiante.getCodigo());

						if (materiasSolicitadas != null && !materiasSolicitadas.isEmpty()) {
							modeloEstudiantesPreinscritos.addElement(estudiante);
						}
					}
				}

				// Limpiar tabla por si quedó algo antes
				modeloMateriasConCheck.setRowCount(0);

				listaEstudiantesPreinscritos.revalidate();
				listaEstudiantesPreinscritos.repaint();

				PreinscripcionesPanel.revalidate();
				PreinscripcionesPanel.repaint();

			}
		});

		btnAsignarMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloLista.clear(); // Borra todos los elementos visuales del JList
				CrearEstudiantePanel.setVisible(false);
				CrearDocentePanel.setVisible(false);
				PreinscripcionesPanel.setVisible(false);
				AsignarMateriaPanel.setVisible(true);
				panelinicio.setVisible(false);
				contentPane.setComponentZOrder(AsignarMateriaPanel, 1); // al frente
				contentPane.revalidate();
				contentPane.repaint();
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInstrucciones.setVisible(false);
				panelGestinPreinscripcion.setVisible(true);
				panelReportarProblema.setVisible(false);

				cargarEstudiantesPreinscritos(gestorU, s); // Pasa los parámetros necesarios
			}
		});

		JLabel VolverTxt_2 = new JLabel("<");
		VolverTxt_2.setHorizontalAlignment(SwingConstants.CENTER);
		VolverTxt_2.setForeground(Color.BLACK);
		VolverTxt_2.setFont(new Font("Verdana", Font.BOLD, 22));
		VolverTxt_2.setBounds(0, 0, 41, 39);
		VolverPanel_2.add(VolverTxt_2);
		VolverPanel_2.addMouseListener(new MouseAdapter() {
			@Override
			// Volver a la anterior ventana
			public void mouseClicked(MouseEvent e) {
				panelInstrucciones.setVisible(true);
				panelGestinPreinscripcion.setVisible(false);
				panelReportarProblema.setVisible(false);
			}

			@Override
			// Cambiar el color del fondo del label
			public void mouseEntered(MouseEvent e) {
				VolverTxt_2.setFont(new Font("Verdana", Font.BOLD, 23));
				VolverTxt_2.setForeground(Color.white);
				VolverPanel_2.setBackground(new Color(0, 139, 224));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				VolverTxt_2.setFont(new Font("Verdana", Font.BOLD, 22));
				VolverTxt_2.setForeground(Color.black);
				VolverPanel_2.setBackground(Color.white);
			}
		});

		JPanel VolverPanel_1 = new JPanel();
		VolverPanel_1.setLayout(null);
		VolverPanel_1.setForeground(Color.BLACK);
		VolverPanel_1.setBackground(Color.WHITE);
		VolverPanel_1.setBounds(0, 0, 41, 39);
		panelReportarProblema.add(VolverPanel_1);

		JLabel VolverTxt_1 = new JLabel("<");
		VolverTxt_1.setHorizontalAlignment(SwingConstants.CENTER);
		VolverTxt_1.setForeground(Color.BLACK);
		VolverTxt_1.setFont(new Font("Verdana", Font.BOLD, 22));
		VolverTxt_1.setBounds(0, 0, 41, 39);
		VolverPanel_1.add(VolverTxt_1);
		VolverPanel_1.addMouseListener(new MouseAdapter() {
			@Override
			// Volver a la anterior ventana
			public void mouseClicked(MouseEvent e) {
				panelInstrucciones.setVisible(true);
				panelGestinPreinscripcion.setVisible(false);
				panelReportarProblema.setVisible(false);
			}

			@Override
			// Cambiar el color del fondo del label
			public void mouseEntered(MouseEvent e) {
				VolverTxt_1.setFont(new Font("Verdana", Font.BOLD, 23));
				VolverTxt_1.setForeground(Color.white);
				VolverPanel_1.setBackground(new Color(0, 139, 224));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				VolverTxt_1.setFont(new Font("Verdana", Font.BOLD, 22));
				VolverTxt_1.setForeground(Color.black);
				VolverPanel_1.setBackground(Color.white);
			}
		});

		btnInscribir.setBounds(82, 236, 85, 21);

		cargarEstudiantesPreinscritos(gestorU, s); // Pasa los parámetros necesarios

	}

	// Dentro de la clase AdminGUI
	private void cargarEstudiantesPreinscritos(GestorUsuarios gestorU, GestionSolicitudes s) {
		System.out.println("DEBUG (AdminGUI): Recargando lista de estudiantes preinscritos...");
		// Siempre limpia el modelo antes de volver a llenarlo
		modeloEstudiantesPreinscritos.clear();

		// Itera sobre todos los usuarios para encontrar estudiantes con solicitudes
		for (Usuario u : gestorU.getUsuarios()) {
			if (u instanceof Estudiante estudiante) { // Verifica si el usuario es un Estudiante
				// Obtiene las solicitudes de este estudiante desde GestionSolicitudes
				List<Materia> materiasSolicitadas = s.obtenerSolicitudes(estudiante.getCodigo());

				// Si hay solicitudes para este estudiante, lo añade al modelo de la lista
				if (materiasSolicitadas != null && !materiasSolicitadas.isEmpty()) {
					modeloEstudiantesPreinscritos.addElement(estudiante);
					System.out.println("DEBUG (AdminGUI): Agregado estudiante con solicitud: " + estudiante.getNombre()
							+ " " + estudiante.getApellido());
				}
			}
		}
		System.out
				.println("DEBUG (AdminGUI): Total de estudiantes en la lista: " + modeloEstudiantesPreinscritos.size());

		// Después de actualizar el modelo, es crucial que la JList y el JScrollPane se
		// actualicen visualmente
		listaEstudiantesPreinscritos.revalidate();
		listaEstudiantesPreinscritos.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
	}
}
