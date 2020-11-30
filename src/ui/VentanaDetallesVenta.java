package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.logic.Producto;
import business.logic.Venta;
import business.transportes.TransportesDataBase;
import business.ventas.VentaDataBase;

public class VentanaDetallesVenta extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private Venta v;
	private DataBase db;
	private JLabel lblTitulo;
	private JPanel panel;
	private JPanel panelSur;
	private JButton btnVolver;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel modeloTableVenta;
	private JTextPane txtpnFechaDeLa;

	public static void run(Venta v, DataBase db) {
		try {
			VentanaDetallesVenta frame = new VentanaDetallesVenta(v, db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VentanaDetallesVenta(Venta v, DataBase db) {
		this.v = v;
		this.db = db;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLblTitulo(), BorderLayout.NORTH);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Detalles de venta");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 21));
		}
		return lblTitulo;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getScrollPane(), BorderLayout.CENTER);
			panel.add(getTxtpnFechaDeLa(), BorderLayout.NORTH);
		}
		return panel;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.add(getBtnVolver());
		}
		return panelSur;
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnVolver;
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
			List<Producto> productos = new VentaDataBase(db).getProductosVenta(v.getVenta_Id());

			Vector<String> vector = new Vector<String>();
			vector.add("Producto");
			vector.add("Uds");
			vector.add("Transporte");
			vector.add("Fecha");
			vector.add("Montaje");

			modeloTableVenta = new DefaultTableModel(vector, productos.size()) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			table = new JTable(modeloTableVenta);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int i = 0; i < productos.size(); i++) {
				String id = productos.get(i).getName();
				table.setValueAt(id, i, 0);
				table.setValueAt(productos.get(i).getUds(), i, 1);
				int r = productos.get(i).getRecoger();

				if (r == 1) {
					table.setValueAt("Sí", i, 2);
					Date t = new TransportesDataBase(db).getTransporteVenta(v.getVenta_Id()).getDia_entrega();
					table.setValueAt(t, i, 3);
					int m = productos.get(i).getMontar();
					if (m == 1)
						table.setValueAt("Sí", i, 4);
					else
						table.setValueAt("No", i, 4);
				} else {
					table.setValueAt("No", i, 2);
					table.setValueAt("-", i, 3);
					table.setValueAt("No", i, 4);
				}

			}

		}
		return table;
	}

	private JTextPane getTxtpnFechaDeLa() {
		if (txtpnFechaDeLa == null) {
			txtpnFechaDeLa = new JTextPane();
			txtpnFechaDeLa.setEditable(false);
			String s = "Id de la venta: " + v.getVenta_Id() + "\nFecha de la venta: " + v.getFechaEntrega();
			txtpnFechaDeLa.setText(s);
			txtpnFechaDeLa.setBackground(UIManager.getColor("Button.background"));
		}
		return txtpnFechaDeLa;
	}
}
