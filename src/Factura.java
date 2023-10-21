
public class Factura {
	private int serie;
	private int numero;
	private String fecha;
	public Factura(int serie, int numero, String fecha) {
		this.serie = serie;
		this.numero = numero;
		this.fecha = fecha;
	}
	public int getSerie() {
		return serie;
	}
	public void setSerie(int serie) {
		this.serie = serie;
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
	public void setFehca(String fecha) {
		this.fecha = fecha;
	}
	
	

}