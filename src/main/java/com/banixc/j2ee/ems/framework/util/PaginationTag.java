package com.banixc.j2ee.ems.framework.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import static com.banixc.j2ee.ems.dao.BaseDAOImpl.DEFAULT_PAGE_ROWS;

public class PaginationTag extends SimpleTagSupport {

    private Pagination pagination;


    @Override
    public void doTag() throws JspException, IOException {
        Long total = pagination.getTotal();
        if (null == total || total == 0 ) return;
        int endPage = (int) ((total-1)/DEFAULT_PAGE_ROWS) + 1;
        if(endPage == 1) return;
        int page = pagination.getPage();

        int start = page - 5 > 0 ? page - 5 : 1;
        int end = page + 5 < endPage ? page + 5 : endPage;

        JspWriter writer = getJspContext().getOut();
        String outString = "<nav><ul class=\"pagination center\">";

        outString += start !=1 ?"<li><a href=\"1\">&laquo;</a></li>":"";

        for (int i = start ; i < page ; i ++)
            outString += "<li><a href=\"" + i + "\">" + i + "</a></li>";

        outString += "<li class=\"active\"><a href=\"" + page + "\">" + page + "</a></li>";

        for (int i = page + 1 ; i <= end ; i ++)
            outString += "<li><a href=\"" + i + "\">" + i + "</a></li>";

        outString += end !=endPage ?"<li><a href=\"" + endPage + "\">&raquo;</a></li>":"";

        writer.print(outString);
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
