package com.bofa.appium;

import com.bofa.appium.annotation.Application;
import com.bofa.appium.container.ApplicationContext;
import com.bofa.appium.container.ApplicationListener;
import com.bofa.appium.execute.ExecutePlatForm;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium
 * @date 2018/12/13
 */
@Application("com.bofa.appium.excute")
public class EntryApplication {

    public static void main(String[] args) {
        ApplicationListener.initContainer();
        args = new String[]{"-u", "33d7b00840a89301ca24b85a50aa819800336701",
                "-b", "com.ai.aitribe2"};
        ((ExecutePlatForm) ApplicationContext.newInstance().getBean(ExecutePlatForm.class.getName())).main(args);
    }


}
