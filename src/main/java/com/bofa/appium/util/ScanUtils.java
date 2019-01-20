package com.bofa.appium.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.util
 * @date 2018/12/14
 */
public class ScanUtils {

    private static final Logger log = LoggerFactory.getLogger(ScanUtils.class);

    private static Set<String> classNames = new HashSet<>();

    public static Set<String> scan(String packageName) {
        if (StringUtils.isBlank(packageName)) {
            throw new RuntimeException("the path must not be null!");
        }

        String packagePath = packageName.replace(".", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            Enumeration<URL> urls = loader.getResources(packagePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                log.info("" + url);
                if ("file".equals(url.getProtocol())) {
                    String path = URLDecoder.decode(url.getPath(), "utf-8");
                    scanFromDir(path, packageName);
                }
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return classNames;
    }

    private static void scanFromDir(String filePath, String packageName) {

        File[] files = new File(filePath).listFiles();
        packageName = packageName + ".";
        for (File childFile : files) { //遍历文件
            if (childFile.isDirectory()) {//还是文件夹就继续递归
                scanFromDir(childFile.getPath(), packageName + childFile.getName());
            } else {
                String fileName = childFile.getName();//文件名
                if (fileName.endsWith(".class")) {//判断文件名是否是.class结尾
                    if (packageName.charAt(0) == '.') {//判断开头是否有'.',有的话截取
                        packageName = packageName.substring(1, packageName.length());
                    }
                    //拼接字符串，得到完整类名 如：edu.nf.beans.util.ScanUtil
                    String className = packageName + fileName.replace(".class", "");
                    try {
                        if (!Class.forName(className).isInterface()) {
                            classNames.add(className);
                        }
                    } catch (ClassNotFoundException e) {
                        log.error(e.getLocalizedMessage());
                    }
                }
            }
        }
    }
}
