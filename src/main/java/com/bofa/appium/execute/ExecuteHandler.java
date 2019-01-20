package com.bofa.appium.execute;

import com.bofa.appium.execute.step.Execute;
import com.google.common.collect.Lists;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium
 * @date 2018/12/13
 */
@Data
public class ExecuteHandler {

    private final Logger log = LoggerFactory.getLogger(ExecuteHandler.class);

    private List<Execute> executeList;

    public ExecuteHandler() {
        executeList = Lists.newArrayList();
    }

    public void process() {

        Execute reEvent = null, reTapEvent = null;

        for (Execute e : executeList) {
            if ("重复获取事件".equals(e.getName())) {
                reEvent = e;
            }

            log.info("handle execute : " + e.getName());
            e.execute();

            if ("重复点击事件".equals(e.getName())) {
                reTapEvent = e;
            }
        }

        if(reEvent != null && reTapEvent != null){
            while(true){
                reEvent.execute();
                reTapEvent.execute();
            }
        }

    }

}
