package cloud.marchand.hypex.client.vues;

import cloud.marchand.hypex.client.App;
import cloud.marchand.hypex.client.layer.gamevue.FPSCounter;
import cloud.marchand.hypex.client.layer.gamevue.MapRenderer;
import cloud.marchand.hypex.client.layer.gamevue.RaycastingSkeleton;
import cloud.marchand.hypex.client.map.Map;

@SuppressWarnings("serial")
public class Game2DVue extends GameVue {

    public Game2DVue(App app, Map map) {
        super(app, map);
        layers.clear();

        layers.add(new MapRenderer());
        layers.add(new RaycastingSkeleton(this));
        layers.add(new FPSCounter());
    }
    
}
