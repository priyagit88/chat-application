import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatHistory {
    // private List<String> history = new ArrayList<>(); // REMOVED: Dont keep previous chat history
    private List<ClientHandler> observers = new ArrayList<>();

    // Synchronized method to add message ensures only one thread updates history at a time
    public synchronized void addMessage(String message) {
        // history.add(message); // REMOVED
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String finalMessage = "[" + timestamp + "] " + message;
        System.out.println("New message: " + finalMessage);
        broadcast(finalMessage);
    }

    // Broadcast message to all connected clients
    private synchronized void broadcast(String message) {
        for (ClientHandler observer : observers) {
            observer.sendMessage(message);
        }
    }

    public synchronized void registerObserver(ClientHandler observer) {
        observers.add(observer);
        broadcastUserList();
    }

    public synchronized void removeObserver(ClientHandler observer) {
        observers.remove(observer);
        broadcastUserList();
    }

    private synchronized void broadcastUserList() {
        String users = observers.stream()
                .map(ClientHandler::getClientName)
                .filter(name -> name != null)
                .collect(Collectors.joining(","));
        broadcast("USERLIST:" + users);
    }
}
