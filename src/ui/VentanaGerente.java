package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;

public class VentanaGerente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAlmacen;
	
	private DataBase db;
	private JButton btnNewButton;
	private JButton btnBalance;

	/**
	 * Launch the application.
	 */

	public static void run(DataBase db) {
		try {
			VentanaGerente frame = new VentanaGerente(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaGerente(DataBase db) {
		this.db = db;
		setTitle("IPS GERENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnAlmacen());
		contentPane.add(getBtnNewButton());
		contentPane.add(getBtnBalance());
	}

	private JButton getBtnAlmacen() {
		if (btnAlmacen == null) {
			btnAlmacen = new JButton("Ver almac\u00E9n");
			btnAlmacen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaAlmacen frame = new VentanaAlmacen(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnAlmacen.setBounds(125, 73, 184, 23);
		}
		return btnAlmacen;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Administrar empleados");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaEmpleados frame = new VentanaEmpleados(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnNewButton.setBounds(125, 133, 184, 23);
		}
		return btnNewButton;
	}
	private JButton getBtnBalance() {
		if (btnBalance == null) {
			btnBalance = new JButton("Ver balance");
			btnBalance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaBalance.run(db);
				}
			});
			btnBalance.setBounds(125, 191, 184, 23);
		}
		return btnBalance;
	}
}
