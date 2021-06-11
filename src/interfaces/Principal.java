package interfaces;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.sqlite.SQLiteDataSource;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Principal {
	
	static JFrame frmJuploader;
	static JLabel lblUsuarioValue;
	static JLabel lblIDValue;

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
					Principal window = new Principal(args);
					window.frmJuploader.setVisible(true);
					window.frmJuploader.setLocationRelativeTo(null);
					window.frmJuploader.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal(String[] args) {
		initialize(args);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] args) {
		frmJuploader = new JFrame();
		frmJuploader.setTitle("Sonar JUploader");
		frmJuploader.setResizable(false);
		frmJuploader.setBounds(100, 100, 600, 400);
		frmJuploader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmJuploader.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("New menu");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		frmJuploader.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Analizar Proyectos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				Analizador.main(null);

			}
		});
		btnNewButton.setBounds(175, 187, 239, 23);
		frmJuploader.getContentPane().add(btnNewButton);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCerrar.setBounds(443, 305, 131, 23);
		frmJuploader.getContentPane().add(btnCerrar);
		
		JButton btnPreconfigurarProyecto = new JButton("Pre-configurar Proyecto");
		btnPreconfigurarProyecto.setBounds(175, 153, 239, 23);
		frmJuploader.getContentPane().add(btnPreconfigurarProyecto);
		
		JButton btnAdministrarOrganizaciones = new JButton("Administrar Organizaciones de Sonar Cloud");
		btnAdministrarOrganizaciones.setBounds(175, 119, 239, 23);
		frmJuploader.getContentPane().add(btnAdministrarOrganizaciones);
		
		JButton btnReportes = new JButton("Visualizar Reportes");
		btnReportes.setBounds(175, 221, 239, 23);
		frmJuploader.getContentPane().add(btnReportes);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
				frmJuploader.dispose();
			}
		});
		btnCerrarSesion.setBounds(443, 275, 131, 23);
		frmJuploader.getContentPane().add(btnCerrarSesion);
		
		JButton btnBorrarUsuario = new JButton("Borrar Usuario");
		btnBorrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "¿Desea realmente borrar su usuario?", "Borrar Usuario", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					
					try {
						SQLiteDataSource ds = new SQLiteDataSource();
						ds.setUrl("jdbc:sqlite:SonarJUploader.db");
						Connection conn = ds.getConnection();
						String query = "DELETE FROM usuarios WHERE ( ID = '"+Principal.lblIDValue.getText()+"' )";
						Statement stmt = conn.createStatement();
						int rv = stmt.executeUpdate( query );
						conn.close();
						JOptionPane.showMessageDialog(null, "Usuario borrado exitosamente.");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "No se pudo borrar el usuario.");
					}
					Login.main(null);
					frmJuploader.dispose();
				}
			}
		});
		btnBorrarUsuario.setBounds(10, 305, 131, 23);
		frmJuploader.getContentPane().add(btnBorrarUsuario);
		
		JLabel lblSonarJuploader = new JLabel("~ Sonar JUploader ~");
		lblSonarJuploader.setHorizontalAlignment(SwingConstants.CENTER);
		lblSonarJuploader.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblSonarJuploader.setBounds(10, 21, 564, 23);
		frmJuploader.getContentPane().add(lblSonarJuploader);
		
		lblUsuarioValue = new JLabel(args[1]);
		lblUsuarioValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuarioValue.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblUsuarioValue.setBounds(294, 78, 200, 23);
		frmJuploader.getContentPane().add(lblUsuarioValue);
		
		lblIDValue = new JLabel(args[0]);
		lblIDValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDValue.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblIDValue.setBounds(294, 55, 200, 23);
		frmJuploader.getContentPane().add(lblIDValue);
		
		JButton btnEditarUsuario = new JButton("Editar Usuario");
		btnEditarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				EditarUsuario.main(lblIDValue.getText());
			}
		});
		btnEditarUsuario.setBounds(10, 275, 131, 23);
		frmJuploader.getContentPane().add(btnEditarUsuario);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setHorizontalAlignment(SwingConstants.LEFT);
		lblId.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblId.setBounds(264, 55, 20, 23);
		frmJuploader.getContentPane().add(lblId);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblUsuario.setBounds(226, 78, 58, 23);
		frmJuploader.getContentPane().add(lblUsuario);
		
		JButton btnAdministrarUsuarios = new JButton("Administrar Usuarios");
		btnAdministrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				AdmUsuarios.main(null);
			}
		});
		btnAdministrarUsuarios.setBounds(175, 255, 239, 23);
		frmJuploader.getContentPane().add(btnAdministrarUsuarios);
		
		if (Integer.valueOf(lblIDValue.getText()) != 1) {
			btnAdministrarUsuarios.hide();
		}
		if (Integer.valueOf(lblIDValue.getText()) == 1) {
			btnBorrarUsuario.setEnabled(false);
		}
	}
}
