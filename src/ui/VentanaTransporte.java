package ui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.logic.Producto;
import business.logic.Venta;
import business.ventas.VentaDataBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class VentanaTransporte extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JLabel lblTitulo;
	private JButton btnAceptar;
	private DefaultTableModel modeloTableTransporte;
	private DefaultTableModel modeloTableRecoger;

	private DataBase db;
	private Venta venta;
	private List<Producto> productos;
	private JPanel panelRecoger;
	private JPanel panelTransporte;
	private JScrollPane scrRecoger;
	private JLabel lblRecoger;
	private JLabel lblTransporte;
	private JPanel panelBtnRecoger;
	private JPanel panelBtnEntregar;
	private JButton btnAñadirAEntrega;
	private JButton btnSiguiente;
	private JButton btnBorrar;
	private JScrollPane scrTransporte;
	private JTable tableTransporte;
	private JTable tableRecoger;

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
		List<Venta> ventas = vdb.getVentas();
		venta = ventas.get(0);
	}

	private void cargarProductos() {
		VentaDataBase vdb = new VentaDataBase(db);
		productos = vdb.getProductos(venta.getVenta_Id());
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
			panelCentro.setForeground(Color.WHITE);
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new GridLayout(1, 2, 5, 0));
			panelCentro.add(getPanelRecoger());
			panelCentro.add(getPanelTransporte());
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

	private JPanel getPanelRecoger() {
		if (panelRecoger == null) {
			panelRecoger = new JPanel();
			panelRecoger.setLayout(new BorderLayout(0, 0));
			panelRecoger.add(getScrRecoger(), BorderLayout.CENTER);
			panelRecoger.add(getLblRecoger(), BorderLayout.NORTH);
			panelRecoger.add(getPanelBtnRecoger(), BorderLayout.SOUTH);
		}
		return panelRecoger;
	}

	private JPanel getPanelTransporte() {
		if (panelTransporte == null) {
			panelTransporte = new JPanel();
			panelTransporte.setLayout(new BorderLayout(0, 0));
			panelTransporte.add(getLblTransporte(), BorderLayout.NORTH);
			panelTransporte.add(getPanelBtnEntregar(), BorderLayout.SOUTH);
			panelTransporte.add(getScrTransporte(), BorderLayout.CENTER);
		}
		return panelTransporte;
	}

	private JScrollPane getScrRecoger() {
		if (scrRecoger == null) {
			scrRecoger = new JScrollPane();
			scrRecoger.setBorder(new LineBorder(Color.BLACK, 6));
			scrRecoger.setBackground(Color.WHITE);
			scrRecoger.setViewportView(getTableRecoger());
		}
		return scrRecoger;
	}

	private JLabel getLblRecoger() {
		if (lblRecoger == null) {
			lblRecoger = new JLabel("Productos a recoger");
			lblRecoger.setFont(new Font("Dialog", Font.BOLD, 14));
			lblRecoger.setBackground(Color.BLACK);
		}
		return lblRecoger;
	}

	private JLabel getLblTransporte() {
		if (lblTransporte == null) {
			lblTransporte = new JLabel("Productos para entregar");
			lblTransporte.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblTransporte;
	}

	private JPanel getPanelBtnRecoger() {
		if (panelBtnRecoger == null) {
			panelBtnRecoger = new JPanel();
			panelBtnRecoger.add(getBtnAñadirAEntrega());
		}
		return panelBtnRecoger;
	}

	private JPanel getPanelBtnEntregar() {
		if (panelBtnEntregar == null) {
			panelBtnEntregar = new JPanel();
			panelBtnEntregar.add(getBtnBorrar());
			panelBtnEntregar.add(getBtnSiguiente());
		}
		return panelBtnEntregar;
	}

	private JButton getBtnAñadirAEntrega() {
		if (btnAñadirAEntrega == null) {
			btnAñadirAEntrega = new JButton("A\u00F1adir a entrega");
			btnAñadirAEntrega.setBackground(Color.LIGHT_GRAY);
			btnAñadirAEntrega.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return btnAñadirAEntrega;
	}

	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return btnSiguiente;
	}

	private JButton getBtnBorrar() {
		if (btnBorrar == null) {
			btnBorrar = new JButton("Borrar");
			btnBorrar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnBorrar.setBackground(Color.LIGHT_GRAY);
		}
		return btnBorrar;
	}

	private JScrollPane getScrTransporte() {
		if (scrTransporte == null) {
			scrTransporte = new JScrollPane();
			scrTransporte.setBorder(new LineBorder(Color.BLACK, 6));
			scrTransporte.setBackground(Color.WHITE);
			scrTransporte.setViewportView(getTableTransporte());
		}
		return scrTransporte;
	}

	private JTable getTableTransporte() {
		if (tableTransporte == null) {
			tableTransporte = new JTable();
		}
		return tableTransporte;
	}

	private JTable getTableRecoger() {
		if (tableRecoger == null) {
			Vector<String> v = new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			modeloTableRecoger = new DefaultTableModel(v, productos.size()) {

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
			tableRecoger = new JTable(modeloTableRecoger);
			tableRecoger.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for (int i = 0; i < productos.size(); i++) {
				tableRecoger.setValueAt(productos.get(i).getName(), i, 0);
				tableRecoger.setValueAt(productos.get(i).getType(), i, 1);
				tableRecoger.setValueAt(productos.get(i).getPrice(), i, 2);
			}
		}
		return tableRecoger;
	}
}
