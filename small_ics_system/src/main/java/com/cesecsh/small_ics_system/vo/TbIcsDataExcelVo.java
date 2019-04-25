package com.cesecsh.small_ics_system.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TbIcsDataExcelVo {
    @Excel(name = "接收值", isImportField = "true", orderNum = "0")
    private String reciveValue;
    @Excel(name = "查表值", isImportField = "true", orderNum = "1")
    private String checkValue;
}
