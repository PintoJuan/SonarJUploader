package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.sqlite.SQLiteDataSource;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import java.awt.Color;

public class Analizador extends JFrame {

	private JPanel contentPane;
	private JTextField txtOrganizacion;
	private JTextField txtToken;
	private JTextField txtCarpetaProyectos;

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
		//GUIConsole console = new GUIConsole();
		//console.run("MyGUIConsole",200,100);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Analizador frame = new Analizador();
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
	public Analizador() {
		setTitle("Sonar JUploader");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOrganizacion = new JLabel("Organizaci\u00F3n");
		lblOrganizacion.setForeground(Color.WHITE);
		lblOrganizacion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblOrganizacion.setBounds(174, 241, 81, 16);
		contentPane.add(lblOrganizacion);
		
		txtOrganizacion = new JTextField();
		txtOrganizacion.setHorizontalAlignment(SwingConstants.CENTER);
		txtOrganizacion.setColumns(10);
		txtOrganizacion.setBounds(51, 261, 320, 28);
		contentPane.add(txtOrganizacion);
		
		JLabel lblToken = new JLabel("Token");
		lblToken.setForeground(Color.WHITE);
		lblToken.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblToken.setBounds(198, 300, 38, 16);
		contentPane.add(lblToken);
		
		txtToken = new JTextField();
		txtToken.setHorizontalAlignment(SwingConstants.CENTER);
		txtToken.setColumns(10);
		txtToken.setBounds(51, 318, 320, 28);
		contentPane.add(txtToken);
		
		JLabel lblCarpetaProyectos = new JLabel("Carpeta de Proyectos");
		lblCarpetaProyectos.setForeground(Color.WHITE);
		lblCarpetaProyectos.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpetaProyectos.setBounds(155, 357, 126, 16);
		contentPane.add(lblCarpetaProyectos);
		
		txtCarpetaProyectos = new JTextField();
		txtCarpetaProyectos.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaProyectos.setColumns(10);
		txtCarpetaProyectos.setBounds(51, 378, 320, 28);
		contentPane.add(txtCarpetaProyectos);
		
		JButton btnBuscar = new JButton("...");
		btnBuscar.addActionListener(new ActionListener() {
			//Permite buscar un directorio de proyectos y obtener su dirección para el analizador
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File yourFolder = fc.getSelectedFile();
				    txtCarpetaProyectos.setText(yourFolder.getAbsolutePath());
				}
			}
		});
		btnBuscar.setBounds(381, 381, 43, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblTituloAnalizador = new JLabel("~ Analizar con Sonar Cloud ~");
		lblTituloAnalizador.setForeground(Color.WHITE);
		lblTituloAnalizador.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblTituloAnalizador.setBounds(69, 26, 285, 16);
		contentPane.add(lblTituloAnalizador);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cierra la ventana de análisis y habilita la ventana principal
				try {
					Principal.frmJuploader.setEnabled(true);
					Principal.frmJuploader.toFront();
					dispose();
				} catch (Exception e1) {
					System.exit(0);
				}
				
				
			}
		});
		btnCerrar.setBounds(335, 417, 89, 23);
		contentPane.add(btnCerrar);
		
		JLabel lblTituloDeOrganizacion = new JLabel("T\u00EDtulo de Organizaci\u00F3n");
		lblTituloDeOrganizacion.setForeground(Color.WHITE);
		lblTituloDeOrganizacion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblTituloDeOrganizacion.setBounds(143, 68, 138, 16);
		contentPane.add(lblTituloDeOrganizacion);
		
		JTextArea txtDescripcion = new JTextArea();
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setEditable(false);
		txtDescripcion.setBounds(51, 152, 320, 78);
		contentPane.add(txtDescripcion);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setForeground(Color.WHITE);
		lblDescripcion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblDescripcion.setBounds(174, 129, 81, 16);
		contentPane.add(lblDescripcion);
		
		JComboBox cbTituloDeOrganizacion = new JComboBox();
		cbTituloDeOrganizacion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//Actualiza los datos de la organización según lo seleccionado en la lista
				
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
					txtOrganizacion.setText(rs.getString( "NOMBRESONAR" ));
					txtToken.setText(rs.getString( "TOKEN" ));
					txtCarpetaProyectos.setText(rs.getString( "CARPETA" ));
					
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al cargar los datos de la organizacion.");
				}
			}
		});
		cbTituloDeOrganizacion.setBounds(51, 95, 320, 23);
		contentPane.add(cbTituloDeOrganizacion);
		
		JCheckBox cbxProjectKeyAutomatico = new JCheckBox("ProjectKey Autom\u00E1tico");
		cbxProjectKeyAutomatico.setFont(new Font("Rockwell", Font.PLAIN, 12));
		cbxProjectKeyAutomatico.setForeground(Color.WHITE);
		cbxProjectKeyAutomatico.setBounds(161, 417, 148, 23);
		contentPane.add(cbxProjectKeyAutomatico);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread one = new Thread() {
					public void run() {
						try {
							//Se genera el análisis en base a los datos ingresados y se guarda la información para los reportes
							
							String organizacion = txtOrganizacion.getText();
							String token = txtToken.getText();
							String carpetaProyectos = txtCarpetaProyectos.getText();
							String pathfolder = carpetaProyectos;
							carpetaProyectos = "\"" + carpetaProyectos+"\"";
							
							if (cbTituloDeOrganizacion.isEnabled() == true) {
								String idTitulo = String.valueOf(cbTituloDeOrganizacion.getSelectedItem());
								int firstSpace = idTitulo.indexOf(" ");
								int startTitle = idTitulo.indexOf(" - ");
								String id = idTitulo.substring(0 , firstSpace);
								String titulo = idTitulo.substring(startTitle + 3 , idTitulo.length());
								
								Calendar currentDate = Calendar.getInstance();
								int anio = currentDate.get(Calendar.YEAR);
								int mes = currentDate.get(Calendar.MONTH) + 1;
								int dia = currentDate.get(Calendar.DAY_OF_MONTH);
								int hora = currentDate.get(Calendar.HOUR_OF_DAY);
								int minutos = currentDate.get(Calendar.MINUTE);
								
								List<String> fileNames = new ArrayList<>();
								String listaDeProyectos = "";
								try {
								      DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(pathfolder));
								      for (Path path : directoryStream) {
								        fileNames.add(path.toString());
								        listaDeProyectos = listaDeProyectos + ", " + path.getFileName().toString();
								      }
								} catch (IOException ex) {
									JOptionPane.showMessageDialog(null, "No se pudo obtener la información de la carpeta de análisis.");
								}
								
								listaDeProyectos = listaDeProyectos.substring(2 , listaDeProyectos.length());
								
								try {
									SQLiteDataSource ds = new SQLiteDataSource();
									ds.setUrl("jdbc:sqlite:SonarJUploader.db");
									Connection conn = ds.getConnection();
									String query = "INSERT INTO analisis ( USUARIOID, ORGANIZACIONID, ORGANIZACIONNOMBRE, DIA, MES, ANIO, HORA, MINUTO, CANTIDAD, LISTA, CARPETA ) VALUES ( '"+Integer.parseInt(Principal.lblIDValue.getText())+"', '"+Integer.parseInt(id)+"', '"+titulo+"', '"+dia+"', '"+mes+"', '"+anio+"', '"+hora+"', '"+minutos+"', '"+fileNames.size()+"', '"+listaDeProyectos+"', '"+pathfolder+"' )";
									Statement stmt = conn.createStatement();
									int rv = stmt.executeUpdate( query );
									conn.close();
								} catch (SQLException e1) {
									JOptionPane.showMessageDialog(null, "No se registrará el análisis en la base de datos.");
								}
							}
							
							String line;
							
							ProcessBuilder processBuilder = null;
							if (cbxProjectKeyAutomatico.isSelected()) {
								processBuilder = new ProcessBuilder("cmd.exe", "/K", "start", "scripts/Analyze-Projects-forced.sh", organizacion, token, carpetaProyectos);
							}
							else {
								processBuilder = new ProcessBuilder("cmd.exe", "/K", "start", "scripts/Analyze-Projects.sh", organizacion, token, carpetaProyectos);
							}
							
						    Process process = processBuilder.start();
						    BufferedReader reader =
						            new BufferedReader(new InputStreamReader(process.getInputStream()));
						    
						    while ((line = reader.readLine()) != null) {
						    	System.out.println(line);
						     } 
						    
						    int output = process.waitFor();

						    reader.close();
						}
						catch (IOException | InterruptedException e1) {
							e1.printStackTrace();
						}
				    }
				};
				    
				one.start();
			}
		});
		btnIniciar.setBounds(51, 417, 89, 23);
		contentPane.add(btnIniciar);
		
		JLabel lblFondo = new JLabel("");
		Image imgFondo = new ImageIcon(this.getClass().getResource("/background.jpg")).getImage();
		lblFondo.setIcon(new ImageIcon(imgFondo));
		lblFondo.setBounds(0, 0, 434, 451);
		contentPane.add(lblFondo);
		
		//Carga la lista de organizaciones del usuario, o inhabilita la lista si se ingresó como invitado
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
					txtOrganizacion.setText(rs.getString( "NOMBRESONAR" ));
					txtToken.setText(rs.getString( "TOKEN" ));
					txtCarpetaProyectos.setText(rs.getString( "CARPETA" ));
					
					
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
