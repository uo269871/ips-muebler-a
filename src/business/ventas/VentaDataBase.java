package business.ventas;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Venta;

public class VentaDataBase{

	private DataBase db;
	
	public VentaDataBase(DataBase db) {
		this.db = db;
	}
	
	public int getNumeroVentas() {
		int aux = 0;
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM  IPS_VENTAS");
			if(rs.next()) {
				aux = rs.getInt(1);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return aux;
	}
	
	public void addVenta(Venta v, List<String> productos, List<Integer> transporte, List<Integer> montaje) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement(
					"insert into ips_ventas(venta_id,client_id,fecha_entrega) values (?,?,?)");
			pst.setString(1, v.getVenta_Id());
			pst.setString(2, v.getClient_Id());
			pst.setDate(3, v.getFechaEntrega());
			pst.executeUpdate();

			pst.close();
			db.cierraConexion();
			int i = 0;
			for (String s : productos) {
				pst = db.getConnection().prepareStatement(
						"insert into ips_ventas_productos(venta_id,product_id, recoger, montar) values (?,?,?,?)");
				pst.setString(1, v.getVenta_Id());
				pst.setString(2, s);
				pst.setInt(3, transporte.get(i));
				pst.setInt(4, montaje.get(i));
				pst.executeUpdate();

				pst.close();
				db.cierraConexion();
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database  " + e.getMessage());
		}
	}
	
	public Venta getUltimaVenta() {
		List<Venta> ventas = new ArrayList<Venta>();
		try {
			Statement pst = db.getConnection().createStatement();
			ResultSet rs = pst.executeQuery("select * from ips_ventas");
			while(rs.next()) {
				String venta_id = rs.getString("venta_id");
				String client_id = rs.getString("client_id");
				Date fecha = rs.getDate("fecha_entrega");
				Venta v = new Venta(venta_id, client_id, fecha);
				ventas.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ventas.get(ventas.size()-1);
		
	}

	public List<Venta> getVentas() {
		List<Venta> ventas = new ArrayList<Venta>();
		try {
			Statement pst = db.getConnection().createStatement();
			ResultSet rs = pst.executeQuery("select * from ips_ventas");
			while(rs.next()) {
				String venta_id = rs.getString("venta_id");
				String client_id = rs.getString("client_id");
				Date fecha = rs.getDate("fecha_entrega");
				Venta v = new Venta(venta_id, client_id, fecha);
				ventas.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ventas;
	}
	
}