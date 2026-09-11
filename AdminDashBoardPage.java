package BloodBank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminDashBoardPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashBoardPage frame = new AdminDashBoardPage();
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
	public AdminDashBoardPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeading = new JLabel("ADMIN DASHBOARD");
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHeading.setBounds(240, 10, 199, 20);
		contentPane.add(lblHeading);
		
		JButton btnPatients = new JButton("PATIENTS");
		btnPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PatientManagement patient = new PatientManagement();
		        patient.setVisible(true);
		        dispose();
		    }
		});
			
		btnPatients.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPatients.setBounds(122, 95, 159, 35);
		contentPane.add(btnPatients);
		
		JButton btnDonors = new JButton("DONORS");
		btnDonors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DonorManagement donor = new DonorManagement();
		        donor.setVisible(true);
		        dispose();
			}
		});
		btnDonors.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDonors.setBounds(385, 95, 155, 35);
		contentPane.add(btnDonors);
		
		JButton btnBloodRequests = new JButton("BLOOD REQUESTS");
		btnBloodRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        BloodRequestManagement request = new BloodRequestManagement();
		        request.setVisible(true);
		        dispose();
			}
		});
		btnBloodRequests.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBloodRequests.setBounds(122, 205, 159, 38);
		contentPane.add(btnBloodRequests);
		
		JButton btnBloodInventory = new JButton("BLOOD INVENTORY");
		btnBloodInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 BloodInventoryManagement inventory = new BloodInventoryManagement();
			        inventory.setVisible(true);
			        dispose();
			}
		});
		btnBloodInventory.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBloodInventory.setBounds(385, 201, 155, 42);
		contentPane.add(btnBloodInventory);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage();
		        login.setVisible(true);
		        dispose();
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogout.setBounds(296, 307, 95, 29);
		contentPane.add(btnLogout);

	}

}
