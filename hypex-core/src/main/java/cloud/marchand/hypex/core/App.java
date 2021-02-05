package cloud.marchand.hypex.core;

import cloud.marchand.hypex.core.models.Game;

public class App {

    public static void main(String[] args) {
        new Game(96){
            @Override
            protected void initialize() {
            }
        };
    }
    
}
