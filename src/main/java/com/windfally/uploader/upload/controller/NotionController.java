package com.windfally.uploader.upload.controller;

import com.petersamokhin.notionapi.Notion;
import com.petersamokhin.notionapi.model.NotionCredentials;
import com.windfally.uploader.upload.dto.NotionDto;
import com.windfally.uploader.upload.service.NotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NotionController {

    private final NotionService notionService;
    private final HttpSession httpSession;

    @GetMapping("/date")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getDateSelect() {
        return "upload/date";
    }

    @PostMapping("/date")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getDate(HttpServletRequest request) throws IOException {
        log.info("{}", request.getParameter("date"));
        return "redirect:/upload/notion/" + request.getParameter("date");
    }

    @GetMapping("/upload/notion/{date}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getNotionData(@PathVariable String date, Model model) {
        List<NotionDto> data = notionService.getEventsByNotion(date);
        httpSession.removeAttribute("data");
        model.addAttribute("date",date);
        model.addAttribute("data", data);
        httpSession.setAttribute("data", data);
        return "upload/notionList";
    }

    @PostMapping("/upload/notion/{date}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String postNotionData(@PathVariable String date) {
        List<NotionDto> data = (List<NotionDto>) httpSession.getAttribute("data");
        notionService.postEvents(data);
        return "redirect:/upload/notion/" + date;
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void test(){
    }
}
