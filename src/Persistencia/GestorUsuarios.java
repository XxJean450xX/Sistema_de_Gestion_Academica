package Persistencia;

import java.io.*;
import java.util.ArrayList;

import Kernel.Usuario;

/**
 * La clase `GestorUsuarios` se encarga de la administración y persistencia
 * de objetos {@link Usuario}. Permite agregar, buscar, listar, guardar y cargar
 * usuarios desde un archivo serializado, asegurando que los datos persistan
 * entre las ejecuciones de la aplicación.
 */
public class GestorUsuarios {

    /**
     * La ruta del archivo donde se serializarán y deserializarán los objetos {@link Usuario}.
     */
    private static final String ARCHIVO_USUARIOS = "data/usuarios.ser";
    /**
     * Una {@link ArrayList} que almacena los objetos {@link Usuario} en memoria.
     */
    private ArrayList<Usuario> usuarios;

    /**
     * Constructor de la clase `GestorUsuarios`.
     * Inicializa la lista de usuarios y luego intenta cargar los usuarios existentes
     * desde el archivo de persistencia.
     */
    public GestorUsuarios() {
        usuarios = new ArrayList<>();
        cargarUsuarios(); // Intenta cargar los usuarios desde archivo
    }

    /**
     * Agrega un nuevo {@link Usuario} a la lista de usuarios gestionados.
     * Antes de añadirlo, verifica si ya existe un usuario con el mismo código
     * para evitar duplicados. Si el código ya existe, se imprime un mensaje
     * de advertencia y el usuario no se añade. Después de agregar, los usuarios
     * se guardan inmediatamente en el archivo de persistencia.
     *
     * @param u El objeto {@link Usuario} a ser agregado.
     */
    public void agregarUsuario(Usuario u) {
        // Validar que no se repita el código
        if (buscarUsuarioPorCodigo(u.getCodigo()) != null) {
            System.out.println("Usuario con código duplicado: " + u.getCodigo());
            return;
        }

        usuarios.add(u);
        guardarUsuarios();
    }

    /**
     * Busca un {@link Usuario} en la lista por su código único.
     *
     * @param codigo El código ({@code long}) del usuario a buscar.
     * @return El objeto {@link Usuario} si se encuentra uno con el código especificado,
     * o `null` si no se encuentra ningún usuario con ese código.
     */
    public Usuario buscarUsuarioPorCodigo(long codigo) {
        for (Usuario u : usuarios) {
            if (u.getCodigo() == codigo) {
                return u;
            }
        }
        return null;
    }

    /**
     * Obtiene la lista completa de todos los {@link Usuario} gestionados.
     *
     * @return Una {@link ArrayList} de objetos {@link Usuario} que contiene todos los usuarios cargados o agregados.
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Guarda la lista actual de {@link Usuario} en el archivo de persistencia serializado.
     * Se asegura de que la carpeta contenedora del archivo exista.
     * Si ocurre un error de E/S durante el proceso de guardado, se imprime
     * un mensaje de error en la consola.
     */
    public void guardarUsuarios() {
        try {
            File archivo = new File(ARCHIVO_USUARIOS);
            archivo.getParentFile().mkdirs(); // Crea carpeta si no existe

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
            oos.writeObject(usuarios);
            oos.close();
            System.out.println("Usuarios guardados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de {@link Usuario} desde el archivo de persistencia serializado.
     * Si el archivo no existe, imprime un mensaje indicando que se creará uno nuevo
     * al guardar. Si ocurre un error de E/S o de clase no encontrada durante la carga,
     * se imprime un mensaje de error y la lista de usuarios se reinicia a una lista vacía
     * para evitar problemas.
     */
    @SuppressWarnings("unchecked")
    public void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            System.out.println("Archivo de usuarios no encontrado. Se creará uno nuevo al guardar.");
            return;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
            usuarios = (ArrayList<Usuario>) ois.readObject();
            ois.close();
            System.out.println("Usuarios cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
            usuarios = new ArrayList<>(); // Reinicia si hay error
        }
    }

    /**
     * Elimina todos los {@link Usuario} de la lista en memoria y los guarda
     * inmediatamente en el archivo de persistencia, lo que resulta en un
     * archivo de usuarios vacío.
     */
    public void eliminarTodosLosUsuarios() {
        usuarios.clear();
        guardarUsuarios();
    }
}