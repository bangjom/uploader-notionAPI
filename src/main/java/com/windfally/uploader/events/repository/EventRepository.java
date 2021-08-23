package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    @Query("select distinct e from Event e left join fetch e.ages left join fetch e.tags left join fetch e.showDates s where s.showDate = :showDate and e.address.city = :city")
    List<Event> findAllByShowDatesAndAddress_City(@Param("showDate") LocalDate showDate, @Param("city") String city);

    @Query("select distinct e from Event e left join fetch e.ages left join fetch e.tags left join fetch e.showDates s where s.showDate = :showDate order by e.createdDate")
    List<Event> findAllByShowDates(@Param("showDate") LocalDate showDate);

    @EntityGraph(attributePaths = {"tags", "ages", "detail", "topImages", "lowerImages"})
    Optional<Event> findOneWithTagsAgesDetailsTopImagesLowerImagesById(Long eventId);

    List<Event> findAllByAddressCity(@Param("city") String city, Pageable pageable);

    @Query("select distinct e from Event e left join fetch e.showDates s where s.showDate >= :showDate order by s.showDate")
    List<Event> findByShowDates_ShowDateIsAfter(LocalDate showDate);
}
