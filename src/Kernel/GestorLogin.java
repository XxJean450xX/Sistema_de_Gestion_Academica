package Kernel;

import Persistencia.GestorUsuarios;

/**
 * La clase GestorLogin se encarga de la lógica de validación de credenciales
 * de usuario para el inicio de sesión.
 */
public class GestorLogin {

    private GestorUsuarios gestorUsuarios;

    /**
     * Constructor de la clase GestorLogin.
     * Inicializa una nueva instancia de {@link GestorUsuarios} para acceder
     * a la información de los usuarios.
     */
    public GestorLogin() {
        gestorUsuarios = new GestorUsuarios();
    }

    /**
     * Valida las credenciales de inicio de sesión de un usuario.
     * Comprueba si existe un usuario con el código dado, si la contraseña hasheada
     * coincide y si el rol del usuario es el esperado.
     * @param codigo El código único del usuario.
     * @param contraseña La contraseña ingresada por el usuario.
     * @param rolEsperado El rol que se espera que tenga el usuario (e.g., "Estudiante", "Profesor", "Administrador").
     * @return true si las credenciales son válidas y el rol coincide, false en caso contrario.
     */
    public boolean validarLogin(long codigo, String contraseña, String rolEsperado) {
        Usuario usuario = gestorUsuarios.buscarUsuarioPorCodigo(codigo);
        if (usuario == null) return false;

        String hashIngresada = HashUtil.sha256(contraseña);
        return hashIngresada.equals(usuario.getPassword()) &&
               usuario.getRol().equalsIgnoreCase(rolEsperado);
    }

    /**
     * Obtiene un objeto Usuario si las credenciales (código y contraseña) son válidas.
     * No valida el rol, solo la existencia del usuario y la coincidencia de la contraseña.
     * @param codigo El código único del usuario.
     * @param contraseña La contraseña ingresada por el usuario.
     * @return El objeto {@link Usuario} si las credenciales son válidas, o null si no se encuentra o la contraseña es incorrecta.
     */
    public Usuario obtenerUsuario(long codigo, String contraseña) {
        Usuario usuario = gestorUsuarios.buscarUsuarioPorCodigo(codigo);
        if (usuario != null && usuario.getPassword().equals(HashUtil.sha256(contraseña))) {
            return usuario;
        }
        return null;
    }

    /**
     * Obtiene el correo electrónico de un usuario dado su código.
     * @param codigo El código único del usuario.
     * @return El correo electrónico del usuario, o null si el usuario no existe.
     */
    public String obtenerCorreoUsuario(int codigo) {
        Usuario usuario = gestorUsuarios.buscarUsuarioPorCodigo(codigo);
        return usuario != null ? usuario.getCorreo() : null;
    }
}