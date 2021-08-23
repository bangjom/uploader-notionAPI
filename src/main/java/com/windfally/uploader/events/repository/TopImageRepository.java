package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.domain.TopImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopImageRepository extends JpaRepository<TopImage,Long> {

    void deleteAllByEvent(Event event);
}
