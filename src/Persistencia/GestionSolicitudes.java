package Persistencia;

import java.io.*;
import java.util.*;

import Kernel.Estudiante;
import Kernel.Materia;

public class GestionSolicitudes implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Long, List<Materia>> solicitudes;

    private final String archivo = "data/solicitudes.ser";

    public GestionSolicitudes() {
        solicitudes = cargarSolicitudes();
    }

    // Guardar solicitud de un estudiante
    public void guardarSolicitud(Estudiante estudiante) {
        solicitudes.put(estudiante.getCodigo(), new ArrayList<>(estudiante.getPreinscripciones()));
        guardarSolicitudes();
    }
   
    public boolean tienePreinscripcion(Estudiante estudiante) {
        if (estudiante == null) return false;
        return solicitudes.containsKey(estudiante.getCodigo());
    }




    // Obtener materias solicitadas por un estudiante
    public List<Materia> obtenerSolicitudes(long codigoEstudiante) {
    	List<Materia> materiasSolicitadas = solicitudes.getOrDefault(codigoEstudiante, new ArrayList<>());
        
        // --- LÍNEA PARA EL DEBUGGING ---
        System.out.println("DEBUG (GestionSolicitudes): Solicitudes para estudiante " + codigoEstudiante + ": " + materiasSolicitadas);
        // --- FIN LÍNEA PARA EL DEBUGGING ---
        return solicitudes.getOrDefault(codigoEstudiante, new ArrayList<>());
    }

    // Eliminar solicitud después de aprobación
    public void eliminarSolicitud(long codigoEstudiante) {
        solicitudes.remove(codigoEstudiante);
        guardarSolicitudes();
    }

    // Obtener lista de estudiantes que tienen solicitud (solo los códigos)
    public Set<Long> getCodigosEstudiantesConSolicitudes() {
        return solicitudes.keySet();
    }

    // Serialización: guardar en archivo
    private void guardarSolicitudes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(solicitudes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialización: cargar desde archivo
    @SuppressWarnings("unchecked")
    private Map<Long, List<Materia>> cargarSolicitudes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Map<Long, List<Materia>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>(); // Si no existe el archivo, devuelve un mapa vacío
        }
    }
    
    
    
    
}

