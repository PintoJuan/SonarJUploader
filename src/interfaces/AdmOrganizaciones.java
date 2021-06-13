package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.sqlite.SQLiteDataSource;

import general.NonEditableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AdmOrganizaciones extends JFrame {

	private JPanel contentPane;
	private JTable tablaOrganizaciones;
	static AdmOrganizaciones frame;

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
					frame = new AdmOrganizaciones();
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
	public AdmOrganizaciones() {
		setResizable(false);
		setTitle("Sonar JUploader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 764, 326);
		contentPane.add(scrollPane);
		
		ResultSet rs = null;
		ArrayList<String> IDs = new ArrayList<String>();
		ArrayList<String> UsuarioIDs = new ArrayList<String>();
		ArrayList<String> Titulos = new ArrayList<String>();
		ArrayList<String> Descripciones = new ArrayList<String>();
		ArrayList<String> NombresSonar = new ArrayList<String>();
		ArrayList<String> Tokens = new ArrayList<String>();
		ArrayList<String> Carpetas = new ArrayList<String>();
		CargarColumnas(rs, IDs, UsuarioIDs, Titulos, Descripciones, NombresSonar, Tokens, Carpetas);
		
		String[][] data = new String[Titulos.size()][7];
		for (int i=0; i < Titulos.size(); i++) {
			data[i][0] = IDs.get(i);
			data[i][1] = UsuarioIDs.get(i);
			data[i][2] = Titulos.get(i);
			data[i][3] = Descripciones.get(i);
			data[i][4] = NombresSonar.get(i);
			data[i][5] = Tokens.get(i);
			data[i][6] = Carpetas.get(i);
		}
		String column[]={"ID","UsuarioID","Titulo","Descripci�n","Nombre Sonar","Token", "Carpeta"};
		
		tablaOrganizaciones = new JTable();
		scrollPane.setViewportView(tablaOrganizaciones);
		tablaOrganizaciones.setModel(new NonEditableModel(data, column));
		tablaOrganizaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnNueva = new JButton("Nueva");
		btnNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(false);
				CrearOrganizacion.main(null);
			}
		});
		btnNueva.setBounds(10, 400, 106, 23);
		contentPane.add(btnNueva);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.frmJuploader.setEnabled(true);
				Principal.frmJuploader.toFront();
				dispose();
			}
		});
		btnCerrar.setBounds(668, 400, 106, 23);
		contentPane.add(btnCerrar);
		
		JLabel lblAdministrarOrganizaciones = new JLabel("~ Administrar Organizaciones ~");
		lblAdministrarOrganizaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministrarOrganizaciones.setFont(new Font("Rockwell", Font.BOLD, 20));
		lblAdministrarOrganizaciones.setBounds(10, 23, 764, 29);
		contentPane.add(lblAdministrarOrganizaciones);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int fila = tablaOrganizaciones.getSelectedRow();
					String id = (String) tablaOrganizaciones.getValueAt(fila, 0);
					frame.setEnabled(false);
					EditarOrganizacion.main(id);
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una organizaci�n para poder editarla.");
				}
				
			}
		});
		btnEditar.setBounds(126, 400, 106, 23);
		contentPane.add(btnEditar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int fila = tablaOrganizaciones.getSelectedRow();
					String id = (String) tablaOrganizaciones.getValueAt(fila, 0);
					int reply = JOptionPane.showConfirmDialog(null, "�Desea realmente borrar la organizaci�n seleccionada?", "Borrar Organizaci�n", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							SQLiteDataSource ds = new SQLiteDataSource();
							ds.setUrl("jdbc:sqlite:SonarJUploader.db");
							Connection conn = ds.getConnection();
							String query = "DELETE FROM organizaciones WHERE ( ID = '"+id+"' )";
							Statement stmt = conn.createStatement();
							int rv = stmt.executeUpdate( query );
							conn.close();
							JOptionPane.showMessageDialog(null, "Organizaci�n borrada exitosamente.");
							AdmOrganizaciones.frame.dispose();
							AdmOrganizaciones.main(null);
							AdmOrganizaciones.frame.toFront();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "No se pudo borrar la organizaci�n.");
						}
					}
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una organizaci�n para poder borrarla.");
				}
			}
		});
		btnBorrar.setBounds(242, 400, 106, 23);
		contentPane.add(btnBorrar);
	}
	
	public void CargarColumnas (ResultSet rs, ArrayList<String> IDs, ArrayList<String> UsuarioIDs, ArrayList<String> Titulos, ArrayList<String> Descripciones, ArrayList<String> NombresSonar, ArrayList<String> Tokens, ArrayList<String> Carpetas) {
		try {
			SQLiteDataSource ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:SonarJUploader.db");
			Connection conn = ds.getConnection();
			String query =  "SELECT * FROM organizaciones WHERE USUARIOID = '"+Principal.lblIDValue.getText()+"'";
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery( query );
			
			while ( rs.next() ) {
			    int id = rs.getInt( "ID" );
			    int usuarioid = rs.getInt( "USUARIOID" );
			    String titulo = rs.getString( "TITULO" );
			    String descripcion = rs.getString( "DESCRIPCION" );
			    String nombresonar = rs.getString( "NOMBRESONAR" );
			    String token = rs.getString( "TOKEN" );
			    String carpeta = rs.getString( "CARPETA" );
			    
			    IDs.add(Integer.toString(id));
			    UsuarioIDs.add(Integer.toString(usuarioid));
			    Titulos.add(titulo);
			    Descripciones.add(descripcion);
			    NombresSonar.add(nombresonar);
			    Tokens.add(token);
			    Carpetas.add(carpeta);
			    
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace(System.out);
			JOptionPane.showMessageDialog(null, "Error al obtener informaci�n de las organizaciones.");
		}
	}
}