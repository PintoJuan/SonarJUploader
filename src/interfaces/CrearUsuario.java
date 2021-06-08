package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.sqlite.SQLiteDataSource;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class CrearUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtContrase�a;

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
					CrearUsuario frame = new CrearUsuario();
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
	public CrearUsuario() {
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTituloCreacion = new JLabel("~ Nuevo Usuario ~");
		lblTituloCreacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCreacion.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblTituloCreacion.setBounds(10, 31, 414, 16);
		contentPane.add(lblTituloCreacion);
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario:");
		lblNombreUsuario.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblNombreUsuario.setBounds(29, 81, 126, 16);
		contentPane.add(lblNombreUsuario);
		
		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setColumns(10);
		txtNombre.setBounds(152, 76, 197, 28);
		contentPane.add(txtNombre);
		
		JLabel lblContrase�a = new JLabel("Contrase\u00F1a:");
		lblContrase�a.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblContrase�a.setBounds(75, 120, 80, 16);
		contentPane.add(lblContrase�a);
		
		txtContrase�a = new JTextField();
		txtContrase�a.setHorizontalAlignment(SwingConstants.CENTER);
		txtContrase�a.setColumns(10);
		txtContrase�a.setBounds(152, 115, 197, 28);
		contentPane.add(txtContrase�a);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
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
					    String nombre = rs.getString( "NOMBRE" );

					    if (txtNombre.getText().equals(nombre)) {
					    	JOptionPane.showMessageDialog(null, "El usuario ya existe.");
					    	conn.close();
					    	return;
					    }
					}
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al iniciar sesi�n.");
				}
				
				try {
					SQLiteDataSource ds = new SQLiteDataSource();
					ds.setUrl("jdbc:sqlite:SonarJUploader.db");
					Connection conn = ds.getConnection();
					String query = "INSERT INTO usuarios ( NOMBRE, PASSWORD ) VALUES ( '"+txtNombre.getText()+"', '"+txtContrase�a.getText()+"' )";
					Statement stmt = conn.createStatement();
					int rv = stmt.executeUpdate( query );
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "No se pudo crear el usuario.");
				}
				JOptionPane.showMessageDialog(null, "Usuario generado exitosamente.");
				Login.frame.setEnabled(true);
				dispose();
			}
		});
		btnAceptar.setBounds(152, 154, 99, 30);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.frame.setEnabled(true);
				dispose();
			}
		});
		btnCancelar.setBounds(250, 154, 99, 30);
		contentPane.add(btnCancelar);
	}
}
