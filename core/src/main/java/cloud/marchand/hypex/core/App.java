package cloud.marchand.hypex.core;

import cloud.marchand.hypex.core.models.Game;

public class App {

    public static void main(String[] args) {
        Game game = new Game(96);
        game.run();
    }
    
}
