package business.transportistas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Transportista;

public class TransportistasDataBase {

	private DataBase db;

	public TransportistasDataBase(DataBase db) {
		super();
		this.db = db;
	}

	public void addTransportista(String id, String id_empleado) {
		Statement st;
		try {
			st = db.getConnection().createStatement();
			st.executeQuery(
					"INSERT INTO IPS_TRANSPORTISTAS(id,id_empleado) values('" + id + "','" + id_empleado + "')");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.cierraConexion();
	}

	public List<Transportista> getTransportistas() {
		List<Transportista> empleados = new ArrayList<Transportista>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * FROM  IPS_EMPLEADOS where id in (select id_empleado from ips_transportistas)");
			while (rs.next()) {
				String dni = rs.getString("dni");
				String id = rs.getString("id");
				String nombre = rs.getString("nombre");
				int telefono = rs.getInt("telefono");
				int hora_entrada = rs.getInt("hora_entrada");
				int minuto_entrada = rs.getInt("minuto_entrada");
				int hora_salida = rs.getInt("hora_salida");
				int minuto_salida = rs.getInt("minuto_salida");
				Transportista e = new Transportista(dni, id, nombre, telefono, hora_entrada, minuto_entrada,
						hora_salida, minuto_salida);
				empleados.add(e);
			}
			rs.close();
			st.close();
//			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return empleados;
	}

	public List<Transportista> getTranspotista(int hora, int minuto) {
		List<Transportista> transportistas = new ArrayList<Transportista>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * from IPS_EMPLEADOS WHERE id in (select id_empleado from ips_transportistas) AND"
							+ " ((HORA_ENTRADA < " + hora + " AND HORA_SALIDA >" + hora + ") OR (HORA_ENTRADA = " + hora
							+ " AND MINUTO_ENTRADA < " + minuto + ") OR (HORA_SALIDA = " + hora
							+ " AND MINUTO_SALIDA > " + minuto + "))");
			while (rs.next()) {
				String dni = rs.getString("dni");
				String id = rs.getString("id");
				String nombre = rs.getString("nombre");
				int telefono = rs.getInt("telefono");
				int hora_entrada = rs.getInt("hora_entrada");
				int minuto_entrada = rs.getInt("minuto_entrada");
				int hora_salida = rs.getInt("hora_salida");
				int minuto_salida = rs.getInt("minuto_salida");
				Statement s = db.getConnection().createStatement();
				ResultSet r = s.executeQuery("SELECT ID FROM IPS_TRANSPORTISTAS WHERE ID_EMPLEADO = '" + id + "'");
				String id_t = "";
				if (r.next())
					id_t = r.getString("id");

				Transportista tr = new Transportista(dni, id_t, nombre, telefono, hora_entrada, minuto_entrada,
						hora_salida, minuto_salida);
				transportistas.add(tr);
				r.close();
				s.close();
			}
			rs.close();
			st.close();
//            db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return transportistas;
	}

}
