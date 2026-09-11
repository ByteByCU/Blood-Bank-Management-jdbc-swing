package BloodBank;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BloodRequestPage extends JFrame {

    private static final long serialVersionUID = 1L;

    private JFrame frame;
    private JTextField txtPatientId;
    private JTextField txtUnits;
    
    private int patientId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BloodRequestPage window = new BloodRequestPage(101);
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public BloodRequestPage(int patientId) {
        this.patientId = patientId;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = this;

        setTitle("Blood Request");
        setBounds(100, 100, 650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("BLOOD REQUEST");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setBounds(220, 30, 250, 40);
        getContentPane().add(lblTitle);

        // PATIENT ID
        JLabel lblPatientId = new JLabel("PATIENT ID");
        lblPatientId.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPatientId.setBounds(100, 100, 150, 30);
        getContentPane().add(lblPatientId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(280, 100, 220, 30);
        txtPatientId.setText(String.valueOf(patientId));
        txtPatientId.setEditable(false);
        getContentPane().add(txtPatientId);
        txtPatientId.setColumns(10);

        // BLOOD GROUP
        JLabel lblBloodGroup = new JLabel("BLOOD GROUP");
        lblBloodGroup.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBloodGroup.setBounds(100, 150, 150, 30);
        getContentPane().add(lblBloodGroup);

        JComboBox<String> cmbBloodGroup = new JComboBox<String>();
        cmbBloodGroup.setBounds(280, 150, 220, 30);

        cmbBloodGroup.addItem("A+");
        cmbBloodGroup.addItem("A-");
        cmbBloodGroup.addItem("B+");
        cmbBloodGroup.addItem("B-");
        cmbBloodGroup.addItem("AB+");
        cmbBloodGroup.addItem("AB-");
        cmbBloodGroup.addItem("O+");
        cmbBloodGroup.addItem("O-");

        getContentPane().add(cmbBloodGroup);

        // UNITS REQUIRED
        JLabel lblUnits = new JLabel("UNITS REQUIRED");
        lblUnits.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUnits.setBounds(100, 200, 150, 30);
        getContentPane().add(lblUnits);

        txtUnits = new JTextField();
        txtUnits.setBounds(280, 200, 220, 30);
        getContentPane().add(txtUnits);
        txtUnits.setColumns(10);

        // REQUEST DATE
        JLabel lblRequestDate = new JLabel("REQUEST DATE");
        lblRequestDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRequestDate.setBounds(100, 250, 150, 30);
        getContentPane().add(lblRequestDate);

        JTextField txtDate = new JTextField();
        txtDate.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtDate.setText("Automatic");
        txtDate.setEditable(false);
        txtDate.setBounds(280, 250, 220, 30);
        getContentPane().add(txtDate);
        txtDate.setColumns(10);

        // STATUS
        JLabel lblStatus = new JLabel("STATUS");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStatus.setBounds(100, 290, 150, 30);
        getContentPane().add(lblStatus);

        JTextField txtStatus = new JTextField();
        txtStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtStatus.setText("PENDING");
        txtStatus.setEditable(false);
        txtStatus.setBounds(280, 300, 220, 30);
        getContentPane().add(txtStatus);
        txtStatus.setColumns(10);

        // SUBMIT REQUEST BUTTON
        JButton btnSubmit = new JButton("SUBMIT REQUEST");
        btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnSubmit.setBounds(150, 370, 180, 35);
        getContentPane().add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    // Get values from form
                    String bloodGroup =
                            cmbBloodGroup.getSelectedItem().toString();

                    String unitsText = txtUnits.getText().trim();

                    // Validate units
                    if (unitsText.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Please enter units required."
                        );
                        return;
                    }

                    int units;

                    try {
                        units = Integer.parseInt(unitsText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Units must be a number."
                        );
                        return;
                    }

                    if (units <= 0) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Units must be greater than 0."
                        );
                        return;
                    }

                    // Oracle connection
                    Connection con = DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:orcl",
                            "bca",
                            "bca"
                    );

                    // -------------------------------------------------
                    // CHECK WHETHER PATIENT ALREADY HAS A PENDING REQUEST
                    // FOR THE SAME BLOOD GROUP
                    // -------------------------------------------------

                    String checkSql =
                            "SELECT COUNT(*) " +
                            "FROM BLOOD_REQUESTS " +
                            "WHERE PATIENT_ID = ? " +
                            "AND BLOOD_GROUP = ? " +
                            "AND STATUS = ?";

                    PreparedStatement checkPst =
                            con.prepareStatement(checkSql);

                    checkPst.setInt(1, patientId);
                    checkPst.setString(2, bloodGroup);
                    checkPst.setString(3, "PENDING");

                    ResultSet checkRs = checkPst.executeQuery();

                    checkRs.next();

                    int count = checkRs.getInt(1);

                    checkRs.close();
                    checkPst.close();

                    if (count > 0) {

                        JOptionPane.showMessageDialog(
                                frame,
                                "You already have a pending request for "
                                + bloodGroup + " blood."
                        );

                        con.close();
                        return;
                    }

                    // -------------------------------------------------
                    // GENERATE REQUEST ID
                    // -------------------------------------------------

                    String idSql =
                            "SELECT NVL(MAX(REQUEST_ID), 0) + 1 " +
                            "FROM BLOOD_REQUESTS";

                    PreparedStatement idPst =
                            con.prepareStatement(idSql);

                    ResultSet idRs = idPst.executeQuery();

                    idRs.next();

                    int requestId = idRs.getInt(1);

                    idRs.close();
                    idPst.close();

                    // -------------------------------------------------
                    // INSERT BLOOD REQUEST
                    // -------------------------------------------------

                    String sql =
                            "INSERT INTO BLOOD_REQUESTS "
                            + "(REQUEST_ID, PATIENT_ID, BLOOD_GROUP, "
                            + "UNITS_REQUIRED, REQUEST_DATE, STATUS) "
                            + "VALUES (?, ?, ?, ?, SYSDATE, ?)";

                    PreparedStatement pst =
                            con.prepareStatement(sql);

                    // IMPORTANT:
                    // There are exactly 5 ? marks in the SQL.
                    pst.setInt(1, requestId);
                    pst.setInt(2, patientId);
                    pst.setString(3, bloodGroup);
                    pst.setInt(4, units);
                    pst.setString(5, "PENDING");

                    pst.executeUpdate();

                    pst.close();
                    con.close();

                    JOptionPane.showMessageDialog(
                            frame,
                            "Blood request submitted successfully!"
                    );

                    // Go back to patient dashboard
                    PatientDashBoardPage dashboard =
                            new PatientDashBoardPage(
                                    patientId,
                                    "Patient"
                            );

                    dashboard.setVisible(true);
                    dispose();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(
                            frame,
                            "Error: " + ex.getMessage()
                    );
                }
            }
        });

        // BACK BUTTON
        JButton btnBack = new JButton("BACK");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.setBounds(407, 370, 120, 35);
        getContentPane().add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PatientDashBoardPage dashboard =
                        new PatientDashBoardPage(
                                patientId,
                                "Patient"
                        );

                dashboard.setVisible(true);
                dispose();
            }
        });
    }
}