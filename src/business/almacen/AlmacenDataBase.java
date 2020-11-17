package business.almacen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Producto;

public class AlmacenDataBase {

	private DataBase db;

	public AlmacenDataBase(DataBase db) {
		super();
		this.db = db;
	}

	public boolean addAlmacenProducto(Producto pr, int uds) {
		if (!getProductos().contains(pr)) {
			try {
				PreparedStatement pst = db.getConnection()
						.prepareStatement("insert into ips_almacen_producto(product_id, unidades) values (?,?)");
				pst.setString(1, pr.getProduct_id());
				pst.setInt(2, uds);

				pst.executeUpdate();

				pst.close();
//				db.cierraConexion();
			} catch (SQLException e) {
				System.out.println("Error while operating the database " + e.getMessage());
			}
			return true;
		}
		else {
			try {
				PreparedStatement pst = db.getConnection()
						.prepareStatement("update ips_almacen_producto set unidades = ? where product_id = ?");
				pst.setInt(1, uds);
				pst.setString(2, pr.getProduct_id());
				
				pst.executeUpdate();
				
				pst.close();
//				db.cierraConexion();
			} catch (SQLException e) {
				System.out.println("Error while operating the database " + e.getMessage());
			}
			return true;
		}
	}

	public int getUnidades(String id) {
		int uds = 0;
		try {
			PreparedStatement pst = db.getConnection()
					.prepareStatement("select unidades from ips_almacen_producto where product_id = ?");
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				uds = rs.getInt("unidades");
			}
//			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}

		return uds;
	}

	public List<Producto> getProductos() {
		List<Producto> productos = new ArrayList<Producto>();
		List<String> aux = new ArrayList<String>();
		try {
			PreparedStatement pst = db.getConnection().prepareStatement("select product_id from ips_almacen_producto");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				aux.add(rs.getString("product_id"));
			}
//			db.cierraConexion();
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
//				db.cierraConexion();

			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return productos;
	}

}
