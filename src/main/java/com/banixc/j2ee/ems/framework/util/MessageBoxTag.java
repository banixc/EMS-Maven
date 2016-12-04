package com.banixc.j2ee.ems.framework.util;

import com.banixc.j2ee.ems.framework.message.Result;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import static com.banixc.j2ee.ems.base.BaseController.SESSION_MESSAGE;
import static com.banixc.j2ee.ems.framework.message.Result.*;

public class MessageBoxTag extends SimpleTagSupport {

    private static final String ALERT_TYPE_DEFAULT = "default";
    private static final String ALERT_TYPE_DANGER = "danger";
    private static final String ALERT_TYPE_SUCCESS = "success";

    @Override
    public void doTag() throws JspException, IOException {
        HttpSession session = ((PageContext)this.getJspContext()).getSession();
        Result result = (Result) session.getAttribute(SESSION_MESSAGE);

        if(null == result) return;

        session.removeAttribute(SESSION_MESSAGE);

        String alertType;

        switch (result.getType()) {
            case RESULT_ERROR:
                alertType = ALERT_TYPE_DANGER;
                break;
            case RESULT_SUCCESS:
                alertType = ALERT_TYPE_SUCCESS;
                break;
            case RESULT_DEFAULT:
            default:
                alertType = ALERT_TYPE_DEFAULT;
                break;
        }

        String outPrint = "<div class=\"message-box\"><div class=\"alert alert-" + alertType +
                " alert-dismissible fade in\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">关闭</span></button>" +
                result.getMessage() +
                "</div></div>";
        JspWriter out = getJspContext().getOut();
        out.print(outPrint);
    }
}
