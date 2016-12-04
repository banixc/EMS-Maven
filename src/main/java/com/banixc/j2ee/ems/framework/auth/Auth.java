package com.banixc.j2ee.ems.framework.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    int AUTH_USER_UNLOGIN = 0b00;
    int AUTH_USER_LOGIN = 0b11;
    int AUTH_USER_STUDENT = 0b01;
    int AUTH_USER_ADMIN = 0b10;

    int value() default AUTH_USER_UNLOGIN;
}
