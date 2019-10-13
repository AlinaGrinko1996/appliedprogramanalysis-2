

public enum Month {
    JANUARY("01", "1-Jan"),
    FEBRUARY("02", "2-Feb"),
    MARCH("03", "3-Mar"),
    APRIL("04", "4-Apr"),
    MAY("05", "5-May"),
    JUNE("06", "6-Jun"),
    JULY("07", "7-Jul"),
    AUGUST("08", "8-Aug"),
    SEPTEMBER("09", "9-Sep"),
    OCTOBER("10", "10-Oct"),
    NOVEMBER("11", "11-Nov"),
    DECEMBER("12", "12-Dec");

    private String _number;
    private String _display;

    Month(String number, String display) {
        _number = number;
        _display = display;
    }

    public String getNumber() {
        return _number;
    }

    public String getDisplay() {
        return _display;
    }
}
