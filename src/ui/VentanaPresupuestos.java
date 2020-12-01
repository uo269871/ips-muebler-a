package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.catalogo.CatalogoDataBase;
import business.logic.Cliente;
import business.logic.Presupuesto;
import business.logic.Producto;
import business.presupuestos.PresupuestosDataBase;
/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Clase que representa la ventana donde se crean presupuestos
 */
public class VentanaPresupuestos extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel modeloTableCatalogo;	
	private DefaultTableModel modeloTablePresupesto;	
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelCatalogo;
	private JLabel lblCatalogo;
	private JPanel panelPresupuesto;
	private JPanel panelBotonCatalogo;
	private JButton btnAddToPresupuesto;
	private JPanel panelBtnsPresupuesto;
	private JButton btnGuardar;
	private JButton btnBorrar;
	private JButton btnCliente;
	private JScrollPane scrollPaneCatalogo;
	private JScrollPane scrollPanePresupuesto;
	private JLabel lblTitle;
	private JTable tableCatalogo;
	private JTable tablePresupuesto;
	private JPanel panelBotones;
	private JPanel panelNortePresupuesto;
	private JLabel lblPresupuesto;
	private JLabel lblCliente;
	private JTextField txtTotal;
	private JButton btnFilterType;
	private JButton btnFilterPrice;
	
	private List<Producto> presupuesto;
	private List<Producto> catalogo;
	private Cliente cliente;
	private boolean filtroTipos = false;
	private boolean filtroPrecios = false;
	private List<Producto> catalogoPrecios;
	private List<Producto> catalogoTipos;
	private String[] tipos;

	
	private DataBase db;
	private JButton btnCargarPlantillas;
	
	/**
	 * Launch the application.
	 */
	public static void run(DataBase db) {
		try {
			VentanaPresupuestos frame = new VentanaPresupuestos(db);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** Constructor de la ventana Principal de la aplicacion para la venta de comida rápida
	 */
	public VentanaPresupuestos(DataBase db) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.db=db;
		this.presupuesto= new ArrayList<Producto>();
		cargarCatalogo();
		cliente= null;
		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(getPanelNorte(), BorderLayout.NORTH);
		getContentPane().add(getPanelCentro(), BorderLayout.CENTER);
		setTitle("IPS LAB");
		setBounds(100, 100, 815, 485);
		setLocationRelativeTo(null);
		cargarTipos();
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
		if (panelCatalogo == null) {
			panelCatalogo = new JPanel();
			panelCatalogo.setForeground(Color.WHITE);
			panelCatalogo.setBackground(Color.WHITE);
			panelCatalogo.setLayout(new BorderLayout(0, 0));
			panelCatalogo.add(getLblCatalogo(), BorderLayout.NORTH);
			panelCatalogo.add(getPanelBotonCatalogo(), BorderLayout.SOUTH);
			panelCatalogo.add(getScrollPaneCatalogo(), BorderLayout.CENTER);
		}
		return panelCatalogo;
	}
	
	/** Método que devuelve o crea y devuelve la etiqueta identificadora de la libreria si no existe
	 * @return la etiqueta
	 */
	private JLabel getLblCatalogo() {
		if (lblCatalogo == null) {
			lblCatalogo = new JLabel("Catalogo completo");
			lblCatalogo.setFont(new Font("Dialog", Font.BOLD, 14));
			lblCatalogo.setForeground(Color.BLACK);
			lblCatalogo.setBackground(new Color(0, 0, 0));
		}
		return lblCatalogo;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan el panel de scroll de la playlist, sus botones 
	 * y la etiqueta identificadora si no existe
	 * @return el panel
	 */
	private JPanel getPanelPresupuesto() {
		if (panelPresupuesto == null) {
			panelPresupuesto = new JPanel();
			panelPresupuesto.setForeground(Color.WHITE);
			panelPresupuesto.setBackground(Color.WHITE);
			panelPresupuesto.setLayout(new BorderLayout(0, 0));
			panelPresupuesto.add(getPanelNortePresupuesto(), BorderLayout.NORTH);
			panelPresupuesto.add(getPanelBtnsPresupuesto(), BorderLayout.SOUTH);
			panelPresupuesto.add(getScrollPanePlay(), BorderLayout.CENTER);
		}
		return panelPresupuesto;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan los botones de add to playlist y delete si no existe
	 * @return el panel
	 */
	private JPanel getPanelBotonCatalogo() {
		if (panelBotonCatalogo == null) {
			panelBotonCatalogo = new JPanel();
			panelBotonCatalogo.setForeground(Color.WHITE);
			panelBotonCatalogo.setBackground(Color.WHITE);
			panelBotonCatalogo.setLayout(new GridLayout(4, 1, 0, 0));
			panelBotonCatalogo.add(getBtnCargarPlantillas());
			panelBotonCatalogo.add(getBtnAddToPresupuesto());
			panelBotonCatalogo.add(getBtnFilterPrice());
			panelBotonCatalogo.add(getBtnFilterType());
		}
		return panelBotonCatalogo;
	}
	
	/** Método que devuelve o crea y devuelve el botón add to playlist si no existe
	 * @actionPerformed añadir los archivos seleccionados en la lista de la libreria a la playlist
	 * @return el botón
	 */
	private JButton getBtnAddToPresupuesto() {
		if (btnAddToPresupuesto == null) {
			btnAddToPresupuesto = new JButton("A\u00F1adir al presupuesto");
			btnAddToPresupuesto.setMnemonic('t');
			btnAddToPresupuesto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<getTableCatalogo().getSelectedRows().length;i++) {
						String parse=JOptionPane.showInputDialog(getContentPane(), "¿Cuantas Uds desearía añadir?","Error en el formato",JOptionPane.QUESTION_MESSAGE);
						int udsToAdd=0;
						try {
							if(parse!=null) {
								udsToAdd = Integer.parseInt(parse);
							}else {
								return;
							}
						}catch (Exception ex){
							JOptionPane.showMessageDialog(getContentPane(), "El formato de las unidades a añadir no es el correcto, por favor intentelo otra vez","Error en el formato",JOptionPane.WARNING_MESSAGE);
							break;
						}
						if(!presupuesto.contains(catalogo.get(getTableCatalogo().getSelectedRows()[i]))) {
							Vector<String> v= new Vector<String>();
							v.add(catalogo.get(getTableCatalogo().getSelectedRows()[i]).getName());
							v.add(catalogo.get(getTableCatalogo().getSelectedRows()[i]).getType());
							v.add(String.format("%.2f", catalogo.get(getTableCatalogo().getSelectedRows()[i]).getPrice()));
							v.add(String.valueOf(udsToAdd));
							modeloTablePresupesto.addRow(v);
//							tablePresupuesto.setValueAt("NO", modeloTablePresupesto.getRowCount() - 1, 3);
//							tablePresupuesto.setValueAt("NO", modeloTablePresupesto.getRowCount() - 1, 4);
							presupuesto.add(new Producto(catalogo.get(getTableCatalogo().getSelectedRows()[i]),udsToAdd));
						}else {
							for(int j=0;j<modeloTablePresupesto.getRowCount();j++) {
								if(catalogo.get(getTableCatalogo().getSelectedRows()[i]).getName().equals(modeloTablePresupesto.getValueAt(j, 0))) {
									Vector<String> v= new Vector<String>();
									v.add(catalogo.get(getTableCatalogo().getSelectedRows()[i]).getName());
									v.add(catalogo.get(getTableCatalogo().getSelectedRows()[i]).getType());
									v.add(String.format("%.2f", catalogo.get(getTableCatalogo().getSelectedRows()[i]).getPrice()));
									int uds=Integer.parseInt(modeloTablePresupesto.getValueAt(j, 3).toString())+udsToAdd;
									v.add(uds+"");
									modeloTablePresupesto.removeRow(j);
									modeloTablePresupesto.addRow(v);
									Producto pr=new Producto(catalogo.get(getTableCatalogo().getSelectedRows()[i]),uds);
									presupuesto.remove(pr);
									presupuesto.add(pr);
									break;
								}
							}
						}
					}
					actualizaPrecio();
					changeEnableSave();
					repaint();
				}
			});
			btnAddToPresupuesto.setMargin(new Insets(0, 0, 0, 0));
			btnAddToPresupuesto.setFont(new Font("Dialog", Font.BOLD, 14));
			btnAddToPresupuesto.setForeground(Color.BLACK);
			btnAddToPresupuesto.setBackground(Color.LIGHT_GRAY);
		}
		return btnAddToPresupuesto;
	}
	
	void setPresupuesto(List<Producto> pre){
		presupuesto=new ArrayList<Producto>();
		modeloTablePresupesto.getDataVector().removeAllElements();
		modeloTablePresupesto.fireTableDataChanged();
		CatalogoDataBase cdb= new CatalogoDataBase(db);
		for(int i =0;i<pre.size();i++) {
			Producto pr=cdb.getProductoById(pre.get(i).getProduct_id());
			pr.setUds(pre.get(i).getUds());
			Vector<String> v= new Vector<String>();
			v.add(pr.getName());
			v.add(pr.getType());
			v.add(pr.getPrice()+"");
			v.add(pr.getUds()+"");
			modeloTablePresupesto.addRow(v);
			presupuesto.add(pr);
		}
		actualizaPrecio();
		changeEnableSave();
		repaint();
	}
	
	private void actualizaPrecio() {
		float price=0;
		for(Producto aux:presupuesto) {
			price+=aux.getPrice()*aux.getUds();
		}
		if(price!=0) {
			getTxtTotal().setText("Total: "+String.format("%.2f", price));
		}else {
			getTxtTotal().setText("Total: ");
		}
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan los numerosos botones que interactuan con la playlist si no existe
	 * @return el panel
	 */
	private JPanel getPanelBtnsPresupuesto() {
		if (panelBtnsPresupuesto == null) {
			panelBtnsPresupuesto = new JPanel();
			panelBtnsPresupuesto.setBackground(new Color(0, 0, 0));
			panelBtnsPresupuesto.setLayout(new GridLayout(1, 1, 0, 0));
			panelBtnsPresupuesto.add(getPanelBotones());
		}
		return panelBtnsPresupuesto;
	}

	/** Método que elimina la/las cancion/canciones selecionadas de la playlist
	 */
	private void delete() {
		for(int i=0;i<tablePresupuesto.getSelectedRows().length;i++) {
			presupuesto.remove(tablePresupuesto.getSelectedRows()[i]);
			modeloTablePresupesto.removeRow(tablePresupuesto.getSelectedRows()[i]);
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
					next();
				}
			});
			btnGuardar.setMargin(new Insets(0, 0, 0, 0));
			btnGuardar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnGuardar.setForeground(Color.BLACK);
			btnGuardar.setBackground(Color.LIGHT_GRAY);
		}
		return btnGuardar;
	}
	
	private void next() {
		guardarPresupuesto();
		dispose();
	}
	
	private void guardarPresupuesto() {
		PresupuestosDataBase pdb = new PresupuestosDataBase(db);
//		List<Presupuesto> presupuestos = pdb.getPresupuestos();
//		int id=1;
//		if(presupuestos.size()>0) {
//			id = Integer.parseInt(presupuestos.get(presupuestos.size()-1).getPresupuesto_id());
//			id++;
//		}
		if(cliente!=null) {
			pdb.addPresupuesto(new Presupuesto(new Date(System.currentTimeMillis() + 1296000000), UUID.randomUUID().toString(), cliente.getClient_id()),presupuesto);
		}else {
			pdb.addPresupuesto(new Presupuesto(new Date(System.currentTimeMillis() + 1296000000), UUID.randomUUID().toString(), null),presupuesto);
		}
		//Eliminar del almacen las uds
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
		if (scrollPaneCatalogo == null) {
			scrollPaneCatalogo = new JScrollPane();
			scrollPaneCatalogo.setBackground(Color.WHITE);
			scrollPaneCatalogo.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPaneCatalogo.setViewportView(getTableCatalogo());
		}
		return scrollPaneCatalogo;
	}
	
	/** Método que devuelve o crea y devuelve el panel de scroll de la playlist
	 * @return el panel de scroll
	 */
	private JScrollPane getScrollPanePlay() {
		if (scrollPanePresupuesto == null) {
			scrollPanePresupuesto = new JScrollPane();
			scrollPanePresupuesto.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPanePresupuesto.setBackground(Color.WHITE);
			scrollPanePresupuesto.setViewportView(getTablePresupuesto());
		}
		return scrollPanePresupuesto;
	}
	
	/** Método que llama al metodo que crea y  abre la ventana de selecion de archivos del dispositivo y cargar la musica selecionada
	 */
	private void cargarCatalogo() {
		CatalogoDataBase cdb = new CatalogoDataBase(db);
		catalogo=cdb.getProductos();
	}	
	
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Crear un nuevo presupuesto");
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
	private JTable getTableCatalogo() {
		if (tableCatalogo == null) {
			int rows= catalogo.size();
			Vector<String> v= new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			modeloTableCatalogo = new DefaultTableModel(v,rows) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			tableCatalogo = new JTable(modeloTableCatalogo);
			tableCatalogo.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0;i<catalogo.size();i++) {
				tableCatalogo.setValueAt(catalogo.get(i).getName(), i, 0);
				tableCatalogo.setValueAt(catalogo.get(i).getType(),i, 1);
				tableCatalogo.setValueAt(String.format("%.2f", catalogo.get(i).getPrice()),i, 2);
			}
		}
		return tableCatalogo;
	}
	
	private JTable getTablePresupuesto() {
		if (tablePresupuesto == null) {
			Vector<String> v= new Vector<String>();
			v.add("Nombre");
			v.add("Tipo");
			v.add("Precio");
			v.add("Unidades");
			modeloTablePresupesto = new DefaultTableModel(v,presupuesto.size()) {

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
			tablePresupuesto = new JTable(modeloTablePresupesto);
			tablePresupuesto.setFont(new Font("Tahoma", Font.PLAIN, 18));
			
		}
		return tablePresupuesto;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 3, 0, 0));
			panelBotones.add(getBtnBorrar());
			panelBotones.add(getBtnCliente());
			panelBotones.add(getBtnGuardar());
		}
		return panelBotones;
	}


	private Component getBtnCliente() {
		if (btnCliente == null) {
			btnCliente = new JButton("Asignar Cliente");
			btnCliente.setMnemonic('b');
			btnCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarVentanaClientes();
				}
			});
			btnCliente.setMargin(new Insets(0, 0, 0, 0));
			btnCliente.setFont(new Font("Dialog", Font.BOLD, 14));
			btnCliente.setForeground(Color.BLACK);
			btnCliente.setBackground(Color.LIGHT_GRAY);
		}
		return btnCliente;
	}

	private void ejecutarVentanaClientes() {
		VentanaClientes.run(this, db);
	}
	
	private JPanel getPanelNortePresupuesto() {
		if (panelNortePresupuesto == null) {
			panelNortePresupuesto = new JPanel();
			panelNortePresupuesto.setBackground(Color.WHITE);
			panelNortePresupuesto.setForeground(Color.WHITE);
			panelNortePresupuesto.setLayout(new GridLayout(0, 2, 0, 0));
			panelNortePresupuesto.add(getLabelPresupuesto());
			panelNortePresupuesto.add(getLblCliente());
		}
		return panelNortePresupuesto;
	}
	private JLabel getLabelPresupuesto() {
		if (lblPresupuesto == null) {
			lblPresupuesto = new JLabel("Nuevo presupuesto para:");
			lblPresupuesto.setForeground(Color.BLACK);
			lblPresupuesto.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPresupuesto.setBackground(new Color(0, 0, 0));
		}
		return lblPresupuesto;
	}
	private JLabel getLblCliente() {
		if (lblCliente == null) {
			if(cliente==null) {
				lblCliente = new JLabel("Sin asignar");
			}else {
				lblCliente = new JLabel(cliente.getName());
			}
			lblCliente.setForeground(Color.BLACK);
			lblCliente.setFont(new Font("Dialog", Font.BOLD, 14));
			lblCliente.setBackground(new Color(0, 0, 0));
		}
		return lblCliente;
	}
	
	public void setCliente(Cliente cliente) {
		if(cliente==null) {
			this.cliente=null;
			getLblCliente().setText("Sin asignar");
		}else{
			this.cliente=cliente;
			getLblCliente().setText(cliente.getName());
		}
		
	}
	
	private void changeEnableSave() {
		if(presupuesto.size()==0) {
			getBtnGuardar().setEnabled(false);
		}else if(presupuesto.size()>0) {
			getBtnGuardar().setEnabled(true);
		}
	}
	private JButton getBtnFilterType() {
		if (btnFilterType == null) {
			btnFilterType = new JButton("Filtrar por tipo");
			btnFilterType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!filtroTipos) {
						filtrarTipos();
					} else {
						quitarFiltroTipos();
					}
				}
			});
			btnFilterType.setFont(new Font("Dialog", Font.BOLD, 14));
			btnFilterType.setForeground(Color.BLACK);
			btnFilterType.setBackground(Color.LIGHT_GRAY);
		}
		return btnFilterType;
	}

	private void filtrarTipos() {
		Object a = JOptionPane.showInputDialog(null, "Elija tipo de mueble", "Mueblería", JOptionPane.QUESTION_MESSAGE,
				null, tipos, tipos[0]);

		try {
			CatalogoDataBase cdb = new CatalogoDataBase(db);
			catalogoTipos = cdb.getProductosFiltroTipos(a.toString());
			borrarTablaCatalogo();
			if (filtroPrecios) {
				for (int i = 0; i < catalogoTipos.size(); i++) {
					if (checkTipoEnPrecio(catalogoTipos.get(i))) {
						Vector<String> v = new Vector<String>();
						v.add(catalogoTipos.get(i).getName());
						v.add(catalogoTipos.get(i).getType());
						v.add(String.valueOf(catalogoTipos.get(i).getPrice()));
						modeloTableCatalogo.addRow(v);
					}
				}
			} else {
				for (int i = 0; i < catalogoTipos.size(); i++) {
					Vector<String> v = new Vector<String>();
					v.add(catalogoTipos.get(i).getName());
					v.add(catalogoTipos.get(i).getType());
					v.add(String.valueOf(catalogoTipos.get(i).getPrice()));
					modeloTableCatalogo.addRow(v);
				}
			}

			filtroTipos = true;
			getBtnFilterType().setText("Quitar filtro de tipo");
		} catch (Exception e) {

		}

	}

	private boolean checkTipoEnPrecio(Producto p) {
		for (Producto aux : catalogoPrecios) {
			if (p.equals(aux))
				return true;
		}
		return false;
	}

	private void filtrarPrecios() {
		CatalogoDataBase cdb = new CatalogoDataBase(db);
		try {
			String parse=JOptionPane.showInputDialog(null, "Ponga el precio mínimo");
			float min=0;
			try {
				if(parse!=null) {
					min = Float.parseFloat(parse);
				}else {
					return;
				}
			}catch (Exception ex){
				JOptionPane.showMessageDialog(getContentPane(), "El formato del precio minimo no es el correcto, por favor intentelo otra vez","Error en el formato",JOptionPane.WARNING_MESSAGE);
				return;
			}
			float max = 0;
			parse=JOptionPane.showInputDialog(null, "Ponga el precio máximo");
			try {
				if(parse!=null) {
					max = Float.parseFloat(parse);
				}else {
					return;
				}
			}catch (Exception ex){
				JOptionPane.showMessageDialog(getContentPane(), "El formato del precio maximo no es el correcto, por favor intentelo otra vez","Error en el formato",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(max < min) {
				JOptionPane.showMessageDialog(getContentPane(), "El precio maximo es menor que el precio minimo, por favor intentelo otra vez","Error en el formato",JOptionPane.WARNING_MESSAGE);
				return;
			}

			catalogoPrecios = cdb.getProductosFiltroPrecios(min, max);

			borrarTablaCatalogo();

			if (filtroTipos) {
				for (int i = 0; i < catalogoPrecios.size(); i++) {
					if (checkPrecioEnTipo(catalogoPrecios.get(i))) {
						Vector<String> v = new Vector<String>();
						v.add(catalogoPrecios.get(i).getName());
						v.add(catalogoPrecios.get(i).getType());
						v.add(String.valueOf(catalogoPrecios.get(i).getPrice()));
						modeloTableCatalogo.addRow(v);
					}
				}
			} else {
				for (int i = 0; i < catalogoPrecios.size(); i++) {
					Vector<String> v = new Vector<String>();
					v.add(catalogoPrecios.get(i).getName());
					v.add(catalogoPrecios.get(i).getType());
					v.add(String.valueOf(catalogoPrecios.get(i).getPrice()));
					modeloTableCatalogo.addRow(v);
				}
			}

			getBtnFilterPrice().setText("Quitar filtro de precio");
			filtroPrecios = true;
		} catch (Exception e) {

		}

	}

	private boolean checkPrecioEnTipo(Producto p) {
		for (Producto aux : catalogoTipos) {
			if (p.equals(aux))
				return true;
		}
		return false;
	}

	private JButton getBtnFilterPrice() {
		if (btnFilterPrice == null) {
			btnFilterPrice = new JButton("Filtrar por precio");
			btnFilterPrice.setFont(new Font("Dialog", Font.BOLD, 14));
			btnFilterPrice.setForeground(Color.BLACK);
			btnFilterPrice.setBackground(Color.LIGHT_GRAY);
			btnFilterPrice.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!filtroPrecios) {
						filtrarPrecios();
					} else {
						quitarFiltroPrecios();
					}

				}
			});
		}

		return btnFilterPrice;
	}

	private void quitarFiltroPrecios() {
		borrarTablaCatalogo();

		if (filtroTipos) {
			for (int i = 0; i < catalogoPrecios.size(); i++) {
				Vector<String> v = new Vector<String>();
				v.add(catalogoTipos.get(i).getName());
				v.add(catalogoTipos.get(i).getType());
				v.add(String.valueOf(catalogoTipos.get(i).getPrice()));
				modeloTableCatalogo.addRow(v);
			}
		} else {
			for (int i = 0; i < catalogo.size(); i++) {
				Vector<String> v = new Vector<String>();
				v.add(catalogo.get(i).getName());
				v.add(catalogo.get(i).getType());
				v.add(String.valueOf(catalogo.get(i).getPrice()));
				modeloTableCatalogo.addRow(v);
			}
		}

		filtroPrecios = false;
		getBtnFilterPrice().setText("Filtrar por precio");
	}

	private void quitarFiltroTipos() {
		borrarTablaCatalogo();

		if (filtroPrecios) {
			for (int i = 0; i < catalogoPrecios.size(); i++) {
				Vector<String> v = new Vector<String>();
				v.add(catalogoPrecios.get(i).getName());
				v.add(catalogoPrecios.get(i).getType());
				v.add(String.valueOf(catalogoPrecios.get(i).getPrice()));
				modeloTableCatalogo.addRow(v);
			}
		} else {
			for (int i = 0; i < catalogo.size(); i++) {
				Vector<String> v = new Vector<String>();
				v.add(catalogo.get(i).getName());
				v.add(catalogo.get(i).getType());
				v.add(String.valueOf(catalogo.get(i).getPrice()));
				modeloTableCatalogo.addRow(v);
			}
		}

		filtroTipos = false;
		getBtnFilterType().setText("Filtrar por tipo");
	}

	private void borrarTablaCatalogo() {
		modeloTableCatalogo.getDataVector().removeAllElements();
		modeloTableCatalogo.fireTableDataChanged();
	}

	private void cargarTipos() {
		tipos = new CatalogoDataBase(db).getTipos();
	}
	private JButton getBtnCargarPlantillas() {
		if (btnCargarPlantillas == null) {
			btnCargarPlantillas = new JButton("Cargar plantillas");
			btnCargarPlantillas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ejecutarVentanaPlantillas();
				}
			});
			btnCargarPlantillas.setMnemonic('t');
			btnCargarPlantillas.setMargin(new Insets(0, 0, 0, 0));
			btnCargarPlantillas.setForeground(Color.BLACK);
			btnCargarPlantillas.setFont(new Font("Dialog", Font.BOLD, 14));
			btnCargarPlantillas.setBackground(Color.LIGHT_GRAY);
		}
		return btnCargarPlantillas;
	}
	
	private void ejecutarVentanaPlantillas() {
		VentanaPlantillasPresupuestos frame = new VentanaPlantillasPresupuestos(this,db);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
