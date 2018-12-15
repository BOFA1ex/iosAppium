package com.bofa.appium.aspect;

import com.bofa.appium.container.ApplicationContext;
import com.bofa.appium.util.ClockUtil;
import com.bofa.appium.util.Pattern;
import com.bofa.appium.util.ScanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Set;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.aspect
 * @date 2018/12/13
 */
public class AutoWiredAspect {

    private static final Logger log = LoggerFactory.getLogger(AutoWiredAspect.class);

    public static Object injection(String packageName) {
        Set<String> classNames = ScanUtils.scan(packageName);
        for (String s : classNames) {

        }
        return null;
    }

    public static boolean injection(Object obj) {
        Class<?> c = obj.getClass();

        for (Field field : c.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Autowired) {
                    log.info("find bean " + field.getName());
                    log.info("bean type " + field.getGenericType().toString());
                    try {
                        // 生成实例
                        field.set(obj, field.getGenericType().getClass().newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.error(e.getLocalizedMessage());
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
