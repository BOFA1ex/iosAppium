package com.bofa.appium.util;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.util
 * @date 2018/12/17
 */
public class OCRUtils {


    public static Object requestOCR() {

        AipOcr client = new AipOcr("15118759", "rGkK77og0D0S3hizspTijXjB", "hndPEeDNg4VGvMA5FFxXsWDVrYu5huWY");

        // 可选：设置log4j日志输出格x式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "logback.xml");

        // 调用接口
        String path = "screen.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        if (res.getJSONArray("words_result") == null ||
                res.getJSONArray("words_result").isNull(0)) {
            return null;
        }
        return ((JSONObject) res.getJSONArray("words_result").get(0)).get("words");
    }

}
