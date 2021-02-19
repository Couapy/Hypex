package cloud.marchand.hypex.client;

import java.awt.Dimension;
import java.io.IOException;

import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.map.TilledMap;
import cloud.marchand.hypex.client.vues.ConnectionVue;
import cloud.marchand.hypex.client.vues.CreateGameVue;
import cloud.marchand.hypex.client.vues.Game2DVue;
import cloud.marchand.hypex.client.vues.GameVue;
import cloud.marchand.hypex.core.interfaces.PlayerInterface;
import cloud.marchand.hypex.core.models.Game;

public class App {

    public static void main(String[] args) throws IOException {
        new App();
    }

    private Window window;

    public App() throws IOException {
        Dimension windowSize = new Dimension(960, 720);
        Map map = new TilledMap("testMap.txt");
        window = new Window(windowSize);
        window.setVue(new GameVue(this, map));
        window.setVisible(true);

        Game2DVue vue2D = new Game2DVue(this, map);
        vue2D.pov = ((GameVue)window.getVue()).pov;
        Window window2D = new Window(windowSize);
        window2D.setVue(vue2D);
        window2D.setVisible(true);
    }

    /**
     * Differents views
     * - create a game
     * - connect to a server
     * - play a game
     *  - select a team
     *  - select a player class
     * - manage settings
     */

    public void createGame() {
        window.setVue(new CreateGameVue(this));
	}

    public void connectServer() {
        window.setVue(new ConnectionVue(this));
	}

    public void startGame(Game game, PlayerInterface player) {
        // TODO: window.setVue(new GameVue(game, player))
    }

    public void selectPlayerClass() {
        // TODO: window.setVue(new ClassVue(List<String> classes))
    }

    public void selectTeam() {
        // TODO: window.setVue(new TeamVue(List<Team> teams))
    }

    public Window getWindow() {
        return window;
    }

}
