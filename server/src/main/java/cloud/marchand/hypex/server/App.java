package cloud.marchand.hypex.server;

import cloud.marchand.hypex.core.models.Game;
import cloud.marchand.hypex.core.models.gamemode.DeathMatch;
import cloud.marchand.hypex.server.client.Client;
import cloud.marchand.hypex.server.server.ProtocolServer;
import cloud.marchand.hypex.server.server.Server;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Game game = new DeathMatch();
        
        ProtocolServer protocol = new ProtocolServer(game);
        Client client = new Client("localhost", 64128);
        Server server = new Server(game, 64128, protocol);

        Thread.sleep(2000);
        
        server.close();
    }

}