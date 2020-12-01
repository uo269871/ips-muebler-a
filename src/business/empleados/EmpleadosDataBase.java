package business.empleados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import business.bbdd.DataBase;
import business.logic.Empleado;
import business.transportistas.TransportistasDataBase;
import business.vendedores.VendedoresDataBase;

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
				String id = rs.getString("id");
				String dni = rs.getString("dni");
				String nombre = rs.getString("nombre");
				String dir = rs.getString("direccion");
				int telefono = rs.getInt("telefono");
				int hora_entrada = rs.getInt("hora_entrada");
				int minuto_entrada = rs.getInt("minuto_entrada");
				int hora_salida = rs.getInt("hora_salida");
				int minuto_salida = rs.getInt("minuto_salida");
				Empleado e = new Empleado(id, dni, nombre, dir, telefono, hora_entrada, minuto_entrada, hora_salida,
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

	public void addEmpleado(Empleado emp) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement(
					"insert into IPS_EMPLEADOS(id, dni, nombre, direccion, telefono, hora_entrada, minuto_entrada, hora_salida, minuto_salida) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setString(1, emp.getId());
			pst.setString(2, emp.getDni());
			pst.setString(3, emp.getNombre());
			pst.setString(4, emp.getDir());
			pst.setInt(5, emp.getTelefono());
			pst.setInt(6, emp.getHora_entrada());
			pst.setInt(7, emp.getMinuto_entrada());
			pst.setInt(8, emp.getHora_salida());
			pst.setInt(9, emp.getMinuto_salida());

			pst.executeUpdate();
			pst.close();
//			int i = 0;
			if (emp.getTipo() == Empleado.Tipo.TRANSPORTISTA) {
				TransportistasDataBase tdb = new TransportistasDataBase(db);
//				List<Transportista> taux = tdb.getTransportistas();
//				for(Transportista t: taux) {
//					if(i < Integer.parseInt(t.getId())) {
//						i = Integer.parseInt(t.getId());
//					}
//				}
				String id = UUID.randomUUID().toString();
				tdb.addTransportista(id, emp.getId());

			} else if (emp.getTipo() == Empleado.Tipo.VENDEDOR) {
				VendedoresDataBase vdb = new VendedoresDataBase(db);
//				List<Vendedor> vaux = tdb.getVendedores();
//				for(Vendedor v: vaux) {
//					if(i < Integer.parseInt(v.getId())) {
//						i = Integer.parseInt(v.getId());
//					}
//				}
				String id = UUID.randomUUID().toString();
				vdb.addVendedor(id, emp.getId());
			}
//			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
	}

	public void updateEmpleado(Empleado emp) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement(
					"update IPS_EMPLEADOS set direccion = ?, telefono = ?, hora_entrada = ?, minuto_entrada = ?, hora_salida = ?, minuto_salida = ?"
							+ " where id = ?");

			pst.setString(1, emp.getDir());
			pst.setInt(2, emp.getTelefono());
			pst.setInt(3, emp.getHora_entrada());
			pst.setInt(4, emp.getMinuto_entrada());
			pst.setInt(5, emp.getHora_salida());
			pst.setInt(6, emp.getMinuto_salida());
			pst.setString(7, emp.getId());

			pst.executeUpdate();

			pst.close();
//			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
	}

	public void deleteEmpleado(Empleado emp) {
		try {
			PreparedStatement pst = null;
			if(emp.getTipo() == Empleado.Tipo.TRANSPORTISTA) {
				pst = db.getConnection().prepareStatement("delete from IPS_TRANSPORTISTAS where ID_EMPLEADO = ?");
			} else {
				pst = db.getConnection().prepareStatement("delete from IPS_VENDEDORES where ID_EMPLEADO = ?");
			}
			pst.setString(1, emp.getId());
			pst.executeUpdate();
			
			pst.close();
			pst = db.getConnection().prepareStatement("delete from IPS_EMPLEADOS where id = ?");

			pst.setString(1, emp.getId());
			pst.executeUpdate();

			pst.close();
//			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
	}
}