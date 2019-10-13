import java.util.ArrayList;
import java.util.List;

public class ResultList <T> {
    private List<T> _list;
    private int foundRows;

    public ResultList() {
        _list = new ArrayList<>();
    }
    public List<T> getList() {
        return _list;
    }

    public void setList(List<T> list) {
        _list = list;
    }

    public int getFoundRows() {
        return foundRows;
    }

    public void setFoundRows(int foundRows) {
        this.foundRows = foundRows;
    }
}