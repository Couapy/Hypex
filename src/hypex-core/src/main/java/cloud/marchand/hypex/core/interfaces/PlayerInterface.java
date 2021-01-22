package cloud.marchand.hypex.core.interfaces;

import java.util.ArrayList;
import java.util.List;

import cloud.marchand.hypex.core.enumerations.PlayerState;
import cloud.marchand.hypex.core.models.Position;
import cloud.marchand.hypex.core.models.Vision;

/**
 * Represent a player in the game
 */
public abstract class PlayerInterface {

    /**
     * Name of the player
     */
    private String name;

    /**
     * Position of the player
     */
    private Position position;

    /**
     * Vision of the player
     */
    private Vision vision;

    /**
     * State of the player
     */
    private PlayerState state;

    /**
     * A list of weapons that the player can use
     */
    private List<WeaponInterface> weapons = new ArrayList<>();

    /**
     * Create a player without name, at the origin
     */
    public PlayerInterface() {
        this.name = "nameless";
        this.position = new Position();
        this.vision = new Vision();
    }

    /**
     * Create a player
     * @param name of the player
     * @param position initial position on the map
     * @param vision initial vision of the player
     */
    public PlayerInterface(String name, Position position, Vision vision) {
        this.name = name;
        this.position = position;
        this.vision = vision;
    }

    /**
     * Shoot the weapon in hand
     */
    public void shoot() {
        // TODO: shoot()
    }

    /**
     * Make reload the weapon in hand
     */
    public void reload() {
        // TODO: reload()
    }

    public void changeWeapon() {

    }
    
}
