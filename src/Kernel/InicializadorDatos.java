package Kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Persistencia.GestionSolicitudes;
import Persistencia.GestorMaterias;
import Persistencia.GestorUsuarios;

/**
 * La clase `InicializadorDatos` se encarga de poblar el sistema con datos iniciales
 * como un administrador por defecto y un conjunto de materias de prueba.
 * Esto es útil para configurar el entorno por primera vez sin necesidad de entrada manual.
 */
public class InicializadorDatos {

	/**
     * Inicializa los datos esenciales del sistema, incluyendo:
     * 1. La creación de un usuario administrador por defecto si no existe.
     * 2. La adición de un conjunto de materias de prueba si no hay materias cargadas previamente.
     *
     * @param gestorUsuarios Un objeto {@link GestorUsuarios} para la gestión y persistencia de usuarios.
     * @param gestorMaterias Un objeto {@link GestorMaterias} para la gestión y persistencia de materias.
     * @param s Un objeto {@link GestionSolicitudes} (aunque no se utiliza directamente en este método,
     * se pasa para la consistencia del contexto).
     */
    public static void inicializar(GestorUsuarios gestorUsuarios, GestorMaterias gestorMaterias, GestionSolicitudes s) {
        // Crear admin si no existe
        if (gestorUsuarios.buscarUsuarioPorCodigo(1) == null) {
            Administrador admin = new Administrador();
            gestorUsuarios.agregarUsuario(admin);
            System.out.println("Administrador creado automáticamente.");
        } else {
            System.out.println("Administrador ya existe.");
        }

        // Agregar materias solo si no existen
        if (gestorMaterias.getMaterias().isEmpty()) {
            System.out.println("Agregando materias de prueba...");

            String[] nombresMaterias = {
            	    "Cálculo Diferencial", "Cálculo Integral", "Cálculo Multivariado", "Ecuaciones Diferenciales",
            	    "Matemáticas Especiales", "Matemáticas Discretas", "Álgebra Lineal", "Probabilidad y Estadística",
            	    "Métodos Numéricos", "Fundamentos de Ingeniería de Software", "Física Newtoniana",
            	    "Electromagnetismo", "Ciencias de la Computación I", "Ciencias de la Computación II",
            	    "Fundamentos de Redes de Comunicaciones", "Programación Básica", "Programación Orientada a Objetos",
            	    "Programación Avanzada", "Modelos de Programación", "Bases de Datos",
            	    "Arquitectura de Computadores", "Teoría de Sistemas", "Análisis y Diseño de Sistemas",
            	    "Investigación de Operaciones I", "Fundamentos de Ciencia de Sistemas",
            	    "Seminario de Introducción a Ingeniería", "Extrínseca I", "Extrínseca II",
            	    "Investigación de Operaciones II", "Historia y Cultura Colombiana", "Grupo de Trabajo",
            	    "Economía"
            	};

            String[] horarios = {
                "06:00-08:00", "08:00-10:00", "10:00-12:00", "12:00-14:00",
                "14:00-16:00", "16:00-18:00", "18:00-20:00"
            };

            // Listas de días por número de créditos
            List<List<String>> dias1Credito = Arrays.asList(
                List.of("Lun"), List.of("Mar"), List.of("Mie"),
                List.of("Jue"), List.of("Vie"), List.of("Sab")
            );

            List<List<String>> dias2Creditos = Arrays.asList(
                List.of("Lun", "Mie"), List.of("Mar", "Jue"),
                List.of("Lun", "Vie"), List.of("Mie", "Vie")
            );

            List<List<String>> dias3Creditos = Arrays.asList(
                List.of("Lun", "Mie", "Vie"), List.of("Mar", "Jue", "Sab"),
                List.of("Lun", "Mie", "Jue"), List.of("Mar", "Jue", "Vie")
            );

            Random random = new Random();

            for (int i = 0; i < nombresMaterias.length; i++) {
                String nombre = nombresMaterias[i];
                int codigo = 202501000 + i; // Automático y único

                int creditos = 1 + random.nextInt(3); // 1 a 3 créditos
                String horaInicio = ""; // Vacío por ahora
                String horaFin = "";    // Vacío por ahora
                List<String> dias = new ArrayList<>(); // Vacío por ahora

                Materia nuevaMateria = new Materia(nombre, codigo, creditos, horaInicio, horaFin, dias);
                gestorMaterias.agregarMateria(nuevaMateria);
            }


            gestorMaterias.guardarMaterias();
            System.out.println("Materias de prueba agregadas.");
        } else {
            System.out.println("Ya existen materias cargadas, no se añadieron nuevas.");
        }
    }
}
