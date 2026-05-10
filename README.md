# Chat Application

A simple, multi-component TCP/IP Chat Application built in Java. It allows multiple clients to connect to a central server and chat with each other in real-time. It includes both a console-based client and a graphical user interface (GUI) client.

## Features

- **Multi-client Support**: Handles multiple concurrent connections using a threaded server.
- **Console Interface**: A lightweight console-based client `ChatClient` for interacting via the terminal.
- **GUI Client**: A user-friendly graphical interface `ChatClientGUI` with chat bubbles, scrollable history, and clear user inputs.
- **Chat History**: Basic storage or management representing messages history (`ChatHistory`).
- **Bundled JDK**: Designed to run seamlessly without needing to globally install Java, by utilizing a bundled JDK (JDK 17).

## Running the Application

Double-click the provided batch files to launch the application components. The batch scripts automatically set the environment variables to use the bundled JDK (so you don't need to configure Java externally).

### 1. Start the Server
Run `run_server.bat` 
Start this first. It spins up the central `ChatServer` that routes all messages between connected clients.

### 2. Start a Client
You can start as many clients as you want to simulate multiple users in the chat room.

- **For the GUI version:** Run `run_gui.bat`
- **For the Console version:** Run `run_client.bat`

## Project Structure

- `ChatServer.java`: The core server that listens for incoming connections and broadcasts messages.
- `ClientHandler.java`: Manages the communication socket for individual clients on the server-side.
- `ChatClient.java`: The console-based client implementation.
- `ChatClientGUI.java`: The Swing/AWT-based graphical client implementation.
- `ChatHistory.java`: Utility mapping/managing message logs across the server.

## Technologies Used
- Java 17
- Java Sockets (Networking)
- Java Swing/AWT (GUI) 
