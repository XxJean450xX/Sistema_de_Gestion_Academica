package Kernel;

import Persistencia.GestorUsuarios;

public class GestorLogin {

    private GestorUsuarios gestorUsuarios;

    public GestorLogin() {
        gestorUsuarios = new GestorUsuarios();
    }

    public boolean validarLogin(long codigo, String contraseña, String rolEsperado) {
        Usuario usuario = gestorUsuarios.buscarUsuarioPorCodigo(codigo);
        if (usuario == null) return false;

        String hashIngresada = HashUtil.sha256(contraseña);
        return hashIngresada.equals(usuario.getPassword()) &&
               usuario.getRol().equalsIgnoreCase(rolEsperado);
    }

    public Usuario obtenerUsuario(long codigo, String contraseña) {
        Usuario usuario = gestorUsuarios.buscarUsuarioPorCodigo(codigo);
        if (usuario != null && usuario.getPassword().equals(HashUtil.sha256(contraseña))) {
            return usuario;
        }
        return null;
    }

    public String obtenerCorreoUsuario(int codigo) {
        Usuario usuario = gestorUsuarios.buscarUsuarioPorCodigo(codigo);
        return usuario != null ? usuario.getCorreo() : null;
    }



}

