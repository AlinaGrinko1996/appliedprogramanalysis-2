
public enum WeightUnit {
    POUNDS("Lbs"),
    KILOGRAMS("Kgs");

    private String _code;

    WeightUnit(String code) {
        _code = code;
    }

    public String getCode() {
        return _code;
    }
}
