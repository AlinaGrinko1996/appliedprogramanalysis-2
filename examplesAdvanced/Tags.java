
public class Tags {
    private int id;
    private String _tag;
    private int _count;

    /**
     * Creates a new <code>Tags</code> instance.
     */
    public Tags() {
    }

    public String getTag() {
        return _tag;
    }

    public void setTag(String tag) {
        _tag = tag;
    }

    public int getCount() {
        return _count;
    }

    public void setCount(int count) {
        _count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Tags tags = (Tags) o;
        return (_tag != null) ? _tag.equals(tags._tag) : (tags._tag == null);
    }

    @Override
    public int hashCode() {
        return (_tag != null) ? _tag.hashCode() : 0;
    }
}
