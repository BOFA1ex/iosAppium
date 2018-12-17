package com.bofa.appium.excute.step;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.excute.ExecuteReq;
import com.bofa.appium.excute.dto.Event;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public ExecuteReq getReq() {
        return req;
    }

    public void setReq(ExecuteReq req) {
        this.req = req;
    }

    private ExecuteReq req;

    private int count = 1;

    private String titleName;

    private String answer;

    private String wrongAnswer;


    private void increment() {
        if (count == 5) {
            count = 1;
        } else {
            count++;
        }
    }

    /**
     * wait application element located
     * then get the element value
     */
    private void waitAndGetText(List<Event> list) {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2000L);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])[2]/XCUIElementTypeOther[2]")));
        /**
         * 题数y坐标 	77
         * 题目y坐标 213
         * 第一个选项的y坐标 369
         *
         */

        driver.findElementsByIosNsPredicate("type == 'XCUIElementTypeStaticText'").forEach(
                mobileElement -> {
                    boolean exists = !mobileElement.getText().matches("^[1-5]/[5]$") &&
                            (mobileElement.getLocation().getY() <= 213 && mobileElement.getLocation().getY() >= 200);
                    if (exists) {
                        titleName = mobileElement.getText();
                    }
                }
        );

        log.error("题目 : " + titleName);


        /**
         * 等待选项
         */
        webDriverWait = new WebDriverWait(driver, 1000L);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])[2]/XCUIElementTypeOther[3]")));

        /**
         * 点击第一个选项
         */
        Point firstPoint = driver.findElementByXPath(
                "(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]"
        ).getLocation();
        new IOSTouchAction(driver).tap(PointOption.point(firstPoint)).perform();

        /**
         * 查找正确答案
         */
        String parentNode = "(//XCUIElementTypeOther[@name=\"信者无敌 向前冲鸭\"])[2]/XCUIElementTypeOther[3]";

        for (int i = 1; i < 5; i++) {

            String childrenNode = "/XCUIElementTypeOther[" + i + "]/XCUIElementTypeOther[2]";

            MobileElement elementByXPath = driver.findElementByXPath(parentNode + childrenNode);

            if (elementByXPath != null) {
                /**
                 * 排除错误答案, 也有可能第一个选项就是正确的
                 */
                boolean exists = (elementByXPath.getLocation().getY() == firstPoint.getY());
                if (exists) {
                    wrongAnswer = elementByXPath.getText();
                }
                answer = elementByXPath.getText();
            }
        }
        /**
         * 如果正确答案为空，说明第一个选项就是正确的
         */
        if (StringUtils.isBlank(answer)) {
            answer = wrongAnswer;
        }

        log.error("答案 : " + answer);

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
        waitAndGetText(req.getEvents());
    }

    @Override
    public String getName() {
        if (req == null) {
            return null;
        }
        return req.getDesc();
    }
}
