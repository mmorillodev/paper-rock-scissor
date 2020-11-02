package socket.server;

import entity.PlayerImpl;
import interfaces.listeners.OnClientConnectedListener;
import utils.Console;

import static resources.Strings.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import static java.lang.System.out;

public class ServerConnection implements OnClientConnectedListener {

    private ServerSocket server;
    GamePartyManager manager = new GamePartyManager();

    public ServerConnection() {
        try {
            server = new ServerSocket(DEFAULT_PORT);
        }
        catch(IOException e) {
            Console.err(e.getMessage());
            e.printStackTrace();
            Console.err(ERROR_BUSY_PORT);
        }
    }

    public void init() throws IOException {
        Console.br();

        AwaitClientThread awaitClientThread = new AwaitClientThread();
        awaitClientThread.setOnClientConnectedListener(this);
        awaitClientThread.start();

        Console.println("Everything set up! To connect to this server use the IP address showed bellow:\n" + InetAddress.getLocalHost().getHostAddress());
    }

    @Override
    public void onClientConnected(PlayerImpl playerImpl) {
        Console.println("Client connected");

        new Thread(() -> new ClientSetting(playerImpl, manager).startInitialConfigs()).start();
    }

    private class AwaitClientThread extends Thread {

        private boolean keepWaiting = true;
        private OnClientConnectedListener onClientConnectedListener;

        @Override
        public void run() {
            try {
                getClients();
            }
            catch (IOException e) {
                out.println(PREFIX_EXCEPTION_THROWN + e.getMessage());
                keepWaiting = false;
            }
        }

        void getClients() throws IOException {
            while (keepWaiting) {
                PlayerImpl playerImpl = new PlayerImpl(server.accept());
                if(onClientConnectedListener != null) {
                    onClientConnectedListener.onClientConnected(playerImpl);
                }
            }
        }

        public void setOnClientConnectedListener(OnClientConnectedListener onClientConnectedListener) {
            this.onClientConnectedListener = onClientConnectedListener;
        }
    }
}