package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.almacen.AlmacenDataBase;
import business.bbdd.DataBase;
import business.logic.Producto;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAlmacen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JLabel lblAlmacen;
	private JScrollPane scrAlmacen;
	private JLabel lblProductos;
	private JTable tableAlmacen;

	private DefaultTableModel modeloTableAlmacen;
	private List<Producto> productos;
	private DataBase db;
	private JPanel panelBtnsAlmacen;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */

	public void run(DataBase db) {
		try {
			VentanaAlmacen frame = new VentanaAlmacen(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaAlmacen(DataBase db) {
		this.db = db;
		productos = getProductos();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
	}
	
	private List<Producto> getProductos() {
		AlmacenDataBase adb = new AlmacenDataBase(db);
		List<Producto> prs = adb.getProductos();
		return prs;
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setLayout(new BorderLayout(0, 0));
			panelNorte.add(getLblAlmacen());
		}
		return panelNorte;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getScrAlmacen());
			panelCentro.add(getLblProductos(), BorderLayout.NORTH);
			panelCentro.add(getPanelBtnsAlmacen(), BorderLayout.SOUTH);
		}
		return panelCentro;
	}

	private JLabel getLblAlmacen() {
		if (lblAlmacen == null) {
			lblAlmacen = new JLabel("Almac\u00E9n");
			lblAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblAlmacen;
	}

	private JScrollPane getScrAlmacen() {
		if (scrAlmacen == null) {
			scrAlmacen = new JScrollPane();
			scrAlmacen.setBorder(new LineBorder(Color.BLACK, 6));
			scrAlmacen.setBackground(Color.WHITE);
			scrAlmacen.setViewportView(getTableAlmacen());
		}
		return scrAlmacen;
	}

	private JLabel getLblProductos() {
		if (lblProductos == null) {
			lblProductos = new JLabel("Productos en almac\u00E9n");
			lblProductos.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblProductos;
	}

	private JTable getTableAlmacen() {
		if (tableAlmacen == null) {
			Vector<String> v = new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			v.add("Unidades");
			modeloTableAlmacen = new DefaultTableModel(v, productos.size()) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					// all cells false
					return false;
				}
			};
			tableAlmacen = new JTable(modeloTableAlmacen);
			tableAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for (int i = 0; i < productos.size(); i++) {
				tableAlmacen.setValueAt(productos.get(i).getName(), i, 0);
				tableAlmacen.setValueAt(productos.get(i).getType(), i, 1);
				tableAlmacen.setValueAt(productos.get(i).getPrice(), i, 2);
				tableAlmacen.setValueAt(getUnidades(productos.get(i)), i, 3);
			}

		}
		return tableAlmacen;
	}
	
	private int getUnidades(Producto pr) {
		AlmacenDataBase adb = new AlmacenDataBase(db);
		int unidades = adb.getUnidades(pr.getProduct_id());
		return unidades;
	}
	private JPanel getPanelBtnsAlmacen() {
		if (panelBtnsAlmacen == null) {
			panelBtnsAlmacen = new JPanel();
			panelBtnsAlmacen.add(getBtnSalir());
		}
		return panelBtnsAlmacen;
	}
	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnSalir.setMargin(new Insets(0, 0, 0, 0));
			btnSalir.setFont(new Font("Dialog", Font.BOLD, 14));
			btnSalir.setForeground(Color.BLACK);
			btnSalir.setBackground(Color.LIGHT_GRAY);
		}
		return btnSalir;
	}
}
