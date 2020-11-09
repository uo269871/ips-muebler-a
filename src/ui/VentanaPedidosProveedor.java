/**
 * 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.logic.Pedido;
import business.logic.Producto;
import business.pedidos.PedidosDataBase;
import util.Claves;

import javax.swing.ListSelectionModel;
/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Clase que representa la ventana donde se visualizan pedidos
 */
public class VentanaPedidosProveedor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel modeloTablePedidos;	
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelPedidos;
	private JPanel panelBtnsPedido;
	private JButton btnEstado;
	private JButton btnDetalles;
	private JScrollPane scrollPanePedidos;
	private JLabel lblTitle;
	private JTable tablePedidos;
	private JPanel panelBotones;
	private JPanel panelNortePedidos;
	private JLabel lblPedidos;
	
	private List<Pedido> pedidos;
	
	private DataBase db;
	
	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaPedidosProveedor frame = new VentanaPedidosProveedor(db);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** Constructor de la ventana Principal de la aplicacion para la venta de comida rápida
	 */
	public VentanaPedidosProveedor(DataBase db) {
		this.db=db;
		pedidos=new ArrayList<Pedido>();
		cargarPedidos();
		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(getPanelNorte(), BorderLayout.NORTH);
		getContentPane().add(getPanelCentro(), BorderLayout.CENTER);
		setTitle("IPS LAB");
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
			panelCentro.setLayout(new GridLayout(1, 2, 5, 0));
			panelCentro.add(getPanelPedidos());
		}
		return panelCentro;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan el panel de scroll de la playlist, sus botones 
	 * y la etiqueta identificadora si no existe
	 * @return el panel
	 */
	private JPanel getPanelPedidos() {
		if (panelPedidos == null) {
			panelPedidos = new JPanel();
			panelPedidos.setForeground(Color.WHITE);
			panelPedidos.setBackground(Color.WHITE);
			panelPedidos.setLayout(new BorderLayout(0, 0));
			panelPedidos.add(getPanelNortePedidos(), BorderLayout.NORTH);
			panelPedidos.add(getPanelBtnsPedido(), BorderLayout.SOUTH);
			panelPedidos.add(getScrollPanePlay(), BorderLayout.CENTER);
		}
		return panelPedidos;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan los numerosos botones que interactuan con la playlist si no existe
	 * @return el panel
	 */
	private JPanel getPanelBtnsPedido() {
		if (panelBtnsPedido == null) {
			panelBtnsPedido = new JPanel();
			panelBtnsPedido.setBackground(new Color(0, 0, 0));
			panelBtnsPedido.setLayout(new GridLayout(1, 1, 0, 0));
			panelBtnsPedido.add(getPanelBotones());
		}
		return panelBtnsPedido;
	}


	/** Método que devuelve o crea y devuelve el botón de borrar si no existe
	 * @actionPerformed elimina la/las cancion/canciones selecionadas de la playlist
	 * @return el botón
	 */
	private JButton getBtnEstado() {
		if (btnEstado == null) {
			btnEstado = new JButton("Cambiar Estado");
			btnEstado.setMnemonic('b');
			btnEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getTablePedidos().getSelectedRow()==-1) {
						return;
					}
					if(getTablePedidos().getValueAt(getTablePedidos().getSelectedRow(), 1).toString().equals("SOLICITADO")) {
						getTablePedidos().setValueAt("RECIBIDO", getTablePedidos().getSelectedRow(), 1);
						PedidosDataBase cdb = new PedidosDataBase(db);
						cdb.update(getTablePedidos().getValueAt(getTablePedidos().getSelectedRow(), 0).toString());
					}
				}
			});
			btnEstado.setMargin(new Insets(0, 0, 0, 0));
			btnEstado.setFont(new Font("Dialog", Font.BOLD, 14));
			btnEstado.setForeground(Color.BLACK);
			btnEstado.setBackground(Color.LIGHT_GRAY);
		}
		return btnEstado;
	}
	
	/** Método que devuelve o crea y devuelve el panel de scroll de la playlist
	 * @return el panel de scroll
	 */
	private JScrollPane getScrollPanePlay() {
		if (scrollPanePedidos == null) {
			scrollPanePedidos = new JScrollPane();
			scrollPanePedidos.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPanePedidos.setBackground(Color.WHITE);
			scrollPanePedidos.setViewportView(getTablePedidos());
		}
		return scrollPanePedidos;
	}
	
	/** Método que llama al metodo que crea y  abre la ventana de selecion de archivos del dispositivo y cargar la musica selecionada
	 */
	private void cargarPedidos() {
		PedidosDataBase cdb = new PedidosDataBase(db);
		pedidos=cdb.getPedidos();
//		var lista=new ArrayList<Producto>();
//		lista.add(new Producto("mesa alta", "mesa", "001", 10));
//		pedidos.add(new Pedido(1, 10, "SOLICITADO", lista));
	}	
	
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Historial pedidos proveedor");
			lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblTitle;
	}
	
	private JTable getTablePedidos() {
		if (tablePedidos == null) {
			Vector<String> v= new Vector<String>();
			v.add("ID Pedido");
			v.add("Estado");
			v.add("Precio");
			modeloTablePedidos = new DefaultTableModel(v,pedidos.size()) {

			    /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
					if (column == 3)
						return true;
					else
				   return false;
			    }
			};
			tablePedidos = new JTable(modeloTablePedidos);
			tablePedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablePedidos.setFont(new Font("Tahoma", Font.PLAIN, 18));
			
			for(int i =0;i<pedidos.size();i++) {
				tablePedidos.setValueAt(Claves.toClave(pedidos.get(i).getPedido_id()), i, 0);
				tablePedidos.setValueAt(pedidos.get(i).getEstado(),i, 1);
				tablePedidos.setValueAt(pedidos.get(i).getTotal_price(),i, 2);
			}
		}
		return tablePedidos;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 2, 0, 0));
			panelBotones.add(getBtnEstado());
			panelBotones.add(getBtnDetalles());
		}
		return panelBotones;
	}


	private Component getBtnDetalles() {
		if (btnDetalles == null) {
			btnDetalles = new JButton("Ver Detalles");
			btnDetalles.setMnemonic('b');
			btnDetalles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarVentanaDetalles();
				}
			});
			btnDetalles.setMargin(new Insets(0, 0, 0, 0));
			btnDetalles.setFont(new Font("Dialog", Font.BOLD, 14));
			btnDetalles.setForeground(Color.BLACK);
			btnDetalles.setBackground(Color.LIGHT_GRAY);
		}
		return btnDetalles;
	}

	private void ejecutarVentanaDetalles() {
		if(getTablePedidos().getSelectedRow()==-1) {
			return;
		}
		String pedido_id=getTablePedidos().getValueAt(getTablePedidos().getSelectedRow(), 0).toString();
		VentanaDetallesPedido.run(db,pedido_id);
	}
	
	private JPanel getPanelNortePedidos() {
		if (panelNortePedidos == null) {
			panelNortePedidos = new JPanel();
			panelNortePedidos.setBackground(Color.WHITE);
			panelNortePedidos.setForeground(Color.WHITE);
			panelNortePedidos.setLayout(new GridLayout(0, 2, 0, 0));
			panelNortePedidos.add(getLabelPedidos());
		}
		return panelNortePedidos;
	}
	private JLabel getLabelPedidos() {
		if (lblPedidos == null) {
			lblPedidos = new JLabel("Listado de los pedidos:");
			lblPedidos.setForeground(Color.BLACK);
			lblPedidos.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPedidos.setBackground(new Color(0, 0, 0));
		}
		return lblPedidos;
	}
}
