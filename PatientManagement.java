package BloodBank;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PatientManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPatientId;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtPhone;
	private JTextField txtAddress;
	private JPasswordField passwordField;
	private JTextField txtUsername;
	private JTable tablePatients;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientManagement frame = new PatientManagement();
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
	public PatientManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeading = new JLabel("PATIENT MANAGEMENT");
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHeading.setBounds(713, 10, 249, 40);
		contentPane.add(lblHeading);
		
		JLabel lblPatientId = new JLabel("PATIENT ID");
		lblPatientId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPatientId.setBounds(35, 66, 80, 12);
		contentPane.add(lblPatientId);
		
		txtPatientId = new JTextField();
		txtPatientId.setBounds(190, 64, 96, 18);
		contentPane.add(txtPatientId);
		txtPatientId.setColumns(10);
		
		JLabel lblName = new JLabel("NAME");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(35, 110, 44, 12);
		contentPane.add(lblName);
		
		JLabel lblAge = new JLabel("AGE");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setBounds(35, 160, 44, 12);
		contentPane.add(lblAge);
		
		JLabel lblGender = new JLabel("GENDER");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGender.setBounds(35, 217, 80, 12);
		contentPane.add(lblGender);
		
		JLabel lblBloodGroup = new JLabel("BLOOD GROUP");
		lblBloodGroup.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBloodGroup.setBounds(35, 274, 95, 12);
		contentPane.add(lblBloodGroup);
		
		JLabel lblPhone = new JLabel("PHONE");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhone.setBounds(35, 325, 44, 12);
		contentPane.add(lblPhone);
		
		JLabel lblAddress = new JLabel("ADDRESS");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAddress.setBounds(35, 382, 80, 12);
		contentPane.add(lblAddress);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(34, 428, 96, 12);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(35, 481, 80, 12);
		contentPane.add(lblPassword);
		
		txtName = new JTextField();
		txtName.setBounds(190, 108, 96, 18);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setBounds(190, 158, 96, 18);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(190, 323, 96, 18);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(190, 380, 96, 18);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		JComboBox comboGender = new JComboBox();
		comboGender.setModel(new DefaultComboBoxModel(new String[] {"MALE", "FEMALE", "OTHER"}));
		comboGender.setBounds(190, 214, 96, 20);
		contentPane.add(comboGender);
		
		JComboBox comboBloodGroup = new JComboBox();
		comboBloodGroup.setModel(new DefaultComboBoxModel(new String[] {"A+", "", "A-", "", "B+", "", "B-", "", "AB+", "", "AB-", "", "O+", "", "O-"}));
		comboBloodGroup.setBounds(190, 271, 96, 20);
		contentPane.add(comboBloodGroup);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(190, 479, 96, 18);
		contentPane.add(passwordField);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(190, 426, 96, 18);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "INSERT INTO PATIENTS " +
				            "(PATIENT_ID, NAME, AGE, GENDER, BLOOD_GROUP, PHONE, ADDRESS, USERNAME, PASSWORD) " +
				            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

				    PreparedStatement pst = con.prepareStatement(sql);

				    pst.setInt(1, Integer.parseInt(txtPatientId.getText()));
				    pst.setString(2, txtName.getText());
				    pst.setInt(3, Integer.parseInt(txtAge.getText()));
				    pst.setString(4, comboGender.getSelectedItem().toString());
				    pst.setString(5, comboBloodGroup.getSelectedItem().toString());
				    pst.setString(6, txtPhone.getText());
				    pst.setString(7, txtAddress.getText());
				    pst.setString(8, txtUsername.getText());
				    pst.setString(9, new String(passwordField.getPassword()));

				    pst.executeUpdate();

				    JOptionPane.showMessageDialog(null, "Patient Added Successfully");

				    pst.close();
				    con.close();

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnAdd.setBounds(414, 532, 84, 20);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int row = tablePatients.getSelectedRow();

				    if (row == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a patient");
				        return;
				    }

				    int choice = JOptionPane.showConfirmDialog(
				        null,
				        "Do you want to delete?",
				        "Confirm Delete",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (choice == JOptionPane.YES_OPTION) {

				        int patientId = Integer.parseInt(
				            tablePatients.getValueAt(row, 0).toString()
				        );

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "DELETE FROM PATIENTS WHERE PATIENT_ID = ?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setInt(1, patientId);

				        pst.executeUpdate();

				        JOptionPane.showMessageDialog(null, "Patient Deleted Successfully");

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {
				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(978, 532, 84, 20);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int row = tablePatients.getSelectedRow();

				    if (row == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a patient");
				        return;
				    }

				    int choice = JOptionPane.showConfirmDialog(
				        null,
				        "Do you want to update?",
				        "Confirm Update",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (choice == JOptionPane.YES_OPTION) {

				        int patientId = Integer.parseInt(
				            tablePatients.getValueAt(row, 0).toString()
				        );

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "UPDATE PATIENTS SET NAME=?, AGE=?, GENDER=?, BLOOD_GROUP=?, PHONE=?, ADDRESS=?, USERNAME=?, PASSWORD=? WHERE PATIENT_ID=?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setString(1, txtName.getText());
				        pst.setInt(2, Integer.parseInt(txtAge.getText()));
				        pst.setString(3, comboGender.getSelectedItem().toString());
				        pst.setString(4, comboBloodGroup.getSelectedItem().toString());
				        pst.setString(5, txtPhone.getText());
				        pst.setString(6, txtAddress.getText());
				        pst.setString(7, txtUsername.getText());
				        pst.setString(8, new String(passwordField.getPassword()));
				        pst.setInt(9, patientId);

				        pst.executeUpdate();

				        JOptionPane.showMessageDialog(null, "Patient Updated Successfully");

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(798, 532, 84, 20);
		contentPane.add(btnUpdate);
		
		JButton btnView = new JButton("VIEW");
		btnView.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "SELECT * FROM PATIENTS ORDER BY PATIENT_ID";

				    PreparedStatement pst = con.prepareStatement(sql);

				    ResultSet rs = pst.executeQuery();

				    DefaultTableModel model = (DefaultTableModel) tablePatients.getModel();

				    model.setRowCount(0);

				    while (rs.next()) {

				        model.addRow(new Object[] {
				            rs.getInt("PATIENT_ID"),
				            rs.getString("NAME"),
				            rs.getInt("AGE"),
				            rs.getString("GENDER"),
				            rs.getString("BLOOD_GROUP"),
				            rs.getString("PHONE"),
				            rs.getString("ADDRESS"),
				            rs.getString("USERNAME"),
				            rs.getString("PASSWORD")
				        });

				    }

				    rs.close();
				    pst.close();
				    con.close();

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnView.setBounds(615, 532, 84, 20);
		contentPane.add(btnView);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDashBoardPage admin = new AdminDashBoardPage();
		        admin.setVisible(true);
		        dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBounds(1187, 532, 84, 20);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(350, 60, 1000, 450);
		contentPane.add(scrollPane);
		
		tablePatients = new JTable();
		tablePatients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablePatients.getSelectedRow();

				txtPatientId.setText(tablePatients.getValueAt(row, 0).toString());
				txtName.setText(tablePatients.getValueAt(row, 1).toString());
				txtAge.setText(tablePatients.getValueAt(row, 2).toString());

				comboGender.setSelectedItem(
				    tablePatients.getValueAt(row, 3).toString()
				);

				comboBloodGroup.setSelectedItem(
				    tablePatients.getValueAt(row, 4).toString()
				);

				txtPhone.setText(tablePatients.getValueAt(row, 5).toString());
				txtAddress.setText(tablePatients.getValueAt(row, 6).toString());
				txtUsername.setText(tablePatients.getValueAt(row, 7).toString());
				passwordField.setText(tablePatients.getValueAt(row, 8).toString());
			}
		});
		scrollPane.setViewportView(tablePatients);
		tablePatients.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PATIENT_ID", "NAME", "AGE", "GENDER", "BLOOD_GROUP", "PHONE", "ADDRESS", "USERNAME", "PASSWORD"
			}
		));

	}
}
