package com.bofa.appium.execute.step;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.execute.dto.Event;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute.step
 * @date 2018/12/17
 */
@Component
public class ReTapEc implements com.bofa.appium.execute.step.Execute {


    @Autowired(value = false)
    private IOSDriver<MobileElement> driver;

    public com.bofa.appium.execute.ExecuteReq getReq() {
        return req;
    }

    public void setReq(com.bofa.appium.execute.ExecuteReq req) {
        this.req = req;
    }

    private com.bofa.appium.execute.ExecuteReq req;

    /**
     * repeat tap event
     */
    public void repeatExecute(List<Event> list){
        list.forEach(
                event -> {
                    WebDriverWait webDriverWait = new WebDriverWait(driver, 1000L);
                    webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(event.getElementValue())));
                    new IOSTouchAction(driver).tap(PointOption.point(driver.findElementByIosNsPredicate("value == '" + event.getElementValue() + "'").getLocation())).perform();
                    log.error("perform over");
                }
        );

    }

    @Override
    public void execute() {
        if (req == null) {
            throw new RuntimeException("req 不能为空!");
        }
        repeatExecute(req.getEvents());
    }

    @Override
    public String getName() {
        if (req == null) {
            return null;
        }
        return req.getDesc();
    }
}
