package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;
import business.empleados.EmpleadosDataBase;
import business.logic.Empleado;

public class VentanaModificar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase db;
	private JLabel lblTelfono;
	private JLabel lblDireccin;
	private JLabel lblHoraDeEntrada;
	private JLabel lblHoraDeSalida;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtHoraDeEntrada;
	private JTextField txtHoraDeSalida;
	private JButton btnNewButton;
	private JButton btnVolver;
	private Empleado emp;

	public static void run(DataBase db, Empleado emp) {
		try {
			VentanaModificar frame = new VentanaModificar(db, emp);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaModificar(DataBase db, Empleado emp) {
		this.db = db;
		this.emp = emp;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblTelfono());
		contentPane.add(getLblDireccin());
		contentPane.add(getLblHoraDeEntrada());
		contentPane.add(getLblHoraDeSalida());
		contentPane.add(getTxtTelefono());
		contentPane.add(getTxtDireccion());
		contentPane.add(getTxtHoraDeEntrada());
		contentPane.add(getTxtHoraDeSalida());
		contentPane.add(getBtnNewButton());
		contentPane.add(getBtnVolver());
	}

	private JLabel getLblTelfono() {
		if (lblTelfono == null) {
			lblTelfono = new JLabel("Tel\u00E9fono:");
			lblTelfono.setBounds(55, 66, 54, 14);
		}
		return lblTelfono;
	}

	private JLabel getLblDireccin() {
		if (lblDireccin == null) {
			lblDireccin = new JLabel("Direcci\u00F3n:");
			lblDireccin.setBounds(55, 91, 54, 14);
		}
		return lblDireccin;
	}

	private JLabel getLblHoraDeEntrada() {
		if (lblHoraDeEntrada == null) {
			lblHoraDeEntrada = new JLabel("Hora de entrada (HH:MM):");
			lblHoraDeEntrada.setBounds(55, 116, 128, 14);
		}
		return lblHoraDeEntrada;
	}

	private JLabel getLblHoraDeSalida() {
		if (lblHoraDeSalida == null) {
			lblHoraDeSalida = new JLabel("Hora de salida (HH:MM):");
			lblHoraDeSalida.setBounds(55, 141, 128, 14);
		}
		return lblHoraDeSalida;
	}

	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setText("telefono");
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(107, 63, 86, 20);
		}
		return txtTelefono;
	}

	private JTextField getTxtDireccion() {
		if (txtDireccion == null) {
			txtDireccion = new JTextField();
			txtDireccion.setHorizontalAlignment(SwingConstants.LEFT);
			txtDireccion.setText("direccion");
			txtDireccion.setColumns(10);
			txtDireccion.setBounds(107, 88, 86, 20);
		}
		return txtDireccion;
	}

	private JTextField getTxtHoraDeEntrada() {
		if (txtHoraDeEntrada == null) {
			txtHoraDeEntrada = new JTextField();
			txtHoraDeEntrada.setText("hora de entrada");
			txtHoraDeEntrada.setColumns(10);
			txtHoraDeEntrada.setBounds(187, 113, 86, 20);
		}
		return txtHoraDeEntrada;
	}

	private JTextField getTxtHoraDeSalida() {
		if (txtHoraDeSalida == null) {
			txtHoraDeSalida = new JTextField();
			txtHoraDeSalida.setText("hora de salida");
			txtHoraDeSalida.setColumns(10);
			txtHoraDeSalida.setBounds(176, 138, 86, 20);
		}
		return txtHoraDeSalida;
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Modificar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modificarEmpleado();
				}
			});
			btnNewButton.setBounds(107, 227, 89, 23);
		}
		return btnNewButton;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVolver.setBounds(218, 227, 89, 23);
		}
		return btnVolver;
	}

	private void modificarEmpleado() {
		EmpleadosDataBase edb = new EmpleadosDataBase(db);
		
		emp.setDir(getTxtDireccion().getText());
		String horaEnt = getTxtHoraDeEntrada().getText();
		String horaFin = getTxtHoraDeSalida().getText();
		if (!horaEnt.contains(":") || !horaFin.contains(":")) {
			return;
		}
		try {
			emp.setTelefono(Integer.parseInt(getTxtTelefono().getText()));
			emp.setHora_entrada(Integer.parseInt(horaEnt.split(":")[0]));
			emp.setMinuto_entrada(Integer.parseInt(horaEnt.split(":")[1]));
			emp.setHora_salida(Integer.parseInt(horaFin.split(":")[0]));
			emp.setMinuto_salida(Integer.parseInt(horaFin.split(":")[1]));
			
			edb.updateEmpleado(emp);
			
			dispose();
		} catch (Exception e) {
			return;
		}
	}
}