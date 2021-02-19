package cloud.marchand.hypex.core.models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cloud.marchand.hypex.core.enumerations.GameState;

/**
 * Game of Hypex
 */
public class Game implements Runnable {

    /**
     * Default refresh rate.
     */
    private static final int DEFAULT_REFRESH_RATE = 64;

    /**
     * State of the game.
     * 
     * @see cloud.marchand.hypex.core.enumerations.GameState
     */
    private GameState state;

    /**
     * Indicates if the game is running.
     */
    private boolean running = false;

    /**
     * Map of the game.
     */
    private Map map;

    /**
     * All players of the game.
     */
    private Set<Player> players = new HashSet<>();

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
    protected long gameStartingDuration = 0_000l;

    /**
     * Duration of round running state of the game.
     */
    protected long roundDuration = 2_000l;

    /**
     * Duration of round ended state of the game.
     */
    protected long roundEndedDuration = 0_000l;

    /**
     * Duration of game ended state of the game.
     */
    protected long gameEndedDuration = 0_000l;

    /**
     * Indicates if the players respawn during the round after dying.
     */
    protected boolean autoRespawn = false;

    /**
     * Indicates if players can hurts other teammate.
     */
    protected boolean friendlyFire = false;

    /**
     * Useful for limit the refresh rate of the game.
     */
    private Timer timer;

    /**
     * Create a default game.
     */
    public Game() {
        this(DEFAULT_REFRESH_RATE);
    }

    /**
     * Create a new game.
     */
    public Game(int refreshRate) {
        timer = new Timer(refreshRate);
    }

    /**
     * Intialize and execute the game.
     */
    public void run() {
        setState(GameState.STARTING);
        round = 1;
        startRound = System.currentTimeMillis();
        running = true;

        while (running) {
            timer.limitRefreshRate();
            
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

        Iterator<Team> teamIterator = teams.iterator();
        while (teamIterator.hasNext()) {
            Team team = teamIterator.next();
            Iterator<Player> playerIterator = team.getPlayers().iterator();
            while (playerIterator.hasNext()) {
                Player player = playerIterator.next();
                player.handleMovements(timer.getRefreshRate());
                player.handleWeapon(teams, team, friendlyFire);
            }
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

	public void onConnect(Player player, Team team) {
        for (Team t : teams) {
            t.removePlayer(player);
        }
        if (teams.contains(team)) {
            team.addPlayer(player);
        }
    }

    public void onDisconnect(Player player) {
        for (Team team : teams) {
            team.removePlayer(player);
        }
    }
    
    public void onDeath(Player player) {
        // TODO
    }

	public Team getTeam(int index) {
        int i = 0;
        for (Team team : teams) {
            if (i == index) {
                return team;
            }
            i++;
        }
        return null;
	}

}
