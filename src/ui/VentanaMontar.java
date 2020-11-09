package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.logic.Producto;
import business.logic.Transporte;
import business.logic.Transportista;
import business.logic.Venta;
import business.transportes.TransportesDataBase;
import business.transportistas.TransportistasDataBase;
import business.ventas.VentaDataBase;

public class VentanaMontar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JLabel lblMontar;
	private JPanel panelCentro;
	private JPanel panelTransporte;
	private JPanel panelMontaje;
	private JScrollPane scrTransporte;
	private JScrollPane scrMontaje;
	private JLabel lblTransporte;
	private JLabel lblMontaje;
	private JTable tableTransporte;
	private JTable tableMontaje;
	private JPanel panelSur;
	private JPanel panelBtnTransporte;
	private JPanel panelBtnMontaje;
	private JButton btnAddMonatje;
	private JButton btnDelMontaje;
	private JPanel panelFecha;
	private JLabel lblDia;
	private JComboBox<Integer> cbDia;
	private JLabel lblMes;
	private JComboBox<Integer> cbMes;
	private JLabel lblAño;
	private JLabel lblHora;
	private JComboBox<Integer> cbHora;
	private JLabel lblMinuto;
	private JComboBox<Integer> cbMinuto;
	private JPanel panelBtnSur;
	private JButton btnSiguiente;
	
	private DataBase db;
	private List<Producto> transportes;
	private List<Producto> montajes;
	private Venta venta;
	private DefaultTableModel modeloTableTransporte;
	private DefaultTableModel modeloTableMontaje;
	private JSpinner spAño;


	/**
	 * Launch the application.
	 */
	public static void run(DataBase db, List<Producto> transportes, Venta venta) {
		try {
			VentanaMontar frame = new VentanaMontar(db, transportes, venta);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaMontar(DataBase db,  List<Producto> transportes, Venta venta) {
		this.db = db;
		this.transportes = transportes;
		this.venta = venta;
		this.montajes = new ArrayList<Producto>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 815, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setLayout(new BorderLayout(0, 0));
			panelNorte.add(getLblMontar());
		}
		return panelNorte;
	}
	private JLabel getLblMontar() {
		if (lblMontar == null) {
			lblMontar = new JLabel("Montaje");
			lblMontar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblMontar;
	}
	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new GridLayout(1, 2, 5, 0));
			panelCentro.add(getPanelTransporte());
			panelCentro.add(getPanelMontaje());
		}
		return panelCentro;
	}
	private JPanel getPanelTransporte() {
		if (panelTransporte == null) {
			panelTransporte = new JPanel();
			panelTransporte.setLayout(new BorderLayout(0, 0));
			panelTransporte.add(getScrTransporte(), BorderLayout.CENTER);
			panelTransporte.add(getLblTransporte(), BorderLayout.NORTH);
			panelTransporte.add(getPanelBtnTransporte(), BorderLayout.SOUTH);
		}
		return panelTransporte;
	}
	private JPanel getPanelMontaje() {
		if (panelMontaje == null) {
			panelMontaje = new JPanel();
			panelMontaje.setLayout(new BorderLayout(0, 0));
			panelMontaje.add(getScrMontaje(), BorderLayout.CENTER);
			panelMontaje.add(getLblMontaje(), BorderLayout.NORTH);
			panelMontaje.add(getPanelBtnMontaje(), BorderLayout.SOUTH);
		}
		return panelMontaje;
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
	private JScrollPane getScrMontaje() {
		if (scrMontaje == null) {
			scrMontaje = new JScrollPane();
			scrMontaje.setBorder(new LineBorder(Color.BLACK, 6));
			scrMontaje.setBackground(Color.WHITE);
			scrMontaje.setViewportView(getTableMontaje());
		}
		return scrMontaje;
	}
	private JLabel getLblTransporte() {
		if (lblTransporte == null) {
			lblTransporte = new JLabel("Productos para entregar");
			lblTransporte.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblTransporte;
	}
	private JLabel getLblMontaje() {
		if (lblMontaje == null) {
			lblMontaje = new JLabel("Productos para montar");
			lblMontaje.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblMontaje;
	}
	private JTable getTableTransporte() {
		if (tableTransporte == null) {
			Vector<String> v = new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			modeloTableTransporte = new DefaultTableModel(v, transportes.size()) {

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
			tableTransporte = new JTable(modeloTableTransporte);
			tableTransporte.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for (int i = 0; i < transportes.size(); i++) {
				tableTransporte.setValueAt(transportes.get(i).getName(), i, 0);
				tableTransporte.setValueAt(transportes.get(i).getType(), i, 1);
				tableTransporte.setValueAt(transportes.get(i).getPrice(), i, 2);
			}
		}
		return tableTransporte;
	}
	private JTable getTableMontaje() {
		if (tableMontaje == null) {
			Vector<String> v= new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			modeloTableMontaje = new DefaultTableModel(v,montajes.size()) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			tableMontaje = new JTable(modeloTableMontaje);
			tableMontaje.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return tableMontaje;
	}
	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new BorderLayout(0, 0));
			panelSur.add(getPanelFecha(), BorderLayout.CENTER);
			panelSur.add(getPanelBtnSur(), BorderLayout.SOUTH);
		}
		return panelSur;
	}
	private JPanel getPanelBtnTransporte() {
		if (panelBtnTransporte == null) {
			panelBtnTransporte = new JPanel();
			panelBtnTransporte.setLayout(new GridLayout(0, 1, 0, 0));
			panelBtnTransporte.add(getBtnAddMonatje());
		}
		return panelBtnTransporte;
	}
	private JPanel getPanelBtnMontaje() {
		if (panelBtnMontaje == null) {
			panelBtnMontaje = new JPanel();
			panelBtnMontaje.setLayout(new GridLayout(0, 1, 0, 0));
			panelBtnMontaje.add(getBtnDelMontaje());
		}
		return panelBtnMontaje;
	}
	private JButton getBtnAddMonatje() {
		if (btnAddMonatje == null) {
			btnAddMonatje = new JButton("Añadir a montaje");
			btnAddMonatje.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<getTableTransporte().getSelectedRows().length;i++) {
						if(!montajes.contains(transportes.get(getTableTransporte().getSelectedRows()[i]))) {
							Vector<String> v=new Vector<String>();
							v.add(transportes.get(getTableTransporte().getSelectedRows()[i]).getName());
							v.add(transportes.get(getTableTransporte().getSelectedRows()[i]).getType());
							v.add(String.valueOf(transportes.get(getTableTransporte().getSelectedRows()[i]).getPrice()));
							modeloTableMontaje.addRow(v);
							montajes.add(transportes.get(getTableTransporte().getSelectedRows()[i]));
							deleteTransporte();
						}
						repaint();
					}
				}
			});
			btnAddMonatje.setMargin(new Insets(0, 0, 0, 0));
			btnAddMonatje.setFont(new Font("Dialog", Font.BOLD, 14));
			btnAddMonatje.setForeground(Color.BLACK);
			btnAddMonatje.setBackground(Color.LIGHT_GRAY);
		}
		return btnAddMonatje;
	}
	
	private void deleteTransporte() {
		for(int i=0;i<tableTransporte.getSelectedRows().length;i++) {
			transportes.remove(tableTransporte.getSelectedRows()[i]);
			modeloTableTransporte.removeRow(tableTransporte.getSelectedRows()[i]);
			i--;
		}
		repaint();
	}
	
	private JButton getBtnDelMontaje() {
		if (btnDelMontaje == null) {
			btnDelMontaje = new JButton("Borrar");
			btnDelMontaje.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteMontaje();
				}
			});
			btnDelMontaje.setMargin(new Insets(0, 0, 0, 0));
			btnDelMontaje.setFont(new Font("Dialog", Font.BOLD, 14));
			btnDelMontaje.setForeground(Color.BLACK);
			btnDelMontaje.setBackground(Color.LIGHT_GRAY);
		}
		return btnDelMontaje;
	}
	
	private void deleteMontaje() {
		for(int i=0;i<tableMontaje.getSelectedRows().length;i++) {
			Vector<String> v=new Vector<String>();
			v.add(montajes.get(getTableMontaje().getSelectedRows()[i]).getName());
			v.add(montajes.get(getTableMontaje().getSelectedRows()[i]).getType());
			v.add(String.valueOf(montajes.get(getTableMontaje().getSelectedRows()[i]).getPrice()));
			modeloTableTransporte.addRow(v);
			transportes.add(montajes.get(getTableMontaje().getSelectedRows()[i]));
			montajes.remove(tableMontaje.getSelectedRows()[i]);
			modeloTableMontaje.removeRow(tableMontaje.getSelectedRows()[i]);
			i--;
		}
		repaint();
	}
	
	private JPanel getPanelFecha() {
		if (panelFecha == null) {
			panelFecha = new JPanel();
			panelFecha.setLayout(new GridLayout(0, 10, 0, 0));
			panelFecha.add(getLblAño());
			panelFecha.add(getSpAño());
			panelFecha.add(getLblMes());		
			panelFecha.add(getCbMes());
			panelFecha.add(getLblDia());
			panelFecha.add(getCbDia());
			panelFecha.add(getLblHora());
			panelFecha.add(getCbHora());
			panelFecha.add(getLblMinuto());
			panelFecha.add(getCbMinuto());
		}
		return panelFecha;
	}
	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("D\u00EDa");
			lblDia.setHorizontalAlignment(SwingConstants.CENTER);
			lblDia.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblDia;
	}
	private JComboBox<Integer> getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox<Integer>();
			cbDia.setModel(new DefaultComboBoxModel<Integer>(getDias()));
		}
		return cbDia;
	}
	
	private Integer[] getDias() {
		if ((Integer)cbMes.getSelectedItem() == 2) {
			Integer[] dias = new Integer[28];
			for (int i = 0; i < dias.length; i++) {
				dias[i] = i+1;
			}
			return dias;
		}
		else if ((Integer)cbMes.getSelectedItem() == 4 || (Integer)cbMes.getSelectedItem() == 6 || (Integer)cbMes.getSelectedItem() == 9 || (Integer)cbMes.getSelectedItem() == 11) {
			Integer[] dias = new Integer[30];
			for (int i = 0; i < dias.length; i++) {
				dias[i] = i+1;
			}
			return dias;
		}
		else {
			Integer[] dias = new Integer[31];
			for (int i = 0; i < dias.length; i++) {
				dias[i] = i+1;
			}
			return dias;
		}
	}
	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes");
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
					cbDia.setModel(new DefaultComboBoxModel<Integer>(getDias()));
				}
			});
			cbMes.setModel(new DefaultComboBoxModel<Integer>(getMeses()));
		}
		return cbMes;
	}
	
	private Integer[] getMeses() {
		Integer[] meses = new Integer[12];
		for (int i = 0; i < meses.length; i++) {
			meses[i] = i+1;
		}
		return meses;
	}
	private JLabel getLblAño() {
		if (lblAño == null) {
			lblAño = new JLabel("A\u00F1o");
			lblAño.setHorizontalAlignment(SwingConstants.CENTER);
			lblAño.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblAño;
	}
	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("Hora");
			lblHora.setHorizontalAlignment(SwingConstants.CENTER);
			lblHora.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblHora;
	}
	private JComboBox<Integer> getCbHora() {
		if (cbHora == null) {
			cbHora = new JComboBox<Integer>();
			cbHora.setModel(new DefaultComboBoxModel<Integer>(getHoras()));
		}
		return cbHora;
	}
	
	private Integer[] getHoras() {
		Integer[] meses = new Integer[24];
		for (int i = 0; i < meses.length; i++) {
			meses[i] = i;
		}
		return meses;
	}
	
	private JLabel getLblMinuto() {
		if (lblMinuto == null) {
			lblMinuto = new JLabel("Minuto");
			lblMinuto.setHorizontalAlignment(SwingConstants.CENTER);
			lblMinuto.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblMinuto;
	}
	private JComboBox<Integer> getCbMinuto() {
		if (cbMinuto == null) {
			cbMinuto = new JComboBox<Integer>();
			cbMinuto.setModel(new DefaultComboBoxModel<Integer>(getMinutos()));
		}
		return cbMinuto;
	}
	
	private Integer[] getMinutos() {
		Integer[] meses = new Integer[60];
		for (int i = 0; i < meses.length; i++) {
			meses[i] = i;
		}
		return meses;
	}
	
	private JPanel getPanelBtnSur() {
		if (panelBtnSur == null) {
			panelBtnSur = new JPanel();
			panelBtnSur.setLayout(new GridLayout(0, 1, 0, 0));
			panelBtnSur.add(getBtnSiguiente());
		}
		return panelBtnSur;
	}
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					Transportista[] transportistas = getTransportistas();
					JFrame frame = new JFrame("Input Dialog Example 3");
					Transportista tr = (Transportista) JOptionPane.showInputDialog(frame, "Seleccione el Transportista", "Transportista", JOptionPane.QUESTION_MESSAGE, null, transportistas, transportistas[0]);
					TransportesDataBase tdb = new TransportesDataBase(db);
					VentaDataBase vdb = new VentaDataBase(db);
					int id = tdb.getNumeroTransportes() + 1;
					Transporte transporte = new Transporte(String.valueOf(id), venta.getVenta_Id(), tr.getDni(), new Date((int)getSpAño().getValue(), (int)cbMes.getSelectedItem(), (int)cbDia.getSelectedItem()), (int)cbHora.getSelectedItem(), (int)cbMinuto.getSelectedItem(), "PENDIENTE");
					tdb.addTransportes(transporte);
					for (Producto p : transportes) {
						vdb.updateTransporteMontaje(p, venta, 1, 0);
					}
					for (Producto p : montajes) {
						vdb.updateTransporteMontaje(p, venta, 1, 1);
					}
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
	
	private Transportista[] getTransportistas() {
		TransportistasDataBase tdb = new TransportistasDataBase(db);
		List<Transportista> trs = tdb.getTranspotista((int)cbHora.getSelectedItem(), (int)cbMinuto.getSelectedItem());
		Transportista[] transportistas = trs.toArray(new Transportista[trs.size()]);
		return transportistas;
	}
	private JSpinner getSpAño() {
		if (spAño == null) {
			spAño = new JSpinner();
			spAño.setModel(new SpinnerNumberModel(Integer.valueOf(2020), 0, null, Integer.valueOf(50)));
		}
		return spAño;
	}
}
