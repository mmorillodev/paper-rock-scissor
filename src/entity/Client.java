package entity;

import utils.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.reader       = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer       = new PrintWriter(clientSocket.getOutputStream());

        Thread waitMessagesThread = new WaitMessagesThread(this);
        waitMessagesThread.setName("messageReader-" + toString());
        waitMessagesThread.start();
    }

    public String readLine() throws IOException {
        return this.reader.readLine();
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    private class WaitMessagesThread extends Thread {
        Client client;

        public WaitMessagesThread(Client client) {
            this.client = client;
        }

        @Override
        public void run() {
            boolean isRunning = true;
            String message;

            while(isRunning) {
                try {
                    message = client.readLine();
                    Console.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}