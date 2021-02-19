package cloud.marchand.hypex.core.models;

import java.util.HashSet;
import java.util.Set;


/**
 * Team of players.
 */
public class Team {

    /**
     * Players in the team.
     */
    private Set<Player> players = new HashSet<>();

    /**
     * Current score of the team.
     * @see cloud.marchand.hypex.core.models.Game#maxRounds
     */
    private int score = 0;

    /**
     * Add a player to the team.
     * @param player new player
     * @return true if success
     */
    public synchronized boolean addPlayer(Player player) {
        return players.add(player);
    }

    /**
     * Remove a player from the team.
     * @param player player removed
     * @return true if success
     */
    public synchronized boolean removePlayer(Player player) {
        return players.remove(player);
    }
    
    /**
     * Give the current score of the team.
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Update the score of the team.
     * @param score new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Give the players in the team.
     * @return player set
     */
    public synchronized Set<Player> getPlayers() {
        return players;
    }

}
