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

public class ProfesorGUI extends JFrame {
	private JPanel contentPane;
	private DefaultListModel<String> modeloLista = new DefaultListModel<>();
	private List<Usuario> estudiantes = new ArrayList<>();
	private List<Usuario> profesores = new ArrayList<>();

	// Guardar informacion de la posicion de mouse para mover la ventana
	int xMouse, yMouse;

	/**
	 * Create the frame.
	 */
	public ProfesorGUI(Profesor profesor, GestorUsuarios gestorU, GestorMaterias gestorM, GestionSolicitudes s) {

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

		JLabel Docente = new JLabel("DOCENTE");
		Docente.setHorizontalAlignment(SwingConstants.CENTER);
		Docente.setBounds(325, 0, 150, 39);
		Header.add(Docente);
		Docente.setFont(new Font("Leelawadee UI", Font.BOLD, 14)); // Fuente
		Docente.setForeground(Color.BLACK); // Color del texto
		Docente.setBackground(new Color(255, 255, 255)); // Color de fondo por defecto
		Docente.setOpaque(true);
		Docente.setBorder(null);

		JPanel NavBar = new JPanel();
		NavBar.setBackground(new Color(128, 0, 0));
		NavBar.setBounds(0, 39, 800, 44);
		contentPane.add(NavBar);
		NavBar.setLayout(null);

		JLabel lblNewLabel = new JLabel(
				profesor.getNombre().toUpperCase() + " " + profesor.getApellido().toUpperCase()); // ← Aquí usamos el
																									// parámetro
																									// directamente
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 14));
		lblNewLabel.setBounds(80, 1, 191, 40);
		NavBar.add(lblNewLabel);

		// INICIO
		JPanel panelinicio = new JPanel();
		panelinicio.setBounds(0, 83, 800, 417);
		contentPane.add(panelinicio);
		panelinicio.setLayout(null);
		// Asignar imagen al JLabel
		// Cargar y escalar la imagen
		ImageIcon ico = new ImageIcon(Login.class.getResource("/Imagenes/FondoInicio.png"));
		Image imagen = ico.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(imagen);

		JPanel panelInstruccionesDocente = new JPanel();
		panelInstruccionesDocente.setLayout(null);
		panelInstruccionesDocente.setBackground(Color.WHITE);
		panelInstruccionesDocente.setBounds(70, 22, 656, 364);
		panelinicio.add(panelInstruccionesDocente);

		// Mensaje de bienvenida
		JLabel lblBienvenidaDoc = new JLabel("¡Bienvenido Docente!");
		lblBienvenidaDoc.setFont(new Font("Leelawadee UI", Font.BOLD, 20));
		lblBienvenidaDoc.setBounds(50, 30, 400, 30);
		panelInstruccionesDocente.add(lblBienvenidaDoc);

		// Instrucciones
		JTextArea instruccionesDoc = new JTextArea("Puedes realizar las siguientes funciones:\n\n"
				+ "• Ingresar y modificar las notas finales de los estudiantes asignados.\n"
				+ "• Consultar tu horario semanal detallado por curso, grupo y aula.\n"
				+ "• Ver la lista de estudiantes inscritos en cada materia.\n"
				+ "• Gestionar observaciones o notas adicionales asociadas a un estudiante.\n\n"
				+ "Asegúrate de ingresar las calificaciones dentro de los plazos establecidos para evitar inconvenientes.");

		instruccionesDoc.setFont(new Font("Leelawadee UI", Font.PLAIN, 16));
		instruccionesDoc.setEditable(false);
		instruccionesDoc.setBackground(Color.WHITE);
		instruccionesDoc.setBounds(50, 80, 500, 200);
		instruccionesDoc.setLineWrap(true);
		instruccionesDoc.setWrapStyleWord(true);
		panelInstruccionesDocente.add(instruccionesDoc);

		JLabel imgFondo = new JLabel("");
		imgFondo.setBounds(-90, 0, 974, 461);
		panelinicio.add(imgFondo);
		imgFondo.setHorizontalAlignment(SwingConstants.CENTER);
		imgFondo.setIcon(imagenEscalada);

		// ---------------- PANEL NOTAS ----------------
		JPanel panelNotas = new JPanel();
		panelNotas.setLayout(null);
		panelNotas.setBounds(71, 106, 655, 363);
		panelNotas.setVisible(false); // Mantenerlo oculto por defecto como en tu código original
		contentPane.add(panelNotas);

		JLabel lblSeleccionaMateria = new JLabel("Selecciona una materia:");
		lblSeleccionaMateria.setBounds(20, 10, 200, 25);
		lblSeleccionaMateria.setFont(new Font("Leelawadee UI", Font.PLAIN, 14));
		panelNotas.add(lblSeleccionaMateria);

		JComboBox<Materia> comboMaterias = new JComboBox<>();
		comboMaterias.setBounds(200, 10, 300, 25);
		panelNotas.add(comboMaterias);

		// Crear modelo de tabla personalizado para controlar la edición de celdas
		DefaultTableModel modeloNotas = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Las columnas 0 (Código), 1 (Nombre) y 5 (Promedio) NO son editables
				return column != 0 && column != 1 && column != 5;
			}
		};
		modeloNotas.addColumn("Código");
		modeloNotas.addColumn("Nombre");
		modeloNotas.addColumn("Nota 1 (35%)");
		modeloNotas.addColumn("Nota 2 (35%)");
		modeloNotas.addColumn("Nota 3 (30%)");
		modeloNotas.addColumn("Promedio");

		// Crear tabla con el modelo
		JTable tablaNotas = new JTable(modeloNotas);

		// Estética de la tabla
		tablaNotas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tablaNotas.setRowHeight(30);
		tablaNotas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tablaNotas.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollNotas = new JScrollPane(tablaNotas);
		scrollNotas.setBounds(20, 50, 610, 230);
		panelNotas.add(scrollNotas);

		// Centrar contenido
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
			tablaNotas.getColumnModel().getColumn(i).setCellRenderer(centro);
		}

		// Desactivar redimensión lateral
		for (int i = 0; i < tablaNotas.getColumnCount(); i++) {
			tablaNotas.getColumnModel().getColumn(i).setResizable(false);
		}

		// --- Configurar el CellEditor para las columnas de notas (2, 3, 4) ---
		// Este editor personalizado se encargará de la validación
		TableCellEditor notaCellEditor = new DefaultCellEditor(new JTextField()) {
			// Patrón para permitir números con hasta dos decimales, entre 0.0 y 5.0
			private static final Pattern NUMERIC_PATTERN = Pattern
					.compile("^(?:[0-4](?:\\.[0-9]{1,2})?|5(?:\\.0{1,2})?)$");

			@Override
			public boolean stopCellEditing() {
				String value = ((JTextField) getComponent()).getText().trim();
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
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				// Al iniciar la edición, seleccionamos todo el texto para facilitar la
				// sobreescritura
				JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				editor.selectAll();
				return editor;
			}
		};

		// Asignar el editor personalizado a las columnas de notas
		tablaNotas.getColumnModel().getColumn(2).setCellEditor(notaCellEditor); // Nota 1 (35%)
		tablaNotas.getColumnModel().getColumn(3).setCellEditor(notaCellEditor); // Nota 2 (35%)
		tablaNotas.getColumnModel().getColumn(4).setCellEditor(notaCellEditor); // Nota 3 (30%)
		// --- Fin de la configuración del CellEditor ---

		JButton btnGuardarNotas = new JButton("Guardar Notas");
		btnGuardarNotas.setBounds(480, 300, 150, 30);
		btnGuardarNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		panelNotas.add(btnGuardarNotas);

		// Cargar materias del profesor
		// Asegúrate de que 'gestorM' y 'profesor' estén correctamente inicializados en
		// tu contexto.
		for (Materia m : gestorM.getMaterias()) {
			if (m.getProfesor() != null && m.getProfesor().equals(profesor)) {
				comboMaterias.addItem(m);
			}
		}

		// Al seleccionar una materia, llenar tabla
		comboMaterias.addActionListener(e -> {
			Materia materiaSeleccionada = (Materia) comboMaterias.getSelectedItem();
			modeloNotas.setRowCount(0);
			if (materiaSeleccionada != null) {
				for (Estudiante est : materiaSeleccionada.getEstudiantes()) {
					List<Double> notas = materiaSeleccionada.getNotasEstudiante(est);
					double prom = materiaSeleccionada.calcularPromedio(est);
					// Al añadir la fila, las notas se insertan como String para mantener el formato
					// si es necesario
					modeloNotas.addRow(new Object[] { est.getCodigo(), est.getNombre(), notas.get(0), notas.get(1),
							notas.get(2), String.format("%.2f", prom) });
				}
			}
		});

		// Guardar notas
		btnGuardarNotas.addActionListener(e -> {
			Materia materia = (Materia) comboMaterias.getSelectedItem();
			if (materia == null)
				return;

			Materia materiaReal = gestorM.buscarMateriaPorCodigo(materia.getCodigo());
			if (materiaReal == null) {
				JOptionPane.showMessageDialog(panelNotas, "No se encontró la materia para guardar las notas.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Asegurarse de que cualquier edición en curso se detenga antes de guardar.
			// Esto es crucial para que el CellEditor realice su validación final.
			if (tablaNotas.isEditing()) {
				// Si stopCellEditing() retorna false, significa que la edición no se pudo
				// detener
				// debido a una validación fallida, y no debemos proceder con el guardado.
				if (!tablaNotas.getCellEditor().stopCellEditing()) {
					return;
				}
			}

			try {
				for (int i = 0; i < modeloNotas.getRowCount(); i++) {
					long codigoEst = (long) modeloNotas.getValueAt(i, 0);
					// Asegúrate de que 'gestorU' esté correctamente inicializado
					Estudiante est = (Estudiante) gestorU.buscarUsuarioPorCodigo(codigoEst);

					// Obtener los valores de la tabla. Ya han sido validados por el CellEditor,
					// pero es bueno hacer un último chequeo de null/vacío.
					String sN1 = modeloNotas.getValueAt(i, 2) != null ? modeloNotas.getValueAt(i, 2).toString().trim()
							: "";
					String sN2 = modeloNotas.getValueAt(i, 3) != null ? modeloNotas.getValueAt(i, 3).toString().trim()
							: "";
					String sN3 = modeloNotas.getValueAt(i, 4) != null ? modeloNotas.getValueAt(i, 4).toString().trim()
							: "";

					if (sN1.isEmpty() || sN2.isEmpty() || sN3.isEmpty()) {
						JOptionPane.showMessageDialog(panelNotas,
								"Por favor completa todas las notas del estudiante: " + est.getNombre(), "Error",
								JOptionPane.ERROR_MESSAGE);
						return; // Detiene el guardado
					}

					double n1 = Double.parseDouble(sN1);
					double n2 = Double.parseDouble(sN2);
					double n3 = Double.parseDouble(sN3);

					// No es estrictamente necesario re-validar el rango aquí si el CellEditor
					// funciona bien,
					// pero puede ser un respaldo.
					if (n1 < 0.0 || n1 > 5.0 || n2 < 0.0 || n2 > 5.0 || n3 < 0.0 || n3 > 5.0) {
						JOptionPane.showMessageDialog(panelNotas,
								"Las notas deben estar entre 0.0 y 5.0 para el estudiante: " + est.getNombre(),
								"Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}

					materiaReal.agregarNotas(materiaReal, est, n1, n2, n3);

					// Calcular promedio y actualizar columna
					double promedio = materiaReal.calcularPromedio(est);
					modeloNotas.setValueAt(String.format("%.2f", promedio), i, 5); // columna 5 = promedio
				}

				// Asegúrate de que 'gestorM' esté correctamente inicializado
				gestorM.guardarMaterias();
				JOptionPane.showMessageDialog(panelNotas, "Notas guardadas exitosamente.");

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

		// ---------------- PANEL GRUPOS ----------------
		JPanel panelGrupos = new JPanel();
		panelGrupos.setLayout(null);
		panelGrupos.setBounds(71, 106, 655, 363);
		panelGrupos.setVisible(false);
		contentPane.add(panelGrupos);

		DefaultTableModel modeloGrupo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloGrupo.setColumnIdentifiers(
				new Object[] { "Cod.", "Nombre", "Créditos", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" });

		JTable tablaGrupo = new JTable(modeloGrupo);
		tablaGrupo.setRowHeight(30);
		JScrollPane scrollGrupo = new JScrollPane(tablaGrupo);
		scrollGrupo.setBounds(20, 20, 610, 300);
		panelGrupos.add(scrollGrupo);

		for (Materia m : gestorM.getMaterias()) {
			if (m.getProfesor() != null && m.getProfesor().equals(profesor)) {
				String[] fila = new String[9];
				fila[0] = String.valueOf(m.getCodigo());
				fila[1] = m.getNombre();
				fila[2] = String.valueOf(m.getCreditos());
				for (int i = 3; i < 9; i++)
					fila[i] = "";

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
				modeloGrupo.addRow(fila);
			}
		}

		JButton btnNotas = new JButton("NOTAS");
		btnNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelinicio.setVisible(false);
				panelGrupos.setVisible(false);
				panelNotas.setVisible(true);
				panelNotas.revalidate();
				panelNotas.repaint();
			}
		});

		btnNotas.addMouseListener(new MouseAdapter() {
			@Override

			public void mouseEntered(MouseEvent e) {
				// Cambio de color al entrar al btn
				btnNotas.setBackground(new Color(80, 0, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Cambio de color al salir al btn
				btnNotas.setBackground(new Color(128, 0, 0));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btnNotas.setBackground(new Color(80, 0, 0));
			}
		});
		btnNotas.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnNotas.setContentAreaFilled(false); // Quita el área rellena
		btnNotas.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnNotas.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnNotas.setForeground(Color.WHITE);
		btnNotas.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnNotas.setBorder(null);
		btnNotas.setBackground(new Color(128, 0, 0));
		btnNotas.setBounds(560, 0, 111, 43);
		NavBar.add(btnNotas);

		JButton btnGrupo = new JButton("HORARIO");
		btnGrupo.setFocusPainted(false); // Quita el borde cuando tiene el foco
		btnGrupo.setContentAreaFilled(false); // Quita el área rellena
		btnGrupo.setOpaque(true); // Hace que se pinte el fondo con el color que elegiste
		btnGrupo.setBorderPainted(false); // Quita el borde pintado (opcional)
		btnGrupo.setForeground(Color.WHITE);
		btnGrupo.setFont(new Font("Leelawadee UI", Font.BOLD, 11));
		btnGrupo.setBorder(null);
		btnGrupo.setBackground(new Color(128, 0, 0));
		btnGrupo.setBounds(680, 0, 103, 43);
		NavBar.add(btnGrupo);

		// EVENTOS
		btnGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelNotas.setVisible(false);
				panelGrupos.setVisible(true);
				contentPane.setComponentZOrder(panelGrupos, 0);
				contentPane.revalidate();
				contentPane.repaint();

				// Limpiar el panel anterior
				panelGrupos.removeAll();
				panelGrupos.setLayout(null);

				// Título
				JLabel lblHorarioProfe = new JLabel("HORARIO DEL PROFESOR");
				lblHorarioProfe.setBounds(163, 10, 400, 40);
				lblHorarioProfe.setHorizontalAlignment(SwingConstants.CENTER);
				lblHorarioProfe.setForeground(Color.BLACK);
				lblHorarioProfe.setFont(new Font("Leelawadee UI", Font.BOLD, 28));
				panelGrupos.add(lblHorarioProfe);

				// Modelo de tabla
				DefaultTableModel modelo = new DefaultTableModel() {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				modelo.setColumnIdentifiers(
						new Object[] { "Cod.", "Materia", "Créditos", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" });

				// Llenar tabla con las materias dictadas por el profesor
				for (Materia materia : gestorM.getMaterias()) {
					if (materia.getProfesor() != null && materia.getProfesor().equals(profesor)) {
						String[] fila = new String[9];
						fila[0] = String.valueOf(materia.getCodigo());
						fila[1] = materia.getNombre();
						fila[2] = String.valueOf(materia.getCreditos());

						// Inicializar celdas vacías
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

				// Centrado del contenido
				DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
				centro.setHorizontalAlignment(SwingConstants.CENTER);
				for (int i = 0; i < tabla.getColumnCount(); i++) {
					tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
				}

				// Minimizar columnas de días vacías
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
						tabla.getColumnModel().getColumn(col).setMinWidth(30);
						tabla.getColumnModel().getColumn(col).setMaxWidth(30);
						tabla.getColumnModel().getColumn(col).setPreferredWidth(30);
					}
				}

				JScrollPane scroll = new JScrollPane(tabla);
				scroll.setBounds(20, 60, 600, 250);
				panelGrupos.add(scroll);

				panelGrupos.revalidate();
				panelGrupos.repaint();
			}
		});

		btnGrupo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGrupo.setBackground(new Color(80, 0, 0));
				btnGrupo.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnGrupo.setBackground(new Color(128, 0, 0));
				btnGrupo.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btnGrupo.setBackground(new Color(100, 0, 0)); // un color distinto para el clic
				btnGrupo.repaint();
			}
		});

		JPanel Escudo = new JPanel();
		Escudo.setForeground(new Color(0, 0, 0));
		Escudo.setLayout(null);
		Escudo.setBackground(new Color(128, 0, 0));
		Escudo.setBounds(10, 0, 52, 43);
		NavBar.add(Escudo);

		JLabel EscudoTxt = new JLabel("");
		EscudoTxt.setHorizontalAlignment(SwingConstants.CENTER);
		EscudoTxt.setBounds(0, 0, 52, 43);
		Escudo.add(EscudoTxt);
		Escudo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Mostrar panel de inicio como hace el botón
				panelinicio.setVisible(true);
				panelNotas.setVisible(false);
				panelGrupos.setVisible(false);

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
