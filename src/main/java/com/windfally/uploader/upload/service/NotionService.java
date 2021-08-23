package com.windfally.uploader.upload.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.windfally.uploader.events.dto.EventDto;
import com.windfally.uploader.events.service.EventService;
import com.windfally.uploader.upload.dto.NotionDto;
import com.windfally.uploader.events.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NotionService {

    private static final String TOKEN = "secret_dXXBaC5i7lpFhzvI8c8IOjGpvEkWKWBOE7iTqg9iOGc";
    private static final String NOTION_GET_URL = "https://api.notion.com/v1/databases/9b0e904354944504804bdf119bc78353/query";
    private static final String NOTION_PATCH_URL = "https://api.notion.com/v1/pages/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final EventService eventService;

    public List<NotionDto> getEventsByNotion(String parameter) {
        ResponseEntity<Map> exchange = getDataByNotion(parameter);
        ArrayList<NotionDto> eventList = new ArrayList<>();
        ArrayList<Map> results = (ArrayList) exchange.getBody().get("results");
        for (Map<String, Object> data : results) {
            Optional<NotionDto> notionDto = parsingNotionData(data);
            if (!notionDto.isEmpty()) {
                eventList.add(notionDto.get());
            }
        }
        return eventList;
    }

    private Optional<NotionDto> parsingNotionData(Map<String, Object> data) {
        Map<String, Object> properties = (Map<String, Object>) data.get("properties");
        String condition = convert(((Map) properties).get("발행"));
        if (condition != null && condition.equals("승인")) {
            NotionDto notionDto = NotionDto.builder()
                    .title(convert(((Map) properties).get("이름")))
                    .temp_schedule_detail(convert(((Map) properties).get("상세")))
                    .show_dates(Arrays.asList(isShowDateNull(convert(((Map) properties).get("표시"))).split(",")))
                    .content(convert(((Map) properties).get("본문")))
                    .city(isCityNull(convert(((Map) properties).get("시"))))
                    .district(convert(((Map) properties).get("구")))
                    .tags(Arrays.asList(Optional.ofNullable(convert(((Map) properties).get("글자태그"))).orElse(",").split(",")))
                    .linkUrl(convert(((Map) properties).get("링크")))
                    .pageId(data.get("id").toString())
                    .publish(condition)
                    .build();
            return Optional.of(notionDto);
        }
        return Optional.ofNullable(null);
    }

    private ResponseEntity<Map> getDataByNotion(String parameter) {
        LocalDate parse = LocalDate.parse(parameter, DateTimeFormatter.ISO_LOCAL_DATE);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN);
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> filter = new HashMap<>();
        filter.put("property", "날짜");
        filter.put("date", new HashMap<>() {{
            put("equals", parse.toString());
        }});
        body.put("filter", filter);
        ResponseEntity<Map> exchange = restTemplate.exchange(NOTION_GET_URL, HttpMethod.POST, new HttpEntity<>(body, headers), Map.class);
        return exchange;
    }

    private String convert(Object map) {
        Map<String, Object> map1 = (Map<String, Object>) map;
        if (map == null) {
            return "";
        }
        if (map1.get("type").equals("url")) {
            return ((Map<String, Object>) map).get(map1.get("type")).toString();
        } else {
            Object o = ((Map<String, Object>) map).get(map1.get("type"));
            if (((ArrayList) o).size() == 0) {
                return "";
            }
            Object list = ((ArrayList) o).get(0);
            return ((Map<String, Object>) list).get("plain_text").toString();
        }

    }

    private String isShowDateNull(String showDates) {
        if (showDates.equals("")) {
            return LocalDate.now().toString();
        }
        return showDates;
    }

    private String isCityNull(String city) {
        if (city.equals("")) {
            return "전국";
        }
        return city;
    }

    private void changeApproveToComplete(NotionDto notionDto) {
        String json = "{\"properties\":{\"발행\":{ \"rich_text\":[{ \"text\":{\"content\":\"완료\"}}]}}}";
        Map body = null;
        try {
            body = objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            log.info("{}", e.getMessage());
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN);
        ResponseEntity<Map> exchange = restTemplate.exchange(NOTION_PATCH_URL + notionDto.getPageId(), HttpMethod.PATCH, new HttpEntity<>(body, headers), Map.class);
    }

    public void postEvents(List<NotionDto> events) {
        for (NotionDto notion : events) {
            EventDto eventDto = EventMapper.INSTANCE.toEventDto(notion);
            HashMap<String, Boolean> result = eventService.addEvent(eventDto);
            if (result.get("success")) {
                changeApproveToComplete(notion);
            }
        }
    }
}