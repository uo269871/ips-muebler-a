package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.clientes.ClientesDataBase;
import business.logic.Cliente;

import javax.swing.ListSelectionModel;
/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Clase que representa la ventana donde se crean presupuestos
 */
public class VentanaClientes extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel modeloTableCliente;		
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelBotonCliente;
	private JButton btnGuardar;
	private JScrollPane scrollPaneCliente;
	private JLabel lblTitle;
	private JTable tableCliente;
	private JButton btnNuevoCliente;

	private List<Cliente> clientes;
	
	private VentanaPresupuestos vp;
	private DataBase db;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void run(VentanaPresupuestos vp,DataBase db) {
		try {
			VentanaClientes frame = new VentanaClientes(vp,db);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** Constructor de la ventana Principal de la aplicacion para la venta de comida rápida
	 */
	public VentanaClientes(VentanaPresupuestos vp,DataBase db) {
		this.vp=vp;
		this.db=db;
		cargarClientes();
		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(getPanelNorte(), BorderLayout.NORTH);
		getContentPane().add(getPanelCentro(), BorderLayout.CENTER);
		getContentPane().add(getPanelBotonCliente(), BorderLayout.SOUTH);
		setTitle("Seleccion cliente IPS LAB");
		setBounds(100, 100, 815, 485);
		setLocationRelativeTo(null);
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan el logo el slider y el panel de volumen si no existe
	 * @return el panel
	 */
	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel();
			panelNorte.setForeground(Color.WHITE);
			panelNorte.setBackground(Color.WHITE);
			panelNorte.setLayout(new GridLayout(1, 0, 0, 0));
			panelNorte.add(getLblTitle());
		}
		return panelNorte;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan el panel de la libreria y el de la playlist si no existe
	 * @return el panel
	 */
	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setForeground(Color.WHITE);
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getScrollPaneCliente());
		}
		return panelCentro;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan los botones de add to playlist y delete si no existe
	 * @return el panel
	 */
	private JPanel getPanelBotonCliente() {
		if (panelBotonCliente == null) {
			panelBotonCliente = new JPanel();
			panelBotonCliente.setForeground(Color.WHITE);
			panelBotonCliente.setBackground(Color.WHITE);
			panelBotonCliente.setLayout(new GridLayout(0, 2, 0, 0));
			panelBotonCliente.add(getBtnGuardar());
			panelBotonCliente.add(getBtnNuevoCliente());
		}
		return panelBotonCliente;
	}
	
	/** Método que devuelve o crea y devuelve el botón add to playlist si no existe
	 * @actionPerformed añadir los archivos seleccionados en la lista de la libreria a la playlist
	 * @return el botón
	 */
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getTableCliente().getSelectedRow()>=0) {
						vp.setCliente(clientes.get(getTableCliente().getSelectedRow()));
					}else {
						vp.setCliente(null);
					}
					dispose();
				}
			});
			btnGuardar.setMargin(new Insets(0, 0, 0, 0));
			btnGuardar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnGuardar.setForeground(Color.BLACK);
			btnGuardar.setBackground(Color.LIGHT_GRAY);
		}
		return btnGuardar;
	}
	
	/** Método que devuelve o crea y devuelve el panel de scroll de la libreria
	 * @return el panel de scroll
	 */
	private JScrollPane getScrollPaneCliente() {
		if (scrollPaneCliente == null) {
			scrollPaneCliente = new JScrollPane();
			scrollPaneCliente.setBackground(Color.WHITE);
			scrollPaneCliente.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPaneCliente.setViewportView(getTableCliente());
		}
		return scrollPaneCliente;
	}
	
	/** Método que llama al metodo que crea y  abre la ventana de selecion de archivos del dispositivo y cargar la musica selecionada
	 */
	private void cargarClientes() {
		ClientesDataBase cdb = new ClientesDataBase(db);
		clientes=cdb.getClientes();
	}
	
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Seleccionar cliente");
			lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblTitle;
	}
	private JTable getTableCliente() {
		if (tableCliente == null) {
			int rows= clientes.size();
			Vector<String> v= new Vector<String>();
			v.add("Nombre");
			v.add("DNI");
			v.add("Direccion");
			v.add("Email");
			modeloTableCliente = new DefaultTableModel(v,rows) {

			    /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			tableCliente = new JTable(modeloTableCliente);
			tableCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0;i<clientes.size();i++) {
				tableCliente.setValueAt(clientes.get(i).getName(), i, 0);
				tableCliente.setValueAt(clientes.get(i).getDni(),i, 1);
				tableCliente.setValueAt(clientes.get(i).getAddress(),i, 2);
				tableCliente.setValueAt(clientes.get(i).getEmail(),i, 3);
			}
		}
		return tableCliente;
	}
	private JButton getBtnNuevoCliente() {
		if (btnNuevoCliente == null) {
			btnNuevoCliente = new JButton("Nuevo Cliente");
			btnNuevoCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarVentanaRegistro();
					dispose();
					VentanaClientes.run(vp, db);
				}
			});
			btnNuevoCliente.setMargin(new Insets(0, 0, 0, 0));
			btnNuevoCliente.setForeground(Color.BLACK);
			btnNuevoCliente.setFont(new Font("Dialog", Font.BOLD, 14));
			btnNuevoCliente.setBackground(Color.LIGHT_GRAY);
		}
		return btnNuevoCliente;
	}
	
	private void ejecutarVentanaRegistro() {
		VentanaRegistro.run(this, db);
	}
}
