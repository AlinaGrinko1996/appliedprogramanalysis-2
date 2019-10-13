public class Threshold {
    public enum Type {DAILY, WEEKLY, VELOCITY}
    private int _threshold;
    private int _rate;          // in seconds
    private Type _thresholdType;

    /**
     * Creates a new <code>Threshold</code> instance.
     */
    public Threshold() {
    }

    /**
     * Gets the value of threshold
     *
     * @return the value of threshold
     */
    public final int getThreshold() {
        return this._threshold;
    }

    /**
     * Sets the value of threshold
     *
     * @param argThreshold Value to assign to this.threshold
     */
    public final void setThreshold(final int argThreshold) {
        this._threshold = argThreshold;
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
     * Gets the value of thresholdType
     *
     * @return the value of thresholdType
     */
    public final Type getThresholdType() {
        return this._thresholdType;
    }

    /**
     * Sets the value of thresholdType
     *
     * @param argThresholdType Value to assign to this.thresholdType
     */
    public final void setThresholdType(final Type argThresholdType) {
        this._thresholdType = argThresholdType;
    }
}
