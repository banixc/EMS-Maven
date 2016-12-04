package com.banixc.j2ee.ems.framework.util;

import java.util.Collection;

public class Pagination {

    private Long total;
    private Integer page;
    private Collection list;

    public Pagination(Long total, Integer page, Collection list) {
        this.total = total;
        this.page = page;
        this.list = list;
    }

    public Pagination() {

    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Collection getList() {
        return list;
    }

    public void setList(Collection list) {
        this.list = list;
    }
}
