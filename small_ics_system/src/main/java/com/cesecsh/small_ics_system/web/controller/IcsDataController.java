package com.cesecsh.small_ics_system.web.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.cesecsh.small_ics_system.model.TbIcsData;
import com.cesecsh.small_ics_system.service.IIcsDataService;
import com.cesecsh.small_ics_system.util.Result;
import com.cesecsh.small_ics_system.vo.TbIcsDataExcelVo;
import com.cesecsh.small_ics_system.vo.TbIcsDataVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ics/data")
public class IcsDataController {
    @Autowired
    private IIcsDataService icsDataService;

    @RequestMapping("/update")
    public Result updateData(@RequestBody TbIcsData data) {
        icsDataService.updateData(data);
        return Result.success();
    }

    @RequestMapping("/get")
    public Result getData(@RequestBody TbIcsData data) {
        data = icsDataService.getData(data.getIcsId());
        return Result.success(data);
    }

    @RequestMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) throws Exception {
        List<TbIcsDataExcelVo> list = new ArrayList<>();
        TbIcsDataExcelVo vo = new TbIcsDataExcelVo("0", "100");
        list.add(vo);
        vo = new TbIcsDataExcelVo("1", "101");
        list.add(vo);
        vo = new TbIcsDataExcelVo("2", "102");
        list.add(vo);
        vo = new TbIcsDataExcelVo("3", "103");
        list.add(vo);

        ExportParams params = new ExportParams();
        params.setSheetName("对照表");
        params.setTitle("对照表");

        Workbook workbook = ExcelExportUtil.exportExcel(params, TbIcsDataExcelVo.class, list);

        response.setHeader("content-Type", "application/vnd.ms-excel;Charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("对照表模板.xls", "UTF-8"));
        workbook.write(response.getOutputStream());
    }

    @RequestMapping("/importData")
    public Result importData(TbIcsDataVo vo) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<TbIcsDataExcelVo> list = ExcelImportUtil.importExcel(vo.getFile().getInputStream(), TbIcsDataExcelVo.class, params);
        icsDataService.importData(list, vo.getIcsDataId());
        return Result.success();
    }

    @RequestMapping("/exportData")
    public void exportData(HttpServletResponse response, String icsDataId) throws Exception {
        List<TbIcsDataExcelVo> list = icsDataService.exportData(icsDataId);

        ExportParams params = new ExportParams();
        params.setSheetName("对照表");
        params.setTitle("对照表");

        Workbook workbook = ExcelExportUtil.exportExcel(params, TbIcsDataExcelVo.class, list);

        response.setHeader("content-Type", "application/vnd.ms-excel;Charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("对照表.xls", "UTF-8"));
        workbook.write(response.getOutputStream());
    }
}
