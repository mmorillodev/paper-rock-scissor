package entity;

import interfaces.OnMessageSentListener;
import interfaces.Player;
import utils.Console;
import utils.StaticResources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerImpl implements Player {

    private PrintWriter writer;
    private BufferedReader reader;

    private OnMessageSentListener onMessageSentListener;

    private int play;

    public PlayerImpl(Socket clientSocket) throws IOException {
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

    public void setOnMessageSentListener(OnMessageSentListener onMessageSentListener) {
        this.onMessageSentListener = onMessageSentListener;
    }

    @Override
    public void sendMessage(String message) {
        Console.println(message);
        Console.println(writer);

        writer.println(message);
        writer.flush();
    }

    @Override
    public int getPlay() {
        this.play = Integer.parseInt(sendQuestion(StaticResources.OPTS_PLAYS));
        return this.play;
    }

    public String sendQuestion(String question) {
        sendMessage(question);
        return readLine();
    }
}