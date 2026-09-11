package BloodBank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DonorManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDonorId;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTable tableDonors;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DonorManagement frame = new DonorManagement();
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
	public DonorManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeading = new JLabel("DONOR MANAGEMENT");
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHeading.setBounds(251, 10, 229, 32);
		contentPane.add(lblHeading);
		
		JLabel lblDonorId = new JLabel("DONOR ID");
		lblDonorId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDonorId.setBounds(40, 93, 76, 12);
		contentPane.add(lblDonorId);
		
		JLabel lblName = new JLabel("NAME");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(40, 131, 44, 12);
		contentPane.add(lblName);
		
		JLabel lblAge = new JLabel("AGE");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setBounds(40, 168, 44, 12);
		contentPane.add(lblAge);
		
		JLabel lblGender = new JLabel("GENDER");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGender.setBounds(40, 200, 57, 12);
		contentPane.add(lblGender);
		
		JLabel lblBloodGroup = new JLabel("BLOOD GROUP");
		lblBloodGroup.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBloodGroup.setBounds(40, 233, 105, 12);
		contentPane.add(lblBloodGroup);
		
		JLabel lblPhone = new JLabel("PHONE");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhone.setBounds(40, 270, 44, 12);
		contentPane.add(lblPhone);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(40, 309, 44, 12);
		contentPane.add(lblEmail);
		
		JLabel lblAddress = new JLabel("ADDRESS");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAddress.setBounds(40, 346, 76, 12);
		contentPane.add(lblAddress);
		
		txtDonorId = new JTextField();
		txtDonorId.setBounds(176, 91, 96, 18);
		contentPane.add(txtDonorId);
		txtDonorId.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(176, 131, 96, 18);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setBounds(176, 166, 96, 18);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		JComboBox comboGender = new JComboBox();
		comboGender.setModel(new DefaultComboBoxModel(new String[] {"MALE", "FEMALE", "OTHER"}));
		comboGender.setBounds(176, 197, 96, 20);
		contentPane.add(comboGender);
		
		JComboBox comboBloodGroup = new JComboBox();
		comboBloodGroup.setModel(new DefaultComboBoxModel(new String[] {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}));
		comboBloodGroup.setBounds(176, 230, 96, 20);
		contentPane.add(comboBloodGroup);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(176, 268, 96, 18);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(176, 307, 96, 18);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(176, 344, 96, 18);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 40, 1050, 300);
		contentPane.add(scrollPane);
		
		tableDonors = new JTable();
		tableDonors.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableDonors.getSelectedRow();

				txtDonorId.setText(tableDonors.getValueAt(row, 0).toString());
				txtName.setText(tableDonors.getValueAt(row, 1).toString());
				txtAge.setText(tableDonors.getValueAt(row, 2).toString());

				comboGender.setSelectedItem(
				    tableDonors.getValueAt(row, 3).toString()
				);

				comboBloodGroup.setSelectedItem(
				    tableDonors.getValueAt(row, 4).toString()
				);

				txtPhone.setText(tableDonors.getValueAt(row, 5).toString());
				txtEmail.setText(tableDonors.getValueAt(row, 6).toString());
				txtAddress.setText(tableDonors.getValueAt(row, 7).toString());
			}
		});
		tableDonors.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"DONOR_ID", "NAME", "AGE", "GENDER", "BLOOD_GROUP", "PHONE", "EMAIL", "ADDRESS"
			}
		));
		scrollPane.setViewportView(tableDonors);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "INSERT INTO DONORS " +
				            "(DONOR_ID, NAME, AGE, GENDER, BLOOD_GROUP, PHONE, EMAIL, ADDRESS) " +
				            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

				    PreparedStatement pst = con.prepareStatement(sql);

				    pst.setInt(1, Integer.parseInt(txtDonorId.getText()));
				    pst.setString(2, txtName.getText());
				    pst.setInt(3, Integer.parseInt(txtAge.getText()));
				    pst.setString(4, comboGender.getSelectedItem().toString());
				    pst.setString(5, comboBloodGroup.getSelectedItem().toString());
				    pst.setString(6, txtPhone.getText());
				    pst.setString(7, txtEmail.getText());
				    pst.setString(8, txtAddress.getText());

				    pst.executeUpdate();

				    JOptionPane.showMessageDialog(null, "Donor Added Successfully");

				    pst.close();
				    con.close();

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBounds(328, 346, 84, 20);
		contentPane.add(btnAdd);
		
		JButton btnView = new JButton("VIEW");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "SELECT * FROM DONORS ORDER BY DONOR_ID";

				    PreparedStatement pst = con.prepareStatement(sql);

				    ResultSet rs = pst.executeQuery();

				    DefaultTableModel model = (DefaultTableModel) tableDonors.getModel();

				    model.setRowCount(0);

				    while (rs.next()) {

				        model.addRow(new Object[] {
				            rs.getInt("DONOR_ID"),
				            rs.getString("NAME"),
				            rs.getInt("AGE"),
				            rs.getString("GENDER"),
				            rs.getString("BLOOD_GROUP"),
				            rs.getString("PHONE"),
				            rs.getString("EMAIL"),
				            rs.getString("ADDRESS")
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
		btnView.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnView.setBounds(473, 346, 84, 20);
		contentPane.add(btnView);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int row = tableDonors.getSelectedRow();

				    if (row == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a donor");
				        return;
				    }

				    int choice = JOptionPane.showConfirmDialog(
				        null,
				        "Do you want to update?",
				        "Confirm Update",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (choice == JOptionPane.YES_OPTION) {

				        int donorId = Integer.parseInt(
				            tableDonors.getValueAt(row, 0).toString()
				        );

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "UPDATE DONORS SET NAME=?, AGE=?, GENDER=?, BLOOD_GROUP=?, PHONE=?, EMAIL=?, ADDRESS=? WHERE DONOR_ID=?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setString(1, txtName.getText());
				        pst.setInt(2, Integer.parseInt(txtAge.getText()));
				        pst.setString(3, comboGender.getSelectedItem().toString());
				        pst.setString(4, comboBloodGroup.getSelectedItem().toString());
				        pst.setString(5, txtPhone.getText());
				        pst.setString(6, txtEmail.getText());
				        pst.setString(7, txtAddress.getText());
				        pst.setInt(8, donorId);

				        pst.executeUpdate();

				        JOptionPane.showMessageDialog(null, "Donor Updated Successfully");

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(611, 346, 84, 20);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int row = tableDonors.getSelectedRow();

				    if (row == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a donor");
				        return;
				    }

				    int choice = JOptionPane.showConfirmDialog(
				        null,
				        "Do you want to delete?",
				        "Confirm Delete",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (choice == JOptionPane.YES_OPTION) {

				        int donorId = Integer.parseInt(
				            tableDonors.getValueAt(row, 0).toString()
				        );

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "DELETE FROM DONORS WHERE DONOR_ID = ?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setInt(1, donorId);

				        pst.executeUpdate();

				        JOptionPane.showMessageDialog(null, "Donor Deleted Successfully");

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(766, 346, 84, 20);
		contentPane.add(btnDelete);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        AdminDashBoardPage admin = new AdminDashBoardPage();
		        admin.setVisible(true);
		        dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBounds(923, 346, 84, 20);
		contentPane.add(btnBack);

	}

}
