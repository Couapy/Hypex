package cloud.marchand.hypex.core.interfaces;

import cloud.marchand.hypex.core.enumerations.WeaponState;

public abstract class WeaponInterface {
    
    private String name;
    private WeaponState state;

    /**
     * Bullet damage.
     */
    private float damages;

    /**
     * Magazine capacity.
     */
    private int magazineCapacity;

    /**
     * Bullets inside the loader.
     */
    private int bulletsMagazine;

    /**
     * Extra bullets.
     */
    private int bulletsSupply;

    /**
     * Number of bullets per second.
     */
    private float fireRate;

    /**
     * The time that the weapon make to load.
     */
    private double timeLoad;

    /**
     * The time that the weapon make to reload.
     */
    private double timeReload;

    /**
     * Last moment the weapon changed state.
     */
    private double lastTimeChangedState;

    /**
     * Indicates if the weapon is firing.
     */
    private boolean primaryShootActived;

    /**
     * Indicates if the weapon is using it special fire.
     */
    private boolean secondaryShootActived;

    /**
     * Create a weapon.
     */
    public WeaponInterface() {
        this.state = WeaponState.LOADING;
    }

    public abstract void secondaryShoot();

    /**
     * Set the weapon loading.
     * Happens when the player select the weapon.
     */
    public void load() {
        setState(WeaponState.LOADING);
	}

    /**
     * Make reloading the weapon.
     */
    public void reload() {
        setState(WeaponState.RELOADING);
    }

    /**
     * Make shoot the weapon.
     */
    public void shoot() {
        primaryShootActived = true;
    }
    
    /**
     * Make stop firing the weapon.
     */
    public void unShoot() {
        primaryShootActived = false;
    }

    /**
     * Make shoot the weapon.
     */
    public void shootSecondary() {
        secondaryShootActived = true;
    }
    
    /**
     * Make stop firing the weapon.
     */
    public void unShootSecondary() {
        secondaryShootActived = false;
    }

    /**
     * Handle the weapon's action.
     */
    public void handle() {
        if (state == WeaponState.LOADING) {
            if (lastTimeChangedState + timeLoad <= System.currentTimeMillis()) {
                setState(WeaponState.READY);
            }
        }
        else if (state == WeaponState.READY) {
            if (primaryShootActived || secondaryShootActived) {
                setState(WeaponState.FIRING);
            }
        }
        else if (state == WeaponState.FIRING) {
            if (bulletsMagazine == 0) {
                return;
            }
            if (lastTimeChangedState + (1_000l / fireRate) >= System.currentTimeMillis()) {
                setState(WeaponState.FIRING);
                bulletsMagazine--;
                // TODO: give damage to others players
            }
        }
        else if (state == WeaponState.RELOADING) {
            if (lastTimeChangedState + timeReload <= System.currentTimeMillis()) {
                setState(WeaponState.READY);
                if (bulletsSupply < magazineCapacity) {
                    bulletsMagazine = bulletsSupply;
                    bulletsSupply = 0;
                }
                else {
                    bulletsMagazine = magazineCapacity;
                    bulletsSupply -= magazineCapacity;
                }
            }
        }
    }

    /**
     * Update the state of the weapon.
     * @param weaponState state of the {@link WeaponState}
     */
    private void setState(WeaponState weaponState) {
        state = weaponState;
        lastTimeChangedState = System.currentTimeMillis();
    }

}
