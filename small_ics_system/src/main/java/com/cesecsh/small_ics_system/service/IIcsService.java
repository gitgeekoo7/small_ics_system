package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.github.pagehelper.PageInfo;

public interface IIcsService {
    void saveIcs(TbIcs ics);

    void deleteIcs(String id);

    void updateIcs(TbIcsVo vo) throws Exception;

    PageInfo<TbIcs> listIcs(QueryObject queryObject);

    TbIcs getIcs(String id);
}
