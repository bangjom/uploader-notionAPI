package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.domain.ShowDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowDateRepository extends JpaRepository<ShowDate, Long> {
    void deleteAllByEvent(Event event);

    List<ShowDate> findAllByIsBestOrderByShowDateDesc(Pageable pageable,Boolean isBest);

    boolean existsByEvent_IdAndIsBest(Long eventId, boolean isBest);
}
