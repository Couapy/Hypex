package cloud.marchand.hypex.client;

import java.awt.Dimension;
import java.io.IOException;

import cloud.marchand.hypex.client.layer.FPSCounter;
import cloud.marchand.hypex.client.layer.RaycastingSkeleton;
import cloud.marchand.hypex.client.layer.Renderer;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.client.map.TilledMap;

public class App {

    public static void main(String[] args) throws IOException {
        Map map = new TilledMap("testMap.txt");
        Canvas canvas2D = new Canvas(map);
        canvas2D.addLayer(new Renderer());
        canvas2D.addLayer(new RaycastingSkeleton(canvas2D));
        canvas2D.addLayer(new FPSCounter());
        Window window2D = new Window(new Dimension(1440, 1024), canvas2D);
        window2D.setVisible(true);
    }

}
