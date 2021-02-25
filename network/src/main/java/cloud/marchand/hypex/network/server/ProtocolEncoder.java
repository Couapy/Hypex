package cloud.marchand.hypex.network.server;

import cloud.marchand.hypex.core.models.Game;
import cloud.marchand.hypex.core.models.Map;

public class ProtocolEncoder {

    private Connection con;
    private Game game;
    private Map map;

    public ProtocolEncoder(Connection con, Game game) {
        this.con = con;
        this.game = game;
        this.map = game.getMap();
    }

    public void sendPos() {
        // TODO
    }

    public void sendMap() {
        String mapRenderedString = map.toString().replace('\n', '+');
        con.send("MAP:" + mapRenderedString);
    }
    
}
