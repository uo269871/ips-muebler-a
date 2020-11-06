/**
 * 
 */
package business.logic;

/**
 * @author uo270656
 *
 */
public class Producto {
	
	private String name;
	private String type;
	private String product_id;
	private float price;
	
	public Producto(String name, String type, String product_id, float price) {
		super();
		this.name = name;
		this.type = type;
		this.product_id = product_id;
		this.price = price;
	}

	public Producto() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @param type the type to set
	 */
	protected void setType(String type) {
		this.type = type;
	}

	/**
	 * @param product_id the product_id to set
	 */
	protected void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @param price the price to set
	 */
	protected void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Nombre: " + getName() + ", Tipo: " + getType() +", Precio: " + getPrice();
	}

	public boolean equals(Producto p) {
		return p.getProduct_id().equals(this.getProduct_id());
	}	
}
