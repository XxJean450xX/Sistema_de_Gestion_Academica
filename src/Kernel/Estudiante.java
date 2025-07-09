package Kernel;

import java.util.ArrayList;
import java.util.Objects;

public class Estudiante extends Usuario {
	private ArrayList<Materia> materias; // materias inscritas
	private ArrayList<Materia> preinscripciones; // materias preinscritas

	public Estudiante(long codigo, String nombre, String apellido,  String correo, String password) {
		super(nombre, apellido, password, codigo, correo, "Estudiante");
		this.materias = new ArrayList<>();
		this.preinscripciones = new ArrayList<>();
	}

	public void preinscribirMateria(Materia materia) {
		if (!preinscripciones.contains(materia)) {
			preinscripciones.add(materia);
		}
	}
	// En la clase Estudiante (dentro de Kernel/Estudiante.java)
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Estudiante)) return false;
	    Estudiante e = (Estudiante) o;
	    return this.codigo == e.codigo;  // Supongo que 'codigo' es un ID único
	}

	@Override
	public int hashCode() {
	    return Long.hashCode(codigo);
	}




	public void agregarMateria(Materia materia) {
		if (!materias.contains(materia)) {
			materias.add(materia);
		}
	}

	public void limpiarPreinscripciones() {
		preinscripciones.clear();
	}

	public ArrayList<Materia> getPreinscripciones() {
		return preinscripciones;
	}

	public ArrayList<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(ArrayList<Materia> materias) {
		this.materias = materias;
	}

	public void setPreinscripciones(ArrayList<Materia> preinscripciones) {
		this.preinscripciones = preinscripciones;
	}
	@Override
	public String toString() {
	    return nombre + " " + apellido + " - " + codigo;
	}


}


