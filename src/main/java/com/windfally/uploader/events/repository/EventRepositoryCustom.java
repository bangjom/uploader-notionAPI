package com.windfally.uploader.events.repository;


import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.dto.EventSearchCondition;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> search(EventSearchCondition eventSearchCondition);
}
