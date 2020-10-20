package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.clientes.ClientesDataBase;
import business.logic.Presupuesto;
import business.logic.Transporte;
import business.logic.Transportista;
import business.logic.Venta;
import business.presupuestos.PresupuestosDataBase;
import business.transportes.TransportesDataBase;
import business.transportistas.TransportistasDataBase;
import business.ventas.VentaDataBase;

public class VentanaVentas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataBase db;
	private Transportista tr;
	private DefaultTableModel modeloTablePresupesto;
	private List<Presupuesto> presupuestos;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnAceptar;
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
			int rows= presupuestos.size();
			Vector<String> v= new Vector<String>();
			v.add("Nombre del cliente");
			v.add("Fecha de creación");
			modeloTablePresupesto = new DefaultTableModel(v,rows) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			table = new JTable(modeloTablePresupesto);
			table.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0;i<presupuestos.size();i++) {
				String nombre = cdb.getCliente(presupuestos.get(i).getClient_id());
				table.setValueAt(nombre, i, 0);
				table.setValueAt(presupuestos.get(i).getFecha_caducidad(),i, 1);
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
					elegirTransporte();
				}
			});	
			btnAceptar.setFont(new Font("Dialog", Font.BOLD, 20));
			btnAceptar.setForeground(Color.BLACK);
			btnAceptar.setBackground(Color.LIGHT_GRAY);
		}
		return btnAceptar;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void elegirTransporte() {
		Integer[] date= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        Integer[] month= {1,2,3,4,5,6,7,8,9,10,11,12};
        Integer[] year={2020,2021,2022};
        Integer[] hour= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        Integer[] minute = new Integer[60];
        for (int i = 0; i < 60; i++) {
        	minute[i] = i;
        }
        JComboBox<Integer> jcd = new JComboBox(date);
        JComboBox<Integer> jcm = new JComboBox(month);
        JComboBox<Integer> jcy = new JComboBox(year);
        JComboBox<Integer> jch = new JComboBox(hour);
        JComboBox<Integer> jcmin = new JComboBox(minute);
        JButton aceptar = new JButton();

        jcd.setEditable(true);
        jcm.setEditable(true);
        jcy.setEditable(true);
        jch.setEditable(true);
        jcmin.setEditable(true);
        
        aceptar.addActionListener(new ActionListener() {
        	@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) { 
        	    elegirTransportista((int)jch.getSelectedItem(),(int)jcmin.getSelectedItem());
        	    TransportesDataBase tdb = new TransportesDataBase(db);
        	    VentaDataBase vdb = new VentaDataBase(db);
        	    tdb.addTransportes(new Transporte(Integer.toString(vdb.getNumeroVentas() + 1), vdb.getUltimaVenta().getVenta_Id(), tr.getDni(), new Date((int)jcy.getSelectedItem(), (int)jcm.getSelectedItem(), (int)jcd.getSelectedItem()), (int)jch.getSelectedItem(),(int)jcmin.getSelectedItem()));
        	    dispose();
        	  } 
        });

        //create a JOptionPane
        Object[] options = new Object[] {};
        JOptionPane jop = new JOptionPane("Please Select",
                                        JOptionPane.QUESTION_MESSAGE,
                                        JOptionPane.DEFAULT_OPTION,
                                        null,options, null);

        //add combos to JOptionPane
        jop.add(jcd);
        jop.add(jcm);
        jop.add(jcy);
        jop.add(jch);
        jop.add(jcmin);
        jop.add(aceptar);

        //create a JDialog and add JOptionPane to it 
        JDialog diag = new JDialog();
        diag.getContentPane().add(jop);
        diag.pack();
        diag.setVisible(true);
		
	}

	protected void elegirTransportista(int hora, int minuto) {
		TransportistasDataBase tdb = new TransportistasDataBase(db);
		JComboBox<Transportista> trans = new JComboBox<Transportista>(new DefaultComboBoxModel<Transportista>(tdb.getTranspotista(hora, minuto)));
		JButton aceptar = new JButton();

		//create a JOptionPane
        Object[] options = new Object[] {};
        JOptionPane jop = new JOptionPane("Please Select",
                                        JOptionPane.QUESTION_MESSAGE,
                                        JOptionPane.DEFAULT_OPTION,
                                        null,options, null);

        //add combos to JOptionPane
        jop.add(trans);
        jop.add(aceptar);

        //create a JDialog and add JOptionPane to it 
        JDialog diag = new JDialog();
        diag.getContentPane().add(jop);
        diag.pack();
        diag.setVisible(true);
        
        aceptar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 
        		tr = (Transportista) trans.getSelectedItem();
        	  } 
        });
	}

	private void aceptarPresupuesto() {
		PresupuestosDataBase pdb = new PresupuestosDataBase(db);
		VentaDataBase vdb = new VentaDataBase(db);
		int id = vdb.getNumeroVentas() + 1;
		Presupuesto p = presupuestos.get(table.getSelectedRow());
		Venta v = new Venta(p.getClient_id(), String.valueOf(id), new Date(System.currentTimeMillis()));
		List<String> products = pdb.getProductosPresupuesto(p.getPresupuesto_id());
		List<Integer> transporte = pdb.getTransportes(p.getPresupuesto_id());
		List<Integer> montaje = pdb.getTransportes(p.getPresupuesto_id());
		vdb.addVenta(v, products,transporte,montaje);
		pdb.eliminarPresupuesto(p.getPresupuesto_id());
	}
}
