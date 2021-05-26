package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Analizador extends JFrame {

	private JPanel contentPane;
	private JTextField txtOrganizacion;
	private JTextField txtToken;
	private JTextField txtCarpetaProyectos;

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
		//GUIConsole console = new GUIConsole();
		//console.run("MyGUIConsole",200,100);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Analizador frame = new Analizador();
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
	public Analizador() {
		setTitle("Analizador de Sonar Cloud");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread one = new Thread() {
					public void run() {
						try {
							
							String organizacion = txtOrganizacion.getText();
							String token = txtToken.getText();
							String carpetaProyectos = txtCarpetaProyectos.getText();
							carpetaProyectos = "\"" + carpetaProyectos+"\"";
							
							String line;
							ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/K", "start", "Analyze-Projects.sh", organizacion, token, carpetaProyectos);
						    Process process = processBuilder.start();
						    BufferedReader reader =
						            new BufferedReader(new InputStreamReader(process.getInputStream()));
						    
						    while ((line = reader.readLine()) != null) {
						    	System.out.println(line);
						     } 
						    
						    int output = process.waitFor();

						    reader.close();
						}
						catch (IOException | InterruptedException e1) {
							e1.printStackTrace();
						}
				    }
				};
				    
				one.start();
			}
		});
		btnIniciar.setBounds(174, 227, 89, 23);
		contentPane.add(btnIniciar);
		
		JLabel lblOrganizacion = new JLabel("Organizaci\u00F3n");
		lblOrganizacion.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblOrganizacion.setBounds(174, 51, 81, 16);
		contentPane.add(lblOrganizacion);
		
		txtOrganizacion = new JTextField();
		txtOrganizacion.setHorizontalAlignment(SwingConstants.CENTER);
		txtOrganizacion.setColumns(10);
		txtOrganizacion.setBounds(51, 71, 320, 28);
		contentPane.add(txtOrganizacion);
		
		JLabel lblToken = new JLabel("Token");
		lblToken.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblToken.setBounds(198, 110, 38, 16);
		contentPane.add(lblToken);
		
		txtToken = new JTextField();
		txtToken.setHorizontalAlignment(SwingConstants.CENTER);
		txtToken.setColumns(10);
		txtToken.setBounds(51, 128, 320, 28);
		contentPane.add(txtToken);
		
		JLabel lblCarpetaProyectos = new JLabel("Carpeta de Proyectos");
		lblCarpetaProyectos.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblCarpetaProyectos.setBounds(155, 167, 126, 16);
		contentPane.add(lblCarpetaProyectos);
		
		txtCarpetaProyectos = new JTextField();
		txtCarpetaProyectos.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarpetaProyectos.setColumns(10);
		txtCarpetaProyectos.setBounds(51, 188, 320, 28);
		contentPane.add(txtCarpetaProyectos);
		
		JButton btnBuscar = new JButton("...");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
				    File yourFolder = fc.getSelectedFile();
				    txtCarpetaProyectos.setText(yourFolder.getAbsolutePath());
				}
			}
		});
		btnBuscar.setBounds(381, 191, 43, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblTituloAnalizador = new JLabel("~ Analizar con Sonar Cloud ~");
		lblTituloAnalizador.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblTituloAnalizador.setBounds(69, 17, 285, 16);
		contentPane.add(lblTituloAnalizador);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Principal.frmJuploader.setEnabled(true);
					dispose();
				} catch (Exception e1) {
					System.exit(0);
				}
				
				
			}
		});
		btnCerrar.setBounds(335, 227, 89, 23);
		contentPane.add(btnCerrar);
	}
}
