package fr.cmm.helper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jean on 18/12/15.
 */
public class PageIndexTest {
    @Test
    public void fixPageIndex() {
        assertEquals(10, PageIndex.fixPageIndex(10));
        assertEquals(1, PageIndex.fixPageIndex(1));
        assertEquals(1, PageIndex.fixPageIndex(0));
        assertEquals(1, PageIndex.fixPageIndex(-10));
    }
}