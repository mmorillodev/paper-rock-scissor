package utils;

public class StaticResources {

    public static final String MENU_OPTIONS;

    public static final String MSG_SERVER_PORT_REQUEST;
    public static final String MSG_DISCONNECTED_CLIENT;
    public static final String MSG_CONNECTED_CLIENT;
    public static final String MSG_SERVER_DISCONNECTED;
    public static final String MSG_IP_REQUEST;
    public static final String MSG_NAME_REQUEST;
    public static final String MSG_CONNECTION_PORT_REQUEST;
    public static final String MSG_CONNECTION_REFUSED;

    public static final String PREFIX_SERVER_NOT_FOUND_ERROR;
    public static final String PREFIX_EXCEPTION_THROWN;
    public static final String PREFIX_SHUTDOWN_SERVER;
    public static final String PREFIX_LEAVE_SERVER;
    public static final String PREFIX_CLEAR_CLI;
    public static final String PREFIX_CHANGE_NICKNAME;

    public static final String ERROR_BUSY_PORT;

    public static final int OPT_CREATE_MATCH;
    public static final int OPT_JOIN_MATCH;

    static {
        MENU_OPTIONS = "[1] Create match\n[2] Join match:\n";

        MSG_SERVER_PORT_REQUEST = "Enter the port the server will listen: ";
        MSG_DISCONNECTED_CLIENT = "Client disconnected!";
        MSG_CONNECTED_CLIENT = "Client connected!";
        MSG_SERVER_DISCONNECTED = "Server disconnected!";
        MSG_IP_REQUEST = "Enter the IP to connect: ";
        MSG_NAME_REQUEST = "Type your name: ";
        MSG_CONNECTION_REFUSED = "Connection refused";
        MSG_CONNECTION_PORT_REQUEST = "Enter the port to connect: ";

        PREFIX_EXCEPTION_THROWN = "Unexpected error thrown: ";
        PREFIX_SERVER_NOT_FOUND_ERROR = "Server not found! ";
        PREFIX_LEAVE_SERVER = "!EXIT";
        PREFIX_SHUTDOWN_SERVER = "!SHUTDOWN";
        PREFIX_CLEAR_CLI = "!CLS";
        PREFIX_CHANGE_NICKNAME = "!CHANGE";

        ERROR_BUSY_PORT = "Port already in use!";

        OPT_CREATE_MATCH = 1;
        OPT_JOIN_MATCH = 2;
    }
}
