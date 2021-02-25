package cloud.marchand.hypex.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cloud.marchand.hypex.core.enumerations.PlayerState;
import cloud.marchand.hypex.core.models.geom.OrientablePoint;

/**
 * Represent a player in the game
 */
public class Player extends OrientablePoint {

    /**
     * Name of the player
     */
    private String name;

    /*
     * State of the player
     */
    private PlayerState state;

    /**
     * A list of weapons that the player can use
     */
    private List<Weapon> weapons = new ArrayList<>();

    /**
     * Current weapon in hand.
     */
    private Weapon handWeapon;

    /**
     * Distance per second.
     */
    private double velocity;

    /**
     * Indicates if the player is moving forward.
     */
    private boolean moveForward = false;

    /**
     * Indicates if the player is moving backward.
     */
    private boolean moveBackward = false;

    /**
     * Indicates if the player is moving to the left.
     */
    private boolean moveLeft = false;

    /**
     * Indicates if the player is moving to the right.
     */
    private boolean moveRight = false;

    /**
     * Create a player without name, at the origin
     */
    public Player() {
        this.name = "nameless";
    }

    /**
     * Create a player
     * 
     * @param name of the player
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Make the weapon in hand shoot.
     */
    public void shoot() {
        if (handWeapon != null) {
            handWeapon.shoot();
        }
    }

    /**
     * Make not the weapon in hand shoot.
     */
    public void unShoot() {
        if (handWeapon != null) {
            handWeapon.unShoot();
        }
    }

    /**
     * Make the weapon in hand shoot.
     */
    public void shootSecondary() {
        if (handWeapon != null) {
            handWeapon.shootSecondary();
        }
    }

    /**
     * Make not the weapon in hand shoot.
     */
    public void unShootSecondary() {
        if (handWeapon != null) {
            handWeapon.unShootSecondary();
        }
    }

    /**
     * Make reload the weapon in hand
     */
    public void reload() {
        if (handWeapon != null) {
            handWeapon.reload();
        }
    }

    /**
     * Change the weapon in hand.
     * 
     * @param index index of the weapon
     * @return true if the index is correct
     */
    public boolean changeWeapon(int index) {
        if (0 <= index && index < weapons.size()) {
            Weapon weapon = weapons.get(index);
            handWeapon = weapon;
            handWeapon.load();
            return true;
        }
        return false;
    }

    /**
     * Move the player every tick based on player velocity.
     * 
     * @param refreshRate refresh rate per second prefered
     */
    public void handleMovements(double refreshRate) {
        double[] vector = new double[] { 0d, 0d };
        double velocityRated = velocity / refreshRate;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        if (moveForward) {
            vector[0] += cos;
            vector[1] += sin;
        }
        if (moveBackward) {
            vector[0] -= cos;
            vector[1] -= sin;
        }
        if (moveLeft) {
            vector[0] -= sin;
            vector[1] += cos;
        }
        if (moveRight) {
            vector[0] += sin;
            vector[1] -= cos;
        }

        x += vector[0] * velocityRated;
        y += vector[1] * velocityRated;
    }

    /**
     * Handle weapon action
     * 
     * @param teams        all teams of the game
     * @param myTeam       the team of the player
     * @param friendlyFire true if the player can hurts mates
     * @see cloud.marchand.hypex.core.enumerations.WeaponState
     */
    public void handleWeapon(Set<Team> teams, Team myTeam, boolean friendlyFire) {
        if (handWeapon != null) {
            handWeapon.handle(teams, myTeam, friendlyFire);
        }
    }

    /**
     * Defines player name.
     * 
     * @param name pseudo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates if the player is moving forward.
     * 
     * @param move true if the player is moving forward
     */
    public void moveForward(boolean move) {
        moveForward = move;
    }

    /**
     * Indicates if the player is moving backward.
     * 
     * @param move true if the player is moving backward
     */
    public void moveBackward(boolean move) {
        moveBackward = move;
    }

    /**
     * Indicates if the player is moving to the left.
     * 
     * @param move true if the player is moving to the left
     */
    public void moveLeft(boolean move) {
        moveLeft = move;
    }

    /**
     * Indicates if the player is moving to the right.
     * 
     * @param move true if the player is moving to the right
     */
    public void moveRight(boolean move) {
        moveRight = move;
    }

    /**
     * Give the player name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

}
