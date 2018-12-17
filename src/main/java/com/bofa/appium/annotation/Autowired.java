package com.bofa.appium.annotation;

import java.lang.annotation.*;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.annotation
 * @date 2018/12/13
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean value() default true;
}
