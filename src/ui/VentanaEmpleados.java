package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.empleados.EmpleadosDataBase;
import business.logic.Empleado;
import business.logic.Transportista;
import business.transportistas.TransportistasDataBase;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaEmpleados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCentro;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel modeloTableEmpleados;
	private DataBase db;
	private List<Empleado> empleados;
	private JPanel panelSur;
	private JButton btnAñadir;
	private JButton btnNewButton;

	public static void run(DataBase db) {
		try {
			VentanaEmpleados frame = new VentanaEmpleados(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaEmpleados(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getLblNewLabel(), BorderLayout.NORTH);
			panelCentro.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panelCentro;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Empleados");
		}
		return lblNewLabel;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			EmpleadosDataBase edb = new EmpleadosDataBase(db);
			empleados = edb.getEmpleados();

			Vector<String> v = new Vector<String>();
			v.add("Nombre del empleado");
			v.add("DNI del empleado");
			v.add("Tipo del empleado");

			modeloTableEmpleados = new DefaultTableModel(v, empleados.size()) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			table = new JTable(modeloTableEmpleados);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for (int i = 0; i < empleados.size(); i++) {
				table.setValueAt(empleados.get(i).getNombre(), i, 0);
				table.setValueAt(empleados.get(i).getDni(), i, 1);
				if (isTransportista(empleados.get(i))) {
					table.setValueAt("Transportista", i, 2);
				} else {
					table.setValueAt("Vendedor", i, 2);
				}

			}
		}
		return table;
	}

	private boolean isTransportista(Empleado e) {
		TransportistasDataBase tdb = new TransportistasDataBase(db);
		List<Transportista> tranportistas = tdb.getTransportistas();

		for (Transportista t : tranportistas) {
			if (e.getDni().equals(t.getDni())) {
				return true;
			}
		}

		return false;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.add(getBtnAñadir());
			panelSur.add(getBtnNewButton());
		}
		return panelSur;
	}

	private JButton getBtnAñadir() {
		if (btnAñadir == null) {
			btnAñadir = new JButton("A\u00F1adir empleado");
			btnAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaAñadir.run(db);
					dispose();
				}
			});
		}
		return btnAñadir;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Modificar empleado");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EmpleadosDataBase edb = new EmpleadosDataBase(db);
					empleados = edb.getEmpleados();
					int pos = table.getSelectedRow();
					
					Empleado emp = empleados.get(pos);
					
					VentanaModificar.run(db, emp);
				}
			});
		}
		return btnNewButton;
	}
}
