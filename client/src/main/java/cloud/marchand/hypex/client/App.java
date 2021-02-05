package cloud.marchand.hypex.client;

import java.io.IOException;

import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.map.TilledMap;

public class App {

    public static void main(String[] args) throws IOException {
        Map map = new TilledMap("testMap.txt");
        Window window = new Window(map);
        window.setVisible(true);
    }

}
