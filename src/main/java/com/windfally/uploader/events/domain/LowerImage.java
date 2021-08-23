package com.windfally.uploader.events.domain;


import com.windfally.uploader.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="lower_images")
@Entity
public class LowerImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lower_image_id")
    private Long id;

    @Column(name ="image_url")
    @Lob
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    private Event event;
}
