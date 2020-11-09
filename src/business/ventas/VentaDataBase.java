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

	public void addVenta(Venta v, List<String> productos) {

		try {
			PreparedStatement pst = db.getConnection()
					.prepareStatement("insert into ips_ventas(venta_id,client_id,fecha_entrega) values (?,?,?)");
			pst.setString(1, v.getVenta_Id());
			pst.setString(2, v.getClient_Id());
			pst.setDate(3, v.getFechaEntrega());
			pst.executeUpdate();

			pst.close();
			db.cierraConexion();
			for (String s : productos) {
				pst = db.getConnection().prepareStatement(
						"insert into ips_ventas_productos(venta_id,product_id, recoger, montar) values (?,?,?,?)");
				pst.setString(1, v.getVenta_Id());
				pst.setString(2, s);
				pst.setInt(3, 0);
				pst.setInt(4, 0);

				pst.executeUpdate();

				pst.close();
				db.cierraConexion();
			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database  " + e.getMessage());
		}
	}
	
	public List<Venta> getVentas() {
		List<Venta> ventas = new ArrayList<Venta>();
		try {
			Statement pst = db.getConnection().createStatement();
			ResultSet rs = pst.executeQuery("select * from ips_ventas");
			while (rs.next()) {
				String venta_id = rs.getString("venta_id");
				String client_id = rs.getString("client_id");
				Date fecha = rs.getDate("fecha_entrega");
				Venta v = new Venta(client_id, venta_id, fecha);
				ventas.add(v);
			}
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}

		return ventas;
	}

	public List<Producto> getProductos(String id) {
		List<Producto> productos = new ArrayList<Producto>();
		List<String> aux = new ArrayList<String>();
		try {
			PreparedStatement pst = db.getConnection()
					.prepareStatement("select product_id from ips_ventas_productos where venta_id = ?");
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				aux.add(rs.getString("product_id"));
			}
			db.cierraConexion();
			for (String s : aux) {
				pst = db.getConnection().prepareStatement("select * from ips_productos where product_id = ?");
				pst.setString(1, s);
				rs = pst.executeQuery();
				if (rs.next()) {
					String type = rs.getString("type");
					float price = rs.getFloat("price");
					String name = rs.getString("name");
					Producto p = new Producto(name, type, s, price);
					productos.add(p);
				}
				pst.close();
				db.cierraConexion();

			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return productos;
	}
	
	public void updateTransporteMontaje(Producto producto, Venta venta, int transporte, int montaje) {
		try {
			PreparedStatement pst = db.getConnection()
					.prepareStatement("update ips_ventas_productos set recoger = ?, montar = ? where product_id = ? and venta_id = ?");
			pst.setInt(1, transporte);
			pst.setInt(2, montaje);
			pst.setString(3, producto.getProduct_id());
			pst.setString(4, venta.getVenta_Id());
			
			pst.executeQuery();
			
			pst.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
	}

}