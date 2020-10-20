/**
 * 
 */
package util;

/**
 * @author uo270656
 *
 */
public class Claves {
	
	public static String toClave(int n) {
		if(n<=0) {
			throw new NumberFormatException("El numero es menor o igual a 0");
		}
		String str = "";
		if(n<10) {
			str="00"+n;
		}else if(n<100) {
			str="0"+n;
		}else {
			str=""+n;
		}
		return str;
	}

}
