import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class CancellationForm extends JFrame {

    private JTextField pnrField;
    private JTextField passengerField;
    private JTextField trainField;
    private JTextField classField;
    private JTextField dateField;
    private JTextField sourceField;
    private JTextField destinationField;

    public CancellationForm(Dashboard dashboard) {
        setTitle("Cancel Ticket");
        setSize(560, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pnrField = new JTextField();
        passengerField = readOnlyField();
        trainField = readOnlyField();
        classField = readOnlyField();
        dateField = readOnlyField();
        sourceField = readOnlyField();
        destinationField = readOnlyField();

        JButton fetchButton = new JButton("Fetch");
        JButton cancelButton = new JButton("Confirm Cancellation");
        JButton backButton = new JButton("Back");

        addRow(panel, gbc, 0, "PNR:", pnrField);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(fetchButton, gbc);

        addRow(panel, gbc, 1, "Passenger:", passengerField);
        addRow(panel, gbc, 2, "Train:", trainField);
        addRow(panel, gbc, 3, "Class:", classField);
        addRow(panel, gbc, 4, "Journey Date:", dateField);
        addRow(panel, gbc, 5, "Source:", sourceField);
        addRow(panel, gbc, 6, "Destination:", destinationField);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(cancelButton);
        buttons.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        panel.add(buttons, gbc);

        add(panel);

        fetchButton.addActionListener(e -> fetchTicket());
        cancelButton.addActionListener(e -> cancelTicket());
        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JTextField readOnlyField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc,
                        int row, String label, JComponent component) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(component, gbc);
    }

    private void fetchTicket() {
        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter PNR.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    passengerField.setText(rs.getString("passenger_name"));
                    trainField.setText(rs.getInt("train_number")
                            + " - " + rs.getString("train_name"));
                    classField.setText(rs.getString("class_type"));
                    dateField.setText(rs.getDate("journey_date").toString());
                    sourceField.setText(rs.getString("source"));
                    destinationField.setText(rs.getString("destination"));
                } else {
                    clearDetails();
                    JOptionPane.showMessageDialog(this,
                            "PNR not found.",
                            "Search",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Unable to fetch booking.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelTicket() {
        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty() || passengerField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Fetch a valid ticket first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM reservations WHERE pnr = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Ticket cancelled successfully.",
                        "Cancellation",
                        JOptionPane.INFORMATION_MESSAGE);
                pnrField.setText("");
                clearDetails();
            } else {
                JOptionPane.showMessageDialog(this,
                        "PNR not found.",
                        "Cancellation",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not cancel ticket.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearDetails() {
        passengerField.setText("");
        trainField.setText("");
        classField.setText("");
        dateField.setText("");
        sourceField.setText("");
        destinationField.setText("");
    }
}
