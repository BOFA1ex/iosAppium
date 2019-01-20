package com.bofa.appium.util;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.openqa.selenium.remote.internal.HttpClientFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute
 * @date 2018/12/23
 */
public class HttpUtil {

    public static void postTitle(String titleName, String rightAnswer, List<String> options){
        HttpPost post = new HttpPost("http://118.25.92.228:8088/question/add");

        post.setHeader("Content-Type", "application/json;charset=utf-8");

        HashMap<String, Object> formMap = new HashMap<>(16);

        formMap.put("titleName", titleName);
        formMap.put("options", options);
        formMap.put("rightAnswer", rightAnswer);

        String json = new Gson().toJson(formMap);

        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));

        post.setEntity(stringEntity);

        try {
            System.err.println(post);
            new HttpClientFactory(2000, 2000).getHttpClient().execute(post);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
