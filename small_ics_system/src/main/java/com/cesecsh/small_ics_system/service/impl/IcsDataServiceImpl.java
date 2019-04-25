package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.mapper.IcsDataMapper;
import com.cesecsh.small_ics_system.model.TbIcsData;
import com.cesecsh.small_ics_system.service.IIcsDataService;
import com.cesecsh.small_ics_system.vo.TbIcsDataExcelVo;
import com.cesecsh.small_ics_system.vo.TbIcsDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class IcsDataServiceImpl implements IIcsDataService {
    @Autowired
    private IcsDataMapper icsDataMapper;

    @Override
    public void saveData(TbIcsData data) {
        data.setId(UUID.randomUUID().toString().replace("-", ""));
        Date date = new Date();
        data.setCreateTime(date);
        data.setUpdateTime(date);
        icsDataMapper.insert(data);
    }

    @Override
    public void updateData(TbIcsData data) {
        data.setUpdateTime(new Date());
        icsDataMapper.update(data);
    }

    @Override
    public void importData(List<TbIcsDataExcelVo> list, String icsDataId) {
        List<TbIcsDataVo> dataChecks = icsDataMapper.listDataCheckByIcsDataId(icsDataId);

        for (TbIcsDataExcelVo vo : list) {
            for (TbIcsDataVo dataCheck : dataChecks) {
                if (dataCheck.getReciveValue().equals(vo.getReciveValue())) {
                    icsDataMapper.deleteDataCheck(dataCheck.getId());
                    break;
                }
            }
            TbIcsDataVo data = new TbIcsDataVo();
            data.setId(UUID.randomUUID().toString().replace("-", ""));
            data.setIcsDataId(icsDataId);

            data.setReciveValue(vo.getReciveValue());
            data.setCheckValue(vo.getCheckValue());

            icsDataMapper.insertDataCheck(data);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbIcsDataExcelVo> exportData(String icsDataId) {
        List<TbIcsDataVo> dataChecks = icsDataMapper.listDataCheckByIcsDataId(icsDataId);
        List<TbIcsDataExcelVo> list = new ArrayList<>();
        TbIcsDataExcelVo vo;
        for (TbIcsDataVo dataCheck : dataChecks) {
            vo = new TbIcsDataExcelVo(dataCheck.getReciveValue(), dataCheck.getCheckValue());
            list.add(vo);
        }
        return list;
    }
}
