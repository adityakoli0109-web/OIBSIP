import java.awt.*;
import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Train Reservation System");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel title = new JLabel("TRAIN RESERVATION SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton bookButton = new JButton("Book Ticket");
        JButton cancelButton = new JButton("Cancel Ticket");
        JButton logoutButton = new JButton("Logout");
        JButton exitButton = new JButton("Exit");

        panel.add(title);
        panel.add(bookButton);
        panel.add(cancelButton);
        panel.add(logoutButton);
        panel.add(exitButton);

        add(panel);

        bookButton.addActionListener(e -> new ReservationForm(this));
        cancelButton.addActionListener(e -> new CancellationForm(this));

        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });

        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
