package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
    void deleteAllByEvent(Event event);
}
