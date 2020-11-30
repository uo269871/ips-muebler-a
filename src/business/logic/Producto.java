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
	private int uds;
	
	public Producto(String name, String type, String product_id, float price) {
		super();
		this.name = name;
		this.type = type;
		this.product_id = product_id;
		this.price = price;
	}
	
	public Producto(String name, String type, String product_id, float price,int uds) {
		this(name,type,product_id,price);
		this.uds=uds;
	}
	
	public Producto(Producto pr,int uds) {
		this(pr.name,pr.type,pr.product_id,pr.price);
		this.uds=uds;
	}
	
	public Producto(String pr,int uds) {
		this.product_id = pr;
		this.uds=uds;
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
	 * @return the uds
	 */
	public int getUds() {
		return uds;
	}

	/**
	 * @param uds the uds to set
	 */
	public void setUds(int uds) {
		this.uds = uds;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Nombre: " + getName() + ", Tipo: " + getType() +", Precio: " + getPrice() + ", Uds: "+getUds();
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (product_id.equals(other.product_id))
			return true;
		else
			return false;
	}	
}
