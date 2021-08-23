package com.windfally.uploader.events.api;

import com.windfally.uploader.events.dto.EventDto;
import com.windfally.uploader.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/event")
@RequiredArgsConstructor
@RestController
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<HashMap<String,Boolean>> postEvent(EventDto eventDto) {
        return ResponseEntity.ok(eventService.addEvent(eventDto));
    }

    @PutMapping
    public ResponseEntity<HashMap<String,Boolean>> editEvent(EventDto eventDto) {
        return ResponseEntity.ok(eventService.editEvent(eventDto));
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String,Boolean>> deleteEvent(@RequestBody Map<String, Long> request) {
        return ResponseEntity.ok(eventService.deleteEvent(request.get("event_id")));
    }

}
