/**
 * 
 */
package business;

import business.bbdd.DataBase;
import ui.VentanaHistorialVentas;

/**
 * @author uo270656
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataBase db=new DataBase(2);
//		VentanaPrincipal.run(db);
		VentanaHistorialVentas.run(db);
	}

}
