
public class Limits {
    private int _daily;
    private int _weekly;
    private int _rate;
    private int _time;

    /**
     * Creates a new <code>Limits</code> instance.
     */
    public Limits() {
    }

    public Limits(SpendingThreshold thresholds) {
        final Threshold daily = thresholds.getDaily();
        final Threshold weekly = thresholds.getWeekly();
        final Threshold velocity = thresholds.getVelocity();
        _daily = daily.getThreshold();
        _weekly = weekly.getThreshold();
        _rate = velocity.getThreshold();
        _time = velocity.getRate();
    }

    /**
     * Gets the value of daily
     *
     * @return the value of daily
     */
    public final int getDaily() {
        return this._daily;
    }

    /**
     * Sets the value of daily
     *
     * @param argDaily Value to assign to this.daily
     */
    public final void setDaily(final int argDaily) {
        this._daily = argDaily;
    }

    /**
     * Gets the value of weekly
     *
     * @return the value of weekly
     */
    public final int getWeekly() {
        return this._weekly;
    }

    /**
     * Sets the value of weekly
     *
     * @param argWeekly Value to assign to this.weekly
     */
    public final void setWeekly(final int argWeekly) {
        this._weekly = argWeekly;
    }

    /**
     * Gets the value of rate
     *
     * @return the value of rate
     */
    public final int getRate() {
        return this._rate;
    }

    /**
     * Sets the value of rate
     *
     * @param argRate Value to assign to this.rate
     */
    public final void setRate(final int argRate) {
        this._rate = argRate;
    }

    /**
     * Gets the value of time
     *
     * @return the value of time
     */
    public final int getTime() {
        return this._time;
    }

    /**
     * Sets the value of time
     *
     * @param argTime Value to assign to this.time
     */
    public final void setTime(final int argTime) {
        this._time = argTime;
    }

}
