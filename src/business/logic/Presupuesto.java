package business.logic;

import java.sql.Date;

/**
 * @author uo270656
 *
 */
public class Presupuesto {
	
	private Date fecha_caducidad;
	private String presupuesto_id;
	private String client_id;
	
	public Presupuesto(Date fecha_caducidad, String presupuesto_id, String client_id) {
		super();
		this.fecha_caducidad = fecha_caducidad;
		this.presupuesto_id = presupuesto_id;
		this.client_id = client_id;
	}
	/**
	 * @return the fecha_caducidad
	 */
	public Date getFecha_caducidad() {
		return fecha_caducidad;
	}
	/**
	 * @return the presupuesto_id
	 */
	public String getPresupuesto_id() {
		return presupuesto_id;
	}
	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}
	/**
	 * @param fecha_caducidad the fecha_caducidad to set
	 */
	protected void setFecha_caducidad(Date fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
	}
	/**
	 * @param presupuesto_id the presupuesto_id to set
	 */
	protected void setPresupuesto_id(String presupuesto_id) {
		this.presupuesto_id = presupuesto_id;
	}
	/**
	 * @param client_id the client_id to set
	 */
	protected void setClient_id(String client_id) {
		this.client_id = client_id;
	}
}
