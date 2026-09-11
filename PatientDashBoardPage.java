package BloodBank;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PatientDashBoardPage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private int patientId;
	private String patientName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					// For testing only
					PatientDashBoardPage frame =
							new PatientDashBoardPage(1, "Patient");

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
	public PatientDashBoardPage(int patientId, String patientName) {

		this.patientId = patientId;
		this.patientName = patientName;

		setTitle("Blood Bank Management - Patient Dashboard");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 1200, 700);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		contentPane = new JPanel();

		contentPane.setBorder(
				new EmptyBorder(5, 5, 5, 5)
		);

		setContentPane(contentPane);

		contentPane.setLayout(null);


		// =========================
		// TITLE
		// =========================

		JLabel lblTitle =
				new JLabel("PATIENT DASHBOARD");

		lblTitle.setFont(
				new Font("Tahoma", Font.BOLD, 12)
		);

		lblTitle.setBounds(
				385, 25, 163, 12
		);

		contentPane.add(lblTitle);


		// =========================
		// WELCOME MESSAGE
		// =========================

		JLabel lblWelcome =
				new JLabel("Welcome, " + patientName);

		lblWelcome.setFont(
				new Font("Tahoma", Font.BOLD, 14)
		);

		lblWelcome.setBounds(
				385, 50, 250, 25
		);

		contentPane.add(lblWelcome);


		// =========================
		// REQUEST BLOOD
		// =========================

		JButton btnRequestBlood =
				new JButton("REQUEST BLOOD");

		btnRequestBlood.setFont(
				new Font("Tahoma", Font.BOLD, 12)
		);

		btnRequestBlood.setBounds(
				497, 103, 178, 20
		);

		contentPane.add(btnRequestBlood);


		// =========================
		// MY REQUESTS
		// =========================

		JButton btnRequests =
				new JButton("MY REQUESTS");

		btnRequests.setFont(
				new Font("Tahoma", Font.BOLD, 12)
		);

		btnRequests.setBounds(
				245, 103, 178, 20
		);

		contentPane.add(btnRequests);


		// =========================
		// LOGOUT
		// =========================

		JButton btnLogout =
				new JButton("LOGOUT");

		btnLogout.setFont(
				new Font("Tahoma", Font.BOLD, 12)
		);

		btnLogout.setBounds(
				408, 157, 145, 20
		);

		contentPane.add(btnLogout);


		// =========================
		// MY REQUESTS ACTION
		// =========================

		btnRequests.addActionListener(
				new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Pass the logged-in patient's ID
				MyRequestPage requests =
						new MyRequestPage(patientId);

				requests.setVisible(true);

				dispose();
			}
		});


		// =========================
		// REQUEST BLOOD ACTION
		// =========================

		btnRequestBlood.addActionListener(
				new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Pass the logged-in patient's ID
				BloodRequestPage request =
						new BloodRequestPage(patientId);

				request.setVisible(true);

				dispose();
			}
		});


		// =========================
		// LOGOUT ACTION
		// =========================

		btnLogout.addActionListener(
				new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				LoginPage login =
						new LoginPage();

				login.setVisible(true);

				dispose();
			}
		});
	}
}