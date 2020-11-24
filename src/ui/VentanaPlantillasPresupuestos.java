package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import business.bbdd.DataBase;
import business.catalogo.CatalogoDataBase;
import business.logic.Presupuesto;
import business.logic.Producto;
import business.presupuestos.PresupuestosDataBase;
/**
 * @author UO270656
 *  DNI 71732222Y
 *	
 *	Clase que representa la ventana donde se crean presupuestos
 */
public class VentanaPlantillasPresupuestos extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel modeloTablePlantillas;	
	private DefaultTableModel modeloTablePresupesto;	
	private JPanel panelNorte;
	private JPanel panelCentro;
	private JPanel panelPlantillas;
	private JLabel lblPlantillas;
	private JPanel panelPresupuesto;
	private JPanel panelBoton;
	private JButton btnSelecionar;
	private JScrollPane scrollPanePlantillas;
	private JScrollPane scrollPanePresupuesto;
	private JLabel lblTitle;
	private JTable tablePlantillas;
	private JTable tablePresupuesto;
	private JPanel panelNortePresupuesto;
	private JLabel lblPresupuesto;
	private JTextField txtTotal;
	
	private List<Producto> presupuesto;
	private List<Presupuesto> plantillas;
	
	private VentanaPresupuestos ventana;
	
	private DataBase db;
	
	/**
	 * Launch the application.
	 */
