package cloud.marchand.hypex.core.interfaces;

import cloud.marchand.hypex.core.enumerations.WeaponState;

public abstract class WeaponInterface {
    
    private String name;
    private WeaponState state;

    private float dammages;
    private int bulletsLoader;
    private int bulletsSupply;
    private float fireRate;
    private float distanceDammageCorreler;

    public WeaponInterface() {
        this.state = WeaponState.ready;
    }

    public void shoot() {
        this.state = WeaponState.firing;
    }

    public abstract void secondaryShoot();

    public void reload() {
        this.state = WeaponState.reloading;
    }

}
