package business.logic;

import java.sql.Date;

public class Venta {
	private String client_Id;
	private String venta_Id;
	private Date fechaEntrega;

	public Venta(String venta_Id, String client_Id, Date fechaEntrega) {
		setClient_Id(client_Id);
		setVenta_Id(venta_Id);
		setFechaEntrega(fechaEntrega);
	}

	public String getClient_Id() {
		return client_Id;
	}

	private void setClient_Id(String client_Id) {
		this.client_Id = client_Id;
	}

	public String getVenta_Id() {
		return venta_Id;
	}

	private void setVenta_Id(String venta_Id) {
		this.venta_Id = venta_Id;
	}


	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	private void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

}
