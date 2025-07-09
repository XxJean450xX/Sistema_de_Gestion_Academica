package Kernel;

import java.awt.EventQueue;
import java.util.ArrayList;

import GUI.Inicio;
import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

public class Launcher {
    public static void main(String[] args) {
        // Inicializa los gestores de datos
        GestorUsuarios gestorA = new GestorUsuarios();
        GestorMaterias gestorM = new GestorMaterias();
        GestionSolicitudes s = new GestionSolicitudes(); // 👈 Aquí creas el gestor de solicitudes

        // Llama al inicializador de datos (si es necesario)
        InicializadorDatos.inicializar(gestorA, gestorM, s);

        // 🧠 RECONEXIÓN de profesor a materia y viceversa
        for (Materia materia : gestorM.getMaterias()) {
            Profesor profe = materia.getProfesor();
            if (profe != null) {
                Usuario u = gestorA.buscarUsuarioPorCodigo(profe.getCodigo());
                if (u instanceof Profesor) {
                    Profesor profeReal = (Profesor) u;
                    materia.setProfesor(profeReal); // 🔁 reconectar
                    profeReal.agregarMateria(materia);
                }
            }
        }

        // 🧠 RECONEXIÓN de estudiante con materias inscritas
        for (Materia materia : gestorM.getMaterias()) {
            for (Estudiante estudiante : materia.getEstudiantes()) {
                Usuario u = gestorA.buscarUsuarioPorCodigo(estudiante.getCodigo());
                if (u instanceof Estudiante) {
                    Estudiante estudianteReal = (Estudiante) u;
                    if (!estudianteReal.getMaterias().contains(materia)) {
                        estudianteReal.agregarMateria(materia);
                    }
                }
            }
        }

        // 🧠 RECONEXIÓN de preinscripciones desde solicitudes
        for (Long codigo : s.getCodigosEstudiantesConSolicitudes()) {
            Usuario u = gestorA.buscarUsuarioPorCodigo(codigo);
            if (u instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) u;
                estudiante.setPreinscripciones(new ArrayList<>(s.obtenerSolicitudes(codigo)));
            }
        }
        for (Usuario u : gestorA.getUsuarios()) {
            if (u instanceof Estudiante est) {
                System.out.println("🧠 Estudiante " + est.getCodigo() + " tiene preinscripciones: " + est.getPreinscripciones().size());
            }
        }



        // Inicia la interfaz gráfica en el hilo EDT
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Inicio frame = new Inicio(gestorA, gestorM, s); // 👈 Aquí puedes también pasar gestorS si tu GUI lo necesita
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
