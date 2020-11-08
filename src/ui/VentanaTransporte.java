package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
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

public class VentanaTransporte extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JLabel lblTitulo;
	private JScrollPane scrTransporte;
	private JPanel panelBtn;
	private JTable tableTransporte;
	private JButton btnAceptar;
	private DefaultTableModel modeloTableTransporte;
	
	private DataBase db;
	private Venta venta;
	private List<Producto> productos;

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaTransporte frame = new VentanaTransporte(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaTransporte(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 485);
		cargarVenta();
		cargarProductos();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
	}
	
	private void cargarVenta() {
		VentaDataBase vdb = new VentaDataBase(db);
		venta = vdb.getUltimaVenta();
	}
	
	private void cargarProductos() {
		VentaDataBase vdb = new VentaDataBase(db);
		productos = vdb.getProductos(venta.getVenta_Id());
		for (Producto p : productos) {
			Vector<String> v= new Vector<String>();
			v.add(p.getName());
			v.add(p.getType());
			v.add(String.valueOf(p.getPrice()));
			modeloTableTransporte.addRow(v);
		}
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setLayout(new BorderLayout(0, 0));
			panelNorte.add(getLblTitulo());
		}
		return panelNorte;
	}
	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getScrTransporte(), BorderLayout.CENTER);
			panelCentro.add(getPanelBtn(), BorderLayout.SOUTH);
		}
		return panelCentro;
	}
	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Transporte");
			lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblTitulo;
	}
	private JScrollPane getScrTransporte() {
		if (scrTransporte == null) {
			scrTransporte = new JScrollPane();
			scrTransporte.setViewportView(getTableTransporte());
		}
		return scrTransporte;
	}
	private JPanel getPanelBtn() {
		if (panelBtn == null) {
			panelBtn = new JPanel();
			panelBtn.add(getBtnAceptar());
		}
		return panelBtn;
	}
	private JTable getTableTransporte() {
		if (tableTransporte == null) {
			Vector<String> v= new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			v.add("Transporte");
			v.add("Montar");
			modeloTableTransporte = new DefaultTableModel(v,productos.size()) {

			    /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
					if (column == 3)
						return true;
					else
				   return false;
			    }
			};
			tableTransporte = new JTable(modeloTableTransporte);
			tableTransporte.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0;i<productos.size();i++) {
				tableTransporte.setValueAt(productos.get(i).getName(), i, 0);
				tableTransporte.setValueAt(productos.get(i).getType(),i, 1);
				tableTransporte.setValueAt(productos.get(i).getPrice(),i, 2);
			}
			
		}
		return tableTransporte;
	}
	
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
		}
		return btnAceptar;
	}
}
