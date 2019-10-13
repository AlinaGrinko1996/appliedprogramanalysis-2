
public enum LengthUnit {
    INCHES("in"),
    CM("cm");

    private String _code;

    /**
     * Creates a new <code>LengthUnit</code> instance.
     */
    LengthUnit(String code) {
        _code = code;
    }

    public String getCode() {
        return _code;
    }
}
