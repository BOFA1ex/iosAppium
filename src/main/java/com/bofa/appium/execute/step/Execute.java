package com.bofa.appium.execute.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute.step
 * @date 2018/12/13
 */
public interface Execute {

    Logger log  = LoggerFactory.getLogger(Execute.class);

    void execute();

    String getName();


}
