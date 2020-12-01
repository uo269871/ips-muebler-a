package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;
import business.empleados.EmpleadosDataBase;
import business.logic.Empleado;
import business.logic.Transportista;
import business.logic.Vendedor;
import business.transportistas.TransportistasDataBase;
import business.vendedores.VendedoresDataBase;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaInicioSesion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private DataBase db;
	private JPanel panelNorte;
	private JLabel lblInicio;
	private JPanel panelCentro;
	private JLabel lblUsuario;
	private JPanel panelSur;
	private JButton btnSalir;
	private JButton btnInicio;
	private JTextField txtUsuario;
	private JLabel lblContraseña;
	private JPasswordField passContraseña;

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaInicioSesion frame = new VentanaInicioSesion(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Create the frame.
	 */
	public VentanaInicioSesion(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setLayout(new BorderLayout(0, 0));
			panelNorte.add(getLblInicio(), BorderLayout.NORTH);
		}
		return panelNorte;
	}
	private JLabel getLblInicio() {
		if (lblInicio == null) {
			lblInicio = new JLabel("Inicio de sesi\u00F3n");
			lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblInicio;
	}
	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new GridLayout(2, 2, 0, 0));
			panelCentro.add(getLblUsuario());
			panelCentro.add(getTxtUsuario());
			panelCentro.add(getLblContraseña());
			panelCentro.add(getPassContraseña());
		}
		return panelCentro;
	}
	private JLabel getLblUsuario() {
		if (lblUsuario == null) {
			lblUsuario = new JLabel("Usuario: ");
			lblUsuario.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblUsuario;
	}
	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new GridLayout(1, 2, 0, 0));
			panelSur.add(getBtnSalir());
			panelSur.add(getBtnInicio());
		}
		return panelSur;
	}
	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnSalir.setFont(new Font("Dialog", Font.BOLD, 14));
			btnSalir.setForeground(Color.BLACK);
			btnSalir.setBackground(Color.LIGHT_GRAY);
		}
		return btnSalir;
	}
	private JButton getBtnInicio() {
		if (btnInicio == null) {
			btnInicio = new JButton("Iniciar sesi\u00F3n");
			btnInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EmpleadosDataBase edb = new EmpleadosDataBase(db);
					if(edb.checkPassword(getTxtUsuario().getText(), String.valueOf(getPassContraseña().getPassword()))) {
						VentanaPrincipal.run(db);
					}
					else {
						JOptionPane.showConfirmDialog(null, "Usuario y contreseña incorrectos");
					}
				}
			});
			btnInicio.setFont(new Font("Dialog", Font.BOLD, 14));
			btnSalir.setForeground(Color.BLACK);
			btnSalir.setBackground(Color.LIGHT_GRAY);
		}
		return btnInicio;
	}
	
	/*private void checkTipo(Empleado e) {
		TransportistasDataBase tdb = new TransportistasDataBase(db);
		List<Transportista> trans = tdb.getTransportistas();
		for (Transportista t : trans) {
			if (t.getId().equals(e.getId())) {
				VentanaTransportista.run(db);
			}
		}
		VendedoresDataBase vdb = new VendedoresDataBase(db);
		List<Vendedor> vends = vdb.getVendedores();
		for (Vendedor v : vends) {
			if (v.getId().equals(e.getId())) {
				VentanaPrincipal.run(db);;
			}
		}
	}*/
	
	private JTextField getTxtUsuario() {
		if (txtUsuario == null) {
			txtUsuario = new JTextField();
			txtUsuario.setFont(new Font("Dialog", Font.BOLD, 14));
			txtUsuario.setColumns(10);
		}
		return txtUsuario;
	}
	private JLabel getLblContraseña() {
		if (lblContraseña == null) {
			lblContraseña = new JLabel("Contrase\u00F1a: ");
			lblContraseña.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblContraseña;
	}
	private JPasswordField getPassContraseña() {
		if (passContraseña == null) {
			passContraseña = new JPasswordField();
			passContraseña.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return passContraseña;
	}
}
