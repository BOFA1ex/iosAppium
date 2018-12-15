package com.bofa.appium.excute;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.excute.step.BaseEc;
import com.bofa.appium.excute.step.WaitAndTapEc;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute
 * @date 2018/12/15
 */
@Component
public class ExecutePlatForm {

    @Autowired
    private WaitAndTapEc waitAndTapEc;

    @Autowired
    private BaseEc baseEc;

    public void main(String[] args){
        baseEc.setReq(ExecuteReq.newInstance(args, "初始加载驱动", (Long) null));
        waitAndTapEc.setReq(ExecuteReq.newInstance(new String[]{"应用"}, "等待控件点击事件", 3000L));

        new ExecuteManager().setTask(
                true,baseEc,waitAndTapEc
        );
    }

    public void test(){
        if(baseEc != null){
            System.err.println(baseEc.getName());
        }
        if(waitAndTapEc != null){
            System.err.println(waitAndTapEc.getName());
        }
    }
}
