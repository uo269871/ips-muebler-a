package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import business.logic.Producto;
import business.logic.Venta;
import business.ventas.VentaDataBase;

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
	private JLabel lblAño;
	private JSpinner spAño;
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
			panelMes.add(getLblAño());
			panelMes.add(getSpAño());
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
				}
			});
			cbMes.setModel(new DefaultComboBoxModel<Integer>(getMeses()));
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

	private JLabel getLblAño() {
		if (lblAño == null) {
			lblAño = new JLabel("A\u00F1o: ");
			lblAño.setHorizontalAlignment(SwingConstants.CENTER);
			lblAño.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblAño;
	}

	private JSpinner getSpAño() {
		if (spAño == null) {
			spAño = new JSpinner();
			spAño.setModel(new SpinnerNumberModel(Integer.valueOf(2020), 0, null, Integer.valueOf(1)));
		}
		return spAño;
	}

	private ChartPanel getPanelBalance() {
		if (panelBalance == null) {
			panelBalance = new ChartPanel(getGrafico());

		}
		return panelBalance;
	}

	private JFreeChart getGrafico() {
		int[] array = new int[3];
		array[0] = getBeneficios();
		array[1] = 6;
		array[2] = 7;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String serie = "Numeros";
		for (int i = 0; i < array.length; i++)
			dataset.addValue(array[i], serie, "" + i);
		JFreeChart chart = ChartFactory.createBarChart("Repeticion de randoms", null, null, dataset,
				PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}
	
	private int getBeneficios() {
		int ben = 0;
		VentaDataBase vdb = new VentaDataBase(db);
		CatalogoDataBase cdb = new CatalogoDataBase(db);
		List<Venta> ventas = vdb.getVentas();
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
}
