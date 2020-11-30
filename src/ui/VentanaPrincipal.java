package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnVendedor;
	private JButton btnGerente;
	private JButton btnTransportista;
	
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
		setTitle("IPS MUEBLERIA");
		this.db=db;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnVendedor());
		contentPane.add(getBtnGerente());
		contentPane.add(getBtnTransportista());
	}

	private JButton getBtnVendedor() {
		if (btnVendedor == null) {
			btnVendedor = new JButton("Iniciar como Vendedor");
			btnVendedor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaVendedor frame = new VentanaVendedor(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnVendedor.setBounds(129, 61, 188, 20);
		}
		return btnVendedor;
	}
	private JButton getBtnGerente() {
		if (btnGerente == null) {
			btnGerente = new JButton("Iniciar como Gerente");
			btnGerente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaGerente frame = new VentanaGerente(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnGerente.setBounds(129, 106, 188, 20);
		}
		return btnGerente;
	}
	private JButton getBtnTransportista() {
		if (btnTransportista == null) {
			btnTransportista = new JButton("Iniciar como Transportista");
			btnTransportista.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaTransportista frame = new VentanaTransportista(db);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
			btnTransportista.setBounds(129, 151, 188, 20);
		}
		return btnTransportista;
	}
}
