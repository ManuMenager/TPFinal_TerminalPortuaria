package cliente;

public abstract class Cliente {

	private String nombre;
	private String apellido;
	private int dni;
	private String email;
	
	public Cliente(String nombre, String apellido, int dni, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public int getDni() {
		return dni;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
