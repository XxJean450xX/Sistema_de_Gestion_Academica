package Kernel;

import java.io.Serializable;

public class Usuario implements Serializable{
	//ATRIBUTOS
	String nombre;
	String apellido;
	String password;
	long codigo;
	String correo;
	private String rol; // Nuevo atributo

	
	public Usuario (String nombre, String apellido, String password, long codigo, String correo, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.codigo = codigo;
		this.correo = correo;
		this.rol = rol;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String username) {
		this.apellido = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
}
