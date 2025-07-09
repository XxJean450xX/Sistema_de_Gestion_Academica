package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

import Kernel.Materia;

public class GestorMaterias {
    private static final String ARCHIVO_MATERIAS = "data/materias.ser";

    private List<Materia> materias;

    public GestorMaterias() {
        materias = cargarMaterias();
    }

    // ✅ Agregar materia verificando por código (long)
    public boolean agregarMateria(Materia materiab) {
        if (buscarMateriaPorCodigo(materiab.getCodigo()) != null) {
            return false; // Ya existe una materia con ese código
        }
        materias.add(materiab);
        guardarMaterias();
        return true;
    }

    // ✅ Buscar materia por código (long)
    public Materia buscarMateriaPorCodigo(long codigo) {
        for (Materia m : materias) {
            if (m.getCodigo() == codigo) {
                return m;
            }
        }
        return null;
    }
    
    

    // ✅ Buscar materia por nombre (opcional)
    public Materia buscarMateriaPorNombre(String nombre) {
        for (Materia m : materias) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    // ✅ Obtener lista completa
    public List<Materia> getMaterias() {
        return materias;
    }

    // ✅ Guardar materias (persistencia)
    public void guardarMaterias() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_MATERIAS))) {
            oos.writeObject(materias);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarTodasLasMaterias() {
        materias.clear();
        guardarMaterias();
    }
    
  


    // ✅ Cargar materias (desde archivo serializado)
    @SuppressWarnings("unchecked")
    public List<Materia> cargarMaterias() {
        File archivo = new File(ARCHIVO_MATERIAS);
        if (!archivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Materia>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean hayConflictoHorario(Materia nueva, List<Materia> yaInscritas) {
        for (Materia inscrita : yaInscritas) {
            // Validar si hay días en común
            for (String diaNueva : nueva.getDias()) {
                for (String diaInscrita : inscrita.getDias()) {
                    if (diaNueva.equalsIgnoreCase(diaInscrita)) {
                        try {
                            LocalTime inicioNueva = LocalTime.parse(nueva.getHoraInicio());
                            LocalTime finNueva = LocalTime.parse(nueva.getHoraFin());
                            LocalTime inicioInscrita = LocalTime.parse(inscrita.getHoraInicio());
                            LocalTime finInscrita = LocalTime.parse(inscrita.getHoraFin());

                            // Validar solapamiento
                            if (inicioNueva.isBefore(finInscrita) && finNueva.isAfter(inicioInscrita)) {
                                // Hay conflicto
                                System.out.println("⚠️ Conflicto detectado:");
                                System.out.println("   Nueva: " + nueva.getNombre() + " (" + diaNueva + " " + inicioNueva + "-" + finNueva + ")");
                                System.out.println("   Ya inscrita: " + inscrita.getNombre() + " (" + diaInscrita + " " + inicioInscrita + "-" + finInscrita + ")");
                                return true;
                            }

                        } catch (Exception e) {
                            System.err.println("❌ Error al parsear horario: " + e.getMessage());
                            // Si hay error de formato, por seguridad se asume que hay conflicto
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
}
