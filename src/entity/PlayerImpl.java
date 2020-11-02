package entity;

import io.ClientInput;
import io.ClientOutput;

import java.io.IOException;
import java.net.Socket;

public class PlayerImpl extends Player {

    public ClientOutput out;
    public ClientInput in;

    private int play;

    public PlayerImpl(Socket clientSocket) throws IOException {
        this.in = new ClientInput(clientSocket);
        this.out = new ClientOutput(clientSocket);
    }

    @Override
    public int getPlay() throws IOException {
        boolean persist = false;

        do {
            try {
                this.play = Integer.parseInt(sendQuestion("[1] Rock\n[2] Paper\n[3] Scizor\n> "));
            }
            catch (NumberFormatException e) {
                persist = true;
            }
        } while (persist);

        return this.play;
    }

    @Override
    public void notifyLoss() {
        super.notifyLoss();
        out.sendMessage("You lost!");
    }

    @Override
    public void notifyWin() {
        super.notifyWin();
        out.sendMessage("You win!");
    }

    @Override
    public void notifyDraw() {
        super.notifyDraw();
        out.sendMessage("Draw!");
    }

    public String sendQuestion(String question) throws IOException {
        out.sendMessage(question);
        return in.readLine();
    }
}