package business.logic;

/**
 * @author uo270656
 *
 */
public class Cliente {

	private String name;
	private String dni;
	private String client_id;
	private String address;
	private String email;
	
	public Cliente(String name, String dni, String client_id, String address) {
		super();
		this.name = name;
		this.dni = dni;
		this.client_id = client_id;
		this.address = address;
	}
	
	public Cliente() {
		super();
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}
	/**
	 * @param dni the dni to set
	 */
	protected void setDni(String dni) {
		this.dni = dni;
	}
	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}
	/**
	 * @param client_id the client_id to set
	 */
	protected void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	protected void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
