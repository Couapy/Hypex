package cloud.marchand.hypex.network.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Network client.
 */
public class Client extends Thread {

    /**
     * Max attemps to connect to the server if fail.
     */
    private static final int MAX_ATTEMPTS = 5;

    private static final long ATTEMPT_TIME = 1_000l;

    /**
     * Adress of the server.
     */
    private String address;

    /**
     * Port of the server.
     */
    private int port;

    private Socket socket;

    private InputStream input;

    private PrintStream output;

    /**
     * Initialize client.
     * 
     * @param address server address
     * @param port    server port
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
        connect(address, port);
        if (socket != null && socket.isConnected()) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
                output.println("Hello world ! " + i);
            }
            output.println("BYE");
            try {
            output.close();
            socket.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }

    /**
     * Connect the coordinates.
     * 
     * @param address server address
     * @param port    server port
     * @return true if the connection succeeded, false else
     */
    private boolean connect(String address, int port) {
        int attempt = 1;
        while (attempt <= MAX_ATTEMPTS) {
            try {
                socket = new Socket(address, port);
            } catch (IOException e1) {
                System.out.println("[INFO][CLIENT] Connection attempt " + attempt + " failed");
                attempt++;
            }
            if (socket == null && attempt <= MAX_ATTEMPTS) {
                try {
                    Thread.sleep(ATTEMPT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                break;
            }
        }
        if (socket != null && socket.isConnected()) {
            try {
                output = new PrintStream(socket.getOutputStream());
                input = new BufferedInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            System.out.println("[ERROR][CLIENT] Connection failed");
        }
        return true;
    }

}
