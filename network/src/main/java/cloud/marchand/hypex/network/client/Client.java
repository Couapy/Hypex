package cloud.marchand.hypex.network.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Network client.
 */
public class Client extends Thread {

    /**
     * Adress of the server.
     */
    private String address;

    /**
     * Port of the server.
     */
    private int port;

    /**
     * Initialize client.
     * @param address server address
     * @param port server port
     */
    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        start();
    }

    /**
     * Handle communication channels with the server.
     */
    public void run() {
        System.out.println("[INFO][CLIENT] Started");
        try {
            Socket socket = new Socket(address, port);
            PrintStream output = new PrintStream(socket.getOutputStream());
            output.println("Hello world !");
            output.println("BYE");
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("[ERROR][CLIENT] Wrong connection informations.");
        }
        System.out.println("[INFO][CLIENT] Stopped");
    }
    
}
