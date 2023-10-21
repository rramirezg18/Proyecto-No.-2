
public class Orden {
	private int numero;
	private String fecha;
	private String nit;
	private float total;
	public Orden(int numero, String fecha, String nit, float total) {
		this.numero = numero;
		this.fecha = fecha;
		this.nit = nit;
		this.total = total;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}

}
