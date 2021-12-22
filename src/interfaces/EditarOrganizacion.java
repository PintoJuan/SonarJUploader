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

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class EditarOrganizacion extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtDescripcion;
	private JTextField txtNombreSonar;
	private JTextField txtToken;
	private JTextField txtCarpetaProyectos;

	/**
	 * Launch the application.
	 */
	public static void main(String pID) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		UIManager.put("OptionPane.messageFont", new Font("Rockwell", Font.PLAIN, 14));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarOrganizacion frame = new EditarOrganizacion(pID);
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
	public EditarOrganizacion(String pID) {
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditarOrganizacin = new JLabel("~ Editar Organizaci\u00F3n de Sonar Cloud ~");
		lblEditarOrganizacin.setForeground(Color.WHITE);
		lblEditarOrganizacin.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarOrganizacin.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblEditarOrganizacin.setBounds(10, 11, 414, 24);
		contentPane.add(lblEditarOrganizacin);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo:");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblTitulo.setBounds(103, 64, 39, 16);
		contentPane.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitulo.setColumns(10);
		txtTitulo.setBounds(146, 59, 197, 28);
		contentPane.add(txtTitulo);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setForeground(Color.WHITE);
		lblDescripcion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblDescripcion.setBounds(65, 96, 77, 16);
		contentPane.add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(146, 91, 197, 28);
		contentPane.add(txtDescripcion);
		
		JLabel lblNombreSonar = new JLabel("Nombre Sonar:");
		lblNombreSonar.setForeground(Color.WHITE);
		lblNombreSonar.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblNombreSonar.setBounds(53, 128, 89, 16);
		contentPane.add(lblNombreSonar);
		
		txtNombreSonar = new JTextField();
		txtNombreSonar.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombreSonar.setColumns(10);
		txtNombreSonar.setBounds(146, 123, 197, 28);
		contentPane.add(txtNombreSonar);
		
		JLabel lblToken = new JLabel("Token:");
		lblToken.setForeground(Color.WHITE);
		lblToken.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblToken.setBounds(100, 160, 42, 16);
		contentPane.add(lblToken);
		
		txtToken = new JTextField();
		txtToken.setHorizontalAlignment(SwingConstants.CENTER);
		txtToken.setColumns(10);
		txtToken.setBounds(146, 155, 197, 28);
		contentPane.add(txtToken);
		
		JLabel lblCarpeta = new JLabel("Carpeta de Proyectos:");
		lblCarpeta.setForeground(Color.WHITE);
		lblCarpeta.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpeta.setBounds(10, 192, 132, 16);
		contentPane.add(lblCarpeta);
		
		txtCarpetaProyectos = new JTextField();
		txtCarpetaProyectos.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaProyectos.setColumns(10);
		txtCarpetaProyectos.setBounds(146, 187, 197, 28);
		contentPane.add(txtCarpetaProyectos);
		
		//Edita la información de la organización en la base de datos en base a los datos ingresados por el usuario
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtTitulo.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un título para la organización.");
					return;
				}
				
				if (txtNombreSonar.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el nombre en Sonar de la organización.");
					return;
				}
				
				if (txtToken.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el token de seguridad de la organización.");
					return;
				}
				
				try {
					SQLiteDataSource ds = new SQLiteDataSource();
					ds.setUrl("jdbc:sqlite:SonarJUploader.db");
					Connection conn = ds.getConnection();
					String query = "UPDATE organizaciones SET TITULO = '"+txtTitulo.getText()+"' WHERE ( ID = '"+pID+"' )";
					Statement stmt = conn.createStatement();
					int rv = stmt.executeUpdate( query );
					query = "UPDATE organizaciones SET DESCRIPCION = '"+txtDescripcion.getText()+"' WHERE ( ID = '"+pID+"' )";
					rv = stmt.executeUpdate( query );
					query = "UPDATE organizaciones SET NOMBRESONAR = '"+txtNombreSonar.getText()+"' WHERE ( ID = '"+pID+"' )";
					rv = stmt.executeUpdate( query );
					query = "UPDATE organizaciones SET TOKEN = '"+txtToken.getText()+"' WHERE ( ID = '"+pID+"' )";
					rv = stmt.executeUpdate( query );
					query = "UPDATE organizaciones SET CARPETA = '"+txtCarpetaProyectos.getText()+"' WHERE ( ID = '"+pID+"' )";
					rv = stmt.executeUpdate( query );
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "No se pudieron actualizar los datos de la organización.");
					AdmOrganizaciones.frame.setEnabled(true);
					AdmOrganizaciones.frame.toFront();
					dispose();
					return;
				}
				JOptionPane.showMessageDialog(null, "Organización actualizada exitosamente.");
				
				AdmOrganizaciones.frame.setEnabled(true);
				AdmOrganizaciones.frame.dispose();
				AdmOrganizaciones.main(null);
				AdmOrganizaciones.frame.toFront();
				dispose();
			}
		});
		btnAceptar.setBounds(146, 219, 99, 30);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdmOrganizaciones.frame.setEnabled(true);
				AdmOrganizaciones.frame.toFront();
				dispose();
			}
		});
		btnCancelar.setBounds(244, 219, 99, 30);
		contentPane.add(btnCancelar);
		
		//Permite buscar una carpeta y obtener su dirección
		JButton btnBuscar = new JButton("...");
		btnBuscar.addActionListener(new ActionListener() {
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
		btnBuscar.setBounds(353, 190, 43, 23);
		contentPane.add(btnBuscar);
		
		//Carga los datos de la organizacion
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM organizaciones WHERE ID = '"+pID+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			
		    String titulo = rs.getString( "TITULO" );
		    String descripcion = rs.getString( "DESCRIPCION" );
		    String nombresonar = rs.getString( "NOMBRESONAR" );
		    String token = rs.getString( "TOKEN" );
		    String carpeta = rs.getString( "CARPETA" );
			
			txtTitulo.setText(titulo);
			txtDescripcion.setText(descripcion);
			txtNombreSonar.setText(nombresonar);
			txtToken.setText(token);
			txtCarpetaProyectos.setText(carpeta);
			
			JLabel lblFondo = new JLabel("");
			Image imgFondo = new ImageIcon(this.getClass().getResource("/background.jpg")).getImage();
			lblFondo.setIcon(new ImageIcon(imgFondo));
			lblFondo.setBounds(0, 0, 434, 261);
			contentPane.add(lblFondo);
			
			
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar los datos de las organizaciones.");
			AdmOrganizaciones.frame.setEnabled(true);
			AdmOrganizaciones.frame.toFront();
			dispose();
		}
	}
}
