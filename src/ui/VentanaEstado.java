package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import business.bbdd.DataBase;
import business.clientes.ClientesDataBase;
import business.logic.Cliente;
import business.logic.Transporte;
import business.logic.Venta;
import business.transportes.TransportesDataBase;
import business.ventas.VentaDataBase;
import util.Correo;

public class VentanaEstado extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelNorte;
	private JLabel lblEstado;
	private JPanel panelCentro;
	private JPanel panelEstado;
	private JLabel lblActual;
	private JTextField txEstado;
	private JPanel panelFecha;
	private JLabel lblFecha;
	private JLabel lblAño;
	private JSpinner spAño;
	private JLabel lblMes;
	private JComboBox<Integer> cbMes;
	private JLabel lblDia;
	private JComboBox<Integer> cbDia;

	private DataBase db;
	private Transporte transporte;
	private JPanel panelSur;
	private JButton btnSalir;
	private JButton btnCambiarEstado;
	private JPanel panelTransporte;
	private JLabel Transporte;
	private JComboBox<Transporte> cbTransporte;
	
	private Correo correo = new Correo();

	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaEstado frame = new VentanaEstado(db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public VentanaEstado(DataBase db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 675, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelNorte(), BorderLayout.NORTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
		checkEstado();
		
	}
	
	private Cliente getCliente() {
		VentaDataBase vdb = new VentaDataBase(db);
		ClientesDataBase cdb = new ClientesDataBase(db);
		String id = "";
		List<Venta> ventas = vdb.getVentas();
		List<Cliente> clientes = cdb.getClientes();
		for (Venta v : ventas) {
			if (transporte.getId_venta() == v.getVenta_Id())
				id = v.getClient_Id();
		}
		if (id == "")
			JOptionPane.showMessageDialog(null, "Error no hay cliente");
		else {
			for (Cliente c : clientes) {
				if (c.getClient_id() == id)
					return c;
			}
		}
		return null;
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setLayout(new BorderLayout(0, 0));
			panelNorte.add(getLblEstado(), BorderLayout.NORTH);
		}
		return panelNorte;
	}

	private JLabel getLblEstado() {
		if (lblEstado == null) {
			lblEstado = new JLabel("Estado");
			lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblEstado;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new GridLayout(0, 1, 0, 0));
			panelCentro.add(getPanelTransporte());
			panelCentro.add(getPanelEstado());
			panelCentro.add(getPanelFecha());
		}
		return panelCentro;
	}

	private JPanel getPanelEstado() {
		if (panelEstado == null) {
			panelEstado = new JPanel();
			panelEstado.setLayout(new GridLayout(0, 2, 0, 0));
			panelEstado.add(getLblActual());
			panelEstado.add(getTxEstado());
		}
		return panelEstado;
	}

	private JLabel getLblActual() {
		if (lblActual == null) {
			lblActual = new JLabel("Estado actual");
			lblActual.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblActual;
	}

	private JTextField getTxEstado() {
		if (txEstado == null) {
			txEstado = new JTextField();
			txEstado.setEnabled(false);
			txEstado.setFont(new Font("Dialog", Font.BOLD, 14));
			txEstado.setColumns(10);
			txEstado.setText(transporte.getEstado());
		}
		return txEstado;
	}

	private JPanel getPanelFecha() {
		if (panelFecha == null) {
			panelFecha = new JPanel();
			panelFecha.setLayout(null);
			panelFecha.add(getLblFecha());
			panelFecha.add(getLblAño());
			panelFecha.add(getSpAño());
			panelFecha.add(getLblMes());
			panelFecha.add(getCbMes());
			panelFecha.add(getLblDia());
			panelFecha.add(getCbDia());
		}
		return panelFecha;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha entrega:");
			lblFecha.setBounds(2, 0, 113, 29);
			lblFecha.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblFecha;
	}

	private JLabel getLblAño() {
		if (lblAño == null) {
			lblAño = new JLabel("A\u00F1o");
			lblAño.setBounds(111, 0, 75, 29);
			lblAño.setHorizontalAlignment(SwingConstants.CENTER);
			lblAño.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblAño;
	}

	@SuppressWarnings("deprecation")
	private JSpinner getSpAño() {
		if (spAño == null) {
			spAño = new JSpinner();
			spAño.setEnabled(false);
			spAño.setBounds(186, 0, 92, 29);
			spAño.setModel(new SpinnerNumberModel(Integer.valueOf(2020), 0, null, Integer.valueOf(1)));
			spAño.setValue(transporte.getDia_entrega().getYear());
		}
		return spAño;
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes");
			lblMes.setBounds(278, 0, 92, 29);
			lblMes.setHorizontalAlignment(SwingConstants.CENTER);
			lblMes.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblMes;
	}

	@SuppressWarnings("deprecation")
	private JComboBox<Integer> getCbMes() {
		if (cbMes == null) {
			cbMes = new JComboBox<Integer>();
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cbDia != null)
						cbDia.setModel(new DefaultComboBoxModel<Integer>(getDias()));
				}
			});
			cbMes.setEnabled(false);
			cbMes.setBounds(370, 0, 92, 29);
			cbMes.setModel(new DefaultComboBoxModel<Integer>(getMeses()));
			cbMes.setSelectedItem(transporte.getDia_entrega().getMonth());
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

	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("D\u00EDa");
			lblDia.setBounds(462, 0, 92, 29);
			lblDia.setHorizontalAlignment(SwingConstants.CENTER);
			lblDia.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return lblDia;
	}

	@SuppressWarnings("deprecation")
	private JComboBox<Integer> getCbDia() {
		if (cbDia == null) {
			cbDia = new JComboBox<Integer>();
			cbDia.setEnabled(false);
			cbDia.setBounds(554, 0, 92, 29);
			cbDia.setModel(new DefaultComboBoxModel<Integer>(getDias()));
			cbDia.setSelectedItem(transporte.getDia_entrega().getDate());
		}
		return cbDia;
	}

	private Integer[] getDias() {
		if ((Integer) cbMes.getSelectedItem() == 2) {
			Integer[] dias = new Integer[28];
			for (int i = 0; i < dias.length; i++) {
				dias[i] = i + 1;
			}
			return dias;
		} else if ((Integer) cbMes.getSelectedItem() == 4 || (Integer) cbMes.getSelectedItem() == 6
				|| (Integer) cbMes.getSelectedItem() == 9 || (Integer) cbMes.getSelectedItem() == 11) {
			Integer[] dias = new Integer[30];
			for (int i = 0; i < dias.length; i++) {
				dias[i] = i + 1;
			}
			return dias;
		} else {
			Integer[] dias = new Integer[31];
			for (int i = 0; i < dias.length; i++) {
				dias[i] = i + 1;
			}
			return dias;
		}
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setLayout(new GridLayout(0, 2, 0, 0));
			panelSur.add(getBtnSalir());
			panelSur.add(getBtnCambiarEstado());
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
			btnSalir.setFont(new Font("Dialog", Font.BOLD, 14));
			btnSalir.setForeground(Color.BLACK);
			btnSalir.setBackground(Color.LIGHT_GRAY);
		}
		return btnSalir;
	}

	private JButton getBtnCambiarEstado() {
		if (btnCambiarEstado == null) {
			btnCambiarEstado = new JButton("Cambiar Estado");
			btnCambiarEstado.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					if (transporte.getEstado().equals("PENDIENTE")) {
						transporte.setEstado("EN TRANSITO");
						txEstado.setText(transporte.getEstado());
						TransportesDataBase tdb = new TransportesDataBase(db);
						tdb.updateEstado(transporte, transporte.getEstado());
						correo.sendEmail(getCliente().getEmail(), new Date((int) spAño.getValue(), (int) cbMes.getSelectedItem(),
								(int) cbDia.getSelectedItem()));
					} else if (transporte.getEstado().equals("EN TRANSITO")) {
						transporte.setEstado("ENTREGADO");
						txEstado.setText(transporte.getEstado());
						TransportesDataBase tdb = new TransportesDataBase(db);
						tdb.updateEstado(transporte, transporte.getEstado());
					} else if (transporte.getEstado().equals("RETRASADO")) {
						transporte.setEstado("PENDIENTE");
						txEstado.setText(transporte.getEstado());
						transporte.setDia_entrega(new Date((int) spAño.getValue(), (int) cbMes.getSelectedItem(),
								(int) cbDia.getSelectedItem()));
						spAño.setEnabled(false);
						cbDia.setEnabled(false);
						cbMes.setEnabled(false);
						TransportesDataBase tdb = new TransportesDataBase(db);
						tdb.updateEstado(transporte, transporte.getEstado());
						tdb.updateFecha(transporte, transporte.getDia_entrega());
					}
				}
			});
			btnCambiarEstado.setFont(new Font("Dialog", Font.BOLD, 14));
			btnCambiarEstado.setForeground(Color.BLACK);
			btnCambiarEstado.setBackground(Color.LIGHT_GRAY);
		}
		return btnCambiarEstado;
	}

	private JPanel getPanelTransporte() {
		if (panelTransporte == null) {
			panelTransporte = new JPanel();
			panelTransporte.setLayout(new GridLayout(0, 2, 0, 0));
			panelTransporte.add(getTransporte());
			panelTransporte.add(getCbTransporte());
		}
		return panelTransporte;
	}

	private JLabel getTransporte() {
		if (Transporte == null) {
			Transporte = new JLabel("Transporte");
			Transporte.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return Transporte;
	}

	private JComboBox<Transporte> getCbTransporte() {
		if (cbTransporte == null) {
			cbTransporte = new JComboBox<Transporte>();
			cbTransporte.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					transporte = (Transporte) cbTransporte.getSelectedItem();
					if (spAño != null && cbMes != null && cbDia != null) {
						spAño.setValue(transporte.getDia_entrega().getYear());
						cbMes.setSelectedItem(transporte.getDia_entrega().getMonth());
						cbDia.setSelectedItem(transporte.getDia_entrega().getDate());
						txEstado.setText(transporte.getEstado());
					}
				}
			});
			cbTransporte.setModel(new DefaultComboBoxModel<Transporte>(getTransportes()));
			cbTransporte.setSelectedIndex(0);
			transporte = (Transporte) cbTransporte.getSelectedItem();
		}
		return cbTransporte;
	}

	private Transporte[] getTransportes() {
		TransportesDataBase tdb = new TransportesDataBase(db);
		List<Transporte> trs = tdb.getTransportes();
		Transporte[] transportes = trs.toArray(new Transporte[trs.size()]);
		return transportes;
	}

	private void checkEstado() {
		if (LocalDate.now().isAfter(
				LocalDate.of((int) spAño.getValue(), (int) cbMes.getSelectedItem(), (int) cbDia.getSelectedItem()))) {
			transporte.setEstado("RETRASADO");
			txEstado.setText(transporte.getEstado());
			JOptionPane.showConfirmDialog(getParent(), "¡Transporte retrasado!\nSeleccione una nueva fecha de entrega");
			spAño.setEnabled(true);
			cbDia.setEnabled(true);
			cbMes.setEnabled(true);
			TransportesDataBase tdb = new TransportesDataBase(db);
			tdb.updateEstado(transporte, transporte.getEstado());
		}
	}

	
	 
}
