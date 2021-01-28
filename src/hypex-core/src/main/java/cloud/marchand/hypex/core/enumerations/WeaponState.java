package cloud.marchand.hypex.core.enumerations;

/**
 * Indicates the weapon status.
 */
public enum WeaponState {
    /**
     * The weapon is loading.
     * Happens when the player select the weapon.
     */
    LOADING,
    /**
     * The weapon is ready to fire ({@link #FIRING}), or to reload ({@link #RELOADING}).
     */
    READY,
    /**
     * The weapon is reloading.
     */
    FIRING,
    /**
     * The weapon is reloading.
     * It will come back to {@link #READY}.
     */
    RELOADING
}
