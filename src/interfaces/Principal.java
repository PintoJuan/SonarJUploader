package interfaces;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

public class Principal {
	
	static JFrame frmJuploader;

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
					Principal window = new Principal();
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
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		btnNewButton.setBounds(175, 152, 239, 23);
		frmJuploader.getContentPane().add(btnNewButton);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCerrar.setBounds(477, 305, 97, 23);
		frmJuploader.getContentPane().add(btnCerrar);
		
		JButton btnPreconfigurarProyecto = new JButton("Pre-configurar Proyecto");
		btnPreconfigurarProyecto.setBounds(175, 118, 239, 23);
		frmJuploader.getContentPane().add(btnPreconfigurarProyecto);
		
		JButton btnAdministrarOrganizaciones = new JButton("Administrar Organizaciones de Sonar Cloud");
		btnAdministrarOrganizaciones.setBounds(175, 84, 239, 23);
		frmJuploader.getContentPane().add(btnAdministrarOrganizaciones);
		
		JButton btnReportes = new JButton("Visualizar Reportes");
		btnReportes.setBounds(175, 186, 239, 23);
		frmJuploader.getContentPane().add(btnReportes);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
				frmJuploader.dispose();
			}
		});
		btnCerrarSesion.setBounds(477, 275, 97, 23);
		frmJuploader.getContentPane().add(btnCerrarSesion);
		
		JButton btnBorrarUsuario = new JButton("Borrar Usuario");
		btnBorrarUsuario.setBounds(10, 305, 109, 23);
		frmJuploader.getContentPane().add(btnBorrarUsuario);
	}

}
