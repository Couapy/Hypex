package cloud.marchand.hypex.client;

import java.awt.Dimension;
import java.io.IOException;

import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.map.TilledMap;
import cloud.marchand.hypex.client.vues.ConnectionVue;
import cloud.marchand.hypex.client.vues.CreateGameVue;
import cloud.marchand.hypex.client.vues.GameVue;
import cloud.marchand.hypex.client.vues.MenuVue;
import cloud.marchand.hypex.core.interfaces.PlayerInterface;
import cloud.marchand.hypex.core.models.Game;

public class App {

    public static void main(String[] args) throws IOException {
        App application = new App();
        if (args.length != 0 && args[0].equals("--debug")) {
            Map map = new TilledMap("testMap.txt");
            Vue vue = new GameVue(application, map);
            application.window.setVue(vue);
        }
    }

    private Window window;

    public App() throws IOException {
        Vue vue = new MenuVue(this);
        window = new Window(new Dimension(1440, 1024));
        window.setVue(vue);
        window.setVisible(true);
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
