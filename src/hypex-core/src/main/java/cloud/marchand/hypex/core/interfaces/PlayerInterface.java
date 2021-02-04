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
     * Move the player.
     */
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
    
    /**
     * Defines player name.
     * @param name pseudo
     */
    public void setName(String name) {
        this.name = name;
    }

}
