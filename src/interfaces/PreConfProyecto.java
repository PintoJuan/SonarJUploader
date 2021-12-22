package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.sqlite.SQLiteDataSource;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;

public class PreConfProyecto extends JFrame {

	private JPanel contentPane;
	private JTextField txtProjectKey;
	private JTextField txtCarpetaProyecto;
	private JTextField txtProjectName;
	private JTextField txtProjectVersion;
	private JTextField txtLenguaje;
	private JTextField txtCarpetaSources;
	private JTextField txtCarpetaBinaries;
	private JTextField txtCarpetaLibraries;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		UIManager.put("OptionPane.messageFont", new Font("Rockwell", Font.PLAIN, 14));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreConfProyecto frame = new PreConfProyecto();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PreConfProyecto() {
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProjectkey = new JLabel("ProjectKey:");
		lblProjectkey.setForeground(Color.WHITE);
		lblProjectkey.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblProjectkey.setBounds(84, 262, 69, 16);
		contentPane.add(lblProjectkey);
		
		txtProjectKey = new JTextField();
		txtProjectKey.setText("organizacion.sonar:nombreproyecto");
		txtProjectKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtProjectKey.setColumns(10);
		txtProjectKey.setBounds(156, 257, 197, 28);
		contentPane.add(txtProjectKey);
		
		JLabel lblPreconfigurarProyecto = new JLabel("~ Pre-configurar Proyecto ~");
		lblPreconfigurarProyecto.setForeground(Color.WHITE);
		lblPreconfigurarProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreconfigurarProyecto.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblPreconfigurarProyecto.setBounds(81, 11, 325, 24);
		contentPane.add(lblPreconfigurarProyecto);
		
		//Crea el fichero sonar.properties con los datos ingresados para el proyecto seleccionado
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtCarpetaProyecto.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar la direcci�n de la carpeta del proyecto.");
					return;
				}
				
				if (txtProjectKey.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar la clave sonar del proyecto.");
					return;
				}
				
				if (txtProjectName.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del proyecto.");
					return;
				}
				
				if (txtProjectVersion.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar la versi�n del proyecto.");
					return;
				}
				
				if (txtLenguaje.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el lenguaje del proyecto.");
					return;
				}
				
				if (txtCarpetaSources.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de la carpeta que contiene los ficheros de c�digo fuente.");
					return;
				}
				
				if (txtCarpetaBinaries.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de la carpeta que contiene los ficheros compilados.");
					return;
				}
				
				if (txtCarpetaLibraries.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de la carpeta que contiene las librer�as utilizadas.");
					return;
				}
				
