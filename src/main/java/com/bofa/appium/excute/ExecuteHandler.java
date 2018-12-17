package com.bofa.appium.excute;

import com.bofa.appium.excute.step.Execute;
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

    private final Logger log  = LoggerFactory.getLogger(ExecuteHandler.class);

    private List<Execute> executeList;

    public ExecuteHandler() {
        executeList = Lists.newArrayList();
    }

    public void process(){
        executeList.forEach(
                e -> {
                    log.info("handle execute : " + e.getName());
                    e.execute();
                }
        );
    }

}
