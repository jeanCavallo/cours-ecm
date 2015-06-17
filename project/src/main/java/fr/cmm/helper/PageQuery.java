package fr.cmm.helper;

public class PageQuery {
    // 0 based page index
    private int index = 0;

    private int size = 20;

    private String tag;

    public int skip() {
        return index * size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
