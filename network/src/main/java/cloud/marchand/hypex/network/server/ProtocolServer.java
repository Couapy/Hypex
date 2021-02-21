package cloud.marchand.hypex.network.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cloud.marchand.hypex.core.models.Game;
import cloud.marchand.hypex.core.models.Player;
import cloud.marchand.hypex.core.models.Team;

public class ProtocolServer {

    private Game game;

    public ProtocolServer(Game game) {
        this.game = game;
    }

    public void parse(Connection con, String data) {
        String instruction = data.split(":")[0];
        List<String> arguments = Arrays.asList(data.split(":"));

        Player player = con.getPlayer();

        switch (instruction) {
            case "FIRE":
                if (arguments.get(1).equals("on")) {
                    player.shoot();
                }
                else {
                    player.unShoot();
                }
                if (arguments.get(2).equals("on")) {
                    player.shootSecondary();
                }
                else {
                    player.unShootSecondary();
                }
                break;
            case "NAME":
                player.setName(arguments.get(1));
                break;
            case "MOVE":
                player.moveForward(arguments.contains("f"));
                player.moveBackward(arguments.contains("b"));
                player.moveLeft(arguments.contains("l"));
                player.moveRight(arguments.contains("r"));
                break;
            case "SELECT":
                player.changeWeapon(Integer.parseInt(arguments.get(1)));
                break;
            case "TEAM":
                Team team = game.getTeam(Integer.parseInt(arguments.get(1)));
                if (team != null) {
                    game.onConnect(player, team);
                }
                break;
            case "CONFIGURATION":
                // TODO
                con.send("NO_CONFIGURATION_YET");
                break;
            case "BYE":
                con.closeConnection();
                break;
            default:
                System.out.println("[WARN][" + con.getName() + "] > " + instruction);
                break;
        }
    }

}
