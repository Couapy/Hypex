package cloud.marchand.hypex.core;

import java.io.IOException;

import cloud.marchand.hypex.core.models.Game;
import cloud.marchand.hypex.core.models.Map;

public class App {

    public static void main(String[] args) throws IOException {
        Map map = Map.fromFile("maps/anarchy.txt");
        Game game = new Game(map, 96);
        game.run();
    }
    
}
