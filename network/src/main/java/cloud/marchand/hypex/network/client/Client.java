package cloud.marchand.hypex.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import cloud.marchand.hypex.core.models.Map;

/**
 * Network client.
 */
public class Client extends Thread {

    private static int CLIENT_INSTANCES = 0;

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

    /**
     * Socket of the connection.
     */
    private Socket socket;

    /**
     * Input stream.
     */
    private BufferedReader input;

    /**
     * Output stream.
     */
    private PrintStream output;

    /**
     * Client-side protocol of communication.
     */
    private ProtocolDecoder decoder;

    /**
     * Server-side protocol of communication.
     */
    private ProtocolEncoder encoder;

    /**
     * Map loaded by server.
     */
    private Map map;

    /**
     * Initialize client.
     * 
     * @param address server address
     * @param port    server port
     */
    public Client(String address, int port) {
        CLIENT_INSTANCES++;
        setName("CLIENT#" + CLIENT_INSTANCES);

        this.address = address;
        this.port = port;
        this.decoder = new ProtocolDecoder(this);
        this.encoder = new ProtocolEncoder();
        start();
    }

    /**
     * Handle communication channels with the server.
     */
    @Override
    public void run() {
        connect(address, port);
        if (socket != null && !socket.isClosed()) {
            String data;
            try {
                while ((data = input.readLine()) != null) {
                    decoder.decode(data);
                }
            } catch (IOException e) {
                closeConnnection();
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
            } else {
                break;
            }
        }
        if (socket != null && socket.isConnected()) {
            try {
                output = new PrintStream(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("[ERROR][CLIENT] Connection failed");
        }
        return true;
    }

    /**
     * Close connection with server.
     */
    public void closeConnnection() {
        if (socket == null || socket.isClosed()) {
            return;
        }
        try {
            output.println("BYE");
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
        }
    }

    /**
     * Indicates if the client is ready to play.
     * 
     * @return true if the player can play
     */
    public boolean isReady() {
        return map != null;
    }

    /**
     * Give the map.
     * 
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Defines a new map.
     * 
     * @param map current map
     */
    public void setMap(Map map) {
        this.map = map;
    }

}
