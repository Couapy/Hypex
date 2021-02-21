package cloud.marchand.hypex.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import cloud.marchand.hypex.core.models.Player;

/**
 * Represent a connection with a client.
 */
public class Connection extends Thread {

    /**
     * Identifier of the connection.
     */
    private static int numberConnections = 0;

    /**
     * Client socket.
     */
    private Socket socket;

    /**
     * Input buffer.
     */
    private BufferedReader input;

    /**
     * Output buffer.
     */
    private PrintStream output;

    /**
     * Indicates if the connection is active.
     */
    private boolean active = true;

    /**
     * Current protocol of communication.
     */
    private ProtocolServer protocol;

    /**
     * Player linked to this connection.
     */
    private Player player;

    /**
     * Instanciate the connection, and set up the input and output.
     * 
     * @param socket client socket
     */
    public Connection(Socket socket, ProtocolServer protocol) {
        numberConnections++;
        setName("Player#" + numberConnections);
        System.out.println("[INFO][" + getName() + "] " + socket.getInetAddress() + " connected.");
        this.socket = socket;
        this.protocol = protocol;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            closeConnection();
        }
        start();
    }

    /**
     * Handler data sended.
     */
    @Override
    public void run() {
        String data;
        while (active) {
            try {
                while ((data = input.readLine()) != null) {
                    protocol.parse(this, data);
                }
            } catch (IOException e) {
                closeConnection();
            }
        }
    }

    /**
     * Send a message through the socket.
     * 
     * @param message message to deliver
     */
    public void send(String message) {
        output.println(message);
    }

    /**
     * Close connection with client.
     */
    public void closeConnection() {
        if (socket == null || socket.isClosed()) {
            active = false;
            return;
        }
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            socket.close();
        } catch (IOException e) {
        } finally {
            active = false;
        }
        System.out.println("[INFO][" + getName() + "] " + socket.getInetAddress() + " disconnected.");
    }

    /**
     * Indicates if a connection is alive
     * 
     * @return true if the connection is remaining
     */
    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }

    /**
     * Give the player linked to this connection.
     * 
     * @return player object
     */
    public Player getPlayer() {
        return player;
    }

}
