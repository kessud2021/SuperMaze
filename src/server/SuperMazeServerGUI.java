import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperMazeServerGUI extends JFrame {
    private final JTextArea logArea;
    private final JButton startButton;
    private final JTextField portField;
    private SuperMazeServer server;

    public SuperMazeServerGUI() {
        setTitle("SuperMaze Server GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Port:"));
        portField = new JTextField("8080", 8);
        topPanel.add(portField);

        startButton = new JButton("Start Server");
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        server = new SuperMazeServer(this);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int port = Integer.parseInt(portField.getText());
                    server.startServer(port);
                } catch (NumberFormatException ex) {
                    appendLog("⚠️ Invalid port number!");
                }
            }
        });
    }

    // ✅ Made public so SuperMazeServer can log messages
    public void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SuperMazeServerGUI().setVisible(true);
        });
    }
}
