
public class Cliente {
	private String nit;
	private String nombre;
	private String telefono;
	public Cliente(String nit, String nombre, String telefono) {
		this.nit = nit;
		this.nombre = nombre;
		this.telefono = telefono;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
