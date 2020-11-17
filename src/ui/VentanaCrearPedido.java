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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.almacen.AlmacenDataBase;
import business.bbdd.DataBase;
import business.logic.Pedido;
import business.logic.Producto;
import business.pedidos.PedidosDataBase;
/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Clase que representa la ventana donde se crean presupuestos
 */
public class VentanaCrearPedido extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel modeloTableAlmacen;	
	private DefaultTableModel modeloTablePedido;	
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelAlmacen;
	private JLabel lblAlmacen;
	private JPanel panelPedido;
	private JPanel panelBotonAlmacen;
	private JButton btnAddToPedido;
	private JPanel panelBtnsPedido;
	private JButton btnGuardar;
	private JButton btnBorrar;
	private JScrollPane scrollPaneAlmacen;
	private JScrollPane scrollPanePedido;
	private JLabel lblTitle;
	private JTable tableAlmacen;
	private JTable tablePedido;
	private JPanel panelBotones;
	private JPanel panelNortePedido;
	private JLabel lblPedido;
	private JTextField txtTotal;
	
	private List<Producto> pedido;
	private List<Producto> almacen;

	
	private DataBase db;
	
	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaCrearPedido frame = new VentanaCrearPedido(db);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** Constructor de la ventana Principal de la aplicacion para la venta de comida rápida
	 */
	public VentanaCrearPedido(DataBase db) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.db=db;
		this.pedido= new ArrayList<Producto>();
		cargarAlmacen();
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
			panelNorte.add(getTxtTotal());
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
			panelCentro.add(getPanelCatalogo());
			panelCentro.add(getPanelPresupuesto());
		}
		return panelCentro;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan el panel de scroll de la libreria, sus botones 
	 * y la etiqueta identificadora si no existe
	 * @return el panel
	 */
	private JPanel getPanelCatalogo() {
		if (panelAlmacen == null) {
			panelAlmacen = new JPanel();
			panelAlmacen.setForeground(Color.WHITE);
			panelAlmacen.setBackground(Color.WHITE);
			panelAlmacen.setLayout(new BorderLayout(0, 0));
			panelAlmacen.add(getLblAlmacen(), BorderLayout.NORTH);
			panelAlmacen.add(getPanelBotonCatalogo(), BorderLayout.SOUTH);
			panelAlmacen.add(getScrollPaneCatalogo(), BorderLayout.CENTER);
		}
		return panelAlmacen;
	}
	
	/** Método que devuelve o crea y devuelve la etiqueta identificadora de la libreria si no existe
	 * @return la etiqueta
	 */
	private JLabel getLblAlmacen() {
		if (lblAlmacen == null) {
			lblAlmacen = new JLabel("Productos en el almacen");
			lblAlmacen.setFont(new Font("Dialog", Font.BOLD, 14));
			lblAlmacen.setForeground(Color.BLACK);
			lblAlmacen.setBackground(new Color(0, 0, 0));
		}
		return lblAlmacen;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan el panel de scroll de la playlist, sus botones 
	 * y la etiqueta identificadora si no existe
	 * @return el panel
	 */
	private JPanel getPanelPresupuesto() {
		if (panelPedido == null) {
			panelPedido = new JPanel();
			panelPedido.setForeground(Color.WHITE);
			panelPedido.setBackground(Color.WHITE);
			panelPedido.setLayout(new BorderLayout(0, 0));
			panelPedido.add(getPanelNortePresupuesto(), BorderLayout.NORTH);
			panelPedido.add(getPanelBtnsPedido(), BorderLayout.SOUTH);
			panelPedido.add(getScrollPanePlay(), BorderLayout.CENTER);
		}
		return panelPedido;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan los botones de add to playlist y delete si no existe
	 * @return el panel
	 */
	private JPanel getPanelBotonCatalogo() {
		if (panelBotonAlmacen == null) {
			panelBotonAlmacen = new JPanel();
			panelBotonAlmacen.setForeground(Color.WHITE);
			panelBotonAlmacen.setBackground(Color.WHITE);
			panelBotonAlmacen.setLayout(new GridLayout(1, 1, 0, 0));
			panelBotonAlmacen.add(getBtnAddToPresupuesto());
		}
		return panelBotonAlmacen;
	}
	
	/** Método que devuelve o crea y devuelve el botón add to playlist si no existe
	 * @actionPerformed añadir los archivos seleccionados en la lista de la libreria a la playlist
	 * @return el botón
	 */
	private JButton getBtnAddToPresupuesto() {
		if (btnAddToPedido == null) {
			btnAddToPedido = new JButton("A\u00F1adir al pedido");
			btnAddToPedido.setMnemonic('t');
			btnAddToPedido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<getTableAlmacen().getSelectedRows().length;i++)
						if(!pedido.contains(almacen.get(getTableAlmacen().getSelectedRows()[i]))) {
							Vector<String> v= new Vector<String>();
							v.add(almacen.get(getTableAlmacen().getSelectedRows()[i]).getProduct_id());
							v.add(almacen.get(getTableAlmacen().getSelectedRows()[i]).getName());
							v.add(almacen.get(getTableAlmacen().getSelectedRows()[i]).getType());
							v.add(String.valueOf(1));
							v.add(String.valueOf(almacen.get(getTableAlmacen().getSelectedRows()[i]).getPrice()));
							modeloTablePedido.addRow(v);
//							tablePresupuesto.setValueAt("NO", modeloTablePresupesto.getRowCount() - 1, 3);
//							tablePresupuesto.setValueAt("NO", modeloTablePresupesto.getRowCount() - 1, 4);
							pedido.add(new Producto(almacen.get(getTableAlmacen().getSelectedRows()[i]),1));
						}else {
							for(int j=0;j<modeloTablePedido.getRowCount();j++) {
								if(almacen.get(getTableAlmacen().getSelectedRows()[i]).getName().equals(modeloTablePedido.getValueAt(j, 1))) {
									Vector<String> v= new Vector<String>();
									v.add(almacen.get(getTableAlmacen().getSelectedRows()[i]).getProduct_id());
									v.add(almacen.get(getTableAlmacen().getSelectedRows()[i]).getName());
									v.add(almacen.get(getTableAlmacen().getSelectedRows()[i]).getType());
									int uds=Integer.parseInt(modeloTablePedido.getValueAt(j, 3).toString())+1;
									v.add(uds+"");
									v.add(String.valueOf(almacen.get(getTableAlmacen().getSelectedRows()[i]).getPrice()));
									modeloTablePedido.removeRow(j);
									modeloTablePedido.addRow(v);
									Producto pr=new Producto(almacen.get(getTableAlmacen().getSelectedRows()[i]),uds);
									pedido.remove(pr);
									pedido.add(pr);
									break;
								}
							}
						}
					actualizaPrecio();
					changeEnableSave();
					repaint();
				}
			});
			btnAddToPedido.setMargin(new Insets(0, 0, 0, 0));
			btnAddToPedido.setFont(new Font("Dialog", Font.BOLD, 14));
			btnAddToPedido.setForeground(Color.BLACK);
			btnAddToPedido.setBackground(Color.LIGHT_GRAY);
		}
		return btnAddToPedido;
	}
	
	private float actualizaPrecio() {
		float price=0;
		for(Producto aux:pedido) {
			price+=aux.getPrice()*aux.getUds();
		}
		if(price!=0) {
			getTxtTotal().setText("Total: "+price);
		}else {
			getTxtTotal().setText("Total: ");
		}
		return price;
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

	/** Método que elimina la/las cancion/canciones selecionadas de la playlist
	 */
	private void delete() {
		for(int i=0;i<tablePedido.getSelectedRows().length;i++) {
			pedido.remove(tablePedido.getSelectedRows()[i]);
			modeloTablePedido.removeRow(tablePedido.getSelectedRows()[i]);
			i--;
		}
		repaint();
	}
	
	/** Método que devuelve o crea y devuelve el botón de avanzar si no existe
	 * @actionPerformed reproduce la siguiente cancion de la que se esta reproduciendo
	 * @return el botón
	 */
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.setEnabled(false);
			btnGuardar.setMnemonic('f');
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarPedido();
				}
			});
			btnGuardar.setMargin(new Insets(0, 0, 0, 0));
			btnGuardar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnGuardar.setForeground(Color.BLACK);
			btnGuardar.setBackground(Color.LIGHT_GRAY);
		}
		return btnGuardar;
	}
	
	private void guardarPedido() {
		PedidosDataBase pdb=new PedidosDataBase(db);
		Pedido pedido= new Pedido(pdb.getPedidos().size()+1,actualizaPrecio(),"SOLICITADO",this.pedido);
		pdb.addPedido(pedido);
	}

	/** Método que devuelve o crea y devuelve el botón de borrar si no existe
	 * @actionPerformed elimina la/las cancion/canciones selecionadas de la playlist
	 * @return el botón
	 */
	private JButton getBtnBorrar() {
		if (btnBorrar == null) {
			btnBorrar = new JButton("Borrar");
			btnBorrar.setMnemonic('b');
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					delete();
					actualizaPrecio();
					changeEnableSave();
				}
			});
			btnBorrar.setMargin(new Insets(0, 0, 0, 0));
			btnBorrar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnBorrar.setForeground(Color.BLACK);
			btnBorrar.setBackground(Color.LIGHT_GRAY);
		}
		return btnBorrar;
	}
	
	/** Método que devuelve o crea y devuelve el panel de scroll de la libreria
	 * @return el panel de scroll
	 */
	private JScrollPane getScrollPaneCatalogo() {
		if (scrollPaneAlmacen == null) {
			scrollPaneAlmacen = new JScrollPane();
			scrollPaneAlmacen.setBackground(Color.WHITE);
			scrollPaneAlmacen.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPaneAlmacen.setViewportView(getTableAlmacen());
		}
		return scrollPaneAlmacen;
	}
	
	/** Método que devuelve o crea y devuelve el panel de scroll de la playlist
	 * @return el panel de scroll
	 */
	private JScrollPane getScrollPanePlay() {
		if (scrollPanePedido == null) {
			scrollPanePedido = new JScrollPane();
			scrollPanePedido.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPanePedido.setBackground(Color.WHITE);
			scrollPanePedido.setViewportView(getTablePedido());
		}
		return scrollPanePedido;
	}
	
	/** Método que llama al metodo que crea y  abre la ventana de selecion de archivos del dispositivo y cargar la musica selecionada
	 */
	private void cargarAlmacen() {
		AlmacenDataBase cdb = new AlmacenDataBase(db);
		almacen=cdb.getProductos();
		for(Producto pr:almacen) {
			pr.setUds(cdb.getUnidades(pr.getProduct_id()));
		}
	}	
	
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Crear un nuevo pedido");
			lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lblTitle;
	}
	private JTextField getTxtTotal() {
		if (txtTotal == null) {
			txtTotal = new JTextField();
			txtTotal.setBackground(Color.WHITE);
			txtTotal.setEditable(false);
			txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 30));
			txtTotal.setText("Total: ");
			txtTotal.setColumns(10);
		}
		return txtTotal;
	}
	private JTable getTableAlmacen() {
		if (tableAlmacen == null) {
			int rows= almacen.size();
			Vector<String> v= new Vector<String>();
			v.add("Id");
			v.add("Nombre");
			v.add("Tipo");
			v.add("Uds");
			modeloTableAlmacen = new DefaultTableModel(v,rows) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			tableAlmacen = new JTable(modeloTableAlmacen);
			tableAlmacen.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0;i<almacen.size();i++) {
				tableAlmacen.setValueAt(almacen.get(i).getProduct_id(),i, 0);
				tableAlmacen.setValueAt(almacen.get(i).getName(), i, 1);
				tableAlmacen.setValueAt(almacen.get(i).getType(),i, 2);
				tableAlmacen.setValueAt(almacen.get(i).getUds(),i, 3);
			}
		}
		return tableAlmacen;
	}
	
	private JTable getTablePedido() {
		if (tablePedido == null) {
			Vector<String> v= new Vector<String>();
			v.add("Id");
			v.add("Nombre");
			v.add("Tipo");
			v.add("Unidades");
			v.add("Precio/Unidad");
			modeloTablePedido = new DefaultTableModel(v,pedido.size()) {

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
			tablePedido = new JTable(modeloTablePedido);
			tablePedido.setFont(new Font("Tahoma", Font.PLAIN, 18));
			
		}
		return tablePedido;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 2, 0, 0));
			panelBotones.add(getBtnBorrar());
			panelBotones.add(getBtnGuardar());
		}
		return panelBotones;
	}
	
	private JPanel getPanelNortePresupuesto() {
		if (panelNortePedido == null) {
			panelNortePedido = new JPanel();
			panelNortePedido.setBackground(Color.WHITE);
			panelNortePedido.setForeground(Color.WHITE);
			panelNortePedido.setLayout(new GridLayout(0, 2, 0, 0));
			panelNortePedido.add(getLabelPedido());
		}
		return panelNortePedido;
	}
	private JLabel getLabelPedido() {
		if (lblPedido == null) {
			lblPedido = new JLabel("Productos a pedir");
			lblPedido.setForeground(Color.BLACK);
			lblPedido.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPedido.setBackground(new Color(0, 0, 0));
		}
		return lblPedido;
	}
	
	private void changeEnableSave() {
		if(pedido.size()==0) {
			getBtnGuardar().setEnabled(false);
		}else if(pedido.size()>0) {
			getBtnGuardar().setEnabled(true);
		}
	}
}
