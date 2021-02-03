package cloud.marchand.hypex.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connection extends Thread {

    private static int numberConnections = 0;

    private Socket socket;
    private BufferedReader input;
    private PrintStream output;
    private boolean active = true;

    public Connection(Socket socket) {
        numberConnections++;
        setName("CON#" + numberConnections);
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            closeConnection();
        }
        start();
    }

    public void run() {
        String data;
        System.out.println("[INFO][" + getName() + "] Started");
        while (active) {
            try {
                Thread.sleep(10);
                while ((data = input.readLine()) != null) {
                    System.out.println("[INFO][" + getName() + "] > " + data);
                    if (data.equals("BYE")) {
                        closeConnection();
                    }
                }
            } catch (InterruptedException e) {
            } catch (IOException e) {
                closeConnection();
            }
        }
        System.out.println("[INFO][" + getName() + "] Stopped");
    }

    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
        }
        finally {
            active = false;
        }
    }
    
}
