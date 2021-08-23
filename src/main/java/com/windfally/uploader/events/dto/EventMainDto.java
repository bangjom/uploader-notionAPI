package com.windfally.uploader.events.dto;

import com.windfally.uploader.events.domain.Age;
import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.domain.ShowDate;
import com.windfally.uploader.events.domain.Tag;
import lombok.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventMainDto {
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
    private List<LocalDate> showDates;

    public static EventMainDto of(Event event) {
        return EventMainDto.builder()
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
                .showDates(event.getShowDates().stream().map(ShowDate::getShowDate).collect(Collectors.toList()))
                .build();
    }
}
