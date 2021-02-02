package cloud.marchand.hypex.core.models;

/**
 * Timer to limit a refresh rate.
 */
public class Timer {

    /**
     * Refresh rate of the game per second.
     */
    private int refreshRate;

    /**
     * Last refreshRateMesured.
     */
    private int refreshRateMesured;

    /**
     * Last time we mesured the refresh rate.
     */
    private long lastRefreshMesure;

    /**
     * Number of refresh iterations during the last second. 
     */
    private int currentRefreshRate;

    /**
     * Duration for one refresh.
     */
    private long refreshDuration;

    /**
     * Indicates if the timer is disabled.
     */
    private boolean disabled;

    /**
     * Defines the refresh rate of the game.
     * Set {@link #refreshRate} to 0 or less to disable the limitation.
     * @param refreshRate refresh rate prefered
     */
    public Timer(int refreshRate) {
        if (refreshRate <= 0) {
            disabled = true;
        }
        else {
            disabled = false;
            this.refreshRate = refreshRate;
            this.refreshDuration = 1000 / refreshRate;
        }
    }

    /**
     * Limit the refresh rate.
     */
    public void limitRefreshRate() {
        if (disabled) {
            return;
        }
        if (System.currentTimeMillis() >= lastRefreshMesure + 1_000l) {
            refreshRateMesured = currentRefreshRate;
            currentRefreshRate = 0;
            lastRefreshMesure = System.currentTimeMillis();
        }
        else {
            currentRefreshRate++;
        }
        try {
            Thread.sleep(refreshDuration);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Give the current refresh rate.
     * @return current refresh rate
     */
    public int getCurrentRefreshRate() {
        return currentRefreshRate;
    }
    
}
