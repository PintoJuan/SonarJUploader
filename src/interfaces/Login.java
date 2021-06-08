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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtContrase�a;
	static Login frame;

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
		
		
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "CREATE TABLE IF NOT EXISTS usuarios ( " +
	                 		"ID INTEGER PRIMARY KEY, " +
	                 		"NOMBRE TEXT NOT NULL, " +
							"PASSWORD TEXT NOT NULL )";
			Statement stmt = conn.createStatement();
			int rv = stmt.executeUpdate( query );
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Existe un error con la base de datos.");
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Login frame = new Login();
					frame = new Login();
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
	public Login() {
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTituloLogin = new JLabel("~ Inicio de Sesi\u00F3n ~");
		lblTituloLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloLogin.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblTituloLogin.setBounds(10, 38, 464, 16);
		contentPane.add(lblTituloLogin);
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario:");
		lblNombreUsuario.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblNombreUsuario.setBounds(39, 88, 126, 16);
		contentPane.add(lblNombreUsuario);
		
		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setColumns(10);
		txtNombre.setBounds(162, 83, 197, 28);
		contentPane.add(txtNombre);
		
		JLabel lblContrase�a = new JLabel("Contrase\u00F1a:");
		lblContrase�a.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblContrase�a.setBounds(85, 127, 80, 16);
		contentPane.add(lblContrase�a);
		
		txtContrase�a = new JTextField();
		txtContrase�a.setHorizontalAlignment(SwingConstants.CENTER);
		txtContrase�a.setColumns(10);
		txtContrase�a.setBounds(162, 122, 197, 28);
		contentPane.add(txtContrase�a);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtNombre.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario.");
					return;
				}
				
				if (txtContrase�a.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar una contrase�a.");
					return;
				}
				
				try {
					SQLiteDataSource ds = new SQLiteDataSource();
					ds.setUrl("jdbc:sqlite:SonarJUploader.db");
					Connection conn = ds.getConnection();
					String query =  "SELECT * FROM usuarios";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery( query );
					
					while ( rs.next() ) {
					    int id = rs.getInt( "ID" );
					    String nombre = rs.getString( "NOMBRE" );
					    String contrase�a = rs.getString( "PASSWORD" );

					    if (txtNombre.getText().equals(nombre) && txtContrase�a.getText().equals(contrase�a)) {
					    	String[] idnombre = new String[2];
					    	idnombre[0] = Integer.toString(id);
					    	idnombre[1] = nombre;
					    	Principal.main(idnombre);
							dispose();
							break;
					    }
					}
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al iniciar sesi�n.");
				}
				
				if (frame.isShowing()) {
					JOptionPane.showMessageDialog(null, "Usuario/Contrase�a err�neo o inexistente.");
				}
				
			}
		});
		btnIngresar.setBounds(204, 161, 99, 30);
		contentPane.add(btnIngresar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCerrar.setBounds(312, 161, 99, 30);
		contentPane.add(btnCerrar);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(false);
				CrearUsuario.main(null);
			}
		});
		btnCrearUsuario.setBounds(95, 162, 99, 28);
		contentPane.add(btnCrearUsuario);
	}
}
