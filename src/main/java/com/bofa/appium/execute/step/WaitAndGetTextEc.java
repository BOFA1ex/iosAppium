package com.bofa.appium.execute.step;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.util.HttpUtil;
import com.bofa.appium.util.ImageUtils;
import com.google.common.collect.Lists;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.Arrays;
import java.util.List;


/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute.step
 * @date 2018/12/16
 */
@Component
public class WaitAndGetTextEc implements Execute {

    @Autowired(value = false)
    private IOSDriver<MobileElement> driver;

    public com.bofa.appium.execute.ExecuteReq getReq() {
        return req;
    }

    public void setReq(com.bofa.appium.execute.ExecuteReq req) {
        this.req = req;
    }

    private com.bofa.appium.execute.ExecuteReq req;

    private List<TitleContent> contents;

    @Data
    private class TitleContent {

        private String titleName;

        private String rightAnswer;

        private List<String> options;

    }

    /**
     * wait application element located
     * then get the element value
     */
    private boolean waitAndGetText() {

        TitleContent content = new TitleContent();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2000L);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])/XCUIElementTypeOther[2]")));
        /**
         * 题数y坐标 	77
         * 题目y坐标 >= 200
         * 第一个选项的y坐标 >= 300
         *
         */
        driver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'").forEach(
                mobileElement -> {
                    boolean exists = !mobileElement.getText().matches("^[1-5]/[5]$") &&
                            (mobileElement.getLocation().getY() <= 213 && mobileElement.getLocation().getY() >= 200);
                    if (exists) {
                        content.setTitleName(mobileElement.getText());
                    }
                }
        );

        if (StringUtils.isBlank(content.getTitleName()) || content.getTitleName().matches("^返回首页.*")) {
            log.error("找不到题目，已退出答题界面");
            return false;
        }

        log.error("题目 : " + content.getTitleName());


        /**
         * 等待选项,并把选项内容记录下来
         */
        webDriverWait = new WebDriverWait(driver, 1000L);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])/XCUIElementTypeOther[3]")));

        List<String> tmp = Lists.newArrayList();
        driver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'").forEach(
                mobileElement -> {
                    boolean exists = (mobileElement.getLocation().getY() >= 300);
                    if (exists) {
                        tmp.add(mobileElement.getText());
                    }
                }
        );
        content.setOptions(tmp);
        log.error("options : " + Arrays.toString(tmp.toArray()));

        /**
         * 点击第一个选项
         */
        try {
            Point firstPoint = driver.findElementByXPath(
                    "(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]"
            ).getLocation();
            new IOSTouchAction(driver).tap(PointOption.point(firstPoint)).perform();
        }catch (Exception e){
            content.setRightAnswer("点击事件出错，无法进行下一步OCR事件");
            System.err.println(e.getMessage());
            return true;
        }


        log.error("tap first option successful ");

        /**
         *截图，查找像素点的位置，裁剪图片获取正确答案的图片，然后通过百度OCR识别得到答案
         */

        Object screenShot = screenShot(driver);
        if(screenShot == null){
            content.setRightAnswer("图片识别不到答案");
        }else{
            content.setRightAnswer(screenShot.toString());
        }
        log.error("答案 : " + content.getRightAnswer());

        contents.add(content);

        return true;

    }

    private Object screenShot(WebDriver driver) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }
        File screen = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
        File screenFile = new File("screen.png");
        try {
            FileUtils.copyFile(screen, screenFile);
            /**
             * 裁剪图片
             */
            return ImageUtils.getImagePixel(screenFile.toString());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }


    @Override
    public void execute() {
        if (req == null) {
            throw new RuntimeException("req 不能为空!");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }
        contents = Lists.newArrayList();

        boolean flag;

        do {
            flag = waitAndGetText();
        } while (flag);

        contents.forEach(
                titleContent -> {
                    HttpUtil.postTitle(titleContent.getTitleName(), titleContent.rightAnswer, titleContent.getOptions());
                }
        );

        // TODO: 2018/12/17 考虑选项个数不统一，表字段会考虑扩展，因此当前选择nosql作为持久层
        // TODO: 2018/12/17 再者考虑logstash增量同步到es
        log.info(Arrays.toString(contents.toArray()));
    }

    @Override
    public String getName() {
        if (req == null) {
            return null;
        }
        return req.getDesc();
    }
}
