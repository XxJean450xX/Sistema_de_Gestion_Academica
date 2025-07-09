package Kernel;

import java.awt.EventQueue;
import java.util.ArrayList;

import GUI.Inicio;
import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

/**
 * La clase `Launcher` es el punto de entrada principal de la aplicación.
 * Se encarga de inicializar los gestores de datos, cargar la información persistente,
 * establecer las reconexiones entre objetos (usuarios, materias, preinscripciones)
 * y finalmente lanzar la interfaz gráfica de usuario.
 */
public class Launcher {
	/**
     * El método `main` es el punto de inicio de la aplicación.
     * Realiza los siguientes pasos:
     * <ol>
     * <li>Inicializa instancias de {@link GestorUsuarios}, {@link GestorMaterias}
     * y {@link GestionSolicitudes} para manejar los datos del sistema.</li>
     * <li>Llama a {@link InicializadorDatos#inicializar} para asegurar que existan
     * datos básicos (como un administrador y materias de prueba) al iniciar la aplicación.</li>
     * <li>Realiza procesos de **reconexión** cruciales para restaurar las relaciones
     * entre objetos después de la deserialización:
     * <ul>
     * <li>**Reconexión de Profesor a Materia:** Asegura que las materias cargadas
     * tengan una referencia correcta al objeto {@link Profesor} completo
     * y que el profesor tenga la materia asignada.</li>
     * <li>**Reconexión de Estudiante con Materias Inscritas:** Verifica que
     * los objetos {@link Estudiante} tengan actualizadas sus listas
     * de materias inscritas basándose en la información de las materias.</li>
     * <li>**Reconexión de Preinscripciones desde Solicitudes:** Sincroniza las
     * preinscripciones de cada {@link Estudiante} con los datos de
     * {@link GestionSolicitudes}.</li>
     * </ul>
     * </li>
     * <li>Lanza la interfaz gráfica de usuario (`GUI.Inicio`) en el
     * Event Dispatch Thread (EDT) para garantizar la seguridad de los hilos en Swing.</li>
     * </ol>
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
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


        // Inicia la interfaz gráfica en el hilo EDT (Event Dispatch Thread)
        // Esto es esencial para aplicaciones Swing para garantizar que todas
        // las operaciones de la UI se ejecuten en el hilo correcto.
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
