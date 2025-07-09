package Kernel;

import java.io.Serializable;
import java.util.*;

/**
 * La clase `Materia` representa una asignatura o curso dentro del sistema académico.
 * Almacena información como el código, nombre, créditos, horario, profesor asignado,
 * y las notas de los estudiantes inscritos. Es serializable para permitir la persistencia de datos.
 */
public class Materia implements Serializable {
	/**
	 * Código único de la materia.
	 */
	private long codigo;
	/**
	 * Número de créditos académicos que otorga la materia.
	 */
	private int creditos;
	/**
	 * Nombre de la materia.
	 */
	private String nombre;
	/**
	 * Hora de inicio de la materia en formato de texto (ej. "08:00").
	 */
	private String horaInicio;
	/**
	 * Hora de finalización de la materia en formato de texto (ej. "10:00").
	 */
	private String horaFin;
	/**
	 * Lista de días de la semana en que se imparte la materia (ej. "Lun", "Mie").
	 */
	private List<String> dias;
	/**
	 * El profesor asignado a esta materia. Una materia solo puede tener un profesor.
	 */
	private Profesor profesor;
	/**
	 * Un mapa que asocia a cada {@link Estudiante} inscrito con una lista de sus notas.
	 * Las notas se almacenan como `Double`.
	 */
	private Map<Estudiante, List<Double>> notasPorEstudiante;
	/**
	 * Una lista de los {@link Estudiante} inscritos en esta materia.
	 */
	private ArrayList<Estudiante> estudiantes;

