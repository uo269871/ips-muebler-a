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
import business.logic.Producto;
import business.logic.Venta;
import business.ventas.VentaDataBase;

public class VentanaHistorialVentas extends JFrame {

	private static final long serialVersionUID = 2318154265005141684L;
	private JPanel contentPane;
	private JPanel panelCentro;
	private JLabel lblHistorial;
	private JScrollPane scrollPane;
	private JTable table;

	private List<Venta> ventas;
	private DefaultTableModel modeloTableVenta;
	private DataBase db;

	public static void run(DataBase db) {
		try {
			VentanaHistorialVentas frame = new VentanaHistorialVentas(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaHistorialVentas(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getLblHistorial(), BorderLayout.NORTH);
			panelCentro.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panelCentro;
	}

	private JLabel getLblHistorial() {
		if (lblHistorial == null) {
			lblHistorial = new JLabel("Historia de ventas");
		}
		return lblHistorial;
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
			

			VentaDataBase vdb = new VentaDataBase(db);
			ventas = vdb.getVentas();

			Vector<String> v = new Vector<String>();
			v.add("Id de la venta");
			v.add("Fecha de la venta");
			v.add("Cuantía de la venta");
			
			modeloTableVenta = new DefaultTableModel(v,ventas.size()) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			
			table = new JTable(modeloTableVenta);
			table.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0;i<ventas.size();i++) {
				String id = ventas.get(i).getVenta_Id();
				table.setValueAt(id, i, 0);
				table.setValueAt(ventas.get(i).getFechaEntrega(),i, 1);
				double price = getPrecioVenta(vdb,id);
				table.setValueAt(price,i, 2);
			}
		}
		return table;
	}
	
	private double getPrecioVenta(VentaDataBase vdb, String id) {
		double precio = 0;
		
		List<Producto> productos = vdb.getProductos(id);
		
		for(Producto p: productos) {
			precio += p.getPrice();
		}
		return precio;
	}
}
