package com.bofa.appium.container;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.excute.step.WaitAndTapEc;
import com.bofa.appium.util.ClockUtil;
import com.bofa.appium.util.Pattern;
import com.bofa.appium.util.ScanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.container
 * @date 2018/12/15
 */
public class ApplicationContextProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextProcessor.class);

    public static void refreshContext() {
        ApplicationContext context = ApplicationContext.newInstance();
        context.getBeans().forEach(
                (Object o) -> {
                    log.info("scan object name : " + o.getClass().getName());
                    for (Field field : o.getClass().getDeclaredFields()) {
                        for (Annotation a : field.getAnnotations()) {
                            if (a instanceof Autowired) {
                                log.info("scan autowired field object name : " + field.getName());
                                try {
                                    if(!field.isAccessible()){
                                        field.setAccessible(true);
                                    }
                                    field.set(o, context.getBean(field.getGenericType().getTypeName()));
                                } catch (IllegalAccessException e) {
                                    log.error(e.getLocalizedMessage());
                                }
                            }
                        }
                    }
                }
        );
    }

    public static void inject(String packageName) {
        ScanUtils.scan(packageName).forEach(
                (String s) -> {
                    try {
                        Class claz = Class.forName(s);
                        for (Annotation a : claz.getDeclaredAnnotations()) {
                            if (a instanceof Component) {
                                log.info("find bean " + s + " inject container");
                                Object o = claz.newInstance();
                                ApplicationContext.newInstance().putBean(o);
                            }
                        }
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                        log.error(e.getMessage());
                    }
                }
        );
    }

    public static void main(String[] args) {
//        ApplicationContextProcessor.inject();
        ApplicationContext.newInstance().getBeans();
    }
}
