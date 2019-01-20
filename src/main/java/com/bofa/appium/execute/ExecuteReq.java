package com.bofa.appium.execute;

import com.bofa.appium.execute.dto.Event;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute
 * @date 2018/12/14
 */
@Data
public class ExecuteReq {


    public static ExecuteReq newInstance(String[] args, String desc) {
        return new ExecuteReq(args, desc);
    }

    public static ExecuteReq newInstance(String[] args, String desc, Event... events) {
        ArrayList<Event> tmp = Lists.newArrayList();
        Collections.addAll(tmp, events);
        return new ExecuteReq(args, desc, tmp);
    }


    public static ExecuteReq newInstance(String[] args, String desc, int xoffset, int yoffset, Long processTime) {
        return new ExecuteReq(args, desc, xoffset, yoffset, processTime);
    }


    public static ExecuteReq newInstance(String[] args, String desc, String elementValue, Long processTime) {
        return new ExecuteReq(args, desc, elementValue, processTime);
    }


    public ExecuteReq(String[] args, String desc, String elementValue, Long processTime) {
        this.args = args;
        this.desc = desc;
        events = Lists.newArrayList();
        events.add(new Event(elementValue, processTime));
    }

    public ExecuteReq(String[] args, String desc, int xoffset, int yoffset, Long processTime) {
        this.args = args;
        this.desc = desc;
        events = Lists.newArrayList();
        events.add(new Event(xoffset, yoffset, processTime));
    }

    public ExecuteReq(String[] args, String desc, List<Event> events) {
        this.args = args;
        this.desc = desc;
        this.events = Lists.newArrayList();
        this.events.addAll(events);
    }

    public ExecuteReq(String[] args, String desc) {
        this.args = args;
        this.desc = desc;
    }

    private String[] args;

    private List<Event> events;

    private String desc;

}