				try {
					String directory = txtCarpetaProyecto.getText();
					directory = directory.replace("\\", "/");
					directory = directory +"/sonar-project.properties";
					PrintWriter writer = new PrintWriter(directory, "UTF-8");
					writer.println("sonar.sourceEncoding=UTF-8");
					writer.println("sonar.projectKey="+txtProjectKey.getText());
					writer.println("sonar.projectName="+txtProjectName.getText());
					writer.println("sonar.projectVersion="+txtProjectVersion.getText());
					writer.println("sonar.language="+txtLenguaje.getText());
					writer.println("sonar.sources="+txtCarpetaSources.getText());
					writer.println("sonar.java.binaries="+txtCarpetaBinaries.getText());
					writer.println("sonar.java.libraries="+txtCarpetaLibraries.getText());
					writer.println("sonar.c.file.suffixes=-");
					writer.println("sonar.cpp.file.suffixes=-");
					writer.println("sonar.objc.file.suffixes=-");
					writer.close();
					JOptionPane.showMessageDialog(null, "Fichero de configuraci�n de Sonar creado exitosamente.");
				}
				catch (Exception e1) {
					e1.printStackTrace(System.out);
					JOptionPane.showMessageDialog(null, "Error al crear el fichero de configuraci�n de Sonar.");
				}
			}
		});
		btnAceptar.setBounds(156, 488, 99, 30);
		contentPane.add(btnAceptar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.frmJuploader.setEnabled(true);
				Principal.frmJuploader.toFront();
				dispose();
			}
		});
		btnCerrar.setBounds(254, 488, 99, 30);
		contentPane.add(btnCerrar);
		
		JLabel lblCarpetaDelProyecto = new JLabel("Carpeta del Proyecto:");
		lblCarpetaDelProyecto.setForeground(Color.WHITE);
		lblCarpetaDelProyecto.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpetaDelProyecto.setBounds(25, 230, 128, 16);
		contentPane.add(lblCarpetaDelProyecto);
		
		txtCarpetaProyecto = new JTextField();
		txtCarpetaProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaProyecto.setColumns(10);
		txtCarpetaProyecto.setBounds(156, 225, 197, 28);
		contentPane.add(txtCarpetaProyecto);
		
		//Permite buscar una carpeta y obtener su direcci�n
		JButton btnBuscarProyecto = new JButton("...");
		btnBuscarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File yourFolder = fc.getSelectedFile();
				    txtCarpetaProyecto.setText(yourFolder.getAbsolutePath());
				    txtProjectName.setText(yourFolder.getName());
				    
				    String projectKey = txtProjectKey.getText();
					projectKey = projectKey.replace("nombreproyecto", yourFolder.getName());
					txtProjectKey.setText(projectKey);
				}
			}
		});
		btnBuscarProyecto.setBounds(363, 228, 43, 23);
		contentPane.add(btnBuscarProyecto);
		
		JLabel lblProjectName = new JLabel("ProjectName:");
		lblProjectName.setForeground(Color.WHITE);
		lblProjectName.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblProjectName.setBounds(70, 294, 82, 16);
		contentPane.add(lblProjectName);
		
		txtProjectName = new JTextField();
		txtProjectName.setText("nombreproyecto");
		txtProjectName.setHorizontalAlignment(SwingConstants.CENTER);
		txtProjectName.setColumns(10);
		txtProjectName.setBounds(156, 289, 197, 28);
		contentPane.add(txtProjectName);
		
		JLabel lblProjectVersion = new JLabel("ProjectVersion:");
		lblProjectVersion.setForeground(Color.WHITE);
		lblProjectVersion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblProjectVersion.setBounds(61, 326, 91, 16);
		contentPane.add(lblProjectVersion);
		
		txtProjectVersion = new JTextField();
		txtProjectVersion.setText("1.0");
		txtProjectVersion.setHorizontalAlignment(SwingConstants.CENTER);
		txtProjectVersion.setColumns(10);
		txtProjectVersion.setBounds(156, 321, 197, 28);
		contentPane.add(txtProjectVersion);
		
		JLabel lblLenguaje = new JLabel("Lenguaje:");
		lblLenguaje.setForeground(Color.WHITE);
		lblLenguaje.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblLenguaje.setBounds(92, 358, 61, 16);
		contentPane.add(lblLenguaje);
		
		txtLenguaje = new JTextField();
		txtLenguaje.setText("java");
		txtLenguaje.setHorizontalAlignment(SwingConstants.CENTER);
		txtLenguaje.setColumns(10);
		txtLenguaje.setBounds(156, 353, 197, 28);
		contentPane.add(txtLenguaje);
		
		JLabel lblCarpetaSources = new JLabel("Carpeta Sources:");
		lblCarpetaSources.setForeground(Color.WHITE);
		lblCarpetaSources.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpetaSources.setBounds(53, 390, 100, 16);
		contentPane.add(lblCarpetaSources);
		
		txtCarpetaSources = new JTextField();
		txtCarpetaSources.setText(".");
		txtCarpetaSources.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaSources.setColumns(10);
		txtCarpetaSources.setBounds(156, 385, 197, 28);
		contentPane.add(txtCarpetaSources);
		
		JLabel lblCarpetaBinaries = new JLabel("Carpeta Binaries:");
		lblCarpetaBinaries.setForeground(Color.WHITE);
		lblCarpetaBinaries.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpetaBinaries.setBounds(49, 422, 104, 16);
		contentPane.add(lblCarpetaBinaries);
		
		txtCarpetaBinaries = new JTextField();
		txtCarpetaBinaries.setText(".");
		txtCarpetaBinaries.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaBinaries.setColumns(10);
		txtCarpetaBinaries.setBounds(156, 417, 197, 28);
		contentPane.add(txtCarpetaBinaries);
		
		JLabel lblCarpetaLibraries = new JLabel("Carpeta Libraries:");
		lblCarpetaLibraries.setForeground(Color.WHITE);
		lblCarpetaLibraries.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpetaLibraries.setBounds(44, 454, 108, 16);
		contentPane.add(lblCarpetaLibraries);
		
		txtCarpetaLibraries = new JTextField();
		txtCarpetaLibraries.setText(".");
		txtCarpetaLibraries.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaLibraries.setColumns(10);
		txtCarpetaLibraries.setBounds(156, 449, 197, 28);
		contentPane.add(txtCarpetaLibraries);
		
		//Permite buscar una carpeta y obtener su direcci�n
		JButton btnBuscarSources = new JButton("...");
		btnBuscarSources.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File yourFolder = fc.getSelectedFile();
				    txtCarpetaSources.setText(yourFolder.getName());
				}
			}
		});
		btnBuscarSources.setBounds(363, 388, 43, 23);
		contentPane.add(btnBuscarSources);
		
		//Permite buscar una carpeta y obtener su direcci�n
		JButton btnBuscarBinaries = new JButton("...");
		btnBuscarBinaries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File yourFolder = fc.getSelectedFile();
				    txtCarpetaBinaries.setText(yourFolder.getName());
				}
			}
		});
		btnBuscarBinaries.setBounds(363, 420, 43, 23);
		contentPane.add(btnBuscarBinaries);
		
		//Permite buscar una carpeta y obtener su direcci�n
		JButton btnBuscarLibraries = new JButton("...");
		btnBuscarLibraries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File yourFolder = fc.getSelectedFile();
				    txtCarpetaLibraries.setText(yourFolder.getName());
				}
			}
		});
		btnBuscarLibraries.setBounds(363, 452, 43, 23);
		contentPane.add(btnBuscarLibraries);
		
		JLabel lblTituloDeOrganizacion = new JLabel("T\u00EDtulo de Organizaci\u00F3n");
		lblTituloDeOrganizacion.setForeground(Color.WHITE);
		lblTituloDeOrganizacion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblTituloDeOrganizacion.setBounds(176, 52, 138, 16);
		contentPane.add(lblTituloDeOrganizacion);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setForeground(Color.WHITE);
		lblDescripcion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblDescripcion.setBounds(207, 113, 81, 16);
		contentPane.add(lblDescripcion);
		
		JTextArea txtDescripcion = new JTextArea();
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setEditable(false);
		txtDescripcion.setBounds(84, 136, 320, 78);
		contentPane.add(txtDescripcion);
		
		//Carga los datos de la organizaci�n en base a la que se elige en la lista desplegable
		JComboBox cbTituloDeOrganizacion = new JComboBox();
		cbTituloDeOrganizacion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String idTitulo = String.valueOf(cbTituloDeOrganizacion.getSelectedItem());
				int firstSpace = idTitulo.indexOf(" ");
				String id = idTitulo.substring(0 , firstSpace);
				
				try {
					SQLiteDataSource ds = new SQLiteDataSource();
					ds.setUrl("jdbc:sqlite:SonarJUploader.db");
					Connection conn = ds.getConnection();
					String query =  "SELECT * FROM organizaciones WHERE ID = '"+id+"'";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery( query );
					
					txtDescripcion.setText(rs.getString( "DESCRIPCION" ));
					
					String projectKey = "organizacion.sonar:nombreproyecto";
					projectKey = projectKey.replace("organizacion", rs.getString( "NOMBRESONAR" ));
					projectKey = projectKey.replace("nombreproyecto", txtProjectName.getText());
					txtProjectKey.setText(projectKey);
					
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al cargar los datos de la organizacion.");
				}
				
			}
		});
		cbTituloDeOrganizacion.setBounds(84, 79, 320, 23);
		contentPane.add(cbTituloDeOrganizacion);
		
		JLabel lblFondo = new JLabel("");
		Image imgFondo = new ImageIcon(this.getClass().getResource("/background.jpg")).getImage();
		lblFondo.setIcon(new ImageIcon(imgFondo));
		lblFondo.setBounds(0, 0, 494, 529);
		contentPane.add(lblFondo);
		
		//Carga las organizaciones a la lista desplegable
		if (Integer.parseInt(Principal.lblIDValue.getText()) >= 2) {
			try {
				SQLiteDataSource ds = new SQLiteDataSource();
				ds.setUrl("jdbc:sqlite:SonarJUploader.db");
				Connection conn = ds.getConnection();
				String query =  "SELECT * FROM organizaciones WHERE USUARIOID = '"+Principal.lblIDValue.getText()+"'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery( query );
				
				if (rs.next() == false) {
					cbTituloDeOrganizacion.enable(false);
					txtDescripcion.enable(false);
				}
				else {
					rs = stmt.executeQuery( query );
					txtDescripcion.setText(rs.getString( "DESCRIPCION" ));
					
					String projectKey = txtProjectKey.getText();
					projectKey = projectKey.replace("organizacion", rs.getString( "NOMBRESONAR" ));
					txtProjectKey.setText(projectKey);
					
					while ( rs.next() ) {
						int id = rs.getInt( "ID" );
						String titulo = rs.getString( "TITULO" );
						cbTituloDeOrganizacion.addItem(id+" - "+titulo);
					}
				}
				
				conn.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Error al cargar los datos de las organizaciones.");
			}
		}
		else {
			cbTituloDeOrganizacion.enable(false);
			txtDescripcion.enable(false);
		}
	}
}
