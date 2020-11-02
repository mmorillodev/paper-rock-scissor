package socket;

import utils.Console;
import utils.ScannerUtils;

import static resources.Strings.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private Socket socket;
    private PrintWriter writer;

    private final ScannerUtils scanner;

    public ClientConnection() {
        scanner = new ScannerUtils();

        try {
           connectToServer();
        }
        catch (IOException e) {
            handleConnectionException(e);
        }
    }

    private void connectToServer() throws IOException {
        Console.br();
        socket = new Socket(scanner.getStringWithMessage(MSG_IP_REQUEST), DEFAULT_PORT);
        this.writer = new PrintWriter(socket.getOutputStream());
    }

    public void handleConnectionException(IOException e) {
        if (e.getMessage().contains(MSG_CONNECTION_REFUSED)) {
            Console.err(PREFIX_SERVER_NOT_FOUND_ERROR + e.getMessage());
        }
    }

    public void init() {
        Console.br();

        Thread messageReaderThread = new Thread(this);
        messageReaderThread.start();

        while(true) {
            sendMessage(scanner.getString());
        }
    }

    @Override
    public void run() {
        try {
            readMessages();
        }
        catch (IOException e) {
            Console.println(MSG_SERVER_DISCONNECTED);
        }
    }

    private void readMessages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message;

        while(true) {
            message = reader.readLine();
            Console.println(message);
        }
    }

    private void sendMessage(String message) {
        this.writer.println(message.trim());
        this.writer.flush();
    }
}
