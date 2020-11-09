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
	}

	private JButton getBtnAlmacen() {
		if (btnAlmacen == null) {
			btnAlmacen = new JButton("Ver almac\u00E9n");
			btnAlmacen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaAlmacen.run(db);
				}
			});
			btnAlmacen.setBounds(122, 116, 184, 23);
		}
		return btnAlmacen;
	}
}
