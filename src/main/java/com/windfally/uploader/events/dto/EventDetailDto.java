package com.windfally.uploader.events.dto;

import com.windfally.uploader.events.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
public class EventDetailDto {
    private Long id;
    private String title;
    private String subtitle;
    private String type;
    private Long discountPrice;
    private Long price;
    private String linkUrl;
    private String city;
    private String district;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private Integer likeCount;
    private String thumbnailUrl;
    private List<Integer> ages;
    private List<String> tags;
    private String target;
    private LocalDate applicationStart;
    private LocalDate applicationEnd;
    private Long entry_fee;
    private String inquiry;
    private String place;
    private String content;
    private LocalDate acceptanceDate;
    private String benefit;
    private Integer opacity;
    private List<String> topImageUrl;
    private List<String> lowerImageUrl;
    private String curationImage;
    private Boolean isPressed;
    private String applicationMethod;
    private String trainingSchedule;
    private String temp_schedule;
    private String temp_schedule_detail;
    private List<LocalDate> show_dates;
    private String video_url;

    public static EventDetailDto of(Event event, Boolean isPressed) {
        return EventDetailDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .subtitle(event.getSubtitle())
                .type(event.getType())
                .discountPrice(event.getDiscountPrice())
                .price(event.getPrice())
                .linkUrl(event.getLinkUrl())
                .city(event.getAddress().getCity())
                .district(event.getAddress().getDistrict())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .likeCount(event.getLikeCount())
                .thumbnailUrl(event.getThumbnailUrl())
                .ages(event.getAges().stream().map(Age::getAge).collect(Collectors.toList()))
                .tags(event.getTags().stream().sorted(Comparator.comparing(Tag::getId)).map(Tag::getName).collect(Collectors.toList()))
                .target(event.getDetail().getTarget())
                .applicationStart(event.getDetail().getApplicationStart())
                .applicationEnd(event.getDetail().getApplicationEnd())
                .entry_fee(event.getDetail().getEntry_fee())
                .inquiry(event.getDetail().getInquiry())
                .place(event.getDetail().getPlace())
                .content(event.getDetail().getContent())
                .acceptanceDate(event.getDetail().getAcceptanceDate())
                .benefit(event.getDetail().getBenefit())
                .opacity(event.getDetail().getOpacity())
                .topImageUrl(event.getTopImages().stream().map(TopImage::getUrl).collect(Collectors.toList()))
                .lowerImageUrl(event.getLowerImages().stream().map(LowerImage::getUrl).collect(Collectors.toList()))
                .applicationMethod(event.getDetail().getApplicationMethod())
                .trainingSchedule(event.getDetail().getTrainingSchedule())
                .temp_schedule(event.getDetail().getTempSchedule())
                .temp_schedule_detail(event.getDetail().getTempScheduleDetail())
                .isPressed(isPressed)
                .video_url(event.getDetail().getVideo())
                .show_dates(event.getShowDates().stream().sorted(Comparator.comparing(ShowDate::getShowDate)).map(ShowDate::getShowDate).collect(Collectors.toList()))
                .build();
    }
}