//	public static void run(DataBase db) {
//		try {
//			VentanaPlantillasPresupuestos frame = new VentanaPlantillasPresupuestos(db);
//			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//			frame.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	
	/** Constructor de la ventana Principal de la aplicacion para la venta de comida rápida
	 * @param ventanaPresupuestos 
	 */
	public VentanaPlantillasPresupuestos(VentanaPresupuestos ventanaPresupuestos, DataBase db) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.ventana=ventanaPresupuestos;
		this.db=db;
		cargarPlantillas();
		presupuesto=new ArrayList<Producto>();
		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(getPanelNorte(), BorderLayout.NORTH);
		getContentPane().add(getPanelCentro(), BorderLayout.CENTER);
		setTitle("IPS LAB");
		setBounds(100, 100, 815, 485);
		setLocationRelativeTo(null);
	}
	
	private void cargarPlantillas() {
		PresupuestosDataBase pdb = new PresupuestosDataBase(db);
		plantillas=pdb.getPresupuestosPlantilla();		
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
		if (panelPlantillas == null) {
			panelPlantillas = new JPanel();
			panelPlantillas.setForeground(Color.WHITE);
			panelPlantillas.setBackground(Color.WHITE);
			panelPlantillas.setLayout(new BorderLayout(0, 0));
			panelPlantillas.add(getLblPlantillas(), BorderLayout.NORTH);
			panelPlantillas.add(getPanelBoton(), BorderLayout.SOUTH);
			panelPlantillas.add(getScrollPanePlantillas(), BorderLayout.CENTER);
		}
		return panelPlantillas;
	}
	
	/** Método que devuelve o crea y devuelve la etiqueta identificadora de la libreria si no existe
	 * @return la etiqueta
	 */
	private JLabel getLblPlantillas() {
		if (lblPlantillas == null) {
			lblPlantillas = new JLabel("Plantillas");
			lblPlantillas.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPlantillas.setForeground(Color.BLACK);
			lblPlantillas.setBackground(new Color(0, 0, 0));
		}
		return lblPlantillas;
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
			panelPresupuesto.add(getScrollPanePlay(), BorderLayout.CENTER);
		}
		return panelPresupuesto;
	}
	
	/** Método que devuelve o crea y devuelve el panel donde se incorporan los botones de add to playlist y delete si no existe
	 * @return el panel
	 */
	private JPanel getPanelBoton() {
		if (panelBoton == null) {
			panelBoton = new JPanel();
			panelBoton.setForeground(Color.WHITE);
			panelBoton.setBackground(Color.WHITE);
			panelBoton.setLayout(new GridLayout(0, 1, 0, 0));
			panelBoton.add(getBtnSelecionar());
		}
		return panelBoton;
	}
	
	/** Método que devuelve o crea y devuelve el botón add to playlist si no existe
	 * @actionPerformed añadir los archivos seleccionados en la lista de la libreria a la playlist
	 * @return el botón
	 */
	private JButton getBtnSelecionar() {
		if (btnSelecionar == null) {
			btnSelecionar = new JButton("Selecionar");
			btnSelecionar.setMnemonic('t');
			btnSelecionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Presupuesto plantillaElegida =plantillas.get(getTablePlantillas().getSelectedRow());
					PresupuestosDataBase pdb = new PresupuestosDataBase(db);
					ventana.setPresupuesto(pdb.getProductosPresupuesto(plantillaElegida.getPresupuesto_id()));
					dispose();
				}
			});
			btnSelecionar.setMargin(new Insets(0, 0, 0, 0));
			btnSelecionar.setFont(new Font("Dialog", Font.BOLD, 14));
			btnSelecionar.setForeground(Color.BLACK);
			btnSelecionar.setBackground(Color.LIGHT_GRAY);
		}
		return btnSelecionar;
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
	
	/** Método que devuelve o crea y devuelve el panel de scroll de la libreria
	 * @return el panel de scroll
	 */
	private JScrollPane getScrollPanePlantillas() {
		if (scrollPanePlantillas == null) {
			scrollPanePlantillas = new JScrollPane();
			scrollPanePlantillas.setBackground(Color.WHITE);
			scrollPanePlantillas.setBorder(new LineBorder(Color.BLACK, 6));
			scrollPanePlantillas.setViewportView(getTablePlantillas());
		}
		return scrollPanePlantillas;
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
	
	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Selecionar plantilla");
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
			txtTotal.setText("Precio plantilla:");
			txtTotal.setColumns(10);
		}
		return txtTotal;
	}
	private JTable getTablePlantillas() {
		if (tablePlantillas == null) {
			int rows= plantillas.size(); //cargar plantillas
			Vector<String> v= new Vector<String>();
			v.add("ID Plantilla");
			modeloTablePlantillas = new DefaultTableModel(v,rows) {
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			};
			tablePlantillas = new JTable(modeloTablePlantillas);
			tablePlantillas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTable tabla =(JTable) e.getComponent();
					pintaPresupuesto(tabla.getSelectedRow());
				}
			});
			tablePlantillas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablePlantillas.setFont(new Font("Tahoma", Font.PLAIN, 18));
			for(int i =0 ;i<rows;i++) {
				tablePlantillas.setValueAt(plantillas.get(i).getPresupuesto_id(), i, 0);
			}
		}
		return tablePlantillas;
	}
	
	private void pintaPresupuesto(int row) {
		presupuesto=new ArrayList<Producto>();
		modeloTablePresupesto.getDataVector().removeAllElements();
		modeloTablePresupesto.fireTableDataChanged();
		CatalogoDataBase cdb= new CatalogoDataBase(db);
		Presupuesto plantillaElegida = plantillas.get(row);
		PresupuestosDataBase pdb = new PresupuestosDataBase(db);
		List<Producto> pre = pdb.getProductosPresupuesto(plantillaElegida.getPresupuesto_id());
		for(int i =0;i<pre.size();i++) {
			Producto pr=cdb.getProductoById(pre.get(i).getProduct_id());
			pr.setUds(pre.get(i).getUds());
			Vector<String> v= new Vector<String>();
			v.add(pr.getName());
			v.add(pr.getType());
			v.add(String.format("%.2f", pr.getPrice()));
			v.add(String.valueOf(pr.getUds()));
			modeloTablePresupesto.addRow(v);
			presupuesto.add(pr);
		}
		actualizaPrecio();
		repaint();
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
	
	private JPanel getPanelNortePresupuesto() {
		if (panelNortePresupuesto == null) {
			panelNortePresupuesto = new JPanel();
			panelNortePresupuesto.setBackground(Color.WHITE);
			panelNortePresupuesto.setForeground(Color.WHITE);
			panelNortePresupuesto.setLayout(new GridLayout(0, 1, 0, 0));
			panelNortePresupuesto.add(getLabelPresupuesto());
		}
		return panelNortePresupuesto;
	}
	private JLabel getLabelPresupuesto() {
		if (lblPresupuesto == null) {
			lblPresupuesto = new JLabel("Presupuesto de la plantilla selecionada");
			lblPresupuesto.setForeground(Color.BLACK);
			lblPresupuesto.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPresupuesto.setBackground(new Color(0, 0, 0));
		}
		return lblPresupuesto;
	}

}
