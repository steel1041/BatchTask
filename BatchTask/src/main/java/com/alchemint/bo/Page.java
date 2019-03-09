package com.alchemint.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2018/12/14.
 */
public class Page<T> implements Serializable {
    /**
     * @Fields serialVersionUID: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -8896679359677548079L;
    public static final int DEFAULT_PAGE_MAXSIZE = 1000;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_CURRENT_PAGE = 1;

    private int startIndex;
    /**
     * 每页显示个数
     */
    private int pageSize;
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 结果列表
     */
    private List<T> rows;

    public int getStartIndex() {
        return (getCurrentPage()-1)*getPageSize();
    }

    public int getPageSize() {
        if (pageSize < 1) {
            this.setPageSize(DEFAULT_PAGE_SIZE);
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > DEFAULT_PAGE_MAXSIZE) {
            setPageSize(DEFAULT_PAGE_SIZE);
        }else {
            this.pageSize = pageSize;
        }
    }

    public int getCurrentPage() {
        if (currentPage < 1) {
            setCurrentPage(DEFAULT_CURRENT_PAGE);
        }
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 设置结果 及总页数
     *
     * @param rows
     */
    public void build(List<T> rows) {
        this.setRows(rows);
        int count = this.getTotalCount();
        int divisor = count / this.getPageSize();
        int remainder = count % this.getPageSize();
        this.setTotalPage(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
