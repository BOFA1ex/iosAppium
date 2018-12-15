package com.bofa.appium.container;

import com.bofa.appium.EntryApplication;
import com.bofa.appium.annotation.Application;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.container
 * @date 2018/12/15
 */
public class ApplicaitonListener {

    public static void initContainer(){
        ApplicationContextProcessor.inject(EntryApplication.class.getAnnotation(Application.class).value());
        ApplicationContextProcessor.refreshContext();
    }

    public static void refreshContext(){
        ApplicationContextProcessor.refreshContext();
    }
}
