package cloud.marchand.hypex.core.models;

import java.util.HashSet;
import java.util.Set;

import cloud.marchand.hypex.core.enumerations.GameState;
import cloud.marchand.hypex.core.interfaces.MapInterface;
import cloud.marchand.hypex.core.interfaces.PlayerInterface;

/**
 * Game of Hypex
 */
public abstract class Game implements Runnable {

    /**
     * State of the game.
     * 
     * @see cloud.marchand.hypex.core.enumerations.GameState
     */
    private GameState state;
    private boolean running = false;

    /**
     * Map of the game.
     */
    private MapInterface map;

    /**
     * All players of the game.
     */
    private Set<PlayerInterface> players = new HashSet<>();

    /**
     * Teams of players.
     */
    private Set<Team> teams = new HashSet<>();

    /**
     * Number of rounds required for the win.
     */
    protected int maxRounds = 1;

    /**
     * Current round
     */
    private int round;

    /**
     * Start of the round represented by a time.
     * 
     * @see java.lang.System#currentTimeMillis
     */
    private long startRound;

    /**
     * Number of maximum players in a game.
     */
    protected int maxPlayers = 1;

    /**
     * Duration of starting state of the game.
     */
    protected long gameStartingDuration = 2_000l;

    /**
     * Duration of round running state of the game.
     */
    protected long roundDuration = 2_000l;

    /**
     * Duration of round ended state of the game.
     */
    protected long roundEndedDuration = 2_000l;

    /**
     * Duration of game ended state of the game.
     */
    protected long gameEndedDuration = 2_000l;

    /**
     * Indicates if the players respawn during the round after dying.
     */
    protected boolean autoRespawn = false;

    /**
     * Indicates if players can hurts other teammate.
     */
    protected boolean friendlyFire = false;

    /**
     * Indicate if players can move.
     * 
     * @see cloud.marchand.hypex.core.enumerations.GameState#ENDED
     */
    protected boolean canMove = false;

    /**
     * Refresh rate of the game per second.
     */
    private int refreshRate;

    /**
     * Last refreshRateMesured.
     */
    private int refreshRateMesured;

    /**
     * Last time we mesured the refresh rate.
     */
    private long lastRefreshMesure;

    /**
     * Number of refresh iterations during the last second. 
     */
    private int currentRefreshRate;

    /**
     * Duration for one refresh.
     */
    private long refreshDuration;

    /**
     * Create a new game.
     */
    public Game(int refreshRate) {
        setRefreshRate(refreshRate);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Defines the settings of the game.
     */
    protected abstract void initialize();

    /**
     * Intialize and execute the game.
     */
    public void run() {
        initialize();
        setState(GameState.STARTING);
        round = 1;
        startRound = System.currentTimeMillis();
        running = true;

        while (running) {
            waitRefreshRate();
            
            if (state == GameState.STARTING) {
                handleStateStarting();
            } else if (state == GameState.RUNNING) {
                handleStateRunning();
            } else if (state == GameState.ROUND_ENDED) {
                handleStateRoundEnded();
            } else if (state == GameState.ENDED) {
                handleStateEnded();
            }

        }
        System.out.println("[INFO][CORE] Game thread ended.");
    }

    /**
     * Limit the refresh rate.
     */
    private void waitRefreshRate() {
        if (System.currentTimeMillis() >= lastRefreshMesure + 1_000l) {
            refreshRateMesured = currentRefreshRate;
            currentRefreshRate = 0;
            lastRefreshMesure = System.currentTimeMillis();
            System.out.println("[INFO][CORE] " + refreshRateMesured  + " / " + refreshRate);
        }
        else {
            currentRefreshRate++;
        }
        try {
            Thread.sleep(refreshDuration);
        } catch (InterruptedException e) {
        }

    }

    /**
     * Handle the game when starting.
     */
    private void handleStateStarting() {
        if (System.currentTimeMillis() >= startRound + gameStartingDuration) {
            setState(GameState.RUNNING);
        }
    }

    /**
     * Handle the game when the round is running.
     */
    private void handleStateRunning() {
        if (isRoundEnded()) {
            setState(GameState.ROUND_ENDED);
        }
    }

    /**
     * Handle the game when it has been ended.
     */
    private void handleStateEnded() {
        if (System.currentTimeMillis() >= startRound + gameEndedDuration) {
            stop();
        }
    }

    /**
     * Handle the game when the round has been ended.
     */
    private void handleStateRoundEnded() {
        if (System.currentTimeMillis() >= startRound + roundEndedDuration) {
            if (isGameEnded()) {
                setState(GameState.ENDED);
            }
            else {
                setState(GameState.RUNNING);
            }
        }
    }

    /**
     * Change the state of the game.
     * @param gameState state of the game
     */
    private void setState(GameState gameState) {
        startRound = System.currentTimeMillis();
        state = gameState;
        System.out.println("[INFO][CORE] Game changed state : " + gameState);

        if (gameState == GameState.RUNNING) {
            round++;
        }
    }

    /**
     * Stop the thread.
     */
    public void stop() {
        running = false;
    }

    /**
     * Indicates if the round is ended or not.
     * 
     * @return true if the round is ended
     */
    private boolean isRoundEnded() {
        return System.currentTimeMillis() >= startRound + roundDuration;
    }

    /**
     * Indicate if the game is ended or not.
     * 
     * @return true if the round is ended
     */
    private boolean isGameEnded() {
        return round >= maxRounds; // TODO
    }

    /**
     * Defines the refresh rate of the game.
     * @param refreshRate
     */
    private void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
        this.refreshDuration = 1000 / refreshRate;
    }

}
