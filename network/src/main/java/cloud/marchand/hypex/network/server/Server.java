package cloud.marchand.hypex.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cloud.marchand.hypex.core.models.Game;


public class Server extends Thread {

    private ServerSocket server;

    private int port;
    private boolean running = true;

    private Game game;

    private ProtocolServer protocol;

    public Server(Game game, int port, ProtocolServer protocol) {
        this.game = game;
        this.port = port;
        this.protocol = protocol;
        start();
    }

    public void run() {
        System.out.println("[INFO][SERVER] Started");

        try {
            server = new ServerSocket(port);
            while (running) {
                Socket socket = server.accept();
                new Connection(socket, protocol);
			}
            server.close();
        } catch (IOException e) {
            System.out.println("[ERROR][SERVER] Port " + port + " is already used.");
        }

        System.out.println("[INFO][SERVER] Stopped");
    }

    public void close() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
	}
    
}
