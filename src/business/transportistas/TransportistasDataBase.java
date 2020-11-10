package business.transportistas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.bbdd.DataBase;
import business.logic.Transportista;

public class TransportistasDataBase {

	private DataBase db;

	public TransportistasDataBase(DataBase db) {
		super();
		this.db = db;
	}
	
	public List<Transportista> getTransportistas() {
		List<Transportista> empleados = new ArrayList<Transportista>();
		try {
			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  IPS_TRANSPORTISTAS");
			while (rs.next()) {
				String dni = rs.getString("dni");
				String id = rs.getString("id_empleado");
				String nombre = rs.getString("nombre");
				int telefono = rs.getInt("telefono");
				int hora_entrada = rs.getInt("hora_entrada");
				int minuto_entrada = rs.getInt("minuto_entrada");
				int hora_salida = rs.getInt("hora_salida");
				int minuto_salida = rs.getInt("minuto_salida");
				Transportista e = new Transportista(dni, id, nombre, telefono, hora_entrada, minuto_entrada, hora_salida,
						minuto_salida);
				empleados.add(e);
			}
			rs.close();
			st.close();
			db.cierraConexion();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		}
		return empleados;
	}

	public List<Transportista> getTranspotista(int hora, int minuto) {
        List<Transportista> transportistas=new ArrayList<Transportista>();
        try {
            Statement st = db.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM  IPS_TRANSPORTISTAS"
                    + " WHERE (HORA_ENTRADA < " + hora + " AND HORA_SALIDA >" + hora
                    + ") OR (HORA_ENTRADA = " + hora + " AND MINUTO_ENTRADA < " + minuto
                    + ") OR (HORA_SALIDA = " + hora + " AND MINUTO_SALIDA > " + minuto + ")");
            while(rs.next()) {
                String dni=rs.getString("dni");
                String id=rs.getString("id_empleado");
                String nombre=rs.getString("nombre");
                int telefono=rs.getInt("telefono");
                int hora_entrada=rs.getInt("hora_entrada");
                int minuto_entrada=rs.getInt("minuto_entrada");
                int hora_salida=rs.getInt("hora_salida");
                int minuto_salida=rs.getInt("minuto_salida");
                Transportista tr= new Transportista(dni,id,nombre,telefono,hora_entrada,minuto_entrada,hora_salida,minuto_salida);
                transportistas.add(tr);
            }
            rs.close();
            st.close();
            db.cierraConexion();
        }catch(SQLException e){
            System.out.println("Error while operating the database " + e.getMessage());
        }
        return transportistas;
    }

}
