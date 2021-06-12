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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class EditarUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtContraseña;
	private JTextField txtCorreo;

	/**
	 * Launch the application.
	 */
	public static void main(String pID) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		UIManager.put("OptionPane.messageFont", new Font("Rockwell", Font.PLAIN, 14));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarUsuario frame = new EditarUsuario(pID);
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
	public EditarUsuario(String pID) {
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditarUsuario = new JLabel("~ Editar Usuario ~");
		lblEditarUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarUsuario.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblEditarUsuario.setBounds(10, 25, 414, 16);
		contentPane.add(lblEditarUsuario);
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario:");
		lblNombreUsuario.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblNombreUsuario.setBounds(29, 75, 126, 16);
		contentPane.add(lblNombreUsuario);
		
		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setColumns(10);
		txtNombre.setBounds(152, 70, 197, 28);
		contentPane.add(txtNombre);
		
		JLabel lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblContraseña.setBounds(75, 114, 80, 16);
		contentPane.add(lblContraseña);
		
		txtContraseña = new JTextField();
		txtContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(152, 109, 197, 28);
		contentPane.add(txtContraseña);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNombre.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario.");
					return;
				}
				
				if (txtContraseña.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar una contraseña.");
					return;
				}
				
				if (txtCorreo.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un correo.");
					return;
				}
				
				try {
					SQLiteDataSource ds = new SQLiteDataSource();
					ds.setUrl("jdbc:sqlite:SonarJUploader.db");
					Connection conn = ds.getConnection();
					String query =  "SELECT * FROM usuarios WHERE ID != '"+pID+"'";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery( query );
					
					while ( rs.next() ) {
					    String nombre = rs.getString( "NOMBRE" );

					    if (txtNombre.getText().equals(nombre)) {
					    	JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe.");
					    	conn.close();
					    	return;
					    }
					}
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al verificar si existe el usuario.");
					return;
				}
				
				try {
					SQLiteDataSource ds = new SQLiteDataSource();
					ds.setUrl("jdbc:sqlite:SonarJUploader.db");
					Connection conn = ds.getConnection();
					String query = "UPDATE usuarios SET NOMBRE = '"+txtNombre.getText()+"' WHERE ( ID = '"+pID+"' )";
					Statement stmt = conn.createStatement();
					int rv = stmt.executeUpdate( query );
					query = "UPDATE usuarios SET PASSWORD = '"+txtContraseña.getText()+"' WHERE ( ID = '"+pID+"' )";
					rv = stmt.executeUpdate( query );
					query = "UPDATE usuarios SET CORREO = '"+txtCorreo.getText()+"' WHERE ( ID = '"+pID+"' )";
					rv = stmt.executeUpdate( query );
					conn.close();
					Principal.lblUsuarioValue.setText(txtNombre.getText());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario.");
					Principal.frmJuploader.setEnabled(true);
					dispose();
					return;
				}
				JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.");
				
				try {
					AdmUsuarios.frame.dispose();
					AdmUsuarios.main(null);
				}
				catch (Exception e1) {
					Principal.frmJuploader.setEnabled(true);
				}
				dispose();
			}
		});
		btnAceptar.setBounds(152, 187, 99, 30);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (AdmUsuarios.frame.isVisible() == true) {
						AdmUsuarios.frame.setEnabled(true);
					}
					else {
						Principal.frmJuploader.setEnabled(true);
						Principal.frmJuploader.toFront();
					}
				}
				catch (Exception e1) {
					Principal.frmJuploader.setEnabled(true);
					Principal.frmJuploader.toFront();
				}				
				dispose();
			}
		});
		btnCancelar.setBounds(250, 187, 99, 30);
		contentPane.add(btnCancelar);
		
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCorreo.setBounds(102, 153, 53, 16);
		contentPane.add(lblCorreo);
		
		txtCorreo = new JTextField();
		txtCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(152, 148, 197, 28);
		contentPane.add(txtCorreo);
		
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM usuarios WHERE ID = '"+pID+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			
			String nombre = rs.getString( "NOMBRE" );
			String contraseña = rs.getString( "PASSWORD" );
			String correo = rs.getString( "CORREO" );
			
			txtNombre.setText(nombre);
			txtContraseña.setText(contraseña);
			txtCorreo.setText(correo);
			
			
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar los datos del usuario.");
			Principal.frmJuploader.setEnabled(true);
			dispose();
		}
	}

}
