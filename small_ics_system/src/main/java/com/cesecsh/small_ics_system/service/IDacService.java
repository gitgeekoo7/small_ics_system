package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.dto.TbDacDto;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.query.DacQueryObject;
import com.cesecsh.small_ics_system.vo.TbDacChannelVo;
import com.cesecsh.small_ics_system.vo.TbDacVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IDacService {

    void saveDac(TbDac dac);

    void deleteDac(String id);

    void updateDac(TbDacVo vo) throws Exception;

    PageInfo<TbDacChannelVo> listDac(DacQueryObject queryObject);

    TbDacDto getDac(String id) throws Exception;

    List<TbDac> listDacByIcsId(String id);
}
