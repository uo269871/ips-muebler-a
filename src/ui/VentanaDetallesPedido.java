/**
 * 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.logic.Pedido;
import business.pedidos.PedidosDataBase;
import util.Claves;
/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Clase que representa la ventana donde se visualizan pedidos
 */
public class VentanaDetallesPedido extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel modeloTablePedidos;	
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelPedidos;
	private JPanel panelBtnsPedido;
	private JScrollPane scrollPanePedidos;
	private JLabel lblTitle;
	private JTable tablePedidos;
	private JPanel panelBotones;
	private JPanel panelNortePedidos;
	private JLabel lblPedidos;
	private JTextField txtPedido;
	
	private Pedido pedido;
	
	private DataBase db;
	private String pedido_id;
	
	/**
	 * Launch the application.
	 */
	public static void run(DataBase db,String pedido_id) {
		try {
			VentanaDetallesPedido frame = new VentanaDetallesPedido(db,pedido_id);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** Constructor de la ventana Principal de la aplicacion para la venta de comida rápida
	 */
	public VentanaDetallesPedido(DataBase db, String pedido_id) {
		this.db=db;
		this.pedido_id=pedido_id;
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
		pedido=cdb.getPedidoById(pedido_id);
//		var lista=new ArrayList<Producto>();
//		lista.add(new Producto("mesa alta", "mesa", "001", 10));
//		pedido=new Pedido(1, 10, "RECIBIDO", lista);
	}	
	
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Ver detalles del pedido");
			lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblTitle;
	}
	
	private JTable getTablePedidos() {
		if (tablePedidos == null) {
			Vector<String> v= new Vector<String>();
			v.add("Nombre Producto");
			v.add("Tipo");
			v.add("Precio/Uds");
			v.add("Unidades");
			v.add("Descuento");
			v.add("PrecioTotal");
			modeloTablePedidos = new DefaultTableModel(v,pedido.getProductos().size()) {

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
			tablePedidos.setFont(new Font("Tahoma", Font.PLAIN, 18));
			
			double precio=0;
			int uds=0;
			double des=1;
			
			for(int i =0;i<pedido.getProductos().size();i++) {
				tablePedidos.setValueAt(pedido.getProductos().get(i).getName(), i, 0);
				tablePedidos.setValueAt(pedido.getProductos().get(i).getType(),i, 1);
				uds=pedido.getProductos().get(i).getUds();
				precio=pedido.getProductos().get(i).getPrice();
				tablePedidos.setValueAt(String.format("%.2f", precio),i, 2);
				tablePedidos.setValueAt(uds,i, 3);
				if(uds<10) {
					tablePedidos.setValueAt("0%",i, 4);
					des=1;
				}else if(uds<20) {
					tablePedidos.setValueAt("5%",i, 4);
					des=0.95;
				}else if(uds<50) {
					tablePedidos.setValueAt("10%",i, 4);
					des=0.9;
				}else {
					tablePedidos.setValueAt("20%",i, 4);
					des=0.8;
				}
				tablePedidos.setValueAt(String.format("%.2f", uds*precio*des),i, 5);
			}
		}
		return tablePedidos;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 2, 0, 0));
		}
		return panelBotones;
	}
	
	private JPanel getPanelNortePedidos() {
		if (panelNortePedidos == null) {
			panelNortePedidos = new JPanel();
			panelNortePedidos.setBackground(Color.WHITE);
			panelNortePedidos.setForeground(Color.WHITE);
			panelNortePedidos.setLayout(new GridLayout(0, 2, 0, 0));
			panelNortePedidos.add(getLabelPedidos());
			panelNortePedidos.add(getTxtPedido());
		}
		return panelNortePedidos;
	}
	private JLabel getLabelPedidos() {
		if (lblPedidos == null) {
			lblPedidos = new JLabel("Pedido: ");
			lblPedidos.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPedidos.setForeground(Color.BLACK);
			lblPedidos.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPedidos.setBackground(new Color(0, 0, 0));
		}
		return lblPedidos;
	}
	private JTextField getTxtPedido() {
		if (txtPedido == null) {
			txtPedido = new JTextField();
			txtPedido.setEditable(false);
			txtPedido.setColumns(10);
			txtPedido.setText(Claves.toClave(Integer.parseInt(pedido_id)));
		}
		return txtPedido;
	}
}
