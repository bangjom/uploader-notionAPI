package com.windfally.uploader.upload.dto;

import com.lannstark.DefaultHeaderStyle;
import com.lannstark.ExcelColumn;
import com.lannstark.ExcelColumnStyle;
import com.lannstark.style.DefaultExcelCellStyle;
import com.windfally.uploader.user.entity.Address;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER")
)
public class UserDto extends ExcelDto {

    @ExcelColumn(headerName = "데이터베이스 id")
    private String userId;

    @ExcelColumn(headerName = "광역시")
    private String city;

    @ExcelColumn(headerName = "시/군/구")
    private String district;

    @ExcelColumn(headerName = "이메일")
    private String email;

    @ExcelColumn(headerName = "전화번호")
    private String phone;

    @ExcelColumn(headerName = "가입 경로")
    private String joinBy;

    @ExcelColumn(headerName = "가입 날짜")
    private LocalDateTime createdDate;
}
