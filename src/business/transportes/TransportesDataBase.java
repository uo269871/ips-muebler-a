package business.transportes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
					"insert into ips_transportes(id_transporte,id_venta,dni_transportista,dia_entrega,hora_entrega,minuto_entrega) values (?,?,?,?,?,?)");
			pst.setString(1, transporte.getId_transporte());
			pst.setString(2, transporte.getId_venta());
			pst.setString(3, transporte.getDni_transportista());
			pst.setDate(4, transporte.getDia_entrega());
			pst.setInt(5, transporte.getHora_entrega());
			pst.setInt(6, transporte.getMinuto_entrega());
			
			pst.executeUpdate();

			pst.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return true;
		
	}

}
