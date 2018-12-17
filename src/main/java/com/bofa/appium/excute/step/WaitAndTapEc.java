package com.bofa.appium.excute.step;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.excute.ExecuteReq;
import com.bofa.appium.excute.dto.Event;
import com.bofa.appium.excute.dto.ExecuteConst;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute.step
 * @date 2018/12/13
 */
@Component
public class WaitAndTapEc implements Execute {

    @Autowired(value = false)
    private IOSDriver<MobileElement> driver;

    public ExecuteReq getReq() {
        return req;
    }

    public void setReq(ExecuteReq req) {
        this.req = req;
    }

    private ExecuteReq req;

    /**
     * wait application element located
     * then tap the element point
     */
    private void waitAndTap(List<Event> list) {
        list.forEach(
                event -> {
                    if (StringUtils.isNotBlank(event.getElementValue())) {
                        WebDriverWait webDriverWait = new WebDriverWait(driver, event.getElementProcessTime());
                        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(event.getElementValue())));
                        new IOSTouchAction(driver).tap(PointOption.point(driver.findElementByIosNsPredicate("value == '" + event.getElementValue() + "'").getLocation())).perform();
                        System.err.println("perform over");
                    }
                    if (event.getElementXOffset() != null && event.getElementYOffset() != null) {
                        new IOSTouchAction(driver).tap(PointOption.point(event.getElementXOffset(), event.getElementYOffset())).perform();
                        System.err.println("perform over");
                    }
                }
        );
    }

    @Override
    public void execute() {
        if (req == null) {
            throw new RuntimeException("req 不能为空!");
        }
        waitAndTap(req.getEvents());
    }

    @Override
    public String getName() {
        if (req == null) {
            return null;
        }
        return req.getDesc();
    }
}
