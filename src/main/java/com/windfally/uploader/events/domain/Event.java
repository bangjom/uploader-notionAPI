package com.windfally.uploader.events.domain;

import com.windfally.uploader.BaseTimeEntity;
import com.windfally.uploader.user.entity.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "events")
@Entity
@ToString
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "type")
    private String type;

    @Column(name = "discount_price")
    private Long discountPrice;

    @Column(name = "original_price")
    private Long price;

    @Column(name = "link_url")
    private String linkUrl;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(name = "event_start_date")
    private LocalDate eventStartDate;

    @Column(name = "event_end_date")
    private LocalDate eventEndDate;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "thumbnail_url")
    @Lob
    private String thumbnailUrl;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Age> ages = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<ShowDate> showDates = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<TopImage> topImages = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<LowerImage> lowerImages = new HashSet<>();

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private Detail detail;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<LikeEvent> likeEvents = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
    }

    public void updateThumbnail(String url) {
        this.thumbnailUrl = url;
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void substractLikeCount() {
        this.likeCount--;
    }

    public void updateMain(Event event) {
        this.title = event.getTitle();
        this.subtitle = event.getSubtitle();
        this.type = event.getType();
        this.discountPrice = event.getDiscountPrice();
        this.price = event.getPrice();
        this.address = event.getAddress();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.linkUrl = event.getLinkUrl();
    }

    public void updateTopImages(List<TopImage> topImages) {
        this.topImages = new HashSet<>(topImages);
    }

    public void updateLowerImages(List<LowerImage> lowerImages) {
        this.lowerImages = new HashSet<>(lowerImages);
    }

    public void updateShowDates(List<ShowDate> showDates) {
        this.showDates = new HashSet<>(showDates);
    }

    public void updateTags(List<Tag> tags) {
        this.tags = new HashSet<>(tags);
    }

    public void updateAges(List<Age> ages) {
        this.ages = new HashSet<>(ages);
    }

    public boolean isBestAtTheDay(LocalDate showDate){
        return this.getShowDates().stream()
                .filter(date -> date.getShowDate().equals(showDate))
                .anyMatch(ShowDate::getIsBest);
    }

    public Optional<ShowDate> getDistinctShowDate(LocalDate showDate){
        return this.getShowDates().stream()
                .filter(date -> date.getShowDate().equals(showDate))
                .findAny();
    }
}
