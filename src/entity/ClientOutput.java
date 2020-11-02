package entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientOutput {
    private PrintWriter writer;

    public ClientOutput(Socket clientSocket) throws IOException {
        this.writer = new PrintWriter(clientSocket.getOutputStream());
    }

    public void sendMessage(String message) {
        br();

        writer.println(message);
        writer.flush();
    }

    public void br() {
        writer.println();
        writer.flush();
    }
}
