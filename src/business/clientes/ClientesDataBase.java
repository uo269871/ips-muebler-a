/**
 * 
 */
package business.clientes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Cliente;

/**
 * @author uo270656
 *
 */
public class ClientesDataBase {

	private DataBase db;

	public ClientesDataBase(DataBase db) {
		super();
		this.db = db;
	}

	public List<Cliente> getClientes() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_CLIENTES");
			while(rs.next()) {
		    	String name=rs.getString("name");
		    	String client_id=rs.getString("client_id");
		    	String dni=rs.getString("dni");
		    	String address=rs.getString("address");
		    	String email=rs.getString("email");
		    	Cliente pr= new Cliente(name,dni,client_id,address,email);
		    	clientes.add(pr);
		    }
			rs.close();
			st.close();
//		    db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return clientes;
	}

	public String getCliente(String id) {
		String name = "";
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT NAME FROM  IPS_CLIENTES WHERE CLIENT_ID = '" + id + "'");
			while (rs.next()) {
				name = rs.getString("name");
			}
			rs.close();
			st.close();
//		    db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return name;
	}

	public boolean addCliente(Cliente cliente) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement("insert into ips_clientes(client_id,name,dni,address,email) values (?,?,?,?,?)");
			pst.setString(1, cliente.getClient_id());
			pst.setString(2, cliente.getName());
			pst.setString(3, cliente.getDni());
			pst.setString(4, cliente.getAddress());
			pst.executeUpdate();

			pst.close();
//		    db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return true;
	}
}
