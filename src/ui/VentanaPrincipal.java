package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnVentas;
	private JButton btnPresupuesto;
	private DataBase db;

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaPrincipal frame = new VentanaPrincipal(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(DataBase db) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnVentas());
		contentPane.add(getBtnPresupuesto());
		this.db = db;
	}

	private JButton getBtnVentas() {
		if (btnVentas == null) {
			btnVentas = new JButton("Realizar venta");
			btnVentas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaVentas.run(db);
				}
			});
			btnVentas.setBounds(315, 256, 169, 87);
			btnVentas.setFont(new Font("Dialog", Font.BOLD, 20));
			btnVentas.setForeground(Color.BLACK);
			btnVentas.setBackground(Color.LIGHT_GRAY);
		}
		return btnVentas;
	}

	private JButton getBtnPresupuesto() {
		if (btnPresupuesto == null) {
			btnPresupuesto = new JButton("Realizar presupuesto");
			btnPresupuesto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaPresupuestos.run(db);
				}
			});
			btnPresupuesto.setBounds(279, 97, 240, 87);
			btnPresupuesto.setFont(new Font("Dialog", Font.BOLD, 20));
			btnPresupuesto.setForeground(Color.BLACK);
			btnPresupuesto.setBackground(Color.LIGHT_GRAY);
		}
		return btnPresupuesto;
	}
}
