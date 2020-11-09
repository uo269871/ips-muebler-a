package business.empleados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Empleado;

public class EmpleadosDataBase {
	private DataBase db;

	public EmpleadosDataBase(DataBase db) {
		this.db = db;
	}

	public List<Empleado> getEmpleados() {
		List<Empleado> empleados = new ArrayList<Empleado>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_EMPLEADOS");
			while (rs.next()) {
				String dni = rs.getString("dni");
				String nombre = rs.getString("nombre");
//				String dir = rs.getString("direccion");
				int telefono = rs.getInt("telefono");
				int hora_entrada = rs.getInt("hora_entrada");
				int minuto_entrada = rs.getInt("minuto_entrada");
				int hora_salida = rs.getInt("hora_salida");
				int minuto_salida = rs.getInt("minuto_salida");
				Empleado e = new Empleado(dni, nombre, "dir", telefono, hora_entrada, minuto_entrada, hora_salida,
						minuto_salida);
				empleados.add(e);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return empleados;
	}
}