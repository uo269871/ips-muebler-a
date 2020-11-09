package business.transportes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Transporte;

public class TransportesDataBase {
	
	private DataBase db;
	
	public TransportesDataBase(DataBase db) {
		super();
		this.db = db;
	}
	
	public boolean addTransportes(Transporte transporte) {
		try {
			PreparedStatement pst = db.getConnection().prepareStatement(
					"insert into ips_transportes(id_transporte,id_venta,dni_transportista,dia_entrega,hora_entrega,minuto_entrega) values (?,?,?,?,?,?,?)");
			pst.setString(1, transporte.getId_transporte());
			pst.setString(2, transporte.getId_venta());
			pst.setString(3, transporte.getDni_transportista());
			pst.setDate(4, transporte.getDia_entrega());
			pst.setInt(5, transporte.getHora_entrega());
			pst.setInt(6, transporte.getMinuto_entrega());
			pst.setString(7, transporte.getEstado());
			
			pst.executeUpdate();

			pst.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return true;
		
	}
	
	public int getNumeroTransportes() {
		int aux = 0;
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM  IPS_TRANSPORTES");
			if (rs.next()) {
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
	
	public List<Transporte> getTransportes() {
		List<Transporte> trs = new ArrayList<Transporte>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_TRANSPORTES");
			while (rs.next()) {
				String id = rs.getString("id_transporte");
				String id_venta = rs.getString("id_venta");
				String dni_transportista = rs.getString("dni_transportista");
				Date dia = rs.getDate("dia_entrega");
				int hora = rs.getInt("hora_entrega");
				int minuto = rs.getInt("minuto_entrega");
				String estado = rs.getString("estado");
				Transporte t = new Transporte(id, id_venta, dni_transportista, dia, hora, minuto, estado);
				trs.add(t);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return trs;
	}

}
