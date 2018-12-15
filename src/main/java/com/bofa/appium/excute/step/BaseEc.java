package com.bofa.appium.excute.step;

import com.bofa.appium.annotation.Component;
import com.bofa.appium.container.ApplicationContext;
import com.bofa.appium.excute.ExecuteReq;
import com.bofa.appium.util.Shell;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium
 * @date 2018/12/13
 */
@Component
public class BaseEc implements Execute{

    private String url;

    private ExecuteReq req;

    public ExecuteReq getReq() {
        return req;
    }

    public void setReq(ExecuteReq req) {
        this.req = req;
    }

    public BaseEc(){
        url = System.getProperty("URL");
    }


    private static boolean needhelp = false;
    private static String UDID, BUNDLEID;
    private static String PORT = "4723";
    private static String PROXYPORT = "8100";

    private void executeParameter(String[] args) {
        int optSetting = 0;

        for (; optSetting < args.length; optSetting++) {
            if ("-u".equals(args[optSetting])) {
                UDID = args[++optSetting];
            } else if ("-b".equals(args[optSetting])) {
                BUNDLEID = args[++optSetting];
            } else if ("-port".equals(args[optSetting])) {
                PORT = args[++optSetting];
            } else if ("-proxyport".equals(args[optSetting])) {
                PROXYPORT = args[++optSetting];
            } else if ("-h".equals(args[optSetting])) {
                needhelp = true;
                System.out.println(
                        "-u:设备的UDID\n" +
                                "-b:测试App的Bundle\n" +
                                "-port:macaca服务的端口，默认" + PORT + "\n" +
                                "-proxyport:usb代理端口，默认" + PROXYPORT);
                break;
            }
        }

        if (!needhelp) {
            try {
                System.out.println("测试设备:" + UDID + "\n" + "测试App:" + BUNDLEID);
                init();
            } catch (Exception e) {
                System.err.println("run error message : " + e.getMessage());
            }
        }
    }


    private void init() {

        //启动app守护进程
        try {
            Shell.launchAPP(UDID, BUNDLEID);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", System.getProperty("platform"));
        caps.setCapability("platformVersion", System.getProperty("platformVersion"));
        caps.setCapability("deviceName", System.getProperty("deviceName"));
        caps.setCapability("xcodeOrgId", System.getProperty("xcodeOrgId"));
        caps.setCapability("automationName", AutomationName.IOS_XCUI_TEST);
        caps.setCapability("autoAcceptAlerts", System.getProperty("autoAcceptAlerts"));
        caps.setCapability("noReset", System.getProperty("noReset"));
        caps.setCapability("udid", UDID);
        caps.setCapability("bundleId", BUNDLEID);

        try {
            log.info("application start " );
            IOSDriver<MobileElement> driver = new IOSDriver<>(new URL(url), caps);
            ApplicationContext.newInstance().putBean(driver);
        } catch (Exception e) {
            log.error("init error message : " + e.getLocalizedMessage() );
        }
    }

    @Override
    public void execute() {
        if(req == null){
            throw new RuntimeException("req 不能为空！");
        }
        executeParameter(req.getArgs());
        init();
    }

    @Override
    public String getName() {
        if(req == null){
            return null;
        }
        return req.getDesc();
    }
}
