package cloud.marchand.hypex.network.client;

import java.util.logging.Logger;

import cloud.marchand.hypex.core.models.Map;

public class ProtocolDecoder {

    private Client client;
    private Logger logger;

    public ProtocolDecoder(Client client) {
        this.client = client;
        logger = Logger.getLogger(client.getName() + "-Decoder");
    }

	public void decode(String data) {
        String[] fields = data.split(":");
        String header = fields[0];
        String body;
        if (fields.length < 2) {
            body = "";
        } else {
            body = data.substring(header.length() + 1);
        }

        if (header.equals("FIRE")) {
        // } else if (header.equals("NAME")) {
        // } else if (header.equals("MOVE")) {
        // } else if (header.equals("PLAYERS")) {
        // } else if (header.equals("TEAM")) {
        } else if (header.equals("MAP")) {
            Map map = Map.fromString(body.replace('+', '\n'));
            client.setMap(map);
            logger.info("Map loaded");
        } else if (header.equals("BYE")) {
            client.closeConnnection();
        } else {
            logger.warning("unknow message : " + data);
        }
	}

    /**
     * TEAM:0
     * POS:380;120
     * PLAYERS:
     */
    
}
