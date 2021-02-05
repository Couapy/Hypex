package cloud.marchand.hypex.core.interfaces;

import java.util.ArrayList;
import java.util.List;

import cloud.marchand.hypex.core.enumerations.PlayerState;

/**
 * Represent a player in the game
 */
public abstract class PlayerInterface {

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
