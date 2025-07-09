package Kernel;

import java.io.Serializable;
/**
 * La clase abstracta `Usuario` sirve como base para todos los tipos de usuarios
 * en el sistema (ej. Administrador, Estudiante, Profesor).
 * Define atributos comunes a todos los usuarios como nombre, apellido, contraseña,
 * código único, correo electrónico y rol.
 * Implementa {@link Serializable} para permitir la persistencia de los objetos `Usuario`.
 */
public class Usuario implements Serializable{
	/**
	 * El nombre del usuario.
	 */
	String nombre;
	/**
	 * El apellido del usuario.
	 */
	String apellido;
	/**
	 * La contraseña del usuario (se recomienda almacenar un hash de la contraseña en una aplicación real).
	 */
	String password;
	/**
	 * El código único de identificación del usuario.
	 */
	long codigo;
	/**
	 * La dirección de correo electrónico del usuario.
	 */
	String correo;
	/**
	 * El rol que desempeña el usuario en el sistema (ej. "Administrador", "Estudiante", "Profesor").
	 */
	private String rol;
	
	/**
	 * Constructor para crear una nueva instancia de `Usuario`.
	 * Este constructor está diseñado para ser llamado por las subclases de `Usuario`.
	 *
	 * @param nombre El nombre del usuario.
	 * @param apellido El apellido del usuario.
	 * @param password La contraseña del usuario.
	 * @param codigo El código único del usuario.
	 * @param correo El correo electrónico del usuario.
	 * @param rol El rol del usuario en el sistema.
	 */
	public Usuario (String nombre, String apellido, String password, long codigo, String correo, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.codigo = codigo;
		this.correo = correo;
		this.rol = rol;

	}

	/**
	 * Obtiene el nombre del usuario.
	 *
	 * @return El nombre del usuario como String.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 *
	 * @param nombre El nuevo nombre del usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el apellido del usuario.
	 *
	 * @return El apellido del usuario como String.
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Establece el apellido del usuario.
	 *
	 * @param apellido El nuevo apellido del usuario.
	 */
	public void setApellido(String username) {
		this.apellido = username;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 *
	 * @return La contraseña del usuario como String.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Establece la contraseña del usuario.
	 *
	 * @param password La nueva contraseña del usuario.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Obtiene el código único del usuario.
	 *
	 * @return El código del usuario como `long`.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * Establece el código único del usuario.
	 *
	 * @param codigo El nuevo código del usuario.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * Obtiene la dirección de correo electrónico del usuario.
	 *
	 * @return El correo electrónico del usuario como String.
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * Establece la dirección de correo electrónico del usuario.
	 *
	 * @param correo La nueva dirección de correo electrónico del usuario.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * Obtiene el rol del usuario en el sistema.
	 *
	 * @return El rol del usuario como String.
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * Establece el rol del usuario en el sistema.
	 *
	 * @param rol El nuevo rol del usuario.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	
}
