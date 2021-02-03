package cloud.marchand.hypex.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket server;

    private int port;
    private boolean running = true;

    public Server(int port) {
        this.port = port;
        start();
    }

    public void run() {
        System.out.println("[INFO][SERVER] Started");

        try {
            server = new ServerSocket(port);
            while (running) {
                Socket socket = server.accept();
                new Connection(socket);
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
