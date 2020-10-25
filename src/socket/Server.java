package socket;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import entity.Client;
import exceptions.NoSuchPartyException;
import interfaces.OnClientConnectedListener;
import interfaces.OnMessageSentListener;
import utils.ClientSetting;
import utils.Console;
import utils.GamePartyManager;
import utils.ScannerUtils;

import static java.lang.System.identityHashCode;
import static utils.StaticResources.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import static java.lang.System.out;

public class Server implements OnClientConnectedListener {

    private ServerSocket server;
    GamePartyManager manager = new GamePartyManager();

    public Server() {
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
        ScannerUtils scanner = new ScannerUtils();

        Console.br();

        AwaitClientThread awaitClientThread = new AwaitClientThread();
        awaitClientThread.setOnClientConnectedListener(this);
        awaitClientThread.start();

        Console.println("Everything set up! To connect to this server use the IP address showed bellow:\n" + InetAddress.getLocalHost().getHostAddress());
    }

    @Override
    public void onClientConnected(Client client) {
        Console.println("Client connected");

        new ClientSetting(client, manager).startInitialConfigs();

        
    }

    private class AwaitClientThread extends Thread implements OnMessageSentListener {

        private boolean keepWaiting = true;
        private OnClientConnectedListener onClientConnectedListener;

        @Override
        public void run() {
            try {
                Console.println("star waiting clients");
                getClients();
            }
            catch (IOException e) {
                out.println(PREFIX_EXCEPTION_THROWN + e.getMessage());
                keepWaiting = false;
            }
        }

        void getClients() throws IOException {
            while (keepWaiting) {
                Client client = new Client(server.accept());
                if(onClientConnectedListener != null) {
                    onClientConnectedListener.onClientConnected(client);
                }
            }
        }

        @Override
        public void onMessageSent(String message) {

        }

        public void setOnClientConnectedListener(OnClientConnectedListener onClientConnectedListener) {
            this.onClientConnectedListener = onClientConnectedListener;
        }
    }
}