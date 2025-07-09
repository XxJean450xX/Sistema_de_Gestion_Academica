package Kernel;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TokenManager {

    private static final String ARCHIVO_TOKENS = "tokens_recuperacion.ser";

    // Clase interna para guardar token con fecha de expiración
    public static class TokenRecuperacion implements Serializable {
        private static final long serialVersionUID = 1L;

        private int idUsuario;
        private String token;
        private LocalDateTime fechaExpiracion;

        public TokenRecuperacion(int idUsuario, String token, LocalDateTime fechaExpiracion) {
            this.idUsuario = idUsuario;
            this.token = token;
            this.fechaExpiracion = fechaExpiracion;
        }

        public int getIdUsuario() {
            return idUsuario;
        }

        public String getToken() {
            return token;
        }

        public LocalDateTime getFechaExpiracion() {
            return fechaExpiracion;
        }
    }

    // Genera un token nuevo y lo guarda en archivo
    public static String generarToken(int cedula) {
        String token = UUID.randomUUID().toString().substring(0, 6); // token corto

        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(10);
        TokenRecuperacion nuevoToken = new TokenRecuperacion(cedula, token, expiracion);

        List<TokenRecuperacion> tokens = cargarTokens();
        tokens.add(nuevoToken);
        guardarTokens(tokens);

        return token;
    }

    // Valida el token ingresado para un usuario, comprobando expiración
    public boolean validarToken(int idUsuario, String tokenIngresado) {
        List<TokenRecuperacion> tokens = cargarTokens();
        LocalDateTime ahora = LocalDateTime.now();

        // Buscar el token más reciente para ese usuario que coincida y no haya expirado
        return tokens.stream()
            .filter(t -> t.getIdUsuario() == idUsuario)
            .filter(t -> t.getToken().equals(tokenIngresado))
            .anyMatch(t -> t.getFechaExpiracion().isAfter(ahora));
    }

    // Elimina todos los tokens para un usuario dado
    public static void eliminarToken(int cedula) {
        List<TokenRecuperacion> tokens = cargarTokens();
        tokens.removeIf(t -> t.getIdUsuario() == cedula);
        guardarTokens(tokens);
    }

    // Carga la lista de tokens desde archivo, o lista vacía si no existe
    private static List<TokenRecuperacion> cargarTokens() {
        File archivo = new File(ARCHIVO_TOKENS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<TokenRecuperacion>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Guarda la lista de tokens en archivo
    private static void guardarTokens(List<TokenRecuperacion> tokens) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_TOKENS))) {
            oos.writeObject(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

