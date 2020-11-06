/**
 * 
 */
package business.presupuestos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Presupuesto;
import business.logic.Producto;

/**
 * @author uo270656
 *
 */
public class PresupuestosDataBase {

	private DataBase db;

	public PresupuestosDataBase(DataBase db) {
		super();
		this.db = db;
	}

	public List<Presupuesto> getPresupuestos() {
		ArrayList<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_PRESUPUESTOS");
			while (rs.next()) {
				Date fecha = rs.getDate("FECHA_CADUCIDAD");
				String client_id = rs.getString("client_id");
				String presupuesto_id = rs.getString("PRESUPUESTO_ID");
				Presupuesto pr = new Presupuesto(fecha, presupuesto_id, client_id);
				presupuestos.add(pr);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return presupuestos;
		
	}
	
	public List<Presupuesto> getPresupuestosPendientes() {
		ArrayList<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_PRESUPUESTOS WHERE FECHA_CADUCIDAD >= SYSDATE AND CLIENT_ID IS NOT NULL");
			while (rs.next()) {
				Date fecha = rs.getDate("FECHA_CADUCIDAD");
				String client_id = rs.getString("client_id");
				String presupuesto_id = rs.getString("PRESUPUESTO_ID");
				Presupuesto pr = new Presupuesto(fecha, presupuesto_id, client_id);
				if(Date.valueOf(LocalDate.now()).before(fecha))
					presupuestos.add(pr);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return presupuestos;
	}

	public boolean addPresupuesto(Presupuesto presupuesto, List<Producto> productos, List<Integer> transporte, List<Integer> montaje) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement(
					"insert into ips_presupuestos(fecha_caducidad,presupuesto_id,client_id) values (?,?,?)");
			pst.setDate(1, presupuesto.getFecha_caducidad());
			pst.setString(2, presupuesto.getPresupuesto_id());
			pst.setString(3, presupuesto.getClient_id());
			pst.executeUpdate();

			pst.close();
			db.cierraConexion();
			int i = 0;
			for (Producto product : productos) {
				pst = db.getConnection().prepareStatement(
						"insert into ips_presupuestos_productos(presupuesto_id,product_id) values (?,?)");
				pst.setString(1, presupuesto.getPresupuesto_id());
				pst.setString(2, product.getProduct_id());
				pst.close();
				db.cierraConexion();
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return true;
	}
	
	public void eliminarPresupuesto(String id) {
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("delete from ips_presupuestos_productos where presupuesto_id = " + id);

			rs.close();
			st.close();
			
			st = db.getConnection().createStatement();
			rs = st.executeQuery("delete from ips_presupuestos where presupuesto_id = " + id);
			
			
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
	}

	public List<String> getProductosPresupuesto(String presupuesto_id) {
		ArrayList<String> productos = new ArrayList<String>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_PRESUPUESTOS_PRODUCTOS WHERE PRESUPUESTO_ID = " + presupuesto_id);
			while (rs.next()) {
				String id = rs.getString("product_id");
				productos.add(id);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return productos;
	}
	
	public List<Integer> getTransportes(String presupuesto_id) {
		ArrayList<Integer> productos = new ArrayList<Integer>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_PRESUPUESTOS_PRODUCTOS WHERE PRESUPUESTO_ID = " + presupuesto_id);
			while (rs.next()) {
				int recoger = rs.getInt("recoger");
				productos.add(recoger);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return productos;
	}
	
}
