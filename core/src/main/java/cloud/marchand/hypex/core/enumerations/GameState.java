package cloud.marchand.hypex.core.enumerations;

/**
 * Indicates the game status.
 */
public enum GameState {
    /**
     * Initialisation phase.
     */
    STARTING,
    /**
     * A round is played.
     */
    RUNNING,
    /**
     * A rouns has just ended. This is a break period.
     */
    ROUND_ENDED,
    /**
     * The game has just ended. The scores are displayed. User action is required to
     * restart a new game.
     */
    ENDED
}
