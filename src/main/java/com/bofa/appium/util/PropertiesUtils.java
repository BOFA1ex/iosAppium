package com.bofa.appium.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.util
 * @date 2018/12/16
 */
public class PropertiesUtils {

    private static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
    private static Properties properties;

    static {
        properties = new Properties();
    }

    public static String getProperty(String key) {
        try {
            FileReader reader = new FileReader("/Users/Bofa/iosAppium/src/main/resources/application.properties");
            properties.load(reader);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return properties.getProperty(key);
    }
}
