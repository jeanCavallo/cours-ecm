package fr.cmm.helper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jean on 18/12/15.
 */
public class PaginationTest {
    @Test
    public void getPageCount() {
        Pagination pagination = new Pagination();
        pagination.setCount(50);
        pagination.setPageSize(20);

        assertEquals(3, pagination.getPageCount());
    }

    @Test
    public void getPageCountMultiple() {
        Pagination pagination = new Pagination();
        pagination.setCount(40);
        pagination.setPageSize(20);

        assertEquals(2, pagination.getPageCount());
    }
}