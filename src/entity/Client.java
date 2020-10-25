package entity;

import interfaces.Player;
import utils.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Player {

    private PrintWriter writer;
    private BufferedReader reader;

    public Client(Socket clientSocket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream());
    }

    public String readLine() {
        try {
            return this.reader.readLine();
        } catch(IOException e) {
            Console.err(e.getMessage());

            return null;
        }
    }

    @Override
    public void sendMessage(String message) {
        Console.println(message);
        Console.println(writer);

        writer.println(message);
        writer.flush();
    }

    @Override
    public String sendQuestion(String question) {
        sendMessage(question);
        return readLine();
    }
}