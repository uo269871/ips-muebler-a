package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;

public class VentanaVendedor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCrearPresupuesto;
	private JButton btnAceptarPresupuesto;
	private JButton btnHistorialDeVentas;
	private JButton btnHistorialPedidosAl;
	
	private DataBase db;
	private JButton btnCrearPedido;

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaVendedor frame = new VentanaVendedor(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaVendedor(DataBase db) {
		setTitle("IPS VENDEDOR");
		this.db=db;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnCrearPresupuesto());
		contentPane.add(getBtnAceptarPresupuesto());
		contentPane.add(getBtnHistorialDeVentas());
		contentPane.add(getBtnHistorialPedidosAl());
		contentPane.add(getBtnCrearPedido());
	}

	private JButton getBtnCrearPresupuesto() {
		if (btnCrearPresupuesto == null) {
			btnCrearPresupuesto = new JButton("Crear presupuesto");
			btnCrearPresupuesto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaPresupuestos frame = new VentanaPresupuestos(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnCrearPresupuesto.setBounds(153, 35, 143, 20);
		}
		return btnCrearPresupuesto;
	}
	private JButton getBtnAceptarPresupuesto() {
		if (btnAceptarPresupuesto == null) {
			btnAceptarPresupuesto = new JButton("Aceptar presupuesto");
			btnAceptarPresupuesto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaVentas frame = new VentanaVentas(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnAceptarPresupuesto.setBounds(146, 80, 156, 20);
		}
		return btnAceptarPresupuesto;
	}
	private JButton getBtnHistorialDeVentas() {
		if (btnHistorialDeVentas == null) {
			btnHistorialDeVentas = new JButton("Historial de ventas");
			btnHistorialDeVentas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaHistorialVentas frame = new VentanaHistorialVentas(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnHistorialDeVentas.setBounds(142, 125, 165, 20);
		}
		return btnHistorialDeVentas;
	}
	private JButton getBtnHistorialPedidosAl() {
		if (btnHistorialPedidosAl == null) {
			btnHistorialPedidosAl = new JButton("Historial pedidos al proveedor");
			btnHistorialPedidosAl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaPedidosProveedor frame = new VentanaPedidosProveedor(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnHistorialPedidosAl.setBounds(106, 215, 237, 20);
		}
		return btnHistorialPedidosAl;
	}
	private JButton getBtnCrearPedido() {
		if (btnCrearPedido == null) {
			btnCrearPedido = new JButton("Hacer pedido al proveedor");
			btnCrearPedido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaCrearPedido frame = new VentanaCrearPedido(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnCrearPedido.setBounds(106, 170, 237, 20);
		}
		return btnCrearPedido;
	}
}
