package fr.cmm.helper;

/**
 * Created by jean on 18/12/15.
 */
public class PageIndex {
    public static int fixPageIndex(int index) {
        return Math.max(index,1);
    }
}
