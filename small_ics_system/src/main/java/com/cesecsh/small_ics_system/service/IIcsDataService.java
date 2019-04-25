package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.model.TbIcsData;
import com.cesecsh.small_ics_system.vo.TbIcsDataExcelVo;

import java.util.List;

public interface IIcsDataService {
    void saveData(TbIcsData data);

    void updateData(TbIcsData data);

    TbIcsData getData(String icsId);

    String getCheckValueByIcsDataIdAndReciveValue(String icsDataId, String reciveValue);

    void importData(List<TbIcsDataExcelVo> list, String icsDataId);

    List<TbIcsDataExcelVo> exportData(String icsDataId);

    void saveDataValue(String id, String data);
}
