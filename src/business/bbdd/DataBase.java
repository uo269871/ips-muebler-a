/**
 * 
 */
package business.bbdd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

/**
 * @author uo270656
 *
 */
public class DataBase {

	final static String DB_URL = "jdbc:oracle:thin:@156.35.94.99:1521:DESA";
	final static String DB_USER = "UO271033";
	final static String DB_PASSWORD = "passuwu";

	final static int BASE_VACIA = 0;
	final static int BASE_RELLENA_DEFECTO = 1;
	final static int BASE_RELLENA_DATOS_ANTERIORES = 2;

	private OracleConnection connection;

	public DataBase(int modo) {
		try {
			getConnection();
			// Get the JDBC driver name and version
			DatabaseMetaData dbmd = connection.getMetaData();
			System.out.println("Driver Name: " + dbmd.getDriverName());
			System.out.println("Driver Version: " + dbmd.getDriverVersion());
			// Print some connection properties
			System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
			System.out.println("Database Username is: " + connection.getUserName());
			System.out.println();
			if (modo == BASE_RELLENA_DEFECTO) {
				inicializeRellena();
			} else if (modo == BASE_VACIA) {
				inicializeVacia();
			} else if (modo == BASE_RELLENA_DATOS_ANTERIORES) {
				//
			}
			cierraConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the connection
	 * @throws SQLException
	 */
	public OracleConnection getConnection() throws SQLException {
		Properties info = new Properties();
		info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
		info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
		info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");

		OracleDataSource ods = new OracleDataSource();

		ods.setURL(DB_URL);
		ods.setConnectionProperties(info);

		connection = (OracleConnection) ods.getConnection();
		return connection;
	}

	public void inicializeRellena() {
		inicializeVacia();
		rellenarDatos();
	}

	@SuppressWarnings("resource")
	public void inicializeVacia() {
		try {
			FileReader f = new FileReader("sqls/TABLAS.sql");
			BufferedReader b = new BufferedReader(f);
			String cadena;
			while ((cadena = b.readLine()) != null) {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT count(*) FROM  ALL_TABLES where table_name = '" + cadena + "'");
				rs.next();
				int x = rs.getInt(1);
				if (x == 1) {
					st.executeUpdate("drop table " + cadena + " cascade constraints");
				}
				rs.close();
				String sql = leerFicheroSQL(cadena + ".sql");
				st.executeUpdate(sql);
				st.close();
			}
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("resource")
	public void rellenarDatos() {
		try {
			Statement st = connection.createStatement();
			FileReader f = new FileReader("sqls/TABLAS.sql");
			BufferedReader b = new BufferedReader(f);
			String cadena;
			while ((cadena = b.readLine()) != null) {
				st.executeUpdate("delete from " + cadena);
				ejecutarFicheroDatos(cadena + "_DATOS.sql", st);
			}
			st.close();
		} catch (SQLException e) {
			System.out.println("Error while operating the database " + e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void ejecutarFicheroDatos(String fileName, Statement st) throws SQLException, IOException{
		String ruta="sqls/";
		ruta+=fileName;
	    String cadena;
	    FileReader f;
	    try {
			f = new FileReader(ruta);
		
			BufferedReader b = new BufferedReader(f);
	      	while((cadena = b.readLine())!=null) {
	          	st.executeUpdate(cadena);
	      	}
	      	b.close();
		} catch (FileNotFoundException e) {
			
		}
	}

	private String leerFicheroSQL(String fileName) throws FileNotFoundException, IOException {
		String ruta = "sqls/";
		ruta += fileName;
		String cadena;
		String ret = "";
		FileReader f = new FileReader(ruta);
		BufferedReader b = new BufferedReader(f);
		while ((cadena = b.readLine()) != null) {
			ret += cadena;
		}
		b.close();
		return ret;
	}

	public void cierraConexion() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
