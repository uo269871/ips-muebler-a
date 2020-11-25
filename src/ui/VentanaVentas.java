package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.almacen.AlmacenDataBase;
import business.bbdd.DataBase;
import business.catalogo.CatalogoDataBase;
import business.clientes.ClientesDataBase;
import business.logic.Pedido;
import business.logic.Presupuesto;
import business.logic.Producto;
import business.logic.Venta;
import business.pedidos.PedidosDataBase;
import business.presupuestos.PresupuestosDataBase;
import business.ventas.VentaDataBase;

public class VentanaVentas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase db;
	private DefaultTableModel modeloTablePresupesto;
	private List<Presupuesto> presupuestos;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnAceptar;

	private Venta v;

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaVentas frame = new VentanaVentas(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaVentas(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 485);
		cargarPresupuestos();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
	}

	private void cargarPresupuestos() {
		PresupuestosDataBase pdb = new PresupuestosDataBase(db);
		presupuestos = pdb.getPresupuestosPendientes();
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

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.add(getBtnAceptar());
		}
		return panelSur;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Ventas");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
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
			ClientesDataBase cdb = new ClientesDataBase(db);
			int rows = presupuestos.size();
			Vector<String> v = new Vector<String>();
			v.add("Nombre del cliente");
			v.add("Fecha de creación");
			modeloTablePresupesto = new DefaultTableModel(v, rows) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table = new JTable(modeloTablePresupesto);
			table.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for (int i = 0; i < presupuestos.size(); i++) {
				String nombre = cdb.getCliente(presupuestos.get(i).getClient_id());
				table.setValueAt(nombre, i, 0);
				Date date = presupuestos.get(i).getFecha_caducidad();
				LocalDate d = date.toLocalDate();
				d = d.minusDays(15);
				table.setValueAt(d.toString(), i, 1);
			}
		}
		return table;
	}

	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar presupuesto");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aceptarPresupuesto();
					ejecutarVentanaTransporte(v);
					dispose();
				}
			});
			btnAceptar.setFont(new Font("Dialog", Font.BOLD, 20));
			btnAceptar.setForeground(Color.BLACK);
			btnAceptar.setBackground(Color.LIGHT_GRAY);
		}
		return btnAceptar;
	}

	private void ejecutarVentanaTransporte(Venta v) {
		VentanaTransporte.run(db, v);
	}

	private void aceptarPresupuesto() {
		PresupuestosDataBase pdb = new PresupuestosDataBase(db);
		VentaDataBase vdb = new VentaDataBase(db);
		String id = UUID.randomUUID().toString();
		int pos = table.getSelectedRow();
		Presupuesto p = presupuestos.get(pos);
		v = new Venta(id,p.getClient_id(), new Date(System.currentTimeMillis()));
		List<Producto> products = pdb.getProductosPresupuesto(p.getPresupuesto_id());
		vdb.addVenta(v, products);
		modificarAlmacen(products,id);
		pdb.eliminarPresupuesto(p.getPresupuesto_id());
	}
	
	private void modificarAlmacen(List<Producto> products, String id) {
		List<Producto> productosAAñadir=new ArrayList<Producto>();
		AlmacenDataBase adb= new AlmacenDataBase(db);
		CatalogoDataBase cdb=new CatalogoDataBase(db);
		PedidosDataBase pdb = new PedidosDataBase(db);
		for(Producto p:products) {
			int uds=adb.getUnidades(p.getProduct_id());
			if(uds-p.getUds()<0) {
				adb.addAlmacenProducto(p, 0);
				int unidadesAPedir=p.getUds()-uds;
				p=cdb.getProductoById(p.getProduct_id());
				p.setUds(unidadesAPedir);
				if(p!=null) {
					productosAAñadir.add(p);
				}
			}else {
				adb.addAlmacenProducto(p, uds-p.getUds());
			}
		}
		if(productosAAñadir.size()>0) {
			int amount=0;
			for(Producto aux:productosAAñadir) {
				amount+=aux.getUds()*aux.getPrice();
			}
			int id_pedido=pdb.getPedidos().size()+1;
			Pedido pedido= new Pedido(id_pedido, amount, "ENCARGADO PARA V:"+id, productosAAñadir);
			pdb.addPedido(pedido);
		}
	}
}
