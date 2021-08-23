package com.windfally.uploader.events.service;

import com.windfally.uploader.error.NotExistException;
import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.dto.*;
import com.windfally.uploader.events.mapper.EventMapper;
import com.windfally.uploader.events.repository.*;
import com.windfally.uploader.upload.service.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class EventService {
    public static final String SLASH = "/";
    public static final String THUMBNAIL = "thumbnail";
    public static final String TOP_IMAGE = "top_image";
    public static final String LOWER_IMAGE = "lower_image";

    private final EventRepository eventRepository;
    private final DetailRepository detailRepository;
    private final TopImageRepository topImageRepository;
    private final LowerImageRepository lowerImageRepository;
    private final ShowDateRepository showDateRepository;
    private final TagRepository tagRepository;
    private final AgeRepository ageRepository;
    private final Uploader uploader;

    public HashMap<String, Boolean> addEvent(EventDto eventDto) {
        HashMap<String, Boolean> result = new HashMap<>();
        Event saveEvent = eventRepository.save(eventDto.toEvent());
        if (eventDto.getThumbnail() != null && !eventDto.getThumbnail().isEmpty()) {
            String thumbnailUrl = uploader.upload(eventDto.getThumbnail(), saveEvent.getId() + SLASH + THUMBNAIL);
            saveEvent.updateThumbnail(thumbnailUrl);
        }
        detailRepository.save(eventDto.toDetail(saveEvent));
        if (eventDto.getTop_images() != null && !eventDto.getTop_images().get(0).isEmpty()) {
            List<String> topImageUrls = saveImages(eventDto.getTop_images(), saveEvent.getId() + SLASH + TOP_IMAGE);
            topImageRepository.saveAll(eventDto.toTopImage(saveEvent, topImageUrls));
        }
        if (eventDto.getLower_images() != null && !eventDto.getLower_images().get(0).isEmpty()) {
            List<String> lowerImageUrls = saveImages(eventDto.getLower_images(), saveEvent.getId() + SLASH + LOWER_IMAGE);
            lowerImageRepository.saveAll(eventDto.toLowerImage(saveEvent, lowerImageUrls));
        }
        showDateRepository.saveAll(eventDto.toShowDates(saveEvent));
        if (eventDto.getTags() != null && !eventDto.getTags().isEmpty()) {
            tagRepository.saveAll(eventDto.toTags(saveEvent));
        }
        if (eventDto.getAges() != null && !eventDto.getAges().isEmpty()) {
            ageRepository.saveAll(eventDto.toAges(saveEvent));
        }
        result.put("success", true);
        return result;
    }


    private List<String> saveImages(List<MultipartFile> files, String dirName) {
        return files.stream()
                .map(file -> uploader.upload(file, dirName))
                .collect(Collectors.toList());
    }


    public HashMap<String, Boolean> editEvent(EventDto eventDto) {
        HashMap<String, Boolean> result = new HashMap<>();
        Optional<Event> findEvent = eventRepository.findById(eventDto.getEventId());
        Event event = findEvent.orElseThrow(() -> new NotExistException("이벤트가 존재하지 않습니다."));
        String thumbnailUrl = null;
        event.updateMain(eventDto.toEvent());
        if (eventDto.getThumbnail() != null && !eventDto.getThumbnail().isEmpty()) {
            thumbnailUrl = uploader.upload(eventDto.getThumbnail(), event.getId() + SLASH + THUMBNAIL);
        }
        event.updateThumbnail(thumbnailUrl);
        event.getDetail().update(eventDto.toDetail(event));
        topImageRepository.deleteAllByEvent(event);
        if (eventDto.getTop_images() != null && !eventDto.getTop_images().get(0).isEmpty()) {
            List<String> topImageUrls = saveImages(eventDto.getTop_images(), event.getId() + SLASH + TOP_IMAGE);
            event.updateTopImages(eventDto.toTopImage(event, topImageUrls));
        }
        lowerImageRepository.deleteAllByEvent(event);
        if (eventDto.getLower_images() != null && !eventDto.getLower_images().get(0).isEmpty()) {
            List<String> lowerImageUrls = saveImages(eventDto.getLower_images(), event.getId() + SLASH + LOWER_IMAGE);
            event.updateLowerImages(eventDto.toLowerImage(event, lowerImageUrls));
        }
        showDateRepository.deleteAllByEvent(event);
        event.updateShowDates(eventDto.toShowDates(event));
        tagRepository.deleteAllByEvent(event);
        ageRepository.deleteAllByEvent(event);
        if (eventDto.getTags() != null && !eventDto.getTags().isEmpty()) {
            event.updateTags(eventDto.toTags(event));
        }
        if (eventDto.getAges() != null && !eventDto.getAges().isEmpty()) {
            event.updateAges(eventDto.toAges(event));
        }
        result.put("success", true);
        return result;
    }

    public HashMap<String, Boolean> deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    public List<com.windfally.uploader.upload.dto.EventDto> getEventsAfterShowDates(LocalDate date) {
        return eventRepository.findByShowDates_ShowDateIsAfter(date).stream()
                .filter(event -> event.getTitle() != null)
                .map(EventMapper.INSTANCE::toUploadEventDto)
                .collect(Collectors.toList());
    }
}
