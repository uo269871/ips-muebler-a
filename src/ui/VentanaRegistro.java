package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import business.bbdd.DataBase;
import business.clientes.ClientesDataBase;
import business.logic.Cliente;

/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Diálogo que pide los datos del cliente, datos como:
 *	Nombre y Apellidos, Año de nacimiento, Contraseña, Método de obtención del pedido
 */
public class VentanaRegistro extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel pnPrincipal;
	private JPanel pnDatos;
	private JButton btnCancelar;
	private JButton btnSiguiente;
	private JLabel lblNombre;
	private JLabel lblDNI;
	private JLabel lblDireccion;
	private JTextField txtNombre;
	private JTextField txtDNI;
	private JTextField txtDireccion;
	
	protected VentanaClientes vc;
	private DataBase db;
	
	/**
	 * Launch the application.
	 */
	public static void run(VentanaClientes vc,DataBase db) {
		try {
			VentanaRegistro frame = new VentanaRegistro(vc,db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Constructor de la ventana Registro que pide los datos del cliente para realizar el pedido
	 * @param pedido el pedido realizado
	 * @param vp Ventana principal que crea este diálogo
	 */
	public VentanaRegistro(VentanaClientes vc,DataBase db) {
		this.vc=vc;
		this.db=db;
		setModal(true);
		setBounds(new Rectangle(0, 0, 600, 325));
		setTitle("Nuevo Cliente IPS LAB");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 324);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(null);
		pnPrincipal.add(getPnDatos());
		pnPrincipal.add(getBtnCancelar());
		pnPrincipal.add(getBtnSiguiente());
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se dispondrán los datos del cliente si no existe
	 * @return el panel
	 */
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setBorder(new TitledBorder(new LineBorder(new Color(191, 205, 219)), "Datos del cliente", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			pnDatos.setBackground(Color.WHITE);
			pnDatos.setBounds(25, 25, 534, 190);
			pnDatos.setLayout(null);
			pnDatos.add(getLblNombre());
			pnDatos.add(getLblDNI());
			pnDatos.add(getLblDireccion());
			pnDatos.add(getTxtNombre());
			pnDatos.add(getTxtDNI());
			pnDatos.add(getTxtDireccion());
		}
		return pnDatos;
	}
	
	/** Método que devuelve o crea y devuelve el botón de Cancelar si no existe
	 * @actionPerformed elimina la ventana
	 * @return el botón
	 */
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setMnemonic('c');
			btnCancelar.setFont(new Font("Constantia", Font.BOLD, 12));
			btnCancelar.setBackground(Color.RED);
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setBounds(476, 236, 100, 35);
		}
		return btnCancelar;
	}
	
	/** Método que devuelve o crea y devuelve el botón de Siguiente si no existe
	 * @actionPerformed comprueba que los datos son correctos y solo si lo son crea la ventana Confirmación
	 * @return el botón
	 */
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Boolean aux1 = comprobarDatos();
					if(aux1) {
						nuevoCliente();
						dispose();
					}
				}
			});
			btnSiguiente.setMnemonic('s');
			btnSiguiente.setFont(new Font("Constantia", Font.BOLD, 12));
			btnSiguiente.setForeground(Color.WHITE);
			btnSiguiente.setBackground(Color.GREEN);
			btnSiguiente.setBounds(366, 236, 100, 35);
		}
		return btnSiguiente;
	}
	
	private void nuevoCliente() {
		ClientesDataBase cdb = new ClientesDataBase(db);
		String id = UUID.randomUUID().toString();
		cdb.addCliente(new Cliente(getTxtNombre().getText(),getTxtDNI().getText(),id,getTxtDireccion().getText()));
	}
	
	/** Método que devuelve o crea y devuelve la etiqueta que indica el Nombre y los apellidos del cliente si no existe
	 * @return la etiqueta
	 */
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre y Apellido: ");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNombre.setDisplayedMnemonic('n');
			lblNombre.setLabelFor(getTxtNombre());
			lblNombre.setBounds(35, 30, 140, 20);
		}
		return lblNombre;
	}
	
	/** Método que devuelve o crea y devuelve la etiqueta que indica el año del cliente si no existe
	 * @return la etiqueta
	 */
	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel("DNI:");
			lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblDNI.setDisplayedMnemonic('a');
			lblDNI.setBounds(35, 85, 140, 20);
		}
		return lblDNI;
	}
	
	/** Método que devuelve o crea y devuelve la etiqueta que indica cuál es la contraseña del cliente si no existe
	 * @return la etiqueta
	 */
	private JLabel getLblDireccion() {
		if (lblDireccion == null) {
			lblDireccion = new JLabel("Direccion");
			lblDireccion.setLabelFor(getTxtDireccion());
			lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblDireccion.setDisplayedMnemonic('p');
			lblDireccion.setBounds(35, 140, 140, 20);
		}
		return lblDireccion;
	}
	
	/** Método que devuelve o crea y devuelve el cuadro de texto que almacena el nombre y los apellidos del cliente si no existe
	 * @return el cuadro de texto
	 */
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(185, 30, 330, 20);
		}
		return txtNombre;
	}
	
	/** Método que comprueba que el cuadro de texto del nombre no está vacío 
	 * @return true si no lo está, false si si
	 */
	private Boolean comprobarDatos() {
		if(getTxtNombre().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nombre y Apellidos vacío");
			return false;
		}else if(getTxtDNI().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "DNI vacío");
			return false;
		}else if(getTxtDireccion().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Direccion vacío");
			return false;
		}
		return true;
	}
	
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setColumns(10);
			txtDNI.setBounds(185, 87, 330, 20);
		}
		return txtDNI;
	}
	private JTextField getTxtDireccion() {
		if (txtDireccion == null) {
			txtDireccion = new JTextField();
			txtDireccion.setColumns(10);
			txtDireccion.setBounds(185, 142, 330, 20);
		}
		return txtDireccion;
	}
}
