import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import java.util.Iterator;

public class Paging {
    private static final int MAX_PAGES = 10;
    private static final int HALF_PAGES = MAX_PAGES / 2;

    @Parameter(required = true)
    private int maxEntries;

    @Parameter(required = true)
    private int totalEntries;

    @Parameter(required = true, defaultPrefix = BindingConstants.PROP)
    @Property
    private String pageName;

    @Parameter(required = false, defaultPrefix = BindingConstants.PROP)
    @Property
    private boolean reset;

    @Persist
    private int startIndex;

    @Property
    private DynamicArray pages;

    @Property
    private int page;

    public int getStartIndex() {
        if (reset) {
            startIndex = 0;
            reset = false;
        }
        return startIndex;
    }

    public void setStartIndex(int argStartIndex) {
        startIndex = argStartIndex;
    }

    public boolean getNeedsPrevious() {
        return startIndex != 0;
    }

    public boolean getNeedsNext() {
        return (startIndex + maxEntries) < totalEntries;
    }

    public boolean getNeedsPaging() {
        return getEndPage() > 1;
    }

    public int getStartPage() {
        final int currentPage = getCurrentPage();
        if (HALF_PAGES > currentPage) {
            return 1;
        } else {
            return currentPage - HALF_PAGES + 1;
        }
    }

    public int getEndPage() {
        final int startPage = getStartPage();
        int endPage = startPage + MAX_PAGES - 1;

        int tempPages = (totalEntries / maxEntries);

        if (totalEntries % maxEntries != 0) {
            tempPages += 1;
        }

        if (endPage > tempPages) {
            final int entries = tempPages * maxEntries;
            endPage = (entries >= totalEntries) ? tempPages : tempPages + 1;
        }
        return endPage;
    }

    public int getCurrentPage() {
        return (startIndex / maxEntries) + 1;
    }

    public boolean isSelected() {
        int currentPage = getCurrentPage();
        return currentPage == page;
    }

    void setupRender() {
        if (reset) {
            startIndex = 0;
            reset = false;
        }

        int startPage = getStartPage();
        int endPage = getEndPage();
        pages = new DynamicArray(startPage, endPage);
    }

    Object onActionFromPrevious() {
        if (startIndex >= maxEntries) {
            startIndex -= maxEntries;
        }
        return pageName;
    }

    Object onActionFromNext() {
        if (getNeedsNext()) {
            startIndex += maxEntries;
        }
        return pageName;
    }

    Object onActionFromPage(int argPage) {
        startIndex = (argPage - 1) * maxEntries;
        if (startIndex > totalEntries) {
            int tempPages = (totalEntries / maxEntries);
            startIndex = tempPages * maxEntries;
        }
        return pageName;
    }

    public static class DynamicArray implements Iterable<String> {
        private int _start;
        private int _end;

        public DynamicArray(int start, int end) {
            _start = start;
            _end = end;
        }

        @Override
        public Iterator<String> iterator() {
            return new PageIterator(_start, _end);
        }
    }

    public static class PageIterator implements Iterator<String> {
        private int _end;
        private int _position;

        public PageIterator(int start, int end) {
            _end = end;
            _position = start;
        }

        @Override
        public boolean hasNext() {
            return _position <= _end;
        }

        @Override
        public String next() {
            return Integer.toString(_position++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = getStartPage(); i <= getEndPage(); i++) {
            final boolean isCurrentPage = i == getCurrentPage();
            if (isCurrentPage) {
                builder.append("(");
            }
            builder.append(i);
            if (isCurrentPage) {
                builder.append(")");
            }
            if (i < getEndPage()) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        final Paging paging = new Paging();
        for (int i = 0; i < 100; i++) {
            System.out.println(paging);
            paging.setStartIndex((i + 1) * 60);
        }
    }
}