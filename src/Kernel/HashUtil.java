package Kernel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * La clase HashUtil proporciona una utilidad para generar hashes SHA-256 de cadenas de texto.
 * Es utilizada principalmente para el hasheo de contraseñas de forma segura.
 */
public class HashUtil {
	/**
	 * Genera un hash SHA-256 de la cadena de entrada proporcionada.
	 * @param input La cadena de texto a la que se le aplicará el hash.
	 * @return Una cadena hexadecimal que representa el hash SHA-256 de la entrada.
	 * @throws RuntimeException Si el algoritmo SHA-256 no está disponible en el entorno Java.
	 */
	public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));  // Convertir byte a hex
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}