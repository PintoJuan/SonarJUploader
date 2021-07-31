package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
		
		//Crea la base de datos y tablas si no existen
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "CREATE TABLE IF NOT EXISTS usuarios ( " +
	                 		"ID INTEGER PRIMARY KEY, " +
	                 		"NOMBRE TEXT NOT NULL, " +
	                 		"PASSWORD TEXT NOT NULL, " +
							"CORREO TEXT NOT NULL )";
			Statement stmt = conn.createStatement();
			int rv = stmt.executeUpdate( query );
			
			query =  "CREATE TABLE IF NOT EXISTS organizaciones ( " +
             		"ID INTEGER PRIMARY KEY, " +
             		"USUARIOID INTEGER NOT NULL, " +
             		"TITULO TEXT NOT NULL, " +
             		"DESCRIPCION TEXT, " +
             		"NOMBRESONAR TEXT NOT NULL, " +
             		"TOKEN TEXT NOT NULL, " +
					"CARPETA TEXT, " +
					"FOREIGN KEY(USUARIOID) REFERENCES usuarios(ID))";
			rv = stmt.executeUpdate( query );
			
			query =  "CREATE TABLE IF NOT EXISTS analisis ( " +
             		"ID INTEGER PRIMARY KEY, " +
             		"USUARIOID INTEGER NOT NULL, " +
             		"ORGANIZACIONID INTEGER NOT NULL, " +
             		"ORGANIZACIONNOMBRE TEXT, " +
             		"DIA INTEGER NOT NULL, " +
             		"MES INTEGER NOT NULL, " +
             		"ANIO INTEGER NOT NULL, " +
             		"HORA INTEGER NOT NULL, " +
             		"MINUTO INTEGER NOT NULL, " +
             		"CANTIDAD INTEGER NOT NULL, " +
             		"LISTA TEXT NOT NULL, " +
					"CARPETA TEXT, " +
					"FOREIGN KEY(USUARIOID) REFERENCES usuarios(ID)" +
					"FOREIGN KEY(ORGANIZACIONID) REFERENCES organizaciones(ID))";
			rv = stmt.executeUpdate( query );
			
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Existe un error con la base de datos.");
		}
		
		//Controla si la tabla de usuarios est� vac�a
		boolean insertAdmin = false;
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM usuarios";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			
			if (rs.next() == false) {
				insertAdmin = true;
			}
			
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Existe un error con la verificaci�n de tabla de usuarios vac�a.");
		}
		
		//Si la tabla de usuarios est� vac�a, crea la cuenta de administraci�n
		if (insertAdmin == true) {
			try {
				SQLiteDataSource ds = new SQLiteDataSource();
				ds.setUrl("jdbc:sqlite:SonarJUploader.db");
				Connection conn = ds.getConnection();
				String query = "INSERT INTO usuarios (NOMBRE, PASSWORD, CORREO) VALUES ('admin', 'admin', 'admin@admin.com')";
				Statement stmt = conn.createStatement();
				int rv = stmt.executeUpdate( query );
				conn.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Existe un error con la creaci�n del usuario vac�a administrador.");
			}
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 283);
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
		
		txtContrase�a = new JPasswordField();
		txtContrase�a.setHorizontalAlignment(SwingConstants.CENTER);
		txtContrase�a.setColumns(10);
		txtContrase�a.setBounds(162, 122, 197, 28);
		contentPane.add(txtContrase�a);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Controla que los campos no est�n vac�os
				if (txtNombre.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario.");
					return;
				}
				
				if (txtContrase�a.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar una contrase�a.");
					return;
				}
				
				//Contrasta el usuario y contrase�a con la base de datos, si son correctos, ingresa a la pantalla principal
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
		btnIngresar.setBounds(266, 161, 99, 30);
		contentPane.add(btnIngresar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cierra el programa
				System.exit(0);
			}
		});
		btnCerrar.setBounds(266, 193, 99, 30);
		contentPane.add(btnCerrar);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abre la pantalla de creaci�n de usuario
				frame.setEnabled(false);
				CrearUsuario.main(null);
			}
		});
		btnCrearUsuario.setBounds(157, 162, 99, 28);
		contentPane.add(btnCrearUsuario);
		
		JButton btnInvitado = new JButton("Invitado");
		btnInvitado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Ingresa a la pantalla principal como invitado, sin cuenta de usuario
				String[] idnombre = new String[2];
		    	idnombre[0] = "0";
		    	idnombre[1] = "Invitado";
		    	Principal.main(idnombre);
				dispose();
			}
		});
		btnInvitado.setBounds(157, 194, 99, 28);
		contentPane.add(btnInvitado);
	}
}
