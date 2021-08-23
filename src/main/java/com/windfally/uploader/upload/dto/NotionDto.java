package com.windfally.uploader.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class NotionDto {
    private String title;
    private String linkUrl;
    private String city;
    private String district;
    private List<String> show_dates;
    private List<String> tags;
    private String content;
    private String temp_schedule_detail;
    private String pageId;
    private String publish;
}
