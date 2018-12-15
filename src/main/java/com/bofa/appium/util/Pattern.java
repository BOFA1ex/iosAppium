package com.bofa.appium.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.util
 * @date 2018/12/13
 */
public enum Pattern {

    /**
     *
     */
    YEAR_PATTERN("[yyyy-MM-dd HH:mm:ss]"),
    MONTH_PATTERN("[mm-dd HH:mm:ss]"),
    DAY_PATTERN("[dd HH:mm:ss]"),
    HOUR_PATTERN("[HH:mm:ss]"),
    HOUR_PATTERN_SSS("[HH:mm:ss-SSS]");

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    Pattern(String pattern) {
        this.pattern = pattern;
    }


}



