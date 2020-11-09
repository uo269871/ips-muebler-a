package business.logic;

public class Empleado {
	
	public enum Tipo { VENDEDOR, TRANSPORTISTA};
	private String id;
	private String dni;
	private String nombre;
	private String dir;
	private int telefono;
	private int hora_entrada;
	private int minuto_entrada;
	private int hora_salida;
	private int minuto_salida;
	private Tipo tipo;
	
	public Empleado() {
		
	}
	
	public Empleado(String id,String dni, String nombre, String dir, int telefono, int hora_entrada, int minuto_entrada, int hora_salida, int minuto_salida) {
		this.setId(id);
		this.setDni(dni);
		this.setNombre(nombre);
		this.setDir(dir);
		this.setTelefono(telefono);
		this.setHora_entrada(hora_entrada);
		this.setMinuto_entrada(minuto_entrada);
		this.setHora_salida(hora_salida);
		this.setMinuto_salida(minuto_salida);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(int hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public int getMinuto_entrada() {
		return minuto_entrada;
	}

	public void setMinuto_entrada(int minuto_entrada) {
		this.minuto_entrada = minuto_entrada;
	}

	public int getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(int hora_salida) {
		this.hora_salida = hora_salida;
	}

	public int getMinuto_salida() {
		return minuto_salida;
	}

	public void setMinuto_salida(int minuto_salida) {
		this.minuto_salida = minuto_salida;
	}
	
	public void isVendedor() {
		tipo = Tipo.VENDEDOR;
	}
	
	public void isTransportista() {
		tipo = Tipo.TRANSPORTISTA;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
}
