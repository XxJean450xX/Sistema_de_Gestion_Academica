package Kernel;

import java.util.ArrayList;
/**
 * La clase `Profesor` representa a un usuario con rol de profesor dentro del sistema.
 * Extiende la clase {@link Usuario} y maneja la asignación de materias
 * y la gestión de notas de estudiantes en las materias que imparte.
 */ 
public class Profesor extends Usuario {
	private ArrayList<Materia> materias;
	/**
	 * Constructor para crear un nuevo objeto `Profesor`.
	 *
	 * @param codigo El código único del profesor.
	 * @param nombre El nombre del profesor.
	 * @param apellido El apellido del profesor.
	 * @param correo El correo electrónico del profesor.
	 * @param password La contraseña del profesor (será hasheada internamente por el constructor de {@link Usuario}).
	 */
	public Profesor(long codigo, String nombre, String apellido,  String correo, String password) {
		super(nombre, apellido, password, codigo, correo, "Profesor");
		this.materias = new ArrayList<>();
	}
	/**
	 * Agrega una {@link Materia} a la lista de materias que imparte este profesor.
	 * La materia solo se añade si no está ya presente en la lista.
	 * Además, este método también establece a este profesor como el profesor de la materia.
	 *
	 * @param materia La {@link Materia} a agregar a la lista del profesor.
	 */
	public void agregarMateria(Materia materia) {
		if (!materias.contains(materia)) {
			materias.add(materia);
			materia.setProfesor(this); // asigna este profesor a la materia
		}
	}
	/**
	 * Compara este objeto `Profesor` con otro objeto para determinar si son iguales.
	 * Dos profesores se consideran iguales si tienen el mismo código único.
	 *
	 * @param o El objeto con el que se va a comparar.
	 * @return `true` si los objetos son el mismo `Profesor` (tienen el mismo código), `false` en caso contrario.
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Profesor)) return false;
	    Profesor p = (Profesor) o;
	    return this.codigo == p.codigo;
	}
	/**
	 * Devuelve un valor de código hash para este profesor.
	 * El código hash se basa únicamente en el código único del profesor,
	 * lo cual es coherente con el método `equals`.
	 *
	 * @return Un valor de código hash para este objeto.
	 */
	@Override
	public int hashCode() {
	    return Long.hashCode(codigo);
	}

	/**
	 * Agrega o actualiza las notas (n1, n2, n3) para un {@link Estudiante} en una {@link Materia} específica.
	 * Este método delega la acción de agregar las notas al objeto `Materia` correspondiente.
	 *
	 * @param materia La {@link Materia} en la que se registrarán las notas.
	 * @param estudiante El {@link Estudiante} a quien se le asignarán las notas.
	 * @param n1 La primera nota.
	 * @param n2 La segunda nota.
	 * @param n3 La tercera nota.
	 */
	public void agregarNotasEstudiante(Materia materia, Estudiante estudiante, double n1, double n2, double n3) {
		materia.agregarNotas(materia, estudiante, n1, n2, n3);
	}
	/**
	 * Obtiene la lista de todas las {@link Materia} que este profesor imparte.
	 *
	 * @return Un {@link ArrayList} de objetos {@link Materia} asignadas a este profesor.
	 */
	public ArrayList<Materia> getMaterias() {
		return materias;
	}
	/**
	 * Establece la lista de {@link Materia} para este profesor.
	 * **Nota:** Usar con precaución, ya que sobrescribe la lista existente.
	 *
	 * @param materias Un {@link ArrayList} de {@link Materia} a establecer como las materias del profesor.
	 */
	public void setMaterias(ArrayList<Materia> materias) {
		this.materias = materias;
	}
	
}
