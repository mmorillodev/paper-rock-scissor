package io;

import utils.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInput {

    private BufferedReader reader;

    public ClientInput(Socket clientSocket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String readLine() throws IOException {
        return this.reader.readLine();
    }
}
