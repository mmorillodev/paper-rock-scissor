package entity;

import interfaces.OnMessageSentListener;
import utils.Console;
import utils.ScannerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private PrintWriter writer;
    private BufferedReader reader;
    private ScannerUtils scannerUtils;

    private OnMessageSentListener onMessageSentListener;

    public Client(Socket clientSocket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream());

        startClient();
    }

    private void startClient() {
        scannerUtils = new ScannerUtils();
    }

    public String readLine() throws IOException {
        return this.reader.readLine();
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public void addMessageListener(OnMessageSentListener onMessageSentListener) {
        this.onMessageSentListener = onMessageSentListener;
    }

    public String sendQuestion(String question) throws IOException {
        sendMessage(question);
        return readLine();
    }
}