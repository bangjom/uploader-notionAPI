package com.windfally.uploader.upload.service;

import com.windfally.uploader.events.service.EventService;
import com.windfally.uploader.upload.dto.EventDto;
import com.windfally.uploader.upload.dto.UserDto;
import com.windfally.uploader.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ExcelService {

    private static final String START = "2021-04-01";

    private final EventService eventService;
    private final UserService userService;

    public List<UserDto> getUserData(String start, String end) {
        LocalDate parsingStart = getStartDate(start);
        LocalDate parsingEnd = getEndDate(end);
        return userService.getUsersByPeriod(parsingStart.atStartOfDay(), parsingEnd.atTime(LocalTime.MAX));
    }

    public List<EventDto> getEventData(String start) {
        LocalDate parsingStart = getStartDate(start);
        return eventService.getEventsAfterShowDates(parsingStart);
    }

    private LocalDate getEndDate(String end) {
        if (end.equals("")) {
            end = LocalDate.now().toString();
        }
        LocalDate parsingEnd = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
        return parsingEnd;
    }

    private LocalDate getStartDate(String start) {
        if (start.equals("")) {
            start = START;
        }
        return LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
    }

    public void uploadExcel(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (extension.equals("xlsx") || extension.equals("xls")) {
            readXls(file);
        }
    }

    private void readXls(MultipartFile file) {
        log.info("service {}",file);
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (extension.equals("xlsx")) {
            try {
                workbook = new XSSFWorkbook(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (extension.equals("xls")) {
            try {
                workbook = new HSSFWorkbook(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            com.windfally.uploader.events.dto.EventDto data = com.windfally.uploader.events.dto.EventDto.builder()
                    .title(row.getCell(0).getStringCellValue())
                    .city(row.getCell(1).getStringCellValue())
                    .district(row.getCell(2).getStringCellValue())
                    .show_dates(Arrays.asList(row.getCell(3).getStringCellValue().split(",")))
                    .content(row.getCell(4).getStringCellValue())
                    .temp_schedule(row.getCell(5).getStringCellValue())
                    .temp_schedule_detail(row.getCell(6).getStringCellValue())
                    .build();
            log.info("{}",data.getContent());
//            (int) row.getCell(0).getNumericCellValue()
            eventService.addEvent(data);
        }
    }
}
