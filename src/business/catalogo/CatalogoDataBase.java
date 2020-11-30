/**
 * 
 */
package business.catalogo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Producto;

/**
 * @author uo270656
 *
 */
public class CatalogoDataBase {

	private DataBase db;

	public CatalogoDataBase(DataBase db) {
		super();
		this.db = db;
	}

	public List<Producto> getProductos() {
		ArrayList<Producto> productos=new ArrayList<Producto>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_PRODUCTOS");
		    while(rs.next()) {
		    	String name=rs.getString("name");
		    	String product_id=rs.getString("product_id");
		    	String type=rs.getString("type");
		    	float price=rs.getFloat("price");
		    	Producto pr= new Producto(name,type,product_id,price);
		    	productos.add(pr);
		    }
		    rs.close();
		    st.close();
//		    db.cierraConexion();
		}catch(SQLException e){
			System.out.println("Error while operating the database " + e.getMessage());
	    }
		return productos;
    }
	
	public Producto getProductoById(String id) {
		Producto pr=null;
		try {
			PreparedStatement st = db.getConnection().prepareStatement("SELECT * FROM  IPS_PRODUCTOS WHERE product_id=?");
			st.setString(1, id);
			ResultSet rs=st.executeQuery();
		    if(rs.next()) {
		    	String name=rs.getString("name");
		    	String product_id=rs.getString("product_id");
		    	String type=rs.getString("type");
		    	float price=rs.getFloat("price");
		    	pr= new Producto(name,type,product_id,price);
		    }
		    rs.close();
		    st.close();
//		    db.cierraConexion();
		}catch(SQLException e){
			System.out.println("Error while operating the database " + e.getMessage());
	    }
		return pr;
    }
	
	public List<Producto> getProductosFiltroTipos(String filtro) {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		try {
			PreparedStatement st = db.getConnection().prepareStatement("SELECT * FROM  IPS_PRODUCTOS WHERE TYPE = ?");
			st.setString(1, filtro);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String product_id = rs.getString("product_id");
				String type = rs.getString("type");
				float price = rs.getFloat("price");
				Producto pr = new Producto(name, type, product_id, price);
				productos.add(pr);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return productos;
	}

	public List<Producto> getProductosFiltroPrecios(float p1, float p2) {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		try {
			PreparedStatement st = db.getConnection()
					.prepareStatement("SELECT * FROM  IPS_PRODUCTOS WHERE PRICE >= ? AND  PRICE <= ?");
			st.setFloat(1, p1);
			st.setFloat(2, p2);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String product_id = rs.getString("product_id");
				String type = rs.getString("type");
				float price = rs.getFloat("price");
				Producto pr = new Producto(name, type, product_id, price);
				productos.add(pr);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return productos;
	}

	public String[] getTipos() {
		List<String> aux = new ArrayList<String>();

		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT TYPE FROM  IPS_PRODUCTOS");
			while (rs.next()) {
				String type = rs.getString("type");
				aux.add(type);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}

		String[] tipos = new String[aux.size()];

		for (int i = 0; i < aux.size(); i++) {
			System.out.println(aux.get(i));
			tipos[i] = aux.get(i);
		}

		return tipos;
	}
	
	public double getPrecio(String id) {
		double precio=0.0;
		try {
			PreparedStatement st = db.getConnection().prepareStatement("SELECT PRICE FROM  IPS_PRODUCTOS WHERE product_id=?");
			st.setString(1, Claves.toClave(Integer.parseInt(id)));
			ResultSet rs=st.executeQuery();
		    if(rs.next()) {
		    	precio = rs.getFloat("price");
		    }
		    rs.close();
		    st.close();
		    db.cierraConexion();
		}catch(SQLException e){
			System.out.println("Error while operating the database " + e.getMessage());
	    }
		return precio;
    }
}
