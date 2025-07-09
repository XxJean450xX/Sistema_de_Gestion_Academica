package Persistencia;

import java.io.*;
import java.util.*;

import Kernel.Estudiante;
import Kernel.Materia;

/**
 * La clase `GestionSolicitudes` se encarga de gestionar las preinscripciones de los estudiantes
 * a diferentes materias. Permite guardar, cargar, consultar y eliminar solicitudes
 * de preinscripción, manteniendo la persistencia de los datos a través de la serialización
 * en un archivo. Cada solicitud está asociada al código único de un estudiante.
 */
public class GestionSolicitudes implements Serializable {

    /**
     * Identificador único para la serialización de la clase.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Un mapa que almacena las solicitudes de los estudiantes.
     * La clave es el código del estudiante ({@code Long}) y el valor es una lista de
     * {@link Materia} que el estudiante ha preinscrito.
     */
    private Map<Long, List<Materia>> solicitudes;

    /**
     * La ruta del archivo donde se serializarán y deserializarán las solicitudes.
     */
    private final String archivo = "data/solicitudes.ser";

    /**
     * Constructor de la clase `GestionSolicitudes`.
     * Al instanciar, intenta cargar las solicitudes existentes desde el archivo de persistencia.
     * Si el archivo no existe o hay un error de lectura, inicializa el mapa de solicitudes como vacío.
     */
    public GestionSolicitudes() {
        solicitudes = cargarSolicitudes();
    }

    /**
     * Guarda la lista de preinscripciones de un estudiante en el sistema de solicitudes.
     * Asocia la lista actual de preinscripciones del estudiante con su código único.
     * Después de actualizar el mapa, las solicitudes se guardan inmediatamente en el archivo de persistencia.
     *
     * @param estudiante El objeto {@link Estudiante} cuyas preinscripciones se desean guardar.
     */
    public void guardarSolicitud(Estudiante estudiante) {
        solicitudes.put(estudiante.getCodigo(), new ArrayList<>(estudiante.getPreinscripciones()));
        guardarSolicitudes();
    }
   
    /**
     * Verifica si un estudiante específico tiene preinscripciones registradas en el sistema.
     *
     * @param estudiante El objeto {@link Estudiante} a verificar.
     * @return {@code true} si el estudiante tiene al menos una preinscripción registrada,
     * {@code false} si el estudiante es nulo o no tiene preinscripciones.
     */
    public boolean tienePreinscripcion(Estudiante estudiante) {
        if (estudiante == null) return false;
        return solicitudes.containsKey(estudiante.getCodigo());
    }

    /**
     * Obtiene la lista de materias preinscritas por un estudiante dado su código.
     * Si el estudiante no tiene solicitudes registradas, devuelve una lista vacía.
     * Incluye una línea de depuración para imprimir las solicitudes obtenidas.
     *
     * @param codigoEstudiante El código ({@code long}) del estudiante cuyas solicitudes se desean obtener.
     * @return Una {@link List} de objetos {@link Materia} que el estudiante ha preinscrito,
     * o una lista vacía si el estudiante no tiene solicitudes.
     */
    public List<Materia> obtenerSolicitudes(long codigoEstudiante) {
    	List<Materia> materiasSolicitadas = solicitudes.getOrDefault(codigoEstudiante, new ArrayList<>());
        
        // --- LÍNEA PARA EL DEBUGGING ---
        System.out.println("DEBUG (GestionSolicitudes): Solicitudes para estudiante " + codigoEstudiante + ": " + materiasSolicitadas);
        // --- FIN LÍNEA PARA EL DEBUGGING ---
        return solicitudes.getOrDefault(codigoEstudiante, new ArrayList<>());
    }

    /**
     * Elimina todas las solicitudes de un estudiante específico del sistema.
     * Esto se usa típicamente después de que las solicitudes han sido procesadas o aprobadas.
     * Los cambios se guardan inmediatamente en el archivo de persistencia.
     *
     * @param codigoEstudiante El código ({@code long}) del estudiante cuyas solicitudes se desean eliminar.
     */
    public void eliminarSolicitud(long codigoEstudiante) {
        solicitudes.remove(codigoEstudiante);
        guardarSolicitudes();
    }

    /**
     * Obtiene un conjunto de todos los códigos de estudiantes que tienen solicitudes de preinscripción
     * registradas en el sistema.
     *
     * @return Un {@link Set} de tipo {@code Long} que contiene los códigos de los estudiantes con solicitudes.
     */
    public Set<Long> getCodigosEstudiantesConSolicitudes() {
        return solicitudes.keySet();
    }

    /**
     * Guarda el mapa actual de solicitudes en un archivo utilizando serialización de objetos.
     * Si ocurre un {@link IOException} durante el proceso de guardado, se imprime el rastro de la pila.
     */
    private void guardarSolicitudes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(solicitudes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga el mapa de solicitudes desde un archivo serializado.
     * Si el archivo no existe o si ocurre un {@link IOException} o {@link ClassNotFoundException}
     * durante la deserialización, se devuelve un mapa vacío para inicializar el estado.
     *
     * @return Un {@link Map} de tipo {@code Long} a {@link List} de {@link Materia} que contiene
     * las solicitudes cargadas, o un {@link HashMap} vacío si hay un problema al cargar.
     */
    @SuppressWarnings("unchecked")
    private Map<Long, List<Materia>> cargarSolicitudes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Map<Long, List<Materia>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>(); // Si no existe el archivo, devuelve un mapa vacío
        }
    }
}