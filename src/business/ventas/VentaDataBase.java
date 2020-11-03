package business.ventas;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Producto;
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
			aux = rs.last() ? rs.getRow() : 0;
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return aux;
	}
	
	public void addVenta(Venta v, List<String> productos) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement(
					"insert into ips_ventas(venta_id,client_id,fecha_entrega) values (?,?,?)");
			pst.setString(1, v.getVenta_Id());
			pst.setString(2, v.getClient_Id());
			pst.setDate(4, v.getFechaEntrega());
			pst.executeUpdate();

			pst.close();
			db.cierraConexion();
			int i = 0;
			for (String s : productos) {
				pst = db.getConnection().prepareStatement(
						"insert into ips_ventas_productos(venta_id,product_id) values (?,?)");
				pst.setString(1, v.getVenta_Id());
				pst.setString(2, s);
				pst.executeUpdate();

				pst.close();
				db.cierraConexion();
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
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
	
	public List<Producto> getProductos(String id) {
		List<Producto> productos = new ArrayList<Producto>();
		try {
			PreparedStatement pst = db.getConnection().prepareStatement("select product_id from ips_ventas_productos where venta_id = ?");
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				String product_id = rs.getString("product_id");
				PreparedStatement pst2 = db.getConnection().prepareStatement("select * from ips_productos where product_id = ?");
				pst2.setString(1, product_id);
				ResultSet rs2 = pst2.executeQuery();
				if(rs2.next()) {
					String type = rs2.getString("type");
					float price = rs2.getFloat("price");
					String name = rs2.getString("name");
					Producto p = new Producto(name, type, product_id, price);
					productos.add(p);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productos;
	}
	
}