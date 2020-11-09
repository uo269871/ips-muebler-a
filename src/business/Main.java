/**
 * 
 */
package business;

import business.bbdd.DataBase;
import ui.VentanaVendedor;

/**
 * @author uo270656
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataBase db=new DataBase(1);
		VentanaVendedor.run(db);
	}

}
