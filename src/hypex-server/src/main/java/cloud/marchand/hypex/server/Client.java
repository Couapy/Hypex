package cloud.marchand.hypex.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread {

    private String address;
    private int port;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        start();
    }

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
