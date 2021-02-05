package cloud.marchand.hypex.core.models.gamemode;

import cloud.marchand.hypex.core.models.Game;

/**
 * DeathMatch game mode.
 * 
 * Auto-Respawn on
 * Friendly-fire on
 */
public class DeathMatch extends Game {

    @Override
    protected void initialize() {
        // Basic settings
        maxPlayers = 16;
        maxRounds = 1;
        autoRespawn = true;
        friendlyFire = true;

        // Durations
        gameStartingDuration = 20l;
        roundDuration = 120l;
        roundEndedDuration = 20l;
        gameEndedDuration = 60l;
    }

}
