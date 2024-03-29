package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import business.bbdd.DataBase;
import business.catalogo.CatalogoDataBase;
import business.logic.Pedido;
import business.logic.Producto;
import business.logic.Venta;
import business.pedidos.PedidosDataBase;
import business.ventas.VentaDataBase;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaBalance extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JLabel lblBalance;
	private JPanel panelSur;
	private JButton btnSalir;
	private JPanel panelCentro;
	private JPanel panelMes;
	private JLabel lblMes;
	private JComboBox<Integer> cbMes;
	private JLabel lblA�o;
	private JSpinner spA�o;
	private ChartPanel panelBalance;

	private DataBase db;

	/**
	 * Launch the application.
	 */

	public static void run(DataBase db) {
		try {
			VentanaBalance frame = new VentanaBalance(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create the frame.
	 */
	public VentanaBalance(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setLayout(new BorderLayout(0, 0));
			panelNorte.add(getLblBalance());
		}
		return panelNorte;
	}

	private JLabel getLblBalance() {
		if (lblBalance == null) {
			lblBalance = new JLabel("Balance");
			lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblBalance;
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new BorderLayout(0, 0));
			panelSur.add(getBtnSalir());
		}
		return panelSur;
	}

	private JButton getBtnSalir() {
		if (btnSalir == null) {
			btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnSalir;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getPanelMes(), BorderLayout.NORTH);
			panelCentro.add(getPanelBalance(), BorderLayout.CENTER);
		}
		return panelCentro;
	}

	private JPanel getPanelMes() {
		if (panelMes == null) {
			panelMes = new JPanel();
			panelMes.setLayout(new GridLayout(0, 4, 0, 0));
			panelMes.add(getLblA�o());
			panelMes.add(getSpA�o());
			panelMes.add(getLblMes());
			panelMes.add(getCbMes());
		}
		return panelMes;
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes: ");
			lblMes.setHorizontalAlignment(SwingConstants.CENTER);
			lblMes.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblMes;
	}

	private JComboBox<Integer> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<Integer>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (panelBalance != null) {
						panelBalance.removeAll();
						panelBalance = new ChartPanel(
								getGrafico((Integer) getSpA�o().getValue(), (Integer) getCbMes().getSelectedItem()));
					}
				}
			});
			cbMes.setModel(new DefaultComboBoxModel<Integer>(getMeses()));
			cbMes.setSelectedItem(LocalDate.now().getMonthValue());
		}
		return cbMes;
	}

	private Integer[] getMeses() {
		Integer[] meses = new Integer[12];
		for (int i = 0; i < meses.length; i++) {
			meses[i] = i + 1;
		}
		return meses;
	}

	private JLabel getLblA�o() {
		if (lblA�o == null) {
			lblA�o = new JLabel("A\u00F1o: ");
			lblA�o.setHorizontalAlignment(SwingConstants.CENTER);
			lblA�o.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblA�o;
	}

	private JSpinner getSpA�o() {
		if (spA�o == null) {
			spA�o = new JSpinner();
			spA�o.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					panelBalance = new ChartPanel(
							getGrafico((Integer) getSpA�o().getValue(), (Integer) getCbMes().getSelectedItem()));
				}
			});
			spA�o.setModel(
					new SpinnerNumberModel(Integer.valueOf(LocalDate.now().getYear()), 0, null, Integer.valueOf(1)));
		}
		return spA�o;
	}

	private ChartPanel getPanelBalance() {
		if (panelBalance == null) {
			panelBalance = new ChartPanel(
					getGrafico((Integer) getSpA�o().getValue(), (Integer) getCbMes().getSelectedItem()));

		}
		return panelBalance;
	}

	private JFreeChart getGrafico(int year, int month) {
		int[] array = new int[3];
		array[0] = getBeneficios(year, month);
		array[1] = getPerdidas();
		array[2] = getBeneficios(year, month) - getPerdidas();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(array[0], "�", "Beneficios");
		dataset.addValue(array[1], "�", "P�rdidas");
		dataset.addValue(array[2], "�", "Total");
		JFreeChart chart = ChartFactory.createBarChart("Balance", null, null, dataset, PlotOrientation.VERTICAL, true,
				true, false);
		return chart;
	}

	private int getBeneficios(int year, int month) {
		int ben = 0;
		VentaDataBase vdb = new VentaDataBase(db);
		CatalogoDataBase cdb = new CatalogoDataBase(db);
		List<Venta> ventas = vdb.getVentas(year, month);
		for (Venta v : ventas) {
			List<Producto> productos = vdb.getProductos(v.getVenta_Id());
			for (Producto p : productos) {
				int uds = vdb.getUnidades(v.getVenta_Id(), p.getProduct_id());
				double precio = cdb.getPrecio(p.getProduct_id());
				ben += uds * precio;
			}
		}

		return ben;
	}

	private int getPerdidas() {
		int per = 0;
		PedidosDataBase pdb = new PedidosDataBase(db);
		List<Pedido> pedidos = pdb.getPedidos();
		for (Pedido p : pedidos) {
			per += p.getTotal_price();
		}

		return per;
	}
}
