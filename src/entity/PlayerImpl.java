package entity;

import io.ClientInput;
import io.ClientOutput;
import resources.Strings;

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
                this.play = Integer.parseInt(sendQuestion(Strings.OPTS_PLAYS));
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
        out.sendMessage("You lost");
    }

    @Override
    public void notifyWin() {
        super.notifyWin();
        out.sendMessage("You lost");
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