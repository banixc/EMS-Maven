package com.banixc.j2ee.ems.framework.message;

import java.io.Serializable;

public class Result implements Serializable {

    public static final int RESULT_DEFAULT = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = 2;

    protected String message;
    protected int type;
    protected String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Result(String message) {
        this.message = message;
        this.type = RESULT_DEFAULT;
    }

    public Result(String message, int type) {
        this.message = message;
        this.type = type;
        this.title = "结果";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[操作结果][" + title + "]: " + message;
    }
}
