package com.bofa.appium.execute;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.execute.dto.Event;
import com.bofa.appium.execute.step.BaseEc;
import com.bofa.appium.execute.step.ReTapEc;
import com.bofa.appium.execute.step.WaitAndGetTextEc;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute
 * @date 2018/12/15
 */
@Component
public class ExecutePlatForm {

    @Autowired
    private com.bofa.appium.execute.step.WaitAndTapEc waitAndTapEc;

    @Autowired
    private BaseEc baseEc;

    @Autowired
    private WaitAndGetTextEc waitAndGetTextEc;

    @Autowired
    private ReTapEc reTapEc;

    /**
     * entry operation 2 platform
     *
     * @param args just for baseEc
     */
    public void main(String[] args) {
        baseEc.setReq(com.bofa.appium.execute.ExecuteReq.newInstance(args, "初始加载驱动"));
        waitAndTapEc.setReq(com.bofa.appium.execute.ExecuteReq.newInstance(
                null, "等待控件点击事件",
                new Event("应用", 3000L),
                new Event(0, 64, null),
                new Event("闯关", 3000L)));
        waitAndGetTextEc.setReq(com.bofa.appium.execute.ExecuteReq.newInstance(
                null, "重复获取事件")
        );
        reTapEc.setReq(com.bofa.appium.execute.ExecuteReq.newInstance(
                null, "重复点击事件",
                new Event("继续答题", null),
                new Event("闯关", 3000L)
        ));
        new ExecuteManager().setTask(
                true, baseEc, waitAndTapEc, waitAndGetTextEc, reTapEc
        );
    }

    public void test() {
        if (baseEc != null) {
            System.err.println(baseEc.getName());
        }
        if (waitAndTapEc != null) {
            System.err.println(waitAndTapEc.getName());
        }
    }
}
