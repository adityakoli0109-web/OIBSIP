import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class ExamFrame extends JFrame {

    private User user;

    private List<Question> questions;

    private int currentQuestion = 0;

    private int[] selectedAnswers;

    private JLabel questionNumberLabel;
    private JLabel timerLabel;
    private JLabel questionLabel;

    private JRadioButton optionA;
    private JRadioButton optionB;
    private JRadioButton optionC;
    private JRadioButton optionD;

    private ButtonGroup optionGroup;

    private JButton previousButton;
    private JButton nextButton;
    private JButton submitButton;

    private Timer timer;

    // 10 MINUTES
    private int timeRemaining = 10 * 60;

    public ExamFrame(User user) {

        this.user = user;

        createQuestions();

        selectedAnswers = new int[questions.size()];

        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
        }

        createUI();

        startTimer();

        loadQuestion();

        setVisible(true);
    }

    // ==========================================
    // CREATE 10 QUESTIONS
    // ==========================================

    private void createQuestions() {

        questions = new ArrayList<>();

        // Question 1
        questions.add(
                new Question(
                        "Which keyword is used to inherit a class in Java?",
                        new String[]{
                                "implements",
                                "extends",
                                "inherits",
                                "super"
                        },
                        1
                )
        );

        // Question 2
        questions.add(
                new Question(
                        "Which method is the entry point of a Java program?",
                        new String[]{
                                "start()",
                                "run()",
                                "main()",
                                "execute()"
                        },
                        2
                )
        );

        // Question 3
        questions.add(
                new Question(
                        "Which collection does not allow duplicate elements?",
                        new String[]{
                                "List",
                                "ArrayList",
                                "Set",
                                "Vector"
                        },
                        2
                )
        );

        // Question 4
        questions.add(
                new Question(
                        "Which keyword is used to create an object?",
                        new String[]{
                                "class",
                                "object",
                                "new",
                                "create"
                        },
                        2
                )
        );

        // Question 5
        questions.add(
                new Question(
                        "Which package contains Swing components?",
                        new String[]{
                                "java.io",
                                "javax.swing",
                                "java.sql",
                                "java.net"
                        },
                        1
                )
        );

        // Question 6
        questions.add(
                new Question(
                        "Which data type stores true or false values?",
                        new String[]{
                                "int",
                                "boolean",
                                "String",
                                "double"
                        },
                        1
                )
        );

        // Question 7
        questions.add(
                new Question(
                        "Which symbol is used to end a Java statement?",
                        new String[]{
                                ".",
                                ":",
                                ";",
                                ","
                        },
                        2
                )
        );

        // Question 8
        questions.add(
                new Question(
                        "Which keyword prevents a class from being inherited?",
                        new String[]{
                                "static",
                                "private",
                                "final",
                                "protected"
                        },
                        2
                )
        );

        // Question 9
        questions.add(
                new Question(
                        "Which concept allows the same method name to have different forms?",
                        new String[]{
                                "Inheritance",
                                "Polymorphism",
                                "Encapsulation",
                                "Abstraction"
                        },
                        1
                )
        );

        // Question 10
        questions.add(
                new Question(
                        "Which keyword is used to define a class in Java?",
                        new String[]{
                                "define",
                                "object",
                                "class",
                                "struct"
                        },
                        2
                )
        );

        // Randomize question order
        Collections.shuffle(questions);
    }

    // ==========================================
    // USER INTERFACE
    // ==========================================

    private void createUI() {

        setTitle(
                "Online Examination - " +
                user.getDisplayName()
        );

        setSize(750, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                WindowConstants.DO_NOTHING_ON_CLOSE
        );

        addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent e) {

                        confirmExit();
                    }
                }
        );

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        // ==========================================
        // TOP PANEL
        // ==========================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        questionNumberLabel =
                new JLabel("Question");

        timerLabel =
                new JLabel("10:00");

        timerLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        topPanel.add(
                questionNumberLabel,
                BorderLayout.WEST
        );

        topPanel.add(
                timerLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // QUESTION PANEL
        // ==========================================

        JPanel questionPanel =
                new JPanel();

        questionPanel.setLayout(
                new BoxLayout(
                        questionPanel,
                        BoxLayout.Y_AXIS
                )
        );

        questionLabel =
                new JLabel();

        questionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        questionPanel.add(
                questionLabel
        );

        questionPanel.add(
                Box.createVerticalStrut(25)
        );

        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        optionGroup = new ButtonGroup();

        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);

        questionPanel.add(optionA);
        questionPanel.add(optionB);
        questionPanel.add(optionC);
        questionPanel.add(optionD);

        mainPanel.add(
                questionPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel();

        previousButton =
                new JButton("Previous");

        nextButton =
                new JButton("Next");

        submitButton =
                new JButton("Submit Exam");

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        previousButton.addActionListener(
                e -> previousQuestion()
        );

        nextButton.addActionListener(
                e -> nextQuestion()
        );

        submitButton.addActionListener(
                e -> confirmSubmit()
        );
    }

    // ==========================================
    // LOAD QUESTION
    // ==========================================

    private void loadQuestion() {

        Question question =
                questions.get(currentQuestion);

        questionNumberLabel.setText(
                "Question " +
                (currentQuestion + 1) +
                " of " +
                questions.size()
        );

        questionLabel.setText(
                "<html><b>" +
                question.getQuestion() +
                "</b></html>"
        );

        String[] options =
                question.getOptions();

        optionA.setText(
                "A. " + options[0]
        );

        optionB.setText(
                "B. " + options[1]
        );

        optionC.setText(
                "C. " + options[2]
        );

        optionD.setText(
                "D. " + options[3]
        );

        optionGroup.clearSelection();

        int selected =
                selectedAnswers[currentQuestion];

        if (selected == 0) {
            optionA.setSelected(true);
        }
        else if (selected == 1) {
            optionB.setSelected(true);
        }
        else if (selected == 2) {
            optionC.setSelected(true);
        }
        else if (selected == 3) {
            optionD.setSelected(true);
        }

        previousButton.setEnabled(
                currentQuestion > 0
        );

        nextButton.setEnabled(
                currentQuestion <
                questions.size() - 1
        );
    }

    // ==========================================
    // SAVE ANSWER
    // ==========================================

    private void saveAnswer() {

        if (optionA.isSelected()) {
            selectedAnswers[currentQuestion] = 0;
        }
        else if (optionB.isSelected()) {
            selectedAnswers[currentQuestion] = 1;
        }
        else if (optionC.isSelected()) {
            selectedAnswers[currentQuestion] = 2;
        }
        else if (optionD.isSelected()) {
            selectedAnswers[currentQuestion] = 3;
        }
    }

    // ==========================================
    // NEXT QUESTION
    // ==========================================

    private void nextQuestion() {

        saveAnswer();

        if (currentQuestion <
                questions.size() - 1) {

            currentQuestion++;

            loadQuestion();
        }
    }

    // ==========================================
    // PREVIOUS QUESTION
    // ==========================================

    private void previousQuestion() {

        saveAnswer();

        if (currentQuestion > 0) {

            currentQuestion--;

            loadQuestion();
        }
    }

    // ==========================================
    // START TIMER
    // ==========================================

    private void startTimer() {

        timer = new Timer(
                1000,
                e -> updateTimer()
        );

        timer.start();
    }

    // ==========================================
    // UPDATE TIMER
    // ==========================================

    private void updateTimer() {

        timeRemaining--;

        int minutes =
                timeRemaining / 60;

        int seconds =
                timeRemaining % 60;

        timerLabel.setText(
                String.format(
                        "%02d:%02d",
                        minutes,
                        seconds
                )
        );

        // TIME OVER
        if (timeRemaining <= 0) {

            timer.stop();

            JOptionPane.showMessageDialog(
                    this,
                    "Time is over. Your exam will be submitted automatically."
            );

            submitExam();
        }
    }

    // ==========================================
    // CONFIRM SUBMIT
    // ==========================================

    private void confirmSubmit() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to submit the exam?",
                        "Confirm Submission",
                        JOptionPane.YES_NO_OPTION
                );

        if (result ==
                JOptionPane.YES_OPTION) {

            submitExam();
        }
    }

    // ==========================================
    // SUBMIT EXAM
    // ==========================================

    private void submitExam() {

        saveAnswer();

        if (timer != null) {
            timer.stop();
        }

        int correct = 0;

        for (int i = 0;
             i < questions.size();
             i++) {

            if (
                    selectedAnswers[i] ==
                    questions
                            .get(i)
                            .getCorrectAnswer()
            ) {

                correct++;
            }
        }

        int total =
                questions.size();

        int incorrect =
                total - correct;

        int timeUsed =
                (10 * 60) -
                timeRemaining;

        dispose();

        new ResultFrame(
                user,
                total,
                correct,
                incorrect,
                timeUsed
        );
    }

    // ==========================================
    // EXIT EXAM
    // ==========================================

    private void confirmExit() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to quit the exam?",
                        "Exit Exam",
                        JOptionPane.YES_NO_OPTION
                );

        if (result ==
                JOptionPane.YES_OPTION) {

            if (timer != null) {
                timer.stop();
            }

            dispose();

            new LoginFrame();
        }
    }
}
