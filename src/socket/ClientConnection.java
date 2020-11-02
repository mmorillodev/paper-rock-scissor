package socket;

import utils.Console;
import utils.ScannerUtils;

import static resources.Environment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {

    private Socket socket;
    private PrintWriter writer;
    private ScannerUtils scanner;

    public ClientConnection() {
        this.scanner = new ScannerUtils();
    }

    public void init() {
        connectToServer();

        new MessageReaderThread().start();

        while(true) {
            sendMessage(scanner.getString());
        }
    }

    private void connectToServer() {
        try {
            Console.br();

            socket = new Socket(scanner.getStringWithMessage("Enter the IP to connect: "), DEFAULT_PORT);
            this.writer = new PrintWriter(socket.getOutputStream());

            Console.br();
        } catch (IOException e) {
            handleConnectionException(e);
        }
    }

    public void handleConnectionException(IOException e) {
        if (e.getMessage().contains("Connection refused")) {
            Console.err("Server not found! " + e.getMessage());
        }
    }

    private void sendMessage(String message) {
        this.writer.println(message.trim());
        this.writer.flush();
    }

    class MessageReaderThread extends Thread {

        @Override
        public void run() {
            try {
                readMessages();
            }
            catch (IOException e) {
                Console.println("Server disconnected!");
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
    }
}
