package cloud.marchand.hypex.client;

import java.awt.Dimension;
import java.io.IOException;

import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.map.TilledMap;
import cloud.marchand.hypex.client.vues.GameVue;

public class App {

    public static void main(String[] args) throws IOException {
        Map map = new TilledMap("testMap.txt");
        GameVue vue = new GameVue(map);

        Window window2D = new Window(new Dimension(1440, 1024), vue);
        window2D.setVisible(true);
    }

}
