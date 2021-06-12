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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class CrearOrganizacion extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtDescripcion;
	private JTextField txtNombreSonar;
	private JTextField txtToken;
	private JTextField txtCarpetaProyectos;

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
					CrearOrganizacion frame = new CrearOrganizacion();
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
	public CrearOrganizacion() {
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCrearOrganizacin = new JLabel("~ Crear Organizaci\u00F3n de Sonar Cloud ~");
		lblCrearOrganizacin.setHorizontalAlignment(SwingConstants.CENTER);
		lblCrearOrganizacin.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblCrearOrganizacin.setBounds(10, 11, 414, 24);
		contentPane.add(lblCrearOrganizacin);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo:");
		lblTitulo.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblTitulo.setBounds(103, 64, 39, 16);
		contentPane.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitulo.setColumns(10);
		txtTitulo.setBounds(146, 59, 197, 28);
		contentPane.add(txtTitulo);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblDescripcion.setBounds(65, 96, 77, 16);
		contentPane.add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(146, 91, 197, 28);
		contentPane.add(txtDescripcion);
		
		JLabel lblNombreSonar = new JLabel("Nombre Sonar:");
		lblNombreSonar.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblNombreSonar.setBounds(53, 128, 89, 16);
		contentPane.add(lblNombreSonar);
		
		txtNombreSonar = new JTextField();
		txtNombreSonar.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombreSonar.setColumns(10);
		txtNombreSonar.setBounds(146, 123, 197, 28);
		contentPane.add(txtNombreSonar);
		
		JLabel lblToken = new JLabel("Token:");
		lblToken.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblToken.setBounds(100, 160, 42, 16);
		contentPane.add(lblToken);
		
		txtToken = new JTextField();
		txtToken.setHorizontalAlignment(SwingConstants.CENTER);
		txtToken.setColumns(10);
		txtToken.setBounds(146, 155, 197, 28);
		contentPane.add(txtToken);
		
		JLabel lblCarpeta = new JLabel("Carpeta de Proyectos:");
		lblCarpeta.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpeta.setBounds(10, 192, 132, 16);
		contentPane.add(lblCarpeta);
		
		txtCarpetaProyectos = new JTextField();
		txtCarpetaProyectos.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaProyectos.setColumns(10);
		txtCarpetaProyectos.setBounds(146, 187, 197, 28);
		contentPane.add(txtCarpetaProyectos);
		
		JButton btnAceptar = new JButton("Aceptar");
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
					String query = "INSERT INTO organizaciones ( USUARIOID, TITULO, DESCRIPCION, NOMBRESONAR, TOKEN, CARPETA ) VALUES ( '"+Integer.parseInt(Principal.lblIDValue.getText())+"', '"+txtTitulo.getText()+"', '"+txtDescripcion.getText()+"', '"+txtNombreSonar.getText()+"', '"+txtToken.getText()+"', '"+txtCarpetaProyectos.getText()+"' )";
					Statement stmt = conn.createStatement();
					int rv = stmt.executeUpdate( query );
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "No se pudo crear la organización.");
					AdmOrganizaciones.frame.setEnabled(true);
					AdmOrganizaciones.frame.toFront();
					dispose();
					return;
				}
				JOptionPane.showMessageDialog(null, "Organización creada exitosamente.");
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
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdmOrganizaciones.frame.setEnabled(true);
				AdmOrganizaciones.frame.toFront();
				dispose();
			}
		});
		btnCancelar.setBounds(244, 219, 99, 30);
		contentPane.add(btnCancelar);
		
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
	}
}
