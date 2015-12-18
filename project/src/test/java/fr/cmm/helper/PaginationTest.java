package fr.cmm.helper;

import org.junit.Test;

import static java.util.Arrays.asList;
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

    @Test
    public void getPages() {
        Pagination pagination = new Pagination();
        pagination.setCount(200);
        pagination.setPageSize(10); // Il faut 20 pages
        pagination.setPageIndex(8);

        assertEquals(asList(4,5,6,7,8,9,10,11,12,13), pagination.getPages());
    }

    @Test
    public void getPagesInf5() {
        Pagination pagination = new Pagination();
        pagination.setCount(200);
        pagination.setPageSize(10); // Il faut 20 pages
        pagination.setPageIndex(4);

        assertEquals(asList(1,2,3,4,5,6,7,8,9,10), pagination.getPages());
    }

    @Test
    public void getPagesUpperThanLimit() {
        Pagination pagination = new Pagination();
        pagination.setCount(200);
        pagination.setPageSize(10); // Il faut 20 pages
        pagination.setPageIndex(18);

        assertEquals(asList(11,12,13,14,15,16,17,18,19,20), pagination.getPages());
    }

    @Test
    public void getPagesLessPagesThan10() {
        Pagination pagination = new Pagination();
        pagination.setCount(80);
        pagination.setPageSize(10); // Il faut 20 pages
        pagination.setPageIndex(4);

        assertEquals(asList(1,2,3,4,5,6,7,8), pagination.getPages());
    }
}