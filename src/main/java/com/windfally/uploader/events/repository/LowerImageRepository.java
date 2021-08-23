package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.domain.LowerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LowerImageRepository extends JpaRepository<LowerImage,Long> {
    void deleteAllByEvent(Event event);
}
