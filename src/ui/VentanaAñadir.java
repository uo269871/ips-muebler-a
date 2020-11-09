package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;
import business.empleados.EmpleadosDataBase;
import business.logic.Empleado;

public class VentanaAñadir extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase db;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblDni;
	private JLabel lblTelfono;
	private JLabel lblDireccin;
	private JLabel lblHoraDeEntrada;
	private JLabel lblHoraDeSalida;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDni;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtHoraDeEntrada;
	private JTextField txtHoraDeSalida;
	private JLabel lblTipo;
	private JComboBox<String> comboBox;
	private JButton btnNewButton;
	private JButton btnVolver;

	public static void run(DataBase db) {
		try {
			VentanaAñadir frame = new VentanaAñadir(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaAñadir(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNombre());
		contentPane.add(getLblApellidos());
		contentPane.add(getLblDni());
		contentPane.add(getLblTelfono());
		contentPane.add(getLblDireccin());
		contentPane.add(getLblHoraDeEntrada());
		contentPane.add(getLblHoraDeSalida());
		contentPane.add(getLblTipo());
		contentPane.add(getTxtNombre());
		contentPane.add(getTxtApellidos());
		contentPane.add(getTxtDni());
		contentPane.add(getTxtTelefono());
		contentPane.add(getTxtDireccion());
		contentPane.add(getTxtHoraDeEntrada());
		contentPane.add(getTxtHoraDeSalida());
		contentPane.add(getComboBox());
		contentPane.add(getBtnNewButton());
		contentPane.add(getBtnVolver());
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(55, 11, 54, 14);
		}
		return lblNombre;
	}

	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setBounds(55, 36, 54, 14);
		}
		return lblApellidos;
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setBounds(55, 61, 54, 14);
		}
		return lblDni;
	}

	private JLabel getLblTelfono() {
		if (lblTelfono == null) {
			lblTelfono = new JLabel("Tel\u00E9fono:");
			lblTelfono.setBounds(55, 86, 54, 14);
		}
		return lblTelfono;
	}

	private JLabel getLblDireccin() {
		if (lblDireccin == null) {
			lblDireccin = new JLabel("Direcci\u00F3n:");
			lblDireccin.setBounds(55, 111, 54, 14);
		}
		return lblDireccin;
	}

	private JLabel getLblHoraDeEntrada() {
		if (lblHoraDeEntrada == null) {
			lblHoraDeEntrada = new JLabel("Hora de entrada (HH:MM):");
			lblHoraDeEntrada.setBounds(55, 136, 128, 14);
		}
		return lblHoraDeEntrada;
	}

	private JLabel getLblHoraDeSalida() {
		if (lblHoraDeSalida == null) {
			lblHoraDeSalida = new JLabel("Hora de salida (HH:MM):");
			lblHoraDeSalida.setBounds(55, 161, 128, 14);
		}
		return lblHoraDeSalida;
	}

	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setText("nombre");
			txtNombre.setBounds(107, 8, 86, 20);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}

	private JTextField getTxtApellidos() {
		if (txtApellidos == null) {
			txtApellidos = new JTextField();
			txtApellidos.setText("apellidos");
			txtApellidos.setColumns(10);
			txtApellidos.setBounds(107, 33, 86, 20);
		}
		return txtApellidos;
	}

	private JTextField getTxtDni() {
		if (txtDni == null) {
			txtDni = new JTextField();
			txtDni.setText("dni");
			txtDni.setColumns(10);
			txtDni.setBounds(82, 58, 86, 20);
		}
		return txtDni;
	}

	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setText("telefono");
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(107, 83, 86, 20);
		}
		return txtTelefono;
	}

	private JTextField getTxtDireccion() {
		if (txtDireccion == null) {
			txtDireccion = new JTextField();
			txtDireccion.setHorizontalAlignment(SwingConstants.LEFT);
			txtDireccion.setText("direccion");
			txtDireccion.setColumns(10);
			txtDireccion.setBounds(107, 108, 86, 20);
		}
		return txtDireccion;
	}

	private JTextField getTxtHoraDeEntrada() {
		if (txtHoraDeEntrada == null) {
			txtHoraDeEntrada = new JTextField();
			txtHoraDeEntrada.setText("hora de entrada");
			txtHoraDeEntrada.setColumns(10);
			txtHoraDeEntrada.setBounds(193, 133, 86, 20);
		}
		return txtHoraDeEntrada;
	}

	private JTextField getTxtHoraDeSalida() {
		if (txtHoraDeSalida == null) {
			txtHoraDeSalida = new JTextField();
			txtHoraDeSalida.setText("hora de salida");
			txtHoraDeSalida.setColumns(10);
			txtHoraDeSalida.setBounds(177, 158, 86, 20);
		}
		return txtHoraDeSalida;
	}

	private JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo:");
			lblTipo.setBounds(55, 186, 39, 14);
		}
		return lblTipo;
	}

	private JComboBox<String> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<String>();
			comboBox.setBounds(82, 183, 101, 20);
			String[] array = { "Transportista", "Vendedor" };
			comboBox.setModel(new DefaultComboBoxModel<String>(array));
		}
		return comboBox;
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("A\u00F1adir");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirEmpleado();
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

	private void añadirEmpleado() {
		EmpleadosDataBase edb = new EmpleadosDataBase(db);
		Empleado emp = new Empleado();
		
		emp.setId(String.valueOf(edb.getEmpleados().size() + 1));
		emp.setNombre(getTxtNombre().getText() + " " + getTxtApellidos().getText());
		emp.setDir(getTxtDireccion().getText());
		emp.setDni(getTxtDni().getText());
		String horaEnt = getTxtHoraDeEntrada().getText();
		String horaFin = getTxtHoraDeSalida().getText();
		if (!horaEnt.contains(":") || !horaFin.contains(":")) {
			return;
		}
		try {
			emp.setHora_entrada(Integer.parseInt(horaEnt.split(":")[0]));
			emp.setMinuto_entrada(Integer.parseInt(horaEnt.split(":")[1]));
			emp.setHora_salida(Integer.parseInt(horaFin.split(":")[0]));
			emp.setMinuto_salida(Integer.parseInt(horaFin.split(":")[1]));
			
			edb.addEmpleado(emp);
			VentanaEmpleados.run(db);
			dispose();
		} catch (Exception e) {
			
			return;
		}
	}
}
