package com.windfally.uploader.events.domain;

import com.windfally.uploader.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "event_details")
@Entity
public class Detail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_detail_id")
    private Long id;

    @Column(name = "application_target")
    private String target;

    @Column(name = "application_start")
    private LocalDate applicationStart;

    @Column(name = "application_end")
    private LocalDate applicationEnd;

    @Column(name = "entry_fee")
    private Long entry_fee;

    @Column(name = "inquiry")
    private String inquiry;

    @Column(name = "place")
    private String place;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name="acceptance_date")
    private LocalDate acceptanceDate;

    @Column(name = "benefit")
    private String benefit;

    @Column(name = "opcacity")
    private Integer opacity;

    @Column(name = "application_method")
    private String applicationMethod;

    @Column(name = "training_schedule")
    private String trainingSchedule;

    @Column(name = "temp_schedule")
    private String tempSchedule;

    @Column(name = "temp_schedule_detail")
    @Lob
    private String tempScheduleDetail;

    @Column(name="video_url")
    @Lob
    private String video;

    @Column(name="curation_image")
    @Lob
    private String curationImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public void update(Detail detail) {
        this.target = detail.getTarget();
        this.applicationStart = detail.getApplicationStart();
        this.applicationEnd = detail.getApplicationEnd();
        this.entry_fee = detail.getEntry_fee();
        this.inquiry = detail.getInquiry();
        this.place = detail.getPlace();
        this.content = detail.getContent();
        this.acceptanceDate = detail.getAcceptanceDate();
        this.benefit = detail.getBenefit();
        this.opacity = detail.getOpacity();
        this.applicationMethod = detail.getApplicationMethod();
        this.trainingSchedule = detail.getTrainingSchedule();
        this.tempSchedule = detail.getTempSchedule();
        this.tempScheduleDetail = detail.getTempScheduleDetail();
        this.video = detail.getVideo();
        this.curationImage =detail.getCurationImage();
    }
}
