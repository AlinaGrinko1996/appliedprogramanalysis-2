public class Report {

    private String _count;
    private String _day;
    private String _date;
 
    /**
     * Creates a new <code>User</code> instance.
     */
    public Report() {
    }

    public void setDate(String date) {
        _date = date;
    }
    
    public String getDate() {
        return _date;
    }
    
    public void setDay(String day) {
        _day = day;
    }
    
    @NonVisual
    public String getDay() {
        return _day;
    }
    
    public void setCount(String count) {
        _count = count;
    }
    
    public String getCount() {
        return _count;
    }
}
