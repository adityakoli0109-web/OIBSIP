import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProfileFrame extends JFrame {

    private User user;

    private JTextField nameField;
    private JPasswordField passwordField;

    public ProfileFrame(User user) {

        this.user = user;

        setTitle("Student Profile");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(
                new BorderLayout()
        );

        // Title
        JLabel titleLabel = new JLabel(
                "STUDENT PROFILE",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // Form
        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel usernameValue =
                new JLabel(user.getUsername());

        JLabel nameLabel =
                new JLabel("Display Name:");

        nameField =
                new JTextField(
                        user.getDisplayName(),
                        15
                );

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordField =
                new JPasswordField(
                        user.getPassword(),
                        15
                );

        JButton updateButton =
                new JButton("Update Profile");

        JButton startButton =
                new JButton("Start Exam");

        JButton logoutButton =
                new JButton("Logout");

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                usernameLabel,
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                usernameValue,
                gbc
        );

        // Display name
        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                nameLabel,
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                nameField,
                gbc
        );

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                passwordLabel,
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                passwordField,
                gbc
        );

        // Update
        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                updateButton,
                gbc
        );

        // Start Exam
        gbc.gridx = 1;

        formPanel.add(
                startButton,
                gbc
        );

        // Logout
        gbc.gridx = 1;
        gbc.gridy = 4;

        formPanel.add(
                logoutButton,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // Update profile
        updateButton.addActionListener(
                e -> updateProfile()
        );

        // Start exam
        startButton.addActionListener(e -> {

                        if (!saveProfile()) {
                                return;
                        }

            dispose();

            new ExamFrame(user);
        });

        // Logout
        logoutButton.addActionListener(e -> {

            dispose();

            new LoginFrame();
        });

        setVisible(true);
    }

    private void updateProfile() {

                if (!saveProfile()) {
                        return;
                }

                JOptionPane.showMessageDialog(
                                this,
                                "Profile updated successfully."
                );
        }

        private boolean saveProfile() {

        String name =
                nameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (name.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fields cannot be empty."
            );

                        return false;
        }

        user.setDisplayName(name);
        user.setPassword(password);

                return true;
    }
}