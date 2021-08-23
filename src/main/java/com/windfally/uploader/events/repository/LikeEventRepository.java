package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.LikeEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeEventRepository extends JpaRepository<LikeEvent,Long> {

    List<LikeEvent> findAllByUserId(Long userId);

    Optional<LikeEvent> findByUserIdAndEventId(Long userId,Long eventId);
}
