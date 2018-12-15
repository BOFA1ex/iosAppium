package com.bofa.appium.excute.step;

import com.bofa.appium.annotation.Component;
import com.bofa.appium.excute.ExecuteReq;
import com.bofa.appium.excute.dto.ExecuteConst;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute.step
 * @date 2018/12/13
 */
@Component
public class WaitAndTapEc implements Execute {


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
    private void waitAndTap(Long[] waitTime, String[] elementValue) {

        if (waitTime.length == 0) {
            waitTime = new Long[]{ExecuteConst.DEFAULT_WAIT_TIME};
        }
        if (elementValue.length == 0) {
            log.error("elementValue is null");
        }
        for (int i = 0; i < elementValue.length; i++) {
            String elementV = elementValue[i];
//            WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime[i]);
//            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(elementV)));
//            new IOSTouchAction(driver).tap(PointOption.point(driver.findElementByIosNsPredicate(elementV).getLocation())).perform();
        }
    }

    @Override
    public void execute() {
        if(req == null){
            throw new RuntimeException("req 不能为空!");
        }
        waitAndTap(req.getParams(), req.getArgs());
    }

    @Override
    public String getName() {
        if(req == null){
            return null;
        }
        return req.getDesc();
    }
}
