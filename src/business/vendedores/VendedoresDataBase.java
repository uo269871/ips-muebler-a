package business.vendedores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Vendedor;

public class VendedoresDataBase {

	private DataBase db;

	public VendedoresDataBase(DataBase db) {
		this.db = db;
	}
	
	public void addVendedor(String id, String id_empleado) {
		Statement st;
		try {
			st = db.getConnection().createStatement();
			st.executeQuery("INSERT INTO IPS_VENDEDORES(id,id_empleado) values('" + id +"','"+id_empleado+"')");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.cierraConexion();
	}
	
	public List<Vendedor> getVendedores() {
		List<Vendedor> empleados = new ArrayList<Vendedor>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_EMPLEADOS where id in (select id_empleado from ips_vendedores)");
			while (rs.next()) {
				String dni = rs.getString("dni");
				String id = rs.getString("id");
				String nombre = rs.getString("nombre");
				int telefono = rs.getInt("telefono");
				int hora_entrada = rs.getInt("hora_entrada");
				int minuto_entrada = rs.getInt("minuto_entrada");
				int hora_salida = rs.getInt("hora_salida");
				int minuto_salida = rs.getInt("minuto_salida");
				Vendedor e = new Vendedor(dni, id, nombre, telefono, hora_entrada, minuto_entrada, hora_salida,
						minuto_salida);
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
}
