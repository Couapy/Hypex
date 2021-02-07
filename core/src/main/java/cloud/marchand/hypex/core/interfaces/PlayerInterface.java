package cloud.marchand.hypex.core.interfaces;

import java.util.ArrayList;
import java.util.List;

import cloud.marchand.hypex.core.enumerations.PlayerState;
import cloud.marchand.hypex.core.models.geom.OrientablePoint;

/**
 * Represent a player in the game
 */
public abstract class PlayerInterface extends OrientablePoint {

    /**
     * Name of the player
     */
    private String name;

    /**
     * State of the player
     */
    private PlayerState state;

    /**
     * A list of weapons that the player can use
     */
    private List<WeaponInterface> weapons = new ArrayList<>();

    /**
     * Current weapon in hand.
     */
    private WeaponInterface handWeapon;

    /**
     * Distance per second.
     */
    private double velocity;

    /**
     * Look direction.
     */
    private double theta;
    
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
    public PlayerInterface() {
        this.name = "nameless";
    }

    /**
     * Create a player
     * @param name of the player
     */
    public PlayerInterface(String name) {
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
     * @param index index of the weapon
     */
    public void changeWeapon(int index) {
        try {
            handWeapon = weapons.get(index);
            handWeapon.load();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    /**
     * Move the player every tick based on player velocity.
     */
	public void handleMovements(double refreshRate) {
        double[] vector = new double[]{0d, 0d};
        double velocityRated = velocity / refreshRate;
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        
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
     * @see cloud.marchand.hypex.core.enumerations.WeaponState
     */
	public void handleWeapon() {
        if (handWeapon != null) {
            handWeapon.handle();
        }
	}
    
    /**
     * Defines player name.
     * @param name pseudo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates if the player is moving forward.
     * @param move true if the player is moving forward
     */
    public void moveForward(boolean move) {
        moveForward = move;
    }

    /**
     * Indicates if the player is moving backward.
     * @param move true if the player is moving backward
     */
    public void moveBackward(boolean move) {
        moveBackward = move;
    }

    /**
     * Indicates if the player is moving to the left.
     * @param move true if the player is moving to the left
     */
    public void moveLeft(boolean move) {
        moveLeft = move;
    }

    /**
     * Indicates if the player is moving to the right.
     * @param move true if the player is moving to the right
     */
    public void moveRight(boolean move) {
        moveRight = move;
    }

    /**
     * Giv the player name.
     * @return name
     */
    public String getName() {
        return name;
    }

}
