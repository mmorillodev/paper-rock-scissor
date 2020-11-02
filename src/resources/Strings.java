package resources;

public class Strings {

    public static final String MENU_OPTIONS;

    public static final String MSG_DISCONNECTED_CLIENT;
    public static final String MSG_CONNECTED_CLIENT;
    public static final String MSG_SERVER_DISCONNECTED;
    public static final String MSG_IP_REQUEST;
    public static final String MSG_CONNECTION_REFUSED;

    public static final String PREFIX_SERVER_NOT_FOUND_ERROR;
    public static final String PREFIX_EXCEPTION_THROWN;
    public static final String ERROR_BUSY_PORT;

    public static final String OPTS_PLAYS;

    public static final int OPT_CREATE_MATCH;
    public static final int OPT_JOIN_MATCH;
    public static final int DEFAULT_PORT;

    static {
        MENU_OPTIONS = "[1] Server\n" +
                        "[2] Client\n" +
                        "> ";

        MSG_DISCONNECTED_CLIENT = "Client disconnected!";
        MSG_CONNECTED_CLIENT = "Client connected!";
        MSG_SERVER_DISCONNECTED = "Server disconnected!";
        MSG_IP_REQUEST = "Enter the IP to connect: ";
        MSG_CONNECTION_REFUSED = "Connection refused";

        PREFIX_EXCEPTION_THROWN = "Unexpected error thrown: ";
        PREFIX_SERVER_NOT_FOUND_ERROR = "Server not found! ";
        ERROR_BUSY_PORT = "Port already in use!";

        OPTS_PLAYS = "[1] Rock\n" +
                      "[2] Paper\n" +
                      "[3] Scizor\n" +
                      "> ";

        OPT_CREATE_MATCH = 1;
        OPT_JOIN_MATCH = 2;
        DEFAULT_PORT = 8080;
    }
}
