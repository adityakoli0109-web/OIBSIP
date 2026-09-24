import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultFrame extends JFrame {

    private User user;

    public ResultFrame(
            User user,
            int total,
            int correct,
            int incorrect,
            int timeUsed) {

        this.user = user;

        setTitle("Exam Result");

        setSize(500, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        // Title

        JLabel title =
                new JLabel("EXAM RESULT");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(title);

        panel.add(
                Box.createVerticalStrut(25)
        );

        // Student

        JLabel nameLabel =
                new JLabel(
                        "Student: " +
                        user.getDisplayName()
                );

        nameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(nameLabel);

        panel.add(
                Box.createVerticalStrut(15)
        );

        // Total

        JLabel totalLabel =
                new JLabel(
                        "Total Questions: " +
                        total
                );

        totalLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(totalLabel);

        // Correct

        JLabel correctLabel =
                new JLabel(
                        "Correct Answers: " +
                        correct
                );

        correctLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(correctLabel);

        // Incorrect

        JLabel incorrectLabel =
                new JLabel(
                        "Incorrect Answers: " +
                        incorrect
                );

        incorrectLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(incorrectLabel);

        // Score

        JLabel scoreLabel =
                new JLabel(
                        "Score: " +
                        correct +
                        " / " +
                        total
                );

        scoreLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        scoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(scoreLabel);

        // Percentage

        double percentage =
                ((double) correct / total) * 100;

        JLabel percentageLabel =
                new JLabel(
                        String.format(
                                "Percentage: %.2f%%",
                                percentage
                        )
                );

        percentageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(percentageLabel);

        // Time

        int minutes =
                timeUsed / 60;

        int seconds =
                timeUsed % 60;

        JLabel timeLabel =
                new JLabel(
                        String.format(
                                "Time Taken: %02d:%02d",
                                minutes,
                                seconds
                        )
                );

        timeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(timeLabel);

        panel.add(
                Box.createVerticalStrut(25)
        );

        // Buttons

        JButton logoutButton =
                new JButton("Logout");

        JButton exitButton =
                new JButton("Exit");

        logoutButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        exitButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(logoutButton);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(exitButton);

        add(panel);

        // Logout

        logoutButton.addActionListener(
                e -> {

                    dispose();

                    new LoginFrame();
                }
        );

        // Exit

        exitButton.addActionListener(
                e -> System.exit(0)
        );

        setVisible(true);
    }
}