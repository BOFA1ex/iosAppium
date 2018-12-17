package appium;

import com.bofa.appium.container.ApplicationContext;
import com.bofa.appium.container.ApplicationListener;
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

        ApplicationListener.initContainer();

        WaitAndTapEc ec = new WaitAndTapEc();
        ec.setReq(new ExecuteReq(null,"hello", null));
        ApplicationContext.newInstance().putBean(ec);

        ApplicationListener.refreshContext();
        ((ExecutePlatForm)ApplicationContext.newInstance().getBean(ExecutePlatForm.class.getName())).test();
    }
}
