import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ChatHistory chatHistory;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket socket, ChatHistory chatHistory) {
        this.socket = socket;
        this.chatHistory = chatHistory;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Enter your name:");
            clientName = in.readLine();
            if (clientName == null) return;
            
            chatHistory.registerObserver(this);
            chatHistory.addMessage(clientName + " has joined the chat.");

            String message;
            while ((message = in.readLine()) != null) {
                chatHistory.addMessage(clientName + ": " + message);
            }
        } catch (IOException e) {
            System.err.println("Error in client handler: " + e.getMessage());
        } finally {
            try {
                chatHistory.removeObserver(this);
                chatHistory.addMessage(clientName + " has left the chat.");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method called by ChatHistory to send message to this specific client
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public String getClientName() {
        return clientName;
    }
}
