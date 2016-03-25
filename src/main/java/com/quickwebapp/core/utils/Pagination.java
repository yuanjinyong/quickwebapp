package com.quickwebapp.core.utils;

public class Pagination {
    private Integer page = 0;
    private Integer pageSize = 20;
    private Integer totalNum = 0;
    private String orderBy;

    public Pagination() {

    }

    public Pagination(Integer page, Integer pageSize, String orderBy) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalNum = 0;
        this.orderBy = orderBy;
    }

    public Integer getStartNum() {
        return page == 0 ? 0 : ((page - 1) * pageSize);
    }

    public Integer getTotalPage() {
        return totalNum == 0 ? 0 : (totalNum / pageSize + 1);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
