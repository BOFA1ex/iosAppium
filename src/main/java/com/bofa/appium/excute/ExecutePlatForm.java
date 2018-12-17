package com.bofa.appium.excute;

import com.bofa.appium.annotation.Autowired;
import com.bofa.appium.annotation.Component;
import com.bofa.appium.excute.dto.Event;
import com.bofa.appium.excute.step.BaseEc;
import com.bofa.appium.excute.step.WaitAndGetTextEc;
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

    @Autowired
    private WaitAndGetTextEc waitAndGetTextEc;

    //    "32"，"213"
//    "47"，"394"
    public void main(String[] args) {
        baseEc.setReq(ExecuteReq.newInstance(args, "初始加载驱动"));
        waitAndTapEc.setReq(ExecuteReq.newInstance(
                null, "等待控件点击事件",
                new Event("应用", 3000L),
                new Event(0, 64, null),
                new Event("闯关", 3000L)));
        waitAndGetTextEc.setReq(ExecuteReq.newInstance(
                null, "获取题目、选项、以及当前题数内容事件",
                new Event("XCUIElementTypeStaticText", 0L)
        ));
        new ExecuteManager().setTask(
                true, baseEc, waitAndTapEc, waitAndGetTextEc
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
