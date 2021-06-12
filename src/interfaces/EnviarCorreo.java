package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.sqlite.SQLiteDataSource;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class EnviarCorreo extends JFrame {

	private JPanel contentPane;
	private JTextField txtCorreoDestino;
	private JTextField txtCorreoAdmin;
	private JPasswordField txtContraseña;
	private JTextField txtUsuario;
	private JTextField txtServer;
	private JTextField txtPort;
	private String ContraseñaUsuario;

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
					EnviarCorreo frame = new EnviarCorreo(pID);
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
	public EnviarCorreo(String pID) {
		setTitle("Sonar JUploader");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnviarCorreo = new JLabel("~ Enviar Correo con Gmail ~");
		lblEnviarCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnviarCorreo.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblEnviarCorreo.setBounds(10, 11, 414, 16);
		contentPane.add(lblEnviarCorreo);
		
		JLabel lblCorreoDestino = new JLabel("Correo Destino:");
		lblCorreoDestino.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCorreoDestino.setBounds(37, 247, 95, 16);
		contentPane.add(lblCorreoDestino);
		
		txtCorreoDestino = new JTextField();
		txtCorreoDestino.setEditable(false);
		txtCorreoDestino.setHorizontalAlignment(SwingConstants.CENTER);
		txtCorreoDestino.setColumns(10);
		txtCorreoDestino.setBounds(138, 242, 197, 28);
		contentPane.add(txtCorreoDestino);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtCorreoAdmin.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el correo del administrador.");
					return;
				}
				
				if (txtContraseña.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña del correo del administrador.");
					return;
				}
				
				if (txtServer.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el servidor SMTP.");
					return;
				}
				
				if (txtPort.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Debe ingresar el puerto del servidor SMTP.");
					return;
				}
				
				try {
					Email email = EmailBuilder.startingBlank()
						    .from("Sonar JUploader's Admin", txtCorreoAdmin.getText())
						    .to(txtUsuario.getText(), txtCorreoDestino.getText())
						    .withSubject("Recuperación de contraseña de Sonar JUploader")
						    .withPlainText("Su usuario de Sonar JUploader es: "+ txtUsuario.getText() +" y su contraseña es: "+ContraseñaUsuario)
						    .buildEmail();

					MailerBuilder
						  .withSMTPServer(txtServer.getText(), Integer. parseInt(txtPort.getText()), txtCorreoAdmin.getText(), txtContraseña.getText())
						  //.withTransportStrategy(TransportStrategy.SMTP_TLS)
						  .buildMailer()
						  .sendMail(email);
					JOptionPane.showMessageDialog(null, "Email enviado.");
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al enviar el email. Debe activar la opción 'Permitir aplicaciones menos seguras' en la cuenta de google.");
				}
				
				
				
			}
		});
		btnEnviar.setBounds(138, 281, 99, 30);
		contentPane.add(btnEnviar);
		
		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdmUsuarios.frame.setEnabled(true);
				AdmUsuarios.frame.toFront();
				dispose();
			}
		});
		btnCancelar.setBounds(236, 281, 99, 30);
		contentPane.add(btnCancelar);
		
		JLabel lblCorreoAdmin = new JLabel("Correo Admin:");
		lblCorreoAdmin.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCorreoAdmin.setBounds(42, 53, 89, 16);
		contentPane.add(lblCorreoAdmin);
		
		txtCorreoAdmin = new JTextField();
		txtCorreoAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		txtCorreoAdmin.setColumns(10);
		txtCorreoAdmin.setBounds(138, 48, 197, 28);
		contentPane.add(txtCorreoAdmin);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(138, 87, 197, 28);
		contentPane.add(txtContraseña);
		
		JLabel lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblContraseña.setBounds(58, 92, 73, 16);
		contentPane.add(lblContraseña);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblServer.setBounds(90, 131, 41, 16);
		contentPane.add(lblServer);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblPort.setBounds(103, 169, 28, 16);
		contentPane.add(lblPort);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblUsuario.setBounds(83, 208, 49, 16);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(138, 203, 197, 28);
		contentPane.add(txtUsuario);
		
		txtServer = new JTextField();
		txtServer.setText("smtp.gmail.com");
		txtServer.setHorizontalAlignment(SwingConstants.CENTER);
		txtServer.setColumns(10);
		txtServer.setBounds(138, 126, 197, 28);
		contentPane.add(txtServer);
		
		txtPort = new JTextField();
		txtPort.setText("25");
		txtPort.setHorizontalAlignment(SwingConstants.CENTER);
		txtPort.setColumns(10);
		txtPort.setBounds(138, 165, 197, 28);
		contentPane.add(txtPort);
		
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM usuarios WHERE ID = '"+pID+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( query );
			
			String nombre = rs.getString( "NOMBRE" );
			String correo = rs.getString( "CORREO" );
			ContraseñaUsuario = rs.getString( "PASSWORD" );
			
			txtUsuario.setText(nombre);
			txtCorreoDestino.setText(correo);
			
					
			query =  "SELECT * FROM usuarios WHERE ID = '1'";
			rs = stmt.executeQuery( query );
			
			correo = rs.getString( "CORREO" );
			txtCorreoAdmin.setText(correo);
			
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar los datos del usuario.");
			Principal.frmJuploader.setEnabled(true);
			dispose();
		}
	}
}
