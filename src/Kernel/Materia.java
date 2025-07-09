package Kernel;

import java.io.Serializable;
import java.util.*;

public class Materia implements Serializable {
	private long codigo;
	private int creditos;
	private String nombre;
	private String horaInicio;
	private String horaFin;
	private List<String> dias;
	private Profesor profesor; // ← Un solo profesor
	private Map<Estudiante, List<Double>> notasPorEstudiante;
	private ArrayList<Estudiante> estudiantes;

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

	// Asignar profesor
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	// Agregar estudiante y registrarlo con notas iniciales
	public void agregarEstudiante(Estudiante estudiante) {
		if (!notasPorEstudiante.containsKey(estudiante)) {
			notasPorEstudiante.put(estudiante, Arrays.asList(0.0, 0.0, 0.0));
			estudiantes.add(estudiante);
			estudiante.agregarMateria(this); // también se agrega la materia al estudiante
		}
	}
	public List<Double> getNotasEstudiante(Estudiante estudiante) {
        // Como notasPorEstudiante.get(estudiante) ya retorna List<Double>, no necesitamos .get(0)
        if (notasPorEstudiante.containsKey(estudiante)) {
            return notasPorEstudiante.get(estudiante);
        }
        return null; // O puedes retornar new ArrayList<>() si prefieres no manejar nulls
    }

	// Agregar notas para un estudiante específico
	public void agregarNotas(Materia materia, Estudiante estudiante, double n1, double n2, double n3) {
		if (notasPorEstudiante.containsKey(estudiante)) {
			notasPorEstudiante.put(estudiante, Arrays.asList(n1, n2, n3));
		}
	}
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Materia materia = (Materia) o;
	    return Objects.equals(nombre, materia.nombre); // o código, si usas código único
	}

	@Override
	public int hashCode() {
	    return Objects.hash(nombre); // o código
	}


	// Calcular promedio del estudiante
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

	// Getters
	public String getNombre() {
		return nombre;
	}

	public ArrayList<Estudiante> getEstudiantes() {
		return estudiantes;
	}
	public long getCodigo() {
	    return codigo;
	}

	public String getHoraInicio() {
	    return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
	    this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
	    return horaFin;
	}

	public void setHoraFin(String horaFin) {
	    this.horaFin = horaFin;
	}

	public List<String> getDias() {
	    return dias;
	}

	public void setDias(List<String> dias) {
	    this.dias = dias;
	}
	
	
	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	// Es crucial tener este método:
    @Override
    public String toString() {
        return nombre; // Asegúrate de que 'nombre' sea el atributo que quieres mostrar
    }
}


