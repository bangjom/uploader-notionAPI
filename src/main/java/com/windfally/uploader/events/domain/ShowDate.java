package com.windfally.uploader.events.domain;

import com.windfally.uploader.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "show_dates")
@Entity
public class ShowDate extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_date_id")
    private Long id;

    @Column(name = "show_date")
    private LocalDate showDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "best",columnDefinition = "tinyint(1) DEFAULT 0")
    private Boolean isBest;

    public void trueBest() {
        this.isBest = true;
    }

    public void falseBest() {
        this.isBest = false;
    }
}
