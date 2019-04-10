package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.github.pagehelper.PageInfo;

public interface IDacService {
	
    void saveDac(TbDac dac);

    void deleteDac(String id);

    void updateDac(TbIcsVo vo) throws Exception;

    PageInfo<TbDac> listDac(QueryObject queryObject);

    TbDac getDac(String id);
}
