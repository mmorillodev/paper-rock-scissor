package utils;

import exceptions.NoSuchOptionException;
import interfaces.ConnectionHandler;
import sockets.ClientConnection;
import sockets.ServerConnection;

import java.util.HashMap;
import java.util.Map;

public class ConnectionUtils {
    private Map<Character, Class> handlers;

    public ConnectionUtils() {
        handlers = new HashMap() {{
            put(1, ServerConnectionHandler.class);
            put(2, ClientConnectionHandler.class);
        }};
    }

    public void connectOrCreateSession(int option) throws IllegalAccessException, InstantiationException, NoSuchOptionException {
        if(option != StaticResources.OPT_CREATE_MATCH || option != StaticResources.OPT_JOIN_MATCH)
            throw new NoSuchOptionException("Option '" + option + "' couldn't be resolved.");

        ((ConnectionHandler) this.handlers.get(option).newInstance()).connect();
    }
}

class ServerConnectionHandler implements ConnectionHandler {
    @Override
    public void connect() {
        new ServerConnection().init();
    }
}

class ClientConnectionHandler implements ConnectionHandler {
    @Override
    public void connect() {
        new ClientConnection().init();
    }
}