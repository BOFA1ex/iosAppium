package com.bofa.appium.excute;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute
 * @date 2018/12/14
 */
@Data
public class ExecuteReq {

    public static ExecuteReq newInstance(String[] args, String desc, Long... params) {
        if (StringUtils.isBlank(desc)) {
            return newInstance(args, params);
        }
        return new ExecuteReq(args, desc, params);
    }

    private static ExecuteReq newInstance(String[] args, Long... params) {
        if (params.length == 0) {
            return new ExecuteReq(args);
        }
        return new ExecuteReq(args, params);
    }

    public ExecuteReq(String[] args, String desc, Long... params) {
        this.args = args;
        this.params = params;
        this.desc = desc;
    }

    public ExecuteReq(String[] args, Long... params) {
        this.args = args;
        this.params = params;
    }

    public ExecuteReq(String[] args) {
        this.args = args;
    }

    private String[] args;

    private Long[] params;

    private String desc;

}
