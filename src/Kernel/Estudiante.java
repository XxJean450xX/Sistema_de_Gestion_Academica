package Kernel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * La clase `Estudiante` representa a un usuario con rol de estudiante dentro del sistema.
 * Extiende la clase {@link Usuario} y añade funcionalidades específicas para la gestión
 * de materias inscritas y preinscripciones.
 */
public class Estudiante extends Usuario {
	/**
	 * Lista de {@link Materia} en las que el estudiante está actualmente inscrito.
	 */
	private ArrayList<Materia> materias; // materias inscritas
	/**
	 * Lista de {@link Materia} a las que el estudiante se ha preinscrito.
	 * Estas materias aún no están confirmadas como inscripciones.
	 */
	private ArrayList<Materia> preinscripciones; // materias preinscritas

	/**
	 * Constructor para crear un nuevo objeto `Estudiante`.
	 *
	 * @param codigo El código único del estudiante.
	 * @param nombre El nombre del estudiante.
	 * @param apellido El apellido del estudiante.
	 * @param correo El correo electrónico del estudiante.
	 * @param password La contraseña del estudiante (será hasheada internamente por el constructor de {@link Usuario}).
	 */
	public Estudiante(long codigo, String nombre, String apellido,  String correo, String password) {
		super(nombre, apellido, password, codigo, correo, "Estudiante");
		this.materias = new ArrayList<>();
		this.preinscripciones = new ArrayList<>();
	}

	/**
	 * Añade una {@link Materia} a la lista de preinscripciones del estudiante.
	 * La materia solo se añade si no está ya presente en la lista de preinscripciones.
	 *
	 * @param materia La {@link Materia} que el estudiante desea preinscribir.
	 */
	public void preinscribirMateria(Materia materia) {
		if (!preinscripciones.contains(materia)) {
			preinscripciones.add(materia);
		}
	}

	/**
	 * Compara este objeto `Estudiante` con otro objeto para determinar si son iguales.
	 * Dos estudiantes se consideran iguales si tienen el mismo código único.
	 *
	 * @param o El objeto con el que se va a comparar.
	 * @return `true` si los objetos son el mismo `Estudiante` (tienen el mismo código), `false` en caso contrario.
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Estudiante)) return false;
	    Estudiante e = (Estudiante) o;
	    return this.codigo == e.codigo;  // Supongo que 'codigo' es un ID único
	}

	/**
	 * Devuelve un valor de código hash para este estudiante.
	 * El código hash se basa únicamente en el código único del estudiante,
	 * lo cual es coherente con el método `equals`.
	 *
	 * @return Un valor de código hash para este objeto.
	 */
	@Override
	public int hashCode() {
	    return Long.hashCode(codigo);
	}

	/**
	 * Añade una {@link Materia} a la lista de materias inscritas del estudiante.
	 * La materia solo se añade si no está ya presente en la lista de materias inscritas.
	 *
	 * @param materia La {@link Materia} que se inscribe para el estudiante.
	 */
	public void agregarMateria(Materia materia) {
		if (!materias.contains(materia)) {
			materias.add(materia);
		}
	}

	/**
	 * Vacía la lista de preinscripciones del estudiante.
	 */
	public void limpiarPreinscripciones() {
		preinscripciones.clear();
	}

	/**
	 * Obtiene la lista de materias que el estudiante ha preinscrito.
	 *
	 * @return Un {@link ArrayList} de {@link Materia} que representa las preinscripciones.
	 */
	public ArrayList<Materia> getPreinscripciones() {
		return preinscripciones;
	}

	/**
	 * Obtiene la lista de materias en las que el estudiante está actualmente inscrito.
	 *
	 * @return Un {@link ArrayList} de {@link Materia} que representa las materias inscritas.
	 */
	public ArrayList<Materia> getMaterias() {
		return materias;
	}

	/**
	 * Establece la lista de materias inscritas para el estudiante.
	 * **Nota:** Usar con precaución, ya que sobrescribe la lista existente.
	 *
	 * @param materias Un {@link ArrayList} de {@link Materia} a establecer como materias inscritas.
	 */
	public void setMaterias(ArrayList<Materia> materias) {
		this.materias = materias;
	}

	/**
	 * Establece la lista de preinscripciones para el estudiante.
	 * **Nota:** Usar con precaución, ya que sobrescribe la lista existente.
	 *
	 * @param preinscripciones Un {@link ArrayList} de {@link Materia} a establecer como preinscripciones.
	 */
	public void setPreinscripciones(ArrayList<Materia> preinscripciones) {
		this.preinscripciones = preinscripciones;
	}

	/**
	 * Devuelve una representación en cadena de este objeto `Estudiante`.
	 * El formato es "nombre apellido - codigo".
	 *
	 * @return Una cadena que representa al estudiante.
	 */
	@Override
	public String toString() {
	    return nombre + " " + apellido + " - " + codigo;
	}
}