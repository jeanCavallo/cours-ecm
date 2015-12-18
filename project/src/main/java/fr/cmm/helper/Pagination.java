package fr.cmm.helper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floorDiv;
import static java.lang.Math.max;
import static java.util.Arrays.asList;

public class Pagination {
    // 1 based page index
    private int pageIndex;

    private int pageSize;

    private long count;

    public static final int PAGINATION_SIZE = 10;

    public int getPreviousPageIndex() {
        return isFirstPage() ? pageIndex : pageIndex - 1;
    }

    public int getNextPageIndex() {
        return isLastPage() ? pageIndex : pageIndex + 1;
    }

    public boolean isFirstPage() {
        return pageIndex == 1;
    }

    public boolean isLastPage() {
        return pageIndex * pageSize >= count;
    }

    public int getPageCount() {
        int resultPageCount;
        if(count%pageSize == 0) {
            resultPageCount = (int) Math.floor(count / pageSize);
        }
        else{
                resultPageCount = (int) Math.floor(count / pageSize)+1;
            }
        return resultPageCount;
    }

    public List<Integer> getPages() {
        int whichPage = this.pageIndex;
        int lowerBound = floorDiv(PAGINATION_SIZE-1,2);
        int upperBound = floorDiv(PAGINATION_SIZE,2);
        List<Integer> saveList = new ArrayList<>();
        int lowerBoundFor;
        int upperBoundFor;

        if (((whichPage-lowerBound)<1) && ((whichPage+upperBound)<=getPageCount())){
            lowerBoundFor = 1;
            upperBoundFor = PAGINATION_SIZE;
        }
        else if (((whichPage-lowerBound)>=1) && ((whichPage+upperBound)>getPageCount())){
            lowerBoundFor = getPageCount()-PAGINATION_SIZE+1;
            upperBoundFor = getPageCount();
        }
        else if ((upperBound+lowerBound+1)>getPageCount()){
            lowerBoundFor = 1;
            upperBoundFor = getPageCount();
        }
        else{
            lowerBoundFor = whichPage-lowerBound;
            upperBoundFor = whichPage+upperBound;
        }

        for (int i = lowerBoundFor; i <= upperBoundFor; i++){
            saveList.add(i);
        }
        return saveList;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
