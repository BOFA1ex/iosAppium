package com.bofa.appium.excute;

import com.bofa.appium.excute.step.Execute;
import com.bofa.appium.util.ClockUtil;
import com.bofa.appium.util.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium
 * @date 2018/12/13
 */
public class ExecuteManager {

    private static final Logger log = LoggerFactory.getLogger(ExecuteManager.class);

    private static ExecuteHandler handler;


    public ExecuteManager() {
        if (handler == null) {
            handler = new ExecuteHandler();
        }
    }

    public void setTask(boolean flag, Execute... execute) {
        for (Execute e : execute) {
            handler.getExecuteList().add(e);
        }
        if(flag){
            process();
        }
    }

    public void process() {
        log.info(ClockUtil.currentDate(Pattern.YEAR_PATTERN) + " start process");
        handler.process();
    }

    public void clearTask() {
        handler.getExecuteList().clear();
    }

}