	/**
	 * Constructor para crear una nueva instancia de `Materia`.
	 *
	 * @param nombre El nombre de la materia.
	 * @param codigo El código único de la materia.
	 * @param creditos El número de créditos de la materia.
	 * @param horaInicio La hora de inicio de la clase (formato String, ej. "08:00").
	 * @param horaFin La hora de fin de la clase (formato String, ej. "10:00").
	 * @param dias Una lista de Strings que representan los días de la semana (ej. "Lun", "Mie").
	 */
	public Materia(String nombre, long codigo, int creditos, String horaInicio, String horaFin, List<String> dias) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.creditos = creditos;
		this.horaInicio = horaInicio;
	    this.horaFin = horaFin;
	    this.dias = dias;
		this.profesor = null; // se asignará luego
		this.notasPorEstudiante = new HashMap<>();
		this.estudiantes = new ArrayList<>();
	}

	/**
	 * Asigna un {@link Profesor} a esta materia.
	 *
	 * @param profesor El objeto {@link Profesor} que será asignado a la materia.
	 */
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	/**
	 * Obtiene el {@link Profesor} actualmente asignado a esta materia.
	 *
	 * @return El objeto {@link Profesor} asignado, o `null` si no hay ninguno.
	 */
	public Profesor getProfesor() {
		return profesor;
	}

	/**
	 * Agrega un {@link Estudiante} a la materia.
	 * Si el estudiante no está ya registrado, se añade a la lista de estudiantes
	 * y se inicializan sus notas con tres valores de 0.0. También se asegura de
	 * que la materia sea agregada a la lista de materias del estudiante.
	 *
	 * @param estudiante El objeto {@link Estudiante} a ser agregado a la materia.
	 */
	public void agregarEstudiante(Estudiante estudiante) {
		if (!notasPorEstudiante.containsKey(estudiante)) {
			notasPorEstudiante.put(estudiante, Arrays.asList(0.0, 0.0, 0.0));
			estudiantes.add(estudiante);
			estudiante.agregarMateria(this); // también se agrega la materia al estudiante
		}
	}
	
	/**
     * Obtiene la lista de notas de un estudiante específico en esta materia.
     *
     * @param estudiante El objeto {@link Estudiante} del cual se desean obtener las notas.
     * @return Una {@link List} de {@link Double} que contiene las notas del estudiante,
     * o `null` si el estudiante no está inscrito en esta materia.
     */
	public List<Double> getNotasEstudiante(Estudiante estudiante) {
        // Como notasPorEstudiante.get(estudiante) ya retorna List<Double>, no necesitamos .get(0)
        if (notasPorEstudiante.containsKey(estudiante)) {
            return notasPorEstudiante.get(estudiante);
        }
        return null; // O puedes retornar new ArrayList<>() si prefieres no manejar nulls
    }

	/**
	 * Asigna o actualiza las tres notas (n1, n2, n3) para un estudiante específico en esta materia.
	 * Las notas se almacenan en un orden específico (n1, n2, n3).
	 *
	 * @param materia La materia (referencia a sí misma, podría omitirse ya que el método está en Materia).
	 * @param estudiante El objeto {@link Estudiante} al que se le asignarán las notas.
	 * @param n1 La primera nota.
	 * @param n2 La segunda nota.
	 * @param n3 La tercera nota.
	 */
	public void agregarNotas(Materia materia, Estudiante estudiante, double n1, double n2, double n3) {
		if (notasPorEstudiante.containsKey(estudiante)) {
			notasPorEstudiante.put(estudiante, Arrays.asList(n1, n2, n3));
		}
	}
	
	/**
	 * Compara esta materia con otro objeto para determinar si son iguales.
	 * Dos materias se consideran iguales si tienen el mismo nombre.
	 * Si se usara un código único como identificador, la comparación debería ser por `codigo`.
	 *
	 * @param o El objeto con el que se va a comparar.
	 * @return `true` si los objetos son la misma materia (tienen el mismo nombre), `false` en caso contrario.
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Materia materia = (Materia) o;
	    return Objects.equals(nombre, materia.nombre); // o código, si usas código único
	}

	/**
	 * Devuelve un valor de código hash para esta materia.
	 * El código hash se basa en el nombre de la materia, lo cual es coherente con el método `equals`.
	 * Si el código fuera el identificador único, el hash debería basarse en el código.
	 *
	 * @return Un valor de código hash para este objeto.
	 */
	@Override
	public int hashCode() {
	    return Objects.hash(nombre); // o código
	}


	/**
	 * Calcula el promedio ponderado de un estudiante en esta materia.
	 * Las ponderaciones son: Nota 1 (35%), Nota 2 (35%), Nota 3 (30%).
	 *
	 * @param estudiante El objeto {@link Estudiante} del cual se calculará el promedio.
	 * @return El promedio ponderado del estudiante, o 0.0 si el estudiante no está registrado.
	 */
	public double calcularPromedio(Estudiante estudiante) {
		if (!notasPorEstudiante.containsKey(estudiante)) return 0.0;
		List<Double> notas = notasPorEstudiante.get(estudiante);
		return notas.get(0) * 0.35 + notas.get(1) * 0.35 + notas.get(2) * 0.30;
	}

	// Mostrar notas detalladas de todos los estudiantes
	public void mostrarNotasDetalladas() {
		System.out.println("Notas de la materia: " + nombre);
		System.out.println("Profesor: " + (profesor != null ? profesor.getNombre() : "Sin asignar"));
		System.out.println("-----------------------------------------------------");
		System.out.printf("%-15s %-7s %-7s %-7s %-10s\n", "Estudiante", "Nota1", "Nota2", "Nota3", "Promedio");

		for (Estudiante e : estudiantes) {
			List<Double> notas = notasPorEstudiante.get(e);
			double promedio = calcularPromedio(e);
			System.out.printf("%-15s %-7.2f %-7.2f %-7.2f %-10.2f\n",
					e.getNombre(), notas.get(0), notas.get(1), notas.get(2), promedio);
		}
	}

	/**
	 * Obtiene el nombre de la materia.
	 *
	 * @return El nombre de la materia como String.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Obtiene la lista de estudiantes inscritos en esta materia.
	 *
	 * @return Un {@link ArrayList} de {@link Estudiante} inscritos.
	 */
	public ArrayList<Estudiante> getEstudiantes() {
		return estudiantes;
	}
	/**
	 * Obtiene el código único de la materia.
	 *
	 * @return El código de la materia como `long`.
	 */
	public long getCodigo() {
	    return codigo;
	}
	/**
	 * Obtiene la hora de inicio de la materia.
	 *
	 * @return La hora de inicio como String (ej. "08:00").
	 */
	public String getHoraInicio() {
	    return horaInicio;
	}
	/**
	 * Establece la hora de inicio de la materia.
	 *
	 * @param horaInicio La nueva hora de inicio como String.
	 */
	public void setHoraInicio(String horaInicio) {
	    this.horaInicio = horaInicio;
	}
	/**
	 * Obtiene la hora de finalización de la materia.
	 *
	 * @return La hora de finalización como String (ej. "10:00").
	 */
	public String getHoraFin() {
	    return horaFin;
	}
	/**
	 * Establece la hora de finalización de la materia.
	 *
	 * @param horaFin La nueva hora de finalización como String.
	 */
	public void setHoraFin(String horaFin) {
	    this.horaFin = horaFin;
	}
	/**
	 * Obtiene la lista de días en que se imparte la materia.
	 *
	 * @return Una {@link List} de Strings que representan los días.
	 */
	public List<String> getDias() {
	    return dias;
	}
	/**
	 * Establece la lista de días en que se impartirá la materia.
	 *
	 * @param dias Una {@link List} de Strings con los nuevos días.
	 */
	public void setDias(List<String> dias) {
	    this.dias = dias;
	}
	
	/**
	 * Obtiene el número de créditos de la materia.
	 *
	 * @return El número de créditos como `int`.
	 */
	public int getCreditos() {
		return creditos;
	}
	/**
	 * Establece el número de créditos de la materia.
	 *
	 * @param creditos El nuevo número de créditos.
	 */
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	/**
     * Devuelve una representación en cadena de este objeto `Materia`.
     * Por defecto, devuelve el nombre de la materia.
     *
     * @return El nombre de la materia como String.
     */
    @Override
    public String toString() {
        return nombre; // Asegúrate de que 'nombre' sea el atributo que quieres mostrar
    }
}


