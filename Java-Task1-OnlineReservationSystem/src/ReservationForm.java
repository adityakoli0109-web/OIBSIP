import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;

public class ReservationForm extends JFrame {

    private JTextField passengerField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JDateChooser dateField;
    private JTextField sourceField;
    private JTextField destinationField;

    private JComboBox<String> classBox;

    private Dashboard dashboard;

    public ReservationForm(Dashboard dashboard) {
        this.dashboard = dashboard;

        setTitle("Book Ticket");
        setSize(560, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        passengerField = new JTextField();
        trainNumberField = new JTextField();
        trainNameField = new JTextField();
        trainNameField.setEditable(false);

        dateField = new JDateChooser();
        dateField.setDateFormatString("yyyy-MM-dd");
        sourceField = new JTextField();
        destinationField = new JTextField();

        classBox = new JComboBox<>(new String[]{
                "Select Class",
                "Sleeper",
                "AC 3 Tier",
                "AC 2 Tier",
                "First Class"
        });

        JButton findButton = new JButton("Find Train");
        JButton bookButton = new JButton("Book Ticket");
        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");

        addRow(panel, gbc, 0, "Passenger Name:", passengerField);
        addRow(panel, gbc, 1, "Train Number:", trainNumberField);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(findButton, gbc);

        addRow(panel, gbc, 2, "Train Name:", trainNameField);
        addRow(panel, gbc, 3, "Class:", classBox);
        addRow(panel, gbc, 4, "Journey Date:", dateField);
        addRow(panel, gbc, 5, "Source:", sourceField);
        addRow(panel, gbc, 6, "Destination:", destinationField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(bookButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        panel.add(buttonPanel, gbc);

        add(panel);

        findButton.addActionListener(e -> findTrain());
        bookButton.addActionListener(e -> bookTicket());
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> dispose());

        setVisible(true);
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

    private void findTrain() {
        String numberText = trainNumberField.getText().trim();

        if (numberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter train number.");
            return;
        }

        int trainNumber;

        try {
            trainNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Train number must be numeric.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT train_name FROM trains WHERE train_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    trainNameField.setText(rs.getString("train_name"));
                } else {
                    trainNameField.setText("");
                    JOptionPane.showMessageDialog(this,
                            "Train not found.",
                            "Search",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Unable to search train.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookTicket() {
        String passenger = passengerField.getText().trim();
        String trainNumberText = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = (String) classBox.getSelectedItem();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        if (passenger.isEmpty() || trainNumberText.isEmpty()
            || trainName.isEmpty() || dateField.getDate() == null
                || source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (classBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a class.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (source.equalsIgnoreCase(destination)) {
            JOptionPane.showMessageDialog(this,
                    "Source and destination cannot be the same.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int trainNumber;
        try {
            trainNumber = Integer.parseInt(trainNumberText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Train number must be numeric.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate journeyDate = dateField.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        String pnr = createPNR();

        String sql = "INSERT INTO reservations "
                + "(pnr, passenger_name, train_number, train_name, class_type, "
                + "journey_date, source, destination) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);
            ps.setString(2, passenger);
            ps.setInt(3, trainNumber);
            ps.setString(4, trainName);
            ps.setString(5, classType);
            ps.setDate(6, Date.valueOf(journeyDate));
            ps.setString(7, source);
            ps.setString(8, destination);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Ticket booked successfully!\n\n"
                    + "PNR: " + pnr + "\n"
                    + "Passenger: " + passenger + "\n"
                    + "Train: " + trainNumber + " - " + trainName + "\n"
                    + "Class: " + classType + "\n"
                    + "Journey Date: " + journeyDate + "\n"
                    + "From: " + source + "\n"
                    + "To: " + destination,
                    "Booking Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);

            clearForm();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this,
                    "PNR conflict. Please try booking again.",
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not save reservation.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String createPNR() {
        long value = System.currentTimeMillis() % 1000000000L;
        return "PNR" + value;
    }

    private void clearForm() {
        passengerField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        dateField.setDate(null);
        sourceField.setText("");
        destinationField.setText("");
        classBox.setSelectedIndex(0);
    }
}
