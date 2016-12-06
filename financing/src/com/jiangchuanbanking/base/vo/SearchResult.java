
package com.jiangchuanbanking.base.vo;

import java.util.List;

/**
 * Search result
 * 
 */
public class SearchResult<T> {
    private long totalRecords;
    private List<T> result;

    public SearchResult(long totalRecords, List<T> result) {
        super();
        this.totalRecords = totalRecords;
        this.result = result;
    }

    /**
     * @return the totalRecords
     */
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * @param totalRecords
     *            the totalRecords to set
     */
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * @return the result
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(List<T> result) {
        this.result = result;
    }

}
