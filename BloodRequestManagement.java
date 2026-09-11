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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BloodRequestManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRequestId;
	private JTextField txtPatientId;
	private JTextField txtUnitsRequired;
	private JTable tableBloodRequests;
	private JTextField txtRequestDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BloodRequestManagement frame = new BloodRequestManagement();
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
	public BloodRequestManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeading = new JLabel("BLOOD REQUEST MANAGEMENT");
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHeading.setBounds(483, 10, 322, 20);
		contentPane.add(lblHeading);
		
		JLabel lblRequestId = new JLabel("REQUEST ID");
		lblRequestId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRequestId.setBounds(45, 89, 89, 12);
		contentPane.add(lblRequestId);
		
		JLabel lblPatientId = new JLabel("PATIENT ID");
		lblPatientId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPatientId.setBounds(45, 142, 89, 12);
		contentPane.add(lblPatientId);
		
		JLabel lblBloodGroup = new JLabel("BLOOD GROUP");
		lblBloodGroup.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBloodGroup.setBounds(45, 215, 107, 12);
		contentPane.add(lblBloodGroup);
		
		JLabel lblUnitsRequired = new JLabel("UNITS REQUIRED");
		lblUnitsRequired.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUnitsRequired.setBounds(36, 291, 135, 12);
		contentPane.add(lblUnitsRequired);
		
		JLabel lblStatus = new JLabel("STATUS");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStatus.setBounds(55, 417, 66, 12);
		contentPane.add(lblStatus);
		
		txtRequestId = new JTextField();
		txtRequestId.setBounds(196, 87, 96, 18);
		contentPane.add(txtRequestId);
		txtRequestId.setColumns(10);
		
		txtPatientId = new JTextField();
		txtPatientId.setBounds(196, 140, 96, 18);
		contentPane.add(txtPatientId);
		txtPatientId.setColumns(10);
		
		txtUnitsRequired = new JTextField();
		txtUnitsRequired.setBounds(196, 289, 96, 18);
		contentPane.add(txtUnitsRequired);
		txtUnitsRequired.setColumns(10);
		
		JComboBox comboBloodGroup = new JComboBox();
		comboBloodGroup.setModel(new DefaultComboBoxModel(new String[] {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}));
		comboBloodGroup.setBounds(196, 212, 96, 20);
		contentPane.add(comboBloodGroup);
		
		JComboBox comboStatus = new JComboBox();
		comboStatus.setModel(new DefaultComboBoxModel(new String[] {"PENDING", "APPROVED", "REJECTED", "COMPLETED"}));
		comboStatus.setBounds(196, 414, 96, 20);
		contentPane.add(comboStatus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(400, 100, 1100, 390);
		contentPane.add(scrollPane);
		
		tableBloodRequests = new JTable();
		tableBloodRequests.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableBloodRequests.getSelectedRow();

				txtRequestId.setText(
				    tableBloodRequests.getValueAt(row, 0).toString()
				);

				txtPatientId.setText(
				    tableBloodRequests.getValueAt(row, 1).toString()
				);

				comboBloodGroup.setSelectedItem(
				    tableBloodRequests.getValueAt(row, 2).toString()
				);

				txtUnitsRequired.setText(
				    tableBloodRequests.getValueAt(row, 3).toString()
				);

				txtRequestDate.setText(
				    tableBloodRequests.getValueAt(row, 4).toString()
				);

				comboStatus.setSelectedItem(
				    tableBloodRequests.getValueAt(row, 5).toString()
				);
			}
		});
		tableBloodRequests.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"REQUEST_ID", "PATIENT_ID", "BLOOD_GROUP", "UNITS_REQUIRED", "REQUEST_DATE", "STATUS"
			}
		));
		scrollPane.setViewportView(tableBloodRequests);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "INSERT INTO BLOOD_REQUESTS " +
				            "(REQUEST_ID, PATIENT_ID, BLOOD_GROUP, UNITS_REQUIRED, REQUEST_DATE, STATUS) " +
				            "VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";

				    PreparedStatement pst = con.prepareStatement(sql);

				    pst.setInt(1, Integer.parseInt(txtRequestId.getText()));
				    pst.setInt(2, Integer.parseInt(txtPatientId.getText()));
				    pst.setString(3, comboBloodGroup.getSelectedItem().toString());
				    pst.setInt(4, Integer.parseInt(txtUnitsRequired.getText()));
				    pst.setString(5, txtRequestDate.getText());
				    pst.setString(6, comboStatus.getSelectedItem().toString());

				    pst.executeUpdate();

				    JOptionPane.showMessageDialog(null, "Blood Request Added Successfully");

				    pst.close();
				    con.close();

				} catch (Exception ex) {
				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBounds(427, 518, 84, 20);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int row = tableBloodRequests.getSelectedRow();

				    if (row == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a request");
				        return;
				    }

				    int choice = JOptionPane.showConfirmDialog(
				        null,
				        "Do you want to update?",
				        "Confirm Update",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (choice == JOptionPane.YES_OPTION) {

				        int requestId = Integer.parseInt(
				            tableBloodRequests.getValueAt(row, 0).toString()
				        );

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "UPDATE BLOOD_REQUESTS SET PATIENT_ID=?, BLOOD_GROUP=?, UNITS_REQUIRED=?, REQUEST_DATE=TO_DATE(?, 'YYYY-MM-DD'), STATUS=? WHERE REQUEST_ID=?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setInt(1, Integer.parseInt(txtPatientId.getText()));
				        pst.setString(2, comboBloodGroup.getSelectedItem().toString());
				        pst.setInt(3, Integer.parseInt(txtUnitsRequired.getText()));
				        pst.setString(4, txtRequestDate.getText());
				        pst.setString(5, comboStatus.getSelectedItem().toString());
				        pst.setInt(6, requestId);

				        pst.executeUpdate();

				        JOptionPane.showMessageDialog(null, "Blood Request Updated Successfully");

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {
				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(899, 518, 84, 20);
		contentPane.add(btnUpdate);
		
		JButton btnView = new JButton("VIEW");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "SELECT * FROM BLOOD_REQUESTS ORDER BY REQUEST_ID";

				    PreparedStatement pst = con.prepareStatement(sql);

				    ResultSet rs = pst.executeQuery();

				    DefaultTableModel model =
				            (DefaultTableModel) tableBloodRequests.getModel();

				    model.setRowCount(0);

				    while (rs.next()) {

				        model.addRow(new Object[] {
				            rs.getInt("REQUEST_ID"),
				            rs.getInt("PATIENT_ID"),
				            rs.getString("BLOOD_GROUP"),
				            rs.getInt("UNITS_REQUIRED"),
				            rs.getDate("REQUEST_DATE"),
				            rs.getString("STATUS")
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
		btnView.setBounds(670, 518, 84, 20);
		contentPane.add(btnView);
		
		txtRequestDate = new JTextField();
		txtRequestDate.setBounds(196, 354, 96, 18);
		contentPane.add(txtRequestDate);
		txtRequestDate.setColumns(10);
		
		JLabel lblRequestDate = new JLabel("REQUEST DATE");
		lblRequestDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRequestDate.setBounds(45, 356, 107, 12);
		contentPane.add(lblRequestDate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int row = tableBloodRequests.getSelectedRow();

				    if (row == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a request");
				        return;
				    }

				    int choice = JOptionPane.showConfirmDialog(
				        null,
				        "Do you want to delete?",
				        "Confirm Delete",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (choice == JOptionPane.YES_OPTION) {

				        int requestId = Integer.parseInt(
				            tableBloodRequests.getValueAt(row, 0).toString()
				        );

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "DELETE FROM BLOOD_REQUESTS WHERE REQUEST_ID = ?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setInt(1, requestId);

				        pst.executeUpdate();

				        JOptionPane.showMessageDialog(
				            null,
				            "Blood Request Deleted Successfully"
				        );

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {
				    JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(1155, 518, 84, 20);
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
		btnBack.setBounds(1390, 518, 84, 20);
		contentPane.add(btnBack);

	}
}
