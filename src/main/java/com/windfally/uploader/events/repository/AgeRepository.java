package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Age;
import com.windfally.uploader.events.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgeRepository extends JpaRepository<Age,Long> {
    void deleteAllByEvent(Event event);
}
