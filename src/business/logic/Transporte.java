package business.logic;

import java.sql.Date;

public class Transporte {
	
	private String id_transporte;
	private String id_venta;
	private String dni_transportista;
	private Date dia_entrega;
	private int hora_entrega;
	private int minuto_entrega;
	private String estado;
	
	public Transporte(String id_transporte,String id_venta, String dni_transportista, Date dia_entrega, int hora_entrega,int minuto_entrega, String estado) {
		super();
		this.setId_transporte(id_transporte);
		this.setId_venta(id_venta);
		this.setDni_transportista(dni_transportista);
		this.setDia_entrega(dia_entrega);
		this.setHora_entrega(hora_entrega);
		this.setMinuto_entrega(minuto_entrega);
		this.setEstado(estado);
	}

	public String getId_transporte() {
		return id_transporte;
	}

	public void setId_transporte(String id_transporte) {
		this.id_transporte = id_transporte;
	}

	public String getId_venta() {
		return id_venta;
	}

	public void setId_venta(String id_venta) {
		this.id_venta = id_venta;
	}

	public String getDni_transportista() {
		return dni_transportista;
	}

	public void setDni_transportista(String dni_transportista) {
		this.dni_transportista = dni_transportista;
	}

	public Date getDia_entrega() {
		return dia_entrega;
	}

	public void setDia_entrega(Date dia_entrega) {
		this.dia_entrega = dia_entrega;
	}

	public int getHora_entrega() {
		return hora_entrega;
	}

	public void setHora_entrega(int hora_entrega) {
		this.hora_entrega = hora_entrega;
	}

	public int getMinuto_entrega() {
		return minuto_entrega;
	}

	public void setMinuto_entrega(int minuto_entrega) {
		this.minuto_entrega = minuto_entrega;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Transporte: " + id_transporte + ", venta: " + id_venta;
	}


}
