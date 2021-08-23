package com.windfally.uploader.events.dto;

import lombok.Data;


@Data
public class EventSearchCondition {
    private String type;
    private String city;
    private String district;
    private String showDate;
    private String title;
    private String tag;
    private Integer age;
}
