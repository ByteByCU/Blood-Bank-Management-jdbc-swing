package BloodBank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class BloodInventoryManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInventoryId;
	private JTextField txtUnitsAvailable;
	private JTextField txtExpiryDate;
	private JTable tableBloodInventory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BloodInventoryManagement frame = new BloodInventoryManagement();
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
	public BloodInventoryManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHeading = new JLabel("BLOOD INVENTORY MANAGEMENT");
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHeading.setBounds(548, 0, 238, 47);
		contentPane.add(lblHeading);
		
		JLabel lblInventoryId = new JLabel("INVENTORY ID");
		lblInventoryId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInventoryId.setBounds(45, 112, 118, 12);
		contentPane.add(lblInventoryId);
		
		JLabel lblBloodGroup = new JLabel("BLOOD GROUP");
		lblBloodGroup.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBloodGroup.setBounds(45, 155, 96, 12);
		contentPane.add(lblBloodGroup);
		
		JLabel lblUnitsAvailable = new JLabel("UNITS AVAILABLE");
		lblUnitsAvailable.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUnitsAvailable.setBounds(45, 197, 133, 12);
		contentPane.add(lblUnitsAvailable);
		
		JLabel lblExpiryDate = new JLabel("EXPIRY DATE");
		lblExpiryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblExpiryDate.setBounds(45, 262, 96, 12);
		contentPane.add(lblExpiryDate);
		
		txtInventoryId = new JTextField();
		txtInventoryId.setBounds(228, 110, 96, 18);
		contentPane.add(txtInventoryId);
		txtInventoryId.setColumns(10);
		
		JComboBox comboBloodGroup = new JComboBox();
		comboBloodGroup.setModel(new DefaultComboBoxModel(new String[] {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}));
		comboBloodGroup.setBounds(228, 152, 96, 20);
		contentPane.add(comboBloodGroup);
		
		txtUnitsAvailable = new JTextField();
		txtUnitsAvailable.setBounds(228, 209, 96, 18);
		contentPane.add(txtUnitsAvailable);
		txtUnitsAvailable.setColumns(10);
		
		txtExpiryDate = new JTextField();
		txtExpiryDate.setBounds(228, 260, 96, 18);
		contentPane.add(txtExpiryDate);
		txtExpiryDate.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(400, 100, 750, 400);
		contentPane.add(scrollPane);
		
		tableBloodInventory = new JTable();
		tableBloodInventory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableBloodInventory.getSelectedRow();

		        txtInventoryId.setText(tableBloodInventory.getValueAt(row, 0).toString());
		        comboBloodGroup.setSelectedItem(tableBloodInventory.getValueAt(row, 1).toString());
		        txtUnitsAvailable.setText(tableBloodInventory.getValueAt(row, 2).toString());
		        
		        if (tableBloodInventory.getValueAt(row, 3) != null) {
		            txtExpiryDate.setText(tableBloodInventory.getValueAt(row, 3).toString());
		        } else {
		            txtExpiryDate.setText("");
		        }
			}
		});
		tableBloodInventory.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"INVENTORY_ID", "BLOOD_GROUP", "UNITS_AVAILABLE", "EXPIRY_DATE"
			}
		));
		scrollPane.setViewportView(tableBloodInventory);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "INSERT INTO BLOOD_INVENTORY " +
				                 "(INVENTORY_ID, BLOOD_GROUP, UNITS_AVAILABLE, EXPIRY_DATE) " +
				                 "VALUES (?, ?, ?, ?)";

				    PreparedStatement pst = con.prepareStatement(sql);

				    pst.setInt(1, Integer.parseInt(txtInventoryId.getText()));
				    pst.setString(2, comboBloodGroup.getSelectedItem().toString());
				    pst.setInt(3, Integer.parseInt(txtUnitsAvailable.getText()));

				    if (txtExpiryDate.getText().isEmpty()) {
				        pst.setNull(4, java.sql.Types.DATE);
				    } else {
				        pst.setDate(4, java.sql.Date.valueOf(txtExpiryDate.getText()));
				    }

				    pst.executeUpdate();

				    JOptionPane.showMessageDialog(null, "Blood inventory added successfully!");

				    pst.close();
				    con.close();

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBounds(440, 530, 84, 20);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    Connection con = DriverManager.getConnection(
				        "jdbc:oracle:thin:@localhost:1521:orcl",
				        "bca",
				        "bca"
				    );

				    String sql = "UPDATE BLOOD_INVENTORY SET " +
				                 "BLOOD_GROUP = ?, " +
				                 "UNITS_AVAILABLE = ?, " +
				                 "EXPIRY_DATE = ? " +
				                 "WHERE INVENTORY_ID = ?";

				    PreparedStatement pst = con.prepareStatement(sql);

				    pst.setString(1, comboBloodGroup.getSelectedItem().toString());
				    pst.setInt(2, Integer.parseInt(txtUnitsAvailable.getText()));

				    if (txtExpiryDate.getText().isEmpty()) {
				        pst.setNull(3, java.sql.Types.DATE);
				    } else {
				        pst.setDate(3, java.sql.Date.valueOf(txtExpiryDate.getText()));
				    }

				    pst.setInt(4, Integer.parseInt(txtInventoryId.getText()));

				    int rows = pst.executeUpdate();

				    if (rows > 0) {
				        JOptionPane.showMessageDialog(null, "Blood inventory updated successfully!");
				    } else {
				        JOptionPane.showMessageDialog(null, "Inventory ID not found!");
				    }

				    pst.close();
				    con.close();

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(740, 530, 84, 20);
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

				    String sql = "SELECT * FROM BLOOD_INVENTORY ORDER BY INVENTORY_ID";

				    PreparedStatement pst = con.prepareStatement(sql);

				    ResultSet rs = pst.executeQuery();

				    DefaultTableModel model =
				            (DefaultTableModel) tableBloodInventory.getModel();

				    model.setRowCount(0);

				    while (rs.next()) {

				        model.addRow(new Object[] {

				            rs.getInt("INVENTORY_ID"),
				            rs.getString("BLOOD_GROUP"),
				            rs.getInt("UNITS_AVAILABLE"),
				            rs.getDate("EXPIRY_DATE")

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
		btnView.setBounds(578, 530, 84, 20);
		contentPane.add(btnView);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

				    int confirm = JOptionPane.showConfirmDialog(
				        null,
				        "Are you sure you want to delete this record?",
				        "Confirm Delete",
				        JOptionPane.YES_NO_OPTION
				    );

				    if (confirm == JOptionPane.YES_OPTION) {

				        Connection con = DriverManager.getConnection(
				            "jdbc:oracle:thin:@localhost:1521:orcl",
				            "bca",
				            "bca"
				        );

				        String sql = "DELETE FROM BLOOD_INVENTORY WHERE INVENTORY_ID = ?";

				        PreparedStatement pst = con.prepareStatement(sql);

				        pst.setInt(1, Integer.parseInt(txtInventoryId.getText()));

				        int rows = pst.executeUpdate();

				        if (rows > 0) {
				            JOptionPane.showMessageDialog(
				                null,
				                "Blood inventory deleted successfully!"
				            );
				        } else {
				            JOptionPane.showMessageDialog(
				                null,
				                "Inventory ID not found!"
				            );
				        }

				        pst.close();
				        con.close();
				    }

				} catch (Exception ex) {

				    JOptionPane.showMessageDialog(null, ex.getMessage());

				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(886, 530, 84, 20);
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
		btnBack.setBounds(1052, 530, 84, 20);
		contentPane.add(btnBack);

	}

}
