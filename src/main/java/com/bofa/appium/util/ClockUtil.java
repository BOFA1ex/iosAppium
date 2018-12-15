package com.bofa.appium.util;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.util
 * @date 2018/12/13
 */
public class ClockUtil {

    public static String currentDate(Pattern pattern){
        return DateFormatUtils.format(System.currentTimeMillis(), pattern.getPattern());
    }
}
