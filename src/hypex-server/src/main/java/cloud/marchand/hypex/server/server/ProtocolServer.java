package cloud.marchand.hypex.server.server;

import cloud.marchand.hypex.core.models.Game;

public class ProtocolServer {

    private Game game;

    public ProtocolServer(Game game) {
        this.game = game;
    }

    public static void parse(Connection con, String data) {
        String instruction = data.split(":")[0];

        switch (instruction) {
            case "FIRE":
                // TODO
                break;
            case "NAME":
                // TODO
                break;
            case "MOVE":
                // TODO
                break;
            case "SELECT":
                // TODO
                break;
            case "TEAM":
                // TODO
                break;
            case "CONFIGURATION":
                // TODO
                break;
            case "BYE":
                con.closeConnection();
                break;
            default:
                System.out.println("[SERVER][" + con.getName() + "][PROTOCOL] Unknow instruction : " + instruction);
                break;
        }
    }

}
