package cloud.marchand.hypex.gui;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Map map = Map.loadFromFile("testMap.txt");
        Window window = new Window(map);
        window.setVisible(true);
    }

}
