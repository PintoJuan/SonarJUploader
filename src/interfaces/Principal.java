package interfaces;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.sqlite.SQLiteDataSource;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.Color;

public class Principal {
	
	static JFrame frmJuploader;
	static JLabel lblUsuarioValue;
	static JLabel lblIDValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmWebSonarCloud = new JMenuItem("Web Sonar Cloud");
		mntmWebSonarCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url_open ="https://sonarcloud.io/";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
				} catch (IOException ex3) {
					JOptionPane.showMessageDialog(null, "No se pudo abrir el navegador web.");
				}
			}
		});
		mnArchivo.add(mntmWebSonarCloud);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnArchivo.add(mntmSalir);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Sonar JUploader es una herramienta para analizar lotes de proyectos java con Sonar Cloud. \n"
						+ "Fue creada en el contexto del Proyecto Final de Carrera del alumno Pinto Oppido Juan Alberto, \n"
						+ "en la carrera de Licenciatura en Sistemas de Información de la Universidad Nacional del Nordeste.");
			}
		});
		mnAyuda.add(mntmAcercaDe);
		frmJuploader.getContentPane().setLayout(null);
		
		JButton btnAnalizarProyecto = new JButton("Analizar Proyectos");
		btnAnalizarProyecto.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnAnalizarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				Analizador.main(null);

			}
		});
		btnAnalizarProyecto.setBounds(158, 180, 271, 23);
		frmJuploader.getContentPane().add(btnAnalizarProyecto);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCerrar.setBounds(443, 305, 131, 23);
		frmJuploader.getContentPane().add(btnCerrar);
		
		JButton btnPreconfigurarProyecto = new JButton("Pre-configurar Proyecto");
		btnPreconfigurarProyecto.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnPreconfigurarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				PreConfProyecto.main(null);
			}
		});
		btnPreconfigurarProyecto.setBounds(158, 146, 271, 23);
		frmJuploader.getContentPane().add(btnPreconfigurarProyecto);
		
		JButton btnAdministrarOrganizaciones = new JButton("Administrar Organizaciones de Sonar Cloud");
		btnAdministrarOrganizaciones.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnAdministrarOrganizaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				AdmOrganizaciones.main(null);
			}
		});
		btnAdministrarOrganizaciones.setBounds(158, 112, 271, 23);
		frmJuploader.getContentPane().add(btnAdministrarOrganizaciones);
		
		JButton btnReportes = new JButton("Visualizar Reportes");
		btnReportes.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				VisualizarReportes.main(null);
			}
		});
		btnReportes.setBounds(158, 214, 271, 23);
		frmJuploader.getContentPane().add(btnReportes);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
				frmJuploader.dispose();
			}
		});
		btnCerrarSesion.setBounds(443, 275, 131, 23);
		frmJuploader.getContentPane().add(btnCerrarSesion);
		
		JButton btnBorrarUsuario = new JButton("Borrar Usuario");
		btnBorrarUsuario.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnBorrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "¿Desea realmente borrar su usuario?", "Borrar Usuario", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					
					try {
						SQLiteDataSource ds = new SQLiteDataSource();
						ds.setUrl("jdbc:sqlite:SonarJUploader.db");
						Connection conn = ds.getConnection();
						String query = "DELETE FROM organizaciones WHERE ( USUARIOID = '"+Principal.lblIDValue.getText()+"' )";
						Statement stmt = conn.createStatement();
						int rv = stmt.executeUpdate( query );
						
						query = "DELETE FROM usuarios WHERE ( ID = '"+Principal.lblIDValue.getText()+"' )";
						rv = stmt.executeUpdate( query );
						
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
		lblSonarJuploader.setForeground(Color.WHITE);
		lblSonarJuploader.setHorizontalAlignment(SwingConstants.CENTER);
		lblSonarJuploader.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblSonarJuploader.setBounds(10, 21, 564, 23);
		frmJuploader.getContentPane().add(lblSonarJuploader);
		
		lblUsuarioValue = new JLabel(args[1]);
		lblUsuarioValue.setForeground(Color.WHITE);
		lblUsuarioValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuarioValue.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblUsuarioValue.setBounds(293, 78, 200, 23);
		frmJuploader.getContentPane().add(lblUsuarioValue);
		
		lblIDValue = new JLabel(args[0]);
		lblIDValue.setForeground(Color.WHITE);
		lblIDValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDValue.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblIDValue.setBounds(293, 55, 200, 23);
		frmJuploader.getContentPane().add(lblIDValue);
		
		JButton btnEditarUsuario = new JButton("Editar Usuario");
		btnEditarUsuario.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnEditarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				EditarUsuario.main(lblIDValue.getText());
			}
		});
		btnEditarUsuario.setBounds(10, 275, 131, 23);
		frmJuploader.getContentPane().add(btnEditarUsuario);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(Color.WHITE);
		lblId.setHorizontalAlignment(SwingConstants.LEFT);
		lblId.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblId.setBounds(263, 55, 20, 23);
		frmJuploader.getContentPane().add(lblId);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setFont(new Font("Rockwell", Font.PLAIN, 15));
		lblUsuario.setBounds(225, 78, 58, 23);
		frmJuploader.getContentPane().add(lblUsuario);
		
		JButton btnAdministrarUsuarios = new JButton("Administrar Usuarios");
		btnAdministrarUsuarios.setFont(new Font("Rockwell", Font.PLAIN, 12));
		btnAdministrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJuploader.setEnabled(false);
				AdmUsuarios.main(null);
			}
		});
		btnAdministrarUsuarios.setBounds(158, 248, 271, 23);
		frmJuploader.getContentPane().add(btnAdministrarUsuarios);
		
		JLabel lblFondo = new JLabel("");
		Image imgFondo = new ImageIcon(this.getClass().getResource("/background.jpg")).getImage();
		lblFondo.setIcon(new ImageIcon(imgFondo));
		lblFondo.setBounds(0, 0, 584, 339);
		frmJuploader.getContentPane().add(lblFondo);
		
		if (Integer.valueOf(lblIDValue.getText()) != 1) {
			btnAdministrarUsuarios.hide();
		}
		if (Integer.valueOf(lblIDValue.getText()) == 1) {
			btnBorrarUsuario.setEnabled(false);
			btnAdministrarOrganizaciones.setEnabled(false);
			btnReportes.setEnabled(false);
			btnAnalizarProyecto.setEnabled(false);
			btnPreconfigurarProyecto.setEnabled(false);
		}
		if (Integer.valueOf(lblIDValue.getText()) == 0) {
			btnBorrarUsuario.setEnabled(false);
			btnEditarUsuario.setEnabled(false);
			btnAdministrarOrganizaciones.setEnabled(false);
			btnReportes.setEnabled(false);
		}
	}
}
