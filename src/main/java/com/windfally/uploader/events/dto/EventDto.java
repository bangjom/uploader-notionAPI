package com.windfally.uploader.events.dto;

import com.windfally.uploader.events.domain.*;
import com.windfally.uploader.user.entity.Address;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventDto {
    private Long eventId;
    private String title;
    private String subtitle;
    private String type;
    private Long discount_price;
    private Long price;
    private String linkUrl;
    private String city;
    private String district;
    private String event_start_date;
    private String event_end_date;
    private MultipartFile thumbnail;
    private List<MultipartFile> lower_images;
    private List<MultipartFile> top_images;
    private MultipartFile curation_image;
    private List<String> show_dates;
    private List<String> tags;
    private String target;
    private String application_start;
    private String application_end;
    private Long entry_fee;
    private String inquiry;
    private String place;
    private String content;
    private String acceptanceDate;
    private String benefit;
    private Integer opacity;
    private Integer likeCount;
    private List<Integer> ages;
    private String application_method;
    private String training_schedule;
    private String temp_schedule;
    private String temp_schedule_detail;
    private String video_url;

    public Event toEvent() {
        return Event.builder()
                .title(this.title)
                .subtitle(this.subtitle)
                .type(this.type)
                .discountPrice(this.discount_price)
                .price(this.price)
                .linkUrl(this.linkUrl)
                .address(new Address(checkCity(this.city), this.district))
                .eventStartDate(this.event_start_date == null ? null : LocalDate.parse(this.event_start_date, DateTimeFormatter.ISO_DATE))
                .eventEndDate(this.event_end_date == null ? null : LocalDate.parse(this.event_end_date, DateTimeFormatter.ISO_DATE))
                .likeCount(0)
                .thumbnailUrl("")
                .build();
    }

    private String checkCity(String city) {
        if(city == null){
            return "전국";
        }
        return city;
    }

    public Detail toDetail(Event saveEvent) {
        return Detail.builder()
                .target(this.target)
                .applicationStart(this.application_start == null ? null : LocalDate.parse(this.application_start, DateTimeFormatter.ISO_DATE))
                .applicationEnd(this.application_end == null ? null : LocalDate.parse(this.application_end, DateTimeFormatter.ISO_DATE))
                .entry_fee(this.entry_fee)
                .inquiry(this.inquiry)
                .place(this.place)
                .content(this.content)
                .acceptanceDate(this.acceptanceDate == null ? null : LocalDate.parse(this.acceptanceDate, DateTimeFormatter.ISO_DATE))
                .benefit(this.benefit)
                .opacity(this.opacity)
                .applicationMethod(this.application_method)
                .trainingSchedule(this.training_schedule)
                .tempSchedule(this.temp_schedule)
                .tempScheduleDetail(this.temp_schedule_detail)
                .video(this.video_url)
                .event(saveEvent)
                .build();
    }

    public List<TopImage> toTopImage(Event saveEvent, List<String> urls) {
        return urls.stream()
                .map(url -> TopImage.builder()
                        .url(url)
                        .event(saveEvent)
                        .build())
                .collect(Collectors.toList());
    }

    public List<LowerImage> toLowerImage(Event saveEvent, List<String> urls) {
        return urls.stream()
                .map(url -> LowerImage.builder()
                        .url(url)
                        .event(saveEvent)
                        .build())
                .collect(Collectors.toList());
    }

    public List<ShowDate> toShowDates(Event saveEvent) {
        List<LocalDate> result = new LinkedList<>();
        List<LocalDate> collect = show_dates.stream()
                .filter(show_date -> !show_date.contains("~"))
                .map(show_date -> LocalDate.parse(show_date, DateTimeFormatter.ISO_DATE))
                .collect(Collectors.toList());
        result.addAll(collect);
        show_dates.stream()
                .map(show_date -> show_date.replaceAll(" ",""))
                .filter(show_date -> show_date.contains("~"))
                .forEach(show_date -> {
                    String[] split = show_date.split("~");
                    LocalDate start = LocalDate.parse(split[0], DateTimeFormatter.ISO_DATE);
                    LocalDate end = LocalDate.parse(split[1], DateTimeFormatter.ISO_DATE);
                    result.addAll(start.datesUntil(end).collect(Collectors.toList()));
                    result.add(end);
                });
        return result.stream()
                .map(date -> ShowDate.builder().showDate(date).isBest(false).event(saveEvent).build())
                .collect(Collectors.toList());
    }

    public List<Tag> toTags(Event saveEvent) {
        return tags.stream()
                .map(tag -> Tag.builder().name(tag).event(saveEvent).build())
                .collect(Collectors.toList());
    }

    public List<Age> toAges(Event saveEvent)  {
        return ages.stream()
                .map(age -> Age.builder().age(age).event(saveEvent).build())
                .collect(Collectors.toList());
    }
}
