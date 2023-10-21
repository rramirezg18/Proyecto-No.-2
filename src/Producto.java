import java.util.*;
public class Producto {
    private int codigo;
    private int lote;
    private String descripcion;
    private int cantidad;
    private float precio;
    private List<Material> materiales; // Lista de materiales utilizados en el producto
	public Producto(int codigo, int lote, String descripcion, int cantidad, float precio, List<Material> materiales) {
		this.codigo = codigo;
		this.lote = lote;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
		this.materiales = materiales;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getLote() {
		return lote;
	}
	public void seLote(int lote) {
		this.lote = lote;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public List<Material> getMateriales() {
		return materiales;
	}
	public void setMateriales(List<Material> materiales) {
		this.materiales = materiales;
	}

    
}
