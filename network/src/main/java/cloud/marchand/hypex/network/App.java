package cloud.marchand.hypex.network;

import java.io.IOException;

import cloud.marchand.hypex.core.models.Game;
import cloud.marchand.hypex.core.models.Map;
import cloud.marchand.hypex.network.client.Client;
import cloud.marchand.hypex.network.server.Server;

public class App {

    public static void main(String[] args) throws InterruptedException, IOException {
        Map map = Map.fromFile("maps/anarchy.txt");
        Game game = new Game(map);

        Client client = new Client("localhost", 64128);
        Server server = new Server(game, 64128);
        waitXS();
        server.close();
    }

    private static void waitXS() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
        }
    }

}
