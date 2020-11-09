package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class VentanaTransporte extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JLabel lblTitulo;
	private DefaultTableModel modeloTableTransporte;
	private DefaultTableModel modeloTableRecoger;

	private DataBase db;
	private Venta venta;
	private List<Producto> productos;
	private List<Producto> transportes;
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
	public static void run(DataBase db, Venta venta) {
		try {
			VentanaTransporte frame = new VentanaTransporte(db, venta);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaTransporte(DataBase db, Venta venta) {
		this.db = db;
		this.venta = venta;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 485);
		cargarProductos();
		transportes = new ArrayList<Producto>();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);

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
			btnAñadirAEntrega.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<getTableRecoger().getSelectedRows().length;i++) {
						if(!transportes.contains(productos.get(getTableRecoger().getSelectedRows()[i]))) {
							Vector<String> v=new Vector<String>();
							v.add(productos.get(getTableRecoger().getSelectedRows()[i]).getName());
							v.add(productos.get(getTableRecoger().getSelectedRows()[i]).getType());
							v.add(String.valueOf(productos.get(getTableRecoger().getSelectedRows()[i]).getPrice()));
							modeloTableTransporte.addRow(v);
							transportes.add(productos.get(getTableRecoger().getSelectedRows()[i]));
							deleteRecoger();
						}
						repaint();
					}
				}
			});
			btnAñadirAEntrega.setMargin(new Insets(0, 0, 0, 0));
			btnAñadirAEntrega.setFont(new Font("Dialog", Font.BOLD, 14));
			btnAñadirAEntrega.setForeground(Color.BLACK);
			btnAñadirAEntrega.setBackground(Color.LIGHT_GRAY);
		}
		return btnAñadirAEntrega;
	}
	
	private void deleteRecoger() {
		for(int i=0;i<tableRecoger.getSelectedRows().length;i++) {
			productos.remove(tableRecoger.getSelectedRows()[i]);
			modeloTableRecoger.removeRow(tableRecoger.getSelectedRows()[i]);
			i--;
		}
		repaint();
	}

	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarVentanaMontar();
					dispose();
				}
			});
			btnSiguiente.setMargin(new Insets(0, 0, 0, 0));
			btnSiguiente.setFont(new Font("Dialog", Font.BOLD, 14));
			btnSiguiente.setForeground(Color.BLACK);
			btnSiguiente.setBackground(Color.LIGHT_GRAY);
		}
		return btnSiguiente;
	}
	
	private void ejecutarVentanaMontar() {
		VentanaMontar.run(db,transportes,venta);
	}

	private JButton getBtnBorrar() {
		if (btnBorrar == null) {
			btnBorrar = new JButton("Borrar");
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteTransporte();
				}
			});
			btnBorrar.setMargin(new Insets(0, 0, 0, 0));
			btnBorrar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnBorrar.setForeground(Color.BLACK);
			btnBorrar.setBackground(Color.LIGHT_GRAY);
		}
		return btnBorrar;
	}
	
	private void deleteTransporte() {
		for(int i=0;i<tableTransporte.getSelectedRows().length;i++) {
			Vector<String> v=new Vector<String>();
			v.add(transportes.get(getTableTransporte().getSelectedRows()[i]).getName());
			v.add(transportes.get(getTableTransporte().getSelectedRows()[i]).getType());
			v.add(String.valueOf(transportes.get(getTableTransporte().getSelectedRows()[i]).getPrice()));
			modeloTableRecoger.addRow(v);
			productos.add(transportes.get(getTableTransporte().getSelectedRows()[i]));
			transportes.remove(tableTransporte.getSelectedRows()[i]);
			modeloTableTransporte.removeRow(tableTransporte.getSelectedRows()[i]);	
			i--;
		}
		repaint();
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
			Vector<String> v= new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			modeloTableTransporte = new DefaultTableModel(v,transportes.size()) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			tableTransporte = new JTable(modeloTableTransporte);
			tableTransporte.setFont(new Font("Tahoma", Font.PLAIN, 18));
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
