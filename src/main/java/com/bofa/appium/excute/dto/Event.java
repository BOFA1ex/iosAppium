package com.bofa.appium.excute.dto;

import lombok.Data;

/**
 * @author Bofa
 * @version 1.0
 * @decription com.bofa.appium.excute.dto
 * @date 2018/12/16
 */
@Data
public class Event {

    public Event(String elementValue, Long elementProcessTime) {
        this.elementValue = elementValue;
        this.elementProcessTime = elementProcessTime;
    }

    public Event(int elementXOffset, Integer elementYOffset, Long elementProcessTime) {
        this.elementXOffset = elementXOffset;
        this.elementYOffset = elementYOffset;
        this.elementProcessTime = elementProcessTime;
    }

    private String elementValue;

    private Integer elementXOffset;

    private Integer elementYOffset;

    private Long elementProcessTime;

    private String enable;

    private String sendKeys;
}
