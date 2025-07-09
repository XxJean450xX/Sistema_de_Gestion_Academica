package Kernel;

import java.util.List;

public class Administrador extends Usuario {

	public Administrador() {

		super("Admin", "",HashUtil.sha256("123"), 1, "admin@correo.com", "Administrador");	}

	public Estudiante crearEstudiante(long codigo, String nombre, String apellido, String correo
			, String password) {
		return new Estudiante(codigo, nombre, apellido, correo, password);
	}

	public Profesor crearProfesor(long codigo, String nombre, String apellido, String correo, String password) {
		return new Profesor(codigo, nombre, apellido, correo, password);
	}

	public void asignarMateriaAProfesor(Profesor profesor, Materia materia) {
		profesor.agregarMateria(materia);
	}

	public void aprobarMateriasSeleccionadas(Estudiante estudiante, List<Materia> seleccionadas) {
	    for (Materia m : seleccionadas) {
	        estudiante.agregarMateria(m);
	        m.agregarEstudiante(estudiante);
	        estudiante.getPreinscripciones().remove(m);
	    }
	

	}
}

 