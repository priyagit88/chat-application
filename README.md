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

Running the project is very straightforward because it includes helpful `.bat` files that use a bundled Java Development Kit (JDK 17). This means you don't even need to have Java installed on your computer manually!

Here is how you can run it:

### Step 1: Start the Chat Server
The server routes all messages and must be running before any chat clients can connect.
- Open your File Explorer and navigate to `d:\All_projects\sem1\chat_application`.
- Double-click on **`run_server.bat`**. 
- A command prompt window will open displaying some configuration text, followed by the server starting. Leave this window open!

### Step 2: Start a Chat Client
Once the server is running, you can connect to it. You have two options for the client, and you can open as many of them as you want to simulate multiple users:

**Option A - The Graphical Client (GUI) - Recommended**
- Double-click on **`run_gui.bat`**.
- This will open a nice windowed interface where you can enter your username and chat intuitively.

**Option B - The Console Client**
- Double-click on **`run_client.bat`**.
- This will open up a command prompt window where you can type commands and chat directly from the terminal.

### Summary
To see the chat in action, simply double click **`run_server.bat`**, wait a second, and then double click **`run_gui.bat`** a couple of times to spawn multiple chat users. Try sending messages between them!
