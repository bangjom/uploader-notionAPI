package com.windfally.uploader.upload.dto;

import com.lannstark.DefaultHeaderStyle;
import com.lannstark.ExcelColumn;
import com.lannstark.ExcelColumnStyle;
import com.lannstark.style.DefaultExcelCellStyle;
import lombok.Data;

@Data
@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER")
)
public class EventDto extends ExcelDto{
    @ExcelColumn(headerName = "제목")
    private String title;
}
