package cloud.marchand.hypex.server;

public class App {

    public static void main(String[] args) throws InterruptedException {
        new Client("localhost", 64128);
        Server server = new Server(64128);

        Thread.sleep(3000);
        
        server.close();
    }

}
