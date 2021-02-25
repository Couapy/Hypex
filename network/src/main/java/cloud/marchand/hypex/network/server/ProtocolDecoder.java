package cloud.marchand.hypex.network.server;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import cloud.marchand.hypex.core.models.Game;
import cloud.marchand.hypex.core.models.Player;
import cloud.marchand.hypex.core.models.Team;

public class ProtocolDecoder {

    private Connection con;
    private Game game;
    private Player player;

    public ProtocolDecoder(Connection con, Game game) {
        this.con = con;
        this.game = game;
        this.player = con.getPlayer();
    }

    public void decode(String data) {
        String[] fields = data.split(":");
        String header = fields[0];
        String body;
        if (fields.length < 2) {
            body = "";
        } else {
             body = fields[1];
        }

        try {
            if (header.equals("FIRE")) {
                decodeFire(body);
            } else if (header.equals("NAME")) {
                decodeName(body);
            } else if (header.equals("MOVE")) {
                decodeMove(body);
            } else if (header.equals("WEAPON")) {
                decodeWeapon(body);
            } else if (header.equals("TEAM")) {
                decodeTeam(body);
            } else if (header.equals("CONFIG")) {
                con.send("NO_CONFIGURATION_YET"); // TODO
            } else if (header.equals("BYE")) {
                con.closeConnection();
            } else {
                System.out.println("[WARN][" + player.getName() + "] > " + data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            con.send("Unknow error happened : " + data);
        }
    }

    public void decodeFire(String body) {
        String[] arguments = body.split(";");
        if (arguments[0].equals("on")) {
            player.shoot();
        }
        else {
            player.unShoot();
        }
        if (arguments[1].equals("on")) {
            player.shootSecondary();
        }
        else {
            player.unShootSecondary();
        }
    }

    public void decodeName(String body) {
        final Pattern pattern = Pattern.compile("^[A-Za-z_-]{3,}$");
        if (pattern.matcher(body).matches()) {
            player.setName(body);
        } else {
            con.send("Invalid name, do not matches pattern : " + pattern);
        }
    }

    public void decodeMove(String body) {
        List<String> arguments = Arrays.asList(body.split(";"));
        player.moveForward(arguments.contains("f"));
        player.moveBackward(arguments.contains("b"));
        player.moveLeft(arguments.contains("l"));
        player.moveRight(arguments.contains("r"));

        System.out.println(arguments.contains("f"));
        System.out.println(arguments.contains("b"));
        System.out.println(arguments.contains("l"));
        System.out.println(arguments.contains("r"));
    }

    public void decodeWeapon(String body) {
        int index = Integer.parseInt(body);
        if (!player.changeWeapon(index)) {
            con.send("Incorrect weapon index : " + index);
        }
    }

    public void decodeTeam(String body) {
        int index = Integer.parseInt(body);
        Team team = game.getTeam(index);
        if (team != null) {
            game.onConnect(player, team);
        } else {
            con.send("Invalid team index : " + index);
        }
    }
    
}
