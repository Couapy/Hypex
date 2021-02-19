package cloud.marchand.hypex.client;

import java.awt.Dimension;
import java.io.IOException;

import cloud.marchand.hypex.client.vues.GameVue;
import cloud.marchand.hypex.client.vues.MenuVue;
import cloud.marchand.hypex.core.models.Map;

/**
 * Main application of Hypex game.
 */
public class App {

    /**
     * Graphical user interface.
     */
    private Window window;

    /**
     * Launch a new instance of the game.
     */
    public App() {
        Dimension windowSize = new Dimension(960, 720);
        window = new Window(windowSize);
        window.setVue(new MenuVue(this));
        window.setVisible(true);
    }

    /**
     * Get the window object of the current instance.
     * 
     * @return JFrame object
     */
    public Window getWindow() {
        return window;
    }

    /**
     * Main entry point.
     * 
     * @param args arguments for the program
     */
    public static void main(String[] args) {
        App app = new App();
        if (args.length > 0 && args[0].equals("--debug")) {
            Map map;
            try {
                map = Map.fromFile("maps/anarchy.txt");
                Pov pov = new Pov(map.getOrigin());
                app.getWindow().setVue(new GameVue(app, map, pov));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

}
