package interfaces;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.sqlite.SQLiteDataSource;

import general.FuncionesUtiles;
import general.NonEditableModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class VisualizarReportes extends JFrame {

	private JPanel contentPane;
	private JTable tablaAnalisis;
	static VisualizarReportes frame;
	private JTextField txtDia1;
	private JTextField txtMes1;
	private JTextField txtAnio1;
	private JTextField txtDia2;
	private JTextField txtMes2;
	private JTextField txtAnio2;
	private JTextField txtProyecto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		UIManager.put("OptionPane.messageFont", new Font("Rockwell", Font.PLAIN, 14));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new VisualizarReportes();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VisualizarReportes() {
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 764, 288);
		contentPane.add(scrollPane);
		
		//Crea los arraylists usados para cargar la información de la base de datos
		ResultSet rs = null;
		ArrayList<String> IDs = new ArrayList<String>();
		ArrayList<String> UsuarioIDs = new ArrayList<String>();
		ArrayList<String> OrganizacionIDs = new ArrayList<String>();
		ArrayList<String> OrganizacionNombres = new ArrayList<String>();
		ArrayList<String> Dias = new ArrayList<String>();
		ArrayList<String> Meses = new ArrayList<String>();
		ArrayList<String> Anios = new ArrayList<String>();
		ArrayList<String> Horas = new ArrayList<String>();
		ArrayList<String> Minutos = new ArrayList<String>();
		ArrayList<String> Cantidades = new ArrayList<String>();
		ArrayList<String> Listas = new ArrayList<String>();
		ArrayList<String> Carpetas = new ArrayList<String>();
		
		//Carga la información de los análisis realizados por el usuario en los arraylists
		CargarColumnas(rs, IDs, UsuarioIDs, OrganizacionIDs, OrganizacionNombres, Dias, Meses, Anios, Horas, Minutos, Cantidades, Listas, Carpetas);
		
		//Carga los datos para formar la tabla
		String[][] data = new String[OrganizacionNombres.size()][12];
		for (int i=0; i < OrganizacionNombres.size(); i++) {
			data[i][0] = IDs.get(i);
			data[i][1] = UsuarioIDs.get(i);
			data[i][2] = OrganizacionIDs.get(i);
			data[i][3] = OrganizacionNombres.get(i);
			data[i][4] = Dias.get(i);
			data[i][5] = Meses.get(i);
			data[i][6] = Anios.get(i);
			data[i][7] = Horas.get(i);
			data[i][8] = Minutos.get(i);
			data[i][9] = Cantidades.get(i);
			data[i][10] = Listas.get(i);
			data[i][11] = Carpetas.get(i);
		}
		String column[]={"ID", "UsuarioID", "OrganizacionID", "Organizacion", "Dia", "Mes", "Año", "Hora", "Minuto", "Cantidad de Proyectos", "Lista", "Carpeta"};
		
		//Crea la tabla y la carga
		tablaAnalisis = new JTable();
		scrollPane.setViewportView(tablaAnalisis);
		tablaAnalisis.setModel(new NonEditableModel(data, column));
		tablaAnalisis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cierra la ventana de visualización de reportes y habilita la ventana principal
				Principal.frmJuploader.setEnabled(true);
				Principal.frmJuploader.toFront();
				dispose();
			}
		});
		btnCerrar.setBounds(668, 470, 106, 23);
		contentPane.add(btnCerrar);
		
		JLabel lblVisualizarReportes = new JLabel("~ Visualizar Reportes ~");
		lblVisualizarReportes.setHorizontalAlignment(SwingConstants.CENTER);
		lblVisualizarReportes.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblVisualizarReportes.setBounds(10, 23, 764, 29);
		contentPane.add(lblVisualizarReportes);
		
		JButton btnExcel = new JButton("Excel");
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Permite exportar la tabla a excel
				try {
					exportarExcel(tablaAnalisis);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error al exportar a excel.");
				}
			}
		});
		btnExcel.setBounds(10, 470, 106, 23);
		contentPane.add(btnExcel);
		
		txtDia1 = new JTextField();
		txtDia1.setBounds(48, 382, 45, 20);
		contentPane.add(txtDia1);
		txtDia1.setColumns(10);
		
		txtMes1 = new JTextField();
		txtMes1.setColumns(10);
		txtMes1.setBounds(103, 382, 45, 20);
		contentPane.add(txtMes1);
		
		txtAnio1 = new JTextField();
		txtAnio1.setColumns(10);
		txtAnio1.setBounds(158, 382, 45, 20);
		contentPane.add(txtAnio1);
		
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(10, 385, 34, 14);
		contentPane.add(lblDesde);
		
		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(10, 424, 34, 14);
		contentPane.add(lblHasta);
		
		txtDia2 = new JTextField();
		txtDia2.setColumns(10);
		txtDia2.setBounds(47, 421, 45, 20);
		contentPane.add(txtDia2);
		
		txtMes2 = new JTextField();
		txtMes2.setColumns(10);
		txtMes2.setBounds(102, 421, 45, 20);
		contentPane.add(txtMes2);
		
		txtAnio2 = new JTextField();
		txtAnio2.setColumns(10);
		txtAnio2.setBounds(157, 421, 45, 20);
		contentPane.add(txtAnio2);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Permite filtrar los datos de la tabla por fecha
				
				//Controla los datos ingresados en los campos de las fechas
				if ((FuncionesUtiles.isInteger(txtDia1.getText()) == false) || (Integer.parseInt(txtDia1.getText()) <= 0) || (Integer.parseInt(txtDia1.getText()) >= 32) || (txtDia1.getText().length() != 2)) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor de día de inicio correcto.");
					return;
				}
				
				if ((FuncionesUtiles.isInteger(txtMes1.getText()) == false) || (Integer.parseInt(txtMes1.getText()) <= 0) || (Integer.parseInt(txtMes1.getText()) >= 13) || (txtMes1.getText().length() != 2)) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor de mes de inicio correcto.");
					return;
				}
				
				if ((FuncionesUtiles.isInteger(txtAnio1.getText()) == false) || (Integer.parseInt(txtAnio1.getText()) <= 0) || (txtAnio1.getText().length() != 4)) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor de año de inicio correcto.");
					return;
				}
				
				if ((FuncionesUtiles.isInteger(txtDia2.getText()) == false) || (Integer.parseInt(txtDia2.getText()) <= 0) || (Integer.parseInt(txtDia2.getText()) >= 32) || (txtDia2.getText().length() != 2)) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor de día de final correcto.");
					return;
				}
								
				if ((FuncionesUtiles.isInteger(txtMes2.getText()) == false) || (Integer.parseInt(txtMes2.getText()) <= 0) || (Integer.parseInt(txtMes2.getText()) >= 13) || (txtMes2.getText().length() != 2)) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor de mes de final correcto.");
					return;
				}
												
				if ((FuncionesUtiles.isInteger(txtAnio2.getText()) == false) || (Integer.parseInt(txtAnio2.getText()) <= 0) || (txtAnio1.getText().length() != 4) || (Integer.parseInt(txtAnio2.getText()) < Integer.parseInt(txtAnio1.getText()))) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor de año de final correcto.");
					return;
				}
				
				//Vacía la tabla
				NonEditableModel dm = (NonEditableModel)tablaAnalisis.getModel();
				while(dm.getRowCount() > 0)
				{
				    dm.removeRow(0);
				}
				
				//Concatena las fechas en formato YYYYMMDD y las transformas en un número que se usará para comparaciones
				String temp = txtAnio1.getText();
				temp = temp.concat(txtMes1.getText());
				temp = temp.concat(txtDia1.getText());
				int fechaInicio = Integer.parseInt(temp);
								
				temp = txtAnio2.getText();
				temp = temp.concat(txtMes2.getText());
				temp = temp.concat(txtDia2.getText());
				int fechaFin = Integer.parseInt(temp);
				
				int cont1 = 0;
				int cont2 = 0;
				
				//Recorre todos los registros para obtener la cantidad exacta que se ubican entre las fechas ingresadas
				for (int i=0; i < OrganizacionNombres.size(); i++) {
									
					temp = Anios.get(i);
					
					//Si el mes tiene una sola cifra, se agrega un cero
					if (Meses.get(i).length() != 2) {
						temp = temp.concat("0");
						temp = temp.concat(Meses.get(i));
					} else {
						temp = temp.concat(Meses.get(i));
					}
					
					//Si el dia tiene una sola cifra, se agrega un cero
					if (Dias.get(i).length() != 2) {
						temp = temp.concat("0");
						temp = temp.concat(Dias.get(i));
					} else {
						temp = temp.concat(Dias.get(i));
					}
					
					int fechaComparar = Integer.parseInt(temp);
					
					//Se controla la fecha del registro con las fechas ingresadas por el usuario y se cuenta
					if ((fechaComparar >= fechaInicio) && (fechaComparar <= fechaFin)) {
						cont1 = cont1 + 1;
					}
					
				}
				
				//Se crea un array con el tamaño exacto de registros contados anteriormente
				String[][] data2 = new String[cont1][12];
				for (int i=0; i < OrganizacionNombres.size(); i++) {
					
					temp = Anios.get(i);
					
					//Si el mes tiene una sola cifra, se agrega un cero
					if (Meses.get(i).length() != 2) {
						temp = temp.concat("0");
						temp = temp.concat(Meses.get(i));
					} else {
						temp = temp.concat(Meses.get(i));
					}
					
					//Si el dia tiene una sola cifra, se agrega un cero
					if (Dias.get(i).length() != 2) {
						temp = temp.concat("0");
						temp = temp.concat(Dias.get(i));
					} else {
						temp = temp.concat(Dias.get(i));
					}
					
					int fechaComparar = Integer.parseInt(temp);
					
					//Se controla la fecha del registro con las fechas ingresadas por el usuario y se agregan al array que se cargará a la tabla
					if ((fechaComparar >= fechaInicio) && (fechaComparar <= fechaFin)) {
						
						data2[cont2][0] = IDs.get(i);
						data2[cont2][1] = UsuarioIDs.get(i);
						data2[cont2][2] = OrganizacionIDs.get(i);
						data2[cont2][3] = OrganizacionNombres.get(i);
						data2[cont2][4] = Dias.get(i);
						data2[cont2][5] = Meses.get(i);
						data2[cont2][6] = Anios.get(i);
						data2[cont2][7] = Horas.get(i);
						data2[cont2][8] = Minutos.get(i);
						data2[cont2][9] = Cantidades.get(i);
						data2[cont2][10] = Listas.get(i);
						data2[cont2][11] = Carpetas.get(i);
						cont2 = cont2 + 1;
					}
					
				}
				
				//Se carga la tabla
				tablaAnalisis.setModel(new NonEditableModel(data2, column));
				tablaAnalisis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
		});
		btnFiltrar.setBounds(223, 420, 106, 23);
		contentPane.add(btnFiltrar);
		
		JLabel lblDia1 = new JLabel("D\u00EDa (DD)");
		lblDia1.setBounds(48, 370, 45, 14);
		contentPane.add(lblDia1);
		
		JLabel lblMes1 = new JLabel("Mes (MM)");
		lblMes1.setBounds(103, 370, 56, 14);
		contentPane.add(lblMes1);
		
		JLabel lblAnio1 = new JLabel("A\u00F1o (YYYY)");
		lblAnio1.setBounds(158, 370, 56, 14);
		contentPane.add(lblAnio1);
		
		JLabel lblDia2 = new JLabel("D\u00EDa (DD)");
		lblDia2.setBounds(47, 409, 45, 14);
		contentPane.add(lblDia2);
		
		JLabel lblMes2 = new JLabel("Mes (MM)");
		lblMes2.setBounds(102, 409, 56, 14);
		contentPane.add(lblMes2);
		
		JLabel lblAnio2 = new JLabel("A\u00F1o (YYYY)");
		lblAnio2.setBounds(157, 409, 56, 14);
		contentPane.add(lblAnio2);
		
		JButton btnMostrarTodo = new JButton("Mostrar Todo");
		btnMostrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Recarga la ventana para que se recargue la tabla con todos los registros
				VisualizarReportes.frame.dispose();
				VisualizarReportes.main(null);
				VisualizarReportes.frame.toFront();
			}
		});
		btnMostrarTodo.setBounds(223, 381, 106, 23);
		contentPane.add(btnMostrarTodo);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(339, 358, 8, 93);
		contentPane.add(separator);
		
		JButton btnOrganizaciones = new JButton("Organizaciones");
		btnOrganizaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Muestra el gráfico de reporte de la cantidad de organizaciones usadas en los análisis
				
				List<String> organizacionesTabla = new ArrayList<String>();
				
				//Carga en la lista la información de las organizaciones mostradas en la tabla
				for(int row = 0; row < tablaAnalisis.getRowCount(); row++) {
					organizacionesTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 3)));  
				}
				
				
				// Crea un nuevo ArrayList
		        ArrayList<String> sinDuplicados = new ArrayList<String>();
		        // Recorre la lista de organizaciones
		        for (String element : organizacionesTabla) {
		            // Se cargan los elementos al nuevo array que no incluye los duplicados
		            if (!sinDuplicados.contains(element)) {
		            	sinDuplicados.add(element);
		            }
		        }
				
		        
		        //Se realiza el gráfico de barras
				DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		        
				for (int i = 0; i < sinDuplicados.size(); i++) {
					dataset.addValue(Collections.frequency(organizacionesTabla, sinDuplicados.get(i)), sinDuplicados.get(i), "");
				}

			    JFreeChart barChart = ChartFactory.createBarChart("Cantidad de Análisis por Organización", "Organizaciones", "Cantidad", dataset, PlotOrientation.VERTICAL, true, true, false);
			    
			    JFrame frame2 = new JFrame("Charts");
			    frame2.setSize(600, 400);
			    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    frame2.setVisible(true);
			    frame2.setTitle("Cantidad de Análisis por Organización");
			    frame2.setLocationRelativeTo(null);
			    ChartPanel cp2 = new ChartPanel(barChart);
			    frame2.getContentPane().add(cp2);
			}
		});
		btnOrganizaciones.setBounds(357, 428, 167, 23);
		contentPane.add(btnOrganizaciones);
		
		JButton btnCantidadPorMes = new JButton("Cantidad Total x Mes");
		btnCantidadPorMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Muestra el gráfico de reporte de la cantidad de proyectos analizados por mes
				
				List<String> cantidadesTabla = new ArrayList<String>();
				List<String> aniosTabla = new ArrayList<String>();
				List<String> mesesTabla = new ArrayList<String>();
				
				//Carga en la lista la información de las cantidades, años y meses mostradas en la tabla
				for(int row = 0; row < tablaAnalisis.getRowCount(); row++) {
					cantidadesTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 9)));
					aniosTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 6))); 
					mesesTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 5))); 
				}

				ArrayList<String> anioConcatMes = new ArrayList<String>();
		        ArrayList<String> mesesParaGrafico = new ArrayList<String>();
		        ArrayList<Integer> CantidadesParaGrafico = new ArrayList<Integer>();
		        
		        //Concatena los años y meses en formato YYYY-MM
		        String guion = "-";
		        for (int i = 0; i < aniosTabla.size(); i++) {
		        	anioConcatMes.add(aniosTabla.get(i).concat(guion.concat(mesesTabla.get(i))));
		        }
		        
		        //Carga un arraylist sin los YYYY-MM repetidos
		        for (String element : anioConcatMes) {
		            if (!mesesParaGrafico.contains(element)) {
		            	mesesParaGrafico.add(element);
		            }
		        }
		        
		        int j = 0;
		        int acum = 0;
		        //Recorre las cantidades de análisis comparando con la fecha que le corresponde, obteniendo los totales por YYYY-MM
		        for (int i = 0; i < anioConcatMes.size(); i++) {
		        	if (anioConcatMes.get(i).equals(mesesParaGrafico.get(j))) {
		        		acum = acum + Integer.parseInt(cantidadesTabla.get(i));
		        	}
		        	else {
		        		CantidadesParaGrafico.add(acum);
		        		acum = 0;
		        		acum = acum + Integer.parseInt(cantidadesTabla.get(i));
		        		j = j + 1;
		        	}
		        	if (i == anioConcatMes.size()-1) {
		        		CantidadesParaGrafico.add(acum);
		        	}
		        }
		        
		      //Se realiza el gráfico de barras
				DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		        
				for (int i = 0; i < mesesParaGrafico.size(); i++) {
					dataset.addValue(CantidadesParaGrafico.get(i), mesesParaGrafico.get(i), "");
				}

			    JFreeChart barChart = ChartFactory.createBarChart("Cantidad de Análisis Total por Mes", "Meses", "Cantidad", dataset, PlotOrientation.VERTICAL, true, true, false);
			    
			    JFrame frame2 = new JFrame("Charts");
			    frame2.setSize(600, 400);
			    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    frame2.setVisible(true);
			    frame2.setTitle("Cantidad de Análisis Total por Mes");
			    frame2.setLocationRelativeTo(null);
			    ChartPanel cp2 = new ChartPanel(barChart);
			    frame2.getContentPane().add(cp2);
			}
		});
		btnCantidadPorMes.setBounds(357, 400, 167, 23);
		contentPane.add(btnCantidadPorMes);
		
		JButton btnCantidadDeUnProyecto = new JButton("Cantidad de un Proyecto ->");
		btnCantidadDeUnProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Muestra el gráfico de reporte de la cantidad de un proyecto específico por mes
				
				//Controla que el usuario ingrese un nombre de proyecto
				if (txtProyecto.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del proyecto.");
					return;
				}
				
				
				List<String> listasTabla = new ArrayList<String>();
				List<String> aniosTabla = new ArrayList<String>();
				List<String> mesesTabla = new ArrayList<String>();
				
				//Carga en la lista la información de las listas de proyectos analizados, años y meses mostradas en la tabla
				for(int row = 0; row < tablaAnalisis.getRowCount(); row++) {
					listasTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 10)));
					aniosTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 6))); 
					mesesTabla.add(String.valueOf(tablaAnalisis.getValueAt(row, 5))); 
				}
				
				
				ArrayList<String> todosLosProyectos = new ArrayList<String>();
				ArrayList<String> fechas = new ArrayList<String>();
				ArrayList<String> fechasSinRepetidos = new ArrayList<String>();
				ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();
				
				//Separa los nombres de proyectos usando la coma como separador y se guardan en un arraylist
				//También guarda las fechas en formato YYYY-MM en otro arraylist
				for (int i = 0; i < listasTabla.size(); i++) {
					String[] temp = listasTabla.get(i).split("\\s*,\\s*");
					for (int j = 0; j < temp.length; j++) {
						todosLosProyectos.add(temp[j]);
						fechas.add(aniosTabla.get(i)+"-"+mesesTabla.get(i));
					}
				}
				
				//Guarda las fechas YYYY-MM sin repetirlas en otro arraylist
				for (String element : fechas) {
		            if (!fechasSinRepetidos.contains(element)) {
		            	fechasSinRepetidos.add(element);
		            }
		        }
				
				int count = 0;
				String date = "";
				
				//Recorre todos los proyectos comparando sus fechas y contando las cantidades de ocurrencia de cada uno en cada mes
				for (int i = 0; i < fechasSinRepetidos.size(); i++) {
					date = fechasSinRepetidos.get(i);
					for (int j = 0; j < todosLosProyectos.size(); j++) {
						
						if ((txtProyecto.getText().equals(todosLosProyectos.get(j))) && (fechas.get(j).equals(date))) {
							count = count + 1;
						}
					}
					cantidadPorMes.add(count);
					count = 0;
				}
				
				int acum = 0;
				
				//Obtiene la cantidad total de apariciones del proyecto
				for (int i = 0; i < cantidadPorMes.size(); i++) {
					acum = acum + cantidadPorMes.get(i);
				}
				
				//Se realiza el gráfico de barras
				DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		        
				for (int i = 0; i < fechasSinRepetidos.size(); i++) {
					dataset.addValue(cantidadPorMes.get(i), fechasSinRepetidos.get(i), "");
				}

			    JFreeChart barChart = ChartFactory.createBarChart("Análisis de "+txtProyecto.getText()+" por Mes", "Meses (Apariciones Totales = "+acum+")", "Cantidad", dataset, PlotOrientation.VERTICAL, true, true, false);
			    
			    JFrame frame2 = new JFrame("Charts");
			    frame2.setSize(600, 400);
			    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    frame2.setVisible(true);
			    frame2.setTitle("Análisis de "+txtProyecto.getText()+" por Mes");
			    frame2.setLocationRelativeTo(null);
			    ChartPanel cp2 = new ChartPanel(barChart);
			    frame2.getContentPane().add(cp2);
				
			}
		});
		btnCantidadDeUnProyecto.setBounds(357, 370, 167, 23);
		contentPane.add(btnCantidadDeUnProyecto);
		
		txtProyecto = new JTextField();
		txtProyecto.setBounds(539, 370, 130, 23);
		contentPane.add(txtProyecto);
		txtProyecto.setColumns(10);
		
		JLabel lblNombreDeProyecto = new JLabel("Nombre de Proyecto en Lista");
		lblNombreDeProyecto.setBounds(534, 355, 145, 14);
		contentPane.add(lblNombreDeProyecto);
		
		JButton btnAbrirCarpeta = new JButton("Abrir Carpeta");
		btnAbrirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abre la carpeta de análisis de los proyectos de la linea seleccionada en la tabla
				try {
					if (tablaAnalisis.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.");
						return;
					}
					
					int fila = tablaAnalisis.getSelectedRow();
					String folder = (String) tablaAnalisis.getValueAt(fila, 11);
					
					String longer = "" + folder.charAt(0);
				    for (int i = 1; i < folder.length(); i++) {
				    	longer += folder.charAt(i);
				    	if (folder.charAt(i) == '\\') {
				    		longer += "\\";
				    	}
				    }

					Desktop.getDesktop().open(new File(longer));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error abriendo la carpeta de proyectos del análisis.");
				}
			}
		});
		btnAbrirCarpeta.setBounds(679, 369, 97, 25);
		contentPane.add(btnAbrirCarpeta);
	}
	
	//Carga la información de los análisis realizados por el usuario en los arraylists
	public void CargarColumnas (ResultSet rs, ArrayList<String> IDs, ArrayList<String> UsuarioIDs, ArrayList<String> OrganizacionIDs, ArrayList<String> OrganizacionNombres, ArrayList<String> Dias, ArrayList<String> Meses, ArrayList<String> Anios, ArrayList<String> Horas, ArrayList<String> Minutos, ArrayList<String> Cantidades, ArrayList<String> Listas, ArrayList<String> Carpetas) {
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM analisis WHERE USUARIOID = '"+Principal.lblIDValue.getText()+"'";
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery( query );
			
			while ( rs.next() ) {
			    int id = rs.getInt( "ID" );
			    int usuarioid = rs.getInt( "USUARIOID" );
			    int organizacionid = rs.getInt( "ORGANIZACIONID" );
			    String organizacionnombre = rs.getString( "ORGANIZACIONNOMBRE" );
			    int dia = rs.getInt( "DIA" );
			    int mes = rs.getInt( "MES" );
			    int anio = rs.getInt( "ANIO" );
			    int hora = rs.getInt( "HORA" );
			    int minuto = rs.getInt( "MINUTO" );
			    int cantidad = rs.getInt( "CANTIDAD" );
			    String lista = rs.getString( "LISTA" );
			    String carpeta = rs.getString( "CARPETA" );
			    
			    IDs.add(Integer.toString(id));
			    UsuarioIDs.add(Integer.toString(usuarioid));
			    OrganizacionIDs.add(Integer.toString(organizacionid));
			    OrganizacionNombres.add(organizacionnombre);
			    Dias.add(Integer.toString(dia));
			    Meses.add(Integer.toString(mes));
			    Anios.add(Integer.toString(anio));
			    Horas.add(Integer.toString(hora));
			    Minutos.add(Integer.toString(minuto));
			    Cantidades.add(Integer.toString(cantidad));
			    Listas.add(lista);
			    Carpetas.add(carpeta);			    
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace(System.out);
			JOptionPane.showMessageDialog(null, "Error al obtener información de los análisis.");
		}
	}
	
	//Exporta los datos mostrados actualmente en la tabla a una planilla de excel
	public void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
}
