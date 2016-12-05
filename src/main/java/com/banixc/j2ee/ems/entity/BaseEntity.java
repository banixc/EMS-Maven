package com.banixc.j2ee.ems.entity;

import java.math.BigDecimal;

public abstract class BaseEntity {
    protected boolean empty(String s) {
        return s == null || s.isEmpty();
    }
    protected boolean empty(int t) {
        return t <= 0;
    }
    protected boolean empty(BigDecimal b){
        return b.compareTo(new BigDecimal("0")) != 1;
    }

    public abstract String valid();
}
