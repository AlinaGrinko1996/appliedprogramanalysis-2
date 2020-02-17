package container;

public class MetricsOfClass {
    private String className;
    private int WMC;
    private int NOM;
    private int RFC;

    public MetricsOfClass(String className) {
        this.className = className;
    }

    //todo test comment
    //test comment same line
    public String getClassName() {
        return className;
    }

    public int getWMC() {
        return WMC;
    }

    public void setWMC(int WMC) {
        this.WMC = WMC;
    }

    public int getNOM() {
        return NOM;
    }

    public void setNOM(int NOM) {
        this.NOM = NOM;
    }

    public int getRFC() {
        return RFC;
    }

    //stupid (is not)
    public void setRFC(int RFC) {
        this.RFC = RFC;
    }

    @Override
    public String toString() {
        return String.format("Class %s\n\tNOM: %d\tWMC: %d\tRFC: %d\n", className, NOM, WMC, RFC);
    }
}