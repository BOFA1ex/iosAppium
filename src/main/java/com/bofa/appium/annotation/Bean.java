package com.bofa.appium.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.annotation
 * @date 2018/12/13
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

}
