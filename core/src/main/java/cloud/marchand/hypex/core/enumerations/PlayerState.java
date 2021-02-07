package cloud.marchand.hypex.core.enumerations;

/**
 * Represent the player state.
 */
public enum PlayerState {
    /**
     * The player has just been connected to the server.
     */
    CONNECTED,
    /**
     * The player is picking a team, and a player class.
     */
    PICKING,
    /**
     * The player is playing in the game.
     */
    PLAYING,
    /**
     * The player is dead.
     * @see cloud.marchand.hypex.core.interfaces.PlayerInterface#maxLife
     * @see cloud.marchand.hypex.core.interfaces.PlayerInterface#life
     */
    DEAD
}
