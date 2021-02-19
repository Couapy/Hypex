package cloud.marchand.hypex.core.models;

import java.util.Iterator;
import java.util.Set;

import cloud.marchand.hypex.core.enumerations.WeaponState;

public class Weapon {

    /**
     * The name of the weapon.
     */
    private String name;

    /**
     * The weapon state.
     * 
     * @see cloud.marchand.hypex.core.enumerations.WeaponState
     */
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
    public Weapon() {
        this.state = WeaponState.LOADING;
    }

    /**
     * Set the weapon loading. Happens when the player select the weapon.
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
    public void handle(Set<Team> teams, Team myTeam, boolean friendlyFire) {
        if (state == WeaponState.LOADING) {
            handleLoading();
        } else if (state == WeaponState.READY) {
            handleReady();
        } else if (state == WeaponState.FIRING) {
            handleFiring(teams, myTeam, friendlyFire);
        } else if (state == WeaponState.RELOADING) {
            handleReloading();
        }
    }

    /**
     * Handle loading state.
     */
    public void handleLoading() {
        if (lastTimeChangedState + timeLoad <= System.currentTimeMillis()) {
            setState(WeaponState.READY);
        }
    }

    /**
     * Handle ready state.
     */
    public void handleReady() {
        if (primaryShootActived || secondaryShootActived) {
            setState(WeaponState.FIRING);
        }
    }

    /**
     * Handle firing state.
     * 
     * @param teams        all teams of the game
     * @param myTeam       the team of the player
     * @param friendlyFire true if the player can hurts mates
     */
    public void handleFiring(Set<Team> teams, Team myTeam, boolean friendlyFire) {
        if (bulletsMagazine == 0) {
            return;
        }
        if (!primaryShootActived && !secondaryShootActived) {
            setState(WeaponState.READY);
            return;
        }
        if (lastTimeChangedState + (1_000l / fireRate) >= System.currentTimeMillis()) {
            setState(WeaponState.FIRING);
            bulletsMagazine--;

            Iterator<Team> teamIterator = teams.iterator();
            while (teamIterator.hasNext()) {
                Team team = teamIterator.next();
                if (!friendlyFire && team == myTeam) {
                    continue;
                }
                Iterator<Player> playerIterator = team.getPlayers().iterator();
                // TODO: give damage to others players
            }
        }
    }

    /**
     * Handle the reloading state.
     */
    public void handleReloading() {
        if (lastTimeChangedState + timeReload <= System.currentTimeMillis()) {
            setState(WeaponState.READY);
            if (bulletsSupply < magazineCapacity) {
                bulletsMagazine = bulletsSupply;
                bulletsSupply = 0;
            } else {
                bulletsMagazine = magazineCapacity;
                bulletsSupply -= magazineCapacity;
            }
        }
    }

    /**
     * Update the state of the weapon.
     * 
     * @param weaponState state of the {@link WeaponState}
     */
    private void setState(WeaponState weaponState) {
        state = weaponState;
        lastTimeChangedState = System.currentTimeMillis();
    }

}
