package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

import Kernel.Materia;

/**
 * La clase `GestorMaterias` se encarga de la gestión y persistencia de objetos {@link Materia}.
 * Permite agregar, buscar, listar, guardar y cargar materias, así como verificar conflictos de horario
 * entre ellas. Las materias se serializan y deserializan desde un archivo para mantener su estado
 * entre ejecuciones de la aplicación.
 */
public class GestorMaterias {
    /**
     * La ruta del archivo donde se guardarán y cargarán las materias serializadas.
     */
    private static final String ARCHIVO_MATERIAS = "data/materias.ser";

    /**
     * La lista en memoria que almacena los objetos {@link Materia} gestionados.
     */
    private List<Materia> materias;

    /**
     * Constructor de la clase `GestorMaterias`.
     * Al instanciar un `GestorMaterias`, intenta cargar las materias existentes
     * desde el archivo de persistencia. Si el archivo no existe o hay un error al cargarlo,
     * inicializa la lista de materias como vacía.
     */
    public GestorMaterias() {
        materias = cargarMaterias();
    }

    /**
     * Agrega una nueva materia a la lista de materias gestionadas.
     * Antes de agregarla, verifica si ya existe una materia con el mismo código.
     * Si no hay conflicto de código, la añade a la lista y guarda los cambios en el archivo de persistencia.
     *
     * @param materiab El objeto {@link Materia} a ser agregado.
     * @return `true` si la materia fue agregada exitosamente (no existía una con el mismo código),
     * `false` en caso contrario (ya existía una materia con ese código).
     */
    public boolean agregarMateria(Materia materiab) {
        if (buscarMateriaPorCodigo(materiab.getCodigo()) != null) {
            return false; // Ya existe una materia con ese código
        }
        materias.add(materiab);
        guardarMaterias();
        return true;
    }

    /**
     * Busca una materia en la lista por su código único.
     *
     * @param codigo El código (long) de la materia a buscar.
     * @return El objeto {@link Materia} si se encuentra una con el código especificado,
     * o `null` si no se encuentra ninguna materia con ese código.
     */
    public Materia buscarMateriaPorCodigo(long codigo) {
        for (Materia m : materias) {
            if (m.getCodigo() == codigo) {
                return m;
            }
        }
        return null;
    }

    /**
     * Busca una materia en la lista por su nombre. La búsqueda no es sensible a mayúsculas/minúsculas.
     *
     * @param nombre El nombre (String) de la materia a buscar.
     * @return El objeto {@link Materia} si se encuentra una con el nombre especificado,
     * o `null` si no se encuentra ninguna materia con ese nombre.
     */
    public Materia buscarMateriaPorNombre(String nombre) {
        for (Materia m : materias) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Obtiene la lista completa de todas las materias gestionadas.
     *
     * @return Una {@link List} de objetos {@link Materia} que contiene todas las materias cargadas o agregadas.
     */
    public List<Materia> getMaterias() {
        return materias;
    }

    /**
     * Guarda la lista actual de materias en el archivo de persistencia serializado.
     * Si ocurre un error de E/S durante el proceso de guardado, se imprime el rastro de la pila (stack trace).
     */
    public void guardarMaterias() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_MATERIAS))) {
            oos.writeObject(materias);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina todas las materias de la lista en memoria y las guarda inmediatamente
     * en el archivo de persistencia, lo que resulta en un archivo de materias vacío.
     */
    public void eliminarTodasLasMaterias() {
        materias.clear();
        guardarMaterias();
    }

    /**
     * Carga la lista de materias desde el archivo de persistencia serializado.
     * Si el archivo no existe, devuelve una lista vacía.
     * Si ocurre un error de E/S o de clase no encontrada durante la carga, se imprime el rastro de la pila
     * y se devuelve una lista vacía para evitar errores en la aplicación.
     *
     * @return Una {@link List} de objetos {@link Materia} cargados desde el archivo,
     * o una lista vacía si el archivo no existe o si ocurre un error durante la carga.
     */
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

    /**
     * Verifica si una nueva materia tiene conflicto de horario con alguna de las materias ya inscritas.
     * Un conflicto ocurre si comparten el mismo día y sus rangos de horario se solapan.
     * Asume que los horarios de las materias están en formato "HH:MM".
     *
     * @param nueva La {@link Materia} que se desea verificar si tiene conflicto.
     * @param yaInscritas Una {@link List} de {@link Materia} que representan las materias ya inscritas
     * con las que se debe comparar la nueva materia.
     * @return `true` si hay un conflicto de horario entre la nueva materia y alguna de las ya inscritas,
     * `false` en caso contrario. También devuelve `true` si hay un error al parsear el formato del horario
     * para asegurar la seguridad y evitar inscripciones erróneas.
     */
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