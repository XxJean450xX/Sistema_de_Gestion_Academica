package Persistencia;

import java.io.*;
import java.util.ArrayList;

import Kernel.Usuario;

public class GestorUsuarios {

    private static final String ARCHIVO_USUARIOS = "data/usuarios.ser";
    private ArrayList<Usuario> usuarios;

    public GestorUsuarios() {
        usuarios = new ArrayList<>();
        cargarUsuarios(); // Intenta cargar los usuarios desde archivo
    }

    public void agregarUsuario(Usuario u) {
        // Validar que no se repita el código
        if (buscarUsuarioPorCodigo(u.getCodigo()) != null) {
            System.out.println("Usuario con código duplicado: " + u.getCodigo());
            return;
        }

        usuarios.add(u);
        guardarUsuarios();
    }

    public Usuario buscarUsuarioPorCodigo(long codigo) {
        for (Usuario u : usuarios) {
            if (u.getCodigo() == codigo) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

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

    public void eliminarTodosLosUsuarios() {
        usuarios.clear();
        guardarUsuarios();
    }
}
