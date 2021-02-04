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
     * Current weapon in hand.
     */
    private WeaponInterface handWeapon;

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
        if (handWeapon != null) {
            handWeapon.shoot();
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

    public void changeWeapon(int index) {
        try {
            handWeapon = weapons.get(index);
            handWeapon.load();
        } catch (IndexOutOfBoundsException e) {
        }
    }

	public void handleMovements() {
        // TODO
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
    
}
