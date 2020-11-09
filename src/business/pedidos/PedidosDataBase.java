/**
 * 
 */
package business.pedidos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Pedido;
import business.logic.Producto;
import util.Claves;

/**
 * @author uo270656
 *
 */
public class PedidosDataBase {

	private DataBase db;

	public PedidosDataBase(DataBase db) {
		super();
		this.db = db;
	}

	public List<Pedido> getPedidos() {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM IPS_PEDIDO");
			while (rs.next()) {
				int pedido_id = Integer.parseInt(rs.getString("pedido_id"));
				float price = rs.getFloat("total_price");
				String state = rs.getString("state");
				Pedido pr = new Pedido(pedido_id ,price,state,null);
				pedidos.add(pr);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return pedidos;
		
	}

//	public boolean addPedido(Pedido pedido) {
//		try {
//			PreparedStatement pst = db.getConnection().prepareStatement(
//					"insert into ips_pedidos_proveedor(product_id,unidades,price,pedido_id,state) values (?,?,?)");
//			pst.setInt(1, pedido.getProduct_id());
//			pst.setInt(2, pedido.getUds());
//			pst.setFloat(3, pedido.getPrice());
//			pst.setInt(4, pedido.getPedido_id());
//			pst.setString(5, pedido.getEstado());
//			pst.executeUpdate();
//
//			pst.close();
//			db.cierraConexion();
//		} catch (SQLException e) {
//			System.out.println("Error while operating the database " + e.getMessage());
//		}
//		return true;
//	}

	public Pedido getPedidoById(String pedido_id) {
		Pedido pr=new Pedido();
		List<Producto> productos=new ArrayList<Producto>();
		try {
			PreparedStatement st = db.getConnection().prepareStatement("SELECT * FROM  IPS_PEDIDO WHERE pedido_id = ?");
			st.setString(1, Claves.toClave(Integer.parseInt(pedido_id)));
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				pr.setEstado(rs.getString("state"));
				pr.setPedido_id(Integer.parseInt(rs.getString("pedido_id")));
				pr.setTotal_price(rs.getFloat("total_price"));
				rs.close();
				st.close();
				db.cierraConexion();
				st = db.getConnection().prepareStatement("SELECT * FROM  IPS_PEDIDO_PRODUCTO WHERE pedido_id = ?");
				st.setString(1, Claves.toClave(Integer.parseInt(pedido_id)));
				rs=st.executeQuery();
				while (rs.next()) {
					Producto pro=new Producto();
					pro.setProduct_id(String.valueOf(rs.getInt("product_id")));
					pro.setUds(rs.getInt("unidades"));
					pro.setPrice(rs.getFloat("price"));
					productos.add(pro);
				}
				rs.close();
				st.close();
				db.cierraConexion();
				for(Producto aux:productos) {
					st = db.getConnection().prepareStatement("SELECT * FROM  IPS_PRODUCTOS WHERE product_id = ?");
					st.setString(1, Claves.toClave(Integer.parseInt(aux.getProduct_id())));
					rs=st.executeQuery();
					while (rs.next()) {
						aux.setType(rs.getString("type"));
						aux.setName(rs.getString("name"));
					}
					rs.close();
					st.close();
					db.cierraConexion();
				}
				pr.setProductos(productos);
			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return pr;
	}
	
	public void update(String pedido_id) {
		try {
			PreparedStatement st = db.getConnection().prepareStatement("update IPS_PEDIDO set  state = 'RECIBIDO' where pedido_id = ?");
			st.setString(1, Claves.toClave(Integer.parseInt(pedido_id)));
			st.executeQuery();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
	}

}
