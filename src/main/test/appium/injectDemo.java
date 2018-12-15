package appium;

import com.bofa.appium.EntryApplication;
import com.bofa.appium.annotation.Application;
import com.bofa.appium.container.ApplicaitonListener;
import com.bofa.appium.container.ApplicationContext;
import com.bofa.appium.container.ApplicationContextProcessor;
import com.bofa.appium.excute.ExecutePlatForm;
import com.bofa.appium.excute.ExecuteReq;
import com.bofa.appium.excute.step.WaitAndTapEc;
import org.junit.Test;

/**
 * @author Bofa
 * @version 1.0
 * @decription appium
 * @date 2018/12/15
 */
public class injectDemo {

    @Test
    public void demo(){

        ApplicaitonListener.initContainer();

        WaitAndTapEc ec = new WaitAndTapEc();
        ec.setReq(new ExecuteReq(null,"hello", (Long) null));
        ApplicationContext.newInstance().putBean(ec);

        ApplicaitonListener.refreshContext();
        ((ExecutePlatForm)ApplicationContext.newInstance().getBean(ExecutePlatForm.class.getName())).test();
    }
}
