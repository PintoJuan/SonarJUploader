package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class EditarUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarUsuario frame = new EditarUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditarUsuario() {
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 240);
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
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		textField.setBounds(152, 70, 197, 28);
		contentPane.add(textField);
		
		JLabel lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblContraseña.setBounds(75, 114, 80, 16);
		contentPane.add(lblContraseña);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setColumns(10);
		textField_1.setBounds(152, 109, 197, 28);
		contentPane.add(textField_1);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(152, 148, 99, 30);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(250, 148, 99, 30);
		contentPane.add(btnCancelar);
	}

}
