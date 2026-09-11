package BloodBank;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class MyRequestPage extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTable table;

    private int patientId;

    /**
     * Launch the application.
     * For testing only.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    // For testing only
                    MyRequestPage frame = new MyRequestPage(1);

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
    public MyRequestPage(int patientId) {

        this.patientId = patientId;

        setTitle("Blood Bank Management - My Requests");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // =========================
        // TABLE
        // =========================

        table = new JTable();

        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "REQUEST_ID",
                "BLOOD_GROUP",
                "UNITS_REQUIRED",
                "REQUEST_DATE",
                "STATUS"
            }
        ));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(139, 68, 880, 400);
        contentPane.add(scrollPane);

        // =========================
        // VIEW BUTTON
        // =========================

        JButton btnView = new JButton("VIEW");
        btnView.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnView.setBounds(378, 513, 105, 35);
        contentPane.add(btnView);

        // =========================
        // BACK BUTTON
        // =========================

        JButton btnBack = new JButton("BACK");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBack.setBounds(648, 513, 105, 35);
        contentPane.add(btnBack);
        
        JLabel lblMyRequestPage = new JLabel("MY REQUEST PAGE");
        lblMyRequestPage.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMyRequestPage.setBounds(518, 10, 148, 25);
        contentPane.add(lblMyRequestPage);

        // =========================
        // VIEW ACTION
        // =========================

        btnView.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int selectedRow = table.getSelectedRow();

                // Check whether a row is selected
                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a request."
                    );

                    return;
                }

                String requestId =
                        table.getValueAt(selectedRow, 0).toString();

                String bloodGroup =
                        table.getValueAt(selectedRow, 1).toString();

                String units =
                        table.getValueAt(selectedRow, 2).toString();

                String requestDate =
                        table.getValueAt(selectedRow, 3).toString();

                String status =
                        table.getValueAt(selectedRow, 4).toString();

                JOptionPane.showMessageDialog(
                        null,
                        "REQUEST ID : " + requestId
                        + "\nBLOOD GROUP : " + bloodGroup
                        + "\nUNITS REQUIRED : " + units
                        + "\nREQUEST DATE : " + requestDate
                        + "\nSTATUS : " + status,
                        "Request Details",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // =========================
        // BACK ACTION
        // =========================

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

        // =========================
        // LOAD REQUESTS
        // =========================

        loadRequests();
    }

    // =========================================================
    // LOAD PATIENT REQUESTS FROM DATABASE
    // =========================================================

    private void loadRequests() {

        try {

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con =
                    DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:orcl",
                            "bca",
                            "bca"
                    );

            String sql =
                    "SELECT REQUEST_ID, BLOOD_GROUP, " +
                    "UNITS_REQUIRED, REQUEST_DATE, STATUS " +
                    "FROM BLOOD_REQUESTS " +
                    "WHERE PATIENT_ID = ? " +
                    "ORDER BY REQUEST_DATE DESC";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(1, patientId);

            ResultSet rs =
                    pst.executeQuery();

            DefaultTableModel model =
                    (DefaultTableModel) table.getModel();

            // Clear existing rows
            model.setRowCount(0);

            while (rs.next()) {

                int requestId =
                        rs.getInt("REQUEST_ID");

                String bloodGroup =
                        rs.getString("BLOOD_GROUP");

                int units =
                        rs.getInt("UNITS_REQUIRED");

                String requestDate =
                        rs.getDate("REQUEST_DATE").toString();

                String status =
                        rs.getString("STATUS");

                model.addRow(new Object[] {
                        requestId,
                        bloodGroup,
                        units,
                        requestDate,
                        status
                });
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "Database Error:\n"
                    + ex.getMessage()
            );

            ex.printStackTrace();
        }
    }
}