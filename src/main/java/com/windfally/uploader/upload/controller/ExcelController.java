package com.windfally.uploader.upload.controller;

import com.lannstark.excel.onesheet.OneSheetExcelFile;
import com.windfally.uploader.upload.dto.EventDto;
import com.windfally.uploader.upload.dto.UserDto;
import com.windfally.uploader.upload.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ExcelController {
    private static final String USER = "사용자";
    private static final String EVENT = "이벤트";

    private final ExcelService excelService;

    @GetMapping("/upload/excel")
    public String getExcelForm() {
        return "upload/excel";
    }

    @PostMapping("/upload/excel")
    public void uploadExcel(@RequestParam("file") MultipartFile file){
        log.info("controller {}",file);
        excelService.uploadExcel(file);
    }

    @GetMapping("/download/excel")
    public String getSelectForm() {
        return "upload/selectData";
    }

    @PostMapping("/download/excel")
    public void downloadData(String kinds, String start, String end, HttpServletResponse response) throws IOException {
        if (kinds.equals(USER)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"user.xls\"");
            List<UserDto> userData = excelService.getUserData(start, end);
            OneSheetExcelFile<UserDto> sheetExcelFile = new OneSheetExcelFile<>(userData, UserDto.class);
            sheetExcelFile.write(response.getOutputStream());
        }
        if (kinds.equals(EVENT)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"event.xls\"");
            List<EventDto> eventData = excelService.getEventData(start);
            OneSheetExcelFile<EventDto> sheetExcelFile = new OneSheetExcelFile<>(eventData, EventDto.class);
            sheetExcelFile.write(response.getOutputStream());
        }
    }
}
