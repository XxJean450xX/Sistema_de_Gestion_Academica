package Kernel;

import java.util.ArrayList;

public class Profesor extends Usuario {
	private ArrayList<Materia> materias;

	public Profesor(long codigo, String nombre, String apellido,  String correo, String password) {
		super(nombre, apellido, password, codigo, correo, "Profesor");
		this.materias = new ArrayList<>();
	}

	public void agregarMateria(Materia materia) {
		if (!materias.contains(materia)) {
			materias.add(materia);
			materia.setProfesor(this); // asigna este profesor a la materia
		}
	}
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Profesor)) return false;
	    Profesor p = (Profesor) o;
	    return this.codigo == p.codigo;
	}

	@Override
	public int hashCode() {
	    return Long.hashCode(codigo);
	}


	public void agregarNotasEstudiante(Materia materia, Estudiante estudiante, double n1, double n2, double n3) {
		materia.agregarNotas(materia, estudiante, n1, n2, n3);
	}

	public ArrayList<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(ArrayList<Materia> materias) {
		this.materias = materias;
	}
	
}
