package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;

public class VentanaTransportista extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnEstado;
	
	private DataBase db;

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaTransportista frame = new VentanaTransportista(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaTransportista(DataBase db) {
		this.db = db;
		setTitle("IPS TRANSPORTISTA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnEstado());
	}

	private JButton getBtnEstado() {
		if (btnEstado == null) {
			btnEstado = new JButton("Estado transporte");
			btnEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaEstado.run(db);
				}
			});
			btnEstado.setBounds(104, 107, 229, 23);
		}
		return btnEstado;
	}
}
