package com.windfally.uploader.events.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventShowDateDto {
    private Long eventId;
    private List<String> showDates;
}
