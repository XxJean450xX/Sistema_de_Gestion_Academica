package Kernel;

import java.util.List;

/**
 * La clase `Administrador` representa a un usuario con privilegios administrativos
 * dentro del sistema. Extiende la clase {@link Usuario} y añade funcionalidades
 * específicas para la gestión de estudiantes, profesores y la aprobación de materias.
 */
public class Administrador extends Usuario {

	/**
	 * Constructor por defecto para la clase `Administrador`.
	 * Inicializa un administrador con valores predefinidos:
	 * <ul>
	 * <li>**Nombre:** "Admin"</li>
	 * <li>**Apellido:** "" (vacío)</li>
	 * <li>**Contraseña:** La versión SHA-256 de "123" (utilizando {@link HashUtil#sha256})</li>
	 * <li>**Código:** 1</li>
	 * <li>**Correo:** "admin@correo.com"</li>
	 * <li>**Rol:** "Administrador"</li>
	 * </ul>
	 */
	public Administrador() {
		super("Admin", "",HashUtil.sha256("123"), 1, "admin@correo.com", "Administrador");
	}

	/**
	 * Crea un nuevo objeto {@link Estudiante} con los datos proporcionados.
	 * Este método es parte de las responsabilidades administrativas para dar de alta nuevos estudiantes.
	 *
	 * @param codigo El código único del estudiante.
	 * @param nombre El nombre del estudiante.
	 * @param apellido El apellido del estudiante.
	 * @param correo El correo electrónico del estudiante.
	 * @param password La contraseña del estudiante (será hasheada internamente por el constructor de {@link Estudiante}).
	 * @return Un nuevo objeto {@link Estudiante}.
	 */
	public Estudiante crearEstudiante(long codigo, String nombre, String apellido, String correo, String password) {
		return new Estudiante(codigo, nombre, apellido, correo, password);
	}

	/**
	 * Crea un nuevo objeto {@link Profesor} con los datos proporcionados.
	 * Este método es parte de las responsabilidades administrativas para dar de alta nuevos profesores.
	 *
	 * @param codigo El código único del profesor.
	 * @param nombre El nombre del profesor.
	 * @param apellido El apellido del profesor.
	 * @param correo El correo electrónico del profesor.
	 * @param password La contraseña del profesor (será hasheada internamente por el constructor de {@link Profesor}).
	 * @return Un nuevo objeto {@link Profesor}.
	 */
	public Profesor crearProfesor(long codigo, String nombre, String apellido, String correo, String password) {
		return new Profesor(codigo, nombre, apellido, correo, password);
	}

	/**
	 * Asigna una {@link Materia} a un {@link Profesor}.
	 * Este método delega la acción de añadir la materia al propio objeto {@link Profesor}.
	 *
	 * @param profesor El objeto {@link Profesor} al que se le asignará la materia.
	 * @param materia La {@link Materia} que se asignará al profesor.
	 */
	public void asignarMateriaAProfesor(Profesor profesor, Materia materia) {
		profesor.agregarMateria(materia);
	}

	/**
	 * Aprueba un conjunto de materias seleccionadas para un {@link Estudiante}.
	 * Para cada materia en la lista `seleccionadas`:
	 * <ul>
	 * <li>Se añade la materia al historial de materias del estudiante.</li>
	 * <li>Se añade el estudiante a la lista de estudiantes inscritos en esa materia.</li>
	 * <li>Se remueve la materia de las preinscripciones pendientes del estudiante.</li>
	 * </ul>
	 *
	 * @param estudiante El objeto {@link Estudiante} al que se le aprobarán las materias.
	 * @param seleccionadas Una {@link List} de objetos {@link Materia} que han sido seleccionadas y deben ser aprobadas.
	 */
	public void aprobarMateriasSeleccionadas(Estudiante estudiante, List<Materia> seleccionadas) {
	    for (Materia m : seleccionadas) {
	        estudiante.agregarMateria(m);
	        m.agregarEstudiante(estudiante);
	        estudiante.getPreinscripciones().remove(m);
	    }
	}
}