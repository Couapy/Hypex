package cloud.marchand.hypex.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import cloud.marchand.hypex.core.models.Game;

/**
 * Handle new incoming 
 */
public class Server extends Thread {

    private ServerSocket server;

    private int port;
    private boolean running = true;

    private Game game;

    private ProtocolServer protocol;

    private List<Connection> connections;

    public Server(Game game, int port, ProtocolServer protocol) {
        this.game = game;
        this.port = port;
        this.protocol = protocol;
        this.connections = new ArrayList<>();
        start();
    }

    public void run() {
        System.out.println("[INFO][SERVER] Started");
        try {
            server = new ServerSocket(port);
            while (running && !server.isClosed()) {
                Socket socket = server.accept();
                connections.add(new Connection(socket, protocol));
			}
            close();
        } catch (SocketException e) {
        } catch (IOException e) {
            System.out.println("[ERROR][SERVER] Port " + port + " is already used.");
        }
        System.out.println("[INFO][SERVER] Closed");
    }

    public void close() {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Connection con : connections) {
            if (con.isConnected()) {
                con.closeConnection();
            }
        }
        running = false;
	}
    
}
