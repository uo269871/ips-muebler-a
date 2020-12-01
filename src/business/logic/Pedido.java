/**
 * 
 */
package business.logic;

import java.sql.Date;
import java.util.List;

/**
 * @author uo270656
 *
 */
public class Pedido {

	private int pedido_id;
	private float total_price;
	private String estado;
	private Date date;
	
	private List<Producto> productos;
	
	public Pedido(int pedido_id, float total_price, String estado,List<Producto> productos) {
		super();
		this.total_price = total_price;
		this.estado=estado;
		this.pedido_id=pedido_id;
		this.productos=productos;
	}
	
	public Pedido(int pedido_id, float total_price, String estado,List<Producto> productos,Date date) {
		super();
		this.total_price = total_price;
		this.estado=estado;
		this.pedido_id=pedido_id;
		this.productos=productos;
		this.setDate(date);
	}

	public Pedido() {
		super();
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @return the pedido_id
	 */
	public int getPedido_id() {
		return pedido_id;
	}

	/**
	 * @return the total_price
	 */
	public float getTotal_price() {
		return total_price;
	}

	/**
	 * @return the productos
	 */
	public List<Producto> getProductos() {
		return productos;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	/**
	 * @param total_price the total_price to set
	 */
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	/**
	 * @param pedido_id the pedido_id to set
	 */
	public void setPedido_id(int pedido_id) {
		this.pedido_id = pedido_id;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (pedido_id==other.pedido_id)
			return true;
		else
			return false;
	}
}
