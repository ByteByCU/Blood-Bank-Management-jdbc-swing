package BloodBank;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					LoginPage frame = new LoginPage();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginPage() {

		setTitle("Blood Bank Management - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 669, 409);

		contentPane = new JPanel();

		contentPane.setBorder(new TitledBorder(
				null,
				"",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null
		));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Heading
		JLabel lblHeading = new JLabel("BLOOD BANK MANAGEMENT");
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHeading.setBounds(185, 10, 276, 34);
		contentPane.add(lblHeading);

		// Username label
		JLabel lblUsername = new JLabel("USERNAME :");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(185, 72, 84, 25);
		contentPane.add(lblUsername);

		// Username field
		txtUsername = new JTextField();
		txtUsername.setBounds(365, 71, 212, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		// Password label
		JLabel lblPassword = new JLabel("PASSWORD :");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(185, 125, 95, 12);
		contentPane.add(lblPassword);

		// Password field
		passwordField = new JPasswordField();
		passwordField.setBounds(365, 109, 212, 28);
		contentPane.add(passwordField);

		// Admin Login button
		JButton btnAdminLogin = new JButton("ADMIN LOGIN");
		btnAdminLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdminLogin.setBounds(188, 188, 125, 41);
		contentPane.add(btnAdminLogin);

		btnAdminLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String username = txtUsername.getText().trim();

				String password =
						new String(passwordField.getPassword());

				if (username.isEmpty() || password.isEmpty()) {

					JOptionPane.showMessageDialog(
							null,
							"Please enter username and password."
					);

					return;
				}

				if (username.equals("admin")
						&& password.equals("admin123")) {

					JOptionPane.showMessageDialog(
							null,
							"Admin Login Successful"
					);

					AdminDashBoardPage admin =
							new AdminDashBoardPage();

					admin.setVisible(true);

					dispose();

				} else {

					JOptionPane.showMessageDialog(
							null,
							"Invalid Admin Username or Password"
					);
				}
			}
		});

		// Patient Login button
		JButton btnPatientLogin = new JButton("PATIENT LOGIN");
		btnPatientLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPatientLogin.setBounds(365, 188, 143, 41);
		contentPane.add(btnPatientLogin);

		btnPatientLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String username =
						txtUsername.getText().trim();

				String password =
						new String(passwordField.getPassword());

				if (username.isEmpty() || password.isEmpty()) {

					JOptionPane.showMessageDialog(
							null,
							"Please enter username and password."
					);

					return;
				}

				try {

					Connection con =
							DriverManager.getConnection(
									"jdbc:oracle:thin:@localhost:1521:orcl",
									"bca",
									"bca"
							);

					String sql =
							"SELECT PATIENT_ID, NAME " +
							"FROM PATIENTS " +
							"WHERE USERNAME = ? " +
							"AND PASSWORD = ?";

					PreparedStatement pst =
							con.prepareStatement(sql);

					pst.setString(1, username);
					pst.setString(2, password);

					ResultSet rs =
							pst.executeQuery();

					if (rs.next()) {

						// Get patient information from database
						int patientId =
								rs.getInt("PATIENT_ID");

						String patientName =
								rs.getString("NAME");

						JOptionPane.showMessageDialog(
								null,
								"Patient Login Successful!\n"
								+ "Welcome, "
								+ patientName
						);

						// Open Patient Dashboard
						PatientDashBoardPage patient =
								new PatientDashBoardPage(
										patientId,
										patientName
								);

						patient.setVisible(true);

						dispose();

					} else {

						JOptionPane.showMessageDialog(
								null,
								"Invalid Username or Password"
						);
					}

					rs.close();
					pst.close();
					con.close();

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(
							null,
							"Database Connection Error:\n"
							+ ex.getMessage()
					);
				}
			}
		});

		// Exit button
		JButton btnExit = new JButton("EXIT");
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExit.setBounds(281, 310, 95, 34);
		contentPane.add(btnExit);

		btnExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});
	}
}