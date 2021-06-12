package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.sqlite.SQLiteDataSource;

import general.NonEditableModel;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AdmUsuarios extends JFrame {

	private JPanel contentPane;
	private JTable tablaUsuarios;
	private JScrollPane scrollPane;
	private JLabel lblAdministrarUsuarios;
	private JButton btnEditar;
	static AdmUsuarios frame;

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
					frame = new AdmUsuarios();
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
	public AdmUsuarios() {
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.frmJuploader.setEnabled(true);
				Principal.frmJuploader.toFront();
				dispose();
			}
		});
		btnCerrar.setBounds(668, 402, 106, 23);
		contentPane.add(btnCerrar);
		
		ResultSet rs = null;
		ArrayList<String> IDs = new ArrayList<String>();
		ArrayList<String> Nombres = new ArrayList<String>();
		ArrayList<String> Contraseñas = new ArrayList<String>();
		ArrayList<String> Correos = new ArrayList<String>();
		CargarColumnas(rs, IDs, Nombres, Contraseñas, Correos);
		
		String[][] data = new String[Nombres.size()][4];
		for (int i=0; i < Nombres.size(); i++) {
			data[i][0] = IDs.get(i);
			data[i][1] = Nombres.get(i);
			data[i][2] = Contraseñas.get(i);
			data[i][3] = Correos.get(i);
		}
		String column[]={"ID","Nombre","Contraseña","Correo"};  
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 764, 326);
		contentPane.add(scrollPane);
		tablaUsuarios = new JTable();
		scrollPane.setViewportView(tablaUsuarios);
		tablaUsuarios.setModel(new NonEditableModel(data, column));
		tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		lblAdministrarUsuarios = new JLabel("~ Administrar Usuarios ~");
		lblAdministrarUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministrarUsuarios.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblAdministrarUsuarios.setBounds(10, 28, 764, 16);
		contentPane.add(lblAdministrarUsuarios);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaUsuarios.getSelectedRow();
				String id = (String) tablaUsuarios.getValueAt(fila, 0);
				frame.setEnabled(false);
				EditarUsuario.main(id);
			}
		});
		btnEditar.setBounds(10, 402, 106, 23);
		contentPane.add(btnEditar);
		
		JButton btnEnviarCorreo = new JButton("Enviar Correo");
		btnEnviarCorreo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaUsuarios.getSelectedRow();
				String id = (String) tablaUsuarios.getValueAt(fila, 0);
				frame.setEnabled(false);
				EnviarCorreo.main(id);
			}
		});
		btnEnviarCorreo.setBounds(126, 402, 106, 23);
		contentPane.add(btnEnviarCorreo);
		
		tablaUsuarios.setRowSelectionInterval(0, 0);
	}
	
	public void CargarColumnas (ResultSet rs, ArrayList<String> IDS, ArrayList<String> Nombres, ArrayList<String> Contraseñas, ArrayList<String> Correos) {
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM usuarios";
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery( query );
			
			while ( rs.next() ) {
			    int id = rs.getInt( "ID" );
			    String nombre = rs.getString( "NOMBRE" );
			    String contraseña = rs.getString( "PASSWORD" );
			    String correo = rs.getString( "CORREO" );
			    
			    IDS.add(Integer.toString(id));
			    Nombres.add(nombre);
			    Contraseñas.add(contraseña);
			    Correos.add(correo);
			    
			}
			conn.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al obtener información de usuarios.");
		}
	}
}
