import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientGUI extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    private JPanel chatPanel;
    private JScrollPane chatScroll;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JTextField inputField;
    private String clientName;

    public ChatClientGUI() {
        super("Chat Application");
        
        // Initial setup
        clientName = JOptionPane.showInputDialog(this, "Enter your name:", "Login", JOptionPane.PLAIN_MESSAGE);
        if (clientName == null || clientName.trim().isEmpty()) {
            System.exit(0);
        }
        setTitle("Chat Application - " + clientName);
        
        // Component initialization
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);
        chatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        chatScroll = new JScrollPane(chatPanel);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane userListScroll = new JScrollPane(userList);
        userListScroll.setPreferredSize(new Dimension(150, 0));
        userListScroll.setBorder(BorderFactory.createTitledBorder("Online Users"));

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        
        // Layout
        setLayout(new BorderLayout());
        add(chatScroll, BorderLayout.CENTER);
        add(userListScroll, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        
        // Event Listeners
        inputField.addActionListener(e -> sendMessage());
        sendButton.addActionListener(e -> sendMessage());
        
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // Connect to server
        connectToServer();
    }
    
    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Read "Enter your name:" prompt (consumes it)
            String prompt = in.readLine(); 
            
            // Send name
            out.println(clientName);
            
            // Start listening thread
            new Thread(this::listenForMessages).start();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not connect to server: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("USERLIST:")) {
                    updateUserList(message.substring(9));
                } else {
                    appendMessage(message);
                }
            }
        } catch (IOException e) {
            appendMessage("Connection lost.");
        }
    }
    
    private void updateUserList(String data) {
        SwingUtilities.invokeLater(() -> {
            userListModel.clear();
            if (!data.isEmpty()) {
                String[] users = data.split(",");
                for (String user : users) {
                    userListModel.addElement(user);
                }
            }
        });
    }
    
    private void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JPanel messageBox = createMessageBox(message);
            chatPanel.add(messageBox);
            chatPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            chatPanel.revalidate();
            
            // Scroll to bottom
            SwingUtilities.invokeLater(() -> {
                JScrollBar vertical = chatScroll.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            });
        });
    }
    
    private JPanel createMessageBox(String message) {
        JPanel box = new JPanel();
        box.setLayout(new BorderLayout());
        box.setBackground(new Color(240, 240, 240));
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(76, 175, 80)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Parse timestamp and content
        String timestamp = "";
        String content = message;
        
        if (message.startsWith("[")) {
            int endBracket = message.indexOf("]");
            if (endBracket > 0) {
                timestamp = message.substring(1, endBracket);
                content = message.substring(endBracket + 1).trim();
            }
        }
        
        // Create timestamp label
        JLabel timeLabel = new JLabel(timestamp);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 10));
        timeLabel.setForeground(new Color(102, 102, 102));
        
        // Create content label
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 12));
        contentArea.setForeground(new Color(51, 51, 51));
        contentArea.setBackground(new Color(240, 240, 240));
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        
        // Add to box
        box.add(timeLabel, BorderLayout.NORTH);
        box.add(contentArea, BorderLayout.CENTER);
        
        return box;
    }
    
    private void sendMessage() {
        String text = inputField.getText();
        if (!text.trim().isEmpty()) {
            out.println(text);
            inputField.setText("");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}
