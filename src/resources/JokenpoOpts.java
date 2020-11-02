package resources;

public enum JokenpoOpts {
    ROCK(1), PAPER(2), SCISSOR(3);

    public int play;

    JokenpoOpts(int opt) {
        this.play = opt;
    }

    public String getString() {
        switch (play) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissor";
        }
        return "invalid";
    }

    public static JokenpoOpts fromInt(int opt) {
        switch (opt) {
            case 1: return ROCK;
            case 2: return PAPER;
            case 3: return SCISSOR;
            default: return null;
        }
    }
}
