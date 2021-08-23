package com.windfally.uploader.events.mapper;

import com.windfally.uploader.events.domain.Event;
import com.windfally.uploader.events.dto.EventDto;
import com.windfally.uploader.upload.dto.NotionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDto toEventDto(NotionDto notionDto);

    com.windfally.uploader.upload.dto.EventDto toUploadEventDto(Event event);
}

